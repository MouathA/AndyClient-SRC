package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSilverfish extends ModelBase
{
    private ModelRenderer[] silverfishBodyParts;
    private ModelRenderer[] silverfishWings;
    private float[] field_78170_c;
    private static final int[][] silverfishBoxLength;
    private static final int[][] silverfishTexturePositions;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000855";
        silverfishBoxLength = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
        silverfishTexturePositions = new int[][] { new int[2], { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11, 0 }, { 13, 4 } };
    }
    
    public ModelSilverfish() {
        this.silverfishBodyParts = new ModelRenderer[7];
        this.field_78170_c = new float[7];
        float n = -3.5f;
        while (0 < this.silverfishBodyParts.length) {
            (this.silverfishBodyParts[0] = new ModelRenderer(this, ModelSilverfish.silverfishTexturePositions[0][0], ModelSilverfish.silverfishTexturePositions[0][1])).addBox(ModelSilverfish.silverfishBoxLength[0][0] * -0.5f, 0.0f, ModelSilverfish.silverfishBoxLength[0][2] * -0.5f, ModelSilverfish.silverfishBoxLength[0][0], ModelSilverfish.silverfishBoxLength[0][1], ModelSilverfish.silverfishBoxLength[0][2]);
            this.silverfishBodyParts[0].setRotationPoint(0.0f, (float)(24 - ModelSilverfish.silverfishBoxLength[0][1]), n);
            this.field_78170_c[0] = n;
            if (0 < this.silverfishBodyParts.length - 1) {
                n += (ModelSilverfish.silverfishBoxLength[0][2] + ModelSilverfish.silverfishBoxLength[1][2]) * 0.5f;
            }
            int n2 = 0;
            ++n2;
        }
        this.silverfishWings = new ModelRenderer[3];
        (this.silverfishWings[0] = new ModelRenderer(this, 20, 0)).addBox(-5.0f, 0.0f, ModelSilverfish.silverfishBoxLength[2][2] * -0.5f, 10, 8, ModelSilverfish.silverfishBoxLength[2][2]);
        this.silverfishWings[0].setRotationPoint(0.0f, 16.0f, this.field_78170_c[2]);
        (this.silverfishWings[1] = new ModelRenderer(this, 20, 11)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[4][2] * -0.5f, 6, 4, ModelSilverfish.silverfishBoxLength[4][2]);
        this.silverfishWings[1].setRotationPoint(0.0f, 20.0f, this.field_78170_c[4]);
        (this.silverfishWings[2] = new ModelRenderer(this, 20, 18)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[4][2] * -0.5f, 6, 5, ModelSilverfish.silverfishBoxLength[1][2]);
        this.silverfishWings[2].setRotationPoint(0.0f, 19.0f, this.field_78170_c[1]);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        int n7 = 0;
        while (0 < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[0].render(n6);
            ++n7;
        }
        while (0 < this.silverfishWings.length) {
            this.silverfishWings[0].render(n6);
            ++n7;
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        while (0 < this.silverfishBodyParts.length) {
            this.silverfishBodyParts[0].rotateAngleY = MathHelper.cos(n3 * 0.9f + 0 * 0.15f * 3.1415927f) * 3.1415927f * 0.05f * (1 + Math.abs(-2));
            this.silverfishBodyParts[0].rotationPointX = MathHelper.sin(n3 * 0.9f + 0 * 0.15f * 3.1415927f) * 3.1415927f * 0.2f * Math.abs(-2);
            int n7 = 0;
            ++n7;
        }
        this.silverfishWings[0].rotateAngleY = this.silverfishBodyParts[2].rotateAngleY;
        this.silverfishWings[1].rotateAngleY = this.silverfishBodyParts[4].rotateAngleY;
        this.silverfishWings[1].rotationPointX = this.silverfishBodyParts[4].rotationPointX;
        this.silverfishWings[2].rotateAngleY = this.silverfishBodyParts[1].rotateAngleY;
        this.silverfishWings[2].rotationPointX = this.silverfishBodyParts[1].rotationPointX;
    }
}
