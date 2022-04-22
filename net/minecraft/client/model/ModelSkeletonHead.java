package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSkeletonHead extends ModelBase
{
    public ModelRenderer skeletonHead;
    private static final String __OBFID;
    
    public ModelSkeletonHead() {
        this(0, 35, 64, 64);
    }
    
    public ModelSkeletonHead(final int n, final int n2, final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        (this.skeletonHead = new ModelRenderer(this, n, n2)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.skeletonHead.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.skeletonHead.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.skeletonHead.rotateAngleY = n4 / 57.295776f;
        this.skeletonHead.rotateAngleX = n5 / 57.295776f;
    }
    
    static {
        __OBFID = "CL_00000856";
    }
}
