package io.netty.util;

import java.net.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.io.*;
import java.util.*;

public final class NetUtil
{
    public static final Inet4Address LOCALHOST4;
    public static final Inet6Address LOCALHOST6;
    public static final InetAddress LOCALHOST;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN;
    private static final InternalLogger logger;
    
    public static byte[] createByteArrayFromIpAddressString(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       45
        //     4: new             Ljava/util/StringTokenizer;
        //     7: dup            
        //     8: aload_0        
        //     9: ldc             "."
        //    11: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    14: astore_1       
        //    15: iconst_4       
        //    16: newarray        B
        //    18: astore          4
        //    20: aload_1        
        //    21: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    24: astore_2       
        //    25: aload_2        
        //    26: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    29: istore_3       
        //    30: aload           4
        //    32: iconst_0       
        //    33: iload_3        
        //    34: i2b            
        //    35: bastore        
        //    36: iinc            5, 1
        //    39: goto            20
        //    42: aload           4
        //    44: areturn        
        //    45: aload_0        
        //    46: if_icmpge       360
        //    49: aload_0        
        //    50: iconst_0       
        //    51: invokevirtual   java/lang/String.charAt:(I)C
        //    54: bipush          91
        //    56: if_icmpne       71
        //    59: aload_0        
        //    60: iconst_1       
        //    61: aload_0        
        //    62: invokevirtual   java/lang/String.length:()I
        //    65: iconst_1       
        //    66: isub           
        //    67: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    70: astore_0       
        //    71: new             Ljava/util/StringTokenizer;
        //    74: dup            
        //    75: aload_0        
        //    76: ldc             ":."
        //    78: iconst_1       
        //    79: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;Ljava/lang/String;Z)V
        //    82: astore_1       
        //    83: new             Ljava/util/ArrayList;
        //    86: dup            
        //    87: invokespecial   java/util/ArrayList.<init>:()V
        //    90: astore_2       
        //    91: new             Ljava/util/ArrayList;
        //    94: dup            
        //    95: invokespecial   java/util/ArrayList.<init>:()V
        //    98: astore_3       
        //    99: ldc             ""
        //   101: astore          4
        //   103: ldc             ""
        //   105: astore          5
        //   107: aload_1        
        //   108: invokevirtual   java/util/StringTokenizer.hasMoreTokens:()Z
        //   111: ifeq            191
        //   114: aload           4
        //   116: astore          5
        //   118: aload_1        
        //   119: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   122: astore          4
        //   124: ldc             ":"
        //   126: aload           4
        //   128: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   131: ifeq            171
        //   134: ldc             ":"
        //   136: aload           5
        //   138: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   141: ifeq            153
        //   144: aload_2        
        //   145: invokevirtual   java/util/ArrayList.size:()I
        //   148: istore          6
        //   150: goto            107
        //   153: aload           5
        //   155: invokevirtual   java/lang/String.isEmpty:()Z
        //   158: ifne            107
        //   161: aload_2        
        //   162: aload           5
        //   164: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   167: pop            
        //   168: goto            107
        //   171: ldc             "."
        //   173: aload           4
        //   175: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   178: ifeq            107
        //   181: aload_3        
        //   182: aload           5
        //   184: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   187: pop            
        //   188: goto            107
        //   191: ldc             ":"
        //   193: aload           5
        //   195: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   198: ifeq            230
        //   201: ldc             ":"
        //   203: aload           4
        //   205: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   208: ifeq            220
        //   211: aload_2        
        //   212: invokevirtual   java/util/ArrayList.size:()I
        //   215: istore          6
        //   217: goto            247
        //   220: aload_2        
        //   221: aload           4
        //   223: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   226: pop            
        //   227: goto            247
        //   230: ldc             "."
        //   232: aload           5
        //   234: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   237: ifeq            247
        //   240: aload_3        
        //   241: aload           4
        //   243: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   246: pop            
        //   247: aload_3        
        //   248: invokevirtual   java/util/ArrayList.isEmpty:()Z
        //   251: ifne            257
        //   254: iinc            7, -2
        //   257: goto            288
        //   260: bipush          8
        //   262: aload_2        
        //   263: invokevirtual   java/util/ArrayList.size:()I
        //   266: isub           
        //   267: istore          8
        //   269: iconst_0       
        //   270: iload           8
        //   272: if_icmpge       288
        //   275: aload_2        
        //   276: iconst_m1      
        //   277: ldc             "0"
        //   279: invokevirtual   java/util/ArrayList.add:(ILjava/lang/Object;)V
        //   282: iinc            9, 1
        //   285: goto            269
        //   288: bipush          16
        //   290: newarray        B
        //   292: astore          8
        //   294: iconst_0       
        //   295: aload_2        
        //   296: invokevirtual   java/util/ArrayList.size:()I
        //   299: if_icmpge       322
        //   302: aload_2        
        //   303: iconst_0       
        //   304: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   307: checkcast       Ljava/lang/String;
        //   310: aload           8
        //   312: iconst_0       
        //   313: invokestatic    io/netty/util/NetUtil.convertToBytes:(Ljava/lang/String;[BI)V
        //   316: iinc            9, 1
        //   319: goto            294
        //   322: iconst_0       
        //   323: aload_3        
        //   324: invokevirtual   java/util/ArrayList.size:()I
        //   327: if_icmpge       357
        //   330: aload           8
        //   332: bipush          12
        //   334: aload_3        
        //   335: iconst_0       
        //   336: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   339: checkcast       Ljava/lang/String;
        //   342: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   345: sipush          255
        //   348: iand           
        //   349: i2b            
        //   350: bastore        
        //   351: iinc            9, 1
        //   354: goto            322
        //   357: aload           8
        //   359: areturn        
        //   360: aconst_null    
        //   361: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void convertToBytes(final String s, final byte[] array, final int n) {
        final int length = s.length();
        array[n + 1] = (array[n] = 0);
        int n3 = 0;
        if (length > 3) {
            final int n2 = 0;
            ++n3;
            array[n] |= (byte)(getIntValue(s.charAt(n2)) << 4);
        }
        if (length > 2) {
            final int n4 = 0;
            ++n3;
            array[n] |= (byte)getIntValue(s.charAt(n4));
        }
        if (length > 1) {
            final int n5 = 0;
            ++n3;
            final int intValue = getIntValue(s.charAt(n5));
            final int n6 = n + 1;
            array[n6] |= (byte)(intValue << 4);
        }
        final int intValue2 = getIntValue(s.charAt(0));
        final int n7 = n + 1;
        array[n7] |= (byte)(intValue2 & 0xF);
    }
    
