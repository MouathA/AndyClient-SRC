package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.protocol.*;

@Immutable
public class NetscapeDraftSpecFactory implements CookieSpecFactory, CookieSpecProvider
{
    private final String[] datepatterns;
    
    public NetscapeDraftSpecFactory(final String[] datepatterns) {
        this.datepatterns = datepatterns;
    }
    
    public NetscapeDraftSpecFactory() {
        this(null);
    }
    
    public CookieSpec newInstance(final HttpParams httpParams) {
        if (httpParams != null) {
            String[] array = null;
            final Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
            if (collection != null) {
                array = collection.toArray(new String[collection.size()]);
            }
            return new NetscapeDraftSpec(array);
        }
        return new NetscapeDraftSpec();
    }
    
    public CookieSpec create(final HttpContext httpContext) {
        return new NetscapeDraftSpec(this.datepatterns);
    }
}
