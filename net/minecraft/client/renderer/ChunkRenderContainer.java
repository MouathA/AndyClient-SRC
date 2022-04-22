package net.minecraft.client.renderer;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.chunk.*;
import Mood.*;
import lumien.chunkanimator.*;
import net.minecraft.util.*;

public abstract class ChunkRenderContainer
{
    private double field_178008_c;
    private double field_178005_d;
    private double field_178006_e;
    protected List field_178009_a;
    protected boolean field_178007_b;
    private static final String __OBFID;
    
    public ChunkRenderContainer() {
        this.field_178009_a = Lists.newArrayListWithCapacity(17424);
    }
    
    public void func_178004_a(final double field_178008_c, final double field_178005_d, final double field_178006_e) {
        this.field_178007_b = true;
        this.field_178009_a.clear();
        this.field_178008_c = field_178008_c;
        this.field_178005_d = field_178005_d;
        this.field_178006_e = field_178006_e;
    }
    
    public void func_178003_a(final RenderChunk renderChunk) {
        final Client instance = Client.INSTANCE;
        if (Client.getModuleByName("BetterChunkLoader").toggled) {
            ChunkAnimator.INSTANCE.animationHandler.preRender(renderChunk);
            final BlockPos func_178568_j = renderChunk.func_178568_j();
            GlStateManager.translate((float)(func_178568_j.getX() - this.field_178008_c), (float)(func_178568_j.getY() - this.field_178005_d), (float)(func_178568_j.getZ() - this.field_178006_e));
        }
        else {
            final BlockPos func_178568_j2 = renderChunk.func_178568_j();
            GlStateManager.translate((float)(func_178568_j2.getX() - this.field_178008_c), (float)(func_178568_j2.getY() - this.field_178005_d), (float)(func_178568_j2.getZ() - this.field_178006_e));
        }
    }
    
    public void func_178002_a(final RenderChunk renderChunk, final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.field_178009_a.add(renderChunk);
    }
    
    public abstract void func_178001_a(final EnumWorldBlockLayer p0);
    
    static {
        __OBFID = "CL_00002563";
    }
}
