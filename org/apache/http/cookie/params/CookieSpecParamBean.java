package org.apache.http.cookie.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import java.util.*;

@Deprecated
@NotThreadSafe
public class CookieSpecParamBean extends HttpAbstractParamBean
{
    public CookieSpecParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setDatePatterns(final Collection collection) {
        this.params.setParameter("http.protocol.cookie-datepatterns", collection);
    }
    
    public void setSingleHeader(final boolean b) {
        this.params.setBooleanParameter("http.protocol.single-cookie-header", b);
    }
}
