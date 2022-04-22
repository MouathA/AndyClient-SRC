package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockCarrot extends BlockCrops
{
    private static final String __OBFID;
    
    @Override
    protected Item getSeed() {
        return Items.carrot;
    }
    
    @Override
    protected Item getCrop() {
        return Items.carrot;
    }
    
    static {
        __OBFID = "CL_00000212";
    }
}
