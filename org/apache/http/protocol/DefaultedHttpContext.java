package org.apache.http.protocol;

import org.apache.http.util.*;

@Deprecated
public final class DefaultedHttpContext implements HttpContext
{
    private final HttpContext local;
    private final HttpContext defaults;
    
    public DefaultedHttpContext(final HttpContext httpContext, final HttpContext defaults) {
        this.local = (HttpContext)Args.notNull(httpContext, "HTTP context");
        this.defaults = defaults;
    }
    
    public Object getAttribute(final String s) {
        final Object attribute = this.local.getAttribute(s);
        if (attribute == null) {
            return this.defaults.getAttribute(s);
        }
        return attribute;
    }
    
    public Object removeAttribute(final String s) {
        return this.local.removeAttribute(s);
    }
    
    public void setAttribute(final String s, final Object o) {
        this.local.setAttribute(s, o);
    }
    
    public HttpContext getDefaults() {
        return this.defaults;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[local: ").append(this.local);
        sb.append("defaults: ").append(this.defaults);
        sb.append("]");
        return sb.toString();
    }
}
