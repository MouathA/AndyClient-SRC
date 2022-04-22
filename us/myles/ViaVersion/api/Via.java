package us.myles.ViaVersion.api;

import io.netty.buffer.*;
import us.myles.ViaVersion.api.boss.*;
import java.util.*;

@Deprecated
public class Via implements ViaAPI
{
    private static final ViaAPI INSTANCE;
    
    private Via() {
    }
    
    @Deprecated
    public static ViaAPI getAPI() {
        return Via.INSTANCE;
    }
    
    @Override
    public int getPlayerVersion(final Object o) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(o);
    }
    
    @Override
    public int getPlayerVersion(final UUID uuid) {
        return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(uuid);
    }
    
    @Override
    public boolean isInjected(final UUID uuid) {
        return com.viaversion.viaversion.api.Via.getAPI().isInjected(uuid);
    }
    
    @Override
    public String getVersion() {
        return com.viaversion.viaversion.api.Via.getAPI().getVersion();
    }
    
    @Override
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(o, byteBuf);
    }
    
    @Override
    public void sendRawPacket(final UUID uuid, final ByteBuf byteBuf) {
        com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(uuid, byteBuf);
    }
    
    @Override
    public BossBar createBossBar(final String s, final BossColor bossColor, final BossStyle bossStyle) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(s, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]));
    }
    
    @Override
    public BossBar createBossBar(final String s, final float n, final BossColor bossColor, final BossStyle bossStyle) {
        return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(s, n, com.viaversion.viaversion.api.legacy.bossbar.BossColor.values()[bossColor.ordinal()], com.viaversion.viaversion.api.legacy.bossbar.BossStyle.values()[bossStyle.ordinal()]));
    }
    
    @Override
    public SortedSet getSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getSupportedVersions();
    }
    
    @Override
    public SortedSet getFullSupportedVersions() {
        return com.viaversion.viaversion.api.Via.getAPI().getFullSupportedVersions();
    }
    
    static {
        INSTANCE = new Via();
    }
}
