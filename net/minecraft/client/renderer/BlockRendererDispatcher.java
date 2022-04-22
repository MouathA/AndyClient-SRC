package net.minecraft.client.renderer;

import net.minecraft.client.settings.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private BlockModelShapes field_175028_a;
    private final GameSettings field_175026_b;
    private final BlockModelRenderer blockModelRenderer;
    private final ChestRenderer chestRenderer;
    private final BlockFluidRenderer fluidRenderer;
    
    public BlockRendererDispatcher(final BlockModelShapes field_175028_a, final GameSettings field_175026_b) {
        this.blockModelRenderer = new BlockModelRenderer();
        this.chestRenderer = new ChestRenderer();
        this.fluidRenderer = new BlockFluidRenderer();
        this.field_175028_a = field_175028_a;
        this.field_175026_b = field_175026_b;
    }
    
    public BlockModelShapes func_175023_a() {
        return this.field_175028_a;
    }
    
    public void func_175020_a(IBlockState actualState, final BlockPos blockPos, final TextureAtlasSprite textureAtlasSprite, final IBlockAccess blockAccess) {
        final Block block = actualState.getBlock();
        if (block.getRenderType() == 3) {
            actualState = block.getActualState(actualState, blockAccess, blockPos);
            final IBakedModel func_178125_b = this.field_175028_a.func_178125_b(actualState);
            if (Reflector.ISmartBlockModel.isInstance(func_178125_b)) {
                final IBlockState blockState = (IBlockState)Reflector.call(block, Reflector.ForgeBlock_getExtendedState, actualState, blockAccess, blockPos);
                final EnumWorldBlockLayer[] values = EnumWorldBlockLayer.values();
                while (0 < values.length) {
                    final EnumWorldBlockLayer enumWorldBlockLayer = values[0];
                    if (Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, enumWorldBlockLayer)) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, enumWorldBlockLayer);
                        this.blockModelRenderer.func_178259_a(blockAccess, new SimpleBakedModel.Builder((IBakedModel)Reflector.call(func_178125_b, Reflector.ISmartBlockModel_handleBlockState, blockState), textureAtlasSprite).func_177645_b(), actualState, blockPos, Tessellator.getInstance().getWorldRenderer());
                    }
                    int n = 0;
                    ++n;
                }
                return;
            }
            this.blockModelRenderer.func_178259_a(blockAccess, new SimpleBakedModel.Builder(func_178125_b, textureAtlasSprite).func_177645_b(), actualState, blockPos, Tessellator.getInstance().getWorldRenderer());
        }
    }
    
    public boolean func_175018_a(final IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final WorldRenderer worldRenderer) {
        final int renderType = blockState.getBlock().getRenderType();
        if (renderType == -1) {
            return false;
        }
        switch (renderType) {
            case 1: {
                if (Config.isShaders()) {
                    SVertexBuilder.pushEntity(blockState, blockPos, blockAccess, worldRenderer);
                }
                final boolean func_178270_a = this.fluidRenderer.func_178270_a(blockAccess, blockState, blockPos, worldRenderer);
                if (Config.isShaders()) {
                    SVertexBuilder.popEntity(worldRenderer);
                }
                return func_178270_a;
            }
            case 2: {
                return false;
            }
            case 3: {
                final IBakedModel modelFromBlockState = this.getModelFromBlockState(blockState, blockAccess, blockPos);
                if (Config.isShaders()) {
                    SVertexBuilder.pushEntity(blockState, blockPos, blockAccess, worldRenderer);
                }
                final boolean func_178259_a = this.blockModelRenderer.func_178259_a(blockAccess, modelFromBlockState, blockState, blockPos, worldRenderer);
                if (Config.isShaders()) {
                    SVertexBuilder.popEntity(worldRenderer);
                }
                return func_178259_a;
            }
            default: {
                return false;
            }
        }
    }
    
    public BlockModelRenderer func_175019_b() {
        return this.blockModelRenderer;
    }
    
    private IBakedModel func_175017_a(final IBlockState blockState, final BlockPos blockPos) {
        IBakedModel bakedModel = this.field_175028_a.func_178125_b(blockState);
        if (blockPos != null && this.field_175026_b.field_178880_u && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).func_177564_a(MathHelper.func_180186_a(blockPos));
        }
        return bakedModel;
    }
    
    public IBakedModel getModelFromBlockState(IBlockState actualState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = actualState.getBlock();
        if (blockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
            actualState = block.getActualState(actualState, blockAccess, blockPos);
        }
        IBakedModel bakedModel = this.field_175028_a.func_178125_b(actualState);
        if (blockPos != null && this.field_175026_b.field_178880_u && bakedModel instanceof WeightedBakedModel) {
            bakedModel = ((WeightedBakedModel)bakedModel).func_177564_a(MathHelper.func_180186_a(blockPos));
        }
        if (Reflector.ISmartBlockModel.isInstance(bakedModel)) {
            bakedModel = (IBakedModel)Reflector.call(bakedModel, Reflector.ISmartBlockModel_handleBlockState, (IBlockState)Reflector.call(block, Reflector.ForgeBlock_getExtendedState, actualState, blockAccess, blockPos));
        }
        return bakedModel;
    }
    
    public void func_175016_a(final IBlockState blockState, final float n) {
        final int renderType = blockState.getBlock().getRenderType();
        if (renderType != -1) {
            switch (renderType) {
                case 2: {
                    this.chestRenderer.func_178175_a(blockState.getBlock(), n);
                    break;
                }
                case 3: {
                    this.blockModelRenderer.func_178266_a(this.func_175017_a(blockState, null), blockState, n, true);
                    break;
                }
            }
        }
    }
    
    public boolean func_175021_a(final Block block, final int n) {
        if (block == null) {
            return false;
        }
        final int renderType = block.getRenderType();
        return renderType != 3 && renderType == 2;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.fluidRenderer.func_178268_a();
    }
}
