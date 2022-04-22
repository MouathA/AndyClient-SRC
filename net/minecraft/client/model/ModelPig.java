package net.minecraft.client.model;

public class ModelPig extends ModelQuadruped
{
    private static final String __OBFID;
    
    public ModelPig() {
        this(0.0f);
    }
    
    public ModelPig(final float n) {
        super(6, n);
        this.head.setTextureOffset(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4, 3, 1, n);
        this.childYOffset = 4.0f;
    }
    
    static {
        __OBFID = "CL_00000849";
    }
}
