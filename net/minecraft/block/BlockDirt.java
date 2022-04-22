package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockDirt extends Block
{
    public static final PropertyEnum VARIANT;
    public static final PropertyBool SNOWY;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000228";
        VARIANT = PropertyEnum.create("variant", DirtType.class);
        SNOWY = PropertyBool.create("snowy");
    }
    
    protected BlockDirt() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirt.VARIANT, DirtType.DIRT).withProperty(BlockDirt.SNOWY, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
            final Block block = blockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            withProperty = withProperty.withProperty(BlockDirt.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
        }
        return withProperty;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return (blockState.getBlock() != this) ? 0 : ((DirtType)blockState.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.byMetadata(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((DirtType)blockState.getValue(BlockDirt.VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDirt.VARIANT, BlockDirt.SNOWY });
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        DirtType dirt = (DirtType)blockState.getValue(BlockDirt.VARIANT);
        if (dirt == DirtType.PODZOL) {
            dirt = DirtType.DIRT;
        }
        return dirt.getMetadata();
    }
    
    public enum DirtType implements IStringSerializable
    {
        DIRT("DIRT", 0, "DIRT", 0, 0, "dirt", "default"), 
        COARSE_DIRT("COARSE_DIRT", 1, "COARSE_DIRT", 1, 1, "coarse_dirt", "coarse"), 
        PODZOL("PODZOL", 2, "PODZOL", 2, 2, "podzol");
        
        private static final DirtType[] METADATA_LOOKUP;
        private final int metadata;
        private final String name;
        private final String unlocalizedName;
        private static final DirtType[] $VALUES;
        private static final String __OBFID;
        private static final DirtType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002125";
            ENUM$VALUES = new DirtType[] { DirtType.DIRT, DirtType.COARSE_DIRT, DirtType.PODZOL };
            METADATA_LOOKUP = new DirtType[values().length];
            $VALUES = new DirtType[] { DirtType.DIRT, DirtType.COARSE_DIRT, DirtType.PODZOL };
            final DirtType[] values = values();
            while (0 < values.length) {
                final DirtType dirtType = values[0];
                DirtType.METADATA_LOOKUP[dirtType.getMetadata()] = dirtType;
                int n = 0;
                ++n;
            }
        }
        
        private DirtType(final String s, final int n, final String s2, final int n2, final int n3, final String s3) {
            this(s, n, s2, n2, n3, s3, s3);
        }
        
        private DirtType(final String s, final int n, final String s2, final int n2, final int metadata, final String name, final String unlocalizedName) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static DirtType byMetadata(final int n) {
            if (0 >= DirtType.METADATA_LOOKUP.length) {}
            return DirtType.METADATA_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
