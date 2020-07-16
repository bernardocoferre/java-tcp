package com.bernardocoferre.tcp.server.codec.types;

import com.bernardocoferre.tcp.concerns.DetectsMessageType;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.model.TextMessage;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;

public class TextMessageType implements DetectsMessageType {

    @Override
    public void typify(MessageReference reference, IoBuffer data) {
        TextMessage textMessage = new TextMessage();
        textMessage.setText(Str.fromHex(data.getHexDump()));

        reference.setTextMessage(textMessage);
    }

}
