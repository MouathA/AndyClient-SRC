package org.apache.http.cookie;

import org.apache.http.*;
import org.apache.http.annotation.*;

@Immutable
public class MalformedCookieException extends ProtocolException
{
    private static final long serialVersionUID = -6695462944287282185L;
    
    public MalformedCookieException() {
    }
    
    public MalformedCookieException(final String s) {
        super(s);
    }
    
    public MalformedCookieException(final String s, final Throwable t) {
        super(s, t);
    }
}
