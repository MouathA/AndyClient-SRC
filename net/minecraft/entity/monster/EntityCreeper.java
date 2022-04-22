package net.minecraft.entity.monster;

import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.*;

public class EntityCreeper extends EntityMob
{
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int field_175494_bm;
    private static final String __OBFID;
    
    public EntityCreeper(final World world) {
        super(world);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.field_175494_bm = 0;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate() {
            private static final String __OBFID;
            final EntityCreeper this$0;
            
            public boolean func_179958_a(final Entity entity) {
                return entity instanceof EntityOcelot;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179958_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002224";
            }
        }, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public int getMaxFallHeight() {
        return (this.getAttackTarget() == null) ? 3 : (3 + (int)(this.getHealth() - 1.0f));
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        this.timeSinceIgnited += (int)(n * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, -1);
        this.dataWatcher.addObject(17, 0);
        this.dataWatcher.addObject(18, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            nbtTagCompound.setBoolean("powered", true);
        }
        nbtTagCompound.setShort("Fuse", (short)this.fuseTime);
        nbtTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        nbtTagCompound.setBoolean("ignited", this.func_146078_ca());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.dataWatcher.updateObject(17, (byte)(byte)(nbtTagCompound.getBoolean("powered") ? 1 : 0));
        if (nbtTagCompound.hasKey("Fuse", 99)) {
            this.fuseTime = nbtTagCompound.getShort("Fuse");
        }
        if (nbtTagCompound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = nbtTagCompound.getByte("ExplosionRadius");
        }
        if (nbtTagCompound.getBoolean("ignited")) {
            this.func_146079_cb();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.func_146078_ca()) {
                this.setCreeperState(1);
            }
            final int creeperState = this.getCreeperState();
            if (creeperState > 0 && this.timeSinceIgnited == 0) {
                this.playSound("creeper.primed", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += creeperState;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.func_146077_cc();
            }
        }
        super.onUpdate();
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntitySkeleton) {
            final int idFromItem = Item.getIdFromItem(Items.record_13);
            this.dropItem(Item.getItemById(idFromItem + this.rand.nextInt(Item.getIdFromItem(Items.record_wait) - idFromItem + 1)), 1);
        }
        else if (damageSource.getEntity() instanceof EntityCreeper && damageSource.getEntity() != this && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0f);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return true;
    }
    
    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public float getCreeperFlashIntensity(final float n) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * n) / (this.fuseTime - 2);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    public void setCreeperState(final int n) {
        this.dataWatcher.updateObject(16, (byte)n);
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        super.onStruckByLightning(entityLightningBolt);
        this.dataWatcher.updateObject(17, 1);
    }
    
    @Override
    protected boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.flint_and_steel) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.ignite", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            entityPlayer.swingItem();
            if (!this.worldObj.isRemote) {
                this.func_146079_cb();
                currentItem.damageItem(1, entityPlayer);
                return true;
            }
        }
        return super.interact(entityPlayer);
    }
    
    private void func_146077_cc() {
        if (!this.worldObj.isRemote) {
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * (this.getPowered() ? 2.0f : 1.0f), this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }
    
    public boolean func_146078_ca() {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }
    
    public void func_146079_cb() {
        this.dataWatcher.updateObject(18, 1);
    }
    
    public boolean isAIEnabled() {
        return this.field_175494_bm < 1 && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot");
    }
    
    public void func_175493_co() {
        ++this.field_175494_bm;
    }
    
    static {
        __OBFID = "CL_00001684";
    }
}
