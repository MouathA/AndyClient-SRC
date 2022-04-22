package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;

public class RenderEnderman extends RenderLiving
{
    private static final ResourceLocation endermanTextures;
    private ModelEnderman endermanModel;
    private Random rnd;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000989";
        endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    }
    
    public RenderEnderman(final RenderManager renderManager) {
        super(renderManager, new ModelEnderman(0.0f), 0.5f);
        this.rnd = new Random();
        this.endermanModel = (ModelEnderman)super.mainModel;
        this.addLayer(new LayerEndermanEyes(this));
        this.addLayer(new LayerHeldBlock(this));
    }
    
    public void doRender(final EntityEnderman entityEnderman, double n, final double n2, double n3, final float n4, final float n5) {
        this.endermanModel.isCarrying = (entityEnderman.func_175489_ck().getBlock().getMaterial() != Material.air);
        this.endermanModel.isAttacking = entityEnderman.isScreaming();
        if (entityEnderman.isScreaming()) {
            final double n6 = 0.02;
            n += this.rnd.nextGaussian() * n6;
            n3 += this.rnd.nextGaussian() * n6;
        }
        super.doRender(entityEnderman, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation func_180573_a(final EntityEnderman entityEnderman) {
        return RenderEnderman.endermanTextures;
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderman)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180573_a((EntityEnderman)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderman)entity, n, n2, n3, n4, n5);
    }
}
