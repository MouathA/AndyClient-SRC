package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.api.rewriter.*;

public interface Protocol
{
    default void registerServerbound(final State state, final int n, final int n2) {
        this.registerServerbound(state, n, n2, null);
    }
    
    default void registerServerbound(final State state, final int n, final int n2, final PacketRemapper packetRemapper) {
        this.registerServerbound(state, n, n2, packetRemapper, false);
    }
    
    void registerServerbound(final State p0, final int p1, final int p2, final PacketRemapper p3, final boolean p4);
    
    @Deprecated
    void cancelServerbound(final State p0, final int p1, final int p2);
    
    void cancelServerbound(final State p0, final int p1);
    
    default void registerClientbound(final State state, final int n, final int n2) {
        this.registerClientbound(state, n, n2, null);
    }
    
    default void registerClientbound(final State state, final int n, final int n2, final PacketRemapper packetRemapper) {
        this.registerClientbound(state, n, n2, packetRemapper, false);
    }
    
    @Deprecated
    void cancelClientbound(final State p0, final int p1, final int p2);
    
    void cancelClientbound(final State p0, final int p1);
    
    void registerClientbound(final State p0, final int p1, final int p2, final PacketRemapper p3, final boolean p4);
    
    void registerClientbound(final ClientboundPacketType p0, final PacketRemapper p1);
    
    default void registerClientbound(final ClientboundPacketType clientboundPacketType, final ClientboundPacketType clientboundPacketType2) {
        this.registerClientbound(clientboundPacketType, clientboundPacketType2, null);
    }
    
    default void registerClientbound(final ClientboundPacketType clientboundPacketType, final ClientboundPacketType clientboundPacketType2, final PacketRemapper packetRemapper) {
        this.registerClientbound(clientboundPacketType, clientboundPacketType2, packetRemapper, false);
    }
    
    void registerClientbound(final ClientboundPacketType p0, final ClientboundPacketType p1, final PacketRemapper p2, final boolean p3);
    
    void cancelClientbound(final ClientboundPacketType p0);
    
    default void registerServerbound(final ServerboundPacketType serverboundPacketType, final ServerboundPacketType serverboundPacketType2) {
        this.registerServerbound(serverboundPacketType, serverboundPacketType2, null);
    }
    
    void registerServerbound(final ServerboundPacketType p0, final PacketRemapper p1);
    
    default void registerServerbound(final ServerboundPacketType serverboundPacketType, final ServerboundPacketType serverboundPacketType2, final PacketRemapper packetRemapper) {
        this.registerServerbound(serverboundPacketType, serverboundPacketType2, packetRemapper, false);
    }
    
    void registerServerbound(final ServerboundPacketType p0, final ServerboundPacketType p1, final PacketRemapper p2, final boolean p3);
    
    void cancelServerbound(final ServerboundPacketType p0);
    
    boolean hasRegisteredClientbound(final ClientboundPacketType p0);
    
    boolean hasRegisteredServerbound(final ServerboundPacketType p0);
    
    boolean hasRegisteredClientbound(final State p0, final int p1);
    
    boolean hasRegisteredServerbound(final State p0, final int p1);
    
    void transform(final Direction p0, final State p1, final PacketWrapper p2) throws Exception;
    
    Object get(final Class p0);
    
    void put(final Object p0);
    
    void initialize();
    
    boolean hasMappingDataToLoad();
    
    void loadMappingData();
    
    default void register(final ViaProviders viaProviders) {
    }
    
    default void init(final UserConnection userConnection) {
    }
    
    default MappingData getMappingData() {
        return null;
    }
    
    default EntityRewriter getEntityRewriter() {
        return null;
    }
    
    default ItemRewriter getItemRewriter() {
        return null;
    }
    
    default boolean isBaseProtocol() {
        return false;
    }
}
