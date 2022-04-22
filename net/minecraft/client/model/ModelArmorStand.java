package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;

public class ModelArmorStand extends ModelArmorStandArmor
{
    public ModelRenderer standRightSide;
    public ModelRenderer standLeftSide;
    public ModelRenderer standWaist;
    public ModelRenderer standBase;
    private static final String __OBFID;
    
    public ModelArmorStand() {
        this(0.0f);
    }
    
    public ModelArmorStand(final float n) {
        super(n, 64, 64);
        (this.bipedHead = new ModelRenderer(this, 0, 0)).addBox(-1.0f, -7.0f, -1.0f, 2, 7, 2, n);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRenderer(this, 0, 26)).addBox(-6.0f, 0.0f, -1.5f, 12, 3, 3, n);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 24, 0)).addBox(-2.0f, -2.0f, -1.0f, 2, 12, 2, n);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 32, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(0.0f, -2.0f, -1.0f, 2, 12, 2, n);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 8, 0)).addBox(-1.0f, 0.0f, -1.0f, 2, 11, 2, n);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 40, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 11, 2, n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.standRightSide = new ModelRenderer(this, 16, 0)).addBox(-3.0f, 3.0f, -1.0f, 2, 7, 2, n);
        this.standRightSide.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.standRightSide.showModel = true;
        (this.standLeftSide = new ModelRenderer(this, 48, 16)).addBox(1.0f, 3.0f, -1.0f, 2, 7, 2, n);
        this.standLeftSide.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.standWaist = new ModelRenderer(this, 0, 48)).addBox(-4.0f, 10.0f, -1.0f, 8, 2, 2, n);
        this.standWaist.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.standBase = new ModelRenderer(this, 0, 32)).addBox(-6.0f, 11.0f, -6.0f, 12, 1, 12, n);
        this.standBase.setRotationPoint(0.0f, 12.0f, 0.0f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
            this.bipedLeftArm.showModel = entityArmorStand.getShowArms();
            this.bipedRightArm.showModel = entityArmorStand.getShowArms();
            this.standBase.showModel = !entityArmorStand.hasNoBasePlate();
            this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
            this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
            this.standRightSide.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().func_179415_b();
            this.standRightSide.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().func_179416_c();
            this.standRightSide.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().func_179413_d();
            this.standLeftSide.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().func_179415_b();
            this.standLeftSide.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().func_179416_c();
            this.standLeftSide.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().func_179413_d();
            this.standWaist.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().func_179415_b();
            this.standWaist.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().func_179416_c();
            this.standWaist.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().func_179413_d();
            final float n7 = (entityArmorStand.getLeftLegRotation().func_179415_b() + entityArmorStand.getRightLegRotation().func_179415_b()) / 2.0f;
            final float n8 = (entityArmorStand.getLeftLegRotation().func_179416_c() + entityArmorStand.getRightLegRotation().func_179416_c()) / 2.0f;
            final float n9 = (entityArmorStand.getLeftLegRotation().func_179413_d() + entityArmorStand.getRightLegRotation().func_179413_d()) / 2.0f;
            this.standBase.rotateAngleX = 0.0f;
            this.standBase.rotateAngleY = 0.017453292f * -entity.rotationYaw;
            this.standBase.rotateAngleZ = 0.0f;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.standRightSide.render(n6);
            this.standLeftSide.render(n6);
            this.standWaist.render(n6);
            this.standBase.render(n6);
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.standRightSide.render(n6);
            this.standLeftSide.render(n6);
            this.standWaist.render(n6);
            this.standBase.render(n6);
        }
    }
    
    @Override
    public void postRenderHiddenArm(final float n) {
        final boolean showModel = this.bipedRightArm.showModel;
        this.bipedRightArm.showModel = true;
        super.postRenderHiddenArm(n);
        this.bipedRightArm.showModel = showModel;
    }
    
    static {
        __OBFID = "CL_00002631";
    }
}
