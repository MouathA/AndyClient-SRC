package org.apache.http.cookie;

import org.apache.http.annotation.*;

@Immutable
public class CookieRestrictionViolationException extends MalformedCookieException
{
    private static final long serialVersionUID = 7371235577078589013L;
    
    public CookieRestrictionViolationException() {
    }
    
    public CookieRestrictionViolationException(final String s) {
        super(s);
    }
}
