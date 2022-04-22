package org.apache.http.impl.conn;

import org.apache.http.impl.*;
import org.apache.http.conn.*;
import org.apache.http.protocol.*;
import org.apache.http.annotation.*;
import java.net.*;
import org.apache.commons.logging.*;
import java.util.*;
import javax.net.ssl.*;
import java.io.*;
import org.apache.http.util.*;
import org.apache.http.io.*;
import org.apache.http.message.*;
import org.apache.http.params.*;
import org.apache.http.*;

@Deprecated
@NotThreadSafe
public class DefaultClientConnection extends SocketHttpClientConnection implements OperatedClientConnection, ManagedHttpClientConnection, HttpContext
{
    private final Log log;
    private final Log headerLog;
    private final Log wireLog;
    private Socket socket;
    private HttpHost targetHost;
    private boolean connSecure;
    private boolean shutdown;
    private final Map attributes;
    
    public DefaultClientConnection() {
        this.log = LogFactory.getLog(this.getClass());
        this.headerLog = LogFactory.getLog("org.apache.http.headers");
        this.wireLog = LogFactory.getLog("org.apache.http.wire");
        this.attributes = new HashMap();
    }
    
    public String getId() {
        return null;
    }
    
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }
    
    public final boolean isSecure() {
        return this.connSecure;
    }
    
    public final Socket getSocket() {
        return this.socket;
    }
    
    public SSLSession getSSLSession() {
        if (this.socket instanceof SSLSocket) {
            return ((SSLSocket)this.socket).getSession();
        }
        return null;
    }
    
    public void opening(final Socket socket, final HttpHost targetHost) throws IOException {
        this.assertNotOpen();
        this.socket = socket;
        this.targetHost = targetHost;
        if (this.shutdown) {
            socket.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
    }
    
    public void openCompleted(final boolean connSecure, final HttpParams httpParams) throws IOException {
        Args.notNull(httpParams, "Parameters");
        this.assertNotOpen();
        this.connSecure = connSecure;
        this.bind(this.socket, httpParams);
    }
    
    @Override
    public void shutdown() throws IOException {
        this.shutdown = true;
        super.shutdown();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection " + this + " shut down");
        }
        final Socket socket = this.socket;
        if (socket != null) {
            socket.close();
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Connection " + this + " closed");
        }
    }
    
    @Override
    protected SessionInputBuffer createSessionInputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        SessionInputBuffer sessionInputBuffer = super.createSessionInputBuffer(socket, (n > 0) ? n : 8192, httpParams);
        if (this.wireLog.isDebugEnabled()) {
            sessionInputBuffer = new LoggingSessionInputBuffer(sessionInputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
        }
        return sessionInputBuffer;
    }
    
    @Override
    protected SessionOutputBuffer createSessionOutputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        SessionOutputBuffer sessionOutputBuffer = super.createSessionOutputBuffer(socket, (n > 0) ? n : 8192, httpParams);
        if (this.wireLog.isDebugEnabled()) {
            sessionOutputBuffer = new LoggingSessionOutputBuffer(sessionOutputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
        }
        return sessionOutputBuffer;
    }
    
    @Override
    protected HttpMessageParser createResponseParser(final SessionInputBuffer sessionInputBuffer, final HttpResponseFactory httpResponseFactory, final HttpParams httpParams) {
        return new DefaultHttpResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }
    
    public void bind(final Socket socket) throws IOException {
        this.bind(socket, new BasicHttpParams());
    }
    
    public void update(final Socket socket, final HttpHost targetHost, final boolean connSecure, final HttpParams httpParams) throws IOException {
        this.assertOpen();
        Args.notNull(targetHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        if (socket != null) {
            this.bind(this.socket = socket, httpParams);
        }
        this.targetHost = targetHost;
        this.connSecure = connSecure;
    }
    
    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        final HttpResponse receiveResponseHeader = super.receiveResponseHeader();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Receiving response: " + receiveResponseHeader.getStatusLine());
        }
        if (this.headerLog.isDebugEnabled()) {
            this.headerLog.debug("<< " + receiveResponseHeader.getStatusLine().toString());
            final Header[] allHeaders = receiveResponseHeader.getAllHeaders();
            while (0 < allHeaders.length) {
                this.headerLog.debug("<< " + allHeaders[0].toString());
                int n = 0;
                ++n;
            }
        }
        return receiveResponseHeader;
    }
    
    @Override
    public void sendRequestHeader(final HttpRequest httpRequest) throws HttpException, IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Sending request: " + httpRequest.getRequestLine());
        }
        super.sendRequestHeader(httpRequest);
        if (this.headerLog.isDebugEnabled()) {
            this.headerLog.debug(">> " + httpRequest.getRequestLine().toString());
            final Header[] allHeaders = httpRequest.getAllHeaders();
            while (0 < allHeaders.length) {
                this.headerLog.debug(">> " + allHeaders[0].toString());
                int n = 0;
                ++n;
            }
        }
    }
    
    public Object getAttribute(final String s) {
        return this.attributes.get(s);
    }
    
    public Object removeAttribute(final String s) {
        return this.attributes.remove(s);
    }
    
    public void setAttribute(final String s, final Object o) {
        this.attributes.put(s, o);
    }
}
