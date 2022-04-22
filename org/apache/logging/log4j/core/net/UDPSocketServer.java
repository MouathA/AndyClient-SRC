package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.core.config.*;

public class UDPSocketServer extends AbstractServer implements Runnable
{
    private final Logger logger;
    private static final int MAX_PORT = 65534;
    private boolean isActive;
    private final DatagramSocket server;
    private final int maxBufferSize = 67584;
    
    public UDPSocketServer(final int n) throws IOException {
        this.isActive = true;
        this.server = new DatagramSocket(n);
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
        final UDPSocketServer udpSocketServer = new UDPSocketServer(int1);
        final Thread thread = new Thread(udpSocketServer);
        thread.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        do {
            line = bufferedReader.readLine();
        } while (line != null && !line.equalsIgnoreCase("Quit") && !line.equalsIgnoreCase("Stop") && !line.equalsIgnoreCase("Exit"));
        udpSocketServer.shutdown();
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
            final byte[] array = new byte[67584];
            final DatagramPacket datagramPacket = new DatagramPacket(array, array.length);
            this.server.receive(datagramPacket);
            final LogEvent logEvent = (LogEvent)new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength())).readObject();
            if (logEvent != null) {
                this.log(logEvent);
            }
        }
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
}
