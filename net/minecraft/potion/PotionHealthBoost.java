package net.minecraft.potion;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionHealthBoost extends Potion
{
    private static final String __OBFID;
    
    public PotionHealthBoost(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, n);
        if (entityLivingBase.getHealth() > entityLivingBase.getMaxHealth()) {
            entityLivingBase.setHealth(entityLivingBase.getMaxHealth());
        }
    }
    
    static {
        __OBFID = "CL_00001526";
    }
}
