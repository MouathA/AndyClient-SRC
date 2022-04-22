package org.apache.commons.io.filefilter;

import java.io.*;

public class DirectoryFileFilter extends AbstractFileFilter implements Serializable
{
    public static final IOFileFilter DIRECTORY;
    public static final IOFileFilter INSTANCE;
    
    protected DirectoryFileFilter() {
    }
    
    @Override
    public boolean accept(final File file) {
        return file.isDirectory();
    }
    
    static {
        DIRECTORY = new DirectoryFileFilter();
        INSTANCE = DirectoryFileFilter.DIRECTORY;
    }
}
