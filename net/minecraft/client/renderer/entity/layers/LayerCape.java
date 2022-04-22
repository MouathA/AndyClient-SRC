package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class LayerCape implements LayerRenderer
{
    private final RenderPlayer playerRenderer;
    private static final String __OBFID;
    
    public LayerCape(final RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
    
    public void doRenderLayer(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (abstractClientPlayer.hasCape() && !abstractClientPlayer.isInvisible() && abstractClientPlayer.func_175148_a(EnumPlayerModelParts.CAPE) && abstractClientPlayer.getLocationCape() != null) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
            GlStateManager.translate(0.0f, 0.0f, 0.125f);
            final double n8 = abstractClientPlayer.field_71091_bM + (abstractClientPlayer.field_71094_bP - abstractClientPlayer.field_71091_bM) * n3 - (abstractClientPlayer.prevPosX + (abstractClientPlayer.posX - abstractClientPlayer.prevPosX) * n3);
            final double n9 = abstractClientPlayer.field_71096_bN + (abstractClientPlayer.field_71095_bQ - abstractClientPlayer.field_71096_bN) * n3 - (abstractClientPlayer.prevPosY + (abstractClientPlayer.posY - abstractClientPlayer.prevPosY) * n3);
            final double n10 = abstractClientPlayer.field_71097_bO + (abstractClientPlayer.field_71085_bR - abstractClientPlayer.field_71097_bO) * n3 - (abstractClientPlayer.prevPosZ + (abstractClientPlayer.posZ - abstractClientPlayer.prevPosZ) * n3);
            final float n11 = abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset) * n3;
            final double n12 = MathHelper.sin(n11 * 3.1415927f / 180.0f);
            final double n13 = -MathHelper.cos(n11 * 3.1415927f / 180.0f);
            final float clamp_float = MathHelper.clamp_float((float)n9 * 10.0f, -6.0f, 32.0f);
            float n14 = (float)(n8 * n12 + n10 * n13) * 100.0f;
            final float n15 = (float)(n8 * n13 - n10 * n12) * 100.0f;
            if (n14 < 0.0f) {
                n14 = 0.0f;
            }
            if (n14 > 165.0f) {
                n14 = 165.0f;
            }
            float n16 = clamp_float + MathHelper.sin((abstractClientPlayer.prevDistanceWalkedModified + (abstractClientPlayer.distanceWalkedModified - abstractClientPlayer.prevDistanceWalkedModified) * n3) * 6.0f) * 32.0f * (abstractClientPlayer.prevCameraYaw + (abstractClientPlayer.cameraYaw - abstractClientPlayer.prevCameraYaw) * n3);
            if (abstractClientPlayer.isSneaking()) {
                n16 += 25.0f;
                GlStateManager.translate(0.0f, 0.142f, -0.0178f);
            }
            GlStateManager.rotate(6.0f + n14 / 2.0f + n16, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n15 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-n15 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            this.playerRenderer.func_177136_g().func_178728_c(0.0625f);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002425";
    }
}
