package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@NotThreadSafe
public class HttpCoreContext implements HttpContext
{
    public static final String HTTP_CONNECTION = "http.connection";
    public static final String HTTP_REQUEST = "http.request";
    public static final String HTTP_RESPONSE = "http.response";
    public static final String HTTP_TARGET_HOST = "http.target_host";
    public static final String HTTP_REQ_SENT = "http.request_sent";
    private final HttpContext context;
    
    public static HttpCoreContext create() {
        return new HttpCoreContext(new BasicHttpContext());
    }
    
    public static HttpCoreContext adapt(final HttpContext httpContext) {
        Args.notNull(httpContext, "HTTP context");
        if (httpContext instanceof HttpCoreContext) {
            return (HttpCoreContext)httpContext;
        }
        return new HttpCoreContext(httpContext);
    }
    
    public HttpCoreContext(final HttpContext context) {
        this.context = context;
    }
    
    public HttpCoreContext() {
        this.context = new BasicHttpContext();
    }
    
    public Object getAttribute(final String s) {
        return this.context.getAttribute(s);
    }
    
    public void setAttribute(final String s, final Object o) {
        this.context.setAttribute(s, o);
    }
    
    public Object removeAttribute(final String s) {
        return this.context.removeAttribute(s);
    }
    
    public Object getAttribute(final String s, final Class clazz) {
        Args.notNull(clazz, "Attribute class");
        final Object attribute = this.getAttribute(s);
        if (attribute == null) {
            return null;
        }
        return clazz.cast(attribute);
    }
    
    public HttpConnection getConnection(final Class clazz) {
        return (HttpConnection)this.getAttribute("http.connection", clazz);
    }
    
    public HttpConnection getConnection() {
        return (HttpConnection)this.getAttribute("http.connection", HttpConnection.class);
    }
    
    public HttpRequest getRequest() {
        return (HttpRequest)this.getAttribute("http.request", HttpRequest.class);
    }
    
    public boolean isRequestSent() {
        final Boolean b = (Boolean)this.getAttribute("http.request_sent", Boolean.class);
        return b != null && b;
    }
    
    public HttpResponse getResponse() {
        return (HttpResponse)this.getAttribute("http.response", HttpResponse.class);
    }
    
    public void setTargetHost(final HttpHost httpHost) {
        this.setAttribute("http.target_host", httpHost);
    }
    
    public HttpHost getTargetHost() {
        return (HttpHost)this.getAttribute("http.target_host", HttpHost.class);
    }
}
