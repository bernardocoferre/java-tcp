package com.bernardocoferre.tcp.server.codec.types;

import com.bernardocoferre.tcp.concerns.Field;
import com.bernardocoferre.tcp.concerns.DetectsMessageType;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.model.UserMessage;
import com.bernardocoferre.tcp.server.codec.FieldReaderType;
import com.bernardocoferre.tcp.server.codec.FieldReader;
import com.bernardocoferre.tcp.support.BufferUtils;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;

import java.util.Map;

public class UserMessageType implements DetectsMessageType {

    @Override
    public void typify(MessageReference reference, IoBuffer data) {
        FieldReader reader = new FieldReader(data, FieldReaderType.USER_MESSAGE);
        Map<Field, IoBuffer> fields = reader.readAll();

        UserMessage userMessage = new UserMessage();
        userMessage.setAge(BufferUtils.getHexInt(fields.get(Field.AGE)));
        userMessage.setWeight(BufferUtils.getHexInt(fields.get(Field.WEIGHT)));
        userMessage.setHeight(BufferUtils.getHexInt(fields.get(Field.HEIGHT)));
        userMessage.setNameLength(BufferUtils.getHexInt(fields.get(Field.NAME_LENGTH)));
        userMessage.setName(BufferUtils.getHexString(fields.get(Field.NAME)));

        reference.setUserMessage(userMessage);
    }

}
