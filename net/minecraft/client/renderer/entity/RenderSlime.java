package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSlime extends RenderLiving
{
    private static final ResourceLocation slimeTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001024";
        slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
    }
    
    public RenderSlime(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        this.addLayer(new LayerSlimeGel(this));
    }
    
    public void doRender(final EntitySlime entitySlime, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.shadowSize = 0.25f * entitySlime.getSlimeSize();
        super.doRender(entitySlime, n, n2, n3, n4, n5);
    }
    
    protected void preRenderCallback(final EntitySlime entitySlime, final float n) {
        final float n2 = (float)entitySlime.getSlimeSize();
        final float n3 = 1.0f / ((entitySlime.prevSquishFactor + (entitySlime.squishFactor - entitySlime.prevSquishFactor) * n) / (n2 * 0.5f + 1.0f) + 1.0f);
        GlStateManager.scale(n3 * n2, 1.0f / n3 * n2, n3 * n2);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySlime entitySlime) {
        return RenderSlime.slimeTextures;
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntitySlime)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntitySlime)entityLivingBase, n);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntitySlime)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySlime)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntitySlime)entity, n, n2, n3, n4, n5);
    }
}
