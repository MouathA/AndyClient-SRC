package net.minecraft.client.renderer;

public class Tessellator
{
    private WorldRenderer worldRenderer;
    private WorldVertexBufferUploader field_178182_b;
    private static final Tessellator instance;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000960";
        instance = new Tessellator(2097152);
    }
    
    public static Tessellator getInstance() {
        return Tessellator.instance;
    }
    
    public Tessellator(final int n) {
        this.field_178182_b = new WorldVertexBufferUploader();
        this.worldRenderer = new WorldRenderer(n);
    }
    
    public int draw() {
        return this.field_178182_b.draw(this.worldRenderer, this.worldRenderer.draw());
    }
    
    public WorldRenderer getWorldRenderer() {
        return this.worldRenderer;
    }
}
