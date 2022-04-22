package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderBiped extends RenderLiving
{
    private static final ResourceLocation field_177118_j;
    protected ModelBiped modelBipedMain;
    protected float field_77070_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001001";
        field_177118_j = new ResourceLocation("textures/entity/steve.png");
    }
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBiped, final float n) {
        this(renderManager, modelBiped, n, 1.0f);
        this.addLayer(new LayerHeldItem(this));
    }
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBipedMain, final float n, final float field_77070_b) {
        super(renderManager, modelBipedMain, n);
        this.modelBipedMain = modelBipedMain;
        this.field_77070_b = field_77070_b;
        this.addLayer(new LayerCustomHead(modelBipedMain.bipedHead));
    }
    
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return RenderBiped.field_177118_j;
    }
    
    @Override
    public void func_82422_c() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLiving)entity);
    }
}
