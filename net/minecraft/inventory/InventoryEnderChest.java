package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest associatedChest;
    private static final String __OBFID;
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
    
    public void setChestTileEntity(final TileEntityEnderChest associatedChest) {
        this.associatedChest = associatedChest;
    }
    
    public void loadInventoryFromNBT(final NBTTagList list) {
        int n = 0;
        while (0 < this.getSizeInventory()) {
            this.setInventorySlotContents(0, null);
            ++n;
        }
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final int n2 = compoundTag.getByte("Slot") & 0xFF;
            if (n2 >= 0 && n2 < this.getSizeInventory()) {
                this.setInventorySlotContents(n2, ItemStack.loadItemStackFromNBT(compoundTag));
            }
            ++n;
        }
    }
    
    public NBTTagList saveInventoryToNBT() {
        final NBTTagList list = new NBTTagList();
        while (0 < this.getSizeInventory()) {
            final ItemStack stackInSlot = this.getStackInSlot(0);
            if (stackInSlot != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte)0);
                stackInSlot.writeToNBT(nbtTagCompound);
                list.appendTag(nbtTagCompound);
            }
            int n = 0;
            ++n;
        }
        return list;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return (this.associatedChest == null || this.associatedChest.func_145971_a(entityPlayer)) && super.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.func_145969_a();
        }
        super.openInventory(entityPlayer);
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.func_145970_b();
        }
        super.closeInventory(entityPlayer);
        this.associatedChest = null;
    }
    
    static {
        __OBFID = "CL_00001759";
    }
}
