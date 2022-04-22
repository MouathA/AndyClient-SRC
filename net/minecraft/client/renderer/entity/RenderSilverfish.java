package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderSilverfish extends RenderLiving
{
    private static final ResourceLocation silverfishTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001022";
        silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
    }
    
    public RenderSilverfish(final RenderManager renderManager) {
        super(renderManager, new ModelSilverfish(), 0.3f);
    }
    
    protected float func_180584_a(final EntitySilverfish entitySilverfish) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntitySilverfish entitySilverfish) {
        return RenderSilverfish.silverfishTextures;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.func_180584_a((EntitySilverfish)entityLivingBase);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySilverfish)entity);
    }
}
