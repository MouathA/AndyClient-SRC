package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;
import org.apache.http.message.*;
import java.util.*;
import org.apache.http.cookie.*;

@NotThreadSafe
public class RFC2109Spec extends CookieSpecBase
{
    private static final CookiePathComparator PATH_COMPARATOR;
    private final String[] datepatterns;
    private final boolean oneHeader;
    
    public RFC2109Spec(final String[] array, final boolean oneHeader) {
        if (array != null) {
            this.datepatterns = array.clone();
        }
        else {
            this.datepatterns = RFC2109Spec.DATE_PATTERNS;
        }
        this.oneHeader = oneHeader;
        this.registerAttribHandler("version", new RFC2109VersionHandler());
        this.registerAttribHandler("path", new BasicPathHandler());
        this.registerAttribHandler("domain", new RFC2109DomainHandler());
        this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
        this.registerAttribHandler("secure", new BasicSecureHandler());
        this.registerAttribHandler("comment", new BasicCommentHandler());
        this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
    }
    
    public RFC2109Spec() {
        this(null, false);
    }
    
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
        return this.parse(header.getElements(), cookieOrigin);
    }
    
    @Override
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        final String name = cookie.getName();
        if (name.indexOf(32) != -1) {
            throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
        }
        if (name.startsWith("$")) {
            throw new CookieRestrictionViolationException("Cookie name may not start with $");
        }
        super.validate(cookie, cookieOrigin);
    }
    
    public List formatCookies(final List list) {
        Args.notEmpty(list, "List of cookies");
        ArrayList<Object> list2;
        if (list.size() > 1) {
            list2 = new ArrayList<Object>(list);
            Collections.sort(list2, RFC2109Spec.PATH_COMPARATOR);
        }
        else {
            list2 = (ArrayList<Object>)list;
        }
        if (this.oneHeader) {
            return this.doFormatOneHeader(list2);
        }
        return this.doFormatManyHeaders(list2);
    }
    
    private List doFormatOneHeader(final List list) {
        for (final Cookie cookie : list) {
            if (cookie.getVersion() < Integer.MAX_VALUE) {
                cookie.getVersion();
            }
        }
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40 * list.size());
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(Integer.MAX_VALUE));
        for (final Cookie cookie2 : list) {
            charArrayBuffer.append("; ");
            this.formatCookieAsVer(charArrayBuffer, cookie2, Integer.MAX_VALUE);
        }
        final ArrayList<BufferedHeader> list2 = new ArrayList<BufferedHeader>(1);
        list2.add(new BufferedHeader(charArrayBuffer));
        return list2;
    }
    
    private List doFormatManyHeaders(final List list) {
        final ArrayList<BufferedHeader> list2 = new ArrayList<BufferedHeader>(list.size());
        for (final Cookie cookie : list) {
            final int version = cookie.getVersion();
            final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
            charArrayBuffer.append("Cookie: ");
            charArrayBuffer.append("$Version=");
            charArrayBuffer.append(Integer.toString(version));
            charArrayBuffer.append("; ");
            this.formatCookieAsVer(charArrayBuffer, cookie, version);
            list2.add(new BufferedHeader(charArrayBuffer));
        }
        return list2;
    }
    
    protected void formatParamAsVer(final CharArrayBuffer charArrayBuffer, final String s, final String s2, final int n) {
        charArrayBuffer.append(s);
        charArrayBuffer.append("=");
        if (s2 != null) {
            if (n > 0) {
                charArrayBuffer.append('\"');
                charArrayBuffer.append(s2);
                charArrayBuffer.append('\"');
            }
            else {
                charArrayBuffer.append(s2);
            }
        }
    }
    
    protected void formatCookieAsVer(final CharArrayBuffer charArrayBuffer, final Cookie cookie, final int n) {
        this.formatParamAsVer(charArrayBuffer, cookie.getName(), cookie.getValue(), n);
        if (cookie.getPath() != null && cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("path")) {
            charArrayBuffer.append("; ");
            this.formatParamAsVer(charArrayBuffer, "$Path", cookie.getPath(), n);
        }
        if (cookie.getDomain() != null && cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("domain")) {
            charArrayBuffer.append("; ");
            this.formatParamAsVer(charArrayBuffer, "$Domain", cookie.getDomain(), n);
        }
    }
    
    public int getVersion() {
        return 1;
    }
    
    public Header getVersionHeader() {
        return null;
    }
    
    @Override
    public String toString() {
        return "rfc2109";
    }
    
    static {
        PATH_COMPARATOR = new CookiePathComparator();
        RFC2109Spec.DATE_PATTERNS = new String[] { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
    }
}
