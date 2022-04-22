package net.minecraft.item;

import net.minecraft.block.*;

public class ItemColored extends ItemBlock
{
    private final Block field_150944_b;
    private String[] field_150945_c;
    private static final String __OBFID;
    
    public ItemColored(final Block field_150944_b, final boolean b) {
        super(field_150944_b);
        this.field_150944_b = field_150944_b;
        if (b) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return this.field_150944_b.getRenderColor(this.field_150944_b.getStateFromMeta(itemStack.getMetadata()));
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    public ItemColored func_150943_a(final String[] field_150945_c) {
        this.field_150945_c = field_150945_c;
        return this;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        if (this.field_150945_c == null) {
            return super.getUnlocalizedName(itemStack);
        }
        final int metadata = itemStack.getMetadata();
        return (metadata >= 0 && metadata < this.field_150945_c.length) ? (String.valueOf(super.getUnlocalizedName(itemStack)) + "." + this.field_150945_c[metadata]) : super.getUnlocalizedName(itemStack);
    }
    
    static {
        __OBFID = "CL_00000003";
    }
}
