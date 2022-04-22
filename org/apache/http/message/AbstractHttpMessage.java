package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.params.*;

@NotThreadSafe
public abstract class AbstractHttpMessage implements HttpMessage
{
    protected HeaderGroup headergroup;
    @Deprecated
    protected HttpParams params;
    
    @Deprecated
    protected AbstractHttpMessage(final HttpParams params) {
        this.headergroup = new HeaderGroup();
        this.params = params;
    }
    
    protected AbstractHttpMessage() {
        this(null);
    }
    
    public boolean containsHeader(final String s) {
        return this.headergroup.containsHeader(s);
    }
    
    public Header[] getHeaders(final String s) {
        return this.headergroup.getHeaders(s);
    }
    
    public Header getFirstHeader(final String s) {
        return this.headergroup.getFirstHeader(s);
    }
    
    public Header getLastHeader(final String s) {
        return this.headergroup.getLastHeader(s);
    }
    
    public Header[] getAllHeaders() {
        return this.headergroup.getAllHeaders();
    }
    
    public void addHeader(final Header header) {
        this.headergroup.addHeader(header);
    }
    
    public void addHeader(final String s, final String s2) {
        Args.notNull(s, "Header name");
        this.headergroup.addHeader(new BasicHeader(s, s2));
    }
    
    public void setHeader(final Header header) {
        this.headergroup.updateHeader(header);
    }
    
    public void setHeader(final String s, final String s2) {
        Args.notNull(s, "Header name");
        this.headergroup.updateHeader(new BasicHeader(s, s2));
    }
    
    public void setHeaders(final Header[] headers) {
        this.headergroup.setHeaders(headers);
    }
    
    public void removeHeader(final Header header) {
        this.headergroup.removeHeader(header);
    }
    
    public void removeHeaders(final String s) {
        if (s == null) {
            return;
        }
        final HeaderIterator iterator = this.headergroup.iterator();
        while (iterator.hasNext()) {
            if (s.equalsIgnoreCase(iterator.nextHeader().getName())) {
                iterator.remove();
            }
        }
    }
    
    public HeaderIterator headerIterator() {
        return this.headergroup.iterator();
    }
    
    public HeaderIterator headerIterator(final String s) {
        return this.headergroup.iterator(s);
    }
    
    @Deprecated
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = new BasicHttpParams();
        }
        return this.params;
    }
    
    @Deprecated
    public void setParams(final HttpParams httpParams) {
        this.params = (HttpParams)Args.notNull(httpParams, "HTTP parameters");
    }
}
