package com.ibm.icu.text;

public final class UnicodeDecompressor implements SCSU
{
    private int fCurrentWindow;
    private int[] fOffsets;
    private int fMode;
    private static final int BUFSIZE = 3;
    private byte[] fBuffer;
    private int fBufferLength;
    
    public UnicodeDecompressor() {
        this.fCurrentWindow = 0;
        this.fOffsets = new int[8];
        this.fMode = 0;
        this.fBuffer = new byte[3];
        this.fBufferLength = 0;
        this.reset();
    }
    
    public static String decompress(final byte[] array) {
        return new String(decompress(array, 0, array.length));
    }
    
    public static char[] decompress(final byte[] array, final int n, final int n2) {
        final UnicodeDecompressor unicodeDecompressor = new UnicodeDecompressor();
        final int max = Math.max(2, 2 * (n2 - n));
        final char[] array2 = new char[max];
        final int decompress = unicodeDecompressor.decompress(array, n, n2, null, array2, 0, max);
        final char[] array3 = new char[decompress];
        System.arraycopy(array2, 0, array3, 0, decompress);
        return array3;
    }
    
    public int decompress(final byte[] array, final int n, final int n2, final int[] array2, final char[] array3, final int n3, final int n4) {
        int n5 = n;
        int n6 = n3;
        if (array3.length < 2 || n4 - n3 < 2) {
            throw new IllegalArgumentException("charBuffer.length < 2");
        }
        if (this.fBufferLength > 0) {
            if (this.fBufferLength != 3) {
                final int n7 = this.fBuffer.length - this.fBufferLength;
                if (n2 - n < 0) {}
                System.arraycopy(array, n, this.fBuffer, this.fBufferLength, 0);
            }
            this.fBufferLength = 0;
            n6 += this.decompress(this.fBuffer, 0, this.fBuffer.length, null, array3, n3, n4);
            n5 += 0;
        }
        int n8;
        int n9;
        byte b;
        int n10;
        int n11;
        int n12;
        int n13;
        byte b2;
        Label_2211:Label_2208:
        while (n5 < n2 && n6 < n4) {
            switch (this.fMode) {
                case 0: {
                    while (n5 < n2 && n6 < n4) {
                        n8 = (array[n5++] & 0xFF);
                        switch (false) {
                            case 128:
                            case 129:
                            case 130:
                            case 131:
                            case 132:
                            case 133:
                            case 134:
                            case 135:
                            case 136:
                            case 137:
                            case 138:
                            case 139:
                            case 140:
                            case 141:
                            case 142:
                            case 143:
                            case 144:
                            case 145:
                            case 146:
                            case 147:
                            case 148:
                            case 149:
                            case 150:
                            case 151:
                            case 152:
                            case 153:
                            case 154:
                            case 155:
                            case 156:
                            case 157:
                            case 158:
                            case 159:
                            case 160:
                            case 161:
                            case 162:
                            case 163:
                            case 164:
                            case 165:
                            case 166:
                            case 167:
                            case 168:
                            case 169:
                            case 170:
                            case 171:
                            case 172:
                            case 173:
                            case 174:
                            case 175:
                            case 176:
                            case 177:
                            case 178:
                            case 179:
                            case 180:
                            case 181:
                            case 182:
                            case 183:
                            case 184:
                            case 185:
                            case 186:
                            case 187:
                            case 188:
                            case 189:
                            case 190:
                            case 191:
                            case 192:
                            case 193:
                            case 194:
                            case 195:
                            case 196:
                            case 197:
                            case 198:
                            case 199:
                            case 200:
                            case 201:
                            case 202:
                            case 203:
                            case 204:
                            case 205:
                            case 206:
                            case 207:
                            case 208:
                            case 209:
                            case 210:
                            case 211:
                            case 212:
                            case 213:
                            case 214:
                            case 215:
                            case 216:
                            case 217:
                            case 218:
                            case 219:
                            case 220:
                            case 221:
                            case 222:
                            case 223:
                            case 224:
                            case 225:
                            case 226:
                            case 227:
                            case 228:
                            case 229:
                            case 230:
                            case 231:
                            case 232:
                            case 233:
                            case 234:
                            case 235:
                            case 236:
                            case 237:
                            case 238:
                            case 239:
                            case 240:
                            case 241:
                            case 242:
                            case 243:
                            case 244:
                            case 245:
                            case 246:
                            case 247:
                            case 248:
                            case 249:
                            case 250:
                            case 251:
                            case 252:
                            case 253:
                            case 254:
                            case 255: {
                                if (this.fOffsets[this.fCurrentWindow] <= 65535) {
                                    array3[n6++] = (char)(0 + this.fOffsets[this.fCurrentWindow] - 128);
                                    continue;
                                }
                                if (n6 + 1 >= n4) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                n9 = this.fOffsets[this.fCurrentWindow] - 65536;
                                array3[n6++] = 55296;
                                array3[n6++] = 56320;
                                continue;
                            }
                            case 0:
                            case 9:
                            case 10:
                            case 13:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                            case 60:
                            case 61:
                            case 62:
                            case 63:
                            case 64:
                            case 65:
                            case 66:
                            case 67:
                            case 68:
                            case 69:
                            case 70:
                            case 71:
                            case 72:
                            case 73:
                            case 74:
                            case 75:
                            case 76:
                            case 77:
                            case 78:
                            case 79:
                            case 80:
                            case 81:
                            case 82:
                            case 83:
                            case 84:
                            case 85:
                            case 86:
                            case 87:
                            case 88:
                            case 89:
                            case 90:
                            case 91:
                            case 92:
                            case 93:
                            case 94:
                            case 95:
                            case 96:
                            case 97:
                            case 98:
                            case 99:
                            case 100:
                            case 101:
                            case 102:
                            case 103:
                            case 104:
                            case 105:
                            case 106:
                            case 107:
                            case 108:
                            case 109:
                            case 110:
                            case 111:
                            case 112:
                            case 113:
                            case 114:
                            case 115:
                            case 116:
                            case 117:
                            case 118:
                            case 119:
                            case 120:
                            case 121:
                            case 122:
                            case 123:
                            case 124:
                            case 125:
                            case 126:
                            case 127: {
                                array3[n6++] = 0;
                                continue;
                            }
                            case 14: {
                                if (n5 + 1 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                b = array[n5++];
                                array3[n6++] = (char)(0x0 | (array[n5++] & 0xFF));
                                continue;
                            }
                            case 15: {
                                this.fMode = 1;
                                continue Label_2208;
                            }
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8: {
                                if (n5 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                n10 = (array[n5++] & 0xFF);
                                array3[n6++] = (char)(0 + ((0 >= 0 && 0 < 128) ? UnicodeDecompressor.sOffsets[-1] : (this.fOffsets[-1] - 128)));
                                continue;
                            }
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23: {
                                this.fCurrentWindow = -16;
                                continue;
                            }
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31: {
                                if (n5 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                this.fCurrentWindow = -24;
                                this.fOffsets[this.fCurrentWindow] = UnicodeDecompressor.sOffsetTable[array[n5++] & 0xFF];
                                continue;
                            }
                            case 11: {
                                if (n5 + 1 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                n11 = (array[n5++] & 0xFF);
                                this.fCurrentWindow = 0;
                                this.fOffsets[this.fCurrentWindow] = 65536 + 128 * (0x0 | (array[n5++] & 0xFF));
                                continue;
                            }
                        }
                    }
                    continue;
                }
                case 1: {
                    while (n5 < n2 && n6 < n4) {
                        n12 = (array[n5++] & 0xFF);
                        switch (false) {
                            case 232:
                            case 233:
                            case 234:
                            case 235:
                            case 236:
                            case 237:
                            case 238:
                            case 239: {
                                if (n5 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                this.fCurrentWindow = -232;
                                this.fOffsets[this.fCurrentWindow] = UnicodeDecompressor.sOffsetTable[array[n5++] & 0xFF];
                                this.fMode = 0;
                                continue Label_2208;
                            }
                            case 241: {
                                if (n5 + 1 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                n13 = (array[n5++] & 0xFF);
                                this.fCurrentWindow = 0;
                                this.fOffsets[this.fCurrentWindow] = 65536 + 128 * (0x0 | (array[n5++] & 0xFF));
                                this.fMode = 0;
                                continue Label_2208;
                            }
                            case 224:
                            case 225:
                            case 226:
                            case 227:
                            case 228:
                            case 229:
                            case 230:
                            case 231: {
                                this.fCurrentWindow = -224;
                                this.fMode = 0;
                                continue Label_2208;
                            }
                            case 240: {
                                if (n5 >= n2 - 1) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                b2 = array[n5++];
                                array3[n6++] = (char)(0x0 | (array[n5++] & 0xFF));
                                continue;
                            }
                            default: {
                                if (n5 >= n2) {
                                    --n5;
                                    System.arraycopy(array, n5, this.fBuffer, 0, n2 - n5);
                                    this.fBufferLength = n2 - n5;
                                    n5 += this.fBufferLength;
                                    break Label_2211;
                                }
                                array3[n6++] = (char)(0x0 | (array[n5++] & 0xFF));
                                continue;
                            }
                        }
                    }
                    continue;
                }
            }
        }
        if (array2 != null) {
            array2[0] = n5 - n;
        }
        return n6 - n3;
    }
    
    public void reset() {
        this.fOffsets[0] = 128;
        this.fOffsets[1] = 192;
        this.fOffsets[2] = 1024;
        this.fOffsets[3] = 1536;
        this.fOffsets[4] = 2304;
        this.fOffsets[5] = 12352;
        this.fOffsets[6] = 12448;
        this.fOffsets[7] = 65280;
        this.fCurrentWindow = 0;
        this.fMode = 0;
        this.fBufferLength = 0;
    }
}
