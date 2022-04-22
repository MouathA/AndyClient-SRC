package net.minecraft.client.model;

import net.minecraft.client.renderer.*;

public class ModelBox
{
    private PositionTextureVertex[] vertexPositions;
    private TexturedQuad[] quadList;
    public final float posX1;
    public final float posY1;
    public final float posZ1;
    public final float posX2;
    public final float posY2;
    public final float posZ2;
    public String field_78247_g;
    private static final String __OBFID;
    
    public ModelBox(final ModelRenderer modelRenderer, final int n, final int n2, final float n3, final float n4, final float n5, final int n6, final int n7, final int n8, final float n9) {
        this(modelRenderer, n, n2, n3, n4, n5, n6, n7, n8, n9, modelRenderer.mirror);
    }
    
    public ModelBox(final ModelRenderer modelRenderer, final int n, final int n2, float posX1, float posY1, float posZ1, final int n3, final int n4, final int n5, final float n6, final boolean b) {
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posZ1 = posZ1;
        this.posX2 = posX1 + n3;
        this.posY2 = posY1 + n4;
        this.posZ2 = posZ1 + n5;
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        final float n7 = posX1 + n3;
        final float n8 = posY1 + n4;
        final float n9 = posZ1 + n5;
        posX1 -= n6;
        posY1 -= n6;
        posZ1 -= n6;
        float n10 = n7 + n6;
        final float n11 = n8 + n6;
        final float n12 = n9 + n6;
        if (b) {
            final float n13 = n10;
            n10 = posX1;
            posX1 = n13;
        }
        final PositionTextureVertex positionTextureVertex = new PositionTextureVertex(posX1, posY1, posZ1, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex2 = new PositionTextureVertex(n10, posY1, posZ1, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex3 = new PositionTextureVertex(n10, n11, posZ1, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex4 = new PositionTextureVertex(posX1, n11, posZ1, 8.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex5 = new PositionTextureVertex(posX1, posY1, n12, 0.0f, 0.0f);
        final PositionTextureVertex positionTextureVertex6 = new PositionTextureVertex(n10, posY1, n12, 0.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex7 = new PositionTextureVertex(n10, n11, n12, 8.0f, 8.0f);
        final PositionTextureVertex positionTextureVertex8 = new PositionTextureVertex(posX1, n11, n12, 8.0f, 0.0f);
        this.vertexPositions[0] = positionTextureVertex;
        this.vertexPositions[1] = positionTextureVertex2;
        this.vertexPositions[2] = positionTextureVertex3;
        this.vertexPositions[3] = positionTextureVertex4;
        this.vertexPositions[4] = positionTextureVertex5;
        this.vertexPositions[5] = positionTextureVertex6;
        this.vertexPositions[6] = positionTextureVertex7;
        this.vertexPositions[7] = positionTextureVertex8;
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex6, positionTextureVertex2, positionTextureVertex3, positionTextureVertex7 }, n + n5 + n3, n2 + n5, n + n5 + n3 + n5, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex, positionTextureVertex5, positionTextureVertex8, positionTextureVertex4 }, n, n2 + n5, n + n5, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex6, positionTextureVertex5, positionTextureVertex, positionTextureVertex2 }, n + n5, n2, n + n5 + n3, n2 + n5, modelRenderer.textureWidth, modelRenderer.textureHeight);
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex3, positionTextureVertex4, positionTextureVertex8, positionTextureVertex7 }, n + n5 + n3, n2 + n5, n + n5 + n3 + n3, n2, modelRenderer.textureWidth, modelRenderer.textureHeight);
        this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex2, positionTextureVertex, positionTextureVertex4, positionTextureVertex3 }, n + n5, n2 + n5, n + n5 + n3, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { positionTextureVertex5, positionTextureVertex6, positionTextureVertex7, positionTextureVertex8 }, n + n5 + n3 + n5, n2 + n5, n + n5 + n3 + n5 + n3, n2 + n5 + n4, modelRenderer.textureWidth, modelRenderer.textureHeight);
        if (b) {
            while (0 < this.quadList.length) {
                this.quadList[0].flipFace();
                int n14 = 0;
                ++n14;
            }
        }
    }
    
    public void render(final WorldRenderer worldRenderer, final float n) {
        while (0 < this.quadList.length) {
            this.quadList[0].func_178765_a(worldRenderer, n);
            int n2 = 0;
            ++n2;
        }
    }
    
    public ModelBox func_78244_a(final String field_78247_g) {
        this.field_78247_g = field_78247_g;
        return this;
    }
    
    static {
        __OBFID = "CL_00000872";
    }
}
