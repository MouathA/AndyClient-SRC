package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class InputStreamEntity extends AbstractHttpEntity
{
    private final InputStream content;
    private final long length;
    
    public InputStreamEntity(final InputStream inputStream) {
        this(inputStream, -1L);
    }
    
    public InputStreamEntity(final InputStream inputStream, final long n) {
        this(inputStream, n, null);
    }
    
    public InputStreamEntity(final InputStream inputStream, final ContentType contentType) {
        this(inputStream, -1L, contentType);
    }
    
    public InputStreamEntity(final InputStream inputStream, final long length, final ContentType contentType) {
        this.content = (InputStream)Args.notNull(inputStream, "Source input stream");
        this.length = length;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }
    
    public boolean isRepeatable() {
        return false;
    }
    
    public long getContentLength() {
        return this.length;
    }
    
    public InputStream getContent() throws IOException {
        return this.content;
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        final InputStream content = this.content;
        final byte[] array = new byte[4096];
        if (this.length < 0L) {
            int read;
            while ((read = content.read(array)) != -1) {
                outputStream.write(array, 0, read);
            }
        }
        else {
            int read2;
            for (long length = this.length; length > 0L; length -= read2) {
                read2 = content.read(array, 0, (int)Math.min(4096L, length));
                if (read2 == -1) {
                    break;
                }
                outputStream.write(array, 0, read2);
            }
        }
        content.close();
    }
    
    public boolean isStreaming() {
        return true;
    }
}
