package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.*;

public class EntityFireworkStarterFX_Factory implements IParticleFactory
{
    private static final String __OBFID;
    
    @Override
    public EntityFX func_178902_a(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        final EntityFireworkSparkFX entityFireworkSparkFX = new EntityFireworkSparkFX(world, n2, n3, n4, n5, n6, n7, Minecraft.getMinecraft().effectRenderer);
        entityFireworkSparkFX.setAlphaF(0.99f);
        return entityFireworkSparkFX;
    }
    
    static {
        __OBFID = "CL_00002603";
    }
}
