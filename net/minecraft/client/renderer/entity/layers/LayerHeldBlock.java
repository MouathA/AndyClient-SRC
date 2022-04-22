package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerHeldBlock implements LayerRenderer
{
    private final RenderEnderman field_177174_a;
    private static final String __OBFID;
    
    public LayerHeldBlock(final RenderEnderman field_177174_a) {
        this.field_177174_a = field_177174_a;
    }
    
    public void func_177173_a(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final IBlockState func_175489_ck = entityEnderman.func_175489_ck();
        if (func_175489_ck.getBlock().getMaterial() != Material.air) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.translate(0.0f, 0.6875f, -0.75f);
            GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.25f, 0.1875f, 0.25f);
            final float n8 = 0.5f;
            GlStateManager.scale(-n8, -n8, n8);
            final int brightnessForRender = entityEnderman.getBrightnessForRender(n3);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_177174_a.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.func_175016_a(func_175489_ck, 1.0f);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177173_a((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002424";
    }
}
