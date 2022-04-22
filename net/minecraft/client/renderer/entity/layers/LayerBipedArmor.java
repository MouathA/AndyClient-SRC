package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;

public class LayerBipedArmor extends LayerArmorBase
{
    private static final String __OBFID;
    
    public LayerBipedArmor(final RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
    }
    
    @Override
    protected void func_177177_a() {
        this.field_177189_c = new ModelBiped(0.5f);
        this.field_177186_d = new ModelBiped(1.0f);
    }
    
    protected void func_177195_a(final ModelBiped modelBiped, final int n) {
        this.func_177194_a(modelBiped);
        switch (n) {
            case 1: {
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
                break;
            }
            case 2: {
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
                break;
            }
            case 3: {
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightArm.showModel = true;
                modelBiped.bipedLeftArm.showModel = true;
                break;
            }
            case 4: {
                modelBiped.bipedHead.showModel = true;
                modelBiped.bipedHeadwear.showModel = true;
                break;
            }
        }
    }
    
    protected void func_177194_a(final ModelBiped modelBiped) {
        modelBiped.func_178719_a(false);
    }
    
    @Override
    protected void func_177179_a(final ModelBase modelBase, final int n) {
        this.func_177195_a((ModelBiped)modelBase, n);
    }
    
    static {
        __OBFID = "CL_00002417";
    }
}
