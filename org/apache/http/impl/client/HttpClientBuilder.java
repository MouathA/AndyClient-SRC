package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.client.*;
import org.apache.http.client.config.*;
import org.apache.http.*;
import java.io.*;
import javax.net.ssl.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.config.*;
import org.apache.http.conn.socket.*;
import org.apache.http.impl.*;
import org.apache.http.protocol.*;
import org.apache.http.client.protocol.*;
import java.net.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.execchain.*;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.cookie.*;
import java.util.*;
import org.apache.http.util.*;

@NotThreadSafe
public class HttpClientBuilder
{
    private HttpRequestExecutor requestExec;
    private X509HostnameVerifier hostnameVerifier;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private SSLContext sslcontext;
    private HttpClientConnectionManager connManager;
    private SchemePortResolver schemePortResolver;
    private ConnectionReuseStrategy reuseStrategy;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private AuthenticationStrategy targetAuthStrategy;
    private AuthenticationStrategy proxyAuthStrategy;
    private UserTokenHandler userTokenHandler;
    private HttpProcessor httpprocessor;
    private LinkedList requestFirst;
    private LinkedList requestLast;
    private LinkedList responseFirst;
    private LinkedList responseLast;
    private HttpRequestRetryHandler retryHandler;
    private HttpRoutePlanner routePlanner;
    private RedirectStrategy redirectStrategy;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private BackoffManager backoffManager;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private Lookup authSchemeRegistry;
    private Lookup cookieSpecRegistry;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private String userAgent;
    private HttpHost proxy;
    private Collection defaultHeaders;
    private SocketConfig defaultSocketConfig;
    private ConnectionConfig defaultConnectionConfig;
    private RequestConfig defaultRequestConfig;
    private boolean systemProperties;
    private boolean redirectHandlingDisabled;
    private boolean automaticRetriesDisabled;
    private boolean contentCompressionDisabled;
    private boolean cookieManagementDisabled;
    private boolean authCachingDisabled;
    private boolean connectionStateDisabled;
    private int maxConnTotal;
    private int maxConnPerRoute;
    private List closeables;
    static final String DEFAULT_USER_AGENT;
    
    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }
    
    protected HttpClientBuilder() {
        this.maxConnTotal = 0;
        this.maxConnPerRoute = 0;
    }
    
    public final HttpClientBuilder setRequestExecutor(final HttpRequestExecutor requestExec) {
        this.requestExec = requestExec;
        return this;
    }
    
    public final HttpClientBuilder setHostnameVerifier(final X509HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }
    
    public final HttpClientBuilder setSslcontext(final SSLContext sslcontext) {
        this.sslcontext = sslcontext;
        return this;
    }
    
    public final HttpClientBuilder setSSLSocketFactory(final LayeredConnectionSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }
    
    public final HttpClientBuilder setMaxConnTotal(final int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
        return this;
    }
    
    public final HttpClientBuilder setMaxConnPerRoute(final int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
        return this;
    }
    
    public final HttpClientBuilder setDefaultSocketConfig(final SocketConfig defaultSocketConfig) {
        this.defaultSocketConfig = defaultSocketConfig;
        return this;
    }
    
    public final HttpClientBuilder setDefaultConnectionConfig(final ConnectionConfig defaultConnectionConfig) {
        this.defaultConnectionConfig = defaultConnectionConfig;
        return this;
    }
    
    public final HttpClientBuilder setConnectionManager(final HttpClientConnectionManager connManager) {
        this.connManager = connManager;
        return this;
    }
    
    public final HttpClientBuilder setConnectionReuseStrategy(final ConnectionReuseStrategy reuseStrategy) {
        this.reuseStrategy = reuseStrategy;
        return this;
    }
    
    public final HttpClientBuilder setKeepAliveStrategy(final ConnectionKeepAliveStrategy keepAliveStrategy) {
        this.keepAliveStrategy = keepAliveStrategy;
        return this;
    }
    
    public final HttpClientBuilder setTargetAuthenticationStrategy(final AuthenticationStrategy targetAuthStrategy) {
        this.targetAuthStrategy = targetAuthStrategy;
        return this;
    }
    
    public final HttpClientBuilder setProxyAuthenticationStrategy(final AuthenticationStrategy proxyAuthStrategy) {
        this.proxyAuthStrategy = proxyAuthStrategy;
        return this;
    }
    
    public final HttpClientBuilder setUserTokenHandler(final UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
        return this;
    }
    
    public final HttpClientBuilder disableConnectionState() {
        this.connectionStateDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder setSchemePortResolver(final SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver;
        return this;
    }
    
    public final HttpClientBuilder setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
    
    public final HttpClientBuilder setDefaultHeaders(final Collection defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
        return this;
    }
    
    public final HttpClientBuilder addInterceptorFirst(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }
    
    public final HttpClientBuilder addInterceptorLast(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }
    
    public final HttpClientBuilder addInterceptorFirst(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }
    
    public final HttpClientBuilder addInterceptorLast(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }
    
    public final HttpClientBuilder disableCookieManagement() {
        this.cookieManagementDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder disableContentCompression() {
        this.contentCompressionDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder disableAuthCaching() {
        this.authCachingDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder setHttpProcessor(final HttpProcessor httpprocessor) {
        this.httpprocessor = httpprocessor;
        return this;
    }
    
    public final HttpClientBuilder setRetryHandler(final HttpRequestRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
        return this;
    }
    
    public final HttpClientBuilder disableAutomaticRetries() {
        this.automaticRetriesDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder setProxy(final HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }
    
    public final HttpClientBuilder setRoutePlanner(final HttpRoutePlanner routePlanner) {
        this.routePlanner = routePlanner;
        return this;
    }
    
    public final HttpClientBuilder setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
        return this;
    }
    
    public final HttpClientBuilder disableRedirectHandling() {
        this.redirectHandlingDisabled = true;
        return this;
    }
    
    public final HttpClientBuilder setConnectionBackoffStrategy(final ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
        return this;
    }
    
    public final HttpClientBuilder setBackoffManager(final BackoffManager backoffManager) {
        this.backoffManager = backoffManager;
        return this;
    }
    
    public final HttpClientBuilder setServiceUnavailableRetryStrategy(final ServiceUnavailableRetryStrategy serviceUnavailStrategy) {
        this.serviceUnavailStrategy = serviceUnavailStrategy;
        return this;
    }
    
    public final HttpClientBuilder setDefaultCookieStore(final CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }
    
    public final HttpClientBuilder setDefaultCredentialsProvider(final CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        return this;
    }
    
    public final HttpClientBuilder setDefaultAuthSchemeRegistry(final Lookup authSchemeRegistry) {
        this.authSchemeRegistry = authSchemeRegistry;
        return this;
    }
    
    public final HttpClientBuilder setDefaultCookieSpecRegistry(final Lookup cookieSpecRegistry) {
        this.cookieSpecRegistry = cookieSpecRegistry;
        return this;
    }
    
    public final HttpClientBuilder setDefaultRequestConfig(final RequestConfig defaultRequestConfig) {
        this.defaultRequestConfig = defaultRequestConfig;
        return this;
    }
    
    public final HttpClientBuilder useSystemProperties() {
        this.systemProperties = true;
        return this;
    }
    
    protected ClientExecChain decorateMainExec(final ClientExecChain clientExecChain) {
        return clientExecChain;
    }
    
    protected ClientExecChain decorateProtocolExec(final ClientExecChain clientExecChain) {
        return clientExecChain;
    }
    
    protected void addCloseable(final Closeable closeable) {
        if (closeable == null) {
            return;
        }
        if (this.closeables == null) {
            this.closeables = new ArrayList();
        }
        this.closeables.add(closeable);
    }
    
    private static String[] split(final String s) {
        if (TextUtils.isBlank(s)) {
            return null;
        }
        return s.split(" *, *");
    }
    
    public CloseableHttpClient build() {
        HttpRequestExecutor requestExec = this.requestExec;
        if (requestExec == null) {
            requestExec = new HttpRequestExecutor();
        }
        HttpClientConnectionManager connManager = this.connManager;
        if (connManager == null) {
            LayeredConnectionSocketFactory sslSocketFactory = this.sslSocketFactory;
            if (sslSocketFactory == null) {
                final String[] array = (String[])(this.systemProperties ? split(System.getProperty("https.protocols")) : null);
                final String[] array2 = (String[])(this.systemProperties ? split(System.getProperty("https.cipherSuites")) : null);
                X509HostnameVerifier x509HostnameVerifier = this.hostnameVerifier;
                if (x509HostnameVerifier == null) {
                    x509HostnameVerifier = SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
                }
                if (this.sslcontext != null) {
                    sslSocketFactory = new SSLConnectionSocketFactory(this.sslcontext, array, array2, x509HostnameVerifier);
                }
                else if (this.systemProperties) {
                    sslSocketFactory = new SSLConnectionSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault(), array, array2, x509HostnameVerifier);
                }
                else {
                    sslSocketFactory = new SSLConnectionSocketFactory(SSLContexts.createDefault(), x509HostnameVerifier);
                }
            }
            final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build());
            if (this.defaultSocketConfig != null) {
                poolingHttpClientConnectionManager.setDefaultSocketConfig(this.defaultSocketConfig);
            }
            if (this.defaultConnectionConfig != null) {
                poolingHttpClientConnectionManager.setDefaultConnectionConfig(this.defaultConnectionConfig);
            }
            if (this.systemProperties && "true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                final int int1 = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
                poolingHttpClientConnectionManager.setDefaultMaxPerRoute(int1);
                poolingHttpClientConnectionManager.setMaxTotal(2 * int1);
            }
            if (this.maxConnTotal > 0) {
                poolingHttpClientConnectionManager.setMaxTotal(this.maxConnTotal);
            }
            if (this.maxConnPerRoute > 0) {
                poolingHttpClientConnectionManager.setDefaultMaxPerRoute(this.maxConnPerRoute);
            }
            connManager = poolingHttpClientConnectionManager;
        }
        ConnectionReuseStrategy connectionReuseStrategy = this.reuseStrategy;
        if (connectionReuseStrategy == null) {
            if (this.systemProperties) {
                if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                    connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
                }
                else {
                    connectionReuseStrategy = NoConnectionReuseStrategy.INSTANCE;
                }
            }
            else {
                connectionReuseStrategy = DefaultConnectionReuseStrategy.INSTANCE;
            }
        }
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = this.keepAliveStrategy;
        if (connectionKeepAliveStrategy == null) {
            connectionKeepAliveStrategy = DefaultConnectionKeepAliveStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy = this.targetAuthStrategy;
        if (authenticationStrategy == null) {
            authenticationStrategy = TargetAuthenticationStrategy.INSTANCE;
        }
        AuthenticationStrategy authenticationStrategy2 = this.proxyAuthStrategy;
        if (authenticationStrategy2 == null) {
            authenticationStrategy2 = ProxyAuthenticationStrategy.INSTANCE;
        }
        UserTokenHandler userTokenHandler = this.userTokenHandler;
        if (userTokenHandler == null) {
            if (!this.connectionStateDisabled) {
                userTokenHandler = DefaultUserTokenHandler.INSTANCE;
            }
            else {
                userTokenHandler = NoopUserTokenHandler.INSTANCE;
            }
        }
        final ClientExecChain decorateMainExec = this.decorateMainExec(new MainClientExec(requestExec, connManager, connectionReuseStrategy, connectionKeepAliveStrategy, authenticationStrategy, authenticationStrategy2, userTokenHandler));
        HttpProcessor httpProcessor = this.httpprocessor;
        if (httpProcessor == null) {
            String s = this.userAgent;
            if (s == null) {
                if (this.systemProperties) {
                    s = System.getProperty("http.agent");
                }
                if (s == null) {
                    s = HttpClientBuilder.DEFAULT_USER_AGENT;
                }
            }
            final HttpProcessorBuilder create = HttpProcessorBuilder.create();
            if (this.requestFirst != null) {
                final Iterator iterator = this.requestFirst.iterator();
                while (iterator.hasNext()) {
                    create.addFirst(iterator.next());
                }
            }
            if (this.responseFirst != null) {
                final Iterator iterator2 = this.responseFirst.iterator();
                while (iterator2.hasNext()) {
                    create.addFirst(iterator2.next());
                }
            }
            create.addAll(new RequestDefaultHeaders(this.defaultHeaders), new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(s), new RequestExpectContinue());
            if (!this.cookieManagementDisabled) {
                create.add(new RequestAddCookies());
            }
            if (!this.contentCompressionDisabled) {
                create.add(new RequestAcceptEncoding());
            }
            if (!this.authCachingDisabled) {
                create.add(new RequestAuthCache());
            }
            if (!this.cookieManagementDisabled) {
                create.add(new ResponseProcessCookies());
            }
            if (!this.contentCompressionDisabled) {
                create.add(new ResponseContentEncoding());
            }
            if (this.requestLast != null) {
                final Iterator iterator3 = this.requestLast.iterator();
                while (iterator3.hasNext()) {
                    create.addLast(iterator3.next());
                }
            }
            if (this.responseLast != null) {
                final Iterator iterator4 = this.responseLast.iterator();
                while (iterator4.hasNext()) {
                    create.addLast(iterator4.next());
                }
            }
            httpProcessor = create.build();
        }
        ClientExecChain decorateProtocolExec = this.decorateProtocolExec(new ProtocolExec(decorateMainExec, httpProcessor));
        if (!this.automaticRetriesDisabled) {
            HttpRequestRetryHandler httpRequestRetryHandler = this.retryHandler;
            if (httpRequestRetryHandler == null) {
                httpRequestRetryHandler = DefaultHttpRequestRetryHandler.INSTANCE;
            }
            decorateProtocolExec = new RetryExec(decorateProtocolExec, httpRequestRetryHandler);
        }
        HttpRoutePlanner routePlanner = this.routePlanner;
        if (routePlanner == null) {
            SchemePortResolver schemePortResolver = this.schemePortResolver;
            if (schemePortResolver == null) {
                schemePortResolver = DefaultSchemePortResolver.INSTANCE;
            }
            if (this.proxy != null) {
                routePlanner = new DefaultProxyRoutePlanner(this.proxy, schemePortResolver);
            }
            else if (this.systemProperties) {
                routePlanner = new SystemDefaultRoutePlanner(schemePortResolver, ProxySelector.getDefault());
            }
            else {
                routePlanner = new DefaultRoutePlanner(schemePortResolver);
            }
        }
        if (!this.redirectHandlingDisabled) {
            RedirectStrategy redirectStrategy = this.redirectStrategy;
            if (redirectStrategy == null) {
                redirectStrategy = DefaultRedirectStrategy.INSTANCE;
            }
            decorateProtocolExec = new RedirectExec(decorateProtocolExec, routePlanner, redirectStrategy);
        }
        final ServiceUnavailableRetryStrategy serviceUnavailStrategy = this.serviceUnavailStrategy;
        if (serviceUnavailStrategy != null) {
            decorateProtocolExec = new ServiceUnavailableRetryExec(decorateProtocolExec, serviceUnavailStrategy);
        }
        final BackoffManager backoffManager = this.backoffManager;
        final ConnectionBackoffStrategy connectionBackoffStrategy = this.connectionBackoffStrategy;
        if (backoffManager != null && connectionBackoffStrategy != null) {
            decorateProtocolExec = new BackoffStrategyExec(decorateProtocolExec, connectionBackoffStrategy, backoffManager);
        }
        Lookup lookup = this.authSchemeRegistry;
        if (lookup == null) {
            lookup = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("NTLM", new NTLMSchemeFactory()).register("negotiate", new SPNegoSchemeFactory()).register("Kerberos", new KerberosSchemeFactory()).build();
        }
        Lookup lookup2 = this.cookieSpecRegistry;
        if (lookup2 == null) {
            lookup2 = RegistryBuilder.create().register("best-match", new BestMatchSpecFactory()).register("standard", new RFC2965SpecFactory()).register("compatibility", new BrowserCompatSpecFactory()).register("netscape", new NetscapeDraftSpecFactory()).register("ignoreCookies", new IgnoreSpecFactory()).register("rfc2109", new RFC2109SpecFactory()).register("rfc2965", new RFC2965SpecFactory()).build();
        }
        CookieStore cookieStore = this.cookieStore;
        if (cookieStore == null) {
            cookieStore = new BasicCookieStore();
        }
        CredentialsProvider credentialsProvider = this.credentialsProvider;
        if (credentialsProvider == null) {
            if (this.systemProperties) {
                credentialsProvider = new SystemDefaultCredentialsProvider();
            }
            else {
                credentialsProvider = new BasicCredentialsProvider();
            }
        }
        return new InternalHttpClient(decorateProtocolExec, connManager, routePlanner, lookup2, lookup, cookieStore, credentialsProvider, (this.defaultRequestConfig != null) ? this.defaultRequestConfig : RequestConfig.DEFAULT, (this.closeables != null) ? new ArrayList(this.closeables) : null);
    }
    
    static {
        final VersionInfo loadVersionInfo = VersionInfo.loadVersionInfo("org.apache.http.client", HttpClientBuilder.class.getClassLoader());
        DEFAULT_USER_AGENT = "Apache-HttpClient/" + ((loadVersionInfo != null) ? loadVersionInfo.getRelease() : "UNAVAILABLE") + " (java 1.5)";
    }
}
