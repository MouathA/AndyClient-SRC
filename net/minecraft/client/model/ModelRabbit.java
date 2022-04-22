package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class ModelRabbit extends ModelBase
{
    ModelRenderer rabbitLeftFoot;
    ModelRenderer rabbitRightFoot;
    ModelRenderer rabbitLeftThigh;
    ModelRenderer rabbitRightThigh;
    ModelRenderer rabbitBody;
    ModelRenderer rabbitLeftArm;
    ModelRenderer rabbitRightArm;
    ModelRenderer rabbitHead;
    ModelRenderer rabbitRightEar;
    ModelRenderer rabbitLeftEar;
    ModelRenderer rabbitTail;
    ModelRenderer rabbitNose;
    private float field_178701_m;
    private float field_178699_n;
    private static final String __OBFID;
    
    public ModelRabbit() {
        this.field_178701_m = 0.0f;
        this.field_178699_n = 0.0f;
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        (this.rabbitLeftFoot = new ModelRenderer(this, 26, 24)).addBox(-1.0f, 5.5f, -3.7f, 2, 1, 7);
        this.rabbitLeftFoot.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftFoot.mirror = true;
        this.setRotationOffset(this.rabbitLeftFoot, 0.0f, 0.0f, 0.0f);
        (this.rabbitRightFoot = new ModelRenderer(this, 8, 24)).addBox(-1.0f, 5.5f, -3.7f, 2, 1, 7);
        this.rabbitRightFoot.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightFoot.mirror = true;
        this.setRotationOffset(this.rabbitRightFoot, 0.0f, 0.0f, 0.0f);
        (this.rabbitLeftThigh = new ModelRenderer(this, 30, 15)).addBox(-1.0f, 0.0f, 0.0f, 2, 4, 5);
        this.rabbitLeftThigh.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftThigh.mirror = true;
        this.setRotationOffset(this.rabbitLeftThigh, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitRightThigh = new ModelRenderer(this, 16, 15)).addBox(-1.0f, 0.0f, 0.0f, 2, 4, 5);
        this.rabbitRightThigh.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightThigh.mirror = true;
        this.setRotationOffset(this.rabbitRightThigh, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitBody = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -2.0f, -10.0f, 6, 5, 10);
        this.rabbitBody.setRotationPoint(0.0f, 19.0f, 8.0f);
        this.rabbitBody.mirror = true;
        this.setRotationOffset(this.rabbitBody, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitLeftArm = new ModelRenderer(this, 8, 15)).addBox(-1.0f, 0.0f, -1.0f, 2, 7, 2);
        this.rabbitLeftArm.setRotationPoint(3.0f, 17.0f, -1.0f);
        this.rabbitLeftArm.mirror = true;
        this.setRotationOffset(this.rabbitLeftArm, -0.17453292f, 0.0f, 0.0f);
        (this.rabbitRightArm = new ModelRenderer(this, 0, 15)).addBox(-1.0f, 0.0f, -1.0f, 2, 7, 2);
        this.rabbitRightArm.setRotationPoint(-3.0f, 17.0f, -1.0f);
        this.rabbitRightArm.mirror = true;
        this.setRotationOffset(this.rabbitRightArm, -0.17453292f, 0.0f, 0.0f);
        (this.rabbitHead = new ModelRenderer(this, 32, 0)).addBox(-2.5f, -4.0f, -5.0f, 5, 4, 5);
        this.rabbitHead.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitHead.mirror = true;
        this.setRotationOffset(this.rabbitHead, 0.0f, 0.0f, 0.0f);
        (this.rabbitRightEar = new ModelRenderer(this, 52, 0)).addBox(-2.5f, -9.0f, -1.0f, 2, 5, 1);
        this.rabbitRightEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitRightEar.mirror = true;
        this.setRotationOffset(this.rabbitRightEar, 0.0f, -0.2617994f, 0.0f);
        (this.rabbitLeftEar = new ModelRenderer(this, 58, 0)).addBox(0.5f, -9.0f, -1.0f, 2, 5, 1);
        this.rabbitLeftEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitLeftEar.mirror = true;
        this.setRotationOffset(this.rabbitLeftEar, 0.0f, 0.2617994f, 0.0f);
        (this.rabbitTail = new ModelRenderer(this, 52, 6)).addBox(-1.5f, -1.5f, 0.0f, 3, 3, 2);
        this.rabbitTail.setRotationPoint(0.0f, 20.0f, 7.0f);
        this.rabbitTail.mirror = true;
        this.setRotationOffset(this.rabbitTail, -0.3490659f, 0.0f, 0.0f);
        (this.rabbitNose = new ModelRenderer(this, 32, 9)).addBox(-0.5f, -2.5f, -5.5f, 1, 1, 1);
        this.rabbitNose.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitNose.mirror = true;
        this.setRotationOffset(this.rabbitNose, 0.0f, 0.0f, 0.0f);
    }
    
    private void setRotationOffset(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.rabbitHead.render(n6);
            this.rabbitLeftEar.render(n6);
            this.rabbitRightEar.render(n6);
            this.rabbitNose.render(n6);
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.rabbitLeftFoot.render(n6);
            this.rabbitRightFoot.render(n6);
            this.rabbitLeftThigh.render(n6);
            this.rabbitRightThigh.render(n6);
            this.rabbitBody.render(n6);
            this.rabbitLeftArm.render(n6);
            this.rabbitRightArm.render(n6);
            this.rabbitTail.render(n6);
        }
        else {
            this.rabbitLeftFoot.render(n6);
            this.rabbitRightFoot.render(n6);
            this.rabbitLeftThigh.render(n6);
            this.rabbitRightThigh.render(n6);
            this.rabbitBody.render(n6);
            this.rabbitLeftArm.render(n6);
            this.rabbitRightArm.render(n6);
            this.rabbitHead.render(n6);
            this.rabbitRightEar.render(n6);
            this.rabbitLeftEar.render(n6);
            this.rabbitTail.render(n6);
            this.rabbitNose.render(n6);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float n7 = n3 - entity.ticksExisted;
        final EntityRabbit entityRabbit = (EntityRabbit)entity;
        final ModelRenderer rabbitNose = this.rabbitNose;
        final ModelRenderer rabbitHead = this.rabbitHead;
        final ModelRenderer rabbitRightEar = this.rabbitRightEar;
        final ModelRenderer rabbitLeftEar = this.rabbitLeftEar;
        final float n8 = n5 * 0.017453292f;
        rabbitLeftEar.rotateAngleX = n8;
        rabbitRightEar.rotateAngleX = n8;
        rabbitHead.rotateAngleX = n8;
        rabbitNose.rotateAngleX = n8;
        final ModelRenderer rabbitNose2 = this.rabbitNose;
        final ModelRenderer rabbitHead2 = this.rabbitHead;
        final float n9 = n4 * 0.017453292f;
        rabbitHead2.rotateAngleY = n9;
        rabbitNose2.rotateAngleY = n9;
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - 0.2617994f;
        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + 0.2617994f;
        this.field_178701_m = MathHelper.sin(entityRabbit.func_175521_o(n7) * 3.1415927f);
        final ModelRenderer rabbitLeftThigh = this.rabbitLeftThigh;
        final ModelRenderer rabbitRightThigh = this.rabbitRightThigh;
        final float n10 = (this.field_178701_m * 50.0f - 21.0f) * 0.017453292f;
        rabbitRightThigh.rotateAngleX = n10;
        rabbitLeftThigh.rotateAngleX = n10;
        final ModelRenderer rabbitLeftFoot = this.rabbitLeftFoot;
        final ModelRenderer rabbitRightFoot = this.rabbitRightFoot;
        final float n11 = this.field_178701_m * 50.0f * 0.017453292f;
        rabbitRightFoot.rotateAngleX = n11;
        rabbitLeftFoot.rotateAngleX = n11;
        final ModelRenderer rabbitLeftArm = this.rabbitLeftArm;
        final ModelRenderer rabbitRightArm = this.rabbitRightArm;
        final float n12 = (this.field_178701_m * -40.0f - 11.0f) * 0.017453292f;
        rabbitRightArm.rotateAngleX = n12;
        rabbitLeftArm.rotateAngleX = n12;
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
    }
    
    static {
        __OBFID = "CL_00002625";
    }
}
