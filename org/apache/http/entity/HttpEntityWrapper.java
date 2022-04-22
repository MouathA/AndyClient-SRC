package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;

@NotThreadSafe
public class HttpEntityWrapper implements HttpEntity
{
    protected HttpEntity wrappedEntity;
    
    public HttpEntityWrapper(final HttpEntity httpEntity) {
        this.wrappedEntity = (HttpEntity)Args.notNull(httpEntity, "Wrapped entity");
    }
    
    public boolean isRepeatable() {
        return this.wrappedEntity.isRepeatable();
    }
    
    public boolean isChunked() {
        return this.wrappedEntity.isChunked();
    }
    
    public long getContentLength() {
        return this.wrappedEntity.getContentLength();
    }
    
    public Header getContentType() {
        return this.wrappedEntity.getContentType();
    }
    
    public Header getContentEncoding() {
        return this.wrappedEntity.getContentEncoding();
    }
    
    public InputStream getContent() throws IOException {
        return this.wrappedEntity.getContent();
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        this.wrappedEntity.writeTo(outputStream);
    }
    
    public boolean isStreaming() {
        return this.wrappedEntity.isStreaming();
    }
    
    @Deprecated
    public void consumeContent() throws IOException {
        this.wrappedEntity.consumeContent();
    }
}
