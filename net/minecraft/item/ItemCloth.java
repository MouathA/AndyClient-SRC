package net.minecraft.item;

import net.minecraft.block.*;

public class ItemCloth extends ItemBlock
{
    private static final String __OBFID;
    
    public ItemCloth(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + "." + EnumDyeColor.func_176764_b(itemStack.getMetadata()).func_176762_d();
    }
    
    static {
        __OBFID = "CL_00000075";
    }
}
