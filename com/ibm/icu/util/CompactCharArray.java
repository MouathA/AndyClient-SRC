package com.ibm.icu.util;

import com.ibm.icu.impl.*;

public final class CompactCharArray implements Cloneable
{
    @Deprecated
    public static final int UNICODECOUNT = 65536;
    @Deprecated
    public static final int BLOCKSHIFT = 5;
    static final int BLOCKCOUNT = 32;
    static final int INDEXSHIFT = 11;
    static final int INDEXCOUNT = 2048;
    static final int BLOCKMASK = 31;
    private char[] values;
    private char[] indices;
    private int[] hashes;
    private boolean isCompact;
    char defaultValue;
    
    @Deprecated
    public CompactCharArray() {
        this('\0');
    }
    
    @Deprecated
    public CompactCharArray(final char defaultValue) {
        this.values = new char[65536];
        this.indices = new char[2048];
        this.hashes = new int[2048];
        int n = 0;
        while (0 < 65536) {
            this.values[0] = defaultValue;
            ++n;
        }
        while (0 < 2048) {
            this.indices[0] = 0;
            this.hashes[0] = 0;
            ++n;
        }
        this.isCompact = false;
        this.defaultValue = defaultValue;
    }
    
    @Deprecated
    public CompactCharArray(final char[] indices, final char[] values) {
        if (indices.length != 2048) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        while (0 < 2048) {
            final char c = indices[0];
            if (c < '\0' || c >= values.length + 32) {
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
    public CompactCharArray(final String s, final String s2) {
        this(Utility.RLEStringToCharArray(s), Utility.RLEStringToCharArray(s2));
    }
    
    @Deprecated
    public char elementAt(final char c) {
        final int n = (this.indices[c >> 5] & '\uffff') + (c & '\u001f');
        return (n >= this.values.length) ? this.defaultValue : this.values[n];
    }
    
    @Deprecated
    public void setElementAt(final char c, final char c2) {
        if (this.isCompact) {
            this.expand();
        }
        this.touchBlock(c >> 5, this.values[c] = c2);
    }
    
    @Deprecated
    public void setElementAt(final char c, final char c2, final char c3) {
        if (this.isCompact) {
            this.expand();
        }
        for (char c4 = c; c4 <= c2; ++c4) {
            this.touchBlock(c4 >> 5, this.values[c4] = c3);
        }
    }
    
    @Deprecated
    public void compact() {
        this.compact(true);
    }
    
    @Deprecated
    public void compact(final boolean b) {
        if (!this.isCompact) {
            final char[] array = b ? new char[65536] : this.values;
            while (0 < this.indices.length) {
                this.indices[0] = '\uffff';
                final boolean blockTouched = this.blockTouched(0);
                if (!blockTouched && 65535 != 65535) {
                    this.indices[0] = '\uffff';
                }
                else {
                    while (0 < 0) {
                        if (this.hashes[0] == this.hashes[0] && arrayRegionMatches(this.values, 0, this.values, 0, 32)) {
                            this.indices[0] = this.indices[0];
                        }
                        int findOverlappingPosition = 0;
                        ++findOverlappingPosition;
                        final int n;
                        n += 32;
                    }
                    if (this.indices[0] == '\uffff') {
                        if (b) {
                            final int findOverlappingPosition = this.FindOverlappingPosition(0, array, 0);
                        }
                        if (32 > 0) {
                            while (0 < 32) {
                                array[0] = this.values[0];
                                int n2 = 0;
                                ++n2;
                            }
                        }
                        this.indices[0] = 0;
                        if (!blockTouched) {
                            final char c = 0;
                        }
                    }
                }
                int n3 = 0;
                ++n3;
                final int n4;
                n4 += 32;
            }
            final char[] values = new char[0];
            System.arraycopy(array, 0, values, 0, 0);
            this.values = values;
            this.isCompact = true;
            this.hashes = null;
        }
    }
    
    private int FindOverlappingPosition(final int n, final char[] array, final int n2) {
        while (0 < n2) {
            if (32 > n2) {}
            if (arrayRegionMatches(this.values, n, array, 0, 32)) {
                return 0;
            }
            int n3 = 0;
            ++n3;
        }
        return n2;
    }
    
    static final boolean arrayRegionMatches(final char[] array, final int n, final char[] array2, final int n2, final int n3) {
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
    public char[] getValueArray() {
        return this.values;
    }
    
    @Deprecated
    public Object clone() {
        final CompactCharArray compactCharArray = (CompactCharArray)super.clone();
        compactCharArray.values = this.values.clone();
        compactCharArray.indices = this.indices.clone();
        if (this.hashes != null) {
            compactCharArray.hashes = this.hashes.clone();
        }
        return compactCharArray;
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
        final CompactCharArray compactCharArray = (CompactCharArray)o;
        while (0 < 65536) {
            if (this.elementAt((char)0) != compactCharArray.elementAt((char)0)) {
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
            final int n = '\0' + this.values[0];
        }
        return 0;
    }
    
    private void expand() {
        if (this.isCompact) {
            this.hashes = new int[2048];
            final char[] values = new char[65536];
            int n = 0;
            while (0 < 65536) {
                values[0] = this.elementAt((char)0);
                ++n;
            }
            while (0 < 2048) {
                this.indices[0] = 0;
                ++n;
            }
            this.values = null;
            this.values = values;
            this.isCompact = false;
        }
    }
}
