package shadersmod.client;

import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;

public class ClippingHelperShadow extends ClippingHelper
{
    private static ClippingHelperShadow instance;
    float[] frustumTest;
    float[][] shadowClipPlanes;
    int shadowClipPlaneCount;
    float[] matInvMP;
    float[] vecIntersection;
    
    static {
        ClippingHelperShadow.instance = new ClippingHelperShadow();
    }
    
    public ClippingHelperShadow() {
        this.frustumTest = new float[6];
        this.shadowClipPlanes = new float[10][4];
        this.matInvMP = new float[16];
        this.vecIntersection = new float[4];
    }
    
    @Override
    public boolean isBoxInFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        while (0 < this.shadowClipPlaneCount) {
            final float[] array = this.shadowClipPlanes[0];
            if (this.dot4(array, n, n2, n3) <= 0.0 && this.dot4(array, n4, n2, n3) <= 0.0 && this.dot4(array, n, n5, n3) <= 0.0 && this.dot4(array, n4, n5, n3) <= 0.0 && this.dot4(array, n, n2, n6) <= 0.0 && this.dot4(array, n4, n2, n6) <= 0.0 && this.dot4(array, n, n5, n6) <= 0.0 && this.dot4(array, n4, n5, n6) <= 0.0) {
                return false;
            }
            int n7 = 0;
            ++n7;
        }
        return true;
    }
    
    private double dot4(final float[] array, final double n, final double n2, final double n3) {
        return array[0] * n + array[1] * n2 + array[2] * n3 + array[3];
    }
    
    private double dot3(final float[] array, final float[] array2) {
        return array[0] * (double)array2[0] + array[1] * (double)array2[1] + array[2] * (double)array2[2];
    }
    
    public static ClippingHelper getInstance() {
        ClippingHelperShadow.instance.init();
        return ClippingHelperShadow.instance;
    }
    
    private void normalizePlane(final float[] array) {
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
    
    private void normalize3(final float[] array) {
        float sqrt_float = MathHelper.sqrt_float(array[0] * array[0] + array[1] * array[1] + array[2] * array[2]);
        if (sqrt_float == 0.0f) {
            sqrt_float = 1.0f;
        }
        final int n = 0;
        array[n] /= sqrt_float;
        final int n2 = 1;
        array[n2] /= sqrt_float;
        final int n3 = 2;
        array[n3] /= sqrt_float;
    }
    
    private void assignPlane(final float[] array, final float n, final float n2, final float n3, final float n4) {
        final float n5 = (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        array[0] = n / n5;
        array[1] = n2 / n5;
        array[2] = n3 / n5;
        array[3] = n4 / n5;
    }
    
    private void copyPlane(final float[] array, final float[] array2) {
        array[0] = array2[0];
        array[1] = array2[1];
        array[2] = array2[2];
        array[3] = array2[3];
    }
    
    private void cross3(final float[] array, final float[] array2, final float[] array3) {
        array[0] = array2[1] * array3[2] - array2[2] * array3[1];
        array[1] = array2[2] * array3[0] - array2[0] * array3[2];
        array[2] = array2[0] * array3[1] - array2[1] * array3[0];
    }
    
    private void addShadowClipPlane(final float[] array) {
        this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], array);
    }
    
    private float length(final float n, final float n2, final float n3) {
        return (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
    }
    
    private float distance(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return this.length(n - n4, n2 - n5, n3 - n6);
    }
    
    private void makeShadowPlane(final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        this.cross3(this.vecIntersection, array2, array3);
        this.cross3(array, this.vecIntersection, array4);
        this.normalize3(array);
        final float n = (float)this.dot3(array2, array3);
        final float n2 = (float)this.dot3(array, array3);
        final float n3 = this.distance(array[0], array[1], array[2], array3[0] * n2, array3[1] * n2, array3[2] * n2) / this.distance(array2[0], array2[1], array2[2], array3[0] * n, array3[1] * n, array3[2] * n);
        final float n4 = (float)this.dot3(array, array2);
        array[3] = array2[3] * n3 + array3[3] * (this.distance(array[0], array[1], array[2], array2[0] * n4, array2[1] * n4, array2[2] * n4) / this.distance(array3[0], array3[1], array3[2], array2[0] * n, array2[1] * n, array2[2] * n));
    }
    
    public void init() {
        final float[] field_178625_b = this.field_178625_b;
        final float[] field_178626_c = this.field_178626_c;
        final float[] clippingMatrix = this.clippingMatrix;
        System.arraycopy(Shaders.faProjection, 0, field_178625_b, 0, 16);
        System.arraycopy(Shaders.faModelView, 0, field_178626_c, 0, 16);
        SMath.multiplyMat4xMat4(clippingMatrix, field_178626_c, field_178625_b);
        this.assignPlane(this.frustum[0], clippingMatrix[3] - clippingMatrix[0], clippingMatrix[7] - clippingMatrix[4], clippingMatrix[11] - clippingMatrix[8], clippingMatrix[15] - clippingMatrix[12]);
        this.assignPlane(this.frustum[1], clippingMatrix[3] + clippingMatrix[0], clippingMatrix[7] + clippingMatrix[4], clippingMatrix[11] + clippingMatrix[8], clippingMatrix[15] + clippingMatrix[12]);
        this.assignPlane(this.frustum[2], clippingMatrix[3] + clippingMatrix[1], clippingMatrix[7] + clippingMatrix[5], clippingMatrix[11] + clippingMatrix[9], clippingMatrix[15] + clippingMatrix[13]);
        this.assignPlane(this.frustum[3], clippingMatrix[3] - clippingMatrix[1], clippingMatrix[7] - clippingMatrix[5], clippingMatrix[11] - clippingMatrix[9], clippingMatrix[15] - clippingMatrix[13]);
        this.assignPlane(this.frustum[4], clippingMatrix[3] - clippingMatrix[2], clippingMatrix[7] - clippingMatrix[6], clippingMatrix[11] - clippingMatrix[10], clippingMatrix[15] - clippingMatrix[14]);
        this.assignPlane(this.frustum[5], clippingMatrix[3] + clippingMatrix[2], clippingMatrix[7] + clippingMatrix[6], clippingMatrix[11] + clippingMatrix[10], clippingMatrix[15] + clippingMatrix[14]);
        final float[] shadowLightPositionVector = Shaders.shadowLightPositionVector;
        final float n = (float)this.dot3(this.frustum[0], shadowLightPositionVector);
        final float n2 = (float)this.dot3(this.frustum[1], shadowLightPositionVector);
        final float n3 = (float)this.dot3(this.frustum[2], shadowLightPositionVector);
        final float n4 = (float)this.dot3(this.frustum[3], shadowLightPositionVector);
        final float n5 = (float)this.dot3(this.frustum[4], shadowLightPositionVector);
        final float n6 = (float)this.dot3(this.frustum[5], shadowLightPositionVector);
        this.shadowClipPlaneCount = 0;
        if (n >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
            if (n > 0.0f) {
                if (n3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], shadowLightPositionVector);
                }
                if (n4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], shadowLightPositionVector);
                }
                if (n5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], shadowLightPositionVector);
                }
                if (n6 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], shadowLightPositionVector);
                }
            }
        }
        if (n2 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
            if (n2 > 0.0f) {
                if (n3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], shadowLightPositionVector);
                }
                if (n4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], shadowLightPositionVector);
                }
                if (n5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], shadowLightPositionVector);
                }
                if (n6 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], shadowLightPositionVector);
                }
            }
        }
        if (n3 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
            if (n3 > 0.0f) {
                if (n < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], shadowLightPositionVector);
                }
                if (n2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], shadowLightPositionVector);
                }
                if (n5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], shadowLightPositionVector);
                }
                if (n6 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], shadowLightPositionVector);
                }
            }
        }
        if (n4 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
            if (n4 > 0.0f) {
                if (n < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], shadowLightPositionVector);
                }
                if (n2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], shadowLightPositionVector);
                }
                if (n5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], shadowLightPositionVector);
                }
                if (n6 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], shadowLightPositionVector);
                }
            }
        }
        if (n5 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
            if (n5 > 0.0f) {
                if (n < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], shadowLightPositionVector);
                }
                if (n2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], shadowLightPositionVector);
                }
                if (n3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], shadowLightPositionVector);
                }
                if (n4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], shadowLightPositionVector);
                }
            }
        }
        if (n6 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
            if (n6 > 0.0f) {
                if (n < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], shadowLightPositionVector);
                }
                if (n2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], shadowLightPositionVector);
                }
                if (n3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], shadowLightPositionVector);
                }
                if (n4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], shadowLightPositionVector);
                }
            }
        }
    }
}
