package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.*;
import org.apache.http.client.protocol.*;

@Deprecated
@ThreadSafe
public class DefaultHttpClient extends AbstractHttpClient
{
    public DefaultHttpClient(final ClientConnectionManager clientConnectionManager, final HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }
    
    public DefaultHttpClient(final ClientConnectionManager clientConnectionManager) {
        super(clientConnectionManager, null);
    }
    
    public DefaultHttpClient(final HttpParams httpParams) {
        super(null, httpParams);
    }
    
    public DefaultHttpClient() {
        super(null, null);
    }
    
    @Override
    protected HttpParams createHttpParams() {
        final SyncBasicHttpParams defaultHttpParams = new SyncBasicHttpParams();
        setDefaultHttpParams(defaultHttpParams);
        return defaultHttpParams;
    }
    
    public static void setDefaultHttpParams(final HttpParams httpParams) {
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.DEF_CONTENT_CHARSET.name());
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setUserAgent(httpParams, HttpClientBuilder.DEFAULT_USER_AGENT);
    }
    
    @Override
    protected BasicHttpProcessor createHttpProcessor() {
        final BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        basicHttpProcessor.addInterceptor(new RequestDefaultHeaders());
        basicHttpProcessor.addInterceptor(new RequestContent());
        basicHttpProcessor.addInterceptor(new RequestTargetHost());
        basicHttpProcessor.addInterceptor(new RequestClientConnControl());
        basicHttpProcessor.addInterceptor(new RequestUserAgent());
        basicHttpProcessor.addInterceptor(new RequestExpectContinue());
        basicHttpProcessor.addInterceptor(new RequestAddCookies());
        basicHttpProcessor.addInterceptor(new ResponseProcessCookies());
        basicHttpProcessor.addInterceptor(new RequestAuthCache());
        basicHttpProcessor.addInterceptor(new RequestTargetAuthentication());
        basicHttpProcessor.addInterceptor(new RequestProxyAuthentication());
        return basicHttpProcessor;
    }
}
