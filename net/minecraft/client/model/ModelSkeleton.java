package net.minecraft.client.model;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ModelSkeleton extends ModelZombie
{
    private static final String __OBFID;
    
    public ModelSkeleton() {
        this(0.0f, false);
    }
    
    public ModelSkeleton(final float n, final boolean b) {
        super(n, 0.0f, 64, 32);
        if (!b) {
            (this.bipedRightArm = new ModelRenderer(this, 40, 16)).addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, n);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            this.bipedLeftArm = new ModelRenderer(this, 40, 16);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedRightLeg = new ModelRenderer(this, 0, 16)).addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, n);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, n);
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.aimedBow = (((EntitySkeleton)entityLivingBase).getSkeletonType() == 1);
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
    }
    
    static {
        __OBFID = "CL_00000857";
    }
}
