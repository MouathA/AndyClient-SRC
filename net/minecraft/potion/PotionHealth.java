package net.minecraft.potion;

import net.minecraft.util.*;

public class PotionHealth extends Potion
{
    private static final String __OBFID;
    
    public PotionHealth(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public boolean isReady(final int n, final int n2) {
        return n >= 1;
    }
    
    static {
        __OBFID = "CL_00001527";
    }
}
