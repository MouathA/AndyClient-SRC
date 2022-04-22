package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.core.net.ssl.*;
import java.net.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import org.apache.logging.log4j.*;
import javax.net.ssl.*;

public class TLSSocketManager extends TCPSocketManager
{
    public static final int DEFAULT_PORT = 6514;
    private static final TLSSocketManagerFactory FACTORY;
    private SSLConfiguration sslConfig;
    
    public TLSSocketManager(final String s, final OutputStream outputStream, final Socket socket, final SSLConfiguration sslConfig, final InetAddress inetAddress, final String s2, final int n, final int n2, final boolean b, final Layout layout) {
        super(s, outputStream, socket, inetAddress, s2, n, n2, b, layout);
        this.sslConfig = sslConfig;
    }
    
    public static TLSSocketManager getSocketManager(final SSLConfiguration sslConfiguration, final String s, final int n, final int n2, final boolean b, final Layout layout) {
        if (Strings.isEmpty(s)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (6514 <= 0) {}
        if (30000 == 0) {}
        return (TLSSocketManager)OutputStreamManager.getManager("TLS:" + s + ":" + 6514, new TLSFactoryData(sslConfiguration, s, 6514, 30000, b, layout), TLSSocketManager.FACTORY);
    }
    
    @Override
    protected Socket createSocket(final String s, final int n) throws IOException {
        return createSSLSocketFactory(this.sslConfig).createSocket(s, n);
    }
    
    private static SSLSocketFactory createSSLSocketFactory(final SSLConfiguration sslConfiguration) {
        SSLSocketFactory sslSocketFactory;
        if (sslConfiguration != null) {
            sslSocketFactory = sslConfiguration.getSSLSocketFactory();
        }
        else {
            sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        }
        return sslSocketFactory;
    }
    
    static Logger access$300() {
        return TLSSocketManager.LOGGER;
    }
    
    static Logger access$700() {
        return TLSSocketManager.LOGGER;
    }
    
    static SSLSocketFactory access$900(final SSLConfiguration sslConfiguration) {
        return createSSLSocketFactory(sslConfiguration);
    }
    
    static {
        FACTORY = new TLSSocketManagerFactory(null);
    }
    
    private static class TLSSocketManagerFactory implements ManagerFactory
    {
        private TLSSocketManagerFactory() {
        }
        
        public TLSSocketManager createManager(final String s, final TLSFactoryData tlsFactoryData) {
            final InetAddress resolveAddress = this.resolveAddress(TLSFactoryData.access$100(tlsFactoryData));
            final Socket socket = this.createSocket(tlsFactoryData);
            final OutputStream outputStream = socket.getOutputStream();
            this.checkDelay(TLSFactoryData.access$200(tlsFactoryData), outputStream);
            return this.createManager(s, outputStream, socket, tlsFactoryData.sslConfig, resolveAddress, TLSFactoryData.access$100(tlsFactoryData), TLSFactoryData.access$400(tlsFactoryData), TLSFactoryData.access$200(tlsFactoryData), TLSFactoryData.access$500(tlsFactoryData), TLSFactoryData.access$600(tlsFactoryData));
        }
        
        private InetAddress resolveAddress(final String s) throws TLSSocketManagerFactoryException {
            return InetAddress.getByName(s);
        }
        
        private void checkDelay(final int n, final OutputStream outputStream) throws TLSSocketManagerFactoryException {
            if (n == 0 && outputStream == null) {
                throw new TLSSocketManagerFactoryException(null);
            }
        }
        
        private Socket createSocket(final TLSFactoryData tlsFactoryData) throws IOException {
            return TLSSocketManager.access$900(tlsFactoryData.sslConfig).createSocket(TLSFactoryData.access$100(tlsFactoryData), TLSFactoryData.access$400(tlsFactoryData));
        }
        
        private TLSSocketManager createManager(final String s, final OutputStream outputStream, final Socket socket, final SSLConfiguration sslConfiguration, final InetAddress inetAddress, final String s2, final int n, final int n2, final boolean b, final Layout layout) {
            return new TLSSocketManager(s, outputStream, socket, sslConfiguration, inetAddress, s2, n, n2, b, layout);
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (TLSFactoryData)o);
        }
        
        TLSSocketManagerFactory(final TLSSocketManager$1 object) {
            this();
        }
        
        private class TLSSocketManagerFactoryException extends Exception
        {
            final TLSSocketManagerFactory this$0;
            
            private TLSSocketManagerFactoryException(final TLSSocketManagerFactory this$0) {
                this.this$0 = this$0;
            }
            
            TLSSocketManagerFactoryException(final TLSSocketManagerFactory tlsSocketManagerFactory, final TLSSocketManager$1 object) {
                this(tlsSocketManagerFactory);
            }
        }
    }
    
    private static class TLSFactoryData
    {
        protected SSLConfiguration sslConfig;
        private final String host;
        private final int port;
        private final int delay;
        private final boolean immediateFail;
        private final Layout layout;
        
        public TLSFactoryData(final SSLConfiguration sslConfig, final String host, final int port, final int delay, final boolean immediateFail, final Layout layout) {
            this.host = host;
            this.port = port;
            this.delay = delay;
            this.immediateFail = immediateFail;
            this.layout = layout;
            this.sslConfig = sslConfig;
        }
        
        static String access$100(final TLSFactoryData tlsFactoryData) {
            return tlsFactoryData.host;
        }
        
        static int access$200(final TLSFactoryData tlsFactoryData) {
            return tlsFactoryData.delay;
        }
        
        static int access$400(final TLSFactoryData tlsFactoryData) {
            return tlsFactoryData.port;
        }
        
        static boolean access$500(final TLSFactoryData tlsFactoryData) {
            return tlsFactoryData.immediateFail;
        }
        
        static Layout access$600(final TLSFactoryData tlsFactoryData) {
            return tlsFactoryData.layout;
        }
    }
}
