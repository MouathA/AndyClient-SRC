package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSquid extends ModelBase
{
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles;
    private static final String __OBFID;
    
    public ModelSquid() {
        this.squidTentacles = new ModelRenderer[8];
        (this.squidBody = new ModelRenderer(this, 0, 0)).addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        final ModelRenderer squidBody = this.squidBody;
        squidBody.rotationPointY += 8;
        while (0 < this.squidTentacles.length) {
            this.squidTentacles[0] = new ModelRenderer(this, 48, 0);
            final double n = 0 * 3.141592653589793 * 2.0 / this.squidTentacles.length;
            final float rotationPointX = (float)Math.cos(n) * 5.0f;
            final float rotationPointZ = (float)Math.sin(n) * 5.0f;
            this.squidTentacles[0].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[0].rotationPointX = rotationPointX;
            this.squidTentacles[0].rotationPointZ = rotationPointZ;
            this.squidTentacles[0].rotationPointY = 15;
            this.squidTentacles[0].rotateAngleY = (float)(0 * 3.141592653589793 * -2.0 / this.squidTentacles.length + 1.5707963267948966);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleX, final float n3, final float n4, final float n5, final Entity entity) {
        final ModelRenderer[] squidTentacles = this.squidTentacles;
        while (0 < squidTentacles.length) {
            squidTentacles[0].rotateAngleX = rotateAngleX;
            int n6 = 0;
            ++n6;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.squidBody.render(n6);
        while (0 < this.squidTentacles.length) {
            this.squidTentacles[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    static {
        __OBFID = "CL_00000861";
    }
}
