package org.apache.http.conn.util;

import org.apache.http.annotation.*;
import java.util.regex.*;

@Immutable
public class InetAddressUtils
{
    private static final String IPV4_BASIC_PATTERN_STRING = "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])";
    private static final Pattern IPV4_PATTERN;
    private static final Pattern IPV4_MAPPED_IPV6_PATTERN;
    private static final Pattern IPV6_STD_PATTERN;
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN;
    private static final char COLON_CHAR = ':';
    private static final int MAX_COLON_COUNT = 7;
    
    private InetAddressUtils() {
    }
    
    public static boolean isIPv4Address(final String s) {
        return InetAddressUtils.IPV4_PATTERN.matcher(s).matches();
    }
    
    public static boolean isIPv4MappedIPv64Address(final String s) {
        return InetAddressUtils.IPV4_MAPPED_IPV6_PATTERN.matcher(s).matches();
    }
    
    public static boolean isIPv6StdAddress(final String s) {
        return InetAddressUtils.IPV6_STD_PATTERN.matcher(s).matches();
    }
    
    public static boolean isIPv6HexCompressedAddress(final String s) {
        while (0 < s.length()) {
            if (s.charAt(0) == ':') {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0 <= 7 && InetAddressUtils.IPV6_HEX_COMPRESSED_PATTERN.matcher(s).matches();
    }
    
    public static boolean isIPv6Address(final String s) {
        return isIPv6StdAddress(s) || isIPv6HexCompressedAddress(s);
    }
    
    static {
        IPV4_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
        IPV4_MAPPED_IPV6_PATTERN = Pattern.compile("^::[fF]{4}:(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
        IPV6_STD_PATTERN = Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");
        IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)::(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$");
    }
}
