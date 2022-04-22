package net.minecraft.item;

import java.text.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public final class ItemStack
{
    public static final DecimalFormat DECIMALFORMAT;
    public int stackSize;
    public int animationsToGo;
    private Item item;
    private NBTTagCompound stackTagCompound;
    private int itemDamage;
    private EntityItemFrame itemFrame;
    private Block canDestroyCacheBlock;
    private boolean canDestroyCacheResult;
    private Block canPlaceOnCacheBlock;
    private boolean canPlaceOnCacheResult;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000043";
        DECIMALFORMAT = new DecimalFormat("#.###");
    }
    
    public ItemStack(final Block block) {
        this(block, 1);
    }
    
    public ItemStack(final Block block, final int n) {
        this(block, n, 0);
    }
    
    public ItemStack(final Block block, final int n, final int n2) {
        this(Item.getItemFromBlock(block), n, n2);
    }
    
    public ItemStack(final Item item) {
        this(item, 1);
    }
    
    public ItemStack(final Item item, final int n) {
        this(item, n, 0);
    }
    
    public ItemStack(final Item item, final int stackSize, final int itemDamage) {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = false;
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = false;
        this.item = item;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public static ItemStack loadItemStackFromNBT(final NBTTagCompound nbtTagCompound) {
        final ItemStack itemStack = new ItemStack();
        itemStack.readFromNBT(nbtTagCompound);
        return (itemStack.getItem() != null) ? itemStack : null;
    }
    
    private ItemStack() {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = false;
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = false;
    }
    
    public ItemStack splitStack(final int n) {
        final ItemStack itemStack = new ItemStack(this.item, n, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= n;
        return itemStack;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public boolean onItemUse(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final boolean onItemUse = this.getItem().onItemUse(this, entityPlayer, world, blockPos, enumFacing, n, n2, n3);
        if (onItemUse) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
        return onItemUse;
    }
    
    public float getStrVsBlock(final Block block) {
        return this.getItem().getStrVsBlock(this, block);
    }
    
    public ItemStack useItemRightClick(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemRightClick(this, world, entityPlayer);
    }
    
    public ItemStack onItemUseFinish(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemUseFinish(this, world, entityPlayer);
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound nbtTagCompound) {
        final ResourceLocation resourceLocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
        nbtTagCompound.setString("id", (resourceLocation == null) ? "minecraft:air" : resourceLocation.toString());
        nbtTagCompound.setByte("Count", (byte)this.stackSize);
        nbtTagCompound.setShort("Damage", (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            nbtTagCompound.setTag("tag", this.stackTagCompound);
        }
        return nbtTagCompound;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("id", 8)) {
            this.item = Item.getByNameOrId(nbtTagCompound.getString("id"));
        }
        else {
            this.item = Item.getItemById(nbtTagCompound.getShort("id"));
        }
        this.stackSize = nbtTagCompound.getByte("Count");
        this.itemDamage = nbtTagCompound.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
        if (nbtTagCompound.hasKey("tag", 10)) {
            this.stackTagCompound = nbtTagCompound.getCompoundTag("tag");
            if (this.item != null) {
                this.item.updateItemStackNBT(this.stackTagCompound);
            }
        }
    }
    
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }
    
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }
    
    public boolean isItemStackDamageable() {
        return this.item != null && this.item.getMaxDamage() > 0 && (!this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable"));
    }
    
    public boolean getHasSubtypes() {
        return this.item.getHasSubtypes();
    }
    
    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public int getMetadata() {
        return this.itemDamage;
    }
    
    public void setItemDamage(final int itemDamage) {
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public int getMaxDamage() {
        return this.item.getMaxDamage();
    }
    
    public boolean attemptDamageItem(int n, final Random random) {
        if (!this.isItemStackDamageable()) {
            return false;
        }
        if (n > 0) {
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            while (enchantmentLevel > 0 && 0 < n) {
                if (EnchantmentDurability.negateDamage(this, enchantmentLevel, random)) {
                    int n2 = 0;
                    ++n2;
                }
                int n3 = 0;
                ++n3;
            }
            n -= 0;
            if (n <= 0) {
                return false;
            }
        }
        this.itemDamage += n;
        return this.itemDamage > this.getMaxDamage();
    }
    
    public void damageItem(final int n, final EntityLivingBase entityLivingBase) {
        if ((!(entityLivingBase instanceof EntityPlayer) || !((EntityPlayer)entityLivingBase).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(n, entityLivingBase.getRNG())) {
            entityLivingBase.renderBrokenItemStack(this);
            --this.stackSize;
            if (entityLivingBase instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
                entityPlayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
                if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                    entityPlayer.destroyCurrentEquippedItem();
                }
            }
            if (this.stackSize < 0) {
                this.stackSize = 0;
            }
            this.itemDamage = 0;
        }
    }
    
    public void hitEntity(final EntityLivingBase entityLivingBase, final EntityPlayer entityPlayer) {
        if (this.item.hitEntity(this, entityLivingBase, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public void onBlockDestroyed(final World world, final Block block, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (this.item.onBlockDestroyed(this, world, block, blockPos, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.item.canHarvestBlock(block);
    }
    
    public boolean interactWithEntity(final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        return this.item.itemInteractionForEntity(this, entityPlayer, entityLivingBase);
    }
    
    public ItemStack copy() {
        final ItemStack itemStack = new ItemStack(this.item, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return itemStack;
    }
    
    public static boolean areItemStackTagsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && (itemStack.stackTagCompound != null || itemStack2.stackTagCompound == null) && (itemStack.stackTagCompound == null || itemStack.stackTagCompound.equals(itemStack2.stackTagCompound)));
    }
    
    public static boolean areItemStacksEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && itemStack.isItemStackEqual(itemStack2));
    }
    
    private boolean isItemStackEqual(final ItemStack itemStack) {
        return this.stackSize == itemStack.stackSize && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage && (this.stackTagCompound != null || itemStack.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(itemStack.stackTagCompound));
    }
    
    public static boolean areItemsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && itemStack.isItemEqual(itemStack2));
    }
    
    public boolean isItemEqual(final ItemStack itemStack) {
        return itemStack != null && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage;
    }
    
    public String getUnlocalizedName() {
        return this.item.getUnlocalizedName(this);
    }
    
    public static ItemStack copyItemStack(final ItemStack itemStack) {
        return (itemStack == null) ? null : itemStack.copy();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.stackSize) + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
    }
    
    public void updateAnimation(final World world, final Entity entity, final int n, final boolean b) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        this.item.onUpdate(this, world, entity, n, b);
    }
    
    public void onCrafting(final World world, final EntityPlayer entityPlayer, final int n) {
        entityPlayer.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], n);
        this.item.onCreated(this, world, entityPlayer);
    }
    
    public boolean getIsItemStackEqual(final ItemStack itemStack) {
        return this.isItemStackEqual(itemStack);
    }
    
    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }
    
    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }
    
    public void onPlayerStoppedUsing(final World world, final EntityPlayer entityPlayer, final int n) {
        this.getItem().onPlayerStoppedUsing(this, world, entityPlayer, n);
    }
    
    public boolean hasTagCompound() {
        return this.stackTagCompound != null;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }
    
    public NBTTagCompound getSubCompound(final String s, final boolean b) {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(s, 10)) {
            return this.stackTagCompound.getCompoundTag(s);
        }
        if (b) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.setTagInfo(s, nbtTagCompound);
            return nbtTagCompound;
        }
        return null;
    }
    
    public NBTTagList getEnchantmentTagList() {
        return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
    }
    
    public void setTagCompound(final NBTTagCompound stackTagCompound) {
        this.stackTagCompound = stackTagCompound;
    }
    
    public String getDisplayName() {
        String s = this.getItem().getItemStackDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
            if (compoundTag.hasKey("Name", 8)) {
                s = compoundTag.getString("Name");
            }
        }
        return s;
    }
    
    public ItemStack setStackDisplayName(final String s) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        if (!this.stackTagCompound.hasKey("display", 10)) {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag("display").setString("Name", s);
        return this;
    }
    
    public void clearCustomName() {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
            compoundTag.removeTag("Name");
            if (compoundTag.hasNoTags()) {
                this.stackTagCompound.removeTag("display");
                if (this.stackTagCompound.hasNoTags()) {
                    this.setTagCompound(null);
                }
            }
        }
    }
    
    public boolean hasDisplayName() {
        return this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10) && this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8);
    }
    
    public List getTooltip(final EntityPlayer entityPlayer, final boolean b) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = this.getDisplayName();
        if (this.hasDisplayName()) {
            s = EnumChatFormatting.ITALIC + s;
        }
        String s2 = String.valueOf(s) + EnumChatFormatting.RESET;
        if (b) {
            String s3 = "";
            if (s2.length() > 0) {
                s2 = String.valueOf(s2) + " (";
                s3 = ")";
            }
            final int idFromItem = Item.getIdFromItem(this.item);
            if (this.getHasSubtypes()) {
                s2 = String.valueOf(s2) + String.format("#%04d/%d%s", idFromItem, this.itemDamage, s3);
            }
            else {
                s2 = String.valueOf(s2) + String.format("#%04d%s", idFromItem, s3);
            }
        }
        else if (!this.hasDisplayName() && this.item == Items.filled_map) {
            s2 = String.valueOf(s2) + " #" + this.itemDamage;
        }
        arrayList.add(s2);
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
            this.stackTagCompound.getInteger("HideFlags");
        }
        if (!false) {
            this.item.addInformation(this, entityPlayer, arrayList, b);
        }
        int n2 = 0;
        if (this.hasTagCompound()) {
            if (!false) {
                final NBTTagList enchantmentTagList = this.getEnchantmentTagList();
                if (enchantmentTagList != null) {
                    while (0 < enchantmentTagList.tagCount()) {
                        final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                        final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
                        if (Enchantment.func_180306_c(short1) != null) {
                            arrayList.add(Enchantment.func_180306_c(short1).getTranslatedName(short2));
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
            if (this.stackTagCompound.hasKey("display", 10)) {
                final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
                if (compoundTag.hasKey("color", 3)) {
                    if (b) {
                        arrayList.add("Color: #" + Integer.toHexString(compoundTag.getInteger("color")).toUpperCase());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }
                if (compoundTag.getTagType("Lore") == 9) {
                    final NBTTagList tagList = compoundTag.getTagList("Lore", 8);
                    if (tagList.tagCount() > 0) {
                        while (0 < tagList.tagCount()) {
                            arrayList.add(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(EnumChatFormatting.ITALIC).append(tagList.getStringTagAt(0)).toString());
                            ++n2;
                        }
                    }
                }
            }
        }
        final Multimap attributeModifiers = this.getAttributeModifiers();
        if (!attributeModifiers.isEmpty() && !false) {
            arrayList.add("");
            for (final Map.Entry<K, AttributeModifier> entry : attributeModifiers.entries()) {
                final AttributeModifier attributeModifier = entry.getValue();
                double amount = attributeModifier.getAmount();
                if (attributeModifier.getID() == Item.itemModifierUUID) {
                    amount += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
                }
                double n3;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    n3 = amount;
                }
                else {
                    n3 = amount * 100.0;
                }
                if (amount > 0.0) {
                    arrayList.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributeModifier.getOperation(), ItemStack.DECIMALFORMAT.format(n3), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey())));
                }
                else {
                    if (amount >= 0.0) {
                        continue;
                    }
                    arrayList.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributeModifier.getOperation(), ItemStack.DECIMALFORMAT.format(n3 * -1.0), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey())));
                }
            }
        }
        if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable") && !false) {
            arrayList.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && !false) {
            final NBTTagList tagList2 = this.stackTagCompound.getTagList("CanDestroy", 8);
            if (tagList2.tagCount() > 0) {
                arrayList.add("");
                arrayList.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));
                while (0 < tagList2.tagCount()) {
                    final Block blockFromName = Block.getBlockFromName(tagList2.getStringTagAt(0));
                    if (blockFromName != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName.getLocalizedName());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + "missingno");
                    }
                    ++n2;
                }
            }
        }
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && !false) {
            final NBTTagList tagList3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
            if (tagList3.tagCount() > 0) {
                arrayList.add("");
                arrayList.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));
                while (0 < tagList3.tagCount()) {
                    final Block blockFromName2 = Block.getBlockFromName(tagList3.getStringTagAt(0));
                    if (blockFromName2 != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName2.getLocalizedName());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + "missingno");
                    }
                    ++n2;
                }
            }
        }
        if (b) {
            if (this.isItemDamaged()) {
                arrayList.add("Durability: " + (this.getMaxDamage() - this.getItemDamage()) + " / " + this.getMaxDamage());
            }
            arrayList.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
            if (this.hasTagCompound()) {
                arrayList.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.getTagCompound().getKeySet().size() + " tag(s)");
            }
        }
        return arrayList;
    }
    
    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }
    
    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }
    
    public boolean isItemEnchantable() {
        return this.getItem().isItemTool(this) && !this.isItemEnchanted();
    }
    
    public void addEnchantment(final Enchantment enchantment, final int n) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.hasKey("ench", 9)) {
            this.stackTagCompound.setTag("ench", new NBTTagList());
        }
        final NBTTagList tagList = this.stackTagCompound.getTagList("ench", 10);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setShort("id", (short)enchantment.effectId);
        nbtTagCompound.setShort("lvl", (byte)n);
        tagList.appendTag(nbtTagCompound);
    }
    
    public boolean isItemEnchanted() {
        return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9);
    }
    
    public void setTagInfo(final String s, final NBTBase nbtBase) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(s, nbtBase);
    }
    
    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }
    
    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }
    
    public void setItemFrame(final EntityItemFrame itemFrame) {
        this.itemFrame = itemFrame;
    }
    
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }
    
    public int getRepairCost() {
        return (this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }
    
    public void setRepairCost(final int n) {
        if (!this.hasTagCompound()) {
            this.stackTagCompound = new NBTTagCompound();
        }
        this.stackTagCompound.setInteger("RepairCost", n);
    }
    
    public Multimap getAttributeModifiers() {
        Multimap multimap;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
            multimap = HashMultimap.create();
            final NBTTagList tagList = this.stackTagCompound.getTagList("AttributeModifiers", 10);
            while (0 < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                final AttributeModifier attributeModifierFromNBT = SharedMonsterAttributes.readAttributeModifierFromNBT(compoundTag);
                if (attributeModifierFromNBT != null && attributeModifierFromNBT.getID().getLeastSignificantBits() != 0L && attributeModifierFromNBT.getID().getMostSignificantBits() != 0L) {
                    ((HashMultimap)multimap).put(compoundTag.getString("AttributeName"), attributeModifierFromNBT);
                }
                int n = 0;
                ++n;
            }
        }
        else {
            multimap = this.getItem().getItemAttributeModifiers();
        }
        return multimap;
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public IChatComponent getChatComponent() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getDisplayName());
        if (this.hasDisplayName()) {
            chatComponentText.getChatStyle().setItalic(true);
        }
        final IChatComponent appendText = new ChatComponentText("[").appendSibling(chatComponentText).appendText("]");
        if (this.item != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.writeToNBT(nbtTagCompound);
            appendText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbtTagCompound.toString())));
            appendText.getChatStyle().setColor(this.getRarity().rarityColor);
        }
        return appendText;
    }
    
    public boolean canDestroy(final Block canDestroyCacheBlock) {
        if (canDestroyCacheBlock == this.canDestroyCacheBlock) {
            return this.canDestroyCacheResult;
        }
        this.canDestroyCacheBlock = canDestroyCacheBlock;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList("CanDestroy", 8);
            while (0 < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(0)) == canDestroyCacheBlock) {
                    return this.canDestroyCacheResult = true;
                }
                int n = 0;
                ++n;
            }
        }
        return this.canDestroyCacheResult = false;
    }
    
    public boolean canPlaceOn(final Block canPlaceOnCacheBlock) {
        if (canPlaceOnCacheBlock == this.canPlaceOnCacheBlock) {
            return this.canPlaceOnCacheResult;
        }
        this.canPlaceOnCacheBlock = canPlaceOnCacheBlock;
        if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList("CanPlaceOn", 8);
            while (0 < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(0)) == canPlaceOnCacheBlock) {
                    return this.canPlaceOnCacheResult = true;
                }
                int n = 0;
                ++n;
            }
        }
        return this.canPlaceOnCacheResult = false;
    }
}
