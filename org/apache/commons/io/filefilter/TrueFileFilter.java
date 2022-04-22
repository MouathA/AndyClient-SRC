package org.apache.commons.io.filefilter;

import java.io.*;

public class TrueFileFilter implements IOFileFilter, Serializable
{
    public static final IOFileFilter TRUE;
    public static final IOFileFilter INSTANCE;
    
    protected TrueFileFilter() {
    }
    
    @Override
    public boolean accept(final File file) {
        return true;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return true;
    }
    
    static {
        TRUE = new TrueFileFilter();
        INSTANCE = TrueFileFilter.TRUE;
    }
}
