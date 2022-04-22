package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;

public abstract class BlockDirectional extends Block
{
    public static final PropertyDirection AGE;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000227";
        AGE = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockDirectional(final Material material) {
        super(material);
    }
}
