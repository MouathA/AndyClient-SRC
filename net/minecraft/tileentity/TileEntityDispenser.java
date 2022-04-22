package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityDispenser extends TileEntityLockable implements IInventory
{
    private static final Random field_174913_f;
    private ItemStack[] field_146022_i;
    protected String field_146020_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000352";
        field_174913_f = new Random();
    }
    
    public TileEntityDispenser() {
        this.field_146022_i = new ItemStack[9];
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.field_146022_i[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.field_146022_i[n] == null) {
            return null;
        }
        if (this.field_146022_i[n].stackSize <= n2) {
            final ItemStack itemStack = this.field_146022_i[n];
            this.field_146022_i[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.field_146022_i[n].splitStack(n2);
        if (this.field_146022_i[n].stackSize == 0) {
            this.field_146022_i[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.field_146022_i[n] != null) {
            final ItemStack itemStack = this.field_146022_i[n];
            this.field_146022_i[n] = null;
            return itemStack;
        }
        return null;
    }
    
    public int func_146017_i() {
        while (0 < this.field_146022_i.length) {
            if (this.field_146022_i[0] != null) {
                final Random field_174913_f = TileEntityDispenser.field_174913_f;
                final int n = 1;
                int n2 = 0;
                ++n2;
                if (field_174913_f.nextInt(n) == 0) {}
            }
            int n3 = 0;
            ++n3;
        }
        return -1;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.field_146022_i[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    public int func_146019_a(final ItemStack itemStack) {
        while (0 < this.field_146022_i.length) {
            if (this.field_146022_i[0] == null || this.field_146022_i[0].getItem() == null) {
                this.setInventorySlotContents(0, itemStack);
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.field_146020_a : "container.dispenser";
    }
    
    public void func_146018_a(final String field_146020_a) {
        this.field_146020_a = field_146020_a;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.field_146022_i = new ItemStack[this.getSizeInventory()];
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.field_146022_i.length) {
                this.field_146022_i[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n2 = 0;
            ++n2;
        }
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.field_146020_a = nbtTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.field_146022_i.length) {
            if (this.field_146022_i[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.field_146022_i[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
        if (this != null) {
            nbtTagCompound.setString("CustomName", this.field_146020_a);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
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
    
    @Override
    public String getGuiID() {
        return "minecraft:dispenser";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerDispenser(inventoryPlayer, this);
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
        while (0 < this.field_146022_i.length) {
            this.field_146022_i[0] = null;
            int n = 0;
            ++n;
        }
    }
}
