package com.google.common.io;

import com.google.common.annotations.*;
import java.io.*;

@Beta
public final class FileBackedOutputStream extends OutputStream
{
    private final int fileThreshold;
    private final boolean resetOnFinalize;
    private final ByteSource source;
    private OutputStream out;
    private MemoryOutput memory;
    private File file;
    
    @VisibleForTesting
    synchronized File getFile() {
        return this.file;
    }
    
    public FileBackedOutputStream(final int n) {
        this(n, false);
    }
    
    public FileBackedOutputStream(final int fileThreshold, final boolean resetOnFinalize) {
        this.fileThreshold = fileThreshold;
        this.resetOnFinalize = resetOnFinalize;
        this.memory = new MemoryOutput(null);
        this.out = this.memory;
        if (resetOnFinalize) {
            this.source = new ByteSource() {
                final FileBackedOutputStream this$0;
                
                @Override
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.access$100(this.this$0);
                }
                
                @Override
                protected void finalize() {
                    this.this$0.reset();
                }
            };
        }
        else {
            this.source = new ByteSource() {
                final FileBackedOutputStream this$0;
                
                @Override
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.access$100(this.this$0);
                }
            };
        }
    }
    
    public ByteSource asByteSource() {
        return this.source;
    }
    
    private synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
    }
    
    public synchronized void reset() throws IOException {
        this.close();
        if (this.memory == null) {
            this.memory = new MemoryOutput(null);
        }
        else {
            this.memory.reset();
        }
        this.out = this.memory;
        if (this.file != null) {
            final File file = this.file;
            this.file = null;
            if (!file.delete()) {
                throw new IOException("Could not delete: " + file);
            }
        }
    }
    
    @Override
    public synchronized void write(final int n) throws IOException {
        this.update(1);
        this.out.write(n);
    }
    
    @Override
    public synchronized void write(final byte[] array) throws IOException {
        this.write(array, 0, array.length);
    }
    
    @Override
    public synchronized void write(final byte[] array, final int n, final int n2) throws IOException {
        this.update(n2);
        this.out.write(array, n, n2);
    }
    
    @Override
    public synchronized void close() throws IOException {
        this.out.close();
    }
    
    @Override
    public synchronized void flush() throws IOException {
        this.out.flush();
    }
    
    private void update(final int n) throws IOException {
        if (this.file == null && this.memory.getCount() + n > this.fileThreshold) {
            final File tempFile = File.createTempFile("FileBackedOutputStream", null);
            if (this.resetOnFinalize) {
                tempFile.deleteOnExit();
            }
            final FileOutputStream out = new FileOutputStream(tempFile);
            out.write(this.memory.getBuffer(), 0, this.memory.getCount());
            out.flush();
            this.out = out;
            this.file = tempFile;
            this.memory = null;
        }
    }
    
    static InputStream access$100(final FileBackedOutputStream fileBackedOutputStream) throws IOException {
        return fileBackedOutputStream.openInputStream();
    }
    
    private static class MemoryOutput extends ByteArrayOutputStream
    {
        private MemoryOutput() {
        }
        
        byte[] getBuffer() {
            return this.buf;
        }
        
        int getCount() {
            return this.count;
        }
        
        MemoryOutput(final FileBackedOutputStream$1 byteSource) {
            this();
        }
    }
}
