package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class EntityBreakingFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final Item item) {
        this(world, n, n2, n3, item, 0);
    }
    
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final Item item, final int n7) {
        this(world, n, n2, n3, item, n7);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += n4;
        this.motionY += n5;
        this.motionZ += n6;
    }
    
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final Item item, final int n4) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.func_180435_a(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, n4));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGravity = Blocks.snow.blockParticleGravity;
        this.particleScale /= 2.0f;
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float interpolatedU = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float interpolatedU2 = interpolatedU + 0.015609375f;
        float interpolatedV = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float interpolatedV2 = interpolatedV + 0.015609375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            interpolatedU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            interpolatedU2 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            interpolatedV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            interpolatedV2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityBreakingFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityBreakingFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityBreakingFX.interpPosZ);
        worldRenderer.func_178986_b(this.particleRed, this.particleGreen, this.particleBlue);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7, interpolatedU, interpolatedV2);
        worldRenderer.addVertexWithUV(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7, interpolatedU, interpolatedV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7, interpolatedU2, interpolatedV);
        worldRenderer.addVertexWithUV(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7, interpolatedU2, interpolatedV2);
    }
    
    static {
        __OBFID = "CL_00000897";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBreakingFX(world, n2, n3, n4, n5, n6, n7, Item.getItemById(array[0]), (array.length > 1) ? array[1] : 0);
        }
        
        static {
            __OBFID = "CL_00002613";
        }
    }
    
    public static class SlimeFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBreakingFX(world, n2, n3, n4, Items.slime_ball);
        }
        
        static {
            __OBFID = "CL_00002612";
        }
    }
    
    public static class SnowballFactory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBreakingFX(world, n2, n3, n4, Items.snowball);
        }
        
        static {
            __OBFID = "CL_00002611";
        }
    }
}
