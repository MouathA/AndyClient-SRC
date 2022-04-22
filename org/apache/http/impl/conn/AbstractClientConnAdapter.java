package org.apache.http.impl.conn;

import org.apache.http.protocol.*;
import org.apache.http.annotation.*;
import org.apache.http.conn.*;
import java.io.*;
import org.apache.http.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.concurrent.*;

@Deprecated
@NotThreadSafe
public abstract class AbstractClientConnAdapter implements ManagedClientConnection, HttpContext
{
    private final ClientConnectionManager connManager;
    private OperatedClientConnection wrappedConnection;
    private boolean markedReusable;
    private boolean released;
    private long duration;
    
    protected AbstractClientConnAdapter(final ClientConnectionManager connManager, final OperatedClientConnection wrappedConnection) {
        this.connManager = connManager;
        this.wrappedConnection = wrappedConnection;
        this.markedReusable = false;
        this.released = false;
        this.duration = Long.MAX_VALUE;
    }
    
    protected synchronized void detach() {
        this.wrappedConnection = null;
        this.duration = Long.MAX_VALUE;
    }
    
    protected OperatedClientConnection getWrappedConnection() {
        return this.wrappedConnection;
    }
    
    protected ClientConnectionManager getManager() {
        return this.connManager;
    }
    
    @Deprecated
    protected final void assertNotAborted() throws InterruptedIOException {
        if (this.isReleased()) {
            throw new InterruptedIOException("Connection has been shut down");
        }
    }
    
    protected boolean isReleased() {
        return this.released;
    }
    
    protected final void assertValid(final OperatedClientConnection operatedClientConnection) throws ConnectionShutdownException {
        if (this.isReleased() || operatedClientConnection == null) {
            throw new ConnectionShutdownException();
        }
    }
    
    public boolean isOpen() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        return wrappedConnection != null && wrappedConnection.isOpen();
    }
    
    public boolean isStale() {
        if (this.isReleased()) {
            return true;
        }
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        return wrappedConnection == null || wrappedConnection.isStale();
    }
    
    public void setSocketTimeout(final int socketTimeout) {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        wrappedConnection.setSocketTimeout(socketTimeout);
    }
    
    public int getSocketTimeout() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getSocketTimeout();
    }
    
    public HttpConnectionMetrics getMetrics() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getMetrics();
    }
    
    public void flush() throws IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        wrappedConnection.flush();
    }
    
    public boolean isResponseAvailable(final int n) throws IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.isResponseAvailable(n);
    }
    
    public void receiveResponseEntity(final HttpResponse httpResponse) throws HttpException, IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        this.unmarkReusable();
        wrappedConnection.receiveResponseEntity(httpResponse);
    }
    
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        this.unmarkReusable();
        return wrappedConnection.receiveResponseHeader();
    }
    
    public void sendRequestEntity(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        this.unmarkReusable();
        wrappedConnection.sendRequestEntity(httpEntityEnclosingRequest);
    }
    
    public void sendRequestHeader(final HttpRequest httpRequest) throws HttpException, IOException {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        this.unmarkReusable();
        wrappedConnection.sendRequestHeader(httpRequest);
    }
    
    public InetAddress getLocalAddress() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getLocalAddress();
    }
    
    public int getLocalPort() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getLocalPort();
    }
    
    public InetAddress getRemoteAddress() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getRemoteAddress();
    }
    
    public int getRemotePort() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.getRemotePort();
    }
    
    public boolean isSecure() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        return wrappedConnection.isSecure();
    }
    
    public void bind(final Socket socket) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public Socket getSocket() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        if (!this.isOpen()) {
            return null;
        }
        return wrappedConnection.getSocket();
    }
    
    public SSLSession getSSLSession() {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        if (!this.isOpen()) {
            return null;
        }
        SSLSession session = null;
        final Socket socket = wrappedConnection.getSocket();
        if (socket instanceof SSLSocket) {
            session = ((SSLSocket)socket).getSession();
        }
        return session;
    }
    
    public void markReusable() {
        this.markedReusable = true;
    }
    
    public void unmarkReusable() {
        this.markedReusable = false;
    }
    
    public boolean isMarkedReusable() {
        return this.markedReusable;
    }
    
    public void setIdleDuration(final long n, final TimeUnit timeUnit) {
        if (n > 0L) {
            this.duration = timeUnit.toMillis(n);
        }
        else {
            this.duration = -1L;
        }
    }
    
    public synchronized void releaseConnection() {
        if (this.released) {
            return;
        }
        this.released = true;
        this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
    }
    
    public synchronized void abortConnection() {
        if (this.released) {
            return;
        }
        this.released = true;
        this.unmarkReusable();
        this.shutdown();
        this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
    }
    
    public Object getAttribute(final String s) {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        if (wrappedConnection instanceof HttpContext) {
            return ((HttpContext)wrappedConnection).getAttribute(s);
        }
        return null;
    }
    
    public Object removeAttribute(final String s) {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        if (wrappedConnection instanceof HttpContext) {
            return ((HttpContext)wrappedConnection).removeAttribute(s);
        }
        return null;
    }
    
    public void setAttribute(final String s, final Object o) {
        final OperatedClientConnection wrappedConnection = this.getWrappedConnection();
        this.assertValid(wrappedConnection);
        if (wrappedConnection instanceof HttpContext) {
            ((HttpContext)wrappedConnection).setAttribute(s, o);
        }
    }
}
