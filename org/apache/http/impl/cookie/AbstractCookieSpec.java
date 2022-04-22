package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;
import org.apache.http.util.*;
import java.util.*;

@NotThreadSafe
public abstract class AbstractCookieSpec implements CookieSpec
{
    private final Map attribHandlerMap;
    
    public AbstractCookieSpec() {
        this.attribHandlerMap = new HashMap(10);
    }
    
    public void registerAttribHandler(final String s, final CookieAttributeHandler cookieAttributeHandler) {
        Args.notNull(s, "Attribute name");
        Args.notNull(cookieAttributeHandler, "Attribute handler");
        this.attribHandlerMap.put(s, cookieAttributeHandler);
    }
    
    protected CookieAttributeHandler findAttribHandler(final String s) {
        return this.attribHandlerMap.get(s);
    }
    
    protected CookieAttributeHandler getAttribHandler(final String s) {
        final CookieAttributeHandler attribHandler = this.findAttribHandler(s);
        if (attribHandler == null) {
            throw new IllegalStateException("Handler not registered for " + s + " attribute.");
        }
        return attribHandler;
    }
    
    protected Collection getAttribHandlers() {
        return this.attribHandlerMap.values();
    }
}
