package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import java.nio.charset.*;
import java.io.*;

@NotThreadSafe
public class StringEntity extends AbstractHttpEntity implements Cloneable
{
    protected final byte[] content;
    
    public StringEntity(final String s, final ContentType contentType) throws UnsupportedCharsetException {
        Args.notNull(s, "Source string");
        Charset def_CONTENT_CHARSET = (contentType != null) ? contentType.getCharset() : null;
        if (def_CONTENT_CHARSET == null) {
            def_CONTENT_CHARSET = HTTP.DEF_CONTENT_CHARSET;
        }
        this.content = s.getBytes(def_CONTENT_CHARSET.name());
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }
    
    @Deprecated
    public StringEntity(final String s, final String s2, final String s3) throws UnsupportedEncodingException {
        Args.notNull(s, "Source string");
        final String s4 = (s2 != null) ? s2 : "text/plain";
        final String s5 = (s3 != null) ? s3 : "ISO-8859-1";
        this.content = s.getBytes(s5);
        this.setContentType(s4 + "; charset=" + s5);
    }
    
    public StringEntity(final String s, final String s2) throws UnsupportedCharsetException {
        this(s, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), s2));
    }
    
    public StringEntity(final String s, final Charset charset) {
        this(s, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset));
    }
    
    public StringEntity(final String s) throws UnsupportedEncodingException {
        this(s, ContentType.DEFAULT_TEXT);
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public long getContentLength() {
        return this.content.length;
    }
    
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.content);
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        outputStream.write(this.content);
        outputStream.flush();
    }
    
    public boolean isStreaming() {
        return false;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
