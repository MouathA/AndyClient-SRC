package com.ibm.icu.impl;

import java.io.*;
import com.ibm.icu.text.*;
import java.util.*;

public final class UBiDiProps
{
    private int[] indexes;
    private int[] mirrors;
    private byte[] jgArray;
    private Trie2_16 trie;
    private static final String DATA_NAME = "ubidi";
    private static final String DATA_TYPE = "icu";
    private static final String DATA_FILE_NAME = "ubidi.icu";
    private static final byte[] FMT;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_MIRROR_LENGTH = 3;
    private static final int IX_JG_START = 4;
    private static final int IX_JG_LIMIT = 5;
    private static final int IX_MAX_VALUES = 15;
    private static final int IX_TOP = 16;
    private static final int JT_SHIFT = 5;
    private static final int JOIN_CONTROL_SHIFT = 10;
    private static final int BIDI_CONTROL_SHIFT = 11;
    private static final int IS_MIRRORED_SHIFT = 12;
    private static final int MIRROR_DELTA_SHIFT = 13;
    private static final int MAX_JG_SHIFT = 16;
    private static final int CLASS_MASK = 31;
    private static final int JT_MASK = 224;
    private static final int MAX_JG_MASK = 16711680;
    private static final int ESC_MIRROR_DELTA = -4;
    private static final int MIRROR_INDEX_SHIFT = 21;
    public static final UBiDiProps INSTANCE;
    
    private UBiDiProps() throws IOException {
        final InputStream stream = ICUData.getStream("data/icudt51b/ubidi.icu");
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(stream, 4096);
        this.readData(bufferedInputStream);
        bufferedInputStream.close();
        stream.close();
    }
    
    private void readData(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        ICUBinary.readHeader(dataInputStream, UBiDiProps.FMT, new IsAcceptable(null));
        final int int1 = dataInputStream.readInt();
        if (int1 < 16) {
            throw new IOException("indexes[0] too small in ubidi.icu");
        }
        (this.indexes = new int[int1])[0] = int1;
        int n = 0;
        while (0 < int1) {
            this.indexes[0] = dataInputStream.readInt();
            ++n;
        }
        this.trie = Trie2_16.createFromSerialized(dataInputStream);
        final int n2 = this.indexes[2];
        final int serializedLength = this.trie.getSerializedLength();
        if (serializedLength > n2) {
            throw new IOException("ubidi.icu: not enough bytes for the trie");
        }
        dataInputStream.skipBytes(n2 - serializedLength);
        final int n3 = this.indexes[3];
        if (n3 > 0) {
            this.mirrors = new int[n3];
            while (0 < n3) {
                this.mirrors[0] = dataInputStream.readInt();
                ++n;
            }
        }
        final int n4 = this.indexes[5] - this.indexes[4];
        this.jgArray = new byte[n4];
        while (0 < n4) {
            this.jgArray[0] = dataInputStream.readByte();
            ++n;
        }
    }
    
    public final void addPropertyStarts(final UnicodeSet set) {
        final Iterator iterator = this.trie.iterator();
        Trie2.Range range;
        while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
        int n = 0;
        while (0 < this.indexes[3]) {
            final int mirrorCodePoint = getMirrorCodePoint(this.mirrors[0]);
            set.add(mirrorCodePoint, mirrorCodePoint + 1);
            ++n;
        }
        for (int n2 = this.indexes[4], n3 = this.indexes[5]; 0 < n3 - n2; ++n2, ++n) {
            if (this.jgArray[0] != 0) {
                set.add(n2);
            }
        }
        if (false) {
            final int n3;
            set.add(n3);
        }
    }
    
    public final int getMaxValue(final int n) {
        final int n2 = this.indexes[15];
        switch (n) {
            case 4096: {
                return n2 & 0x1F;
            }
            case 4102: {
                return (n2 & 0xFF0000) >> 16;
            }
            case 4103: {
                return (n2 & 0xE0) >> 5;
            }
            default: {
                return -1;
            }
        }
    }
    
    public final int getClass(final int n) {
        return getClassFromProps(this.trie.get(n));
    }
    
    public final boolean isMirrored(final int n) {
        return getFlagFromProps(this.trie.get(n), 12);
    }
    
    public final int getMirror(final int n) {
        final int n2 = (short)this.trie.get(n) >> 13;
        if (n2 != -4) {
            return n + n2;
        }
        while (0 < this.indexes[3]) {
            final int n3 = this.mirrors[0];
            final int mirrorCodePoint = getMirrorCodePoint(n3);
            if (n == mirrorCodePoint) {
                return getMirrorCodePoint(this.mirrors[getMirrorIndex(n3)]);
            }
            if (n < mirrorCodePoint) {
                break;
            }
            int n4 = 0;
            ++n4;
        }
        return n;
    }
    
    public final boolean isBidiControl(final int n) {
        return getFlagFromProps(this.trie.get(n), 11);
    }
    
    public final boolean isJoinControl(final int n) {
        return getFlagFromProps(this.trie.get(n), 10);
    }
    
    public final int getJoiningType(final int n) {
        return (this.trie.get(n) & 0xE0) >> 5;
    }
    
    public final int getJoiningGroup(final int n) {
        final int n2 = this.indexes[4];
        final int n3 = this.indexes[5];
        if (n2 <= n && n < n3) {
            return this.jgArray[n - n2] & 0xFF;
        }
        return 0;
    }
    
    private static final int getClassFromProps(final int n) {
        return n & 0x1F;
    }
    
    private static final boolean getFlagFromProps(final int n, final int n2) {
        return (n >> n2 & 0x1) != 0x0;
    }
    
    private static final int getMirrorCodePoint(final int n) {
        return n & 0x1FFFFF;
    }
    
    private static final int getMirrorIndex(final int n) {
        return n >>> 21;
    }
    
    static {
        FMT = new byte[] { 66, 105, 68, 105 };
        INSTANCE = new UBiDiProps();
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        private IsAcceptable() {
        }
        
        public boolean isDataVersionAcceptable(final byte[] array) {
            return array[0] == 2;
        }
        
        IsAcceptable(final UBiDiProps$1 object) {
            this();
        }
    }
}
