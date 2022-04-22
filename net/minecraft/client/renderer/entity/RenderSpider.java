package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderSpider extends RenderLiving
{
    private static final ResourceLocation spiderTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001027";
        spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
    }
    
    public RenderSpider(final RenderManager renderManager) {
        super(renderManager, new ModelSpider(), 1.0f);
        this.addLayer(new LayerSpiderEyes(this));
    }
    
    protected float getDeathMaxRotation(final EntitySpider entitySpider) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntitySpider entitySpider) {
        return RenderSpider.spiderTextures;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntitySpider)entityLivingBase);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySpider)entity);
    }
}
