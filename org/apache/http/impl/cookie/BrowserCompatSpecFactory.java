package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.protocol.*;

@Immutable
public class BrowserCompatSpecFactory implements CookieSpecFactory, CookieSpecProvider
{
    private final String[] datepatterns;
    private final SecurityLevel securityLevel;
    
    public BrowserCompatSpecFactory(final String[] datepatterns, final SecurityLevel securityLevel) {
        this.datepatterns = datepatterns;
        this.securityLevel = securityLevel;
    }
    
    public BrowserCompatSpecFactory(final String[] array) {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }
    
    public BrowserCompatSpecFactory() {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }
    
    public CookieSpec newInstance(final HttpParams httpParams) {
        if (httpParams != null) {
            String[] array = null;
            final Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
            if (collection != null) {
                array = collection.toArray(new String[collection.size()]);
            }
            return new BrowserCompatSpec(array, this.securityLevel);
        }
        return new BrowserCompatSpec(null, this.securityLevel);
    }
    
    public CookieSpec create(final HttpContext httpContext) {
        return new BrowserCompatSpec(this.datepatterns);
    }
    
    public enum SecurityLevel
    {
        SECURITYLEVEL_DEFAULT("SECURITYLEVEL_DEFAULT", 0), 
        SECURITYLEVEL_IE_MEDIUM("SECURITYLEVEL_IE_MEDIUM", 1);
        
        private static final SecurityLevel[] $VALUES;
        
        private SecurityLevel(final String s, final int n) {
        }
        
        static {
            $VALUES = new SecurityLevel[] { SecurityLevel.SECURITYLEVEL_DEFAULT, SecurityLevel.SECURITYLEVEL_IE_MEDIUM };
        }
    }
}
