package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderSnowMan extends RenderLiving
{
    private static final ResourceLocation snowManTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001025";
        snowManTextures = new ResourceLocation("textures/entity/snowman.png");
    }
    
    public RenderSnowMan(final RenderManager renderManager) {
        super(renderManager, new ModelSnowMan(), 0.5f);
        this.addLayer(new LayerSnowmanHead(this));
    }
    
    protected ResourceLocation func_180587_a(final EntitySnowman entitySnowman) {
        return RenderSnowMan.snowManTextures;
    }
    
    public ModelSnowMan func_177123_g() {
        return (ModelSnowMan)super.getMainModel();
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.func_177123_g();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180587_a((EntitySnowman)entity);
    }
}
