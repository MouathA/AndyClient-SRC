package net.minecraft.entity.player;

import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class InventoryPlayer implements IInventory
{
    public ItemStack[] mainInventory;
    public ItemStack[] armorInventory;
    public int currentItem;
    public EntityPlayer player;
    private ItemStack itemStack;
    public boolean inventoryChanged;
    private static final String __OBFID;
    
    public InventoryPlayer(final EntityPlayer player) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        this.player = player;
    }
    
    public ItemStack getCurrentItem() {
        return (this.currentItem < 9 && this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
    }
    
    private int getInventorySlotContainItem(final Item item) {
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null && this.mainInventory[0].getItem() == item) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private int getInventorySlotContainItemAndDamage(final Item item, final int n) {
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null && this.mainInventory[0].getItem() == item && this.mainInventory[0].getMetadata() == n) {
                return 0;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    private int storeItemStack(final ItemStack itemStack) {
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null && this.mainInventory[0].getItem() == itemStack.getItem() && this.mainInventory[0].isStackable() && this.mainInventory[0].stackSize < this.mainInventory[0].getMaxStackSize() && this.mainInventory[0].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[0].getHasSubtypes() || this.mainInventory[0].getMetadata() == itemStack.getMetadata()) && ItemStack.areItemStackTagsEqual(this.mainInventory[0], itemStack)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public int getFirstEmptyStack() {
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] == null) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public void setCurrentItem(final Item item, final int n, final boolean b, final boolean b2) {
        final ItemStack currentItem = this.getCurrentItem();
        final int currentItem2 = b ? this.getInventorySlotContainItemAndDamage(item, n) : this.getInventorySlotContainItem(item);
        if (currentItem2 >= 0 && currentItem2 < 9) {
            this.currentItem = currentItem2;
        }
        else if (b2 && item != null) {
            final int firstEmptyStack = this.getFirstEmptyStack();
            if (firstEmptyStack >= 0 && firstEmptyStack < 9) {
                this.currentItem = firstEmptyStack;
            }
            if (currentItem == null || !currentItem.isItemEnchantable() || this.getInventorySlotContainItemAndDamage(currentItem.getItem(), currentItem.getItemDamage()) != this.currentItem) {
                final int inventorySlotContainItemAndDamage = this.getInventorySlotContainItemAndDamage(item, n);
                if (inventorySlotContainItemAndDamage >= 0) {
                    final int stackSize = this.mainInventory[inventorySlotContainItemAndDamage].stackSize;
                    this.mainInventory[inventorySlotContainItemAndDamage] = this.mainInventory[this.currentItem];
                }
                this.mainInventory[this.currentItem] = new ItemStack(item, 1, n);
            }
        }
    }
    
    public void changeCurrentItem(final int n) {
        if (-1 > 0) {}
        if (-1 < 0) {}
        ++this.currentItem;
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }
    
    public int func_174925_a(final Item item, final int n, final int n2, final NBTTagCompound nbtTagCompound) {
        int n4 = 0;
        while (0 < this.mainInventory.length) {
            final ItemStack itemStack = this.mainInventory[0];
            if (itemStack != null && (item == null || itemStack.getItem() == item) && (n <= -1 || itemStack.getMetadata() == n) && (nbtTagCompound == null || CommandTestForBlock.func_175775_a(nbtTagCompound, itemStack.getTagCompound(), true))) {
                final int n3 = (n2 <= 0) ? itemStack.stackSize : Math.min(n2 - 0, itemStack.stackSize);
                if (n2 != 0) {
                    final ItemStack itemStack2 = this.mainInventory[0];
                    itemStack2.stackSize -= n3;
                    if (this.mainInventory[0].stackSize == 0) {
                        this.mainInventory[0] = null;
                    }
                    if (n2 > 0 && 0 >= n2) {
                        return 0;
                    }
                }
            }
            ++n4;
        }
        while (0 < this.armorInventory.length) {
            final ItemStack itemStack3 = this.armorInventory[0];
            if (itemStack3 != null && (item == null || itemStack3.getItem() == item) && (n <= -1 || itemStack3.getMetadata() == n) && (nbtTagCompound == null || CommandTestForBlock.func_175775_a(nbtTagCompound, itemStack3.getTagCompound(), false))) {
                final int n5 = (n2 <= 0) ? itemStack3.stackSize : Math.min(n2 - 0, itemStack3.stackSize);
                if (n2 != 0) {
                    final ItemStack itemStack4 = this.armorInventory[0];
                    itemStack4.stackSize -= n5;
                    if (this.armorInventory[0].stackSize == 0) {
                        this.armorInventory[0] = null;
                    }
                    if (n2 > 0 && 0 >= n2) {
                        return 0;
                    }
                }
            }
            ++n4;
        }
        if (this.itemStack != null) {
            if (item != null && this.itemStack.getItem() != item) {
                return 0;
            }
            if (n > -1 && this.itemStack.getMetadata() != n) {
                return 0;
            }
            if (nbtTagCompound != null && !CommandTestForBlock.func_175775_a(nbtTagCompound, this.itemStack.getTagCompound(), false)) {
                return 0;
            }
            n4 = ((n2 <= 0) ? this.itemStack.stackSize : Math.min(n2 - 0, this.itemStack.stackSize));
            if (n2 != 0) {
                final ItemStack itemStack5 = this.itemStack;
                itemStack5.stackSize -= 0;
                if (this.itemStack.stackSize == 0) {
                    this.itemStack = null;
                }
                if (n2 > 0 && 0 >= n2) {
                    return 0;
                }
            }
        }
        return 0;
    }
    
    private int storePartialItemStack(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        final int stackSize = itemStack.stackSize;
        int n = this.storeItemStack(itemStack);
        if (n < 0) {
            n = this.getFirstEmptyStack();
        }
        if (n < 0) {
            return stackSize;
        }
        if (this.mainInventory[n] == null) {
            this.mainInventory[n] = new ItemStack(item, 0, itemStack.getMetadata());
            if (itemStack.hasTagCompound()) {
                this.mainInventory[n].setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            }
        }
        int n2;
        if ((n2 = stackSize) > this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize) {
            n2 = this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize;
        }
        if (n2 > this.getInventoryStackLimit() - this.mainInventory[n].stackSize) {
            n2 = this.getInventoryStackLimit() - this.mainInventory[n].stackSize;
        }
        if (n2 == 0) {
            return stackSize;
        }
        final int n3 = stackSize - n2;
        final ItemStack itemStack2 = this.mainInventory[n];
        itemStack2.stackSize += n2;
        this.mainInventory[n].animationsToGo = 5;
        return n3;
    }
    
    public void decrementAnimations() {
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null) {
                this.mainInventory[0].updateAnimation(this.player.worldObj, this.player, 0, this.currentItem == 0);
            }
            int n = 0;
            ++n;
        }
    }
    
    public boolean consumeInventoryItem(final Item item) {
        final int inventorySlotContainItem = this.getInventorySlotContainItem(item);
        if (inventorySlotContainItem < 0) {
            return false;
        }
        final ItemStack itemStack = this.mainInventory[inventorySlotContainItem];
        if (--itemStack.stackSize <= 0) {
            this.mainInventory[inventorySlotContainItem] = null;
        }
        return true;
    }
    
    public boolean hasItem(final Item item) {
        return this.getInventorySlotContainItem(item) >= 0;
    }
    
    public boolean addItemStackToInventory(final ItemStack itemStack) {
        if (itemStack == null || itemStack.stackSize == 0 || itemStack.getItem() == null) {
            return false;
        }
        if (itemStack.isItemDamaged()) {
            final int firstEmptyStack = this.getFirstEmptyStack();
            if (firstEmptyStack >= 0) {
                this.mainInventory[firstEmptyStack] = ItemStack.copyItemStack(itemStack);
                this.mainInventory[firstEmptyStack].animationsToGo = 5;
                itemStack.stackSize = 0;
                return true;
            }
            if (this.player.capabilities.isCreativeMode) {
                itemStack.stackSize = 0;
                return true;
            }
            return false;
        }
        else {
            int stackSize;
            do {
                stackSize = itemStack.stackSize;
                itemStack.stackSize = this.storePartialItemStack(itemStack);
            } while (itemStack.stackSize > 0 && itemStack.stackSize < stackSize);
            if (itemStack.stackSize == stackSize && this.player.capabilities.isCreativeMode) {
                itemStack.stackSize = 0;
                return true;
            }
            return itemStack.stackSize < stackSize;
        }
    }
    
    @Override
    public ItemStack decrStackSize(int n, final int n2) {
        ItemStack[] array = this.mainInventory;
        if (n >= this.mainInventory.length) {
            array = this.armorInventory;
            n -= this.mainInventory.length;
        }
        if (array[n] == null) {
            return null;
        }
        if (array[n].stackSize <= n2) {
            final ItemStack itemStack = array[n];
            array[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = array[n].splitStack(n2);
        if (array[n].stackSize == 0) {
            array[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int n) {
        ItemStack[] array = this.mainInventory;
        if (n >= this.mainInventory.length) {
            array = this.armorInventory;
            n -= this.mainInventory.length;
        }
        if (array[n] != null) {
            final ItemStack itemStack = array[n];
            array[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int n, final ItemStack itemStack) {
        ItemStack[] array = this.mainInventory;
        if (n >= array.length) {
            n -= array.length;
            array = this.armorInventory;
        }
        array[n] = itemStack;
    }
    
    public float getStrVsBlock(final Block block) {
        float n = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            n *= this.mainInventory[this.currentItem].getStrVsBlock(block);
        }
        return n;
    }
    
    public NBTTagList writeToNBT(final NBTTagList list) {
        int n = 0;
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte)0);
                this.mainInventory[0].writeToNBT(nbtTagCompound);
                list.appendTag(nbtTagCompound);
            }
            ++n;
        }
        while (0 < this.armorInventory.length) {
            if (this.armorInventory[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)100);
                this.armorInventory[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++n;
        }
        return list;
    }
    
    public void readFromNBT(final NBTTagList list) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(compoundTag);
            if (loadItemStackFromNBT != null) {
                if (n >= 0 && n < this.mainInventory.length) {
                    this.mainInventory[n] = loadItemStackFromNBT;
                }
                if (n >= 100 && n < this.armorInventory.length + 100) {
                    this.armorInventory[n - 100] = loadItemStackFromNBT;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }
    
    @Override
    public ItemStack getStackInSlot(int n) {
        ItemStack[] array = this.mainInventory;
        if (n >= array.length) {
            n -= array.length;
            array = this.armorInventory;
        }
        return array[n];
    }
    
    @Override
    public String getName() {
        return "container.inventory";
    }
    
    @Override
    public boolean hasCustomName() {
        return false;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean func_146025_b(final Block block) {
        if (block.getMaterial().isToolNotRequired()) {
            return true;
        }
        final ItemStack stackInSlot = this.getStackInSlot(this.currentItem);
        return stackInSlot != null && stackInSlot.canHarvestBlock(block);
    }
    
    public ItemStack armorItemInSlot(final int n) {
        return this.armorInventory[n];
    }
    
    public int getTotalArmorValue() {
        while (0 < this.armorInventory.length) {
            if (this.armorInventory[0] != null && this.armorInventory[0].getItem() instanceof ItemArmor) {
                final int n = 0 + ((ItemArmor)this.armorInventory[0].getItem()).damageReduceAmount;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public void damageArmor(float n) {
        n /= 4.0f;
        if (n < 1.0f) {
            n = 1.0f;
        }
        while (0 < this.armorInventory.length) {
            if (this.armorInventory[0] != null && this.armorInventory[0].getItem() instanceof ItemArmor) {
                this.armorInventory[0].damageItem((int)n, this.player);
                if (this.armorInventory[0].stackSize == 0) {
                    this.armorInventory[0] = null;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void dropAllItems() {
        int n = 0;
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null) {
                this.player.func_146097_a(this.mainInventory[0], true, false);
                this.mainInventory[0] = null;
            }
            ++n;
        }
        while (0 < this.armorInventory.length) {
            if (this.armorInventory[0] != null) {
                this.player.func_146097_a(this.armorInventory[0], true, false);
                this.armorInventory[0] = null;
            }
            ++n;
        }
    }
    
    @Override
    public void markDirty() {
        this.inventoryChanged = true;
    }
    
    public void setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return !this.player.isDead && entityPlayer.getDistanceSqToEntity(this.player) <= 64.0;
    }
    
    public boolean hasItemStack(final ItemStack itemStack) {
        int n = 0;
        while (0 < this.armorInventory.length) {
            if (this.armorInventory[0] != null && this.armorInventory[0].isItemEqual(itemStack)) {
                return true;
            }
            ++n;
        }
        while (0 < this.mainInventory.length) {
            if (this.mainInventory[0] != null && this.mainInventory[0].isItemEqual(itemStack)) {
                return true;
            }
            ++n;
        }
        return false;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    public void copyInventory(final InventoryPlayer inventoryPlayer) {
        int n = 0;
        while (0 < this.mainInventory.length) {
            this.mainInventory[0] = ItemStack.copyItemStack(inventoryPlayer.mainInventory[0]);
            ++n;
        }
        while (0 < this.armorInventory.length) {
            this.armorInventory[0] = ItemStack.copyItemStack(inventoryPlayer.armorInventory[0]);
            ++n;
        }
        this.currentItem = inventoryPlayer.currentItem;
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public void clearInventory() {
        int n = 0;
        while (0 < this.mainInventory.length) {
            this.mainInventory[0] = null;
            ++n;
        }
        while (0 < this.armorInventory.length) {
            this.armorInventory[0] = null;
            ++n;
        }
    }
    
    public ItemStack getInvStack(int n) {
        ItemStack[] array = this.mainInventory;
        if (n >= array.length) {
            n -= array.length;
            array = this.armorInventory;
        }
        return array[n];
    }
    
    static {
        __OBFID = "CL_00001709";
    }
}
