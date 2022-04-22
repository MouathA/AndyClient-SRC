package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderCreeper extends RenderLiving
{
    private static final ResourceLocation creeperTextures;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000985";
        creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
    
    public RenderCreeper(final RenderManager renderManager) {
        super(renderManager, new ModelCreeper(), 0.5f);
        this.addLayer(new LayerCreeperCharge(this));
    }
    
    protected void func_180570_a(final EntityCreeper entityCreeper, final float n) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n);
        final float n2 = 1.0f + MathHelper.sin(creeperFlashIntensity * 100.0f) * creeperFlashIntensity * 0.01f;
        final float clamp_float = MathHelper.clamp_float(creeperFlashIntensity, 0.0f, 1.0f);
        final float n3 = clamp_float * clamp_float;
        final float n4 = n3 * n3;
        final float n5 = (1.0f + n4 * 0.4f) * n2;
        GlStateManager.scale(n5, (1.0f + n4 * 0.1f) / n2, n5);
    }
    
    protected int func_180571_a(final EntityCreeper entityCreeper, final float n, final float n2) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n2);
        if ((int)(creeperFlashIntensity * 10.0f) % 2 == 0) {
            return 0;
        }
        return MathHelper.clamp_int((int)(creeperFlashIntensity * 0.2f * 255.0f), 0, 255) << 24 | 0xFFFFFF;
    }
    
    protected ResourceLocation getEntityTexture(final EntityCreeper entityCreeper) {
        return RenderCreeper.creeperTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.func_180570_a((EntityCreeper)entityLivingBase, n);
    }
    
    @Override
    protected int getColorMultiplier(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        return this.func_180571_a((EntityCreeper)entityLivingBase, n, n2);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCreeper)entity);
    }
}
