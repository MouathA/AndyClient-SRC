package org.apache.http;

import java.io.*;

public class ConnectionClosedException extends IOException
{
    private static final long serialVersionUID = 617550366255636674L;
    
    public ConnectionClosedException(final String s) {
        super(s);
    }
}
