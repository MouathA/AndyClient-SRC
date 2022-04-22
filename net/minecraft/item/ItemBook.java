package net.minecraft.item;

public class ItemBook extends Item
{
    private static final String __OBFID;
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return itemStack.stackSize == 1;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    static {
        __OBFID = "CL_00001775";
    }
}
