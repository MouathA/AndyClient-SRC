package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.utils.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class MappedLegacyBlockItem
{
    private final int id;
    private final short data;
    private final String name;
    private final Block block;
    private BlockEntityHandler blockEntityHandler;
    
    public MappedLegacyBlockItem(final int id, final short data, final String s, final boolean b) {
        this.id = id;
        this.data = data;
        this.name = ((s != null) ? ("§f" + s) : null);
        this.block = (b ? new Block(id, data) : null);
    }
    
    public int getId() {
        return this.id;
    }
    
    public short getData() {
        return this.data;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isBlock() {
        return this.block != null;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public boolean hasBlockEntityHandler() {
        return this.blockEntityHandler != null;
    }
    
    public BlockEntityHandler getBlockEntityHandler() {
        return this.blockEntityHandler;
    }
    
    public void setBlockEntityHandler(final BlockEntityHandler blockEntityHandler) {
        this.blockEntityHandler = blockEntityHandler;
    }
    
    @FunctionalInterface
    public interface BlockEntityHandler
    {
        CompoundTag handleOrNewCompoundTag(final int p0, final CompoundTag p1);
    }
}
