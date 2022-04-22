package io.netty.handler.codec.serialization;

import java.io.*;

public class ObjectDecoderInputStream extends InputStream implements ObjectInput
{
    private final DataInputStream in;
    private final int maxObjectSize;
    private final ClassResolver classResolver;
    
    public ObjectDecoderInputStream(final InputStream inputStream) {
        this(inputStream, null);
    }
    
    public ObjectDecoderInputStream(final InputStream inputStream, final ClassLoader classLoader) {
        this(inputStream, classLoader, 1048576);
    }
    
    public ObjectDecoderInputStream(final InputStream inputStream, final int n) {
        this(inputStream, null, n);
    }
    
    public ObjectDecoderInputStream(final InputStream inputStream, final ClassLoader classLoader, final int maxObjectSize) {
        if (inputStream == null) {
            throw new NullPointerException("in");
        }
        if (maxObjectSize <= 0) {
            throw new IllegalArgumentException("maxObjectSize: " + maxObjectSize);
        }
        if (inputStream instanceof DataInputStream) {
            this.in = (DataInputStream)inputStream;
        }
        else {
            this.in = new DataInputStream(inputStream);
        }
        this.classResolver = ClassResolvers.weakCachingResolver(classLoader);
        this.maxObjectSize = maxObjectSize;
    }
    
    @Override
    public Object readObject() throws ClassNotFoundException, IOException {
        final int int1 = this.readInt();
        if (int1 <= 0) {
            throw new StreamCorruptedException("invalid data length: " + int1);
        }
        if (int1 > this.maxObjectSize) {
            throw new StreamCorruptedException("data length too big: " + int1 + " (max: " + this.maxObjectSize + ')');
        }
        return new CompactObjectInputStream(this.in, this.classResolver).readObject();
    }
    
    @Override
    public int available() throws IOException {
        return this.in.available();
    }
    
    @Override
    public void close() throws IOException {
        this.in.close();
    }
    
    @Override
    public void mark(final int n) {
        this.in.mark(n);
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    @Override
    public int read() throws IOException {
        return this.in.read();
    }
    
    @Override
    public final int read(final byte[] array, final int n, final int n2) throws IOException {
        return this.in.read(array, n, n2);
    }
    
    @Override
    public final int read(final byte[] array) throws IOException {
        return this.in.read(array);
    }
    
    @Override
    public final boolean readBoolean() throws IOException {
        return this.in.readBoolean();
    }
    
    @Override
    public final byte readByte() throws IOException {
        return this.in.readByte();
    }
    
    @Override
    public final char readChar() throws IOException {
        return this.in.readChar();
    }
    
    @Override
    public final double readDouble() throws IOException {
        return this.in.readDouble();
    }
    
    @Override
    public final float readFloat() throws IOException {
        return this.in.readFloat();
    }
    
    @Override
    public final void readFully(final byte[] array, final int n, final int n2) throws IOException {
        this.in.readFully(array, n, n2);
    }
    
    @Override
    public final void readFully(final byte[] array) throws IOException {
        this.in.readFully(array);
    }
    
    @Override
    public final int readInt() throws IOException {
        return this.in.readInt();
    }
    
    @Deprecated
    @Override
    public final String readLine() throws IOException {
        return this.in.readLine();
    }
    
    @Override
    public final long readLong() throws IOException {
        return this.in.readLong();
    }
    
    @Override
    public final short readShort() throws IOException {
        return this.in.readShort();
    }
    
    @Override
    public final int readUnsignedByte() throws IOException {
        return this.in.readUnsignedByte();
    }
    
    @Override
    public final int readUnsignedShort() throws IOException {
        return this.in.readUnsignedShort();
    }
    
    @Override
    public final String readUTF() throws IOException {
        return this.in.readUTF();
    }
    
    @Override
    public void reset() throws IOException {
        this.in.reset();
    }
    
    @Override
    public long skip(final long n) throws IOException {
        return this.in.skip(n);
    }
    
    @Override
    public final int skipBytes(final int n) throws IOException {
        return this.in.skipBytes(n);
    }
}
