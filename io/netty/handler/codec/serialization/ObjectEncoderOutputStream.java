package io.netty.handler.codec.serialization;

import io.netty.buffer.*;
import java.io.*;

public class ObjectEncoderOutputStream extends OutputStream implements ObjectOutput
{
    private final DataOutputStream out;
    private final int estimatedLength;
    
    public ObjectEncoderOutputStream(final OutputStream outputStream) {
        this(outputStream, 512);
    }
    
    public ObjectEncoderOutputStream(final OutputStream outputStream, final int estimatedLength) {
        if (outputStream == null) {
            throw new NullPointerException("out");
        }
        if (estimatedLength < 0) {
            throw new IllegalArgumentException("estimatedLength: " + estimatedLength);
        }
        if (outputStream instanceof DataOutputStream) {
            this.out = (DataOutputStream)outputStream;
        }
        else {
            this.out = new DataOutputStream(outputStream);
        }
        this.estimatedLength = estimatedLength;
    }
    
    @Override
    public void writeObject(final Object o) throws IOException {
        final ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(Unpooled.buffer(this.estimatedLength));
        final CompactObjectOutputStream compactObjectOutputStream = new CompactObjectOutputStream(byteBufOutputStream);
        compactObjectOutputStream.writeObject(o);
        compactObjectOutputStream.flush();
        compactObjectOutputStream.close();
        final ByteBuf buffer = byteBufOutputStream.buffer();
        final int readableBytes = buffer.readableBytes();
        this.writeInt(readableBytes);
        buffer.getBytes(0, this, readableBytes);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    public final int size() {
        return this.out.size();
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.out.write(array);
    }
    
    @Override
    public final void writeBoolean(final boolean b) throws IOException {
        this.out.writeBoolean(b);
    }
    
    @Override
    public final void writeByte(final int n) throws IOException {
        this.out.writeByte(n);
    }
    
    @Override
    public final void writeBytes(final String s) throws IOException {
        this.out.writeBytes(s);
    }
    
    @Override
    public final void writeChar(final int n) throws IOException {
        this.out.writeChar(n);
    }
    
    @Override
    public final void writeChars(final String s) throws IOException {
        this.out.writeChars(s);
    }
    
    @Override
    public final void writeDouble(final double n) throws IOException {
        this.out.writeDouble(n);
    }
    
    @Override
    public final void writeFloat(final float n) throws IOException {
        this.out.writeFloat(n);
    }
    
    @Override
    public final void writeInt(final int n) throws IOException {
        this.out.writeInt(n);
    }
    
    @Override
    public final void writeLong(final long n) throws IOException {
        this.out.writeLong(n);
    }
    
    @Override
    public final void writeShort(final int n) throws IOException {
        this.out.writeShort(n);
    }
    
    @Override
    public final void writeUTF(final String s) throws IOException {
        this.out.writeUTF(s);
    }
}
