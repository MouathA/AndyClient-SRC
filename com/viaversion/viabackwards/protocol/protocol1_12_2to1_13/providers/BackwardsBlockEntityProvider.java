package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class BackwardsBlockEntityProvider implements Provider
{
    private final Map handlers;
    
    public BackwardsBlockEntityProvider() {
        (this.handlers = new HashMap()).put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:piston", new PistonHandler());
    }
    
    public boolean isHandled(final String s) {
        return this.handlers.containsKey(s);
    }
    
    public CompoundTag transform(final UserConnection userConnection, final Position position, final CompoundTag compoundTag) throws Exception {
        final Tag value = compoundTag.get("id");
        if (!(value instanceof StringTag)) {
            return compoundTag;
        }
        final String s = (String)value.getValue();
        final BackwardsBlockEntityHandler backwardsBlockEntityHandler = this.handlers.get(s);
        if (backwardsBlockEntityHandler == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Unhandled BlockEntity " + s + " full tag: " + compoundTag);
            }
            return compoundTag;
        }
        final Integer value2 = ((BackwardsBlockStorage)userConnection.get(BackwardsBlockStorage.class)).get(position);
        if (value2 == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Handled BlockEntity does not have a stored block :( " + s + " full tag: " + compoundTag);
            }
            return compoundTag;
        }
        return backwardsBlockEntityHandler.transform(userConnection, value2, compoundTag);
    }
    
    public CompoundTag transform(final UserConnection userConnection, final Position position, final String s) throws Exception {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("id", new StringTag(s));
        compoundTag.put("x", new IntTag(Math.toIntExact(position.getX())));
        compoundTag.put("y", new IntTag(Math.toIntExact(position.getY())));
        compoundTag.put("z", new IntTag(Math.toIntExact(position.getZ())));
        return this.transform(userConnection, position, compoundTag);
    }
    
    public interface BackwardsBlockEntityHandler
    {
        CompoundTag transform(final UserConnection p0, final int p1, final CompoundTag p2);
    }
}
