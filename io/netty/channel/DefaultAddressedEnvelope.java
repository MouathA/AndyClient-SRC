package io.netty.channel;

import java.net.*;
import io.netty.util.*;
import io.netty.util.internal.*;

public class DefaultAddressedEnvelope implements AddressedEnvelope
{
    private final Object message;
    private final SocketAddress sender;
    private final SocketAddress recipient;
    
    public DefaultAddressedEnvelope(final Object message, final SocketAddress recipient, final SocketAddress sender) {
        if (message == null) {
            throw new NullPointerException("message");
        }
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
    
    public DefaultAddressedEnvelope(final Object o, final SocketAddress socketAddress) {
        this(o, socketAddress, null);
    }
    
    @Override
    public Object content() {
        return this.message;
    }
    
    @Override
    public SocketAddress sender() {
        return this.sender;
    }
    
    @Override
    public SocketAddress recipient() {
        return this.recipient;
    }
    
    @Override
    public int refCnt() {
        if (this.message instanceof ReferenceCounted) {
            return ((ReferenceCounted)this.message).refCnt();
        }
        return 1;
    }
    
    @Override
    public AddressedEnvelope retain() {
        ReferenceCountUtil.retain(this.message);
        return this;
    }
    
    @Override
    public AddressedEnvelope retain(final int n) {
        ReferenceCountUtil.retain(this.message, n);
        return this;
    }
    
    @Override
    public boolean release() {
        return ReferenceCountUtil.release(this.message);
    }
    
    @Override
    public boolean release(final int n) {
        return ReferenceCountUtil.release(this.message, n);
    }
    
    @Override
    public String toString() {
        if (this.sender != null) {
            return StringUtil.simpleClassName(this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')';
        }
        return StringUtil.simpleClassName(this) + "(=> " + this.recipient + ", " + this.message + ')';
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}
