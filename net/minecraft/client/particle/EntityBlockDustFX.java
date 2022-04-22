package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class EntityBlockDustFX extends EntityDiggingFX
{
    private static final String __OBFID;
    
    protected EntityBlockDustFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final IBlockState blockState) {
        super(world, n, n2, n3, motionX, motionY, motionZ, blockState);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    static {
        __OBFID = "CL_00000931";
    }
    
    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID;
        
        @Override
        public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final IBlockState stateById = Block.getStateById(array[0]);
            return (stateById.getBlock().getRenderType() == -1) ? null : new EntityBlockDustFX(world, n2, n3, n4, n5, n6, n7, stateById).func_174845_l();
        }
        
        static {
            __OBFID = "CL_00002576";
        }
    }
}
