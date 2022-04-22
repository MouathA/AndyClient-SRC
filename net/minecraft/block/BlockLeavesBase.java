package net.minecraft.block;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import optifine.*;

public class BlockLeavesBase extends Block
{
    protected boolean field_150121_P;
    private static final String __OBFID;
    private static Map mapOriginalOpacity;
    
    static {
        __OBFID = "CL_00000326";
        BlockLeavesBase.mapOriginalOpacity = new IdentityHashMap();
    }
    
    protected BlockLeavesBase(final Material material, final boolean field_150121_P) {
        super(material);
        this.field_150121_P = field_150121_P;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (!Config.isCullFacesLeaves() || blockAccess.getBlockState(blockPos).getBlock() != this) && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    public static void setLightOpacity(final Block block, final int lightOpacity) {
        if (!BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            BlockLeavesBase.mapOriginalOpacity.put(block, block.getLightOpacity());
        }
        block.setLightOpacity(lightOpacity);
    }
    
    public static void restoreLightOpacity(final Block block) {
        if (BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            setLightOpacity(block, BlockLeavesBase.mapOriginalOpacity.get(block));
        }
    }
}
