package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;
import org.apache.http.message.*;

@NotThreadSafe
public class RFC2965Spec extends RFC2109Spec
{
    public RFC2965Spec() {
        this(null, false);
    }
    
    public RFC2965Spec(final String[] array, final boolean b) {
        super(array, b);
        this.registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
        this.registerAttribHandler("port", new RFC2965PortAttributeHandler());
        this.registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
        this.registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
        this.registerAttribHandler("version", new RFC2965VersionAttributeHandler());
    }
    
    @Override
    public List parse(final Header header, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie2")) {
            throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
        }
        return this.createCookies(header.getElements(), adjustEffectiveHost(cookieOrigin));
    }
    
    @Override
    protected List parse(final HeaderElement[] array, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        return this.createCookies(array, adjustEffectiveHost(cookieOrigin));
    }
    
    private List createCookies(final HeaderElement[] array, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        final ArrayList<BasicClientCookie2> list = new ArrayList<BasicClientCookie2>(array.length);
        while (0 < array.length) {
            final HeaderElement headerElement = array[0];
            final String name = headerElement.getName();
            final String value = headerElement.getValue();
            if (name == null || name.length() == 0) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            final BasicClientCookie2 basicClientCookie2 = new BasicClientCookie2(name, value);
            basicClientCookie2.setPath(CookieSpecBase.getDefaultPath(cookieOrigin));
            basicClientCookie2.setDomain(CookieSpecBase.getDefaultDomain(cookieOrigin));
            basicClientCookie2.setPorts(new int[] { cookieOrigin.getPort() });
            final NameValuePair[] parameters = headerElement.getParameters();
            final HashMap hashMap = new HashMap<Object, Object>(parameters.length);
            for (int i = parameters.length - 1; i >= 0; --i) {
                final NameValuePair nameValuePair = parameters[i];
                hashMap.put(nameValuePair.getName().toLowerCase(Locale.ENGLISH), nameValuePair);
            }
            final Iterator<Map.Entry<String, NameValuePair>> iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                final NameValuePair nameValuePair2 = iterator.next().getValue();
                final String lowerCase = nameValuePair2.getName().toLowerCase(Locale.ENGLISH);
                basicClientCookie2.setAttribute(lowerCase, nameValuePair2.getValue());
                final CookieAttributeHandler attribHandler = this.findAttribHandler(lowerCase);
                if (attribHandler != null) {
                    attribHandler.parse(basicClientCookie2, nameValuePair2.getValue());
                }
            }
            list.add(basicClientCookie2);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    @Override
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        super.validate(cookie, adjustEffectiveHost(cookieOrigin));
    }
    
    @Override
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return super.match(cookie, adjustEffectiveHost(cookieOrigin));
    }
    
    @Override
    protected void formatCookieAsVer(final CharArrayBuffer charArrayBuffer, final Cookie cookie, final int n) {
        super.formatCookieAsVer(charArrayBuffer, cookie, n);
        if (cookie instanceof ClientCookie) {
            final String attribute = ((ClientCookie)cookie).getAttribute("port");
            if (attribute != null) {
                charArrayBuffer.append("; $Port");
                charArrayBuffer.append("=\"");
                if (attribute.trim().length() > 0) {
                    final int[] ports = cookie.getPorts();
                    if (ports != null) {
                        while (0 < ports.length) {
                            if (0 > 0) {
                                charArrayBuffer.append(",");
                            }
                            charArrayBuffer.append(Integer.toString(ports[0]));
                            int n2 = 0;
                            ++n2;
                        }
                    }
                }
                charArrayBuffer.append("\"");
            }
        }
    }
    
    private static CookieOrigin adjustEffectiveHost(final CookieOrigin cookieOrigin) {
        final String host = cookieOrigin.getHost();
        while (0 < host.length()) {
            final char char1 = host.charAt(0);
            if (char1 == '.' || char1 == ':') {
                break;
            }
            int n = 0;
            ++n;
        }
        if (false) {
            return new CookieOrigin(host + ".local", cookieOrigin.getPort(), cookieOrigin.getPath(), cookieOrigin.isSecure());
        }
        return cookieOrigin;
    }
    
    @Override
    public int getVersion() {
        return 1;
    }
    
    @Override
    public Header getVersionHeader() {
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
        charArrayBuffer.append("Cookie2");
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(this.getVersion()));
        return new BufferedHeader(charArrayBuffer);
    }
    
    @Override
    public String toString() {
        return "rfc2965";
    }
}
