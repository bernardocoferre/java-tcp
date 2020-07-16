package com.bernardocoferre.tcp.server.codec;

import com.bernardocoferre.tcp.concerns.Field;
import org.apache.mina.core.buffer.IoBuffer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FieldReaderType {

    private static final List<Field> EXTERNAL_FIELDS = Arrays.asList(Field.INIT, Field.BYTES, Field.FRAME, Field.DATA, Field.CRC, Field.END);
    private static final List<Field> USER_FIELDS = Arrays.asList(Field.AGE, Field.WEIGHT, Field.HEIGHT, Field.NAME_LENGTH, Field.NAME);
    private static final List<Field> DATE_FIELDS = Arrays.asList(Field.DAY, Field.MONTH, Field.YEAR, Field.HOUR, Field.MINUTE, Field.SECOND);

    public static final FieldReaderType EXTERNAL_MESSAGE = new FieldReaderType(Field.DATA, Field.BYTES, 5, EXTERNAL_FIELDS);
    public static final FieldReaderType USER_MESSAGE = new FieldReaderType(Field.NAME, Field.NAME_LENGTH, 0, USER_FIELDS);
    public static final FieldReaderType DATE_MESSAGE = new FieldReaderType(null, null, 0, DATE_FIELDS);

    private boolean checkData = true;
    private Field dataField;
    private Field lengthField;
    private int lengthIgnore;
    private List<Field> fields;

    private FieldReaderType(Field dataField, Field lengthField, int lengthIgnore, List<Field> fields) {
        this.checkData = dataField != null;
        this.dataField = dataField;
        this.lengthField = lengthField;
        this.lengthIgnore = lengthIgnore;
        this.fields = fields;
    }

    public int length(Map<Field, IoBuffer> fields) {
        IoBuffer field = fields.get(this.lengthField);

        if (field == null)
            return 0;

        int length = Integer.parseInt(field.getHexDump(), 16);

        return length - lengthIgnore;
    }

    public boolean isCheckData() {
        return checkData;
    }

    public int getDataIndex() {
        return this.fields.indexOf(this.dataField);
    }

    public List<Field> getFields() {
        return fields;
    }

}
