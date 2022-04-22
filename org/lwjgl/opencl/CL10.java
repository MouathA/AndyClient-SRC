package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

public final class CL10
{
    public static final int CL_SUCCESS = 0;
    public static final int CL_DEVICE_NOT_FOUND = -1;
    public static final int CL_DEVICE_NOT_AVAILABLE = -2;
    public static final int CL_COMPILER_NOT_AVAILABLE = -3;
    public static final int CL_MEM_OBJECT_ALLOCATION_FAILURE = -4;
    public static final int CL_OUT_OF_RESOURCES = -5;
    public static final int CL_OUT_OF_HOST_MEMORY = -6;
    public static final int CL_PROFILING_INFO_NOT_AVAILABLE = -7;
    public static final int CL_MEM_COPY_OVERLAP = -8;
    public static final int CL_IMAGE_FORMAT_MISMATCH = -9;
    public static final int CL_IMAGE_FORMAT_NOT_SUPPORTED = -10;
    public static final int CL_BUILD_PROGRAM_FAILURE = -11;
    public static final int CL_MAP_FAILURE = -12;
    public static final int CL_INVALID_VALUE = -30;
    public static final int CL_INVALID_DEVICE_TYPE = -31;
    public static final int CL_INVALID_PLATFORM = -32;
    public static final int CL_INVALID_DEVICE = -33;
    public static final int CL_INVALID_CONTEXT = -34;
    public static final int CL_INVALID_QUEUE_PROPERTIES = -35;
    public static final int CL_INVALID_COMMAND_QUEUE = -36;
    public static final int CL_INVALID_HOST_PTR = -37;
    public static final int CL_INVALID_MEM_OBJECT = -38;
    public static final int CL_INVALID_IMAGE_FORMAT_DESCRIPTOR = -39;
    public static final int CL_INVALID_IMAGE_SIZE = -40;
    public static final int CL_INVALID_SAMPLER = -41;
    public static final int CL_INVALID_BINARY = -42;
    public static final int CL_INVALID_BUILD_OPTIONS = -43;
    public static final int CL_INVALID_PROGRAM = -44;
    public static final int CL_INVALID_PROGRAM_EXECUTABLE = -45;
    public static final int CL_INVALID_KERNEL_NAME = -46;
    public static final int CL_INVALID_KERNEL_DEFINITION = -47;
    public static final int CL_INVALID_KERNEL = -48;
    public static final int CL_INVALID_ARG_INDEX = -49;
    public static final int CL_INVALID_ARG_VALUE = -50;
    public static final int CL_INVALID_ARG_SIZE = -51;
    public static final int CL_INVALID_KERNEL_ARGS = -52;
    public static final int CL_INVALID_WORK_DIMENSION = -53;
    public static final int CL_INVALID_WORK_GROUP_SIZE = -54;
    public static final int CL_INVALID_WORK_ITEM_SIZE = -55;
    public static final int CL_INVALID_GLOBAL_OFFSET = -56;
    public static final int CL_INVALID_EVENT_WAIT_LIST = -57;
    public static final int CL_INVALID_EVENT = -58;
    public static final int CL_INVALID_OPERATION = -59;
    public static final int CL_INVALID_GL_OBJECT = -60;
    public static final int CL_INVALID_BUFFER_SIZE = -61;
    public static final int CL_INVALID_MIP_LEVEL = -62;
    public static final int CL_INVALID_GLOBAL_WORK_SIZE = -63;
    public static final int CL_VERSION_1_0 = 1;
    public static final int CL_FALSE = 0;
    public static final int CL_TRUE = 1;
    public static final int CL_PLATFORM_PROFILE = 2304;
    public static final int CL_PLATFORM_VERSION = 2305;
    public static final int CL_PLATFORM_NAME = 2306;
    public static final int CL_PLATFORM_VENDOR = 2307;
    public static final int CL_PLATFORM_EXTENSIONS = 2308;
    public static final int CL_DEVICE_TYPE_DEFAULT = 1;
    public static final int CL_DEVICE_TYPE_CPU = 2;
    public static final int CL_DEVICE_TYPE_GPU = 4;
    public static final int CL_DEVICE_TYPE_ACCELERATOR = 8;
    public static final int CL_DEVICE_TYPE_ALL = -1;
    public static final int CL_DEVICE_TYPE = 4096;
    public static final int CL_DEVICE_VENDOR_ID = 4097;
    public static final int CL_DEVICE_MAX_COMPUTE_UNITS = 4098;
    public static final int CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS = 4099;
    public static final int CL_DEVICE_MAX_WORK_GROUP_SIZE = 4100;
    public static final int CL_DEVICE_MAX_WORK_ITEM_SIZES = 4101;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR = 4102;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT = 4103;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_ = 4104;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG = 4105;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT = 4106;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE = 4107;
    public static final int CL_DEVICE_MAX_CLOCK_FREQUENCY = 4108;
    public static final int CL_DEVICE_ADDRESS_BITS = 4109;
    public static final int CL_DEVICE_MAX_READ_IMAGE_ARGS = 4110;
    public static final int CL_DEVICE_MAX_WRITE_IMAGE_ARGS = 4111;
    public static final int CL_DEVICE_MAX_MEM_ALLOC_SIZE = 4112;
    public static final int CL_DEVICE_IMAGE2D_MAX_WIDTH = 4113;
    public static final int CL_DEVICE_IMAGE2D_MAX_HEIGHT = 4114;
    public static final int CL_DEVICE_IMAGE3D_MAX_WIDTH = 4115;
    public static final int CL_DEVICE_IMAGE3D_MAX_HEIGHT = 4116;
    public static final int CL_DEVICE_IMAGE3D_MAX_DEPTH = 4117;
    public static final int CL_DEVICE_IMAGE_SUPPORT = 4118;
    public static final int CL_DEVICE_MAX_PARAMETER_SIZE = 4119;
    public static final int CL_DEVICE_MAX_SAMPLERS = 4120;
    public static final int CL_DEVICE_MEM_BASE_ADDR_ALIGN = 4121;
    public static final int CL_DEVICE_MIN_DATA_TYPE_ALIGN_SIZE = 4122;
    public static final int CL_DEVICE_SINGLE_FP_CONFIG = 4123;
    public static final int CL_DEVICE_GLOBAL_MEM_CACHE_TYPE = 4124;
    public static final int CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE = 4125;
    public static final int CL_DEVICE_GLOBAL_MEM_CACHE_SIZE = 4126;
    public static final int CL_DEVICE_GLOBAL_MEM_SIZE = 4127;
    public static final int CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE = 4128;
    public static final int CL_DEVICE_MAX_CONSTANT_ARGS = 4129;
    public static final int CL_DEVICE_LOCAL_MEM_TYPE = 4130;
    public static final int CL_DEVICE_LOCAL_MEM_SIZE = 4131;
    public static final int CL_DEVICE_ERROR_CORRECTION_SUPPORT = 4132;
    public static final int CL_DEVICE_PROFILING_TIMER_RESOLUTION = 4133;
    public static final int CL_DEVICE_ENDIAN_LITTLE = 4134;
    public static final int CL_DEVICE_AVAILABLE = 4135;
    public static final int CL_DEVICE_COMPILER_AVAILABLE = 4136;
    public static final int CL_DEVICE_EXECUTION_CAPABILITIES = 4137;
    public static final int CL_DEVICE_QUEUE_PROPERTIES = 4138;
    public static final int CL_DEVICE_NAME = 4139;
    public static final int CL_DEVICE_VENDOR = 4140;
    public static final int CL_DRIVER_VERSION = 4141;
    public static final int CL_DEVICE_PROFILE = 4142;
    public static final int CL_DEVICE_VERSION = 4143;
    public static final int CL_DEVICE_EXTENSIONS = 4144;
    public static final int CL_DEVICE_PLATFORM = 4145;
    public static final int CL_FP_DENORM = 1;
    public static final int CL_FP_INF_NAN = 2;
    public static final int CL_FP_ROUND_TO_NEAREST = 4;
    public static final int CL_FP_ROUND_TO_ZERO = 8;
    public static final int CL_FP_ROUND_TO_INF = 16;
    public static final int CL_FP_FMA = 32;
    public static final int CL_NONE = 0;
    public static final int CL_READ_ONLY_CACHE = 1;
    public static final int CL_READ_WRITE_CACHE = 2;
    public static final int CL_LOCAL = 1;
    public static final int CL_GLOBAL = 2;
    public static final int CL_EXEC_KERNEL = 1;
    public static final int CL_EXEC_NATIVE_KERNEL = 2;
    public static final int CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE = 1;
    public static final int CL_QUEUE_PROFILING_ENABLE = 2;
    public static final int CL_CONTEXT_REFERENCE_COUNT = 4224;
    public static final int CL_CONTEXT_DEVICES = 4225;
    public static final int CL_CONTEXT_PROPERTIES = 4226;
    public static final int CL_CONTEXT_PLATFORM = 4228;
    public static final int CL_QUEUE_CONTEXT = 4240;
    public static final int CL_QUEUE_DEVICE = 4241;
    public static final int CL_QUEUE_REFERENCE_COUNT = 4242;
    public static final int CL_QUEUE_PROPERTIES = 4243;
    public static final int CL_MEM_READ_WRITE = 1;
    public static final int CL_MEM_WRITE_ONLY = 2;
    public static final int CL_MEM_READ_ONLY = 4;
    public static final int CL_MEM_USE_HOST_PTR = 8;
    public static final int CL_MEM_ALLOC_HOST_PTR = 16;
    public static final int CL_MEM_COPY_HOST_PTR = 32;
    public static final int CL_R = 4272;
    public static final int CL_A = 4273;
    public static final int CL_RG = 4274;
    public static final int CL_RA = 4275;
    public static final int CL_RGB = 4276;
    public static final int CL_RGBA = 4277;
    public static final int CL_BGRA = 4278;
    public static final int CL_ARGB = 4279;
    public static final int CL_INTENSITY = 4280;
    public static final int CL_LUMINANCE = 4281;
    public static final int CL_SNORM_INT8 = 4304;
    public static final int CL_SNORM_INT16 = 4305;
    public static final int CL_UNORM_INT8 = 4306;
    public static final int CL_UNORM_INT16 = 4307;
    public static final int CL_UNORM_SHORT_565 = 4308;
    public static final int CL_UNORM_SHORT_555 = 4309;
    public static final int CL_UNORM_INT_101010 = 4310;
    public static final int CL_SIGNED_INT8 = 4311;
    public static final int CL_SIGNED_INT16 = 4312;
    public static final int CL_SIGNED_INT32 = 4313;
    public static final int CL_UNSIGNED_INT8 = 4314;
    public static final int CL_UNSIGNED_INT16 = 4315;
    public static final int CL_UNSIGNED_INT32 = 4316;
    public static final int CL_HALF_FLOAT = 4317;
    public static final int CL_FLOAT = 4318;
    public static final int CL_MEM_OBJECT_BUFFER = 4336;
    public static final int CL_MEM_OBJECT_IMAGE2D = 4337;
    public static final int CL_MEM_OBJECT_IMAGE3D = 4338;
    public static final int CL_MEM_TYPE = 4352;
    public static final int CL_MEM_FLAGS = 4353;
    public static final int CL_MEM_SIZE = 4354;
    public static final int CL_MEM_HOST_PTR = 4355;
    public static final int CL_MEM_MAP_COUNT = 4356;
    public static final int CL_MEM_REFERENCE_COUNT = 4357;
    public static final int CL_MEM_CONTEXT = 4358;
    public static final int CL_IMAGE_FORMAT = 4368;
    public static final int CL_IMAGE_ELEMENT_SIZE = 4369;
    public static final int CL_IMAGE_ROW_PITCH = 4370;
    public static final int CL_IMAGE_SLICE_PITCH = 4371;
    public static final int CL_IMAGE_WIDTH = 4372;
    public static final int CL_IMAGE_HEIGHT = 4373;
    public static final int CL_IMAGE_DEPTH = 4374;
    public static final int CL_ADDRESS_NONE = 4400;
    public static final int CL_ADDRESS_CLAMP_TO_EDGE = 4401;
    public static final int CL_ADDRESS_CLAMP = 4402;
    public static final int CL_ADDRESS_REPEAT = 4403;
    public static final int CL_FILTER_NEAREST = 4416;
    public static final int CL_FILTER_LINEAR = 4417;
    public static final int CL_SAMPLER_REFERENCE_COUNT = 4432;
    public static final int CL_SAMPLER_CONTEXT = 4433;
    public static final int CL_SAMPLER_NORMALIZED_COORDS = 4434;
    public static final int CL_SAMPLER_ADDRESSING_MODE = 4435;
    public static final int CL_SAMPLER_FILTER_MODE = 4436;
    public static final int CL_MAP_READ = 1;
    public static final int CL_MAP_WRITE = 2;
    public static final int CL_PROGRAM_REFERENCE_COUNT = 4448;
    public static final int CL_PROGRAM_CONTEXT = 4449;
    public static final int CL_PROGRAM_NUM_DEVICES = 4450;
    public static final int CL_PROGRAM_DEVICES = 4451;
    public static final int CL_PROGRAM_SOURCE = 4452;
    public static final int CL_PROGRAM_BINARY_SIZES = 4453;
    public static final int CL_PROGRAM_BINARIES = 4454;
    public static final int CL_PROGRAM_BUILD_STATUS = 4481;
    public static final int CL_PROGRAM_BUILD_OPTIONS = 4482;
    public static final int CL_PROGRAM_BUILD_LOG = 4483;
    public static final int CL_BUILD_SUCCESS = 0;
    public static final int CL_BUILD_NONE = -1;
    public static final int CL_BUILD_ERROR = -2;
    public static final int CL_BUILD_IN_PROGRESS = -3;
    public static final int CL_KERNEL_FUNCTION_NAME = 4496;
    public static final int CL_KERNEL_NUM_ARGS = 4497;
    public static final int CL_KERNEL_REFERENCE_COUNT = 4498;
    public static final int CL_KERNEL_CONTEXT = 4499;
    public static final int CL_KERNEL_PROGRAM = 4500;
    public static final int CL_KERNEL_WORK_GROUP_SIZE = 4528;
    public static final int CL_KERNEL_COMPILE_WORK_GROUP_SIZE = 4529;
    public static final int CL_KERNEL_LOCAL_MEM_SIZE = 4530;
    public static final int CL_EVENT_COMMAND_QUEUE = 4560;
    public static final int CL_EVENT_COMMAND_TYPE = 4561;
    public static final int CL_EVENT_REFERENCE_COUNT = 4562;
    public static final int CL_EVENT_COMMAND_EXECUTION_STATUS = 4563;
    public static final int CL_COMMAND_NDRANGE_KERNEL = 4592;
    public static final int CL_COMMAND_TASK = 4593;
    public static final int CL_COMMAND_NATIVE_KERNEL = 4594;
    public static final int CL_COMMAND_READ_BUFFER = 4595;
    public static final int CL_COMMAND_WRITE_BUFFER = 4596;
    public static final int CL_COMMAND_COPY_BUFFER = 4597;
    public static final int CL_COMMAND_READ_IMAGE = 4598;
    public static final int CL_COMMAND_WRITE_IMAGE = 4599;
    public static final int CL_COMMAND_COPY_IMAGE = 4600;
    public static final int CL_COMMAND_COPY_IMAGE_TO_BUFFER = 4601;
    public static final int CL_COMMAND_COPY_BUFFER_TO_IMAGE = 4602;
    public static final int CL_COMMAND_MAP_BUFFER = 4603;
    public static final int CL_COMMAND_MAP_IMAGE = 4604;
    public static final int CL_COMMAND_UNMAP_MEM_OBJECT = 4605;
    public static final int CL_COMMAND_MARKER = 4606;
    public static final int CL_COMMAND_ACQUIRE_GL_OBJECTS = 4607;
    public static final int CL_COMMAND_RELEASE_GL_OBJECTS = 4608;
    public static final int CL_COMPLETE = 0;
    public static final int CL_RUNNING = 1;
    public static final int CL_SUBMITTED = 2;
    public static final int CL_QUEUED = 3;
    public static final int CL_PROFILING_COMMAND_QUEUED = 4736;
    public static final int CL_PROFILING_COMMAND_SUBMIT = 4737;
    public static final int CL_PROFILING_COMMAND_START = 4738;
    public static final int CL_PROFILING_COMMAND_END = 4739;
    
