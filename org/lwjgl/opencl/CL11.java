package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

public final class CL11
{
    public static final int CL_MISALIGNED_SUB_BUFFER_OFFSET = -13;
    public static final int CL_EXEC_STATUS_ERROR_FOR_EVENTS_IN_WAIT_LIST = -14;
    public static final int CL_INVALID_PROPERTY = -64;
    public static final int CL_VERSION_1_1 = 1;
    public static final int CL_DEVICE_PREFERRED_VECTOR_WIDTH_HALF = 4148;
    public static final int CL_DEVICE_HOST_UNIFIED_MEMORY = 4149;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR = 4150;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT = 4151;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_INT = 4152;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG = 4153;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT = 4154;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE = 4155;
    public static final int CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF = 4156;
    public static final int CL_DEVICE_OPENCL_C_VERSION = 4157;
    public static final int CL_FP_SOFT_FLOAT = 64;
    public static final int CL_CONTEXT_NUM_DEVICES = 4227;
    public static final int CL_Rx = 4282;
    public static final int CL_RGx = 4283;
    public static final int CL_RGBx = 4284;
    public static final int CL_MEM_ASSOCIATED_MEMOBJECT = 4359;
    public static final int CL_MEM_OFFSET = 4360;
    public static final int CL_ADDRESS_MIRRORED_REPEAT = 4404;
    public static final int CL_KERNEL_PREFERRED_WORK_GROUP_SIZE_MULTIPLE = 4531;
    public static final int CL_KERNEL_PRIVATE_MEM_SIZE = 4532;
    public static final int CL_EVENT_CONTEXT = 4564;
    public static final int CL_COMMAND_READ_BUFFER_RECT = 4609;
    public static final int CL_COMMAND_WRITE_BUFFER_RECT = 4610;
    public static final int CL_COMMAND_COPY_BUFFER_RECT = 4611;
    public static final int CL_COMMAND_USER = 4612;
    public static final int CL_BUFFER_CREATE_TYPE_REGION = 4640;
    
    private CL11() {
    }
    
    public static CLMem clCreateSubBuffer(final CLMem clMem, final long n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final long clCreateSubBuffer = CLCapabilities.clCreateSubBuffer;
        BufferChecks.checkFunctionAddress(clCreateSubBuffer);
        BufferChecks.checkBuffer(byteBuffer, 2 * PointerBuffer.getPointerSize());
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return CLMem.create(nclCreateSubBuffer(clMem.getPointer(), n, n2, MemoryUtil.getAddress(byteBuffer), MemoryUtil.getAddressSafe(intBuffer), clCreateSubBuffer), (CLContext)clMem.getParent());
    }
    
    static native long nclCreateSubBuffer(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5);
    
