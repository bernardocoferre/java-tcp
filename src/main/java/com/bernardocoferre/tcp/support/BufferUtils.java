package com.bernardocoferre.tcp.support;

import org.apache.mina.core.buffer.IoBuffer;

import java.util.Objects;

public class BufferUtils {

    /**
     * The last retrieved field index.
     */
    private int last = 0;

    /**
     * The original IoBuffer to be read.
     */
    private IoBuffer in;

    /**
     * Creates a new BufferUtils instance from the received {@link IoBuffer} instance.
     *
     * @param buffer the buffer received from client
     */
    public BufferUtils(IoBuffer buffer) {
        Objects.requireNonNull(buffer, "IoBuffer cannot be null!!");
        this.in = buffer;
    }

    /**
     * Reads the next field single byte from buffer.
     *
     * @return sliced field
     */
    public IoBuffer next() {
        return this.in.getSlice(this.last++, 1);
    }

    /**
     * Read the field present in a given index.
     *
     * @param index the field index
     * @param length the field length
     * @return sliced field
     */
    public IoBuffer get(int index, int length) {
        this.last = index + length;
        return this.in.getSlice(index, length);
    }

    /**
     * Returns the current field hex dump.
     *
     * @param field current field
     * @return hex dump
     */
    public static String getFieldHexDump(IoBuffer field) {
        return field == null ? null : field.getHexDump();
    }

    /**
     * Returns the current field hex dump.
     *
     * @param field current field
     * @return hex dump
     */
    public static int getHexInt(IoBuffer field) {
        if (field == null)
            return 0;

        return Integer.parseInt(getFieldHexDump(field), 16);
    }

    /**
     * Returns the current field hex dump.
     *
     * @param field current field
     * @return hex dump
     */
    public static String getHexString(IoBuffer field) {
        if (field == null)
            return null;

        return Str.fromHex(getFieldHexDump(field));
    }

}
