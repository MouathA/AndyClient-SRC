package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class BasicHttpEntity extends AbstractHttpEntity
{
    private InputStream content;
    private long length;
    
    public BasicHttpEntity() {
        this.length = -1L;
    }
    
    public long getContentLength() {
        return this.length;
    }
    
    public InputStream getContent() throws IllegalStateException {
        Asserts.check(this.content != null, "Content has not been provided");
        return this.content;
    }
    
    public boolean isRepeatable() {
        return false;
    }
    
    public void setContentLength(final long length) {
        this.length = length;
    }
    
    public void setContent(final InputStream content) {
        this.content = content;
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        final InputStream content = this.getContent();
        final byte[] array = new byte[4096];
        int read;
        while ((read = content.read(array)) != -1) {
            outputStream.write(array, 0, read);
        }
        content.close();
    }
    
    public boolean isStreaming() {
        return this.content != null;
    }
}
