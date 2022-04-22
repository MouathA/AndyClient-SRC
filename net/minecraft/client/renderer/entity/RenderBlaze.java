package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderBlaze extends RenderLiving
{
    private static final ResourceLocation blazeTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000980";
        blazeTextures = new ResourceLocation("textures/entity/blaze.png");
    }
    
    public RenderBlaze(final RenderManager renderManager) {
        super(renderManager, new ModelBlaze(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlaze entityBlaze) {
        return RenderBlaze.blazeTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityBlaze)entity);
    }
}
