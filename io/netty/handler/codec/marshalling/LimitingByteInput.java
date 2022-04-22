package io.netty.handler.codec.marshalling;

import org.jboss.marshalling.*;
import java.io.*;

class LimitingByteInput implements ByteInput
{
    private static final TooBigObjectException EXCEPTION;
    private final ByteInput input;
    private final long limit;
    private long read;
    
    LimitingByteInput(final ByteInput input, final long limit) {
        if (limit <= 0L) {
            throw new IllegalArgumentException("The limit MUST be > 0");
        }
        this.input = input;
        this.limit = limit;
    }
    
    public void close() throws IOException {
    }
    
    public int available() throws IOException {
        return this.readable(this.input.available());
    }
    
    public int read() throws IOException {
        if (this.readable(1) > 0) {
            final int read = this.input.read();
            ++this.read;
            return read;
        }
        throw LimitingByteInput.EXCEPTION;
    }
    
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        final int readable = this.readable(n2);
        if (readable > 0) {
            final int read = this.input.read(array, n, readable);
            this.read += read;
            return read;
        }
        throw LimitingByteInput.EXCEPTION;
    }
    
    public long skip(final long n) throws IOException {
        final int readable = this.readable((int)n);
        if (readable > 0) {
            final long skip = this.input.skip((long)readable);
            this.read += skip;
            return skip;
        }
        throw LimitingByteInput.EXCEPTION;
    }
    
    private int readable(final int n) {
        return (int)Math.min(n, this.limit - this.read);
    }
    
    static {
        EXCEPTION = new TooBigObjectException();
    }
    
    static final class TooBigObjectException extends IOException
    {
        private static final long serialVersionUID = 1L;
    }
}
