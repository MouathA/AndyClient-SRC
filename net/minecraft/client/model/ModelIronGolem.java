package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class ModelIronGolem extends ModelBase
{
    public ModelRenderer ironGolemHead;
    public ModelRenderer ironGolemBody;
    public ModelRenderer ironGolemRightArm;
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer ironGolemLeftLeg;
    public ModelRenderer ironGolemRightLeg;
    private static final String __OBFID;
    
    public ModelIronGolem() {
        this(0.0f);
    }
    
    public ModelIronGolem(final float n) {
        this(n, -7.0f);
    }
    
    public ModelIronGolem(final float n, final float n2) {
        (this.ironGolemHead = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, 0.0f + n2, -2.0f);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8, 10, 8, n);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2, 4, 2, n);
        (this.ironGolemBody = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18, 12, 11, n);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9, 5, 6, n + 0.5f);
        (this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4, 30, 6, n);
        (this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0f, -2.5f, -3.0f, 4, 30, 6, n);
        (this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128)).setRotationPoint(-4.0f, 18.0f + n2, 0.0f);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, n);
        this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0f, 18.0f + n2, 0.0f);
        this.ironGolemRightLeg.addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, n);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.ironGolemHead.render(n6);
        this.ironGolemBody.render(n6);
        this.ironGolemLeftLeg.render(n6);
        this.ironGolemRightLeg.render(n6);
        this.ironGolemRightArm.render(n6);
        this.ironGolemLeftArm.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.ironGolemHead.rotateAngleY = n4 / 57.295776f;
        this.ironGolemHead.rotateAngleX = n5 / 57.295776f;
        this.ironGolemLeftLeg.rotateAngleX = -1.5f * this.func_78172_a(n, 13.0f) * n2;
        this.ironGolemRightLeg.rotateAngleX = 1.5f * this.func_78172_a(n, 13.0f) * n2;
        this.ironGolemLeftLeg.rotateAngleY = 0.0f;
        this.ironGolemRightLeg.rotateAngleY = 0.0f;
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityIronGolem entityIronGolem = (EntityIronGolem)entityLivingBase;
        final int attackTimer = entityIronGolem.getAttackTimer();
        if (attackTimer > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(attackTimer - n3, 10.0f);
            this.ironGolemLeftArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(attackTimer - n3, 10.0f);
        }
        else {
            final int holdRoseTick = entityIronGolem.getHoldRoseTick();
            if (holdRoseTick > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8f + 0.025f * this.func_78172_a((float)holdRoseTick, 70.0f);
                this.ironGolemLeftArm.rotateAngleX = 0.0f;
            }
            else {
                this.ironGolemRightArm.rotateAngleX = (-0.2f + 1.5f * this.func_78172_a(n, 13.0f)) * n2;
                this.ironGolemLeftArm.rotateAngleX = (-0.2f - 1.5f * this.func_78172_a(n, 13.0f)) * n2;
            }
        }
    }
    
    private float func_78172_a(final float n, final float n2) {
        return (Math.abs(n % n2 - n2 * 0.5f) - n2 * 0.25f) / (n2 * 0.25f);
    }
    
    static {
        __OBFID = "CL_00000863";
    }
}
