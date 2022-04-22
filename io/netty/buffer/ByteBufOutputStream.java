package io.netty.buffer;

import java.io.*;

public class ByteBufOutputStream extends OutputStream implements DataOutput
{
    private final ByteBuf buffer;
    private final int startIndex;
    private final DataOutputStream utf8out;
    
    public ByteBufOutputStream(final ByteBuf buffer) {
        this.utf8out = new DataOutputStream(this);
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = buffer;
        this.startIndex = buffer.writerIndex();
    }
    
    public int writtenBytes() {
        return this.buffer.writerIndex() - this.startIndex;
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 == 0) {
            return;
        }
        this.buffer.writeBytes(array, n, n2);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.buffer.writeBytes(array);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.buffer.writeByte((byte)n);
    }
    
    @Override
    public void writeBoolean(final boolean b) throws IOException {
        this.write(b ? 1 : 0);
    }
    
    @Override
    public void writeByte(final int n) throws IOException {
        this.write(n);
    }
    
    @Override
    public void writeBytes(final String s) throws IOException {
        while (0 < s.length()) {
            this.write((byte)s.charAt(0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeChar(final int n) throws IOException {
        this.writeShort((short)n);
    }
    
    @Override
    public void writeChars(final String s) throws IOException {
        while (0 < s.length()) {
            this.writeChar(s.charAt(0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeDouble(final double n) throws IOException {
        this.writeLong(Double.doubleToLongBits(n));
    }
    
    @Override
    public void writeFloat(final float n) throws IOException {
        this.writeInt(Float.floatToIntBits(n));
    }
    
    @Override
    public void writeInt(final int n) throws IOException {
        this.buffer.writeInt(n);
    }
    
    @Override
    public void writeLong(final long n) throws IOException {
        this.buffer.writeLong(n);
    }
    
    @Override
    public void writeShort(final int n) throws IOException {
        this.buffer.writeShort((short)n);
    }
    
    @Override
    public void writeUTF(final String s) throws IOException {
        this.utf8out.writeUTF(s);
    }
    
    public ByteBuf buffer() {
        return this.buffer;
    }
}
