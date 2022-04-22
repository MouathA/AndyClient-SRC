package org.apache.http.impl.client;

import org.apache.http.annotation.*;

@Immutable
public class LaxRedirectStrategy extends DefaultRedirectStrategy
{
    @Override
    protected boolean isRedirectable(final String s) {
        final String[] redirect_METHODS = LaxRedirectStrategy.REDIRECT_METHODS;
        while (0 < redirect_METHODS.length) {
            if (redirect_METHODS[0].equalsIgnoreCase(s)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    static {
        LaxRedirectStrategy.REDIRECT_METHODS = new String[] { "GET", "POST", "HEAD" };
    }
}
