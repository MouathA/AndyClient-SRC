package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    private static final String __OBFID;
    
    public EntitySnowman(final World world) {
        super(world);
        this.setSize(0.7f, 1.9f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.mobSelector));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            final int floor_double = MathHelper.floor_double(this.posX);
            final int floor_double2 = MathHelper.floor_double(this.posY);
            final int floor_double3 = MathHelper.floor_double(this.posZ);
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0f);
            }
            if (this.worldObj.getBiomeGenForCoords(new BlockPos(floor_double, 0, floor_double3)).func_180626_a(new BlockPos(floor_double, floor_double2, floor_double3)) > 1.0f) {
                this.attackEntityFrom(DamageSource.onFire, 1.0f);
            }
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Items.snowball;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(16)) {
            this.dropItem(Items.snowball, 1);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        final EntitySnowball entitySnowball = new EntitySnowball(this.worldObj, this);
        final double n2 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.100000023841858;
        final double n3 = entityLivingBase.posX - this.posX;
        final double n4 = n2 - entitySnowball.posY;
        final double n5 = entityLivingBase.posZ - this.posZ;
        entitySnowball.setThrowableHeading(n3, n4 + MathHelper.sqrt_double(n3 * n3 + n5 * n5) * 0.2f, n5, 1.6f, 12.0f);
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(entitySnowball);
    }
    
    @Override
    public float getEyeHeight() {
        return 1.7f;
    }
    
    static {
        __OBFID = "CL_00001650";
    }
}
