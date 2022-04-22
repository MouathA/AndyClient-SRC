package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

public class ModelWolf extends ModelBase
{
    public ModelRenderer wolfHeadMain;
    public ModelRenderer wolfBody;
    public ModelRenderer wolfLeg1;
    public ModelRenderer wolfLeg2;
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    ModelRenderer wolfTail;
    ModelRenderer wolfMane;
    private static final String __OBFID;
    
    public ModelWolf() {
        final float n = 0.0f;
        final float n2 = 13.5f;
        (this.wolfHeadMain = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -3.0f, -2.0f, 6, 6, 4, n);
        this.wolfHeadMain.setRotationPoint(-1.0f, n2, -7.0f);
        (this.wolfBody = new ModelRenderer(this, 18, 14)).addBox(-4.0f, -2.0f, -3.0f, 6, 9, 6, n);
        this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
        (this.wolfMane = new ModelRenderer(this, 21, 0)).addBox(-4.0f, -3.0f, -3.0f, 8, 6, 7, n);
        this.wolfMane.setRotationPoint(-1.0f, 14.0f, 2.0f);
        (this.wolfLeg1 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, n);
        this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
        (this.wolfLeg2 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, n);
        this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
        (this.wolfLeg3 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, n);
        this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
        (this.wolfLeg4 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, n);
        this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
        (this.wolfTail = new ModelRenderer(this, 9, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, n);
        this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0f, -5.0f, 0.0f, 2, 2, 1, n);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0f, -5.0f, 0.0f, 2, 2, 1, n);
        this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5f, 0.0f, -5.0f, 3, 3, 4, n);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.wolfHeadMain.renderWithRotation(n6);
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.wolfBody.render(n6);
            this.wolfLeg1.render(n6);
            this.wolfLeg2.render(n6);
            this.wolfLeg3.render(n6);
            this.wolfLeg4.render(n6);
            this.wolfTail.renderWithRotation(n6);
            this.wolfMane.render(n6);
        }
        else {
            this.wolfHeadMain.renderWithRotation(n6);
            this.wolfBody.render(n6);
            this.wolfLeg1.render(n6);
            this.wolfLeg2.render(n6);
            this.wolfLeg3.render(n6);
            this.wolfLeg4.render(n6);
            this.wolfTail.renderWithRotation(n6);
            this.wolfMane.render(n6);
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityWolf entityWolf = (EntityWolf)entityLivingBase;
        if (entityWolf.isAngry()) {
            this.wolfTail.rotateAngleY = 0.0f;
        }
        else {
            this.wolfTail.rotateAngleY = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        }
        if (entityWolf.isSitting()) {
            this.wolfMane.setRotationPoint(-1.0f, 16.0f, -3.0f);
            this.wolfMane.rotateAngleX = 1.2566371f;
            this.wolfMane.rotateAngleY = 0.0f;
            this.wolfBody.setRotationPoint(0.0f, 18.0f, 0.0f);
            this.wolfBody.rotateAngleX = 0.7853982f;
            this.wolfTail.setRotationPoint(-1.0f, 21.0f, 6.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 22.0f, 2.0f);
            this.wolfLeg1.rotateAngleX = 4.712389f;
            this.wolfLeg2.setRotationPoint(0.5f, 22.0f, 2.0f);
            this.wolfLeg2.rotateAngleX = 4.712389f;
            this.wolfLeg3.rotateAngleX = 5.811947f;
            this.wolfLeg3.setRotationPoint(-2.49f, 17.0f, -4.0f);
            this.wolfLeg4.rotateAngleX = 5.811947f;
            this.wolfLeg4.setRotationPoint(0.51f, 17.0f, -4.0f);
        }
        else {
            this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
            this.wolfBody.rotateAngleX = 1.5707964f;
            this.wolfMane.setRotationPoint(-1.0f, 14.0f, -3.0f);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
            this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
            this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
            this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        }
        this.wolfHeadMain.rotateAngleZ = entityWolf.getInterestedAngle(n3) + entityWolf.getShakeAngle(n3, 0.0f);
        this.wolfMane.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.08f);
        this.wolfBody.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.16f);
        this.wolfTail.rotateAngleZ = entityWolf.getShakeAngle(n3, -0.2f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleX, final float n3, final float n4, final float n5, final Entity entity) {
        super.setRotationAngles(n, n2, rotateAngleX, n3, n4, n5, entity);
        this.wolfHeadMain.rotateAngleX = n4 / 57.295776f;
        this.wolfHeadMain.rotateAngleY = n3 / 57.295776f;
        this.wolfTail.rotateAngleX = rotateAngleX;
    }
    
    static {
        __OBFID = "CL_00000868";
    }
}
