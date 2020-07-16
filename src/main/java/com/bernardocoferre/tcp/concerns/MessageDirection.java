package com.bernardocoferre.tcp.concerns;

public enum MessageDirection {

    RECEIVED("received"),
    SENT("sent")
    ;

    private String label;

    private MessageDirection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
