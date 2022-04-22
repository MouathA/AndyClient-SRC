package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer[] sideModels;
    private static final String __OBFID;
    
    public ModelMinecart() {
        (this.sideModels = new ModelRenderer[7])[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        this.sideModels[0].addBox(0, 0, -1.0f, 20, 16, 2, 0.0f);
        this.sideModels[0].setRotationPoint(0.0f, 4, 0.0f);
        this.sideModels[5].addBox(1, 1, -1.0f, 18, 14, 1, 0.0f);
        this.sideModels[5].setRotationPoint(0.0f, 4, 0.0f);
        this.sideModels[1].addBox(2, -9, -1.0f, 16, 8, 2, 0.0f);
        this.sideModels[1].setRotationPoint(1, 4, 0.0f);
        this.sideModels[2].addBox(2, -9, -1.0f, 16, 8, 2, 0.0f);
        this.sideModels[2].setRotationPoint(-1, 4, 0.0f);
        this.sideModels[3].addBox(2, -9, -1.0f, 16, 8, 2, 0.0f);
        this.sideModels[3].setRotationPoint(0.0f, 4, 1);
        this.sideModels[4].addBox(2, -9, -1.0f, 16, 8, 2, 0.0f);
        this.sideModels[4].setRotationPoint(0.0f, 4, -1);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = 3.1415927f;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.sideModels[5].rotationPointY = 4.0f - n3;
        while (0 < 6) {
            this.sideModels[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    static {
        __OBFID = "CL_00000844";
    }
}
