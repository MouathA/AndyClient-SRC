package org.apache.commons.codec.binary;

public class Base32 extends BaseNCodec
{
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR;
    private static final byte[] DECODE_TABLE;
    private static final byte[] ENCODE_TABLE;
    private static final byte[] HEX_DECODE_TABLE;
    private static final byte[] HEX_ENCODE_TABLE;
    private static final int MASK_5BITS = 31;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;
    
    public Base32() {
        this(false);
    }
    
    public Base32(final boolean b) {
        this(0, null, b);
    }
    
    public Base32(final int n) {
        this(n, Base32.CHUNK_SEPARATOR);
    }
    
    public Base32(final int n, final byte[] array) {
        this(n, array, false);
    }
    
    public Base32(final int n, final byte[] array, final boolean b) {
        super(5, 8, n, (array == null) ? 0 : array.length);
        if (b) {
            this.encodeTable = Base32.HEX_ENCODE_TABLE;
            this.decodeTable = Base32.HEX_DECODE_TABLE;
        }
        else {
            this.encodeTable = Base32.ENCODE_TABLE;
            this.decodeTable = Base32.DECODE_TABLE;
        }
        if (n > 0) {
            if (array == null) {
                throw new IllegalArgumentException("lineLength " + n + " > 0, but lineSeparator is null");
            }
            if (this.containsAlphabetOrPad(array)) {
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + StringUtils.newStringUtf8(array) + "]");
            }
            this.encodeSize = 8 + array.length;
            System.arraycopy(array, 0, this.lineSeparator = new byte[array.length], 0, array.length);
        }
        else {
            this.encodeSize = 8;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
    }
    
    @Override
    void decode(final byte[] array, int n, final int n2, final Context context) {
        if (context.eof) {
            return;
        }
        if (n2 < 0) {
            context.eof = true;
        }
        while (0 < n2) {
            final byte b = array[n++];
            if (b == 61) {
                context.eof = true;
                break;
            }
            final byte[] ensureBufferSize = this.ensureBufferSize(this.decodeSize, context);
            if (b >= 0 && b < this.decodeTable.length) {
                final byte b2 = this.decodeTable[b];
                if (b2 >= 0) {
                    context.modulus = (context.modulus + 1) % 8;
                    context.lbitWorkArea = (context.lbitWorkArea << 5) + b2;
                    if (context.modulus == 0) {
                        ensureBufferSize[context.pos++] = (byte)(context.lbitWorkArea >> 32 & 0xFFL);
                        ensureBufferSize[context.pos++] = (byte)(context.lbitWorkArea >> 24 & 0xFFL);
                        ensureBufferSize[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                        ensureBufferSize[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                        ensureBufferSize[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    }
                }
            }
            int n3 = 0;
            ++n3;
        }
        if (context.eof && context.modulus >= 2) {
            final byte[] ensureBufferSize2 = this.ensureBufferSize(this.decodeSize, context);
            switch (context.modulus) {
                case 2: {
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 2 & 0xFFL);
                    break;
                }
                case 3: {
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 7 & 0xFFL);
                    break;
                }
                case 4: {
                    context.lbitWorkArea >>= 4;
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 5: {
                    context.lbitWorkArea >>= 1;
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 6: {
                    context.lbitWorkArea >>= 6;
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 7: {
                    context.lbitWorkArea >>= 3;
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 24 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    ensureBufferSize2[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
    }
    
    @Override
    void encode(final byte[] array, int n, final int n2, final Context context) {
        if (context.eof) {
            return;
        }
        if (n2 < 0) {
            context.eof = true;
            if (0 == context.modulus && this.lineLength == 0) {
                return;
            }
            final byte[] ensureBufferSize = this.ensureBufferSize(this.encodeSize, context);
            final int pos = context.pos;
            switch (context.modulus) {
                case 0: {
                    break;
                }
                case 1: {
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 3) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 2) & 0x1F];
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    break;
                }
                case 2: {
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 11) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 6) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 1) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 4) & 0x1F];
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    break;
                }
                case 3: {
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 19) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 14) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 9) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 4) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 1) & 0x1F];
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    ensureBufferSize[context.pos++] = 61;
                    break;
                }
                case 4: {
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 27) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 22) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 17) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 12) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 7) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 2) & 0x1F];
                    ensureBufferSize[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 3) & 0x1F];
                    ensureBufferSize[context.pos++] = 61;
                    break;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
            context.currentLinePos += context.pos - pos;
            if (this.lineLength > 0 && context.currentLinePos > 0) {
                System.arraycopy(this.lineSeparator, 0, ensureBufferSize, context.pos, this.lineSeparator.length);
                context.pos += this.lineSeparator.length;
            }
        }
        else {
            while (0 < n2) {
                final byte[] ensureBufferSize2 = this.ensureBufferSize(this.encodeSize, context);
                context.modulus = (context.modulus + 1) % 5;
                int n3 = array[n++];
                if (n3 < 0) {
                    n3 += 256;
                }
                context.lbitWorkArea = (context.lbitWorkArea << 8) + n3;
                if (0 == context.modulus) {
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 35) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 30) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 25) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 20) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 15) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 10) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 5) & 0x1F];
                    ensureBufferSize2[context.pos++] = this.encodeTable[(int)context.lbitWorkArea & 0x1F];
                    context.currentLinePos += 8;
                    if (this.lineLength > 0 && this.lineLength <= context.currentLinePos) {
                        System.arraycopy(this.lineSeparator, 0, ensureBufferSize2, context.pos, this.lineSeparator.length);
                        context.pos += this.lineSeparator.length;
                        context.currentLinePos = 0;
                    }
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    public boolean isInAlphabet(final byte b) {
        return b >= 0 && b < this.decodeTable.length && this.decodeTable[b] != -1;
    }
    
    static {
        CHUNK_SEPARATOR = new byte[] { 13, 10 };
        DECODE_TABLE = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
        ENCODE_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55 };
        HEX_DECODE_TABLE = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32 };
        HEX_ENCODE_TABLE = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86 };
    }
}
