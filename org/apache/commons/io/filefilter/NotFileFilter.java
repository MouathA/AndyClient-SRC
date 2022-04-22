package org.apache.commons.io.filefilter;

import java.io.*;

public class NotFileFilter extends AbstractFileFilter implements Serializable
{
    private final IOFileFilter filter;
    
    public NotFileFilter(final IOFileFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The filter must not be null");
        }
        this.filter = filter;
    }
    
    @Override
    public boolean accept(final File file) {
        return !this.filter.accept(file);
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return !this.filter.accept(file, s);
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + this.filter.toString() + ")";
    }
}
