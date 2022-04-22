package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ListedRenderChunk extends RenderChunk
{
    private final int field_178601_d;
    private static final String __OBFID;
    
    public ListedRenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        super(world, renderGlobal, blockPos, n);
        this.field_178601_d = GLAllocation.generateDisplayLists(EnumWorldBlockLayer.values().length);
    }
    
    public int func_178600_a(final EnumWorldBlockLayer enumWorldBlockLayer, final CompiledChunk compiledChunk) {
        return compiledChunk.func_178491_b(enumWorldBlockLayer) ? -1 : (this.field_178601_d + enumWorldBlockLayer.ordinal());
    }
    
    @Override
    public void func_178566_a() {
        super.func_178566_a();
        GLAllocation.func_178874_a(this.field_178601_d, EnumWorldBlockLayer.values().length);
    }
    
    static {
        __OBFID = "CL_00002453";
    }
}
