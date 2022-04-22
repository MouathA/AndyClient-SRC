package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderWitch extends RenderLiving
{
    private static final ResourceLocation witchTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001033";
        witchTextures = new ResourceLocation("textures/entity/witch.png");
    }
    
    public RenderWitch(final RenderManager renderManager) {
        super(renderManager, new ModelWitch(0.0f), 0.5f);
        this.addLayer(new LayerHeldItemWitch(this));
    }
    
    public void func_180590_a(final EntityWitch entityWitch, final double n, final double n2, final double n3, final float n4, final float n5) {
        ((ModelWitch)this.mainModel).field_82900_g = (entityWitch.getHeldItem() != null);
        super.doRender(entityWitch, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180589_a(final EntityWitch entityWitch) {
        return RenderWitch.witchTextures;
    }
    
    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    protected void preRenderCallback(final EntityWitch entityWitch, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180590_a((EntityWitch)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityWitch)entityLivingBase, n);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180590_a((EntityWitch)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180589_a((EntityWitch)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180590_a((EntityWitch)entity, n, n2, n3, n4, n5);
    }
}
