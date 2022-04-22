package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockSeaLantern extends Block
{
    private static final String __OBFID;
    
    public BlockSeaLantern(final Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 2 + random.nextInt(2);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(n + 1), 1, 5);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.prismarine_crystals;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.quartzColor;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    static {
        __OBFID = "CL_00002066";
    }
}
