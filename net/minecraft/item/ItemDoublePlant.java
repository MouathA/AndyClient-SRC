package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.world.*;

public class ItemDoublePlant extends ItemMultiTexture
{
    private static final String __OBFID;
    
    public ItemDoublePlant(final Block block, final Block block2, final Function function) {
        super(block, block2, function);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final BlockDoublePlant.EnumPlantType func_176938_a = BlockDoublePlant.EnumPlantType.func_176938_a(itemStack.getMetadata());
        return (func_176938_a != BlockDoublePlant.EnumPlantType.GRASS && func_176938_a != BlockDoublePlant.EnumPlantType.FERN) ? super.getColorFromItemStack(itemStack, n) : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    static {
        __OBFID = "CL_00000021";
    }
}
