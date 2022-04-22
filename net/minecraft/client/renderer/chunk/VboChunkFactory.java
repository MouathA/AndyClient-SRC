package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class VboChunkFactory implements IRenderChunkFactory
{
    private static final String __OBFID;
    
    @Override
    public RenderChunk func_178602_a(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        return new RenderChunk(world, renderGlobal, blockPos, n);
    }
    
    static {
        __OBFID = "CL_00002451";
    }
}
