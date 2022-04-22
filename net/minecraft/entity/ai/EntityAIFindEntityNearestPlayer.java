package net.minecraft.entity.ai;

import com.google.common.base.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase
{
    private static final Logger field_179436_a;
    private EntityLiving field_179434_b;
    private final Predicate field_179435_c;
    private final EntityAINearestAttackableTarget.Sorter field_179432_d;
    private EntityLivingBase field_179433_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002248";
        field_179436_a = LogManager.getLogger();
    }
    
    public EntityAIFindEntityNearestPlayer(final EntityLiving field_179434_b) {
        this.field_179434_b = field_179434_b;
        if (field_179434_b instanceof EntityCreature) {
            EntityAIFindEntityNearestPlayer.field_179436_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }
        this.field_179435_c = new Predicate() {
            private static final String __OBFID;
            final EntityAIFindEntityNearestPlayer this$0;
            
            public boolean func_179880_a(final Entity entity) {
                if (!(entity instanceof EntityPlayer)) {
                    return false;
                }
                double func_179431_f = this.this$0.func_179431_f();
                if (entity.isSneaking()) {
                    func_179431_f *= 0.800000011920929;
                }
                if (entity.isInvisible()) {
                    float armorVisibility = ((EntityPlayer)entity).getArmorVisibility();
                    if (armorVisibility < 0.1f) {
                        armorVisibility = 0.1f;
                    }
                    func_179431_f *= 0.7f * armorVisibility;
                }
                return entity.getDistanceToEntity(EntityAIFindEntityNearestPlayer.access$0(this.this$0)) <= func_179431_f && EntityAITarget.func_179445_a(EntityAIFindEntityNearestPlayer.access$0(this.this$0), (EntityLivingBase)entity, false, true);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179880_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002247";
            }
        };
        this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(field_179434_b);
    }
    
    @Override
    public boolean shouldExecute() {
        final double func_179431_f = this.func_179431_f();
        final List func_175647_a = this.field_179434_b.worldObj.func_175647_a(EntityPlayer.class, this.field_179434_b.getEntityBoundingBox().expand(func_179431_f, 4.0, func_179431_f), this.field_179435_c);
        Collections.sort((List<Object>)func_175647_a, this.field_179432_d);
        if (func_175647_a.isEmpty()) {
            return false;
        }
        this.field_179433_e = func_175647_a.get(0);
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.field_179434_b.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        final Team team = this.field_179434_b.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return false;
        }
        final double func_179431_f = this.func_179431_f();
        return this.field_179434_b.getDistanceSqToEntity(attackTarget) <= func_179431_f * func_179431_f && (!(attackTarget instanceof EntityPlayerMP) || !((EntityPlayerMP)attackTarget).theItemInWorldManager.isCreative());
    }
    
    @Override
    public void startExecuting() {
        this.field_179434_b.setAttackTarget(this.field_179433_e);
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        this.field_179434_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    protected double func_179431_f() {
        final IAttributeInstance entityAttribute = this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (entityAttribute == null) ? 16.0 : entityAttribute.getAttributeValue();
    }
    
    static EntityLiving access$0(final EntityAIFindEntityNearestPlayer entityAIFindEntityNearestPlayer) {
        return entityAIFindEntityNearestPlayer.field_179434_b;
    }
}
