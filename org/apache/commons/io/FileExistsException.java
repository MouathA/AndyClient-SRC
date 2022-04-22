package org.apache.commons.io;

import java.io.*;

public class FileExistsException extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public FileExistsException() {
    }
    
    public FileExistsException(final String s) {
        super(s);
    }
    
    public FileExistsException(final File file) {
        super("File " + file + " exists");
    }
}
