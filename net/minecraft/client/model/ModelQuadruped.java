package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ModelQuadruped extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    protected float childYOffset;
    protected float childZOffset;
    private static final String __OBFID;
    
    public ModelQuadruped(final int n, final float n2) {
        this.head = new ModelRenderer(this, 0, 0);
        this.childYOffset = 8.0f;
        this.childZOffset = 4.0f;
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, n2);
        this.head.setRotationPoint(0.0f, (float)(18 - n), -6.0f);
        (this.body = new ModelRenderer(this, 28, 8)).addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, n2);
        this.body.setRotationPoint(0.0f, (float)(17 - n), 2.0f);
        (this.leg1 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, n, 4, n2);
        this.leg1.setRotationPoint(-3.0f, (float)(24 - n), 7.0f);
        (this.leg2 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, n, 4, n2);
        this.leg2.setRotationPoint(3.0f, (float)(24 - n), 7.0f);
        (this.leg3 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, n, 4, n2);
        this.leg3.setRotationPoint(-3.0f, (float)(24 - n), -5.0f);
        (this.leg4 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, n, 4, n2);
        this.leg4.setRotationPoint(3.0f, (float)(24 - n), -5.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.translate(0.0f, this.childYOffset * n6, this.childZOffset * n6);
            this.head.render(n6);
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.body.render(n6);
            this.leg1.render(n6);
            this.leg2.render(n6);
            this.leg3.render(n6);
            this.leg4.render(n6);
        }
        else {
            this.head.render(n6);
            this.body.render(n6);
            this.leg1.render(n6);
            this.leg2.render(n6);
            this.leg3.render(n6);
            this.leg4.render(n6);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.head.rotateAngleX = n5 / 57.295776f;
        this.head.rotateAngleY = n4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
    }
    
    static {
        __OBFID = "CL_00000851";
    }
}
