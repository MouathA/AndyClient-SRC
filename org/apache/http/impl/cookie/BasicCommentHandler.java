package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class BasicCommentHandler extends AbstractCookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String comment) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        setCookie.setComment(comment);
    }
}
