package com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1;

import com.viaversion.viaversion.bukkit.providers.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class BukkitInventoryUpdateTask implements Runnable
{
    private final BukkitInventoryQuickMoveProvider provider;
    private final UUID uuid;
    private final List items;
    
    public BukkitInventoryUpdateTask(final BukkitInventoryQuickMoveProvider provider, final UUID uuid) {
        this.provider = provider;
        this.uuid = uuid;
        this.items = Collections.synchronizedList(new ArrayList<Object>());
    }
    
    public void addItem(final short n, final short n2, final short n3) {
        this.items.add(new ItemTransaction(n, n2, n3));
    }
    
    @Override
    public void run() {
        final Player player = Bukkit.getServer().getPlayer(this.uuid);
        if (player == null) {
            this.provider.onTaskExecuted(this.uuid);
            return;
        }
        // monitorenter(items = this.items)
        final Iterator<ItemTransaction> iterator = (Iterator<ItemTransaction>)this.items.iterator();
        while (iterator.hasNext() && this.provider.sendPacketToServer(player, this.provider.buildWindowClickPacket(player, iterator.next()))) {}
        this.items.clear();
        // monitorexit(items)
        this.provider.onTaskExecuted(this.uuid);
    }
}
