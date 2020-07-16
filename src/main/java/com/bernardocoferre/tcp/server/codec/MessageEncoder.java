package com.bernardocoferre.tcp.server.codec;

import com.bernardocoferre.tcp.model.MessageData;
import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MessageEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        MessageData messageData = (MessageData) message;

        IoBuffer buffer = IoBuffer.allocate(5);
        buffer.setAutoExpand(true);

        buffer.put(Hex.decodeHex(messageData.getInit()));
        buffer.put(Hex.decodeHex(messageData.getBytes()));
        buffer.put(Hex.decodeHex(messageData.getFrame()));

        if (messageData.getData() != null)
            buffer.put(Hex.decodeHex(messageData.getData().replaceAll("\\s+", "")));

        buffer.put(Hex.decodeHex(messageData.getCrc()));
        buffer.put(Hex.decodeHex(messageData.getEnd()));

        buffer.flip();

        out.write(buffer);
    }

}
