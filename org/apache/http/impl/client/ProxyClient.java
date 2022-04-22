package org.apache.http.impl.client;

import org.apache.http.config.*;
import org.apache.http.client.config.*;
import org.apache.http.impl.conn.*;
import org.apache.http.client.protocol.*;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.*;
import org.apache.http.client.params.*;
import org.apache.http.params.*;
import java.net.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import org.apache.http.message.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.*;
import org.apache.http.client.*;
import org.apache.http.util.*;
import org.apache.http.entity.*;
import org.apache.http.impl.execchain.*;
import org.apache.http.*;
import java.io.*;

public class ProxyClient
{
    private final HttpConnectionFactory connFactory;
    private final ConnectionConfig connectionConfig;
    private final RequestConfig requestConfig;
    private final HttpProcessor httpProcessor;
    private final HttpRequestExecutor requestExec;
    private final ProxyAuthenticationStrategy proxyAuthStrategy;
    private final HttpAuthenticator authenticator;
    private final AuthState proxyAuthState;
    private final AuthSchemeRegistry authSchemeRegistry;
    private final ConnectionReuseStrategy reuseStrategy;
    
    public ProxyClient(final HttpConnectionFactory httpConnectionFactory, final ConnectionConfig connectionConfig, final RequestConfig requestConfig) {
        this.connFactory = ((httpConnectionFactory != null) ? httpConnectionFactory : ManagedHttpClientConnectionFactory.INSTANCE);
        this.connectionConfig = ((connectionConfig != null) ? connectionConfig : ConnectionConfig.DEFAULT);
        this.requestConfig = ((requestConfig != null) ? requestConfig : RequestConfig.DEFAULT);
        this.httpProcessor = new ImmutableHttpProcessor(new HttpRequestInterceptor[] { new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent() });
        this.requestExec = new HttpRequestExecutor();
        this.proxyAuthStrategy = new ProxyAuthenticationStrategy();
        this.authenticator = new HttpAuthenticator();
        this.proxyAuthState = new AuthState();
        (this.authSchemeRegistry = new AuthSchemeRegistry()).register("Basic", new BasicSchemeFactory());
        this.authSchemeRegistry.register("Digest", new DigestSchemeFactory());
        this.authSchemeRegistry.register("NTLM", new NTLMSchemeFactory());
        this.authSchemeRegistry.register("negotiate", new SPNegoSchemeFactory());
        this.authSchemeRegistry.register("Kerberos", new KerberosSchemeFactory());
        this.reuseStrategy = new DefaultConnectionReuseStrategy();
    }
    
    @Deprecated
    public ProxyClient(final HttpParams httpParams) {
        this(null, HttpParamConfig.getConnectionConfig(httpParams), HttpClientParamConfig.getRequestConfig(httpParams));
    }
    
    public ProxyClient(final RequestConfig requestConfig) {
        this(null, null, requestConfig);
    }
    
    public ProxyClient() {
        this(null, null, null);
    }
    
    @Deprecated
    public HttpParams getParams() {
        return new BasicHttpParams();
    }
    
    @Deprecated
    public AuthSchemeRegistry getAuthSchemeRegistry() {
        return this.authSchemeRegistry;
    }
    
    public Socket tunnel(final HttpHost httpHost, final HttpHost httpHost2, final Credentials credentials) throws IOException, HttpException {
        Args.notNull(httpHost, "Proxy host");
        Args.notNull(httpHost2, "Target host");
        Args.notNull(credentials, "Credentials");
        HttpHost httpHost3 = httpHost2;
        if (httpHost3.getPort() <= 0) {
            httpHost3 = new HttpHost(httpHost3.getHostName(), 80, httpHost3.getSchemeName());
        }
        final HttpRoute httpRoute = new HttpRoute(httpHost3, this.requestConfig.getLocalAddress(), httpHost, false, RouteInfo.TunnelType.TUNNELLED, RouteInfo.LayerType.PLAIN);
        final ManagedHttpClientConnection managedHttpClientConnection = (ManagedHttpClientConnection)this.connFactory.create(httpRoute, this.connectionConfig);
        final BasicHttpContext basicHttpContext = new BasicHttpContext();
        final BasicHttpRequest basicHttpRequest = new BasicHttpRequest("CONNECT", httpHost3.toHostString(), HttpVersion.HTTP_1_1);
        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(new AuthScope(httpHost), credentials);
        basicHttpContext.setAttribute("http.target_host", httpHost2);
        basicHttpContext.setAttribute("http.connection", managedHttpClientConnection);
        basicHttpContext.setAttribute("http.request", basicHttpRequest);
        basicHttpContext.setAttribute("http.route", httpRoute);
        basicHttpContext.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
        basicHttpContext.setAttribute("http.auth.credentials-provider", basicCredentialsProvider);
        basicHttpContext.setAttribute("http.authscheme-registry", this.authSchemeRegistry);
        basicHttpContext.setAttribute("http.request-config", this.requestConfig);
        this.requestExec.preProcess(basicHttpRequest, this.httpProcessor, basicHttpContext);
        while (true) {
            if (!managedHttpClientConnection.isOpen()) {
                managedHttpClientConnection.bind(new Socket(httpHost.getHostName(), httpHost.getPort()));
            }
            this.authenticator.generateAuthResponse(basicHttpRequest, this.proxyAuthState, basicHttpContext);
            final HttpResponse execute = this.requestExec.execute(basicHttpRequest, managedHttpClientConnection, basicHttpContext);
            if (execute.getStatusLine().getStatusCode() < 200) {
                throw new HttpException("Unexpected response to CONNECT request: " + execute.getStatusLine());
            }
            if (this.authenticator.isAuthenticationRequested(httpHost, execute, this.proxyAuthStrategy, this.proxyAuthState, basicHttpContext) && this.authenticator.handleAuthChallenge(httpHost, execute, this.proxyAuthStrategy, this.proxyAuthState, basicHttpContext)) {
                if (this.reuseStrategy.keepAlive(execute, basicHttpContext)) {
                    EntityUtils.consume(execute.getEntity());
                }
                else {
                    managedHttpClientConnection.close();
                }
                basicHttpRequest.removeHeaders("Proxy-Authorization");
            }
            else {
                if (execute.getStatusLine().getStatusCode() > 299) {
                    final HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        execute.setEntity(new BufferedHttpEntity(entity));
                    }
                    managedHttpClientConnection.close();
                    throw new TunnelRefusedException("CONNECT refused by proxy: " + execute.getStatusLine(), execute);
                }
                return managedHttpClientConnection.getSocket();
            }
        }
    }
}
