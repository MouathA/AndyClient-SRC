package net.minecraft.client.renderer.block.model;

import javax.vecmath.*;
import net.minecraft.util.*;

public class BlockPartRotation
{
    public final Vector3f field_178344_a;
    public final EnumFacing.Axis field_178342_b;
    public final float field_178343_c;
    public final boolean field_178341_d;
    private static final String __OBFID;
    
    public BlockPartRotation(final Vector3f field_178344_a, final EnumFacing.Axis field_178342_b, final float field_178343_c, final boolean field_178341_d) {
        this.field_178344_a = field_178344_a;
        this.field_178342_b = field_178342_b;
        this.field_178343_c = field_178343_c;
        this.field_178341_d = field_178341_d;
    }
    
    static {
        __OBFID = "CL_00002506";
    }
}
