package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelEnderCrystal extends ModelBase
{
    private ModelRenderer cube;
    private ModelRenderer glass;
    private ModelRenderer base;
    private static final String __OBFID;
    
    public ModelEnderCrystal(final float n, final boolean b) {
        this.glass = new ModelRenderer(this, "glass");
        this.glass.setTextureOffset(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        if (b) {
            this.base = new ModelRenderer(this, "base");
            this.base.setTextureOffset(0, 16).addBox(-6.0f, 0.0f, -6.0f, 12, 4, 12);
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.translate(0.0f, -0.5f, 0.0f);
        if (this.base != null) {
            this.base.render(n6);
        }
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.8f + n3, 0.0f);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        this.glass.render(n6);
        final float n7 = 0.875f;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        this.glass.render(n6);
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        this.cube.render(n6);
    }
    
    static {
        __OBFID = "CL_00000871";
    }
}
