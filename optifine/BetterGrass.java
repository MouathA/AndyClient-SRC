package optifine;

import net.minecraft.client.resources.model.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class BetterGrass
{
    private static IBakedModel modelEmpty;
    private static IBakedModel modelCubeMycelium;
    private static IBakedModel modelCubeGrassSnowy;
    private static IBakedModel modelCubeGrass;
    
    static {
        BetterGrass.modelEmpty = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false, null, null);
        BetterGrass.modelCubeMycelium = null;
        BetterGrass.modelCubeGrassSnowy = null;
        BetterGrass.modelCubeGrass = null;
    }
    
    public static void update() {
        BetterGrass.modelCubeGrass = BlockModelUtils.makeModelCube("minecraft:blocks/grass_top", 0);
        BetterGrass.modelCubeGrassSnowy = BlockModelUtils.makeModelCube("minecraft:blocks/snow", -1);
        BetterGrass.modelCubeMycelium = BlockModelUtils.makeModelCube("minecraft:blocks/mycelium_top", -1);
    }
    
    public static List getFaceQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, final List list) {
        if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN) {
            return list;
        }
        if (block instanceof BlockMycelium) {
            return Config.isBetterGrassFancy() ? ((getBlockAt(blockPos.offsetDown(), enumFacing, blockAccess) == Blocks.mycelium) ? BetterGrass.modelCubeMycelium.func_177551_a(enumFacing) : list) : BetterGrass.modelCubeMycelium.func_177551_a(enumFacing);
        }
        if (block instanceof BlockGrass) {
            final Block block2 = blockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            final boolean b = block2 == Blocks.snow || block2 == Blocks.snow_layer;
            if (!Config.isBetterGrassFancy()) {
                if (b) {
                    return BetterGrass.modelCubeGrassSnowy.func_177551_a(enumFacing);
                }
                return BetterGrass.modelCubeGrass.func_177551_a(enumFacing);
            }
            else if (b) {
                if (getBlockAt(blockPos, enumFacing, blockAccess) == Blocks.snow_layer) {
                    return BetterGrass.modelCubeGrassSnowy.func_177551_a(enumFacing);
                }
            }
            else if (getBlockAt(blockPos.offsetDown(), enumFacing, blockAccess) == Blocks.grass) {
                return BetterGrass.modelCubeGrass.func_177551_a(enumFacing);
            }
        }
        return list;
    }
    
    private static Block getBlockAt(final BlockPos blockPos, final EnumFacing enumFacing, final IBlockAccess blockAccess) {
        return blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
    }
}
