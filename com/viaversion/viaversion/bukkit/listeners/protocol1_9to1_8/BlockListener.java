package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import org.bukkit.event.block.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class BlockListener extends ViaBukkitListener
{
    public BlockListener(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void placeBlock(final BlockPlaceEvent blockPlaceEvent) {
        if (this.isOnPipe(blockPlaceEvent.getPlayer())) {
            final Block blockPlaced = blockPlaceEvent.getBlockPlaced();
            ((EntityTracker1_9)this.getUserConnection(blockPlaceEvent.getPlayer()).getEntityTracker(Protocol1_9To1_8.class)).addBlockInteraction(new Position(blockPlaced.getX(), (short)blockPlaced.getY(), blockPlaced.getZ()));
        }
    }
}
