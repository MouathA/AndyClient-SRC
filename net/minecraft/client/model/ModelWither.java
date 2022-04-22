package net.minecraft.client.model;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;

public class ModelWither extends ModelBase
{
    private ModelRenderer[] field_82905_a;
    private ModelRenderer[] field_82904_b;
    private static final String __OBFID;
    
    public ModelWither(final float n) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_82905_a = new ModelRenderer[3];
        (this.field_82905_a[0] = new ModelRenderer(this, 0, 16)).addBox(-10.0f, 3.9f, -0.5f, 20, 3, 3, n);
        (this.field_82905_a[1] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight)).setRotationPoint(-2.0f, 6.9f, -0.5f);
        this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0f, 0.0f, 0.0f, 3, 10, 3, n);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 1.5f, 0.5f, 11, 2, 2, n);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 4.0f, 0.5f, 11, 2, 2, n);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 6.5f, 0.5f, 11, 2, 2, n);
        (this.field_82905_a[2] = new ModelRenderer(this, 12, 22)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3, n);
        this.field_82904_b = new ModelRenderer[3];
        (this.field_82904_b[0] = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8, n);
        (this.field_82904_b[1] = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -4.0f, -4.0f, 6, 6, 6, n);
        this.field_82904_b[1].rotationPointX = -8.0f;
        this.field_82904_b[1].rotationPointY = 4.0f;
        (this.field_82904_b[2] = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -4.0f, -4.0f, 6, 6, 6, n);
        this.field_82904_b[2].rotationPointX = 10.0f;
        this.field_82904_b[2].rotationPointY = 4.0f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final ModelRenderer[] field_82904_b = this.field_82904_b;
        int n7 = 0;
        while (0 < field_82904_b.length) {
            field_82904_b[0].render(n6);
            ++n7;
        }
        final ModelRenderer[] field_82905_a = this.field_82905_a;
        while (0 < field_82905_a.length) {
            field_82905_a[0].render(n6);
            ++n7;
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float cos = MathHelper.cos(n3 * 0.1f);
        this.field_82905_a[1].rotateAngleX = (0.065f + 0.05f * cos) * 3.1415927f;
        this.field_82905_a[2].setRotationPoint(-2.0f, 6.9f + MathHelper.cos(this.field_82905_a[1].rotateAngleX) * 10.0f, -0.5f + MathHelper.sin(this.field_82905_a[1].rotateAngleX) * 10.0f);
        this.field_82905_a[2].rotateAngleX = (0.265f + 0.1f * cos) * 3.1415927f;
        this.field_82904_b[0].rotateAngleY = n4 / 57.295776f;
        this.field_82904_b[0].rotateAngleX = n5 / 57.295776f;
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityWither entityWither = (EntityWither)entityLivingBase;
    }
    
    static {
        __OBFID = "CL_00000867";
    }
}
