package net.minecraft.item;

import net.minecraft.block.*;

public class ItemAnvilBlock extends ItemMultiTexture
{
    private static final String __OBFID;
    
    public ItemAnvilBlock(final Block block) {
        super(block, block, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
    }
    
    @Override
    public int getMetadata(final int n) {
        return n << 2;
    }
    
    static {
        __OBFID = "CL_00001764";
    }
}
