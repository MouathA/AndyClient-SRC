package io.netty.handler.codec.base64;

import io.netty.buffer.*;

public final class Base64
{
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte EQUALS_SIGN = 61;
    private static final byte NEW_LINE = 10;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte EQUALS_SIGN_ENC = -1;
    
    private static byte[] alphabet(final Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.alphabet;
    }
    
    private static byte[] decodabet(final Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.decodabet;
    }
    
    private static boolean breakLines(final Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.breakLinesByDefault;
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf) {
        return encode(byteBuf, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final Base64Dialect base64Dialect) {
        return encode(byteBuf, breakLines(base64Dialect), base64Dialect);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final boolean b) {
        return encode(byteBuf, b, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final boolean b, final Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        final ByteBuf encode = encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), b, base64Dialect);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return encode;
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final int n, final int n2) {
        return encode(byteBuf, n, n2, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final int n, final int n2, final Base64Dialect base64Dialect) {
        return encode(byteBuf, n, n2, breakLines(base64Dialect), base64Dialect);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final int n, final int n2, final boolean b) {
        return encode(byteBuf, n, n2, b, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf encode(final ByteBuf byteBuf, final int n, final int n2, final boolean b, final Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        final int n3 = n2 * 4 / 3;
        final ByteBuf order = Unpooled.buffer(n3 + ((n2 % 3 > 0) ? 4 : 0) + (b ? (n3 / 76) : 0)).order(byteBuf.order());
        int n6 = 0;
        while (0 < n2 - 2) {
            encode3to4(byteBuf, 0 + n, 3, order, 0, base64Dialect);
            final int n4;
            n4 += 4;
            if (b) {}
            final int n5;
            n5 += 3;
            n6 += 4;
        }
        if (0 < n2) {
            encode3to4(byteBuf, 0 + n, n2 - 0, order, 0, base64Dialect);
            n6 += 4;
        }
        return order.slice(0, 0);
    }
    
    private static void encode3to4(final ByteBuf byteBuf, final int n, final int n2, final ByteBuf byteBuf2, final int n3, final Base64Dialect base64Dialect) {
        final byte[] alphabet = alphabet(base64Dialect);
        final int n4 = ((n2 > 0) ? (byteBuf.getByte(n) << 24 >>> 8) : 0) | ((n2 > 1) ? (byteBuf.getByte(n + 1) << 24 >>> 16) : 0) | ((n2 > 2) ? (byteBuf.getByte(n + 2) << 24 >>> 24) : 0);
        switch (n2) {
            case 3: {
                byteBuf2.setByte(n3, alphabet[n4 >>> 18]);
                byteBuf2.setByte(n3 + 1, alphabet[n4 >>> 12 & 0x3F]);
                byteBuf2.setByte(n3 + 2, alphabet[n4 >>> 6 & 0x3F]);
                byteBuf2.setByte(n3 + 3, alphabet[n4 & 0x3F]);
                break;
            }
            case 2: {
                byteBuf2.setByte(n3, alphabet[n4 >>> 18]);
                byteBuf2.setByte(n3 + 1, alphabet[n4 >>> 12 & 0x3F]);
                byteBuf2.setByte(n3 + 2, alphabet[n4 >>> 6 & 0x3F]);
                byteBuf2.setByte(n3 + 3, 61);
                break;
            }
            case 1: {
                byteBuf2.setByte(n3, alphabet[n4 >>> 18]);
                byteBuf2.setByte(n3 + 1, alphabet[n4 >>> 12 & 0x3F]);
                byteBuf2.setByte(n3 + 2, 61);
                byteBuf2.setByte(n3 + 3, 61);
                break;
            }
        }
    }
    
    public static ByteBuf decode(final ByteBuf byteBuf) {
        return decode(byteBuf, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf decode(final ByteBuf byteBuf, final Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        final ByteBuf decode = decode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), base64Dialect);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return decode;
    }
    
    public static ByteBuf decode(final ByteBuf byteBuf, final int n, final int n2) {
        return decode(byteBuf, n, n2, Base64Dialect.STANDARD);
    }
    
    public static ByteBuf decode(final ByteBuf byteBuf, final int n, final int n2, final Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        final byte[] decodabet = decodabet(base64Dialect);
        final ByteBuf order = byteBuf.alloc().buffer(n2 * 3 / 4).order(byteBuf.order());
        final byte[] array = new byte[4];
        for (int i = n; i < n + n2; ++i) {
            final byte b = (byte)(byteBuf.getByte(i) & 0x7F);
            final byte b2 = decodabet[b];
            if (b2 < -5) {
                throw new IllegalArgumentException("bad Base64 input character at " + i + ": " + byteBuf.getUnsignedByte(i) + " (decimal)");
            }
            if (b2 >= -1) {
                final byte[] array2 = array;
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                array2[n3] = b;
            }
        }
        return order.slice(0, 0);
    }
    
    private static int decode4to3(final byte[] array, final int n, final ByteBuf byteBuf, final int n2, final Base64Dialect base64Dialect) {
        final byte[] decodabet = decodabet(base64Dialect);
        if (array[n + 2] == 61) {
            byteBuf.setByte(n2, (byte)(((decodabet[array[n]] & 0xFF) << 18 | (decodabet[array[n + 1]] & 0xFF) << 12) >>> 16));
            return 1;
        }
        if (array[n + 3] == 61) {
            final int n3 = (decodabet[array[n]] & 0xFF) << 18 | (decodabet[array[n + 1]] & 0xFF) << 12 | (decodabet[array[n + 2]] & 0xFF) << 6;
            byteBuf.setByte(n2, (byte)(n3 >>> 16));
            byteBuf.setByte(n2 + 1, (byte)(n3 >>> 8));
            return 2;
        }
        final int n4 = (decodabet[array[n]] & 0xFF) << 18 | (decodabet[array[n + 1]] & 0xFF) << 12 | (decodabet[array[n + 2]] & 0xFF) << 6 | (decodabet[array[n + 3]] & 0xFF);
        byteBuf.setByte(n2, (byte)(n4 >> 16));
        byteBuf.setByte(n2 + 1, (byte)(n4 >> 8));
        byteBuf.setByte(n2 + 2, (byte)n4);
        return 3;
    }
    
    private Base64() {
    }
}
