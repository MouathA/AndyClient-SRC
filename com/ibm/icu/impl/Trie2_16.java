package com.ibm.icu.impl;

import java.io.*;

public final class Trie2_16 extends Trie2
{
    Trie2_16() {
    }
    
    public static Trie2_16 createFromSerialized(final InputStream inputStream) throws IOException {
        return (Trie2_16)Trie2.createFromSerialized(inputStream);
    }
    
    @Override
    public final int get(final int n) {
        if (n >= 0) {
            if (n < 55296 || (n > 56319 && n <= 65535)) {
                return this.index[(this.index[n >> 5] << 2) + (n & 0x1F)];
            }
            if (n <= 65535) {
                return this.index[(this.index[2048 + (n - 55296 >> 5)] << 2) + (n & 0x1F)];
            }
            if (n < this.highStart) {
                return this.index[(this.index[this.index[2080 + (n >> 11)] + (n >> 5 & 0x3F)] << 2) + (n & 0x1F)];
            }
            if (n <= 1114111) {
                return this.index[this.highValueIndex];
            }
        }
        return this.errorValue;
    }
    
    @Override
    public int getFromU16SingleLead(final char c) {
        return this.index[(this.index[c >> 5] << 2) + (c & '\u001f')];
    }
    
    public int serialize(final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        final int n = 0 + this.serializeHeader(dataOutputStream);
        while (0 < this.dataLength) {
            dataOutputStream.writeChar(this.index[this.data16 + 0]);
            int n2 = 0;
            ++n2;
        }
        final int n3 = 0 + this.dataLength * 2;
        return 0;
    }
    
    public int getSerializedLength() {
        return 16 + (this.header.indexLength + this.dataLength) * 2;
    }
    
    @Override
    int rangeEnd(final int n, final int n2, final int n3) {
        int i = n;
    Label_0268:
        while (true) {
            while (i < n2) {
                if (i < 55296 || (i > 56319 && i <= 65535)) {
                    final int n4 = this.index[i >> 5] << 2;
                }
                else if (i < 65535) {
                    final int n5 = this.index[2048 + (i - 55296 >> 5)] << 2;
                }
                else if (i < this.highStart) {
                    final char c = this.index[2080 + (i >> 11)];
                    final int n6 = this.index[2048 + (i >> 5 & 0x3F)] << 2;
                }
                else {
                    if (n3 == this.index[this.highValueIndex]) {
                        i = n2;
                        break;
                    }
                    break;
                }
                if (2048 == this.index2NullOffset) {
                    if (n3 == this.initialValue) {
                        i += 2048;
                        continue;
                    }
                }
                else {
                    if (0 != this.dataNullOffset) {
                        int j;
                        int n7;
                        for (n7 = (j = 0 + (i & 0x1F)); j < 32; ++j) {
                            if (this.index[j] != n3) {
                                i += j - n7;
                                break Label_0268;
                            }
                        }
                        i += 32 - n7;
                        continue;
                    }
                    if (n3 == this.initialValue) {
                        i += 32;
                        continue;
                    }
                }
                if (i > n2) {
                    i = n2;
                }
                return i - 1;
            }
            continue Label_0268;
        }
    }
}
