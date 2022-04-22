package org.apache.logging.log4j.core.net;

import java.net.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.appender.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.*;
import java.util.concurrent.*;

public class TCPSocketManager extends AbstractSocketManager
{
    public static final int DEFAULT_RECONNECTION_DELAY = 30000;
    private static final int DEFAULT_PORT = 4560;
    private static final TCPSocketManagerFactory FACTORY;
    private final int reconnectionDelay;
    private Reconnector connector;
    private Socket socket;
    private final boolean retry;
    private final boolean immediateFail;
    
    public TCPSocketManager(final String s, final OutputStream outputStream, final Socket socket, final InetAddress inetAddress, final String s2, final int n, final int reconnectionDelay, final boolean immediateFail, final Layout layout) {
        super(s, outputStream, inetAddress, s2, n, layout);
        this.connector = null;
        this.reconnectionDelay = reconnectionDelay;
        this.socket = socket;
        this.immediateFail = immediateFail;
        this.retry = (reconnectionDelay > 0);
        if (socket == null) {
            (this.connector = new Reconnector(this)).setDaemon(true);
            this.connector.setPriority(1);
            this.connector.start();
        }
    }
    
    public static TCPSocketManager getSocketManager(final String s, final int n, final int n2, final boolean b, final Layout layout) {
        if (Strings.isEmpty(s)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (4560 <= 0) {}
        if (30000 == 0) {}
        return (TCPSocketManager)OutputStreamManager.getManager("TCP:" + s + ":" + 4560, new FactoryData(s, 4560, 30000, b, layout), TCPSocketManager.FACTORY);
    }
    
    @Override
    protected void write(final byte[] array, final int n, final int n2) {
        if (this.socket == null) {
            if (this.connector != null && !this.immediateFail) {
                this.connector.latch();
            }
            if (this.socket == null) {
                throw new AppenderLoggingException("Error writing to " + this.getName() + " socket not available");
            }
        }
        // monitorenter(this)
        this.getOutputStream().write(array, n, n2);
    }
    // monitorexit(this)
    
    @Override
    protected synchronized void close() {
        super.close();
        if (this.connector != null) {
            this.connector.shutdown();
            this.connector.interrupt();
            this.connector = null;
        }
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("protocol", "tcp");
        hashMap.put("direction", "out");
        return hashMap;
    }
    
    protected Socket createSocket(final InetAddress inetAddress, final int n) throws IOException {
        return this.createSocket(inetAddress.getHostName(), n);
    }
    
    protected Socket createSocket(final String s, final int n) throws IOException {
        return new Socket(s, n);
    }
    
    static int access$000(final TCPSocketManager tcpSocketManager) {
        return tcpSocketManager.reconnectionDelay;
    }
    
    static OutputStream access$100(final TCPSocketManager tcpSocketManager) {
        return tcpSocketManager.getOutputStream();
    }
    
    static void access$200(final TCPSocketManager tcpSocketManager, final OutputStream outputStream) {
        tcpSocketManager.setOutputStream(outputStream);
    }
    
    static Socket access$302(final TCPSocketManager tcpSocketManager, final Socket socket) {
        return tcpSocketManager.socket = socket;
    }
    
    static Reconnector access$402(final TCPSocketManager tcpSocketManager, final Reconnector connector) {
        return tcpSocketManager.connector = connector;
    }
    
    static Logger access$500() {
        return TCPSocketManager.LOGGER;
    }
    
    static Logger access$600() {
        return TCPSocketManager.LOGGER;
    }
    
    static Logger access$700() {
        return TCPSocketManager.LOGGER;
    }
    
    static Logger access$800() {
        return TCPSocketManager.LOGGER;
    }
    
    static Logger access$1000() {
        return TCPSocketManager.LOGGER;
    }
    
    static Logger access$1500() {
        return TCPSocketManager.LOGGER;
    }
    
    static {
        FACTORY = new TCPSocketManagerFactory();
    }
    
    protected static class TCPSocketManagerFactory implements ManagerFactory
    {
        public TCPSocketManager createManager(final String s, final FactoryData factoryData) {
            final InetAddress byName = InetAddress.getByName(FactoryData.access$900(factoryData));
            final Socket socket = new Socket(FactoryData.access$900(factoryData), FactoryData.access$1100(factoryData));
            return new TCPSocketManager(s, socket.getOutputStream(), socket, byName, FactoryData.access$900(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), FactoryData.access$1300(factoryData), FactoryData.access$1400(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
    }
    
    private static class FactoryData
    {
        private final String host;
        private final int port;
        private final int delay;
        private final boolean immediateFail;
        private final Layout layout;
        
        public FactoryData(final String host, final int port, final int delay, final boolean immediateFail, final Layout layout) {
            this.host = host;
            this.port = port;
            this.delay = delay;
            this.immediateFail = immediateFail;
            this.layout = layout;
        }
        
        static String access$900(final FactoryData factoryData) {
            return factoryData.host;
        }
        
        static int access$1100(final FactoryData factoryData) {
            return factoryData.port;
        }
        
        static int access$1200(final FactoryData factoryData) {
            return factoryData.delay;
        }
        
        static boolean access$1300(final FactoryData factoryData) {
            return factoryData.immediateFail;
        }
        
        static Layout access$1400(final FactoryData factoryData) {
            return factoryData.layout;
        }
    }
    
    private class Reconnector extends Thread
    {
        private final CountDownLatch latch;
        private boolean shutdown;
        private final Object owner;
        final TCPSocketManager this$0;
        
        public Reconnector(final TCPSocketManager this$0, final OutputStreamManager owner) {
            this.this$0 = this$0;
            this.latch = new CountDownLatch(1);
            this.shutdown = false;
            this.owner = owner;
        }
        
        public void latch() {
            this.latch.await();
        }
        
        public void shutdown() {
            this.shutdown = true;
        }
        
        @Override
        public void run() {
            while (!this.shutdown) {
                Thread.sleep(TCPSocketManager.access$000(this.this$0));
                final Socket socket = this.this$0.createSocket(this.this$0.address, this.this$0.port);
                final OutputStream outputStream = socket.getOutputStream();
                // monitorenter(owner = this.owner)
                TCPSocketManager.access$100(this.this$0).close();
                TCPSocketManager.access$200(this.this$0, outputStream);
                TCPSocketManager.access$302(this.this$0, socket);
                TCPSocketManager.access$402(this.this$0, null);
                this.shutdown = true;
                // monitorexit(owner)
                TCPSocketManager.access$500().debug("Connection to " + this.this$0.host + ":" + this.this$0.port + " reestablished.");
                this.latch.countDown();
            }
        }
    }
}
