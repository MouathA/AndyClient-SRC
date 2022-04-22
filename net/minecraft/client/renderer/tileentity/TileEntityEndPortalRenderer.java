package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import java.util.*;
import java.nio.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147529_c;
    private static final ResourceLocation field_147526_d;
    private static final Random field_147527_e;
    FloatBuffer field_147528_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002467";
        field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
        field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
        field_147527_e = new Random(31100L);
    }
    
    public TileEntityEndPortalRenderer() {
        this.field_147528_b = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public void func_180544_a(final TileEntityEndPortal tileEntityEndPortal, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float n6 = (float)this.rendererDispatcher.field_147560_j;
        final float n7 = (float)this.rendererDispatcher.field_147561_k;
        final float n8 = (float)this.rendererDispatcher.field_147558_l;
        TileEntityEndPortalRenderer.field_147527_e.setSeed(31100L);
        final float n9 = 0.75f;
        while (0 < 16) {
            float n10 = 16;
            float n11 = 0.0625f;
            float n12 = 1.0f / (n10 + 1.0f);
            if (!false) {
                this.bindTexture(TileEntityEndPortalRenderer.field_147529_c);
                n12 = 0.1f;
                n10 = 65.0f;
                n11 = 0.125f;
                GlStateManager.blendFunc(770, 771);
            }
            if (0 >= 1) {
                this.bindTexture(TileEntityEndPortalRenderer.field_147526_d);
            }
            if (false == true) {
                GlStateManager.blendFunc(1, 1);
                n11 = 0.5f;
            }
            final float n13 = (float)(-(n2 + n9));
            GlStateManager.translate(n6, (n13 + (float)ActiveRenderInfo.func_178804_a().yCoord) / (n13 + n10 + (float)ActiveRenderInfo.func_178804_a().yCoord) + (float)(n2 + n9), n8);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9473, this.func_147525_a(1.0f, 0.0f, 0.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9473, this.func_147525_a(0.0f, 0.0f, 1.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9473, this.func_147525_a(0.0f, 0.0f, 0.0f, 1.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, this.func_147525_a(0.0f, 1.0f, 0.0f, 0.0f));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.matrixMode(5890);
            GlStateManager.translate(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GlStateManager.scale(n11, n11, n11);
            GlStateManager.translate(0.5f, 0.5f, 0.0f);
            GlStateManager.rotate(0 * 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.0f);
            GlStateManager.translate(-n6, -n8, -n7);
            final float n14 = n13 + (float)ActiveRenderInfo.func_178804_a().yCoord;
            GlStateManager.translate((float)ActiveRenderInfo.func_178804_a().xCoord * n10 / n14, (float)ActiveRenderInfo.func_178804_a().zCoord * n10 / n14, -n7);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();
            float n15 = TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.1f;
            float n16 = TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.4f;
            float n17 = TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.5f;
            if (!false) {
                n17 = 1.0f;
                n16 = 1.0f;
                n15 = 1.0f;
            }
            worldRenderer.func_178960_a(n15 * n12, n16 * n12, n17 * n12, 1.0f);
            worldRenderer.addVertex(n, n2 + n9, n3);
            worldRenderer.addVertex(n, n2 + n9, n3 + 1.0);
            worldRenderer.addVertex(n + 1.0, n2 + n9, n3 + 1.0);
            worldRenderer.addVertex(n + 1.0, n2 + n9, n3);
            instance.draw();
            GlStateManager.matrixMode(5888);
            int n18 = 0;
            ++n18;
        }
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
    }
    
    private FloatBuffer func_147525_a(final float n, final float n2, final float n3, final float n4) {
        this.field_147528_b.clear();
        this.field_147528_b.put(n).put(n2).put(n3).put(n4);
        this.field_147528_b.flip();
        return this.field_147528_b;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180544_a((TileEntityEndPortal)tileEntity, n, n2, n3, n4, n5);
    }
}
