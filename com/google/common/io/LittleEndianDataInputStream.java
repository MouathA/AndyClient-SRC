package com.google.common.io;

import com.google.common.annotations.*;
import com.google.common.base.*;
import com.google.common.primitives.*;
import java.io.*;

@Beta
public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput
{
    public LittleEndianDataInputStream(final InputStream inputStream) {
        super((InputStream)Preconditions.checkNotNull(inputStream));
    }
    
    @Override
    public String readLine() {
        throw new UnsupportedOperationException("readLine is not supported");
    }
    
    @Override
    public void readFully(final byte[] array) throws IOException {
        ByteStreams.readFully(this, array);
    }
    
    @Override
    public void readFully(final byte[] array, final int n, final int n2) throws IOException {
        ByteStreams.readFully(this, array, n, n2);
    }
    
    @Override
    public int skipBytes(final int n) throws IOException {
        return (int)this.in.skip(n);
    }
    
    @Override
    public int readUnsignedByte() throws IOException {
        final int read = this.in.read();
        if (0 > read) {
            throw new EOFException();
        }
        return read;
    }
    
    @Override
    public int readUnsignedShort() throws IOException {
        return Ints.fromBytes((byte)0, (byte)0, this.readAndCheckByte(), this.readAndCheckByte());
    }
    
    @Override
    public int readInt() throws IOException {
        return Ints.fromBytes(this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte());
    }
    
    @Override
    public long readLong() throws IOException {
        return Longs.fromBytes(this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte(), this.readAndCheckByte());
    }
    
    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public String readUTF() throws IOException {
        return new DataInputStream(this.in).readUTF();
    }
    
    @Override
    public short readShort() throws IOException {
        return (short)this.readUnsignedShort();
    }
    
    @Override
    public char readChar() throws IOException {
        return (char)this.readUnsignedShort();
    }
    
    @Override
    public byte readByte() throws IOException {
        return (byte)this.readUnsignedByte();
    }
    
    @Override
    public boolean readBoolean() throws IOException {
        return this.readUnsignedByte() != 0;
    }
    
    private byte readAndCheckByte() throws IOException, EOFException {
        final int read = this.in.read();
        if (-1 == read) {
            throw new EOFException();
        }
        return (byte)read;
    }
}
