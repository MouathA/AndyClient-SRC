package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import optifine.*;
import net.minecraft.entity.*;

public class LayerSpiderEyes implements LayerRenderer
{
    private static final ResourceLocation field_177150_a;
    private final RenderSpider field_177149_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002410";
        field_177150_a = new ResourceLocation("textures/entity/spider_eyes.png");
    }
    
    public LayerSpiderEyes(final RenderSpider field_177149_b) {
        this.field_177149_b = field_177149_b;
    }
    
    public void func_177148_a(final EntitySpider entitySpider, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.field_177149_b.bindTexture(LayerSpiderEyes.field_177150_a);
        GlStateManager.blendFunc(1, 1);
        if (entitySpider.isInvisible()) {
            GlStateManager.depthMask(false);
        }
        else {
            GlStateManager.depthMask(true);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Config.isShaders();
        this.field_177149_b.getMainModel().render(entitySpider, n, n2, n4, n5, n6, n7);
        entitySpider.getBrightnessForRender(n3);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        this.field_177149_b.func_177105_a(entitySpider, n3);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177148_a((EntitySpider)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
