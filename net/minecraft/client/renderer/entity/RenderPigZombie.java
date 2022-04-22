package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderPigZombie extends RenderBiped
{
    private static final ResourceLocation field_177120_j;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002434";
        field_177120_j = new ResourceLocation("textures/entity/zombie_pigman.png");
    }
    
    public RenderPigZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor((RendererLivingEntity)this) {
            private static final String __OBFID;
            final RenderPigZombie this$0;
            
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
            
            static {
                __OBFID = "CL_00002433";
            }
        });
    }
    
    protected ResourceLocation func_177119_a(final EntityPigZombie entityPigZombie) {
        return RenderPigZombie.field_177120_j;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.func_177119_a((EntityPigZombie)entityLiving);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_177119_a((EntityPigZombie)entity);
    }
}
