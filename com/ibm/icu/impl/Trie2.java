package com.ibm.icu.impl;

import java.io.*;
import java.util.*;

public abstract class Trie2 implements Iterable
{
    private static ValueMapper defaultValueMapper;
    UTrie2Header header;
    char[] index;
    int data16;
    int[] data32;
    int indexLength;
    int dataLength;
    int index2NullOffset;
    int initialValue;
    int errorValue;
    int highStart;
    int highValueIndex;
    int dataNullOffset;
    int fHash;
    static final int UTRIE2_OPTIONS_VALUE_BITS_MASK = 15;
    static final int UTRIE2_SHIFT_1 = 11;
    static final int UTRIE2_SHIFT_2 = 5;
    static final int UTRIE2_SHIFT_1_2 = 6;
    static final int UTRIE2_OMITTED_BMP_INDEX_1_LENGTH = 32;
    static final int UTRIE2_CP_PER_INDEX_1_ENTRY = 2048;
    static final int UTRIE2_INDEX_2_BLOCK_LENGTH = 64;
    static final int UTRIE2_INDEX_2_MASK = 63;
    static final int UTRIE2_DATA_BLOCK_LENGTH = 32;
    static final int UTRIE2_DATA_MASK = 31;
    static final int UTRIE2_INDEX_SHIFT = 2;
    static final int UTRIE2_DATA_GRANULARITY = 4;
    static final int UTRIE2_INDEX_2_OFFSET = 0;
    static final int UTRIE2_LSCP_INDEX_2_OFFSET = 2048;
    static final int UTRIE2_LSCP_INDEX_2_LENGTH = 32;
    static final int UTRIE2_INDEX_2_BMP_LENGTH = 2080;
    static final int UTRIE2_UTF8_2B_INDEX_2_OFFSET = 2080;
    static final int UTRIE2_UTF8_2B_INDEX_2_LENGTH = 32;
    static final int UTRIE2_INDEX_1_OFFSET = 2112;
    static final int UTRIE2_MAX_INDEX_1_LENGTH = 512;
    static final int UTRIE2_BAD_UTF8_DATA_OFFSET = 128;
    static final int UTRIE2_DATA_START_OFFSET = 192;
    static final int UNEWTRIE2_INDEX_GAP_OFFSET = 2080;
    static final int UNEWTRIE2_INDEX_GAP_LENGTH = 576;
    static final int UNEWTRIE2_MAX_INDEX_2_LENGTH = 35488;
    static final int UNEWTRIE2_INDEX_1_LENGTH = 544;
    static final int UNEWTRIE2_MAX_DATA_LENGTH = 1115264;
    
