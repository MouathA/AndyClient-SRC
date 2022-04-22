package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.data.entity.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.function.*;
import io.netty.channel.*;
import java.util.*;

public interface UserConnection
{
    StorableObject get(final Class p0);
    
    boolean has(final Class p0);
    
    void put(final StorableObject p0);
    
    Collection getEntityTrackers();
    
    EntityTracker getEntityTracker(final Class p0);
    
    void addEntityTracker(final Class p0, final EntityTracker p1);
    
    void clearStoredObjects();
    
    void sendRawPacket(final ByteBuf p0);
    
    void scheduleSendRawPacket(final ByteBuf p0);
    
    ChannelFuture sendRawPacketFuture(final ByteBuf p0);
    
    PacketTracker getPacketTracker();
    
    void disconnect(final String p0);
    
    void sendRawPacketToServer(final ByteBuf p0);
    
    void scheduleSendRawPacketToServer(final ByteBuf p0);
    
    boolean checkServerboundPacket();
    
    boolean checkClientboundPacket();
    
    default boolean checkIncomingPacket() {
        return this.isClientSide() ? this.checkClientboundPacket() : this.checkServerboundPacket();
    }
    
    default boolean checkOutgoingPacket() {
        return this.isClientSide() ? this.checkServerboundPacket() : this.checkClientboundPacket();
    }
    
    boolean shouldTransformPacket();
    
    void transformClientbound(final ByteBuf p0, final Function p1) throws Exception;
    
    void transformServerbound(final ByteBuf p0, final Function p1) throws Exception;
    
    default void transformOutgoing(final ByteBuf byteBuf, final Function function) throws Exception {
        if (this.isClientSide()) {
            this.transformServerbound(byteBuf, function);
        }
        else {
            this.transformClientbound(byteBuf, function);
        }
    }
    
    default void transformIncoming(final ByteBuf byteBuf, final Function function) throws Exception {
        if (this.isClientSide()) {
            this.transformClientbound(byteBuf, function);
        }
        else {
            this.transformServerbound(byteBuf, function);
        }
    }
    
    long getId();
    
    Channel getChannel();
    
    ProtocolInfo getProtocolInfo();
    
    Map getStoredObjects();
    
    boolean isActive();
    
    void setActive(final boolean p0);
    
    boolean isPendingDisconnect();
    
    void setPendingDisconnect(final boolean p0);
    
    boolean isClientSide();
    
    boolean shouldApplyBlockProtocol();
    
    boolean isPacketLimiterEnabled();
    
    void setPacketLimiterEnabled(final boolean p0);
    
    UUID generatePassthroughToken();
}
