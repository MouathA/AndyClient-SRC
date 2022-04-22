package org.apache.http.impl.conn;

import org.apache.http.conn.*;
import org.apache.commons.logging.*;
import java.util.concurrent.*;
import org.apache.http.util.*;
import java.util.*;
import java.net.*;

public class InMemoryDnsResolver implements DnsResolver
{
    private final Log log;
    private final Map dnsMap;
    
    public InMemoryDnsResolver() {
        this.log = LogFactory.getLog(InMemoryDnsResolver.class);
        this.dnsMap = new ConcurrentHashMap();
    }
    
    public void add(final String s, final InetAddress... array) {
        Args.notNull(s, "Host name");
        Args.notNull(array, "Array of IP addresses");
        this.dnsMap.put(s, array);
    }
    
    public InetAddress[] resolve(final String s) throws UnknownHostException {
        final InetAddress[] array = this.dnsMap.get(s);
        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + s + " to " + Arrays.deepToString(array));
        }
        if (array == null) {
            throw new UnknownHostException(s + " cannot be resolved");
        }
        return array;
    }
}
