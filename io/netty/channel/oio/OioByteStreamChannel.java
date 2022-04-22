package io.netty.channel.oio;

import io.netty.buffer.*;
import io.netty.channel.*;
import java.io.*;
import java.nio.channels.*;

public abstract class OioByteStreamChannel extends AbstractOioByteChannel
{
    private static final InputStream CLOSED_IN;
    private static final OutputStream CLOSED_OUT;
    private InputStream is;
    private OutputStream os;
    private WritableByteChannel outChannel;
    
    protected OioByteStreamChannel(final Channel channel) {
        super(channel);
    }
    
    protected final void activate(final InputStream is, final OutputStream os) {
        if (this.is != null) {
            throw new IllegalStateException("input was set already");
        }
        if (this.os != null) {
            throw new IllegalStateException("output was set already");
        }
        if (is == null) {
            throw new NullPointerException("is");
        }
        if (os == null) {
            throw new NullPointerException("os");
        }
        this.is = is;
        this.os = os;
    }
    
    @Override
    public boolean isActive() {
        final InputStream is = this.is;
        if (is == null || is == OioByteStreamChannel.CLOSED_IN) {
            return false;
        }
        final OutputStream os = this.os;
        return os != null && os != OioByteStreamChannel.CLOSED_OUT;
    }
    
    @Override
    protected int available() {
        return this.is.available();
    }
    
    @Override
    protected int doReadBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.writeBytes(this.is, Math.max(1, Math.min(this.available(), byteBuf.maxWritableBytes())));
    }
    
    @Override
    protected void doWriteBytes(final ByteBuf byteBuf) throws Exception {
        final OutputStream os = this.os;
        if (os == null) {
            throw new NotYetConnectedException();
        }
        byteBuf.readBytes(os, byteBuf.readableBytes());
    }
    
    @Override
    protected void doWriteFileRegion(final FileRegion fileRegion) throws Exception {
        final OutputStream os = this.os;
        if (os == null) {
            throw new NotYetConnectedException();
        }
        if (this.outChannel == null) {
            this.outChannel = Channels.newChannel(os);
        }
        long n = 0L;
        while (true) {
            final long transferTo = fileRegion.transferTo(this.outChannel, n);
            if (transferTo == -1L) {
                checkEOF(fileRegion);
                return;
            }
            n += transferTo;
            if (n >= fileRegion.count()) {
                return;
            }
        }
    }
    
    private static void checkEOF(final FileRegion fileRegion) throws IOException {
        if (fileRegion.transfered() < fileRegion.count()) {
            throw new EOFException("Expected to be able to write " + fileRegion.count() + " bytes, " + "but only wrote " + fileRegion.transfered());
        }
    }
    
    @Override
    protected void doClose() throws Exception {
        final InputStream is = this.is;
        final OutputStream os = this.os;
        this.is = OioByteStreamChannel.CLOSED_IN;
        this.os = OioByteStreamChannel.CLOSED_OUT;
        if (is != null) {
            is.close();
        }
        if (os != null) {
            os.close();
        }
    }
    
    static {
        CLOSED_IN = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };
        CLOSED_OUT = new OutputStream() {
            @Override
            public void write(final int n) throws IOException {
                throw new ClosedChannelException();
            }
        };
    }
}
