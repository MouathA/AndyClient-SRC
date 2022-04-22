package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

public final class CL12
{
    public static final int CL_COMPILE_PROGRAM_FAILURE = -15;
    public static final int CL_LINKER_NOT_AVAILABLE = -16;
    public static final int CL_LINK_PROGRAM_FAILURE = -17;
    public static final int CL_DEVICE_PARTITION_FAILED = -18;
    public static final int CL_KERNEL_ARG_INFO_NOT_AVAILABLE = -19;
    public static final int CL_INVALID_IMAGE_DESCRIPTOR = -65;
    public static final int CL_INVALID_COMPILER_OPTIONS = -66;
    public static final int CL_INVALID_LINKER_OPTIONS = -67;
    public static final int CL_INVALID_DEVICE_PARTITION_COUNT = -68;
    public static final int CL_VERSION_1_2 = 1;
    public static final int CL_BLOCKING = 1;
    public static final int CL_NON_BLOCKING = 0;
    public static final int CL_DEVICE_TYPE_CUSTOM = 16;
    public static final int CL_DEVICE_DOUBLE_FP_CONFIG = 4146;
    public static final int CL_DEVICE_LINKER_AVAILABLE = 4158;
    public static final int CL_DEVICE_BUILT_IN_KERNELS = 4159;
    public static final int CL_DEVICE_IMAGE_MAX_BUFFER_SIZE = 4160;
    public static final int CL_DEVICE_IMAGE_MAX_ARRAY_SIZE = 4161;
    public static final int CL_DEVICE_PARENT_DEVICE = 4162;
    public static final int CL_DEVICE_PARTITION_MAX_SUB_DEVICES = 4163;
    public static final int CL_DEVICE_PARTITION_PROPERTIES = 4164;
    public static final int CL_DEVICE_PARTITION_AFFINITY_DOMAIN = 4165;
    public static final int CL_DEVICE_PARTITION_TYPE = 4166;
    public static final int CL_DEVICE_REFERENCE_COUNT = 4167;
    public static final int CL_DEVICE_PREFERRED_INTEROP_USER_SYNC = 4168;
    public static final int CL_DEVICE_PRINTF_BUFFER_SIZE = 4169;
    public static final int CL_FP_CORRECTLY_ROUNDED_DIVIDE_SQRT = 128;
    public static final int CL_CONTEXT_INTEROP_USER_SYNC = 4229;
    public static final int CL_DEVICE_PARTITION_EQUALLY = 4230;
    public static final int CL_DEVICE_PARTITION_BY_COUNTS = 4231;
    public static final int CL_DEVICE_PARTITION_BY_COUNTS_LIST_END = 0;
    public static final int CL_DEVICE_PARTITION_BY_AFFINITY_DOMAIN = 4232;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_NUMA = 1;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_L4_CACHE = 2;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_L3_CACHE = 4;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_L2_CACHE = 8;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_L1_CACHE = 16;
    public static final int CL_DEVICE_AFFINITY_DOMAIN_NEXT_PARTITIONABLE = 32;
    public static final int CL_MEM_HOST_WRITE_ONLY = 128;
    public static final int CL_MEM_HOST_READ_ONLY = 256;
    public static final int CL_MEM_HOST_NO_ACCESS = 512;
    public static final int CL_MIGRATE_MEM_OBJECT_HOST = 1;
    public static final int CL_MIGRATE_MEM_OBJECT_CONTENT_UNDEFINED = 2;
    public static final int CL_MEM_OBJECT_IMAGE2D_ARRAY = 4339;
    public static final int CL_MEM_OBJECT_IMAGE1D = 4340;
    public static final int CL_MEM_OBJECT_IMAGE1D_ARRAY = 4341;
    public static final int CL_MEM_OBJECT_IMAGE1D_BUFFER = 4342;
    public static final int CL_IMAGE_ARRAY_SIZE = 4375;
    public static final int CL_IMAGE_BUFFER = 4376;
    public static final int CL_IMAGE_NUM_MIP_LEVELS = 4377;
    public static final int CL_IMAGE_NUM_SAMPLES = 4378;
    public static final int CL_MAP_WRITE_INVALIDATE_REGION = 4;
    public static final int CL_PROGRAM_NUM_KERNELS = 4455;
    public static final int CL_PROGRAM_KERNEL_NAMES = 4456;
    public static final int CL_PROGRAM_BINARY_TYPE = 4484;
    public static final int CL_PROGRAM_BINARY_TYPE_NONE = 0;
    public static final int CL_PROGRAM_BINARY_TYPE_COMPILED_OBJECT = 1;
    public static final int CL_PROGRAM_BINARY_TYPE_LIBRARY = 2;
    public static final int CL_PROGRAM_BINARY_TYPE_EXECUTABLE = 4;
    public static final int CL_KERNEL_ATTRIBUTES = 4501;
    public static final int CL_KERNEL_ARG_ADDRESS_QUALIFIER = 4502;
    public static final int CL_KERNEL_ARG_ACCESS_QUALIFIER = 4503;
    public static final int CL_KERNEL_ARG_TYPE_NAME = 4504;
    public static final int CL_KERNEL_ARG_TYPE_QUALIFIER = 4505;
    public static final int CL_KERNEL_ARG_NAME = 4506;
    public static final int CL_KERNEL_ARG_ADDRESS_GLOBAL = 4506;
    public static final int CL_KERNEL_ARG_ADDRESS_LOCAL = 4507;
    public static final int CL_KERNEL_ARG_ADDRESS_CONSTANT = 4508;
    public static final int CL_KERNEL_ARG_ADDRESS_PRIVATE = 4509;
    public static final int CL_KERNEL_ARG_ACCESS_READ_ONLY = 4512;
    public static final int CL_KERNEL_ARG_ACCESS_WRITE_ONLY = 4513;
    public static final int CL_KERNEL_ARG_ACCESS_READ_WRITE = 4514;
    public static final int CL_KERNEL_ARG_ACCESS_NONE = 4515;
    public static final int CL_KERNEL_ARG_TYPE_NONE = 0;
    public static final int CL_KERNEL_ARG_TYPE_CONST = 1;
    public static final int CL_KERNEL_ARG_TYPE_RESTRICT = 2;
    public static final int CL_KERNEL_ARG_TYPE_VOLATILE = 4;
    public static final int CL_KERNEL_GLOBAL_WORK_SIZE = 4533;
    public static final int CL_COMMAND_BARRIER = 4613;
    public static final int CL_COMMAND_MIGRATE_MEM_OBJECTS = 4614;
    public static final int CL_COMMAND_FILL_BUFFER = 4615;
    public static final int CL_COMMAND_FILL_IMAGE = 4616;
    
