package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import optifine.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147513_b;
    private final ModelSign model;
    
    static {
        field_147513_b = new ResourceLocation("textures/entity/sign.png");
    }
    
    public TileEntitySignRenderer() {
        this.model = new ModelSign();
    }
    
    public void func_180541_a(final TileEntitySign tileEntitySign, final double n, final double n2, final double n3, final float n4, final int n5) {
        final Block blockType = tileEntitySign.getBlockType();
        final float n6 = 0.6666667f;
        if (blockType == Blocks.standing_sign) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n6, (float)n3 + 0.5f);
            GlStateManager.rotate(-(tileEntitySign.getBlockMetadata() * 360 / 16.0f), 0.0f, 1.0f, 0.0f);
            this.model.signStick.showModel = true;
        }
        else {
            final int blockMetadata = tileEntitySign.getBlockMetadata();
            float n7 = 0.0f;
            if (blockMetadata == 2) {
                n7 = 180.0f;
            }
            if (blockMetadata == 4) {
                n7 = 90.0f;
            }
            if (blockMetadata == 5) {
                n7 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n6, (float)n3 + 0.5f);
            GlStateManager.rotate(-n7, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.model.signStick.showModel = false;
        }
        if (n5 >= 0) {
            this.bindTexture(TileEntitySignRenderer.DESTROY_STAGES[n5]);
            GlStateManager.matrixMode(5890);
            GlStateManager.scale(4.0f, 2.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(5888);
        }
        else {
            this.bindTexture(TileEntitySignRenderer.field_147513_b);
        }
        GlStateManager.scale(n6, -n6, -n6);
        this.model.renderSign();
        final FontRenderer fontRenderer = this.getFontRenderer();
        final float n8 = 0.015625f * n6;
        GlStateManager.translate(0.0f, 0.5f * n6, 0.07f * n6);
        GlStateManager.scale(n8, -n8, n8);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * n8);
        GlStateManager.depthMask(false);
        if (Config.isCustomColors()) {
            CustomColors.getSignTextColor(0);
        }
        if (n5 < 0) {
            while (0 < tileEntitySign.signText.length) {
                if (tileEntitySign.signText[0] != null) {
                    final List func_178908_a = GuiUtilRenderComponents.func_178908_a(tileEntitySign.signText[0], 90, fontRenderer, false, true);
                    final String s = (func_178908_a != null && func_178908_a.size() > 0) ? func_178908_a.get(0).getFormattedText() : "";
                    if (0 == tileEntitySign.lineBeingEdited) {
                        final String string = "> " + s + " <";
                        fontRenderer.drawString(string, -fontRenderer.getStringWidth(string) / 2, 0 - tileEntitySign.signText.length * 5, 0);
                    }
                    else {
                        fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 0 - tileEntitySign.signText.length * 5, 0);
                    }
                }
                int n9 = 0;
                ++n9;
            }
        }
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (n5 >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_180541_a((TileEntitySign)tileEntity, n, n2, n3, n4, n5);
    }
}
