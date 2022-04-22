package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147520_b;
    private ModelChest field_147521_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000967";
        field_147520_b = new ResourceLocation("textures/entity/chest/ender.png");
    }
    
    public TileEntityEnderChestRenderer() {
        this.field_147521_c = new ModelChest();
    }
    
    public void func_180540_a(final TileEntityEnderChest tileEntityEnderChest, final double n, final double n2, final double n3, final float n4, final int n5) {
        if (tileEntityEnderChest.hasWorldObj()) {
            tileEntityEnderChest.getBlockMetadata();
        }
        if (n5 >= 0) {
            this.bindTexture(TileEntityEnderChestRenderer.DESTROY_STAGES[n5]);
            GlStateManager.matrixMode(5890);
            GlStateManager.scale(4.0f, 4.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        }
        else {
            this.bindTexture(TileEntityEnderChestRenderer.field_147520_b);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate((float)n, (float)n2 + 1.0f, (float)n3 + 1.0f);
        GlStateManager.scale(1.0f, -1.0f, -1.0f);
        GlStateManager.translate(0.5f, 0.5f, 0.5f);
        if (0 == 2) {}
        if (0 == 3) {}
        if (0 == 4) {}
        if (0 == 5) {}
        GlStateManager.rotate(-90, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(-0.5f, -0.5f, -0.5f);
        final float n6 = 1.0f - (tileEntityEnderChest.prevLidAngle + (tileEntityEnderChest.field_145972_a - tileEntityEnderChest.prevLidAngle) * n4);
        this.field_147521_c.chestLid.rotateAngleX = -((1.0f - n6 * n6 * n6) * 3.1415927f / 2.0f);
        this.field_147521_c.renderAll();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (n5 >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180540_a((TileEntityEnderChest)tileEntity, n, n2, n3, n4, n5);
    }
}
