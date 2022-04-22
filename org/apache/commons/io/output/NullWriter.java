package org.apache.commons.io.output;

import java.io.*;

public class NullWriter extends Writer
{
    public static final NullWriter NULL_WRITER;
    
    @Override
    public Writer append(final char c) {
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence, final int n, final int n2) {
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence) {
        return this;
    }
    
    @Override
    public void write(final int n) {
    }
    
    @Override
    public void write(final char[] array) {
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
    }
    
    @Override
    public void write(final String s) {
    }
    
    @Override
    public void write(final String s, final int n, final int n2) {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() {
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
    
    static {
        NULL_WRITER = new NullWriter();
    }
}
