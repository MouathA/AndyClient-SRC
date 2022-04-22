package us.myles.ViaVersion.api;

import io.netty.buffer.*;
import us.myles.ViaVersion.api.boss.*;
import java.util.*;

@Deprecated
public interface ViaAPI
{
    int getPlayerVersion(final Object p0);
    
    int getPlayerVersion(final UUID p0);
    
    default boolean isPorted(final UUID uuid) {
        return this.isInjected(uuid);
    }
    
    boolean isInjected(final UUID p0);
    
    String getVersion();
    
    void sendRawPacket(final Object p0, final ByteBuf p1);
    
    void sendRawPacket(final UUID p0, final ByteBuf p1);
    
    BossBar createBossBar(final String p0, final BossColor p1, final BossStyle p2);
    
    BossBar createBossBar(final String p0, final float p1, final BossColor p2, final BossStyle p3);
    
    SortedSet getSupportedVersions();
    
    SortedSet getFullSupportedVersions();
}
