package com.ibm.icu.impl;

import java.io.*;
import java.util.*;

final class UCharacterNameReader implements ICUBinary.Authenticate
{
    private DataInputStream m_dataInputStream_;
    private static final int GROUP_INFO_SIZE_ = 3;
    private int m_tokenstringindex_;
    private int m_groupindex_;
    private int m_groupstringindex_;
    private int m_algnamesindex_;
    private static final int ALG_INFO_SIZE_ = 12;
    private static final byte[] DATA_FORMAT_VERSION_;
    private static final byte[] DATA_FORMAT_ID_;
    
    public boolean isDataVersionAcceptable(final byte[] array) {
        return array[0] == UCharacterNameReader.DATA_FORMAT_VERSION_[0];
    }
    
    protected UCharacterNameReader(final InputStream inputStream) throws IOException {
        ICUBinary.readHeader(inputStream, UCharacterNameReader.DATA_FORMAT_ID_, this);
        this.m_dataInputStream_ = new DataInputStream(inputStream);
    }
    
    protected void read(final UCharacterName uCharacterName) throws IOException {
        this.m_tokenstringindex_ = this.m_dataInputStream_.readInt();
        this.m_groupindex_ = this.m_dataInputStream_.readInt();
        this.m_groupstringindex_ = this.m_dataInputStream_.readInt();
        this.m_algnamesindex_ = this.m_dataInputStream_.readInt();
        final char char1 = this.m_dataInputStream_.readChar();
        final char[] array = new char[char1];
        while ('\0' < char1) {
            array[0] = this.m_dataInputStream_.readChar();
            final char c = 1;
        }
        final int n = this.m_groupindex_ - this.m_tokenstringindex_;
        final byte[] array2 = new byte[0];
        this.m_dataInputStream_.readFully(array2);
        uCharacterName.setToken(array, array2);
        final char char2 = this.m_dataInputStream_.readChar();
        uCharacterName.setGroupCountSize(char2, 3);
        final int n2 = char2 * '\u0003';
        final char[] array3 = new char[n2];
        while (0 < n2) {
            array3[0] = this.m_dataInputStream_.readChar();
            int n3 = 0;
            ++n3;
        }
        final int n4 = this.m_algnamesindex_ - this.m_groupstringindex_;
        final byte[] array4 = new byte[0];
        this.m_dataInputStream_.readFully(array4);
        uCharacterName.setGroup(array3, array4);
        final int int1 = this.m_dataInputStream_.readInt();
        final UCharacterName.AlgorithmName[] algorithm = new UCharacterName.AlgorithmName[int1];
        while (0 < int1) {
            final UCharacterName.AlgorithmName alg = this.readAlg();
            if (alg == null) {
                throw new IOException("unames.icu read error: Algorithmic names creation error");
            }
            algorithm[0] = alg;
            int n5 = 0;
            ++n5;
        }
        uCharacterName.setAlgorithm(algorithm);
    }
    
    protected boolean authenticate(final byte[] array, final byte[] array2) {
        return Arrays.equals(UCharacterNameReader.DATA_FORMAT_ID_, array) && Arrays.equals(UCharacterNameReader.DATA_FORMAT_VERSION_, array2);
    }
    
    private UCharacterName.AlgorithmName readAlg() throws IOException {
        final UCharacterName.AlgorithmName algorithmName = new UCharacterName.AlgorithmName();
        final int int1 = this.m_dataInputStream_.readInt();
        final int int2 = this.m_dataInputStream_.readInt();
        final byte byte1 = this.m_dataInputStream_.readByte();
        final byte byte2 = this.m_dataInputStream_.readByte();
        if (!algorithmName.setInfo(int1, int2, byte1, byte2)) {
            return null;
        }
        int char1 = this.m_dataInputStream_.readChar();
        if (byte1 == 1) {
            final char[] factor = new char[byte2];
            while (0 < byte2) {
                factor[0] = this.m_dataInputStream_.readChar();
                int n = 0;
                ++n;
            }
            algorithmName.setFactor(factor);
            char1 -= byte2 << 1;
        }
        final StringBuilder sb = new StringBuilder();
        int n = (char)(this.m_dataInputStream_.readByte() & 0xFF);
        while (false) {
            sb.append('\0');
            n = (char)(this.m_dataInputStream_.readByte() & 0xFF);
        }
        algorithmName.setPrefix(sb.toString());
        final int n2 = char1 - (12 + sb.length() + 1);
        if (n2 > 0) {
            final byte[] factorString = new byte[n2];
            this.m_dataInputStream_.readFully(factorString);
            algorithmName.setFactorString(factorString);
        }
        return algorithmName;
    }
    
    static {
        DATA_FORMAT_VERSION_ = new byte[] { 1, 0, 0, 0 };
        DATA_FORMAT_ID_ = new byte[] { 117, 110, 97, 109 };
    }
}
