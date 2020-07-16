package com.bernardocoferre.tcp.concerns;

public enum FrameType {

    ACK("A0"),
    TEXT("A1"),
    USER("A2"),
    DATE("A3")
    ;

    private String label;

    private FrameType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static FrameType fromLabel(String value) {
        for (FrameType type : values())
            if (type.label.equals(value))
                return type;

        return null;
    }

}
