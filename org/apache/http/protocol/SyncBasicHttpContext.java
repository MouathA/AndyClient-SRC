package org.apache.http.protocol;

@Deprecated
public class SyncBasicHttpContext extends BasicHttpContext
{
    public SyncBasicHttpContext(final HttpContext httpContext) {
        super(httpContext);
    }
    
    public SyncBasicHttpContext() {
    }
    
    @Override
    public synchronized Object getAttribute(final String s) {
        return super.getAttribute(s);
    }
    
    @Override
    public synchronized void setAttribute(final String s, final Object o) {
        super.setAttribute(s, o);
    }
    
    @Override
    public synchronized Object removeAttribute(final String s) {
        return super.removeAttribute(s);
    }
    
    @Override
    public synchronized void clear() {
        super.clear();
    }
}
