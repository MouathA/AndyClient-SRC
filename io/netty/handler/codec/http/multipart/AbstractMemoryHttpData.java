package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import io.netty.buffer.*;
import java.nio.*;
import java.nio.channels.*;
import io.netty.handler.codec.http.*;
import java.io.*;

public abstract class AbstractMemoryHttpData extends AbstractHttpData
{
    private ByteBuf byteBuf;
    private int chunkPosition;
    protected boolean isRenamed;
    
    protected AbstractMemoryHttpData(final String s, final Charset charset, final long n) {
        super(s, charset, n);
    }
    
    @Override
    public void setContent(final ByteBuf byteBuf) throws IOException {
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        final long size = byteBuf.readableBytes();
        if (this.definedSize > 0L && this.definedSize < size) {
            throw new IOException("Out of size: " + size + " > " + this.definedSize);
        }
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = byteBuf;
        this.size = size;
        this.completed = true;
    }
    
    @Override
    public void setContent(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        final ByteBuf buffer = Unpooled.buffer();
        final byte[] array = new byte[16384];
        for (int i = inputStream.read(array); i > 0; i = inputStream.read(array)) {
            buffer.writeBytes(array, 0, i);
        }
        this.size = 0;
        if (this.definedSize > 0L && this.definedSize < this.size) {
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = buffer;
        this.completed = true;
    }
    
    @Override
    public void addContent(final ByteBuf byteBuf, final boolean b) throws IOException {
        if (byteBuf != null) {
            final long n = byteBuf.readableBytes();
            if (this.definedSize > 0L && this.definedSize < this.size + n) {
                throw new IOException("Out of size: " + (this.size + n) + " > " + this.definedSize);
            }
            this.size += n;
            if (this.byteBuf == null) {
                this.byteBuf = byteBuf;
            }
            else if (this.byteBuf instanceof CompositeByteBuf) {
                final CompositeByteBuf compositeByteBuf = (CompositeByteBuf)this.byteBuf;
                compositeByteBuf.addComponent(byteBuf);
                compositeByteBuf.writerIndex(compositeByteBuf.writerIndex() + byteBuf.readableBytes());
            }
            else {
                final CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer(Integer.MAX_VALUE);
                compositeBuffer.addComponents(this.byteBuf, byteBuf);
                compositeBuffer.writerIndex(this.byteBuf.readableBytes() + byteBuf.readableBytes());
                this.byteBuf = compositeBuffer;
            }
        }
        if (b) {
            this.completed = true;
        }
        else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }
    
    @Override
    public void setContent(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
        }
        final long length = file.length();
        if (length > 2147483647L) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final FileChannel channel = fileInputStream.getChannel();
        final ByteBuffer wrap = ByteBuffer.wrap(new byte[(int)length]);
        while (0 < length) {
            final int n = 0 + channel.read(wrap);
        }
        channel.close();
        fileInputStream.close();
        wrap.flip();
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = Unpooled.wrappedBuffer(Integer.MAX_VALUE, wrap);
        this.size = length;
        this.completed = true;
    }
    
    @Override
    public void delete() {
        if (this.byteBuf != null) {
            this.byteBuf.release();
            this.byteBuf = null;
        }
    }
    
    @Override
    public byte[] get() {
        if (this.byteBuf == null) {
            return Unpooled.EMPTY_BUFFER.array();
        }
        final byte[] array = new byte[this.byteBuf.readableBytes()];
        this.byteBuf.getBytes(this.byteBuf.readerIndex(), array);
        return array;
    }
    
    @Override
    public String getString() {
        return this.getString(HttpConstants.DEFAULT_CHARSET);
    }
    
    @Override
    public String getString(Charset default_CHARSET) {
        if (this.byteBuf == null) {
            return "";
        }
        if (default_CHARSET == null) {
            default_CHARSET = HttpConstants.DEFAULT_CHARSET;
        }
        return this.byteBuf.toString(default_CHARSET);
    }
    
    @Override
    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }
    
    @Override
    public ByteBuf getChunk(final int n) throws IOException {
        if (this.byteBuf == null || n == 0 || this.byteBuf.readableBytes() == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        final int n2 = this.byteBuf.readableBytes() - this.chunkPosition;
        if (n2 == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int n3;
        if (n2 < (n3 = n)) {
            n3 = n2;
        }
        final ByteBuf retain = this.byteBuf.slice(this.chunkPosition, n3).retain();
        this.chunkPosition += n3;
        return retain;
    }
    
    @Override
    public boolean isInMemory() {
        return true;
    }
    
    @Override
    public boolean renameTo(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("dest");
        }
        if (this.byteBuf == null) {
            file.createNewFile();
            return this.isRenamed = true;
        }
        final int readableBytes = this.byteBuf.readableBytes();
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        final FileChannel channel = fileOutputStream.getChannel();
        if (this.byteBuf.nioBufferCount() == 1) {
            final ByteBuffer nioBuffer = this.byteBuf.nioBuffer();
            while (0 < readableBytes) {
                final int n = 0 + channel.write(nioBuffer);
            }
        }
        else {
            final ByteBuffer[] nioBuffers = this.byteBuf.nioBuffers();
            while (0 < readableBytes) {
                final int n2 = (int)(0 + channel.write(nioBuffers));
            }
        }
        channel.force(false);
        channel.close();
        fileOutputStream.close();
        this.isRenamed = true;
        return readableBytes == 0;
    }
    
    @Override
    public File getFile() throws IOException {
        throw new IOException("Not represented by a file");
    }
}
