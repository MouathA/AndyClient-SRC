package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public abstract class BlockRotatedPillar extends Block
{
    public static final PropertyEnum field_176298_M;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000302";
        field_176298_M = PropertyEnum.create("axis", EnumFacing.Axis.class);
    }
    
    protected BlockRotatedPillar(final Material material) {
        super(material);
    }
}
