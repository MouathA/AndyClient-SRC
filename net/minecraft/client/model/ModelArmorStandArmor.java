package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class ModelArmorStandArmor extends ModelBiped
{
    private static final String __OBFID;
    
    public ModelArmorStandArmor() {
        this(0.0f);
    }
    
    public ModelArmorStandArmor(final float n) {
        this(n, 64, 32);
    }
    
    protected ModelArmorStandArmor(final float n, final int n2, final int n3) {
        super(n, 0.0f, n2, n3);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
            this.bipedHead.rotateAngleX = 0.017453292f * entityArmorStand.getHeadRotation().func_179415_b();
            this.bipedHead.rotateAngleY = 0.017453292f * entityArmorStand.getHeadRotation().func_179416_c();
            this.bipedHead.rotateAngleZ = 0.017453292f * entityArmorStand.getHeadRotation().func_179413_d();
            this.bipedHead.setRotationPoint(0.0f, 1.0f, 0.0f);
            this.bipedBody.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().func_179415_b();
            this.bipedBody.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().func_179416_c();
            this.bipedBody.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().func_179413_d();
            this.bipedLeftArm.rotateAngleX = 0.017453292f * entityArmorStand.getLeftArmRotation().func_179415_b();
            this.bipedLeftArm.rotateAngleY = 0.017453292f * entityArmorStand.getLeftArmRotation().func_179416_c();
            this.bipedLeftArm.rotateAngleZ = 0.017453292f * entityArmorStand.getLeftArmRotation().func_179413_d();
            this.bipedRightArm.rotateAngleX = 0.017453292f * entityArmorStand.getRightArmRotation().func_179415_b();
            this.bipedRightArm.rotateAngleY = 0.017453292f * entityArmorStand.getRightArmRotation().func_179416_c();
            this.bipedRightArm.rotateAngleZ = 0.017453292f * entityArmorStand.getRightArmRotation().func_179413_d();
            this.bipedLeftLeg.rotateAngleX = 0.017453292f * entityArmorStand.getLeftLegRotation().func_179415_b();
            this.bipedLeftLeg.rotateAngleY = 0.017453292f * entityArmorStand.getLeftLegRotation().func_179416_c();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292f * entityArmorStand.getLeftLegRotation().func_179413_d();
            this.bipedLeftLeg.setRotationPoint(1.9f, 11.0f, 0.0f);
            this.bipedRightLeg.rotateAngleX = 0.017453292f * entityArmorStand.getRightLegRotation().func_179415_b();
            this.bipedRightLeg.rotateAngleY = 0.017453292f * entityArmorStand.getRightLegRotation().func_179416_c();
            this.bipedRightLeg.rotateAngleZ = 0.017453292f * entityArmorStand.getRightLegRotation().func_179413_d();
            this.bipedRightLeg.setRotationPoint(-1.9f, 11.0f, 0.0f);
            ModelBase.func_178685_a(this.bipedHead, this.bipedHeadwear);
        }
    }
    
    static {
        __OBFID = "CL_00002632";
    }
}
