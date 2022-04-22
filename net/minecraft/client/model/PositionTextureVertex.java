package net.minecraft.client.model;

import net.minecraft.util.*;

public class PositionTextureVertex
{
    public Vec3 vector3D;
    public float texturePositionX;
    public float texturePositionY;
    private static final String __OBFID;
    
    public PositionTextureVertex(final float n, final float n2, final float n3, final float n4, final float n5) {
        this(new Vec3(n, n2, n3), n4, n5);
    }
    
    public PositionTextureVertex setTexturePosition(final float n, final float n2) {
        return new PositionTextureVertex(this, n, n2);
    }
    
    public PositionTextureVertex(final PositionTextureVertex positionTextureVertex, final float texturePositionX, final float texturePositionY) {
        this.vector3D = positionTextureVertex.vector3D;
        this.texturePositionX = texturePositionX;
        this.texturePositionY = texturePositionY;
    }
    
    public PositionTextureVertex(final Vec3 vector3D, final float texturePositionX, final float texturePositionY) {
        this.vector3D = vector3D;
        this.texturePositionX = texturePositionX;
        this.texturePositionY = texturePositionY;
    }
    
    static {
        __OBFID = "CL_00000862";
    }
}
