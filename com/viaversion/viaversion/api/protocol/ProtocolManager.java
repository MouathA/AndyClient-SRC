package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.version.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public interface ProtocolManager
{
    ServerProtocolVersion getServerProtocolVersion();
    
    Protocol getProtocol(final Class p0);
    
    default Protocol getProtocol(final ProtocolVersion protocolVersion, final ProtocolVersion protocolVersion2) {
        return this.getProtocol(protocolVersion.getVersion(), protocolVersion2.getVersion());
    }
    
    Protocol getProtocol(final int p0, final int p1);
    
    Protocol getBaseProtocol();
    
    Protocol getBaseProtocol(final int p0);
    
    default boolean isBaseProtocol(final Protocol protocol) {
        return protocol.isBaseProtocol();
    }
    
    void registerProtocol(final Protocol p0, final ProtocolVersion p1, final ProtocolVersion p2);
    
    void registerProtocol(final Protocol p0, final List p1, final int p2);
    
    void registerBaseProtocol(final Protocol p0, final Range p1);
    
    List getProtocolPath(final int p0, final int p1);
    
    VersionedPacketTransformer createPacketTransformer(final ProtocolVersion p0, final Class p1, final Class p2);
    
    boolean onlyCheckLoweringPathEntries();
    
    void setOnlyCheckLoweringPathEntries(final boolean p0);
    
    int getMaxProtocolPathSize();
    
    void setMaxProtocolPathSize(final int p0);
    
    SortedSet getSupportedVersions();
    
    boolean isWorkingPipe();
    
    void completeMappingDataLoading(final Class p0) throws Exception;
    
    boolean checkForMappingCompletion();
    
    void addMappingLoaderFuture(final Class p0, final Runnable p1);
    
    void addMappingLoaderFuture(final Class p0, final Class p1, final Runnable p2);
    
    CompletableFuture getMappingLoaderFuture(final Class p0);
    
    PacketWrapper createPacketWrapper(final PacketType p0, final ByteBuf p1, final UserConnection p2);
    
    @Deprecated
    PacketWrapper createPacketWrapper(final int p0, final ByteBuf p1, final UserConnection p2);
}
