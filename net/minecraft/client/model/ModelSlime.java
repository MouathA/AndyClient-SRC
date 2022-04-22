package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSlime extends ModelBase
{
    ModelRenderer slimeBodies;
    ModelRenderer slimeRightEye;
    ModelRenderer slimeLeftEye;
    ModelRenderer slimeMouth;
    private static final String __OBFID;
    
    public ModelSlime(final int n) {
        (this.slimeBodies = new ModelRenderer(this, 0, n)).addBox(-4.0f, 16.0f, -4.0f, 8, 8, 8);
        if (n > 0) {
            (this.slimeBodies = new ModelRenderer(this, 0, n)).addBox(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            (this.slimeRightEye = new ModelRenderer(this, 32, 0)).addBox(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            (this.slimeLeftEye = new ModelRenderer(this, 32, 4)).addBox(1.25f, 18.0f, -3.5f, 2, 2, 2);
            (this.slimeMouth = new ModelRenderer(this, 32, 8)).addBox(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.slimeBodies.render(n6);
        if (this.slimeRightEye != null) {
            this.slimeRightEye.render(n6);
            this.slimeLeftEye.render(n6);
            this.slimeMouth.render(n6);
        }
    }
    
    static {
        __OBFID = "CL_00000858";
    }
}
