package org.apache.logging.log4j.core.helpers;

import org.apache.logging.log4j.*;
import java.net.*;
import org.apache.logging.log4j.status.*;

public final class NetUtils
{
    private static final Logger LOGGER;
    
    private NetUtils() {
    }
    
    public static String getLocalHostname() {
        return InetAddress.getLocalHost().getHostName();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
