package org.apache.commons.io.output;

import java.io.*;

public class ProxyWriter extends FilterWriter
{
    public ProxyWriter(final Writer writer) {
        super(writer);
    }
    
    @Override
    public Writer append(final char c) throws IOException {
        this.beforeWrite(1);
        this.out.append(c);
        this.afterWrite(1);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        this.beforeWrite(n2 - n);
        this.out.append(charSequence, n, n2);
        this.afterWrite(n2 - n);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence) throws IOException {
        if (charSequence != null) {
            charSequence.length();
        }
        this.beforeWrite(0);
        this.out.append(charSequence);
        this.afterWrite(0);
        return this;
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.beforeWrite(1);
        this.out.write(n);
        this.afterWrite(1);
    }
    
    @Override
    public void write(final char[] array) throws IOException {
        if (array != null) {
            final int length = array.length;
        }
        this.beforeWrite(0);
        this.out.write(array);
        this.afterWrite(0);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) throws IOException {
        this.beforeWrite(n2);
        this.out.write(array, n, n2);
        this.afterWrite(n2);
    }
    
    @Override
    public void write(final String s) throws IOException {
        if (s != null) {
            s.length();
        }
        this.beforeWrite(0);
        this.out.write(s);
        this.afterWrite(0);
    }
    
    @Override
    public void write(final String s, final int n, final int n2) throws IOException {
        this.beforeWrite(n2);
        this.out.write(s, n, n2);
        this.afterWrite(n2);
    }
    
    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
    }
    
    protected void beforeWrite(final int n) throws IOException {
    }
    
    protected void afterWrite(final int n) throws IOException {
    }
    
    protected void handleIOException(final IOException ex) throws IOException {
        throw ex;
    }
    
    @Override
    public Appendable append(final char c) throws IOException {
        return this.append(c);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}