    public static Trie2 createFromSerialized(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final UTrie2Header header = new UTrie2Header();
        switch (header.signature = dataInputStream.readInt()) {
            case 1416784178: {
                break;
            }
            case 845771348: {
                header.signature = Integer.reverseBytes(header.signature);
                break;
            }
            default: {
                throw new IllegalArgumentException("Stream does not contain a serialized UTrie2");
            }
        }
        header.options = swapShort(true, dataInputStream.readUnsignedShort());
        header.indexLength = swapShort(true, dataInputStream.readUnsignedShort());
        header.shiftedDataLength = swapShort(true, dataInputStream.readUnsignedShort());
        header.index2NullOffset = swapShort(true, dataInputStream.readUnsignedShort());
        header.dataNullOffset = swapShort(true, dataInputStream.readUnsignedShort());
        header.shiftedHighStart = swapShort(true, dataInputStream.readUnsignedShort());
        if ((header.options & 0xF) > 1) {
            throw new IllegalArgumentException("UTrie2 serialized format error.");
        }
        ValueWidth valueWidth;
        Trie2 trie2;
        if ((header.options & 0xF) == 0x0) {
            valueWidth = ValueWidth.BITS_16;
            trie2 = new Trie2_16();
        }
        else {
            valueWidth = ValueWidth.BITS_32;
            trie2 = new Trie2_32();
        }
        trie2.header = header;
        trie2.indexLength = header.indexLength;
        trie2.dataLength = header.shiftedDataLength << 2;
        trie2.index2NullOffset = header.index2NullOffset;
        trie2.dataNullOffset = header.dataNullOffset;
        trie2.highStart = header.shiftedHighStart << 11;
        trie2.highValueIndex = trie2.dataLength - 4;
        if (valueWidth == ValueWidth.BITS_16) {
            final Trie2 trie3 = trie2;
            trie3.highValueIndex += trie2.indexLength;
        }
        int indexLength = trie2.indexLength;
        if (valueWidth == ValueWidth.BITS_16) {
            indexLength += trie2.dataLength;
        }
        trie2.index = new char[indexLength];
        int n = 0;
        while (0 < trie2.indexLength) {
            trie2.index[0] = swapChar(true, dataInputStream.readChar());
            ++n;
        }
        if (valueWidth == ValueWidth.BITS_16) {
            trie2.data16 = trie2.indexLength;
            while (0 < trie2.dataLength) {
                trie2.index[trie2.data16 + 0] = swapChar(true, dataInputStream.readChar());
                ++n;
            }
        }
        else {
            trie2.data32 = new int[trie2.dataLength];
            while (0 < trie2.dataLength) {
                trie2.data32[0] = swapInt(true, dataInputStream.readInt());
                ++n;
            }
        }
        switch (valueWidth) {
            case BITS_16: {
                trie2.data32 = null;
                trie2.initialValue = trie2.index[trie2.dataNullOffset];
                trie2.errorValue = trie2.index[trie2.data16 + 128];
                break;
            }
            case BITS_32: {
                trie2.data16 = 0;
                trie2.initialValue = trie2.data32[trie2.dataNullOffset];
                trie2.errorValue = trie2.data32[128];
                break;
            }
            default: {
                throw new IllegalArgumentException("UTrie2 serialized format error.");
            }
        }
        return trie2;
    }
    
    private static int swapShort(final boolean b, final int n) {
        return b ? (Short.reverseBytes((short)n) & 0xFFFF) : n;
    }
    
    private static char swapChar(final boolean b, final char c) {
        return b ? ((char)Short.reverseBytes((short)c)) : c;
    }
    
    private static int swapInt(final boolean b, final int n) {
        return b ? Integer.reverseBytes(n) : n;
    }
    
