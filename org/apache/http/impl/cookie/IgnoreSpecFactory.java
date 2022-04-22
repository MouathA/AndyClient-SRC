package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.cookie.*;
import org.apache.http.protocol.*;

@Immutable
public class IgnoreSpecFactory implements CookieSpecFactory, CookieSpecProvider
{
    public CookieSpec newInstance(final HttpParams httpParams) {
        return new IgnoreSpec();
    }
    
    public CookieSpec create(final HttpContext httpContext) {
        return new IgnoreSpec();
    }
}
