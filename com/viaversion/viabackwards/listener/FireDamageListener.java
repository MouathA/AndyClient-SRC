package com.viaversion.viabackwards.listener;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class FireDamageListener extends ViaBukkitListener
{
    public FireDamageListener(final BukkitPlugin bukkitPlugin) {
        super((Plugin)bukkitPlugin, Protocol1_11_1To1_12.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFireDamage(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntityType() != EntityType.PLAYER) {
            return;
        }
        final EntityDamageEvent.DamageCause cause = entityDamageEvent.getCause();
        if (cause != EntityDamageEvent.DamageCause.FIRE && cause != EntityDamageEvent.DamageCause.FIRE_TICK && cause != EntityDamageEvent.DamageCause.LAVA && cause != EntityDamageEvent.DamageCause.DROWNING) {
            return;
        }
        final Player player = (Player)entityDamageEvent.getEntity();
        if (this.isOnPipe(player)) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}
