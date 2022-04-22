package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation beaconBeam;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000962";
        beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
    }
    
    public void func_180536_a(final TileEntityBeacon tileEntityBeacon, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float shouldBeamRender = tileEntityBeacon.shouldBeamRender();
        GlStateManager.alphaFunc(516, 0.1f);
        if (shouldBeamRender > 0.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final List func_174907_n = tileEntityBeacon.func_174907_n();
            while (0 < func_174907_n.size()) {
                final TileEntityBeacon.BeamSegment beamSegment = func_174907_n.get(0);
                final int n6 = 0 + beamSegment.func_177264_c();
                this.bindTexture(TileEntityBeaconRenderer.beaconBeam);
                GL11.glTexParameterf(3553, 10242, 10497.0f);
                GL11.glTexParameterf(3553, 10243, 10497.0f);
                GlStateManager.depthMask(true);
                GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
                final float n7 = tileEntityBeacon.getWorld().getTotalWorldTime() + n4;
                final float n8 = -n7 * 0.2f - MathHelper.floor_float(-n7 * 0.1f);
                final double n9 = n7 * 0.025 * -1.5;
                worldRenderer.startDrawingQuads();
                final double n10 = 0.2;
                final double n11 = 0.5 + Math.cos(n9 + 2.356194490192345) * n10;
                final double n12 = 0.5 + Math.sin(n9 + 2.356194490192345) * n10;
                final double n13 = 0.5 + Math.cos(n9 + 0.7853981633974483) * n10;
                final double n14 = 0.5 + Math.sin(n9 + 0.7853981633974483) * n10;
                final double n15 = 0.5 + Math.cos(n9 + 3.9269908169872414) * n10;
                final double n16 = 0.5 + Math.sin(n9 + 3.9269908169872414) * n10;
                final double n17 = 0.5 + Math.cos(n9 + 5.497787143782138) * n10;
                final double n18 = 0.5 + Math.sin(n9 + 5.497787143782138) * n10;
                final double n19 = 0.0;
                final double n20 = 1.0;
                final double n21 = -1.0f + n8;
                final double n22 = beamSegment.func_177264_c() * shouldBeamRender * (0.5 / n10) + n21;
                worldRenderer.func_178960_a(beamSegment.func_177263_b()[0], beamSegment.func_177263_b()[1], beamSegment.func_177263_b()[2], 0.125f);
                worldRenderer.addVertexWithUV(n + n11, n2 + n6, n3 + n12, n20, n22);
                worldRenderer.addVertexWithUV(n + n11, n2 + 0, n3 + n12, n20, n21);
                worldRenderer.addVertexWithUV(n + n13, n2 + 0, n3 + n14, n19, n21);
                worldRenderer.addVertexWithUV(n + n13, n2 + n6, n3 + n14, n19, n22);
                worldRenderer.addVertexWithUV(n + n17, n2 + n6, n3 + n18, n20, n22);
                worldRenderer.addVertexWithUV(n + n17, n2 + 0, n3 + n18, n20, n21);
                worldRenderer.addVertexWithUV(n + n15, n2 + 0, n3 + n16, n19, n21);
                worldRenderer.addVertexWithUV(n + n15, n2 + n6, n3 + n16, n19, n22);
                worldRenderer.addVertexWithUV(n + n13, n2 + n6, n3 + n14, n20, n22);
                worldRenderer.addVertexWithUV(n + n13, n2 + 0, n3 + n14, n20, n21);
                worldRenderer.addVertexWithUV(n + n17, n2 + 0, n3 + n18, n19, n21);
                worldRenderer.addVertexWithUV(n + n17, n2 + n6, n3 + n18, n19, n22);
                worldRenderer.addVertexWithUV(n + n15, n2 + n6, n3 + n16, n20, n22);
                worldRenderer.addVertexWithUV(n + n15, n2 + 0, n3 + n16, n20, n21);
                worldRenderer.addVertexWithUV(n + n11, n2 + 0, n3 + n12, n19, n21);
                worldRenderer.addVertexWithUV(n + n11, n2 + n6, n3 + n12, n19, n22);
                instance.draw();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.depthMask(false);
                worldRenderer.startDrawingQuads();
                worldRenderer.func_178960_a(beamSegment.func_177263_b()[0], beamSegment.func_177263_b()[1], beamSegment.func_177263_b()[2], 0.125f);
                final double n23 = 0.2;
                final double n24 = 0.2;
                final double n25 = 0.8;
                final double n26 = 0.2;
                final double n27 = 0.2;
                final double n28 = 0.8;
                final double n29 = 0.8;
                final double n30 = 0.8;
                final double n31 = 0.0;
                final double n32 = 1.0;
                final double n33 = -1.0f + n8;
                final double n34 = beamSegment.func_177264_c() * shouldBeamRender + n33;
                worldRenderer.addVertexWithUV(n + n23, n2 + n6, n3 + n24, n32, n34);
                worldRenderer.addVertexWithUV(n + n23, n2 + 0, n3 + n24, n32, n33);
                worldRenderer.addVertexWithUV(n + n25, n2 + 0, n3 + n26, n31, n33);
                worldRenderer.addVertexWithUV(n + n25, n2 + n6, n3 + n26, n31, n34);
                worldRenderer.addVertexWithUV(n + n29, n2 + n6, n3 + n30, n32, n34);
                worldRenderer.addVertexWithUV(n + n29, n2 + 0, n3 + n30, n32, n33);
                worldRenderer.addVertexWithUV(n + n27, n2 + 0, n3 + n28, n31, n33);
                worldRenderer.addVertexWithUV(n + n27, n2 + n6, n3 + n28, n31, n34);
                worldRenderer.addVertexWithUV(n + n25, n2 + n6, n3 + n26, n32, n34);
                worldRenderer.addVertexWithUV(n + n25, n2 + 0, n3 + n26, n32, n33);
                worldRenderer.addVertexWithUV(n + n29, n2 + 0, n3 + n30, n31, n33);
                worldRenderer.addVertexWithUV(n + n29, n2 + n6, n3 + n30, n31, n34);
                worldRenderer.addVertexWithUV(n + n27, n2 + n6, n3 + n28, n32, n34);
                worldRenderer.addVertexWithUV(n + n27, n2 + 0, n3 + n28, n32, n33);
                worldRenderer.addVertexWithUV(n + n23, n2 + 0, n3 + n24, n31, n33);
                worldRenderer.addVertexWithUV(n + n23, n2 + n6, n3 + n24, n31, n34);
                instance.draw();
                GlStateManager.depthMask(true);
                int n35 = 0;
                ++n35;
            }
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180536_a((TileEntityBeacon)tileEntity, n, n2, n3, n4, n5);
    }
}
