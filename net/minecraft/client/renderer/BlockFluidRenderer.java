package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import optifine.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class BlockFluidRenderer
{
    private TextureAtlasSprite[] field_178272_a;
    private TextureAtlasSprite[] field_178271_b;
    private static final String __OBFID;
    
    public BlockFluidRenderer() {
        this.field_178272_a = new TextureAtlasSprite[2];
        this.field_178271_b = new TextureAtlasSprite[2];
        this.func_178268_a();
    }
    
    protected void func_178268_a() {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        this.field_178272_a[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_still");
        this.field_178272_a[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_flow");
        this.field_178271_b[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_still");
        this.field_178271_b[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_flow");
    }
    
    public boolean func_178270_a(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer) {
        final BlockLiquid blockLiquid = (BlockLiquid)blockState.getBlock();
        blockLiquid.setBlockBoundsBasedOnState(blockAccess, blockPos);
        final TextureAtlasSprite[] array = (blockLiquid.getMaterial() == Material.lava) ? this.field_178272_a : this.field_178271_b;
        final RenderEnv instance = RenderEnv.getInstance(blockAccess, blockState, blockPos);
        final int fluidColor = CustomColors.getFluidColor(blockAccess, blockState, blockPos, instance);
        final float n = (fluidColor >> 16 & 0xFF) / 255.0f;
        final float n2 = (fluidColor >> 8 & 0xFF) / 255.0f;
        final float n3 = (fluidColor & 0xFF) / 255.0f;
        final boolean shouldSideBeRendered = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetUp(), EnumFacing.UP);
        final boolean shouldSideBeRendered2 = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetDown(), EnumFacing.DOWN);
        final boolean[] borderFlags = instance.getBorderFlags();
        borderFlags[0] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetNorth(), EnumFacing.NORTH);
        borderFlags[1] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetSouth(), EnumFacing.SOUTH);
        borderFlags[2] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetWest(), EnumFacing.WEST);
        borderFlags[3] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.offsetEast(), EnumFacing.EAST);
        if (!shouldSideBeRendered && !shouldSideBeRendered2 && !borderFlags[0] && !borderFlags[1] && !borderFlags[2] && !borderFlags[3]) {
            return false;
        }
        final float n4 = 0.5f;
        final float n5 = 1.0f;
        final float n6 = 0.8f;
        final float n7 = 0.6f;
        final Material material = blockLiquid.getMaterial();
        float func_178269_a = this.func_178269_a(blockAccess, blockPos, material);
        float func_178269_a2 = this.func_178269_a(blockAccess, blockPos.offsetSouth(), material);
        float func_178269_a3 = this.func_178269_a(blockAccess, blockPos.offsetEast().offsetSouth(), material);
        float func_178269_a4 = this.func_178269_a(blockAccess, blockPos.offsetEast(), material);
        final double n8 = blockPos.getX();
        final double n9 = blockPos.getY();
        final double n10 = blockPos.getZ();
        final float n11 = 0.001f;
        if (shouldSideBeRendered) {
            TextureAtlasSprite sprite = array[0];
            final float n12 = (float)BlockLiquid.func_180689_a(blockAccess, blockPos, material);
            if (n12 > -999.0f) {
                sprite = array[1];
            }
            worldRenderer.setSprite(sprite);
            func_178269_a -= n11;
            func_178269_a2 -= n11;
            func_178269_a3 -= n11;
            func_178269_a4 -= n11;
            float n13;
            float n14;
            float interpolatedU;
            float n15;
            float n16;
            float interpolatedV;
            float interpolatedU2;
            float interpolatedV2;
            if (n12 < -999.0f) {
                n13 = sprite.getInterpolatedU(0.0);
                n14 = sprite.getInterpolatedV(0.0);
                interpolatedU = n13;
                n15 = sprite.getInterpolatedV(16.0);
                n16 = sprite.getInterpolatedU(16.0);
                interpolatedV = n15;
                interpolatedU2 = n16;
                interpolatedV2 = n14;
            }
            else {
                final float n17 = MathHelper.sin(n12) * 0.25f;
                final float n18 = MathHelper.cos(n12) * 0.25f;
                n13 = sprite.getInterpolatedU(8.0f + (-n18 - n17) * 16.0f);
                n14 = sprite.getInterpolatedV(8.0f + (-n18 + n17) * 16.0f);
                interpolatedU = sprite.getInterpolatedU(8.0f + (-n18 + n17) * 16.0f);
                n15 = sprite.getInterpolatedV(8.0f + (n18 + n17) * 16.0f);
                n16 = sprite.getInterpolatedU(8.0f + (n18 + n17) * 16.0f);
                interpolatedV = sprite.getInterpolatedV(8.0f + (n18 - n17) * 16.0f);
                interpolatedU2 = sprite.getInterpolatedU(8.0f + (n18 - n17) * 16.0f);
                interpolatedV2 = sprite.getInterpolatedV(8.0f + (-n18 - n17) * 16.0f);
            }
            worldRenderer.func_178963_b(blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos));
            worldRenderer.func_178986_b(n5 * n, n5 * n2, n5 * n3);
            worldRenderer.addVertexWithUV(n8 + 0.0, n9 + func_178269_a, n10 + 0.0, n13, n14);
            worldRenderer.addVertexWithUV(n8 + 0.0, n9 + func_178269_a2, n10 + 1.0, interpolatedU, n15);
            worldRenderer.addVertexWithUV(n8 + 1.0, n9 + func_178269_a3, n10 + 1.0, n16, interpolatedV);
            worldRenderer.addVertexWithUV(n8 + 1.0, n9 + func_178269_a4, n10 + 0.0, interpolatedU2, interpolatedV2);
            if (blockLiquid.func_176364_g(blockAccess, blockPos.offsetUp())) {
                worldRenderer.addVertexWithUV(n8 + 0.0, n9 + func_178269_a, n10 + 0.0, n13, n14);
                worldRenderer.addVertexWithUV(n8 + 1.0, n9 + func_178269_a4, n10 + 0.0, interpolatedU2, interpolatedV2);
                worldRenderer.addVertexWithUV(n8 + 1.0, n9 + func_178269_a3, n10 + 1.0, n16, interpolatedV);
                worldRenderer.addVertexWithUV(n8 + 0.0, n9 + func_178269_a2, n10 + 1.0, interpolatedU, n15);
            }
        }
        if (shouldSideBeRendered2) {
            worldRenderer.func_178963_b(blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos.offsetDown()));
            worldRenderer.func_178986_b(n4, n4, n4);
            final float minU = array[0].getMinU();
            final float maxU = array[0].getMaxU();
            final float minV = array[0].getMinV();
            final float maxV = array[0].getMaxV();
            worldRenderer.addVertexWithUV(n8, n9, n10 + 1.0, minU, maxV);
            worldRenderer.addVertexWithUV(n8, n9, n10, minU, minV);
            worldRenderer.addVertexWithUV(n8 + 1.0, n9, n10, maxU, minV);
            worldRenderer.addVertexWithUV(n8 + 1.0, n9, n10 + 1.0, maxU, maxV);
        }
        while (0 < 4) {
            int n19 = 0;
            if (!false) {
                --n19;
            }
            if (false == true) {
                ++n19;
            }
            int n20 = 0;
            if (0 == 2) {
                --n20;
            }
            if (0 == 3) {
                ++n20;
            }
            final BlockPos add = blockPos.add(0, 0, 0);
            final TextureAtlasSprite sprite2 = array[1];
            worldRenderer.setSprite(sprite2);
            if (borderFlags[0]) {
                float n21;
                float n22;
                double n23;
                double n24;
                double n25;
                double n26;
                if (!false) {
                    n21 = func_178269_a;
                    n22 = func_178269_a4;
                    n23 = n8;
                    n24 = n8 + 1.0;
                    n25 = n10 + n11;
                    n26 = n10 + n11;
                }
                else if (false == true) {
                    n21 = func_178269_a3;
                    n22 = func_178269_a2;
                    n23 = n8 + 1.0;
                    n24 = n8;
                    n25 = n10 + 1.0 - n11;
                    n26 = n10 + 1.0 - n11;
                }
                else if (0 == 2) {
                    n21 = func_178269_a2;
                    n22 = func_178269_a;
                    n23 = n8 + n11;
                    n24 = n8 + n11;
                    n25 = n10 + 1.0;
                    n26 = n10;
                }
                else {
                    n21 = func_178269_a4;
                    n22 = func_178269_a3;
                    n23 = n8 + 1.0 - n11;
                    n24 = n8 + 1.0 - n11;
                    n25 = n10;
                    n26 = n10 + 1.0;
                }
                final float interpolatedU3 = sprite2.getInterpolatedU(0.0);
                final float interpolatedU4 = sprite2.getInterpolatedU(8.0);
                final float interpolatedV3 = sprite2.getInterpolatedV((1.0f - n21) * 16.0f * 0.5f);
                final float interpolatedV4 = sprite2.getInterpolatedV((1.0f - n22) * 16.0f * 0.5f);
                final float interpolatedV5 = sprite2.getInterpolatedV(8.0);
                worldRenderer.func_178963_b(blockLiquid.getMixedBrightnessForBlock(blockAccess, add));
                final float n27 = 1.0f * ((0 < 2) ? n6 : n7);
                worldRenderer.func_178986_b(n5 * n27 * n, n5 * n27 * n2, n5 * n27 * n3);
                worldRenderer.addVertexWithUV(n23, n9 + n21, n25, interpolatedU3, interpolatedV3);
                worldRenderer.addVertexWithUV(n24, n9 + n22, n26, interpolatedU4, interpolatedV4);
                worldRenderer.addVertexWithUV(n24, n9 + 0.0, n26, interpolatedU4, interpolatedV5);
                worldRenderer.addVertexWithUV(n23, n9 + 0.0, n25, interpolatedU3, interpolatedV5);
                worldRenderer.addVertexWithUV(n23, n9 + 0.0, n25, interpolatedU3, interpolatedV5);
                worldRenderer.addVertexWithUV(n24, n9 + 0.0, n26, interpolatedU4, interpolatedV5);
                worldRenderer.addVertexWithUV(n24, n9 + n22, n26, interpolatedU4, interpolatedV4);
                worldRenderer.addVertexWithUV(n23, n9 + n21, n25, interpolatedU3, interpolatedV3);
            }
            int n28 = 0;
            ++n28;
        }
        worldRenderer.setSprite(null);
        return true;
    }
    
    private float func_178269_a(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        float n = 0.0f;
        while (0 < 4) {
            final BlockPos add = blockPos.add(0, 0, 0);
            if (blockAccess.getBlockState(add.offsetUp()).getBlock().getMaterial() == material) {
                return 1.0f;
            }
            final IBlockState blockState = blockAccess.getBlockState(add);
            final Material material2 = blockState.getBlock().getMaterial();
            if (material2 == material) {
                final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
                int n2 = 0;
                if (intValue >= 8 || intValue == 0) {
                    n += BlockLiquid.getLiquidHeightPercent(intValue) * 10.0f;
                    n2 += 10;
                }
                n += BlockLiquid.getLiquidHeightPercent(intValue);
                ++n2;
            }
            else if (!material2.isSolid()) {
                ++n;
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return 1.0f - n / 0;
    }
    
    static {
        __OBFID = "CL_00002519";
    }
}
