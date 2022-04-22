package org.apache.http.impl.conn;

import org.apache.http.impl.*;
import org.apache.http.conn.*;
import org.apache.http.protocol.*;
import org.apache.http.annotation.*;
import java.util.*;
import java.nio.charset.*;
import org.apache.http.config.*;
import org.apache.http.entity.*;
import org.apache.http.io.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;

@NotThreadSafe
public class DefaultManagedHttpClientConnection extends DefaultBHttpClientConnection implements ManagedHttpClientConnection, HttpContext
{
    private final String id;
    private final Map attributes;
    private boolean shutdown;
    
    public DefaultManagedHttpClientConnection(final String id, final int n, final int n2, final CharsetDecoder charsetDecoder, final CharsetEncoder charsetEncoder, final MessageConstraints messageConstraints, final ContentLengthStrategy contentLengthStrategy, final ContentLengthStrategy contentLengthStrategy2, final HttpMessageWriterFactory httpMessageWriterFactory, final HttpMessageParserFactory httpMessageParserFactory) {
        super(n, n2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2, httpMessageWriterFactory, httpMessageParserFactory);
        this.id = id;
        this.attributes = new ConcurrentHashMap();
    }
    
    public DefaultManagedHttpClientConnection(final String s, final int n) {
        this(s, n, n, null, null, null, null, null, null, null);
    }
    
    public String getId() {
        return this.id;
    }
    
    @Override
    public void shutdown() throws IOException {
        this.shutdown = true;
        super.shutdown();
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
    
    @Override
    public void bind(final Socket socket) throws IOException {
        if (this.shutdown) {
            socket.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
        super.bind(socket);
    }
    
    public Socket getSocket() {
        return super.getSocket();
    }
    
    public SSLSession getSSLSession() {
        final Socket socket = super.getSocket();
        if (socket instanceof SSLSocket) {
            return ((SSLSocket)socket).getSession();
        }
        return null;
    }
}
