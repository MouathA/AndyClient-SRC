package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.client.config.*;
import org.apache.http.client.protocol.*;
import org.apache.http.config.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import java.util.*;

@Immutable
abstract class AuthenticationStrategyImpl implements AuthenticationStrategy
{
    private final Log log;
    private static final List DEFAULT_SCHEME_PRIORITY;
    private final int challengeCode;
    private final String headerName;
    
    AuthenticationStrategyImpl(final int challengeCode, final String headerName) {
        this.log = LogFactory.getLog(this.getClass());
        this.challengeCode = challengeCode;
        this.headerName = headerName;
    }
    
    public boolean isAuthenticationRequested(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        return httpResponse.getStatusLine().getStatusCode() == this.challengeCode;
    }
    
    public Map getChallenges(final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(httpResponse, "HTTP response");
        final Header[] headers = httpResponse.getHeaders(this.headerName);
        final HashMap hashMap = new HashMap<String, FormattedHeader>(headers.length);
        final Header[] array = headers;
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
    
    abstract Collection getPreferredAuthSchemes(final RequestConfig p0);
    
    public Queue select(final Map map, final HttpHost httpHost, final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httpHost, "Host");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final LinkedList<AuthOption> list = new LinkedList<AuthOption>();
        final Lookup authSchemeRegistry = adapt.getAuthSchemeRegistry();
        if (authSchemeRegistry == null) {
            this.log.debug("Auth scheme registry not set in the context");
            return list;
        }
        final CredentialsProvider credentialsProvider = adapt.getCredentialsProvider();
        if (credentialsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return list;
        }
        Collection collection = this.getPreferredAuthSchemes(adapt.getRequestConfig());
        if (collection == null) {
            collection = AuthenticationStrategyImpl.DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Authentication schemes in the order of preference: " + collection);
        }
        for (final String s : collection) {
            final Header header = map.get(s.toLowerCase(Locale.US));
            if (header != null) {
                final AuthSchemeProvider authSchemeProvider = (AuthSchemeProvider)authSchemeRegistry.lookup(s);
                if (authSchemeProvider == null) {
                    if (!this.log.isWarnEnabled()) {
                        continue;
                    }
                    this.log.warn("Authentication scheme " + s + " not supported");
                }
                else {
                    final AuthScheme create = authSchemeProvider.create(httpContext);
                    create.processChallenge(header);
                    final Credentials credentials = credentialsProvider.getCredentials(new AuthScope(httpHost.getHostName(), httpHost.getPort(), create.getRealm(), create.getSchemeName()));
                    if (credentials == null) {
                        continue;
                    }
                    list.add(new AuthOption(create, credentials));
                }
            }
            else {
                if (!this.log.isDebugEnabled()) {
                    continue;
                }
                this.log.debug("Challenge for " + s + " authentication scheme not available");
            }
        }
        return list;
    }
    
    public void authSucceeded(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        Args.notNull(httpHost, "Host");
        Args.notNull(authScheme, "Auth scheme");
        Args.notNull(httpContext, "HTTP context");
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        if (this.isCachable(authScheme)) {
            AuthCache authCache = adapt.getAuthCache();
            if (authCache == null) {
                authCache = new BasicAuthCache();
                adapt.setAuthCache(authCache);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
            }
            authCache.put(httpHost, authScheme);
        }
    }
    
    protected boolean isCachable(final AuthScheme authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        final String schemeName = authScheme.getSchemeName();
        return schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest");
    }
    
    public void authFailed(final HttpHost httpHost, final AuthScheme authScheme, final HttpContext httpContext) {
        Args.notNull(httpHost, "Host");
        Args.notNull(httpContext, "HTTP context");
        final AuthCache authCache = HttpClientContext.adapt(httpContext).getAuthCache();
        if (authCache != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Clearing cached auth scheme for " + httpHost);
            }
            authCache.remove(httpHost);
        }
    }
    
    static {
        DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList((List<?>)Arrays.asList("negotiate", "Kerberos", "NTLM", "Digest", "Basic"));
    }
}
