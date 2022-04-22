package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.cookie.*;

@NotThreadSafe
public class IgnoreSpec extends CookieSpecBase
{
    public int getVersion() {
        return 0;
    }
    
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        return Collections.emptyList();
    }
    
    public List formatCookies(final List list) {
        return Collections.emptyList();
    }
    
    public Header getVersionHeader() {
        return null;
    }
}
