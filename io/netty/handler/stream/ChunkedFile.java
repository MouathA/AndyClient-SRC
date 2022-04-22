package io.netty.handler.stream;

import java.io.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class ChunkedFile implements ChunkedInput
{
    private final RandomAccessFile file;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;
    
    public ChunkedFile(final File file) throws IOException {
        this(file, 8192);
    }
    
    public ChunkedFile(final File file, final int n) throws IOException {
        this(new RandomAccessFile(file, "r"), n);
    }
    
    public ChunkedFile(final RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 8192);
    }
    
    public ChunkedFile(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        this(randomAccessFile, 0L, randomAccessFile.length(), n);
    }
    
    public ChunkedFile(final RandomAccessFile file, final long n, final long n2, final int chunkSize) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
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
        this.file = file;
        this.startOffset = n;
        this.offset = n;
        this.endOffset = n + n2;
        this.chunkSize = chunkSize;
        file.seek(n);
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
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }
    
    @Override
    public void close() throws Exception {
        this.file.close();
    }
    
    @Override
    public ByteBuf readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final long offset = this.offset;
        if (offset >= this.endOffset) {
            return null;
        }
        final int n = (int)Math.min(this.chunkSize, this.endOffset - offset);
        final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(n);
        this.file.readFully(heapBuffer.array(), heapBuffer.arrayOffset(), n);
        heapBuffer.writerIndex(n);
        this.offset = offset + n;
        final ByteBuf byteBuf = heapBuffer;
        if (false) {
            heapBuffer.release();
        }
        return byteBuf;
    }
    
    @Override
    public Object readChunk(final ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}
