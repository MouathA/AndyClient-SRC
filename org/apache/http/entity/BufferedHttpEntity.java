package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;

@NotThreadSafe
public class BufferedHttpEntity extends HttpEntityWrapper
{
    private final byte[] buffer;
    
    public BufferedHttpEntity(final HttpEntity httpEntity) throws IOException {
        super(httpEntity);
        if (!httpEntity.isRepeatable() || httpEntity.getContentLength() < 0L) {
            this.buffer = EntityUtils.toByteArray(httpEntity);
        }
        else {
            this.buffer = null;
        }
    }
    
    @Override
    public long getContentLength() {
        if (this.buffer != null) {
            return this.buffer.length;
        }
        return super.getContentLength();
    }
    
    @Override
    public InputStream getContent() throws IOException {
        if (this.buffer != null) {
            return new ByteArrayInputStream(this.buffer);
        }
        return super.getContent();
    }
    
    @Override
    public boolean isChunked() {
        return this.buffer == null && super.isChunked();
    }
    
    @Override
    public boolean isRepeatable() {
        return true;
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        if (this.buffer != null) {
            outputStream.write(this.buffer);
        }
        else {
            super.writeTo(outputStream);
        }
    }
    
    @Override
    public boolean isStreaming() {
        return this.buffer == null && super.isStreaming();
    }
}
