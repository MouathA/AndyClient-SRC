package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.protocol.*;

@Immutable
public class RFC2109SpecFactory implements CookieSpecFactory, CookieSpecProvider
{
    private final String[] datepatterns;
    private final boolean oneHeader;
    
    public RFC2109SpecFactory(final String[] datepatterns, final boolean oneHeader) {
        this.datepatterns = datepatterns;
        this.oneHeader = oneHeader;
    }
    
    public RFC2109SpecFactory() {
        this(null, false);
    }
    
    public CookieSpec newInstance(final HttpParams httpParams) {
        if (httpParams != null) {
            String[] array = null;
            final Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
            if (collection != null) {
                array = collection.toArray(new String[collection.size()]);
            }
            return new RFC2109Spec(array, httpParams.getBooleanParameter("http.protocol.single-cookie-header", false));
        }
        return new RFC2109Spec();
    }
    
    public CookieSpec create(final HttpContext httpContext) {
        return new RFC2109Spec(this.datepatterns, this.oneHeader);
    }
}
