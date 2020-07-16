package com.bernardocoferre.tcp.server.codec;

import com.bernardocoferre.tcp.concerns.*;
import com.bernardocoferre.tcp.model.*;
import com.bernardocoferre.tcp.model.MessageData;
import com.bernardocoferre.tcp.server.codec.types.TextMessageType;
import com.bernardocoferre.tcp.server.codec.types.UserMessageType;
import com.bernardocoferre.tcp.support.Addr;
import com.bernardocoferre.tcp.support.BufferUtils;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class MessageDecoder extends CumulativeProtocolDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    /**
     * The original received raw hex value.
     */
    private String rawHex;

    private Map<Field, IoBuffer> fields;

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        this.rawHex = in.getHexDump();

        // bootstrap all protocol fields
        FieldReader reader = new FieldReader(in, FieldReaderType.EXTERNAL_MESSAGE);
        this.fields = reader.readAll();

        Message message = this.createMessage(session);
        out.write(message);

        return false;
    }

    /**
     * Creates a new fresh MessageData instance using received data.
     *
     * @return created MessageData
     */
    private MessageData createMessageData() {
        MessageData messageData = new MessageData();
        messageData.setInit(BufferUtils.getFieldHexDump(this.fields.get(Field.INIT)));
        messageData.setBytes(BufferUtils.getFieldHexDump((this.fields.get(Field.BYTES))));
        messageData.setFrame(BufferUtils.getFieldHexDump((this.fields.get(Field.FRAME))));
        messageData.setData(BufferUtils.getFieldHexDump((this.fields.get(Field.DATA))));
        messageData.setCrc(BufferUtils.getFieldHexDump((this.fields.get(Field.CRC))));
        messageData.setEnd(BufferUtils.getFieldHexDump((this.fields.get(Field.END))));

        return messageData;
    }

    /**
     * Creates a new fresh Message instance using received data.
     *
     * @return created Message
     */
    private Message createMessage(IoSession session) {
        FrameType frameType = FrameType.fromLabel(this.fields.get(Field.FRAME).getHexDump());
        MessageType messageType = MessageType.convertFrame(frameType);

        Message message = new Message();
        message.setSessionId(session.getId());
        message.setType(messageType);
        message.setIpAddress(Addr.getIpAddress(session));
        message.setReceivedAt(new Date());
        message.setReference(new LinkedList<>(Collections.singletonList(this.createMessageReference(message))));

        return message;
    }

    /**
     * Creates a new fresh MessageReference instance using received data.
     *
     * @return created MessageReference
     */
    private MessageReference createMessageReference(Message message) {
        MessageReference messageReference = new MessageReference();
        messageReference.setMessage(message);
        messageReference.setData(this.createMessageData());
        messageReference.setRawHex(this.rawHex);
        messageReference.setDirection(MessageDirection.RECEIVED);

        this.addType(messageReference);

        return messageReference;
    }

    private void addType(MessageReference messageReference) {
        DetectsMessageType type = this.findType(messageReference.getMessage().getType());

        if (type == null)
            return;

        type.typify(messageReference, this.fields.get(Field.DATA));
    }

    private DetectsMessageType findType(MessageType type) {
        switch (type) {
            case TEXT:
            case DATE:
                return new TextMessageType();

            case USER:
                return new UserMessageType();

            case UNKNOWN:
                return null;

            default:
                LOGGER.warn(new Str.Builder("Type not mapped for message: [{}]").build(type));
        }

        return null;
    }

}
