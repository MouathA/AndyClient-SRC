package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerCreeperCharge implements LayerRenderer
{
    private static final ResourceLocation LIGHTNING_TEXTURE;
    private final RenderCreeper creeperRenderer;
    private final ModelCreeper creeperModel;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002423";
        LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    }
    
    public LayerCreeperCharge(final RenderCreeper creeperRenderer) {
        this.creeperModel = new ModelCreeper(2.0f);
        this.creeperRenderer = creeperRenderer;
    }
    
    public void doRenderLayer(final EntityCreeper entityCreeper, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityCreeper.getPowered()) {
            GlStateManager.depthMask(!entityCreeper.isInvisible());
            this.creeperRenderer.bindTexture(LayerCreeperCharge.LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            final float n8 = entityCreeper.ticksExisted + n3;
            GlStateManager.translate(n8 * 0.01f, n8 * 0.01f, 0.0f);
            GlStateManager.matrixMode(5888);
            final float n9 = 0.5f;
            GlStateManager.color(n9, n9, n9, 1.0f);
            GlStateManager.blendFunc(1, 1);
            this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
            this.creeperModel.render(entityCreeper, n, n2, n4, n5, n6, n7);
            GlStateManager.matrixMode(5890);
            GlStateManager.matrixMode(5888);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityCreeper)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
