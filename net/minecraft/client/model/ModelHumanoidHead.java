package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelHumanoidHead extends ModelSkeletonHead
{
    private final ModelRenderer head;
    private static final String __OBFID;
    
    public ModelHumanoidHead() {
        super(0, 0, 64, 64);
        (this.head = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.25f);
        this.head.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        this.head.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleY = this.skeletonHead.rotateAngleY;
        this.head.rotateAngleX = this.skeletonHead.rotateAngleX;
    }
    
    static {
        __OBFID = "CL_00002627";
    }
}
