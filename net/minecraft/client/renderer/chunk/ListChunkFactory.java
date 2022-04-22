package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ListChunkFactory implements IRenderChunkFactory
{
    @Override
    public RenderChunk func_178602_a(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        return new ListedRenderChunk(world, renderGlobal, blockPos, n);
    }
}
