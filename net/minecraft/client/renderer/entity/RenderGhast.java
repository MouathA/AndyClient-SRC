package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderGhast extends RenderLiving
{
    private static final ResourceLocation ghastTextures;
    private static final ResourceLocation ghastShootingTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000997";
        ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
        ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
    }
    
    public RenderGhast(final RenderManager renderManager) {
        super(renderManager, new ModelGhast(), 0.5f);
    }
    
    protected ResourceLocation func_180576_a(final EntityGhast entityGhast) {
        return entityGhast.func_110182_bF() ? RenderGhast.ghastShootingTextures : RenderGhast.ghastTextures;
    }
    
    protected void preRenderCallback(final EntityGhast entityGhast, final float n) {
        final float n2 = 1.0f;
        final float n3 = (8.0f + n2) / 2.0f;
        final float n4 = (8.0f + 1.0f / n2) / 2.0f;
        GlStateManager.scale(n4, n3, n4);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGhast)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180576_a((EntityGhast)entity);
    }
}
