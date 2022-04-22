package net.minecraft.item;

import net.minecraft.block.*;
import com.google.common.base.*;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block theBlock;
    protected final Function nameFunction;
    private static final String __OBFID;
    
    public ItemMultiTexture(final Block block, final Block theBlock, final Function nameFunction) {
        super(block);
        this.theBlock = theBlock;
        this.nameFunction = nameFunction;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public ItemMultiTexture(final Block block, final Block block2, final String[] array) {
        this(block, block2, new Function() {
            private static final String __OBFID;
            private final String[] val$p_i45346_3_;
            
            public String apply(final ItemStack itemStack) {
                itemStack.getMetadata();
                if (0 < 0 || 0 >= this.val$p_i45346_3_.length) {}
                return this.val$p_i45346_3_[0];
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
            
            static {
                __OBFID = "CL_00002161";
            }
        });
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + "." + (String)this.nameFunction.apply(itemStack);
    }
    
    static {
        __OBFID = "CL_00000051";
    }
}
