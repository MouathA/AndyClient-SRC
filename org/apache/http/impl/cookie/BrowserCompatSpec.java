package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.message.*;

@NotThreadSafe
public class BrowserCompatSpec extends CookieSpecBase
{
    private final String[] datepatterns;
    
    public BrowserCompatSpec(final String[] array, final BrowserCompatSpecFactory.SecurityLevel securityLevel) {
        if (array != null) {
            this.datepatterns = array.clone();
        }
        else {
            this.datepatterns = BrowserCompatSpec.DEFAULT_DATE_PATTERNS;
        }
        switch (securityLevel) {
            case SECURITYLEVEL_DEFAULT: {
                this.registerAttribHandler("path", new BasicPathHandler());
                break;
            }
            case SECURITYLEVEL_IE_MEDIUM: {
                this.registerAttribHandler("path", new BasicPathHandler() {
                    final BrowserCompatSpec this$0;
                    
                    @Override
                    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
                    }
                });
                break;
            }
            default: {
                throw new RuntimeException("Unknown security level");
            }
        }
        this.registerAttribHandler("domain", new BasicDomainHandler());
        this.registerAttribHandler("max-age", new BasicMaxAgeHandler());
        this.registerAttribHandler("secure", new BasicSecureHandler());
        this.registerAttribHandler("comment", new BasicCommentHandler());
        this.registerAttribHandler("expires", new BasicExpiresHandler(this.datepatterns));
        this.registerAttribHandler("version", new BrowserCompatVersionAttributeHandler());
    }
    
    public BrowserCompatSpec(final String[] array) {
        this(array, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }
    
    public BrowserCompatSpec() {
        this(null, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }
    
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
        final HeaderElement[] elements;
        HeaderElement[] array = elements = header.getElements();
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
            array = new HeaderElement[] { default1.parseHeader(buffer, parserCursor) };
        }
        return this.parse(array, cookieOrigin);
    }
    
    private static boolean isQuoteEnclosed(final String s) {
        return s != null && s.startsWith("\"") && s.endsWith("\"");
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
            final String name = cookie.getName();
            final String value = cookie.getValue();
            if (cookie.getVersion() > 0 && !isQuoteEnclosed(value)) {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(charArrayBuffer, new BasicHeaderElement(name, value), false);
            }
            else {
                charArrayBuffer.append(name);
                charArrayBuffer.append("=");
                if (value != null) {
                    charArrayBuffer.append(value);
                }
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
        return "compatibility";
    }
    
    static {
        BrowserCompatSpec.DEFAULT_DATE_PATTERNS = new String[] { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z" };
    }
}
