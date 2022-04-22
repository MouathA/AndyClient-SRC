package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import optifine.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerWolfCollar implements LayerRenderer
{
    private static final ResourceLocation field_177147_a;
    private final RenderWolf field_177146_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002405";
        field_177147_a = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }
    
    public LayerWolfCollar(final RenderWolf field_177146_b) {
        this.field_177146_b = field_177146_b;
    }
    
    public void func_177145_a(final EntityWolf entityWolf, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWolf.isTamed() && !entityWolf.isInvisible()) {
            this.field_177146_b.bindTexture(LayerWolfCollar.field_177147_a);
            final EnumDyeColor func_176764_b = EnumDyeColor.func_176764_b(entityWolf.func_175546_cu().func_176765_a());
            float[] array = EntitySheep.func_175513_a(func_176764_b);
            if (Config.isCustomColors()) {
                array = CustomColors.getWolfCollarColors(func_176764_b, array);
            }
            GlStateManager.color(array[0], array[1], array[2]);
            this.field_177146_b.getMainModel().render(entityWolf, n, n2, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.func_177145_a((EntityWolf)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