    public static int getVersion(final InputStream inputStream, final boolean b) throws IOException {
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("Input stream must support mark().");
        }
        inputStream.mark(4);
        final byte[] array = new byte[4];
        final int read = inputStream.read(array);
        inputStream.reset();
        if (read != array.length) {
            return 0;
        }
        if (array[0] == 84 && array[1] == 114 && array[2] == 105 && array[3] == 101) {
            return 1;
        }
        if (array[0] == 84 && array[1] == 114 && array[2] == 105 && array[3] == 50) {
            return 2;
        }
        if (b) {
            if (array[0] == 101 && array[1] == 105 && array[2] == 114 && array[3] == 84) {
                return 1;
            }
            if (array[0] == 50 && array[1] == 105 && array[2] == 114 && array[3] == 84) {
                return 2;
            }
        }
        return 0;
    }
    
    public abstract int get(final int p0);
    
    public abstract int getFromU16SingleLead(final char p0);
    
    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof Trie2)) {
            return false;
        }
        final Trie2 trie2 = (Trie2)o;
        final Iterator iterator = trie2.iterator();
        for (final Range range : this) {
            if (!iterator.hasNext()) {
                return false;
            }
            if (!range.equals(iterator.next())) {
                return false;
            }
        }
        return !iterator.hasNext() && this.errorValue == trie2.errorValue && this.initialValue == trie2.initialValue;
    }
    
    @Override
    public int hashCode() {
        if (this.fHash == 0) {
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                hashInt(1, iterator.next().hashCode());
            }
            if (!true) {}
            this.fHash = 1;
        }
        return this.fHash;
    }
    
    public Iterator iterator() {
        return this.iterator(Trie2.defaultValueMapper);
    }
    
    public Iterator iterator(final ValueMapper valueMapper) {
        return new Trie2Iterator(valueMapper);
    }
    
    public Iterator iteratorForLeadSurrogate(final char c, final ValueMapper valueMapper) {
        return new Trie2Iterator(c, valueMapper);
    }
    
    public Iterator iteratorForLeadSurrogate(final char c) {
        return new Trie2Iterator(c, Trie2.defaultValueMapper);
    }
    
    protected int serializeHeader(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.header.signature);
        dataOutputStream.writeShort(this.header.options);
        dataOutputStream.writeShort(this.header.indexLength);
        dataOutputStream.writeShort(this.header.shiftedDataLength);
        dataOutputStream.writeShort(this.header.index2NullOffset);
        dataOutputStream.writeShort(this.header.dataNullOffset);
        dataOutputStream.writeShort(this.header.shiftedHighStart);
        int n = 0;
        n += 16;
        while (0 < this.header.indexLength) {
            dataOutputStream.writeChar(this.index[0]);
            int n2 = 0;
            ++n2;
        }
        n = 0 + this.header.indexLength;
        return 0;
    }
    
    public CharSequenceIterator charSequenceIterator(final CharSequence charSequence, final int n) {
        return new CharSequenceIterator(charSequence, n);
    }
    
    int rangeEnd(final int n, final int n2, final int n3) {
        int min;
        int n4;
        for (min = Math.min(this.highStart, n2), n4 = n + 1; n4 < min && this.get(n4) == n3; ++n4) {}
        if (n4 >= this.highStart) {
            n4 = n2;
        }
        return n4 - 1;
    }
    
    private static int hashByte(int n, final int n2) {
        n *= 16777619;
        n ^= n2;
        return n;
    }
    
    private static int hashUChar32(int n, final int n2) {
        n = hashByte(n, n2 & 0xFF);
        n = hashByte(n, n2 >> 8 & 0xFF);
        n = hashByte(n, n2 >> 16);
        return n;
    }
    
    private static int hashInt(int n, final int n2) {
        n = hashByte(n, n2 & 0xFF);
        n = hashByte(n, n2 >> 8 & 0xFF);
        n = hashByte(n, n2 >> 16 & 0xFF);
        n = hashByte(n, n2 >> 24 & 0xFF);
        return n;
    }
    
    static int access$000() {
        return -2128831035;
    }
    
    static int access$100(final int n, final int n2) {
        return hashUChar32(n, n2);
    }
    
    static int access$200(final int n, final int n2) {
        return hashInt(n, n2);
    }
    
    static int access$300(final int n, final int n2) {
        return hashByte(n, n2);
    }
    
    static {
        Trie2.defaultValueMapper = new ValueMapper() {
            public int map(final int n) {
                return n;
            }
        };
    }
    
    enum ValueWidth
    {
        BITS_16("BITS_16", 0), 
        BITS_32("BITS_32", 1);
        
        private static final ValueWidth[] $VALUES;
        
        private ValueWidth(final String s, final int n) {
        }
        
        static {
            $VALUES = new ValueWidth[] { ValueWidth.BITS_16, ValueWidth.BITS_32 };
        }
    }
    
    class Trie2Iterator implements Iterator
    {
        private ValueMapper mapper;
        private Range returnValue;
        private int nextStart;
        private int limitCP;
        private boolean doingCodePoints;
        private boolean doLeadSurrogates;
        final Trie2 this$0;
        
        Trie2Iterator(final Trie2 this$0, final ValueMapper mapper) {
            this.this$0 = this$0;
            this.returnValue = new Range();
            this.doingCodePoints = true;
            this.doLeadSurrogates = true;
            this.mapper = mapper;
            this.nextStart = 0;
            this.limitCP = 1114112;
            this.doLeadSurrogates = true;
        }
        
        Trie2Iterator(final Trie2 this$0, final char c, final ValueMapper mapper) {
            this.this$0 = this$0;
            this.returnValue = new Range();
            this.doingCodePoints = true;
            this.doLeadSurrogates = true;
            if (c < '\ud800' || c > '\udbff') {
                throw new IllegalArgumentException("Bad lead surrogate value.");
            }
            this.mapper = mapper;
            this.nextStart = c - '\ud7c0' << 10;
            this.limitCP = this.nextStart + 1024;
            this.doLeadSurrogates = false;
        }
        
        public Range next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.nextStart >= this.limitCP) {
                this.doingCodePoints = false;
                this.nextStart = 55296;
            }
            if (this.doingCodePoints) {
                this.this$0.get(this.nextStart);
                this.mapper.map(0);
                this.this$0.rangeEnd(this.nextStart, this.limitCP, 0);
                while (0 < this.limitCP - 1) {
                    this.this$0.get(1);
                    if (this.mapper.map(0) != 0) {
                        break;
                    }
                    this.this$0.rangeEnd(1, this.limitCP, 0);
                }
            }
            else {
                this.this$0.getFromU16SingleLead((char)this.nextStart);
                this.mapper.map(0);
                this.rangeEndLS((char)this.nextStart);
                while (0 < 56319) {
                    this.this$0.getFromU16SingleLead((char)1);
                    if (this.mapper.map(0) != 0) {
                        break;
                    }
                    this.rangeEndLS((char)1);
                }
            }
            this.returnValue.startCodePoint = this.nextStart;
            this.returnValue.endCodePoint = 0;
            this.returnValue.value = 0;
            this.returnValue.leadSurrogate = !this.doingCodePoints;
            this.nextStart = 1;
            return this.returnValue;
        }
        
        public boolean hasNext() {
            return (this.doingCodePoints && (this.doLeadSurrogates || this.nextStart < this.limitCP)) || this.nextStart < 56320;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        private int rangeEndLS(final char c) {
            if (c >= '\udbff') {
                return 56319;
            }
            int fromU16SingleLead;
            int n;
            for (fromU16SingleLead = this.this$0.getFromU16SingleLead(c), n = c + '\u0001'; n <= 56319 && this.this$0.getFromU16SingleLead((char)n) == fromU16SingleLead; ++n) {}
            return n - 1;
        }
        
        public Object next() {
            return this.next();
        }
    }
    
    public interface ValueMapper
    {
        int map(final int p0);
    }
    
    public static class Range
    {
        public int startCodePoint;
        public int endCodePoint;
        public int value;
        public boolean leadSurrogate;
        
        @Override
        public boolean equals(final Object o) {
            if (o == null || !o.getClass().equals(this.getClass())) {
                return false;
            }
            final Range range = (Range)o;
            return this.startCodePoint == range.startCodePoint && this.endCodePoint == range.endCodePoint && this.value == range.value && this.leadSurrogate == range.leadSurrogate;
        }
        
        @Override
        public int hashCode() {
            return Trie2.access$300(Trie2.access$200(Trie2.access$100(Trie2.access$100(Trie2.access$000(), this.startCodePoint), this.endCodePoint), this.value), this.leadSurrogate ? 1 : 0);
        }
    }
    
    static class UTrie2Header
    {
        int signature;
        int options;
        int indexLength;
        int shiftedDataLength;
        int index2NullOffset;
        int dataNullOffset;
        int shiftedHighStart;
    }
    
    public class CharSequenceIterator implements Iterator
    {
        private CharSequence text;
        private int textLength;
        private int index;
        private CharSequenceValues fResults;
        final Trie2 this$0;
        
        CharSequenceIterator(final Trie2 this$0, final CharSequence text, final int n) {
            this.this$0 = this$0;
            this.fResults = new CharSequenceValues();
            this.text = text;
            this.textLength = this.text.length();
            this.set(n);
        }
        
        public void set(final int index) {
            if (index < 0 || index > this.textLength) {
                throw new IndexOutOfBoundsException();
            }
            this.index = index;
        }
        
        public final boolean hasNext() {
            return this.index < this.textLength;
        }
        
        public final boolean hasPrevious() {
            return this.index > 0;
        }
        
        public CharSequenceValues next() {
            final int codePoint = Character.codePointAt(this.text, this.index);
            final int value = this.this$0.get(codePoint);
            this.fResults.index = this.index;
            this.fResults.codePoint = codePoint;
            this.fResults.value = value;
            ++this.index;
            if (codePoint >= 65536) {
                ++this.index;
            }
            return this.fResults;
        }
        
        public CharSequenceValues previous() {
            final int codePointBefore = Character.codePointBefore(this.text, this.index);
            final int value = this.this$0.get(codePointBefore);
            --this.index;
            if (codePointBefore >= 65536) {
                --this.index;
            }
            this.fResults.index = this.index;
            this.fResults.codePoint = codePointBefore;
            this.fResults.value = value;
            return this.fResults;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Trie2.CharSequenceIterator does not support remove().");
        }
        
        public Object next() {
            return this.next();
        }
    }
    
    public static class CharSequenceValues
    {
        public int index;
        public int codePoint;
        public int value;
    }
}
