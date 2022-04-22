package net.minecraft.client.multiplayer;

import java.net.*;
import java.util.*;
import javax.naming.directory.*;

public class ServerAddress
{
    private final String ipAddress;
    private final int serverPort;
    private static final String __OBFID;
    
    private ServerAddress(final String ipAddress, final int serverPort) {
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
    }
    
    public String getIP() {
        return IDN.toASCII(this.ipAddress);
    }
    
    public int getPort() {
        return this.serverPort;
    }
    
    public static ServerAddress func_78860_a(final String s) {
        if (s == null) {
            return null;
        }
        String[] split = s.split(":");
        if (s.startsWith("[")) {
            final int index = s.indexOf("]");
            if (index > 0) {
                final String substring = s.substring(1, index);
                final String trim = s.substring(index + 1).trim();
                if (trim.startsWith(":") && trim.length() > 0) {
                    split = new String[] { substring, trim.substring(1) };
                }
                else {
                    split = new String[] { substring };
                }
            }
        }
        if (split.length > 2) {
            split = new String[] { s };
        }
        String s2 = split[0];
        int intWithDefault = (split.length > 1) ? parseIntWithDefault(split[1], 25565) : 25565;
        if (intWithDefault == 25565) {
            final String[] serverAddress = getServerAddress(s2);
            s2 = serverAddress[0];
            intWithDefault = parseIntWithDefault(serverAddress[1], 25565);
        }
        return new ServerAddress(s2, intWithDefault);
    }
    
    private static String[] getServerAddress(final String s) {
        Class.forName("com.sun.jndi.dns.DnsContextFactory");
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        hashtable.put("java.naming.provider.url", "dns:");
        hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
        final String[] split = new InitialDirContext(hashtable).getAttributes("_minecraft._tcp." + s, new String[] { "SRV" }).get("srv").get().toString().split(" ", 4);
        return new String[] { split[3], split[2] };
    }
    
    private static int parseIntWithDefault(final String s, final int n) {
        return Integer.parseInt(s.trim());
    }
    
    public static ServerAddress fromString(final String s) {
        if (s == null) {
            return null;
        }
        String[] split = s.split(":");
        if (s.startsWith("[")) {
            final int index = s.indexOf("]");
            if (index > 0) {
                final String substring = s.substring(1, index);
                final String trim = s.substring(index + 1).trim();
                if (trim.startsWith(":") && trim.length() > 0) {
                    split = new String[] { substring, trim.substring(1) };
                }
                else {
                    split = new String[] { substring };
                }
            }
        }
        if (split.length > 2) {
            split = new String[] { s };
        }
        String s2 = split[0];
        int intWithDefault = (split.length > 1) ? parseIntWithDefault(split[1], 25565) : 25565;
        if (intWithDefault == 25565) {
            final String[] serverAddress = getServerAddress(s2);
            s2 = serverAddress[0];
            intWithDefault = parseIntWithDefault(serverAddress[1], 25565);
        }
        return new ServerAddress(s2, intWithDefault);
    }
    
    static {
        __OBFID = "CL_00000889";
    }
}
