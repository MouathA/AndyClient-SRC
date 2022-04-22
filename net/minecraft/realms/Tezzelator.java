package net.minecraft.realms;

import net.minecraft.client.renderer.*;

public class Tezzelator
{
    public static Tessellator t;
    public static final Tezzelator instance;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001855";
        Tezzelator.t = Tessellator.getInstance();
        instance = new Tezzelator();
    }
    
    public int end() {
        return Tezzelator.t.draw();
    }
    
    public void vertex(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().addVertex(n, n2, n3);
    }
    
    public void color(final float n, final float n2, final float n3, final float n4) {
        Tezzelator.t.getWorldRenderer().func_178960_a(n, n2, n3, n4);
    }
    
    public void color(final int n, final int n2, final int n3) {
        Tezzelator.t.getWorldRenderer().setPosition(n, n2, n3);
    }
    
    public void tex2(final int n) {
        Tezzelator.t.getWorldRenderer().func_178963_b(n);
    }
    
    public void normal(final float n, final float n2, final float n3) {
        Tezzelator.t.getWorldRenderer().func_178980_d(n, n2, n3);
    }
    
    public void noColor() {
        Tezzelator.t.getWorldRenderer().markDirty();
    }
    
    public void color(final int n) {
        Tezzelator.t.getWorldRenderer().func_178991_c(n);
    }
    
    public void color(final float n, final float n2, final float n3) {
        Tezzelator.t.getWorldRenderer().func_178986_b(n, n2, n3);
    }
    
    public WorldRenderer.State sortQuads(final float n, final float n2, final float n3) {
        return Tezzelator.t.getWorldRenderer().getVertexState(n, n2, n3);
    }
    
    public void restoreState(final WorldRenderer.State vertexState) {
        Tezzelator.t.getWorldRenderer().setVertexState(vertexState);
    }
    
    public void begin(final int n) {
        Tezzelator.t.getWorldRenderer().startDrawing(n);
    }
    
    public void begin() {
        Tezzelator.t.getWorldRenderer().startDrawingQuads();
    }
    
    public void vertexUV(final double n, final double n2, final double n3, final double n4, final double n5) {
        Tezzelator.t.getWorldRenderer().addVertexWithUV(n, n2, n3, n4, n5);
    }
    
    public void color(final int n, final int n2) {
        Tezzelator.t.getWorldRenderer().func_178974_a(n, n2);
    }
    
    public void offset(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().setTranslation(n, n2, n3);
    }
    
    public void color(final int n, final int n2, final int n3, final int n4) {
        Tezzelator.t.getWorldRenderer().func_178961_b(n, n2, n3, n4);
    }
    
    public void tex(final double n, final double n2) {
        Tezzelator.t.getWorldRenderer().setTextureUV(n, n2);
    }
    
    public void color(final byte b, final byte b2, final byte b3) {
        Tezzelator.t.getWorldRenderer().func_178982_a(b, b2, b3);
    }
}
