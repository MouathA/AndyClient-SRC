package net.minecraft.client.model;

import optifine.*;
import shadersmod.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class TexturedQuad
{
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    private static final String __OBFID;
    
    public TexturedQuad(final PositionTextureVertex[] vertexPositions) {
        this.vertexPositions = vertexPositions;
        this.nVertices = vertexPositions.length;
    }
    
    public TexturedQuad(final PositionTextureVertex[] array, final int n, final int n2, final int n3, final int n4, final float n5, final float n6) {
        this(array);
        final float n7 = 0.0f / n5;
        final float n8 = 0.0f / n6;
        array[0] = array[0].setTexturePosition(n3 / n5 - n7, n2 / n6 + n8);
        array[1] = array[1].setTexturePosition(n / n5 + n7, n2 / n6 + n8);
        array[2] = array[2].setTexturePosition(n / n5 + n7, n4 / n6 - n8);
        array[3] = array[3].setTexturePosition(n3 / n5 - n7, n4 / n6 - n8);
    }
    
    public void flipFace() {
        final PositionTextureVertex[] vertexPositions = new PositionTextureVertex[this.vertexPositions.length];
        while (0 < this.vertexPositions.length) {
            vertexPositions[0] = this.vertexPositions[this.vertexPositions.length - 0 - 1];
            int n = 0;
            ++n;
        }
        this.vertexPositions = vertexPositions;
    }
    
    public void func_178765_a(final WorldRenderer worldRenderer, final float n) {
        final Vec3 normalize = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D).crossProduct(this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D)).normalize();
        worldRenderer.startDrawingQuads();
        if (Config.isShaders()) {
            SVertexBuilder.startTexturedQuad(worldRenderer);
        }
        if (this.invertNormal) {
            worldRenderer.func_178980_d(-(float)normalize.xCoord, -(float)normalize.yCoord, -(float)normalize.zCoord);
        }
        else {
            worldRenderer.func_178980_d((float)normalize.xCoord, (float)normalize.yCoord, (float)normalize.zCoord);
        }
        while (0 < 4) {
            final PositionTextureVertex positionTextureVertex = this.vertexPositions[0];
            worldRenderer.addVertexWithUV(positionTextureVertex.vector3D.xCoord * n, positionTextureVertex.vector3D.yCoord * n, positionTextureVertex.vector3D.zCoord * n, positionTextureVertex.texturePositionX, positionTextureVertex.texturePositionY);
            int n2 = 0;
            ++n2;
        }
        Tessellator.getInstance().draw();
    }
    
    static {
        __OBFID = "CL_00000850";
    }
}
