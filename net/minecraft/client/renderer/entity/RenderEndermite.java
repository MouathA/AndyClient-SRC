package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderEndermite extends RenderLiving
{
    private static final ResourceLocation field_177108_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002445";
        field_177108_a = new ResourceLocation("textures/entity/endermite.png");
    }
    
    public RenderEndermite(final RenderManager renderManager) {
        super(renderManager, new ModelEnderMite(), 0.3f);
    }
    
    protected float func_177107_a(final EntityEndermite entityEndermite) {
        return 180.0f;
    }
    
    protected ResourceLocation func_177106_b(final EntityEndermite entityEndermite) {
        return RenderEndermite.field_177108_a;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.func_177107_a((EntityEndermite)entityLivingBase);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_177106_b((EntityEndermite)entity);
    }
}
