package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import java.lang.reflect.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1.*;
import java.util.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.*;
import org.bukkit.event.inventory.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.bukkit.util.*;
import org.bukkit.inventory.*;

public class BukkitInventoryQuickMoveProvider extends InventoryQuickMoveProvider
{
    private final Map updateTasks;
    private final boolean supported;
    private Class windowClickPacketClass;
    private Object clickTypeEnum;
    private Method nmsItemMethod;
    private Method craftPlayerHandle;
    private Field connection;
    private Method packetMethod;
    
    public BukkitInventoryQuickMoveProvider() {
        this.updateTasks = new ConcurrentHashMap();
        this.supported = this.isSupported();
        this.setupReflection();
    }
    
    @Override
    public boolean registerQuickMoveAction(final short n, final short n2, final short n3, final UserConnection userConnection) {
        if (!this.supported) {
            return false;
        }
        if (n2 < 0) {
            return false;
        }
        if (n == 0 && n2 >= 36 && n2 <= 45 && Via.getAPI().getServerVersion().lowestSupportedVersion() == ProtocolVersion.v1_8.getVersion()) {
            return false;
        }
        final UUID uuid = userConnection.getProtocolInfo().getUuid();
        BukkitInventoryUpdateTask bukkitInventoryUpdateTask = this.updateTasks.get(uuid);
        final boolean b = bukkitInventoryUpdateTask != null;
        if (!b) {
            bukkitInventoryUpdateTask = new BukkitInventoryUpdateTask(this, uuid);
            this.updateTasks.put(uuid, bukkitInventoryUpdateTask);
        }
        bukkitInventoryUpdateTask.addItem(n, n2, n3);
        if (!b && Via.getPlatform().isPluginEnabled()) {
            Via.getPlatform().runSync(bukkitInventoryUpdateTask);
        }
        return true;
    }
    
    public Object buildWindowClickPacket(final Player player, final ItemTransaction itemTransaction) {
        if (!this.supported) {
            return null;
        }
        final InventoryView openInventory = player.getOpenInventory();
        short slotId = itemTransaction.getSlotId();
        final Inventory topInventory = openInventory.getTopInventory();
        final InventoryType inventoryType = (topInventory == null) ? null : topInventory.getType();
        if (inventoryType != null && Via.getAPI().getServerVersion().lowestSupportedVersion() == ProtocolVersion.v1_8.getVersion() && inventoryType == InventoryType.BREWING && slotId >= 5 && slotId <= 40) {
            --slotId;
        }
        Object item = null;
        if (slotId <= openInventory.countSlots()) {
            item = openInventory.getItem((int)slotId);
        }
        else {
            Via.getPlatform().getLogger().severe("Failed to get an item to create a window click packet. Please report this issue to the ViaVersion Github: " + ("Too many inventory slots: slotId: " + slotId + " invSlotCount: " + openInventory.countSlots() + " invType: " + openInventory.getType() + " topInvType: " + inventoryType));
        }
        final Object instance = this.windowClickPacketClass.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        final Object o = (item == null) ? null : this.nmsItemMethod.invoke(null, item);
        ReflectionUtil.set(instance, "a", (int)itemTransaction.getWindowId());
        ReflectionUtil.set(instance, "slot", (int)slotId);
        ReflectionUtil.set(instance, "button", 0);
        ReflectionUtil.set(instance, "d", itemTransaction.getActionId());
        ReflectionUtil.set(instance, "item", o);
        final int lowestSupportedVersion = Via.getAPI().getServerVersion().lowestSupportedVersion();
        if (lowestSupportedVersion == ProtocolVersion.v1_8.getVersion()) {
            ReflectionUtil.set(instance, "shift", 1);
        }
        else if (lowestSupportedVersion >= ProtocolVersion.v1_9.getVersion()) {
            ReflectionUtil.set(instance, "shift", this.clickTypeEnum);
        }
        return instance;
    }
    
    public boolean sendPacketToServer(final Player player, final Object o) {
        if (o == null) {
            return true;
        }
        this.packetMethod.invoke(this.connection.get(this.craftPlayerHandle.invoke(player, new Object[0])), o);
        return true;
    }
    
    public void onTaskExecuted(final UUID uuid) {
        this.updateTasks.remove(uuid);
    }
    
    private void setupReflection() {
        if (!this.supported) {
            return;
        }
        this.windowClickPacketClass = NMSUtil.nms("PacketPlayInWindowClick");
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
            this.clickTypeEnum = NMSUtil.nms("InventoryClickType").getEnumConstants()[1];
        }
        this.nmsItemMethod = NMSUtil.obc("inventory.CraftItemStack").getDeclaredMethod("asNMSCopy", ItemStack.class);
        this.craftPlayerHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", (Class[])new Class[0]);
        this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
        this.packetMethod = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", this.windowClickPacketClass);
    }
    
    private boolean isSupported() {
        final int lowestSupportedVersion = Via.getAPI().getServerVersion().lowestSupportedVersion();
        return lowestSupportedVersion >= ProtocolVersion.v1_8.getVersion() && lowestSupportedVersion <= ProtocolVersion.v1_11_1.getVersion();
    }
}
