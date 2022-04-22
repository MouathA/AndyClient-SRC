package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import optifine.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSheepWool implements LayerRenderer
{
    private static final ResourceLocation TEXTURE;
    private final RenderSheep sheepRenderer;
    private final ModelSheep1 sheepModel;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002413";
        TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    }
    
    public LayerSheepWool(final RenderSheep sheepRenderer) {
        this.sheepModel = new ModelSheep1();
        this.sheepRenderer = sheepRenderer;
    }
    
    public void doRenderLayer(final EntitySheep entitySheep, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySheep.getSheared() && !entitySheep.isInvisible()) {
            this.sheepRenderer.bindTexture(LayerSheepWool.TEXTURE);
            if (entitySheep.hasCustomName() && "jeb_".equals(entitySheep.getCustomNameTag())) {
                final int n8 = entitySheep.ticksExisted / 25 + entitySheep.getEntityId();
                final int length = EnumDyeColor.values().length;
                final int n9 = n8 % length;
                final int n10 = (n8 + 1) % length;
                final float n11 = (entitySheep.ticksExisted % 25 + n3) / 25.0f;
                float[] array = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(n9));
                float[] array2 = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(n10));
                if (Config.isCustomColors()) {
                    array = CustomColors.getSheepColors(EnumDyeColor.func_176764_b(n9), array);
                    array2 = CustomColors.getSheepColors(EnumDyeColor.func_176764_b(n10), array2);
                }
                GlStateManager.color(array[0] * (1.0f - n11) + array2[0] * n11, array[1] * (1.0f - n11) + array2[1] * n11, array[2] * (1.0f - n11) + array2[2] * n11);
            }
            else {
                float[] array3 = EntitySheep.func_175513_a(entitySheep.func_175509_cj());
                if (Config.isCustomColors()) {
                    array3 = CustomColors.getSheepColors(entitySheep.func_175509_cj(), array3);
                }
                GlStateManager.color(array3[0], array3[1], array3[2]);
            }
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitySheep, n, n2, n3);
            this.sheepModel.render(entitySheep, n, n2, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySheep)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
