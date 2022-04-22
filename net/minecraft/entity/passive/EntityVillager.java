package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.village.*;
import net.minecraft.potion.*;
import net.minecraft.stats.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;
    private EntityPlayer buyingPlayer;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private boolean needsInitilization;
    private boolean field_175565_bs;
    private int wealth;
    private String lastBuyingPlayer;
    private int field_175563_bv;
    private int field_175562_bw;
    private boolean isLookingForHome;
    private boolean field_175564_by;
    private InventoryBasic field_175560_bz;
    private static final ITradeList[][][][] field_175561_bA;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001707";
        field_175561_bA = new ITradeList[][][][] { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds(Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds(Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds(Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds(Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds(Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds(Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds(Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds(Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } };
    }
    
    public EntityVillager(final World world) {
        this(world, 0);
    }
    
    public EntityVillager(final World world, final int profession) {
        super(world);
        this.field_175560_bz = new InventoryBasic("Items", false, 8);
        this.setProfession(profession);
        this.setSize(0.6f, 1.8f);
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, new Predicate() {
            private static final String __OBFID;
            final EntityVillager this$0;
            
            public boolean func_179530_a(final Entity entity) {
                return entity instanceof EntityZombie;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_179530_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002195";
            }
        }, 8.0f, 0.6, 0.6));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
        this.setCanPickUpLoot(true);
    }
    
    private void func_175552_ct() {
        if (!this.field_175564_by) {
            this.field_175564_by = true;
            if (this.isChild()) {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32));
            }
            else if (this.getProfession() == 0) {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6));
            }
        }
    }
    
    @Override
    protected void func_175500_n() {
        if (this.getProfession() == 0) {
            this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6));
        }
        super.func_175500_n();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }
    
    @Override
    protected void updateAITasks() {
        final int randomTickDivider = this.randomTickDivider - 1;
        this.randomTickDivider = randomTickDivider;
        if (randomTickDivider <= 0) {
            final BlockPos blockPos = new BlockPos(this);
            this.worldObj.getVillageCollection().func_176060_a(blockPos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.getVillageCollection().func_176056_a(blockPos, 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                this.func_175449_a(this.villageObj.func_180608_a(), (int)(this.villageObj.getVillageRadius() * 1.0f));
                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }
        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    for (final MerchantRecipe merchantRecipe : this.buyingList) {
                        if (merchantRecipe.isRecipeDisabled()) {
                            merchantRecipe.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }
                    this.func_175554_cu();
                    this.needsInitilization = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }
        super.updateAITasks();
    }
    
    @Override
    public boolean interact(final EntityPlayer customer) {
        final ItemStack currentItem = customer.inventory.getCurrentItem();
        if ((currentItem == null || currentItem.getItem() != Items.spawn_egg) && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
                this.setCustomer(customer);
                customer.displayVillagerTradeGui(this);
            }
            customer.triggerAchievement(StatList.timesTalkedToVillagerStat);
            return true;
        }
        return super.interact(customer);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Profession", this.getProfession());
        nbtTagCompound.setInteger("Riches", this.wealth);
        nbtTagCompound.setInteger("Career", this.field_175563_bv);
        nbtTagCompound.setInteger("CareerLevel", this.field_175562_bw);
        nbtTagCompound.setBoolean("Willing", this.field_175565_bs);
        if (this.buyingList != null) {
            nbtTagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }
        final NBTTagList list = new NBTTagList();
        while (0 < this.field_175560_bz.getSizeInventory()) {
            final ItemStack stackInSlot = this.field_175560_bz.getStackInSlot(0);
            if (stackInSlot != null) {
                list.appendTag(stackInSlot.writeToNBT(new NBTTagCompound()));
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Inventory", list);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setProfession(nbtTagCompound.getInteger("Profession"));
        this.wealth = nbtTagCompound.getInteger("Riches");
        this.field_175563_bv = nbtTagCompound.getInteger("Career");
        this.field_175562_bw = nbtTagCompound.getInteger("CareerLevel");
        this.field_175565_bs = nbtTagCompound.getBoolean("Willing");
        if (nbtTagCompound.hasKey("Offers", 10)) {
            this.buyingList = new MerchantRecipeList(nbtTagCompound.getCompoundTag("Offers"));
        }
        final NBTTagList tagList = nbtTagCompound.getTagList("Inventory", 10);
        while (0 < tagList.tagCount()) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
            if (loadItemStackFromNBT != null) {
                this.field_175560_bz.func_174894_a(loadItemStackFromNBT);
            }
            int n = 0;
            ++n;
        }
        this.setCanPickUpLoot(true);
        this.func_175552_ct();
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.villager.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.villager.death";
    }
    
    public void setProfession(final int n) {
        this.dataWatcher.updateObject(16, n);
    }
    
    public int getProfession() {
        return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
    }
    
    public boolean isMating() {
        return this.isMating;
    }
    
    public void setMating(final boolean isMating) {
        this.isMating = isMating;
    }
    
    public void setPlaying(final boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (this.villageObj != null && revengeTarget != null) {
            this.villageObj.addOrRenewAgressor(revengeTarget);
            if (revengeTarget instanceof EntityPlayer) {
                if (this.isChild()) {}
                this.villageObj.setReputationForPlayer(revengeTarget.getName(), -3);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte)13);
                }
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.villageObj != null) {
            final Entity entity = damageSource.getEntity();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(entity.getName(), -2);
                }
                else if (entity instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            }
            else if (this.worldObj.getClosestPlayerToEntity(this, 16.0) != null) {
                this.villageObj.endMatingSeason();
            }
        }
        super.onDeath(damageSource);
    }
    
    @Override
    public void setCustomer(final EntityPlayer buyingPlayer) {
        this.buyingPlayer = buyingPlayer;
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }
    
    public boolean isTrading() {
        return this.buyingPlayer != null;
    }
    
    public boolean func_175550_n(final boolean b) {
        if (!this.field_175565_bs && b && this.func_175553_cp()) {
            while (0 < this.field_175560_bz.getSizeInventory()) {
                final ItemStack stackInSlot = this.field_175560_bz.getStackInSlot(0);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem() == Items.bread && stackInSlot.stackSize >= 3) {
                        this.field_175560_bz.decrStackSize(0, 3);
                    }
                    else if ((stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot) && stackInSlot.stackSize >= 12) {
                        this.field_175560_bz.decrStackSize(0, 12);
                    }
                }
                if (true) {
                    this.worldObj.setEntityState(this, (byte)18);
                    this.field_175565_bs = true;
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        return this.field_175565_bs;
    }
    
    public void func_175549_o(final boolean field_175565_bs) {
        this.field_175565_bs = field_175565_bs;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
        int n = 3 + this.rand.nextInt(4);
        if (merchantRecipe.func_180321_e() == 1 || this.rand.nextInt(5) == 0) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            this.field_175565_bs = true;
            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getName();
            }
            else {
                this.lastBuyingPlayer = null;
            }
            n += 5;
        }
        if (merchantRecipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += merchantRecipe.getItemToBuy().stackSize;
        }
        if (merchantRecipe.func_180322_j()) {
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5, this.posZ, n));
        }
    }
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            if (itemStack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            }
            else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        if (this.buyingList == null) {
            this.func_175554_cu();
        }
        return this.buyingList;
    }
    
    private void func_175554_cu() {
        final ITradeList[][][] array = EntityVillager.field_175561_bA[this.getProfession()];
        if (this.field_175563_bv != 0 && this.field_175562_bw != 0) {
            ++this.field_175562_bw;
        }
        else {
            this.field_175563_bv = this.rand.nextInt(array.length) + 1;
            this.field_175562_bw = 1;
        }
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        final int n = this.field_175563_bv - 1;
        final int n2 = this.field_175562_bw - 1;
        final ITradeList[][] array2 = array[n];
        if (n2 < array2.length) {
            ITradeList[] array3;
            while (0 < (array3 = array2[n2]).length) {
                array3[0].func_179401_a(this.buyingList, this.rand);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList list) {
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final String customNameTag = this.getCustomNameTag();
        if (customNameTag != null && customNameTag.length() > 0) {
            return new ChatComponentText(customNameTag);
        }
        if (this.buyingList == null) {
            this.func_175554_cu();
        }
        String s = null;
        switch (this.getProfession()) {
            case 0: {
                if (this.field_175563_bv == 1) {
                    s = "farmer";
                    break;
                }
                if (this.field_175563_bv == 2) {
                    s = "fisherman";
                    break;
                }
                if (this.field_175563_bv == 3) {
                    s = "shepherd";
                    break;
                }
                if (this.field_175563_bv == 4) {
                    s = "fletcher";
                    break;
                }
                break;
            }
            case 1: {
                s = "librarian";
                break;
            }
            case 2: {
                s = "cleric";
                break;
            }
            case 3: {
                if (this.field_175563_bv == 1) {
                    s = "armor";
                    break;
                }
                if (this.field_175563_bv == 2) {
                    s = "weapon";
                    break;
                }
                if (this.field_175563_bv == 3) {
                    s = "tool";
                    break;
                }
                break;
            }
            case 4: {
                if (this.field_175563_bv == 1) {
                    s = "butcher";
                    break;
                }
                if (this.field_175563_bv == 2) {
                    s = "leather";
                    break;
                }
                break;
            }
        }
        if (s != null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("entity.Villager." + s, new Object[0]);
            chatComponentTranslation.getChatStyle().setChatHoverEvent(this.func_174823_aP());
            chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentTranslation;
        }
        return super.getDisplayName();
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isChild()) {
            n -= (float)0.81;
        }
        return n;
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 12) {
            this.func_180489_a(EnumParticleTypes.HEART);
        }
        else if (b == 13) {
            this.func_180489_a(EnumParticleTypes.VILLAGER_ANGRY);
        }
        else if (b == 14) {
            this.func_180489_a(EnumParticleTypes.VILLAGER_HAPPY);
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    private void func_180489_a(final EnumParticleTypes enumParticleTypes) {
        while (0 < 5) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 1.0 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, IEntityLivingData func_180482_a) {
        func_180482_a = super.func_180482_a(difficultyInstance, func_180482_a);
        this.setProfession(this.worldObj.rand.nextInt(5));
        this.func_175552_ct();
        return func_180482_a;
    }
    
    public void setLookingForHome() {
        this.isLookingForHome = true;
    }
    
    public EntityVillager func_180488_b(final EntityAgeable entityAgeable) {
        final EntityVillager entityVillager = new EntityVillager(this.worldObj);
        entityVillager.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityVillager)), null);
        return entityVillager;
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote) {
            final EntityWitch entityWitch = new EntityWitch(this.worldObj);
            entityWitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityWitch.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(entityWitch)), null);
            this.worldObj.spawnEntityInWorld(entityWitch);
            this.setDead();
        }
    }
    
    public InventoryBasic func_175551_co() {
        return this.field_175560_bz;
    }
    
    @Override
    protected void func_175445_a(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (this.func_175558_a(entityItem2.getItem())) {
            final ItemStack func_174894_a = this.field_175560_bz.func_174894_a(entityItem2);
            if (func_174894_a == null) {
                entityItem.setDead();
            }
            else {
                entityItem2.stackSize = func_174894_a.stackSize;
            }
        }
    }
    
    private boolean func_175558_a(final Item item) {
        return item == Items.bread || item == Items.potato || item == Items.carrot || item == Items.wheat || item == Items.wheat_seeds;
    }
    
    public boolean func_175553_cp() {
        return this.func_175559_s(1);
    }
    
    public boolean func_175555_cq() {
        return this.func_175559_s(2);
    }
    
    public boolean func_175557_cr() {
        return (this.getProfession() == 0) ? (!this.func_175559_s(5)) : (!this.func_175559_s(1));
    }
    
    private boolean func_175559_s(final int n) {
        final boolean b = this.getProfession() == 0;
        while (0 < this.field_175560_bz.getSizeInventory()) {
            final ItemStack stackInSlot = this.field_175560_bz.getStackInSlot(0);
            if (stackInSlot != null) {
                if ((stackInSlot.getItem() == Items.bread && stackInSlot.stackSize >= 3 * n) || (stackInSlot.getItem() == Items.potato && stackInSlot.stackSize >= 12 * n) || (stackInSlot.getItem() == Items.carrot && stackInSlot.stackSize >= 12 * n)) {
                    return true;
                }
                if (b && stackInSlot.getItem() == Items.wheat && stackInSlot.stackSize >= 9 * n) {
                    return true;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public boolean func_175556_cs() {
        while (0 < this.field_175560_bz.getSizeInventory()) {
            final ItemStack stackInSlot = this.field_175560_bz.getStackInSlot(0);
            if (stackInSlot != null && (stackInSlot.getItem() == Items.wheat_seeds || stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean func_174820_d(final int n, final ItemStack itemStack) {
        if (super.func_174820_d(n, itemStack)) {
            return true;
        }
        final int n2 = n - 300;
        if (n2 >= 0 && n2 < this.field_175560_bz.getSizeInventory()) {
            this.field_175560_bz.setInventorySlotContents(n2, itemStack);
            return true;
        }
        return false;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.func_180488_b(entityAgeable);
    }
    
    static class EmeraldForItems implements ITradeList
    {
        public Item field_179405_a;
        public PriceInfo field_179404_b;
        private static final String __OBFID;
        
        public EmeraldForItems(final Item field_179405_a, final PriceInfo field_179404_b) {
            this.field_179405_a = field_179405_a;
            this.field_179404_b = field_179404_b;
        }
        
        @Override
        public void func_179401_a(final MerchantRecipeList list, final Random random) {
            if (this.field_179404_b != null) {
                this.field_179404_b.func_179412_a(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.field_179405_a, 1, 0), Items.emerald));
        }
        
        static {
            __OBFID = "CL_00002194";
        }
    }
    
    interface ITradeList
    {
        void func_179401_a(final MerchantRecipeList p0, final Random p1);
    }
    
    static class PriceInfo extends Tuple
    {
        private static final String __OBFID;
        
        public PriceInfo(final int n, final int n2) {
            super(n, n2);
        }
        
        public int func_179412_a(final Random random) {
            return (int)(((int)this.getFirst() >= (int)this.getSecond()) ? this.getFirst() : ((int)this.getFirst() + random.nextInt((int)this.getSecond() - (int)this.getFirst() + 1)));
        }
        
        static {
            __OBFID = "CL_00002189";
        }
    }
    
    static class ItemAndEmeraldToItem implements ITradeList
    {
        public ItemStack field_179411_a;
        public PriceInfo field_179409_b;
        public ItemStack field_179410_c;
        public PriceInfo field_179408_d;
        private static final String __OBFID;
        
        public ItemAndEmeraldToItem(final Item item, final PriceInfo field_179409_b, final Item item2, final PriceInfo field_179408_d) {
            this.field_179411_a = new ItemStack(item);
            this.field_179409_b = field_179409_b;
            this.field_179410_c = new ItemStack(item2);
            this.field_179408_d = field_179408_d;
        }
        
        @Override
        public void func_179401_a(final MerchantRecipeList list, final Random random) {
            if (this.field_179409_b != null) {
                this.field_179409_b.func_179412_a(random);
            }
            if (this.field_179408_d != null) {
                this.field_179408_d.func_179412_a(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), 1, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), 1, this.field_179410_c.getMetadata())));
        }
        
        static {
            __OBFID = "CL_00002191";
        }
    }
    
    static class ListEnchantedBookForEmeralds implements ITradeList
    {
        private static final String __OBFID;
        
        @Override
        public void func_179401_a(final MerchantRecipeList list, final Random random) {
            final Enchantment enchantment = Enchantment.enchantmentsList[random.nextInt(Enchantment.enchantmentsList.length)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            final ItemStack enchantedItemStack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, randomIntegerInRange));
            final int n = 2 + random.nextInt(5 + randomIntegerInRange * 10) + 3 * randomIntegerInRange;
            if (64 > 64) {}
            list.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, 64), enchantedItemStack));
        }
        
        static {
            __OBFID = "CL_00002193";
        }
    }
    
    static class ListEnchantedItemForEmeralds implements ITradeList
    {
        public ItemStack field_179407_a;
        public PriceInfo field_179406_b;
        private static final String __OBFID;
        
        public ListEnchantedItemForEmeralds(final Item item, final PriceInfo field_179406_b) {
            this.field_179407_a = new ItemStack(item);
            this.field_179406_b = field_179406_b;
        }
        
        @Override
        public void func_179401_a(final MerchantRecipeList list, final Random random) {
            if (this.field_179406_b != null) {
                this.field_179406_b.func_179412_a(random);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata()), 5 + random.nextInt(15))));
        }
        
        static {
            __OBFID = "CL_00002192";
        }
    }
    
    static class ListItemForEmeralds implements ITradeList
    {
        public ItemStack field_179403_a;
        public PriceInfo field_179402_b;
        private static final String __OBFID;
        
        public ListItemForEmeralds(final Item item, final PriceInfo field_179402_b) {
            this.field_179403_a = new ItemStack(item);
            this.field_179402_b = field_179402_b;
        }
        
        public ListItemForEmeralds(final ItemStack field_179403_a, final PriceInfo field_179402_b) {
            this.field_179403_a = field_179403_a;
            this.field_179402_b = field_179402_b;
        }
        
        @Override
        public void func_179401_a(final MerchantRecipeList list, final Random random) {
            if (this.field_179402_b != null) {
                this.field_179402_b.func_179412_a(random);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), new ItemStack(this.field_179403_a.getItem(), 1, this.field_179403_a.getMetadata())));
        }
        
        static {
            __OBFID = "CL_00002190";
        }
    }
}