    private CL12() {
    }
    
    public static int clRetainDevice(final CLDevice clDevice) {
        final long clRetainDevice = CLCapabilities.clRetainDevice;
        BufferChecks.checkFunctionAddress(clRetainDevice);
        final int nclRetainDevice = nclRetainDevice(clDevice.getPointer(), clRetainDevice);
        if (nclRetainDevice == 0) {
            clDevice.retain();
        }
        return nclRetainDevice;
    }
    
    static native int nclRetainDevice(final long p0, final long p1);
    
    public static int clReleaseDevice(final CLDevice clDevice) {
        final long clReleaseDevice = CLCapabilities.clReleaseDevice;
        BufferChecks.checkFunctionAddress(clReleaseDevice);
        APIUtil.releaseObjects(clDevice);
        final int nclReleaseDevice = nclReleaseDevice(clDevice.getPointer(), clReleaseDevice);
        if (nclReleaseDevice == 0) {
            clDevice.release();
        }
        return nclReleaseDevice;
    }
    
    static native int nclReleaseDevice(final long p0, final long p1);
    
    public static int clCreateSubDevices(final CLDevice clDevice, final LongBuffer longBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer) {
        final long clCreateSubDevices = CLCapabilities.clCreateSubDevices;
        BufferChecks.checkFunctionAddress(clCreateSubDevices);
        BufferChecks.checkDirect(longBuffer);
        BufferChecks.checkNullTerminated(longBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        final int nclCreateSubDevices = nclCreateSubDevices(clDevice.getPointer(), MemoryUtil.getAddress(longBuffer), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateSubDevices);
        if (nclCreateSubDevices == 0 && pointerBuffer != null) {
            clDevice.registerSubCLDevices(pointerBuffer);
        }
        return nclCreateSubDevices;
    }
    
    static native int nclCreateSubDevices(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5);
    
    public static CLMem clCreateImage(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ByteBuffer byteBuffer3, final IntBuffer intBuffer) {
        final long clCreateImage = CLCapabilities.clCreateImage;
        BufferChecks.checkFunctionAddress(clCreateImage);
        BufferChecks.checkBuffer(byteBuffer, 8);
        BufferChecks.checkBuffer(byteBuffer2, 7 * PointerBuffer.getPointerSize() + 8 + PointerBuffer.getPointerSize());
        if (byteBuffer3 != null) {
            BufferChecks.checkDirect(byteBuffer3);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddressSafe(byteBuffer3), MemoryUtil.getAddressSafe(intBuffer), clCreateImage), clContext);
    }
    
