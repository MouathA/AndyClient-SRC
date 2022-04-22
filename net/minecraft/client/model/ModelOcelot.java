package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

public class ModelOcelot extends ModelBase
{
    ModelRenderer ocelotBackLeftLeg;
    ModelRenderer ocelotBackRightLeg;
    ModelRenderer ocelotFrontLeftLeg;
    ModelRenderer ocelotFrontRightLeg;
    ModelRenderer ocelotTail;
    ModelRenderer ocelotTail2;
    ModelRenderer ocelotHead;
    ModelRenderer ocelotBody;
    int field_78163_i;
    private static final String __OBFID;
    
    public ModelOcelot() {
        this.field_78163_i = 1;
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        (this.ocelotHead = new ModelRenderer(this, "head")).addBox("main", -2.5f, -2.0f, -3.0f, 5, 4, 5);
        this.ocelotHead.addBox("nose", -1.5f, 0.0f, -4.0f, 3, 2, 2);
        this.ocelotHead.addBox("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.addBox("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.setRotationPoint(0.0f, 15.0f, -9.0f);
        (this.ocelotBody = new ModelRenderer(this, 20, 0)).addBox(-2.0f, 3.0f, -8.0f, 4, 16, 6, 0.0f);
        this.ocelotBody.setRotationPoint(0.0f, 12.0f, -10.0f);
        (this.ocelotTail = new ModelRenderer(this, 0, 15)).addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail.rotateAngleX = 0.9f;
        this.ocelotTail.setRotationPoint(0.0f, 15.0f, 8.0f);
        (this.ocelotTail2 = new ModelRenderer(this, 4, 15)).addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail2.setRotationPoint(0.0f, 20.0f, 14.0f);
        (this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13)).addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackLeftLeg.setRotationPoint(1.1f, 18.0f, 5.0f);
        (this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13)).addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackRightLeg.setRotationPoint(-1.1f, 18.0f, 5.0f);
        (this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0)).addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontLeftLeg.setRotationPoint(1.2f, 13.8f, -5.0f);
        (this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0)).addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontRightLeg.setRotationPoint(-1.2f, 13.8f, -5.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.5f / n7, 1.5f / n7, 1.5f / n7);
            GlStateManager.translate(0.0f, 10.0f * n6, 4.0f * n6);
            this.ocelotHead.render(n6);
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.ocelotBody.render(n6);
            this.ocelotBackLeftLeg.render(n6);
            this.ocelotBackRightLeg.render(n6);
            this.ocelotFrontLeftLeg.render(n6);
            this.ocelotFrontRightLeg.render(n6);
            this.ocelotTail.render(n6);
            this.ocelotTail2.render(n6);
        }
        else {
            this.ocelotHead.render(n6);
            this.ocelotBody.render(n6);
            this.ocelotTail.render(n6);
            this.ocelotTail2.render(n6);
            this.ocelotBackLeftLeg.render(n6);
            this.ocelotBackRightLeg.render(n6);
            this.ocelotFrontLeftLeg.render(n6);
            this.ocelotFrontRightLeg.render(n6);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.ocelotHead.rotateAngleX = n5 / 57.295776f;
        this.ocelotHead.rotateAngleY = n4 / 57.295776f;
        if (this.field_78163_i != 3) {
            this.ocelotBody.rotateAngleX = 1.5707964f;
            if (this.field_78163_i == 2) {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 0.3f) * 1.0f * n2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f + 0.3f) * 1.0f * n2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotTail2.rotateAngleX = 1.7278761f + 0.31415927f * MathHelper.cos(n) * n2;
            }
            else {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.0f * n2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.0f * n2;
                if (this.field_78163_i == 1) {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.7853982f * MathHelper.cos(n) * n2;
                }
                else {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.47123894f * MathHelper.cos(n) * n2;
                }
            }
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityOcelot entityOcelot = (EntityOcelot)entityLivingBase;
        this.ocelotBody.rotationPointY = 12.0f;
        this.ocelotBody.rotationPointZ = -10.0f;
        this.ocelotHead.rotationPointY = 15.0f;
        this.ocelotHead.rotationPointZ = -9.0f;
        this.ocelotTail.rotationPointY = 15.0f;
        this.ocelotTail.rotationPointZ = 8.0f;
        this.ocelotTail2.rotationPointY = 20.0f;
        this.ocelotTail2.rotationPointZ = 14.0f;
        final ModelRenderer ocelotFrontLeftLeg = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg = this.ocelotFrontRightLeg;
        final float n4 = 13.8f;
        ocelotFrontRightLeg.rotationPointY = n4;
        ocelotFrontLeftLeg.rotationPointY = n4;
        final ModelRenderer ocelotFrontLeftLeg2 = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg2 = this.ocelotFrontRightLeg;
        final float n5 = -5.0f;
        ocelotFrontRightLeg2.rotationPointZ = n5;
        ocelotFrontLeftLeg2.rotationPointZ = n5;
        final ModelRenderer ocelotBackLeftLeg = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg = this.ocelotBackRightLeg;
        final float n6 = 18.0f;
        ocelotBackRightLeg.rotationPointY = n6;
        ocelotBackLeftLeg.rotationPointY = n6;
        final ModelRenderer ocelotBackLeftLeg2 = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg2 = this.ocelotBackRightLeg;
        final float n7 = 5.0f;
        ocelotBackRightLeg2.rotationPointZ = n7;
        ocelotBackLeftLeg2.rotationPointZ = n7;
        this.ocelotTail.rotateAngleX = 0.9f;
        if (entityOcelot.isSneaking()) {
            final ModelRenderer ocelotBody = this.ocelotBody;
            ++ocelotBody.rotationPointY;
            final ModelRenderer ocelotHead = this.ocelotHead;
            ocelotHead.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail = this.ocelotTail;
            ++ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail2 = this.ocelotTail2;
            ocelotTail2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotTail3 = this.ocelotTail2;
            ocelotTail3.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 0;
        }
        else if (entityOcelot.isSprinting()) {
            this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail4 = this.ocelotTail2;
            ocelotTail4.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 2;
        }
        else if (entityOcelot.isSitting()) {
            this.ocelotBody.rotateAngleX = 0.7853982f;
            final ModelRenderer ocelotBody2 = this.ocelotBody;
            ocelotBody2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotBody3 = this.ocelotBody;
            ocelotBody3.rotationPointZ += 5.0f;
            final ModelRenderer ocelotHead2 = this.ocelotHead;
            ocelotHead2.rotationPointY -= 3.3f;
            final ModelRenderer ocelotHead3 = this.ocelotHead;
            ++ocelotHead3.rotationPointZ;
            final ModelRenderer ocelotTail5 = this.ocelotTail;
            ocelotTail5.rotationPointY += 8.0f;
            final ModelRenderer ocelotTail6 = this.ocelotTail;
            ocelotTail6.rotationPointZ -= 2.0f;
            final ModelRenderer ocelotTail7 = this.ocelotTail2;
            ocelotTail7.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail8 = this.ocelotTail2;
            ocelotTail8.rotationPointZ -= 0.8f;
            this.ocelotTail.rotateAngleX = 1.7278761f;
            this.ocelotTail2.rotateAngleX = 2.670354f;
            final ModelRenderer ocelotFrontLeftLeg3 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg3 = this.ocelotFrontRightLeg;
            final float n8 = -0.15707964f;
            ocelotFrontRightLeg3.rotateAngleX = n8;
            ocelotFrontLeftLeg3.rotateAngleX = n8;
            final ModelRenderer ocelotFrontLeftLeg4 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg4 = this.ocelotFrontRightLeg;
            final float n9 = 15.8f;
            ocelotFrontRightLeg4.rotationPointY = n9;
            ocelotFrontLeftLeg4.rotationPointY = n9;
            final ModelRenderer ocelotFrontLeftLeg5 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg5 = this.ocelotFrontRightLeg;
            final float n10 = -7.0f;
            ocelotFrontRightLeg5.rotationPointZ = n10;
            ocelotFrontLeftLeg5.rotationPointZ = n10;
            final ModelRenderer ocelotBackLeftLeg3 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg3 = this.ocelotBackRightLeg;
            final float n11 = -1.5707964f;
            ocelotBackRightLeg3.rotateAngleX = n11;
            ocelotBackLeftLeg3.rotateAngleX = n11;
            final ModelRenderer ocelotBackLeftLeg4 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg4 = this.ocelotBackRightLeg;
            final float n12 = 21.0f;
            ocelotBackRightLeg4.rotationPointY = n12;
            ocelotBackLeftLeg4.rotationPointY = n12;
            final ModelRenderer ocelotBackLeftLeg5 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg5 = this.ocelotBackRightLeg;
            final float n13 = 1.0f;
            ocelotBackRightLeg5.rotationPointZ = n13;
            ocelotBackLeftLeg5.rotationPointZ = n13;
            this.field_78163_i = 3;
        }
        else {
            this.field_78163_i = 1;
        }
    }
    
    static {
        __OBFID = "CL_00000848";
    }
}
