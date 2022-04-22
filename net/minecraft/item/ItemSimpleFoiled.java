package net.minecraft.item;

public class ItemSimpleFoiled extends Item
{
    private static final String __OBFID;
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return true;
    }
    
    static {
        __OBFID = "CL_00000065";
    }
}
