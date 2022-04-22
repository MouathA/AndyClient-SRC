package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.message.*;
import java.io.*;

@NotThreadSafe
public abstract class AbstractHttpEntity implements HttpEntity
{
    protected static final int OUTPUT_BUFFER_SIZE = 4096;
    protected Header contentType;
    protected Header contentEncoding;
    protected boolean chunked;
    
    protected AbstractHttpEntity() {
    }
    
    public Header getContentType() {
        return this.contentType;
    }
    
    public Header getContentEncoding() {
        return this.contentEncoding;
    }
    
    public boolean isChunked() {
        return this.chunked;
    }
    
    public void setContentType(final Header contentType) {
        this.contentType = contentType;
    }
    
    public void setContentType(final String s) {
        Header contentType = null;
        if (s != null) {
            contentType = new BasicHeader("Content-Type", s);
        }
        this.setContentType(contentType);
    }
    
    public void setContentEncoding(final Header contentEncoding) {
        this.contentEncoding = contentEncoding;
    }
    
    public void setContentEncoding(final String s) {
        Header contentEncoding = null;
        if (s != null) {
            contentEncoding = new BasicHeader("Content-Encoding", s);
        }
        this.setContentEncoding(contentEncoding);
    }
    
    public void setChunked(final boolean chunked) {
        this.chunked = chunked;
    }
    
    @Deprecated
    public void consumeContent() throws IOException {
    }
}
