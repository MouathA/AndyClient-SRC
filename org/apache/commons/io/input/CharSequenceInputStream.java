package org.apache.commons.io.input;

import java.nio.*;
import java.nio.charset.*;
import java.io.*;

public class CharSequenceInputStream extends InputStream
{
    private final CharsetEncoder encoder;
    private final CharBuffer cbuf;
    private final ByteBuffer bbuf;
    private int mark;
    
    public CharSequenceInputStream(final CharSequence charSequence, final Charset charset, final int n) {
        this.encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        (this.bbuf = ByteBuffer.allocate(n)).flip();
        this.cbuf = CharBuffer.wrap(charSequence);
        this.mark = -1;
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final String s, final int n) {
        this(charSequence, Charset.forName(s), n);
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final Charset charset) {
        this(charSequence, charset, 2048);
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final String s) {
        this(charSequence, s, 2048);
    }
    
    private void fillBuffer() throws CharacterCodingException {
        this.bbuf.compact();
        final CoderResult encode = this.encoder.encode(this.cbuf, this.bbuf, true);
        if (encode.isError()) {
            encode.throwException();
        }
        this.bbuf.flip();
    }
    
    @Override
    public int read(final byte[] array, int n, int i) throws IOException {
        if (array == null) {
            throw new NullPointerException("Byte array is null");
        }
        if (i < 0 || n + i > array.length) {
            throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + n + ", length=" + i);
        }
        if (i == 0) {
            return 0;
        }
        if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
            return -1;
        }
        while (i > 0) {
            if (this.bbuf.hasRemaining()) {
                final int min = Math.min(this.bbuf.remaining(), i);
                this.bbuf.get(array, n, min);
                n += min;
                i -= min;
            }
            else {
                this.fillBuffer();
                if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                    break;
                }
                continue;
            }
        }
        return (!false && !this.cbuf.hasRemaining()) ? -1 : 0;
    }
    
    @Override
    public int read() throws IOException {
        while (!this.bbuf.hasRemaining()) {
            this.fillBuffer();
            if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                return -1;
            }
        }
        return this.bbuf.get() & 0xFF;
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public long skip(long n) throws IOException {
        while (n > 0L && this.cbuf.hasRemaining()) {
            this.cbuf.get();
            --n;
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public int available() throws IOException {
        return this.cbuf.remaining();
    }
    
    @Override
    public void close() throws IOException {
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.mark = this.cbuf.position();
    }
    
    @Override
    public synchronized void reset() throws IOException {
        if (this.mark != -1) {
            this.cbuf.position(this.mark);
            this.mark = -1;
        }
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
}
