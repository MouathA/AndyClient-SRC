package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSquid extends RenderLiving
{
    private static final ResourceLocation squidTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001028";
        squidTextures = new ResourceLocation("textures/entity/squid.png");
    }
    
    public RenderSquid(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySquid entitySquid) {
        return RenderSquid.squidTextures;
    }
    
    protected void rotateCorpse(final EntitySquid entitySquid, final float n, final float n2, final float n3) {
        final float n4 = entitySquid.prevSquidPitch + (entitySquid.squidPitch - entitySquid.prevSquidPitch) * n3;
        final float n5 = entitySquid.prevSquidYaw + (entitySquid.squidYaw - entitySquid.prevSquidYaw) * n3;
        GlStateManager.translate(0.0f, 0.5f, 0.0f);
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n4, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, -1.2f, 0.0f);
    }
    
    protected float handleRotationFloat(final EntitySquid entitySquid, final float n) {
        return entitySquid.lastTentacleAngle + (entitySquid.tentacleAngle - entitySquid.lastTentacleAngle) * n;
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntitySquid)entityLivingBase, n);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntitySquid)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySquid)entity);
    }
}
