package io.netty.handler.stream;

import java.io.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class ChunkedStream implements ChunkedInput
{
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final PushbackInputStream in;
    private final int chunkSize;
    private long offset;
    
    public ChunkedStream(final InputStream inputStream) {
        this(inputStream, 8192);
    }
    
    public ChunkedStream(final InputStream inputStream, final int chunkSize) {
        if (inputStream == null) {
            throw new NullPointerException("in");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        if (inputStream instanceof PushbackInputStream) {
            this.in = (PushbackInputStream)inputStream;
        }
        else {
            this.in = new PushbackInputStream(inputStream);
        }
        this.chunkSize = chunkSize;
    }
    
    public long transferredBytes() {
        return this.offset;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        final int read = this.in.read();
        if (read < 0) {
            return true;
        }
        this.in.unread(read);
        return false;
    }
    
    @Override
    public void close() throws Exception {
        this.in.close();
    }
    
    @Override
    public ByteBuf readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.isEndOfInput()) {
            return null;
        }
        int n;
        if (this.in.available() <= 0) {
            n = this.chunkSize;
        }
        else {
            n = Math.min(this.chunkSize, this.in.available());
        }
        final ByteBuf buffer = channelHandlerContext.alloc().buffer(n);
        this.offset += buffer.writeBytes(this.in, n);
        final ByteBuf byteBuf = buffer;
        if (false) {
            buffer.release();
        }
        return byteBuf;
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}
