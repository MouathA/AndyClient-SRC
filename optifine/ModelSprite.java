package optifine;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ModelSprite
{
    private ModelRenderer modelRenderer;
    private int textureOffsetX;
    private int textureOffsetY;
    private float posX;
    private float posY;
    private float posZ;
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    private float sizeAdd;
    private float minU;
    private float minV;
    private float maxU;
    private float maxV;
    
    public ModelSprite(final ModelRenderer modelRenderer, final int textureOffsetX, final int textureOffsetY, final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ, final float sizeAdd) {
        this.modelRenderer = null;
        this.textureOffsetX = 0;
        this.textureOffsetY = 0;
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.posZ = 0.0f;
        this.sizeX = 0;
        this.sizeY = 0;
        this.sizeZ = 0;
        this.sizeAdd = 0.0f;
        this.minU = 0.0f;
        this.minV = 0.0f;
        this.maxU = 0.0f;
        this.maxV = 0.0f;
        this.modelRenderer = modelRenderer;
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.sizeAdd = sizeAdd;
        this.minU = textureOffsetX / modelRenderer.textureWidth;
        this.minV = textureOffsetY / modelRenderer.textureHeight;
        this.maxU = (textureOffsetX + sizeX) / modelRenderer.textureWidth;
        this.maxV = (textureOffsetY + sizeY) / modelRenderer.textureHeight;
    }
    
    public void render(final Tessellator tessellator, final float n) {
        GlStateManager.translate(this.posX * n, this.posY * n, this.posZ * n);
        float n2 = this.minU;
        float n3 = this.maxU;
        float n4 = this.minV;
        float n5 = this.maxV;
        if (this.modelRenderer.mirror) {
            n2 = this.maxU;
            n3 = this.minU;
        }
        if (this.modelRenderer.mirrorV) {
            n4 = this.maxV;
            n5 = this.minV;
        }
        renderItemIn2D(tessellator, n2, n4, n3, n5, this.sizeX, this.sizeY, n * this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
        GlStateManager.translate(-this.posX * n, -this.posY * n, -this.posZ * n);
    }
    
    public static void renderItemIn2D(final Tessellator tessellator, final float n, final float n2, final float n3, final float n4, final int n5, final int n6, float n7, final float n8, final float n9) {
        if (n7 < 6.25E-4f) {
            n7 = 6.25E-4f;
        }
        final float n10 = n3 - n;
        final float n11 = n4 - n2;
        final double n12 = MathHelper.abs(n10) * (n8 / 16.0f);
        final double n13 = MathHelper.abs(n11) * (n9 / 16.0f);
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, 0.0f, -1.0f);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, n, n2);
        worldRenderer.addVertexWithUV(n12, 0.0, 0.0, n3, n2);
        worldRenderer.addVertexWithUV(n12, n13, 0.0, n3, n4);
        worldRenderer.addVertexWithUV(0.0, n13, 0.0, n, n4);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, 0.0f, 1.0f);
        worldRenderer.addVertexWithUV(0.0, n13, n7, n, n4);
        worldRenderer.addVertexWithUV(n12, n13, n7, n3, n4);
        worldRenderer.addVertexWithUV(n12, 0.0, n7, n3, n2);
        worldRenderer.addVertexWithUV(0.0, 0.0, n7, n, n2);
        tessellator.draw();
        final float n14 = 0.5f * n10 / n5;
        final float n15 = 0.5f * n11 / n6;
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(-1.0f, 0.0f, 0.0f);
        int n18 = 0;
        while (0 < n5) {
            final float n16 = 0 / (float)n5;
            final float n17 = n + n10 * n16 + n14;
            worldRenderer.addVertexWithUV(n16 * n12, 0.0, n7, n17, n2);
            worldRenderer.addVertexWithUV(n16 * n12, 0.0, 0.0, n17, n2);
            worldRenderer.addVertexWithUV(n16 * n12, n13, 0.0, n17, n4);
            worldRenderer.addVertexWithUV(n16 * n12, n13, n7, n17, n4);
            ++n18;
        }
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(1.0f, 0.0f, 0.0f);
        while (0 < n5) {
            final float n19 = 0 / (float)n5;
            final float n20 = n + n10 * n19 + n14;
            final float n21 = n19 + 1.0f / n5;
            worldRenderer.addVertexWithUV(n21 * n12, n13, n7, n20, n4);
            worldRenderer.addVertexWithUV(n21 * n12, n13, 0.0, n20, n4);
            worldRenderer.addVertexWithUV(n21 * n12, 0.0, 0.0, n20, n2);
            worldRenderer.addVertexWithUV(n21 * n12, 0.0, n7, n20, n2);
            ++n18;
        }
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
        while (0 < n6) {
            final float n22 = 0 / (float)n6;
            final float n23 = n2 + n11 * n22 + n15;
            final float n24 = n22 + 1.0f / n6;
            worldRenderer.addVertexWithUV(0.0, n24 * n13, 0.0, n, n23);
            worldRenderer.addVertexWithUV(n12, n24 * n13, 0.0, n3, n23);
            worldRenderer.addVertexWithUV(n12, n24 * n13, n7, n3, n23);
            worldRenderer.addVertexWithUV(0.0, n24 * n13, n7, n, n23);
            ++n18;
        }
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178980_d(0.0f, -1.0f, 0.0f);
        while (0 < n6) {
            final float n25 = 0 / (float)n6;
            final float n26 = n2 + n11 * n25 + n15;
            worldRenderer.addVertexWithUV(n12, n25 * n13, 0.0, n3, n26);
            worldRenderer.addVertexWithUV(0.0, n25 * n13, 0.0, n, n26);
            worldRenderer.addVertexWithUV(0.0, n25 * n13, n7, n, n26);
            worldRenderer.addVertexWithUV(n12, n25 * n13, n7, n3, n26);
            ++n18;
        }
        tessellator.draw();
    }
}
