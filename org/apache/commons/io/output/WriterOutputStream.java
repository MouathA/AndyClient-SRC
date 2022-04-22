package org.apache.commons.io.output;

import java.nio.*;
import java.io.*;
import java.nio.charset.*;

public class WriterOutputStream extends OutputStream
{
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Writer writer;
    private final CharsetDecoder decoder;
    private final boolean writeImmediately;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    
    public WriterOutputStream(final Writer writer, final CharsetDecoder charsetDecoder) {
        this(writer, charsetDecoder, 1024, false);
    }
    
    public WriterOutputStream(final Writer writer, final CharsetDecoder decoder, final int n, final boolean writeImmediately) {
        this.decoderIn = ByteBuffer.allocate(128);
        this.writer = writer;
        this.decoder = decoder;
        this.writeImmediately = writeImmediately;
        this.decoderOut = CharBuffer.allocate(n);
    }
    
    public WriterOutputStream(final Writer writer, final Charset charset, final int n, final boolean b) {
        this(writer, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), n, b);
    }
    
    public WriterOutputStream(final Writer writer, final Charset charset) {
        this(writer, charset, 1024, false);
    }
    
    public WriterOutputStream(final Writer writer, final String s, final int n, final boolean b) {
        this(writer, Charset.forName(s), n, b);
    }
    
    public WriterOutputStream(final Writer writer, final String s) {
        this(writer, s, 1024, false);
    }
    
    public WriterOutputStream(final Writer writer) {
        this(writer, Charset.defaultCharset(), 1024, false);
    }
    
    @Override
    public void write(final byte[] array, int n, int i) throws IOException {
        while (i > 0) {
            final int min = Math.min(i, this.decoderIn.remaining());
            this.decoderIn.put(array, n, min);
            this.processInput(false);
            i -= min;
            n += min;
        }
        if (this.writeImmediately) {
            this.flushOutput();
        }
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.write(new byte[] { (byte)n }, 0, 1);
    }
    
    @Override
    public void flush() throws IOException {
        this.flushOutput();
        this.writer.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.processInput(true);
        this.flushOutput();
        this.writer.close();
    }
    
    private void processInput(final boolean b) throws IOException {
        this.decoderIn.flip();
        CoderResult decode;
        while (true) {
            decode = this.decoder.decode(this.decoderIn, this.decoderOut, b);
            if (!decode.isOverflow()) {
                break;
            }
            this.flushOutput();
        }
        if (decode.isUnderflow()) {
            this.decoderIn.compact();
            return;
        }
        throw new IOException("Unexpected coder result");
    }
    
    private void flushOutput() throws IOException {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }
}
