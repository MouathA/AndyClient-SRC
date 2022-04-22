package org.apache.http;

import java.io.*;

public interface HttpEntity
{
    boolean isRepeatable();
    
    boolean isChunked();
    
    long getContentLength();
    
    Header getContentType();
    
    Header getContentEncoding();
    
    InputStream getContent() throws IOException, IllegalStateException;
    
    void writeTo(final OutputStream p0) throws IOException;
    
    boolean isStreaming();
    
    @Deprecated
    void consumeContent() throws IOException;
}
