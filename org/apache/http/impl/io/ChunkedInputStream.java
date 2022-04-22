package org.apache.http.impl.io;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.io.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.message.*;

@NotThreadSafe
public class ChunkedInputStream extends InputStream
{
    private static final int CHUNK_LEN = 1;
    private static final int CHUNK_DATA = 2;
    private static final int CHUNK_CRLF = 3;
    private static final int BUFFER_SIZE = 2048;
    private final SessionInputBuffer in;
    private final CharArrayBuffer buffer;
    private int state;
    private int chunkSize;
    private int pos;
    private boolean eof;
    private boolean closed;
    private Header[] footers;
    
    public ChunkedInputStream(final SessionInputBuffer sessionInputBuffer) {
        this.eof = false;
        this.closed = false;
        this.footers = new Header[0];
        this.in = (SessionInputBuffer)Args.notNull(sessionInputBuffer, "Session input buffer");
        this.pos = 0;
        this.buffer = new CharArrayBuffer(16);
        this.state = 1;
    }
    
    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            return Math.min(((BufferInfo)this.in).length(), this.chunkSize - this.pos);
        }
        return 0;
    }
    
    @Override
    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.eof) {
            return -1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return -1;
            }
        }
        final int read = this.in.read();
        if (read != -1) {
            ++this.pos;
            if (this.pos >= this.chunkSize) {
                this.state = 3;
            }
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.eof) {
            return -1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return -1;
            }
        }
        final int read = this.in.read(array, n, Math.min(n2, this.chunkSize - this.pos));
        if (read != -1) {
            this.pos += read;
            if (this.pos >= this.chunkSize) {
                this.state = 3;
            }
            return read;
        }
        this.eof = true;
        throw new TruncatedChunkException("Truncated chunk ( expected size: " + this.chunkSize + "; actual size: " + this.pos + ")");
    }
    
    @Override
    public int read(final byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }
    
    private void nextChunk() throws IOException {
        this.chunkSize = this.getChunkSize();
        if (this.chunkSize < 0) {
            throw new MalformedChunkCodingException("Negative chunk size");
        }
        this.state = 2;
        this.pos = 0;
        if (this.chunkSize == 0) {
            this.eof = true;
            this.parseTrailerHeaders();
        }
    }
    
    private int getChunkSize() throws IOException {
        switch (this.state) {
            case 3: {
                this.buffer.clear();
                if (this.in.readLine(this.buffer) == -1) {
                    return 0;
                }
                if (!this.buffer.isEmpty()) {
                    throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
                }
                this.state = 1;
            }
            case 1: {
                this.buffer.clear();
                if (this.in.readLine(this.buffer) == -1) {
                    return 0;
                }
                int n = this.buffer.indexOf(59);
                if (n < 0) {
                    n = this.buffer.length();
                }
                return Integer.parseInt(this.buffer.substringTrimmed(0, n), 16);
            }
            default: {
                throw new IllegalStateException("Inconsistent codec state");
            }
        }
    }
    
    private void parseTrailerHeaders() throws IOException {
        this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            if (!this.eof) {
                while (this.read(new byte[2048]) >= 0) {}
            }
            this.eof = true;
            this.closed = true;
        }
    }
    
    public Header[] getFooters() {
        return this.footers.clone();
    }
}
