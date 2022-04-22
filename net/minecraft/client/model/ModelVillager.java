package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelVillager extends ModelBase
{
    public ModelRenderer villagerHead;
    public ModelRenderer villagerBody;
    public ModelRenderer villagerArms;
    public ModelRenderer rightVillagerLeg;
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer villagerNose;
    private static final String __OBFID;
    
    public ModelVillager(final float n) {
        this(n, 0.0f, 64, 64);
    }
    
    public ModelVillager(final float n, final float n2, final int n3, final int n4) {
        (this.villagerHead = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, n);
        (this.villagerNose = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, n2 - 2.0f, 0.0f);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2, 4, 2, n);
        this.villagerHead.addChild(this.villagerNose);
        (this.villagerBody = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6, n);
        this.villagerBody.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8, 18, 6, n + 0.5f);
        (this.villagerArms = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2 + 2.0f, 0.0f);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4, 8, 4, n);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0f, -2.0f, -2.0f, 4, 8, 4, n);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8, 4, 4, n);
        (this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(n3, n4)).setRotationPoint(-2.0f, 12.0f + n2, 0.0f);
        this.rightVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n);
        this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(n3, n4);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0f, 12.0f + n2, 0.0f);
        this.leftVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.villagerHead.render(n6);
        this.villagerBody.render(n6);
        this.rightVillagerLeg.render(n6);
        this.leftVillagerLeg.render(n6);
        this.villagerArms.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.villagerHead.rotateAngleY = n4 / 57.295776f;
        this.villagerHead.rotateAngleX = n5 / 57.295776f;
        this.villagerArms.rotationPointY = 3.0f;
        this.villagerArms.rotationPointZ = -1.0f;
        this.villagerArms.rotateAngleX = -0.75f;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2 * 0.5f;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2 * 0.5f;
        this.rightVillagerLeg.rotateAngleY = 0.0f;
        this.leftVillagerLeg.rotateAngleY = 0.0f;
    }
    
    static {
        __OBFID = "CL_00000864";
    }
}
