package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class Barrier extends EntityFX
{
    private static final String __OBFID;
    
    protected Barrier(final World world, final double n, final double n2, final double n3, final Item item) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.func_180435_a(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleGravity = 0.0f;
        this.particleMaxAge = 80;
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float minU = this.particleIcon.getMinU();
        final float maxU = this.particleIcon.getMaxU();
        final float minV = this.particleIcon.getMinV();
        final float maxV = this.particleIcon.getMaxV();
        final float n7 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - Barrier.interpPosX);
        final float n8 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - Barrier.interpPosY);
        final float n9 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - Barrier.interpPosZ);
        worldRenderer.func_178986_b(this.particleRed, this.particleGreen, this.particleBlue);
        final float n10 = 0.5f;
        worldRenderer.addVertexWithUV(n7 - n2 * n10 - n5 * n10, n8 - n3 * n10, n9 - n4 * n10 - n6 * n10, maxU, maxV);
        worldRenderer.addVertexWithUV(n7 - n2 * n10 + n5 * n10, n8 + n3 * n10, n9 - n4 * n10 + n6 * n10, maxU, minV);
        worldRenderer.addVertexWithUV(n7 + n2 * n10 + n5 * n10, n8 + n3 * n10, n9 + n4 * n10 + n6 * n10, minU, minV);
        worldRenderer.addVertexWithUV(n7 + n2 * n10 - n5 * n10, n8 - n3 * n10, n9 + n4 * n10 - n6 * n10, minU, maxV);
    }
    
    static {
        __OBFID = "CL_00002615";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new Barrier(world, n2, n3, n4, Item.getItemFromBlock(Blocks.barrier));
        }
        
        static {
            __OBFID = "CL_00002614";
        }
    }
}
