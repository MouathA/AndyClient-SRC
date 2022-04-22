package org.apache.commons.io.filefilter;

import java.io.*;

public abstract class AbstractFileFilter implements IOFileFilter
{
    @Override
    public boolean accept(final File file) {
        return this.accept(file.getParentFile(), file.getName());
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return this.accept(new File(file, s));
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
