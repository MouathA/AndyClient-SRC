package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityFlowerPot extends TileEntity
{
    private Item flowerPotItem;
    private int flowerPotData;
    private static final String __OBFID;
    
    public TileEntityFlowerPot() {
    }
    
    public TileEntityFlowerPot(final Item flowerPotItem, final int flowerPotData) {
        this.flowerPotItem = flowerPotItem;
        this.flowerPotData = flowerPotData;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final ResourceLocation resourceLocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.flowerPotItem);
        nbtTagCompound.setString("Item", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setInteger("Data", this.flowerPotData);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("Item", 8)) {
            this.flowerPotItem = Item.getByNameOrId(nbtTagCompound.getString("Item"));
        }
        else {
            this.flowerPotItem = Item.getItemById(nbtTagCompound.getInteger("Item"));
        }
        this.flowerPotData = nbtTagCompound.getInteger("Data");
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        nbtTagCompound.removeTag("Item");
        nbtTagCompound.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
        return new S35PacketUpdateTileEntity(this.pos, 5, nbtTagCompound);
    }
    
    public void func_145964_a(final Item flowerPotItem, final int flowerPotData) {
        this.flowerPotItem = flowerPotItem;
        this.flowerPotData = flowerPotData;
    }
    
    public Item getFlowerPotItem() {
        return this.flowerPotItem;
    }
    
    public int getFlowerPotData() {
        return this.flowerPotData;
    }
    
    static {
        __OBFID = "CL_00000356";
    }
}
