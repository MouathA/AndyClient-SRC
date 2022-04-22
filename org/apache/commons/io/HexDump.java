package org.apache.commons.io;

import java.io.*;

public class HexDump
{
    public static final String EOL;
    private static final char[] _hexcodes;
    private static final int[] _shifts;
    
    public static void dump(final byte[] array, final long n, final OutputStream outputStream, final int n2) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (n2 < 0 || n2 >= array.length) {
            throw new ArrayIndexOutOfBoundsException("illegal index: " + n2 + " into array of length " + array.length);
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("cannot write to nullstream");
        }
        long n3 = n + n2;
        final StringBuilder sb = new StringBuilder(74);
        for (int i = n2; i < array.length; i += 16) {
            int n4 = array.length - i;
            if (n4 > 16) {
                n4 = 16;
            }
            dump(sb, n3).append(' ');
            for (int j = 0; j < 16; ++j) {
                if (j < n4) {
                    dump(sb, array[j + i]);
                }
                else {
                    sb.append("  ");
                }
                sb.append(' ');
            }
            for (int k = 0; k < n4; ++k) {
                if (array[k + i] >= 32 && array[k + i] < 127) {
                    sb.append((char)array[k + i]);
                }
                else {
                    sb.append('.');
                }
            }
            sb.append(HexDump.EOL);
            outputStream.write(sb.toString().getBytes());
            outputStream.flush();
            sb.setLength(0);
            n3 += n4;
        }
    }
    
    private static StringBuilder dump(final StringBuilder sb, final long n) {
        for (int i = 0; i < 8; ++i) {
            sb.append(HexDump._hexcodes[(int)(n >> HexDump._shifts[i]) & 0xF]);
        }
        return sb;
    }
    
    private static StringBuilder dump(final StringBuilder sb, final byte b) {
        for (int i = 0; i < 2; ++i) {
            sb.append(HexDump._hexcodes[b >> HexDump._shifts[i + 6] & 0xF]);
        }
        return sb;
    }
    
    static {
        EOL = System.getProperty("line.separator");
        _hexcodes = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        _shifts = new int[] { 28, 24, 20, 16, 12, 8, 4, 0 };
    }
}
