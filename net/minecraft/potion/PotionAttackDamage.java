package net.minecraft.potion;

import net.minecraft.util.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionAttackDamage extends Potion
{
    private static final String __OBFID;
    
    protected PotionAttackDamage(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public double func_111183_a(final int n, final AttributeModifier attributeModifier) {
        return (this.id == Potion.weakness.id) ? (-0.5f * (n + 1)) : (1.3 * (n + 1));
    }
    
    static {
        __OBFID = "CL_00001525";
    }
}
