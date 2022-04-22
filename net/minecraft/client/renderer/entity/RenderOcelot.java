package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderOcelot extends RenderLiving
{
    private static final ResourceLocation blackOcelotTextures;
    private static final ResourceLocation ocelotTextures;
    private static final ResourceLocation redOcelotTextures;
    private static final ResourceLocation siameseOcelotTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001017";
        blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
        ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
        redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
        siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
    }
    
    public RenderOcelot(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    protected ResourceLocation getEntityTexture(final EntityOcelot entityOcelot) {
        switch (entityOcelot.getTameSkin()) {
            default: {
                return RenderOcelot.ocelotTextures;
            }
            case 1: {
                return RenderOcelot.blackOcelotTextures;
            }
            case 2: {
                return RenderOcelot.redOcelotTextures;
            }
            case 3: {
                return RenderOcelot.siameseOcelotTextures;
            }
        }
    }
    
    protected void preRenderCallback(final EntityOcelot entityOcelot, final float n) {
        super.preRenderCallback(entityOcelot, n);
        if (entityOcelot.isTamed()) {
            GlStateManager.scale(0.8f, 0.8f, 0.8f);
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityOcelot)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityOcelot)entity);
    }
}
