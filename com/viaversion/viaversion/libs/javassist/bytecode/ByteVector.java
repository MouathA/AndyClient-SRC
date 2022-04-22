package com.viaversion.viaversion.libs.javassist.bytecode;

class ByteVector implements Cloneable
{
    private byte[] buffer;
    private int size;
    
    public ByteVector() {
        this.buffer = new byte[64];
        this.size = 0;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final ByteVector byteVector = (ByteVector)super.clone();
        byteVector.buffer = this.buffer.clone();
        return byteVector;
    }
    
    public final int getSize() {
        return this.size;
    }
    
    public final byte[] copy() {
        final byte[] array = new byte[this.size];
        System.arraycopy(this.buffer, 0, array, 0, this.size);
        return array;
    }
    
    public int read(final int n) {
        if (n < 0 || this.size <= n) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.buffer[n];
    }
    
    public void write(final int n, final int n2) {
        if (n < 0 || this.size <= n) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        this.buffer[n] = (byte)n2;
    }
    
    public void add(final int n) {
        this.addGap(1);
        this.buffer[this.size - 1] = (byte)n;
    }
    
    public void add(final int n, final int n2) {
        this.addGap(2);
        this.buffer[this.size - 2] = (byte)n;
        this.buffer[this.size - 1] = (byte)n2;
    }
    
    public void add(final int n, final int n2, final int n3, final int n4) {
        this.addGap(4);
        this.buffer[this.size - 4] = (byte)n;
        this.buffer[this.size - 3] = (byte)n2;
        this.buffer[this.size - 2] = (byte)n3;
        this.buffer[this.size - 1] = (byte)n4;
    }
    
    public void addGap(final int n) {
        if (this.size + n > this.buffer.length) {
            int n2 = this.size << 1;
            if (n2 < this.size + n) {
                n2 = this.size + n;
            }
            final byte[] buffer = new byte[n2];
            System.arraycopy(this.buffer, 0, buffer, 0, this.size);
            this.buffer = buffer;
        }
        this.size += n;
    }
}
