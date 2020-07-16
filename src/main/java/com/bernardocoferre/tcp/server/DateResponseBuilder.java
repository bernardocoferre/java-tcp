package com.bernardocoferre.tcp.server;

import com.bernardocoferre.tcp.concerns.FrameType;
import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.model.MessageReference;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateResponseBuilder extends ResponseBuilder {

    @Override
    public FrameType frame() {
        return FrameType.DATE;
    }

    @Override
    public IoBuffer build(Message message) throws Exception {
        MessageReference received = message.getReference().get(0);
        String timeZone = received.getTextMessage().getText();

        LocalDateTime localDate = new Date().toInstant().atZone(ZoneId.of(timeZone)).toLocalDateTime();

        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear() % 100;
        int hour = localDate.getHour();
        int minute = localDate.getMinute();
        int second = localDate.getSecond();

        IoBuffer dateBuffer = IoBuffer.allocate(0);
        dateBuffer.setAutoExpand(true);

        StringUtils.leftPad(Integer.toHexString(day), 2, "0");

        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(day), 2, "0")));
        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(month), 2, "0")));
        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(year),2, "0")));
        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(hour), 2, "0")));
        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(minute), 2, "0")));
        dateBuffer.put(Hex.decodeHex(StringUtils.leftPad(Integer.toHexString(second), 2, "0")));

        dateBuffer.flip();

        return dateBuffer;
    }

}
