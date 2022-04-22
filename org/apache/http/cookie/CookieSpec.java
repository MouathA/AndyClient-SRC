package org.apache.http.cookie;

import org.apache.http.*;
import java.util.*;

public interface CookieSpec
{
    int getVersion();
    
    List parse(final Header p0, final CookieOrigin p1) throws MalformedCookieException;
    
    void validate(final Cookie p0, final CookieOrigin p1) throws MalformedCookieException;
    
    boolean match(final Cookie p0, final CookieOrigin p1);
    
    List formatCookies(final List p0);
    
    Header getVersionHeader();
}
