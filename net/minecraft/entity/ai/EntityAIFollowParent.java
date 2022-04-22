package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityAIFollowParent extends EntityAIBase
{
    EntityAnimal childAnimal;
    EntityAnimal parentAnimal;
    double field_75347_c;
    private int field_75345_d;
    private static final String __OBFID;
    
    public EntityAIFollowParent(final EntityAnimal childAnimal, final double field_75347_c) {
        this.childAnimal = childAnimal;
        this.field_75347_c = field_75347_c;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        final List entitiesWithinAABB = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
        EntityAnimal parentAnimal = null;
        double n = Double.MAX_VALUE;
        for (final EntityAnimal entityAnimal : entitiesWithinAABB) {
            if (entityAnimal.getGrowingAge() >= 0) {
                final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(entityAnimal);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                parentAnimal = entityAnimal;
            }
        }
        if (parentAnimal == null) {
            return false;
        }
        if (n < 9.0) {
            return false;
        }
        this.parentAnimal = parentAnimal;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        if (!this.parentAnimal.isEntityAlive()) {
            return false;
        }
        final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        return distanceSqToEntity >= 9.0 && distanceSqToEntity <= 256.0;
    }
    
    @Override
    public void startExecuting() {
        this.field_75345_d = 0;
    }
    
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }
    
    @Override
    public void updateTask() {
        final int field_75345_d = this.field_75345_d - 1;
        this.field_75345_d = field_75345_d;
        if (field_75345_d <= 0) {
            this.field_75345_d = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_75347_c);
        }
    }
    
    static {
        __OBFID = "CL_00001586";
    }
}
