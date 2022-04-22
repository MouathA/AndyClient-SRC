package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelWitch extends ModelVillager
{
    public boolean field_82900_g;
    private ModelRenderer field_82901_h;
    private ModelRenderer witchHat;
    private static final String __OBFID;
    
    public ModelWitch(final float n) {
        super(n, 0.0f, 64, 128);
        (this.field_82901_h = new ModelRenderer(this).setTextureSize(64, 128)).setRotationPoint(0.0f, -2.0f, 0.0f);
        this.field_82901_h.setTextureOffset(0, 0).addBox(0.0f, 3.0f, -6.75f, 1, 1, 1, -0.25f);
        this.villagerNose.addChild(this.field_82901_h);
        (this.witchHat = new ModelRenderer(this).setTextureSize(64, 128)).setRotationPoint(-5.0f, -10.03125f, -5.0f);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0f, 0.0f, 0.0f, 10, 2, 10);
        this.villagerHead.addChild(this.witchHat);
        final ModelRenderer setTextureSize = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7, 4, 7);
        setTextureSize.rotateAngleX = -0.05235988f;
        setTextureSize.rotateAngleZ = 0.02617994f;
        this.witchHat.addChild(setTextureSize);
        final ModelRenderer setTextureSize2 = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize2.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize2.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4);
        setTextureSize2.rotateAngleX = -0.10471976f;
        setTextureSize2.rotateAngleZ = 0.05235988f;
        setTextureSize.addChild(setTextureSize2);
        final ModelRenderer setTextureSize3 = new ModelRenderer(this).setTextureSize(64, 128);
        setTextureSize3.setRotationPoint(1.75f, -2.0f, 2.0f);
        setTextureSize3.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        setTextureSize3.rotateAngleX = -0.20943952f;
        setTextureSize3.rotateAngleZ = 0.10471976f;
        setTextureSize2.addChild(setTextureSize3);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final ModelRenderer villagerNose = this.villagerNose;
        final ModelRenderer villagerNose2 = this.villagerNose;
        final ModelRenderer villagerNose3 = this.villagerNose;
        final float offsetX = 0.0f;
        villagerNose3.offsetZ = offsetX;
        villagerNose2.offsetY = offsetX;
        villagerNose.offsetX = offsetX;
        final float n7 = 0.01f * (entity.getEntityId() % 10);
        this.villagerNose.rotateAngleX = MathHelper.sin(entity.ticksExisted * n7) * 4.5f * 3.1415927f / 180.0f;
        this.villagerNose.rotateAngleY = 0.0f;
        this.villagerNose.rotateAngleZ = MathHelper.cos(entity.ticksExisted * n7) * 2.5f * 3.1415927f / 180.0f;
        if (this.field_82900_g) {
            this.villagerNose.rotateAngleX = -0.9f;
            this.villagerNose.offsetZ = -0.09375f;
            this.villagerNose.offsetY = 0.1875f;
        }
    }
    
    static {
        __OBFID = "CL_00000866";
    }
}
