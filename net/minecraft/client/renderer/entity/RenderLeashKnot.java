package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderLeashKnot extends Render
{
    private static final ResourceLocation leashKnotTextures;
    private ModelLeashKnot leashKnotModel;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001010";
        leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
    }
    
    public RenderLeashKnot(final RenderManager renderManager) {
        super(renderManager);
        this.leashKnotModel = new ModelLeashKnot();
    }
    
    public void func_180559_a(final EntityLeashKnot entityLeashKnot, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n6 = 0.0625f;
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.bindEntityTexture(entityLeashKnot);
        this.leashKnotModel.render(entityLeashKnot, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, n6);
        super.doRender(entityLeashKnot, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityLeashKnot entityLeashKnot) {
        return RenderLeashKnot.leashKnotTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLeashKnot)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180559_a((EntityLeashKnot)entity, n, n2, n3, n4, n5);
    }
}
