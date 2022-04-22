package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.io.*;
import org.apache.http.*;

@Immutable
public class HttpRequestExecutor
{
    public static final int DEFAULT_WAIT_FOR_CONTINUE = 3000;
    private final int waitForContinue;
    
    public HttpRequestExecutor(final int n) {
        this.waitForContinue = Args.positive(n, "Wait for continue time");
    }
    
    public HttpRequestExecutor() {
        this(3000);
    }
    
    protected boolean canResponseHaveBody(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if ("HEAD".equalsIgnoreCase(httpRequest.getRequestLine().getMethod())) {
            return false;
        }
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        return statusCode >= 200 && statusCode != 204 && statusCode != 304 && statusCode != 205;
    }
    
    public HttpResponse execute(final HttpRequest httpRequest, final HttpClientConnection httpClientConnection, final HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse httpResponse = this.doSendRequest(httpRequest, httpClientConnection, httpContext);
        if (httpResponse == null) {
            httpResponse = this.doReceiveResponse(httpRequest, httpClientConnection, httpContext);
        }
        return httpResponse;
    }
    
    private static void closeConnection(final HttpClientConnection httpClientConnection) {
        httpClientConnection.close();
    }
    
    public void preProcess(final HttpRequest httpRequest, final HttpProcessor httpProcessor, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.request", httpRequest);
        httpProcessor.process(httpRequest, httpContext);
    }
    
    protected HttpResponse doSendRequest(final HttpRequest httpRequest, final HttpClientConnection httpClientConnection, final HttpContext httpContext) throws IOException, HttpException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse receiveResponseHeader = null;
        httpContext.setAttribute("http.connection", httpClientConnection);
        httpContext.setAttribute("http.request_sent", Boolean.FALSE);
        httpClientConnection.sendRequestHeader(httpRequest);
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            final ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            if (((HttpEntityEnclosingRequest)httpRequest).expectContinue() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpClientConnection.flush();
                if (httpClientConnection.isResponseAvailable(this.waitForContinue)) {
                    receiveResponseHeader = httpClientConnection.receiveResponseHeader();
                    if (this.canResponseHaveBody(httpRequest, receiveResponseHeader)) {
                        httpClientConnection.receiveResponseEntity(receiveResponseHeader);
                    }
                    final int statusCode = receiveResponseHeader.getStatusLine().getStatusCode();
                    if (statusCode < 200) {
                        if (statusCode != 100) {
                            throw new ProtocolException("Unexpected response: " + receiveResponseHeader.getStatusLine());
                        }
                        receiveResponseHeader = null;
                    }
                }
            }
            if (false) {
                httpClientConnection.sendRequestEntity((HttpEntityEnclosingRequest)httpRequest);
            }
        }
        httpClientConnection.flush();
        httpContext.setAttribute("http.request_sent", Boolean.TRUE);
        return receiveResponseHeader;
    }
    
    protected HttpResponse doReceiveResponse(final HttpRequest httpRequest, final HttpClientConnection httpClientConnection, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpClientConnection, "Client connection");
        Args.notNull(httpContext, "HTTP context");
        HttpResponse receiveResponseHeader = null;
        while (receiveResponseHeader == null || 0 < 200) {
            receiveResponseHeader = httpClientConnection.receiveResponseHeader();
            if (this.canResponseHaveBody(httpRequest, receiveResponseHeader)) {
                httpClientConnection.receiveResponseEntity(receiveResponseHeader);
            }
            receiveResponseHeader.getStatusLine().getStatusCode();
        }
        return receiveResponseHeader;
    }
    
    public void postProcess(final HttpResponse httpResponse, final HttpProcessor httpProcessor, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpProcessor, "HTTP processor");
        Args.notNull(httpContext, "HTTP context");
        httpContext.setAttribute("http.response", httpResponse);
        httpProcessor.process(httpResponse, httpContext);
    }
}
