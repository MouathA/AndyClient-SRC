package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.type.*;
import io.netty.channel.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import java.util.*;

public interface PacketWrapper
{
    public static final int PASSTHROUGH_ID = 1000;
    
    default PacketWrapper create(final PacketType packetType, final UserConnection userConnection) {
        return create(packetType, null, userConnection);
    }
    
    default PacketWrapper create(final PacketType packetType, final ByteBuf byteBuf, final UserConnection userConnection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(packetType, byteBuf, userConnection);
    }
    
    @Deprecated
    default PacketWrapper create(final int n, final ByteBuf byteBuf, final UserConnection userConnection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(n, byteBuf, userConnection);
    }
    
    Object get(final Type p0, final int p1) throws Exception;
    
    boolean is(final Type p0, final int p1);
    
    boolean isReadable(final Type p0, final int p1);
    
    void set(final Type p0, final int p1, final Object p2) throws Exception;
    
    Object read(final Type p0) throws Exception;
    
    void write(final Type p0, final Object p1);
    
    Object passthrough(final Type p0) throws Exception;
    
    void passthroughAll() throws Exception;
    
    void writeToBuffer(final ByteBuf p0) throws Exception;
    
    void clearInputBuffer();
    
    void clearPacket();
    
    default void send(final Class clazz) throws Exception {
        this.send(clazz, true);
    }
    
    void send(final Class p0, final boolean p1) throws Exception;
    
    default void scheduleSend(final Class clazz) throws Exception {
        this.scheduleSend(clazz, true);
    }
    
    void scheduleSend(final Class p0, final boolean p1) throws Exception;
    
    ChannelFuture sendFuture(final Class p0) throws Exception;
    
    @Deprecated
    default void send() throws Exception {
        this.sendRaw();
    }
    
    void sendRaw() throws Exception;
    
    void scheduleSendRaw() throws Exception;
    
    default PacketWrapper create(final PacketType packetType) {
        return this.create(packetType.getId());
    }
    
    default PacketWrapper create(final PacketType packetType, final PacketHandler packetHandler) throws Exception {
        return this.create(packetType.getId(), packetHandler);
    }
    
    PacketWrapper create(final int p0);
    
    PacketWrapper create(final int p0, final PacketHandler p1) throws Exception;
    
    PacketWrapper apply(final Direction p0, final State p1, final int p2, final List p3, final boolean p4) throws Exception;
    
    PacketWrapper apply(final Direction p0, final State p1, final int p2, final List p3) throws Exception;
    
    void cancel();
    
    boolean isCancelled();
    
    UserConnection user();
    
    void resetReader();
    
    @Deprecated
    default void sendToServer() throws Exception {
        this.sendToServerRaw();
    }
    
    void sendToServerRaw() throws Exception;
    
    void scheduleSendToServerRaw() throws Exception;
    
    default void sendToServer(final Class clazz) throws Exception {
        this.sendToServer(clazz, true);
    }
    
    void sendToServer(final Class p0, final boolean p1) throws Exception;
    
    default void scheduleSendToServer(final Class clazz) throws Exception {
        this.scheduleSendToServer(clazz, true);
    }
    
    void scheduleSendToServer(final Class p0, final boolean p1) throws Exception;
    
    PacketType getPacketType();
    
    void setPacketType(final PacketType p0);
    
    int getId();
    
    @Deprecated
    default void setId(final PacketType packetType) {
        this.setPacketType(packetType);
    }
    
    @Deprecated
    void setId(final int p0);
}
