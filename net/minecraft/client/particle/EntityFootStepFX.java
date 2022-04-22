package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class EntityFootStepFX extends EntityFX
{
    private static final ResourceLocation field_110126_a;
    private int footstepAge;
    private int footstepMaxAge;
    private TextureManager currentFootSteps;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000908";
        field_110126_a = new ResourceLocation("textures/particle/footprint.png");
    }
    
    protected EntityFootStepFX(final TextureManager currentFootSteps, final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.currentFootSteps = currentFootSteps;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.footstepMaxAge = 200;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.footstepAge + n) / this.footstepMaxAge;
        float n8 = 2.0f - n7 * n7 * 2.0f;
        if (n8 > 1.0f) {
            n8 = 1.0f;
        }
        final float n9 = n8 * 0.2f;
        final float n10 = 0.125f;
        final float n11 = (float)(this.posX - EntityFootStepFX.interpPosX);
        final float n12 = (float)(this.posY - EntityFootStepFX.interpPosY);
        final float n13 = (float)(this.posZ - EntityFootStepFX.interpPosZ);
        final float lightBrightness = this.worldObj.getLightBrightness(new BlockPos(this));
        this.currentFootSteps.bindTexture(EntityFootStepFX.field_110126_a);
        GlStateManager.blendFunc(770, 771);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178960_a(lightBrightness, lightBrightness, lightBrightness, n9);
        worldRenderer.addVertexWithUV(n11 - n10, n12, n13 + n10, 0.0, 1.0);
        worldRenderer.addVertexWithUV(n11 + n10, n12, n13 + n10, 1.0, 1.0);
        worldRenderer.addVertexWithUV(n11 + n10, n12, n13 - n10, 1.0, 0.0);
        worldRenderer.addVertexWithUV(n11 - n10, n12, n13 - n10, 0.0, 0.0);
        Tessellator.getInstance().draw();
    }
    
    @Override
    public void onUpdate() {
        ++this.footstepAge;
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), world, n2, n3, n4);
        }
        
        static {
            __OBFID = "CL_00002601";
        }
    }
}
