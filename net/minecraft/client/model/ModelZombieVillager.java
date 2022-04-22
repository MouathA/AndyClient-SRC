package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelZombieVillager extends ModelBiped
{
    private static final String __OBFID;
    
    public ModelZombieVillager() {
        this(0.0f, 0.0f, false);
    }
    
    public ModelZombieVillager(final float n, final float n2, final boolean b) {
        super(n, 0.0f, 64, b ? 32 : 64);
        if (b) {
            (this.bipedHead = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -10.0f, -4.0f, 8, 8, 8, n);
            this.bipedHead.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        }
        else {
            (this.bipedHead = new ModelRenderer(this)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
            this.bipedHead.setTextureOffset(0, 32).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, n);
            this.bipedHead.setTextureOffset(24, 32).addBox(-1.0f, -3.0f, -6.0f, 2, 4, 2, n);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final float sin = MathHelper.sin(this.swingProgress * 3.1415927f);
        final float sin2 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - sin * 0.6f);
        this.bipedLeftArm.rotateAngleY = 0.1f - sin * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(n3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(n3 * 0.067f) * 0.05f;
    }
    
    static {
        __OBFID = "CL_00000865";
    }
}
