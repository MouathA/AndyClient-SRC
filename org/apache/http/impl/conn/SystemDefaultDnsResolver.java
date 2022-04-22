package org.apache.http.impl.conn;

import org.apache.http.conn.*;
import java.net.*;

public class SystemDefaultDnsResolver implements DnsResolver
{
    public static final SystemDefaultDnsResolver INSTANCE;
    
    public InetAddress[] resolve(final String s) throws UnknownHostException {
        return InetAddress.getAllByName(s);
    }
    
    static {
        INSTANCE = new SystemDefaultDnsResolver();
    }
}
