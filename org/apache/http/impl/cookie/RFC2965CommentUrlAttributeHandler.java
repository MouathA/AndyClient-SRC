package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2965CommentUrlAttributeHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String commentURL) throws MalformedCookieException {
        if (setCookie instanceof SetCookie2) {
            ((SetCookie2)setCookie).setCommentURL(commentURL);
        }
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        return true;
    }
}
