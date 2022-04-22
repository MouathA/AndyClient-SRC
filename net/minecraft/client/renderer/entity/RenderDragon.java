package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class RenderDragon extends RenderLiving
{
    private static final ResourceLocation enderDragonCrystalBeamTextures;
    private static final ResourceLocation enderDragonExplodingTextures;
    private static final ResourceLocation enderDragonTextures;
    protected ModelDragon modelDragon;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000988";
        enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
        enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
        enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    }
    
    public RenderDragon(final RenderManager renderManager) {
        super(renderManager, new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.addLayer(new LayerEnderDragonEyes(this));
        this.addLayer(new LayerEnderDragonDeath());
    }
    
    protected void func_180575_a(final EntityDragon entityDragon, final float n, final float n2, final float n3) {
        final float n4 = (float)entityDragon.getMovementOffsets(7, n3)[0];
        final float n5 = (float)(entityDragon.getMovementOffsets(5, n3)[1] - entityDragon.getMovementOffsets(10, n3)[1]);
        GlStateManager.rotate(-n4, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n5 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 1.0f);
        if (entityDragon.deathTime > 0) {
            float sqrt_float = MathHelper.sqrt_float((entityDragon.deathTime + n3 - 1.0f) / 20.0f * 1.6f);
            if (sqrt_float > 1.0f) {
                sqrt_float = 1.0f;
            }
            GlStateManager.rotate(sqrt_float * this.getDeathMaxRotation(entityDragon), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void renderModel(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (entityDragon.deathTicks > 0) {
            final float n7 = entityDragon.deathTicks / 200.0f;
            GlStateManager.depthFunc(515);
            GlStateManager.alphaFunc(516, n7);
            this.bindTexture(RenderDragon.enderDragonExplodingTextures);
            this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.depthFunc(514);
        }
        this.bindEntityTexture(entityDragon);
        this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
        if (entityDragon.hurtTime > 0) {
            GlStateManager.depthFunc(514);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 0.0f, 0.0f, 0.5f);
            this.mainModel.render(entityDragon, n, n2, n3, n4, n5, n6);
            GlStateManager.depthFunc(515);
        }
    }
    
    public void doRender(final EntityDragon entityDragon, final double n, final double n2, final double n3, final float n4, final float n5) {
        BossStatus.setBossStatus(entityDragon, false);
        super.doRender(entityDragon, n, n2, n3, n4, n5);
        if (entityDragon.healingEnderCrystal != null) {
            this.func_180574_a(entityDragon, n, n2, n3, n5);
        }
    }
    
    protected void func_180574_a(final EntityDragon entityDragon, final double n, final double n2, final double n3, final float n4) {
        final float n5 = MathHelper.sin((entityDragon.healingEnderCrystal.innerRotation + n4) * 0.2f) / 2.0f + 0.5f;
        final float n6 = (n5 * n5 + n5) * 0.2f;
        final float n7 = (float)(entityDragon.healingEnderCrystal.posX - entityDragon.posX - (entityDragon.prevPosX - entityDragon.posX) * (1.0f - n4));
        final float n8 = (float)(n6 + entityDragon.healingEnderCrystal.posY - 1.0 - entityDragon.posY - (entityDragon.prevPosY - entityDragon.posY) * (1.0f - n4));
        final float n9 = (float)(entityDragon.healingEnderCrystal.posZ - entityDragon.posZ - (entityDragon.prevPosZ - entityDragon.posZ) * (1.0f - n4));
        final float sqrt_float = MathHelper.sqrt_float(n7 * n7 + n9 * n9);
        final float sqrt_float2 = MathHelper.sqrt_float(n7 * n7 + n8 * n8 + n9 * n9);
        GlStateManager.translate((float)n, (float)n2 + 2.0f, (float)n3);
        GlStateManager.rotate((float)(-Math.atan2(n9, n7)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((float)(-Math.atan2(sqrt_float, n8)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.bindTexture(RenderDragon.enderDragonCrystalBeamTextures);
        GlStateManager.shadeModel(7425);
        final float n10 = 0.0f - (entityDragon.ticksExisted + n4) * 0.01f;
        final float n11 = MathHelper.sqrt_float(n7 * n7 + n8 * n8 + n9 * n9) / 32.0f - (entityDragon.ticksExisted + n4) * 0.01f;
        worldRenderer.startDrawing(5);
        while (0 <= 8) {
            final float n12 = MathHelper.sin(0 * 3.1415927f * 2.0f / 8) * 0.75f;
            final float n13 = MathHelper.cos(0 * 3.1415927f * 2.0f / 8) * 0.75f;
            final float n14 = 0 * 1.0f / 8;
            worldRenderer.func_178991_c(0);
            worldRenderer.addVertexWithUV(n12 * 0.2f, n13 * 0.2f, 0.0, n14, n11);
            worldRenderer.func_178991_c(16777215);
            worldRenderer.addVertexWithUV(n12, n13, sqrt_float2, n14, n10);
            int n15 = 0;
            ++n15;
        }
        instance.draw();
        GlStateManager.shadeModel(7424);
    }
    
    protected ResourceLocation getEntityTexture(final EntityDragon entityDragon) {
        return RenderDragon.enderDragonTextures;
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityDragon)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.func_180575_a((EntityDragon)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void renderModel(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.renderModel((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityDragon)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityDragon)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityDragon)entity, n, n2, n3, n4, n5);
    }
}
