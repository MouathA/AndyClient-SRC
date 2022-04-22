package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.helpers.*;
import java.util.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "multicastdns", category = "Core", elementType = "advertiser", printObject = false)
public class MulticastDNSAdvertiser implements Advertiser
{
    protected static final Logger LOGGER;
    private static Object jmDNS;
    private static Class jmDNSClass;
    private static Class serviceInfoClass;
    
    @Override
    public Object advertise(final Map map) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().length() <= 255 && ((String)entry.getValue()).length() <= 255) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
        }
        final String s = hashMap.get("protocol");
        final String string = "._log4j._" + ((s != null) ? s : "tcp") + ".local.";
        final int int1 = Integers.parseInt(hashMap.get("port"), 4555);
        final String s2 = hashMap.get("name");
        if (MulticastDNSAdvertiser.jmDNS != null) {
            MulticastDNSAdvertiser.jmDNSClass.getMethod("create", (Class[])null);
            Object o;
            if (true) {
                o = this.buildServiceInfoVersion3(string, int1, s2, hashMap);
            }
            else {
                o = this.buildServiceInfoVersion1(string, int1, s2, hashMap);
            }
            MulticastDNSAdvertiser.jmDNSClass.getMethod("registerService", MulticastDNSAdvertiser.serviceInfoClass).invoke(MulticastDNSAdvertiser.jmDNS, o);
            return o;
        }
        MulticastDNSAdvertiser.LOGGER.warn("JMDNS not available - will not advertise ZeroConf support");
        return null;
    }
    
    @Override
    public void unadvertise(final Object o) {
        if (MulticastDNSAdvertiser.jmDNS != null) {
            MulticastDNSAdvertiser.jmDNSClass.getMethod("unregisterService", MulticastDNSAdvertiser.serviceInfoClass).invoke(MulticastDNSAdvertiser.jmDNS, o);
        }
    }
    
    private static Object createJmDNSVersion1() {
        return MulticastDNSAdvertiser.jmDNSClass.newInstance();
    }
    
    private static Object createJmDNSVersion3() {
        return MulticastDNSAdvertiser.jmDNSClass.getMethod("create", (Class[])null).invoke(null, (Object[])null);
    }
    
    private Object buildServiceInfoVersion1(final String s, final int n, final String s2, final Map map) {
        return MulticastDNSAdvertiser.serviceInfoClass.getConstructor(String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Hashtable.class).newInstance(s, s2, n, 0, 0, new Hashtable(map));
    }
    
    private Object buildServiceInfoVersion3(final String s, final int n, final String s2, final Map map) {
        return MulticastDNSAdvertiser.serviceInfoClass.getMethod("create", String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Map.class).invoke(null, s, s2, n, 0, 0, map);
    }
    
    private static Object initializeJMDNS() {
        MulticastDNSAdvertiser.jmDNSClass = Class.forName("javax.jmdns.JmDNS");
        MulticastDNSAdvertiser.serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
        MulticastDNSAdvertiser.jmDNSClass.getMethod("create", (Class[])null);
        if (true) {
            return createJmDNSVersion3();
        }
        return createJmDNSVersion1();
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        MulticastDNSAdvertiser.jmDNS = initializeJMDNS();
    }
}
