package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class ByteArrayEntity extends AbstractHttpEntity implements Cloneable
{
    @Deprecated
    protected final byte[] content;
    private final byte[] b;
    private final int off;
    private final int len;
    
    public ByteArrayEntity(final byte[] array, final ContentType contentType) {
        Args.notNull(array, "Source byte array");
        this.content = array;
        this.b = array;
        this.off = 0;
        this.len = this.b.length;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }
    
    public ByteArrayEntity(final byte[] array, final int off, final int len, final ContentType contentType) {
        Args.notNull(array, "Source byte array");
        if (off < 0 || off > array.length || len < 0 || off + len < 0 || off + len > array.length) {
            throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + array.length);
        }
        this.content = array;
        this.b = array;
        this.off = off;
        this.len = len;
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }
    
    public ByteArrayEntity(final byte[] array) {
        this(array, null);
    }
    
    public ByteArrayEntity(final byte[] array, final int n, final int n2) {
        this(array, n, n2, null);
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public long getContentLength() {
        return this.len;
    }
    
    public InputStream getContent() {
        return new ByteArrayInputStream(this.b, this.off, this.len);
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        outputStream.write(this.b, this.off, this.len);
        outputStream.flush();
    }
    
    public boolean isStreaming() {
        return false;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
