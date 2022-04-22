package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.io.*;

class BreakDictionary
{
    private char[] reverseColumnMap;
    private CompactByteArray columnMap;
    private int numCols;
    private short[] table;
    private short[] rowIndex;
    private int[] rowIndexFlags;
    private short[] rowIndexFlagsIndex;
    private byte[] rowIndexShifts;
    
    static void writeToFile(final String s, final String s2) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        final BreakDictionary breakDictionary = new BreakDictionary(new FileInputStream(s));
        PrintWriter printWriter = null;
        if (s2 != null) {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(s2), "UnicodeLittle"));
        }
        breakDictionary.printWordList("", 0, printWriter);
        if (printWriter != null) {
            printWriter.close();
        }
    }
    
    void printWordList(final String s, final int n, final PrintWriter printWriter) throws IOException {
        if (n == 65535) {
            System.out.println(s);
            if (printWriter != null) {
                printWriter.println(s);
            }
        }
        else {
            while (0 < this.numCols) {
                final int n2 = this.at(n, 0) & 0xFFFF;
                if (n2 != 0) {
                    final char c = this.reverseColumnMap[0];
                    String string = s;
                    if (c != '\0') {
                        string += c;
                    }
                    this.printWordList(string, n2, printWriter);
                }
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    BreakDictionary(final InputStream inputStream) throws IOException {
        this.reverseColumnMap = null;
        this.columnMap = null;
        this.table = null;
        this.rowIndex = null;
        this.rowIndexFlags = null;
        this.rowIndexFlagsIndex = null;
        this.rowIndexShifts = null;
        this.readDictionaryFile(new DataInputStream(inputStream));
    }
    
    void readDictionaryFile(final DataInputStream dataInputStream) throws IOException {
        dataInputStream.readInt();
        final char[] array = new char[dataInputStream.readInt()];
        while (0 < array.length) {
            array[0] = (char)dataInputStream.readShort();
            int n = 0;
            ++n;
        }
        final byte[] array2 = new byte[dataInputStream.readInt()];
        int n2 = 0;
        while (0 < array2.length) {
            array2[0] = dataInputStream.readByte();
            ++n2;
        }
        this.columnMap = new CompactByteArray(array, array2);
        this.numCols = dataInputStream.readInt();
        dataInputStream.readInt();
        this.rowIndex = new short[dataInputStream.readInt()];
        while (0 < this.rowIndex.length) {
            this.rowIndex[0] = dataInputStream.readShort();
            ++n2;
        }
        this.rowIndexFlagsIndex = new short[dataInputStream.readInt()];
        while (0 < this.rowIndexFlagsIndex.length) {
            this.rowIndexFlagsIndex[0] = dataInputStream.readShort();
            ++n2;
        }
        this.rowIndexFlags = new int[dataInputStream.readInt()];
        while (0 < this.rowIndexFlags.length) {
            this.rowIndexFlags[0] = dataInputStream.readInt();
            ++n2;
        }
        this.rowIndexShifts = new byte[dataInputStream.readInt()];
        while (0 < this.rowIndexShifts.length) {
            this.rowIndexShifts[0] = dataInputStream.readByte();
            ++n2;
        }
        this.table = new short[dataInputStream.readInt()];
        while (0 < this.table.length) {
            this.table[0] = dataInputStream.readShort();
            ++n2;
        }
        this.reverseColumnMap = new char[this.numCols];
        while (0 < 65535) {
            final byte element = this.columnMap.elementAt('\0');
            if (element != 0) {
                this.reverseColumnMap[element] = '\0';
            }
            n2 = 1;
        }
        dataInputStream.close();
    }
    
    final short at(final int n, final char c) {
        return this.at(n, this.columnMap.elementAt(c));
    }
    
    final short at(final int n, final int n2) {
        if (this.cellIsPopulated(n, n2)) {
            return this.internalAt(this.rowIndex[n], n2 + this.rowIndexShifts[n]);
        }
        return 0;
    }
    
    private final boolean cellIsPopulated(final int n, final int n2) {
        if (this.rowIndexFlagsIndex[n] < 0) {
            return n2 == -this.rowIndexFlagsIndex[n];
        }
        return (this.rowIndexFlags[this.rowIndexFlagsIndex[n] + (n2 >> 5)] & 1 << (n2 & 0x1F)) != 0x0;
    }
    
    private final short internalAt(final int n, final int n2) {
        return this.table[n * this.numCols + n2];
    }
}
