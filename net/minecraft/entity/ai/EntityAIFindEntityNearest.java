package net.minecraft.entity.ai;

import com.google.common.base.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityAIFindEntityNearest extends EntityAIBase
{
    private static final Logger field_179444_a;
    private EntityLiving field_179442_b;
    private final Predicate field_179443_c;
    private final EntityAINearestAttackableTarget.Sorter field_179440_d;
    private EntityLivingBase field_179441_e;
    private Class field_179439_f;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002250";
        field_179444_a = LogManager.getLogger();
    }
    
    public EntityAIFindEntityNearest(final EntityLiving field_179442_b, final Class field_179439_f) {
        this.field_179442_b = field_179442_b;
        this.field_179439_f = field_179439_f;
        if (field_179442_b instanceof EntityCreature) {
            EntityAIFindEntityNearest.field_179444_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }
        this.field_179443_c = new Predicate() {
            private static final String __OBFID;
            final EntityAIFindEntityNearest this$0;
            
            public boolean func_179876_a(final EntityLivingBase entityLivingBase) {
                double func_179438_f = this.this$0.func_179438_f();
                if (entityLivingBase.isSneaking()) {
                    func_179438_f *= 0.800000011920929;
                }
                return !entityLivingBase.isInvisible() && entityLivingBase.getDistanceToEntity(EntityAIFindEntityNearest.access$0(this.this$0)) <= func_179438_f && EntityAITarget.func_179445_a(EntityAIFindEntityNearest.access$0(this.this$0), entityLivingBase, false, true);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179876_a((EntityLivingBase)o);
            }
            
            static {
                __OBFID = "CL_00002249";
            }
        };
        this.field_179440_d = new EntityAINearestAttackableTarget.Sorter(field_179442_b);
    }
    
    @Override
    public boolean shouldExecute() {
        final double func_179438_f = this.func_179438_f();
        final List func_175647_a = this.field_179442_b.worldObj.func_175647_a(this.field_179439_f, this.field_179442_b.getEntityBoundingBox().expand(func_179438_f, 4.0, func_179438_f), this.field_179443_c);
        Collections.sort((List<Object>)func_175647_a, this.field_179440_d);
        if (func_175647_a.isEmpty()) {
            return false;
        }
        this.field_179441_e = func_175647_a.get(0);
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.field_179442_b.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        final double func_179438_f = this.func_179438_f();
        return this.field_179442_b.getDistanceSqToEntity(attackTarget) <= func_179438_f * func_179438_f && (!(attackTarget instanceof EntityPlayerMP) || !((EntityPlayerMP)attackTarget).theItemInWorldManager.isCreative());
    }
    
    @Override
    public void startExecuting() {
        this.field_179442_b.setAttackTarget(this.field_179441_e);
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        this.field_179442_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    protected double func_179438_f() {
        final IAttributeInstance entityAttribute = this.field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (entityAttribute == null) ? 16.0 : entityAttribute.getAttributeValue();
    }
    
    static EntityLiving access$0(final EntityAIFindEntityNearest entityAIFindEntityNearest) {
        return entityAIFindEntityNearest.field_179442_b;
    }
}
