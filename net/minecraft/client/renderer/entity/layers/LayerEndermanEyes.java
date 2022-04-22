package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import optifine.*;
import net.minecraft.entity.*;

public class LayerEndermanEyes implements LayerRenderer
{
    private static final ResourceLocation field_177203_a;
    private final RenderEnderman field_177202_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002418";
        field_177203_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    }
    
    public LayerEndermanEyes(final RenderEnderman field_177202_b) {
        this.field_177202_b = field_177202_b;
    }
    
    public void func_177201_a(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.field_177202_b.bindTexture(LayerEndermanEyes.field_177203_a);
        GlStateManager.blendFunc(1, 1);
        if (entityEnderman.isInvisible()) {
            GlStateManager.depthMask(false);
        }
        else {
            GlStateManager.depthMask(true);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680 / 1.0f, 571 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Config.isShaders();
        this.field_177202_b.getMainModel().render(entityEnderman, n, n2, n4, n5, n6, n7);
        this.field_177202_b.func_177105_a(entityEnderman, n3);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177201_a((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
