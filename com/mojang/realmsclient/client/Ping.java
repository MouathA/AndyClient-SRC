package com.mojang.realmsclient.client;

import com.mojang.realmsclient.dto.*;
import java.util.*;
import java.net.*;

public class Ping
{
    public static List ping(final Region... array) {
        while (0 < array.length) {
            ping(Region.access$000(array[0]));
            int length = 0;
            ++length;
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        int length = array.length;
        Collections.sort(list, new Comparator() {
            public int compare(final RegionPingResult regionPingResult, final RegionPingResult regionPingResult2) {
                return regionPingResult.ping() - regionPingResult2.ping();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((RegionPingResult)o, (RegionPingResult)o2);
            }
        });
        return list;
    }
    
    private static int ping(final String s) {
        long n = 0L;
        while (true) {
            final InetSocketAddress inetSocketAddress = new InetSocketAddress(s, 80);
            final Socket socket = new Socket();
            final long now = now();
            socket.connect(inetSocketAddress, 700);
            n += now() - now;
            close(socket);
            int n2 = 0;
            ++n2;
        }
    }
    
    private static void close(final Socket socket) {
        if (socket != null) {
            socket.close();
        }
    }
    
    private static long now() {
        return System.currentTimeMillis();
    }
    
    public static List pingAllRegions() {
        return ping(Region.values());
    }
    
    enum Region
    {
        US_EAST_1("US_EAST_1", 0, "us-east-1", "ec2.us-east-1.amazonaws.com"), 
        US_WEST_2("US_WEST_2", 1, "us-west-2", "ec2.us-west-2.amazonaws.com"), 
        US_WEST_1("US_WEST_1", 2, "us-west-1", "ec2.us-west-1.amazonaws.com"), 
        EU_WEST_1("EU_WEST_1", 3, "eu-west-1", "ec2.eu-west-1.amazonaws.com"), 
        AP_SOUTHEAST_1("AP_SOUTHEAST_1", 4, "ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"), 
        AP_SOUTHEAST_2("AP_SOUTHEAST_2", 5, "ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"), 
        AP_NORTHEAST_1("AP_NORTHEAST_1", 6, "ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"), 
        SA_EAST_1("SA_EAST_1", 7, "sa-east-1", "ec2.sa-east-1.amazonaws.com");
        
        private final String name;
        private final String endpoint;
        private static final Region[] $VALUES;
        
        private Region(final String s, final int n, final String name, final String endpoint) {
            this.name = name;
            this.endpoint = endpoint;
        }
        
        static String access$000(final Region region) {
            return region.endpoint;
        }
        
        static String access$100(final Region region) {
            return region.name;
        }
        
        static {
            $VALUES = new Region[] { Region.US_EAST_1, Region.US_WEST_2, Region.US_WEST_1, Region.EU_WEST_1, Region.AP_SOUTHEAST_1, Region.AP_SOUTHEAST_2, Region.AP_NORTHEAST_1, Region.SA_EAST_1 };
        }
    }
}
