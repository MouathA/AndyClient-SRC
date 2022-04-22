package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSpider extends ModelBase
{
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;
    private static final String __OBFID;
    
    public ModelSpider() {
        final float n = 0.0f;
        (this.spiderHead = new ModelRenderer(this, 32, 4)).addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, n);
        this.spiderHead.setRotationPoint(0.0f, 15, -3.0f);
        (this.spiderNeck = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, n);
        this.spiderNeck.setRotationPoint(0.0f, 15, 0.0f);
        (this.spiderBody = new ModelRenderer(this, 0, 12)).addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, n);
        this.spiderBody.setRotationPoint(0.0f, 15, 9.0f);
        (this.spiderLeg1 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg1.setRotationPoint(-4.0f, 15, 2.0f);
        (this.spiderLeg2 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg2.setRotationPoint(4.0f, 15, 2.0f);
        (this.spiderLeg3 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg3.setRotationPoint(-4.0f, 15, 1.0f);
        (this.spiderLeg4 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg4.setRotationPoint(4.0f, 15, 1.0f);
        (this.spiderLeg5 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg5.setRotationPoint(-4.0f, 15, 0.0f);
        (this.spiderLeg6 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg6.setRotationPoint(4.0f, 15, 0.0f);
        (this.spiderLeg7 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg7.setRotationPoint(-4.0f, 15, -1.0f);
        (this.spiderLeg8 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, n);
        this.spiderLeg8.setRotationPoint(4.0f, 15, -1.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.spiderHead.render(n6);
        this.spiderNeck.render(n6);
        this.spiderBody.render(n6);
        this.spiderLeg1.render(n6);
        this.spiderLeg2.render(n6);
        this.spiderLeg3.render(n6);
        this.spiderLeg4.render(n6);
        this.spiderLeg5.render(n6);
        this.spiderLeg6.render(n6);
        this.spiderLeg7.render(n6);
        this.spiderLeg8.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.spiderHead.rotateAngleY = n4 / 57.295776f;
        this.spiderHead.rotateAngleX = n5 / 57.295776f;
        final float n7 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -n7;
        this.spiderLeg2.rotateAngleZ = n7;
        this.spiderLeg3.rotateAngleZ = -n7 * 0.74f;
        this.spiderLeg4.rotateAngleZ = n7 * 0.74f;
        this.spiderLeg5.rotateAngleZ = -n7 * 0.74f;
        this.spiderLeg6.rotateAngleZ = n7 * 0.74f;
        this.spiderLeg7.rotateAngleZ = -n7;
        this.spiderLeg8.rotateAngleZ = n7;
        final float n8 = -0.0f;
        final float n9 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = n9 * 2.0f + n8;
        this.spiderLeg2.rotateAngleY = -n9 * 2.0f - n8;
        this.spiderLeg3.rotateAngleY = n9 * 1.0f + n8;
        this.spiderLeg4.rotateAngleY = -n9 * 1.0f - n8;
        this.spiderLeg5.rotateAngleY = -n9 * 1.0f + n8;
        this.spiderLeg6.rotateAngleY = n9 * 1.0f - n8;
        this.spiderLeg7.rotateAngleY = -n9 * 2.0f + n8;
        this.spiderLeg8.rotateAngleY = n9 * 2.0f - n8;
        final float n10 = -(MathHelper.cos(n * 0.6662f * 2.0f + 0.0f) * 0.4f) * n2;
        final float n11 = -(MathHelper.cos(n * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * n2;
        final float n12 = -(MathHelper.cos(n * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * n2;
        final float n13 = -(MathHelper.cos(n * 0.6662f * 2.0f + 4.712389f) * 0.4f) * n2;
        final float n14 = Math.abs(MathHelper.sin(n * 0.6662f + 0.0f) * 0.4f) * n2;
        final float n15 = Math.abs(MathHelper.sin(n * 0.6662f + 3.1415927f) * 0.4f) * n2;
        final float n16 = Math.abs(MathHelper.sin(n * 0.6662f + 1.5707964f) * 0.4f) * n2;
        final float n17 = Math.abs(MathHelper.sin(n * 0.6662f + 4.712389f) * 0.4f) * n2;
        final ModelRenderer spiderLeg1 = this.spiderLeg1;
        spiderLeg1.rotateAngleY += n10;
        final ModelRenderer spiderLeg2 = this.spiderLeg2;
        spiderLeg2.rotateAngleY += -n10;
        final ModelRenderer spiderLeg3 = this.spiderLeg3;
        spiderLeg3.rotateAngleY += n11;
        final ModelRenderer spiderLeg4 = this.spiderLeg4;
        spiderLeg4.rotateAngleY += -n11;
        final ModelRenderer spiderLeg5 = this.spiderLeg5;
        spiderLeg5.rotateAngleY += n12;
        final ModelRenderer spiderLeg6 = this.spiderLeg6;
        spiderLeg6.rotateAngleY += -n12;
        final ModelRenderer spiderLeg7 = this.spiderLeg7;
        spiderLeg7.rotateAngleY += n13;
        final ModelRenderer spiderLeg8 = this.spiderLeg8;
        spiderLeg8.rotateAngleY += -n13;
        final ModelRenderer spiderLeg9 = this.spiderLeg1;
        spiderLeg9.rotateAngleZ += n14;
        final ModelRenderer spiderLeg10 = this.spiderLeg2;
        spiderLeg10.rotateAngleZ += -n14;
        final ModelRenderer spiderLeg11 = this.spiderLeg3;
        spiderLeg11.rotateAngleZ += n15;
        final ModelRenderer spiderLeg12 = this.spiderLeg4;
        spiderLeg12.rotateAngleZ += -n15;
        final ModelRenderer spiderLeg13 = this.spiderLeg5;
        spiderLeg13.rotateAngleZ += n16;
        final ModelRenderer spiderLeg14 = this.spiderLeg6;
        spiderLeg14.rotateAngleZ += -n16;
        final ModelRenderer spiderLeg15 = this.spiderLeg7;
        spiderLeg15.rotateAngleZ += n17;
        final ModelRenderer spiderLeg16 = this.spiderLeg8;
        spiderLeg16.rotateAngleZ += -n17;
    }
    
    static {
        __OBFID = "CL_00000860";
    }
}
