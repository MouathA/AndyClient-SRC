package net.minecraft.client.model;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ModelSheep2 extends ModelQuadruped
{
    private float field_78153_i;
    private static final String __OBFID;
    
    public ModelSheep2() {
        super(12, 0.0f);
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -4.0f, -6.0f, 6, 6, 8, 0.0f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(this, 28, 8)).addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
        this.head.rotationPointY = 6.0f + ((EntitySheep)entityLivingBase).getHeadRotationPointY(n3) * 9.0f;
        this.field_78153_i = ((EntitySheep)entityLivingBase).getHeadRotationAngleX(n3);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleX = this.field_78153_i;
    }
    
    static {
        __OBFID = "CL_00000853";
    }
}
