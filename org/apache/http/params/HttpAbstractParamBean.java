package org.apache.http.params;

import org.apache.http.util.*;

@Deprecated
public abstract class HttpAbstractParamBean
{
    protected final HttpParams params;
    
    public HttpAbstractParamBean(final HttpParams httpParams) {
        this.params = (HttpParams)Args.notNull(httpParams, "HTTP parameters");
    }
}
