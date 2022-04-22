package DTool.util;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import javax.vecmath.*;

public class EntityUtil
{
    public static boolean isAnimal(final Entity entity) {
        return entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob || entity instanceof EntityGolem;
    }
    
    public static Vector3d getInterpolatedPos(final Entity entity, final float n) {
        final de.gerrygames.viarewind.utils.math.Vector3d vector3d = new de.gerrygames.viarewind.utils.math.Vector3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
        return null;
    }
}
