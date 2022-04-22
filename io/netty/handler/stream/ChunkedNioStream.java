package io.netty.handler.stream;

import java.nio.channels.*;
import java.nio.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class ChunkedNioStream implements ChunkedInput
{
    private final ReadableByteChannel in;
    private final int chunkSize;
    private long offset;
    private final ByteBuffer byteBuffer;
    
    public ChunkedNioStream(final ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, 8192);
    }
    
    public ChunkedNioStream(final ReadableByteChannel in, final int chunkSize) {
        if (in == null) {
            throw new NullPointerException("in");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        this.in = in;
        this.offset = 0L;
        this.chunkSize = chunkSize;
        this.byteBuffer = ByteBuffer.allocate(chunkSize);
    }
    
    public long transferredBytes() {
        return this.offset;
    }
    
    @Override
    public void close() throws Exception {
        this.in.close();
    }
    
    @Override
    public ByteBuf readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this > 0) {
            return null;
        }
        int position = this.byteBuffer.position();
        do {
            this.in.read(this.byteBuffer);
            position += 0;
            this.offset += 0;
        } while (position != this.chunkSize);
        this.byteBuffer.flip();
        final ByteBuf buffer = channelHandlerContext.alloc().buffer(this.byteBuffer.remaining());
        buffer.writeBytes(this.byteBuffer);
        this.byteBuffer.clear();
        return buffer;
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}
