package org.apache.commons.io.filefilter;

import java.io.*;

public class FalseFileFilter implements IOFileFilter, Serializable
{
    public static final IOFileFilter FALSE;
    public static final IOFileFilter INSTANCE;
    
    protected FalseFileFilter() {
    }
    
    @Override
    public boolean accept(final File file) {
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return false;
    }
    
    static {
        FALSE = new FalseFileFilter();
        INSTANCE = FalseFileFilter.FALSE;
    }
}
