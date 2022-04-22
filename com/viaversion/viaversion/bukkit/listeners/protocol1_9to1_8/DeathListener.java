package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import com.viaversion.viaversion.api.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.connection.*;

public class DeathListener extends ViaBukkitListener
{
    public DeathListener(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDeath(final PlayerDeathEvent playerDeathEvent) {
        final Player entity = playerDeathEvent.getEntity();
        if (this.isOnPipe(entity) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(entity.getWorld()) && playerDeathEvent.getDeathMessage() != null) {
            this.sendPacket(entity, playerDeathEvent.getDeathMessage());
        }
    }
    
    public boolean checkGamerule(final World world) {
        return Boolean.parseBoolean(world.getGameRuleValue("showDeathMessages"));
    }
    
    private void sendPacket(final Player player, final String s) {
        Via.getPlatform().runSync(new Runnable(player, s) {
            final Player val$p;
            final String val$msg;
            final DeathListener this$0;
            
            @Override
            public void run() {
                final UserConnection access$000 = DeathListener.access$000(this.this$0, this.val$p);
                if (access$000 != null) {
                    final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.COMBAT_EVENT, null, access$000);
                    create.write(Type.VAR_INT, 2);
                    create.write(Type.VAR_INT, this.val$p.getEntityId());
                    create.write(Type.INT, this.val$p.getEntityId());
                    Protocol1_9To1_8.FIX_JSON.write(create, this.val$msg);
                    create.scheduleSend(Protocol1_9To1_8.class);
                }
            }
        });
    }
    
    static UserConnection access$000(final DeathListener deathListener, final Player player) {
        return deathListener.getUserConnection(player);
    }
}
