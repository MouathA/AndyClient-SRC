package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.*;

@Deprecated
@Immutable
public class TunnelRefusedException extends HttpException
{
    private static final long serialVersionUID = -8646722842745617323L;
    private final HttpResponse response;
    
    public TunnelRefusedException(final String s, final HttpResponse response) {
        super(s);
        this.response = response;
    }
    
    public HttpResponse getResponse() {
        return this.response;
    }
}
