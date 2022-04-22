package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer creeperArmor;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    private static final String __OBFID;
    
    public ModelCreeper() {
        this(0.0f);
    }
    
    public ModelCreeper(final float n) {
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, n);
        this.head.setRotationPoint(0.0f, 6, 0.0f);
        (this.creeperArmor = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, n + 0.5f);
        this.creeperArmor.setRotationPoint(0.0f, 6, 0.0f);
        (this.body = new ModelRenderer(this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, n);
        this.body.setRotationPoint(0.0f, 6, 0.0f);
        (this.leg1 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg1.setRotationPoint(-2.0f, 18, 4.0f);
        (this.leg2 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg2.setRotationPoint(2.0f, 18, 4.0f);
        (this.leg3 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg3.setRotationPoint(-2.0f, 18, -4.0f);
        (this.leg4 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, n);
        this.leg4.setRotationPoint(2.0f, 18, -4.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.render(n6);
        this.body.render(n6);
        this.leg1.render(n6);
        this.leg2.render(n6);
        this.leg3.render(n6);
        this.leg4.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.head.rotateAngleY = n4 / 57.295776f;
        this.head.rotateAngleX = n5 / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leg2.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg3.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.leg4.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
    }
    
    static {
        __OBFID = "CL_00000837";
    }
}
