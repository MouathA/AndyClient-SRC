package org.apache.http.client;

import java.io.*;
import org.apache.http.annotation.*;

@Immutable
public class ClientProtocolException extends IOException
{
    private static final long serialVersionUID = -5596590843227115865L;
    
    public ClientProtocolException() {
    }
    
    public ClientProtocolException(final String s) {
        super(s);
    }
    
    public ClientProtocolException(final Throwable t) {
        this.initCause(t);
    }
    
    public ClientProtocolException(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
}
