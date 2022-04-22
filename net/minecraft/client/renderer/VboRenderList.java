package net.minecraft.client.renderer;

import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import optifine.*;
import org.lwjgl.opengl.*;

public class VboRenderList extends ChunkRenderContainer
{
    @Override
    public void func_178001_a(final EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.field_178007_b) {
            for (final RenderChunk renderChunk : this.field_178009_a) {
                final VertexBuffer func_178565_b = renderChunk.func_178565_b(enumWorldBlockLayer.ordinal());
                this.func_178003_a(renderChunk);
                renderChunk.func_178572_f();
                func_178565_b.func_177359_a();
                this.func_178010_a();
                func_178565_b.func_177358_a(7);
            }
            OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
            this.field_178009_a.clear();
        }
    }
    
    private void func_178010_a() {
        if (!Config.isShaders()) {
            GL11.glVertexPointer(3, 5126, 28, 0L);
            GL11.glColorPointer(4, 5121, 28, 12L);
            GL11.glTexCoordPointer(2, 5126, 28, 16L);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glTexCoordPointer(2, 5122, 28, 24L);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }
}
