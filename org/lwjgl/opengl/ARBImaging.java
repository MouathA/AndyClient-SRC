package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class ARBImaging
{
    public static final int GL_BLEND_COLOR = 32773;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX = 32776;
    public static final int GL_BLEND_EQUATION = 32777;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static final int GL_COLOR_MATRIX = 32945;
    public static final int GL_COLOR_MATRIX_STACK_DEPTH = 32946;
    public static final int GL_MAX_COLOR_MATRIX_STACK_DEPTH = 32947;
    public static final int GL_POST_COLOR_MATRIX_RED_SCALE = 32948;
    public static final int GL_POST_COLOR_MATRIX_GREEN_SCALE = 32949;
    public static final int GL_POST_COLOR_MATRIX_BLUE_SCALE = 32950;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_SCALE = 32951;
    public static final int GL_POST_COLOR_MATRIX_RED_BIAS = 32952;
    public static final int GL_POST_COLOR_MATRIX_GREEN_BIAS = 32953;
    public static final int GL_POST_COLOR_MATRIX_BLUE_BIAS = 32954;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_BIAS = 32955;
    public static final int GL_COLOR_TABLE = 32976;
    public static final int GL_POST_CONVOLUTION_COLOR_TABLE = 32977;
    public static final int GL_POST_COLOR_MATRIX_COLOR_TABLE = 32978;
    public static final int GL_PROXY_COLOR_TABLE = 32979;
    public static final int GL_PROXY_POST_CONVOLUTION_COLOR_TABLE = 32980;
    public static final int GL_PROXY_POST_COLOR_MATRIX_COLOR_TABLE = 32981;
    public static final int GL_COLOR_TABLE_SCALE = 32982;
    public static final int GL_COLOR_TABLE_BIAS = 32983;
    public static final int GL_COLOR_TABLE_FORMAT = 32984;
    public static final int GL_COLOR_TABLE_WIDTH = 32985;
    public static final int GL_COLOR_TABLE_RED_SIZE = 32986;
    public static final int GL_COLOR_TABLE_GREEN_SIZE = 32987;
    public static final int GL_COLOR_TABLE_BLUE_SIZE = 32988;
    public static final int GL_COLOR_TABLE_ALPHA_SIZE = 32989;
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE = 32990;
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE = 32991;
    public static final int GL_CONVOLUTION_1D = 32784;
    public static final int GL_CONVOLUTION_2D = 32785;
    public static final int GL_SEPARABLE_2D = 32786;
    public static final int GL_CONVOLUTION_BORDER_MODE = 32787;
    public static final int GL_CONVOLUTION_FILTER_SCALE = 32788;
    public static final int GL_CONVOLUTION_FILTER_BIAS = 32789;
    public static final int GL_REDUCE = 32790;
    public static final int GL_CONVOLUTION_FORMAT = 32791;
    public static final int GL_CONVOLUTION_WIDTH = 32792;
    public static final int GL_CONVOLUTION_HEIGHT = 32793;
    public static final int GL_MAX_CONVOLUTION_WIDTH = 32794;
    public static final int GL_MAX_CONVOLUTION_HEIGHT = 32795;
    public static final int GL_POST_CONVOLUTION_RED_SCALE = 32796;
    public static final int GL_POST_CONVOLUTION_GREEN_SCALE = 32797;
    public static final int GL_POST_CONVOLUTION_BLUE_SCALE = 32798;
    public static final int GL_POST_CONVOLUTION_ALPHA_SCALE = 32799;
    public static final int GL_POST_CONVOLUTION_RED_BIAS = 32800;
    public static final int GL_POST_CONVOLUTION_GREEN_BIAS = 32801;
    public static final int GL_POST_CONVOLUTION_BLUE_BIAS = 32802;
    public static final int GL_POST_CONVOLUTION_ALPHA_BIAS = 32803;
    public static final int GL_IGNORE_BORDER = 33104;
    public static final int GL_CONSTANT_BORDER = 33105;
    public static final int GL_REPLICATE_BORDER = 33107;
    public static final int GL_CONVOLUTION_BORDER_COLOR = 33108;
    public static final int GL_HISTOGRAM = 32804;
    public static final int GL_PROXY_HISTOGRAM = 32805;
    public static final int GL_HISTOGRAM_WIDTH = 32806;
    public static final int GL_HISTOGRAM_FORMAT = 32807;
    public static final int GL_HISTOGRAM_RED_SIZE = 32808;
    public static final int GL_HISTOGRAM_GREEN_SIZE = 32809;
    public static final int GL_HISTOGRAM_BLUE_SIZE = 32810;
    public static final int GL_HISTOGRAM_ALPHA_SIZE = 32811;
    public static final int GL_HISTOGRAM_LUMINANCE_SIZE = 32812;
    public static final int GL_HISTOGRAM_SINK = 32813;
    public static final int GL_MINMAX = 32814;
    public static final int GL_MINMAX_FORMAT = 32815;
    public static final int GL_MINMAX_SINK = 32816;
    public static final int GL_TABLE_TOO_LARGE = 32817;
    
    private ARBImaging() {
    }
    
    public static void glColorTable(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorTable = capabilities.glColorTable;
        BufferChecks.checkFunctionAddress(glColorTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 256);
        nglColorTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glColorTable);
    }
    
    public static void glColorTable(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorTable = capabilities.glColorTable;
        BufferChecks.checkFunctionAddress(glColorTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, 256);
        nglColorTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glColorTable);
    }
    
    public static void glColorTable(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorTable = capabilities.glColorTable;
        BufferChecks.checkFunctionAddress(glColorTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglColorTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glColorTable);
    }
    
    static native void nglColorTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glColorTable(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorTable = capabilities.glColorTable;
        BufferChecks.checkFunctionAddress(glColorTable);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglColorTableBO(n, n2, n3, n4, n5, n6, glColorTable);
    }
    
    static native void nglColorTableBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glColorSubTable(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorSubTable = capabilities.glColorSubTable;
        BufferChecks.checkFunctionAddress(glColorSubTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 256);
        nglColorSubTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glColorSubTable);
    }
    
    public static void glColorSubTable(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorSubTable = capabilities.glColorSubTable;
        BufferChecks.checkFunctionAddress(glColorSubTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, 256);
        nglColorSubTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glColorSubTable);
    }
    
    public static void glColorSubTable(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorSubTable = capabilities.glColorSubTable;
        BufferChecks.checkFunctionAddress(glColorSubTable);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglColorSubTable(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glColorSubTable);
    }
    
    static native void nglColorSubTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glColorSubTable(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glColorSubTable = capabilities.glColorSubTable;
        BufferChecks.checkFunctionAddress(glColorSubTable);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglColorSubTableBO(n, n2, n3, n4, n5, n6, glColorSubTable);
    }
    
    static native void nglColorSubTableBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glColorTableParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glColorTableParameteriv = GLContext.getCapabilities().glColorTableParameteriv;
        BufferChecks.checkFunctionAddress(glColorTableParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglColorTableParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glColorTableParameteriv);
    }
    
    static native void nglColorTableParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glColorTableParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glColorTableParameterfv = GLContext.getCapabilities().glColorTableParameterfv;
        BufferChecks.checkFunctionAddress(glColorTableParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglColorTableParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glColorTableParameterfv);
    }
    
    static native void nglColorTableParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glCopyColorSubTable(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glCopyColorSubTable = GLContext.getCapabilities().glCopyColorSubTable;
        BufferChecks.checkFunctionAddress(glCopyColorSubTable);
        nglCopyColorSubTable(n, n2, n3, n4, n5, glCopyColorSubTable);
    }
    
    static native void nglCopyColorSubTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glCopyColorTable(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glCopyColorTable = GLContext.getCapabilities().glCopyColorTable;
        BufferChecks.checkFunctionAddress(glCopyColorTable);
        nglCopyColorTable(n, n2, n3, n4, n5, glCopyColorTable);
    }
    
    static native void nglCopyColorTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glGetColorTable(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glGetColorTable = GLContext.getCapabilities().glGetColorTable;
        BufferChecks.checkFunctionAddress(glGetColorTable);
        BufferChecks.checkBuffer(byteBuffer, 256);
        nglGetColorTable(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetColorTable);
    }
    
    public static void glGetColorTable(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final long glGetColorTable = GLContext.getCapabilities().glGetColorTable;
        BufferChecks.checkFunctionAddress(glGetColorTable);
        BufferChecks.checkBuffer(doubleBuffer, 256);
        nglGetColorTable(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetColorTable);
    }
    
    public static void glGetColorTable(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final long glGetColorTable = GLContext.getCapabilities().glGetColorTable;
        BufferChecks.checkFunctionAddress(glGetColorTable);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglGetColorTable(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetColorTable);
    }
    
    static native void nglGetColorTable(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetColorTableParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetColorTableParameteriv = GLContext.getCapabilities().glGetColorTableParameteriv;
        BufferChecks.checkFunctionAddress(glGetColorTableParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetColorTableParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetColorTableParameteriv);
    }
    
    static native void nglGetColorTableParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetColorTableParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetColorTableParameterfv = GLContext.getCapabilities().glGetColorTableParameterfv;
        BufferChecks.checkFunctionAddress(glGetColorTableParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetColorTableParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetColorTableParameterfv);
    }
    
    static native void nglGetColorTableParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBlendEquation(final int n) {
        GL14.glBlendEquation(n);
    }
    
    public static void glBlendColor(final float n, final float n2, final float n3, final float n4) {
        GL14.glBlendColor(n, n2, n3, n4);
    }
    
    public static void glHistogram(final int n, final int n2, final int n3, final boolean b) {
        final long glHistogram = GLContext.getCapabilities().glHistogram;
        BufferChecks.checkFunctionAddress(glHistogram);
        nglHistogram(n, n2, n3, b, glHistogram);
    }
    
    static native void nglHistogram(final int p0, final int p1, final int p2, final boolean p3, final long p4);
    
    public static void glResetHistogram(final int n) {
        final long glResetHistogram = GLContext.getCapabilities().glResetHistogram;
        BufferChecks.checkFunctionAddress(glResetHistogram);
        nglResetHistogram(n, glResetHistogram);
    }
    
    static native void nglResetHistogram(final int p0, final long p1);
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 256);
        nglGetHistogram(n, b, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetHistogram);
    }
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, 256);
        nglGetHistogram(n, b, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetHistogram);
    }
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglGetHistogram(n, b, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetHistogram);
    }
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, 256);
        nglGetHistogram(n, b, n2, n3, MemoryUtil.getAddress(intBuffer), glGetHistogram);
    }
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, 256);
        nglGetHistogram(n, b, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetHistogram);
    }
    
    static native void nglGetHistogram(final int p0, final boolean p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetHistogram(final int n, final boolean b, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetHistogram = capabilities.glGetHistogram;
        BufferChecks.checkFunctionAddress(glGetHistogram);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetHistogramBO(n, b, n2, n3, n4, glGetHistogram);
    }
    
    static native void nglGetHistogramBO(final int p0, final boolean p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetHistogramParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetHistogramParameterfv = GLContext.getCapabilities().glGetHistogramParameterfv;
        BufferChecks.checkFunctionAddress(glGetHistogramParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 256);
        nglGetHistogramParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetHistogramParameterfv);
    }
    
    static native void nglGetHistogramParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetHistogramParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetHistogramParameteriv = GLContext.getCapabilities().glGetHistogramParameteriv;
        BufferChecks.checkFunctionAddress(glGetHistogramParameteriv);
        BufferChecks.checkBuffer(intBuffer, 256);
        nglGetHistogramParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetHistogramParameteriv);
    }
    
    static native void nglGetHistogramParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glMinmax(final int n, final int n2, final boolean b) {
        final long glMinmax = GLContext.getCapabilities().glMinmax;
        BufferChecks.checkFunctionAddress(glMinmax);
        nglMinmax(n, n2, b, glMinmax);
    }
    
    static native void nglMinmax(final int p0, final int p1, final boolean p2, final long p3);
    
    public static void glResetMinmax(final int n) {
        final long glResetMinmax = GLContext.getCapabilities().glResetMinmax;
        BufferChecks.checkFunctionAddress(glResetMinmax);
        nglResetMinmax(n, glResetMinmax);
    }
    
    static native void nglResetMinmax(final int p0, final long p1);
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetMinmax(n, b, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetMinmax);
    }
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, 4);
        nglGetMinmax(n, b, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetMinmax);
    }
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMinmax(n, b, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetMinmax);
    }
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMinmax(n, b, n2, n3, MemoryUtil.getAddress(intBuffer), glGetMinmax);
    }
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglGetMinmax(n, b, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetMinmax);
    }
    
    static native void nglGetMinmax(final int p0, final boolean p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetMinmax(final int n, final boolean b, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetMinmax = capabilities.glGetMinmax;
        BufferChecks.checkFunctionAddress(glGetMinmax);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetMinmaxBO(n, b, n2, n3, n4, glGetMinmax);
    }
    
    static native void nglGetMinmaxBO(final int p0, final boolean p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetMinmaxParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetMinmaxParameterfv = GLContext.getCapabilities().glGetMinmaxParameterfv;
        BufferChecks.checkFunctionAddress(glGetMinmaxParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetMinmaxParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetMinmaxParameterfv);
    }
    
    static native void nglGetMinmaxParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetMinmaxParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetMinmaxParameteriv = GLContext.getCapabilities().glGetMinmaxParameteriv;
        BufferChecks.checkFunctionAddress(glGetMinmaxParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetMinmaxParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetMinmaxParameteriv);
    }
    
    static native void nglGetMinmaxParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n4, n5, n3, 1, 1));
        nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), glConvolutionFilter1D);
    }
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(doubleBuffer, GLChecks.calculateImageStorage(doubleBuffer, n4, n5, n3, 1, 1));
        nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), glConvolutionFilter1D);
    }
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(floatBuffer, GLChecks.calculateImageStorage(floatBuffer, n4, n5, n3, 1, 1));
        nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), glConvolutionFilter1D);
    }
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n4, n5, n3, 1, 1));
        nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), glConvolutionFilter1D);
    }
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n4, n5, n3, 1, 1));
        nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), glConvolutionFilter1D);
    }
    
    static native void nglConvolutionFilter1D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter1D = capabilities.glConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter1D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglConvolutionFilter1DBO(n, n2, n3, n4, n5, n6, glConvolutionFilter1D);
    }
    
    static native void nglConvolutionFilter1DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glConvolutionFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter2D = capabilities.glConvolutionFilter2D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, GLChecks.calculateImageStorage(byteBuffer, n5, n6, n3, n4, 1));
        nglConvolutionFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glConvolutionFilter2D);
    }
    
    public static void glConvolutionFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter2D = capabilities.glConvolutionFilter2D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, GLChecks.calculateImageStorage(intBuffer, n5, n6, n3, n4, 1));
        nglConvolutionFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), glConvolutionFilter2D);
    }
    
    public static void glConvolutionFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter2D = capabilities.glConvolutionFilter2D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkBuffer(shortBuffer, GLChecks.calculateImageStorage(shortBuffer, n5, n6, n3, n4, 1));
        nglConvolutionFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), glConvolutionFilter2D);
    }
    
    static native void nglConvolutionFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glConvolutionFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glConvolutionFilter2D = capabilities.glConvolutionFilter2D;
        BufferChecks.checkFunctionAddress(glConvolutionFilter2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglConvolutionFilter2DBO(n, n2, n3, n4, n5, n6, n7, glConvolutionFilter2D);
    }
    
    static native void nglConvolutionFilter2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glConvolutionParameterf(final int n, final int n2, final float n3) {
        final long glConvolutionParameterf = GLContext.getCapabilities().glConvolutionParameterf;
        BufferChecks.checkFunctionAddress(glConvolutionParameterf);
        nglConvolutionParameterf(n, n2, n3, glConvolutionParameterf);
    }
    
    static native void nglConvolutionParameterf(final int p0, final int p1, final float p2, final long p3);
    
    public static void glConvolutionParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glConvolutionParameterfv = GLContext.getCapabilities().glConvolutionParameterfv;
        BufferChecks.checkFunctionAddress(glConvolutionParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglConvolutionParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glConvolutionParameterfv);
    }
    
    static native void nglConvolutionParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glConvolutionParameteri(final int n, final int n2, final int n3) {
        final long glConvolutionParameteri = GLContext.getCapabilities().glConvolutionParameteri;
        BufferChecks.checkFunctionAddress(glConvolutionParameteri);
        nglConvolutionParameteri(n, n2, n3, glConvolutionParameteri);
    }
    
    static native void nglConvolutionParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glConvolutionParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glConvolutionParameteriv = GLContext.getCapabilities().glConvolutionParameteriv;
        BufferChecks.checkFunctionAddress(glConvolutionParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglConvolutionParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glConvolutionParameteriv);
    }
    
    static native void nglConvolutionParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glCopyConvolutionFilter1D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glCopyConvolutionFilter1D = GLContext.getCapabilities().glCopyConvolutionFilter1D;
        BufferChecks.checkFunctionAddress(glCopyConvolutionFilter1D);
        nglCopyConvolutionFilter1D(n, n2, n3, n4, n5, glCopyConvolutionFilter1D);
    }
    
    static native void nglCopyConvolutionFilter1D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glCopyConvolutionFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glCopyConvolutionFilter2D = GLContext.getCapabilities().glCopyConvolutionFilter2D;
        BufferChecks.checkFunctionAddress(glCopyConvolutionFilter2D);
        nglCopyConvolutionFilter2D(n, n2, n3, n4, n5, n6, glCopyConvolutionFilter2D);
    }
    
    static native void nglCopyConvolutionFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        nglGetConvolutionFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetConvolutionFilter);
    }
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetConvolutionFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), glGetConvolutionFilter);
    }
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        nglGetConvolutionFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), glGetConvolutionFilter);
    }
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        nglGetConvolutionFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetConvolutionFilter);
    }
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        nglGetConvolutionFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), glGetConvolutionFilter);
    }
    
    static native void nglGetConvolutionFilter(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetConvolutionFilter(final int n, final int n2, final int n3, final long n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetConvolutionFilter = capabilities.glGetConvolutionFilter;
        BufferChecks.checkFunctionAddress(glGetConvolutionFilter);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetConvolutionFilterBO(n, n2, n3, n4, glGetConvolutionFilter);
    }
    
    static native void nglGetConvolutionFilterBO(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGetConvolutionParameter(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glGetConvolutionParameterfv = GLContext.getCapabilities().glGetConvolutionParameterfv;
        BufferChecks.checkFunctionAddress(glGetConvolutionParameterfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglGetConvolutionParameterfv(n, n2, MemoryUtil.getAddress(floatBuffer), glGetConvolutionParameterfv);
    }
    
    static native void nglGetConvolutionParameterfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetConvolutionParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetConvolutionParameteriv = GLContext.getCapabilities().glGetConvolutionParameteriv;
        BufferChecks.checkFunctionAddress(glGetConvolutionParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetConvolutionParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetConvolutionParameteriv);
    }
    
    static native void nglGetConvolutionParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(floatBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(floatBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(floatBuffer2);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(floatBuffer2), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(floatBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer, final FloatBuffer floatBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(floatBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(floatBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glSeparableFilter2D);
    }
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glSeparableFilter2D);
    }
    
    static native void nglSeparableFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7, final long p8);
    
    public static void glSeparableFilter2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7, final long n8) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSeparableFilter2D = capabilities.glSeparableFilter2D;
        BufferChecks.checkFunctionAddress(glSeparableFilter2D);
        GLChecks.ensureUnpackPBOenabled(capabilities);
        nglSeparableFilter2DBO(n, n2, n3, n4, n5, n6, n7, n8, glSeparableFilter2D);
    }
    
    static native void nglSeparableFilter2DBO(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7, final long p8);
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(byteBuffer3);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(byteBuffer3), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final DoubleBuffer doubleBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(doubleBuffer3);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(doubleBuffer3), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final FloatBuffer floatBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(floatBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(intBuffer3);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        BufferChecks.checkDirect(shortBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(shortBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(byteBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final DoubleBuffer doubleBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(doubleBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(doubleBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final DoubleBuffer doubleBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(intBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final IntBuffer intBuffer, final ShortBuffer shortBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(intBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(shortBuffer2), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(byteBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(byteBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final DoubleBuffer doubleBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(doubleBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(doubleBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(intBuffer);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(intBuffer), glGetSeparableFilter);
    }
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ShortBuffer shortBuffer3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        BufferChecks.checkDirect(shortBuffer2);
        BufferChecks.checkDirect(shortBuffer3);
        nglGetSeparableFilter(n, n2, n3, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddress(shortBuffer2), MemoryUtil.getAddress(shortBuffer3), glGetSeparableFilter);
    }
    
    static native void nglGetSeparableFilter(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static void glGetSeparableFilter(final int n, final int n2, final int n3, final long n4, final long n5, final long n6) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetSeparableFilter = capabilities.glGetSeparableFilter;
        BufferChecks.checkFunctionAddress(glGetSeparableFilter);
        GLChecks.ensurePackPBOenabled(capabilities);
        nglGetSeparableFilterBO(n, n2, n3, n4, n5, n6, glGetSeparableFilter);
    }
    
    static native void nglGetSeparableFilterBO(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6);
}
