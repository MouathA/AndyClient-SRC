package net.minecraft.client.renderer.entity;

import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class RenderPotion extends RenderSnowball
{
    private static final String __OBFID;
    
    public RenderPotion(final RenderManager renderManager, final RenderItem renderItem) {
        super(renderManager, Items.potionitem, renderItem);
    }
    
    public ItemStack func_177085_a(final EntityPotion entityPotion) {
        return new ItemStack(this.field_177084_a, 1, entityPotion.getPotionDamage());
    }
    
    @Override
    public ItemStack func_177082_d(final Entity entity) {
        return this.func_177085_a((EntityPotion)entity);
    }
    
    static {
        __OBFID = "CL_00002430";
    }
}
