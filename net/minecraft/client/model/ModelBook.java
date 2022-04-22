package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelBook extends ModelBase
{
    public ModelRenderer coverRight;
    public ModelRenderer coverLeft;
    public ModelRenderer pagesRight;
    public ModelRenderer pagesLeft;
    public ModelRenderer flippingPageRight;
    public ModelRenderer flippingPageLeft;
    public ModelRenderer bookSpine;
    private static final String __OBFID;
    
    public ModelBook() {
        this.coverRight = new ModelRenderer(this).setTextureOffset(0, 0).addBox(-6.0f, -5.0f, 0.0f, 6, 10, 0);
        this.coverLeft = new ModelRenderer(this).setTextureOffset(16, 0).addBox(0.0f, -5.0f, 0.0f, 6, 10, 0);
        this.pagesRight = new ModelRenderer(this).setTextureOffset(0, 10).addBox(0.0f, -4.0f, -0.99f, 5, 8, 1);
        this.pagesLeft = new ModelRenderer(this).setTextureOffset(12, 10).addBox(0.0f, -4.0f, -0.01f, 5, 8, 1);
        this.flippingPageRight = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.flippingPageLeft = new ModelRenderer(this).setTextureOffset(24, 10).addBox(0.0f, -4.0f, 0.0f, 5, 8, 0);
        this.bookSpine = new ModelRenderer(this).setTextureOffset(12, 0).addBox(-1.0f, -5.0f, 0.0f, 2, 10, 0);
        this.coverRight.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.coverLeft.setRotationPoint(0.0f, 0.0f, 1.0f);
        this.bookSpine.rotateAngleY = 1.5707964f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.coverRight.render(n6);
        this.coverLeft.render(n6);
        this.bookSpine.render(n6);
        this.pagesRight.render(n6);
        this.pagesLeft.render(n6);
        this.flippingPageRight.render(n6);
        this.flippingPageLeft.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float rotateAngleY = (MathHelper.sin(n * 0.02f) * 0.1f + 1.25f) * n4;
        this.coverRight.rotateAngleY = 3.1415927f + rotateAngleY;
        this.coverLeft.rotateAngleY = -rotateAngleY;
        this.pagesRight.rotateAngleY = rotateAngleY;
        this.pagesLeft.rotateAngleY = -rotateAngleY;
        this.flippingPageRight.rotateAngleY = rotateAngleY - rotateAngleY * 2.0f * n2;
        this.flippingPageLeft.rotateAngleY = rotateAngleY - rotateAngleY * 2.0f * n3;
        this.pagesRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.pagesLeft.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageRight.rotationPointX = MathHelper.sin(rotateAngleY);
        this.flippingPageLeft.rotationPointX = MathHelper.sin(rotateAngleY);
    }
    
    static {
        __OBFID = "CL_00000833";
    }
}
