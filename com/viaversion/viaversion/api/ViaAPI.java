package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import java.util.*;
import com.viaversion.viaversion.api.legacy.*;

public interface ViaAPI
{
    default int majorVersion() {
        return 4;
    }
    
    default int apiVersion() {
        return 10;
    }
    
    ServerProtocolVersion getServerVersion();
    
    int getPlayerVersion(final Object p0);
    
    int getPlayerVersion(final UUID p0);
    
    boolean isInjected(final UUID p0);
    
    UserConnection getConnection(final UUID p0);
    
    String getVersion();
    
    void sendRawPacket(final Object p0, final ByteBuf p1);
    
    void sendRawPacket(final UUID p0, final ByteBuf p1);
    
    SortedSet getSupportedVersions();
    
    SortedSet getFullSupportedVersions();
    
    LegacyViaAPI legacyAPI();
}
