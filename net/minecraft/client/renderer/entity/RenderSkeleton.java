package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSkeleton extends RenderBiped
{
    private static final ResourceLocation skeletonTextures;
    private static final ResourceLocation witherSkeletonTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001023";
        skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }
    
    public RenderSkeleton(final RenderManager renderManager) {
        super(renderManager, new ModelSkeleton(), 0.5f);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor((RendererLivingEntity)this) {
            private static final String __OBFID;
            final RenderSkeleton this$0;
            
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelSkeleton(0.5f, true);
                this.field_177186_d = new ModelSkeleton(1.0f, true);
            }
            
            static {
                __OBFID = "CL_00002431";
            }
        });
    }
    
    protected void preRenderCallback(final EntitySkeleton entitySkeleton, final float n) {
        if (entitySkeleton.getSkeletonType() == 1) {
            GlStateManager.scale(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    protected ResourceLocation func_180577_a(final EntitySkeleton entitySkeleton) {
        return (entitySkeleton.getSkeletonType() == 1) ? RenderSkeleton.witherSkeletonTextures : RenderSkeleton.skeletonTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.func_180577_a((EntitySkeleton)entityLiving);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntitySkeleton)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180577_a((EntitySkeleton)entity);
    }
}
