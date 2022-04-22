package com.ibm.icu.impl;

import java.util.*;
import java.io.*;
import com.ibm.icu.text.*;

public abstract class Trie
{
    protected static final int LEAD_INDEX_OFFSET_ = 320;
    protected static final int INDEX_STAGE_1_SHIFT_ = 5;
    protected static final int INDEX_STAGE_2_SHIFT_ = 2;
    protected static final int DATA_BLOCK_LENGTH = 32;
    protected static final int INDEX_STAGE_3_MASK_ = 31;
    protected static final int SURROGATE_BLOCK_BITS = 5;
    protected static final int SURROGATE_BLOCK_COUNT = 32;
    protected static final int BMP_INDEX_LENGTH = 2048;
    protected static final int SURROGATE_MASK_ = 1023;
    protected char[] m_index_;
    protected DataManipulate m_dataManipulate_;
    protected int m_dataOffset_;
    protected int m_dataLength_;
    protected static final int HEADER_LENGTH_ = 16;
    protected static final int HEADER_OPTIONS_LATIN1_IS_LINEAR_MASK_ = 512;
    protected static final int HEADER_SIGNATURE_ = 1416784229;
    private static final int HEADER_OPTIONS_SHIFT_MASK_ = 15;
    protected static final int HEADER_OPTIONS_INDEX_SHIFT_ = 4;
    protected static final int HEADER_OPTIONS_DATA_IS_32_BIT_ = 256;
    private boolean m_isLatin1Linear_;
    private int m_options_;
    static final boolean $assertionsDisabled;
    
    public final boolean isLatin1Linear() {
        return this.m_isLatin1Linear_;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Trie)) {
            return false;
        }
        final Trie trie = (Trie)o;
        return this.m_isLatin1Linear_ == trie.m_isLatin1Linear_ && this.m_options_ == trie.m_options_ && this.m_dataLength_ == trie.m_dataLength_ && Arrays.equals(this.m_index_, trie.m_index_);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    public int getSerializedDataSize() {
        final int n = 16 + (this.m_dataOffset_ << 1);
        if (this.isCharTrie()) {
            final int n2 = 16 + (this.m_dataLength_ << 1);
        }
        else if (this.isIntTrie()) {
            final int n3 = 16 + (this.m_dataLength_ << 2);
        }
        return 16;
    }
    
    protected Trie(final InputStream inputStream, final DataManipulate dataManipulate_) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        final int int1 = dataInputStream.readInt();
        this.m_options_ = dataInputStream.readInt();
        if (!this.checkHeader(int1)) {
            throw new IllegalArgumentException("ICU data file error: Trie header authentication failed, please check if you have the most updated ICU data file");
        }
        if (dataManipulate_ != null) {
            this.m_dataManipulate_ = dataManipulate_;
        }
        else {
            this.m_dataManipulate_ = new DefaultGetFoldingOffset(null);
        }
        this.m_isLatin1Linear_ = ((this.m_options_ & 0x200) != 0x0);
        this.m_dataOffset_ = dataInputStream.readInt();
        this.m_dataLength_ = dataInputStream.readInt();
        this.unserialize(inputStream);
    }
    
    protected Trie(final char[] index_, final int options_, final DataManipulate dataManipulate_) {
        this.m_options_ = options_;
        if (dataManipulate_ != null) {
            this.m_dataManipulate_ = dataManipulate_;
        }
        else {
            this.m_dataManipulate_ = new DefaultGetFoldingOffset(null);
        }
        this.m_isLatin1Linear_ = ((this.m_options_ & 0x200) != 0x0);
        this.m_index_ = index_;
        this.m_dataOffset_ = this.m_index_.length;
    }
    
    protected abstract int getSurrogateOffset(final char p0, final char p1);
    
    protected abstract int getValue(final int p0);
    
    protected abstract int getInitialValue();
    
    protected final int getRawOffset(final int n, final char c) {
        return (this.m_index_[n + (c >> 5)] << 2) + (c & '\u001f');
    }
    
    protected final int getBMPOffset(final char c) {
        return (c >= '\ud800' && c <= '\udbff') ? this.getRawOffset(320, c) : this.getRawOffset(0, c);
    }
    
    protected final int getLeadOffset(final char c) {
        return this.getRawOffset(0, c);
    }
    
    protected final int getCodePointOffset(final int n) {
        if (n < 0) {
            return -1;
        }
        if (n < 55296) {
            return this.getRawOffset(0, (char)n);
        }
        if (n < 65536) {
            return this.getBMPOffset((char)n);
        }
        if (n <= 1114111) {
            return this.getSurrogateOffset(UTF16.getLeadSurrogate(n), (char)(n & 0x3FF));
        }
        return -1;
    }
    
    protected void unserialize(final InputStream inputStream) throws IOException {
        this.m_index_ = new char[this.m_dataOffset_];
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        while (0 < this.m_dataOffset_) {
            this.m_index_[0] = dataInputStream.readChar();
            int n = 0;
            ++n;
        }
    }
    
    protected final boolean isIntTrie() {
        return (this.m_options_ & 0x100) != 0x0;
    }
    
    protected final boolean isCharTrie() {
        return (this.m_options_ & 0x100) == 0x0;
    }
    
    private final boolean checkHeader(final int n) {
        return n == 1416784229 && (this.m_options_ & 0xF) == 0x5 && (this.m_options_ >> 4 & 0xF) == 0x2;
    }
    
    static {
        $assertionsDisabled = !Trie.class.desiredAssertionStatus();
    }
    
    private static class DefaultGetFoldingOffset implements DataManipulate
    {
        private DefaultGetFoldingOffset() {
        }
        
        public int getFoldingOffset(final int n) {
            return n;
        }
        
        DefaultGetFoldingOffset(final Trie$1 object) {
            this();
        }
    }
    
    public interface DataManipulate
    {
        int getFoldingOffset(final int p0);
    }
}
