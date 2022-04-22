package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelLeashKnot extends ModelBase
{
    public ModelRenderer field_110723_a;
    private static final String __OBFID;
    
    public ModelLeashKnot() {
        this(0, 0, 32, 32);
    }
    
    public ModelLeashKnot(final int n, final int n2, final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        (this.field_110723_a = new ModelRenderer(this, n, n2)).addBox(-3.0f, -6.0f, -3.0f, 6, 8, 6, 0.0f);
        this.field_110723_a.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.field_110723_a.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.field_110723_a.rotateAngleY = n4 / 57.295776f;
        this.field_110723_a.rotateAngleX = n5 / 57.295776f;
    }
    
    static {
        __OBFID = "CL_00000843";
    }
}