    static int getIntValue(final char c) {
        switch (c) {
            case '0': {
                return 0;
            }
            case '1': {
                return 1;
            }
            case '2': {
                return 2;
            }
            case '3': {
                return 3;
            }
            case '4': {
                return 4;
            }
            case '5': {
                return 5;
            }
            case '6': {
                return 6;
            }
            case '7': {
                return 7;
            }
            case '8': {
                return 8;
            }
            case '9': {
                return 9;
            }
            default: {
                switch (Character.toLowerCase(c)) {
                    case 'a': {
                        return 10;
                    }
                    case 'b': {
                        return 11;
                    }
                    case 'c': {
                        return 12;
                    }
                    case 'd': {
                        return 13;
                    }
                    case 'e': {
                        return 14;
                    }
                    case 'f': {
                        return 15;
                    }
                    default: {
                        return 0;
                    }
                }
                break;
            }
        }
    }
    
    private NetUtil() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NetUtil.class);
        final byte[] array = { 127, 0, 0, 1 };
        final byte[] array2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        final Inet4Address inet4Address = LOCALHOST4 = (Inet4Address)InetAddress.getByAddress(array);
        final Inet6Address inet6Address = LOCALHOST6 = (Inet6Address)InetAddress.getByAddress(array2);
        final ArrayList<NetworkInterface> list = new ArrayList<NetworkInterface>();
        final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.getInetAddresses().hasMoreElements()) {
                list.add(networkInterface);
            }
        }
        NetworkInterface loopback_IF = null;
        InetAddress localhost = null;
    Label_0324:
        for (final NetworkInterface networkInterface2 : list) {
            final Enumeration<InetAddress> inetAddresses = networkInterface2.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                final InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress.isLoopbackAddress()) {
                    loopback_IF = networkInterface2;
                    localhost = inetAddress;
                    break Label_0324;
                }
            }
        }
        if (loopback_IF == null) {
            for (final NetworkInterface networkInterface3 : list) {
                if (networkInterface3.isLoopback()) {
                    final Enumeration<InetAddress> inetAddresses2 = networkInterface3.getInetAddresses();
                    if (inetAddresses2.hasMoreElements()) {
                        loopback_IF = networkInterface3;
                        localhost = inetAddresses2.nextElement();
                        break;
                    }
                    continue;
                }
            }
            if (loopback_IF == null) {
                NetUtil.logger.warn("Failed to find the loopback interface");
            }
        }
        if (loopback_IF != null) {
            NetUtil.logger.debug("Loopback interface: {} ({}, {})", loopback_IF.getName(), loopback_IF.getDisplayName(), localhost.getHostAddress());
        }
        else if (localhost == null) {
            if (NetworkInterface.getByInetAddress(NetUtil.LOCALHOST6) != null) {
                NetUtil.logger.debug("Using hard-coded IPv6 localhost address: {}", inet6Address);
                localhost = inet6Address;
            }
            if (localhost == null) {
                NetUtil.logger.debug("Using hard-coded IPv4 localhost address: {}", inet4Address);
                localhost = inet4Address;
            }
        }
        LOOPBACK_IF = loopback_IF;
        LOCALHOST = localhost;
        int int1 = PlatformDependent.isWindows() ? 200 : 128;
        final File file = new File("/proc/sys/net/core/somaxconn");
        if (file.exists()) {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int1 = Integer.parseInt(bufferedReader.readLine());
            if (NetUtil.logger.isDebugEnabled()) {
                NetUtil.logger.debug("{}: {}", file, int1);
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        else if (NetUtil.logger.isDebugEnabled()) {
            NetUtil.logger.debug("{}: {} (non-existent)", file, int1);
        }
        SOMAXCONN = int1;
    }
}