    private CL10() {
    }
    
    public static int clGetPlatformIDs(final PointerBuffer pointerBuffer, IntBuffer bufferInt) {
        final long clGetPlatformIDs = CLCapabilities.clGetPlatformIDs;
        BufferChecks.checkFunctionAddress(clGetPlatformIDs);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (bufferInt != null) {
            BufferChecks.checkBuffer(bufferInt, 1);
        }
        if (bufferInt == null) {
            bufferInt = APIUtil.getBufferInt();
        }
        final int nclGetPlatformIDs = nclGetPlatformIDs((pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(bufferInt), clGetPlatformIDs);
        if (nclGetPlatformIDs == 0 && pointerBuffer != null) {
            CLPlatform.registerCLPlatforms(pointerBuffer, bufferInt);
        }
        return nclGetPlatformIDs;
    }
    
    static native int nclGetPlatformIDs(final int p0, final long p1, final long p2, final long p3);
    
    public static int clGetPlatformInfo(final CLPlatform clPlatform, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetPlatformInfo = CLCapabilities.clGetPlatformInfo;
        BufferChecks.checkFunctionAddress(clGetPlatformInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetPlatformInfo((clPlatform == null) ? 0L : clPlatform.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetPlatformInfo);
    }
    
    static native int nclGetPlatformInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clGetDeviceIDs(final CLPlatform clPlatform, final long n, final PointerBuffer pointerBuffer, IntBuffer bufferInt) {
        final long clGetDeviceIDs = CLCapabilities.clGetDeviceIDs;
        BufferChecks.checkFunctionAddress(clGetDeviceIDs);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (bufferInt != null) {
            BufferChecks.checkBuffer(bufferInt, 1);
        }
        else {
            bufferInt = APIUtil.getBufferInt();
        }
        final int nclGetDeviceIDs = nclGetDeviceIDs(clPlatform.getPointer(), n, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(bufferInt), clGetDeviceIDs);
        if (nclGetDeviceIDs == 0 && pointerBuffer != null) {
            clPlatform.registerCLDevices(pointerBuffer, bufferInt);
        }
        return nclGetDeviceIDs;
    }
    
    static native int nclGetDeviceIDs(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5);
    
    public static int clGetDeviceInfo(final CLDevice clDevice, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetDeviceInfo = CLCapabilities.clGetDeviceInfo;
        BufferChecks.checkFunctionAddress(clGetDeviceInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetDeviceInfo(clDevice.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetDeviceInfo);
    }
    
    static native int nclGetDeviceInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLContext clCreateContext(final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final CLContextCallback clContextCallback, final IntBuffer intBuffer) {
        final long clCreateContext = CLCapabilities.clCreateContext;
        BufferChecks.checkFunctionAddress(clCreateContext);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkNullTerminated(pointerBuffer);
        BufferChecks.checkBuffer(pointerBuffer2, 1);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final long contextCallback = (clContextCallback == null || clContextCallback.isCustom()) ? 0L : CallbackUtil.createGlobalRef(clContextCallback);
        final CLContext clContext = new CLContext(nclCreateContext(MemoryUtil.getAddress(pointerBuffer), pointerBuffer2.remaining(), MemoryUtil.getAddress(pointerBuffer2), (clContextCallback == null) ? 0L : clContextCallback.getPointer(), contextCallback, MemoryUtil.getAddressSafe(intBuffer), clCreateContext), APIUtil.getCLPlatform(pointerBuffer));
        final CLContext clContext2;
        if ((clContext2 = clContext) != null) {
            clContext.setContextCallback(contextCallback);
        }
        return clContext2;
    }
    
    static native long nclCreateContext(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static CLContext clCreateContext(final PointerBuffer pointerBuffer, final CLDevice clDevice, final CLContextCallback clContextCallback, final IntBuffer intBuffer) {
        final long clCreateContext = CLCapabilities.clCreateContext;
        BufferChecks.checkFunctionAddress(clCreateContext);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkNullTerminated(pointerBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final long contextCallback = (clContextCallback == null || clContextCallback.isCustom()) ? 0L : CallbackUtil.createGlobalRef(clContextCallback);
        final CLContext clContext = new CLContext(nclCreateContext(MemoryUtil.getAddress(pointerBuffer), 1, APIUtil.getPointer(clDevice), (clContextCallback == null) ? 0L : clContextCallback.getPointer(), contextCallback, MemoryUtil.getAddressSafe(intBuffer), clCreateContext), APIUtil.getCLPlatform(pointerBuffer));
        final CLContext clContext2;
        if ((clContext2 = clContext) != null) {
            clContext.setContextCallback(contextCallback);
        }
        return clContext2;
    }
    
    public static CLContext clCreateContextFromType(final PointerBuffer pointerBuffer, final long n, final CLContextCallback clContextCallback, final IntBuffer intBuffer) {
        final long clCreateContextFromType = CLCapabilities.clCreateContextFromType;
        BufferChecks.checkFunctionAddress(clCreateContextFromType);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkNullTerminated(pointerBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final long contextCallback = (clContextCallback == null || clContextCallback.isCustom()) ? 0L : CallbackUtil.createGlobalRef(clContextCallback);
        final CLContext clContext = new CLContext(nclCreateContextFromType(MemoryUtil.getAddress(pointerBuffer), n, (clContextCallback == null) ? 0L : clContextCallback.getPointer(), contextCallback, MemoryUtil.getAddressSafe(intBuffer), clCreateContextFromType), APIUtil.getCLPlatform(pointerBuffer));
        final CLContext clContext2;
        if ((clContext2 = clContext) != null) {
            clContext.setContextCallback(contextCallback);
        }
        return clContext2;
    }
    
    static native long nclCreateContextFromType(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clRetainContext(final CLContext clContext) {
        final long clRetainContext = CLCapabilities.clRetainContext;
        BufferChecks.checkFunctionAddress(clRetainContext);
        final int nclRetainContext = nclRetainContext(clContext.getPointer(), clRetainContext);
        if (nclRetainContext == 0) {
            clContext.retain();
        }
        return nclRetainContext;
    }
    
    static native int nclRetainContext(final long p0, final long p1);
    
    public static int clReleaseContext(final CLContext clContext) {
        final long clReleaseContext = CLCapabilities.clReleaseContext;
        BufferChecks.checkFunctionAddress(clReleaseContext);
        APIUtil.releaseObjects(clContext);
        final int nclReleaseContext = nclReleaseContext(clContext.getPointer(), clReleaseContext);
        if (nclReleaseContext == 0) {
            clContext.releaseImpl();
        }
        return nclReleaseContext;
    }
    
    static native int nclReleaseContext(final long p0, final long p1);
    
    public static int clGetContextInfo(final CLContext clContext, final int n, final ByteBuffer byteBuffer, PointerBuffer bufferPointer) {
        final long clGetContextInfo = CLCapabilities.clGetContextInfo;
        BufferChecks.checkFunctionAddress(clGetContextInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (bufferPointer != null) {
            BufferChecks.checkBuffer(bufferPointer, 1);
        }
        if (bufferPointer == null && APIUtil.isDevicesParam(n)) {
            bufferPointer = APIUtil.getBufferPointer();
        }
        final int nclGetContextInfo = nclGetContextInfo(clContext.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(bufferPointer), clGetContextInfo);
        if (nclGetContextInfo == 0 && byteBuffer != null && APIUtil.isDevicesParam(n)) {
            ((CLPlatform)clContext.getParent()).registerCLDevices(byteBuffer, bufferPointer);
        }
        return nclGetContextInfo;
    }
    
    static native int nclGetContextInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLCommandQueue clCreateCommandQueue(final CLContext clContext, final CLDevice clDevice, final long n, final IntBuffer intBuffer) {
        final long clCreateCommandQueue = CLCapabilities.clCreateCommandQueue;
        BufferChecks.checkFunctionAddress(clCreateCommandQueue);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLCommandQueue(nclCreateCommandQueue(clContext.getPointer(), clDevice.getPointer(), n, MemoryUtil.getAddressSafe(intBuffer), clCreateCommandQueue), clContext, clDevice);
    }
    
    static native long nclCreateCommandQueue(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    public static int clRetainCommandQueue(final CLCommandQueue clCommandQueue) {
        final long clRetainCommandQueue = CLCapabilities.clRetainCommandQueue;
        BufferChecks.checkFunctionAddress(clRetainCommandQueue);
        final int nclRetainCommandQueue = nclRetainCommandQueue(clCommandQueue.getPointer(), clRetainCommandQueue);
        if (nclRetainCommandQueue == 0) {
            clCommandQueue.retain();
        }
        return nclRetainCommandQueue;
    }
    
    static native int nclRetainCommandQueue(final long p0, final long p1);
    
    public static int clReleaseCommandQueue(final CLCommandQueue clCommandQueue) {
        final long clReleaseCommandQueue = CLCapabilities.clReleaseCommandQueue;
        BufferChecks.checkFunctionAddress(clReleaseCommandQueue);
        APIUtil.releaseObjects(clCommandQueue);
        final int nclReleaseCommandQueue = nclReleaseCommandQueue(clCommandQueue.getPointer(), clReleaseCommandQueue);
        if (nclReleaseCommandQueue == 0) {
            clCommandQueue.release();
        }
        return nclReleaseCommandQueue;
    }
    
    static native int nclReleaseCommandQueue(final long p0, final long p1);
    
    public static int clGetCommandQueueInfo(final CLCommandQueue clCommandQueue, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetCommandQueueInfo = CLCapabilities.clGetCommandQueueInfo;
        BufferChecks.checkFunctionAddress(clGetCommandQueueInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetCommandQueueInfo(clCommandQueue.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetCommandQueueInfo);
    }
    
    static native int nclGetCommandQueueInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final long n2, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, n2, 0L, MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(byteBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final DoubleBuffer doubleBuffer, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(floatBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(intBuffer);
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final LongBuffer longBuffer, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(longBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    public static CLMem clCreateBuffer(final CLContext clContext, final long n, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final long clCreateBuffer = CLCapabilities.clCreateBuffer;
        BufferChecks.checkFunctionAddress(clCreateBuffer);
        BufferChecks.checkDirect(shortBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateBuffer(clContext.getPointer(), n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateBuffer), clContext);
    }
    
    static native long nclCreateBuffer(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(byteBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final DoubleBuffer doubleBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(floatBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(intBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final LongBuffer longBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(longBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    public static int clEnqueueReadBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReadBuffer = CLCapabilities.clEnqueueReadBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueReadBuffer);
        BufferChecks.checkDirect(shortBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReadBuffer = nclEnqueueReadBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReadBuffer);
        if (nclEnqueueReadBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReadBuffer;
    }
    
    static native int nclEnqueueReadBuffer(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(byteBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final DoubleBuffer doubleBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(doubleBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(floatBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(intBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final LongBuffer longBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(longBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    public static int clEnqueueWriteBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueWriteBuffer = CLCapabilities.clEnqueueWriteBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBuffer);
        BufferChecks.checkDirect(shortBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueWriteBuffer = nclEnqueueWriteBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueWriteBuffer);
        if (nclEnqueueWriteBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueWriteBuffer;
    }
    
    static native int nclEnqueueWriteBuffer(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueCopyBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final CLMem clMem2, final long n, final long n2, final long n3, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueCopyBuffer = CLCapabilities.clEnqueueCopyBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueCopyBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueCopyBuffer = nclEnqueueCopyBuffer(clCommandQueue.getPointer(), clMem.getPointer(), clMem2.getPointer(), n, n2, n3, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueCopyBuffer);
        if (nclEnqueueCopyBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueCopyBuffer;
    }
    
    static native int nclEnqueueCopyBuffer(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static ByteBuffer clEnqueueMapBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final long n3, final long n4, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final IntBuffer intBuffer) {
        final long clEnqueueMapBuffer = CLCapabilities.clEnqueueMapBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueMapBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final ByteBuffer nclEnqueueMapBuffer = nclEnqueueMapBuffer(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, n3, n4, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(intBuffer), n4, clEnqueueMapBuffer);
        if (nclEnqueueMapBuffer != null) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return (LWJGLUtil.CHECKS && nclEnqueueMapBuffer == null) ? null : nclEnqueueMapBuffer.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nclEnqueueMapBuffer(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9, final long p10, final long p11);
    
    public static CLMem clCreateImage2D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final ByteBuffer byteBuffer2, final IntBuffer intBuffer) {
        final long clCreateImage2D = CLCapabilities.clCreateImage2D;
        BufferChecks.checkFunctionAddress(clCreateImage2D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (byteBuffer2 != null) {
            BufferChecks.checkBuffer(byteBuffer2, CLChecks.calculateImage2DSize(byteBuffer, n2, n3, n4));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage2D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(byteBuffer2), MemoryUtil.getAddressSafe(intBuffer), clCreateImage2D), clContext);
    }
    
    public static CLMem clCreateImage2D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final long clCreateImage2D = CLCapabilities.clCreateImage2D;
        BufferChecks.checkFunctionAddress(clCreateImage2D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateImage2DSize(byteBuffer, n2, n3, n4));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage2D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(floatBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage2D), clContext);
    }
    
    public static CLMem clCreateImage2D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateImage2D = CLCapabilities.clCreateImage2D;
        BufferChecks.checkFunctionAddress(clCreateImage2D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, CLChecks.calculateImage2DSize(byteBuffer, n2, n3, n4));
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLMem(nclCreateImage2D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateImage2D), clContext);
    }
    
    public static CLMem clCreateImage2D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final long clCreateImage2D = CLCapabilities.clCreateImage2D;
        BufferChecks.checkFunctionAddress(clCreateImage2D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateImage2DSize(byteBuffer, n2, n3, n4));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage2D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, MemoryUtil.getAddressSafe(shortBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage2D), clContext);
    }
    
    static native long nclCreateImage2D(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8);
    
    public static CLMem clCreateImage3D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final long n5, final long n6, final ByteBuffer byteBuffer2, final IntBuffer intBuffer) {
        final long clCreateImage3D = CLCapabilities.clCreateImage3D;
        BufferChecks.checkFunctionAddress(clCreateImage3D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (byteBuffer2 != null) {
            BufferChecks.checkBuffer(byteBuffer2, CLChecks.calculateImage3DSize(byteBuffer, n2, n3, n3, n5, n6));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage3D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, n6, MemoryUtil.getAddressSafe(byteBuffer2), MemoryUtil.getAddressSafe(intBuffer), clCreateImage3D), clContext);
    }
    
    public static CLMem clCreateImage3D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final long n5, final long n6, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final long clCreateImage3D = CLCapabilities.clCreateImage3D;
        BufferChecks.checkFunctionAddress(clCreateImage3D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (floatBuffer != null) {
            BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateImage3DSize(byteBuffer, n2, n3, n3, n5, n6));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage3D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, n6, MemoryUtil.getAddressSafe(floatBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage3D), clContext);
    }
    
    public static CLMem clCreateImage3D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final long n5, final long n6, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateImage3D = CLCapabilities.clCreateImage3D;
        BufferChecks.checkFunctionAddress(clCreateImage3D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, CLChecks.calculateImage3DSize(byteBuffer, n2, n3, n3, n5, n6));
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLMem(nclCreateImage3D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, n6, MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateImage3D), clContext);
    }
    
    public static CLMem clCreateImage3D(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final long n2, final long n3, final long n4, final long n5, final long n6, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final long clCreateImage3D = CLCapabilities.clCreateImage3D;
        BufferChecks.checkFunctionAddress(clCreateImage3D);
        BufferChecks.checkBuffer(byteBuffer, 8);
        if (shortBuffer != null) {
            BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateImage3DSize(byteBuffer, n2, n3, n3, n5, n6));
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage3D(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), n2, n3, n4, n5, n6, MemoryUtil.getAddressSafe(shortBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage3D), clContext);
    }
    
    static native long nclCreateImage3D(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8, final long p9, final long p10);
    
    public static int clGetSupportedImageFormats(final CLContext clContext, final long n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clGetSupportedImageFormats = CLCapabilities.clGetSupportedImageFormats;
        BufferChecks.checkFunctionAddress(clGetSupportedImageFormats);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return nclGetSupportedImageFormats(clContext.getPointer(), n, n2, ((byteBuffer == null) ? 0 : byteBuffer.remaining()) / 8, MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(intBuffer), clGetSupportedImageFormats);
    }
    
    static native int nclGetSupportedImageFormats(final long p0, final long p1, final int p2, final int p3, final long p4, final long p5, final long p6);
    
    public static int clEnqueueReadImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueReadImage = CLCapabilities.clEnqueueReadImage;
        BufferChecks.checkFunctionAddress(clEnqueueReadImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(byteBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueReadImage = nclEnqueueReadImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(byteBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueReadImage);
        if (nclEnqueueReadImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueReadImage;
    }
    
    public static int clEnqueueReadImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueReadImage = CLCapabilities.clEnqueueReadImage;
        BufferChecks.checkFunctionAddress(clEnqueueReadImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueReadImage = nclEnqueueReadImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(floatBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueReadImage);
        if (nclEnqueueReadImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueReadImage;
    }
    
    public static int clEnqueueReadImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueReadImage = CLCapabilities.clEnqueueReadImage;
        BufferChecks.checkFunctionAddress(clEnqueueReadImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(intBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueReadImage = nclEnqueueReadImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(intBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueReadImage);
        if (nclEnqueueReadImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueReadImage;
    }
    
    public static int clEnqueueReadImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueReadImage = CLCapabilities.clEnqueueReadImage;
        BufferChecks.checkFunctionAddress(clEnqueueReadImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueReadImage = nclEnqueueReadImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(shortBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueReadImage);
        if (nclEnqueueReadImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueReadImage;
    }
    
    static native int nclEnqueueReadImage(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final int p8, final long p9, final long p10, final long p11);
    
    public static int clEnqueueWriteImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueWriteImage = CLCapabilities.clEnqueueWriteImage;
        BufferChecks.checkFunctionAddress(clEnqueueWriteImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(byteBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueWriteImage = nclEnqueueWriteImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(byteBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueWriteImage);
        if (nclEnqueueWriteImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueWriteImage;
    }
    
    public static int clEnqueueWriteImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueWriteImage = CLCapabilities.clEnqueueWriteImage;
        BufferChecks.checkFunctionAddress(clEnqueueWriteImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueWriteImage = nclEnqueueWriteImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(floatBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueWriteImage);
        if (nclEnqueueWriteImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueWriteImage;
    }
    
    public static int clEnqueueWriteImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final IntBuffer intBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueWriteImage = CLCapabilities.clEnqueueWriteImage;
        BufferChecks.checkFunctionAddress(clEnqueueWriteImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(intBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueWriteImage = nclEnqueueWriteImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(intBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueWriteImage);
        if (nclEnqueueWriteImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueWriteImage;
    }
    
    public static int clEnqueueWriteImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n2, final long n3, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueWriteImage = CLCapabilities.clEnqueueWriteImage;
        BufferChecks.checkFunctionAddress(clEnqueueWriteImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateImageSize(pointerBuffer2, n2, n3));
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueWriteImage = nclEnqueueWriteImage(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n2, n3, MemoryUtil.getAddress(shortBuffer), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueWriteImage);
        if (nclEnqueueWriteImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueWriteImage;
    }
    
    static native int nclEnqueueWriteImage(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final int p8, final long p9, final long p10, final long p11);
    
    public static int clEnqueueCopyImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final CLMem clMem2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueCopyImage = CLCapabilities.clEnqueueCopyImage;
        BufferChecks.checkFunctionAddress(clEnqueueCopyImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueCopyImage = nclEnqueueCopyImage(clCommandQueue.getPointer(), clMem.getPointer(), clMem2.getPointer(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueCopyImage);
        if (nclEnqueueCopyImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueCopyImage;
    }
    
    static native int nclEnqueueCopyImage(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueCopyImageToBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final CLMem clMem2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final long n, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueCopyImageToBuffer = CLCapabilities.clEnqueueCopyImageToBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueCopyImageToBuffer);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueCopyImageToBuffer = nclEnqueueCopyImageToBuffer(clCommandQueue.getPointer(), clMem.getPointer(), clMem2.getPointer(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), n, (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueCopyImageToBuffer);
        if (nclEnqueueCopyImageToBuffer == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueCopyImageToBuffer;
    }
    
    static native int nclEnqueueCopyImageToBuffer(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueCopyBufferToImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final CLMem clMem2, final long n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueCopyBufferToImage = CLCapabilities.clEnqueueCopyBufferToImage;
        BufferChecks.checkFunctionAddress(clEnqueueCopyBufferToImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        final int nclEnqueueCopyBufferToImage = nclEnqueueCopyBufferToImage(clCommandQueue.getPointer(), clMem.getPointer(), clMem2.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueCopyBufferToImage);
        if (nclEnqueueCopyBufferToImage == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer4);
        }
        return nclEnqueueCopyBufferToImage;
    }
    
    static native int nclEnqueueCopyBufferToImage(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static ByteBuffer clEnqueueMapImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final long n2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5, final PointerBuffer pointerBuffer6, final IntBuffer intBuffer) {
        final long clEnqueueMapImage = CLCapabilities.clEnqueueMapImage;
        BufferChecks.checkFunctionAddress(clEnqueueMapImage);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 1);
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkDirect(pointerBuffer5);
        }
        if (pointerBuffer6 != null) {
            BufferChecks.checkBuffer(pointerBuffer6, 1);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final ByteBuffer nclEnqueueMapImage = nclEnqueueMapImage(clCommandQueue.getPointer(), clMem.getPointer(), n, n2, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), (pointerBuffer5 == null) ? 0 : pointerBuffer5.remaining(), MemoryUtil.getAddressSafe(pointerBuffer5), MemoryUtil.getAddressSafe(pointerBuffer6), MemoryUtil.getAddressSafe(intBuffer), clEnqueueMapImage);
        if (nclEnqueueMapImage != null) {
            clCommandQueue.registerCLEvent(pointerBuffer6);
        }
        return (LWJGLUtil.CHECKS && nclEnqueueMapImage == null) ? null : nclEnqueueMapImage.order(ByteOrder.nativeOrder());
    }
    
    static native ByteBuffer nclEnqueueMapImage(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final int p8, final long p9, final long p10, final long p11, final long p12);
    
    public static int clGetImageInfo(final CLMem clMem, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetImageInfo = CLCapabilities.clGetImageInfo;
        BufferChecks.checkFunctionAddress(clGetImageInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetImageInfo(clMem.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetImageInfo);
    }
    
    static native int nclGetImageInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clRetainMemObject(final CLMem clMem) {
        final long clRetainMemObject = CLCapabilities.clRetainMemObject;
        BufferChecks.checkFunctionAddress(clRetainMemObject);
        final int nclRetainMemObject = nclRetainMemObject(clMem.getPointer(), clRetainMemObject);
        if (nclRetainMemObject == 0) {
            clMem.retain();
        }
        return nclRetainMemObject;
    }
    
    static native int nclRetainMemObject(final long p0, final long p1);
    
    public static int clReleaseMemObject(final CLMem clMem) {
        final long clReleaseMemObject = CLCapabilities.clReleaseMemObject;
        BufferChecks.checkFunctionAddress(clReleaseMemObject);
        final int nclReleaseMemObject = nclReleaseMemObject(clMem.getPointer(), clReleaseMemObject);
        if (nclReleaseMemObject == 0) {
            clMem.release();
        }
        return nclReleaseMemObject;
    }
    
    static native int nclReleaseMemObject(final long p0, final long p1);
    
    public static int clEnqueueUnmapMemObject(final CLCommandQueue clCommandQueue, final CLMem clMem, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueUnmapMemObject = CLCapabilities.clEnqueueUnmapMemObject;
        BufferChecks.checkFunctionAddress(clEnqueueUnmapMemObject);
        BufferChecks.checkDirect(byteBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueUnmapMemObject = nclEnqueueUnmapMemObject(clCommandQueue.getPointer(), clMem.getPointer(), MemoryUtil.getAddress(byteBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueUnmapMemObject);
        if (nclEnqueueUnmapMemObject == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueUnmapMemObject;
    }
    
    static native int nclEnqueueUnmapMemObject(final long p0, final long p1, final long p2, final int p3, final long p4, final long p5, final long p6);
    
    public static int clGetMemObjectInfo(final CLMem clMem, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetMemObjectInfo = CLCapabilities.clGetMemObjectInfo;
        BufferChecks.checkFunctionAddress(clGetMemObjectInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetMemObjectInfo(clMem.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetMemObjectInfo);
    }
    
    static native int nclGetMemObjectInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLSampler clCreateSampler(final CLContext clContext, final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final long clCreateSampler = CLCapabilities.clCreateSampler;
        BufferChecks.checkFunctionAddress(clCreateSampler);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLSampler(nclCreateSampler(clContext.getPointer(), n, n2, n3, MemoryUtil.getAddressSafe(intBuffer), clCreateSampler), clContext);
    }
    
    static native long nclCreateSampler(final long p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    public static int clRetainSampler(final CLSampler clSampler) {
        final long clRetainSampler = CLCapabilities.clRetainSampler;
        BufferChecks.checkFunctionAddress(clRetainSampler);
        final int nclRetainSampler = nclRetainSampler(clSampler.getPointer(), clRetainSampler);
        if (nclRetainSampler == 0) {
            clSampler.retain();
        }
        return nclRetainSampler;
    }
    
    static native int nclRetainSampler(final long p0, final long p1);
    
    public static int clReleaseSampler(final CLSampler clSampler) {
        final long clReleaseSampler = CLCapabilities.clReleaseSampler;
        BufferChecks.checkFunctionAddress(clReleaseSampler);
        final int nclReleaseSampler = nclReleaseSampler(clSampler.getPointer(), clReleaseSampler);
        if (nclReleaseSampler == 0) {
            clSampler.release();
        }
        return nclReleaseSampler;
    }
    
    static native int nclReleaseSampler(final long p0, final long p1);
    
    public static int clGetSamplerInfo(final CLSampler clSampler, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetSamplerInfo = CLCapabilities.clGetSamplerInfo;
        BufferChecks.checkFunctionAddress(clGetSamplerInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetSamplerInfo(clSampler.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetSamplerInfo);
    }
    
    static native int nclGetSamplerInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithSource(final CLContext clContext, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clCreateProgramWithSource = CLCapabilities.clCreateProgramWithSource;
        BufferChecks.checkFunctionAddress(clCreateProgramWithSource);
        BufferChecks.checkDirect(byteBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithSource(clContext.getPointer(), 1, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithSource), clContext);
    }
    
    static native long nclCreateProgramWithSource(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithSource(final CLContext clContext, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final long clCreateProgramWithSource = CLCapabilities.clCreateProgramWithSource;
        BufferChecks.checkFunctionAddress(clCreateProgramWithSource);
        BufferChecks.checkBuffer(byteBuffer, APIUtil.getSize(pointerBuffer));
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithSource2(clContext.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithSource), clContext);
    }
    
    static native long nclCreateProgramWithSource2(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithSource(final CLContext clContext, final ByteBuffer[] array, final IntBuffer intBuffer) {
        final long clCreateProgramWithSource = CLCapabilities.clCreateProgramWithSource;
        BufferChecks.checkFunctionAddress(clCreateProgramWithSource);
        BufferChecks.checkArray(array, 1);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithSource3(clContext.getPointer(), array.length, array, APIUtil.getLengths(array), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithSource), clContext);
    }
    
    static native long nclCreateProgramWithSource3(final long p0, final int p1, final ByteBuffer[] p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithSource(final CLContext clContext, final CharSequence charSequence, final IntBuffer intBuffer) {
        final long clCreateProgramWithSource = CLCapabilities.clCreateProgramWithSource;
        BufferChecks.checkFunctionAddress(clCreateProgramWithSource);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithSource(clContext.getPointer(), 1, APIUtil.getBuffer(charSequence), charSequence.length(), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithSource), clContext);
    }
    
    public static CLProgram clCreateProgramWithSource(final CLContext clContext, final CharSequence[] array, final IntBuffer intBuffer) {
        final long clCreateProgramWithSource = CLCapabilities.clCreateProgramWithSource;
        BufferChecks.checkFunctionAddress(clCreateProgramWithSource);
        BufferChecks.checkArray(array);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithSource4(clContext.getPointer(), array.length, APIUtil.getBuffer(array), APIUtil.getLengths(array), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithSource), clContext);
    }
    
    static native long nclCreateProgramWithSource4(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithBinary(final CLContext clContext, final CLDevice clDevice, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateProgramWithBinary = CLCapabilities.clCreateProgramWithBinary;
        BufferChecks.checkFunctionAddress(clCreateProgramWithBinary);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkBuffer(intBuffer, 1);
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLProgram(nclCreateProgramWithBinary(clContext.getPointer(), 1, clDevice.getPointer(), byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateProgramWithBinary), clContext);
    }
    
    static native long nclCreateProgramWithBinary(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static CLProgram clCreateProgramWithBinary(final CLContext clContext, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateProgramWithBinary = CLCapabilities.clCreateProgramWithBinary;
        BufferChecks.checkFunctionAddress(clCreateProgramWithBinary);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        BufferChecks.checkBuffer(pointerBuffer2, pointerBuffer.remaining());
        BufferChecks.checkBuffer(byteBuffer, APIUtil.getSize(pointerBuffer2));
        BufferChecks.checkBuffer(intBuffer, pointerBuffer.remaining());
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLProgram(nclCreateProgramWithBinary2(clContext.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateProgramWithBinary), clContext);
    }
    
    static native long nclCreateProgramWithBinary2(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7);
    
    public static CLProgram clCreateProgramWithBinary(final CLContext clContext, final PointerBuffer pointerBuffer, final ByteBuffer[] array, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateProgramWithBinary = CLCapabilities.clCreateProgramWithBinary;
        BufferChecks.checkFunctionAddress(clCreateProgramWithBinary);
        BufferChecks.checkBuffer(pointerBuffer, array.length);
        BufferChecks.checkArray(array, 1);
        BufferChecks.checkBuffer(intBuffer, array.length);
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLProgram(nclCreateProgramWithBinary3(clContext.getPointer(), array.length, MemoryUtil.getAddress(pointerBuffer), APIUtil.getLengths(array), array, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateProgramWithBinary), clContext);
    }
    
    static native long nclCreateProgramWithBinary3(final long p0, final int p1, final long p2, final long p3, final ByteBuffer[] p4, final long p5, final long p6, final long p7);
    
    public static int clRetainProgram(final CLProgram clProgram) {
        final long clRetainProgram = CLCapabilities.clRetainProgram;
        BufferChecks.checkFunctionAddress(clRetainProgram);
        final int nclRetainProgram = nclRetainProgram(clProgram.getPointer(), clRetainProgram);
        if (nclRetainProgram == 0) {
            clProgram.retain();
        }
        return nclRetainProgram;
    }
    
    static native int nclRetainProgram(final long p0, final long p1);
    
    public static int clReleaseProgram(final CLProgram clProgram) {
        final long clReleaseProgram = CLCapabilities.clReleaseProgram;
        BufferChecks.checkFunctionAddress(clReleaseProgram);
        APIUtil.releaseObjects(clProgram);
        final int nclReleaseProgram = nclReleaseProgram(clProgram.getPointer(), clReleaseProgram);
        if (nclReleaseProgram == 0) {
            clProgram.release();
        }
        return nclReleaseProgram;
    }
    
    static native int nclReleaseProgram(final long p0, final long p1);
    
    public static int clBuildProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final CLBuildProgramCallback clBuildProgramCallback) {
        final long clBuildProgram = CLCapabilities.clBuildProgram;
        BufferChecks.checkFunctionAddress(clBuildProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        final long globalRef = CallbackUtil.createGlobalRef(clBuildProgramCallback);
        if (clBuildProgramCallback != null) {
            clBuildProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclBuildProgram(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddress(byteBuffer), (clBuildProgramCallback == null) ? 0L : clBuildProgramCallback.getPointer(), globalRef, clBuildProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclBuildProgram(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static int clBuildProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final CharSequence charSequence, final CLBuildProgramCallback clBuildProgramCallback) {
        final long clBuildProgram = CLCapabilities.clBuildProgram;
        BufferChecks.checkFunctionAddress(clBuildProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        final long globalRef = CallbackUtil.createGlobalRef(clBuildProgramCallback);
        if (clBuildProgramCallback != null) {
            clBuildProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclBuildProgram(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), APIUtil.getBufferNT(charSequence), (clBuildProgramCallback == null) ? 0L : clBuildProgramCallback.getPointer(), globalRef, clBuildProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    public static int clBuildProgram(final CLProgram clProgram, final CLDevice clDevice, final CharSequence charSequence, final CLBuildProgramCallback clBuildProgramCallback) {
        final long clBuildProgram = CLCapabilities.clBuildProgram;
        BufferChecks.checkFunctionAddress(clBuildProgram);
        final long globalRef = CallbackUtil.createGlobalRef(clBuildProgramCallback);
        if (clBuildProgramCallback != null) {
            clBuildProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclBuildProgram(clProgram.getPointer(), 1, APIUtil.getPointer(clDevice), APIUtil.getBufferNT(charSequence), (clBuildProgramCallback == null) ? 0L : clBuildProgramCallback.getPointer(), globalRef, clBuildProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    public static int clUnloadCompiler() {
        final long clUnloadCompiler = CLCapabilities.clUnloadCompiler;
        BufferChecks.checkFunctionAddress(clUnloadCompiler);
        return nclUnloadCompiler(clUnloadCompiler);
    }
    
    static native int nclUnloadCompiler(final long p0);
    
    public static int clGetProgramInfo(final CLProgram clProgram, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetProgramInfo = CLCapabilities.clGetProgramInfo;
        BufferChecks.checkFunctionAddress(clGetProgramInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetProgramInfo(clProgram.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetProgramInfo);
    }
    
    static native int nclGetProgramInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clGetProgramInfo(final CLProgram clProgram, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer2) {
        final long clGetProgramInfo = CLCapabilities.clGetProgramInfo;
        BufferChecks.checkFunctionAddress(clGetProgramInfo);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        BufferChecks.checkBuffer(byteBuffer, APIUtil.getSize(pointerBuffer));
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        return nclGetProgramInfo2(clProgram.getPointer(), 4454, pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clGetProgramInfo);
    }
    
    static native int nclGetProgramInfo2(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static int clGetProgramInfo(final CLProgram clProgram, final ByteBuffer[] array, final PointerBuffer pointerBuffer) {
        final long clGetProgramInfo = CLCapabilities.clGetProgramInfo;
        BufferChecks.checkFunctionAddress(clGetProgramInfo);
        BufferChecks.checkArray(array);
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetProgramInfo3(clProgram.getPointer(), 4454, array.length, array, MemoryUtil.getAddressSafe(pointerBuffer), clGetProgramInfo);
    }
    
    static native int nclGetProgramInfo3(final long p0, final int p1, final long p2, final ByteBuffer[] p3, final long p4, final long p5);
    
    public static int clGetProgramBuildInfo(final CLProgram clProgram, final CLDevice clDevice, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetProgramBuildInfo = CLCapabilities.clGetProgramBuildInfo;
        BufferChecks.checkFunctionAddress(clGetProgramBuildInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetProgramBuildInfo(clProgram.getPointer(), clDevice.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetProgramBuildInfo);
    }
    
    static native int nclGetProgramBuildInfo(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static CLKernel clCreateKernel(final CLProgram clProgram, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clCreateKernel = CLCapabilities.clCreateKernel;
        BufferChecks.checkFunctionAddress(clCreateKernel);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLKernel(nclCreateKernel(clProgram.getPointer(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateKernel), clProgram);
    }
    
    static native long nclCreateKernel(final long p0, final long p1, final long p2, final long p3);
    
    public static CLKernel clCreateKernel(final CLProgram clProgram, final CharSequence charSequence, final IntBuffer intBuffer) {
        final long clCreateKernel = CLCapabilities.clCreateKernel;
        BufferChecks.checkFunctionAddress(clCreateKernel);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLKernel(nclCreateKernel(clProgram.getPointer(), APIUtil.getBufferNT(charSequence), MemoryUtil.getAddressSafe(intBuffer), clCreateKernel), clProgram);
    }
    
    public static int clCreateKernelsInProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final long clCreateKernelsInProgram = CLCapabilities.clCreateKernelsInProgram;
        BufferChecks.checkFunctionAddress(clCreateKernelsInProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final int nclCreateKernelsInProgram = nclCreateKernelsInProgram(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateKernelsInProgram);
        if (nclCreateKernelsInProgram == 0 && pointerBuffer != null) {
            clProgram.registerCLKernels(pointerBuffer);
        }
        return nclCreateKernelsInProgram;
    }
    
    static native int nclCreateKernelsInProgram(final long p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int clRetainKernel(final CLKernel clKernel) {
        final long clRetainKernel = CLCapabilities.clRetainKernel;
        BufferChecks.checkFunctionAddress(clRetainKernel);
        final int nclRetainKernel = nclRetainKernel(clKernel.getPointer(), clRetainKernel);
        if (nclRetainKernel == 0) {
            clKernel.retain();
        }
        return nclRetainKernel;
    }
    
    static native int nclRetainKernel(final long p0, final long p1);
    
    public static int clReleaseKernel(final CLKernel clKernel) {
        final long clReleaseKernel = CLCapabilities.clReleaseKernel;
        BufferChecks.checkFunctionAddress(clReleaseKernel);
        final int nclReleaseKernel = nclReleaseKernel(clKernel.getPointer(), clReleaseKernel);
        if (nclReleaseKernel == 0) {
            clKernel.release();
        }
        return nclReleaseKernel;
    }
    
    static native int nclReleaseKernel(final long p0, final long p1);
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final long n2) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        return nclSetKernelArg(clKernel.getPointer(), n, n2, 0L, clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final ByteBuffer byteBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(byteBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, byteBuffer.remaining(), MemoryUtil.getAddress(byteBuffer), clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final DoubleBuffer doubleBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(doubleBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, doubleBuffer.remaining() << 3, MemoryUtil.getAddress(doubleBuffer), clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final FloatBuffer floatBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(floatBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, floatBuffer.remaining() << 2, MemoryUtil.getAddress(floatBuffer), clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final IntBuffer intBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(intBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, intBuffer.remaining() << 2, MemoryUtil.getAddress(intBuffer), clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final LongBuffer longBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(longBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, longBuffer.remaining() << 3, MemoryUtil.getAddress(longBuffer), clSetKernelArg);
    }
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final ShortBuffer shortBuffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        BufferChecks.checkDirect(shortBuffer);
        return nclSetKernelArg(clKernel.getPointer(), n, shortBuffer.remaining() << 1, MemoryUtil.getAddress(shortBuffer), clSetKernelArg);
    }
    
    static native int nclSetKernelArg(final long p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int clSetKernelArg(final CLKernel clKernel, final int n, final CLObject clObject) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        return nclSetKernelArg(clKernel.getPointer(), n, PointerBuffer.getPointerSize(), APIUtil.getPointerSafe(clObject), clSetKernelArg);
    }
    
    static int clSetKernelArg(final CLKernel clKernel, final int n, final long n2, final Buffer buffer) {
        final long clSetKernelArg = CLCapabilities.clSetKernelArg;
        BufferChecks.checkFunctionAddress(clSetKernelArg);
        return nclSetKernelArg(clKernel.getPointer(), n, n2, MemoryUtil.getAddress0(buffer), clSetKernelArg);
    }
    
    public static int clGetKernelInfo(final CLKernel clKernel, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetKernelInfo = CLCapabilities.clGetKernelInfo;
        BufferChecks.checkFunctionAddress(clGetKernelInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetKernelInfo(clKernel.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetKernelInfo);
    }
    
    static native int nclGetKernelInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clGetKernelWorkGroupInfo(final CLKernel clKernel, final CLDevice clDevice, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetKernelWorkGroupInfo = CLCapabilities.clGetKernelWorkGroupInfo;
        BufferChecks.checkFunctionAddress(clGetKernelWorkGroupInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetKernelWorkGroupInfo(clKernel.getPointer(), (clDevice == null) ? 0L : clDevice.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetKernelWorkGroupInfo);
    }
    
    static native int nclGetKernelWorkGroupInfo(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static int clEnqueueNDRangeKernel(final CLCommandQueue clCommandQueue, final CLKernel clKernel, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueNDRangeKernel = CLCapabilities.clEnqueueNDRangeKernel;
        BufferChecks.checkFunctionAddress(clEnqueueNDRangeKernel);
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, n);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, n);
        }
        if (pointerBuffer3 != null) {
            BufferChecks.checkBuffer(pointerBuffer3, n);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueNDRangeKernel = nclEnqueueNDRangeKernel(clCommandQueue.getPointer(), clKernel.getPointer(), n, MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(pointerBuffer3), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueNDRangeKernel);
        if (nclEnqueueNDRangeKernel == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueNDRangeKernel;
    }
    
    static native int nclEnqueueNDRangeKernel(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueTask(final CLCommandQueue clCommandQueue, final CLKernel clKernel, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueTask = CLCapabilities.clEnqueueTask;
        BufferChecks.checkFunctionAddress(clEnqueueTask);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueTask = nclEnqueueTask(clCommandQueue.getPointer(), clKernel.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueTask);
        if (nclEnqueueTask == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueTask;
    }
    
    static native int nclEnqueueTask(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5);
    
    public static int clEnqueueNativeKernel(final CLCommandQueue clCommandQueue, final CLNativeKernel clNativeKernel, final CLMem[] array, final long[] array2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueNativeKernel = CLCapabilities.clEnqueueNativeKernel;
        BufferChecks.checkFunctionAddress(clEnqueueNativeKernel);
        if (array != null) {
            BufferChecks.checkArray(array, 1);
        }
        if (array2 != null) {
            BufferChecks.checkArray(array2, array.length);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final long globalRef = CallbackUtil.createGlobalRef(clNativeKernel);
        final ByteBuffer nativeKernelArgs = APIUtil.getNativeKernelArgs(globalRef, array, array2);
        nclEnqueueNativeKernel(clCommandQueue.getPointer(), clNativeKernel.getPointer(), MemoryUtil.getAddress0(nativeKernelArgs), nativeKernelArgs.remaining(), (array == null) ? 0 : array.length, array, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueNativeKernel);
        if (!false) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclEnqueueNativeKernel(final long p0, final long p1, final long p2, final long p3, final int p4, final CLMem[] p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clWaitForEvents(final PointerBuffer pointerBuffer) {
        final long clWaitForEvents = CLCapabilities.clWaitForEvents;
        BufferChecks.checkFunctionAddress(clWaitForEvents);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        return nclWaitForEvents(pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), clWaitForEvents);
    }
    
    static native int nclWaitForEvents(final int p0, final long p1, final long p2);
    
    public static int clWaitForEvents(final CLEvent clEvent) {
        final long clWaitForEvents = CLCapabilities.clWaitForEvents;
        BufferChecks.checkFunctionAddress(clWaitForEvents);
        return nclWaitForEvents(1, APIUtil.getPointer(clEvent), clWaitForEvents);
    }
    
    public static int clGetEventInfo(final CLEvent clEvent, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetEventInfo = CLCapabilities.clGetEventInfo;
        BufferChecks.checkFunctionAddress(clGetEventInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetEventInfo(clEvent.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetEventInfo);
    }
    
    static native int nclGetEventInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clRetainEvent(final CLEvent clEvent) {
        final long clRetainEvent = CLCapabilities.clRetainEvent;
        BufferChecks.checkFunctionAddress(clRetainEvent);
        final int nclRetainEvent = nclRetainEvent(clEvent.getPointer(), clRetainEvent);
        if (nclRetainEvent == 0) {
            clEvent.retain();
        }
        return nclRetainEvent;
    }
    
    static native int nclRetainEvent(final long p0, final long p1);
    
    public static int clReleaseEvent(final CLEvent clEvent) {
        final long clReleaseEvent = CLCapabilities.clReleaseEvent;
        BufferChecks.checkFunctionAddress(clReleaseEvent);
        final int nclReleaseEvent = nclReleaseEvent(clEvent.getPointer(), clReleaseEvent);
        if (nclReleaseEvent == 0) {
            clEvent.release();
        }
        return nclReleaseEvent;
    }
    
    static native int nclReleaseEvent(final long p0, final long p1);
    
    public static int clEnqueueMarker(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer) {
        final long clEnqueueMarker = CLCapabilities.clEnqueueMarker;
        BufferChecks.checkFunctionAddress(clEnqueueMarker);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        final int nclEnqueueMarker = nclEnqueueMarker(clCommandQueue.getPointer(), MemoryUtil.getAddress(pointerBuffer), clEnqueueMarker);
        if (nclEnqueueMarker == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer);
        }
        return nclEnqueueMarker;
    }
    
    static native int nclEnqueueMarker(final long p0, final long p1, final long p2);
    
    public static int clEnqueueBarrier(final CLCommandQueue clCommandQueue) {
        final long clEnqueueBarrier = CLCapabilities.clEnqueueBarrier;
        BufferChecks.checkFunctionAddress(clEnqueueBarrier);
        return nclEnqueueBarrier(clCommandQueue.getPointer(), clEnqueueBarrier);
    }
    
    static native int nclEnqueueBarrier(final long p0, final long p1);
    
    public static int clEnqueueWaitForEvents(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer) {
        final long clEnqueueWaitForEvents = CLCapabilities.clEnqueueWaitForEvents;
        BufferChecks.checkFunctionAddress(clEnqueueWaitForEvents);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        return nclEnqueueWaitForEvents(clCommandQueue.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), clEnqueueWaitForEvents);
    }
    
    static native int nclEnqueueWaitForEvents(final long p0, final int p1, final long p2, final long p3);
    
    public static int clEnqueueWaitForEvents(final CLCommandQueue clCommandQueue, final CLEvent clEvent) {
        final long clEnqueueWaitForEvents = CLCapabilities.clEnqueueWaitForEvents;
        BufferChecks.checkFunctionAddress(clEnqueueWaitForEvents);
        return nclEnqueueWaitForEvents(clCommandQueue.getPointer(), 1, APIUtil.getPointer(clEvent), clEnqueueWaitForEvents);
    }
    
    public static int clGetEventProfilingInfo(final CLEvent clEvent, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetEventProfilingInfo = CLCapabilities.clGetEventProfilingInfo;
        BufferChecks.checkFunctionAddress(clGetEventProfilingInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetEventProfilingInfo(clEvent.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetEventProfilingInfo);
    }
    
    static native int nclGetEventProfilingInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clFlush(final CLCommandQueue clCommandQueue) {
        final long clFlush = CLCapabilities.clFlush;
        BufferChecks.checkFunctionAddress(clFlush);
        return nclFlush(clCommandQueue.getPointer(), clFlush);
    }
    
    static native int nclFlush(final long p0, final long p1);
    
    public static int clFinish(final CLCommandQueue clCommandQueue) {
        final long clFinish = CLCapabilities.clFinish;
        BufferChecks.checkFunctionAddress(clFinish);
        return nclFinish(clCommandQueue.getPointer(), clFinish);
    }
    
    static native int nclFinish(final long p0, final long p1);
    
    static CLFunctionAddress clGetExtensionFunctionAddress(final ByteBuffer byteBuffer) {
        final long clGetExtensionFunctionAddress = CLCapabilities.clGetExtensionFunctionAddress;
        BufferChecks.checkFunctionAddress(clGetExtensionFunctionAddress);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return new CLFunctionAddress(nclGetExtensionFunctionAddress(MemoryUtil.getAddress(byteBuffer), clGetExtensionFunctionAddress));
    }
    
    static native long nclGetExtensionFunctionAddress(final long p0, final long p1);
    
    static CLFunctionAddress clGetExtensionFunctionAddress(final CharSequence charSequence) {
        final long clGetExtensionFunctionAddress = CLCapabilities.clGetExtensionFunctionAddress;
        BufferChecks.checkFunctionAddress(clGetExtensionFunctionAddress);
        return new CLFunctionAddress(nclGetExtensionFunctionAddress(APIUtil.getBufferNT(charSequence), clGetExtensionFunctionAddress));
    }
}
