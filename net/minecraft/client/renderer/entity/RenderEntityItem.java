package net.minecraft.client.renderer.entity;

import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import Mood.*;
import DTool.util.*;

public class RenderEntityItem extends Render
{
    private final RenderItem field_177080_a;
    private Random field_177079_e;
    private static final String __OBFID;
    
    public RenderEntityItem(final RenderManager renderManager, final RenderItem field_177080_a) {
        super(renderManager);
        this.field_177079_e = new Random();
        this.field_177080_a = field_177080_a;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    private int func_177077_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final IBakedModel bakedModel) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() == null) {
            return 0;
        }
        final boolean ambientOcclusionEnabled = bakedModel.isAmbientOcclusionEnabled();
        final int func_177078_a = this.func_177078_a(entityItem2);
        GlStateManager.translate((float)n, (float)n2 + (MathHelper.sin((entityItem.func_174872_o() + n4) / 10.0f + entityItem.hoverStart) * 0.1f + 0.1f) + 0.25f, (float)n3);
        if (ambientOcclusionEnabled || (this.renderManager.options != null && this.renderManager.options.fancyGraphics)) {
            GlStateManager.rotate(((entityItem.func_174872_o() + n4) / 20.0f + entityItem.hoverStart) * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (!ambientOcclusionEnabled) {
            GlStateManager.translate(-0.0f * (func_177078_a - 1) * 0.5f, -0.0f * (func_177078_a - 1) * 0.5f, -0.046875f * (func_177078_a - 1) * 0.5f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return func_177078_a;
    }
    
    private int func_177078_a(final ItemStack itemStack) {
        if (itemStack.stackSize <= 48) {
            if (itemStack.stackSize <= 32) {
                if (itemStack.stackSize <= 16) {
                    if (itemStack.stackSize > 1) {}
                }
            }
        }
        return 2;
    }
    
    public void func_177075_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final float n5) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        this.field_177079_e.setSeed(187L);
        if (this.bindEntityTexture(entityItem)) {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(entityItem)).func_174936_b(false, false);
        }
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final IBakedModel itemModel = this.field_177080_a.getItemModelMesher().getItemModel(entityItem2);
        while (0 < this.func_177077_a(entityItem, n, n2, n3, n5, itemModel)) {
            if (itemModel.isAmbientOcclusionEnabled()) {
                if (0 > 0) {
                    GlStateManager.translate((this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f, (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f, (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f);
                }
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                this.field_177080_a.func_180454_a(entityItem2, itemModel);
            }
            else {
                this.field_177080_a.func_180454_a(entityItem2, itemModel);
                GlStateManager.translate(0.0f, 0.0f, 0.046875f);
            }
            int n6 = 0;
            ++n6;
        }
        this.bindEntityTexture(entityItem);
        if (true) {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(entityItem)).func_174935_a();
        }
        super.doRender(entityItem, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_177076_a(final EntityItem entityItem) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_177076_a((EntityItem)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("ItemPhysics").toggled) {
            ItemPhysicUtils.doRender(entity, n, n2, n3, n4, n5);
        }
        else {
            this.func_177075_a((EntityItem)entity, n, n2, n3, n4, n5);
        }
        super.doRender(entity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00002442";
    }
}
