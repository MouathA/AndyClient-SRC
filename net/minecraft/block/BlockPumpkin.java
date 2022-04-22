package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;

public class BlockPumpkin extends BlockDirectional
{
    private BlockPattern field_176394_a;
    private BlockPattern field_176393_b;
    private BlockPattern field_176395_M;
    private BlockPattern field_176396_O;
    private static final String __OBFID;
    
    protected BlockPumpkin() {
        super(Material.gourd);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPumpkin.AGE, EnumFacing.NORTH));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.createGolem(world, blockPos);
    }
    
    public boolean func_176390_d(final World world, final BlockPos blockPos) {
        return this.func_176392_j().func_177681_a(world, blockPos) != null || this.func_176389_S().func_177681_a(world, blockPos) != null;
    }
    
    private void createGolem(final World world, final BlockPos blockPos) {
        final BlockPattern.PatternHelper func_177681_a;
        if ((func_177681_a = this.func_176391_l().func_177681_a(world, blockPos)) != null) {
            while (0 < this.func_176391_l().func_177685_b()) {
                world.setBlockState(func_177681_a.func_177670_a(0, 0, 0).getPos(), Blocks.air.getDefaultState(), 2);
                int n = 0;
                ++n;
            }
            final EntitySnowman entitySnowman = new EntitySnowman(world);
            final BlockPos pos = func_177681_a.func_177670_a(0, 2, 0).getPos();
            entitySnowman.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySnowman);
            while (0 < this.func_176391_l().func_177685_b()) {
                world.func_175722_b(func_177681_a.func_177670_a(0, 0, 0).getPos(), Blocks.air);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            final BlockPattern.PatternHelper func_177681_a2;
            if ((func_177681_a2 = this.func_176388_T().func_177681_a(world, blockPos)) != null) {
                while (0 < this.func_176388_T().func_177684_c()) {
                    while (0 < this.func_176388_T().func_177685_b()) {
                        world.setBlockState(func_177681_a2.func_177670_a(0, 0, 0).getPos(), Blocks.air.getDefaultState(), 2);
                        int n3 = 0;
                        ++n3;
                    }
                    int n = 0;
                    ++n;
                }
                final BlockPos pos2 = func_177681_a2.func_177670_a(1, 2, 0).getPos();
                final EntityIronGolem entityIronGolem = new EntityIronGolem(world);
                entityIronGolem.setPlayerCreated(true);
                entityIronGolem.setLocationAndAngles(pos2.getX() + 0.5, pos2.getY() + 0.05, pos2.getZ() + 0.5, 0.0f, 0.0f);
                world.spawnEntityInWorld(entityIronGolem);
                while (0 < this.func_176388_T().func_177684_c()) {
                    while (0 < this.func_176388_T().func_177685_b()) {
                        world.func_175722_b(func_177681_a2.func_177670_a(0, 0, 0).getPos(), Blocks.air);
                        int n4 = 0;
                        ++n4;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockPumpkin.AGE, entityLivingBase.func_174811_aO().getOpposite());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPumpkin.AGE, EnumFacing.getHorizontal(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockPumpkin.AGE)).getHorizontalIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPumpkin.AGE });
    }
    
    protected BlockPattern func_176392_j() {
        if (this.field_176394_a == null) {
            this.field_176394_a = FactoryBlockPattern.start().aisle(" ", "#", "#").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.field_176394_a;
    }
    
    protected BlockPattern func_176391_l() {
        if (this.field_176393_b == null) {
            this.field_176393_b = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.field_176393_b;
    }
    
    protected BlockPattern func_176389_S() {
        if (this.field_176395_M == null) {
            this.field_176395_M = FactoryBlockPattern.start().aisle("~ ~", "###", "~#~").where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176395_M;
    }
    
    protected BlockPattern func_176388_T() {
        if (this.field_176396_O == null) {
            this.field_176396_O = FactoryBlockPattern.start().aisle("~^~", "###", "~#~").where('^', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.pumpkin))).where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.field_176396_O;
    }
    
    static {
        __OBFID = "CL_00000291";
    }
}
