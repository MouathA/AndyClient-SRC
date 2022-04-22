package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;

public class VertexBufferUploader extends WorldVertexBufferUploader
{
    private VertexBuffer field_178179_a;
    private static final String __OBFID;
    
    public VertexBufferUploader() {
        this.field_178179_a = null;
    }
    
    @Override
    public int draw(final WorldRenderer worldRenderer, final int n) {
        worldRenderer.reset();
        this.field_178179_a.func_177360_a(worldRenderer.func_178966_f(), worldRenderer.func_178976_e());
        return n;
    }
    
    public void func_178178_a(final VertexBuffer field_178179_a) {
        this.field_178179_a = field_178179_a;
    }
    
    static {
        __OBFID = "CL_00002532";
    }
}
