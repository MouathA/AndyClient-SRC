package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;

public class Gui
{
    public static final ResourceLocation optionsBackground;
    public static final ResourceLocation statIcons;
    public static final ResourceLocation icons;
    protected float zLevel;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000662";
        optionsBackground = new ResourceLocation("textures/gui/options_background.png");
        statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
        icons = new ResourceLocation("textures/gui/icons.png");
    }
    
    protected void drawHorizontalLine(int n, int n2, final int n3, final int n4) {
        if (n2 < n) {
            final int n5 = n;
            n = n2;
            n2 = n5;
        }
        drawRect(n, n3, n2 + 1, n3 + 1, n4);
    }
    
    protected void drawVerticalLine(final int n, int n2, int n3, final int n4) {
        if (n3 < n2) {
            final int n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        drawRect(n, n2 + 1, n + 1, n3, n4);
    }
    
    public static void drawRect(int n, int n2, int n3, int n4, final int n5) {
        if (n < n3) {
            final int n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final int n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n9, n10, n11, n8);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(n, n4, 0.0);
        worldRenderer.addVertex(n3, n4, 0.0);
        worldRenderer.addVertex(n3, n2, 0.0);
        worldRenderer.addVertex(n, n2, 0.0);
        instance.draw();
    }
    
    protected void drawGradientRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178960_a(n8, n9, n10, n7);
        worldRenderer.addVertex(n3, n2, this.zLevel);
        worldRenderer.addVertex(n, n2, this.zLevel);
        worldRenderer.func_178960_a(n12, n13, n14, n11);
        worldRenderer.addVertex(n, n4, this.zLevel);
        worldRenderer.addVertex(n3, n4, this.zLevel);
        instance.draw();
        GlStateManager.shadeModel(7424);
    }
    
    public static void drawCenteredString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.func_175063_a(s, (float)(n - fontRenderer.getStringWidth(s) / 2), (float)n2, n3);
    }
    
    public void drawString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.func_175063_a(s, (float)n, (float)n2, n3);
    }
    
    public void drawTexturedModalRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n + 0, n2 + n6, this.zLevel, (n3 + 0) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + n6, this.zLevel, (n3 + n5) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + 0, this.zLevel, (n3 + n5) * n7, (n4 + 0) * n8);
        worldRenderer.addVertexWithUV(n + 0, n2 + 0, this.zLevel, (n3 + 0) * n7, (n4 + 0) * n8);
        instance.draw();
    }
    
    public void func_175174_a(final float n, final float n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n + 0.0f, n2 + n6, this.zLevel, (n3 + 0) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + n6, this.zLevel, (n3 + n5) * n7, (n4 + n6) * n8);
        worldRenderer.addVertexWithUV(n + n5, n2 + 0.0f, this.zLevel, (n3 + n5) * n7, (n4 + 0) * n8);
        worldRenderer.addVertexWithUV(n + 0.0f, n2 + 0.0f, this.zLevel, (n3 + 0) * n7, (n4 + 0) * n8);
        instance.draw();
    }
    
    public void func_175175_a(final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n + 0, n2 + n4, this.zLevel, textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxV());
        worldRenderer.addVertexWithUV(n + n3, n2 + n4, this.zLevel, textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV());
        worldRenderer.addVertexWithUV(n + n3, n2 + 0, this.zLevel, textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV());
        worldRenderer.addVertexWithUV(n + 0, n2 + 0, this.zLevel, textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV());
        instance.draw();
    }
    
    public static void drawModalRectWithCustomSizedTexture(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        final float n9 = 1.0f / n7;
        final float n10 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n, n2 + n6, 0.0, n3 * n9, (n4 + n6) * n10);
        worldRenderer.addVertexWithUV(n + n5, n2 + n6, 0.0, (n3 + n5) * n9, (n4 + n6) * n10);
        worldRenderer.addVertexWithUV(n + n5, n2, 0.0, (n3 + n5) * n9, n4 * n10);
        worldRenderer.addVertexWithUV(n, n2, 0.0, n3 * n9, n4 * n10);
        instance.draw();
    }
    
    public static void drawScaledCustomSizeModalRect(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        final float n11 = 1.0f / n9;
        final float n12 = 1.0f / n10;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n, n2 + n8, 0.0, n3 * n11, (n4 + n6) * n12);
        worldRenderer.addVertexWithUV(n + n7, n2 + n8, 0.0, (n3 + n5) * n11, (n4 + n6) * n12);
        worldRenderer.addVertexWithUV(n + n7, n2, 0.0, (n3 + n5) * n11, n4 * n12);
        worldRenderer.addVertexWithUV(n, n2, 0.0, n3 * n11, n4 * n12);
        instance.draw();
    }
    
    public static void drawScaledCustomSizeModalRect(final double n, final double n2, final float n3, final float n4, final double n5, final double n6, final double n7, final double n8, final float n9, final float n10) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n, n2 + n8, 0.0, n3 * n9, (n4 + (float)n6) * n10);
        worldRenderer.addVertexWithUV(n + n7, n2 + n8, 0.0, (n3 + (float)n5) * n9, (n4 + (float)n6) * n10);
        worldRenderer.addVertexWithUV(n + n7, n2, 0.0, (n3 + (float)n5) * n9, n4 * n10);
        worldRenderer.addVertexWithUV(n, n2, 0.0, n3 * n9, n4 * n10);
        instance.draw();
    }
    
    public static void drawRect(double n, double n2, double n3, double n4, final int n5) {
        if (n < n3) {
            final double n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n9, n10, n11, n8);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(n, n4, 0.0);
        worldRenderer.addVertex(n3, n4, 0.0);
        worldRenderer.addVertex(n3, n2, 0.0);
        worldRenderer.addVertex(n, n2, 0.0);
        instance.draw();
    }
    
    public static void drawModalRectWithCustomSizedTexture(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = 1.0f / n7;
        final float n10 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(n, n2 + n6, 0.0, n3 * n9, (n4 + n6) * n10);
        worldRenderer.addVertexWithUV(n + n5, n2 + n6, 0.0, (n3 + n5) * n9, (n4 + n6) * n10);
        worldRenderer.addVertexWithUV(n + n5, n2, 0.0, (n3 + n5) * n9, n4 * n10);
        worldRenderer.addVertexWithUV(n, n2, 0.0, n3 * n9, n4 * n10);
        instance.draw();
    }
    
    public static void drawTexturedModalRect(final float n, final float n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = n3 * 0.00390625f;
        final float n8 = (n3 + n5) * 0.00390625f;
        final float n9 = n4 * 0.00390625f;
        final float n10 = (n4 + n6) * 0.00390625f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(n7, n10);
        GL11.glVertex2f(n, n2 + n6);
        GL11.glTexCoord2f(n8, n10);
        GL11.glVertex2f(n + n5, n2 + n6);
        GL11.glTexCoord2f(n8, n9);
        GL11.glVertex2f(n + n5, n2);
        GL11.glTexCoord2f(n7, n9);
        GL11.glVertex2f(n, n2);
    }
}
