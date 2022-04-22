package io.netty.handler.codec.socks;

import java.net.*;
import io.netty.buffer.*;
import io.netty.util.*;

public final class SocksCmdRequest extends SocksRequest
{
    private final SocksCmdType cmdType;
    private final SocksAddressType addressType;
    private final String host;
    private final int port;
    
    public SocksCmdRequest(final SocksCmdType cmdType, final SocksAddressType addressType, final String s, final int port) {
        super(SocksRequestType.CMD);
        if (cmdType == null) {
            throw new NullPointerException("cmdType");
        }
        if (addressType == null) {
            throw new NullPointerException("addressType");
        }
        if (s == null) {
            throw new NullPointerException("host");
        }
        switch (addressType) {
            case IPv4: {
                if (!NetUtil.isValidIpV4Address(s)) {
                    throw new IllegalArgumentException(s + " is not a valid IPv4 address");
                }
                break;
            }
            case DOMAIN: {
                if (IDN.toASCII(s).length() > 255) {
                    throw new IllegalArgumentException(s + " IDN: " + IDN.toASCII(s) + " exceeds 255 char limit");
                }
                break;
            }
            case IPv6: {
                if (!NetUtil.isValidIpV6Address(s)) {
                    throw new IllegalArgumentException(s + " is not a valid IPv6 address");
                }
                break;
            }
        }
        if (port <= 0 || port >= 65536) {
            throw new IllegalArgumentException(port + " is not in bounds 0 < x < 65536");
        }
        this.cmdType = cmdType;
        this.addressType = addressType;
        this.host = IDN.toASCII(s);
        this.port = port;
    }
    
    public SocksCmdType cmdType() {
        return this.cmdType;
    }
    
    public SocksAddressType addressType() {
        return this.addressType;
    }
    
    public String host() {
        return IDN.toUnicode(this.host);
    }
    
    public int port() {
        return this.port;
    }
    
    @Override
    public void encodeAsByteBuf(final ByteBuf byteBuf) {
        byteBuf.writeByte(this.protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdType.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (this.addressType) {
            case IPv4: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                break;
            }
            case DOMAIN: {
                byteBuf.writeByte(this.host.length());
                byteBuf.writeBytes(this.host.getBytes(CharsetUtil.US_ASCII));
                byteBuf.writeShort(this.port);
                break;
            }
            case IPv6: {
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                break;
            }
        }
    }
}
