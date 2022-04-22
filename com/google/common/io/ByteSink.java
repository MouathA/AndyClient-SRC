package com.google.common.io;

import java.nio.charset.*;
import com.google.common.base.*;
import java.io.*;

public abstract class ByteSink implements OutputSupplier
{
    protected ByteSink() {
    }
    
    public CharSink asCharSink(final Charset charset) {
        return new AsCharSink(charset, null);
    }
    
    public abstract OutputStream openStream() throws IOException;
    
    @Deprecated
    @Override
    public final OutputStream getOutput() throws IOException {
        return this.openStream();
    }
    
    public OutputStream openBufferedStream() throws IOException {
        final OutputStream openStream = this.openStream();
        return (openStream instanceof BufferedOutputStream) ? openStream : new BufferedOutputStream(openStream);
    }
    
    public void write(final byte[] array) throws IOException {
        Preconditions.checkNotNull(array);
        final Closer create = Closer.create();
        final OutputStream outputStream = (OutputStream)create.register(this.openStream());
        outputStream.write(array);
        outputStream.flush();
        create.close();
    }
    
    public long writeFrom(final InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        final Closer create = Closer.create();
        final OutputStream outputStream = (OutputStream)create.register(this.openStream());
        final long copy = ByteStreams.copy(inputStream, outputStream);
        outputStream.flush();
        final long n = copy;
        create.close();
        return n;
    }
    
    @Override
    public Object getOutput() throws IOException {
        return this.getOutput();
    }
    
    private final class AsCharSink extends CharSink
    {
        private final Charset charset;
        final ByteSink this$0;
        
        private AsCharSink(final ByteSink this$0, final Charset charset) {
            this.this$0 = this$0;
            this.charset = (Charset)Preconditions.checkNotNull(charset);
        }
        
        @Override
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(this.this$0.openStream(), this.charset);
        }
        
        @Override
        public String toString() {
            return this.this$0.toString() + ".asCharSink(" + this.charset + ")";
        }
        
        AsCharSink(final ByteSink byteSink, final Charset charset, final ByteSink$1 object) {
            this(byteSink, charset);
        }
    }
}
