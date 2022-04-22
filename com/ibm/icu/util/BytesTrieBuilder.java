package com.ibm.icu.util;

import java.nio.*;

public final class BytesTrieBuilder extends StringTrieBuilder
{
    private final byte[] intBytes;
    private byte[] bytes;
    private int bytesLength;
    static final boolean $assertionsDisabled;
    
    public BytesTrieBuilder() {
        this.intBytes = new byte[5];
    }
    
    public BytesTrieBuilder add(final byte[] array, final int n, final int n2) {
        this.addImpl(new BytesAsCharSequence(array, n), n2);
        return this;
    }
    
    public BytesTrie build(final Option option) {
        this.buildBytes(option);
        return new BytesTrie(this.bytes, this.bytes.length - this.bytesLength);
    }
    
    public ByteBuffer buildByteBuffer(final Option option) {
        this.buildBytes(option);
        return ByteBuffer.wrap(this.bytes, this.bytes.length - this.bytesLength, this.bytesLength);
    }
    
    private void buildBytes(final Option option) {
        if (this.bytes == null) {
            this.bytes = new byte[1024];
        }
        this.buildImpl(option);
    }
    
    public BytesTrieBuilder clear() {
        this.clearImpl();
        this.bytes = null;
        this.bytesLength = 0;
        return this;
    }
    
    @Override
    @Deprecated
    protected boolean matchNodesCanHaveValues() {
        return false;
    }
    
    @Override
    @Deprecated
    protected int getMaxBranchLinearSubNodeLength() {
        return 5;
    }
    
    @Override
    @Deprecated
    protected int getMinLinearMatch() {
        return 16;
    }
    
    @Override
    @Deprecated
    protected int getMaxLinearMatchLength() {
        return 16;
    }
    
    private void ensureCapacity(final int n) {
        if (n > this.bytes.length) {
            int i = this.bytes.length;
            do {
                i *= 2;
            } while (i <= n);
            final byte[] bytes = new byte[i];
            System.arraycopy(this.bytes, this.bytes.length - this.bytesLength, bytes, bytes.length - this.bytesLength, this.bytesLength);
            this.bytes = bytes;
        }
    }
    
    @Override
    @Deprecated
    protected int write(final int n) {
        final int bytesLength = this.bytesLength + 1;
        this.ensureCapacity(bytesLength);
        this.bytesLength = bytesLength;
        this.bytes[this.bytes.length - this.bytesLength] = (byte)n;
        return this.bytesLength;
    }
    
    @Override
    @Deprecated
    protected int write(int n, int i) {
        final int bytesLength = this.bytesLength + i;
        this.ensureCapacity(bytesLength);
        this.bytesLength = bytesLength;
        int n2 = this.bytes.length - this.bytesLength;
        while (i > 0) {
            this.bytes[n2++] = (byte)this.strings.charAt(n++);
            --i;
        }
        return this.bytesLength;
    }
    
    private int write(final byte[] array, final int n) {
        final int bytesLength = this.bytesLength + n;
        this.ensureCapacity(bytesLength);
        this.bytesLength = bytesLength;
        System.arraycopy(array, 0, this.bytes, this.bytes.length - this.bytesLength, n);
        return this.bytesLength;
    }
    
    @Override
    @Deprecated
    protected int writeValueAndFinal(final int n, final boolean b) {
        if (0 <= n && n <= 64) {
            return this.write(16 + n << 1 | (b ? 1 : 0));
        }
        if (n < 0 || n > 16777215) {
            this.intBytes[0] = 127;
            this.intBytes[1] = (byte)(n >> 24);
            this.intBytes[2] = (byte)(n >> 16);
            this.intBytes[3] = (byte)(n >> 8);
            this.intBytes[4] = (byte)n;
        }
        else {
            int n3 = 0;
            if (n <= 6911) {
                this.intBytes[0] = (byte)(81 + (n >> 8));
            }
            else {
                if (n <= 1179647) {
                    this.intBytes[0] = (byte)(108 + (n >> 16));
                }
                else {
                    this.intBytes[0] = 126;
                    this.intBytes[1] = (byte)(n >> 16);
                }
                final byte[] intBytes = this.intBytes;
                final int n2 = 2;
                ++n3;
                intBytes[n2] = (byte)(n >> 8);
            }
            final byte[] intBytes2 = this.intBytes;
            final int n4 = 2;
            ++n3;
            intBytes2[n4] = (byte)n;
        }
        this.intBytes[0] = (byte)(this.intBytes[0] << 1 | (b ? 1 : 0));
        return this.write(this.intBytes, 2);
    }
    
    @Override
    @Deprecated
    protected int writeValueAndType(final boolean b, final int n, final int n2) {
        int n3 = this.write(n2);
        if (b) {
            n3 = this.writeValueAndFinal(n, false);
        }
        return n3;
    }
    
    @Override
    @Deprecated
    protected int writeDeltaTo(final int n) {
        final int n2 = this.bytesLength - n;
        assert n2 >= 0;
        if (n2 <= 191) {
            return this.write(n2);
        }
        if (n2 <= 12287) {
            this.intBytes[0] = (byte)(192 + (n2 >> 8));
        }
        else {
            if (n2 <= 917503) {
                this.intBytes[0] = (byte)(240 + (n2 >> 16));
            }
            else {
                if (n2 <= 16777215) {
                    this.intBytes[0] = -2;
                }
                else {
                    this.intBytes[0] = -1;
                    this.intBytes[1] = (byte)(n2 >> 24);
                }
                this.intBytes[1] = (byte)(n2 >> 16);
            }
            this.intBytes[1] = (byte)(n2 >> 8);
        }
        final byte[] intBytes = this.intBytes;
        final int n3 = 4;
        int n4 = 0;
        ++n4;
        intBytes[n3] = (byte)n2;
        return this.write(this.intBytes, 4);
    }
    
    static {
        $assertionsDisabled = !BytesTrieBuilder.class.desiredAssertionStatus();
    }
    
    private static final class BytesAsCharSequence implements CharSequence
    {
        private byte[] s;
        private int len;
        
        public BytesAsCharSequence(final byte[] s, final int len) {
            this.s = s;
            this.len = len;
        }
        
        public char charAt(final int n) {
            return (char)(this.s[n] & 0xFF);
        }
        
        public int length() {
            return this.len;
        }
        
        public CharSequence subSequence(final int n, final int n2) {
            return null;
        }
    }
}
