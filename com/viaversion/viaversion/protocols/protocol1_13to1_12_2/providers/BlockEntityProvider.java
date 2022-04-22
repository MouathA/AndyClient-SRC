package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class BlockEntityProvider implements Provider
{
    private final Map handlers;
    
    public BlockEntityProvider() {
        (this.handlers = new HashMap()).put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:command_block", new CommandBlockHandler());
    }
    
    public int transform(final UserConnection userConnection, final Position position, final CompoundTag compoundTag, final boolean b) throws Exception {
        final Tag value = compoundTag.get("id");
        if (value == null) {
            return -1;
        }
        final String s = (String)value.getValue();
        final BlockEntityHandler blockEntityHandler = this.handlers.get(s);
        if (blockEntityHandler == null) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Unhandled BlockEntity " + s + " full tag: " + compoundTag);
            }
            return -1;
        }
        final int transform = blockEntityHandler.transform(userConnection, compoundTag);
        if (b && transform != -1) {
            this.sendBlockChange(userConnection, position, transform);
        }
        return transform;
    }
    
    private void sendBlockChange(final UserConnection userConnection, final Position position, final int n) throws Exception {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, userConnection);
        create.write(Type.POSITION, position);
        create.write(Type.VAR_INT, n);
        create.send(Protocol1_13To1_12_2.class);
    }
    
    public interface BlockEntityHandler
    {
        int transform(final UserConnection p0, final CompoundTag p1);
    }
}
