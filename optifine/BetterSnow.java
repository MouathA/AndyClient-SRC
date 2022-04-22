package optifine;

import net.minecraft.client.resources.model.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class BetterSnow
{
    private static IBakedModel modelSnowLayer;
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178125_b(Blocks.snow_layer.getDefaultState());
    }
    
    public static IBakedModel getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static IBlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }
    
    public static boolean shouldRender(final IBlockAccess blockAccess, final Block block, final IBlockState blockState, final BlockPos blockPos) {
        return blockState == 0 && hasSnowNeighbours(blockAccess, blockPos);
    }
    
    private static boolean hasSnowNeighbours(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block snow_layer = Blocks.snow_layer;
        return (blockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.offsetWest()).getBlock() == snow_layer || blockAccess.getBlockState(blockPos.offsetEast()).getBlock() == snow_layer) && blockAccess.getBlockState(blockPos.offsetDown()).getBlock().isOpaqueCube();
    }
}
