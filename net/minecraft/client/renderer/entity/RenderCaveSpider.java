package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderCaveSpider extends RenderSpider
{
    private static final ResourceLocation caveSpiderTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000982";
        caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
    }
    
    public RenderCaveSpider(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize *= 0.7f;
    }
    
    protected void func_180585_a(final EntityCaveSpider entityCaveSpider, final float n) {
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
    }
    
    protected ResourceLocation func_180586_a(final EntityCaveSpider entityCaveSpider) {
        return RenderCaveSpider.caveSpiderTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySpider entitySpider) {
        return this.func_180586_a((EntityCaveSpider)entitySpider);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_180585_a((EntityCaveSpider)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180586_a((EntityCaveSpider)entity);
    }
}
