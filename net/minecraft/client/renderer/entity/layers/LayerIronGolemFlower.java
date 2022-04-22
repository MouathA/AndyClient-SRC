package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerIronGolemFlower implements LayerRenderer
{
    private final RenderIronGolem field_177154_a;
    private static final String __OBFID;
    
    public LayerIronGolemFlower(final RenderIronGolem field_177154_a) {
        this.field_177154_a = field_177154_a;
    }
    
    public void func_177153_a(final EntityIronGolem entityIronGolem, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityIronGolem.getHoldRoseTick() != 0) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.rotate(5.0f + 180.0f * ((ModelIronGolem)this.field_177154_a.getMainModel()).ironGolemRightArm.rotateAngleX / 3.1415927f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(-0.9375f, -0.625f, -0.9375f);
            final float n8 = 0.5f;
            GlStateManager.scale(n8, -n8, n8);
            final int brightnessForRender = entityIronGolem.getBrightnessForRender(n3);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_177154_a.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.func_175016_a(Blocks.red_flower.getDefaultState(), 1.0f);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177153_a((EntityIronGolem)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002408";
    }
}
