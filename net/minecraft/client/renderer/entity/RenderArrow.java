package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class RenderArrow extends Render
{
    private static final ResourceLocation arrowTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000978";
        arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }
    
    public RenderArrow(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(final EntityArrow entityArrow, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.bindEntityTexture(entityArrow);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.rotate(entityArrow.prevRotationYaw + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * n5 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entityArrow.prevRotationPitch + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * n5, 0.0f, 0.0f, 1.0f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float n6 = 0 / 32.0f;
        final float n7 = 5 / 32.0f;
        final float n8 = 0.0f;
        final float n9 = 0.15625f;
        final float n10 = 5 / 32.0f;
        final float n11 = 10 / 32.0f;
        final float n12 = 0.05625f;
        final float n13 = entityArrow.arrowShake - n5;
        if (n13 > 0.0f) {
            GlStateManager.rotate(-MathHelper.sin(n13 * 3.0f) * n13, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(n12, n12, n12);
        GlStateManager.translate(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(n12, 0.0f, 0.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, -2.0, -2.0, n8, n10);
        worldRenderer.addVertexWithUV(-7.0, -2.0, 2.0, n9, n10);
        worldRenderer.addVertexWithUV(-7.0, 2.0, 2.0, n9, n11);
        worldRenderer.addVertexWithUV(-7.0, 2.0, -2.0, n8, n11);
        instance.draw();
        GL11.glNormal3f(-n12, 0.0f, 0.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, 2.0, -2.0, n8, n10);
        worldRenderer.addVertexWithUV(-7.0, 2.0, 2.0, n9, n10);
        worldRenderer.addVertexWithUV(-7.0, -2.0, 2.0, n9, n11);
        worldRenderer.addVertexWithUV(-7.0, -2.0, -2.0, n8, n11);
        instance.draw();
        super.doRender(entityArrow, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityArrow entityArrow) {
        return RenderArrow.arrowTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityArrow)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityArrow)entity, n, n2, n3, n4, n5);
    }
}
