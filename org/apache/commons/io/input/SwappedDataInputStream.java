package org.apache.commons.io.input;

import java.io.*;
import org.apache.commons.io.*;

public class SwappedDataInputStream extends ProxyInputStream implements DataInput
{
    public SwappedDataInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public boolean readBoolean() throws IOException, EOFException {
        return 0 != this.readByte();
    }
    
    @Override
    public byte readByte() throws IOException, EOFException {
        return (byte)this.in.read();
    }
    
    @Override
    public char readChar() throws IOException, EOFException {
        return (char)this.readShort();
    }
    
    @Override
    public double readDouble() throws IOException, EOFException {
        return EndianUtils.readSwappedDouble(this.in);
    }
    
    @Override
    public float readFloat() throws IOException, EOFException {
        return EndianUtils.readSwappedFloat(this.in);
    }
    
    @Override
    public void readFully(final byte[] array) throws IOException, EOFException {
        this.readFully(array, 0, array.length);
    }
    
    @Override
    public void readFully(final byte[] array, final int n, final int n2) throws IOException, EOFException {
        int read;
        for (int i = n2; i > 0; i -= read) {
            read = this.read(array, n + n2 - i, i);
            if (-1 == read) {
                throw new EOFException();
            }
        }
    }
    
    @Override
    public int readInt() throws IOException, EOFException {
        return EndianUtils.readSwappedInteger(this.in);
    }
    
    @Override
    public String readLine() throws IOException, EOFException {
        throw new UnsupportedOperationException("Operation not supported: readLine()");
    }
    
    @Override
    public long readLong() throws IOException, EOFException {
        return EndianUtils.readSwappedLong(this.in);
    }
    
    @Override
    public short readShort() throws IOException, EOFException {
        return EndianUtils.readSwappedShort(this.in);
    }
    
    @Override
    public int readUnsignedByte() throws IOException, EOFException {
        return this.in.read();
    }
    
    @Override
    public int readUnsignedShort() throws IOException, EOFException {
        return EndianUtils.readSwappedUnsignedShort(this.in);
    }
    
    @Override
    public String readUTF() throws IOException, EOFException {
        throw new UnsupportedOperationException("Operation not supported: readUTF()");
    }
    
    @Override
    public int skipBytes(final int n) throws IOException, EOFException {
        return (int)this.in.skip(n);
    }
}
