package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerMooshroomMushroom implements LayerRenderer
{
    private final RenderMooshroom field_177205_a;
    private static final String __OBFID;
    
    public LayerMooshroomMushroom(final RenderMooshroom field_177205_a) {
        this.field_177205_a = field_177205_a;
    }
    
    public void func_177204_a(final EntityMooshroom entityMooshroom, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entityMooshroom.isChild() && !entityMooshroom.isInvisible()) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            this.field_177205_a.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.2f, 0.35f, 0.5f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.translate(0.1f, 0.0f, -0.6f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0f);
            ((ModelQuadruped)this.field_177205_a.getMainModel()).head.postRender(0.0625f);
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.0f, 0.7f, -0.2f);
            GlStateManager.rotate(12.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.func_175016_a(Blocks.red_mushroom.getDefaultState(), 1.0f);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177204_a((EntityMooshroom)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002415";
    }
}
