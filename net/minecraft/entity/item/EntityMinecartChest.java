package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class EntityMinecartChest extends EntityMinecartContainer
{
    private static final String __OBFID;
    
    public EntityMinecartChest(final World world) {
        super(world);
    }
    
    public EntityMinecartChest(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        this.dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), 1, 0.0f);
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.CHEST;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.chest.getDefaultState().withProperty(BlockChest.FACING_PROP, EnumFacing.NORTH);
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:chest";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    static {
        __OBFID = "CL_00001671";
    }
}
