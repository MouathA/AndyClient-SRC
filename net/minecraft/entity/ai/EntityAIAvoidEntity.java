package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIAvoidEntity extends EntityAIBase
{
    public final Predicate field_179509_a;
    protected EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    protected Entity closestLivingEntity;
    private float field_179508_f;
    private PathEntity entityPathEntity;
    private PathNavigate entityPathNavigate;
    private Predicate field_179510_i;
    private static final String __OBFID;
    
    public EntityAIAvoidEntity(final EntityCreature theEntity, final Predicate field_179510_i, final float field_179508_f, final double farSpeed, final double nearSpeed) {
        this.field_179509_a = new Predicate() {
            private static final String __OBFID;
            final EntityAIAvoidEntity this$0;
            
            public boolean func_180419_a(final Entity entity) {
                return entity.isEntityAlive() && this.this$0.theEntity.getEntitySenses().canSee(entity);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180419_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001575";
            }
        };
        this.theEntity = theEntity;
        this.field_179510_i = field_179510_i;
        this.field_179508_f = field_179508_f;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.entityPathNavigate = theEntity.getNavigator();
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final List func_175674_a = this.theEntity.worldObj.func_175674_a(this.theEntity, this.theEntity.getEntityBoundingBox().expand(this.field_179508_f, 3.0, this.field_179508_f), Predicates.and(IEntitySelector.field_180132_d, this.field_179509_a, this.field_179510_i));
        if (func_175674_a.isEmpty()) {
            return false;
        }
        this.closestLivingEntity = func_175674_a.get(0);
        final Vec3 randomTargetBlockAway = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if (randomTargetBlockAway == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord);
        return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(randomTargetBlockAway);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }
    
    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }
    
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        }
        else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
    
    static {
        __OBFID = "CL_00001574";
    }
}
