package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderBat extends RenderLiving
{
    private static final ResourceLocation batTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000979";
        batTextures = new ResourceLocation("textures/entity/bat.png");
    }
    
    public RenderBat(final RenderManager renderManager) {
        super(renderManager, new ModelBat(), 0.25f);
    }
    
    protected ResourceLocation func_180566_a(final EntityBat entityBat) {
        return RenderBat.batTextures;
    }
    
    protected void func_180567_a(final EntityBat entityBat, final float n) {
        GlStateManager.scale(0.35f, 0.35f, 0.35f);
    }
    
    protected void rotateCorpse(final EntityBat entityBat, final float n, final float n2, final float n3) {
        if (!entityBat.getIsBatHanging()) {
            GlStateManager.translate(0.0f, MathHelper.cos(n * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GlStateManager.translate(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(entityBat, n, n2, n3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_180567_a((EntityBat)entityLivingBase, n);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityBat)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180566_a((EntityBat)entity);
    }
}
