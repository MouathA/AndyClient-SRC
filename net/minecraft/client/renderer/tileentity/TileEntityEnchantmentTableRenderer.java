package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147540_b;
    private ModelBook field_147541_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002470";
        field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
    }
    
    public TileEntityEnchantmentTableRenderer() {
        this.field_147541_c = new ModelBook();
    }
    
    public void func_180537_a(final TileEntityEnchantmentTable tileEntityEnchantmentTable, final double n, final double n2, final double n3, final float n4, final int n5) {
        GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f, (float)n3 + 0.5f);
        final float n6 = tileEntityEnchantmentTable.tickCount + n4;
        GlStateManager.translate(0.0f, 0.1f + MathHelper.sin(n6 * 0.1f) * 0.01f, 0.0f);
        float n7;
        for (n7 = tileEntityEnchantmentTable.bookRotation - tileEntityEnchantmentTable.bookRotationPrev; n7 >= 3.1415927f; n7 -= 6.2831855f) {}
        while (n7 < -3.1415927f) {
            n7 += 6.2831855f;
        }
        GlStateManager.rotate(-(tileEntityEnchantmentTable.bookRotationPrev + n7 * n4) * 180.0f / 3.1415927f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(80.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(TileEntityEnchantmentTableRenderer.field_147540_b);
        final float n8 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * n4 + 0.25f;
        final float n9 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * n4 + 0.75f;
        float n10 = (n8 - MathHelper.truncateDoubleToInt(n8)) * 1.6f - 0.3f;
        float n11 = (n9 - MathHelper.truncateDoubleToInt(n9)) * 1.6f - 0.3f;
        if (n10 < 0.0f) {
            n10 = 0.0f;
        }
        if (n11 < 0.0f) {
            n11 = 0.0f;
        }
        if (n10 > 1.0f) {
            n10 = 1.0f;
        }
        if (n11 > 1.0f) {
            n11 = 1.0f;
        }
        this.field_147541_c.render(null, n6, n10, n11, tileEntityEnchantmentTable.bookSpreadPrev + (tileEntityEnchantmentTable.bookSpread - tileEntityEnchantmentTable.bookSpreadPrev) * n4, 0.0f, 0.0625f);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180537_a((TileEntityEnchantmentTable)tileEntity, n, n2, n3, n4, n5);
    }
}
