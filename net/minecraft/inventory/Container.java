package net.minecraft.inventory;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public abstract class Container
{
    public List inventoryItemStacks;
    public List inventorySlots;
    public int windowId;
    private short transactionID;
    private int dragMode;
    private int dragEvent;
    private final Set dragSlots;
    protected List crafters;
    private Set playerList;
    private static final String __OBFID;
    
    public Container() {
        this.inventoryItemStacks = Lists.newArrayList();
        this.inventorySlots = Lists.newArrayList();
        this.dragMode = -1;
        this.dragSlots = Sets.newHashSet();
        this.crafters = Lists.newArrayList();
        this.playerList = Sets.newHashSet();
    }
    
    protected Slot addSlotToContainer(final Slot slot) {
        slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(slot);
        this.inventoryItemStacks.add(null);
        return slot;
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        if (this.crafters.contains(crafting)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.crafters.add(crafting);
        crafting.updateCraftingInventory(this, this.getInventory());
        this.detectAndSendChanges();
    }
    
    public void removeCraftingFromCrafters(final ICrafting crafting) {
        this.crafters.remove(crafting);
    }
    
    public List getInventory() {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.inventorySlots.size()) {
            arrayList.add(this.inventorySlots.get(0).getStack());
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public void detectAndSendChanges() {
        while (0 < this.inventorySlots.size()) {
            final ItemStack stack = this.inventorySlots.get(0).getStack();
            if (!ItemStack.areItemStacksEqual(this.inventoryItemStacks.get(0), stack)) {
                final ItemStack itemStack = (stack == null) ? null : stack.copy();
                this.inventoryItemStacks.set(0, itemStack);
                while (0 < this.crafters.size()) {
                    this.crafters.get(0).sendSlotContents(this, 0, itemStack);
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        return false;
    }
    
    public Slot getSlotFromInventory(final IInventory inventory, final int n) {
        while (0 < this.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.get(0);
            if (slot.isHere(inventory, n)) {
                return slot;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public Slot getSlot(final int n) {
        return this.inventorySlots.get(n);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        final Slot slot = this.inventorySlots.get(n);
        return (slot != null) ? slot.getStack() : null;
    }
    
    public ItemStack slotClick(final int n, final int n2, final int n3, final EntityPlayer entityPlayer) {
        ItemStack itemStack = null;
        final InventoryPlayer inventory = entityPlayer.inventory;
        if (n3 == 5) {
            final int dragEvent = this.dragEvent;
            this.dragEvent = getDragEvent(n2);
            if ((dragEvent != 1 || this.dragEvent != 2) && dragEvent != this.dragEvent) {
                this.resetDrag();
            }
            else if (inventory.getItemStack() == null) {
                this.resetDrag();
            }
            else if (this.dragEvent == 0) {
                this.dragMode = extractDragMode(n2);
                if (func_180610_a(this.dragMode, entityPlayer)) {
                    this.dragEvent = 1;
                    this.dragSlots.clear();
                }
                else {
                    this.resetDrag();
                }
            }
            else if (this.dragEvent == 1) {
                final Slot slot = this.inventorySlots.get(n);
                if (slot != null && canAddItemToSlot(slot, inventory.getItemStack(), true) && slot.isItemValid(inventory.getItemStack()) && inventory.getItemStack().stackSize > this.dragSlots.size() && this.canDragIntoSlot(slot)) {
                    this.dragSlots.add(slot);
                }
            }
            else if (this.dragEvent == 2) {
                if (!this.dragSlots.isEmpty()) {
                    ItemStack copy = inventory.getItemStack().copy();
                    int stackSize = inventory.getItemStack().stackSize;
                    for (final Slot slot2 : this.dragSlots) {
                        if (slot2 != null && canAddItemToSlot(slot2, inventory.getItemStack(), true) && slot2.isItemValid(inventory.getItemStack()) && inventory.getItemStack().stackSize >= this.dragSlots.size() && this.canDragIntoSlot(slot2)) {
                            final ItemStack copy2 = copy.copy();
                            final int n4 = slot2.getHasStack() ? slot2.getStack().stackSize : 0;
                            computeStackSize(this.dragSlots, this.dragMode, copy2, n4);
                            if (copy2.stackSize > copy2.getMaxStackSize()) {
                                copy2.stackSize = copy2.getMaxStackSize();
                            }
                            if (copy2.stackSize > slot2.func_178170_b(copy2)) {
                                copy2.stackSize = slot2.func_178170_b(copy2);
                            }
                            stackSize -= copy2.stackSize - n4;
                            slot2.putStack(copy2);
                        }
                    }
                    copy.stackSize = stackSize;
                    if (copy.stackSize <= 0) {
                        copy = null;
                    }
                    inventory.setItemStack(copy);
                }
                this.resetDrag();
            }
            else {
                this.resetDrag();
            }
        }
        else if (this.dragEvent != 0) {
            this.resetDrag();
        }
        else if ((n3 == 0 || n3 == 1) && (n2 == 0 || n2 == 1)) {
            if (n == -999) {
                if (inventory.getItemStack() != null) {
                    if (n2 == 0) {
                        entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), true);
                        inventory.setItemStack(null);
                    }
                    if (n2 == 1) {
                        entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack().splitStack(1), true);
                        if (inventory.getItemStack().stackSize == 0) {
                            inventory.setItemStack(null);
                        }
                    }
                }
            }
            else if (n3 == 1) {
                if (n < 0) {
                    return null;
                }
                final Slot slot3 = this.inventorySlots.get(n);
                if (slot3 != null && slot3.canTakeStack(entityPlayer)) {
                    final ItemStack transferStackInSlot = this.transferStackInSlot(entityPlayer, n);
                    if (transferStackInSlot != null) {
                        final Item item = transferStackInSlot.getItem();
                        itemStack = transferStackInSlot.copy();
                        if (slot3.getStack() != null && slot3.getStack().getItem() == item) {
                            this.retrySlotClick(n, n2, true, entityPlayer);
                        }
                    }
                }
            }
            else {
                if (n < 0) {
                    return null;
                }
                final Slot slot4 = this.inventorySlots.get(n);
                if (slot4 != null) {
                    final ItemStack stack = slot4.getStack();
                    final ItemStack itemStack2 = inventory.getItemStack();
                    if (stack != null) {
                        itemStack = stack.copy();
                    }
                    if (stack == null) {
                        if (itemStack2 != null && slot4.isItemValid(itemStack2)) {
                            final int n5 = (n2 == 0) ? itemStack2.stackSize : 1;
                            if (-1 > slot4.func_178170_b(itemStack2)) {
                                slot4.func_178170_b(itemStack2);
                            }
                            if (itemStack2.stackSize >= -1) {
                                slot4.putStack(itemStack2.splitStack(-1));
                            }
                            if (itemStack2.stackSize == 0) {
                                inventory.setItemStack(null);
                            }
                        }
                    }
                    else if (slot4.canTakeStack(entityPlayer)) {
                        if (itemStack2 == null) {
                            final int n6 = (n2 == 0) ? stack.stackSize : ((stack.stackSize + 1) / 2);
                            inventory.setItemStack(slot4.decrStackSize(-1));
                            if (stack.stackSize == 0) {
                                slot4.putStack(null);
                            }
                            slot4.onPickupFromSlot(entityPlayer, inventory.getItemStack());
                        }
                        else if (slot4.isItemValid(itemStack2)) {
                            if (stack.getItem() == itemStack2.getItem() && stack.getMetadata() == itemStack2.getMetadata() && ItemStack.areItemStackTagsEqual(stack, itemStack2)) {
                                final int n7 = (n2 == 0) ? itemStack2.stackSize : 1;
                                if (-1 > slot4.func_178170_b(itemStack2) - stack.stackSize) {
                                    final int n8 = slot4.func_178170_b(itemStack2) - stack.stackSize;
                                }
                                if (-1 > itemStack2.getMaxStackSize() - stack.stackSize) {
                                    final int n9 = itemStack2.getMaxStackSize() - stack.stackSize;
                                }
                                itemStack2.splitStack(-1);
                                if (itemStack2.stackSize == 0) {
                                    inventory.setItemStack(null);
                                }
                                final ItemStack itemStack3 = stack;
                                --itemStack3.stackSize;
                            }
                            else if (itemStack2.stackSize <= slot4.func_178170_b(itemStack2)) {
                                slot4.putStack(itemStack2);
                                inventory.setItemStack(stack);
                            }
                        }
                        else if (stack.getItem() == itemStack2.getItem() && itemStack2.getMaxStackSize() > 1 && (!stack.getHasSubtypes() || stack.getMetadata() == itemStack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemStack2)) {
                            final int stackSize2 = stack.stackSize;
                            if (-1 > 0 && -1 + itemStack2.stackSize <= itemStack2.getMaxStackSize()) {
                                final ItemStack itemStack4 = itemStack2;
                                --itemStack4.stackSize;
                                if (slot4.decrStackSize(-1).stackSize == 0) {
                                    slot4.putStack(null);
                                }
                                slot4.onPickupFromSlot(entityPlayer, inventory.getItemStack());
                            }
                        }
                    }
                    slot4.onSlotChanged();
                }
            }
        }
        else if (n3 == 2 && n2 >= 0 && n2 < 9) {
            final Slot slot5 = this.inventorySlots.get(n);
            if (slot5.canTakeStack(entityPlayer)) {
                final ItemStack stackInSlot = inventory.getStackInSlot(n2);
                int n10 = (stackInSlot == null || (slot5.inventory == inventory && slot5.isItemValid(stackInSlot))) ? 1 : 0;
                if (!false) {
                    inventory.getFirstEmptyStack();
                    n10 = ((false | -1 > -1) ? 1 : 0);
                }
                if (slot5.getHasStack() && false) {
                    final ItemStack stack2 = slot5.getStack();
                    inventory.setInventorySlotContents(n2, stack2.copy());
                    if ((slot5.inventory != inventory || !slot5.isItemValid(stackInSlot)) && stackInSlot != null) {
                        if (-1 > -1) {
                            inventory.addItemStackToInventory(stackInSlot);
                            slot5.decrStackSize(stack2.stackSize);
                            slot5.putStack(null);
                            slot5.onPickupFromSlot(entityPlayer, stack2);
                        }
                    }
                    else {
                        slot5.decrStackSize(stack2.stackSize);
                        slot5.putStack(stackInSlot);
                        slot5.onPickupFromSlot(entityPlayer, stack2);
                    }
                }
                else if (!slot5.getHasStack() && stackInSlot != null && slot5.isItemValid(stackInSlot)) {
                    inventory.setInventorySlotContents(n2, null);
                    slot5.putStack(stackInSlot);
                }
            }
        }
        else if (n3 == 3 && entityPlayer.capabilities.isCreativeMode && inventory.getItemStack() == null && n >= 0) {
            final Slot slot6 = this.inventorySlots.get(n);
            if (slot6 != null && slot6.getHasStack()) {
                final ItemStack copy3 = slot6.getStack().copy();
                copy3.stackSize = copy3.getMaxStackSize();
                inventory.setItemStack(copy3);
            }
        }
        else if (n3 == 4 && inventory.getItemStack() == null && n >= 0) {
            final Slot slot7 = this.inventorySlots.get(n);
            if (slot7 != null && slot7.getHasStack() && slot7.canTakeStack(entityPlayer)) {
                final ItemStack decrStackSize = slot7.decrStackSize((n2 == 0) ? 1 : slot7.getStack().stackSize);
                slot7.onPickupFromSlot(entityPlayer, decrStackSize);
                entityPlayer.dropPlayerItemWithRandomChoice(decrStackSize, true);
            }
        }
        else if (n3 == 6 && n >= 0) {
            final Slot slot8 = this.inventorySlots.get(n);
            final ItemStack itemStack5 = inventory.getItemStack();
            if (itemStack5 != null && (slot8 == null || !slot8.getHasStack() || !slot8.canTakeStack(entityPlayer))) {
                final int n11 = (n2 == 0) ? 0 : (this.inventorySlots.size() - 1);
                final int n12 = (n2 == 0) ? 1 : -1;
                while (0 < 2) {
                    for (int n13 = n11; n13 >= 0 && n13 < this.inventorySlots.size() && itemStack5.stackSize < itemStack5.getMaxStackSize(); --n13) {
                        final Slot slot9 = this.inventorySlots.get(n13);
                        if (slot9.getHasStack() && canAddItemToSlot(slot9, itemStack5, true) && slot9.canTakeStack(entityPlayer) && this.func_94530_a(itemStack5, slot9) && (false || slot9.getStack().stackSize != slot9.getStack().getMaxStackSize())) {
                            final int min = Math.min(itemStack5.getMaxStackSize() - itemStack5.stackSize, slot9.getStack().stackSize);
                            final ItemStack decrStackSize2 = slot9.decrStackSize(min);
                            final ItemStack itemStack6 = itemStack5;
                            itemStack6.stackSize += min;
                            if (decrStackSize2.stackSize <= 0) {
                                slot9.putStack(null);
                            }
                            slot9.onPickupFromSlot(entityPlayer, decrStackSize2);
                        }
                    }
                    int n10 = 0;
                    ++n10;
                }
            }
            this.detectAndSendChanges();
        }
        return itemStack;
    }
    
    public boolean func_94530_a(final ItemStack itemStack, final Slot slot) {
        return true;
    }
    
    protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        this.slotClick(n, n2, 1, entityPlayer);
    }
    
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        final InventoryPlayer inventory = entityPlayer.inventory;
        if (inventory.getItemStack() != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), false);
            inventory.setItemStack(null);
        }
    }
    
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.detectAndSendChanges();
    }
    
    public void putStackInSlot(final int n, final ItemStack itemStack) {
        this.getSlot(n).putStack(itemStack);
    }
    
    public void putStacksInSlots(final ItemStack[] array) {
        while (0 < array.length) {
            this.getSlot(0).putStack(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void updateProgressBar(final int n, final int n2) {
    }
    
    public short getNextTransactionID(final InventoryPlayer inventoryPlayer) {
        return (short)(++this.transactionID);
    }
    
    public boolean getCanCraft(final EntityPlayer entityPlayer) {
        return !this.playerList.contains(entityPlayer);
    }
    
    public void setCanCraft(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.playerList.remove(entityPlayer);
        }
        else {
            this.playerList.add(entityPlayer);
        }
    }
    
    public abstract boolean canInteractWith(final EntityPlayer p0);
    
    protected boolean mergeItemStack(final ItemStack itemStack, final int n, final int n2, final boolean b) {
        int n3 = n;
        if (b) {
            n3 = n2 - 1;
        }
        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && ((!b && n3 < n2) || (b && n3 >= n))) {
                final Slot slot = this.inventorySlots.get(n3);
                final ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() || itemStack.getMetadata() == stack.getMetadata()) && ItemStack.areItemStackTagsEqual(itemStack, stack)) {
                    final int stackSize = stack.stackSize + itemStack.stackSize;
                    if (stackSize <= itemStack.getMaxStackSize()) {
                        itemStack.stackSize = 0;
                        stack.stackSize = stackSize;
                        slot.onSlotChanged();
                    }
                    else if (stack.stackSize < itemStack.getMaxStackSize()) {
                        itemStack.stackSize -= itemStack.getMaxStackSize() - stack.stackSize;
                        stack.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                    }
                }
                if (b) {
                    --n3;
                }
                else {
                    ++n3;
                }
            }
        }
        if (itemStack.stackSize > 0) {
            int n4;
            if (b) {
                n4 = n2 - 1;
            }
            else {
                n4 = n;
            }
            while ((!b && n4 < n2) || (b && n4 >= n)) {
                final Slot slot2 = this.inventorySlots.get(n4);
                if (slot2.getStack() == null) {
                    slot2.putStack(itemStack.copy());
                    slot2.onSlotChanged();
                    itemStack.stackSize = 0;
                    break;
                }
                if (b) {
                    --n4;
                }
                else {
                    ++n4;
                }
            }
        }
        return true;
    }
    
    public static int extractDragMode(final int n) {
        return n >> 2 & 0x3;
    }
    
    public static int getDragEvent(final int n) {
        return n & 0x3;
    }
    
    public static int func_94534_d(final int n, final int n2) {
        return (n & 0x3) | (n2 & 0x3) << 2;
    }
    
    public static boolean func_180610_a(final int n, final EntityPlayer entityPlayer) {
        return n == 0 || n == 1 || (n == 2 && entityPlayer.capabilities.isCreativeMode);
    }
    
    protected void resetDrag() {
        this.dragEvent = 0;
        this.dragSlots.clear();
    }
    
    public static boolean canAddItemToSlot(final Slot slot, final ItemStack itemStack, final boolean b) {
        boolean b2 = slot == null || !slot.getHasStack();
        if (slot != null && slot.getHasStack() && itemStack != null && itemStack.isItemEqual(slot.getStack()) && ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack)) {
            b2 |= (slot.getStack().stackSize + (b ? 0 : itemStack.stackSize) <= itemStack.getMaxStackSize());
        }
        return b2;
    }
    
    public static void computeStackSize(final Set set, final int n, final ItemStack itemStack, final int n2) {
        switch (n) {
            case 0: {
                itemStack.stackSize = MathHelper.floor_float(itemStack.stackSize / (float)set.size());
                break;
            }
            case 1: {
                itemStack.stackSize = 1;
                break;
            }
            case 2: {
                itemStack.stackSize = itemStack.getItem().getItemStackLimit();
                break;
            }
        }
        itemStack.stackSize += n2;
    }
    
    public boolean canDragIntoSlot(final Slot slot) {
        return true;
    }
    
    public static int calcRedstoneFromInventory(final TileEntity tileEntity) {
        return (tileEntity instanceof IInventory) ? calcRedstoneFromInventory((IInventory)tileEntity) : 0;
    }
    
    public static int calcRedstoneFromInventory(final IInventory inventory) {
        if (inventory == null) {
            return 0;
        }
        float n = 0.0f;
        while (0 < inventory.getSizeInventory()) {
            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            if (stackInSlot != null) {
                n += stackInSlot.stackSize / (float)Math.min(inventory.getInventoryStackLimit(), stackInSlot.getMaxStackSize());
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return MathHelper.floor_float(n / inventory.getSizeInventory() * 14.0f) + ((0 > 0) ? 1 : 0);
    }
    
    static {
        __OBFID = "CL_00001730";
    }
}