    public static int clSetMemObjectDestructorCallback(final CLMem clMem, final CLMemObjectDestructorCallback clMemObjectDestructorCallback) {
        final long clSetMemObjectDestructorCallback = CLCapabilities.clSetMemObjectDestructorCallback;
        BufferChecks.checkFunctionAddress(clSetMemObjectDestructorCallback);
        final long globalRef = CallbackUtil.createGlobalRef(clMemObjectDestructorCallback);
        nclSetMemObjectDestructorCallback(clMem.getPointer(), clMemObjectDestructorCallback.getPointer(), globalRef, clSetMemObjectDestructorCallback);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclSetMemObjectDestructorCallback(final long p0, final long p1, final long p2, final long p3);
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(byteBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final DoubleBuffer doubleBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(doubleBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final IntBuffer intBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(intBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final LongBuffer longBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(longBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(longBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    public static int clEnqueueReadBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueReadBufferRect = CLCapabilities.clEnqueueReadBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueReadBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueReadBufferRect = nclEnqueueReadBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueReadBufferRect);
        if (nclEnqueueReadBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueReadBufferRect;
    }
    
    static native int nclEnqueueReadBufferRect(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8, final long p9, final long p10, final int p11, final long p12, final long p13, final long p14);
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(byteBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(byteBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final DoubleBuffer doubleBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(doubleBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(doubleBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final FloatBuffer floatBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(floatBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(floatBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final IntBuffer intBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(intBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(intBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final LongBuffer longBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(longBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(longBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    public static int clEnqueueWriteBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final int n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n2, final long n3, final long n4, final long n5, final ShortBuffer shortBuffer, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueWriteBufferRect = CLCapabilities.clEnqueueWriteBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueWriteBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        BufferChecks.checkBuffer(shortBuffer, CLChecks.calculateBufferRectSize(pointerBuffer2, pointerBuffer3, n4, n5));
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueWriteBufferRect = nclEnqueueWriteBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), n, MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n2, n3, n4, n5, MemoryUtil.getAddress(shortBuffer), (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueWriteBufferRect);
        if (nclEnqueueWriteBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueWriteBufferRect;
    }
    
    static native int nclEnqueueWriteBufferRect(final long p0, final long p1, final int p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8, final long p9, final long p10, final int p11, final long p12, final long p13, final long p14);
    
    public static int clEnqueueCopyBufferRect(final CLCommandQueue clCommandQueue, final CLMem clMem, final CLMem clMem2, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3, final long n, final long n2, final long n3, final long n4, final PointerBuffer pointerBuffer4, final PointerBuffer pointerBuffer5) {
        final long clEnqueueCopyBufferRect = CLCapabilities.clEnqueueCopyBufferRect;
        BufferChecks.checkFunctionAddress(clEnqueueCopyBufferRect);
        BufferChecks.checkBuffer(pointerBuffer, 3);
        BufferChecks.checkBuffer(pointerBuffer2, 3);
        BufferChecks.checkBuffer(pointerBuffer3, 3);
        if (pointerBuffer4 != null) {
            BufferChecks.checkDirect(pointerBuffer4);
        }
        if (pointerBuffer5 != null) {
            BufferChecks.checkBuffer(pointerBuffer5, 1);
        }
        final int nclEnqueueCopyBufferRect = nclEnqueueCopyBufferRect(clCommandQueue.getPointer(), clMem.getPointer(), clMem2.getPointer(), MemoryUtil.getAddress(pointerBuffer), MemoryUtil.getAddress(pointerBuffer2), MemoryUtil.getAddress(pointerBuffer3), n, n2, n3, n4, (pointerBuffer4 == null) ? 0 : pointerBuffer4.remaining(), MemoryUtil.getAddressSafe(pointerBuffer4), MemoryUtil.getAddressSafe(pointerBuffer5), clEnqueueCopyBufferRect);
        if (nclEnqueueCopyBufferRect == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer5);
        }
        return nclEnqueueCopyBufferRect;
    }
    
    static native int nclEnqueueCopyBufferRect(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5, final long p6, final long p7, final long p8, final long p9, final int p10, final long p11, final long p12, final long p13);
    
    public static CLEvent clCreateUserEvent(final CLContext clContext, final IntBuffer intBuffer) {
        final long clCreateUserEvent = CLCapabilities.clCreateUserEvent;
        BufferChecks.checkFunctionAddress(clCreateUserEvent);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLEvent(nclCreateUserEvent(clContext.getPointer(), MemoryUtil.getAddressSafe(intBuffer), clCreateUserEvent), clContext);
    }
    
    static native long nclCreateUserEvent(final long p0, final long p1, final long p2);
    
    public static int clSetUserEventStatus(final CLEvent clEvent, final int n) {
        final long clSetUserEventStatus = CLCapabilities.clSetUserEventStatus;
        BufferChecks.checkFunctionAddress(clSetUserEventStatus);
        return nclSetUserEventStatus(clEvent.getPointer(), n, clSetUserEventStatus);
    }
    
    static native int nclSetUserEventStatus(final long p0, final int p1, final long p2);
    
    public static int clSetEventCallback(final CLEvent clEvent, final int n, final CLEventCallback clEventCallback) {
        final long clSetEventCallback = CLCapabilities.clSetEventCallback;
        BufferChecks.checkFunctionAddress(clSetEventCallback);
        final long globalRef = CallbackUtil.createGlobalRef(clEventCallback);
        clEventCallback.setRegistry(clEvent.getParentRegistry());
        nclSetEventCallback(clEvent.getPointer(), n, clEventCallback.getPointer(), globalRef, clSetEventCallback);
        CallbackUtil.checkCallback(0, globalRef);
        return 0;
    }
    
    static native int nclSetEventCallback(final long p0, final int p1, final long p2, final long p3, final long p4);
}
