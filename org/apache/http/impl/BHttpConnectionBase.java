package org.apache.http.impl;

import org.apache.http.annotation.*;
import java.nio.charset.*;
import org.apache.http.config.*;
import org.apache.http.impl.entity.*;
import java.io.*;
import org.apache.http.io.*;
import org.apache.http.impl.io.*;
import org.apache.http.entity.*;
import org.apache.http.*;
import org.apache.http.util.*;
import java.net.*;

@NotThreadSafe
public class BHttpConnectionBase implements HttpConnection, HttpInetConnection
{
    private final SessionInputBufferImpl inbuffer;
    private final SessionOutputBufferImpl outbuffer;
    private final HttpConnectionMetricsImpl connMetrics;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;
    private boolean open;
    private Socket socket;
    
    protected BHttpConnectionBase(final int n, final int n2, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints, final ContentLengthStrategy contentLengthStrategy, final ContentLengthStrategy contentLengthStrategy2) {
        Args.positive(n, "Buffer size");
        final HttpTransportMetricsImpl httpTransportMetricsImpl = new HttpTransportMetricsImpl();
        final HttpTransportMetricsImpl httpTransportMetricsImpl2 = new HttpTransportMetricsImpl();
        this.inbuffer = new SessionInputBufferImpl(httpTransportMetricsImpl, n, -1, (messageConstraints != null) ? messageConstraints : MessageConstraints.DEFAULT, charsetDecoder);
        this.outbuffer = new SessionOutputBufferImpl(httpTransportMetricsImpl2, n, n2, charsetEncoder);
        this.connMetrics = new HttpConnectionMetricsImpl(httpTransportMetricsImpl, httpTransportMetricsImpl2);
        this.incomingContentStrategy = ((contentLengthStrategy != null) ? contentLengthStrategy : LaxContentLengthStrategy.INSTANCE);
        this.outgoingContentStrategy = ((contentLengthStrategy2 != null) ? contentLengthStrategy2 : StrictContentLengthStrategy.INSTANCE);
    }
    
    protected void ensureOpen() throws IOException {
        Asserts.check(this.open, "Connection is not open");
        if (!this.inbuffer.isBound()) {
            this.inbuffer.bind(this.getSocketInputStream(this.socket));
        }
        if (!this.outbuffer.isBound()) {
            this.outbuffer.bind(this.getSocketOutputStream(this.socket));
        }
    }
    
    protected InputStream getSocketInputStream(final Socket socket) throws IOException {
        return socket.getInputStream();
    }
    
    protected OutputStream getSocketOutputStream(final Socket socket) throws IOException {
        return socket.getOutputStream();
    }
    
    protected void bind(final Socket socket) throws IOException {
        Args.notNull(socket, "Socket");
        this.socket = socket;
        this.open = true;
        this.inbuffer.bind(null);
        this.outbuffer.bind(null);
    }
    
    protected SessionInputBuffer getSessionInputBuffer() {
        return this.inbuffer;
    }
    
    protected SessionOutputBuffer getSessionOutputBuffer() {
        return this.outbuffer;
    }
    
    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    protected Socket getSocket() {
        return this.socket;
    }
    
    protected OutputStream createOutputStream(final long n, final SessionOutputBuffer sessionOutputBuffer) {
        if (n == -2L) {
            return new ChunkedOutputStream(2048, sessionOutputBuffer);
        }
        if (n == -1L) {
            return new IdentityOutputStream(sessionOutputBuffer);
        }
        return new ContentLengthOutputStream(sessionOutputBuffer, n);
    }
    
    protected OutputStream prepareOutput(final HttpMessage httpMessage) throws HttpException {
        return this.createOutputStream(this.outgoingContentStrategy.determineLength(httpMessage), this.outbuffer);
    }
    
