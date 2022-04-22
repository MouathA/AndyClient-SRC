package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import java.util.*;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    protected final Class targetClass;
    private final int targetChance;
    protected final Sorter theNearestAttackableTargetSorter;
    protected Predicate targetEntitySelector;
    protected EntityLivingBase targetEntity;
    private static final String __OBFID;
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class clazz, final boolean b) {
        this(entityCreature, clazz, b, false);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class clazz, final boolean b, final boolean b2) {
        this(entityCreature, clazz, 10, b, b2, null);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class targetClass, final int targetChance, final boolean b, final boolean b2, final Predicate predicate) {
        super(entityCreature, b, b2);
        this.targetClass = targetClass;
        this.targetChance = targetChance;
        this.theNearestAttackableTargetSorter = new Sorter(entityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = new Predicate(predicate) {
            private static final String __OBFID;
            final EntityAINearestAttackableTarget this$0;
            private final Predicate val$p_i45880_6_;
            
            public boolean func_179878_a(final EntityLivingBase entityLivingBase) {
                if (this.val$p_i45880_6_ != null && !this.val$p_i45880_6_.apply(entityLivingBase)) {
                    return false;
                }
                if (entityLivingBase instanceof EntityPlayer) {
                    double targetDistance = this.this$0.getTargetDistance();
                    if (entityLivingBase.isSneaking()) {
                        targetDistance *= 0.800000011920929;
                    }
                    if (entityLivingBase.isInvisible()) {
                        float armorVisibility = ((EntityPlayer)entityLivingBase).getArmorVisibility();
                        if (armorVisibility < 0.1f) {
                            armorVisibility = 0.1f;
                        }
                        targetDistance *= 0.7f * armorVisibility;
                    }
                    if (entityLivingBase.getDistanceToEntity(this.this$0.taskOwner) > targetDistance) {
                        return false;
                    }
                }
                return this.this$0.isSuitableTarget(entityLivingBase, false);
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179878_a((EntityLivingBase)o);
            }
            
            static {
                __OBFID = "CL_00001621";
            }
        };
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        final double targetDistance = this.getTargetDistance();
        final List func_175647_a = this.taskOwner.worldObj.func_175647_a(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), Predicates.and(this.targetEntitySelector, IEntitySelector.field_180132_d));
        Collections.sort((List<Object>)func_175647_a, this.theNearestAttackableTargetSorter);
        if (func_175647_a.isEmpty()) {
            return false;
        }
        this.targetEntity = func_175647_a.get(0);
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    
    static {
        __OBFID = "CL_00001620";
    }
    
    public static class Sorter implements Comparator
    {
        private final Entity theEntity;
        private static final String __OBFID;
        
        public Sorter(final Entity theEntity) {
            this.theEntity = theEntity;
        }
        
        public int compare(final Entity entity, final Entity entity2) {
            final double distanceSqToEntity = this.theEntity.getDistanceSqToEntity(entity);
            final double distanceSqToEntity2 = this.theEntity.getDistanceSqToEntity(entity2);
            return (distanceSqToEntity < distanceSqToEntity2) ? -1 : (distanceSqToEntity > distanceSqToEntity2);
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Entity)o, (Entity)o2);
        }
        
        static {
            __OBFID = "CL_00001622";
        }
    }
}
