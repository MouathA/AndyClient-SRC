package org.apache.commons.io.output;

import java.nio.charset.*;
import org.apache.commons.io.*;
import java.io.*;

public class LockableFileWriter extends Writer
{
    private static final String LCK = ".lck";
    private final Writer out;
    private final File lockFile;
    
    public LockableFileWriter(final String s) throws IOException {
        this(s, false, null);
    }
    
    public LockableFileWriter(final String s, final boolean b) throws IOException {
        this(s, b, null);
    }
    
    public LockableFileWriter(final String s, final boolean b, final String s2) throws IOException {
        this(new File(s), b, s2);
    }
    
    public LockableFileWriter(final File file) throws IOException {
        this(file, false, null);
    }
    
    public LockableFileWriter(final File file, final boolean b) throws IOException {
        this(file, b, null);
    }
    
    public LockableFileWriter(final File file, final boolean b, final String s) throws IOException {
        this(file, Charset.defaultCharset(), b, s);
    }
    
    public LockableFileWriter(final File file, final Charset charset) throws IOException {
        this(file, charset, false, null);
    }
    
    public LockableFileWriter(final File file, final String s) throws IOException {
        this(file, s, false, null);
    }
    
    public LockableFileWriter(File absoluteFile, final Charset charset, final boolean b, String property) throws IOException {
        absoluteFile = absoluteFile.getAbsoluteFile();
        if (absoluteFile.getParentFile() != null) {
            FileUtils.forceMkdir(absoluteFile.getParentFile());
        }
        if (absoluteFile.isDirectory()) {
            throw new IOException("File specified is a directory");
        }
        if (property == null) {
            property = System.getProperty("java.io.tmpdir");
        }
        final File file = new File(property);
        FileUtils.forceMkdir(file);
        this.testLockDir(file);
        this.lockFile = new File(file, absoluteFile.getName() + ".lck");
        this.createLock();
        this.out = this.initWriter(absoluteFile, charset, b);
    }
    
    public LockableFileWriter(final File file, final String s, final boolean b, final String s2) throws IOException {
        this(file, Charsets.toCharset(s), b, s2);
    }
    
    private void testLockDir(final File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Could not find lockDir: " + file.getAbsolutePath());
        }
        if (!file.canWrite()) {
            throw new IOException("Could not write to lockDir: " + file.getAbsolutePath());
        }
    }
    
    private void createLock() throws IOException {
        final Class<LockableFileWriter> clazz = LockableFileWriter.class;
        final Class<LockableFileWriter> clazz2 = LockableFileWriter.class;
        // monitorenter(clazz)
        if (!this.lockFile.createNewFile()) {
            throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
        }
        this.lockFile.deleteOnExit();
    }
    // monitorexit(clazz2)
    
    private Writer initWriter(final File file, final Charset charset, final boolean b) throws IOException {
        file.exists();
        return new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), b), Charsets.toCharset(charset));
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
        this.lockFile.delete();
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.out.write(n);
    }
    
    @Override
    public void write(final char[] array) throws IOException {
        this.out.write(array);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        this.out.write(array, n, n2);
    }
    
    @Override
    public void write(final String s) throws IOException {
        this.out.write(s);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) throws IOException {
        this.out.write(s, n, n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
}