    protected InputStream createInputStream(final long n, final SessionInputBuffer sessionInputBuffer) {
        if (n == -2L) {
            return new ChunkedInputStream(sessionInputBuffer);
        }
        if (n == -1L) {
            return new IdentityInputStream(sessionInputBuffer);
        }
        return new ContentLengthInputStream(sessionInputBuffer, n);
    }
    
    protected HttpEntity prepareInput(final HttpMessage httpMessage) throws HttpException {
        final BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        final long determineLength = this.incomingContentStrategy.determineLength(httpMessage);
        final InputStream inputStream = this.createInputStream(determineLength, this.inbuffer);
        if (determineLength == -2L) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(inputStream);
        }
        else if (determineLength == -1L) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(inputStream);
        }
        else {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(determineLength);
            basicHttpEntity.setContent(inputStream);
        }
        final Header firstHeader = httpMessage.getFirstHeader("Content-Type");
        if (firstHeader != null) {
            basicHttpEntity.setContentType(firstHeader);
        }
        final Header firstHeader2 = httpMessage.getFirstHeader("Content-Encoding");
        if (firstHeader2 != null) {
            basicHttpEntity.setContentEncoding(firstHeader2);
        }
        return basicHttpEntity;
    }
    
    public InetAddress getLocalAddress() {
        if (this.socket != null) {
            return this.socket.getLocalAddress();
        }
        return null;
    }
    
    public int getLocalPort() {
        if (this.socket != null) {
            return this.socket.getLocalPort();
        }
        return -1;
    }
    
    public InetAddress getRemoteAddress() {
        if (this.socket != null) {
            return this.socket.getInetAddress();
        }
        return null;
    }
    
    public int getRemotePort() {
        if (this.socket != null) {
            return this.socket.getPort();
        }
        return -1;
    }
    
    public void setSocketTimeout(final int soTimeout) {
        if (this.socket != null) {
            this.socket.setSoTimeout(soTimeout);
        }
    }
    
    public int getSocketTimeout() {
        if (this.socket != null) {
            return this.socket.getSoTimeout();
        }
        return -1;
    }
    
    public void shutdown() throws IOException {
        this.open = false;
        final Socket socket = this.socket;
        if (socket != null) {
            socket.close();
        }
    }
    
    public void close() throws IOException {
        if (!this.open) {
            return;
        }
        this.open = false;
        final Socket socket = this.socket;
        this.inbuffer.clear();
        this.outbuffer.flush();
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
    
    private int fillInputBuffer(final int soTimeout) throws IOException {
        final int soTimeout2 = this.socket.getSoTimeout();
        this.socket.setSoTimeout(soTimeout);
        final int fillBuffer = this.inbuffer.fillBuffer();
        this.socket.setSoTimeout(soTimeout2);
        return fillBuffer;
    }
    
    protected boolean awaitInput(final int n) throws IOException {
        if (this.inbuffer.hasBufferedData()) {
            return true;
        }
        this.fillInputBuffer(n);
        return this.inbuffer.hasBufferedData();
    }
    
    public boolean isStale() {
        return !this.isOpen() || this.fillInputBuffer(1) < 0;
    }
    
    protected void incrementRequestCount() {
        this.connMetrics.incrementRequestCount();
    }
    
    protected void incrementResponseCount() {
        this.connMetrics.incrementResponseCount();
    }
    
    public HttpConnectionMetrics getMetrics() {
        return this.connMetrics;
    }
    
    @Override
    public String toString() {
        if (this.socket != null) {
            final StringBuilder sb = new StringBuilder();
            final SocketAddress remoteSocketAddress = this.socket.getRemoteSocketAddress();
            final SocketAddress localSocketAddress = this.socket.getLocalSocketAddress();
            if (remoteSocketAddress != null && localSocketAddress != null) {
                NetUtils.formatAddress(sb, localSocketAddress);
                sb.append("<->");
                NetUtils.formatAddress(sb, remoteSocketAddress);
            }
            return sb.toString();
        }
        return "[Not bound]";
    }
}
