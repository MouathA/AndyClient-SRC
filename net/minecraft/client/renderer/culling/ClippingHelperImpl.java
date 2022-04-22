package net.minecraft.client.renderer.culling;

import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ClippingHelperImpl extends ClippingHelper
{
    private static ClippingHelperImpl instance;
    private FloatBuffer projectionMatrixBuffer;
    private FloatBuffer modelviewMatrixBuffer;
    private FloatBuffer field_78564_h;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000975";
        ClippingHelperImpl.instance = new ClippingHelperImpl();
    }
    
    public ClippingHelperImpl() {
        this.projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.field_78564_h = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public static ClippingHelper getInstance() {
        ClippingHelperImpl.instance.init();
        return ClippingHelperImpl.instance;
    }
    
    private void func_180547_a(final float[] array) {
        final float sqrt_float = MathHelper.sqrt_float(array[0] * array[0] + array[1] * array[1] + array[2] * array[2]);
        final int n = 0;
        array[n] /= sqrt_float;
        final int n2 = 1;
        array[n2] /= sqrt_float;
        final int n3 = 2;
        array[n3] /= sqrt_float;
        final int n4 = 3;
        array[n4] /= sqrt_float;
    }
    
    public void init() {
        this.projectionMatrixBuffer.clear();
        this.modelviewMatrixBuffer.clear();
        this.field_78564_h.clear();
        GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
        GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
        final float[] field_178625_b = this.field_178625_b;
        final float[] field_178626_c = this.field_178626_c;
        this.projectionMatrixBuffer.flip().limit(16);
        this.projectionMatrixBuffer.get(field_178625_b);
        this.modelviewMatrixBuffer.flip().limit(16);
        this.modelviewMatrixBuffer.get(field_178626_c);
        this.clippingMatrix[0] = field_178626_c[0] * field_178625_b[0] + field_178626_c[1] * field_178625_b[4] + field_178626_c[2] * field_178625_b[8] + field_178626_c[3] * field_178625_b[12];
        this.clippingMatrix[1] = field_178626_c[0] * field_178625_b[1] + field_178626_c[1] * field_178625_b[5] + field_178626_c[2] * field_178625_b[9] + field_178626_c[3] * field_178625_b[13];
        this.clippingMatrix[2] = field_178626_c[0] * field_178625_b[2] + field_178626_c[1] * field_178625_b[6] + field_178626_c[2] * field_178625_b[10] + field_178626_c[3] * field_178625_b[14];
        this.clippingMatrix[3] = field_178626_c[0] * field_178625_b[3] + field_178626_c[1] * field_178625_b[7] + field_178626_c[2] * field_178625_b[11] + field_178626_c[3] * field_178625_b[15];
        this.clippingMatrix[4] = field_178626_c[4] * field_178625_b[0] + field_178626_c[5] * field_178625_b[4] + field_178626_c[6] * field_178625_b[8] + field_178626_c[7] * field_178625_b[12];
        this.clippingMatrix[5] = field_178626_c[4] * field_178625_b[1] + field_178626_c[5] * field_178625_b[5] + field_178626_c[6] * field_178625_b[9] + field_178626_c[7] * field_178625_b[13];
        this.clippingMatrix[6] = field_178626_c[4] * field_178625_b[2] + field_178626_c[5] * field_178625_b[6] + field_178626_c[6] * field_178625_b[10] + field_178626_c[7] * field_178625_b[14];
        this.clippingMatrix[7] = field_178626_c[4] * field_178625_b[3] + field_178626_c[5] * field_178625_b[7] + field_178626_c[6] * field_178625_b[11] + field_178626_c[7] * field_178625_b[15];
        this.clippingMatrix[8] = field_178626_c[8] * field_178625_b[0] + field_178626_c[9] * field_178625_b[4] + field_178626_c[10] * field_178625_b[8] + field_178626_c[11] * field_178625_b[12];
        this.clippingMatrix[9] = field_178626_c[8] * field_178625_b[1] + field_178626_c[9] * field_178625_b[5] + field_178626_c[10] * field_178625_b[9] + field_178626_c[11] * field_178625_b[13];
        this.clippingMatrix[10] = field_178626_c[8] * field_178625_b[2] + field_178626_c[9] * field_178625_b[6] + field_178626_c[10] * field_178625_b[10] + field_178626_c[11] * field_178625_b[14];
        this.clippingMatrix[11] = field_178626_c[8] * field_178625_b[3] + field_178626_c[9] * field_178625_b[7] + field_178626_c[10] * field_178625_b[11] + field_178626_c[11] * field_178625_b[15];
        this.clippingMatrix[12] = field_178626_c[12] * field_178625_b[0] + field_178626_c[13] * field_178625_b[4] + field_178626_c[14] * field_178625_b[8] + field_178626_c[15] * field_178625_b[12];
        this.clippingMatrix[13] = field_178626_c[12] * field_178625_b[1] + field_178626_c[13] * field_178625_b[5] + field_178626_c[14] * field_178625_b[9] + field_178626_c[15] * field_178625_b[13];
        this.clippingMatrix[14] = field_178626_c[12] * field_178625_b[2] + field_178626_c[13] * field_178625_b[6] + field_178626_c[14] * field_178625_b[10] + field_178626_c[15] * field_178625_b[14];
        this.clippingMatrix[15] = field_178626_c[12] * field_178625_b[3] + field_178626_c[13] * field_178625_b[7] + field_178626_c[14] * field_178625_b[11] + field_178626_c[15] * field_178625_b[15];
        final float[] array = this.frustum[0];
        array[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
        array[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
        array[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
        array[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
        this.func_180547_a(array);
        final float[] array2 = this.frustum[1];
        array2[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
        array2[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
        array2[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
        array2[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
        this.func_180547_a(array2);
        final float[] array3 = this.frustum[2];
        array3[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
        array3[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
        array3[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
        array3[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
        this.func_180547_a(array3);
        final float[] array4 = this.frustum[3];
        array4[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
        array4[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
        array4[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
        array4[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
        this.func_180547_a(array4);
        final float[] array5 = this.frustum[4];
        array5[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
        array5[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
        array5[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
        array5[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
        this.func_180547_a(array5);
        final float[] array6 = this.frustum[5];
        array6[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
        array6[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
        array6[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
        array6[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
        this.func_180547_a(array6);
    }
}
