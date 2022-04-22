package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import org.bukkit.scheduler.*;
import java.util.concurrent.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.inventory.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class HandItemCache extends BukkitRunnable
{
    private final Map handCache;
    
    public HandItemCache() {
        this.handCache = new ConcurrentHashMap();
    }
    
    public void run() {
        final ArrayList<UUID> list = (ArrayList<UUID>)new ArrayList<Object>(this.handCache.keySet());
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.handCache.put(player.getUniqueId(), convert(player.getItemInHand()));
            list.remove(player.getUniqueId());
        }
        final Iterator<Object> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            this.handCache.remove(iterator2.next());
        }
    }
    
    public Item getHandItem(final UUID uuid) {
        return this.handCache.get(uuid);
    }
    
    public static Item convert(final ItemStack itemStack) {
        if (itemStack == null) {
            return new DataItem(0, (byte)0, (short)0, null);
        }
        return new DataItem(itemStack.getTypeId(), (byte)itemStack.getAmount(), itemStack.getDurability(), null);
    }
}
