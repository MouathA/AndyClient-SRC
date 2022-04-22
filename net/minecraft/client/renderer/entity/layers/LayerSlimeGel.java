package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSlimeGel implements LayerRenderer
{
    private final RenderSlime slimeRenderer;
    private final ModelBase slimeModel;
    private static final String __OBFID;
    
    public LayerSlimeGel(final RenderSlime slimeRenderer) {
        this.slimeModel = new ModelSlime(0);
        this.slimeRenderer = slimeRenderer;
    }
    
    public void doRenderLayer(final EntitySlime entitySlime, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySlime.isInvisible()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.blendFunc(770, 771);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitySlime, n, n2, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySlime)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        __OBFID = "CL_00002412";
    }
}
