package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerEnchantment extends Container
{
    public IInventory tableInventory;
    private World worldPointer;
    private BlockPos field_178150_j;
    private Random rand;
    public int field_178149_f;
    public int[] enchantLevels;
    public int[] field_178151_h;
    private static final String __OBFID;
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World worldPointer, final BlockPos field_178150_j) {
        this.tableInventory = new InventoryBasic("Enchant", true, 2) {
            private static final String __OBFID;
            final ContainerEnchantment this$0;
            
            @Override
            public int getInventoryStackLimit() {
                return 64;
            }
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
            
            static {
                __OBFID = "CL_00001746";
            }
        };
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.field_178151_h = new int[] { -1, -1, -1 };
        this.worldPointer = worldPointer;
        this.field_178150_j = field_178150_j;
        this.field_178149_f = inventoryPlayer.player.func_175138_ci();
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47) {
            private static final String __OBFID;
            final ContainerEnchantment this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return true;
            }
            
            @Override
            public int getSlotStackLimit() {
                return 1;
            }
            
            static {
                __OBFID = "CL_00001747";
            }
        });
        this.addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47) {
            private static final String __OBFID;
            final ContainerEnchantment this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return itemStack.getItem() == Items.dye && EnumDyeColor.func_176766_a(itemStack.getMetadata()) == EnumDyeColor.BLUE;
            }
            
            static {
                __OBFID = "CL_00002185";
            }
        });
        int n2 = 0;
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
                int n = 0;
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 0, 8, 142));
            ++n2;
        }
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        crafting.sendProgressBarUpdate(this, 3, this.field_178149_f & 0xFFFFFFF0);
        crafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
        crafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
        crafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        while (0 < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(0);
            crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
            crafting.sendProgressBarUpdate(this, 3, this.field_178149_f & 0xFFFFFFF0);
            crafting.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
            crafting.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
            crafting.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void updateProgressBar(final int n, final int field_178149_f) {
        if (n >= 0 && n <= 2) {
            this.enchantLevels[n] = field_178149_f;
        }
        else if (n == 3) {
            this.field_178149_f = field_178149_f;
        }
        else if (n >= 4 && n <= 6) {
            this.field_178151_h[n - 4] = field_178149_f;
        }
        else {
            super.updateProgressBar(n, field_178149_f);
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        if (inventory == this.tableInventory) {
            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.isItemEnchantable()) {
                if (!this.worldPointer.isRemote) {
                    int n3 = 0;
                    while (0 <= 1) {
                        while (-1 <= 1) {
                            if ((false || -1 != 0) && this.worldPointer.isAirBlock(this.field_178150_j.add(-1, 0, 0)) && this.worldPointer.isAirBlock(this.field_178150_j.add(-1, 1, 0))) {
                                int n = 0;
                                if (this.worldPointer.getBlockState(this.field_178150_j.add(-2, 0, 0)).getBlock() == Blocks.bookshelf) {
                                    ++n;
                                }
                                if (this.worldPointer.getBlockState(this.field_178150_j.add(-2, 1, 0)).getBlock() == Blocks.bookshelf) {
                                    ++n;
                                }
                                if (-1 != 0 && false) {
                                    if (this.worldPointer.getBlockState(this.field_178150_j.add(-2, 0, 0)).getBlock() == Blocks.bookshelf) {
                                        ++n;
                                    }
                                    if (this.worldPointer.getBlockState(this.field_178150_j.add(-2, 1, 0)).getBlock() == Blocks.bookshelf) {
                                        ++n;
                                    }
                                    if (this.worldPointer.getBlockState(this.field_178150_j.add(-1, 0, 0)).getBlock() == Blocks.bookshelf) {
                                        ++n;
                                    }
                                    if (this.worldPointer.getBlockState(this.field_178150_j.add(-1, 1, 0)).getBlock() == Blocks.bookshelf) {
                                        ++n;
                                    }
                                }
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        ++n3;
                    }
                    this.rand.setSeed(this.field_178149_f);
                    while (0 < 3) {
                        this.enchantLevels[0] = EnchantmentHelper.calcItemStackEnchantability(this.rand, 0, 0, stackInSlot);
                        this.field_178151_h[0] = -1;
                        if (this.enchantLevels[0] < 1) {
                            this.enchantLevels[0] = 0;
                        }
                        ++n3;
                    }
                    while (0 < 3) {
                        if (this.enchantLevels[0] > 0) {
                            final List func_178148_a = this.func_178148_a(stackInSlot, 0, this.enchantLevels[0]);
                            if (func_178148_a != null && !func_178148_a.isEmpty()) {
                                final EnchantmentData enchantmentData = func_178148_a.get(this.rand.nextInt(func_178148_a.size()));
                                this.field_178151_h[0] = (enchantmentData.enchantmentobj.effectId | enchantmentData.enchantmentLevel << 8);
                            }
                        }
                        ++n3;
                    }
                    this.detectAndSendChanges();
                }
            }
            else {
                while (0 < 3) {
                    this.enchantLevels[0] = 0;
                    this.field_178151_h[0] = -1;
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    @Override
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot(0);
        final ItemStack stackInSlot2 = this.tableInventory.getStackInSlot(1);
        final int n2 = n + 1;
        if ((stackInSlot2 == null || stackInSlot2.stackSize < n2) && !entityPlayer.capabilities.isCreativeMode) {
            return false;
        }
        if (this.enchantLevels[n] > 0 && stackInSlot != null && ((entityPlayer.experienceLevel >= n2 && entityPlayer.experienceLevel >= this.enchantLevels[n]) || entityPlayer.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isRemote) {
                final List func_178148_a = this.func_178148_a(stackInSlot, n, this.enchantLevels[n]);
                final boolean b = stackInSlot.getItem() == Items.book;
                if (func_178148_a != null) {
                    entityPlayer.func_71013_b(n2);
                    if (b) {
                        stackInSlot.setItem(Items.enchanted_book);
                    }
                    while (0 < func_178148_a.size()) {
                        final EnchantmentData enchantmentData = func_178148_a.get(0);
                        if (b) {
                            Items.enchanted_book.addEnchantment(stackInSlot, enchantmentData);
                        }
                        else {
                            stackInSlot.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                        }
                        int n3 = 0;
                        ++n3;
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = stackInSlot2;
                        itemStack.stackSize -= n2;
                        if (stackInSlot2.stackSize <= 0) {
                            this.tableInventory.setInventorySlotContents(1, null);
                        }
                    }
                    this.tableInventory.markDirty();
                    this.field_178149_f = entityPlayer.func_175138_ci();
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return true;
        }
        return false;
    }
    
    private List func_178148_a(final ItemStack itemStack, final int n, final int n2) {
        this.rand.setSeed(this.field_178149_f + n);
        final List buildEnchantmentList = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, n2);
        if (itemStack.getItem() == Items.book && buildEnchantmentList != null && buildEnchantmentList.size() > 1) {
            buildEnchantmentList.remove(this.rand.nextInt(buildEnchantmentList.size()));
        }
        return buildEnchantmentList;
    }
    
    public int func_178147_e() {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot(1);
        return (stackInSlot == null) ? 0 : stackInSlot.stackSize;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.worldPointer.isRemote) {
            while (0 < this.tableInventory.getSizeInventory()) {
                final ItemStack stackInSlotOnClosing = this.tableInventory.getStackInSlotOnClosing(0);
                if (stackInSlotOnClosing != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing, false);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.worldPointer.getBlockState(this.field_178150_j).getBlock() == Blocks.enchanting_table && entityPlayer.getDistanceSq(this.field_178150_j.getX() + 0.5, this.field_178150_j.getY() + 0.5, this.field_178150_j.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return null;
                }
            }
            else if (n == 1) {
                if (!this.mergeItemStack(stack, 2, 38, true)) {
                    return null;
                }
            }
            else if (stack.getItem() == Items.dye && EnumDyeColor.func_176766_a(stack.getMetadata()) == EnumDyeColor.BLUE) {
                if (!this.mergeItemStack(stack, 1, 2, true)) {
                    return null;
                }
            }
            else {
                if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(stack)) {
                    return null;
                }
                if (stack.hasTagCompound() && stack.stackSize == 1) {
                    this.inventorySlots.get(0).putStack(stack.copy());
                    stack.stackSize = 0;
                }
                else if (stack.stackSize >= 1) {
                    this.inventorySlots.get(0).putStack(new ItemStack(stack.getItem(), 1, stack.getMetadata()));
                    final ItemStack itemStack = stack;
                    --itemStack.stackSize;
                }
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    static {
        __OBFID = "CL_00001745";
    }
}
