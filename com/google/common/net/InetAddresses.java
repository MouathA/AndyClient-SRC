package com.google.common.net;

import com.google.common.annotations.*;
import java.nio.*;
import com.google.common.primitives.*;
import java.util.*;
import com.google.common.io.*;
import com.google.common.hash.*;
import java.net.*;
import javax.annotation.*;
import com.google.common.base.*;

@Beta
public final class InetAddresses
{
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Inet4Address LOOPBACK4;
    private static final Inet4Address ANY4;
    
    private InetAddresses() {
    }
    
    private static Inet4Address getInet4Address(final byte[] array) {
        Preconditions.checkArgument(array.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", array.length);
        return (Inet4Address)bytesToInetAddress(array);
    }
    
    public static InetAddress forString(final String s) {
        final byte[] ipStringToBytes = ipStringToBytes(s);
        if (ipStringToBytes == null) {
            throw new IllegalArgumentException(String.format("'%s' is not an IP string literal.", s));
        }
        return bytesToInetAddress(ipStringToBytes);
    }
    
    public static boolean isInetAddress(final String s) {
        return ipStringToBytes(s) != null;
    }
    
    private static byte[] ipStringToBytes(String convertDottedQuadToHex) {
        while (0 < convertDottedQuadToHex.length()) {
            final char char1 = convertDottedQuadToHex.charAt(0);
            if (char1 != '.') {
                if (char1 == ':') {
                    if (true) {
                        return null;
                    }
                }
                else if (Character.digit(char1, 16) == -1) {
                    return null;
                }
            }
            int n = 0;
            ++n;
        }
        if (true) {
            if (true) {
                convertDottedQuadToHex = convertDottedQuadToHex(convertDottedQuadToHex);
                if (convertDottedQuadToHex == null) {
                    return null;
                }
            }
            return textToNumericFormatV6(convertDottedQuadToHex);
        }
        if (true) {
            return textToNumericFormatV4(convertDottedQuadToHex);
        }
        return null;
    }
    
    private static byte[] textToNumericFormatV4(final String s) {
        final String[] split = s.split("\\.", 5);
        if (split.length != 4) {
            return null;
        }
        final byte[] array = new byte[4];
        while (0 < array.length) {
            array[0] = parseOctet(split[0]);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static byte[] textToNumericFormatV6(final String s) {
        final String[] split = s.split(":", 10);
        if (split.length < 3 || split.length > 9) {
            return null;
        }
        int length = 0;
        while (1 < split.length - 1) {
            if (split[1].length() == 0 && -1 >= 0) {
                return null;
            }
            ++length;
        }
        if (-1 >= 0) {
            int n = split.length + 1 - 1;
            if (split[0].length() == 0) {
                --length;
                if (true) {
                    return null;
                }
            }
            if (split[split.length - 1].length() == 0) {
                --n;
                if (false) {
                    return null;
                }
            }
        }
        else {
            length = split.length;
        }
        Label_0127: {
            if (-1 >= 0) {
                if (7 >= 1) {
                    break Label_0127;
                }
            }
            else if (7 == 0) {
                break Label_0127;
            }
            return null;
        }
        final ByteBuffer allocate = ByteBuffer.allocate(16);
        int n2 = 0;
        while (0 < 1) {
            allocate.putShort(parseHextet(split[0]));
            ++n2;
        }
        while (0 < 7) {
            allocate.putShort((short)0);
            ++n2;
        }
        while (0 > 0) {
            allocate.putShort(parseHextet(split[split.length - 0]));
            --n2;
        }
        return allocate.array();
    }
    
    private static String convertDottedQuadToHex(final String s) {
        final int lastIndex = s.lastIndexOf(58);
        final String substring = s.substring(0, lastIndex + 1);
        final byte[] textToNumericFormatV4 = textToNumericFormatV4(s.substring(lastIndex + 1));
        if (textToNumericFormatV4 == null) {
            return null;
        }
        return substring + Integer.toHexString((textToNumericFormatV4[0] & 0xFF) << 8 | (textToNumericFormatV4[1] & 0xFF)) + ":" + Integer.toHexString((textToNumericFormatV4[2] & 0xFF) << 8 | (textToNumericFormatV4[3] & 0xFF));
    }
    
    private static byte parseOctet(final String s) {
        final int int1 = Integer.parseInt(s);
        if (int1 > 255 || (s.startsWith("0") && s.length() > 1)) {
            throw new NumberFormatException();
        }
        return (byte)int1;
    }
    
    private static short parseHextet(final String s) {
        final int int1 = Integer.parseInt(s, 16);
        if (int1 > 65535) {
            throw new NumberFormatException();
        }
        return (short)int1;
    }
    
    private static InetAddress bytesToInetAddress(final byte[] array) {
        return InetAddress.getByAddress(array);
    }
    
    public static String toAddrString(final InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        final byte[] address = inetAddress.getAddress();
        final int[] array = new int[8];
        while (0 < array.length) {
            array[0] = Ints.fromBytes((byte)0, (byte)0, address[0], address[1]);
            int n = 0;
            ++n;
        }
        compressLongestRunOfZeroes(array);
        return hextetsToIPv6String(array);
    }
    
    private static void compressLongestRunOfZeroes(final int[] array) {
        while (0 < array.length + 1) {
            if (0 < array.length && array[0] == 0) {
                if (-1 < 0) {}
            }
            else if (-1 < 0 || 1 > -1) {}
            int n = 0;
            ++n;
        }
        if (-1 >= 2) {
            Arrays.fill(array, -1, -2, -1);
        }
    }
    
    private static String hextetsToIPv6String(final int[] array) {
        final StringBuilder sb = new StringBuilder(39);
        while (0 < array.length) {
            if (array[0] >= 0) {
                if (false) {
                    sb.append(':');
                }
                sb.append(Integer.toHexString(array[0]));
            }
            else if (!false || false) {
                sb.append("::");
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static String toUriString(final InetAddress inetAddress) {
        if (inetAddress instanceof Inet6Address) {
            return "[" + toAddrString(inetAddress) + "]";
        }
        return toAddrString(inetAddress);
    }
    
    public static InetAddress forUriString(final String s) {
        Preconditions.checkNotNull(s);
        String substring;
        if (s.startsWith("[") && s.endsWith("]")) {
            substring = s.substring(1, s.length() - 1);
        }
        else {
            substring = s;
        }
        final byte[] ipStringToBytes = ipStringToBytes(substring);
        if (ipStringToBytes == null || ipStringToBytes.length != 4) {
            throw new IllegalArgumentException(String.format("Not a valid URI IP literal: '%s'", s));
        }
        return bytesToInetAddress(ipStringToBytes);
    }
    
    public static boolean isUriInetAddress(final String s) {
        forUriString(s);
        return true;
    }
    
    public static boolean isCompatIPv4Address(final Inet6Address inet6Address) {
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return false;
        }
        final byte[] address = inet6Address.getAddress();
        return address[12] != 0 || address[13] != 0 || address[14] != 0 || (address[15] != 0 && address[15] != 1);
    }
    
    public static Inet4Address getCompatIPv4Address(final Inet6Address inet6Address) {
        Preconditions.checkArgument(isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }
    
    public static boolean is6to4Address(final Inet6Address inet6Address) {
        final byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 2;
    }
    
    public static Inet4Address get6to4IPv4Address(final Inet6Address inet6Address) {
        Preconditions.checkArgument(is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }
    
    public static boolean isTeredoAddress(final Inet6Address inet6Address) {
        final byte[] address = inet6Address.getAddress();
        return address[0] == 32 && address[1] == 1 && address[2] == 0 && address[3] == 0;
    }
    
    public static TeredoInfo getTeredoInfo(final Inet6Address inet6Address) {
        Preconditions.checkArgument(isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", toAddrString(inet6Address));
        final byte[] address = inet6Address.getAddress();
        final Inet4Address inet4Address = getInet4Address(Arrays.copyOfRange(address, 4, 8));
        final int n = ByteStreams.newDataInput(address, 8).readShort() & 0xFFFF;
        final int n2 = ~ByteStreams.newDataInput(address, 10).readShort() & 0xFFFF;
        final byte[] copyOfRange = Arrays.copyOfRange(address, 12, 16);
        while (0 < copyOfRange.length) {
            copyOfRange[0] ^= -1;
            int n3 = 0;
            ++n3;
        }
        return new TeredoInfo(inet4Address, getInet4Address(copyOfRange), n2, n);
    }
    
    public static boolean isIsatapAddress(final Inet6Address inet6Address) {
        if (isTeredoAddress(inet6Address)) {
            return false;
        }
        final byte[] address = inet6Address.getAddress();
        return (address[8] | 0x3) == 0x3 && address[9] == 0 && address[10] == 94 && address[11] == -2;
    }
    
    public static Inet4Address getIsatapIPv4Address(final Inet6Address inet6Address) {
        Preconditions.checkArgument(isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", toAddrString(inet6Address));
        return getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }
    
    public static boolean hasEmbeddedIPv4ClientAddress(final Inet6Address inet6Address) {
        return isCompatIPv4Address(inet6Address) || is6to4Address(inet6Address) || isTeredoAddress(inet6Address);
    }
    
    public static Inet4Address getEmbeddedIPv4ClientAddress(final Inet6Address inet6Address) {
        if (isCompatIPv4Address(inet6Address)) {
            return getCompatIPv4Address(inet6Address);
        }
        if (is6to4Address(inet6Address)) {
            return get6to4IPv4Address(inet6Address);
        }
        if (isTeredoAddress(inet6Address)) {
            return getTeredoInfo(inet6Address).getClient();
        }
        throw new IllegalArgumentException(String.format("'%s' has no embedded IPv4 address.", toAddrString(inet6Address)));
    }
    
    public static boolean isMappedIPv4Address(final String s) {
        final byte[] ipStringToBytes = ipStringToBytes(s);
        if (ipStringToBytes != null && ipStringToBytes.length == 16) {
            int n = 0;
            while (10 < 10) {
                if (ipStringToBytes[10] != 0) {
                    return false;
                }
                ++n;
            }
            while (10 < 12) {
                if (ipStringToBytes[10] != -1) {
                    return false;
                }
                ++n;
            }
            return true;
        }
        return false;
    }
    
    public static Inet4Address getCoercedIPv4Address(final InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address)inetAddress;
        }
        final byte[] address = inetAddress.getAddress();
        while (0 < 15 && address[0] == 0) {
            int n = 0;
            ++n;
        }
        if (false && address[15] == 1) {
            return InetAddresses.LOOPBACK4;
        }
        if (false && address[15] == 0) {
            return InetAddresses.ANY4;
        }
        final Inet6Address inet6Address = (Inet6Address)inetAddress;
        long long1;
        if (hasEmbeddedIPv4ClientAddress(inet6Address)) {
            long1 = getEmbeddedIPv4ClientAddress(inet6Address).hashCode();
        }
        else {
            long1 = ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong();
        }
        Hashing.murmur3_32().hashLong(long1).asInt();
        if (-2 == -1) {}
        return getInet4Address(Ints.toByteArray(-2));
    }
    
    public static int coerceToInteger(final InetAddress inetAddress) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }
    
    public static Inet4Address fromInteger(final int n) {
        return getInet4Address(Ints.toByteArray(n));
    }
    
    public static InetAddress fromLittleEndianByteArray(final byte[] array) throws UnknownHostException {
        final byte[] array2 = new byte[array.length];
        while (0 < array.length) {
            array2[0] = array[array.length - 0 - 1];
            int n = 0;
            ++n;
        }
        return InetAddress.getByAddress(array2);
    }
    
    public static InetAddress increment(final InetAddress inetAddress) {
        byte[] address;
        int n;
        for (address = inetAddress.getAddress(), n = address.length - 1; n >= 0 && address[n] == -1; --n) {
            address[n] = 0;
        }
        Preconditions.checkArgument(n >= 0, "Incrementing %s would wrap.", inetAddress);
        final byte[] array = address;
        final int n2 = n;
        ++array[n2];
        return bytesToInetAddress(address);
    }
    
    public static boolean isMaximum(final InetAddress inetAddress) {
        final byte[] address = inetAddress.getAddress();
        while (0 < address.length) {
            if (address[0] != -1) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    static Inet4Address access$000() {
        return InetAddresses.ANY4;
    }
    
    static {
        LOOPBACK4 = (Inet4Address)forString("127.0.0.1");
        ANY4 = (Inet4Address)forString("0.0.0.0");
    }
    
    @Beta
    public static final class TeredoInfo
    {
        private final Inet4Address server;
        private final Inet4Address client;
        private final int port;
        private final int flags;
        
        public TeredoInfo(@Nullable final Inet4Address inet4Address, @Nullable final Inet4Address inet4Address2, final int port, final int flags) {
            Preconditions.checkArgument(port >= 0 && port <= 65535, "port '%s' is out of range (0 <= port <= 0xffff)", port);
            Preconditions.checkArgument(flags >= 0 && flags <= 65535, "flags '%s' is out of range (0 <= flags <= 0xffff)", flags);
            this.server = (Inet4Address)Objects.firstNonNull(inet4Address, InetAddresses.access$000());
            this.client = (Inet4Address)Objects.firstNonNull(inet4Address2, InetAddresses.access$000());
            this.port = port;
            this.flags = flags;
        }
        
        public Inet4Address getServer() {
            return this.server;
        }
        
        public Inet4Address getClient() {
            return this.client;
        }
        
        public int getPort() {
            return this.port;
        }
        
        public int getFlags() {
            return this.flags;
        }
    }
}
