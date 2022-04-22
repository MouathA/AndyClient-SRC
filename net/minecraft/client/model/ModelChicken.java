package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ModelChicken extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;
    private static final String __OBFID;
    
    public ModelChicken() {
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-2.0f, -6.0f, -2.0f, 4, 6, 3, 0.0f);
        this.head.setRotationPoint(0.0f, 15, -4.0f);
        (this.bill = new ModelRenderer(this, 14, 0)).addBox(-2.0f, -4.0f, -4.0f, 4, 2, 2, 0.0f);
        this.bill.setRotationPoint(0.0f, 15, -4.0f);
        (this.chin = new ModelRenderer(this, 14, 4)).addBox(-1.0f, -2.0f, -3.0f, 2, 2, 2, 0.0f);
        this.chin.setRotationPoint(0.0f, 15, -4.0f);
        (this.body = new ModelRenderer(this, 0, 9)).addBox(-3.0f, -4.0f, -3.0f, 6, 8, 6, 0.0f);
        this.body.setRotationPoint(0.0f, 16, 0.0f);
        (this.rightLeg = new ModelRenderer(this, 26, 0)).addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.rightLeg.setRotationPoint(-2.0f, 19, 1.0f);
        (this.leftLeg = new ModelRenderer(this, 26, 0)).addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.leftLeg.setRotationPoint(1.0f, 19, 1.0f);
        (this.rightWing = new ModelRenderer(this, 24, 13)).addBox(0.0f, 0.0f, -3.0f, 1, 4, 6);
        this.rightWing.setRotationPoint(-4.0f, 13, 0.0f);
        (this.leftWing = new ModelRenderer(this, 24, 13)).addBox(-1.0f, 0.0f, -3.0f, 1, 4, 6);
        this.leftWing.setRotationPoint(4.0f, 13, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.head.render(n6);
            this.bill.render(n6);
            this.chin.render(n6);
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.body.render(n6);
            this.rightLeg.render(n6);
            this.leftLeg.render(n6);
            this.rightWing.render(n6);
            this.leftWing.render(n6);
        }
        else {
            this.head.render(n6);
            this.bill.render(n6);
            this.chin.render(n6);
            this.body.render(n6);
            this.rightLeg.render(n6);
            this.leftLeg.render(n6);
            this.rightWing.render(n6);
            this.leftWing.render(n6);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleZ, final float n3, final float n4, final float n5, final Entity entity) {
        this.head.rotateAngleX = n4 / 57.295776f;
        this.head.rotateAngleY = n3 / 57.295776f;
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = 1.5707964f;
        this.rightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.rightWing.rotateAngleZ = rotateAngleZ;
        this.leftWing.rotateAngleZ = -rotateAngleZ;
    }
    
    static {
        __OBFID = "CL_00000835";
    }
}
