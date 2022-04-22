package org.apache.http.client;

import org.apache.http.annotation.*;

@Immutable
public class CircularRedirectException extends RedirectException
{
    private static final long serialVersionUID = 6830063487001091803L;
    
    public CircularRedirectException() {
    }
    
    public CircularRedirectException(final String s) {
        super(s);
    }
    
    public CircularRedirectException(final String s, final Throwable t) {
        super(s, t);
    }
}
