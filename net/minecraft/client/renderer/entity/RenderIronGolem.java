package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderIronGolem extends RenderLiving
{
    private static final ResourceLocation ironGolemTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001031";
        ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
    }
    
    public RenderIronGolem(final RenderManager renderManager) {
        super(renderManager, new ModelIronGolem(), 0.5f);
        this.addLayer(new LayerIronGolemFlower(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityIronGolem entityIronGolem) {
        return RenderIronGolem.ironGolemTextures;
    }
    
    protected void func_180588_a(final EntityIronGolem entityIronGolem, final float n, final float n2, final float n3) {
        super.rotateCorpse(entityIronGolem, n, n2, n3);
        if (entityIronGolem.limbSwingAmount >= 0.01) {
            final float n4 = 13.0f;
            GlStateManager.rotate(6.5f * ((Math.abs((entityIronGolem.limbSwing - entityIronGolem.limbSwingAmount * (1.0f - n3) + 6.0f) % n4 - n4 * 0.5f) - n4 * 0.25f) / (n4 * 0.25f)), 0.0f, 0.0f, 1.0f);
        }
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.func_180588_a((EntityIronGolem)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityIronGolem)entity);
    }
}
