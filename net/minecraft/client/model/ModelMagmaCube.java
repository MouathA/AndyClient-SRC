package net.minecraft.client.model;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] segments;
    ModelRenderer core;
    private static final String __OBFID;
    
    public ModelMagmaCube() {
        this.segments = new ModelRenderer[8];
        while (0 < this.segments.length) {
            if (0 != 2) {
                if (0 == 3) {}
            }
            (this.segments[0] = new ModelRenderer(this, 24, 19)).addBox(-4.0f, 16, -4.0f, 8, 1, 8);
            int n = 0;
            ++n;
        }
        (this.core = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 18.0f, -2.0f, 4, 4, 4);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityMagmaCube entityMagmaCube = (EntityMagmaCube)entityLivingBase;
        float n4 = entityMagmaCube.prevSquishFactor + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * n3;
        if (n4 < 0.0f) {
            n4 = 0.0f;
        }
        while (0 < this.segments.length) {
            this.segments[0].rotationPointY = -4 * n4 * 1.7f;
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.core.render(n6);
        while (0 < this.segments.length) {
            this.segments[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    static {
        __OBFID = "CL_00000842";
    }
}
