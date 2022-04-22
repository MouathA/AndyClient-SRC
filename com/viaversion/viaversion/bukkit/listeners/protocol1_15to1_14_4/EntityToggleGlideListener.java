package com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viaversion.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import org.bukkit.potion.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;
import org.bukkit.event.*;

public class EntityToggleGlideListener extends ViaBukkitListener
{
    private boolean swimmingMethodExists;
    
    public EntityToggleGlideListener(final ViaVersionPlugin viaVersionPlugin) {
        super((Plugin)viaVersionPlugin, Protocol1_15To1_14_4.class);
        Player.class.getMethod("isSwimming", (Class<?>[])new Class[0]);
        this.swimmingMethodExists = true;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void entityToggleGlide(final EntityToggleGlideEvent entityToggleGlideEvent) {
        if (!(entityToggleGlideEvent.getEntity() instanceof Player)) {
            return;
        }
        final Player player = (Player)entityToggleGlideEvent.getEntity();
        if (!this.isOnPipe(player)) {
            return;
        }
        if (entityToggleGlideEvent.isGliding() && entityToggleGlideEvent.isCancelled()) {
            final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_15.ENTITY_METADATA, null, this.getUserConnection(player));
            create.write(Type.VAR_INT, player.getEntityId());
            if (player.getFireTicks() > 0) {
                final byte b = 1;
            }
            if (player.isSneaking()) {
                final byte b2 = 2;
            }
            if (player.isSprinting()) {
                final byte b3 = 8;
            }
            if (this.swimmingMethodExists && player.isSwimming()) {
                final byte b4 = 16;
            }
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                final byte b5 = 32;
            }
            if (player.isGlowing()) {
                final byte b6 = 64;
            }
            create.write(Types1_14.METADATA_LIST, Arrays.asList(new Metadata(0, Types1_14.META_TYPES.byteType, 0)));
            create.scheduleSend(Protocol1_15To1_14_4.class);
        }
    }
}
