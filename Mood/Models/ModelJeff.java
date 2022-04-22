package Mood.Models;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelJeff extends ModelBase
{
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer knifehandle;
    ModelRenderer knife;
    ModelRenderer knife1;
    ModelRenderer knife2;
    ModelRenderer knife3;
    
    public ModelJeff() {
        super.textureWidth = 64;
        super.textureHeight = 64;
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-5.0f, -10.0f, -4.0f, 10, 10, 8);
        this.head.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.head.setTextureSize(64, 64);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        (this.body = new ModelRenderer(this, 16, 36)).addBox(-4.0f, 0.0f, -2.0f, 8, 14, 4);
        this.body.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.body.setTextureSize(64, 64);
        this.body.mirror = true;
        this.setRotation(this.body, 0.2443461f, 0.0f, 0.0f);
        (this.rightarm = new ModelRenderer(this, 43, 19)).addBox(-3.0f, -2.0f, -2.0f, 3, 12, 3);
        this.rightarm.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.rightarm.setTextureSize(64, 64);
        this.rightarm.mirror = true;
        this.setRotation(this.rightarm, -1.308997f, 0.0f, 0.0f);
        (this.leftarm = new ModelRenderer(this, 43, 19)).addBox(0.0f, -2.0f, -2.0f, 3, 12, 3);
        this.leftarm.setRotationPoint(4.0f, 2.0f, 0.0f);
        this.leftarm.setTextureSize(64, 64);
        this.leftarm.mirror = true;
        this.setRotation(this.leftarm, 0.0f, 0.0f, 0.0f);
        (this.rightleg = new ModelRenderer(this, 0, 19)).addBox(-2.0f, 0.0f, -2.0f, 4, 10, 4);
        this.rightleg.setRotationPoint(-2.0f, 14.0f, 3.0f);
        this.rightleg.setTextureSize(64, 64);
        this.rightleg.mirror = true;
        this.setRotation(this.rightleg, 0.0f, 0.0f, 0.0f);
        (this.leftleg = new ModelRenderer(this, 0, 19)).addBox(-2.0f, 0.0f, -2.0f, 4, 10, 4);
        this.leftleg.setRotationPoint(2.0f, 14.0f, 3.0f);
        this.leftleg.setTextureSize(64, 64);
        this.leftleg.mirror = true;
        this.setRotation(this.leftleg, 0.0f, 0.0f, 0.0f);
        (this.knifehandle = new ModelRenderer(this, 37, 47)).addBox(-2.0f, 8.0f, -9.0f, 1, 2, 12);
        this.knifehandle.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.knifehandle.setTextureSize(64, 64);
        this.knifehandle.mirror = true;
        this.setRotation(this.knifehandle, -1.308997f, 0.0f, 0.0f);
        (this.knife = new ModelRenderer(this, 43, 11)).addBox(-2.0f, 10.0f, -8.0f, 1, 2, 5);
        this.knife.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.knife.setTextureSize(64, 64);
        this.knife.mirror = true;
        this.setRotation(this.knife, -1.308997f, 0.0f, 0.0f);
        (this.knife1 = new ModelRenderer(this, 43, 6)).addBox(-2.0f, 12.0f, -7.0f, 1, 1, 3);
        this.knife1.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.knife1.setTextureSize(64, 64);
        this.knife1.mirror = true;
        this.setRotation(this.knife1, -1.308997f, 0.0f, 0.0f);
        (this.knife2 = new ModelRenderer(this, 56, 15)).addBox(-2.0f, 10.0f, -3.0f, 1, 1, 1);
        this.knife2.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.knife2.setTextureSize(64, 64);
        this.knife2.mirror = true;
        this.setRotation(this.knife2, -1.308997f, 0.0f, 0.0f);
        (this.knife3 = new ModelRenderer(this, 52, 8)).addBox(-2.0f, 10.0f, -9.0f, 1, 1, 1);
        this.knife3.setRotationPoint(-4.0f, 2.0f, 0.0f);
        this.knife3.setTextureSize(64, 64);
        this.knife3.mirror = true;
        this.setRotation(this.knife3, -1.308997f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        this.setRotationAngles(n, n2, n3, n4, n5, n6);
        this.head.render(n6);
        this.body.render(n6);
        this.rightarm.render(n6);
        this.leftarm.render(n6);
        this.rightleg.render(n6);
        this.leftleg.render(n6);
        this.knifehandle.render(n6);
        this.knife.render(n6);
        this.knife1.render(n6);
        this.knife2.render(n6);
        this.knife3.render(n6);
    }
    
    private void setRotation(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
    
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.head.rotateAngleY = n4 / 57.295776f;
        this.head.rotateAngleX = n5 / 57.295776f;
        this.leftarm.rotateAngleX = MathHelper.cos(n * 0.6662f) * 2.0f * n2 * 0.5f;
        this.leftarm.rotateAngleZ = 0.0f;
        this.rightleg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leftleg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.rightleg.rotateAngleY = 0.0f;
        this.leftleg.rotateAngleY = 0.0f;
    }
}
