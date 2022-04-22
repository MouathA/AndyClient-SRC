package net.minecraft.client.renderer.culling;

public class ClippingHelper
{
    public float[][] frustum;
    public float[] field_178625_b;
    public float[] field_178626_c;
    public float[] clippingMatrix;
    private static final String __OBFID;
    
    public ClippingHelper() {
        this.frustum = new float[6][4];
        this.field_178625_b = new float[16];
        this.field_178626_c = new float[16];
        this.clippingMatrix = new float[16];
    }
    
    private float dot(final float[] array, final float n, final float n2, final float n3) {
        return array[0] * n + array[1] * n2 + array[2] * n3 + array[3];
    }
    
    public boolean isBoxInFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final float n7 = (float)n;
        final float n8 = (float)n2;
        final float n9 = (float)n3;
        final float n10 = (float)n4;
        final float n11 = (float)n5;
        final float n12 = (float)n6;
        while (0 < 6) {
            final float[] array = this.frustum[0];
            if (this.dot(array, n7, n8, n9) <= 0.0f && this.dot(array, n10, n8, n9) <= 0.0f && this.dot(array, n7, n11, n9) <= 0.0f && this.dot(array, n10, n11, n9) <= 0.0f && this.dot(array, n7, n8, n12) <= 0.0f && this.dot(array, n10, n8, n12) <= 0.0f && this.dot(array, n7, n11, n12) <= 0.0f && this.dot(array, n10, n11, n12) <= 0.0f) {
                return false;
            }
            int n13 = 0;
            ++n13;
        }
        return true;
    }
    
    static {
        __OBFID = "CL_00000977";
    }
}
