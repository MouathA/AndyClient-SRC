package io.netty.buffer;

import java.io.*;

public class ByteBufInputStream extends InputStream implements DataInput
{
    private final ByteBuf buffer;
    private final int startIndex;
    private final int endIndex;
    private final StringBuilder lineBuf;
    
    public ByteBufInputStream(final ByteBuf byteBuf) {
        this(byteBuf, byteBuf.readableBytes());
    }
    
    public ByteBufInputStream(final ByteBuf buffer, final int n) {
        this.lineBuf = new StringBuilder();
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        if (n < 0) {
            throw new IllegalArgumentException("length: " + n);
        }
        if (n > buffer.readableBytes()) {
            throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + n + ", maximum is " + buffer.readableBytes());
        }
        this.buffer = buffer;
        this.startIndex = buffer.readerIndex();
        this.endIndex = this.startIndex + n;
        buffer.markReaderIndex();
    }
    
    public int readBytes() {
        return this.buffer.readerIndex() - this.startIndex;
    }
    
    @Override
    public int available() throws IOException {
        return this.endIndex - this.buffer.readerIndex();
    }
    
    @Override
    public void mark(final int n) {
        this.buffer.markReaderIndex();
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public int read() throws IOException {
        if (!this.buffer.isReadable()) {
            return -1;
        }
        return this.buffer.readByte() & 0xFF;
    }
    
    @Override
    public int read(final byte[] array, final int n, int min) throws IOException {
        final int available = this.available();
        if (available == 0) {
            return -1;
        }
        min = Math.min(available, min);
        this.buffer.readBytes(array, n, min);
        return min;
    }
    
    @Override
    public void reset() throws IOException {
        this.buffer.resetReaderIndex();
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n > 2147483647L) {
            return this.skipBytes(Integer.MAX_VALUE);
        }
        return this.skipBytes((int)n);
    }
    
    @Override
    public boolean readBoolean() throws IOException {
        this.checkAvailable(1);
        return this.read() != 0;
    }
    
    @Override
    public byte readByte() throws IOException {
        if (!this.buffer.isReadable()) {
            throw new EOFException();
        }
        return this.buffer.readByte();
    }
    
    @Override
    public char readChar() throws IOException {
        return (char)this.readShort();
    }
    
    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public void readFully(final byte[] array) throws IOException {
        this.readFully(array, 0, array.length);
    }
    
    @Override
    public void readFully(final byte[] array, final int n, final int n2) throws IOException {
        this.checkAvailable(n2);
        this.buffer.readBytes(array, n, n2);
    }
    
    @Override
    public int readInt() throws IOException {
        this.checkAvailable(4);
        return this.buffer.readInt();
    }
    
    @Override
    public String readLine() throws IOException {
        this.lineBuf.setLength(0);
        while (this.buffer.isReadable()) {
            final short unsignedByte = this.buffer.readUnsignedByte();
            switch (unsignedByte) {
                case 10: {
                    break;
                }
                case 13: {
                    if (this.buffer.isReadable() && (char)this.buffer.getUnsignedByte(this.buffer.readerIndex()) == '\n') {
                        this.buffer.skipBytes(1);
                        break;
                    }
                    break;
                }
                default: {
                    this.lineBuf.append((char)unsignedByte);
                    continue;
                }
            }
            return this.lineBuf.toString();
        }
        return (this.lineBuf.length() > 0) ? this.lineBuf.toString() : null;
    }
    
    @Override
    public long readLong() throws IOException {
        this.checkAvailable(8);
        return this.buffer.readLong();
    }
    
    @Override
    public short readShort() throws IOException {
        this.checkAvailable(2);
        return this.buffer.readShort();
    }
    
    @Override
    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }
    
    @Override
    public int readUnsignedByte() throws IOException {
        return this.readByte() & 0xFF;
    }
    
    @Override
    public int readUnsignedShort() throws IOException {
        return this.readShort() & 0xFFFF;
    }
    
    @Override
    public int skipBytes(final int n) throws IOException {
        final int min = Math.min(this.available(), n);
        this.buffer.skipBytes(min);
        return min;
    }
    
    private void checkAvailable(final int n) throws IOException {
        if (n < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
        }
        if (n > this.available()) {
            throw new EOFException("fieldSize is too long! Length is " + n + ", but maximum is " + this.available());
        }
    }
}
