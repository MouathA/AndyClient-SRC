package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider
{
    public static final PropertyBool field_176464_a;
    public static final PropertyEnum field_176463_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000220";
        field_176464_a = PropertyBool.create("powered");
        field_176463_b = PropertyEnum.create("mode", Mode.class);
    }
    
    public BlockRedstoneComparator(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneComparator.AGE, EnumFacing.NORTH).withProperty(BlockRedstoneComparator.field_176464_a, false).withProperty(BlockRedstoneComparator.field_176463_b, Mode.COMPARE));
        this.isBlockContainer = true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.comparator;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.comparator;
    }
    
    @Override
    protected int func_176403_d(final IBlockState blockState) {
        return 2;
    }
    
    @Override
    protected IBlockState func_180674_e(final IBlockState blockState) {
        return Blocks.powered_comparator.getDefaultState().withProperty(BlockRedstoneComparator.AGE, blockState.getValue(BlockRedstoneComparator.AGE)).withProperty(BlockRedstoneComparator.field_176464_a, blockState.getValue(BlockRedstoneComparator.field_176464_a)).withProperty(BlockRedstoneComparator.field_176463_b, blockState.getValue(BlockRedstoneComparator.field_176463_b));
    }
    
    @Override
    protected IBlockState func_180675_k(final IBlockState blockState) {
        return Blocks.unpowered_comparator.getDefaultState().withProperty(BlockRedstoneComparator.AGE, blockState.getValue(BlockRedstoneComparator.AGE)).withProperty(BlockRedstoneComparator.field_176464_a, blockState.getValue(BlockRedstoneComparator.field_176464_a)).withProperty(BlockRedstoneComparator.field_176463_b, blockState.getValue(BlockRedstoneComparator.field_176463_b));
    }
    
    @Override
    protected boolean func_176406_l(final IBlockState blockState) {
        return this.isRepeaterPowered || (boolean)blockState.getValue(BlockRedstoneComparator.field_176464_a);
    }
    
    @Override
    protected int func_176408_a(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityComparator) ? ((TileEntityComparator)tileEntity).getOutputSignal() : 0;
    }
    
    private int func_176460_j(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return (blockState.getValue(BlockRedstoneComparator.field_176463_b) == Mode.SUBTRACT) ? Math.max(this.func_176397_f(world, blockPos, blockState) - this.func_176407_c(world, blockPos, blockState), 0) : this.func_176397_f(world, blockPos, blockState);
    }
    
    protected boolean func_176404_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int func_176397_f = this.func_176397_f(world, blockPos, blockState);
        if (func_176397_f >= 15) {
            return true;
        }
        if (func_176397_f == 0) {
            return false;
        }
        final int func_176407_c = this.func_176407_c(world, blockPos, blockState);
        return func_176407_c == 0 || func_176397_f >= func_176407_c;
    }
    
    @Override
    protected int func_176397_f(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n = super.func_176397_f(world, blockPos, blockState);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneComparator.AGE);
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = world.getBlockState(offset).getBlock();
        if (block.hasComparatorInputOverride()) {
            n = block.getComparatorInputOverride(world, offset);
        }
        else if (n < 15 && block.isNormalCube()) {
            final BlockPos offset2 = offset.offset(enumFacing);
            final Block block2 = world.getBlockState(offset2).getBlock();
            if (block2.hasComparatorInputOverride()) {
                n = block2.getComparatorInputOverride(world, offset2);
            }
            else if (block2.getMaterial() == Material.air) {
                final EntityItemFrame func_176461_a = this.func_176461_a(world, enumFacing, offset2);
                if (func_176461_a != null) {
                    n = func_176461_a.func_174866_q();
                }
            }
        }
        return n;
    }
    
    private EntityItemFrame func_176461_a(final World world, final EnumFacing enumFacing, final BlockPos blockPos) {
        final List func_175647_a = world.func_175647_a(EntityItemFrame.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1), new Predicate(enumFacing) {
            private static final String __OBFID;
            final BlockRedstoneComparator this$0;
            private final EnumFacing val$p_176461_2_;
            
            public boolean func_180416_a(final Entity entity) {
                return entity != null && entity.func_174811_aO() == this.val$p_176461_2_;
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180416_a((Entity)o);
            }
            
            static {
                __OBFID = "CL_00002129";
            }
        });
        return (func_175647_a.size() == 1) ? func_175647_a.get(0) : null;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return false;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockRedstoneComparator.field_176463_b);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, (cycleProperty.getValue(BlockRedstoneComparator.field_176463_b) == Mode.SUBTRACT) ? 0.55f : 0.5f);
        world.setBlockState(blockPos, cycleProperty, 2);
        this.func_176462_k(world, blockPos, cycleProperty);
        return true;
    }
    
    @Override
    protected void func_176398_g(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isBlockTickPending(blockPos, this)) {
            final int func_176460_j = this.func_176460_j(world, blockPos, blockState);
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (func_176460_j != ((tileEntity instanceof TileEntityComparator) ? ((TileEntityComparator)tileEntity).getOutputSignal() : 0) || this.func_176406_l(blockState) != this.func_176404_e(world, blockPos, blockState)) {
                if (this.func_176402_i(world, blockPos, blockState)) {
                    world.func_175654_a(blockPos, this, 2, -1);
                }
                else {
                    world.func_175654_a(blockPos, this, 2, 0);
                }
            }
        }
    }
    
    private void func_176462_k(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int func_176460_j = this.func_176460_j(world, blockPos, blockState);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityComparator) {
            final TileEntityComparator tileEntityComparator = (TileEntityComparator)tileEntity;
            tileEntityComparator.getOutputSignal();
            tileEntityComparator.setOutputSignal(func_176460_j);
        }
        if (func_176460_j || blockState.getValue(BlockRedstoneComparator.field_176463_b) == Mode.COMPARE) {
            final boolean func_176404_e = this.func_176404_e(world, blockPos, blockState);
            final boolean func_176406_l = this.func_176406_l(blockState);
            if (func_176406_l && !func_176404_e) {
                world.setBlockState(blockPos, blockState.withProperty(BlockRedstoneComparator.field_176464_a, false), 2);
            }
            else if (!func_176406_l && func_176404_e) {
                world.setBlockState(blockPos, blockState.withProperty(BlockRedstoneComparator.field_176464_a, true), 2);
            }
            this.func_176400_h(world, blockPos, blockState);
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            world.setBlockState(blockPos, this.func_180675_k(blockState).withProperty(BlockRedstoneComparator.field_176464_a, true), 4);
        }
        this.func_176462_k(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        world.setTileEntity(blockPos, this.createNewTileEntity(world, 0));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
        this.func_176400_h(world, blockPos, blockState);
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(n, n2);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityComparator();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneComparator.AGE, EnumFacing.getHorizontal(n)).withProperty(BlockRedstoneComparator.field_176464_a, (n & 0x8) > 0).withProperty(BlockRedstoneComparator.field_176463_b, ((n & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0x0 | ((EnumFacing)blockState.getValue(BlockRedstoneComparator.AGE)).getHorizontalIndex();
        if (blockState.getValue(BlockRedstoneComparator.field_176464_a)) {
            n |= 0x8;
        }
        if (blockState.getValue(BlockRedstoneComparator.field_176463_b) == Mode.SUBTRACT) {
            n |= 0x4;
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneComparator.AGE, BlockRedstoneComparator.field_176463_b, BlockRedstoneComparator.field_176464_a });
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockRedstoneComparator.AGE, entityLivingBase.func_174811_aO().getOpposite()).withProperty(BlockRedstoneComparator.field_176464_a, false).withProperty(BlockRedstoneComparator.field_176463_b, Mode.COMPARE);
    }
    
    public enum Mode implements IStringSerializable
    {
        COMPARE("COMPARE", 0, "COMPARE", 0, "compare"), 
        SUBTRACT("SUBTRACT", 1, "SUBTRACT", 1, "subtract");
        
        private final String field_177041_c;
        private static final Mode[] $VALUES;
        private static final String __OBFID;
        private static final Mode[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002128";
            ENUM$VALUES = new Mode[] { Mode.COMPARE, Mode.SUBTRACT };
            $VALUES = new Mode[] { Mode.COMPARE, Mode.SUBTRACT };
        }
        
        private Mode(final String s, final int n, final String s2, final int n2, final String field_177041_c) {
            this.field_177041_c = field_177041_c;
        }
        
        @Override
        public String toString() {
            return this.field_177041_c;
        }
        
        @Override
        public String getName() {
            return this.field_177041_c;
        }
    }
}
