package org.apache.http.client;

import org.apache.http.*;
import org.apache.http.annotation.*;

@Immutable
public class RedirectException extends ProtocolException
{
    private static final long serialVersionUID = 4418824536372559326L;
    
    public RedirectException() {
    }
    
    public RedirectException(final String s) {
        super(s);
    }
    
    public RedirectException(final String s, final Throwable t) {
        super(s, t);
    }
}
