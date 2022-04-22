package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.api.connection.*;
import org.bukkit.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import org.bukkit.entity.*;

public class BukkitViaMovementTransmitter extends MovementTransmitterProvider
{
    private static boolean USE_NMS;
    private Object idlePacket;
    private Object idlePacket2;
    private Method getHandle;
    private Field connection;
    private Method handleFlying;
    
    public BukkitViaMovementTransmitter() {
        BukkitViaMovementTransmitter.USE_NMS = Via.getConfig().isNMSPlayerTicking();
        final Class nms = NMSUtil.nms("PacketPlayInFlying");
        this.idlePacket = nms.newInstance();
        this.idlePacket2 = nms.newInstance();
        final Field declaredField = nms.getDeclaredField("f");
        declaredField.setAccessible(true);
        declaredField.set(this.idlePacket2, true);
        if (BukkitViaMovementTransmitter.USE_NMS) {
            this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", (Class[])new Class[0]);
            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
            this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", nms);
        }
    }
    
    @Override
    public Object getFlyingPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket2;
    }
    
    @Override
    public Object getGroundPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket;
    }
    
    @Override
    public void sendPlayer(final UserConnection userConnection) {
        if (BukkitViaMovementTransmitter.USE_NMS) {
            final Player player = Bukkit.getPlayer(userConnection.getProtocolInfo().getUuid());
            if (player != null) {
                final Object value = this.connection.get(this.getHandle.invoke(player, new Object[0]));
                if (value != null) {
                    this.handleFlying.invoke(value, ((MovementTracker)userConnection.get(MovementTracker.class)).isGround() ? this.idlePacket2 : this.idlePacket);
                    ((MovementTracker)userConnection.get(MovementTracker.class)).incrementIdlePacket();
                }
            }
        }
        else {
            super.sendPlayer(userConnection);
        }
    }
    
    static {
        BukkitViaMovementTransmitter.USE_NMS = true;
    }
}
