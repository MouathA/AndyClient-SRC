package org.apache.http.client.entity;

import java.util.zip.*;
import org.apache.http.*;
import java.io.*;

public class GzipDecompressingEntity extends DecompressingEntity
{
    public GzipDecompressingEntity(final HttpEntity httpEntity) {
        super(httpEntity);
    }
    
    @Override
    InputStream decorate(final InputStream inputStream) throws IOException {
        return new GZIPInputStream(inputStream);
    }
    
    @Override
    public Header getContentEncoding() {
        return null;
    }
    
    @Override
    public long getContentLength() {
        return -1L;
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
    }
    
    @Override
    public InputStream getContent() throws IOException {
        return super.getContent();
    }
}
