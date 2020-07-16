package com.bernardocoferre.tcp.support;

import com.bernardocoferre.tcp.crc.AlgoParams;
import com.bernardocoferre.tcp.crc.CrcCalculator;
import com.bernardocoferre.tcp.model.MessageData;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crc {

    private static final Logger LOGGER = LoggerFactory.getLogger(Crc.class);

    public static final AlgoParams CRC8 = new AlgoParams("CRC-8", 8, 0x7, 0x0, false, false, 0x0, 0xF4);
    public static final AlgoParams CRC8_CDMA2000 = new AlgoParams("CRC-8/CDMA2000", 8, 0x9B, 0xFF, false, false, 0x0, 0xDA);
    public static final AlgoParams CRC8_DARC = new AlgoParams("CRC-8/DARC", 8, 0x39, 0x0, true, true, 0x0, 0x15);
    public static final AlgoParams CRC8_DVBS2 = new AlgoParams("CRC-8/DVB-S2", 8, 0xD5, 0x0, false, false, 0x0, 0xBC);
    public static final AlgoParams CRC8_EBU = new AlgoParams("CRC-8/EBU", 8, 0x1D, 0xFF, true, true, 0x0, 0x97);
    public static final AlgoParams CRC8_ICODE = new AlgoParams("CRC-8/I-CODE", 8, 0x1D, 0xFD, false, false, 0x0, 0x7E);
    public static final AlgoParams CRC8_ITU = new AlgoParams("CRC-8/ITU", 8, 0x7, 0x0, false, false, 0x55, 0xA1);
    public static final AlgoParams CRC8_MAXIM = new AlgoParams("CRC-8/MAXIM", 8, 0x31, 0x0, true, true, 0x0, 0xA1);
    public static final AlgoParams CRC8_ROHC = new AlgoParams("CRC-8/ROHC", 8, 0x7, 0xFF, true, true, 0x0, 0xD0);
    public static final AlgoParams CRC8_WCDMA = new AlgoParams("CRC-8/WCDMA", 8, 0x9B, 0x0, true, true, 0x0, 0x25);

    public static final AlgoParams[] PARAMS = new AlgoParams[]{
            CRC8, CRC8_CDMA2000, CRC8_DARC, CRC8_DVBS2, CRC8_EBU, CRC8_ICODE, CRC8_ITU, CRC8_MAXIM, CRC8_ROHC, CRC8_WCDMA
    };

    /**
     * Calculates the CRC-8 value from given BYTES, FRAME and DATA fields.
     * The result is the long value from algorithm.
     *
     * @param bytes the BYTES field
     * @param frame the FRAME field
     * @param data the DATA field
     * @return CRC-8 algorithm result
     */
    public static long crc8(String bytes, String frame, String data) {
        try {
            String hex = (bytes + frame + data).replaceAll("\\s+", "");
            byte[] b = Hex.decodeHex(hex.toCharArray());
            CrcCalculator calculator = new CrcCalculator(CRC8);

            return calculator.calc(b, 0, b.length);

        } catch (DecoderException e) {
            LOGGER.warn("Failed to calculate CRC field!", e);
            return 0;
        }
    }

    /**
     * Calculates the CRC-8 value from given BYTES, FRAME and DATA fields.
     * The result is the Hex value from algorithm.
     *
     * @param bytes the BYTES field
     * @param frame the FRAME field
     * @param data the DATA field
     * @return CRC-8 hex algorithm result
     */
    public static String crc8Hex(String bytes, String frame, String data) {
        long result = crc8(bytes, frame, data);
        return Long.toHexString(result).toUpperCase();
    }

    /**
     * Calculates the CRC-8 value from given {@link MessageData} instance.
     * The result is the Hex value from algorithm.
     *
     * @param data the message data
     * @return CRC-8 hex algorithm result
     */
    public static String fromMessageData(MessageData data) {
        long result = crc8(data.getBytes(), data.getFrame(), data.getData());
        return Long.toHexString(result).toUpperCase();
    }

}
