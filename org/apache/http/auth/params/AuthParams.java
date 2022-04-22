package org.apache.http.auth.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;

@Deprecated
@Immutable
public final class AuthParams
{
    private AuthParams() {
    }
    
    public static String getCredentialCharset(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String name = (String)httpParams.getParameter("http.auth.credential-charset");
        if (name == null) {
            name = HTTP.DEF_PROTOCOL_CHARSET.name();
        }
        return name;
    }
    
    public static void setCredentialCharset(final HttpParams httpParams, final String s) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.auth.credential-charset", s);
    }
}
