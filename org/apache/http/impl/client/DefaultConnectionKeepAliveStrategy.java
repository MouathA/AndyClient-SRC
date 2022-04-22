package org.apache.http.impl.client;

import org.apache.http.conn.*;
import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import org.apache.http.*;

@Immutable
public class DefaultConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy
{
    public static final DefaultConnectionKeepAliveStrategy INSTANCE;
    
    public long getKeepAliveDuration(final HttpResponse httpResponse, final HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        final BasicHeaderElementIterator basicHeaderElementIterator = new BasicHeaderElementIterator(httpResponse.headerIterator("Keep-Alive"));
        while (basicHeaderElementIterator.hasNext()) {
            final HeaderElement nextElement = basicHeaderElementIterator.nextElement();
            final String name = nextElement.getName();
            final String value = nextElement.getValue();
            if (value != null && name.equalsIgnoreCase("timeout")) {
                return Long.parseLong(value) * 1000L;
            }
        }
        return -1L;
    }
    
    static {
        INSTANCE = new DefaultConnectionKeepAliveStrategy();
    }
}
