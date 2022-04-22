package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RandomPositionGenerator
{
    private static Vec3 staticVector;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001629";
        RandomPositionGenerator.staticVector = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static Vec3 findRandomTarget(final EntityCreature entityCreature, final int n, final int n2) {
        return findRandomTargetBlock(entityCreature, n, n2, null);
    }
    
    public static Vec3 findRandomTargetBlockTowards(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = vec3.subtract(entityCreature.posX, entityCreature.posY, entityCreature.posZ);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = new Vec3(entityCreature.posX, entityCreature.posY, entityCreature.posZ).subtract(vec3);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    private static Vec3 findRandomTargetBlock(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        entityCreature.getRNG();
        if (entityCreature.hasHome()) {
            final double n3 = entityCreature.func_180486_cf().distanceSq(MathHelper.floor_double(entityCreature.posX), MathHelper.floor_double(entityCreature.posY), MathHelper.floor_double(entityCreature.posZ)) + 4.0;
            final double n4 = entityCreature.getMaximumHomeDistance() + n;
            final boolean b = n3 < n4 * n4;
        }
        return new Vec3(0, 0, 0);
    }
}
