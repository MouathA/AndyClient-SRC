package org.apache.commons.io.filefilter;

import java.io.*;

public class DelegateFileFilter extends AbstractFileFilter implements Serializable
{
    private final FilenameFilter filenameFilter;
    private final FileFilter fileFilter;
    
    public DelegateFileFilter(final FilenameFilter filenameFilter) {
        if (filenameFilter == null) {
            throw new IllegalArgumentException("The FilenameFilter must not be null");
        }
        this.filenameFilter = filenameFilter;
        this.fileFilter = null;
    }
    
    public DelegateFileFilter(final FileFilter fileFilter) {
        if (fileFilter == null) {
            throw new IllegalArgumentException("The FileFilter must not be null");
        }
        this.fileFilter = fileFilter;
        this.filenameFilter = null;
    }
    
    @Override
    public boolean accept(final File file) {
        if (this.fileFilter != null) {
            return this.fileFilter.accept(file);
        }
        return super.accept(file);
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        if (this.filenameFilter != null) {
            return this.filenameFilter.accept(file, s);
        }
        return super.accept(file, s);
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + ((this.fileFilter != null) ? this.fileFilter.toString() : this.filenameFilter.toString()) + ")";
    }
}
