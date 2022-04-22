package net.minecraft.potion;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionAbsoption extends Potion
{
    private static final String __OBFID;
    
    protected PotionAbsoption(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() - 4 * (n + 1));
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, n);
    }
    
    @Override
    public void applyAttributesModifiersToEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() + 4 * (n + 1));
        super.applyAttributesModifiersToEntity(entityLivingBase, baseAttributeMap, n);
    }
    
    static {
        __OBFID = "CL_00001524";
    }
}
