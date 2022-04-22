package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.protocol.*;

@Immutable
public class BestMatchSpecFactory implements CookieSpecFactory, CookieSpecProvider
{
    private final String[] datepatterns;
    private final boolean oneHeader;
    
    public BestMatchSpecFactory(final String[] datepatterns, final boolean oneHeader) {
        this.datepatterns = datepatterns;
        this.oneHeader = oneHeader;
    }
    
    public BestMatchSpecFactory() {
        this(null, false);
    }
    
    public CookieSpec newInstance(final HttpParams httpParams) {
        if (httpParams != null) {
            String[] array = null;
            final Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
            if (collection != null) {
                array = collection.toArray(new String[collection.size()]);
            }
            return new BestMatchSpec(array, httpParams.getBooleanParameter("http.protocol.single-cookie-header", false));
        }
        return new BestMatchSpec();
    }
    
    public CookieSpec create(final HttpContext httpContext) {
        return new BestMatchSpec(this.datepatterns, this.oneHeader);
    }
}
