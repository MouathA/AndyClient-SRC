package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class ArmorStandRenderer extends RendererLivingEntity
{
    public static final ResourceLocation field_177103_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002447";
        field_177103_a = new ResourceLocation("textures/entity/armorstand/wood.png");
    }
    
    public ArmorStandRenderer(final RenderManager renderManager) {
        super(renderManager, new ModelArmorStand(), 0.0f);
        this.addLayer(new LayerBipedArmor((RendererLivingEntity)this) {
            private static final String __OBFID;
            final ArmorStandRenderer this$0;
            
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelArmorStandArmor(0.5f);
                this.field_177186_d = new ModelArmorStandArmor(1.0f);
            }
            
            static {
                __OBFID = "CL_00002446";
            }
        });
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerCustomHead(this.func_177100_a().bipedHead));
    }
    
    protected ResourceLocation func_177102_a(final EntityArmorStand entityArmorStand) {
        return ArmorStandRenderer.field_177103_a;
    }
    
    public ModelArmorStand func_177100_a() {
        return (ModelArmorStand)super.getMainModel();
    }
    
    protected void func_177101_a(final EntityArmorStand entityArmorStand, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
    }
    
    protected boolean func_177099_b(final EntityArmorStand entityArmorStand) {
        return entityArmorStand.getAlwaysRenderNameTag();
    }
    
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.func_177099_b((EntityArmorStand)entityLivingBase);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.func_177101_a((EntityArmorStand)entityLivingBase, n, n2, n3);
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.func_177100_a();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_177102_a((EntityArmorStand)entity);
    }
    
    @Override
    protected boolean func_177070_b(final Entity entity) {
        return this.func_177099_b((EntityArmorStand)entity);
    }
}
