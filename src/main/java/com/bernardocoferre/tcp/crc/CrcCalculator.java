package com.bernardocoferre.tcp.crc;

public class CrcCalculator {

    private AlgoParams parameters;
    private byte hashSize = 8;
    private long mask = 0xFFFFFFFFFFFFFFFFL;
    private long[] table = new long[256];

    public CrcCalculator(AlgoParams params) {
        this.parameters = params;
        this.hashSize = (byte) params.hashSize;

        if (this.hashSize < 64) {
            this.mask = (1L << this.hashSize) - 1;
        }

        this.createTable();
    }

    public long calc(byte[] data, int offset, int length) {
        long init = this.parameters.refOut ? reverseBits(this.parameters.init, this.hashSize) : this.parameters.init;
        long hash = this.computeCrc(init, data, offset, length);

        return (hash ^ this.parameters.xorOut) & this.mask;
    }

    private long computeCrc(long init, byte[] data, int offset, int length) {
        long crc = init;

        if (this.parameters.refOut) {
            for (int i = offset; i < offset + length; i++) {
                crc = (this.table[(int)((crc ^ data[i]) & 0xFF)] ^ (crc >>> 8));
                crc &= this.mask;
            }
        } else {
            int toRight = (this.hashSize - 8);
            toRight = toRight < 0 ? 0 : toRight;

            for (int i = offset; i < offset + length; i++) {
                crc = (this.table[(int)(((crc >> toRight) ^ data[i]) & 0xFF)] ^ (crc << 8));
                crc &= this.mask;
            }
        }

        return crc;
    }

    private void createTable() {
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = this.createTableEntry(i);
        }
    }

    private long createTableEntry(int index) {
        long r = (long)index;

        if (this.parameters.refIn) {
            r = reverseBits(r, this.hashSize);
        } else if (this.hashSize > 8) {
            r <<= (this.hashSize - 8);
        }

        long lastBit = (1L << (this.hashSize - 1));

        for (int i = 0; i < 8; i++) {
            if ((r & lastBit) != 0) {
                r = ((r << 1) ^ this.parameters.poly);
            } else {
                r <<= 1;
            }
        }

        if (this.parameters.refOut) {
            r = reverseBits(r, this.hashSize);
        }

        return r & this.mask;
    }

    public static long reverseBits(long ul, int valueLength) {
        long newValue = 0;

        for (int i = valueLength - 1; i >= 0; i--) {
            newValue |= (ul & 1) << i;
            ul >>= 1;
        }

        return newValue;
    }

}
