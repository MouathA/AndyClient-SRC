package org.apache.commons.io.filefilter;

import java.io.*;

public class FileFileFilter extends AbstractFileFilter implements Serializable
{
    public static final IOFileFilter FILE;
    
    protected FileFileFilter() {
    }
    
    @Override
    public boolean accept(final File file) {
        return file.isFile();
    }
    
    static {
        FILE = new FileFileFilter();
    }
}
