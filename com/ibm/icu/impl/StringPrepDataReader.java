package com.ibm.icu.impl;

import java.io.*;

public final class StringPrepDataReader implements ICUBinary.Authenticate
{
    private static final boolean debug;
    private DataInputStream dataInputStream;
    private byte[] unicodeVersion;
    private static final byte[] DATA_FORMAT_ID;
    private static final byte[] DATA_FORMAT_VERSION;
    
    public StringPrepDataReader(final InputStream inputStream) throws IOException {
        if (StringPrepDataReader.debug) {
            System.out.println("Bytes in inputStream " + inputStream.available());
        }
        this.unicodeVersion = ICUBinary.readHeader(inputStream, StringPrepDataReader.DATA_FORMAT_ID, this);
        if (StringPrepDataReader.debug) {
            System.out.println("Bytes left in inputStream " + inputStream.available());
        }
        this.dataInputStream = new DataInputStream(inputStream);
        if (StringPrepDataReader.debug) {
            System.out.println("Bytes left in dataInputStream " + this.dataInputStream.available());
        }
    }
    
    public void read(final byte[] array, final char[] array2) throws IOException {
        this.dataInputStream.readFully(array);
        while (0 < array2.length) {
            array2[0] = this.dataInputStream.readChar();
            int n = 0;
            ++n;
        }
    }
    
    public byte[] getDataFormatVersion() {
        return StringPrepDataReader.DATA_FORMAT_VERSION;
    }
    
    public boolean isDataVersionAcceptable(final byte[] array) {
        return array[0] == StringPrepDataReader.DATA_FORMAT_VERSION[0] && array[2] == StringPrepDataReader.DATA_FORMAT_VERSION[2] && array[3] == StringPrepDataReader.DATA_FORMAT_VERSION[3];
    }
    
    public int[] readIndexes(final int n) throws IOException {
        final int[] array = new int[n];
        while (0 < n) {
            array[0] = this.dataInputStream.readInt();
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    public byte[] getUnicodeVersion() {
        return this.unicodeVersion;
    }
    
    static {
        debug = ICUDebug.enabled("NormalizerDataReader");
        DATA_FORMAT_ID = new byte[] { 83, 80, 82, 80 };
        DATA_FORMAT_VERSION = new byte[] { 3, 2, 5, 2 };
    }
}
