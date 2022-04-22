package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderWither extends RenderLiving
{
    private static final ResourceLocation invulnerableWitherTextures;
    private static final ResourceLocation witherTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001034";
        invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    }
    
    public RenderWither(final RenderManager renderManager) {
        super(renderManager, new ModelWither(0.0f), 1.0f);
        this.addLayer(new LayerWitherAura(this));
    }
    
    public void func_180591_a(final EntityWither entityWither, final double n, final double n2, final double n3, final float n4, final float n5) {
        BossStatus.setBossStatus(entityWither, true);
        super.doRender(entityWither, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityWither entityWither) {
        final int invulTime = entityWither.getInvulTime();
        return (invulTime > 0 && (invulTime > 80 || invulTime / 5 % 2 != 1)) ? RenderWither.invulnerableWitherTextures : RenderWither.witherTextures;
    }
    
    protected void func_180592_a(final EntityWither entityWither, final float n) {
        float n2 = 2.0f;
        final int invulTime = entityWither.getInvulTime();
        if (invulTime > 0) {
            n2 -= (invulTime - n) / 220.0f * 0.5f;
        }
        GlStateManager.scale(n2, n2, n2);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180591_a((EntityWither)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_180592_a((EntityWither)entityLivingBase, n);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180591_a((EntityWither)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWither)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180591_a((EntityWither)entity, n, n2, n3, n4, n5);
    }
}
