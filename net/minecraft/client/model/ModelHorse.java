package net.minecraft.client.model;

import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelHorse extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer field_178711_b;
    private ModelRenderer field_178712_c;
    private ModelRenderer horseLeftEar;
    private ModelRenderer horseRightEar;
    private ModelRenderer muleLeftEar;
    private ModelRenderer muleRightEar;
    private ModelRenderer neck;
    private ModelRenderer horseFaceRopes;
    private ModelRenderer mane;
    private ModelRenderer body;
    private ModelRenderer tailBase;
    private ModelRenderer tailMiddle;
    private ModelRenderer tailTip;
    private ModelRenderer backLeftLeg;
    private ModelRenderer backLeftShin;
    private ModelRenderer backLeftHoof;
    private ModelRenderer backRightLeg;
    private ModelRenderer backRightShin;
    private ModelRenderer backRightHoof;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer frontLeftShin;
    private ModelRenderer frontLeftHoof;
    private ModelRenderer frontRightLeg;
    private ModelRenderer frontRightShin;
    private ModelRenderer frontRightHoof;
    private ModelRenderer muleLeftChest;
    private ModelRenderer muleRightChest;
    private ModelRenderer horseSaddleBottom;
    private ModelRenderer horseSaddleFront;
    private ModelRenderer horseSaddleBack;
    private ModelRenderer horseLeftSaddleRope;
    private ModelRenderer horseLeftSaddleMetal;
    private ModelRenderer horseRightSaddleRope;
    private ModelRenderer horseRightSaddleMetal;
    private ModelRenderer horseLeftFaceMetal;
    private ModelRenderer horseRightFaceMetal;
    private ModelRenderer horseLeftRein;
    private ModelRenderer horseRightRein;
    private static final String __OBFID;
    
    public ModelHorse() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        (this.body = new ModelRenderer(this, 0, 34)).addBox(-5.0f, -8.0f, -19.0f, 10, 10, 24);
        this.body.setRotationPoint(0.0f, 11.0f, 9.0f);
        (this.tailBase = new ModelRenderer(this, 44, 0)).addBox(-1.0f, -1.0f, 0.0f, 2, 2, 3);
        this.tailBase.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailBase, -1.134464f, 0.0f, 0.0f);
        (this.tailMiddle = new ModelRenderer(this, 38, 7)).addBox(-1.5f, -2.0f, 3.0f, 3, 4, 7);
        this.tailMiddle.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailMiddle, -1.134464f, 0.0f, 0.0f);
        (this.tailTip = new ModelRenderer(this, 24, 3)).addBox(-1.5f, -4.5f, 9.0f, 3, 4, 7);
        this.tailTip.setRotationPoint(0.0f, 3.0f, 14.0f);
        this.setBoxRotation(this.tailTip, -1.40215f, 0.0f, 0.0f);
        (this.backLeftLeg = new ModelRenderer(this, 78, 29)).addBox(-2.5f, -2.0f, -2.5f, 4, 9, 5);
        this.backLeftLeg.setRotationPoint(4.0f, 9.0f, 11.0f);
        (this.backLeftShin = new ModelRenderer(this, 78, 43)).addBox(-2.0f, 0.0f, -1.5f, 3, 5, 3);
        this.backLeftShin.setRotationPoint(4.0f, 16.0f, 11.0f);
        (this.backLeftHoof = new ModelRenderer(this, 78, 51)).addBox(-2.5f, 5.1f, -2.0f, 4, 3, 4);
        this.backLeftHoof.setRotationPoint(4.0f, 16.0f, 11.0f);
        (this.backRightLeg = new ModelRenderer(this, 96, 29)).addBox(-1.5f, -2.0f, -2.5f, 4, 9, 5);
        this.backRightLeg.setRotationPoint(-4.0f, 9.0f, 11.0f);
        (this.backRightShin = new ModelRenderer(this, 96, 43)).addBox(-1.0f, 0.0f, -1.5f, 3, 5, 3);
        this.backRightShin.setRotationPoint(-4.0f, 16.0f, 11.0f);
        (this.backRightHoof = new ModelRenderer(this, 96, 51)).addBox(-1.5f, 5.1f, -2.0f, 4, 3, 4);
        this.backRightHoof.setRotationPoint(-4.0f, 16.0f, 11.0f);
        (this.frontLeftLeg = new ModelRenderer(this, 44, 29)).addBox(-1.9f, -1.0f, -2.1f, 3, 8, 4);
        this.frontLeftLeg.setRotationPoint(4.0f, 9.0f, -8.0f);
        (this.frontLeftShin = new ModelRenderer(this, 44, 41)).addBox(-1.9f, 0.0f, -1.6f, 3, 5, 3);
        this.frontLeftShin.setRotationPoint(4.0f, 16.0f, -8.0f);
        (this.frontLeftHoof = new ModelRenderer(this, 44, 51)).addBox(-2.4f, 5.1f, -2.1f, 4, 3, 4);
        this.frontLeftHoof.setRotationPoint(4.0f, 16.0f, -8.0f);
        (this.frontRightLeg = new ModelRenderer(this, 60, 29)).addBox(-1.1f, -1.0f, -2.1f, 3, 8, 4);
        this.frontRightLeg.setRotationPoint(-4.0f, 9.0f, -8.0f);
        (this.frontRightShin = new ModelRenderer(this, 60, 41)).addBox(-1.1f, 0.0f, -1.6f, 3, 5, 3);
        this.frontRightShin.setRotationPoint(-4.0f, 16.0f, -8.0f);
        (this.frontRightHoof = new ModelRenderer(this, 60, 51)).addBox(-1.6f, 5.1f, -2.1f, 4, 3, 4);
        this.frontRightHoof.setRotationPoint(-4.0f, 16.0f, -8.0f);
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-2.5f, -10.0f, -1.5f, 5, 5, 7);
        this.head.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.head, 0.5235988f, 0.0f, 0.0f);
        (this.field_178711_b = new ModelRenderer(this, 24, 18)).addBox(-2.0f, -10.0f, -7.0f, 4, 3, 6);
        this.field_178711_b.setRotationPoint(0.0f, 3.95f, -10.0f);
        this.setBoxRotation(this.field_178711_b, 0.5235988f, 0.0f, 0.0f);
        (this.field_178712_c = new ModelRenderer(this, 24, 27)).addBox(-2.0f, -7.0f, -6.5f, 4, 2, 5);
        this.field_178712_c.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.field_178712_c, 0.5235988f, 0.0f, 0.0f);
        this.head.addChild(this.field_178711_b);
        this.head.addChild(this.field_178712_c);
        (this.horseLeftEar = new ModelRenderer(this, 0, 0)).addBox(0.45f, -12.0f, 4.0f, 2, 3, 1);
        this.horseLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftEar, 0.5235988f, 0.0f, 0.0f);
        (this.horseRightEar = new ModelRenderer(this, 0, 0)).addBox(-2.45f, -12.0f, 4.0f, 2, 3, 1);
        this.horseRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightEar, 0.5235988f, 0.0f, 0.0f);
        (this.muleLeftEar = new ModelRenderer(this, 0, 12)).addBox(-2.0f, -16.0f, 4.0f, 2, 7, 1);
        this.muleLeftEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleLeftEar, 0.5235988f, 0.0f, 0.2617994f);
        (this.muleRightEar = new ModelRenderer(this, 0, 12)).addBox(0.0f, -16.0f, 4.0f, 2, 7, 1);
        this.muleRightEar.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.muleRightEar, 0.5235988f, 0.0f, -0.2617994f);
        (this.neck = new ModelRenderer(this, 0, 12)).addBox(-2.05f, -9.8f, -2.0f, 4, 14, 8);
        this.neck.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.neck, 0.5235988f, 0.0f, 0.0f);
        (this.muleLeftChest = new ModelRenderer(this, 0, 34)).addBox(-3.0f, 0.0f, 0.0f, 8, 8, 3);
        this.muleLeftChest.setRotationPoint(-7.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleLeftChest, 0.0f, 1.5707964f, 0.0f);
        (this.muleRightChest = new ModelRenderer(this, 0, 47)).addBox(-3.0f, 0.0f, 0.0f, 8, 8, 3);
        this.muleRightChest.setRotationPoint(4.5f, 3.0f, 10.0f);
        this.setBoxRotation(this.muleRightChest, 0.0f, 1.5707964f, 0.0f);
        (this.horseSaddleBottom = new ModelRenderer(this, 80, 0)).addBox(-5.0f, 0.0f, -3.0f, 10, 1, 8);
        this.horseSaddleBottom.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseSaddleFront = new ModelRenderer(this, 106, 9)).addBox(-1.5f, -1.0f, -3.0f, 3, 1, 2);
        this.horseSaddleFront.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseSaddleBack = new ModelRenderer(this, 80, 9)).addBox(-4.0f, -1.0f, 3.0f, 8, 1, 2);
        this.horseSaddleBack.setRotationPoint(0.0f, 2.0f, 2.0f);
        (this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0)).addBox(-0.5f, 6.0f, -1.0f, 1, 2, 2);
        this.horseLeftSaddleMetal.setRotationPoint(5.0f, 3.0f, 2.0f);
        (this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 6, 1);
        this.horseLeftSaddleRope.setRotationPoint(5.0f, 3.0f, 2.0f);
        (this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4)).addBox(-0.5f, 6.0f, -1.0f, 1, 2, 2);
        this.horseRightSaddleMetal.setRotationPoint(-5.0f, 3.0f, 2.0f);
        (this.horseRightSaddleRope = new ModelRenderer(this, 80, 0)).addBox(-0.5f, 0.0f, -0.5f, 1, 6, 1);
        this.horseRightSaddleRope.setRotationPoint(-5.0f, 3.0f, 2.0f);
        (this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13)).addBox(1.5f, -8.0f, -4.0f, 1, 2, 2);
        this.horseLeftFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseLeftFaceMetal, 0.5235988f, 0.0f, 0.0f);
        (this.horseRightFaceMetal = new ModelRenderer(this, 74, 13)).addBox(-2.5f, -8.0f, -4.0f, 1, 2, 2);
        this.horseRightFaceMetal.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseRightFaceMetal, 0.5235988f, 0.0f, 0.0f);
        (this.horseLeftRein = new ModelRenderer(this, 44, 10)).addBox(2.6f, -6.0f, -6.0f, 0, 3, 16);
        this.horseLeftRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        (this.horseRightRein = new ModelRenderer(this, 44, 5)).addBox(-2.6f, -6.0f, -6.0f, 0, 3, 16);
        this.horseRightRein.setRotationPoint(0.0f, 4.0f, -10.0f);
        (this.mane = new ModelRenderer(this, 58, 0)).addBox(-1.0f, -11.5f, 5.0f, 2, 16, 4);
        this.mane.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.mane, 0.5235988f, 0.0f, 0.0f);
        (this.horseFaceRopes = new ModelRenderer(this, 80, 12)).addBox(-2.5f, -10.1f, -7.0f, 5, 5, 12, 0.2f);
        this.horseFaceRopes.setRotationPoint(0.0f, 4.0f, -10.0f);
        this.setBoxRotation(this.horseFaceRopes, 0.5235988f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final EntityHorse entityHorse = (EntityHorse)entity;
        final int horseType = entityHorse.getHorseType();
        final float grassEatingAmount = entityHorse.getGrassEatingAmount(0.0f);
        final boolean adultHorse = entityHorse.isAdultHorse();
        final boolean b = adultHorse && entityHorse.isHorseSaddled();
        final boolean b2 = adultHorse && entityHorse.isChested();
        final boolean b3 = horseType == 1 || horseType == 2;
        final float horseSize = entityHorse.getHorseSize();
        final boolean b4 = entityHorse.riddenByEntity != null;
        if (b) {
            this.horseFaceRopes.render(n6);
            this.horseSaddleBottom.render(n6);
            this.horseSaddleFront.render(n6);
            this.horseSaddleBack.render(n6);
            this.horseLeftSaddleRope.render(n6);
            this.horseLeftSaddleMetal.render(n6);
            this.horseRightSaddleRope.render(n6);
            this.horseRightSaddleMetal.render(n6);
            this.horseLeftFaceMetal.render(n6);
            this.horseRightFaceMetal.render(n6);
            if (b4) {
                this.horseLeftRein.render(n6);
                this.horseRightRein.render(n6);
            }
        }
        if (!adultHorse) {
            GlStateManager.scale(horseSize, 0.5f + horseSize * 0.5f, horseSize);
            GlStateManager.translate(0.0f, 0.95f * (1.0f - horseSize), 0.0f);
        }
        this.backLeftLeg.render(n6);
        this.backLeftShin.render(n6);
        this.backLeftHoof.render(n6);
        this.backRightLeg.render(n6);
        this.backRightShin.render(n6);
        this.backRightHoof.render(n6);
        this.frontLeftLeg.render(n6);
        this.frontLeftShin.render(n6);
        this.frontLeftHoof.render(n6);
        this.frontRightLeg.render(n6);
        this.frontRightShin.render(n6);
        this.frontRightHoof.render(n6);
        if (!adultHorse) {
            GlStateManager.scale(horseSize, horseSize, horseSize);
            GlStateManager.translate(0.0f, 1.35f * (1.0f - horseSize), 0.0f);
        }
        this.body.render(n6);
        this.tailBase.render(n6);
        this.tailMiddle.render(n6);
        this.tailTip.render(n6);
        this.neck.render(n6);
        this.mane.render(n6);
        if (!adultHorse) {
            final float n7 = 0.5f + horseSize * horseSize * 0.5f;
            GlStateManager.scale(n7, n7, n7);
            if (grassEatingAmount <= 0.0f) {
                GlStateManager.translate(0.0f, 1.35f * (1.0f - horseSize), 0.0f);
            }
            else {
                GlStateManager.translate(0.0f, 0.9f * (1.0f - horseSize) * grassEatingAmount + 1.35f * (1.0f - horseSize) * (1.0f - grassEatingAmount), 0.15f * (1.0f - horseSize) * grassEatingAmount);
            }
        }
        if (b3) {
            this.muleLeftEar.render(n6);
            this.muleRightEar.render(n6);
        }
        else {
            this.horseLeftEar.render(n6);
            this.horseRightEar.render(n6);
        }
        this.head.render(n6);
        if (b2) {
            this.muleLeftChest.render(n6);
            this.muleRightChest.render(n6);
        }
    }
    
    private void setBoxRotation(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
    
    private float updateHorseRotation(final float n, final float n2, final float n3) {
        float n4;
        for (n4 = n2 - n; n4 < -180.0f; n4 += 360.0f) {}
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
        final float updateHorseRotation = this.updateHorseRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, n3);
        final float updateHorseRotation2 = this.updateHorseRotation(entityLivingBase.prevRotationYawHead, entityLivingBase.rotationYawHead, n3);
        final float n4 = entityLivingBase.prevRotationPitch + (entityLivingBase.rotationPitch - entityLivingBase.prevRotationPitch) * n3;
        float n5 = updateHorseRotation2 - updateHorseRotation;
        float n6 = n4 / 57.295776f;
        if (n5 > 20.0f) {
            n5 = 20.0f;
        }
        if (n5 < -20.0f) {
            n5 = -20.0f;
        }
        if (n2 > 0.2f) {
            n6 += MathHelper.cos(n * 0.4f) * 0.15f * n2;
        }
        final EntityHorse entityHorse = (EntityHorse)entityLivingBase;
        final float grassEatingAmount = entityHorse.getGrassEatingAmount(n3);
        final float rearingAmount = entityHorse.getRearingAmount(n3);
        final float n7 = 1.0f - rearingAmount;
        final float func_110201_q = entityHorse.func_110201_q(n3);
        final boolean b = entityHorse.field_110278_bp != 0;
        final boolean horseSaddled = entityHorse.isHorseSaddled();
        final boolean b2 = entityHorse.riddenByEntity != null;
        final float n8 = entityLivingBase.ticksExisted + n3;
        final float cos = MathHelper.cos(n * 0.6662f + 3.1415927f);
        final float n9 = cos * 0.8f * n2;
        this.head.rotationPointY = 4.0f;
        this.head.rotationPointZ = -10.0f;
        this.tailBase.rotationPointY = 3.0f;
        this.tailMiddle.rotationPointZ = 14.0f;
        this.muleRightChest.rotationPointY = 3.0f;
        this.muleRightChest.rotationPointZ = 10.0f;
        this.body.rotateAngleX = 0.0f;
        this.head.rotateAngleX = 0.5235988f + n6;
        this.head.rotateAngleY = n5 / 57.295776f;
        this.head.rotateAngleX = rearingAmount * (0.2617994f + n6) + grassEatingAmount * 2.18166f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotateAngleX;
        this.head.rotateAngleY = rearingAmount * n5 / 57.295776f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotateAngleY;
        this.head.rotationPointY = rearingAmount * -6.0f + grassEatingAmount * 11.0f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotationPointY;
        this.head.rotationPointZ = rearingAmount * -1.0f + grassEatingAmount * -10.0f + (1.0f - Math.max(rearingAmount, grassEatingAmount)) * this.head.rotationPointZ;
        this.tailBase.rotationPointY = rearingAmount * 9.0f + n7 * this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = rearingAmount * 18.0f + n7 * this.tailMiddle.rotationPointZ;
        this.muleRightChest.rotationPointY = rearingAmount * 5.5f + n7 * this.muleRightChest.rotationPointY;
        this.muleRightChest.rotationPointZ = rearingAmount * 15.0f + n7 * this.muleRightChest.rotationPointZ;
        this.body.rotateAngleX = rearingAmount * -45.0f / 57.295776f + n7 * this.body.rotateAngleX;
        this.horseLeftEar.rotationPointY = this.head.rotationPointY;
        this.horseRightEar.rotationPointY = this.head.rotationPointY;
        this.muleLeftEar.rotationPointY = this.head.rotationPointY;
        this.muleRightEar.rotationPointY = this.head.rotationPointY;
        this.neck.rotationPointY = this.head.rotationPointY;
        this.field_178711_b.rotationPointY = 0.02f;
        this.field_178712_c.rotationPointY = 0.0f;
        this.mane.rotationPointY = this.head.rotationPointY;
        this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
        this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
        this.neck.rotationPointZ = this.head.rotationPointZ;
        this.field_178711_b.rotationPointZ = 0.02f - func_110201_q * 1.0f;
        this.field_178712_c.rotationPointZ = 0.0f + func_110201_q * 1.0f;
        this.mane.rotationPointZ = this.head.rotationPointZ;
        this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
        this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.field_178711_b.rotateAngleX = 0.0f - 0.09424778f * func_110201_q;
        this.field_178712_c.rotateAngleX = 0.0f + 0.15707964f * func_110201_q;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.field_178711_b.rotateAngleY = 0.0f;
        this.field_178712_c.rotateAngleY = 0.0f;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftChest.rotateAngleX = n9 / 5.0f;
        this.muleRightChest.rotateAngleX = -n9 / 5.0f;
        final float n10 = 0.2617994f * rearingAmount;
        final float cos2 = MathHelper.cos(n8 * 0.6f + 3.1415927f);
        this.frontLeftLeg.rotationPointY = -2.0f * rearingAmount + 9.0f * n7;
        this.frontLeftLeg.rotationPointZ = -2.0f * rearingAmount + -8.0f * n7;
        this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
        this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
        this.backLeftShin.rotationPointY = this.backLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + n10 + n7 * -cos * 0.5f * n2) * 7.0f;
        this.backLeftShin.rotationPointZ = this.backLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + n10 + n7 * -cos * 0.5f * n2) * 7.0f;
        this.backRightShin.rotationPointY = this.backRightLeg.rotationPointY + MathHelper.sin(1.5707964f + n10 + n7 * cos * 0.5f * n2) * 7.0f;
        this.backRightShin.rotationPointZ = this.backRightLeg.rotationPointZ + MathHelper.cos(4.712389f + n10 + n7 * cos * 0.5f * n2) * 7.0f;
        final float rotateAngleX = (-1.0471976f + cos2) * rearingAmount + n9 * n7;
        final float rotateAngleX2 = (-1.0471976f + -cos2) * rearingAmount + -n9 * n7;
        this.frontLeftShin.rotationPointY = this.frontLeftLeg.rotationPointY + MathHelper.sin(1.5707964f + rotateAngleX) * 7.0f;
        this.frontLeftShin.rotationPointZ = this.frontLeftLeg.rotationPointZ + MathHelper.cos(4.712389f + rotateAngleX) * 7.0f;
        this.frontRightShin.rotationPointY = this.frontRightLeg.rotationPointY + MathHelper.sin(1.5707964f + rotateAngleX2) * 7.0f;
        this.frontRightShin.rotationPointZ = this.frontRightLeg.rotationPointZ + MathHelper.cos(4.712389f + rotateAngleX2) * 7.0f;
        this.backLeftLeg.rotateAngleX = n10 + -cos * 0.5f * n2 * n7;
        this.backLeftShin.rotateAngleX = -0.08726646f * rearingAmount + (-cos * 0.5f * n2 - Math.max(0.0f, cos * 0.5f * n2)) * n7;
        this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
        this.backRightLeg.rotateAngleX = n10 + cos * 0.5f * n2 * n7;
        this.backRightShin.rotateAngleX = -0.08726646f * rearingAmount + (cos * 0.5f * n2 - Math.max(0.0f, -cos * 0.5f * n2)) * n7;
        this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
        this.frontLeftLeg.rotateAngleX = rotateAngleX;
        this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f + cos2 * 0.2f)) * rearingAmount + (n9 + Math.max(0.0f, cos * 0.5f * n2)) * n7;
        this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
        this.frontRightLeg.rotateAngleX = rotateAngleX2;
        this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927f * Math.max(0.0f, 0.2f - cos2 * 0.2f)) * rearingAmount + (-n9 + Math.max(0.0f, -cos * 0.5f * n2)) * n7;
        this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
        this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
        this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
        this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
        this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
        this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
        this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
        this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
        this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
        if (horseSaddled) {
            this.horseSaddleBottom.rotationPointY = rearingAmount * 0.5f + n7 * 2.0f;
            this.horseSaddleBottom.rotationPointZ = rearingAmount * 11.0f + n7 * 2.0f;
            this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
            this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
            this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
            this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
            this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
            this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
            this.horseLeftRein.rotationPointY = this.head.rotationPointY;
            this.horseRightRein.rotationPointY = this.head.rotationPointY;
            this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
            this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
            this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
            this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
            this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
            this.horseLeftRein.rotateAngleX = n6;
            this.horseRightRein.rotateAngleX = n6;
            this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
            this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
            this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
            this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
            this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
            if (b2) {
                this.horseLeftSaddleRope.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseRightSaddleRope.rotateAngleX = -1.0471976f;
                this.horseRightSaddleMetal.rotateAngleX = -1.0471976f;
                this.horseLeftSaddleRope.rotateAngleZ = 0.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = 0.0f;
                this.horseRightSaddleRope.rotateAngleZ = 0.0f;
                this.horseRightSaddleMetal.rotateAngleZ = 0.0f;
            }
            else {
                this.horseLeftSaddleRope.rotateAngleX = n9 / 3.0f;
                this.horseLeftSaddleMetal.rotateAngleX = n9 / 3.0f;
                this.horseRightSaddleRope.rotateAngleX = n9 / 3.0f;
                this.horseRightSaddleMetal.rotateAngleX = n9 / 3.0f;
                this.horseLeftSaddleRope.rotateAngleZ = n9 / 5.0f;
                this.horseLeftSaddleMetal.rotateAngleZ = n9 / 5.0f;
                this.horseRightSaddleRope.rotateAngleZ = -n9 / 5.0f;
                this.horseRightSaddleMetal.rotateAngleZ = -n9 / 5.0f;
            }
        }
        float n11 = -1.3089f + n2 * 1.5f;
        if (n11 > 0.0f) {
            n11 = 0.0f;
        }
        if (b) {
            this.tailBase.rotateAngleY = MathHelper.cos(n8 * 0.7f);
            n11 = 0.0f;
        }
        else {
            this.tailBase.rotateAngleY = 0.0f;
        }
        this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
        this.tailTip.rotationPointY = this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailBase.rotateAngleX = n11;
        this.tailMiddle.rotateAngleX = n11;
        this.tailTip.rotateAngleX = -0.2618f + n11;
    }
    
    static {
        __OBFID = "CL_00000846";
    }
}
