package org.apache.commons.io.output;

import java.io.*;

public class StringBuilderWriter extends Writer implements Serializable
{
    private final StringBuilder builder;
    
    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }
    
    public StringBuilderWriter(final int n) {
        this.builder = new StringBuilder(n);
    }
    
    public StringBuilderWriter(final StringBuilder sb) {
        this.builder = ((sb != null) ? sb : new StringBuilder());
    }
    
    @Override
    public Writer append(final char c) {
        this.builder.append(c);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence) {
        this.builder.append(charSequence);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence, final int n, final int n2) {
        this.builder.append(charSequence, n, n2);
        return this;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void write(final String s) {
        if (s != null) {
            this.builder.append(s);
        }
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        if (array != null) {
            this.builder.append(array, n, n2);
        }
    }
    
    public StringBuilder getBuilder() {
        return this.builder;
    }
    
    @Override
    public String toString() {
        return this.builder.toString();
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
