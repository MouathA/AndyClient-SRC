package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockDoublePlant extends BlockBush implements IGrowable
{
    public static final PropertyEnum VARIANT_PROP;
    public static final PropertyEnum HALF_PROP;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000231";
        VARIANT_PROP = PropertyEnum.create("variant", EnumPlantType.class);
        HALF_PROP = PropertyEnum.create("half", EnumBlockHalf.class);
    }
    
    public BlockDoublePlant() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDoublePlant.VARIANT_PROP, EnumPlantType.SUNFLOWER).withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.LOWER));
        this.setHardness(0.0f);
        this.setStepSound(BlockDoublePlant.soundTypeGrass);
        this.setUnlocalizedName("doublePlant");
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public EnumPlantType func_176490_e(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            return (EnumPlantType)this.getActualState(blockState, blockAccess, blockPos).getValue(BlockDoublePlant.VARIANT_PROP);
        }
        return EnumPlantType.FERN;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && world.isAirBlock(blockPos.offsetUp());
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return true;
        }
        final EnumPlantType enumPlantType = (EnumPlantType)this.getActualState(blockState, world, blockPos).getValue(BlockDoublePlant.VARIANT_PROP);
        return enumPlantType == EnumPlantType.FERN || enumPlantType == EnumPlantType.GRASS;
    }
    
    @Override
    protected void func_176475_e(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            final boolean b = blockState.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER;
            final BlockPos blockPos2 = b ? blockPos : blockPos.offsetUp();
            final BlockPos blockPos3 = b ? blockPos.offsetDown() : blockPos;
            final Block block = b ? this : world.getBlockState(blockPos2).getBlock();
            final Block block2 = b ? world.getBlockState(blockPos3).getBlock() : this;
            if (block == this) {
                world.setBlockState(blockPos2, Blocks.air.getDefaultState(), 3);
            }
            if (block2 == this) {
                world.setBlockState(blockPos3, Blocks.air.getDefaultState(), 3);
                if (!b) {
                    this.dropBlockAsItem(world, blockPos3, blockState, 0);
                }
            }
        }
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER) {
            return world.getBlockState(blockPos.offsetDown()).getBlock() == this;
        }
        final IBlockState blockState2 = world.getBlockState(blockPos.offsetUp());
        return blockState2.getBlock() == this && super.canBlockStay(world, blockPos, blockState2);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        if (blockState.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER) {
            return null;
        }
        final EnumPlantType enumPlantType = (EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT_PROP);
        return (enumPlantType == EnumPlantType.FERN) ? null : ((enumPlantType == EnumPlantType.GRASS) ? ((random.nextInt(8) == 0) ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return (blockState.getValue(BlockDoublePlant.HALF_PROP) != EnumBlockHalf.UPPER && blockState.getValue(BlockDoublePlant.VARIANT_PROP) != EnumPlantType.GRASS) ? ((EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT_PROP)).func_176936_a() : 0;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final EnumPlantType func_176490_e = this.func_176490_e(blockAccess, blockPos);
        return (func_176490_e != EnumPlantType.GRASS && func_176490_e != EnumPlantType.FERN) ? 16777215 : BiomeColorHelper.func_180286_a(blockAccess, blockPos);
    }
    
    public void func_176491_a(final World world, final BlockPos blockPos, final EnumPlantType enumPlantType, final int n) {
        world.setBlockState(blockPos, this.getDefaultState().withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT_PROP, enumPlantType), n);
        world.setBlockState(blockPos.offsetUp(), this.getDefaultState().withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.UPPER), n);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos.offsetUp(), this.getDefaultState().withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.UPPER), 2);
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (world.isRemote || entityPlayer.getCurrentEquippedItem() == null || entityPlayer.getCurrentEquippedItem().getItem() != Items.shears || blockState.getValue(BlockDoublePlant.HALF_PROP) != EnumBlockHalf.LOWER || !this.func_176489_b(world, blockPos, blockState, entityPlayer)) {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (blockState.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER) {
            if (world.getBlockState(blockPos.offsetDown()).getBlock() == this) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final IBlockState blockState2 = world.getBlockState(blockPos.offsetDown());
                    final EnumPlantType enumPlantType = (EnumPlantType)blockState2.getValue(BlockDoublePlant.VARIANT_PROP);
                    if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
                        world.destroyBlock(blockPos.offsetDown(), true);
                    }
                    else if (!world.isRemote) {
                        if (entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
                            this.func_176489_b(world, blockPos, blockState2, entityPlayer);
                            world.setBlockToAir(blockPos.offsetDown());
                        }
                        else {
                            world.destroyBlock(blockPos.offsetDown(), true);
                        }
                    }
                    else {
                        world.setBlockToAir(blockPos.offsetDown());
                    }
                }
                else {
                    world.setBlockToAir(blockPos.offsetDown());
                }
            }
        }
        else if (entityPlayer.capabilities.isCreativeMode && world.getBlockState(blockPos.offsetUp()).getBlock() == this) {
            world.setBlockState(blockPos.offsetUp(), Blocks.air.getDefaultState(), 2);
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    private boolean func_176489_b(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        final EnumPlantType enumPlantType = (EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT_PROP);
        if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
            return false;
        }
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 2, ((enumPlantType == EnumPlantType.GRASS) ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).func_177044_a()));
        return true;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumPlantType[] values = EnumPlantType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].func_176936_a()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return this.func_176490_e(world, blockPos).func_176936_a();
    }
    
    @Override
    public boolean isStillGrowing(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        final EnumPlantType func_176490_e = this.func_176490_e(world, blockPos);
        return func_176490_e != EnumPlantType.GRASS && func_176490_e != EnumPlantType.FERN;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        Block.spawnAsEntity(world, blockPos, new ItemStack(this, 1, this.func_176490_e(world, blockPos).func_176936_a()));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(BlockDoublePlant.HALF_PROP, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT_PROP, EnumPlantType.func_176938_a(n & 0x7));
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.offsetDown());
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty(BlockDoublePlant.VARIANT_PROP, blockState.getValue(BlockDoublePlant.VARIANT_PROP));
            }
        }
        return withProperty;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (blockState.getValue(BlockDoublePlant.HALF_PROP) == EnumBlockHalf.UPPER) ? 8 : ((EnumPlantType)blockState.getValue(BlockDoublePlant.VARIANT_PROP)).func_176936_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDoublePlant.HALF_PROP, BlockDoublePlant.VARIANT_PROP });
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    enum EnumBlockHalf implements IStringSerializable
    {
        UPPER("UPPER", 0, "UPPER", 0), 
        LOWER("LOWER", 1, "LOWER", 1);
        
        private static final EnumBlockHalf[] $VALUES;
        private static final String __OBFID;
        private static final EnumBlockHalf[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002122";
            ENUM$VALUES = new EnumBlockHalf[] { EnumBlockHalf.UPPER, EnumBlockHalf.LOWER };
            $VALUES = new EnumBlockHalf[] { EnumBlockHalf.UPPER, EnumBlockHalf.LOWER };
        }
        
        private EnumBlockHalf(final String s, final int n, final String s2, final int n2) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            return (this == EnumBlockHalf.UPPER) ? "upper" : "lower";
        }
    }
    
    public enum EnumPlantType implements IStringSerializable
    {
        SUNFLOWER("SUNFLOWER", 0, "SUNFLOWER", 0, 0, "sunflower"), 
        SYRINGA("SYRINGA", 1, "SYRINGA", 1, 1, "syringa"), 
        GRASS("GRASS", 2, "GRASS", 2, 2, "double_grass", "grass"), 
        FERN("FERN", 3, "FERN", 3, 3, "double_fern", "fern"), 
        ROSE("ROSE", 4, "ROSE", 4, 4, "double_rose", "rose"), 
        PAEONIA("PAEONIA", 5, "PAEONIA", 5, 5, "paeonia");
        
        private static final EnumPlantType[] field_176941_g;
        private final int field_176949_h;
        private final String field_176950_i;
        private final String field_176947_j;
        private static final EnumPlantType[] $VALUES;
        private static final String __OBFID;
        private static final EnumPlantType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002121";
            ENUM$VALUES = new EnumPlantType[] { EnumPlantType.SUNFLOWER, EnumPlantType.SYRINGA, EnumPlantType.GRASS, EnumPlantType.FERN, EnumPlantType.ROSE, EnumPlantType.PAEONIA };
            field_176941_g = new EnumPlantType[values().length];
            $VALUES = new EnumPlantType[] { EnumPlantType.SUNFLOWER, EnumPlantType.SYRINGA, EnumPlantType.GRASS, EnumPlantType.FERN, EnumPlantType.ROSE, EnumPlantType.PAEONIA };
            final EnumPlantType[] values = values();
            while (0 < values.length) {
                final EnumPlantType enumPlantType = values[0];
                EnumPlantType.field_176941_g[enumPlantType.func_176936_a()] = enumPlantType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumPlantType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private EnumPlantType(final String s, final int n, final String s2, final int n2, final int field_176949_h, final String field_176950_i, final String field_176947_j) {
            this.field_176949_h = field_176949_h;
            this.field_176950_i = field_176950_i;
            this.field_176947_j = field_176947_j;
        }
        
        public int func_176936_a() {
            return this.field_176949_h;
        }
        
        @Override
        public String toString() {
            return this.field_176950_i;
        }
        
        public static EnumPlantType func_176938_a(final int n) {
            if (0 < 0 || 0 >= EnumPlantType.field_176941_g.length) {}
            return EnumPlantType.field_176941_g[0];
        }
        
        @Override
        public String getName() {
            return this.field_176950_i;
        }
        
        public String func_176939_c() {
            return this.field_176947_j;
        }
    }
}
