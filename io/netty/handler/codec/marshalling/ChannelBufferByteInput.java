package io.netty.handler.codec.marshalling;

import org.jboss.marshalling.*;
import io.netty.buffer.*;
import java.io.*;

class ChannelBufferByteInput implements ByteInput
{
    private final ByteBuf buffer;
    
    ChannelBufferByteInput(final ByteBuf buffer) {
        this.buffer = buffer;
    }
    
    public void close() throws IOException {
    }
    
    public int available() throws IOException {
        return this.buffer.readableBytes();
    }
    
    public int read() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte() & 0xFF;
        }
        return -1;
    }
    
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, int min) throws IOException {
        final int available = this.available();
        if (available == 0) {
            return -1;
        }
        min = Math.min(available, min);
        this.buffer.readBytes(array, n, min);
        return min;
    }
    
    public long skip(long n) throws IOException {
        final int readableBytes = this.buffer.readableBytes();
        if (readableBytes < n) {
            n = readableBytes;
        }
        this.buffer.readerIndex((int)(this.buffer.readerIndex() + n));
        return n;
    }
}
