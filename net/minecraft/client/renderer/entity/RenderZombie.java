package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderZombie extends RenderBiped
{
    private static final ResourceLocation zombieTextures;
    private static final ResourceLocation zombieVillagerTextures;
    private final ModelBiped field_82434_o;
    private final ModelZombieVillager zombieVillagerModel;
    private final List field_177121_n;
    private final List field_177122_o;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001037";
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
        zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    }
    
    public RenderZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        final LayerRenderer layerRenderer = this.field_177097_h.get(0);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        this.addLayer(new LayerHeldItem(this));
        final LayerBipedArmor layerBipedArmor = new LayerBipedArmor((RendererLivingEntity)this) {
            private static final String __OBFID;
            final RenderZombie this$0;
            
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
            
            static {
                __OBFID = "CL_00002429";
            }
        };
        this.addLayer(layerBipedArmor);
        this.field_177122_o = Lists.newArrayList(this.field_177097_h);
        if (layerRenderer instanceof LayerCustomHead) {
            this.func_177089_b(layerRenderer);
            this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }
        this.func_177089_b(layerBipedArmor);
        this.addLayer(new LayerVillagerArmor(this));
        this.field_177121_n = Lists.newArrayList(this.field_177097_h);
    }
    
    public void func_180579_a(final EntityZombie entityZombie, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_82427_a(entityZombie);
        super.doRender(entityZombie, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180578_a(final EntityZombie entityZombie) {
        return entityZombie.isVillager() ? RenderZombie.zombieVillagerTextures : RenderZombie.zombieTextures;
    }
    
    private void func_82427_a(final EntityZombie entityZombie) {
        if (entityZombie.isVillager()) {
            this.mainModel = this.zombieVillagerModel;
            this.field_177097_h = this.field_177121_n;
        }
        else {
            this.mainModel = this.field_82434_o;
            this.field_177097_h = this.field_177122_o;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    protected void rotateCorpse(final EntityZombie entityZombie, final float n, float n2, final float n3) {
        if (entityZombie.isConverting()) {
            n2 += (float)(Math.cos(entityZombie.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(entityZombie, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.func_180578_a((EntityZombie)entityLiving);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180579_a((EntityZombie)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityZombie)entityLivingBase, n, n2, n3);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180579_a((EntityZombie)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180578_a((EntityZombie)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_180579_a((EntityZombie)entity, n, n2, n3, n4, n5);
    }
}
