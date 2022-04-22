package net.minecraft.item;

import net.minecraft.block.*;

public class ItemLeaves extends ItemBlock
{
    private final BlockLeaves field_150940_b;
    private static final String __OBFID;
    
    public ItemLeaves(final BlockLeaves field_150940_b) {
        super(field_150940_b);
        this.field_150940_b = field_150940_b;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int n) {
        return n | 0x4;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.field_150940_b.getRenderColor(this.field_150940_b.getStateFromMeta(itemStack.getMetadata()));
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + "." + this.field_150940_b.func_176233_b(itemStack.getMetadata()).func_176840_c();
    }
    
    static {
        __OBFID = "CL_00000046";
    }
}
