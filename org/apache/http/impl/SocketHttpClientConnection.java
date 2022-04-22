package org.apache.http.impl;

import org.apache.http.*;
import org.apache.http.annotation.*;
import org.apache.http.params.*;
import java.io.*;
import org.apache.http.io.*;
import org.apache.http.impl.io.*;
import org.apache.http.util.*;
import java.net.*;

@Deprecated
@NotThreadSafe
public class SocketHttpClientConnection extends AbstractHttpClientConnection implements HttpInetConnection
{
    private boolean open;
    private Socket socket;
    
    public SocketHttpClientConnection() {
        this.socket = null;
    }
    
    protected void assertNotOpen() {
        Asserts.check(!this.open, "Connection is already open");
    }
    
    @Override
    protected void assertOpen() {
        Asserts.check(this.open, "Connection is not open");
    }
    
    protected SessionInputBuffer createSessionInputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        return new SocketInputBuffer(socket, n, httpParams);
    }
    
    protected SessionOutputBuffer createSessionOutputBuffer(final Socket socket, final int n, final HttpParams httpParams) throws IOException {
        return new SocketOutputBuffer(socket, n, httpParams);
    }
    
    protected void bind(final Socket socket, final HttpParams httpParams) throws IOException {
        Args.notNull(socket, "Socket");
        Args.notNull(httpParams, "HTTP parameters");
        this.socket = socket;
        final int intParameter = httpParams.getIntParameter("http.socket.buffer-size", -1);
        this.init(this.createSessionInputBuffer(socket, intParameter, httpParams), this.createSessionOutputBuffer(socket, intParameter, httpParams), httpParams);
        this.open = true;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    protected Socket getSocket() {
        return this.socket;
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
        this.assertOpen();
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
        this.doFlush();
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
    
    private static void formatAddress(final StringBuilder sb, final SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            sb.append((inetSocketAddress.getAddress() != null) ? inetSocketAddress.getAddress().getHostAddress() : inetSocketAddress.getAddress()).append(':').append(inetSocketAddress.getPort());
        }
        else {
            sb.append(socketAddress);
        }
    }
    
    @Override
    public String toString() {
        if (this.socket != null) {
            final StringBuilder sb = new StringBuilder();
            final SocketAddress remoteSocketAddress = this.socket.getRemoteSocketAddress();
            final SocketAddress localSocketAddress = this.socket.getLocalSocketAddress();
            if (remoteSocketAddress != null && localSocketAddress != null) {
                formatAddress(sb, localSocketAddress);
                sb.append("<->");
                formatAddress(sb, remoteSocketAddress);
            }
            return sb.toString();
        }
        return super.toString();
    }
}
