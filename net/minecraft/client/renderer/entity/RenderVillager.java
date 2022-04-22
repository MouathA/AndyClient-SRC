package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderVillager extends RenderLiving
{
    private static final ResourceLocation villagerTextures;
    private static final ResourceLocation farmerVillagerTextures;
    private static final ResourceLocation librarianVillagerTextures;
    private static final ResourceLocation priestVillagerTextures;
    private static final ResourceLocation smithVillagerTextures;
    private static final ResourceLocation butcherVillagerTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001032";
        villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
        farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
        librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
        priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
        smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
        butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");
    }
    
    public RenderVillager(final RenderManager renderManager) {
        super(renderManager, new ModelVillager(0.0f), 0.5f);
        this.addLayer(new LayerCustomHead(this.func_177134_g().villagerHead));
    }
    
    public ModelVillager func_177134_g() {
        return (ModelVillager)super.getMainModel();
    }
    
    protected ResourceLocation getEntityTexture(final EntityVillager entityVillager) {
        switch (entityVillager.getProfession()) {
            case 0: {
                return RenderVillager.farmerVillagerTextures;
            }
            case 1: {
                return RenderVillager.librarianVillagerTextures;
            }
            case 2: {
                return RenderVillager.priestVillagerTextures;
            }
            case 3: {
                return RenderVillager.smithVillagerTextures;
            }
            case 4: {
                return RenderVillager.butcherVillagerTextures;
            }
            default: {
                return RenderVillager.villagerTextures;
            }
        }
    }
    
    protected void preRenderCallback(final EntityVillager entityVillager, final float n) {
        float n2 = 0.9375f;
        if (entityVillager.getGrowingAge() < 0) {
            n2 *= 0.5;
            this.shadowSize = 0.25f;
        }
        else {
            this.shadowSize = 0.5f;
        }
        GlStateManager.scale(n2, n2, n2);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityVillager)entityLivingBase, n);
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.func_177134_g();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityVillager)entity);
    }
}
