package net.minecraft.item;

import net.minecraft.block.*;

public class ItemPiston extends ItemBlock
{
    private static final String __OBFID;
    
    public ItemPiston(final Block block) {
        super(block);
    }
    
    @Override
    public int getMetadata(final int n) {
        return 7;
    }
    
    static {
        __OBFID = "CL_00000054";
    }
}
