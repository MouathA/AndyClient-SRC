package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer
{
    private final BlockRendererDispatcher field_178462_c;
    private static final String __OBFID;
    
    public TileEntityPistonRenderer() {
        this.field_178462_c = Minecraft.getMinecraft().getBlockRendererDispatcher();
    }
    
    public void func_178461_a(final TileEntityPiston tileEntityPiston, final double n, final double n2, final double n3, final float n4, final int n5) {
        final BlockPos pos = tileEntityPiston.getPos();
        final IBlockState func_174927_b = tileEntityPiston.func_174927_b();
        final Block block = func_174927_b.getBlock();
        if (block.getMaterial() != Material.air && tileEntityPiston.func_145860_a(n4) < 1.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.blendFunc(770, 771);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(7425);
            }
            else {
                GlStateManager.shadeModel(7424);
            }
            worldRenderer.startDrawingQuads();
            worldRenderer.setVertexFormat(DefaultVertexFormats.field_176600_a);
            worldRenderer.setTranslation((float)n - pos.getX() + tileEntityPiston.func_174929_b(n4), (float)n2 - pos.getY() + tileEntityPiston.func_174928_c(n4), (float)n3 - pos.getZ() + tileEntityPiston.func_174926_d(n4));
            worldRenderer.func_178986_b(1.0f, 1.0f, 1.0f);
            final World world = this.getWorld();
            if (block == Blocks.piston_head && tileEntityPiston.func_145860_a(n4) < 0.5f) {
                final IBlockState withProperty = func_174927_b.withProperty(BlockPistonExtension.field_176327_M, true);
                this.field_178462_c.func_175019_b().renderBlockModel(world, this.field_178462_c.getModelFromBlockState(withProperty, world, pos), withProperty, pos, worldRenderer, true);
            }
            else if (tileEntityPiston.shouldPistonHeadBeRendered() && !tileEntityPiston.isExtending()) {
                final IBlockState withProperty2 = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.field_176325_b, (block == Blocks.sticky_piston) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT).withProperty(BlockPistonExtension.field_176326_a, func_174927_b.getValue(BlockPistonBase.FACING)).withProperty(BlockPistonExtension.field_176327_M, tileEntityPiston.func_145860_a(n4) >= 0.5f);
                this.field_178462_c.func_175019_b().renderBlockModel(world, this.field_178462_c.getModelFromBlockState(withProperty2, world, pos), withProperty2, pos, worldRenderer, true);
                worldRenderer.setTranslation((float)n - pos.getX(), (float)n2 - pos.getY(), (float)n3 - pos.getZ());
                func_174927_b.withProperty(BlockPistonBase.EXTENDED, true);
                this.field_178462_c.func_175019_b().renderBlockModel(world, this.field_178462_c.getModelFromBlockState(func_174927_b, world, pos), func_174927_b, pos, worldRenderer, true);
            }
            else {
                this.field_178462_c.func_175019_b().renderBlockModel(world, this.field_178462_c.getModelFromBlockState(func_174927_b, world, pos), func_174927_b, pos, worldRenderer, false);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            instance.draw();
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.func_178461_a((TileEntityPiston)tileEntity, n, n2, n3, n4, n5);
    }
    
    static {
        __OBFID = "CL_00002469";
    }
}
