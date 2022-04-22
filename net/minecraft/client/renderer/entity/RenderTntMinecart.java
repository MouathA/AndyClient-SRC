package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class RenderTntMinecart extends RenderMinecart
{
    private static final String __OBFID;
    
    public RenderTntMinecart(final RenderManager renderManager) {
        super(renderManager);
    }
    
    protected void func_180561_a(final EntityMinecartTNT entityMinecartTNT, final float n, final IBlockState blockState) {
        final int func_94104_d = entityMinecartTNT.func_94104_d();
        if (func_94104_d > -1 && func_94104_d - n + 1.0f < 10.0f) {
            final float clamp_float = MathHelper.clamp_float(1.0f - (func_94104_d - n + 1.0f) / 10.0f, 0.0f, 1.0f);
            final float n2 = clamp_float * clamp_float;
            final float n3 = 1.0f + n2 * n2 * 0.3f;
            GlStateManager.scale(n3, n3, n3);
        }
        super.func_180560_a(entityMinecartTNT, n, blockState);
        if (func_94104_d > -1 && func_94104_d / 5 % 2 == 0) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0f, 1.0f, 1.0f, (1.0f - (func_94104_d - n + 1.0f) / 100.0f) * 0.8f);
            blockRendererDispatcher.func_175016_a(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        this.func_180561_a((EntityMinecartTNT)entityMinecart, n, blockState);
    }
    
    static {
        __OBFID = "CL_00001029";
    }
}
