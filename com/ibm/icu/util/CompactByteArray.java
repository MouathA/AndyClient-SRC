package com.ibm.icu.util;

import com.ibm.icu.impl.*;

public final class CompactByteArray implements Cloneable
{
    @Deprecated
    public static final int UNICODECOUNT = 65536;
    private static final int BLOCKSHIFT = 7;
    private static final int BLOCKCOUNT = 128;
    private static final int INDEXSHIFT = 9;
    private static final int INDEXCOUNT = 512;
    private static final int BLOCKMASK = 127;
    private byte[] values;
    private char[] indices;
    private int[] hashes;
    private boolean isCompact;
    byte defaultValue;
    
    @Deprecated
    public CompactByteArray() {
        this((byte)0);
    }
    
    @Deprecated
    public CompactByteArray(final byte defaultValue) {
        this.values = new byte[65536];
        this.indices = new char[512];
        this.hashes = new int[512];
        int n = 0;
        while (0 < 65536) {
            this.values[0] = defaultValue;
            ++n;
        }
        while (0 < 512) {
            this.indices[0] = 0;
            this.hashes[0] = 0;
            ++n;
        }
        this.isCompact = false;
        this.defaultValue = defaultValue;
    }
    
    @Deprecated
    public CompactByteArray(final char[] indices, final byte[] values) {
        if (indices.length != 512) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        while (0 < 512) {
            final char c = indices[0];
            if (c < '\0' || c >= values.length + 128) {
                throw new IllegalArgumentException("Index out of bounds.");
            }
            int n = 0;
            ++n;
        }
        this.indices = indices;
        this.values = values;
        this.isCompact = true;
    }
    
    @Deprecated
    public CompactByteArray(final String s, final String s2) {
        this(Utility.RLEStringToCharArray(s), Utility.RLEStringToByteArray(s2));
    }
    
    @Deprecated
    public byte elementAt(final char c) {
        return this.values[(this.indices[c >> 7] & '\uffff') + (c & '\u007f')];
    }
    
    @Deprecated
    public void setElementAt(final char c, final byte b) {
        if (this.isCompact) {
            this.expand();
        }
        this.touchBlock(c >> 7, this.values[c] = b);
    }
    
    @Deprecated
    public void setElementAt(final char c, final char c2, final byte b) {
        if (this.isCompact) {
            this.expand();
        }
        for (char c3 = c; c3 <= c2; ++c3) {
            this.touchBlock(c3 >> 7, this.values[c3] = b);
        }
    }
    
    @Deprecated
    public void compact() {
        this.compact(false);
    }
    
    @Deprecated
    public void compact(final boolean b) {
        if (!this.isCompact) {
            while (0 < this.indices.length) {
                this.indices[0] = '\uffff';
                final boolean blockTouched = this.blockTouched(0);
                if (!blockTouched && 65535 != 65535) {
                    this.indices[0] = '\uffff';
                }
                else {
                    while (0 < 0) {
                        if (this.hashes[0] == this.hashes[0] && arrayRegionMatches(this.values, 0, this.values, 0, 128)) {
                            this.indices[0] = 0;
                            break;
                        }
                        int n = 0;
                        ++n;
                        final int n2;
                        n2 += 128;
                    }
                    if (this.indices[0] == '\uffff') {
                        System.arraycopy(this.values, 0, this.values, 0, 128);
                        this.indices[0] = 0;
                        this.hashes[0] = this.hashes[0];
                        int n3 = 0;
                        ++n3;
                        if (!blockTouched) {
                            final char c = 0;
                        }
                    }
                }
                int n4 = 0;
                ++n4;
                final int n5;
                n5 += 128;
            }
            final byte[] values = new byte[0];
            System.arraycopy(this.values, 0, values, 0, 0);
            this.values = values;
            this.isCompact = true;
            this.hashes = null;
        }
    }
    
    static final boolean arrayRegionMatches(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
        final int n4 = n + n3;
        final int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (array[i] != array2[i + n5]) {
                return false;
            }
        }
        return true;
    }
    
    private final void touchBlock(final int n, final int n2) {
        this.hashes[n] = (this.hashes[n] + (n2 << 1) | 0x1);
    }
    
    private final boolean blockTouched(final int n) {
        return this.hashes[n] != 0;
    }
    
    @Deprecated
    public char[] getIndexArray() {
        return this.indices;
    }
    
    @Deprecated
    public byte[] getValueArray() {
        return this.values;
    }
    
    @Deprecated
    public Object clone() {
        final CompactByteArray compactByteArray = (CompactByteArray)super.clone();
        compactByteArray.values = this.values.clone();
        compactByteArray.indices = this.indices.clone();
        if (this.hashes != null) {
            compactByteArray.hashes = this.hashes.clone();
        }
        return compactByteArray;
    }
    
    @Override
    @Deprecated
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final CompactByteArray compactByteArray = (CompactByteArray)o;
        while (0 < 65536) {
            if (this.elementAt((char)0) != compactByteArray.elementAt((char)0)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    @Deprecated
    public int hashCode() {
        Math.min(3, this.values.length / 16);
        while (0 < this.values.length) {
            final int n = 0 + this.values[0];
        }
        return 0;
    }
    
    private void expand() {
        if (this.isCompact) {
            this.hashes = new int[512];
            final byte[] values = new byte[65536];
            int n = 0;
            while (0 < 65536) {
                this.touchBlock(0, values[0] = this.elementAt((char)0));
                ++n;
            }
            while (0 < 512) {
                this.indices[0] = 0;
                ++n;
            }
            this.values = null;
            this.values = values;
            this.isCompact = false;
        }
    }
}
