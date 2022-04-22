package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import optifine.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockModelRenderer
{
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002518";
    }
    
    public static void updateAoLightValue() {
        BlockModelRenderer.aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
    }
    
    public BlockModelRenderer() {
        if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists()) {
            Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, false);
        }
    }
    
    public boolean func_178259_a(final IBlockAccess blockAccess, final IBakedModel bakedModel, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer) {
        blockState.getBlock().setBlockBoundsBasedOnState(blockAccess, blockPos);
        return this.renderBlockModel(blockAccess, bakedModel, blockState, blockPos, worldRenderer, true);
    }
    
    public boolean renderBlockModel(final IBlockAccess blockAccess, IBakedModel leavesModel, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        final boolean b2 = Minecraft.isAmbientOcclusionEnabled() && blockState.getBlock().getLightValue() == 0 && leavesModel.isGui3d();
        final Block block = blockState.getBlock();
        if (Config.isTreesSmart() && blockState.getBlock() instanceof BlockLeavesBase) {
            leavesModel = SmartLeaves.getLeavesModel(leavesModel);
        }
        return b2 ? this.renderModelAmbientOcclusion(blockAccess, leavesModel, block, blockState, blockPos, worldRenderer, b) : this.renderModelStandard(blockAccess, leavesModel, block, blockState, blockPos, worldRenderer, b);
    }
    
    public boolean func_178265_a(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        return this.renderModelAmbientOcclusion(blockAccess, bakedModel, block, blockAccess.getBlockState(blockPos), blockPos, worldRenderer, b);
    }
    
    public boolean renderModelAmbientOcclusion(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        worldRenderer.func_178963_b(983055);
        RenderEnv renderEnv = null;
        final EnumFacing[] values = EnumFacing.VALUES;
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            List list = bakedModel.func_177551_a(enumFacing);
            if (!list.isEmpty()) {
                final BlockPos offset = blockPos.offset(enumFacing);
                if (!b || block.shouldSideBeRendered(blockAccess, offset, enumFacing)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
                    }
                    if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = BetterGrass.getFaceQuads(blockAccess, block, blockPos, enumFacing, list);
                    }
                    this.renderModelAmbientOcclusionQuads(blockAccess, block, blockPos, worldRenderer, list, renderEnv);
                }
            }
            int n = 0;
            ++n;
        }
        final List func_177550_a = bakedModel.func_177550_a();
        if (func_177550_a.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
            }
            this.renderModelAmbientOcclusionQuads(blockAccess, block, blockPos, worldRenderer, func_177550_a, renderEnv);
        }
        if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos)) {
            final IBakedModel modelSnowLayer = BetterSnow.getModelSnowLayer();
            final IBlockState stateSnowLayer = BetterSnow.getStateSnowLayer();
            this.renderModelAmbientOcclusion(blockAccess, modelSnowLayer, stateSnowLayer.getBlock(), stateSnowLayer, blockPos, worldRenderer, true);
        }
        return true;
    }
    
    public boolean func_178258_b(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        return this.renderModelStandard(blockAccess, bakedModel, block, blockAccess.getBlockState(blockPos), blockPos, worldRenderer, b);
    }
    
    public boolean renderModelStandard(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        RenderEnv renderEnv = null;
        final EnumFacing[] values = EnumFacing.VALUES;
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            List list = bakedModel.func_177551_a(enumFacing);
            if (!list.isEmpty()) {
                final BlockPos offset = blockPos.offset(enumFacing);
                if (!b || block.shouldSideBeRendered(blockAccess, offset, enumFacing)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
                    }
                    if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = BetterGrass.getFaceQuads(blockAccess, block, blockPos, enumFacing, list);
                    }
                    this.renderModelStandardQuads(blockAccess, block, blockPos, enumFacing, block.getMixedBrightnessForBlock(blockAccess, offset), false, worldRenderer, list, renderEnv);
                }
            }
            int n = 0;
            ++n;
        }
        final List func_177550_a = bakedModel.func_177550_a();
        if (func_177550_a.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
            }
            this.renderModelStandardQuads(blockAccess, block, blockPos, null, -1, true, worldRenderer, func_177550_a, renderEnv);
        }
        if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos) && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos)) {
            final IBakedModel modelSnowLayer = BetterSnow.getModelSnowLayer();
            final IBlockState stateSnowLayer = BetterSnow.getStateSnowLayer();
            this.renderModelStandard(blockAccess, modelSnowLayer, stateSnowLayer.getBlock(), stateSnowLayer, blockPos, worldRenderer, true);
        }
        return true;
    }
    
    private void renderModelAmbientOcclusionQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final List list, final RenderEnv renderEnv) {
        final float[] quadBounds = renderEnv.getQuadBounds();
        final BitSet boundsFlags = renderEnv.getBoundsFlags();
        final AmbientOcclusionFace aoFace = renderEnv.getAoFace();
        final IBlockState blockState = renderEnv.getBlockState();
        double n = blockPos.getX();
        double n2 = blockPos.getY();
        double n3 = blockPos.getZ();
        final Block.EnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != Block.EnumOffsetType.NONE) {
            final long func_180186_a = MathHelper.func_180186_a(blockPos);
            n += ((func_180186_a >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            n3 += ((func_180186_a >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
            if (offsetType == Block.EnumOffsetType.XYZ) {
                n2 += ((func_180186_a >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        for (BakedQuad bakedQuad : list) {
            if (!renderEnv.isBreakingAnimation(bakedQuad)) {
                final BakedQuad bakedQuad2 = bakedQuad;
                if (Config.isConnectedTextures()) {
                    bakedQuad = ConnectedTextures.getConnectedTexture(blockAccess, blockState, blockPos, bakedQuad, renderEnv);
                }
                if (bakedQuad == bakedQuad2 && Config.isNaturalTextures()) {
                    bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
                }
            }
            this.func_178261_a(block, bakedQuad.func_178209_a(), bakedQuad.getFace(), quadBounds, boundsFlags);
            aoFace.func_178204_a(blockAccess, block, blockPos, bakedQuad.getFace(), quadBounds, boundsFlags);
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.func_178981_a(bakedQuad.getVertexDataSingle());
                worldRenderer.putSprite(bakedQuad.getSprite());
            }
            else {
                worldRenderer.func_178981_a(bakedQuad.func_178209_a());
            }
            worldRenderer.func_178962_a(AmbientOcclusionFace.access$0(aoFace)[0], AmbientOcclusionFace.access$0(aoFace)[1], AmbientOcclusionFace.access$0(aoFace)[2], AmbientOcclusionFace.access$0(aoFace)[3]);
            final int colorMultiplier = CustomColors.getColorMultiplier(bakedQuad, block, blockAccess, blockPos, renderEnv);
            if (!bakedQuad.func_178212_b() && colorMultiplier == -1) {
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[0], AmbientOcclusionFace.access$1(aoFace)[0], AmbientOcclusionFace.access$1(aoFace)[0], 4);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[1], AmbientOcclusionFace.access$1(aoFace)[1], AmbientOcclusionFace.access$1(aoFace)[1], 3);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[2], AmbientOcclusionFace.access$1(aoFace)[2], AmbientOcclusionFace.access$1(aoFace)[2], 2);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[3], AmbientOcclusionFace.access$1(aoFace)[3], AmbientOcclusionFace.access$1(aoFace)[3], 1);
            }
            else {
                int n4;
                if (colorMultiplier != -1) {
                    n4 = colorMultiplier;
                }
                else {
                    n4 = block.colorMultiplier(blockAccess, blockPos, bakedQuad.func_178211_c());
                }
                if (EntityRenderer.anaglyphEnable) {
                    n4 = TextureUtil.func_177054_c(n4);
                }
                final float n5 = (n4 >> 16 & 0xFF) / 255.0f;
                final float n6 = (n4 >> 8 & 0xFF) / 255.0f;
                final float n7 = (n4 & 0xFF) / 255.0f;
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[0] * n5, AmbientOcclusionFace.access$1(aoFace)[0] * n6, AmbientOcclusionFace.access$1(aoFace)[0] * n7, 4);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[1] * n5, AmbientOcclusionFace.access$1(aoFace)[1] * n6, AmbientOcclusionFace.access$1(aoFace)[1] * n7, 3);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[2] * n5, AmbientOcclusionFace.access$1(aoFace)[2] * n6, AmbientOcclusionFace.access$1(aoFace)[2] * n7, 2);
                worldRenderer.func_178978_a(AmbientOcclusionFace.access$1(aoFace)[3] * n5, AmbientOcclusionFace.access$1(aoFace)[3] * n6, AmbientOcclusionFace.access$1(aoFace)[3] * n7, 1);
            }
            worldRenderer.func_178987_a(n, n2, n3);
        }
    }
    
    private void func_178261_a(final Block block, final int[] array, final EnumFacing enumFacing, final float[] array2, final BitSet set) {
        float min = 32.0f;
        float min2 = 32.0f;
        float min3 = 32.0f;
        float max = -32.0f;
        float max2 = -32.0f;
        float max3 = -32.0f;
        final int n = array.length / 4;
        while (0 < 4) {
            final float intBitsToFloat = Float.intBitsToFloat(array[0 * n]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array[0 * n + 1]);
            final float intBitsToFloat3 = Float.intBitsToFloat(array[0 * n + 2]);
            min = Math.min(min, intBitsToFloat);
            min2 = Math.min(min2, intBitsToFloat2);
            min3 = Math.min(min3, intBitsToFloat3);
            max = Math.max(max, intBitsToFloat);
            max2 = Math.max(max2, intBitsToFloat2);
            max3 = Math.max(max3, intBitsToFloat3);
            int n2 = 0;
            ++n2;
        }
        if (array2 != null) {
            array2[EnumFacing.WEST.getIndex()] = min;
            array2[EnumFacing.EAST.getIndex()] = max;
            array2[EnumFacing.DOWN.getIndex()] = min2;
            array2[EnumFacing.UP.getIndex()] = max2;
            array2[EnumFacing.NORTH.getIndex()] = min3;
            array2[EnumFacing.SOUTH.getIndex()] = max3;
            array2[EnumFacing.WEST.getIndex() + EnumFacing.VALUES.length] = 1.0f - min;
            array2[EnumFacing.EAST.getIndex() + EnumFacing.VALUES.length] = 1.0f - max;
            array2[EnumFacing.DOWN.getIndex() + EnumFacing.VALUES.length] = 1.0f - min2;
            array2[EnumFacing.UP.getIndex() + EnumFacing.VALUES.length] = 1.0f - max2;
            array2[EnumFacing.NORTH.getIndex() + EnumFacing.VALUES.length] = 1.0f - min3;
            array2[EnumFacing.SOUTH.getIndex() + EnumFacing.VALUES.length] = 1.0f - max3;
        }
        switch (SwitchEnumFacing.field_178290_a[enumFacing.ordinal()]) {
            case 1: {
                set.set(1, min >= 1.0E-4f || min3 >= 1.0E-4f || max <= 0.9999f || max3 <= 0.9999f);
                set.set(0, (min2 < 1.0E-4f || block.isFullCube()) && min2 == max2);
                break;
            }
            case 2: {
                set.set(1, min >= 1.0E-4f || min3 >= 1.0E-4f || max <= 0.9999f || max3 <= 0.9999f);
                set.set(0, (max2 > 0.9999f || block.isFullCube()) && min2 == max2);
                break;
            }
            case 3: {
                set.set(1, min >= 1.0E-4f || min2 >= 1.0E-4f || max <= 0.9999f || max2 <= 0.9999f);
                set.set(0, (min3 < 1.0E-4f || block.isFullCube()) && min3 == max3);
                break;
            }
            case 4: {
                set.set(1, min >= 1.0E-4f || min2 >= 1.0E-4f || max <= 0.9999f || max2 <= 0.9999f);
                set.set(0, (max3 > 0.9999f || block.isFullCube()) && min3 == max3);
                break;
            }
            case 5: {
                set.set(1, min2 >= 1.0E-4f || min3 >= 1.0E-4f || max2 <= 0.9999f || max3 <= 0.9999f);
                set.set(0, (min < 1.0E-4f || block.isFullCube()) && min == max);
                break;
            }
            case 6: {
                set.set(1, min2 >= 1.0E-4f || min3 >= 1.0E-4f || max2 <= 0.9999f || max3 <= 0.9999f);
                set.set(0, (max > 0.9999f || block.isFullCube()) && min == max);
                break;
            }
        }
    }
    
    private void renderModelStandardQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, int n, final boolean b, final WorldRenderer worldRenderer, final List list, final RenderEnv renderEnv) {
        final BitSet boundsFlags = renderEnv.getBoundsFlags();
        final IBlockState blockState = renderEnv.getBlockState();
        double n2 = blockPos.getX();
        double n3 = blockPos.getY();
        double n4 = blockPos.getZ();
        final Block.EnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != Block.EnumOffsetType.NONE) {
            final long n5 = (long)(blockPos.getX() * 3129871) ^ blockPos.getZ() * 116129781L;
            final long n6 = n5 * n5 * 42317861L + n5 * 11L;
            n2 += ((n6 >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            n4 += ((n6 >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
            if (offsetType == Block.EnumOffsetType.XYZ) {
                n3 += ((n6 >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        for (BakedQuad bakedQuad : list) {
            if (!renderEnv.isBreakingAnimation(bakedQuad)) {
                final BakedQuad bakedQuad2 = bakedQuad;
                if (Config.isConnectedTextures()) {
                    bakedQuad = ConnectedTextures.getConnectedTexture(blockAccess, blockState, blockPos, bakedQuad, renderEnv);
                }
                if (bakedQuad == bakedQuad2 && Config.isNaturalTextures()) {
                    bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
                }
            }
            if (b) {
                this.func_178261_a(block, bakedQuad.func_178209_a(), bakedQuad.getFace(), null, boundsFlags);
                n = (boundsFlags.get(0) ? block.getMixedBrightnessForBlock(blockAccess, blockPos.offset(bakedQuad.getFace())) : block.getMixedBrightnessForBlock(blockAccess, blockPos));
            }
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.func_178981_a(bakedQuad.getVertexDataSingle());
                worldRenderer.putSprite(bakedQuad.getSprite());
            }
            else {
                worldRenderer.func_178981_a(bakedQuad.func_178209_a());
            }
            worldRenderer.func_178962_a(n, n, n, n);
            final int colorMultiplier = CustomColors.getColorMultiplier(bakedQuad, block, blockAccess, blockPos, renderEnv);
            if (bakedQuad.func_178212_b() || colorMultiplier != -1) {
                int n7;
                if (colorMultiplier != -1) {
                    n7 = colorMultiplier;
                }
                else {
                    n7 = block.colorMultiplier(blockAccess, blockPos, bakedQuad.func_178211_c());
                }
                if (EntityRenderer.anaglyphEnable) {
                    n7 = TextureUtil.func_177054_c(n7);
                }
                final float n8 = (n7 >> 16 & 0xFF) / 255.0f;
                final float n9 = (n7 >> 8 & 0xFF) / 255.0f;
                final float n10 = (n7 & 0xFF) / 255.0f;
                worldRenderer.func_178978_a(n8, n9, n10, 4);
                worldRenderer.func_178978_a(n8, n9, n10, 3);
                worldRenderer.func_178978_a(n8, n9, n10, 2);
                worldRenderer.func_178978_a(n8, n9, n10, 1);
            }
            worldRenderer.func_178987_a(n2, n3, n4);
        }
    }
    
    public void func_178262_a(final IBakedModel bakedModel, final float n, final float n2, final float n3, final float n4) {
        final EnumFacing[] values = EnumFacing.VALUES;
        while (0 < values.length) {
            this.func_178264_a(n, n2, n3, n4, bakedModel.func_177551_a(values[0]));
            int n5 = 0;
            ++n5;
        }
        this.func_178264_a(n, n2, n3, n4, bakedModel.func_177550_a());
    }
    
    public void func_178266_a(final IBakedModel bakedModel, final IBlockState blockState, final float n, final boolean b) {
        final Block block = blockState.getBlock();
        block.setBlockBoundsForItemRender();
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        int n2 = block.getRenderColor(block.getStateForEntityRender(blockState));
        if (EntityRenderer.anaglyphEnable) {
            n2 = TextureUtil.func_177054_c(n2);
        }
        final float n3 = (n2 >> 16 & 0xFF) / 255.0f;
        final float n4 = (n2 >> 8 & 0xFF) / 255.0f;
        final float n5 = (n2 & 0xFF) / 255.0f;
        if (!b) {
            GlStateManager.color(n, n, n, 1.0f);
        }
        this.func_178262_a(bakedModel, n, n3, n4, n5);
    }
    
    private void func_178264_a(final float n, final float n2, final float n3, final float n4, final List list) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        for (final BakedQuad bakedQuad : list) {
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexFormat(DefaultVertexFormats.field_176599_b);
            worldRenderer.func_178981_a(bakedQuad.func_178209_a());
            if (bakedQuad.func_178212_b()) {
                worldRenderer.func_178990_f(n2 * n, n3 * n, n4 * n);
            }
            else {
                worldRenderer.func_178990_f(n, n, n);
            }
            final Vec3i directionVec = bakedQuad.getFace().getDirectionVec();
            worldRenderer.func_178975_e((float)directionVec.getX(), (float)directionVec.getY(), (float)directionVec.getZ());
            instance.draw();
        }
    }
    
    public static float fixAoLightValue(final float n) {
        return (n == 0.2f) ? 0.0f : n;
    }
    
    public static class AmbientOcclusionFace
    {
        private final float[] field_178206_b;
        private final int[] field_178207_c;
        private static final String __OBFID;
        
        public AmbientOcclusionFace(final BlockModelRenderer blockModelRenderer) {
            this.field_178206_b = new float[4];
            this.field_178207_c = new int[4];
        }
        
        public AmbientOcclusionFace() {
            this.field_178206_b = new float[4];
            this.field_178207_c = new int[4];
        }
        
        public void func_178204_a(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, final float[] array, final BitSet set) {
            final BlockPos blockPos2 = set.get(0) ? blockPos.offset(enumFacing) : blockPos;
            final EnumNeighborInfo func_178273_a = EnumNeighborInfo.func_178273_a(enumFacing);
            final BlockPos offset = blockPos2.offset(func_178273_a.field_178276_g[0]);
            final BlockPos offset2 = blockPos2.offset(func_178273_a.field_178276_g[1]);
            final BlockPos offset3 = blockPos2.offset(func_178273_a.field_178276_g[2]);
            final BlockPos offset4 = blockPos2.offset(func_178273_a.field_178276_g[3]);
            final int mixedBrightnessForBlock = block.getMixedBrightnessForBlock(blockAccess, offset);
            final int mixedBrightnessForBlock2 = block.getMixedBrightnessForBlock(blockAccess, offset2);
            final int mixedBrightnessForBlock3 = block.getMixedBrightnessForBlock(blockAccess, offset3);
            final int mixedBrightnessForBlock4 = block.getMixedBrightnessForBlock(blockAccess, offset4);
            final float fixAoLightValue = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue2 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset2).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue3 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset3).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue4 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset4).getBlock().getAmbientOcclusionLightValue());
            final boolean translucent = blockAccess.getBlockState(offset.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent2 = blockAccess.getBlockState(offset2.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent3 = blockAccess.getBlockState(offset3.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent4 = blockAccess.getBlockState(offset4.offset(enumFacing)).getBlock().isTranslucent();
            float fixAoLightValue5;
            int mixedBrightnessForBlock5;
            if (!translucent3 && !translucent) {
                fixAoLightValue5 = fixAoLightValue;
                mixedBrightnessForBlock5 = mixedBrightnessForBlock;
            }
            else {
                final BlockPos offset5 = offset.offset(func_178273_a.field_178276_g[2]);
                fixAoLightValue5 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset5).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock5 = block.getMixedBrightnessForBlock(blockAccess, offset5);
            }
            float fixAoLightValue6;
            int mixedBrightnessForBlock6;
            if (!translucent4 && !translucent) {
                fixAoLightValue6 = fixAoLightValue;
                mixedBrightnessForBlock6 = mixedBrightnessForBlock;
            }
            else {
                final BlockPos offset6 = offset.offset(func_178273_a.field_178276_g[3]);
                fixAoLightValue6 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset6).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock6 = block.getMixedBrightnessForBlock(blockAccess, offset6);
            }
            float fixAoLightValue7;
            int mixedBrightnessForBlock7;
            if (!translucent3 && !translucent2) {
                fixAoLightValue7 = fixAoLightValue2;
                mixedBrightnessForBlock7 = mixedBrightnessForBlock2;
            }
            else {
                final BlockPos offset7 = offset2.offset(func_178273_a.field_178276_g[2]);
                fixAoLightValue7 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset7).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock7 = block.getMixedBrightnessForBlock(blockAccess, offset7);
            }
            float fixAoLightValue8;
            int mixedBrightnessForBlock8;
            if (!translucent4 && !translucent2) {
                fixAoLightValue8 = fixAoLightValue2;
                mixedBrightnessForBlock8 = mixedBrightnessForBlock2;
            }
            else {
                final BlockPos offset8 = offset2.offset(func_178273_a.field_178276_g[3]);
                fixAoLightValue8 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset8).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock8 = block.getMixedBrightnessForBlock(blockAccess, offset8);
            }
            int n = block.getMixedBrightnessForBlock(blockAccess, blockPos);
            if (set.get(0) || !blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock().isOpaqueCube()) {
                n = block.getMixedBrightnessForBlock(blockAccess, blockPos.offset(enumFacing));
            }
            final float fixAoLightValue9 = BlockModelRenderer.fixAoLightValue(set.get(0) ? blockAccess.getBlockState(blockPos2).getBlock().getAmbientOcclusionLightValue() : blockAccess.getBlockState(blockPos).getBlock().getAmbientOcclusionLightValue());
            final VertexTranslations func_178184_a = VertexTranslations.func_178184_a(enumFacing);
            if (set.get(1) && func_178273_a.field_178289_i) {
                final float n2 = (fixAoLightValue4 + fixAoLightValue + fixAoLightValue6 + fixAoLightValue9) * 0.25f;
                final float n3 = (fixAoLightValue3 + fixAoLightValue + fixAoLightValue5 + fixAoLightValue9) * 0.25f;
                final float n4 = (fixAoLightValue3 + fixAoLightValue2 + fixAoLightValue7 + fixAoLightValue9) * 0.25f;
                final float n5 = (fixAoLightValue4 + fixAoLightValue2 + fixAoLightValue8 + fixAoLightValue9) * 0.25f;
                final float n6 = array[func_178273_a.field_178286_j[0].field_178229_m] * array[func_178273_a.field_178286_j[1].field_178229_m];
                final float n7 = array[func_178273_a.field_178286_j[2].field_178229_m] * array[func_178273_a.field_178286_j[3].field_178229_m];
                final float n8 = array[func_178273_a.field_178286_j[4].field_178229_m] * array[func_178273_a.field_178286_j[5].field_178229_m];
                final float n9 = array[func_178273_a.field_178286_j[6].field_178229_m] * array[func_178273_a.field_178286_j[7].field_178229_m];
                final float n10 = array[func_178273_a.field_178287_k[0].field_178229_m] * array[func_178273_a.field_178287_k[1].field_178229_m];
                final float n11 = array[func_178273_a.field_178287_k[2].field_178229_m] * array[func_178273_a.field_178287_k[3].field_178229_m];
                final float n12 = array[func_178273_a.field_178287_k[4].field_178229_m] * array[func_178273_a.field_178287_k[5].field_178229_m];
                final float n13 = array[func_178273_a.field_178287_k[6].field_178229_m] * array[func_178273_a.field_178287_k[7].field_178229_m];
                final float n14 = array[func_178273_a.field_178284_l[0].field_178229_m] * array[func_178273_a.field_178284_l[1].field_178229_m];
                final float n15 = array[func_178273_a.field_178284_l[2].field_178229_m] * array[func_178273_a.field_178284_l[3].field_178229_m];
                final float n16 = array[func_178273_a.field_178284_l[4].field_178229_m] * array[func_178273_a.field_178284_l[5].field_178229_m];
                final float n17 = array[func_178273_a.field_178284_l[6].field_178229_m] * array[func_178273_a.field_178284_l[7].field_178229_m];
                final float n18 = array[func_178273_a.field_178285_m[0].field_178229_m] * array[func_178273_a.field_178285_m[1].field_178229_m];
                final float n19 = array[func_178273_a.field_178285_m[2].field_178229_m] * array[func_178273_a.field_178285_m[3].field_178229_m];
                final float n20 = array[func_178273_a.field_178285_m[4].field_178229_m] * array[func_178273_a.field_178285_m[5].field_178229_m];
                final float n21 = array[func_178273_a.field_178285_m[6].field_178229_m] * array[func_178273_a.field_178285_m[7].field_178229_m];
                this.field_178206_b[VertexTranslations.access$2(func_178184_a)] = n2 * n6 + n3 * n7 + n4 * n8 + n5 * n9;
                this.field_178206_b[VertexTranslations.access$3(func_178184_a)] = n2 * n10 + n3 * n11 + n4 * n12 + n5 * n13;
                this.field_178206_b[VertexTranslations.access$4(func_178184_a)] = n2 * n14 + n3 * n15 + n4 * n16 + n5 * n17;
                this.field_178206_b[VertexTranslations.access$5(func_178184_a)] = n2 * n18 + n3 * n19 + n4 * n20 + n5 * n21;
                final int aoBrightness = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock, mixedBrightnessForBlock6, n);
                final int aoBrightness2 = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock, mixedBrightnessForBlock5, n);
                final int aoBrightness3 = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock2, mixedBrightnessForBlock7, n);
                final int aoBrightness4 = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock2, mixedBrightnessForBlock8, n);
                this.field_178207_c[VertexTranslations.access$2(func_178184_a)] = this.func_178203_a(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n6, n7, n8, n9);
                this.field_178207_c[VertexTranslations.access$3(func_178184_a)] = this.func_178203_a(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n10, n11, n12, n13);
                this.field_178207_c[VertexTranslations.access$4(func_178184_a)] = this.func_178203_a(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n14, n15, n16, n17);
                this.field_178207_c[VertexTranslations.access$5(func_178184_a)] = this.func_178203_a(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n18, n19, n20, n21);
            }
            else {
                final float n22 = (fixAoLightValue4 + fixAoLightValue + fixAoLightValue6 + fixAoLightValue9) * 0.25f;
                final float n23 = (fixAoLightValue3 + fixAoLightValue + fixAoLightValue5 + fixAoLightValue9) * 0.25f;
                final float n24 = (fixAoLightValue3 + fixAoLightValue2 + fixAoLightValue7 + fixAoLightValue9) * 0.25f;
                final float n25 = (fixAoLightValue4 + fixAoLightValue2 + fixAoLightValue8 + fixAoLightValue9) * 0.25f;
                this.field_178207_c[VertexTranslations.access$2(func_178184_a)] = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock, mixedBrightnessForBlock6, n);
                this.field_178207_c[VertexTranslations.access$3(func_178184_a)] = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock, mixedBrightnessForBlock5, n);
                this.field_178207_c[VertexTranslations.access$4(func_178184_a)] = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock2, mixedBrightnessForBlock7, n);
                this.field_178207_c[VertexTranslations.access$5(func_178184_a)] = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock2, mixedBrightnessForBlock8, n);
                this.field_178206_b[VertexTranslations.access$2(func_178184_a)] = n22;
                this.field_178206_b[VertexTranslations.access$3(func_178184_a)] = n23;
                this.field_178206_b[VertexTranslations.access$4(func_178184_a)] = n24;
                this.field_178206_b[VertexTranslations.access$5(func_178184_a)] = n25;
            }
        }
        
        private int getAoBrightness(int n, int n2, int n3, final int n4) {
            if (n == 0) {
                n = n4;
            }
            if (n2 == 0) {
                n2 = n4;
            }
            if (n3 == 0) {
                n3 = n4;
            }
            return n + n2 + n3 + n4 >> 2 & 0xFF00FF;
        }
        
        private int func_178203_a(final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final float n8) {
            return ((int)((n >> 16 & 0xFF) * n5 + (n2 >> 16 & 0xFF) * n6 + (n3 >> 16 & 0xFF) * n7 + (n4 >> 16 & 0xFF) * n8) & 0xFF) << 16 | ((int)((n & 0xFF) * n5 + (n2 & 0xFF) * n6 + (n3 & 0xFF) * n7 + (n4 & 0xFF) * n8) & 0xFF);
        }
        
        static int[] access$0(final AmbientOcclusionFace ambientOcclusionFace) {
            return ambientOcclusionFace.field_178207_c;
        }
        
        static float[] access$1(final AmbientOcclusionFace ambientOcclusionFace) {
            return ambientOcclusionFace.field_178206_b;
        }
        
        static {
            __OBFID = "CL_00002515";
        }
    }
    
    public enum EnumNeighborInfo
    {
        DOWN("DOWN", 0, "DOWN", 0, "DOWN", 0, new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5f, true, new Orientation[] { Orientation.FLIP_WEST, Orientation.SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_SOUTH, Orientation.WEST, Orientation.FLIP_SOUTH, Orientation.WEST, Orientation.SOUTH }, new Orientation[] { Orientation.FLIP_WEST, Orientation.NORTH, Orientation.FLIP_WEST, Orientation.FLIP_NORTH, Orientation.WEST, Orientation.FLIP_NORTH, Orientation.WEST, Orientation.NORTH }, new Orientation[] { Orientation.FLIP_EAST, Orientation.NORTH, Orientation.FLIP_EAST, Orientation.FLIP_NORTH, Orientation.EAST, Orientation.FLIP_NORTH, Orientation.EAST, Orientation.NORTH }, new Orientation[] { Orientation.FLIP_EAST, Orientation.SOUTH, Orientation.FLIP_EAST, Orientation.FLIP_SOUTH, Orientation.EAST, Orientation.FLIP_SOUTH, Orientation.EAST, Orientation.SOUTH }), 
        UP("UP", 1, "UP", 1, "UP", 1, new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH }, 1.0f, true, new Orientation[] { Orientation.EAST, Orientation.SOUTH, Orientation.EAST, Orientation.FLIP_SOUTH, Orientation.FLIP_EAST, Orientation.FLIP_SOUTH, Orientation.FLIP_EAST, Orientation.SOUTH }, new Orientation[] { Orientation.EAST, Orientation.NORTH, Orientation.EAST, Orientation.FLIP_NORTH, Orientation.FLIP_EAST, Orientation.FLIP_NORTH, Orientation.FLIP_EAST, Orientation.NORTH }, new Orientation[] { Orientation.WEST, Orientation.NORTH, Orientation.WEST, Orientation.FLIP_NORTH, Orientation.FLIP_WEST, Orientation.FLIP_NORTH, Orientation.FLIP_WEST, Orientation.NORTH }, new Orientation[] { Orientation.WEST, Orientation.SOUTH, Orientation.WEST, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.SOUTH }), 
        NORTH("NORTH", 2, "NORTH", 2, "NORTH", 2, new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST }, 0.8f, true, new Orientation[] { Orientation.UP, Orientation.FLIP_WEST, Orientation.UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST }, new Orientation[] { Orientation.UP, Orientation.FLIP_EAST, Orientation.UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST }, new Orientation[] { Orientation.DOWN, Orientation.FLIP_EAST, Orientation.DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST }, new Orientation[] { Orientation.DOWN, Orientation.FLIP_WEST, Orientation.DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST }), 
        SOUTH("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }, 0.8f, true, new Orientation[] { Orientation.UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.UP, Orientation.WEST }, new Orientation[] { Orientation.DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.DOWN, Orientation.WEST }, new Orientation[] { Orientation.DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.DOWN, Orientation.EAST }, new Orientation[] { Orientation.UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.UP, Orientation.EAST }), 
        WEST("WEST", 4, "WEST", 4, "WEST", 4, new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6f, true, new Orientation[] { Orientation.UP, Orientation.SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.SOUTH }, new Orientation[] { Orientation.UP, Orientation.NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.NORTH }, new Orientation[] { Orientation.DOWN, Orientation.NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.NORTH }, new Orientation[] { Orientation.DOWN, Orientation.SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.SOUTH }), 
        EAST("EAST", 5, "EAST", 5, "EAST", 5, new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6f, true, new Orientation[] { Orientation.FLIP_DOWN, Orientation.SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.SOUTH }, new Orientation[] { Orientation.FLIP_DOWN, Orientation.NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.NORTH }, new Orientation[] { Orientation.FLIP_UP, Orientation.NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.NORTH }, new Orientation[] { Orientation.FLIP_UP, Orientation.SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.SOUTH });
        
        protected final EnumFacing[] field_178276_g;
        protected final float field_178288_h;
        protected final boolean field_178289_i;
        protected final Orientation[] field_178286_j;
        protected final Orientation[] field_178287_k;
        protected final Orientation[] field_178284_l;
        protected final Orientation[] field_178285_m;
        private static final EnumNeighborInfo[] field_178282_n;
        private static final EnumNeighborInfo[] $VALUES;
        private static final String __OBFID;
        private static final EnumNeighborInfo[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002516";
            ENUM$VALUES = new EnumNeighborInfo[] { EnumNeighborInfo.DOWN, EnumNeighborInfo.UP, EnumNeighborInfo.NORTH, EnumNeighborInfo.SOUTH, EnumNeighborInfo.WEST, EnumNeighborInfo.EAST };
            field_178282_n = new EnumNeighborInfo[6];
            $VALUES = new EnumNeighborInfo[] { EnumNeighborInfo.DOWN, EnumNeighborInfo.UP, EnumNeighborInfo.NORTH, EnumNeighborInfo.SOUTH, EnumNeighborInfo.WEST, EnumNeighborInfo.EAST };
            EnumNeighborInfo.field_178282_n[EnumFacing.DOWN.getIndex()] = EnumNeighborInfo.DOWN;
            EnumNeighborInfo.field_178282_n[EnumFacing.UP.getIndex()] = EnumNeighborInfo.UP;
            EnumNeighborInfo.field_178282_n[EnumFacing.NORTH.getIndex()] = EnumNeighborInfo.NORTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.SOUTH.getIndex()] = EnumNeighborInfo.SOUTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.WEST.getIndex()] = EnumNeighborInfo.WEST;
            EnumNeighborInfo.field_178282_n[EnumFacing.EAST.getIndex()] = EnumNeighborInfo.EAST;
        }
        
        private EnumNeighborInfo(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final EnumFacing[] field_178276_g, final float field_178288_h, final boolean field_178289_i, final Orientation[] field_178286_j, final Orientation[] field_178287_k, final Orientation[] field_178284_l, final Orientation[] field_178285_m) {
            this.field_178276_g = field_178276_g;
            this.field_178288_h = field_178288_h;
            this.field_178289_i = field_178289_i;
            this.field_178286_j = field_178286_j;
            this.field_178287_k = field_178287_k;
            this.field_178284_l = field_178284_l;
            this.field_178285_m = field_178285_m;
        }
        
        public static EnumNeighborInfo func_178273_a(final EnumFacing enumFacing) {
            return EnumNeighborInfo.field_178282_n[enumFacing.getIndex()];
        }
    }
    
    public enum Orientation
    {
        DOWN("DOWN", 0, "DOWN", 0, "DOWN", 0, EnumFacing.DOWN, false), 
        UP("UP", 1, "UP", 1, "UP", 1, EnumFacing.UP, false), 
        NORTH("NORTH", 2, "NORTH", 2, "NORTH", 2, EnumFacing.NORTH, false), 
        SOUTH("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, EnumFacing.SOUTH, false), 
        WEST("WEST", 4, "WEST", 4, "WEST", 4, EnumFacing.WEST, false), 
        EAST("EAST", 5, "EAST", 5, "EAST", 5, EnumFacing.EAST, false), 
        FLIP_DOWN("FLIP_DOWN", 6, "FLIP_DOWN", 6, "FLIP_DOWN", 6, EnumFacing.DOWN, true), 
        FLIP_UP("FLIP_UP", 7, "FLIP_UP", 7, "FLIP_UP", 7, EnumFacing.UP, true), 
        FLIP_NORTH("FLIP_NORTH", 8, "FLIP_NORTH", 8, "FLIP_NORTH", 8, EnumFacing.NORTH, true), 
        FLIP_SOUTH("FLIP_SOUTH", 9, "FLIP_SOUTH", 9, "FLIP_SOUTH", 9, EnumFacing.SOUTH, true), 
        FLIP_WEST("FLIP_WEST", 10, "FLIP_WEST", 10, "FLIP_WEST", 10, EnumFacing.WEST, true), 
        FLIP_EAST("FLIP_EAST", 11, "FLIP_EAST", 11, "FLIP_EAST", 11, EnumFacing.EAST, true);
        
        protected final int field_178229_m;
        private static final Orientation[] $VALUES;
        private static final String __OBFID;
        private static final Orientation[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002513";
            ENUM$VALUES = new Orientation[] { Orientation.DOWN, Orientation.UP, Orientation.NORTH, Orientation.SOUTH, Orientation.WEST, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_EAST };
            $VALUES = new Orientation[] { Orientation.DOWN, Orientation.UP, Orientation.NORTH, Orientation.SOUTH, Orientation.WEST, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_EAST };
        }
        
        private Orientation(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final EnumFacing enumFacing, final boolean b) {
            this.field_178229_m = enumFacing.getIndex() + (b ? EnumFacing.values().length : 0);
        }
    }
    
    enum VertexTranslations
    {
        DOWN("DOWN", 0, "DOWN", 0, "DOWN", 0, 0, 1, 2, 3), 
        UP("UP", 1, "UP", 1, "UP", 1, 2, 3, 0, 1), 
        NORTH("NORTH", 2, "NORTH", 2, "NORTH", 2, 3, 0, 1, 2), 
        SOUTH("SOUTH", 3, "SOUTH", 3, "SOUTH", 3, 0, 1, 2, 3), 
        WEST("WEST", 4, "WEST", 4, "WEST", 4, 3, 0, 1, 2), 
        EAST("EAST", 5, "EAST", 5, "EAST", 5, 1, 2, 3, 0);
        
        private final int field_178191_g;
        private final int field_178200_h;
        private final int field_178201_i;
        private final int field_178198_j;
        private static final VertexTranslations[] field_178199_k;
        private static final VertexTranslations[] $VALUES;
        private static final String __OBFID;
        private static final VertexTranslations[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002514";
            ENUM$VALUES = new VertexTranslations[] { VertexTranslations.DOWN, VertexTranslations.UP, VertexTranslations.NORTH, VertexTranslations.SOUTH, VertexTranslations.WEST, VertexTranslations.EAST };
            field_178199_k = new VertexTranslations[6];
            $VALUES = new VertexTranslations[] { VertexTranslations.DOWN, VertexTranslations.UP, VertexTranslations.NORTH, VertexTranslations.SOUTH, VertexTranslations.WEST, VertexTranslations.EAST };
            VertexTranslations.field_178199_k[EnumFacing.DOWN.getIndex()] = VertexTranslations.DOWN;
            VertexTranslations.field_178199_k[EnumFacing.UP.getIndex()] = VertexTranslations.UP;
            VertexTranslations.field_178199_k[EnumFacing.NORTH.getIndex()] = VertexTranslations.NORTH;
            VertexTranslations.field_178199_k[EnumFacing.SOUTH.getIndex()] = VertexTranslations.SOUTH;
            VertexTranslations.field_178199_k[EnumFacing.WEST.getIndex()] = VertexTranslations.WEST;
            VertexTranslations.field_178199_k[EnumFacing.EAST.getIndex()] = VertexTranslations.EAST;
        }
        
        private VertexTranslations(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final int field_178191_g, final int field_178200_h, final int field_178201_i, final int field_178198_j) {
            this.field_178191_g = field_178191_g;
            this.field_178200_h = field_178200_h;
            this.field_178201_i = field_178201_i;
            this.field_178198_j = field_178198_j;
        }
        
        public static VertexTranslations func_178184_a(final EnumFacing enumFacing) {
            return VertexTranslations.field_178199_k[enumFacing.getIndex()];
        }
        
        static int access$2(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178191_g;
        }
        
        static int access$3(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178200_h;
        }
        
        static int access$4(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178201_i;
        }
        
        static int access$5(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178198_j;
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178290_a;
        private static final String __OBFID;
        private static final String[] lIllIIIIIlIlllIl;
        private static String[] lIllIIIIIlIllllI;
        
        static {
            lIIIIIIllIlllIlII();
            lIIIIIIllIlllIIll();
            __OBFID = SwitchEnumFacing.lIllIIIIIlIlllIl[0];
            field_178290_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lIIIIIIllIlllIIll() {
            (lIllIIIIIlIlllIl = new String[1])[0] = lIIIIIIllIlllIIlI(SwitchEnumFacing.lIllIIIIIlIllllI[0], SwitchEnumFacing.lIllIIIIIlIllllI[1]);
            SwitchEnumFacing.lIllIIIIIlIllllI = null;
        }
        
        private static void lIIIIIIllIlllIlII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIllIIIIIlIllllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIIIIIllIlllIIlI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
