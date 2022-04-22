package org.apache.http.protocol;

import org.apache.http.annotation.*;
import java.util.*;
import java.util.concurrent.*;
import org.apache.http.util.*;

@ThreadSafe
public class BasicHttpContext implements HttpContext
{
    private final HttpContext parentContext;
    private final Map map;
    
    public BasicHttpContext() {
        this(null);
    }
    
    public BasicHttpContext(final HttpContext parentContext) {
        this.map = new ConcurrentHashMap();
        this.parentContext = parentContext;
    }
    
    public Object getAttribute(final String s) {
        Args.notNull(s, "Id");
        Object o = this.map.get(s);
        if (o == null && this.parentContext != null) {
            o = this.parentContext.getAttribute(s);
        }
        return o;
    }
    
    public void setAttribute(final String s, final Object o) {
        Args.notNull(s, "Id");
        if (o != null) {
            this.map.put(s, o);
        }
        else {
            this.map.remove(s);
        }
    }
    
    public Object removeAttribute(final String s) {
        Args.notNull(s, "Id");
        return this.map.remove(s);
    }
    
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
}
