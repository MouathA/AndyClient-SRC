package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.core.appender.*;
import java.net.*;
import java.io.*;
import org.apache.logging.log4j.core.*;
import java.util.*;

public abstract class AbstractSocketManager extends OutputStreamManager
{
    protected final InetAddress address;
    protected final String host;
    protected final int port;
    
    public AbstractSocketManager(final String s, final OutputStream outputStream, final InetAddress address, final String host, final int port, final Layout layout) {
        super(outputStream, s, layout);
        this.address = address;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public Map getContentFormat() {
        final HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("port", Integer.toString(this.port));
        hashMap.put("address", this.address.getHostAddress());
        return hashMap;
    }
}
