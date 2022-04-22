package net.minecraft.client.renderer.entity;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;

public class RenderHorse extends RenderLiving
{
    private static final Map field_110852_a;
    private static final ResourceLocation whiteHorseTextures;
    private static final ResourceLocation muleTextures;
    private static final ResourceLocation donkeyTextures;
    private static final ResourceLocation zombieHorseTextures;
    private static final ResourceLocation skeletonHorseTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001000";
        field_110852_a = Maps.newHashMap();
        whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
        muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
        donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
        zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
        skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
    }
    
    public RenderHorse(final RenderManager renderManager, final ModelHorse modelHorse, final float n) {
        super(renderManager, modelHorse, n);
    }
    
    protected void func_180580_a(final EntityHorse entityHorse, final float n) {
        float n2 = 1.0f;
        final int horseType = entityHorse.getHorseType();
        if (horseType == 1) {
            n2 *= 0.87f;
        }
        else if (horseType == 2) {
            n2 *= 0.92f;
        }
        GlStateManager.scale(n2, n2, n2);
        super.preRenderCallback(entityHorse, n);
    }
    
    protected ResourceLocation func_180581_a(final EntityHorse entityHorse) {
        if (entityHorse.func_110239_cn()) {
            return this.func_110848_b(entityHorse);
        }
        switch (entityHorse.getHorseType()) {
            default: {
                return RenderHorse.whiteHorseTextures;
            }
            case 1: {
                return RenderHorse.donkeyTextures;
            }
            case 2: {
                return RenderHorse.muleTextures;
            }
            case 3: {
                return RenderHorse.zombieHorseTextures;
            }
            case 4: {
                return RenderHorse.skeletonHorseTextures;
            }
        }
    }
    
    private ResourceLocation func_110848_b(final EntityHorse entityHorse) {
        final String horseTexture = entityHorse.getHorseTexture();
        if (!entityHorse.func_175507_cI()) {
            return null;
        }
        ResourceLocation resourceLocation = RenderHorse.field_110852_a.get(horseTexture);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(horseTexture);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, new LayeredTexture(entityHorse.getVariantTexturePaths()));
            RenderHorse.field_110852_a.put(horseTexture, resourceLocation);
        }
        return resourceLocation;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_180580_a((EntityHorse)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.func_180581_a((EntityHorse)entity);
    }
}
