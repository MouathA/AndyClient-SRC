package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class RenderPainting extends Render
{
    private static final ResourceLocation field_110807_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001018";
        field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
    }
    
    public RenderPainting(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(final EntityPainting entityPainting, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate(n, n2, n3);
        GlStateManager.rotate(180.0f - n4, 0.0f, 1.0f, 0.0f);
        this.bindEntityTexture(entityPainting);
        final EntityPainting.EnumArt art = entityPainting.art;
        final float n6 = 0.0625f;
        GlStateManager.scale(n6, n6, n6);
        this.func_77010_a(entityPainting, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
        super.doRender(entityPainting, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180562_a(final EntityPainting entityPainting) {
        return RenderPainting.field_110807_a;
    }
    
    private void func_77010_a(final EntityPainting entityPainting, final int n, final int n2, final int n3, final int n4) {
        final float n5 = -n / 2.0f;
        final float n6 = -n2 / 2.0f;
        final float n7 = 0.5f;
        final float n8 = 0.75f;
        final float n9 = 0.8125f;
        final float n10 = 0.0f;
        final float n11 = 0.0625f;
        final float n12 = 0.75f;
        final float n13 = 0.8125f;
        final float n14 = 0.001953125f;
        final float n15 = 0.001953125f;
        final float n16 = 0.7519531f;
        final float n17 = 0.7519531f;
        final float n18 = 0.0f;
        final float n19 = 0.0625f;
        while (0 < n / 16) {
            while (0 < n2 / 16) {
                final float n20 = n5 + 16;
                final float n21 = n5 + 0;
                final float n22 = n6 + 16;
                final float n23 = n6 + 0;
                this.func_77008_a(entityPainting, (n20 + n21) / 2.0f, (n22 + n23) / 2.0f);
                final float n24 = (n3 + n - 0) / 256.0f;
                final float n25 = (n3 + n - 16) / 256.0f;
                final float n26 = (n4 + n2 - 0) / 256.0f;
                final float n27 = (n4 + n2 - 16) / 256.0f;
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178980_d(0.0f, 0.0f, -1.0f);
                worldRenderer.addVertexWithUV(n20, n23, -n7, n25, n26);
                worldRenderer.addVertexWithUV(n21, n23, -n7, n24, n26);
                worldRenderer.addVertexWithUV(n21, n22, -n7, n24, n27);
                worldRenderer.addVertexWithUV(n20, n22, -n7, n25, n27);
                worldRenderer.func_178980_d(0.0f, 0.0f, 1.0f);
                worldRenderer.addVertexWithUV(n20, n22, n7, n8, n10);
                worldRenderer.addVertexWithUV(n21, n22, n7, n9, n10);
                worldRenderer.addVertexWithUV(n21, n23, n7, n9, n11);
                worldRenderer.addVertexWithUV(n20, n23, n7, n8, n11);
                worldRenderer.func_178980_d(0.0f, 1.0f, 0.0f);
                worldRenderer.addVertexWithUV(n20, n22, -n7, n12, n14);
                worldRenderer.addVertexWithUV(n21, n22, -n7, n13, n14);
                worldRenderer.addVertexWithUV(n21, n22, n7, n13, n15);
                worldRenderer.addVertexWithUV(n20, n22, n7, n12, n15);
                worldRenderer.func_178980_d(0.0f, -1.0f, 0.0f);
                worldRenderer.addVertexWithUV(n20, n23, n7, n12, n14);
                worldRenderer.addVertexWithUV(n21, n23, n7, n13, n14);
                worldRenderer.addVertexWithUV(n21, n23, -n7, n13, n15);
                worldRenderer.addVertexWithUV(n20, n23, -n7, n12, n15);
                worldRenderer.func_178980_d(-1.0f, 0.0f, 0.0f);
                worldRenderer.addVertexWithUV(n20, n22, n7, n17, n18);
                worldRenderer.addVertexWithUV(n20, n23, n7, n17, n19);
                worldRenderer.addVertexWithUV(n20, n23, -n7, n16, n19);
                worldRenderer.addVertexWithUV(n20, n22, -n7, n16, n18);
                worldRenderer.func_178980_d(1.0f, 0.0f, 0.0f);
                worldRenderer.addVertexWithUV(n21, n22, -n7, n17, n18);
                worldRenderer.addVertexWithUV(n21, n23, -n7, n17, n19);
                worldRenderer.addVertexWithUV(n21, n23, n7, n16, n19);
                worldRenderer.addVertexWithUV(n21, n22, n7, n16, n18);
                instance.draw();
                int n28 = 0;
                ++n28;
            }
            int n29 = 0;
            ++n29;
        }
    }
    
    private void func_77008_a(final EntityPainting entityPainting, final float n, final float n2) {
        int n3 = MathHelper.floor_double(entityPainting.posX);
        final int floor_double = MathHelper.floor_double(entityPainting.posY + n2 / 16.0f);
        int n4 = MathHelper.floor_double(entityPainting.posZ);
        final EnumFacing field_174860_b = entityPainting.field_174860_b;
        if (field_174860_b == EnumFacing.NORTH) {
            n3 = MathHelper.floor_double(entityPainting.posX + n / 16.0f);
        }
        if (field_174860_b == EnumFacing.WEST) {
            n4 = MathHelper.floor_double(entityPainting.posZ - n / 16.0f);
        }
        if (field_174860_b == EnumFacing.SOUTH) {
            n3 = MathHelper.floor_double(entityPainting.posX - n / 16.0f);
        }
        if (field_174860_b == EnumFacing.EAST) {
            n4 = MathHelper.floor_double(entityPainting.posZ + n / 16.0f);
        }
        final int combinedLight = this.renderManager.worldObj.getCombinedLight(new BlockPos(n3, floor_double, n4), 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(combinedLight % 65536), (float)(combinedLight / 65536));
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180562_a((EntityPainting)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityPainting)entity, n, n2, n3, n4, n5);
    }
}
