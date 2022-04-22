package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private boolean isBlocked;
    private int transferTicker;
    private BlockPos field_174900_c;
    private static final String __OBFID;
    
    public EntityMinecartHopper(final World world) {
        super(world);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    public EntityMinecartHopper(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.HOPPER;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.hopper.getDefaultState();
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }
    
    @Override
    public int getSizeInventory() {
        return 5;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            entityPlayer.displayGUIChest(this);
        }
        return true;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        final boolean blocked = !b;
        if (blocked != this.getBlocked()) {
            this.setBlocked(blocked);
        }
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    public void setBlocked(final boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public double getXPos() {
        return this.posX;
    }
    
    @Override
    public double getYPos() {
        return this.posY;
    }
    
    @Override
    public double getZPos() {
        return this.posZ;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getBlocked()) {
            if (new BlockPos(this).equals(this.field_174900_c)) {
                --this.transferTicker;
            }
            else {
                this.setTransferTicker(0);
            }
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }
    
    public boolean func_96112_aD() {
        if (TileEntityHopper.func_145891_a((IHopper)this)) {
            return true;
        }
        final List func_175647_a = this.worldObj.func_175647_a(EntityItem.class, this.getEntityBoundingBox().expand(0.25, 0.0, 0.25), IEntitySelector.selectAnything);
        if (func_175647_a.size() > 0) {
            TileEntityHopper.func_145898_a(this, func_175647_a.get(0));
        }
        return false;
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0f);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("TransferCooldown", this.transferTicker);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.transferTicker = nbtTagCompound.getInteger("TransferCooldown");
    }
    
    public void setTransferTicker(final int transferTicker) {
        this.transferTicker = transferTicker;
    }
    
    public boolean canTransfer() {
        return this.transferTicker > 0;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:hopper";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerHopper(inventoryPlayer, this, entityPlayer);
    }
    
    static {
        __OBFID = "CL_00001676";
    }
}
