package com.bernardocoferre.tcp.server;

import com.bernardocoferre.tcp.concerns.DetectsMessageType;
import com.bernardocoferre.tcp.concerns.FrameType;
import com.bernardocoferre.tcp.concerns.MessageDirection;
import com.bernardocoferre.tcp.concerns.MessageType;
import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.model.MessageData;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.server.codec.types.DateMessageType;
import com.bernardocoferre.tcp.server.codec.types.TextMessageType;
import com.bernardocoferre.tcp.support.Crc;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResponseBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBuilder.class);

    /**
     * Builds a fresh new {@link IoBuffer} instance containing the DATA field that will be sent back to client.
     *
     * @param message the current message
     * @return the created buffer
     * @throws Exception represents any errors occurred during the build process
     */
    public abstract IoBuffer build(Message message) throws Exception;

    /**
     * Returns the {@link FrameType} of message that will be sent back.
     *
     * @return the frame
     */
    public abstract FrameType frame();

    /**
     * Creates a new {@link MessageReference} instance with the message that will be sent back.
     *
     * @param message the current message
     * @return the fresh new created message reference
     * @throws Exception represents any errors occurred during the build process
     */
    public MessageReference createResponse(Message message) throws Exception {
        IoBuffer buffer = this.build(message);
        String rawHex = buffer.getHexDump();
        int length = buffer.remaining();

        MessageReference response = new MessageReference();
        response.setMessage(message);
        response.setDirection(MessageDirection.SENT);

        this.addType(response);

        MessageData responseData = new MessageData();
        responseData.setInit("0A");
        responseData.setBytes(String.format("%02X", length + 5));
        responseData.setFrame(this.frame().getLabel());
        responseData.setData(rawHex);
        responseData.setCrc(Crc.fromMessageData(responseData));
        responseData.setEnd("0D");

        response.setData(responseData);
        response.setRawHex(responseData.compileRawHex());

        return response;
    }

    /**
     * Add the typified message to the message reference.
     *
     * @param response the current message reference
     * @throws Exception in case of parse errors
     */
    private void addType(MessageReference response) throws Exception {
        DetectsMessageType type = this.findType(response.getMessage().getType());

        if (type == null)
            return;

        type.typify(response, this.build(response.getMessage()));
    }

    /**
     * Finds the typified message based on the current message type.
     *
     * @param type the current message type
     * @return message subtype handler
     */
    private DetectsMessageType findType(MessageType type) {
        switch (type) {
            case TEXT:
            case USER:
                return new TextMessageType();

            case DATE:
                return new DateMessageType();

            default:
                LOGGER.warn(new Str.Builder("Type not mapped for message: [{}]").build(type));
        }

        return null;
    }

}
