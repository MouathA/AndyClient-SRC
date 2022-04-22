package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityFireworkOverlayFX extends EntityFX
{
    private static final String __OBFID;
    
    protected EntityFireworkOverlayFX(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.particleMaxAge = 4;
    }
    
    @Override
    public void func_180434_a(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = 0.25f;
        final float n8 = n7 + 0.25f;
        final float n9 = 0.125f;
        final float n10 = n9 + 0.25f;
        final float n11 = 7.1f * MathHelper.sin((this.particleAge + n - 1.0f) * 0.25f * 3.1415927f);
        this.particleAlpha = 0.6f - (this.particleAge + n - 1.0f) * 0.25f * 0.5f;
        final float n12 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityFireworkOverlayFX.interpPosX);
        final float n13 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityFireworkOverlayFX.interpPosY);
        final float n14 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityFireworkOverlayFX.interpPosZ);
        worldRenderer.func_178960_a(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        worldRenderer.addVertexWithUV(n12 - n2 * n11 - n5 * n11, n13 - n3 * n11, n14 - n4 * n11 - n6 * n11, n8, n10);
        worldRenderer.addVertexWithUV(n12 - n2 * n11 + n5 * n11, n13 + n3 * n11, n14 - n4 * n11 + n6 * n11, n8, n9);
        worldRenderer.addVertexWithUV(n12 + n2 * n11 + n5 * n11, n13 + n3 * n11, n14 + n4 * n11 + n6 * n11, n7, n9);
        worldRenderer.addVertexWithUV(n12 + n2 * n11 - n5 * n11, n13 - n3 * n11, n14 + n4 * n11 - n6 * n11, n7, n10);
    }
    
    static {
        __OBFID = "CL_00000904";
    }
}
