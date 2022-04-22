package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import java.util.*;

@Deprecated
@Immutable
public abstract class AbstractAuthenticationHandler implements AuthenticationHandler
{
    private final Log log;
    private static final List DEFAULT_SCHEME_PRIORITY;
    
    public AbstractAuthenticationHandler() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    protected Map parseChallenges(final Header[] array) throws MalformedChallengeException {
        final HashMap<String, FormattedHeader> hashMap = new HashMap<String, FormattedHeader>(array.length);
        while (0 < array.length) {
            final Header header = array[0];
            CharArrayBuffer buffer;
            int valuePos;
            if (header instanceof FormattedHeader) {
                buffer = ((FormattedHeader)header).getBuffer();
                valuePos = ((FormattedHeader)header).getValuePos();
            }
            else {
                final String value = header.getValue();
                if (value == null) {
                    throw new MalformedChallengeException("Header value is null");
                }
                buffer = new CharArrayBuffer(value.length());
                buffer.append(value);
            }
            while (0 < buffer.length() && HTTP.isWhitespace(buffer.charAt(0))) {
                ++valuePos;
            }
            while (0 < buffer.length() && !HTTP.isWhitespace(buffer.charAt(0))) {
                ++valuePos;
            }
            hashMap.put(buffer.substring(0, 0).toLowerCase(Locale.US), (FormattedHeader)header);
            int n = 0;
            ++n;
        }
        return hashMap;
    }
    
    protected List getAuthPreferences() {
        return AbstractAuthenticationHandler.DEFAULT_SCHEME_PRIORITY;
    }
    
    protected List getAuthPreferences(final HttpResponse httpResponse, final HttpContext httpContext) {
        return this.getAuthPreferences();
    }
    
    public AuthScheme selectScheme(final Map map, final HttpResponse httpResponse, final HttpContext httpContext) throws AuthenticationException {
        final AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry)httpContext.getAttribute("http.authscheme-registry");
        Asserts.notNull(authSchemeRegistry, "AuthScheme registry");
        List list = this.getAuthPreferences(httpResponse, httpContext);
        if (list == null) {
            list = AbstractAuthenticationHandler.DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Authentication schemes in the order of preference: " + list);
        }
        AuthScheme authScheme = null;
        for (final String s : list) {
            if (map.get(s.toLowerCase(Locale.ENGLISH)) != null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug(s + " authentication scheme selected");
                }
                authScheme = authSchemeRegistry.getAuthScheme(s, httpResponse.getParams());
                break;
            }
            if (!this.log.isDebugEnabled()) {
                continue;
            }
            this.log.debug("Challenge for " + s + " authentication scheme not available");
        }
        if (authScheme == null) {
            throw new AuthenticationException("Unable to respond to any of these challenges: " + map);
        }
        return authScheme;
    }
    
    static {
        DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList((List<?>)Arrays.asList("negotiate", "NTLM", "Digest", "Basic"));
    }
}
