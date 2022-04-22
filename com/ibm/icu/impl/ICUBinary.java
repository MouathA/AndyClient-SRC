package com.ibm.icu.impl;

import java.io.*;
import java.util.*;
import com.ibm.icu.util.*;

public final class ICUBinary
{
    private static final byte MAGIC1 = -38;
    private static final byte MAGIC2 = 39;
    private static final byte BIG_ENDIAN_ = 1;
    private static final byte CHAR_SET_ = 0;
    private static final byte CHAR_SIZE_ = 2;
    private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
    private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
    
    public static final byte[] readHeader(final InputStream inputStream, final byte[] array, final Authenticate authenticate) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final char char1 = dataInputStream.readChar();
        final byte byte1 = dataInputStream.readByte();
        int n = 0;
        ++n;
        final byte byte2 = dataInputStream.readByte();
        ++n;
        if (byte1 != -38 || byte2 != 39) {
            throw new IOException("ICU data file error: Not an ICU data file");
        }
        dataInputStream.readChar();
        n += 2;
        dataInputStream.readChar();
        n += 2;
        final byte byte3 = dataInputStream.readByte();
        ++n;
        final byte byte4 = dataInputStream.readByte();
        ++n;
        final byte byte5 = dataInputStream.readByte();
        ++n;
        dataInputStream.readByte();
        ++n;
        final byte[] array2 = new byte[4];
        dataInputStream.readFully(array2);
        n += 4;
        final byte[] array3 = new byte[4];
        dataInputStream.readFully(array3);
        n += 4;
        final byte[] array4 = new byte[4];
        dataInputStream.readFully(array4);
        n += 4;
        if (char1 < '\u0002') {
            throw new IOException("Internal Error: Header size error");
        }
        dataInputStream.skipBytes(char1 - '\u0002');
        if (byte3 != 1 || byte4 != 0 || byte5 != 2 || !Arrays.equals(array, array2) || (authenticate != null && !authenticate.isDataVersionAcceptable(array3))) {
            throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
        }
        return array4;
    }
    
    public static final VersionInfo readHeaderAndDataVersion(final InputStream inputStream, final byte[] array, final Authenticate authenticate) throws IOException {
        final byte[] header = readHeader(inputStream, array, authenticate);
        return VersionInfo.getInstance(header[0], header[1], header[2], header[3]);
    }
    
    public interface Authenticate
    {
        boolean isDataVersionAcceptable(final byte[] p0);
    }
}
