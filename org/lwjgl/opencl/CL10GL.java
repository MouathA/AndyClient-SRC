package org.lwjgl.opencl;

import java.nio.*;
import org.lwjgl.*;

public final class CL10GL
{
    public static final int CL_GL_OBJECT_BUFFER = 8192;
    public static final int CL_GL_OBJECT_TEXTURE2D = 8193;
    public static final int CL_GL_OBJECT_TEXTURE3D = 8194;
    public static final int CL_GL_OBJECT_RENDERBUFFER = 8195;
    public static final int CL_GL_TEXTURE_TARGET = 8196;
    public static final int CL_GL_MIPMAP_LEVEL = 8197;
    
    private CL10GL() {
    }
    
    public static CLMem clCreateFromGLBuffer(final CLContext clContext, final long n, final int n2, final IntBuffer intBuffer) {
        final long clCreateFromGLBuffer = CLCapabilities.clCreateFromGLBuffer;
        BufferChecks.checkFunctionAddress(clCreateFromGLBuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateFromGLBuffer(clContext.getPointer(), n, n2, MemoryUtil.getAddressSafe(intBuffer), clCreateFromGLBuffer), clContext);
    }
    
    static native long nclCreateFromGLBuffer(final long p0, final long p1, final int p2, final long p3, final long p4);
    
