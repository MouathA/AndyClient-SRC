package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import java.io.*;
import org.apache.http.conn.*;
import org.apache.http.protocol.*;
import java.lang.reflect.*;
import org.apache.http.*;

@NotThreadSafe
class CPoolProxy implements InvocationHandler
{
    private static final Method CLOSE_METHOD;
    private static final Method SHUTDOWN_METHOD;
    private static final Method IS_OPEN_METHOD;
    private static final Method IS_STALE_METHOD;
    private CPoolEntry poolEntry;
    
    CPoolProxy(final CPoolEntry poolEntry) {
        this.poolEntry = poolEntry;
    }
    
    CPoolEntry getPoolEntry() {
        return this.poolEntry;
    }
    
    CPoolEntry detach() {
        final CPoolEntry poolEntry = this.poolEntry;
        this.poolEntry = null;
        return poolEntry;
    }
    
    HttpClientConnection getConnection() {
        final CPoolEntry poolEntry = this.poolEntry;
        if (poolEntry == null) {
            return null;
        }
        return (HttpClientConnection)poolEntry.getConnection();
    }
    
    public void close() throws IOException {
        final CPoolEntry poolEntry = this.poolEntry;
        if (poolEntry != null) {
            poolEntry.closeConnection();
        }
    }
    
    public void shutdown() throws IOException {
        final CPoolEntry poolEntry = this.poolEntry;
        if (poolEntry != null) {
            poolEntry.shutdownConnection();
        }
    }
    
    public boolean isOpen() {
        final CPoolEntry poolEntry = this.poolEntry;
        return poolEntry != null && !poolEntry.isClosed();
    }
    
    public boolean isStale() {
        final HttpClientConnection connection = this.getConnection();
        return connection == null || connection.isStale();
    }
    
    public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
        if (method.equals(CPoolProxy.CLOSE_METHOD)) {
            this.close();
            return null;
        }
        if (method.equals(CPoolProxy.SHUTDOWN_METHOD)) {
            this.shutdown();
            return null;
        }
        if (method.equals(CPoolProxy.IS_OPEN_METHOD)) {
            return this.isOpen();
        }
        if (method.equals(CPoolProxy.IS_STALE_METHOD)) {
            return this.isStale();
        }
        final HttpClientConnection connection = this.getConnection();
        if (connection == null) {
            throw new ConnectionShutdownException();
        }
        return method.invoke(connection, array);
    }
    
    public static HttpClientConnection newProxy(final CPoolEntry cPoolEntry) {
        return (HttpClientConnection)Proxy.newProxyInstance(CPoolProxy.class.getClassLoader(), new Class[] { ManagedHttpClientConnection.class, HttpContext.class }, new CPoolProxy(cPoolEntry));
    }
    
    private static CPoolProxy getHandler(final HttpClientConnection httpClientConnection) {
        final InvocationHandler invocationHandler = Proxy.getInvocationHandler(httpClientConnection);
        if (!CPoolProxy.class.isInstance(invocationHandler)) {
            throw new IllegalStateException("Unexpected proxy handler class: " + invocationHandler);
        }
        return CPoolProxy.class.cast(invocationHandler);
    }
    
    public static CPoolEntry getPoolEntry(final HttpClientConnection httpClientConnection) {
        final CPoolEntry poolEntry = getHandler(httpClientConnection).getPoolEntry();
        if (poolEntry == null) {
            throw new ConnectionShutdownException();
        }
        return poolEntry;
    }
    
    public static CPoolEntry detach(final HttpClientConnection httpClientConnection) {
        return getHandler(httpClientConnection).detach();
    }
    
    static {
        CLOSE_METHOD = HttpConnection.class.getMethod("close", (Class<?>[])new Class[0]);
        SHUTDOWN_METHOD = HttpConnection.class.getMethod("shutdown", (Class<?>[])new Class[0]);
        IS_OPEN_METHOD = HttpConnection.class.getMethod("isOpen", (Class<?>[])new Class[0]);
        IS_STALE_METHOD = HttpConnection.class.getMethod("isStale", (Class<?>[])new Class[0]);
    }
}
