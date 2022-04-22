package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelEnderMite extends ModelBase
{
    private static final int[][] field_178716_a;
    private static final int[][] field_178714_b;
    private static final int field_178715_c;
    private final ModelRenderer[] field_178713_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002629";
        field_178716_a = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
        field_178714_b = new int[][] { new int[2], { 0, 5 }, { 0, 14 }, { 0, 18 } };
        field_178715_c = ModelEnderMite.field_178716_a.length;
    }
    
    public ModelEnderMite() {
        this.field_178713_d = new ModelRenderer[ModelEnderMite.field_178715_c];
        float n = -3.5f;
        while (0 < this.field_178713_d.length) {
            (this.field_178713_d[0] = new ModelRenderer(this, ModelEnderMite.field_178714_b[0][0], ModelEnderMite.field_178714_b[0][1])).addBox(ModelEnderMite.field_178716_a[0][0] * -0.5f, 0.0f, ModelEnderMite.field_178716_a[0][2] * -0.5f, ModelEnderMite.field_178716_a[0][0], ModelEnderMite.field_178716_a[0][1], ModelEnderMite.field_178716_a[0][2]);
            this.field_178713_d[0].setRotationPoint(0.0f, (float)(24 - ModelEnderMite.field_178716_a[0][1]), n);
            if (0 < this.field_178713_d.length - 1) {
                n += (ModelEnderMite.field_178716_a[0][2] + ModelEnderMite.field_178716_a[1][2]) * 0.5f;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        while (0 < this.field_178713_d.length) {
            this.field_178713_d[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        while (0 < this.field_178713_d.length) {
            this.field_178713_d[0].rotateAngleY = MathHelper.cos(n3 * 0.9f + 0 * 0.15f * 3.1415927f) * 3.1415927f * 0.01f * (1 + Math.abs(-2));
            this.field_178713_d[0].rotationPointX = MathHelper.sin(n3 * 0.9f + 0 * 0.15f * 3.1415927f) * 3.1415927f * 0.1f * Math.abs(-2);
            int n7 = 0;
            ++n7;
        }
    }
}
