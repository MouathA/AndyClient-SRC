package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockOre extends Block
{
    private static final String __OBFID;
    
    public BlockOre() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (this == Blocks.coal_ore) ? Items.coal : ((this == Blocks.diamond_ore) ? Items.diamond : ((this == Blocks.lapis_ore) ? Items.dye : ((this == Blocks.emerald_ore) ? Items.emerald : ((this == Blocks.quartz_ore) ? Items.quartz : Item.getItemFromBlock(this)))));
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return (this == Blocks.lapis_ore) ? (4 + random.nextInt(5)) : 1;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        if (n > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, n)) {
            final int n2 = random.nextInt(n + 2) - 1;
            if (0 < 0) {}
            return this.quantityDropped(random) * 1;
        }
        return this.quantityDropped(random);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (this.getItemDropped(blockState, world.rand, n2) != Item.getItemFromBlock(this)) {
            if (this == Blocks.coal_ore) {
                MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            }
            else if (this == Blocks.diamond_ore) {
                MathHelper.getRandomIntegerInRange(world.rand, 3, 7);
            }
            else if (this == Blocks.emerald_ore) {
                MathHelper.getRandomIntegerInRange(world.rand, 3, 7);
            }
            else if (this == Blocks.lapis_ore) {
                MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
            }
            else if (this == Blocks.quartz_ore) {
                MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
            }
            this.dropXpOnBlockBreak(world, blockPos, 0);
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return 0;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return (this == Blocks.lapis_ore) ? EnumDyeColor.BLUE.getDyeColorDamage() : 0;
    }
    
    static {
        __OBFID = "CL_00000282";
    }
}
