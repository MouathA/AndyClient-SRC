package org.apache.http.entity;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;

@NotThreadSafe
public class FileEntity extends AbstractHttpEntity implements Cloneable
{
    protected final File file;
    
    @Deprecated
    public FileEntity(final File file, final String contentType) {
        this.file = (File)Args.notNull(file, "File");
        this.setContentType(contentType);
    }
    
    public FileEntity(final File file, final ContentType contentType) {
        this.file = (File)Args.notNull(file, "File");
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }
    
    public FileEntity(final File file) {
        this.file = (File)Args.notNull(file, "File");
    }
    
    public boolean isRepeatable() {
        return true;
    }
    
    public long getContentLength() {
        return this.file.length();
    }
    
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        final FileInputStream fileInputStream = new FileInputStream(this.file);
        final byte[] array = new byte[4096];
        int read;
        while ((read = fileInputStream.read(array)) != -1) {
            outputStream.write(array, 0, read);
        }
        outputStream.flush();
        fileInputStream.close();
    }
    
    public boolean isStreaming() {
        return false;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
