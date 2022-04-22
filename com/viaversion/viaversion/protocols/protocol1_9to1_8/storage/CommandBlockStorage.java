package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class CommandBlockStorage implements StorableObject
{
    private final Map storedCommandBlocks;
    private boolean permissions;
    
    public CommandBlockStorage() {
        this.storedCommandBlocks = new ConcurrentHashMap();
        this.permissions = false;
    }
    
    public void unloadChunk(final int n, final int n2) {
        this.storedCommandBlocks.remove(new Pair(n, n2));
    }
    
    public void addOrUpdateBlock(final Position position, final CompoundTag compoundTag) {
        final Pair chunkCoords = this.getChunkCoords(position);
        if (!this.storedCommandBlocks.containsKey(chunkCoords)) {
            this.storedCommandBlocks.put(chunkCoords, new ConcurrentHashMap());
        }
        final Map<Object, CompoundTag> map = this.storedCommandBlocks.get(chunkCoords);
        if (map.containsKey(position) && map.get(position).equals(compoundTag)) {
            return;
        }
        map.put(position, compoundTag);
    }
    
    private Pair getChunkCoords(final Position position) {
        return new Pair(Math.floorDiv(position.x(), 16), Math.floorDiv(position.z(), 16));
    }
    
    public Optional getCommandBlock(final Position position) {
        final Map<Object, CompoundTag> map = this.storedCommandBlocks.get(this.getChunkCoords(position));
        if (map == null) {
            return Optional.empty();
        }
        final CompoundTag compoundTag = map.get(position);
        if (compoundTag == null) {
            return Optional.empty();
        }
        final CompoundTag clone = compoundTag.clone();
        clone.put("powered", new ByteTag((byte)0));
        clone.put("auto", new ByteTag((byte)0));
        clone.put("conditionMet", new ByteTag((byte)0));
        return Optional.of(clone);
    }
    
    public void unloadChunks() {
        this.storedCommandBlocks.clear();
    }
    
    public boolean isPermissions() {
        return this.permissions;
    }
    
    public void setPermissions(final boolean permissions) {
        this.permissions = permissions;
    }
}
