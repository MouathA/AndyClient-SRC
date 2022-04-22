package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockSponge extends Block
{
    public static final PropertyBool WET_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000311";
        WET_PROP = PropertyBool.create("wet");
    }
    
    protected BlockSponge() {
        super(Material.sponge);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSponge.WET_PROP, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockSponge.WET_PROP)) ? 1 : 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setWet(world, blockPos, blockState);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.setWet(world, blockPos, blockState);
        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }
    
    protected void setWet(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!(boolean)blockState.getValue(BlockSponge.WET_PROP) && this.absorbWater(world, blockPos)) {
            world.setBlockState(blockPos, blockState.withProperty(BlockSponge.WET_PROP, true), 2);
            world.playAuxSFX(2001, blockPos, Block.getIdFromBlock(Blocks.water));
        }
    }
    
    private boolean absorbWater(final World world, final BlockPos blockPos) {
        final LinkedList linkedList = Lists.newLinkedList();
        final ArrayList arrayList = Lists.newArrayList();
        linkedList.add(new Tuple(blockPos, 0));
        while (!linkedList.isEmpty()) {
            final Tuple tuple = linkedList.poll();
            final BlockPos blockPos2 = (BlockPos)tuple.getFirst();
            final int intValue = (int)tuple.getSecond();
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final BlockPos offset = blockPos2.offset(values[0]);
                if (world.getBlockState(offset).getBlock().getMaterial() == Material.water) {
                    world.setBlockState(offset, Blocks.air.getDefaultState(), 2);
                    arrayList.add(offset);
                    int n = 0;
                    ++n;
                    if (intValue < 6) {
                        linkedList.add(new Tuple(offset, intValue + 1));
                    }
                }
                int n2 = 0;
                ++n2;
            }
            if (0 > 64) {
                break;
            }
        }
        final Iterator<BlockPos> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            world.notifyNeighborsOfStateChange(iterator.next(), Blocks.air);
        }
        return 0 > 0;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSponge.WET_PROP, (n & 0x1) == 0x1);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockSponge.WET_PROP)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSponge.WET_PROP });
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (blockState.getValue(BlockSponge.WET_PROP)) {
            final EnumFacing random2 = EnumFacing.random(random);
            if (random2 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(world, blockPos.offset(random2))) {
                final double n = blockPos.getX();
                final double n2 = blockPos.getY();
                final double n3 = blockPos.getZ();
                double n4;
                double n5;
                double n6;
                if (random2 == EnumFacing.DOWN) {
                    n4 = n2 - 0.05;
                    n5 = n + random.nextDouble();
                    n6 = n3 + random.nextDouble();
                }
                else {
                    n4 = n2 + random.nextDouble() * 0.8;
                    if (random2.getAxis() == EnumFacing.Axis.X) {
                        n6 = n3 + random.nextDouble();
                        if (random2 == EnumFacing.EAST) {
                            n5 = n + 1.0;
                        }
                        else {
                            n5 = n + 0.05;
                        }
                    }
                    else {
                        n5 = n + random.nextDouble();
                        if (random2 == EnumFacing.SOUTH) {
                            n6 = n3 + 1.0;
                        }
                        else {
                            n6 = n3 + 0.05;
                        }
                    }
                }
                world.spawnParticle(EnumParticleTypes.DRIP_WATER, n5, n4, n6, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
}
