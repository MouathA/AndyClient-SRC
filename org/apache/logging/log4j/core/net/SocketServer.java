package org.apache.logging.log4j.core.net;

import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import java.nio.charset.*;
import java.util.*;
import org.apache.logging.log4j.core.*;
import java.net.*;
import org.apache.logging.log4j.core.config.*;
import java.io.*;

public class SocketServer extends AbstractServer implements Runnable
{
    private final Logger logger;
    private static final int MAX_PORT = 65534;
    private boolean isActive;
    private final ServerSocket server;
    private final ConcurrentMap handlers;
    
    public SocketServer(final int n) throws IOException {
        this.isActive = true;
        this.handlers = new ConcurrentHashMap();
        this.server = new ServerSocket(n);
        this.logger = LogManager.getLogger(this.getClass().getName() + '.' + n);
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length < 1 || array.length > 2) {
            System.err.println("Incorrect number of arguments");
            return;
        }
        final int int1 = Integer.parseInt(array[0]);
        if (int1 <= 0 || int1 >= 65534) {
            System.err.println("Invalid port number");
            return;
        }
        if (array.length == 2 && array[1].length() > 0) {
            ConfigurationFactory.setConfigurationFactory(new ServerConfigurationFactory(array[1]));
        }
        final SocketServer socketServer = new SocketServer(int1);
        final Thread thread = new Thread(socketServer);
        thread.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
        String line;
        do {
            line = bufferedReader.readLine();
        } while (line != null && !line.equalsIgnoreCase("Quit") && !line.equalsIgnoreCase("Stop") && !line.equalsIgnoreCase("Exit"));
        socketServer.shutdown();
        thread.join();
    }
    
    private static void printUsage() {
        System.out.println("Usage: ServerSocket port configFilePath");
    }
    
    public void shutdown() {
        this.isActive = false;
        Thread.currentThread().interrupt();
    }
    
    @Override
    public void run() {
        while (this.isActive) {
            final Socket accept = this.server.accept();
            accept.setSoLinger(true, 0);
            final SocketHandler socketHandler = new SocketHandler(accept);
            this.handlers.put(socketHandler.getId(), socketHandler);
            socketHandler.start();
        }
        final Iterator iterator = this.handlers.entrySet().iterator();
        while (iterator.hasNext()) {
            final SocketHandler socketHandler2 = iterator.next().getValue();
            socketHandler2.shutdown();
            socketHandler2.join();
        }
    }
    
    static void access$000(final SocketServer socketServer, final LogEvent logEvent) {
        socketServer.log(logEvent);
    }
    
    static Logger access$100(final SocketServer socketServer) {
        return socketServer.logger;
    }
    
    static ConcurrentMap access$200(final SocketServer socketServer) {
        return socketServer.handlers;
    }
    
    private static class ServerConfigurationFactory extends XMLConfigurationFactory
    {
        private final String path;
        
        public ServerConfigurationFactory(final String path) {
            this.path = path;
        }
        
        @Override
        public Configuration getConfiguration(final String s, final URI uri) {
            if (this.path != null && this.path.length() > 0) {
                final File file = new File(this.path);
                ConfigurationSource configurationSource = new ConfigurationSource(new FileInputStream(file), file);
                if (configurationSource == null) {
                    configurationSource = new ConfigurationSource(new URL(this.path).openStream(), this.path);
                }
                if (configurationSource != null) {
                    return new XMLConfiguration(configurationSource);
                }
                System.err.println("Unable to process configuration at " + this.path + ", using default.");
            }
            return super.getConfiguration(s, uri);
        }
    }
    
    private class SocketHandler extends Thread
    {
        private final ObjectInputStream ois;
        private boolean shutdown;
        final SocketServer this$0;
        
        public SocketHandler(final SocketServer this$0, final Socket socket) throws IOException {
            this.this$0 = this$0;
            this.shutdown = false;
            this.ois = new ObjectInputStream(socket.getInputStream());
        }
        
        public void shutdown() {
            this.shutdown = true;
            this.interrupt();
        }
        
        @Override
        public void run() {
            while (!this.shutdown) {
                final LogEvent logEvent = (LogEvent)this.ois.readObject();
                if (logEvent != null) {
                    SocketServer.access$000(this.this$0, logEvent);
                }
            }
            SocketServer.access$200(this.this$0).remove(this.getId());
        }
    }
}
