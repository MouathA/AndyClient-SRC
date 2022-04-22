package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;

public class SlotCrafting extends Slot
{
    private final InventoryCrafting craftMatrix;
    private final EntityPlayer thePlayer;
    private int amountCrafted;
    private static final String __OBFID;
    
    public SlotCrafting(final EntityPlayer thePlayer, final InventoryCrafting craftMatrix, final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
        this.thePlayer = thePlayer;
        this.craftMatrix = craftMatrix;
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.amountCrafted += n;
        this.onCrafting(itemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        if (this.amountCrafted > 0) {
            itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        }
        this.amountCrafted = 0;
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
        }
        if (itemStack.getItem() instanceof ItemPickaxe) {
            this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
            this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
        }
        if (itemStack.getItem() instanceof ItemHoe) {
            this.thePlayer.triggerAchievement(AchievementList.buildHoe);
        }
        if (itemStack.getItem() == Items.bread) {
            this.thePlayer.triggerAchievement(AchievementList.makeBread);
        }
        if (itemStack.getItem() == Items.cake) {
            this.thePlayer.triggerAchievement(AchievementList.bakeCake);
        }
        if (itemStack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)itemStack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
            this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
        }
        if (itemStack.getItem() instanceof ItemSword) {
            this.thePlayer.triggerAchievement(AchievementList.buildSword);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.enchantments);
        }
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
            this.thePlayer.triggerAchievement(AchievementList.bookcase);
        }
        if (itemStack.getItem() == Items.golden_apple && itemStack.getMetadata() == 1) {
            this.thePlayer.triggerAchievement(AchievementList.overpowered);
        }
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onCrafting(itemStack);
        final ItemStack[] func_180303_b = CraftingManager.getInstance().func_180303_b(this.craftMatrix, entityPlayer.worldObj);
        while (0 < func_180303_b.length) {
            final ItemStack stackInSlot = this.craftMatrix.getStackInSlot(0);
            final ItemStack itemStack2 = func_180303_b[0];
            if (stackInSlot != null) {
                this.craftMatrix.decrStackSize(0, 1);
            }
            if (itemStack2 != null) {
                if (this.craftMatrix.getStackInSlot(0) == null) {
                    this.craftMatrix.setInventorySlotContents(0, itemStack2);
                }
                else if (!this.thePlayer.inventory.addItemStackToInventory(itemStack2)) {
                    this.thePlayer.dropPlayerItemWithRandomChoice(itemStack2, false);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00001761";
    }
}
