package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;

public class SlotFurnaceFuel extends Slot
{
    private static final String __OBFID;
    
    public SlotFurnaceFuel(final IInventory inventory, final int n, final int n2, final int n3) {
        super(inventory, n, n2, n3);
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return TileEntityFurnace.isItemFuel(itemStack) || func_178173_c_(itemStack);
    }
    
    @Override
    public int func_178170_b(final ItemStack itemStack) {
        return func_178173_c_(itemStack) ? 1 : super.func_178170_b(itemStack);
    }
    
    public static boolean func_178173_c_(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && itemStack.getItem() == Items.bucket;
    }
    
    static {
        __OBFID = "CL_00002184";
    }
}
