package DTool.util;

import net.minecraft.client.renderer.culling.*;
import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ClippingHelperImpl extends ClippingHelper
{
    private static final ClippingHelperImpl instance;
    private final FloatBuffer projectionMatrixBuffer;
    private final FloatBuffer modelviewMatrixBuffer;
    public float[] projectionMatrix;
    public float[] modelviewMatrix;
    private final FloatBuffer field_78564_h;
    
    static {
        instance = new ClippingHelperImpl();
    }
    
    public ClippingHelperImpl() {
        this.projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.projectionMatrix = new float[16];
        this.modelviewMatrix = new float[16];
        this.field_78564_h = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public static ClippingHelper getInstance() {
        ClippingHelperImpl.instance.init();
        return ClippingHelperImpl.instance;
    }
    
    private void normalize(final float[] array) {
        final float sqrt_float = MathHelper.sqrt_float(array[0] * array[0] + array[1] * array[1] + array[2] * array[2]);
        array[0] /= sqrt_float;
        array[1] /= sqrt_float;
        array[2] /= sqrt_float;
        array[3] /= sqrt_float;
    }
    
    public void init() {
        this.projectionMatrixBuffer.clear();
        this.modelviewMatrixBuffer.clear();
        this.field_78564_h.clear();
        GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
        GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
        final float[] projectionMatrix = this.projectionMatrix;
        final float[] modelviewMatrix = this.modelviewMatrix;
        this.projectionMatrixBuffer.flip().limit(16);
        this.projectionMatrixBuffer.get(projectionMatrix);
        this.modelviewMatrixBuffer.flip().limit(16);
        this.modelviewMatrixBuffer.get(modelviewMatrix);
        this.clippingMatrix[0] = modelviewMatrix[0] * projectionMatrix[0] + modelviewMatrix[1] * projectionMatrix[4] + modelviewMatrix[2] * projectionMatrix[8] + modelviewMatrix[3] * projectionMatrix[12];
        this.clippingMatrix[1] = modelviewMatrix[0] * projectionMatrix[1] + modelviewMatrix[1] * projectionMatrix[5] + modelviewMatrix[2] * projectionMatrix[9] + modelviewMatrix[3] * projectionMatrix[13];
        this.clippingMatrix[2] = modelviewMatrix[0] * projectionMatrix[2] + modelviewMatrix[1] * projectionMatrix[6] + modelviewMatrix[2] * projectionMatrix[10] + modelviewMatrix[3] * projectionMatrix[14];
        this.clippingMatrix[3] = modelviewMatrix[0] * projectionMatrix[3] + modelviewMatrix[1] * projectionMatrix[7] + modelviewMatrix[2] * projectionMatrix[11] + modelviewMatrix[3] * projectionMatrix[15];
        this.clippingMatrix[4] = modelviewMatrix[4] * projectionMatrix[0] + modelviewMatrix[5] * projectionMatrix[4] + modelviewMatrix[6] * projectionMatrix[8] + modelviewMatrix[7] * projectionMatrix[12];
        this.clippingMatrix[5] = modelviewMatrix[4] * projectionMatrix[1] + modelviewMatrix[5] * projectionMatrix[5] + modelviewMatrix[6] * projectionMatrix[9] + modelviewMatrix[7] * projectionMatrix[13];
        this.clippingMatrix[6] = modelviewMatrix[4] * projectionMatrix[2] + modelviewMatrix[5] * projectionMatrix[6] + modelviewMatrix[6] * projectionMatrix[10] + modelviewMatrix[7] * projectionMatrix[14];
        this.clippingMatrix[7] = modelviewMatrix[4] * projectionMatrix[3] + modelviewMatrix[5] * projectionMatrix[7] + modelviewMatrix[6] * projectionMatrix[11] + modelviewMatrix[7] * projectionMatrix[15];
        this.clippingMatrix[8] = modelviewMatrix[8] * projectionMatrix[0] + modelviewMatrix[9] * projectionMatrix[4] + modelviewMatrix[10] * projectionMatrix[8] + modelviewMatrix[11] * projectionMatrix[12];
        this.clippingMatrix[9] = modelviewMatrix[8] * projectionMatrix[1] + modelviewMatrix[9] * projectionMatrix[5] + modelviewMatrix[10] * projectionMatrix[9] + modelviewMatrix[11] * projectionMatrix[13];
        this.clippingMatrix[10] = modelviewMatrix[8] * projectionMatrix[2] + modelviewMatrix[9] * projectionMatrix[6] + modelviewMatrix[10] * projectionMatrix[10] + modelviewMatrix[11] * projectionMatrix[14];
        this.clippingMatrix[11] = modelviewMatrix[8] * projectionMatrix[3] + modelviewMatrix[9] * projectionMatrix[7] + modelviewMatrix[10] * projectionMatrix[11] + modelviewMatrix[11] * projectionMatrix[15];
        this.clippingMatrix[12] = modelviewMatrix[12] * projectionMatrix[0] + modelviewMatrix[13] * projectionMatrix[4] + modelviewMatrix[14] * projectionMatrix[8] + modelviewMatrix[15] * projectionMatrix[12];
        this.clippingMatrix[13] = modelviewMatrix[12] * projectionMatrix[1] + modelviewMatrix[13] * projectionMatrix[5] + modelviewMatrix[14] * projectionMatrix[9] + modelviewMatrix[15] * projectionMatrix[13];
        this.clippingMatrix[14] = modelviewMatrix[12] * projectionMatrix[2] + modelviewMatrix[13] * projectionMatrix[6] + modelviewMatrix[14] * projectionMatrix[10] + modelviewMatrix[15] * projectionMatrix[14];
        this.clippingMatrix[15] = modelviewMatrix[12] * projectionMatrix[3] + modelviewMatrix[13] * projectionMatrix[7] + modelviewMatrix[14] * projectionMatrix[11] + modelviewMatrix[15] * projectionMatrix[15];
        final float[] array = this.frustum[0];
        array[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
        array[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
        array[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
        array[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
        this.normalize(array);
        final float[] array2 = this.frustum[1];
        array2[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
        array2[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
        array2[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
        array2[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
        this.normalize(array2);
        final float[] array3 = this.frustum[2];
        array3[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
        array3[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
        array3[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
        array3[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
        this.normalize(array3);
        final float[] array4 = this.frustum[3];
        array4[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
        array4[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
        array4[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
        array4[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
        this.normalize(array4);
        final float[] array5 = this.frustum[4];
        array5[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
        array5[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
        array5[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
        array5[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
        this.normalize(array5);
        final float[] array6 = this.frustum[5];
        array6[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
        array6[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
        array6[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
        array6[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
        this.normalize(array6);
    }
}
