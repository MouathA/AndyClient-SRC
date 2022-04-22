package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class GL43
{
    public static final int GL_NUM_SHADING_LANGUAGE_VERSIONS = 33513;
    public static final int GL_VERTEX_ATTRIB_ARRAY_LONG = 34638;
    public static final int GL_COMPRESSED_RGB8_ETC2 = 37492;
    public static final int GL_COMPRESSED_SRGB8_ETC2 = 37493;
    public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37494;
    public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37495;
    public static final int GL_COMPRESSED_RGBA8_ETC2_EAC = 37496;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 37497;
    public static final int GL_COMPRESSED_R11_EAC = 37488;
    public static final int GL_COMPRESSED_SIGNED_R11_EAC = 37489;
    public static final int GL_COMPRESSED_RG11_EAC = 37490;
    public static final int GL_COMPRESSED_SIGNED_RG11_EAC = 37491;
    public static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = 36201;
    public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 36202;
    public static final int GL_MAX_ELEMENT_INDEX = 36203;
    public static final int GL_COMPUTE_SHADER = 37305;
    public static final int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 37307;
    public static final int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 37308;
    public static final int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 37309;
    public static final int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 33378;
    public static final int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 33379;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 33380;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 33381;
    public static final int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 33382;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 37099;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 37310;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 37311;
    public static final int GL_COMPUTE_WORK_GROUP_SIZE = 33383;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 37100;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 37101;
    public static final int GL_DISPATCH_INDIRECT_BUFFER = 37102;
    public static final int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 37103;
    public static final int GL_COMPUTE_SHADER_BIT = 32;
    public static final int GL_DEBUG_OUTPUT = 37600;
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static final int GL_MAX_LABEL_LENGTH = 33512;
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static final int GL_DEBUG_SOURCE_API = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER = 33355;
    public static final int GL_DEBUG_TYPE_ERROR = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static final int GL_DEBUG_TYPE_OTHER = 33361;
    public static final int GL_DEBUG_TYPE_MARKER = 33384;
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW = 37192;
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static final int GL_BUFFER = 33504;
    public static final int GL_SHADER = 33505;
    public static final int GL_PROGRAM = 33506;
    public static final int GL_QUERY = 33507;
    public static final int GL_PROGRAM_PIPELINE = 33508;
    public static final int GL_SAMPLER = 33510;
    public static final int GL_DISPLAY_LIST = 33511;
    public static final int GL_MAX_UNIFORM_LOCATIONS = 33390;
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;
    public static final int GL_INTERNALFORMAT_SUPPORTED = 33391;
    public static final int GL_INTERNALFORMAT_PREFERRED = 33392;
    public static final int GL_INTERNALFORMAT_RED_SIZE = 33393;
    public static final int GL_INTERNALFORMAT_GREEN_SIZE = 33394;
    public static final int GL_INTERNALFORMAT_BLUE_SIZE = 33395;
    public static final int GL_INTERNALFORMAT_ALPHA_SIZE = 33396;
    public static final int GL_INTERNALFORMAT_DEPTH_SIZE = 33397;
    public static final int GL_INTERNALFORMAT_STENCIL_SIZE = 33398;
    public static final int GL_INTERNALFORMAT_SHARED_SIZE = 33399;
    public static final int GL_INTERNALFORMAT_RED_TYPE = 33400;
    public static final int GL_INTERNALFORMAT_GREEN_TYPE = 33401;
    public static final int GL_INTERNALFORMAT_BLUE_TYPE = 33402;
    public static final int GL_INTERNALFORMAT_ALPHA_TYPE = 33403;
    public static final int GL_INTERNALFORMAT_DEPTH_TYPE = 33404;
    public static final int GL_INTERNALFORMAT_STENCIL_TYPE = 33405;
    public static final int GL_MAX_WIDTH = 33406;
    public static final int GL_MAX_HEIGHT = 33407;
    public static final int GL_MAX_DEPTH = 33408;
    public static final int GL_MAX_LAYERS = 33409;
    public static final int GL_MAX_COMBINED_DIMENSIONS = 33410;
    public static final int GL_COLOR_COMPONENTS = 33411;
    public static final int GL_DEPTH_COMPONENTS = 33412;
    public static final int GL_STENCIL_COMPONENTS = 33413;
    public static final int GL_COLOR_RENDERABLE = 33414;
    public static final int GL_DEPTH_RENDERABLE = 33415;
    public static final int GL_STENCIL_RENDERABLE = 33416;
    public static final int GL_FRAMEBUFFER_RENDERABLE = 33417;
    public static final int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 33418;
    public static final int GL_FRAMEBUFFER_BLEND = 33419;
    public static final int GL_READ_PIXELS = 33420;
    public static final int GL_READ_PIXELS_FORMAT = 33421;
    public static final int GL_READ_PIXELS_TYPE = 33422;
    public static final int GL_TEXTURE_IMAGE_FORMAT = 33423;
    public static final int GL_TEXTURE_IMAGE_TYPE = 33424;
    public static final int GL_GET_TEXTURE_IMAGE_FORMAT = 33425;
    public static final int GL_GET_TEXTURE_IMAGE_TYPE = 33426;
    public static final int GL_MIPMAP = 33427;
    public static final int GL_MANUAL_GENERATE_MIPMAP = 33428;
    public static final int GL_AUTO_GENERATE_MIPMAP = 33429;
    public static final int GL_COLOR_ENCODING = 33430;
    public static final int GL_SRGB_READ = 33431;
    public static final int GL_SRGB_WRITE = 33432;
    public static final int GL_SRGB_DECODE_ARB = 33433;
    public static final int GL_FILTER = 33434;
    public static final int GL_VERTEX_TEXTURE = 33435;
    public static final int GL_TESS_CONTROL_TEXTURE = 33436;
    public static final int GL_TESS_EVALUATION_TEXTURE = 33437;
    public static final int GL_GEOMETRY_TEXTURE = 33438;
    public static final int GL_FRAGMENT_TEXTURE = 33439;
    public static final int GL_COMPUTE_TEXTURE = 33440;
    public static final int GL_TEXTURE_SHADOW = 33441;
    public static final int GL_TEXTURE_GATHER = 33442;
    public static final int GL_TEXTURE_GATHER_SHADOW = 33443;
    public static final int GL_SHADER_IMAGE_LOAD = 33444;
    public static final int GL_SHADER_IMAGE_STORE = 33445;
    public static final int GL_SHADER_IMAGE_ATOMIC = 33446;
    public static final int GL_IMAGE_TEXEL_SIZE = 33447;
    public static final int GL_IMAGE_COMPATIBILITY_CLASS = 33448;
    public static final int GL_IMAGE_PIXEL_FORMAT = 33449;
    public static final int GL_IMAGE_PIXEL_TYPE = 33450;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 33452;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 33453;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 33454;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 33455;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 33457;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 33458;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 33459;
    public static final int GL_CLEAR_BUFFER = 33460;
    public static final int GL_TEXTURE_VIEW = 33461;
    public static final int GL_VIEW_COMPATIBILITY_CLASS = 33462;
    public static final int GL_FULL_SUPPORT = 33463;
    public static final int GL_CAVEAT_SUPPORT = 33464;
    public static final int GL_IMAGE_CLASS_4_X_32 = 33465;
    public static final int GL_IMAGE_CLASS_2_X_32 = 33466;
    public static final int GL_IMAGE_CLASS_1_X_32 = 33467;
    public static final int GL_IMAGE_CLASS_4_X_16 = 33468;
    public static final int GL_IMAGE_CLASS_2_X_16 = 33469;
    public static final int GL_IMAGE_CLASS_1_X_16 = 33470;
    public static final int GL_IMAGE_CLASS_4_X_8 = 33471;
    public static final int GL_IMAGE_CLASS_2_X_8 = 33472;
    public static final int GL_IMAGE_CLASS_1_X_8 = 33473;
    public static final int GL_IMAGE_CLASS_11_11_10 = 33474;
    public static final int GL_IMAGE_CLASS_10_10_10_2 = 33475;
    public static final int GL_VIEW_CLASS_128_BITS = 33476;
    public static final int GL_VIEW_CLASS_96_BITS = 33477;
    public static final int GL_VIEW_CLASS_64_BITS = 33478;
    public static final int GL_VIEW_CLASS_48_BITS = 33479;
    public static final int GL_VIEW_CLASS_32_BITS = 33480;
    public static final int GL_VIEW_CLASS_24_BITS = 33481;
    public static final int GL_VIEW_CLASS_16_BITS = 33482;
    public static final int GL_VIEW_CLASS_8_BITS = 33483;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGB = 33484;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 33485;
    public static final int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 33486;
    public static final int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 33487;
    public static final int GL_VIEW_CLASS_RGTC1_RED = 33488;
    public static final int GL_VIEW_CLASS_RGTC2_RG = 33489;
    public static final int GL_VIEW_CLASS_BPTC_UNORM = 33490;
    public static final int GL_VIEW_CLASS_BPTC_FLOAT = 33491;
    public static final int GL_UNIFORM = 37601;
    public static final int GL_UNIFORM_BLOCK = 37602;
    public static final int GL_PROGRAM_INPUT = 37603;
    public static final int GL_PROGRAM_OUTPUT = 37604;
    public static final int GL_BUFFER_VARIABLE = 37605;
    public static final int GL_SHADER_STORAGE_BLOCK = 37606;
    public static final int GL_VERTEX_SUBROUTINE = 37608;
    public static final int GL_TESS_CONTROL_SUBROUTINE = 37609;
    public static final int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    public static final int GL_GEOMETRY_SUBROUTINE = 37611;
    public static final int GL_FRAGMENT_SUBROUTINE = 37612;
    public static final int GL_COMPUTE_SUBROUTINE = 37613;
    public static final int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    public static final int GL_ACTIVE_RESOURCES = 37621;
    public static final int GL_MAX_NAME_LENGTH = 37622;
    public static final int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    public static final int GL_NAME_LENGTH = 37625;
    public static final int GL_TYPE = 37626;
    public static final int GL_ARRAY_SIZE = 37627;
    public static final int GL_OFFSET = 37628;
    public static final int GL_BLOCK_INDEX = 37629;
    public static final int GL_ARRAY_STRIDE = 37630;
    public static final int GL_MATRIX_STRIDE = 37631;
    public static final int GL_IS_ROW_MAJOR = 37632;
    public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    public static final int GL_BUFFER_BINDING = 37634;
    public static final int GL_BUFFER_DATA_SIZE = 37635;
    public static final int GL_NUM_ACTIVE_VARIABLES = 37636;
    public static final int GL_ACTIVE_VARIABLES = 37637;
    public static final int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    public static final int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    public static final int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    public static final int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    public static final int GL_LOCATION = 37646;
    public static final int GL_LOCATION_INDEX = 37647;
    public static final int GL_IS_PER_PATCH = 37607;
    public static final int GL_SHADER_STORAGE_BUFFER = 37074;
    public static final int GL_SHADER_STORAGE_BUFFER_BINDING = 37075;
    public static final int GL_SHADER_STORAGE_BUFFER_START = 37076;
    public static final int GL_SHADER_STORAGE_BUFFER_SIZE = 37077;
    public static final int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 37078;
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 37079;
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 37080;
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 37081;
    public static final int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 37082;
    public static final int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 37083;
    public static final int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 37084;
    public static final int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 37085;
    public static final int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 37086;
    public static final int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 37087;
    public static final int GL_SHADER_STORAGE_BARRIER_BIT = 8192;
    public static final int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 36665;
    public static final int GL_DEPTH_STENCIL_TEXTURE_MODE = 37098;
    public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
    public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
    public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
    public static final int GL_VERTEX_BINDING_OFFSET = 33495;
    public static final int GL_VERTEX_BINDING_STRIDE = 33496;
    public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
    public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;
    
    private GL43() {
    }
    
    public static void glClearBufferData(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glClearBufferData = GLContext.getCapabilities().glClearBufferData;
        BufferChecks.checkFunctionAddress(glClearBufferData);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearBufferData(n, n2, n3, n4, MemoryUtil.getAddress(byteBuffer), glClearBufferData);
    }
    
    static native void nglClearBufferData(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glClearBufferSubData(final int n, final int n2, final long n3, final long n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final long glClearBufferSubData = GLContext.getCapabilities().glClearBufferSubData;
        BufferChecks.checkFunctionAddress(glClearBufferSubData);
        BufferChecks.checkBuffer(byteBuffer, 1);
        nglClearBufferSubData(n, n2, n3, n4, n5, n6, MemoryUtil.getAddress(byteBuffer), glClearBufferSubData);
    }
    
    static native void nglClearBufferSubData(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final long p6, final long p7);
    
    public static void glDispatchCompute(final int n, final int n2, final int n3) {
        final long glDispatchCompute = GLContext.getCapabilities().glDispatchCompute;
        BufferChecks.checkFunctionAddress(glDispatchCompute);
        nglDispatchCompute(n, n2, n3, glDispatchCompute);
    }
    
    static native void nglDispatchCompute(final int p0, final int p1, final int p2, final long p3);
    
    public static void glDispatchComputeIndirect(final long n) {
        final long glDispatchComputeIndirect = GLContext.getCapabilities().glDispatchComputeIndirect;
        BufferChecks.checkFunctionAddress(glDispatchComputeIndirect);
        nglDispatchComputeIndirect(n, glDispatchComputeIndirect);
    }
    
    static native void nglDispatchComputeIndirect(final long p0, final long p1);
    
    public static void glCopyImageSubData(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15) {
        final long glCopyImageSubData = GLContext.getCapabilities().glCopyImageSubData;
        BufferChecks.checkFunctionAddress(glCopyImageSubData);
        nglCopyImageSubData(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, glCopyImageSubData);
    }
    
    static native void nglCopyImageSubData(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final long p15);
    
    public static void glDebugMessageControl(final int n, final int n2, final int n3, final IntBuffer intBuffer, final boolean b) {
        final long glDebugMessageControl = GLContext.getCapabilities().glDebugMessageControl;
        BufferChecks.checkFunctionAddress(glDebugMessageControl);
        if (intBuffer != null) {
            BufferChecks.checkDirect(intBuffer);
        }
        nglDebugMessageControl(n, n2, n3, (intBuffer == null) ? 0 : intBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), b, glDebugMessageControl);
    }
    
    static native void nglDebugMessageControl(final int p0, final int p1, final int p2, final int p3, final long p4, final boolean p5, final long p6);
    
    public static void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        final long glDebugMessageInsert = GLContext.getCapabilities().glDebugMessageInsert;
        BufferChecks.checkFunctionAddress(glDebugMessageInsert);
        BufferChecks.checkDirect(byteBuffer);
        nglDebugMessageInsert(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glDebugMessageInsert);
    }
    
    static native void nglDebugMessageInsert(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDebugMessageInsert = capabilities.glDebugMessageInsert;
        BufferChecks.checkFunctionAddress(glDebugMessageInsert);
        nglDebugMessageInsert(n, n2, n3, n4, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glDebugMessageInsert);
    }
    
    public static void glDebugMessageCallback(final KHRDebugCallback khrDebugCallback) {
        final long glDebugMessageCallback = GLContext.getCapabilities().glDebugMessageCallback;
        BufferChecks.checkFunctionAddress(glDebugMessageCallback);
        final long n = (khrDebugCallback == null) ? 0L : CallbackUtil.createGlobalRef(khrDebugCallback.getHandler());
        CallbackUtil.registerContextCallbackKHR(n);
        nglDebugMessageCallback((khrDebugCallback == null) ? 0L : khrDebugCallback.getPointer(), n, glDebugMessageCallback);
    }
    
    static native void nglDebugMessageCallback(final long p0, final long p1, final long p2);
    
    public static int glGetDebugMessageLog(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        final long glGetDebugMessageLog = GLContext.getCapabilities().glGetDebugMessageLog;
        BufferChecks.checkFunctionAddress(glGetDebugMessageLog);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, n);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, n);
        }
        if (intBuffer3 != null) {
            BufferChecks.checkBuffer(intBuffer3, n);
        }
        if (intBuffer4 != null) {
            BufferChecks.checkBuffer(intBuffer4, n);
        }
        if (intBuffer5 != null) {
            BufferChecks.checkBuffer(intBuffer5, n);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        return nglGetDebugMessageLog(n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), MemoryUtil.getAddressSafe(intBuffer3), MemoryUtil.getAddressSafe(intBuffer4), MemoryUtil.getAddressSafe(intBuffer5), MemoryUtil.getAddressSafe(byteBuffer), glGetDebugMessageLog);
    }
    
    static native int nglGetDebugMessageLog(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8);
    
    public static void glPushDebugGroup(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glPushDebugGroup = GLContext.getCapabilities().glPushDebugGroup;
        BufferChecks.checkFunctionAddress(glPushDebugGroup);
        BufferChecks.checkDirect(byteBuffer);
        nglPushDebugGroup(n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), glPushDebugGroup);
    }
    
    static native void nglPushDebugGroup(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glPushDebugGroup(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glPushDebugGroup = capabilities.glPushDebugGroup;
        BufferChecks.checkFunctionAddress(glPushDebugGroup);
        nglPushDebugGroup(n, n2, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glPushDebugGroup);
    }
    
    public static void glPopDebugGroup() {
        final long glPopDebugGroup = GLContext.getCapabilities().glPopDebugGroup;
        BufferChecks.checkFunctionAddress(glPopDebugGroup);
        nglPopDebugGroup(glPopDebugGroup);
    }
    
    static native void nglPopDebugGroup(final long p0);
    
    public static void glObjectLabel(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glObjectLabel = GLContext.getCapabilities().glObjectLabel;
        BufferChecks.checkFunctionAddress(glObjectLabel);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        nglObjectLabel(n, n2, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), glObjectLabel);
    }
    
    static native void nglObjectLabel(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glObjectLabel(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glObjectLabel = capabilities.glObjectLabel;
        BufferChecks.checkFunctionAddress(glObjectLabel);
        nglObjectLabel(n, n2, charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glObjectLabel);
    }
    
    public static void glGetObjectLabel(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetObjectLabel = GLContext.getCapabilities().glGetObjectLabel;
        BufferChecks.checkFunctionAddress(glGetObjectLabel);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetObjectLabel(n, n2, byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetObjectLabel);
    }
    
    static native void nglGetObjectLabel(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static String glGetObjectLabel(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectLabel = capabilities.glGetObjectLabel;
        BufferChecks.checkFunctionAddress(glGetObjectLabel);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetObjectLabel(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetObjectLabel);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glObjectPtrLabel(final PointerWrapper pointerWrapper, final ByteBuffer byteBuffer) {
        final long glObjectPtrLabel = GLContext.getCapabilities().glObjectPtrLabel;
        BufferChecks.checkFunctionAddress(glObjectPtrLabel);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        nglObjectPtrLabel(pointerWrapper.getPointer(), (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), glObjectPtrLabel);
    }
    
    static native void nglObjectPtrLabel(final long p0, final int p1, final long p2, final long p3);
    
    public static void glObjectPtrLabel(final PointerWrapper pointerWrapper, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glObjectPtrLabel = capabilities.glObjectPtrLabel;
        BufferChecks.checkFunctionAddress(glObjectPtrLabel);
        nglObjectPtrLabel(pointerWrapper.getPointer(), charSequence.length(), APIUtil.getBuffer(capabilities, charSequence), glObjectPtrLabel);
    }
    
    public static void glGetObjectPtrLabel(final PointerWrapper pointerWrapper, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetObjectPtrLabel = GLContext.getCapabilities().glGetObjectPtrLabel;
        BufferChecks.checkFunctionAddress(glGetObjectPtrLabel);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(byteBuffer);
        nglGetObjectPtrLabel(pointerWrapper.getPointer(), byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddress(byteBuffer), glGetObjectPtrLabel);
    }
    
    static native void nglGetObjectPtrLabel(final long p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetObjectPtrLabel(final PointerWrapper pointerWrapper, final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetObjectPtrLabel = capabilities.glGetObjectPtrLabel;
        BufferChecks.checkFunctionAddress(glGetObjectPtrLabel);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n);
        nglGetObjectPtrLabel(pointerWrapper.getPointer(), n, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetObjectPtrLabel);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glFramebufferParameteri(final int n, final int n2, final int n3) {
        final long glFramebufferParameteri = GLContext.getCapabilities().glFramebufferParameteri;
        BufferChecks.checkFunctionAddress(glFramebufferParameteri);
        nglFramebufferParameteri(n, n2, n3, glFramebufferParameteri);
    }
    
    static native void nglFramebufferParameteri(final int p0, final int p1, final int p2, final long p3);
    
    public static void glGetFramebufferParameter(final int n, final int n2, final IntBuffer intBuffer) {
        final long glGetFramebufferParameteriv = GLContext.getCapabilities().glGetFramebufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetFramebufferParameteriv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetFramebufferParameteriv(n, n2, MemoryUtil.getAddress(intBuffer), glGetFramebufferParameteriv);
    }
    
    static native void nglGetFramebufferParameteriv(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetFramebufferParameteri(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetFramebufferParameteriv = capabilities.glGetFramebufferParameteriv;
        BufferChecks.checkFunctionAddress(glGetFramebufferParameteriv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetFramebufferParameteriv(n, n2, MemoryUtil.getAddress(bufferInt), glGetFramebufferParameteriv);
        return bufferInt.get(0);
    }
    
    public static void glGetInternalformat(final int n, final int n2, final int n3, final LongBuffer longBuffer) {
        final long glGetInternalformati64v = GLContext.getCapabilities().glGetInternalformati64v;
        BufferChecks.checkFunctionAddress(glGetInternalformati64v);
        BufferChecks.checkDirect(longBuffer);
        nglGetInternalformati64v(n, n2, n3, longBuffer.remaining(), MemoryUtil.getAddress(longBuffer), glGetInternalformati64v);
    }
    
    static native void nglGetInternalformati64v(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static long glGetInternalformati64(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetInternalformati64v = capabilities.glGetInternalformati64v;
        BufferChecks.checkFunctionAddress(glGetInternalformati64v);
        final LongBuffer bufferLong = APIUtil.getBufferLong(capabilities);
        nglGetInternalformati64v(n, n2, n3, 1, MemoryUtil.getAddress(bufferLong), glGetInternalformati64v);
        return bufferLong.get(0);
    }
    
    public static void glInvalidateTexSubImage(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glInvalidateTexSubImage = GLContext.getCapabilities().glInvalidateTexSubImage;
        BufferChecks.checkFunctionAddress(glInvalidateTexSubImage);
        nglInvalidateTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, glInvalidateTexSubImage);
    }
    
    static native void nglInvalidateTexSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glInvalidateTexImage(final int n, final int n2) {
        final long glInvalidateTexImage = GLContext.getCapabilities().glInvalidateTexImage;
        BufferChecks.checkFunctionAddress(glInvalidateTexImage);
        nglInvalidateTexImage(n, n2, glInvalidateTexImage);
    }
    
    static native void nglInvalidateTexImage(final int p0, final int p1, final long p2);
    
    public static void glInvalidateBufferSubData(final int n, final long n2, final long n3) {
        final long glInvalidateBufferSubData = GLContext.getCapabilities().glInvalidateBufferSubData;
        BufferChecks.checkFunctionAddress(glInvalidateBufferSubData);
        nglInvalidateBufferSubData(n, n2, n3, glInvalidateBufferSubData);
    }
    
    static native void nglInvalidateBufferSubData(final int p0, final long p1, final long p2, final long p3);
    
    public static void glInvalidateBufferData(final int n) {
        final long glInvalidateBufferData = GLContext.getCapabilities().glInvalidateBufferData;
        BufferChecks.checkFunctionAddress(glInvalidateBufferData);
        nglInvalidateBufferData(n, glInvalidateBufferData);
    }
    
    static native void nglInvalidateBufferData(final int p0, final long p1);
    
    public static void glInvalidateFramebuffer(final int n, final IntBuffer intBuffer) {
        final long glInvalidateFramebuffer = GLContext.getCapabilities().glInvalidateFramebuffer;
        BufferChecks.checkFunctionAddress(glInvalidateFramebuffer);
        BufferChecks.checkDirect(intBuffer);
        nglInvalidateFramebuffer(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glInvalidateFramebuffer);
    }
    
    static native void nglInvalidateFramebuffer(final int p0, final int p1, final long p2, final long p3);
    
    public static void glInvalidateSubFramebuffer(final int n, final IntBuffer intBuffer, final int n2, final int n3, final int n4, final int n5) {
        final long glInvalidateSubFramebuffer = GLContext.getCapabilities().glInvalidateSubFramebuffer;
        BufferChecks.checkFunctionAddress(glInvalidateSubFramebuffer);
        BufferChecks.checkDirect(intBuffer);
        nglInvalidateSubFramebuffer(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), n2, n3, n4, n5, glInvalidateSubFramebuffer);
    }
    
    static native void nglInvalidateSubFramebuffer(final int p0, final int p1, final long p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    public static void glMultiDrawArraysIndirect(final int n, final ByteBuffer byteBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirect = capabilities.glMultiDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n3 == 0) ? 16 : n3) * n2);
        nglMultiDrawArraysIndirect(n, MemoryUtil.getAddress(byteBuffer), n2, n3, glMultiDrawArraysIndirect);
    }
    
    static native void nglMultiDrawArraysIndirect(final int p0, final long p1, final int p2, final int p3, final long p4);
    
    public static void glMultiDrawArraysIndirect(final int n, final long n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirect = capabilities.glMultiDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirect);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawArraysIndirectBO(n, n2, n3, n4, glMultiDrawArraysIndirect);
    }
    
    static native void nglMultiDrawArraysIndirectBO(final int p0, final long p1, final int p2, final int p3, final long p4);
    
    public static void glMultiDrawArraysIndirect(final int n, final IntBuffer intBuffer, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawArraysIndirect = capabilities.glMultiDrawArraysIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawArraysIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n3 == 0) ? 4 : (n3 >> 2)) * n2);
        nglMultiDrawArraysIndirect(n, MemoryUtil.getAddress(intBuffer), n2, n3, glMultiDrawArraysIndirect);
    }
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirect = capabilities.glMultiDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(byteBuffer, ((n4 == 0) ? 20 : n4) * n3);
        nglMultiDrawElementsIndirect(n, n2, MemoryUtil.getAddress(byteBuffer), n3, n4, glMultiDrawElementsIndirect);
    }
    
    static native void nglMultiDrawElementsIndirect(final int p0, final int p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final long n3, final int n4, final int n5) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirect = capabilities.glMultiDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirect);
        GLChecks.ensureIndirectBOenabled(capabilities);
        nglMultiDrawElementsIndirectBO(n, n2, n3, n4, n5, glMultiDrawElementsIndirect);
    }
    
    static native void nglMultiDrawElementsIndirectBO(final int p0, final int p1, final long p2, final int p3, final int p4, final long p5);
    
    public static void glMultiDrawElementsIndirect(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glMultiDrawElementsIndirect = capabilities.glMultiDrawElementsIndirect;
        BufferChecks.checkFunctionAddress(glMultiDrawElementsIndirect);
        GLChecks.ensureIndirectBOdisabled(capabilities);
        BufferChecks.checkBuffer(intBuffer, ((n4 == 0) ? 5 : (n4 >> 2)) * n3);
        nglMultiDrawElementsIndirect(n, n2, MemoryUtil.getAddress(intBuffer), n3, n4, glMultiDrawElementsIndirect);
    }
    
    public static void glGetProgramInterface(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long glGetProgramInterfaceiv = GLContext.getCapabilities().glGetProgramInterfaceiv;
        BufferChecks.checkFunctionAddress(glGetProgramInterfaceiv);
        BufferChecks.checkBuffer(intBuffer, 1);
        nglGetProgramInterfaceiv(n, n2, n3, MemoryUtil.getAddress(intBuffer), glGetProgramInterfaceiv);
    }
    
    static native void nglGetProgramInterfaceiv(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static int glGetProgramInterfacei(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramInterfaceiv = capabilities.glGetProgramInterfaceiv;
        BufferChecks.checkFunctionAddress(glGetProgramInterfaceiv);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetProgramInterfaceiv(n, n2, n3, MemoryUtil.getAddress(bufferInt), glGetProgramInterfaceiv);
        return bufferInt.get(0);
    }
    
    public static int glGetProgramResourceIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetProgramResourceIndex = GLContext.getCapabilities().glGetProgramResourceIndex;
        BufferChecks.checkFunctionAddress(glGetProgramResourceIndex);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetProgramResourceIndex(n, n2, MemoryUtil.getAddress(byteBuffer), glGetProgramResourceIndex);
    }
    
    static native int nglGetProgramResourceIndex(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetProgramResourceIndex(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramResourceIndex = capabilities.glGetProgramResourceIndex;
        BufferChecks.checkFunctionAddress(glGetProgramResourceIndex);
        return nglGetProgramResourceIndex(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glGetProgramResourceIndex);
    }
    
    public static void glGetProgramResourceName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetProgramResourceName = GLContext.getCapabilities().glGetProgramResourceName;
        BufferChecks.checkFunctionAddress(glGetProgramResourceName);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        nglGetProgramResourceName(n, n2, n3, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(byteBuffer), glGetProgramResourceName);
    }
    
    static native void nglGetProgramResourceName(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5, final long p6);
    
    public static String glGetProgramResourceName(final int n, final int n2, final int n3, final int n4) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramResourceName = capabilities.glGetProgramResourceName;
        BufferChecks.checkFunctionAddress(glGetProgramResourceName);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n4);
        nglGetProgramResourceName(n, n2, n3, n4, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetProgramResourceName);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetProgramResource(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        final long glGetProgramResourceiv = GLContext.getCapabilities().glGetProgramResourceiv;
        BufferChecks.checkFunctionAddress(glGetProgramResourceiv);
        BufferChecks.checkDirect(intBuffer);
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        BufferChecks.checkDirect(intBuffer3);
        nglGetProgramResourceiv(n, n2, n3, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), intBuffer3.remaining(), MemoryUtil.getAddressSafe(intBuffer2), MemoryUtil.getAddress(intBuffer3), glGetProgramResourceiv);
    }
    
    static native void nglGetProgramResourceiv(final int p0, final int p1, final int p2, final int p3, final long p4, final int p5, final long p6, final long p7, final long p8);
    
    public static int glGetProgramResourceLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetProgramResourceLocation = GLContext.getCapabilities().glGetProgramResourceLocation;
        BufferChecks.checkFunctionAddress(glGetProgramResourceLocation);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetProgramResourceLocation(n, n2, MemoryUtil.getAddress(byteBuffer), glGetProgramResourceLocation);
    }
    
    static native int nglGetProgramResourceLocation(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetProgramResourceLocation(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramResourceLocation = capabilities.glGetProgramResourceLocation;
        BufferChecks.checkFunctionAddress(glGetProgramResourceLocation);
        return nglGetProgramResourceLocation(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glGetProgramResourceLocation);
    }
    
    public static int glGetProgramResourceLocationIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        final long glGetProgramResourceLocationIndex = GLContext.getCapabilities().glGetProgramResourceLocationIndex;
        BufferChecks.checkFunctionAddress(glGetProgramResourceLocationIndex);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return nglGetProgramResourceLocationIndex(n, n2, MemoryUtil.getAddress(byteBuffer), glGetProgramResourceLocationIndex);
    }
    
    static native int nglGetProgramResourceLocationIndex(final int p0, final int p1, final long p2, final long p3);
    
    public static int glGetProgramResourceLocationIndex(final int n, final int n2, final CharSequence charSequence) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetProgramResourceLocationIndex = capabilities.glGetProgramResourceLocationIndex;
        BufferChecks.checkFunctionAddress(glGetProgramResourceLocationIndex);
        return nglGetProgramResourceLocationIndex(n, n2, APIUtil.getBufferNT(capabilities, charSequence), glGetProgramResourceLocationIndex);
    }
    
    public static void glShaderStorageBlockBinding(final int n, final int n2, final int n3) {
        final long glShaderStorageBlockBinding = GLContext.getCapabilities().glShaderStorageBlockBinding;
        BufferChecks.checkFunctionAddress(glShaderStorageBlockBinding);
        nglShaderStorageBlockBinding(n, n2, n3, glShaderStorageBlockBinding);
    }
    
    static native void nglShaderStorageBlockBinding(final int p0, final int p1, final int p2, final long p3);
    
    public static void glTexBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long glTexBufferRange = GLContext.getCapabilities().glTexBufferRange;
        BufferChecks.checkFunctionAddress(glTexBufferRange);
        nglTexBufferRange(n, n2, n3, n4, n5, glTexBufferRange);
    }
    
    static native void nglTexBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static void glTexStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final long glTexStorage2DMultisample = GLContext.getCapabilities().glTexStorage2DMultisample;
        BufferChecks.checkFunctionAddress(glTexStorage2DMultisample);
        nglTexStorage2DMultisample(n, n2, n3, n4, n5, b, glTexStorage2DMultisample);
    }
    
    static native void nglTexStorage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6);
    
    public static void glTexStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long glTexStorage3DMultisample = GLContext.getCapabilities().glTexStorage3DMultisample;
        BufferChecks.checkFunctionAddress(glTexStorage3DMultisample);
        nglTexStorage3DMultisample(n, n2, n3, n4, n5, n6, b, glTexStorage3DMultisample);
    }
    
    static native void nglTexStorage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    public static void glTextureView(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long glTextureView = GLContext.getCapabilities().glTextureView;
        BufferChecks.checkFunctionAddress(glTextureView);
        nglTextureView(n, n2, n3, n4, n5, n6, n7, n8, glTextureView);
    }
    
    static native void nglTextureView(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    public static void glBindVertexBuffer(final int n, final int n2, final long n3, final int n4) {
        final long glBindVertexBuffer = GLContext.getCapabilities().glBindVertexBuffer;
        BufferChecks.checkFunctionAddress(glBindVertexBuffer);
        nglBindVertexBuffer(n, n2, n3, n4, glBindVertexBuffer);
    }
    
    static native void nglBindVertexBuffer(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    public static void glVertexAttribFormat(final int n, final int n2, final int n3, final boolean b, final int n4) {
        final long glVertexAttribFormat = GLContext.getCapabilities().glVertexAttribFormat;
        BufferChecks.checkFunctionAddress(glVertexAttribFormat);
        nglVertexAttribFormat(n, n2, n3, b, n4, glVertexAttribFormat);
    }
    
    static native void nglVertexAttribFormat(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5);
    
    public static void glVertexAttribIFormat(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribIFormat = GLContext.getCapabilities().glVertexAttribIFormat;
        BufferChecks.checkFunctionAddress(glVertexAttribIFormat);
        nglVertexAttribIFormat(n, n2, n3, n4, glVertexAttribIFormat);
    }
    
    static native void nglVertexAttribIFormat(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribLFormat(final int n, final int n2, final int n3, final int n4) {
        final long glVertexAttribLFormat = GLContext.getCapabilities().glVertexAttribLFormat;
        BufferChecks.checkFunctionAddress(glVertexAttribLFormat);
        nglVertexAttribLFormat(n, n2, n3, n4, glVertexAttribLFormat);
    }
    
    static native void nglVertexAttribLFormat(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    public static void glVertexAttribBinding(final int n, final int n2) {
        final long glVertexAttribBinding = GLContext.getCapabilities().glVertexAttribBinding;
        BufferChecks.checkFunctionAddress(glVertexAttribBinding);
        nglVertexAttribBinding(n, n2, glVertexAttribBinding);
    }
    
    static native void nglVertexAttribBinding(final int p0, final int p1, final long p2);
    
    public static void glVertexBindingDivisor(final int n, final int n2) {
        final long glVertexBindingDivisor = GLContext.getCapabilities().glVertexBindingDivisor;
        BufferChecks.checkFunctionAddress(glVertexBindingDivisor);
        nglVertexBindingDivisor(n, n2, glVertexBindingDivisor);
    }
    
    static native void nglVertexBindingDivisor(final int p0, final int p1, final long p2);
}
