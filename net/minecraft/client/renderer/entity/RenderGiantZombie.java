package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderGiantZombie extends RenderLiving
{
    private static final ResourceLocation zombieTextures;
    private float scale;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000998";
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    }
    
    public RenderGiantZombie(final RenderManager renderManager, final ModelBase modelBase, final float n, final float scale) {
        super(renderManager, modelBase, n * scale);
        this.scale = scale;
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor((RendererLivingEntity)this) {
            private static final String __OBFID;
            final RenderGiantZombie this$0;
            
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
            
            static {
                __OBFID = "CL_00002444";
            }
        });
    }
    
    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    protected void preRenderCallback(final EntityGiantZombie entityGiantZombie, final float n) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
    
    protected ResourceLocation getEntityTexture(final EntityGiantZombie entityGiantZombie) {
        return RenderGiantZombie.zombieTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGiantZombie)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGiantZombie)entity);
    }
}
