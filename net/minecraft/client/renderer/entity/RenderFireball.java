package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFireball extends Render
{
    private float scale;
    private static final String __OBFID;
    
    public RenderFireball(final RenderManager renderManager, final float scale) {
        super(renderManager);
        this.scale = scale;
    }
    
    public void doRender(final EntityFireball entityFireball, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.bindEntityTexture(entityFireball);
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float scale = this.scale;
        GlStateManager.scale(scale / 1.0f, scale / 1.0f, scale / 1.0f);
        final TextureAtlasSprite particleIcon = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float minU = particleIcon.getMinU();
        final float maxU = particleIcon.getMaxU();
        final float minV = particleIcon.getMinV();
        final float maxV = particleIcon.getMaxV();
        final float n6 = 1.0f;
        final float n7 = 0.5f;
        final float n8 = 0.25f;
        GlStateManager.rotate(180.0f - RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
        worldRenderer.addVertexWithUV(0.0f - n7, 0.0f - n8, 0.0, minU, maxV);
        worldRenderer.addVertexWithUV(n6 - n7, 0.0f - n8, 0.0, maxU, maxV);
        worldRenderer.addVertexWithUV(n6 - n7, 1.0f - n8, 0.0, maxU, minV);
        worldRenderer.addVertexWithUV(0.0f - n7, 1.0f - n8, 0.0, minU, minV);
        instance.draw();
        super.doRender(entityFireball, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180556_a(final EntityFireball entityFireball) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180556_a((EntityFireball)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFireball)entity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00000995";
    }
}
