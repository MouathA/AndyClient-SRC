package org.lwjgl.opengl;

import java.nio.*;

public final class ARBES2Compatibility
{
    public static final int GL_SHADER_COMPILER = 36346;
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static final int GL_MAX_VARYING_VECTORS = 36348;
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static final int GL_FIXED = 5132;
    public static final int GL_LOW_FLOAT = 36336;
    public static final int GL_MEDIUM_FLOAT = 36337;
    public static final int GL_HIGH_FLOAT = 36338;
    public static final int GL_LOW_INT = 36339;
    public static final int GL_MEDIUM_INT = 36340;
    public static final int GL_HIGH_INT = 36341;
    public static final int GL_RGB565 = 36194;
    
    private ARBES2Compatibility() {
    }
    
    public static void glReleaseShaderCompiler() {
    }
    
    public static void glShaderBinary(final IntBuffer intBuffer, final int n, final ByteBuffer byteBuffer) {
        GL41.glShaderBinary(intBuffer, n, byteBuffer);
    }
    
    public static void glGetShaderPrecisionFormat(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        GL41.glGetShaderPrecisionFormat(n, n2, intBuffer, intBuffer2);
    }
    
    public static void glDepthRangef(final float n, final float n2) {
        GL41.glDepthRangef(n, n2);
    }
    
    public static void glClearDepthf(final float n) {
        GL41.glClearDepthf(n);
    }
}
