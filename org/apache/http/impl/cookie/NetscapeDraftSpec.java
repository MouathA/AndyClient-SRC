package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.message.*;

@NotThreadSafe
public class NetscapeDraftSpec extends CookieSpecBase
{
    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";
    private final String[] datepatterns;
    
    public NetscapeDraftSpec(final String[] array) {
        if (array != null) {
            this.datepatterns = array.clone();
        }
        else {
            this.datepatterns = new String[] { "EEE, dd-MMM-yy HH:mm:ss z" };
        }
        this.registerAttribHandler("path", new BasicPathHandler());
        this.registerAttribHandler("domain", new NetscapeDomainHandler());
        this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
        this.registerAttribHandler("secure", new BasicSecureHandler());
        this.registerAttribHandler("comment", new BasicCommentHandler());
        this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
    }
    
    public NetscapeDraftSpec() {
        this(null);
    }
    
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
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
        return this.parse(new HeaderElement[] { default1.parseHeader(buffer, parserCursor) }, cookieOrigin);
    }
    
    public List formatCookies(final List list) {
        Args.notEmpty(list, "List of cookies");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(20 * list.size());
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        while (0 < list.size()) {
            final Cookie cookie = list.get(0);
            if (0 > 0) {
                charArrayBuffer.append("; ");
            }
            charArrayBuffer.append(cookie.getName());
            final String value = cookie.getValue();
            if (value != null) {
                charArrayBuffer.append("=");
                charArrayBuffer.append(value);
            }
            int n = 0;
            ++n;
        }
        final ArrayList<BufferedHeader> list2 = new ArrayList<BufferedHeader>(1);
        list2.add(new BufferedHeader(charArrayBuffer));
        return list2;
    }
    
    public int getVersion() {
        return 0;
    }
    
    public Header getVersionHeader() {
        return null;
    }
    
    @Override
    public String toString() {
        return "netscape";
    }
}
