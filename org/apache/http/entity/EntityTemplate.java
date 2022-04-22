package org.apache.http.entity;

import org.apache.http.util.*;
import java.io.*;

public class EntityTemplate extends AbstractHttpEntity
{
    private final ContentProducer contentproducer;
    
    public EntityTemplate(final ContentProducer contentProducer) {
        this.contentproducer = (ContentProducer)Args.notNull(contentProducer, "Content producer");
    }
    
    public long getContentLength() {
        return -1L;
    }
    
    public InputStream getContent() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.writeTo(byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        this.contentproducer.writeTo(outputStream);
    }
    
    public boolean isStreaming() {
        return false;
    }
}
