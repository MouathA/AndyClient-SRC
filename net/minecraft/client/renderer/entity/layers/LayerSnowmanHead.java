package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;

public class LayerSnowmanHead implements LayerRenderer
{
    private final RenderSnowMan field_177152_a;
    private static final String __OBFID;
    
    public LayerSnowmanHead(final RenderSnowMan field_177152_a) {
        this.field_177152_a = field_177152_a;
    }
    
    public void func_177151_a(final EntitySnowman entitySnowman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySnowman.isInvisible()) {
            this.field_177152_a.func_177123_g().head.postRender(0.0625f);
            final float n8 = 0.625f;
            GlStateManager.translate(0.0f, -0.34375f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(n8, -n8, -n8);
            Minecraft.getMinecraft().getItemRenderer().renderItem(entitySnowman, new ItemStack(Blocks.pumpkin, 1), ItemCameraTransforms.TransformType.HEAD);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177151_a((EntitySnowman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002411";
    }
}
