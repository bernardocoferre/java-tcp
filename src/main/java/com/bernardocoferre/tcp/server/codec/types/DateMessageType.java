package com.bernardocoferre.tcp.server.codec.types;

import com.bernardocoferre.tcp.concerns.DetectsMessageType;
import com.bernardocoferre.tcp.concerns.Field;
import com.bernardocoferre.tcp.model.DateMessage;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.model.UserMessage;
import com.bernardocoferre.tcp.server.codec.FieldReader;
import com.bernardocoferre.tcp.server.codec.FieldReaderType;
import com.bernardocoferre.tcp.support.BufferUtils;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DateMessageType implements DetectsMessageType {

    @Override
    public void typify(MessageReference reference, IoBuffer data) {
        FieldReader reader = new FieldReader(data, FieldReaderType.DATE_MESSAGE);
        Map<Field, IoBuffer> fields = reader.readAll();

        DateMessage dateMessage = new DateMessage();
        dateMessage.setDay(BufferUtils.getHexInt(fields.get(Field.DAY)));
        dateMessage.setMonth(BufferUtils.getHexInt(fields.get(Field.MONTH)));
        dateMessage.setYear(BufferUtils.getHexInt(fields.get(Field.YEAR)));
        dateMessage.setHour(BufferUtils.getHexInt(fields.get(Field.HOUR)));
        dateMessage.setMinute(BufferUtils.getHexInt(fields.get(Field.MINUTE)));
        dateMessage.setSecond(BufferUtils.getHexInt(fields.get(Field.SECOND)));

        reference.setDateMessage(dateMessage);
    }

}
