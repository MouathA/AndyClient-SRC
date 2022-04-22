package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class LayerWitherAura implements LayerRenderer
{
    private static final ResourceLocation field_177217_a;
    private final RenderWither field_177215_b;
    private final ModelWither field_177216_c;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002406";
        field_177217_a = new ResourceLocation("textures/entity/wither/wither_armor.png");
    }
    
    public LayerWitherAura(final RenderWither field_177215_b) {
        this.field_177216_c = new ModelWither(0.5f);
        this.field_177215_b = field_177215_b;
    }
    
    public void func_177214_a(final EntityWither entityWither, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWither.isArmored()) {
            GlStateManager.depthMask(!entityWither.isInvisible());
            this.field_177215_b.bindTexture(LayerWitherAura.field_177217_a);
            GlStateManager.matrixMode(5890);
            final float n8 = entityWither.ticksExisted + n3;
            GlStateManager.translate(MathHelper.cos(n8 * 0.02f) * 3.0f, n8 * 0.01f, 0.0f);
            GlStateManager.matrixMode(5888);
            final float n9 = 0.5f;
            GlStateManager.color(n9, n9, n9, 1.0f);
            GlStateManager.blendFunc(1, 1);
            this.field_177216_c.setLivingAnimations(entityWither, n, n2, n3);
            this.field_177216_c.setModelAttributes(this.field_177215_b.getMainModel());
            this.field_177216_c.render(entityWither, n, n2, n4, n5, n6, n7);
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
        this.func_177214_a((EntityWither)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
