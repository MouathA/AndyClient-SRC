package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSnowMan extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer bottomBody;
    public ModelRenderer head;
    public ModelRenderer rightHand;
    public ModelRenderer leftHand;
    private static final String __OBFID;
    
    public ModelSnowMan() {
        final float n = 4.0f;
        final float n2 = 0.0f;
        (this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, n2 - 0.5f);
        this.head.setRotationPoint(0.0f, 0.0f + n, 0.0f);
        (this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64)).addBox(-1.0f, 0.0f, -1.0f, 12, 2, 2, n2 - 0.5f);
        this.rightHand.setRotationPoint(0.0f, 0.0f + n + 9.0f - 7.0f, 0.0f);
        (this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64)).addBox(-1.0f, 0.0f, -1.0f, 12, 2, 2, n2 - 0.5f);
        this.leftHand.setRotationPoint(0.0f, 0.0f + n + 9.0f - 7.0f, 0.0f);
        (this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64)).addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10, n2 - 0.5f);
        this.body.setRotationPoint(0.0f, 0.0f + n + 9.0f, 0.0f);
        (this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64)).addBox(-6.0f, -12.0f, -6.0f, 12, 12, 12, n2 - 0.5f);
        this.bottomBody.setRotationPoint(0.0f, 0.0f + n + 20.0f, 0.0f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleY = n4 / 57.295776f;
        this.head.rotateAngleX = n5 / 57.295776f;
        this.body.rotateAngleY = n4 / 57.295776f * 0.25f;
        final float sin = MathHelper.sin(this.body.rotateAngleY);
        final float cos = MathHelper.cos(this.body.rotateAngleY);
        this.rightHand.rotateAngleZ = 1.0f;
        this.leftHand.rotateAngleZ = -1.0f;
        this.rightHand.rotateAngleY = 0.0f + this.body.rotateAngleY;
        this.leftHand.rotateAngleY = 3.1415927f + this.body.rotateAngleY;
        this.rightHand.rotationPointX = cos * 5.0f;
        this.rightHand.rotationPointZ = -sin * 5.0f;
        this.leftHand.rotationPointX = -cos * 5.0f;
        this.leftHand.rotationPointZ = sin * 5.0f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.body.render(n6);
        this.bottomBody.render(n6);
        this.head.render(n6);
        this.rightHand.render(n6);
        this.leftHand.render(n6);
    }
    
    static {
        __OBFID = "CL_00000859";
    }
}
