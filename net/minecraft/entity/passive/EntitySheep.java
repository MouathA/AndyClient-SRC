package net.minecraft.entity.passive;

import com.google.common.collect.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.crafting.*;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting inventoryCrafting;
    private static final Map field_175514_bm;
    private int sheepTimer;
    private EntityAIEatGrass entityAIEatGrass;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001648";
        (field_175514_bm = Maps.newEnumMap(EnumDyeColor.class)).put(EnumDyeColor.WHITE, new float[] { 1.0f, 1.0f, 1.0f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.ORANGE, new float[] { 0.85f, 0.5f, 0.2f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.MAGENTA, new float[] { 0.7f, 0.3f, 0.85f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.LIGHT_BLUE, new float[] { 0.4f, 0.6f, 0.85f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.YELLOW, new float[] { 0.9f, 0.9f, 0.2f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.LIME, new float[] { 0.5f, 0.8f, 0.1f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.PINK, new float[] { 0.95f, 0.5f, 0.65f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.GRAY, new float[] { 0.3f, 0.3f, 0.3f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.SILVER, new float[] { 0.6f, 0.6f, 0.6f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.CYAN, new float[] { 0.3f, 0.5f, 0.6f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.PURPLE, new float[] { 0.5f, 0.25f, 0.7f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.BLUE, new float[] { 0.2f, 0.3f, 0.7f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.BROWN, new float[] { 0.4f, 0.3f, 0.2f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.GREEN, new float[] { 0.4f, 0.5f, 0.2f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.RED, new float[] { 0.6f, 0.2f, 0.2f });
        EntitySheep.field_175514_bm.put(EnumDyeColor.BLACK, new float[] { 0.1f, 0.1f, 0.1f });
    }
    
    public static float[] func_175513_a(final EnumDyeColor enumDyeColor) {
        return EntitySheep.field_175514_bm.get(enumDyeColor);
    }
    
    public EntitySheep(final World world) {
        super(world);
        this.inventoryCrafting = new InventoryCrafting(new Container() {
            private static final String __OBFID;
            final EntitySheep this$0;
            
            @Override
            public boolean canInteractWith(final EntityPlayer entityPlayer) {
                return false;
            }
            
            static {
                __OBFID = "CL_00001649";
            }
        }, 2, 1);
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }
    
    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (this != 0) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.func_175509_cj().func_176765_a()), 0.0f);
        }
        while (0 < this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + n)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_mutton, 1);
            }
            else {
                this.dropItem(Items.mutton, 1);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.wool);
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public float getHeadRotationPointY(final float n) {
        return (this.sheepTimer <= 0) ? 0.0f : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0f : ((this.sheepTimer < 4) ? ((this.sheepTimer - n) / 4.0f) : (-(this.sheepTimer - 40 - n) / 4.0f)));
    }
    
    public float getHeadRotationAngleX(final float n) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            return 0.62831855f + 0.2199115f * MathHelper.sin((this.sheepTimer - 4 - n) / 32.0f * 28.7f);
        }
        return (this.sheepTimer > 0) ? 0.62831855f : (this.rotationPitch / 57.295776f);
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.shears && this != 0 && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setSheared(true);
                while (0 < 1 + this.rand.nextInt(3)) {
                    final EntityItem entityDropItem;
                    final EntityItem entityItem = entityDropItem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.func_175509_cj().func_176765_a()), 1.0f);
                    entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                    final EntityItem entityItem2 = entityItem;
                    entityItem2.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem3 = entityItem;
                    entityItem3.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    int n = 0;
                    ++n;
                }
            }
            currentItem.damageItem(1, entityPlayer);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Sheared", this.getSheared());
        nbtTagCompound.setByte("Color", (byte)this.func_175509_cj().func_176765_a());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSheared(nbtTagCompound.getBoolean("Sheared"));
        this.func_175512_b(EnumDyeColor.func_176764_b(nbtTagCompound.getByte("Color")));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected void func_180429_a(final BlockPos blockPos, final Block block) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }
    
    public EnumDyeColor func_175509_cj() {
        return EnumDyeColor.func_176764_b(this.dataWatcher.getWatchableObjectByte(16) & 0xF);
    }
    
    public void func_175512_b(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(16, (byte)((this.dataWatcher.getWatchableObjectByte(16) & 0xF0) | (enumDyeColor.func_176765_a() & 0xF)));
    }
    
    public void setSheared(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x10));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFEF));
        }
    }
    
    public static EnumDyeColor func_175510_a(final Random random) {
        final int nextInt = random.nextInt(100);
        return (nextInt < 5) ? EnumDyeColor.BLACK : ((nextInt < 10) ? EnumDyeColor.GRAY : ((nextInt < 15) ? EnumDyeColor.SILVER : ((nextInt < 18) ? EnumDyeColor.BROWN : ((random.nextInt(500) == 0) ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
    }
    
    public EntitySheep func_180491_b(final EntityAgeable entityAgeable) {
        final EntitySheep entitySheep = (EntitySheep)entityAgeable;
        final EntitySheep entitySheep2 = new EntitySheep(this.worldObj);
        entitySheep2.func_175512_b(this.func_175511_a(this, entitySheep));
        return entitySheep2;
    }
    
    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, IEntityLivingData func_180482_a) {
        func_180482_a = super.func_180482_a(difficultyInstance, func_180482_a);
        this.func_175512_b(func_175510_a(this.worldObj.rand));
        return func_180482_a;
    }
    
    private EnumDyeColor func_175511_a(final EntityAnimal entityAnimal, final EntityAnimal entityAnimal2) {
        final int dyeColorDamage = ((EntitySheep)entityAnimal).func_175509_cj().getDyeColorDamage();
        final int dyeColorDamage2 = ((EntitySheep)entityAnimal2).func_175509_cj().getDyeColorDamage();
        this.inventoryCrafting.getStackInSlot(0).setItemDamage(dyeColorDamage);
        this.inventoryCrafting.getStackInSlot(1).setItemDamage(dyeColorDamage2);
        final ItemStack matchingRecipe = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)entityAnimal).worldObj);
        int metadata;
        if (matchingRecipe != null && matchingRecipe.getItem() == Items.dye) {
            metadata = matchingRecipe.getMetadata();
        }
        else {
            metadata = (this.worldObj.rand.nextBoolean() ? dyeColorDamage : dyeColorDamage2);
        }
        return EnumDyeColor.func_176766_a(metadata);
    }
    
    @Override
    public float getEyeHeight() {
        return 0.95f * this.height;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.func_180491_b(entityAgeable);
    }
}
