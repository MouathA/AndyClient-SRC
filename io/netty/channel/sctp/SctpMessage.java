package io.netty.channel.sctp;

import com.sun.nio.sctp.*;
import io.netty.buffer.*;
import io.netty.util.*;

public final class SctpMessage extends DefaultByteBufHolder
{
    private final int streamIdentifier;
    private final int protocolIdentifier;
    private final MessageInfo msgInfo;
    
    public SctpMessage(final int protocolIdentifier, final int streamIdentifier, final ByteBuf byteBuf) {
        super(byteBuf);
        this.protocolIdentifier = protocolIdentifier;
        this.streamIdentifier = streamIdentifier;
        this.msgInfo = null;
    }
    
    public SctpMessage(final MessageInfo msgInfo, final ByteBuf byteBuf) {
        super(byteBuf);
        if (msgInfo == null) {
            throw new NullPointerException("msgInfo");
        }
        this.msgInfo = msgInfo;
        this.streamIdentifier = msgInfo.streamNumber();
        this.protocolIdentifier = msgInfo.payloadProtocolID();
    }
    
    public int streamIdentifier() {
        return this.streamIdentifier;
    }
    
    public int protocolIdentifier() {
        return this.protocolIdentifier;
    }
    
    public MessageInfo messageInfo() {
        return this.msgInfo;
    }
    
    public boolean isComplete() {
        return this.msgInfo == null || this.msgInfo.isComplete();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SctpMessage sctpMessage = (SctpMessage)o;
        return this.protocolIdentifier == sctpMessage.protocolIdentifier && this.streamIdentifier == sctpMessage.streamIdentifier && this.content().equals(sctpMessage.content());
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * this.streamIdentifier + this.protocolIdentifier) + this.content().hashCode();
    }
    
    @Override
    public SctpMessage copy() {
        if (this.msgInfo == null) {
            return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, this.content().copy());
        }
        return new SctpMessage(this.msgInfo, this.content().copy());
    }
    
    @Override
    public SctpMessage duplicate() {
        if (this.msgInfo == null) {
            return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, this.content().duplicate());
        }
        return new SctpMessage(this.msgInfo, this.content().copy());
    }
    
    @Override
    public SctpMessage retain() {
        super.retain();
        return this;
    }
    
    @Override
    public SctpMessage retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public String toString() {
        if (this.refCnt() == 0) {
            return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=(FREED)}";
        }
        return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=" + ByteBufUtil.hexDump(this.content()) + '}';
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
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}
