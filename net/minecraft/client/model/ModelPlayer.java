package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelPlayer extends ModelBiped
{
    public ModelRenderer field_178734_a;
    public ModelRenderer field_178732_b;
    public ModelRenderer field_178733_c;
    public ModelRenderer field_178731_d;
    public ModelRenderer field_178730_v;
    private ModelRenderer field_178729_w;
    private ModelRenderer field_178736_x;
    private boolean field_178735_y;
    private static final String __OBFID;
    
    public ModelPlayer(final float n, final boolean field_178735_y) {
        super(n, 0.0f, 64, 64);
        this.field_178735_y = field_178735_y;
        (this.field_178736_x = new ModelRenderer(this, 24, 0)).addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, n);
        (this.field_178729_w = new ModelRenderer(this, 0, 0)).setTextureSize(64, 32);
        this.field_178729_w.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, n);
        if (field_178735_y) {
            (this.bipedLeftArm = new ModelRenderer(this, 32, 48)).addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.bipedRightArm = new ModelRenderer(this, 40, 16)).addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, n);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
            (this.field_178734_a = new ModelRenderer(this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, n + 0.25f);
            this.field_178734_a.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.field_178732_b = new ModelRenderer(this, 40, 32)).addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, n + 0.25f);
            this.field_178732_b.setRotationPoint(-5.0f, 2.5f, 10.0f);
        }
        else {
            (this.bipedLeftArm = new ModelRenderer(this, 32, 48)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.field_178734_a = new ModelRenderer(this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, n + 0.25f);
            this.field_178734_a.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.field_178732_b = new ModelRenderer(this, 40, 32)).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, n + 0.25f);
            this.field_178732_b.setRotationPoint(-5.0f, 2.0f, 10.0f);
        }
        (this.bipedLeftLeg = new ModelRenderer(this, 16, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.field_178733_c = new ModelRenderer(this, 0, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n + 0.25f);
        this.field_178733_c.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.field_178731_d = new ModelRenderer(this, 0, 32)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n + 0.25f);
        this.field_178731_d.setRotationPoint(-1.9f, 12.0f, 0.0f);
        (this.field_178730_v = new ModelRenderer(this, 16, 32)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, n + 0.25f);
        this.field_178730_v.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.field_178733_c.render(n6);
            this.field_178731_d.render(n6);
            this.field_178734_a.render(n6);
            this.field_178732_b.render(n6);
            this.field_178730_v.render(n6);
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.field_178733_c.render(n6);
            this.field_178731_d.render(n6);
            this.field_178734_a.render(n6);
            this.field_178732_b.render(n6);
            this.field_178730_v.render(n6);
        }
    }
    
    public void func_178727_b(final float n) {
        ModelBase.func_178685_a(this.bipedHead, this.field_178736_x);
        this.field_178736_x.rotationPointX = 0.0f;
        this.field_178736_x.rotationPointY = 0.0f;
        this.field_178736_x.render(n);
    }
    
    public void func_178728_c(final float n) {
        this.field_178729_w.render(n);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        ModelBase.func_178685_a(this.bipedLeftLeg, this.field_178733_c);
        ModelBase.func_178685_a(this.bipedRightLeg, this.field_178731_d);
        ModelBase.func_178685_a(this.bipedLeftArm, this.field_178734_a);
        ModelBase.func_178685_a(this.bipedRightArm, this.field_178732_b);
        ModelBase.func_178685_a(this.bipedBody, this.field_178730_v);
    }
    
    public void func_178725_a() {
        this.bipedRightArm.render(0.0625f);
        this.field_178732_b.render(0.0625f);
    }
    
    public void func_178726_b() {
        this.bipedLeftArm.render(0.0625f);
        this.field_178734_a.render(0.0625f);
    }
    
    @Override
    public void func_178719_a(final boolean showModel) {
        super.func_178719_a(showModel);
        this.field_178734_a.showModel = showModel;
        this.field_178732_b.showModel = showModel;
        this.field_178733_c.showModel = showModel;
        this.field_178731_d.showModel = showModel;
        this.field_178730_v.showModel = showModel;
        this.field_178729_w.showModel = showModel;
        this.field_178736_x.showModel = showModel;
    }
    
    @Override
    public void postRenderHiddenArm(final float n) {
        if (this.field_178735_y) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            ++bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(n);
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            --bipedRightArm2.rotationPointX;
        }
        else {
            this.bipedRightArm.postRender(n);
        }
    }
    
    static {
        __OBFID = "CL_00002626";
    }
}
