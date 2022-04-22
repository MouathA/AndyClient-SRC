package net.minecraft.dispenser;

import net.minecraft.item.*;

public interface IBehaviorDispenseItem
{
    public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem() {
        private static final String __OBFID;
        
        @Override
        public ItemStack dispense(final IBlockSource blockSource, final ItemStack itemStack) {
            return itemStack;
        }
        
        static {
            __OBFID = "CL_00001200";
        }
    };
    
    ItemStack dispense(final IBlockSource p0, final ItemStack p1);
}
