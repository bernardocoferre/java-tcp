package com.bernardocoferre.tcp.concerns;

public enum Field {

    /**
     * Maps the protocol structure fields.
     */
    INIT, BYTES, FRAME, DATA, CRC, END,

    /**
     * Maps the user request message fields.
     */
    AGE, WEIGHT, HEIGHT, NAME_LENGTH, NAME,

    /**
     * Maps the date response message fields.
     */
    DAY, MONTH, YEAR, HOUR, MINUTE, SECOND
    ;

}
