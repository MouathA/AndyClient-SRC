package io.netty.handler.codec.socks;

import java.net.*;
import io.netty.buffer.*;
import io.netty.util.*;

public final class SocksCmdResponse extends SocksResponse
{
    private final SocksCmdStatus cmdStatus;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;
    private static final byte[] DOMAIN_ZEROED;
    private static final byte[] IPv4_HOSTNAME_ZEROED;
    private static final byte[] IPv6_HOSTNAME_ZEROED;
    
    public SocksCmdResponse(final SocksCmdStatus socksCmdStatus, final SocksAddressType socksAddressType) {
        this(socksCmdStatus, socksAddressType, null, 0);
    }
    
    public SocksCmdResponse(final SocksCmdStatus cmdStatus, final SocksAddressType addressType, String ascii, final int port) {
        super(SocksResponseType.CMD);
        if (cmdStatus == null) {
            throw new NullPointerException("cmdStatus");
        }
        if (addressType == null) {
            throw new NullPointerException("addressType");
        }
        if (ascii != null) {
            switch (addressType) {
                case IPv4: {
                    if (!NetUtil.isValidIpV4Address(ascii)) {
                        throw new IllegalArgumentException(ascii + " is not a valid IPv4 address");
                    }
                    break;
                }
                case DOMAIN: {
                    if (IDN.toASCII(ascii).length() > 255) {
                        throw new IllegalArgumentException(ascii + " IDN: " + IDN.toASCII(ascii) + " exceeds 255 char limit");
                    }
                    break;
                }
                case IPv6: {
                    if (!NetUtil.isValidIpV6Address(ascii)) {
                        throw new IllegalArgumentException(ascii + " is not a valid IPv6 address");
                    }
                    break;
                }
            }
            ascii = IDN.toASCII(ascii);
        }
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException(port + " is not in bounds 0 <= x <= 65535");
        }
        this.cmdStatus = cmdStatus;
        this.addressType = addressType;
        this.host = ascii;
        this.port = port;
    }
    
    public SocksCmdStatus cmdStatus() {
        return this.cmdStatus;
    }
    
    public SocksAddressType addressType() {
        return this.addressType;
    }
    
    public String host() {
        if (this.host != null) {
            return IDN.toUnicode(this.host);
        }
        return null;
    }
    
    public int port() {
        return this.port;
    }
    
    @Override
    public void encodeAsByteBuf(final ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdStatus.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (this.addressType) {
            case IPv4: {
                byteBuf.writeBytes((this.host == null) ? SocksCmdResponse.IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                break;
            }
            case DOMAIN: {
                final byte[] array = (this.host == null) ? SocksCmdResponse.DOMAIN_ZEROED : this.host.getBytes(CharsetUtil.US_ASCII);
                byteBuf.writeByte(array.length);
                byteBuf.writeBytes(array);
                byteBuf.writeShort(this.port);
                break;
            }
            case IPv6: {
                byteBuf.writeBytes((this.host == null) ? SocksCmdResponse.IPv6_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                break;
            }
        }
    }
    
    static {
        DOMAIN_ZEROED = new byte[] { 0 };
        IPv4_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0 };
        IPv6_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    }
}
