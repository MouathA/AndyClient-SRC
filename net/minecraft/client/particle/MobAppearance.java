package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;

public class MobAppearance extends EntityFX
{
    private EntityLivingBase field_174844_a;
    private static final String __OBFID;
    
    protected MobAppearance(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleGravity = 0.0f;
        this.particleMaxAge = 30;
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_174844_a == null) {
            final EntityGuardian field_174844_a = new EntityGuardian(this.worldObj);
            field_174844_a.func_175465_cm();
            this.field_174844_a = field_174844_a;
        }
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (this.field_174844_a != null) {
            final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            renderManager.func_178628_a(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
            final float n7 = 0.42553192f;
            final float n8 = (this.particleAge + n) / this.particleMaxAge;
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(770, 771);
            final float n9 = 240.0f;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n9, n9);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 0.05f + 0.5f * MathHelper.sin(n8 * 3.1415927f));
            GlStateManager.translate(0.0f, 1.8f, 0.0f);
            GlStateManager.rotate(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(60.0f - 150.0f * n8 - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.4f, -1.5f);
            GlStateManager.scale(n7, n7, n7);
            final EntityLivingBase field_174844_a = this.field_174844_a;
            final EntityLivingBase field_174844_a2 = this.field_174844_a;
            final float n10 = 0.0f;
            field_174844_a2.prevRotationYaw = n10;
            field_174844_a.rotationYaw = n10;
            final EntityLivingBase field_174844_a3 = this.field_174844_a;
            final EntityLivingBase field_174844_a4 = this.field_174844_a;
            final float n11 = 0.0f;
            field_174844_a4.prevRotationYawHead = n11;
            field_174844_a3.rotationYawHead = n11;
            renderManager.renderEntityWithPosYaw(this.field_174844_a, 0.0, 0.0, 0.0, 0.0f, n);
        }
    }
    
    static {
        __OBFID = "CL_00002594";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new MobAppearance(world, n2, n3, n4);
        }
        
        static {
            __OBFID = "CL_00002593";
        }
    }
}
