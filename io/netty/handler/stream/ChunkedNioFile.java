package io.netty.handler.stream;

import java.io.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.nio.channels.*;

public class ChunkedNioFile implements ChunkedInput
{
    private final FileChannel in;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;
    
    public ChunkedNioFile(final File file) throws IOException {
        this(new FileInputStream(file).getChannel());
    }
    
    public ChunkedNioFile(final File file, final int n) throws IOException {
        this(new FileInputStream(file).getChannel(), n);
    }
    
    public ChunkedNioFile(final FileChannel fileChannel) throws IOException {
        this(fileChannel, 8192);
    }
    
    public ChunkedNioFile(final FileChannel fileChannel, final int n) throws IOException {
        this(fileChannel, 0L, fileChannel.size(), n);
    }
    
    public ChunkedNioFile(final FileChannel in, final long n, final long n2, final int chunkSize) throws IOException {
        if (in == null) {
            throw new NullPointerException("in");
        }
        if (n < 0L) {
            throw new IllegalArgumentException("offset: " + n + " (expected: 0 or greater)");
        }
        if (n2 < 0L) {
            throw new IllegalArgumentException("length: " + n2 + " (expected: 0 or greater)");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        if (n != 0L) {
            in.position(n);
        }
        this.in = in;
        this.chunkSize = chunkSize;
        this.startOffset = n;
        this.offset = n;
        this.endOffset = n + n2;
    }
    
    public long startOffset() {
        return this.startOffset;
    }
    
    public long endOffset() {
        return this.endOffset;
    }
    
    public long currentOffset() {
        return this.offset;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        return this.offset >= this.endOffset || !this.in.isOpen();
    }
    
    @Override
    public void close() throws Exception {
        this.in.close();
    }
    
    @Override
    public ByteBuf readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final long offset = this.offset;
        if (offset >= this.endOffset) {
            return null;
        }
        final int n = (int)Math.min(this.chunkSize, this.endOffset - offset);
        final ByteBuf buffer = channelHandlerContext.alloc().buffer(n);
        while (true) {
            while (buffer.writeBytes(this.in, n - 0) >= 0) {
                if (n == 0) {
                    this.offset += 0;
                    final ByteBuf byteBuf = buffer;
                    if (false) {
                        buffer.release();
                    }
                    return byteBuf;
                }
            }
            continue;
        }
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}
