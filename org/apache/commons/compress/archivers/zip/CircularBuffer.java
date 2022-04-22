package org.apache.commons.compress.archivers.zip;

class CircularBuffer
{
    private final int size;
    private final byte[] buffer;
    private int readIndex;
    private int writeIndex;
    
    CircularBuffer(final int size) {
        this.size = size;
        this.buffer = new byte[size];
    }
    
    public boolean available() {
        return this.readIndex != this.writeIndex;
    }
    
    public void put(final int n) {
        this.buffer[this.writeIndex] = (byte)n;
        this.writeIndex = (this.writeIndex + 1) % this.size;
    }
    
    public int get() {
        if (this.available()) {
            final byte b = this.buffer[this.readIndex];
            this.readIndex = (this.readIndex + 1) % this.size;
            return b & 0xFF;
        }
        return -1;
    }
    
    public void copy(final int n, final int n2) {
        final int n3 = this.writeIndex - n;
        for (int n4 = n3 + n2, i = n3; i < n4; ++i) {
            this.buffer[this.writeIndex] = this.buffer[(i + this.size) % this.size];
            this.writeIndex = (this.writeIndex + 1) % this.size;
        }
    }
}
