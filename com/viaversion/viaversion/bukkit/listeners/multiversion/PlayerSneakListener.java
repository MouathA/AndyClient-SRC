package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.bukkit.listeners.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

public class PlayerSneakListener extends ViaBukkitListener
{
    private static final float STANDING_HEIGHT = 1.8f;
    private static final float HEIGHT_1_14 = 1.5f;
    private static final float HEIGHT_1_9 = 1.6f;
    private static final float DEFAULT_WIDTH = 0.6f;
    private final boolean is1_9Fix;
    private final boolean is1_14Fix;
    private Map sneaking;
    private Set sneakingUuids;
    private final Method getHandle;
    private Method setSize;
    private boolean useCache;
    
    public PlayerSneakListener(final ViaVersionPlugin viaVersionPlugin, final boolean is1_9Fix, final boolean is1_14Fix) throws ReflectiveOperationException {
        super((Plugin)viaVersionPlugin, null);
        this.is1_9Fix = is1_9Fix;
        this.is1_14Fix = is1_14Fix;
        final String name = viaVersionPlugin.getServer().getClass().getPackage().getName();
        this.getHandle = Class.forName(name + ".entity.CraftPlayer").getMethod("getHandle", (Class<?>[])new Class[0]);
        this.setSize = Class.forName(name.replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer").getMethod("setSize", Float.TYPE, Float.TYPE);
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
            this.sneaking = new WeakHashMap();
            this.useCache = true;
            viaVersionPlugin.getServer().getScheduler().runTaskTimer((Plugin)viaVersionPlugin, (Runnable)new Runnable() {
                final PlayerSneakListener this$0;
                
                @Override
                public void run() {
                    for (final Map.Entry<Player, V> entry : PlayerSneakListener.access$000(this.this$0).entrySet()) {
                        PlayerSneakListener.access$100(this.this$0, entry.getKey(), ((boolean)entry.getValue()) ? 1.5f : 1.6f);
                    }
                }
            }, 1L, 1L);
        }
        if (is1_14Fix) {
            this.sneakingUuids = new HashSet();
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerToggleSneak(final PlayerToggleSneakEvent playerToggleSneakEvent) {
        final Player player = playerToggleSneakEvent.getPlayer();
        final UserConnection userConnection = this.getUserConnection(player);
        if (userConnection == null) {
            return;
        }
        final ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (protocolInfo == null) {
            return;
        }
        final int protocolVersion = protocolInfo.getProtocolVersion();
        if (this.is1_14Fix && protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
            this.setHeight(player, playerToggleSneakEvent.isSneaking() ? 1.5f : 1.8f);
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneakingUuids.add(player.getUniqueId());
            }
            else {
                this.sneakingUuids.remove(player.getUniqueId());
            }
            if (!this.useCache) {
                return;
            }
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneaking.put(player, true);
            }
            else {
                this.sneaking.remove(player);
            }
        }
        else if (this.is1_9Fix && protocolVersion >= ProtocolVersion.v1_9.getVersion()) {
            this.setHeight(player, playerToggleSneakEvent.isSneaking() ? 1.6f : 1.8f);
            if (!this.useCache) {
                return;
            }
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneaking.put(player, false);
            }
            else {
                this.sneaking.remove(player);
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void playerDamage(final EntityDamageEvent entityDamageEvent) {
        if (!this.is1_14Fix) {
            return;
        }
        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.SUFFOCATION) {
            return;
        }
        if (entityDamageEvent.getEntityType() != EntityType.PLAYER) {
            return;
        }
        final Player player = (Player)entityDamageEvent.getEntity();
        if (!this.sneakingUuids.contains(player.getUniqueId())) {
            return;
        }
        final double n = player.getEyeLocation().getY() + 0.045;
        if (n - (int)n < 0.09) {
            entityDamageEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void playerQuit(final PlayerQuitEvent playerQuitEvent) {
        if (this.sneaking != null) {
            this.sneaking.remove(playerQuitEvent.getPlayer());
        }
        if (this.sneakingUuids != null) {
            this.sneakingUuids.remove(playerQuitEvent.getPlayer().getUniqueId());
        }
    }
    
    private void setHeight(final Player player, final float n) {
        this.setSize.invoke(this.getHandle.invoke(player, new Object[0]), 0.6f, n);
    }
    
    static Map access$000(final PlayerSneakListener playerSneakListener) {
        return playerSneakListener.sneaking;
    }
    
    static void access$100(final PlayerSneakListener playerSneakListener, final Player player, final float n) {
        playerSneakListener.setHeight(player, n);
    }
}
