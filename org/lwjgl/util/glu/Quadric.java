package org.lwjgl.util.glu;

import org.lwjgl.opengl.*;

public class Quadric
{
    protected int drawStyle;
    protected int orientation;
    protected boolean textureFlag;
    protected int normals;
    
    public Quadric() {
        this.drawStyle = 100012;
        this.orientation = 100020;
        this.textureFlag = false;
        this.normals = 100000;
    }
    
    protected void normal3f(float n, float n2, float n3) {
        final float n4 = (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        if (n4 > 1.0E-5f) {
            n /= n4;
            n2 /= n4;
            n3 /= n4;
        }
        GL11.glNormal3f(n, n2, n3);
    }
    
    public void setDrawStyle(final int drawStyle) {
        this.drawStyle = drawStyle;
    }
    
    public void setNormals(final int normals) {
        this.normals = normals;
    }
    
    public void setOrientation(final int orientation) {
        this.orientation = orientation;
    }
    
    public void setTextureFlag(final boolean textureFlag) {
        this.textureFlag = textureFlag;
    }
    
    public int getDrawStyle() {
        return this.drawStyle;
    }
    
    public int getNormals() {
        return this.normals;
    }
    
    public int getOrientation() {
        return this.orientation;
    }
    
    public boolean getTextureFlag() {
        return this.textureFlag;
    }
    
    protected void TXTR_COORD(final float n, final float n2) {
        if (this.textureFlag) {
            GL11.glTexCoord2f(n, n2);
        }
    }
    
    protected float sin(final float n) {
        return (float)Math.sin(n);
    }
    
    protected float cos(final float n) {
        return (float)Math.cos(n);
    }
}
