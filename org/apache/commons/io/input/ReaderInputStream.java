package org.apache.commons.io.input;

import java.nio.*;
import java.nio.charset.*;
import java.io.*;

public class ReaderInputStream extends InputStream
{
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Reader reader;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private CoderResult lastCoderResult;
    private boolean endOfInput;
    
    public ReaderInputStream(final Reader reader, final CharsetEncoder charsetEncoder) {
        this(reader, charsetEncoder, 1024);
    }
    
    public ReaderInputStream(final Reader reader, final CharsetEncoder encoder, final int n) {
        this.reader = reader;
        this.encoder = encoder;
        (this.encoderIn = CharBuffer.allocate(n)).flip();
        (this.encoderOut = ByteBuffer.allocate(128)).flip();
    }
    
    public ReaderInputStream(final Reader reader, final Charset charset, final int n) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), n);
    }
    
    public ReaderInputStream(final Reader reader, final Charset charset) {
        this(reader, charset, 1024);
    }
    
    public ReaderInputStream(final Reader reader, final String s, final int n) {
        this(reader, Charset.forName(s), n);
    }
    
    public ReaderInputStream(final Reader reader, final String s) {
        this(reader, s, 1024);
    }
    
    public ReaderInputStream(final Reader reader) {
        this(reader, Charset.defaultCharset());
    }
    
    private void fillBuffer() throws IOException {
        if (!this.endOfInput && (this.lastCoderResult == null || this.lastCoderResult.isUnderflow())) {
            this.encoderIn.compact();
            final int position = this.encoderIn.position();
            final int read = this.reader.read(this.encoderIn.array(), position, this.encoderIn.remaining());
            if (read == -1) {
                this.endOfInput = true;
            }
            else {
                this.encoderIn.position(position + read);
            }
            this.encoderIn.flip();
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        this.encoderOut.flip();
    }
    
    @Override
    public int read(final byte[] array, int n, int i) throws IOException {
        if (array == null) {
            throw new NullPointerException("Byte array must not be null");
        }
        if (i < 0 || n < 0 || n + i > array.length) {
            throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + n + ", length=" + i);
        }
        if (i == 0) {
            return 0;
        }
        while (i > 0) {
            if (this.encoderOut.hasRemaining()) {
                final int min = Math.min(this.encoderOut.remaining(), i);
                this.encoderOut.get(array, n, min);
                n += min;
                i -= min;
            }
            else {
                this.fillBuffer();
                if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                    break;
                }
                continue;
            }
        }
        return (!false && this.endOfInput) ? -1 : 0;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read() throws IOException {
        while (!this.encoderOut.hasRemaining()) {
            this.fillBuffer();
            if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                return -1;
            }
        }
        return this.encoderOut.get() & 0xFF;
    }
    
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
