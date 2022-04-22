package org.apache.http.client.entity;

import org.apache.http.entity.*;
import org.apache.http.*;
import org.apache.http.message.*;
import java.io.*;
import org.apache.http.util.*;
import java.util.zip.*;

public class GzipCompressingEntity extends HttpEntityWrapper
{
    private static final String GZIP_CODEC = "gzip";
    
    public GzipCompressingEntity(final HttpEntity httpEntity) {
        super(httpEntity);
    }
    
    @Override
    public Header getContentEncoding() {
        return new BasicHeader("Content-Encoding", "gzip");
    }
    
    @Override
    public long getContentLength() {
        return -1L;
    }
    
    @Override
    public boolean isChunked() {
        return true;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        this.wrappedEntity.writeTo(gzipOutputStream);
        gzipOutputStream.close();
    }
}
