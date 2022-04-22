package io.netty.channel.socket;

import io.netty.buffer.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.*;

public final class DatagramPacket extends DefaultAddressedEnvelope implements ByteBufHolder
{
    public DatagramPacket(final ByteBuf byteBuf, final InetSocketAddress inetSocketAddress) {
        super(byteBuf, inetSocketAddress);
    }
    
    public DatagramPacket(final ByteBuf byteBuf, final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2) {
        super(byteBuf, inetSocketAddress, inetSocketAddress2);
    }
    
    @Override
    public DatagramPacket copy() {
        return new DatagramPacket(((ByteBuf)this.content()).copy(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }
    
    @Override
    public DatagramPacket duplicate() {
        return new DatagramPacket(((ByteBuf)this.content()).duplicate(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }
    
    @Override
    public DatagramPacket retain() {
        super.retain();
        return this;
    }
    
    @Override
    public DatagramPacket retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public AddressedEnvelope retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public AddressedEnvelope retain() {
        return this.retain();
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }
    
    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }
    
    @Override
    public ByteBuf content() {
        return (ByteBuf)super.content();
    }
}
