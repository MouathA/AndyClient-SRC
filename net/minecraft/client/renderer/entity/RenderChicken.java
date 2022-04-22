package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderChicken extends RenderLiving
{
    private static final ResourceLocation chickenTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000983";
        chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    }
    
    public RenderChicken(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    protected ResourceLocation func_180568_a(final EntityChicken entityChicken) {
        return RenderChicken.chickenTextures;
    }
    
    protected float func_180569_a(final EntityChicken entityChicken, final float n) {
        return (MathHelper.sin(entityChicken.field_70888_h + (entityChicken.field_70886_e - entityChicken.field_70888_h) * n) + 1.0f) * (entityChicken.field_70884_g + (entityChicken.destPos - entityChicken.field_70884_g) * n);
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.func_180569_a((EntityChicken)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180568_a((EntityChicken)entity);
    }
}
