package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL30
{
    public static final int GL_MAJOR_VERSION = 33307;
    public static final int GL_MINOR_VERSION = 33308;
    public static final int GL_NUM_EXTENSIONS = 33309;
    public static final int GL_CONTEXT_FLAGS = 33310;
    public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
    public static final int GL_DEPTH_BUFFER = 33315;
    public static final int GL_STENCIL_BUFFER = 33316;
    public static final int GL_COMPRESSED_RED = 33317;
    public static final int GL_COMPRESSED_RG = 33318;
    public static final int GL_COMPARE_REF_TO_TEXTURE = 34894;
    public static final int GL_CLIP_DISTANCE0 = 12288;
    public static final int GL_CLIP_DISTANCE1 = 12289;
    public static final int GL_CLIP_DISTANCE2 = 12290;
    public static final int GL_CLIP_DISTANCE3 = 12291;
    public static final int GL_CLIP_DISTANCE4 = 12292;
    public static final int GL_CLIP_DISTANCE5 = 12293;
    public static final int GL_CLIP_DISTANCE6 = 12294;
    public static final int GL_CLIP_DISTANCE7 = 12295;
    public static final int GL_MAX_CLIP_DISTANCES = 3378;
    public static final int GL_MAX_VARYING_COMPONENTS = 35659;
    public static final int GL_BUFFER_ACCESS_FLAGS = 37151;
    public static final int GL_BUFFER_MAP_LENGTH = 37152;
    public static final int GL_BUFFER_MAP_OFFSET = 37153;
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
    public static final int GL_SAMPLER_BUFFER = 36290;
    public static final int GL_SAMPLER_CUBE_SHADOW = 36293;
    public static final int GL_UNSIGNED_INT_VEC2 = 36294;
    public static final int GL_UNSIGNED_INT_VEC3 = 36295;
    public static final int GL_UNSIGNED_INT_VEC4 = 36296;
    public static final int GL_INT_SAMPLER_1D = 36297;
    public static final int GL_INT_SAMPLER_2D = 36298;
    public static final int GL_INT_SAMPLER_3D = 36299;
    public static final int GL_INT_SAMPLER_CUBE = 36300;
    public static final int GL_INT_SAMPLER_2D_RECT = 36301;
    public static final int GL_INT_SAMPLER_1D_ARRAY = 36302;
    public static final int GL_INT_SAMPLER_2D_ARRAY = 36303;
    public static final int GL_INT_SAMPLER_BUFFER = 36304;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
    public static final int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT = 36309;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER = 36312;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
    public static final int GL_QUERY_WAIT = 36371;
    public static final int GL_QUERY_NO_WAIT = 36372;
    public static final int GL_QUERY_BY_REGION_WAIT = 36373;
    public static final int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    public static final int GL_CLAMP_VERTEX_COLOR = 35098;
    public static final int GL_CLAMP_FRAGMENT_COLOR = 35099;
    public static final int GL_CLAMP_READ_COLOR = 35100;
    public static final int GL_FIXED_ONLY = 35101;
    public static final int GL_DEPTH_COMPONENT32F = 36012;
    public static final int GL_DEPTH32F_STENCIL8 = 36013;
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
    public static final int GL_TEXTURE_RED_TYPE = 35856;
    public static final int GL_TEXTURE_GREEN_TYPE = 35857;
    public static final int GL_TEXTURE_BLUE_TYPE = 35858;
    public static final int GL_TEXTURE_ALPHA_TYPE = 35859;
    public static final int GL_TEXTURE_LUMINANCE_TYPE = 35860;
    public static final int GL_TEXTURE_INTENSITY_TYPE = 35861;
    public static final int GL_TEXTURE_DEPTH_TYPE = 35862;
    public static final int GL_UNSIGNED_NORMALIZED = 35863;
    public static final int GL_RGBA32F = 34836;
    public static final int GL_RGB32F = 34837;
    public static final int GL_ALPHA32F = 34838;
    public static final int GL_RGBA16F = 34842;
    public static final int GL_RGB16F = 34843;
    public static final int GL_ALPHA16F = 34844;
    public static final int GL_R11F_G11F_B10F = 35898;
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
    public static final int GL_RGB9_E5 = 35901;
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
    public static final int GL_TEXTURE_SHARED_SIZE = 35903;
    public static final int GL_FRAMEBUFFER = 36160;
    public static final int GL_READ_FRAMEBUFFER = 36008;
    public static final int GL_DRAW_FRAMEBUFFER = 36009;
    public static final int GL_RENDERBUFFER = 36161;
    public static final int GL_STENCIL_INDEX1 = 36166;
    public static final int GL_STENCIL_INDEX4 = 36167;
    public static final int GL_STENCIL_INDEX8 = 36168;
    public static final int GL_STENCIL_INDEX16 = 36169;
    public static final int GL_RENDERBUFFER_WIDTH = 36162;
    public static final int GL_RENDERBUFFER_HEIGHT = 36163;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    public static final int GL_RENDERBUFFER_RED_SIZE = 36176;
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    public static final int GL_FRAMEBUFFER_DEFAULT = 33304;
    public static final int GL_INDEX = 33314;
    public static final int GL_COLOR_ATTACHMENT0 = 36064;
    public static final int GL_COLOR_ATTACHMENT1 = 36065;
    public static final int GL_COLOR_ATTACHMENT2 = 36066;
    public static final int GL_COLOR_ATTACHMENT3 = 36067;
    public static final int GL_COLOR_ATTACHMENT4 = 36068;
    public static final int GL_COLOR_ATTACHMENT5 = 36069;
    public static final int GL_COLOR_ATTACHMENT6 = 36070;
    public static final int GL_COLOR_ATTACHMENT7 = 36071;
    public static final int GL_COLOR_ATTACHMENT8 = 36072;
    public static final int GL_COLOR_ATTACHMENT9 = 36073;
    public static final int GL_COLOR_ATTACHMENT10 = 36074;
    public static final int GL_COLOR_ATTACHMENT11 = 36075;
    public static final int GL_COLOR_ATTACHMENT12 = 36076;
    public static final int GL_COLOR_ATTACHMENT13 = 36077;
    public static final int GL_COLOR_ATTACHMENT14 = 36078;
    public static final int GL_COLOR_ATTACHMENT15 = 36079;
    public static final int GL_DEPTH_ATTACHMENT = 36096;
    public static final int GL_STENCIL_ATTACHMENT = 36128;
    public static final int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static final int GL_FRAMEBUFFER_UNDEFINED = 33305;
    public static final int GL_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_RENDERBUFFER_BINDING = 36007;
    public static final int GL_MAX_COLOR_ATTACHMENTS = 36063;
    public static final int GL_MAX_RENDERBUFFER_SIZE = 34024;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    public static final int GL_HALF_FLOAT = 5131;
    public static final int GL_RENDERBUFFER_SAMPLES = 36011;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    public static final int GL_MAX_SAMPLES = 36183;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_READ_FRAMEBUFFER_BINDING = 36010;
    public static final int GL_RGBA_INTEGER_MODE = 36254;
    public static final int GL_RGBA32UI = 36208;
    public static final int GL_RGB32UI = 36209;
    public static final int GL_ALPHA32UI = 36210;
    public static final int GL_RGBA16UI = 36214;
    public static final int GL_RGB16UI = 36215;
    public static final int GL_ALPHA16UI = 36216;
    public static final int GL_RGBA8UI = 36220;
    public static final int GL_RGB8UI = 36221;
    public static final int GL_ALPHA8UI = 36222;
    public static final int GL_RGBA32I = 36226;
    public static final int GL_RGB32I = 36227;
    public static final int GL_ALPHA32I = 36228;
    public static final int GL_RGBA16I = 36232;
    public static final int GL_RGB16I = 36233;
    public static final int GL_ALPHA16I = 36234;
    public static final int GL_RGBA8I = 36238;
    public static final int GL_RGB8I = 36239;
    public static final int GL_ALPHA8I = 36240;
    public static final int GL_RED_INTEGER = 36244;
    public static final int GL_GREEN_INTEGER = 36245;
    public static final int GL_BLUE_INTEGER = 36246;
    public static final int GL_ALPHA_INTEGER = 36247;
    public static final int GL_RGB_INTEGER = 36248;
    public static final int GL_RGBA_INTEGER = 36249;
    public static final int GL_BGR_INTEGER = 36250;
    public static final int GL_BGRA_INTEGER = 36251;
    public static final int GL_TEXTURE_1D_ARRAY = 35864;
    public static final int GL_TEXTURE_2D_ARRAY = 35866;
    public static final int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
    public static final int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
    public static final int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
    public static final int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
    public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
    public static final int GL_COMPARE_REF_DEPTH_TO_TEXTURE = 34894;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    public static final int GL_SAMPLER_1D_ARRAY = 36288;
    public static final int GL_SAMPLER_2D_ARRAY = 36289;
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
    public static final int GL_DEPTH_STENCIL = 34041;
    public static final int GL_UNSIGNED_INT_24_8 = 34042;
    public static final int GL_DEPTH24_STENCIL8 = 35056;
    public static final int GL_TEXTURE_STENCIL_SIZE = 35057;
    public static final int GL_COMPRESSED_RED_RGTC1 = 36283;
    public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
    public static final int GL_COMPRESSED_RG_RGTC2 = 36285;
    public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
    public static final int GL_R8 = 33321;
    public static final int GL_R16 = 33322;
    public static final int GL_RG8 = 33323;
    public static final int GL_RG16 = 33324;
    public static final int GL_R16F = 33325;
    public static final int GL_R32F = 33326;
    public static final int GL_RG16F = 33327;
    public static final int GL_RG32F = 33328;
    public static final int GL_R8I = 33329;
    public static final int GL_R8UI = 33330;
    public static final int GL_R16I = 33331;
    public static final int GL_R16UI = 33332;
    public static final int GL_R32I = 33333;
    public static final int GL_R32UI = 33334;
    public static final int GL_RG8I = 33335;
    public static final int GL_RG8UI = 33336;
    public static final int GL_RG16I = 33337;
    public static final int GL_RG16UI = 33338;
    public static final int GL_RG32I = 33339;
    public static final int GL_RG32UI = 33340;
    public static final int GL_RG = 33319;
    public static final int GL_RG_INTEGER = 33320;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS = 35980;
    public static final int GL_SEPARATE_ATTRIBS = 35981;
    public static final int GL_PRIMITIVES_GENERATED = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
    public static final int GL_RASTERIZER_DISCARD = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
    public static final int GL_VERTEX_ARRAY_BINDING = 34229;
    public static final int GL_FRAMEBUFFER_SRGB = 36281;
    public static final int GL_FRAMEBUFFER_SRGB_CAPABLE = 36282;
    
    private GL30() {
    }
    
    public static String glGetStringi(final int n, final int n2) {
        final long glGetStringi = GLContext.getCapabilities().glGetStringi;
        BufferChecks.checkFunctionAddress(glGetStringi);
        return nglGetStringi(n, n2, glGetStringi);
    }
    
    static native String nglGetStringi(final int p0, final int p1, final long p2);
    
    public static void glClearBuffer(final int n, final int n2, final FloatBuffer floatBuffer) {
        final long glClearBufferfv = GLContext.getCapabilities().glClearBufferfv;
        BufferChecks.checkFunctionAddress(glClearBufferfv);
        BufferChecks.checkBuffer(floatBuffer, 4);
        nglClearBufferfv(n, n2, MemoryUtil.getAddress(floatBuffer), glClearBufferfv);
    }
    
    static native void nglClearBufferfv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glClearBuffer(final int n, final int n2, final IntBuffer intBuffer) {
        final long glClearBufferiv = GLContext.getCapabilities().glClearBufferiv;
        BufferChecks.checkFunctionAddress(glClearBufferiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglClearBufferiv(n, n2, MemoryUtil.getAddress(intBuffer), glClearBufferiv);
    }
    
    static native void nglClearBufferiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glClearBufferu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glClearBufferuiv = GLContext.getCapabilities().glClearBufferuiv;
        BufferChecks.checkFunctionAddress(glClearBufferuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglClearBufferuiv(n, n2, MemoryUtil.getAddress(intBuffer), glClearBufferuiv);
    }
    
    static native void nglClearBufferuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glClearBufferfi(final int n, final int n2, final float n3, final int n4) {
        final long glClearBufferfi = GLContext.getCapabilities().glClearBufferfi;
        BufferChecks.checkFunctionAddress(glClearBufferfi);
        nglClearBufferfi(n, n2, n3, n4, glClearBufferfi);
    }
    
    static native void nglClearBufferfi(final int p0, final int p1, final float p2, final int p3, final long p4);
    
    public static void glVertexAttribI1i(final int n, final int n2) {
        final long glVertexAttribI1i = GLContext.getCapabilities().glVertexAttribI1i;
        BufferChecks.checkFunctionAddress(glVertexAttribI1i);
        nglVertexAttribI1i(n, n2, glVertexAttribI1i);
    }
    
    static native void nglVertexAttribI1i(final int p0, final int p1, final long p2);
    
    public static void glVertexAttribI2i(final int n, final int n2, final int n3) {
        final long glVertexAttribI2i = GLContext.getCapabilities().glVertexAttribI2i;
        BufferChecks.checkFunctionAddress(glVertexAttribI2i);
        nglVertexAttribI2i(n, n2, n3, glVertexAttribI2i);
    }
    
    static native void nglVertexAttribI2i(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexAttribI3i(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribI3i = GLContext.getCapabilities().glVertexAttribI3i;
        BufferChecks.checkFunctionAddress(glVertexAttribI3i);
        nglVertexAttribI3i(n, n2, n3, n4, glVertexAttribI3i);
    }
    
    static native void nglVertexAttribI3i(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribI4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexAttribI4i = GLContext.getCapabilities().glVertexAttribI4i;
        BufferChecks.checkFunctionAddress(glVertexAttribI4i);
        nglVertexAttribI4i(n, n2, n3, n4, n5, glVertexAttribI4i);
    }
    
    static native void nglVertexAttribI4i(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexAttribI1ui(final int n, final int n2) {
        final long glVertexAttribI1ui = GLContext.getCapabilities().glVertexAttribI1ui;
        BufferChecks.checkFunctionAddress(glVertexAttribI1ui);
        nglVertexAttribI1ui(n, n2, glVertexAttribI1ui);
    }
    
    static native void nglVertexAttribI1ui(final int p0, final int p1, final long p2);
    
    public static void glVertexAttribI2ui(final int n, final int n2, final int n3) {
        final long glVertexAttribI2ui = GLContext.getCapabilities().glVertexAttribI2ui;
        BufferChecks.checkFunctionAddress(glVertexAttribI2ui);
        nglVertexAttribI2ui(n, n2, n3, glVertexAttribI2ui);
    }
    
    static native void nglVertexAttribI2ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glVertexAttribI3ui(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribI3ui = GLContext.getCapabilities().glVertexAttribI3ui;
        BufferChecks.checkFunctionAddress(glVertexAttribI3ui);
        nglVertexAttribI3ui(n, n2, n3, n4, glVertexAttribI3ui);
    }
    
    static native void nglVertexAttribI3ui(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribI4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glVertexAttribI4ui = GLContext.getCapabilities().glVertexAttribI4ui;
        BufferChecks.checkFunctionAddress(glVertexAttribI4ui);
        nglVertexAttribI4ui(n, n2, n3, n4, n5, glVertexAttribI4ui);
    }
    
    static native void nglVertexAttribI4ui(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glVertexAttribI1(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI1iv = GLContext.getCapabilities().glVertexAttribI1iv;
        BufferChecks.checkFunctionAddress(glVertexAttribI1iv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglVertexAttribI1iv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI1iv);
    }
    
    static native void nglVertexAttribI1iv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI2(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI2iv = GLContext.getCapabilities().glVertexAttribI2iv;
        BufferChecks.checkFunctionAddress(glVertexAttribI2iv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexAttribI2iv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI2iv);
    }
    
    static native void nglVertexAttribI2iv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI3(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI3iv = GLContext.getCapabilities().glVertexAttribI3iv;
        BufferChecks.checkFunctionAddress(glVertexAttribI3iv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexAttribI3iv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI3iv);
    }
    
    static native void nglVertexAttribI3iv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI4iv = GLContext.getCapabilities().glVertexAttribI4iv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4iv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexAttribI4iv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI4iv);
    }
    
    static native void nglVertexAttribI4iv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI1u(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI1uiv = GLContext.getCapabilities().glVertexAttribI1uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribI1uiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglVertexAttribI1uiv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI1uiv);
    }
    
    static native void nglVertexAttribI1uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI2u(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI2uiv = GLContext.getCapabilities().glVertexAttribI2uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribI2uiv);
        BufferChecks.checkBuffer(intBuffer, 2);
        nglVertexAttribI2uiv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI2uiv);
    }
    
    static native void nglVertexAttribI2uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI3u(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI3uiv = GLContext.getCapabilities().glVertexAttribI3uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribI3uiv);
        BufferChecks.checkBuffer(intBuffer, 3);
        nglVertexAttribI3uiv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI3uiv);
    }
    
    static native void nglVertexAttribI3uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4u(final int n, final IntBuffer intBuffer) {
        final long glVertexAttribI4uiv = GLContext.getCapabilities().glVertexAttribI4uiv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4uiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglVertexAttribI4uiv(n, MemoryUtil.getAddress(intBuffer), glVertexAttribI4uiv);
    }
    
    static native void nglVertexAttribI4uiv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4(final int n, final ByteBuffer byteBuffer) {
        final long glVertexAttribI4bv = GLContext.getCapabilities().glVertexAttribI4bv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4bv);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVertexAttribI4bv(n, MemoryUtil.getAddress(byteBuffer), glVertexAttribI4bv);
    }
    
    static native void nglVertexAttribI4bv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribI4sv = GLContext.getCapabilities().glVertexAttribI4sv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4sv);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVertexAttribI4sv(n, MemoryUtil.getAddress(shortBuffer), glVertexAttribI4sv);
    }
    
    static native void nglVertexAttribI4sv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4u(final int n, final ByteBuffer byteBuffer) {
        final long glVertexAttribI4ubv = GLContext.getCapabilities().glVertexAttribI4ubv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4ubv);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglVertexAttribI4ubv(n, MemoryUtil.getAddress(byteBuffer), glVertexAttribI4ubv);
    }
    
    static native void nglVertexAttribI4ubv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribI4u(final int n, final ShortBuffer shortBuffer) {
        final long glVertexAttribI4usv = GLContext.getCapabilities().glVertexAttribI4usv;
        BufferChecks.checkFunctionAddress(glVertexAttribI4usv);
        BufferChecks.checkBuffer(shortBuffer, 4);
        nglVertexAttribI4usv(n, MemoryUtil.getAddress(shortBuffer), glVertexAttribI4usv);
    }
    
    static native void nglVertexAttribI4usv(final int p0, final long p1, final long p2);
    
    public static void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointer = capabilities.glVertexAttribIPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = byteBuffer;
        }
        nglVertexAttribIPointer(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glVertexAttribIPointer);
    }
    
    public static void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointer = capabilities.glVertexAttribIPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = intBuffer;
        }
        nglVertexAttribIPointer(n, n2, n3, n4, MemoryUtil.getAddress(intBuffer), glVertexAttribIPointer);
    }
    
    public static void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointer = capabilities.glVertexAttribIPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointer);
        GLChecks.ensureArrayVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences(capabilities).glVertexAttribPointer_buffer[n] = shortBuffer;
        }
        nglVertexAttribIPointer(n, n2, n3, n4, MemoryUtil.getAddress(shortBuffer), glVertexAttribIPointer);
    }
    
    static native void nglVertexAttribIPointer(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final long n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glVertexAttribIPointer = capabilities.glVertexAttribIPointer;
        BufferChecks.checkFunctionAddress(glVertexAttribIPointer);
        GLChecks.ensureArrayVBOenabled(capabilities);
        nglVertexAttribIPointerBO(n, n2, n3, n4, n5, glVertexAttribIPointer);
    }
    
    static native void nglVertexAttribIPointerBO(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glGetVertexAttribI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribIiv = GLContext.getCapabilities().glGetVertexAttribIiv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribIiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribIiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribIiv);
    }
    
    static native void nglGetVertexAttribIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetVertexAttribIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetVertexAttribIuiv = GLContext.getCapabilities().glGetVertexAttribIuiv;
        BufferChecks.checkFunctionAddress(glGetVertexAttribIuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetVertexAttribIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetVertexAttribIuiv);
    }
    
    static native void nglGetVertexAttribIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform1ui(final int n, final int n2) {
        final long glUniform1ui = GLContext.getCapabilities().glUniform1ui;
        BufferChecks.checkFunctionAddress(glUniform1ui);
        nglUniform1ui(n, n2, glUniform1ui);
    }
    
    static native void nglUniform1ui(final int p0, final int p1, final long p2);
    
    public static void glUniform2ui(final int n, final int n2, final int n3) {
        final long glUniform2ui = GLContext.getCapabilities().glUniform2ui;
        BufferChecks.checkFunctionAddress(glUniform2ui);
        nglUniform2ui(n, n2, n3, glUniform2ui);
    }
    
    static native void nglUniform2ui(final int p0, final int p1, final int p2, final long p3);
    
    public static void glUniform3ui(final int n, final int n2, final int n3, final int n4) {
        final long glUniform3ui = GLContext.getCapabilities().glUniform3ui;
        BufferChecks.checkFunctionAddress(glUniform3ui);
        nglUniform3ui(n, n2, n3, n4, glUniform3ui);
    }
    
    static native void nglUniform3ui(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glUniform4ui = GLContext.getCapabilities().glUniform4ui;
        BufferChecks.checkFunctionAddress(glUniform4ui);
        nglUniform4ui(n, n2, n3, n4, n5, glUniform4ui);
    }
    
    static native void nglUniform4ui(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glUniform1u(final int n, final IntBuffer intBuffer) {
        final long glUniform1uiv = GLContext.getCapabilities().glUniform1uiv;
        BufferChecks.checkFunctionAddress(glUniform1uiv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform1uiv(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glUniform1uiv);
    }
    
    static native void nglUniform1uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform2u(final int n, final IntBuffer intBuffer) {
        final long glUniform2uiv = GLContext.getCapabilities().glUniform2uiv;
        BufferChecks.checkFunctionAddress(glUniform2uiv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform2uiv(n, intBuffer.remaining() >> 1, MemoryUtil.getAddress(intBuffer), glUniform2uiv);
    }
    
    static native void nglUniform2uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform3u(final int n, final IntBuffer intBuffer) {
        final long glUniform3uiv = GLContext.getCapabilities().glUniform3uiv;
        BufferChecks.checkFunctionAddress(glUniform3uiv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform3uiv(n, intBuffer.remaining() / 3, MemoryUtil.getAddress(intBuffer), glUniform3uiv);
    }
    
    static native void nglUniform3uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glUniform4u(final int n, final IntBuffer intBuffer) {
        final long glUniform4uiv = GLContext.getCapabilities().glUniform4uiv;
        BufferChecks.checkFunctionAddress(glUniform4uiv);
        BufferChecks.checkDirect(intBuffer);
        nglUniform4uiv(n, intBuffer.remaining() >> 2, MemoryUtil.getAddress(intBuffer), glUniform4uiv);
    }
    
    static native void nglUniform4uiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glGetUniformu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetUniformuiv = GLContext.getCapabilities().glGetUniformuiv;
        BufferChecks.checkFunctionAddress(glGetUniformuiv);
        BufferChecks.checkDirect(intBuffer);
        nglGetUniformuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetUniformuiv);
    }
    
    static native void nglGetUniformuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindFragDataLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glBindFragDataLocation = GLContext.getCapabilities().glBindFragDataLocation;
        BufferChecks.checkFunctionAddress(glBindFragDataLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        nglBindFragDataLocation(n, n2, MemoryUtil.getAddress(byteBuffer), glBindFragDataLocation);
    }
    
    static native void nglBindFragDataLocation(final int p0, final int p1, final long p2, final long p3);
    
    public static void glBindFragDataLocation(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindFragDataLocation = capabilities.glBindFragDataLocation;
        BufferChecks.checkFunctionAddress(glBindFragDataLocation);
        nglBindFragDataLocation(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glBindFragDataLocation);
    }
    
    public static int glGetFragDataLocation(final int n, final ByteBuffer byteBuffer) {
        final long glGetFragDataLocation = GLContext.getCapabilities().glGetFragDataLocation;
        BufferChecks.checkFunctionAddress(glGetFragDataLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetFragDataLocation(n, MemoryUtil.getAddress(byteBuffer), glGetFragDataLocation);
    }
    
    static native int nglGetFragDataLocation(final int p0, final long p1, final long p2);
    
    public static int glGetFragDataLocation(final int n, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFragDataLocation = capabilities.glGetFragDataLocation;
        BufferChecks.checkFunctionAddress(glGetFragDataLocation);
        return nglGetFragDataLocation(n, APIUtil.getBufferNT(capabilities, charSequence), glGetFragDataLocation);
    }
    
    public static void glBeginConditionalRender(final int n, final int n2) {
        final long glBeginConditionalRender = GLContext.getCapabilities().glBeginConditionalRender;
        BufferChecks.checkFunctionAddress(glBeginConditionalRender);
        nglBeginConditionalRender(n, n2, glBeginConditionalRender);
    }
    
    static native void nglBeginConditionalRender(final int p0, final int p1, final long p2);
    
    public static void glEndConditionalRender() {
        final long glEndConditionalRender = GLContext.getCapabilities().glEndConditionalRender;
        BufferChecks.checkFunctionAddress(glEndConditionalRender);
        nglEndConditionalRender(glEndConditionalRender);
    }
    
    static native void nglEndConditionalRender(final long p0);
    
    public static ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4, final ByteBuffer byteBuffer) {
        final long glMapBufferRange = GLContext.getCapabilities().glMapBufferRange;
        BufferChecks.checkFunctionAddress(glMapBufferRange);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        final ByteBuffer nglMapBufferRange = nglMapBufferRange(n, n2, n3, n4, byteBuffer, glMapBufferRange);
        return (LWJGLUtil.CHECKS && nglMapBufferRange == null) ? null : nglMapBufferRange.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nglMapBufferRange(final int p0, final long p1, final long p2, final int p3, final ByteBuffer p4, final long p5);
    
    public static void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        final long glFlushMappedBufferRange = GLContext.getCapabilities().glFlushMappedBufferRange;
        BufferChecks.checkFunctionAddress(glFlushMappedBufferRange);
        nglFlushMappedBufferRange(n, n2, n3, glFlushMappedBufferRange);
    }
    
    static native void nglFlushMappedBufferRange(final int p0, final long p1, final long p2, final long p3);
    
    public static void glClampColor(final int n, final int n2) {
        final long glClampColor = GLContext.getCapabilities().glClampColor;
        BufferChecks.checkFunctionAddress(glClampColor);
        nglClampColor(n, n2, glClampColor);
    }
    
    static native void nglClampColor(final int p0, final int p1, final long p2);
    
    public static boolean glIsRenderbuffer(final int n) {
        final long glIsRenderbuffer = GLContext.getCapabilities().glIsRenderbuffer;
        BufferChecks.checkFunctionAddress(glIsRenderbuffer);
        return nglIsRenderbuffer(n, glIsRenderbuffer);
    }
    
    static native boolean nglIsRenderbuffer(final int p0, final long p1);
    
    public static void glBindRenderbuffer(final int n, final int n2) {
        final long glBindRenderbuffer = GLContext.getCapabilities().glBindRenderbuffer;
        BufferChecks.checkFunctionAddress(glBindRenderbuffer);
        nglBindRenderbuffer(n, n2, glBindRenderbuffer);
    }
    
    static native void nglBindRenderbuffer(final int p0, final int p1, final long p2);
    
    public static void glDeleteRenderbuffers(final IntBuffer intBuffer) {
        final long glDeleteRenderbuffers = GLContext.getCapabilities().glDeleteRenderbuffers;
        BufferChecks.checkFunctionAddress(glDeleteRenderbuffers);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteRenderbuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteRenderbuffers);
    }
    
    static native void nglDeleteRenderbuffers(final int p0, final long p1, final long p2);
    
    public static void glDeleteRenderbuffers(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteRenderbuffers = capabilities.glDeleteRenderbuffers;
        BufferChecks.checkFunctionAddress(glDeleteRenderbuffers);
        nglDeleteRenderbuffers(1, APIUtil.getInt(capabilities, n), glDeleteRenderbuffers);
    }
    
    public static void glGenRenderbuffers(final IntBuffer intBuffer) {
        final long glGenRenderbuffers = GLContext.getCapabilities().glGenRenderbuffers;
        BufferChecks.checkFunctionAddress(glGenRenderbuffers);
        BufferChecks.checkDirect(intBuffer);
        nglGenRenderbuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenRenderbuffers);
    }
    
    static native void nglGenRenderbuffers(final int p0, final long p1, final long p2);
    
    public static int glGenRenderbuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenRenderbuffers = capabilities.glGenRenderbuffers;
        BufferChecks.checkFunctionAddress(glGenRenderbuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenRenderbuffers(1, MemoryUtil.getAddress(bufferInt), glGenRenderbuffers);
        return bufferInt.get(0);
    }
    
    public static void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        final long glRenderbufferStorage = GLContext.getCapabilities().glRenderbufferStorage;
        BufferChecks.checkFunctionAddress(glRenderbufferStorage);
        nglRenderbufferStorage(n, n2, n3, n4, glRenderbufferStorage);
    }
    
    static native void nglRenderbufferStorage(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetRenderbufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetRenderbufferParameteriv = GLContext.getCapabilities().glGetRenderbufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetRenderbufferParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetRenderbufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetRenderbufferParameteriv);
    }
    
    static native void nglGetRenderbufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    @Deprecated
    public static int glGetRenderbufferParameter(final int n, final int n2) {
        return glGetRenderbufferParameteri(n, n2);
    }
    
    public static int glGetRenderbufferParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetRenderbufferParameteriv = capabilities.glGetRenderbufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetRenderbufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetRenderbufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetRenderbufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static boolean glIsFramebuffer(final int n) {
        final long glIsFramebuffer = GLContext.getCapabilities().glIsFramebuffer;
        BufferChecks.checkFunctionAddress(glIsFramebuffer);
        return nglIsFramebuffer(n, glIsFramebuffer);
    }
    
    static native boolean nglIsFramebuffer(final int p0, final long p1);
    
    public static void glBindFramebuffer(final int n, final int n2) {
        final long glBindFramebuffer = GLContext.getCapabilities().glBindFramebuffer;
        BufferChecks.checkFunctionAddress(glBindFramebuffer);
        nglBindFramebuffer(n, n2, glBindFramebuffer);
    }
    
    static native void nglBindFramebuffer(final int p0, final int p1, final long p2);
    
    public static void glDeleteFramebuffers(final IntBuffer intBuffer) {
        final long glDeleteFramebuffers = GLContext.getCapabilities().glDeleteFramebuffers;
        BufferChecks.checkFunctionAddress(glDeleteFramebuffers);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteFramebuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteFramebuffers);
    }
    
    static native void nglDeleteFramebuffers(final int p0, final long p1, final long p2);
    
    public static void glDeleteFramebuffers(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteFramebuffers = capabilities.glDeleteFramebuffers;
        BufferChecks.checkFunctionAddress(glDeleteFramebuffers);
        nglDeleteFramebuffers(1, APIUtil.getInt(capabilities, n), glDeleteFramebuffers);
    }
    
    public static void glGenFramebuffers(final IntBuffer intBuffer) {
        final long glGenFramebuffers = GLContext.getCapabilities().glGenFramebuffers;
        BufferChecks.checkFunctionAddress(glGenFramebuffers);
        BufferChecks.checkDirect(intBuffer);
        nglGenFramebuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenFramebuffers);
    }
    
    static native void nglGenFramebuffers(final int p0, final long p1, final long p2);
    
    public static int glGenFramebuffers() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenFramebuffers = capabilities.glGenFramebuffers;
        BufferChecks.checkFunctionAddress(glGenFramebuffers);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenFramebuffers(1, MemoryUtil.getAddress(bufferInt), glGenFramebuffers);
        return bufferInt.get(0);
    }
    
    public static int glCheckFramebufferStatus(final int n) {
        final long glCheckFramebufferStatus = GLContext.getCapabilities().glCheckFramebufferStatus;
        BufferChecks.checkFunctionAddress(glCheckFramebufferStatus);
        return nglCheckFramebufferStatus(n, glCheckFramebufferStatus);
    }
    
    static native int nglCheckFramebufferStatus(final int p0, final long p1);
    
    public static void glFramebufferTexture1D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTexture1D = GLContext.getCapabilities().glFramebufferTexture1D;
        BufferChecks.checkFunctionAddress(glFramebufferTexture1D);
        nglFramebufferTexture1D(n, n2, n3, n4, n5, glFramebufferTexture1D);
    }
    
    static native void nglFramebufferTexture1D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTexture2D = GLContext.getCapabilities().glFramebufferTexture2D;
        BufferChecks.checkFunctionAddress(glFramebufferTexture2D);
        nglFramebufferTexture2D(n, n2, n3, n4, n5, glFramebufferTexture2D);
    }
    
    static native void nglFramebufferTexture2D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glFramebufferTexture3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long glFramebufferTexture3D = GLContext.getCapabilities().glFramebufferTexture3D;
        BufferChecks.checkFunctionAddress(glFramebufferTexture3D);
        nglFramebufferTexture3D(n, n2, n3, n4, n5, n6, glFramebufferTexture3D);
    }
    
    static native void nglFramebufferTexture3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    public static void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        final long glFramebufferRenderbuffer = GLContext.getCapabilities().glFramebufferRenderbuffer;
        BufferChecks.checkFunctionAddress(glFramebufferRenderbuffer);
        nglFramebufferRenderbuffer(n, n2, n3, n4, glFramebufferRenderbuffer);
    }
    
    static native void nglFramebufferRenderbuffer(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glGetFramebufferAttachmentParameter(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetFramebufferAttachmentParameteriv = GLContext.getCapabilities().glGetFramebufferAttachmentParameteriv;
        BufferChecks.checkFunctionAddress(glGetFramebufferAttachmentParameteriv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetFramebufferAttachmentParameteriv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetFramebufferAttachmentParameteriv);
    }
    
    static native void nglGetFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Deprecated
    public static int glGetFramebufferAttachmentParameter(final int n, final int n2, final int n3) {
        return glGetFramebufferAttachmentParameteri(n, n2, n3);
    }
    
    public static int glGetFramebufferAttachmentParameteri(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFramebufferAttachmentParameteriv = capabilities.glGetFramebufferAttachmentParameteriv;
        BufferChecks.checkFunctionAddress(glGetFramebufferAttachmentParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetFramebufferAttachmentParameteriv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetFramebufferAttachmentParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGenerateMipmap(final int n) {
        final long glGenerateMipmap = GLContext.getCapabilities().glGenerateMipmap;
        BufferChecks.checkFunctionAddress(glGenerateMipmap);
        nglGenerateMipmap(n, glGenerateMipmap);
    }
    
    static native void nglGenerateMipmap(final int p0, final long p1);
    
    public static void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glRenderbufferStorageMultisample = GLContext.getCapabilities().glRenderbufferStorageMultisample;
        BufferChecks.checkFunctionAddress(glRenderbufferStorageMultisample);
        nglRenderbufferStorageMultisample(n, n2, n3, n4, n5, glRenderbufferStorageMultisample);
    }
    
    static native void nglRenderbufferStorageMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glBlitFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long glBlitFramebuffer = GLContext.getCapabilities().glBlitFramebuffer;
        BufferChecks.checkFunctionAddress(glBlitFramebuffer);
        nglBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, glBlitFramebuffer);
    }
    
    static native void nglBlitFramebuffer(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    public static void glTexParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexParameterIiv = GLContext.getCapabilities().glTexParameterIiv;
        BufferChecks.checkFunctionAddress(glTexParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glTexParameterIiv);
    }
    
    static native void nglTexParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexParameterIi(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexParameterIiv = capabilities.glTexParameterIiv;
        BufferChecks.checkFunctionAddress(glTexParameterIiv);
        nglTexParameterIiv(n, n2, APIUtil.getInt(capabilities, n3), glTexParameterIiv);
    }
    
    public static void glTexParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glTexParameterIuiv = GLContext.getCapabilities().glTexParameterIuiv;
        BufferChecks.checkFunctionAddress(glTexParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglTexParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glTexParameterIuiv);
    }
    
    static native void nglTexParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static void glTexParameterIui(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTexParameterIuiv = capabilities.glTexParameterIuiv;
        BufferChecks.checkFunctionAddress(glTexParameterIuiv);
        nglTexParameterIuiv(n, n2, APIUtil.getInt(capabilities, n3), glTexParameterIuiv);
    }
    
    public static void glGetTexParameterI(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexParameterIiv = GLContext.getCapabilities().glGetTexParameterIiv;
        BufferChecks.checkFunctionAddress(glGetTexParameterIiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexParameterIiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexParameterIiv);
    }
    
    static native void nglGetTexParameterIiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexParameterIi(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameterIiv = capabilities.glGetTexParameterIiv;
        BufferChecks.checkFunctionAddress(glGetTexParameterIiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexParameterIiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexParameterIiv);
        return bufferInt.get(0);
    }
    
    public static void glGetTexParameterIu(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetTexParameterIuiv = GLContext.getCapabilities().glGetTexParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetTexParameterIuiv);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetTexParameterIuiv(n, n2, MemoryUtil.getAddress(intBuffer), glGetTexParameterIuiv);
    }
    
    static native void nglGetTexParameterIuiv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetTexParameterIui(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTexParameterIuiv = capabilities.glGetTexParameterIuiv;
        BufferChecks.checkFunctionAddress(glGetTexParameterIuiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetTexParameterIuiv(n, n2, MemoryUtil.getAddress(bufferInt), glGetTexParameterIuiv);
        return bufferInt.get(0);
    }
    
    public static void glFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long glFramebufferTextureLayer = GLContext.getCapabilities().glFramebufferTextureLayer;
        BufferChecks.checkFunctionAddress(glFramebufferTextureLayer);
        nglFramebufferTextureLayer(n, n2, n3, n4, n5, glFramebufferTextureLayer);
    }
    
    static native void nglFramebufferTextureLayer(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public static void glColorMaski(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long glColorMaski = GLContext.getCapabilities().glColorMaski;
        BufferChecks.checkFunctionAddress(glColorMaski);
        nglColorMaski(n, b, b2, b3, b4, glColorMaski);
    }
    
    static native void nglColorMaski(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4, final long p5);
    
    public static void glGetBoolean(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetBooleani_v = GLContext.getCapabilities().glGetBooleani_v;
        BufferChecks.checkFunctionAddress(glGetBooleani_v);
        BufferChecks.checkBuffer(byteBuffer, 4);
        nglGetBooleani_v(n, n2, MemoryUtil.getAddress(byteBuffer), glGetBooleani_v);
    }
    
    static native void nglGetBooleani_v(final int p0, final int p1, final long p2, final long p3);
    
    public static boolean glGetBoolean(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetBooleani_v = capabilities.glGetBooleani_v;
        BufferChecks.checkFunctionAddress(glGetBooleani_v);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, 1);
        nglGetBooleani_v(n, n2, MemoryUtil.getAddress(bufferByte), glGetBooleani_v);
        return bufferByte.get(0) == 1;
    }
    
    public static void glGetInteger(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetIntegeri_v = GLContext.getCapabilities().glGetIntegeri_v;
        BufferChecks.checkFunctionAddress(glGetIntegeri_v);
        BufferChecks.checkBuffer(intBuffer, 4);
        nglGetIntegeri_v(n, n2, MemoryUtil.getAddress(intBuffer), glGetIntegeri_v);
    }
    
    static native void nglGetIntegeri_v(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetInteger(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetIntegeri_v = capabilities.glGetIntegeri_v;
        BufferChecks.checkFunctionAddress(glGetIntegeri_v);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetIntegeri_v(n, n2, MemoryUtil.getAddress(bufferInt), glGetIntegeri_v);
        return bufferInt.get(0);
    }
    
    public static void glEnablei(final int n, final int n2) {
        final long glEnablei = GLContext.getCapabilities().glEnablei;
        BufferChecks.checkFunctionAddress(glEnablei);
        nglEnablei(n, n2, glEnablei);
    }
    
    static native void nglEnablei(final int p0, final int p1, final long p2);
    
    public static void glDisablei(final int n, final int n2) {
        final long glDisablei = GLContext.getCapabilities().glDisablei;
        BufferChecks.checkFunctionAddress(glDisablei);
        nglDisablei(n, n2, glDisablei);
    }
    
    static native void nglDisablei(final int p0, final int p1, final long p2);
    
    public static boolean glIsEnabledi(final int n, final int n2) {
        final long glIsEnabledi = GLContext.getCapabilities().glIsEnabledi;
        BufferChecks.checkFunctionAddress(glIsEnabledi);
        return nglIsEnabledi(n, n2, glIsEnabledi);
    }
    
    static native boolean nglIsEnabledi(final int p0, final int p1, final long p2);
    
    public static void glBindBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glBindBufferRange = GLContext.getCapabilities().glBindBufferRange;
        BufferChecks.checkFunctionAddress(glBindBufferRange);
        nglBindBufferRange(n, n2, n3, n4, n5, glBindBufferRange);
    }
    
    static native void nglBindBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glBindBufferBase(final int n, final int n2, final int n3) {
        final long glBindBufferBase = GLContext.getCapabilities().glBindBufferBase;
        BufferChecks.checkFunctionAddress(glBindBufferBase);
        nglBindBufferBase(n, n2, n3, glBindBufferBase);
    }
    
    static native void nglBindBufferBase(final int p0, final int p1, final int p2, final long p3);
    
    public static void glBeginTransformFeedback(final int n) {
        final long glBeginTransformFeedback = GLContext.getCapabilities().glBeginTransformFeedback;
        BufferChecks.checkFunctionAddress(glBeginTransformFeedback);
        nglBeginTransformFeedback(n, glBeginTransformFeedback);
    }
    
    static native void nglBeginTransformFeedback(final int p0, final long p1);
    
    public static void glEndTransformFeedback() {
        final long glEndTransformFeedback = GLContext.getCapabilities().glEndTransformFeedback;
        BufferChecks.checkFunctionAddress(glEndTransformFeedback);
        nglEndTransformFeedback(glEndTransformFeedback);
    }
    
    static native void nglEndTransformFeedback(final long p0);
    
    public static void glTransformFeedbackVaryings(final int n, final int n2, final ByteBuffer byteBuffer, final int n3) {
        final long glTransformFeedbackVaryings = GLContext.getCapabilities().glTransformFeedbackVaryings;
        BufferChecks.checkFunctionAddress(glTransformFeedbackVaryings);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer, n2);
        nglTransformFeedbackVaryings(n, n2, MemoryUtil.getAddress(byteBuffer), n3, glTransformFeedbackVaryings);
    }
    
    static native void nglTransformFeedbackVaryings(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glTransformFeedbackVaryings(final int n, final CharSequence[] array, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glTransformFeedbackVaryings = capabilities.glTransformFeedbackVaryings;
        BufferChecks.checkFunctionAddress(glTransformFeedbackVaryings);
        BufferChecks.checkArray(array);
        nglTransformFeedbackVaryings(n, array.length, APIUtil.getBufferNT(capabilities, array), n2, glTransformFeedbackVaryings);
    }
    
    public static void glGetTransformFeedbackVarying(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final long glGetTransformFeedbackVarying = GLContext.getCapabilities().glGetTransformFeedbackVarying;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVarying);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkBuffer(intBuffer2, 1);
        BufferChecks.checkBuffer(intBuffer3, 1);
        BufferChecks.checkDirect(byteBuffer);
        nglGetTransformFeedbackVarying(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(intBuffer3), MemoryUtil.getAddress(byteBuffer), glGetTransformFeedbackVarying);
    }
    
    static native void nglGetTransformFeedbackVarying(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static String glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetTransformFeedbackVarying = capabilities.glGetTransformFeedbackVarying;
        BufferChecks.checkFunctionAddress(glGetTransformFeedbackVarying);
        BufferChecks.checkBuffer(intBuffer, 1);
        BufferChecks.checkBuffer(intBuffer2, 1);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetTransformFeedbackVarying(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), MemoryUtil.getAddress(bufferByte), glGetTransformFeedbackVarying);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glBindVertexArray(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glBindVertexArray = capabilities.glBindVertexArray;
        BufferChecks.checkFunctionAddress(glBindVertexArray);
        StateTracker.bindVAO(capabilities, n);
        nglBindVertexArray(n, glBindVertexArray);
    }
    
    static native void nglBindVertexArray(final int p0, final long p1);
    
    public static void glDeleteVertexArrays(final IntBuffer intBuffer) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteVertexArrays = capabilities.glDeleteVertexArrays;
        BufferChecks.checkFunctionAddress(glDeleteVertexArrays);
        StateTracker.deleteVAO(capabilities, intBuffer);
        BufferChecks.checkDirect(intBuffer);
        nglDeleteVertexArrays(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeleteVertexArrays);
    }
    
    static native void nglDeleteVertexArrays(final int p0, final long p1, final long p2);
    
    public static void glDeleteVertexArrays(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeleteVertexArrays = capabilities.glDeleteVertexArrays;
        BufferChecks.checkFunctionAddress(glDeleteVertexArrays);
        StateTracker.deleteVAO(capabilities, n);
        nglDeleteVertexArrays(1, APIUtil.getInt(capabilities, n), glDeleteVertexArrays);
    }
    
    public static void glGenVertexArrays(final IntBuffer intBuffer) {
        final long glGenVertexArrays = GLContext.getCapabilities().glGenVertexArrays;
        BufferChecks.checkFunctionAddress(glGenVertexArrays);
        BufferChecks.checkDirect(intBuffer);
        nglGenVertexArrays(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenVertexArrays);
    }
    
    static native void nglGenVertexArrays(final int p0, final long p1, final long p2);
    
    public static int glGenVertexArrays() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenVertexArrays = capabilities.glGenVertexArrays;
        BufferChecks.checkFunctionAddress(glGenVertexArrays);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenVertexArrays(1, MemoryUtil.getAddress(bufferInt), glGenVertexArrays);
        return bufferInt.get(0);
    }
    
    public static boolean glIsVertexArray(final int n) {
        final long glIsVertexArray = GLContext.getCapabilities().glIsVertexArray;
        BufferChecks.checkFunctionAddress(glIsVertexArray);
        return nglIsVertexArray(n, glIsVertexArray);
    }
    
    static native boolean nglIsVertexArray(final int p0, final long p1);
}
