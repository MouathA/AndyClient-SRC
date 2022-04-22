package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class LayerHeldItemWitch implements LayerRenderer
{
    private final RenderWitch field_177144_a;
    private static final String __OBFID;
    
    public LayerHeldItemWitch(final RenderWitch field_177144_a) {
        this.field_177144_a = field_177144_a;
    }
    
    public void func_177143_a(final EntityWitch entityWitch, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final ItemStack heldItem = entityWitch.getHeldItem();
        if (heldItem != null) {
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            if (this.field_177144_a.getMainModel().isChild) {
                GlStateManager.translate(0.0f, 0.625f, 0.0f);
                GlStateManager.rotate(-20.0f, -1.0f, 0.0f, 0.0f);
                final float n8 = 0.5f;
                GlStateManager.scale(n8, n8, n8);
            }
            ((ModelWitch)this.field_177144_a.getMainModel()).villagerNose.postRender(0.0625f);
            GlStateManager.translate(-0.0625f, 0.53125f, 0.21875f);
            final Item item = heldItem.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (item instanceof ItemBlock && minecraft.getBlockRendererDispatcher().func_175021_a(Block.getBlockFromItem(item), heldItem.getMetadata())) {
                GlStateManager.translate(0.0f, 0.1875f, -0.3125f);
                GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                final float n9 = 0.375f;
                GlStateManager.scale(n9, -n9, n9);
            }
            else if (item == Items.bow) {
                GlStateManager.translate(0.0f, 0.125f, 0.3125f);
                GlStateManager.rotate(-20.0f, 0.0f, 1.0f, 0.0f);
                final float n10 = 0.625f;
                GlStateManager.scale(n10, -n10, n10);
                GlStateManager.rotate(-100.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (item.isFull3D()) {
                if (item.shouldRotateAroundWhenRendering()) {
                    GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.translate(0.0f, -0.125f, 0.0f);
                }
                this.field_177144_a.func_82422_c();
                final float n11 = 0.625f;
                GlStateManager.scale(n11, -n11, n11);
                GlStateManager.rotate(-100.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.translate(0.25f, 0.1875f, -0.1875f);
                final float n12 = 0.375f;
                GlStateManager.scale(n12, n12, n12);
                GlStateManager.rotate(60.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(20.0f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotate(-15.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(40.0f, 0.0f, 0.0f, 1.0f);
            minecraft.getItemRenderer().renderItem(entityWitch, heldItem, ItemCameraTransforms.TransformType.THIRD_PERSON);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177143_a((EntityWitch)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002407";
    }
}
