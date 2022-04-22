package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.message.*;
import org.apache.http.*;
import java.util.*;

@Immutable
public class DefaultHttpResponseFactory implements HttpResponseFactory
{
    public static final DefaultHttpResponseFactory INSTANCE;
    protected final ReasonPhraseCatalog reasonCatalog;
    
    public DefaultHttpResponseFactory(final ReasonPhraseCatalog reasonPhraseCatalog) {
        this.reasonCatalog = (ReasonPhraseCatalog)Args.notNull(reasonPhraseCatalog, "Reason phrase catalog");
    }
    
    public DefaultHttpResponseFactory() {
        this(EnglishReasonPhraseCatalog.INSTANCE);
    }
    
    public HttpResponse newHttpResponse(final ProtocolVersion protocolVersion, final int n, final HttpContext httpContext) {
        Args.notNull(protocolVersion, "HTTP version");
        final Locale determineLocale = this.determineLocale(httpContext);
        return new BasicHttpResponse(new BasicStatusLine(protocolVersion, n, this.reasonCatalog.getReason(n, determineLocale)), this.reasonCatalog, determineLocale);
    }
    
    public HttpResponse newHttpResponse(final StatusLine statusLine, final HttpContext httpContext) {
        Args.notNull(statusLine, "Status line");
        return new BasicHttpResponse(statusLine, this.reasonCatalog, this.determineLocale(httpContext));
    }
    
    protected Locale determineLocale(final HttpContext httpContext) {
        return Locale.getDefault();
    }
    
    static {
        INSTANCE = new DefaultHttpResponseFactory();
    }
}
