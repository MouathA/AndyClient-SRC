package org.apache.http;

import java.io.*;

public class ContentTooLongException extends IOException
{
    private static final long serialVersionUID = -924287689552495383L;
    
    public ContentTooLongException(final String s) {
        super(s);
    }
}
