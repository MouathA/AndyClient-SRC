package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import optifine.*;
import net.minecraft.client.renderer.*;

public class RenderXPOrb extends Render
{
    private static final ResourceLocation experienceOrbTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000993";
        experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
    }
    
    public RenderXPOrb(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    public void doRender(final EntityXPOrb entityXPOrb, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindEntityTexture(entityXPOrb);
        final int textureByXP = entityXPOrb.getTextureByXP();
        final float n6 = (textureByXP % 4 * 16 + 0) / 64.0f;
        final float n7 = (textureByXP % 4 * 16 + 16) / 64.0f;
        final float n8 = (textureByXP / 4 * 16 + 0) / 64.0f;
        final float n9 = (textureByXP / 4 * 16 + 16) / 64.0f;
        final float n10 = 1.0f;
        final float n11 = 0.5f;
        final float n12 = 0.25f;
        final int brightnessForRender = entityXPOrb.getBrightnessForRender(n5);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n13 = 255.0f;
        final float n14 = (entityXPOrb.xpColor + n5) / 2.0f;
        int n15 = (int)((MathHelper.sin(n14 + 0.0f) + 1.0f) * 0.5f * n13) << 16 | (int)n13 << 8 | (int)((MathHelper.sin(n14 + 4.1887903f) + 1.0f) * 0.1f * n13);
        GlStateManager.rotate(180.0f - RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        final float n16 = 0.3f;
        GlStateManager.scale(n16, n16, n16);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        if (Config.isCustomColors()) {
            final int xpOrbColor = CustomColors.getXpOrbColor(n13);
            if (xpOrbColor >= 0) {
                n15 = xpOrbColor;
            }
        }
        worldRenderer.func_178974_a(n15, 128);
        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
        worldRenderer.addVertexWithUV(0.0f - n11, 0.0f - n12, 0.0, n6, n9);
        worldRenderer.addVertexWithUV(n10 - n11, 0.0f - n12, 0.0, n7, n9);
        worldRenderer.addVertexWithUV(n10 - n11, 1.0f - n12, 0.0, n7, n8);
        worldRenderer.addVertexWithUV(0.0f - n11, 1.0f - n12, 0.0, n6, n8);
        instance.draw();
        super.doRender(entityXPOrb, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getTexture(final EntityXPOrb entityXPOrb) {
        return RenderXPOrb.experienceOrbTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getTexture((EntityXPOrb)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityXPOrb)entity, n, n2, n3, n4, n5);
    }
}
