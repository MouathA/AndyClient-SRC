package org.apache.logging.log4j.core.net;

import java.io.*;
import java.net.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.appender.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class DatagramSocketManager extends AbstractSocketManager
{
    private static final DatagramSocketManagerFactory FACTORY;
    
    protected DatagramSocketManager(final String s, final OutputStream outputStream, final InetAddress inetAddress, final String s2, final int n, final Layout layout) {
        super(s, outputStream, inetAddress, s2, n, layout);
    }
    
    public static DatagramSocketManager getSocketManager(final String s, final int n, final Layout layout) {
        if (Strings.isEmpty(s)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("A port value is required");
        }
        return (DatagramSocketManager)OutputStreamManager.getManager("UDP:" + s + ":" + n, new FactoryData(s, n, layout), DatagramSocketManager.FACTORY);
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("protocol", "udp");
        hashMap.put("direction", "out");
        return hashMap;
    }
    
    static Logger access$400() {
        return DatagramSocketManager.LOGGER;
    }
    
    static {
        FACTORY = new DatagramSocketManagerFactory(null);
    }
    
    private static class DatagramSocketManagerFactory implements ManagerFactory
    {
        private DatagramSocketManagerFactory() {
        }
        
        public DatagramSocketManager createManager(final String s, final FactoryData factoryData) {
            return new DatagramSocketManager(s, new DatagramOutputStream(FactoryData.access$100(factoryData), FactoryData.access$200(factoryData), FactoryData.access$300(factoryData).getHeader(), FactoryData.access$300(factoryData).getFooter()), InetAddress.getByName(FactoryData.access$100(factoryData)), FactoryData.access$100(factoryData), FactoryData.access$200(factoryData), FactoryData.access$300(factoryData));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        DatagramSocketManagerFactory(final DatagramSocketManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final String host;
        private final int port;
        private final Layout layout;
        
        public FactoryData(final String host, final int port, final Layout layout) {
            this.host = host;
            this.port = port;
            this.layout = layout;
        }
        
        static String access$100(final FactoryData factoryData) {
            return factoryData.host;
        }
        
        static int access$200(final FactoryData factoryData) {
            return factoryData.port;
        }
        
        static Layout access$300(final FactoryData factoryData) {
            return factoryData.layout;
        }
    }
}
