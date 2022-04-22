package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.*;
import org.bukkit.entity.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import org.bukkit.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;

public class BukkitViaAPI extends ViaAPIBase
{
    private final ViaVersionPlugin plugin;
    
    public BukkitViaAPI(final ViaVersionPlugin plugin) {
        this.plugin = plugin;
    }
    
    public int getPlayerVersion(final Player player) {
        return this.getPlayerVersion(player.getUniqueId());
    }
    
    @Override
    public int getPlayerVersion(final UUID uuid) {
        final UserConnection connectedClient = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connectedClient != null) {
            return connectedClient.getProtocolInfo().getProtocolVersion();
        }
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null && this.isProtocolSupport()) {
            return ProtocolSupportUtil.getProtocolVersion(player);
        }
        return -1;
    }
    
    public void sendRawPacket(final Player player, final ByteBuf byteBuf) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), byteBuf);
    }
    
    public boolean isCompatSpigotBuild() {
        return this.plugin.isCompatSpigotBuild();
    }
    
    public boolean isProtocolSupport() {
        return this.plugin.isProtocolSupport();
    }
    
    @Override
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        this.sendRawPacket((Player)o, byteBuf);
    }
    
    @Override
    public int getPlayerVersion(final Object o) {
        return this.getPlayerVersion((Player)o);
    }
}
