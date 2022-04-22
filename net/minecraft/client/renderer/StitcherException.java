package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;

public class StitcherException extends RuntimeException
{
    private final Stitcher.Holder field_98149_a;
    private static final String __OBFID;
    
    public StitcherException(final Stitcher.Holder field_98149_a, final String s) {
        super(s);
        this.field_98149_a = field_98149_a;
    }
    
    static {
        __OBFID = "CL_00001057";
    }
}
