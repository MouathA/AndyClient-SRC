package org.apache.http.client.entity;

import org.apache.http.entity.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.util.*;

abstract class DecompressingEntity extends HttpEntityWrapper
{
    private static final int BUFFER_SIZE = 2048;
    private InputStream content;
    
    public DecompressingEntity(final HttpEntity httpEntity) {
        super(httpEntity);
    }
    
    abstract InputStream decorate(final InputStream p0) throws IOException;
    
    private InputStream getDecompressingStream() throws IOException {
        return new LazyDecompressingInputStream(this.wrappedEntity.getContent(), this);
    }
    
    @Override
    public InputStream getContent() throws IOException {
        if (this.wrappedEntity.isStreaming()) {
            if (this.content == null) {
                this.content = this.getDecompressingStream();
            }
            return this.content;
        }
        return this.getDecompressingStream();
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        final InputStream content = this.getContent();
        final byte[] array = new byte[2048];
        int read;
        while ((read = content.read(array)) != -1) {
            outputStream.write(array, 0, read);
        }
        content.close();
    }
}
