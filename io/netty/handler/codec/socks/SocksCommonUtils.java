package io.netty.handler.codec.socks;

import io.netty.util.internal.*;

final class SocksCommonUtils
{
    public static final SocksRequest UNKNOWN_SOCKS_REQUEST;
    public static final SocksResponse UNKNOWN_SOCKS_RESPONSE;
    private static final int SECOND_ADDRESS_OCTET_SHIFT = 16;
    private static final int FIRST_ADDRESS_OCTET_SHIFT = 24;
    private static final int THIRD_ADDRESS_OCTET_SHIFT = 8;
    private static final int XOR_DEFAULT_VALUE = 255;
    private static final char[] ipv6conseqZeroFiller;
    private static final char ipv6hextetSeparator = ':';
    static final boolean $assertionsDisabled;
    
    private SocksCommonUtils() {
    }
    
    public static String intToIp(final int n) {
        return String.valueOf(n >> 24 & 0xFF) + '.' + (n >> 16 & 0xFF) + '.' + (n >> 8 & 0xFF) + '.' + (n & 0xFF);
    }
    
    public static String ipv6toCompressedForm(final byte[] array) {
        assert array.length == 16;
        while (true) {
            if (0 < array.length && array[0] == 0 && array[1] == 0) {
                final int n;
                n += 2;
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public static String ipv6toStr(final byte[] array) {
        assert array.length == 16;
        final StringBuilder sb = new StringBuilder(39);
        ipv6toStr(sb, array, 0, 8);
        return sb.toString();
    }
    
    private static void ipv6toStr(final StringBuilder sb, final byte[] array, final int n, int n2) {
        --n2;
        int i;
        for (i = n; i < n2; ++i) {
            appendHextet(sb, array, i);
            sb.append(':');
        }
        appendHextet(sb, array, i);
    }
    
    private static void appendHextet(final StringBuilder sb, final byte[] array, final int n) {
        StringUtil.toHexString(sb, array, n << 1, 2);
    }
    
    static {
        $assertionsDisabled = !SocksCommonUtils.class.desiredAssertionStatus();
        UNKNOWN_SOCKS_REQUEST = new UnknownSocksRequest();
        UNKNOWN_SOCKS_RESPONSE = new UnknownSocksResponse();
        ipv6conseqZeroFiller = new char[] { ':', ':' };
    }
}
