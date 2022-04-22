package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import java.nio.*;
import io.netty.util.internal.*;
import io.netty.buffer.*;
import java.io.*;
import io.netty.handler.codec.http.*;
import java.nio.channels.*;
import io.netty.util.internal.logging.*;

public abstract class AbstractDiskHttpData extends AbstractHttpData
{
    private static final InternalLogger logger;
    protected File file;
    private boolean isRenamed;
    private FileChannel fileChannel;
    
    protected AbstractDiskHttpData(final String s, final Charset charset, final long n) {
        super(s, charset, n);
    }
    
    protected abstract String getDiskFilename();
    
    protected abstract String getPrefix();
    
    protected abstract String getBaseDirectory();
    
    protected abstract String getPostfix();
    
    protected abstract boolean deleteOnExit();
    
    private File tempFile() throws IOException {
        final String diskFilename = this.getDiskFilename();
        String s;
        if (diskFilename != null) {
            s = '_' + diskFilename;
        }
        else {
            s = this.getPostfix();
        }
        File file;
        if (this.getBaseDirectory() == null) {
            file = File.createTempFile(this.getPrefix(), s);
        }
        else {
            file = File.createTempFile(this.getPrefix(), s, new File(this.getBaseDirectory()));
        }
        if (this.deleteOnExit()) {
            file.deleteOnExit();
        }
        return file;
    }
    
    @Override
    public void setContent(final ByteBuf byteBuf) throws IOException {
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        this.size = byteBuf.readableBytes();
        if (this.definedSize > 0L && this.definedSize < this.size) {
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        if (this.file == null) {
            this.file = this.tempFile();
        }
        if (byteBuf.readableBytes() == 0) {
            this.file.createNewFile();
            byteBuf.release();
            return;
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        final FileChannel channel = fileOutputStream.getChannel();
        final ByteBuffer nioBuffer = byteBuf.nioBuffer();
        while (0 < this.size) {
            final int n = 0 + channel.write(nioBuffer);
        }
        byteBuf.readerIndex(byteBuf.readerIndex() + 0);
        channel.force(false);
        channel.close();
        fileOutputStream.close();
        this.completed = true;
        byteBuf.release();
    }
    
    @Override
    public void addContent(final ByteBuf byteBuf, final boolean b) throws IOException {
        if (byteBuf != null) {
            final int readableBytes = byteBuf.readableBytes();
            if (this.definedSize > 0L && this.definedSize < this.size + readableBytes) {
                throw new IOException("Out of size: " + (this.size + readableBytes) + " > " + this.definedSize);
            }
            final ByteBuffer byteBuffer = (byteBuf.nioBufferCount() == 1) ? byteBuf.nioBuffer() : byteBuf.copy().nioBuffer();
            if (this.file == null) {
                this.file = this.tempFile();
            }
            if (this.fileChannel == null) {
                this.fileChannel = new FileOutputStream(this.file).getChannel();
            }
            while (0 < readableBytes) {
                final int n = 0 + this.fileChannel.write(byteBuffer);
            }
            this.size += readableBytes;
            byteBuf.readerIndex(byteBuf.readerIndex() + 0);
            byteBuf.release();
        }
        if (b) {
            if (this.file == null) {
                this.file = this.tempFile();
            }
            if (this.fileChannel == null) {
                this.fileChannel = new FileOutputStream(this.file).getChannel();
            }
            this.fileChannel.force(false);
            this.fileChannel.close();
            this.fileChannel = null;
            this.completed = true;
        }
        else if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
    }
    
    @Override
    public void setContent(final File file) throws IOException {
        if (this.file != null) {
            this.delete();
        }
        this.file = file;
        this.size = file.length();
        this.isRenamed = true;
        this.completed = true;
    }
    
    @Override
    public void setContent(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        if (this.file != null) {
            this.delete();
        }
        this.file = this.tempFile();
        final FileChannel channel = new FileOutputStream(this.file).getChannel();
        final byte[] array = new byte[16384];
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        for (int i = inputStream.read(array); i > 0; i = inputStream.read(array)) {
            wrap.position(i).flip();
            final int n = 0 + channel.write(wrap);
        }
        channel.force(false);
        channel.close();
        this.size = 0;
        if (this.definedSize > 0L && this.definedSize < this.size) {
            this.file.delete();
            this.file = null;
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        }
        this.isRenamed = true;
        this.completed = true;
    }
    
    @Override
    public void delete() {
        if (this.fileChannel != null) {
            this.fileChannel.force(false);
            this.fileChannel.close();
            this.fileChannel = null;
        }
        if (!this.isRenamed) {
            if (this.file != null && this.file.exists()) {
                this.file.delete();
            }
            this.file = null;
        }
    }
    
    @Override
    public byte[] get() throws IOException {
        if (this.file == null) {
            return EmptyArrays.EMPTY_BYTES;
        }
        return readFrom(this.file);
    }
    
    @Override
    public ByteBuf getByteBuf() throws IOException {
        if (this.file == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.wrappedBuffer(readFrom(this.file));
    }
    
    @Override
    public ByteBuf getChunk(final int n) throws IOException {
        if (this.file == null || n == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (this.fileChannel == null) {
            this.fileChannel = new FileInputStream(this.file).getChannel();
        }
        final ByteBuffer allocate = ByteBuffer.allocate(n);
        while (0 < n) {
            if (this.fileChannel.read(allocate) == -1) {
                this.fileChannel.close();
                this.fileChannel = null;
                break;
            }
        }
        if (!false) {
            return Unpooled.EMPTY_BUFFER;
        }
        allocate.flip();
        final ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(allocate);
        wrappedBuffer.readerIndex(0);
        wrappedBuffer.writerIndex(0);
        return wrappedBuffer;
    }
    
    @Override
    public String getString() throws IOException {
        return this.getString(HttpConstants.DEFAULT_CHARSET);
    }
    
    @Override
    public String getString(final Charset charset) throws IOException {
        if (this.file == null) {
            return "";
        }
        if (charset == null) {
            return new String(readFrom(this.file), HttpConstants.DEFAULT_CHARSET.name());
        }
        return new String(readFrom(this.file), charset.name());
    }
    
    @Override
    public boolean isInMemory() {
        return false;
    }
    
    @Override
    public boolean renameTo(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("dest");
        }
        if (this.file == null) {
            throw new IOException("No file defined so cannot be renamed");
        }
        if (this.file.renameTo(file)) {
            this.file = file;
            return this.isRenamed = true;
        }
        final FileInputStream fileInputStream = new FileInputStream(this.file);
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel;
        FileChannel channel2;
        long n;
        int n2;
        for (channel = fileInputStream.getChannel(), channel2 = fileOutputStream.getChannel(), n = 0L; n < this.size; n += channel.transferTo(n, 8196, channel2)) {
            if (8196 < this.size - n) {
                n2 = (int)(this.size - n);
            }
        }
        channel.close();
        channel2.close();
        if (n == this.size) {
            this.file.delete();
            this.file = file;
            return this.isRenamed = true;
        }
        file.delete();
        return false;
    }
    
    private static byte[] readFrom(final File file) throws IOException {
        final long length = file.length();
        if (length > 2147483647L) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        final FileChannel channel = new FileInputStream(file).getChannel();
        final byte[] array = new byte[(int)length];
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        while (0 < length) {
            final int n = 0 + channel.read(wrap);
        }
        channel.close();
        return array;
    }
    
    @Override
    public File getFile() throws IOException {
        return this.file;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
    }
}