    public static CLMem clCreateImage(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final FloatBuffer floatBuffer, final IntBuffer intBuffer) {
        final long clCreateImage = CLCapabilities.clCreateImage;
        BufferChecks.checkFunctionAddress(clCreateImage);
        BufferChecks.checkBuffer(byteBuffer, 8);
        BufferChecks.checkBuffer(byteBuffer2, 7 * PointerBuffer.getPointerSize() + 8 + PointerBuffer.getPointerSize());
        if (floatBuffer != null) {
            BufferChecks.checkDirect(floatBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddressSafe(floatBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage), clContext);
    }
    
    public static CLMem clCreateImage(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clCreateImage = CLCapabilities.clCreateImage;
        BufferChecks.checkFunctionAddress(clCreateImage);
        BufferChecks.checkBuffer(byteBuffer, 8);
        BufferChecks.checkBuffer(byteBuffer2, 7 * PointerBuffer.getPointerSize() + 8 + PointerBuffer.getPointerSize());
        if (intBuffer != null) {
            BufferChecks.checkDirect(intBuffer);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return new CLMem(nclCreateImage(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clCreateImage), clContext);
    }
    
    public static CLMem clCreateImage(final CLContext clContext, final long n, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final ShortBuffer shortBuffer, final IntBuffer intBuffer) {
        final long clCreateImage = CLCapabilities.clCreateImage;
        BufferChecks.checkFunctionAddress(clCreateImage);
        BufferChecks.checkBuffer(byteBuffer, 8);
        BufferChecks.checkBuffer(byteBuffer2, 7 * PointerBuffer.getPointerSize() + 8 + PointerBuffer.getPointerSize());
        if (shortBuffer != null) {
            BufferChecks.checkDirect(shortBuffer);
        }
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateImage(clContext.getPointer(), n, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(byteBuffer2), MemoryUtil.getAddressSafe(shortBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateImage), clContext);
    }
    
    static native long nclCreateImage(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final long p6);
    
    public static CLProgram clCreateProgramWithBuiltInKernels(final CLContext clContext, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clCreateProgramWithBuiltInKernels = CLCapabilities.clCreateProgramWithBuiltInKernels;
        BufferChecks.checkFunctionAddress(clCreateProgramWithBuiltInKernels);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        BufferChecks.checkDirect(byteBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithBuiltInKernels(clContext.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithBuiltInKernels), clContext);
    }
    
    static native long nclCreateProgramWithBuiltInKernels(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static CLProgram clCreateProgramWithBuiltInKernels(final CLContext clContext, final PointerBuffer pointerBuffer, final CharSequence charSequence, final IntBuffer intBuffer) {
        final long clCreateProgramWithBuiltInKernels = CLCapabilities.clCreateProgramWithBuiltInKernels;
        BufferChecks.checkFunctionAddress(clCreateProgramWithBuiltInKernels);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLProgram(nclCreateProgramWithBuiltInKernels(clContext.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), APIUtil.getBuffer(charSequence), MemoryUtil.getAddressSafe(intBuffer), clCreateProgramWithBuiltInKernels), clContext);
    }
    
    public static int clCompileProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer2, final ByteBuffer byteBuffer2, final CLCompileProgramCallback clCompileProgramCallback) {
        final long clCompileProgram = CLCapabilities.clCompileProgram;
        BufferChecks.checkFunctionAddress(clCompileProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        BufferChecks.checkBuffer(pointerBuffer2, 1);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkNullTerminated(byteBuffer2);
        final long globalRef = CallbackUtil.createGlobalRef(clCompileProgramCallback);
        if (clCompileProgramCallback != null) {
            clCompileProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclCompileProgram(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddress(byteBuffer), 1, MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(byteBuffer2), (clCompileProgramCallback == null) ? 0L : clCompileProgramCallback.getPointer(), globalRef, clCompileProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclCompileProgram(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final long p6, final long p7, final long p8, final long p9);
    
    public static int clCompileProgramMulti(final CLProgram clProgram, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer2, final ByteBuffer byteBuffer2, final CLCompileProgramCallback clCompileProgramCallback) {
        final long clCompileProgram = CLCapabilities.clCompileProgram;
        BufferChecks.checkFunctionAddress(clCompileProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        BufferChecks.checkBuffer(pointerBuffer2, 1);
        BufferChecks.checkDirect(byteBuffer2);
        BufferChecks.checkNullTerminated(byteBuffer2, pointerBuffer2.remaining());
        final long globalRef = CallbackUtil.createGlobalRef(clCompileProgramCallback);
        if (clCompileProgramCallback != null) {
            clCompileProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclCompileProgramMulti(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddress(byteBuffer), pointerBuffer2.remaining(), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(byteBuffer2), (clCompileProgramCallback == null) ? 0L : clCompileProgramCallback.getPointer(), globalRef, clCompileProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclCompileProgramMulti(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final long p6, final long p7, final long p8, final long p9);
    
    public static int clCompileProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer2, final ByteBuffer[] array, final CLCompileProgramCallback clCompileProgramCallback) {
        final long clCompileProgram = CLCapabilities.clCompileProgram;
        BufferChecks.checkFunctionAddress(clCompileProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        BufferChecks.checkBuffer(pointerBuffer2, array.length);
        BufferChecks.checkArray(array, 1);
        final long globalRef = CallbackUtil.createGlobalRef(clCompileProgramCallback);
        if (clCompileProgramCallback != null) {
            clCompileProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclCompileProgram3(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddress(byteBuffer), array.length, MemoryUtil.getAddress(pointerBuffer2), array, (clCompileProgramCallback == null) ? 0L : clCompileProgramCallback.getPointer(), globalRef, clCompileProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclCompileProgram3(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final ByteBuffer[] p6, final long p7, final long p8, final long p9);
    
    public static int clCompileProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final CharSequence charSequence, final PointerBuffer pointerBuffer2, final CharSequence charSequence2, final CLCompileProgramCallback clCompileProgramCallback) {
        final long clCompileProgram = CLCapabilities.clCompileProgram;
        BufferChecks.checkFunctionAddress(clCompileProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkBuffer(pointerBuffer2, 1);
        final long globalRef = CallbackUtil.createGlobalRef(clCompileProgramCallback);
        if (clCompileProgramCallback != null) {
            clCompileProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclCompileProgram(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), APIUtil.getBufferNT(charSequence), 1, MemoryUtil.getAddress(pointerBuffer2), APIUtil.getBufferNT(charSequence2), (clCompileProgramCallback == null) ? 0L : clCompileProgramCallback.getPointer(), globalRef, clCompileProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    public static int clCompileProgram(final CLProgram clProgram, final PointerBuffer pointerBuffer, final CharSequence charSequence, final PointerBuffer pointerBuffer2, final CharSequence[] array, final CLCompileProgramCallback clCompileProgramCallback) {
        final long clCompileProgram = CLCapabilities.clCompileProgram;
        BufferChecks.checkFunctionAddress(clCompileProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkBuffer(pointerBuffer2, 1);
        BufferChecks.checkArray(array);
        final long globalRef = CallbackUtil.createGlobalRef(clCompileProgramCallback);
        if (clCompileProgramCallback != null) {
            clCompileProgramCallback.setContext((CLContext)clProgram.getParent());
        }
        nclCompileProgramMulti(clProgram.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), APIUtil.getBufferNT(charSequence), pointerBuffer2.remaining(), MemoryUtil.getAddress(pointerBuffer2), APIUtil.getBufferNT(array), (clCompileProgramCallback == null) ? 0L : clCompileProgramCallback.getPointer(), globalRef, clCompileProgram);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    public static CLProgram clLinkProgram(final CLContext context, final PointerBuffer pointerBuffer, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer2, final CLLinkProgramCallback clLinkProgramCallback, final IntBuffer intBuffer) {
        final long clLinkProgram = CLCapabilities.clLinkProgram;
        BufferChecks.checkFunctionAddress(clLinkProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        BufferChecks.checkDirect(pointerBuffer2);
        BufferChecks.checkBuffer(intBuffer, 1);
        final long globalRef = CallbackUtil.createGlobalRef(clLinkProgramCallback);
        if (clLinkProgramCallback != null) {
            clLinkProgramCallback.setContext(context);
        }
        final CLProgram clProgram = new CLProgram(nclLinkProgram(context.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddress(byteBuffer), pointerBuffer2.remaining(), MemoryUtil.getAddress(pointerBuffer2), (clLinkProgramCallback == null) ? 0L : clLinkProgramCallback.getPointer(), globalRef, MemoryUtil.getAddress(intBuffer), clLinkProgram), context);
        CallbackUtil.checkCallback(intBuffer.get(intBuffer.position()), globalRef);
        return clProgram;
    }
    
    static native long nclLinkProgram(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final long p6, final long p7, final long p8, final long p9);
    
    public static CLProgram clLinkProgram(final CLContext context, final PointerBuffer pointerBuffer, final CharSequence charSequence, final PointerBuffer pointerBuffer2, final CLLinkProgramCallback clLinkProgramCallback, final IntBuffer intBuffer) {
        final long clLinkProgram = CLCapabilities.clLinkProgram;
        BufferChecks.checkFunctionAddress(clLinkProgram);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        BufferChecks.checkDirect(pointerBuffer2);
        BufferChecks.checkBuffer(intBuffer, 1);
        final long globalRef = CallbackUtil.createGlobalRef(clLinkProgramCallback);
        if (clLinkProgramCallback != null) {
            clLinkProgramCallback.setContext(context);
        }
        final CLProgram clProgram = new CLProgram(nclLinkProgram(context.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), APIUtil.getBufferNT(charSequence), pointerBuffer2.remaining(), MemoryUtil.getAddress(pointerBuffer2), (clLinkProgramCallback == null) ? 0L : clLinkProgramCallback.getPointer(), globalRef, MemoryUtil.getAddress(intBuffer), clLinkProgram), context);
        CallbackUtil.checkCallback(intBuffer.get(intBuffer.position()), globalRef);
        return clProgram;
    }
    
    public static int clUnloadPlatformCompiler(final CLPlatform clPlatform) {
        final long clUnloadPlatformCompiler = CLCapabilities.clUnloadPlatformCompiler;
        BufferChecks.checkFunctionAddress(clUnloadPlatformCompiler);
        return nclUnloadPlatformCompiler(clPlatform.getPointer(), clUnloadPlatformCompiler);
    }
    
    static native int nclUnloadPlatformCompiler(final long p0, final long p1);
    
    public static int clGetKernelArgInfo(final CLKernel clKernel, final int n, final int n2, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetKernelArgInfo = CLCapabilities.clGetKernelArgInfo;
        BufferChecks.checkFunctionAddress(clGetKernelArgInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetKernelArgInfo(clKernel.getPointer(), n, n2, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetKernelArgInfo);
    }
    
    static native int nclGetKernelArgInfo(final long p0, final int p1, final int p2, final long p3, final long p4, final long p5, final long p6);
    
    public static int clEnqueueFillBuffer(final CLCommandQueue clCommandQueue, final CLMem clMem, final ByteBuffer byteBuffer, final long n, final long n2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueFillBuffer = CLCapabilities.clEnqueueFillBuffer;
        BufferChecks.checkFunctionAddress(clEnqueueFillBuffer);
        BufferChecks.checkDirect(byteBuffer);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        return nclEnqueueFillBuffer(clCommandQueue.getPointer(), clMem.getPointer(), MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), n, n2, (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueFillBuffer);
    }
    
    static native int nclEnqueueFillBuffer(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final int p6, final long p7, final long p8, final long p9);
    
    public static int clEnqueueFillImage(final CLCommandQueue clCommandQueue, final CLMem clMem, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final PointerBuffer pointerBuffer4) {
        final long clEnqueueFillImage = CLCapabilities.clEnqueueFillImage;
        BufferChecks.checkFunctionAddress(clEnqueueFillImage);
        BufferChecks.checkBuffer(byteBuffer, 16);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        if (pointerBuffer3 != null) {
            BufferChecks.checkDirect(pointerBuffer3);
        }
        if (pointerBuffer4 != null) {
            BufferChecks.checkBuffer(pointerBuffer4, 1);
        }
        return nclEnqueueFillImage(clCommandQueue.getPointer(), clMem.getPointer(), MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), (pointerBuffer3 == null) ? 0 : pointerBuffer3.remaining(), MemoryUtil.getAddressSafe(pointerBuffer3), MemoryUtil.getAddressSafe(pointerBuffer4), clEnqueueFillImage);
    }
    
    static native int nclEnqueueFillImage(final long p0, final long p1, final long p2, final long p3, final long p4, final int p5, final long p6, final long p7, final long p8);
    
    public static int clEnqueueMigrateMemObjects(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final long n, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3) {
        final long clEnqueueMigrateMemObjects = CLCapabilities.clEnqueueMigrateMemObjects;
        BufferChecks.checkFunctionAddress(clEnqueueMigrateMemObjects);
        BufferChecks.checkDirect(pointerBuffer);
        if (pointerBuffer2 != null) {
            BufferChecks.checkDirect(pointerBuffer2);
        }
        if (pointerBuffer3 != null) {
            BufferChecks.checkBuffer(pointerBuffer3, 1);
        }
        return nclEnqueueMigrateMemObjects(clCommandQueue.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), n, (pointerBuffer2 == null) ? 0 : pointerBuffer2.remaining(), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(pointerBuffer3), clEnqueueMigrateMemObjects);
    }
    
    static native int nclEnqueueMigrateMemObjects(final long p0, final int p1, final long p2, final long p3, final int p4, final long p5, final long p6, final long p7);
    
    public static int clEnqueueMarkerWithWaitList(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueMarkerWithWaitList = CLCapabilities.clEnqueueMarkerWithWaitList;
        BufferChecks.checkFunctionAddress(clEnqueueMarkerWithWaitList);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        return nclEnqueueMarkerWithWaitList(clCommandQueue.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueMarkerWithWaitList);
    }
    
    static native int nclEnqueueMarkerWithWaitList(final long p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int clEnqueueBarrierWithWaitList(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueBarrierWithWaitList = CLCapabilities.clEnqueueBarrierWithWaitList;
        BufferChecks.checkFunctionAddress(clEnqueueBarrierWithWaitList);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        return nclEnqueueBarrierWithWaitList(clCommandQueue.getPointer(), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueBarrierWithWaitList);
    }
    
    static native int nclEnqueueBarrierWithWaitList(final long p0, final int p1, final long p2, final long p3, final long p4);
    
    public static int clSetPrintfCallback(final CLContext clContext, final CLPrintfCallback clPrintfCallback) {
        final long clSetPrintfCallback = CLCapabilities.clSetPrintfCallback;
        BufferChecks.checkFunctionAddress(clSetPrintfCallback);
        final long globalRef = CallbackUtil.createGlobalRef(clPrintfCallback);
        nclSetPrintfCallback(clContext.getPointer(), clPrintfCallback.getPointer(), globalRef, clSetPrintfCallback);
        clContext.setPrintfCallback(globalRef, 0);
        return 0;
    }
    
    static native int nclSetPrintfCallback(final long p0, final long p1, final long p2, final long p3);
    
    static CLFunctionAddress clGetExtensionFunctionAddressForPlatform(final CLPlatform clPlatform, final ByteBuffer byteBuffer) {
        final long clGetExtensionFunctionAddressForPlatform = CLCapabilities.clGetExtensionFunctionAddressForPlatform;
        BufferChecks.checkFunctionAddress(clGetExtensionFunctionAddressForPlatform);
        BufferChecks.checkDirect(byteBuffer);
        BufferChecks.checkNullTerminated(byteBuffer);
        return new CLFunctionAddress(nclGetExtensionFunctionAddressForPlatform(clPlatform.getPointer(), MemoryUtil.getAddress(byteBuffer), clGetExtensionFunctionAddressForPlatform));
    }
    
    static native long nclGetExtensionFunctionAddressForPlatform(final long p0, final long p1, final long p2);
    
    static CLFunctionAddress clGetExtensionFunctionAddressForPlatform(final CLPlatform clPlatform, final CharSequence charSequence) {
        final long clGetExtensionFunctionAddressForPlatform = CLCapabilities.clGetExtensionFunctionAddressForPlatform;
        BufferChecks.checkFunctionAddress(clGetExtensionFunctionAddressForPlatform);
        return new CLFunctionAddress(nclGetExtensionFunctionAddressForPlatform(clPlatform.getPointer(), APIUtil.getBufferNT(charSequence), clGetExtensionFunctionAddressForPlatform));
    }
}
