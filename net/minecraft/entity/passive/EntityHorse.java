package net.minecraft.entity.passive;

import com.google.common.base.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.inventory.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private static final Predicate horseBreedingSelector;
    private static final IAttribute horseJumpStrength;
    private static final String[] horseArmorTextures;
    private static final String[] field_110273_bx;
    private static final int[] armorValues;
    private static final String[] horseTextures;
    private static final String[] field_110269_bA;
    private static final String[] horseMarkingTextures;
    private static final String[] field_110292_bC;
    private int eatingHaystackCounter;
    private int openMouthCounter;
    private int jumpRearingCounter;
    public int field_110278_bp;
    public int field_110279_bq;
    protected boolean horseJumping;
    private AnimalChest horseChest;
    private boolean hasReproduced;
    protected int temper;
    protected float jumpPower;
    private boolean field_110294_bI;
    private float headLean;
    private float prevHeadLean;
    private float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    private int gallopTime;
    private String field_110286_bQ;
    private String[] field_110280_bR;
    private boolean field_175508_bO;
    private static final String __OBFID;
    private static final String[] llllIIIIIIIIIlI;
    private static String[] llllIIIIIIlIllI;
    
    static {
        lIllIIIlIIIllIII();
        lIllIIIlIIIlIlIl();
        __OBFID = EntityHorse.llllIIIIIIIIIlI[0];
        horseBreedingSelector = new Predicate() {
            private static final String __OBFID;
            
            public boolean func_179873_a(final Entity entity) {
                return entity instanceof EntityHorse && ((EntityHorse)entity).func_110205_ce();
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179873_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00001642";
            }
        };
        horseJumpStrength = new RangedAttribute(null, EntityHorse.llllIIIIIIIIIlI[1], 0.7, 0.0, 2.0).setDescription(EntityHorse.llllIIIIIIIIIlI[2]).setShouldWatch(true);
        horseArmorTextures = new String[] { null, EntityHorse.llllIIIIIIIIIlI[3], EntityHorse.llllIIIIIIIIIlI[4], EntityHorse.llllIIIIIIIIIlI[5] };
        field_110273_bx = new String[] { EntityHorse.llllIIIIIIIIIlI[6], EntityHorse.llllIIIIIIIIIlI[7], EntityHorse.llllIIIIIIIIIlI[8], EntityHorse.llllIIIIIIIIIlI[9] };
        armorValues = new int[] { 0, 5, 7, 11 };
        horseTextures = new String[] { EntityHorse.llllIIIIIIIIIlI[10], EntityHorse.llllIIIIIIIIIlI[11], EntityHorse.llllIIIIIIIIIlI[12], EntityHorse.llllIIIIIIIIIlI[13], EntityHorse.llllIIIIIIIIIlI[14], EntityHorse.llllIIIIIIIIIlI[15], EntityHorse.llllIIIIIIIIIlI[16] };
        field_110269_bA = new String[] { EntityHorse.llllIIIIIIIIIlI[17], EntityHorse.llllIIIIIIIIIlI[18], EntityHorse.llllIIIIIIIIIlI[19], EntityHorse.llllIIIIIIIIIlI[20], EntityHorse.llllIIIIIIIIIlI[21], EntityHorse.llllIIIIIIIIIlI[22], EntityHorse.llllIIIIIIIIIlI[23] };
        horseMarkingTextures = new String[] { null, EntityHorse.llllIIIIIIIIIlI[24], EntityHorse.llllIIIIIIIIIlI[25], EntityHorse.llllIIIIIIIIIlI[26], EntityHorse.llllIIIIIIIIIlI[27] };
        field_110292_bC = new String[] { EntityHorse.llllIIIIIIIIIlI[28], EntityHorse.llllIIIIIIIIIlI[29], EntityHorse.llllIIIIIIIIIlI[30], EntityHorse.llllIIIIIIIIIlI[31], EntityHorse.llllIIIIIIIIIlI[32] };
    }
    
    public EntityHorse(final World world) {
        super(world);
        this.field_110280_bR = new String[3];
        this.field_175508_bO = false;
        this.setSize(1.4f, 1.6f);
        this.setChested(this.isImmuneToFire = false);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.func_110226_cD();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(19, 0);
        this.dataWatcher.addObject(20, 0);
        this.dataWatcher.addObject(21, String.valueOf(EntityHorse.llllIIIIIIIIIlI[33]));
        this.dataWatcher.addObject(22, 0);
    }
    
    public void setHorseType(final int n) {
        this.dataWatcher.updateObject(19, (byte)n);
        this.func_110230_cF();
    }
    
    public int getHorseType() {
        return this.dataWatcher.getWatchableObjectByte(19);
    }
    
    public void setHorseVariant(final int n) {
        this.dataWatcher.updateObject(20, n);
        this.func_110230_cF();
    }
    
    public int getHorseVariant() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        switch (this.getHorseType()) {
            default: {
                return StatCollector.translateToLocal(EntityHorse.llllIIIIIIIIIlI[34]);
            }
            case 1: {
                return StatCollector.translateToLocal(EntityHorse.llllIIIIIIIIIlI[35]);
            }
            case 2: {
                return StatCollector.translateToLocal(EntityHorse.llllIIIIIIIIIlI[36]);
            }
            case 3: {
                return StatCollector.translateToLocal(EntityHorse.llllIIIIIIIIIlI[37]);
            }
            case 4: {
                return StatCollector.translateToLocal(EntityHorse.llllIIIIIIIIIlI[38]);
            }
        }
    }
    
    private boolean getHorseWatchableBoolean(final int n) {
        return (this.dataWatcher.getWatchableObjectInt(16) & n) != 0x0;
    }
    
    private void setHorseWatchableBoolean(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(16);
        if (b) {
            this.dataWatcher.updateObject(16, watchableObjectInt | n);
        }
        else {
            this.dataWatcher.updateObject(16, watchableObjectInt & ~n);
        }
    }
    
    public boolean isAdultHorse() {
        return !this.isChild();
    }
    
    public boolean isTame() {
        return this.getHorseWatchableBoolean(2);
    }
    
    public boolean func_110253_bW() {
        return this.isAdultHorse();
    }
    
    public String func_152119_ch() {
        return this.dataWatcher.getWatchableObjectString(21);
    }
    
    public void func_152120_b(final String s) {
        this.dataWatcher.updateObject(21, s);
    }
    
    public float getHorseSize() {
        final int growingAge = this.getGrowingAge();
        return (growingAge >= 0) ? 1.0f : (0.5f + (-24000 - growingAge) / -24000.0f * 0.5f);
    }
    
    @Override
    public void setScaleForAge(final boolean b) {
        if (b) {
            this.setScale(this.getHorseSize());
        }
        else {
            this.setScale(1.0f);
        }
    }
    
    public boolean isHorseJumping() {
        return this.horseJumping;
    }
    
    public void setHorseTamed(final boolean b) {
        this.setHorseWatchableBoolean(2, b);
    }
    
    public void setHorseJumping(final boolean horseJumping) {
        this.horseJumping = horseJumping;
    }
    
    public boolean allowLeashing() {
        return !this.isUndead() && super.allowLeashing();
    }
    
    @Override
    protected void func_142017_o(final float n) {
        if (n > 6.0f && this.isEatingHaystack()) {
            this.setEatingHaystack(false);
        }
    }
    
    public boolean isChested() {
        return this.getHorseWatchableBoolean(8);
    }
    
    public int func_110241_cb() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }
    
    private int getHorseArmorIndex(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        return (item == Items.iron_horse_armor) ? 1 : ((item == Items.golden_horse_armor) ? 2 : ((item == Items.diamond_horse_armor) ? 3 : 0));
    }
    
    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(32);
    }
    
    public boolean isRearing() {
        return this.getHorseWatchableBoolean(64);
    }
    
    public boolean func_110205_ce() {
        return this.getHorseWatchableBoolean(16);
    }
    
    public boolean getHasReproduced() {
        return this.hasReproduced;
    }
    
    public void setHorseArmorStack(final ItemStack itemStack) {
        this.dataWatcher.updateObject(22, this.getHorseArmorIndex(itemStack));
        this.func_110230_cF();
    }
    
    public void func_110242_l(final boolean b) {
        this.setHorseWatchableBoolean(16, b);
    }
    
    public void setChested(final boolean b) {
        this.setHorseWatchableBoolean(8, b);
    }
    
    public void setHasReproduced(final boolean hasReproduced) {
        this.hasReproduced = hasReproduced;
    }
    
    public void setHorseSaddled(final boolean b) {
        this.setHorseWatchableBoolean(4, b);
    }
    
    public int getTemper() {
        return this.temper;
    }
    
    public void setTemper(final int temper) {
        this.temper = temper;
    }
    
    public int increaseTemper(final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getTemper() + n, 0, this.getMaxTemper());
        this.setTemper(clamp_int);
        return clamp_int;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity entity = damageSource.getEntity();
        return (this.riddenByEntity == null || !this.riddenByEntity.equals(entity)) && super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public int getTotalArmorValue() {
        return EntityHorse.armorValues[this.func_110241_cb()];
    }
    
    @Override
    public boolean canBePushed() {
        return this.riddenByEntity == null;
    }
    
    public boolean prepareChunkForSpawn() {
        this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
        return true;
    }
    
    public void dropChests() {
        if (!this.worldObj.isRemote && this.isChested()) {
            this.dropItem(Item.getItemFromBlock(Blocks.chest), 1);
            this.setChested(false);
        }
    }
    
    private void func_110266_cB() {
        this.openHorseMouth();
        if (!this.isSlient()) {
            this.worldObj.playSoundAtEntity(this, EntityHorse.llllIIIIIIIIIlI[39], 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (n > 1.0f) {
            this.playSound(EntityHorse.llllIIIIIIIIIlI[40], 0.4f, 1.0f);
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int((n * 0.5f - 3.0f) * n2);
        if (ceiling_float_int > 0) {
            this.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            }
            final Block block = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2 - this.prevRotationYaw, this.posZ)).getBlock();
            if (block.getMaterial() != Material.air && !this.isSlient()) {
                final Block.SoundType stepSound = block.stepSound;
                this.worldObj.playSoundAtEntity(this, stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    private int func_110225_cC() {
        final int horseType = this.getHorseType();
        return (this.isChested() && (horseType == 1 || horseType == 2)) ? 17 : 2;
    }
    
    private void func_110226_cD() {
        final AnimalChest horseChest = this.horseChest;
        (this.horseChest = new AnimalChest(EntityHorse.llllIIIIIIIIIlI[41], this.func_110225_cC())).func_110133_a(this.getName());
        if (horseChest != null) {
            horseChest.func_110132_b(this);
            for (int min = Math.min(horseChest.getSizeInventory(), this.horseChest.getSizeInventory()), i = 0; i < min; ++i) {
                final ItemStack stackInSlot = horseChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    this.horseChest.setInventorySlotContents(i, stackInSlot.copy());
                }
            }
        }
        this.horseChest.func_110134_a(this);
        this.func_110232_cE();
    }
    
    private void func_110232_cE() {
        if (!this.worldObj.isRemote) {
            this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
            if (this.canWearArmor()) {
                this.setHorseArmorStack(this.horseChest.getStackInSlot(1));
            }
        }
    }
    
    @Override
    public void onInventoryChanged(final InventoryBasic inventoryBasic) {
        final int func_110241_cb = this.func_110241_cb();
        final boolean horseSaddled = this.isHorseSaddled();
        this.func_110232_cE();
        if (this.ticksExisted > 20) {
            if (func_110241_cb == 0 && func_110241_cb != this.func_110241_cb()) {
                this.playSound(EntityHorse.llllIIIIIIIIIlI[42], 0.5f, 1.0f);
            }
            else if (func_110241_cb != this.func_110241_cb()) {
                this.playSound(EntityHorse.llllIIIIIIIIIlI[43], 0.5f, 1.0f);
            }
            if (!horseSaddled && this.isHorseSaddled()) {
                this.playSound(EntityHorse.llllIIIIIIIIIlI[44], 0.5f, 1.0f);
            }
        }
    }
    
    @Override
    public boolean getCanSpawnHere() {
        this.prepareChunkForSpawn();
        return super.getCanSpawnHere();
    }
    
    protected EntityHorse getClosestHorse(final Entity entity, final double n) {
        double n2 = Double.MAX_VALUE;
        Entity entity2 = null;
        for (final Entity entity3 : this.worldObj.func_175674_a(entity, entity.getEntityBoundingBox().addCoord(n, n, n), EntityHorse.horseBreedingSelector)) {
            final double distanceSq = entity3.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (distanceSq < n2) {
                entity2 = entity3;
                n2 = distanceSq;
            }
        }
        return (EntityHorse)entity2;
    }
    
    public double getHorseJumpStrength() {
        return this.getEntityAttribute(EntityHorse.horseJumpStrength).getAttributeValue();
    }
    
    @Override
    protected String getDeathSound() {
        this.openHorseMouth();
        final int horseType = this.getHorseType();
        return (horseType == 3) ? EntityHorse.llllIIIIIIIIIlI[45] : ((horseType == 4) ? EntityHorse.llllIIIIIIIIIlI[46] : ((horseType != 1 && horseType != 2) ? EntityHorse.llllIIIIIIIIIlI[47] : EntityHorse.llllIIIIIIIIIlI[48]));
    }
    
    @Override
    protected Item getDropItem() {
        final boolean b = this.rand.nextInt(4) == 0;
        final int horseType = this.getHorseType();
        return (horseType == 4) ? Items.bone : ((horseType == 3) ? (b ? null : Items.rotten_flesh) : Items.leather);
    }
    
    @Override
    protected String getHurtSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(3) == 0) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        return (horseType == 3) ? EntityHorse.llllIIIIIIIIIlI[49] : ((horseType == 4) ? EntityHorse.llllIIIIIIIIIlI[50] : ((horseType != 1 && horseType != 2) ? EntityHorse.llllIIIIIIIIIlI[51] : EntityHorse.llllIIIIIIIIIlI[52]));
    }
    
    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(4);
    }
    
    @Override
    protected String getLivingSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked()) {
            this.makeHorseRear();
        }
        final int horseType = this.getHorseType();
        return (horseType == 3) ? EntityHorse.llllIIIIIIIIIlI[53] : ((horseType == 4) ? EntityHorse.llllIIIIIIIIIlI[54] : ((horseType != 1 && horseType != 2) ? EntityHorse.llllIIIIIIIIIlI[55] : EntityHorse.llllIIIIIIIIIlI[56]));
    }
    
    protected String getAngrySoundName() {
        this.openHorseMouth();
        this.makeHorseRear();
        final int horseType = this.getHorseType();
        return (horseType != 3 && horseType != 4) ? ((horseType != 1 && horseType != 2) ? EntityHorse.llllIIIIIIIIIlI[57] : EntityHorse.llllIIIIIIIIIlI[58]) : null;
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        Block.SoundType soundType = block.stepSound;
        if (this.worldObj.getBlockState(blockPos.offsetUp()).getBlock() == Blocks.snow_layer) {
            soundType = Blocks.snow_layer.stepSound;
        }
        if (!block.getMaterial().isLiquid()) {
            final int horseType = this.getHorseType();
            if (this.riddenByEntity != null && horseType != 1 && horseType != 2) {
                ++this.gallopTime;
                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playSound(EntityHorse.llllIIIIIIIIIlI[59], soundType.getVolume() * 0.15f, soundType.getFrequency());
                    if (horseType == 0 && this.rand.nextInt(10) == 0) {
                        this.playSound(EntityHorse.llllIIIIIIIIIlI[60], soundType.getVolume() * 0.6f, soundType.getFrequency());
                    }
                }
                else if (this.gallopTime <= 5) {
                    this.playSound(EntityHorse.llllIIIIIIIIIlI[61], soundType.getVolume() * 0.15f, soundType.getFrequency());
                }
            }
            else if (soundType == Block.soundTypeWood) {
                this.playSound(EntityHorse.llllIIIIIIIIIlI[62], soundType.getVolume() * 0.15f, soundType.getFrequency());
            }
            else {
                this.playSound(EntityHorse.llllIIIIIIIIIlI[63], soundType.getVolume() * 0.15f, soundType.getFrequency());
            }
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(EntityHorse.horseJumpStrength);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552);
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }
    
    public int getMaxTemper() {
        return 100;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }
    
    @Override
    public int getTalkInterval() {
        return 400;
    }
    
    public boolean func_110239_cn() {
        return this.getHorseType() == 0 || this.func_110241_cb() > 0;
    }
    
    private void func_110230_cF() {
        this.field_110286_bQ = null;
    }
    
    public boolean func_175507_cI() {
        return this.field_175508_bO;
    }
    
    private void setHorseTexturePaths() {
        this.field_110286_bQ = EntityHorse.llllIIIIIIIIIlI[64];
        this.field_110280_bR[0] = null;
        this.field_110280_bR[1] = null;
        this.field_110280_bR[2] = null;
        final int horseType = this.getHorseType();
        final int horseVariant = this.getHorseVariant();
        if (horseType == 0) {
            final int n = horseVariant & 0xFF;
            final int n2 = (horseVariant & 0xFF00) >> 8;
            if (n >= EntityHorse.horseTextures.length) {
                this.field_175508_bO = false;
                return;
            }
            this.field_110280_bR[0] = EntityHorse.horseTextures[n];
            this.field_110286_bQ = String.valueOf(this.field_110286_bQ) + EntityHorse.field_110269_bA[n];
            if (n2 >= EntityHorse.horseMarkingTextures.length) {
                this.field_175508_bO = false;
                return;
            }
            this.field_110280_bR[1] = EntityHorse.horseMarkingTextures[n2];
            this.field_110286_bQ = String.valueOf(this.field_110286_bQ) + EntityHorse.field_110292_bC[n2];
        }
        else {
            this.field_110280_bR[0] = EntityHorse.llllIIIIIIIIIlI[65];
            this.field_110286_bQ = String.valueOf(this.field_110286_bQ) + EntityHorse.llllIIIIIIIIIlI[66] + horseType + EntityHorse.llllIIIIIIIIIlI[67];
        }
        final int func_110241_cb = this.func_110241_cb();
        if (func_110241_cb >= EntityHorse.horseArmorTextures.length) {
            this.field_175508_bO = false;
        }
        else {
            this.field_110280_bR[2] = EntityHorse.horseArmorTextures[func_110241_cb];
            this.field_110286_bQ = String.valueOf(this.field_110286_bQ) + EntityHorse.field_110273_bx[func_110241_cb];
            this.field_175508_bO = true;
        }
    }
    
    public String getHorseTexture() {
        if (this.field_110286_bQ == null) {
            this.setHorseTexturePaths();
        }
        return this.field_110286_bQ;
    }
    
    public String[] getVariantTexturePaths() {
        if (this.field_110286_bQ == null) {
            this.setHorseTexturePaths();
        }
        return this.field_110280_bR;
    }
    
    public void openGUI(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityPlayer) && this.isTame()) {
            this.horseChest.func_110133_a(this.getName());
            entityPlayer.displayGUIHorse(this, this.horseChest);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.spawn_egg) {
            return super.interact(entityPlayer);
        }
        if (!this.isTame() && this.isUndead()) {
            return false;
        }
        if (this.isTame() && this.isAdultHorse() && entityPlayer.isSneaking()) {
            this.openGUI(entityPlayer);
            return true;
        }
        if (this.func_110253_bW() && this.riddenByEntity != null) {
            return super.interact(entityPlayer);
        }
        if (currentItem != null) {
            int n = 0;
            if (this.canWearArmor()) {
                int n2 = -1;
                if (currentItem.getItem() == Items.iron_horse_armor) {
                    n2 = 1;
                }
                else if (currentItem.getItem() == Items.golden_horse_armor) {
                    n2 = 2;
                }
                else if (currentItem.getItem() == Items.diamond_horse_armor) {
                    n2 = 3;
                }
                if (n2 >= 0) {
                    if (!this.isTame()) {
                        this.makeHorseRearWithSound();
                        return true;
                    }
                    this.openGUI(entityPlayer);
                    return true;
                }
            }
            if (n == 0 && !this.isUndead()) {
                float n3 = 0.0f;
                int n4 = 0;
                int n5 = 0;
                if (currentItem.getItem() == Items.wheat) {
                    n3 = 2.0f;
                    n4 = 20;
                    n5 = 3;
                }
                else if (currentItem.getItem() == Items.sugar) {
                    n3 = 1.0f;
                    n4 = 30;
                    n5 = 3;
                }
                else if (Block.getBlockFromItem(currentItem.getItem()) == Blocks.hay_block) {
                    n3 = 20.0f;
                    n4 = 180;
                }
                else if (currentItem.getItem() == Items.apple) {
                    n3 = 3.0f;
                    n4 = 60;
                    n5 = 3;
                }
                else if (currentItem.getItem() == Items.golden_carrot) {
                    n3 = 4.0f;
                    n4 = 60;
                    n5 = 5;
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        n = 1;
                        this.setInLove(entityPlayer);
                    }
                }
                else if (currentItem.getItem() == Items.golden_apple) {
                    n3 = 10.0f;
                    n4 = 240;
                    n5 = 10;
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        n = 1;
                        this.setInLove(entityPlayer);
                    }
                }
                if (this.getHealth() < this.getMaxHealth() && n3 > 0.0f) {
                    this.heal(n3);
                    n = 1;
                }
                if (!this.isAdultHorse() && n4 > 0) {
                    this.addGrowth(n4);
                    n = 1;
                }
                if (n5 > 0 && (n != 0 || !this.isTame()) && n5 < this.getMaxTemper()) {
                    n = 1;
                    this.increaseTemper(n5);
                }
                if (n != 0) {
                    this.func_110266_cB();
                }
            }
            if (!this.isTame() && n == 0) {
                if (currentItem != null && currentItem.interactWithEntity(entityPlayer, this)) {
                    return true;
                }
                this.makeHorseRearWithSound();
                return true;
            }
            else {
                if (n == 0 && this.canCarryChest() && !this.isChested() && currentItem.getItem() == Item.getItemFromBlock(Blocks.chest)) {
                    this.setChested(true);
                    this.playSound(EntityHorse.llllIIIIIIIIIlI[68], 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                    n = 1;
                    this.func_110226_cD();
                }
                if (n == 0 && this.func_110253_bW() && !this.isHorseSaddled() && currentItem.getItem() == Items.saddle) {
                    this.openGUI(entityPlayer);
                    return true;
                }
                if (n != 0) {
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = currentItem;
                        if (--itemStack.stackSize == 0) {
                            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                        }
                    }
                    return true;
                }
            }
        }
        if (!this.func_110253_bW() || this.riddenByEntity != null) {
            return super.interact(entityPlayer);
        }
        if (currentItem != null && currentItem.interactWithEntity(entityPlayer, this)) {
            return true;
        }
        this.func_110237_h(entityPlayer);
        return true;
    }
    
    private void func_110237_h(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = this.rotationYaw;
        entityPlayer.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
    }
    
    public boolean canWearArmor() {
        return this.getHorseType() == 0;
    }
    
    public boolean canCarryChest() {
        final int horseType = this.getHorseType();
        return horseType == 2 || horseType == 1;
    }
    
    @Override
    protected boolean isMovementBlocked() {
        return (this.riddenByEntity != null && this.isHorseSaddled()) || this.isEatingHaystack() || this.isRearing();
    }
    
    public boolean isUndead() {
        final int horseType = this.getHorseType();
        return horseType == 3 || horseType == 4;
    }
    
    public boolean isSterile() {
        return this.isUndead() || this.getHorseType() == 2;
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return false;
    }
    
    private void func_110210_cH() {
        this.field_110278_bp = 1;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.worldObj.isRemote) {
            this.dropChestItems();
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.rand.nextInt(200) == 0) {
            this.func_110210_cH();
        }
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (!this.isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ))).getBlock() == Blocks.grass) {
                this.setEatingHaystack(true);
            }
            if (this.isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
                this.eatingHaystackCounter = 0;
                this.setEatingHaystack(false);
            }
            if (this.func_110205_ce() && !this.isAdultHorse() && !this.isEatingHaystack()) {
                final EntityHorse closestHorse = this.getClosestHorse(this, 16.0);
                if (closestHorse != null && this.getDistanceSqToEntity(closestHorse) > 4.0) {
                    this.navigator.getPathToEntityLiving(closestHorse);
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
            this.dataWatcher.func_111144_e();
            this.func_110230_cF();
        }
        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean(128, false);
        }
        if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }
        if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
            this.field_110278_bp = 0;
        }
        if (this.field_110279_bq > 0) {
            ++this.field_110279_bq;
            if (this.field_110279_bq > 300) {
                this.field_110279_bq = 0;
            }
        }
        this.prevHeadLean = this.headLean;
        if (this.isEatingHaystack()) {
            this.headLean += (1.0f - this.headLean) * 0.4f + 0.05f;
            if (this.headLean > 1.0f) {
                this.headLean = 1.0f;
            }
        }
        else {
            this.headLean += (0.0f - this.headLean) * 0.4f - 0.05f;
            if (this.headLean < 0.0f) {
                this.headLean = 0.0f;
            }
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            final float n = 0.0f;
            this.headLean = n;
            this.prevHeadLean = n;
            this.rearingAmount += (1.0f - this.rearingAmount) * 0.4f + 0.05f;
            if (this.rearingAmount > 1.0f) {
                this.rearingAmount = 1.0f;
            }
        }
        else {
            this.field_110294_bI = false;
            this.rearingAmount += (0.8f * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6f - 0.05f;
            if (this.rearingAmount < 0.0f) {
                this.rearingAmount = 0.0f;
            }
        }
        this.prevMouthOpenness = this.mouthOpenness;
        if (this.getHorseWatchableBoolean(128)) {
            this.mouthOpenness += (1.0f - this.mouthOpenness) * 0.7f + 0.05f;
            if (this.mouthOpenness > 1.0f) {
                this.mouthOpenness = 1.0f;
            }
        }
        else {
            this.mouthOpenness += (0.0f - this.mouthOpenness) * 0.7f - 0.05f;
            if (this.mouthOpenness < 0.0f) {
                this.mouthOpenness = 0.0f;
            }
        }
    }
    
    private void openHorseMouth() {
        if (!this.worldObj.isRemote) {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(128, true);
        }
    }
    
    private boolean canMate() {
        return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.isSterile() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    
    @Override
    public void setEating(final boolean b) {
        this.setHorseWatchableBoolean(32, b);
    }
    
    public void setEatingHaystack(final boolean eating) {
        this.setEating(eating);
    }
    
    public void setRearing(final boolean b) {
        if (b) {
            this.setEatingHaystack(false);
        }
        this.setHorseWatchableBoolean(64, b);
    }
    
    private void makeHorseRear() {
        if (!this.worldObj.isRemote) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }
    }
    
    public void makeHorseRearWithSound() {
        this.makeHorseRear();
        final String angrySoundName = this.getAngrySoundName();
        if (angrySoundName != null) {
            this.playSound(angrySoundName, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    public void dropChestItems() {
        this.dropItemsInChest(this, this.horseChest);
        this.dropChests();
    }
    
    private void dropItemsInChest(final Entity entity, final AnimalChest animalChest) {
        if (animalChest != null && !this.worldObj.isRemote) {
            for (int i = 0; i < animalChest.getSizeInventory(); ++i) {
                final ItemStack stackInSlot = animalChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    this.entityDropItem(stackInSlot, 0.0f);
                }
            }
        }
    }
    
    public boolean setTamedBy(final EntityPlayer entityPlayer) {
        this.func_152120_b(entityPlayer.getUniqueID().toString());
        this.setHorseTamed(true);
        return true;
    }
    
    @Override
    public void moveEntityWithHeading(float n, float moveForward) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            n = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5f;
            moveForward = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (moveForward <= 0.0f) {
                moveForward *= 0.25f;
                this.gallopTime = 0;
            }
            if (this.onGround && this.jumpPower == 0.0f && this.isRearing() && !this.field_110294_bI) {
                n = 0.0f;
                moveForward = 0.0f;
            }
            if (this.jumpPower > 0.0f && !this.isHorseJumping() && this.onGround) {
                this.motionY = this.getHorseJumpStrength() * this.jumpPower;
                if (this.isPotionActive(Potion.jump)) {
                    this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                }
                this.setHorseJumping(true);
                this.isAirBorne = true;
                if (moveForward > 0.0f) {
                    final float sin = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
                    final float cos = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
                    this.motionX += -0.4f * sin * this.jumpPower;
                    this.motionZ += 0.4f * cos * this.jumpPower;
                    this.playSound(EntityHorse.llllIIIIIIIIIlI[69], 0.4f, 1.0f);
                }
                this.jumpPower = 0.0f;
            }
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (!this.worldObj.isRemote) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(n, moveForward);
            }
            if (this.onGround) {
                this.jumpPower = 0.0f;
                this.setHorseJumping(false);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double n2 = this.posX - this.prevPosX;
            final double n3 = this.posZ - this.prevPosZ;
            float n4 = MathHelper.sqrt_double(n2 * n2 + n3 * n3) * 4.0f;
            if (n4 > 1.0f) {
                n4 = 1.0f;
            }
            this.limbSwingAmount += (n4 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.moveEntityWithHeading(n, moveForward);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityHorse.llllIIIIIIIIIlI[70], this.isEatingHaystack());
        nbtTagCompound.setBoolean(EntityHorse.llllIIIIIIIIIlI[71], this.isChested());
        nbtTagCompound.setBoolean(EntityHorse.llllIIIIIIIIIlI[72], this.getHasReproduced());
        nbtTagCompound.setBoolean(EntityHorse.llllIIIIIIIIIlI[73], this.func_110205_ce());
        nbtTagCompound.setInteger(EntityHorse.llllIIIIIIIIIlI[74], this.getHorseType());
        nbtTagCompound.setInteger(EntityHorse.llllIIIIIIIIIlI[75], this.getHorseVariant());
        nbtTagCompound.setInteger(EntityHorse.llllIIIIIIIIIlI[76], this.getTemper());
        nbtTagCompound.setBoolean(EntityHorse.llllIIIIIIIIIlI[77], this.isTame());
        nbtTagCompound.setString(EntityHorse.llllIIIIIIIIIlI[78], this.func_152119_ch());
        if (this.isChested()) {
            final NBTTagList list = new NBTTagList();
            for (int i = 2; i < this.horseChest.getSizeInventory(); ++i) {
                final ItemStack stackInSlot = this.horseChest.getStackInSlot(i);
                if (stackInSlot != null) {
                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                    nbtTagCompound2.setByte(EntityHorse.llllIIIIIIIIIlI[79], (byte)i);
                    stackInSlot.writeToNBT(nbtTagCompound2);
                    list.appendTag(nbtTagCompound2);
                }
            }
            nbtTagCompound.setTag(EntityHorse.llllIIIIIIIIIlI[80], list);
        }
        if (this.horseChest.getStackInSlot(1) != null) {
            nbtTagCompound.setTag(EntityHorse.llllIIIIIIIIIlI[81], this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
        if (this.horseChest.getStackInSlot(0) != null) {
            nbtTagCompound.setTag(EntityHorse.llllIIIIIIIIIlI[82], this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setEatingHaystack(nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[83]));
        this.func_110242_l(nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[84]));
        this.setChested(nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[85]));
        this.setHasReproduced(nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[86]));
        this.setHorseType(nbtTagCompound.getInteger(EntityHorse.llllIIIIIIIIIlI[87]));
        this.setHorseVariant(nbtTagCompound.getInteger(EntityHorse.llllIIIIIIIIIlI[88]));
        this.setTemper(nbtTagCompound.getInteger(EntityHorse.llllIIIIIIIIIlI[89]));
        this.setHorseTamed(nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[90]));
        final String s = EntityHorse.llllIIIIIIIIIlI[91];
        String s2;
        if (nbtTagCompound.hasKey(EntityHorse.llllIIIIIIIIIlI[92], 8)) {
            s2 = nbtTagCompound.getString(EntityHorse.llllIIIIIIIIIlI[93]);
        }
        else {
            s2 = PreYggdrasilConverter.func_152719_a(nbtTagCompound.getString(EntityHorse.llllIIIIIIIIIlI[94]));
        }
        if (s2.length() > 0) {
            this.func_152120_b(s2);
        }
        final IAttributeInstance attributeInstanceByName = this.getAttributeMap().getAttributeInstanceByName(EntityHorse.llllIIIIIIIIIlI[95]);
        if (attributeInstanceByName != null) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(attributeInstanceByName.getBaseValue() * 0.25);
        }
        if (this.isChested()) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityHorse.llllIIIIIIIIIlI[96], 10);
            this.func_110226_cD();
            for (int i = 0; i < tagList.tagCount(); ++i) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
                final int n = compoundTag.getByte(EntityHorse.llllIIIIIIIIIlI[97]) & 0xFF;
                if (n >= 2 && n < this.horseChest.getSizeInventory()) {
                    this.horseChest.setInventorySlotContents(n, ItemStack.loadItemStackFromNBT(compoundTag));
                }
            }
        }
        if (nbtTagCompound.hasKey(EntityHorse.llllIIIIIIIIIlI[98], 10)) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityHorse.llllIIIIIIIIIlI[99]));
            if (loadItemStackFromNBT != null && func_146085_a(loadItemStackFromNBT.getItem())) {
                this.horseChest.setInventorySlotContents(1, loadItemStackFromNBT);
            }
        }
        if (nbtTagCompound.hasKey(EntityHorse.llllIIIIIIIIIlI[100], 10)) {
            final ItemStack loadItemStackFromNBT2 = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityHorse.llllIIIIIIIIIlI[101]));
            if (loadItemStackFromNBT2 != null && loadItemStackFromNBT2.getItem() == Items.saddle) {
                this.horseChest.setInventorySlotContents(0, loadItemStackFromNBT2);
            }
        }
        else if (nbtTagCompound.getBoolean(EntityHorse.llllIIIIIIIIIlI[102])) {
            this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
        }
        this.func_110232_cE();
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (entityAnimal.getClass() != this.getClass()) {
            return false;
        }
        final EntityHorse entityHorse = (EntityHorse)entityAnimal;
        if (this.canMate() && entityHorse.canMate()) {
            final int horseType = this.getHorseType();
            final int horseType2 = entityHorse.getHorseType();
            return horseType == horseType2 || (horseType == 0 && horseType2 == 1) || (horseType == 1 && horseType2 == 0);
        }
        return false;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        final EntityHorse entityHorse = (EntityHorse)entityAgeable;
        final EntityHorse entityHorse2 = new EntityHorse(this.worldObj);
        final int horseType = this.getHorseType();
        final int horseType2 = entityHorse.getHorseType();
        int horseType3 = 0;
        if (horseType == horseType2) {
            horseType3 = horseType;
        }
        else if ((horseType == 0 && horseType2 == 1) || (horseType == 1 && horseType2 == 0)) {
            horseType3 = 2;
        }
        if (horseType3 == 0) {
            final int nextInt = this.rand.nextInt(9);
            int nextInt2;
            if (nextInt < 4) {
                nextInt2 = (this.getHorseVariant() & 0xFF);
            }
            else if (nextInt < 8) {
                nextInt2 = (entityHorse.getHorseVariant() & 0xFF);
            }
            else {
                nextInt2 = this.rand.nextInt(7);
            }
            final int nextInt3 = this.rand.nextInt(5);
            int horseVariant;
            if (nextInt3 < 2) {
                horseVariant = (nextInt2 | (this.getHorseVariant() & 0xFF00));
            }
            else if (nextInt3 < 4) {
                horseVariant = (nextInt2 | (entityHorse.getHorseVariant() & 0xFF00));
            }
            else {
                horseVariant = (nextInt2 | (this.rand.nextInt(5) << 8 & 0xFF00));
            }
            entityHorse2.setHorseVariant(horseVariant);
        }
        entityHorse2.setHorseType(horseType3);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.func_110267_cL()) / 3.0);
        entityHorse2.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue((this.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + entityAgeable.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + this.func_110245_cM()) / 3.0);
        entityHorse2.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + entityAgeable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.func_110203_cN()) / 3.0);
        return entityHorse2;
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        IEntityLivingData func_180482_a = super.func_180482_a(difficultyInstance, entityLivingData);
        int horseVariant = 0;
        int field_111107_a;
        if (func_180482_a instanceof GroupData) {
            field_111107_a = ((GroupData)func_180482_a).field_111107_a;
            horseVariant = ((((GroupData)func_180482_a).field_111106_b & 0xFF) | this.rand.nextInt(5) << 8);
        }
        else {
            if (this.rand.nextInt(10) == 0) {
                field_111107_a = 1;
            }
            else {
                final int nextInt = this.rand.nextInt(7);
                final int nextInt2 = this.rand.nextInt(5);
                field_111107_a = 0;
                horseVariant = (nextInt | nextInt2 << 8);
            }
            func_180482_a = new GroupData(field_111107_a, horseVariant);
        }
        this.setHorseType(field_111107_a);
        this.setHorseVariant(horseVariant);
        if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(-24000);
        }
        if (field_111107_a != 4 && field_111107_a != 3) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.func_110267_cL());
            if (field_111107_a == 0) {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.func_110203_cN());
            }
            else {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776);
            }
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
        }
        if (field_111107_a != 2 && field_111107_a != 1) {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(this.func_110245_cM());
        }
        else {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(0.5);
        }
        this.setHealth(this.getMaxHealth());
        return func_180482_a;
    }
    
    public float getGrassEatingAmount(final float n) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * n;
    }
    
    public float getRearingAmount(final float n) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * n;
    }
    
    public float func_110201_q(final float n) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * n;
    }
    
    public void setJumpPower(int n) {
        if (this.isHorseSaddled()) {
            if (n < 0) {
                n = 0;
            }
            else {
                this.field_110294_bI = true;
                this.makeHorseRear();
            }
            if (n >= 90) {
                this.jumpPower = 1.0f;
            }
            else {
                this.jumpPower = 0.4f + 0.4f * n / 90.0f;
            }
        }
    }
    
    protected void spawnHorseParticles(final boolean b) {
        final EnumParticleTypes enumParticleTypes = b ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
        for (int i = 0; i < 7; ++i) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 7) {
            this.spawnHorseParticles(true);
        }
        else if (b == 6) {
            this.spawnHorseParticles(false);
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        if (this.prevRearingAmount > 0.0f) {
            final float sin = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
            final float cos = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
            final float n = 0.7f * this.prevRearingAmount;
            this.riddenByEntity.setPosition(this.posX + n * sin, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + 0.15f * this.prevRearingAmount, this.posZ - n * cos);
            if (this.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }
    
    private float func_110267_cL() {
        return 15.0f + this.rand.nextInt(8) + this.rand.nextInt(9);
    }
    
    private double func_110245_cM() {
        return 0.4000000059604645 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }
    
    private double func_110203_cN() {
        return (0.44999998807907104 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }
    
    public static boolean func_146085_a(final Item item) {
        return item == Items.iron_horse_armor || item == Items.golden_horse_armor || item == Items.diamond_horse_armor;
    }
    
    @Override
    public boolean isOnLadder() {
        return false;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    @Override
    public boolean func_174820_d(final int n, final ItemStack itemStack) {
        if (n == 499 && this.canCarryChest()) {
            if (itemStack == null && this.isChested()) {
                this.setChested(false);
                this.func_110226_cD();
                return true;
            }
            if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.chest) && !this.isChested()) {
                this.setChested(true);
                this.func_110226_cD();
                return true;
            }
        }
        final int n2 = n - 400;
        if (n2 >= 0 && n2 < 2 && n2 < this.horseChest.getSizeInventory()) {
            if (n2 == 0 && itemStack != null && itemStack.getItem() != Items.saddle) {
                return false;
            }
            if (n2 == 1 && ((itemStack != null && !func_146085_a(itemStack.getItem())) || !this.canWearArmor())) {
                return false;
            }
            this.horseChest.setInventorySlotContents(n2, itemStack);
            this.func_110232_cE();
            return true;
        }
        else {
            final int n3 = n - 500 + 2;
            if (n3 >= 2 && n3 < this.horseChest.getSizeInventory()) {
                this.horseChest.setInventorySlotContents(n3, itemStack);
                return true;
            }
            return false;
        }
    }
    
    private static void lIllIIIlIIIlIlIl() {
        (llllIIIIIIIIIlI = new String[103])[0] = lIllIIIIllllIlll(EntityHorse.llllIIIIIIlIllI[0], EntityHorse.llllIIIIIIlIllI[1]);
        EntityHorse.llllIIIIIIIIIlI[1] = lIllIIIIllllIlll(EntityHorse.llllIIIIIIlIllI[2], EntityHorse.llllIIIIIIlIllI[3]);
        EntityHorse.llllIIIIIIIIIlI[2] = lIllIIIIlllllIII(EntityHorse.llllIIIIIIlIllI[4], EntityHorse.llllIIIIIIlIllI[5]);
        EntityHorse.llllIIIIIIIIIlI[3] = lIllIIIIlllllIIl(EntityHorse.llllIIIIIIlIllI[6], EntityHorse.llllIIIIIIlIllI[7]);
        EntityHorse.llllIIIIIIIIIlI[4] = lIllIIIIlllllIlI(EntityHorse.llllIIIIIIlIllI[8], EntityHorse.llllIIIIIIlIllI[9]);
        EntityHorse.llllIIIIIIIIIlI[5] = lIllIIIIllllIlll(EntityHorse.llllIIIIIIlIllI[10], EntityHorse.llllIIIIIIlIllI[11]);
        EntityHorse.llllIIIIIIIIIlI[6] = lIllIIIIlllllIII(EntityHorse.llllIIIIIIlIllI[12], EntityHorse.llllIIIIIIlIllI[13]);
        EntityHorse.llllIIIIIIIIIlI[7] = lIllIIIIlllllIII(EntityHorse.llllIIIIIIlIllI[14], EntityHorse.llllIIIIIIlIllI[15]);
        EntityHorse.llllIIIIIIIIIlI[8] = lIllIIIIlllllIIl(EntityHorse.llllIIIIIIlIllI[16], EntityHorse.llllIIIIIIlIllI[17]);
        EntityHorse.llllIIIIIIIIIlI[9] = lIllIIIIllllIlll(EntityHorse.llllIIIIIIlIllI[18], EntityHorse.llllIIIIIIlIllI[19]);
        EntityHorse.llllIIIIIIIIIlI[10] = lIllIIIIlllllIIl(EntityHorse.llllIIIIIIlIllI[20], EntityHorse.llllIIIIIIlIllI[21]);
        EntityHorse.llllIIIIIIIIIlI[11] = lIllIIIIlllllIlI("KY2OGcugGcEXA0uq9EpY4N0Oc0AG+ElPDykgxqXoMTpjqyxZbMCxug==", "ahDHo");
        EntityHorse.llllIIIIIIIIIlI[12] = lIllIIIIlllllIIl("nMau2j5h7N9TFH3mHFbd1CzkGBGnThphv6mENtVeSzQx2KnAu4/7QFLgl6a2mvG8", "JZUhv");
        EntityHorse.llllIIIIIIIIIlI[13] = lIllIIIIllllIlll("SR1RFnK0YmwUMZL+AGTqR6NfAv/XzT63tosfYlT2cVBNWaz6eVODWA==", "rIWoB");
        EntityHorse.llllIIIIIIIIIlI[14] = lIllIIIIlllllIIl("/uqa58DTE6+cPYgY/qDNI1hX8QhyD7NgZOP9QqwNT/sVyi+YWoyvpGqtSjW1PucL", "FQsLv");
        EntityHorse.llllIIIIIIIIIlI[15] = lIllIIIIlllllIII("MhQdICw0FBZ7PCgFDCAgaRkKJiojXg07KzUUOjMrJwhLJDch", "FqeTY");
        EntityHorse.llllIIIIIIIIIlI[16] = lIllIIIIlllllIlI("WWsV+SXQvBN8IVNRZNX7qFwWU8z6OtQi1xY1d5CypVNDNQ2+B2x9QAB87R0k+W3Y", "tEOop");
        EntityHorse.llllIIIIIIIIIlI[17] = lIllIIIIlllllIIl("3yR0hm/qe/CuYG4Cl0+8fA==", "TofwI");
        EntityHorse.llllIIIIIIIIIlI[18] = lIllIIIIlllllIII("CSUU", "aFfWJ");
        EntityHorse.llllIIIIIIIIIlI[19] = lIllIIIIlllllIIl("NAMoEEOKmsRVq4DQ/smADw==", "LMNyT");
        EntityHorse.llllIIIIIIIIIlI[20] = lIllIIIIlllllIIl("mLEtKo7OKjCzr4AaIyiApQ==", "bavCM");
        EntityHorse.llllIIIIIIIIIlI[21] = lIllIIIIlllllIlI("OPBBurLrkC4=", "szDTo");
        EntityHorse.llllIIIIIIIIIlI[22] = lIllIIIIllllIlll("d3VIiMPqUA4=", "CEgSo");
        EntityHorse.llllIIIIIIIIIlI[23] = lIllIIIIllllIlll("cIOjCaLpbmg=", "LqjNG");
        EntityHorse.llllIIIIIIIIIlI[24] = lIllIIIIllllIlll("i7jgBSzEjP/85y36fjoOF81w5tBCS0821BWSn+57WUQorRPGpahGV8J42zjuonXQ", "HIRMl");
        EntityHorse.llllIIIIIIIIIlI[25] = lIllIIIIlllllIIl("AxspyOz4TvivQpHE/dXPQoh91KtfgyBB8GRUdvhlTSJXvQpl4ZpqDP1JL50ZN7vD3q0EvvFwqXfxSInBsO4dXA==", "pigcT");
        EntityHorse.llllIIIIIIIIIlI[26] = lIllIIIIlllllIIl("psZ1nGXA00dz82SHqSjFZPJowXlkYqZdqFjHf/EXUnwY4obvbOKXQPqfpOOTvUCZjoqlv4KoFOeyolrSj5UEow==", "uQBDj");
        EntityHorse.llllIIIIIIIIIlI[27] = lIllIIIIlllllIIl("+AZeSziI9zhdvq/0Og/WCTvaS5qBRPetLR8M38/zChEq+fZz4QqBlkjvfRLBHBgG9kZvFIl9Cn1BbKfpLbtJKg==", "zLLjg");
        EntityHorse.llllIIIIIIIIIlI[28] = lIllIIIIlllllIIl("RtFRE7s+/T6vWFKq5L/pIw==", "YwtfS");
        EntityHorse.llllIIIIIIIIIlI[29] = lIllIIIIlllllIII("FAgP", "cgPlz");
        EntityHorse.llllIIIIIIIIIlI[30] = lIllIIIIllllIlll("/+OuwajBNg8=", "teacT");
        EntityHorse.llllIIIIIIIIIlI[31] = lIllIIIIllllIlll("Imd8yssR0h4=", "MKYLy");
        EntityHorse.llllIIIIIIIIIlI[32] = lIllIIIIlllllIII("JT4W", "GZycu");
        EntityHorse.llllIIIIIIIIIlI[33] = lIllIIIIlllllIII("", "ixrfR");
        EntityHorse.llllIIIIIIIIIlI[34] = lIllIIIIllllIlll("RcyYauQYrmZbTetXKUGozvuZA/mORXAY", "AROcn");
        EntityHorse.llllIIIIIIIIIlI[35] = lIllIIIIlllllIlI("4keKzDksuzZaMSg2feR3CTJYI8D3dqYo", "SswdT");
        EntityHorse.llllIIIIIIIIIlI[36] = lIllIIIIlllllIII("Iw8RDCc/TwgQPyNPCwQ+Iw==", "FaeeS");
        EntityHorse.llllIIIIIIIIIlI[37] = lIllIIIIlllllIII("CgUdLjEWRRMoKA0CDC8qHRgMaSsOBgw=", "okiGE");
        EntityHorse.llllIIIIIIIIIlI[38] = lIllIIIIlllllIII("EAglJw0MSCIlHBkDJSEXHQkjPRxbCDAjHA==", "ufQNy");
        EntityHorse.llllIIIIIIIIIlI[39] = lIllIIIIllllIlll("/MKdJMPzOII=", "iLXlm");
        EntityHorse.llllIIIIIIIIIlI[40] = lIllIIIIlllllIII("ITUhfC4jKDA3aCA7LTY=", "LZCRF");
        EntityHorse.llllIIIIIIIIIlI[41] = lIllIIIIlllllIII("OCo9MSgzLSoxOQ==", "pEOBM");
        EntityHorse.llllIIIIIIIIIlI[42] = lIllIIIIlllllIIl("bOzakywoGVlcKtAj002+RQ==", "QEcOe");
        EntityHorse.llllIIIIIIIIIlI[43] = lIllIIIIllllIlll("8pf3dvxuelW2cqus0djA/g==", "MKkYG");
        EntityHorse.llllIIIIIIIIIlI[44] = lIllIIIIlllllIII("PwEmRyI9HDcMZD4LJR0iNxw=", "RnDiJ");
        EntityHorse.llllIIIIIIIIIlI[45] = lIllIIIIlllllIIl("45DVAsK6Rwgay10/YZ1418V2uj+0Dm7MpglGkqfJ5XA=", "CbDKX");
        EntityHorse.llllIIIIIIIIIlI[46] = lIllIIIIlllllIIl("6ug5JN48A7u5KsBOGMiFl4weTTmOW1/z0GZZAiAIr+g=", "HOZIm");
        EntityHorse.llllIIIIIIIIIlI[47] = lIllIIIIlllllIIl("h1T1zYtD8MXYieuodCQmaQ==", "oeEVN");
        EntityHorse.llllIIIIIIIIIlI[48] = lIllIIIIlllllIIl("dneQZ5rEhjFLSb+3JcKtvnTTnj4V9ggqrYzAKRjtxlA=", "THUyu");
        EntityHorse.llllIIIIIIIIIlI[49] = lIllIIIIlllllIII("KSohVzwrNzAcej4qLhs9IWsrECA=", "DECyT");
        EntityHorse.llllIIIIIIIIIlI[50] = lIllIIIIlllllIlI("J4v6QJQZryi5RqF+qT+EFtFO1fdkyy0h", "PGfVD");
        EntityHorse.llllIIIIIIIIIlI[51] = lIllIIIIllllIlll("qdBdSW4GDSwfnqgHYn1d7A==", "nWSOI");
        EntityHorse.llllIIIIIIIIIlI[52] = lIllIIIIlllllIlI("TS4HgG8FFDz7fX8i2kpuxw27FOllj4ax", "RhCSx");
        EntityHorse.llllIIIIIIIIIlI[53] = lIllIIIIlllllIII("FysOfAIVNh83RAArATADH2oFNgYf", "zDlRj");
        EntityHorse.llllIIIIIIIIIlI[54] = lIllIIIIllllIlll("M8RL2VUlED5XEIeak201vqNO+MGl6zR9", "xjWHz");
        EntityHorse.llllIIIIIIIIIlI[55] = lIllIIIIllllIlll("8dj0a3MMDV1ilMY+Rw5WIA==", "QYVQx");
        EntityHorse.llllIIIIIIIIIlI[56] = lIllIIIIlllllIlI("F+NKu0jTt6abHzjQwKKkooWT9mg5Rka3", "rSDRD");
        EntityHorse.llllIIIIIIIIIlI[57] = lIllIIIIllllIlll("veYR0Bq+N3yn5j28mJ7Stg==", "PwqwB");
        EntityHorse.llllIIIIIIIIIlI[58] = lIllIIIIllllIlll("bsNawS6afpSomSHigxS8VSkjDhq6cCSl", "lAoDg");
        EntityHorse.llllIIIIIIIIIlI[59] = lIllIIIIlllllIII("KzwFdxIpIRQ8VCEyCzUVNg==", "FSgYz");
        EntityHorse.llllIIIIIIIIIlI[60] = lIllIIIIllllIlll("EPdBXLnqk1RXqyHm2wNMjJBvLmxTpqd2", "CuOTK");
        EntityHorse.llllIIIIIIIIIlI[61] = lIllIIIIlllllIlI("1KSQN7UM/l9YNjt04qFSlg==", "EmIIq");
        EntityHorse.llllIIIIIIIIIlI[62] = lIllIIIIlllllIIl("uJEy/ToKKCoH7OVD7knJgg==", "RbjCW");
        EntityHorse.llllIIIIIIIIIlI[63] = lIllIIIIllllIlll("0qcAPjI2Rok0xVkcj+5jTQ==", "vERvd");
        EntityHorse.llllIIIIIIIIIlI[64] = lIllIIIIllllIlll("UX+VIbyssf8=", "OojlH");
        EntityHorse.llllIIIIIIIIIlI[65] = lIllIIIIlllllIlI("gBAEtIOnzC8=", "wfiQZ");
        EntityHorse.llllIIIIIIIIIlI[66] = lIllIIIIllllIlll("BrTxfaFFg+E=", "ZXIbC");
        EntityHorse.llllIIIIIIIIIlI[67] = lIllIIIIlllllIIl("f8XeJ7RcnuIddyIypa1UtA==", "PKgsW");
        EntityHorse.llllIIIIIIIIIlI[68] = lIllIIIIlllllIII("Lw4beiUqCBo/IywRFTs2", "BayTF");
        EntityHorse.llllIIIIIIIIIlI[69] = lIllIIIIlllllIII("JAAGXwEmHRcURyMaCQE=", "Iodqi");
        EntityHorse.llllIIIIIIIIIlI[70] = lIllIIIIlllllIlI("9wjmt6b2s6Eu1dgLgaZTww==", "OINGb");
        EntityHorse.llllIIIIIIIIIlI[71] = lIllIIIIlllllIlI("UVSIYNFu3QyWScNgHYWukA==", "XDTjS");
        EntityHorse.llllIIIIIIIIIlI[72] = lIllIIIIllllIlll("OkIOs/UBac8ZCp/rwyfObw==", "ADiUR");
        EntityHorse.llllIIIIIIIIIlI[73] = lIllIIIIllllIlll("hsG6rJm8IR4=", "KqARP");
        EntityHorse.llllIIIIIIIIIlI[74] = lIllIIIIlllllIIl("qV+c/tSuuts9JiVGEsRTYw==", "VSCoH");
        EntityHorse.llllIIIIIIIIIlI[75] = lIllIIIIlllllIII("ETgHCCApLQ==", "GYuaA");
        EntityHorse.llllIIIIIIIIIlI[76] = lIllIIIIlllllIIl("1ylpXFXB9taUHPbSAnneXg==", "wSHmf");
        EntityHorse.llllIIIIIIIIIlI[77] = lIllIIIIllllIlll("oDPYV8VwMjE=", "aqLmt");
        EntityHorse.llllIIIIIIIIIlI[78] = lIllIIIIlllllIlI("zB15nXK+NA2EpeExL4jXPA==", "KJWqj");
        EntityHorse.llllIIIIIIIIIlI[79] = lIllIIIIlllllIlI("U/Jtf8kHkDA=", "fBgxd");
        EntityHorse.llllIIIIIIIIIlI[80] = lIllIIIIllllIlll("aiKrLAUMnpU=", "HvWAs");
        EntityHorse.llllIIIIIIIIIlI[81] = lIllIIIIlllllIlI("jUGpfZ7VWJpuAt3wcXsrXw==", "qNRHf");
        EntityHorse.llllIIIIIIIIIlI[82] = lIllIIIIllllIlll("DrBeRK5raPsGpJxs5cFybA==", "iyQfu");
        EntityHorse.llllIIIIIIIIIlI[83] = lIllIIIIllllIlll("PvsyUiyfO8K31EGH/I2w7A==", "eqrXr");
        EntityHorse.llllIIIIIIIIIlI[84] = lIllIIIIlllllIlI("AsAjH7GqvLU=", "bwQyi");
        EntityHorse.llllIIIIIIIIIlI[85] = lIllIIIIllllIlll("kx+F0Qr9ta6lgPG+I3GQbQ==", "aTCYJ");
        EntityHorse.llllIIIIIIIIIlI[86] = lIllIIIIlllllIIl("8kFE2E61Pvh1MDZx2T3ZkQ==", "pcBZo");
        EntityHorse.llllIIIIIIIIIlI[87] = lIllIIIIlllllIIl("XAGYNNrGmI5+rFDIQqjTPg==", "rFxod");
        EntityHorse.llllIIIIIIIIIlI[88] = lIllIIIIlllllIII("MwAcOQMLFQ==", "eanPb");
        EntityHorse.llllIIIIIIIIIlI[89] = lIllIIIIlllllIII("ID0lOCEG", "tXHHD");
        EntityHorse.llllIIIIIIIIIlI[90] = lIllIIIIllllIlll("VFuB20QlS7Q=", "KrmSC");
        EntityHorse.llllIIIIIIIIIlI[91] = lIllIIIIlllllIlI("VpJ8nxi7HAo=", "xVSjy");
        EntityHorse.llllIIIIIIIIIlI[92] = lIllIIIIlllllIII("Gg4CHDYALCU9", "UylyD");
        EntityHorse.llllIIIIIIIIIlI[93] = lIllIIIIllllIlll("MI0n0UkvUi2Nv32/9GHAXg==", "tlJQO");
        EntityHorse.llllIIIIIIIIIlI[94] = lIllIIIIlllllIIl("1lDkQZSRxVj9D0LW4IjuyA==", "oQpat");
        EntityHorse.llllIIIIIIIIIlI[95] = lIllIIIIlllllIlI("A//XRYA1yUU=", "BTYTy");
        EntityHorse.llllIIIIIIIIIlI[96] = lIllIIIIlllllIlI("ezwkHdqp7kQ=", "WilVo");
        EntityHorse.llllIIIIIIIIIlI[97] = lIllIIIIlllllIII("ITooNg==", "rVGBd");
        EntityHorse.llllIIIIIIIIIlI[98] = lIllIIIIlllllIlI("ZvKyn8IGzE3XIbWm/0zVhQ==", "VzbSi");
        EntityHorse.llllIIIIIIIIIlI[99] = lIllIIIIlllllIlI("u62qhLtIk62FbNN0r6sHaw==", "XFIiw");
        EntityHorse.llllIIIIIIIIIlI[100] = lIllIIIIllllIlll("rhmvnmSpL8FJ6T5vcjjO4Q==", "hEhdm");
        EntityHorse.llllIIIIIIIIIlI[101] = lIllIIIIlllllIII("OAIxPjUOKiE/NA==", "kcUZY");
        EntityHorse.llllIIIIIIIIIlI[102] = lIllIIIIlllllIlI("CBb2qIDVe20=", "smgok");
        EntityHorse.llllIIIIIIlIllI = null;
    }
    
    private static void lIllIIIlIIIllIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        EntityHorse.llllIIIIIIlIllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIllIIIIlllllIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIllIIIIllllIlll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIllIIIIlllllIII(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIllIIIIlllllIIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int field_111107_a;
        public int field_111106_b;
        private static final String __OBFID;
        
        public GroupData(final int field_111107_a, final int field_111106_b) {
            this.field_111107_a = field_111107_a;
            this.field_111106_b = field_111106_b;
        }
        
        static {
            __OBFID = "CL_00001643";
        }
    }
}
