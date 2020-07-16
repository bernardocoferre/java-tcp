package com.bernardocoferre.tcp.server.codec;

import com.bernardocoferre.tcp.concerns.Field;
import com.bernardocoferre.tcp.support.BufferUtils;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FieldReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldReader.class);

    private IoBuffer buffer;
    private FieldReaderType handler;
    private Map<Field, IoBuffer> values = new HashMap<>();

    /**
     * The BufferUtils helper.
     */
    private BufferUtils helper;

    public FieldReader(IoBuffer buffer, FieldReaderType handler) {
        this.buffer = buffer;
        this.handler = handler;
        this.helper = new BufferUtils(buffer);
    }

    public Map<Field, IoBuffer> readAll() {
        int index = 0;

        while (this.buffer.hasRemaining()) {
            IoBuffer field = this.field(index);
            this.fill(field, index);

            index++;
        }

        return this.values;
    }

    /**
     * Detect and returns the current field by analyzing the current index.
     * Before return the proper field, we will consume the detected field from buffer.
     * For data field, we need to consume the same amount of data length (calculated using the BYTES field).
     *
     * @param index the current read field index
     * @return IoBuffer instance slice for the current field
     */
    private IoBuffer field(int index) {
        this.consumes(1);

        if (! this.handler.isCheckData())
            return this.helper.next();

        int length = this.handler.length(this.values);
        int dataIndex = this.handler.getDataIndex();

        if (index != dataIndex)
            return this.helper.next();

        this.consumes(length - 1);
        return this.helper.get(dataIndex, length);
    }

    /**
     * Consumes data from the original received buffer.
     *
     * @param amount the amount to consume from buffer
     */
    private void consumes(int amount) {
        for (int i = 0; i < amount; i++)
            this.buffer.get();
    }

    /**
     * Fills the proper field by analyzing the field index.
     *
     * @param index the field index
     */
    private void fill(IoBuffer data, int index) {
        Field field = this.handler.getFields().get(index);

        if (field == null) {
            LOGGER.warn(new Str.Builder("Destination field not mapped for index: [{}]").build(index));
            return;
        }

        this.values.put(field, data);
    }

}
