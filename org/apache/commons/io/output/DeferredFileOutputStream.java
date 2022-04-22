package org.apache.commons.io.output;

import org.apache.commons.io.*;
import java.io.*;

public class DeferredFileOutputStream extends ThresholdingOutputStream
{
    private ByteArrayOutputStream memoryOutputStream;
    private OutputStream currentOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;
    private final File directory;
    private boolean closed;
    
    public DeferredFileOutputStream(final int n, final File file) {
        this(n, file, null, null, null);
    }
    
    public DeferredFileOutputStream(final int n, final String s, final String s2, final File file) {
        this(n, null, s, s2, file);
        if (s == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
    }
    
    private DeferredFileOutputStream(final int n, final File outputFile, final String prefix, final String suffix, final File directory) {
        super(n);
        this.closed = false;
        this.outputFile = outputFile;
        this.memoryOutputStream = new ByteArrayOutputStream();
        this.currentOutputStream = this.memoryOutputStream;
        this.prefix = prefix;
        this.suffix = suffix;
        this.directory = directory;
    }
    
    @Override
    protected OutputStream getStream() throws IOException {
        return this.currentOutputStream;
    }
    
    @Override
    protected void thresholdReached() throws IOException {
        if (this.prefix != null) {
            this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
        }
        final FileOutputStream currentOutputStream = new FileOutputStream(this.outputFile);
        this.memoryOutputStream.writeTo(currentOutputStream);
        this.currentOutputStream = currentOutputStream;
        this.memoryOutputStream = null;
    }
    
    public byte[] getData() {
        if (this.memoryOutputStream != null) {
            return this.memoryOutputStream.toByteArray();
        }
        return null;
    }
    
    public File getFile() {
        return this.outputFile;
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.closed = true;
    }
    
    public void writeTo(final OutputStream outputStream) throws IOException {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        }
        if (this == 0) {
            this.memoryOutputStream.writeTo(outputStream);
        }
        else {
            final FileInputStream fileInputStream = new FileInputStream(this.outputFile);
            IOUtils.copy(fileInputStream, outputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
    }
}
