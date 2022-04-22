package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import net.minecraft.util.*;

public interface IEntityMultiPart
{
    World func_82194_d();
    
    boolean attackEntityFromPart(final EntityDragonPart p0, final DamageSource p1, final float p2);
}
