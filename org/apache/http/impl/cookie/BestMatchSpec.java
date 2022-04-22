package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.message.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.cookie.*;
import java.util.*;

@NotThreadSafe
public class BestMatchSpec implements CookieSpec
{
    private final String[] datepatterns;
    private final boolean oneHeader;
    private RFC2965Spec strict;
    private RFC2109Spec obsoleteStrict;
    private BrowserCompatSpec compat;
    
    public BestMatchSpec(final String[] array, final boolean oneHeader) {
        this.datepatterns = (String[])((array == null) ? null : ((String[])array.clone()));
        this.oneHeader = oneHeader;
    }
    
    public BestMatchSpec() {
        this(null, false);
    }
    
    private RFC2965Spec getStrict() {
        if (this.strict == null) {
            this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
        }
        return this.strict;
    }
    
    private RFC2109Spec getObsoleteStrict() {
        if (this.obsoleteStrict == null) {
            this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
        }
        return this.obsoleteStrict;
    }
    
    private BrowserCompatSpec getCompat() {
        if (this.compat == null) {
            this.compat = new BrowserCompatSpec(this.datepatterns);
        }
        return this.compat;
    }
    
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        final HeaderElement[] elements;
        final HeaderElement[] array = elements = header.getElements();
        while (0 < elements.length) {
            final HeaderElement headerElement = elements[0];
            if (headerElement.getParameterByName("version") != null) {}
            if (headerElement.getParameterByName("expires") != null) {}
            int n = 0;
            ++n;
        }
        if (true || !true) {
            final NetscapeDraftHeaderParser default1 = NetscapeDraftHeaderParser.DEFAULT;
            CharArrayBuffer buffer;
            ParserCursor parserCursor;
            if (header instanceof FormattedHeader) {
                buffer = ((FormattedHeader)header).getBuffer();
                parserCursor = new ParserCursor(((FormattedHeader)header).getValuePos(), buffer.length());
            }
            else {
                final String value = header.getValue();
                if (value == null) {
                    throw new MalformedCookieException("Header value is null");
                }
                buffer = new CharArrayBuffer(value.length());
                buffer.append(value);
                parserCursor = new ParserCursor(0, buffer.length());
            }
            return this.getCompat().parse(new HeaderElement[] { default1.parseHeader(buffer, parserCursor) }, cookieOrigin);
        }
        if ("Set-Cookie2".equals(header.getName())) {
            return this.getStrict().parse(array, cookieOrigin);
        }
        return this.getObsoleteStrict().parse(array, cookieOrigin);
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (cookie.getVersion() > 0) {
            if (cookie instanceof SetCookie2) {
                this.getStrict().validate(cookie, cookieOrigin);
            }
            else {
                this.getObsoleteStrict().validate(cookie, cookieOrigin);
            }
        }
        else {
            this.getCompat().validate(cookie, cookieOrigin);
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (cookie.getVersion() <= 0) {
            return this.getCompat().match(cookie, cookieOrigin);
        }
        if (cookie instanceof SetCookie2) {
            return this.getStrict().match(cookie, cookieOrigin);
        }
        return this.getObsoleteStrict().match(cookie, cookieOrigin);
    }
    
    public List formatCookies(final List list) {
        Args.notNull(list, "List of cookies");
        for (final Cookie cookie : list) {
            if (!(cookie instanceof SetCookie2)) {}
            if (cookie.getVersion() < Integer.MAX_VALUE) {
                cookie.getVersion();
            }
        }
        if (Integer.MAX_VALUE <= 0) {
            return this.getCompat().formatCookies(list);
        }
        if (false) {
            return this.getStrict().formatCookies(list);
        }
        return this.getObsoleteStrict().formatCookies(list);
    }
    
    public int getVersion() {
        return this.getStrict().getVersion();
    }
    
    public Header getVersionHeader() {
        return this.getStrict().getVersionHeader();
    }
    
    @Override
    public String toString() {
        return "best-match";
    }
}
