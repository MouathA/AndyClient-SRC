package org.apache.commons.io.output;

import java.nio.charset.*;
import java.io.*;

public class FileWriterWithEncoding extends Writer
{
    private final Writer out;
    
    public FileWriterWithEncoding(final String s, final String s2) throws IOException {
        this(new File(s), s2, false);
    }
    
    public FileWriterWithEncoding(final String s, final String s2, final boolean b) throws IOException {
        this(new File(s), s2, b);
    }
    
    public FileWriterWithEncoding(final String s, final Charset charset) throws IOException {
        this(new File(s), charset, false);
    }
    
    public FileWriterWithEncoding(final String s, final Charset charset, final boolean b) throws IOException {
        this(new File(s), charset, b);
    }
    
    public FileWriterWithEncoding(final String s, final CharsetEncoder charsetEncoder) throws IOException {
        this(new File(s), charsetEncoder, false);
    }
    
    public FileWriterWithEncoding(final String s, final CharsetEncoder charsetEncoder, final boolean b) throws IOException {
        this(new File(s), charsetEncoder, b);
    }
    
    public FileWriterWithEncoding(final File file, final String s) throws IOException {
        this(file, s, false);
    }
    
    public FileWriterWithEncoding(final File file, final String s, final boolean b) throws IOException {
        this.out = initWriter(file, s, b);
    }
    
    public FileWriterWithEncoding(final File file, final Charset charset) throws IOException {
        this(file, charset, false);
    }
    
    public FileWriterWithEncoding(final File file, final Charset charset, final boolean b) throws IOException {
        this.out = initWriter(file, charset, b);
    }
    
    public FileWriterWithEncoding(final File file, final CharsetEncoder charsetEncoder) throws IOException {
        this(file, charsetEncoder, false);
    }
    
    public FileWriterWithEncoding(final File file, final CharsetEncoder charsetEncoder, final boolean b) throws IOException {
        this.out = initWriter(file, charsetEncoder, b);
    }
    
    private static Writer initWriter(final File file, final Object o, final boolean b) throws IOException {
        if (file == null) {
            throw new NullPointerException("File is missing");
        }
        if (o == null) {
            throw new NullPointerException("Encoding is missing");
        }
        file.exists();
        final FileOutputStream fileOutputStream = new FileOutputStream(file, b);
        OutputStreamWriter outputStreamWriter;
        if (o instanceof Charset) {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, (Charset)o);
        }
        else if (o instanceof CharsetEncoder) {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, (CharsetEncoder)o);
        }
        else {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, (String)o);
        }
        return outputStreamWriter;
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
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
}
