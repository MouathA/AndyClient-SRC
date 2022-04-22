package org.apache.commons.io.filefilter;

import java.io.*;

public class SizeFileFilter extends AbstractFileFilter implements Serializable
{
    private final long size;
    private final boolean acceptLarger;
    
    public SizeFileFilter(final long n) {
        this(n, true);
    }
    
    public SizeFileFilter(final long size, final boolean acceptLarger) {
        if (size < 0L) {
            throw new IllegalArgumentException("The size must be non-negative");
        }
        this.size = size;
        this.acceptLarger = acceptLarger;
    }
    
    @Override
    public boolean accept(final File file) {
        final boolean b = file.length() < this.size;
        return this.acceptLarger ? (!b) : b;
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + (this.acceptLarger ? ">=" : "<") + this.size + ")";
    }
}
