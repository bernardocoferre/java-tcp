package com.bernardocoferre.tcp.concerns;

import com.bernardocoferre.tcp.support.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MessageType {

    TEXT("text"),
    USER("user"),
    DATE("date"),
    UNKNOWN("unknown"),
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageType.class);

    private String label;

    MessageType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static MessageType convertFrame(FrameType frameType) {
        switch (frameType) {
            case ACK:
                return MessageType.UNKNOWN;

            case TEXT:
                return MessageType.TEXT;

            case USER:
                return MessageType.USER;

            case DATE:
                return MessageType.DATE;

            default:
                LOGGER.warn(new Str.Builder("Message type not mapped for frame: [{}]").build(frameType));
                break;
        }

        return null;
    }

}