    public static CLMem clCreateFromGLTexture2D(final CLContext clContext, final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long clCreateFromGLTexture2D = CLCapabilities.clCreateFromGLTexture2D;
        BufferChecks.checkFunctionAddress(clCreateFromGLTexture2D);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateFromGLTexture2D(clContext.getPointer(), n, n2, n3, n4, MemoryUtil.getAddressSafe(intBuffer), clCreateFromGLTexture2D), clContext);
    }
    
    static native long nclCreateFromGLTexture2D(final long p0, final long p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static CLMem clCreateFromGLTexture3D(final CLContext clContext, final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final long clCreateFromGLTexture3D = CLCapabilities.clCreateFromGLTexture3D;
        BufferChecks.checkFunctionAddress(clCreateFromGLTexture3D);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateFromGLTexture3D(clContext.getPointer(), n, n2, n3, n4, MemoryUtil.getAddressSafe(intBuffer), clCreateFromGLTexture3D), clContext);
    }
    
    static native long nclCreateFromGLTexture3D(final long p0, final long p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    public static CLMem clCreateFromGLRenderbuffer(final CLContext clContext, final long n, final int n2, final IntBuffer intBuffer) {
        final long clCreateFromGLRenderbuffer = CLCapabilities.clCreateFromGLRenderbuffer;
        BufferChecks.checkFunctionAddress(clCreateFromGLRenderbuffer);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        return new CLMem(nclCreateFromGLRenderbuffer(clContext.getPointer(), n, n2, MemoryUtil.getAddressSafe(intBuffer), clCreateFromGLRenderbuffer), clContext);
    }
    
    static native long nclCreateFromGLRenderbuffer(final long p0, final long p1, final int p2, final long p3, final long p4);
    
    public static int clGetGLObjectInfo(final CLMem clMem, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long clGetGLObjectInfo = CLCapabilities.clGetGLObjectInfo;
        BufferChecks.checkFunctionAddress(clGetGLObjectInfo);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        return nclGetGLObjectInfo(clMem.getPointer(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), clGetGLObjectInfo);
    }
    
    static native int nclGetGLObjectInfo(final long p0, final long p1, final long p2, final long p3);
    
    public static int clGetGLTextureInfo(final CLMem clMem, final int n, final ByteBuffer byteBuffer, final PointerBuffer pointerBuffer) {
        final long clGetGLTextureInfo = CLCapabilities.clGetGLTextureInfo;
        BufferChecks.checkFunctionAddress(clGetGLTextureInfo);
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        if (pointerBuffer != null) {
            BufferChecks.checkBuffer(pointerBuffer, 1);
        }
        return nclGetGLTextureInfo(clMem.getPointer(), n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(byteBuffer), MemoryUtil.getAddressSafe(pointerBuffer), clGetGLTextureInfo);
    }
    
    static native int nclGetGLTextureInfo(final long p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    public static int clEnqueueAcquireGLObjects(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3) {
        final long clEnqueueAcquireGLObjects = CLCapabilities.clEnqueueAcquireGLObjects;
        BufferChecks.checkFunctionAddress(clEnqueueAcquireGLObjects);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (pointerBuffer2 != null) {
            BufferChecks.checkDirect(pointerBuffer2);
        }
        if (pointerBuffer3 != null) {
            BufferChecks.checkBuffer(pointerBuffer3, 1);
        }
        final int nclEnqueueAcquireGLObjects = nclEnqueueAcquireGLObjects(clCommandQueue.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), (pointerBuffer2 == null) ? 0 : pointerBuffer2.remaining(), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(pointerBuffer3), clEnqueueAcquireGLObjects);
        if (nclEnqueueAcquireGLObjects == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer3);
        }
        return nclEnqueueAcquireGLObjects;
    }
    
    static native int nclEnqueueAcquireGLObjects(final long p0, final int p1, final long p2, final int p3, final long p4, final long p5, final long p6);
    
    public static int clEnqueueAcquireGLObjects(final CLCommandQueue clCommandQueue, final CLMem clMem, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueAcquireGLObjects = CLCapabilities.clEnqueueAcquireGLObjects;
        BufferChecks.checkFunctionAddress(clEnqueueAcquireGLObjects);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueAcquireGLObjects = nclEnqueueAcquireGLObjects(clCommandQueue.getPointer(), 1, APIUtil.getPointer(clMem), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueAcquireGLObjects);
        if (nclEnqueueAcquireGLObjects == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueAcquireGLObjects;
    }
    
    public static int clEnqueueReleaseGLObjects(final CLCommandQueue clCommandQueue, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2, final PointerBuffer pointerBuffer3) {
        final long clEnqueueReleaseGLObjects = CLCapabilities.clEnqueueReleaseGLObjects;
        BufferChecks.checkFunctionAddress(clEnqueueReleaseGLObjects);
        BufferChecks.checkBuffer(pointerBuffer, 1);
        if (pointerBuffer2 != null) {
            BufferChecks.checkDirect(pointerBuffer2);
        }
        if (pointerBuffer3 != null) {
            BufferChecks.checkBuffer(pointerBuffer3, 1);
        }
        final int nclEnqueueReleaseGLObjects = nclEnqueueReleaseGLObjects(clCommandQueue.getPointer(), pointerBuffer.remaining(), MemoryUtil.getAddress(pointerBuffer), (pointerBuffer2 == null) ? 0 : pointerBuffer2.remaining(), MemoryUtil.getAddressSafe(pointerBuffer2), MemoryUtil.getAddressSafe(pointerBuffer3), clEnqueueReleaseGLObjects);
        if (nclEnqueueReleaseGLObjects == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer3);
        }
        return nclEnqueueReleaseGLObjects;
    }
    
    static native int nclEnqueueReleaseGLObjects(final long p0, final int p1, final long p2, final int p3, final long p4, final long p5, final long p6);
    
    public static int clEnqueueReleaseGLObjects(final CLCommandQueue clCommandQueue, final CLMem clMem, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final long clEnqueueReleaseGLObjects = CLCapabilities.clEnqueueReleaseGLObjects;
        BufferChecks.checkFunctionAddress(clEnqueueReleaseGLObjects);
        if (pointerBuffer != null) {
            BufferChecks.checkDirect(pointerBuffer);
        }
        if (pointerBuffer2 != null) {
            BufferChecks.checkBuffer(pointerBuffer2, 1);
        }
        final int nclEnqueueReleaseGLObjects = nclEnqueueReleaseGLObjects(clCommandQueue.getPointer(), 1, APIUtil.getPointer(clMem), (pointerBuffer == null) ? 0 : pointerBuffer.remaining(), MemoryUtil.getAddressSafe(pointerBuffer), MemoryUtil.getAddressSafe(pointerBuffer2), clEnqueueReleaseGLObjects);
        if (nclEnqueueReleaseGLObjects == 0) {
            clCommandQueue.registerCLEvent(pointerBuffer2);
        }
        return nclEnqueueReleaseGLObjects;
    }
}
