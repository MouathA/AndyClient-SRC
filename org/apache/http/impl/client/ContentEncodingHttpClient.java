package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.client.protocol.*;
import org.apache.http.*;

@Deprecated
@ThreadSafe
public class ContentEncodingHttpClient extends DefaultHttpClient
{
    public ContentEncodingHttpClient(final ClientConnectionManager clientConnectionManager, final HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }
    
    public ContentEncodingHttpClient(final HttpParams httpParams) {
        this(null, httpParams);
    }
    
    public ContentEncodingHttpClient() {
        this((HttpParams)null);
    }
    
    @Override
    protected BasicHttpProcessor createHttpProcessor() {
        final BasicHttpProcessor httpProcessor = super.createHttpProcessor();
        httpProcessor.addRequestInterceptor(new RequestAcceptEncoding());
        httpProcessor.addResponseInterceptor(new ResponseContentEncoding());
        return httpProcessor;
    }
}
