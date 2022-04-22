package org.apache.logging.log4j.core.helpers;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.util.*;
import java.net.*;
import java.security.*;
import java.nio.*;
import java.lang.reflect.*;
import java.util.*;

public final class UUIDUtil
{
    public static final String UUID_SEQUENCE = "org.apache.logging.log4j.uuidSequence";
    private static final String ASSIGNED_SEQUENCES = "org.apache.logging.log4j.assignedSequences";
    private static AtomicInteger count;
    private static final long TYPE1 = 4096L;
    private static final byte VARIANT = Byte.MIN_VALUE;
    private static final int SEQUENCE_MASK = 16383;
    private static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 122192928000000000L;
    private static long uuidSequence;
    private static long least;
    private static final long LOW_MASK = 4294967295L;
    private static final long MID_MASK = 281470681743360L;
    private static final long HIGH_MASK = 1152640029630136320L;
    private static final int NODE_SIZE = 8;
    private static final int SHIFT_2 = 16;
    private static final int SHIFT_4 = 32;
    private static final int SHIFT_6 = 48;
    private static final int HUNDRED_NANOS_PER_MILLI = 10000;
    
    private UUIDUtil() {
    }
    
    public static UUID getTimeBasedUUID() {
        final long n = System.currentTimeMillis() * 10000L + 122192928000000000L + UUIDUtil.count.incrementAndGet() % 10000;
        return new UUID((n & 0xFFFFFFFFL) << 32 | (n & 0xFFFF00000000L) >> 16 | 0x1000L | (n & 0xFFF000000000000L) >> 48, UUIDUtil.least);
    }
    
    static {
        UUIDUtil.count = new AtomicInteger(0);
        UUIDUtil.uuidSequence = PropertiesUtil.getProperties().getLongProperty("org.apache.logging.log4j.uuidSequence", 0L);
        byte[] address = null;
        final InetAddress localHost = InetAddress.getLocalHost();
        final NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(localHost);
        if (byInetAddress != null && !byInetAddress.isLoopback() && byInetAddress.isUp()) {
            final Method method = byInetAddress.getClass().getMethod("getHardwareAddress", (Class<?>[])new Class[0]);
            if (method != null) {
                address = (byte[])method.invoke(byInetAddress, new Object[0]);
            }
        }
        if (address == null) {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements() && address == null) {
                final NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface != null && networkInterface.isUp() && !networkInterface.isLoopback()) {
                    final Method method2 = networkInterface.getClass().getMethod("getHardwareAddress", (Class<?>[])new Class[0]);
                    if (method2 == null) {
                        continue;
                    }
                    address = (byte[])method2.invoke(networkInterface, new Object[0]);
                }
            }
        }
        if (address == null || address.length == 0) {
            address = localHost.getAddress();
        }
        final SecureRandom secureRandom = new SecureRandom();
        if (address == null || address.length == 0) {
            address = new byte[6];
            secureRandom.nextBytes(address);
        }
        final int n = (address.length >= 6) ? 6 : address.length;
        final int n2 = (address.length >= 6) ? (address.length - 6) : 0;
        final byte[] array = new byte[8];
        while (2 < 8) {
            array[2] = 0;
            int n3 = 0;
            ++n3;
        }
        System.arraycopy(address, n2, array, n2 + 2, n);
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        long n4 = UUIDUtil.uuidSequence;
        // monitorenter(runtime = Runtime.getRuntime())
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.assignedSequences");
        long[] array2;
        int length = 0;
        if (stringProperty == null) {
            array2 = new long[0];
        }
        else {
            final String[] split = stringProperty.split(",");
            array2 = new long[split.length];
            final String[] array3 = split;
            length = array3.length;
            while (0 < 0) {
                array2[0] = Long.parseLong(array3[0]);
                int n5 = 0;
                ++n5;
                int n6 = 0;
                ++n6;
            }
        }
        if (n4 == 0L) {
            n4 = secureRandom.nextLong();
        }
        long n7 = n4 & 0x3FFFL;
        do {
            final long[] array4 = array2;
            while (0 < array4.length && array4[0] != n7) {
                ++length;
            }
            if (true) {
                n7 = (n7 + 1L & 0x3FFFL);
            }
        } while (true);
        System.setProperty("org.apache.logging.log4j.assignedSequences", (stringProperty == null) ? Long.toString(n7) : (stringProperty + "," + Long.toString(n7)));
        // monitorexit(runtime)
        UUIDUtil.least = (wrap.getLong() | n7 << 48);
    }
}
