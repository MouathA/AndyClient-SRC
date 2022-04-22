package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

public final class AMDPerformanceMonitor
{
    public static final int GL_COUNTER_TYPE_AMD = 35776;
    public static final int GL_COUNTER_RANGE_AMD = 35777;
    public static final int GL_UNSIGNED_INT64_AMD = 35778;
    public static final int GL_PERCENTAGE_AMD = 35779;
    public static final int GL_PERFMON_RESULT_AVAILABLE_AMD = 35780;
    public static final int GL_PERFMON_RESULT_SIZE_AMD = 35781;
    public static final int GL_PERFMON_RESULT_AMD = 35782;
    
    private AMDPerformanceMonitor() {
    }
    
    public static void glGetPerfMonitorGroupsAMD(final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetPerfMonitorGroupsAMD = GLContext.getCapabilities().glGetPerfMonitorGroupsAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorGroupsAMD);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        BufferChecks.checkDirect(intBuffer2);
        nglGetPerfMonitorGroupsAMD(MemoryUtil.getAddressSafe(intBuffer), intBuffer2.remaining(), MemoryUtil.getAddress(intBuffer2), glGetPerfMonitorGroupsAMD);
    }
    
    static native void nglGetPerfMonitorGroupsAMD(final long p0, final int p1, final long p2, final long p3);
    
    public static void glGetPerfMonitorCountersAMD(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        final long glGetPerfMonitorCountersAMD = GLContext.getCapabilities().glGetPerfMonitorCountersAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCountersAMD);
        BufferChecks.checkBuffer(intBuffer, 1);
        BufferChecks.checkBuffer(intBuffer2, 1);
        if (intBuffer3 != null) {
            BufferChecks.checkDirect(intBuffer3);
        }
        nglGetPerfMonitorCountersAMD(n, MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddress(intBuffer2), (intBuffer3 == null) ? 0 : intBuffer3.remaining(), MemoryUtil.getAddressSafe(intBuffer3), glGetPerfMonitorCountersAMD);
    }
    
    static native void nglGetPerfMonitorCountersAMD(final int p0, final long p1, final long p2, final int p3, final long p4, final long p5);
    
    public static void glGetPerfMonitorGroupStringAMD(final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetPerfMonitorGroupStringAMD = GLContext.getCapabilities().glGetPerfMonitorGroupStringAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorGroupStringAMD);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        nglGetPerfMonitorGroupStringAMD(n, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(byteBuffer), glGetPerfMonitorGroupStringAMD);
    }
    
    static native void nglGetPerfMonitorGroupStringAMD(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    public static String glGetPerfMonitorGroupStringAMD(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPerfMonitorGroupStringAMD = capabilities.glGetPerfMonitorGroupStringAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorGroupStringAMD);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n2);
        nglGetPerfMonitorGroupStringAMD(n, n2, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetPerfMonitorGroupStringAMD);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetPerfMonitorCounterStringAMD(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final long glGetPerfMonitorCounterStringAMD = GLContext.getCapabilities().glGetPerfMonitorCounterStringAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCounterStringAMD);
        if (intBuffer != null) {
            BufferChecks.checkBuffer(intBuffer, 1);
        }
        if (byteBuffer != null) {
            BufferChecks.checkDirect(byteBuffer);
        }
        nglGetPerfMonitorCounterStringAMD(n, n2, (byteBuffer == null) ? 0 : byteBuffer.remaining(), MemoryUtil.getAddressSafe(intBuffer), MemoryUtil.getAddressSafe(byteBuffer), glGetPerfMonitorCounterStringAMD);
    }
    
    static native void nglGetPerfMonitorCounterStringAMD(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static String glGetPerfMonitorCounterStringAMD(final int n, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPerfMonitorCounterStringAMD = capabilities.glGetPerfMonitorCounterStringAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCounterStringAMD);
        final IntBuffer lengths = APIUtil.getLengths(capabilities);
        final ByteBuffer bufferByte = APIUtil.getBufferByte(capabilities, n3);
        nglGetPerfMonitorCounterStringAMD(n, n2, n3, MemoryUtil.getAddress0(lengths), MemoryUtil.getAddress(bufferByte), glGetPerfMonitorCounterStringAMD);
        bufferByte.limit(lengths.get(0));
        return APIUtil.getString(capabilities, bufferByte);
    }
    
    public static void glGetPerfMonitorCounterInfoAMD(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final long glGetPerfMonitorCounterInfoAMD = GLContext.getCapabilities().glGetPerfMonitorCounterInfoAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCounterInfoAMD);
        BufferChecks.checkBuffer(byteBuffer, 16);
        nglGetPerfMonitorCounterInfoAMD(n, n2, n3, MemoryUtil.getAddress(byteBuffer), glGetPerfMonitorCounterInfoAMD);
    }
    
    static native void nglGetPerfMonitorCounterInfoAMD(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    public static void glGenPerfMonitorsAMD(final IntBuffer intBuffer) {
        final long glGenPerfMonitorsAMD = GLContext.getCapabilities().glGenPerfMonitorsAMD;
        BufferChecks.checkFunctionAddress(glGenPerfMonitorsAMD);
        BufferChecks.checkDirect(intBuffer);
        nglGenPerfMonitorsAMD(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glGenPerfMonitorsAMD);
    }
    
    static native void nglGenPerfMonitorsAMD(final int p0, final long p1, final long p2);
    
    public static int glGenPerfMonitorsAMD() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGenPerfMonitorsAMD = capabilities.glGenPerfMonitorsAMD;
        BufferChecks.checkFunctionAddress(glGenPerfMonitorsAMD);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGenPerfMonitorsAMD(1, MemoryUtil.getAddress(bufferInt), glGenPerfMonitorsAMD);
        return bufferInt.get(0);
    }
    
    public static void glDeletePerfMonitorsAMD(final IntBuffer intBuffer) {
        final long glDeletePerfMonitorsAMD = GLContext.getCapabilities().glDeletePerfMonitorsAMD;
        BufferChecks.checkFunctionAddress(glDeletePerfMonitorsAMD);
        BufferChecks.checkDirect(intBuffer);
        nglDeletePerfMonitorsAMD(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glDeletePerfMonitorsAMD);
    }
    
    static native void nglDeletePerfMonitorsAMD(final int p0, final long p1, final long p2);
    
    public static void glDeletePerfMonitorsAMD(final int n) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glDeletePerfMonitorsAMD = capabilities.glDeletePerfMonitorsAMD;
        BufferChecks.checkFunctionAddress(glDeletePerfMonitorsAMD);
        nglDeletePerfMonitorsAMD(1, APIUtil.getInt(capabilities, n), glDeletePerfMonitorsAMD);
    }
    
    public static void glSelectPerfMonitorCountersAMD(final int n, final boolean b, final int n2, final IntBuffer intBuffer) {
        final long glSelectPerfMonitorCountersAMD = GLContext.getCapabilities().glSelectPerfMonitorCountersAMD;
        BufferChecks.checkFunctionAddress(glSelectPerfMonitorCountersAMD);
        BufferChecks.checkDirect(intBuffer);
        nglSelectPerfMonitorCountersAMD(n, b, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), glSelectPerfMonitorCountersAMD);
    }
    
    static native void nglSelectPerfMonitorCountersAMD(final int p0, final boolean p1, final int p2, final int p3, final long p4, final long p5);
    
    public static void glSelectPerfMonitorCountersAMD(final int n, final boolean b, final int n2, final int n3) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glSelectPerfMonitorCountersAMD = capabilities.glSelectPerfMonitorCountersAMD;
        BufferChecks.checkFunctionAddress(glSelectPerfMonitorCountersAMD);
        nglSelectPerfMonitorCountersAMD(n, b, n2, 1, APIUtil.getInt(capabilities, n3), glSelectPerfMonitorCountersAMD);
    }
    
    public static void glBeginPerfMonitorAMD(final int n) {
        final long glBeginPerfMonitorAMD = GLContext.getCapabilities().glBeginPerfMonitorAMD;
        BufferChecks.checkFunctionAddress(glBeginPerfMonitorAMD);
        nglBeginPerfMonitorAMD(n, glBeginPerfMonitorAMD);
    }
    
    static native void nglBeginPerfMonitorAMD(final int p0, final long p1);
    
    public static void glEndPerfMonitorAMD(final int n) {
        final long glEndPerfMonitorAMD = GLContext.getCapabilities().glEndPerfMonitorAMD;
        BufferChecks.checkFunctionAddress(glEndPerfMonitorAMD);
        nglEndPerfMonitorAMD(n, glEndPerfMonitorAMD);
    }
    
    static native void nglEndPerfMonitorAMD(final int p0, final long p1);
    
    public static void glGetPerfMonitorCounterDataAMD(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final long glGetPerfMonitorCounterDataAMD = GLContext.getCapabilities().glGetPerfMonitorCounterDataAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCounterDataAMD);
        BufferChecks.checkDirect(intBuffer);
        if (intBuffer2 != null) {
            BufferChecks.checkBuffer(intBuffer2, 1);
        }
        nglGetPerfMonitorCounterDataAMD(n, n2, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer), MemoryUtil.getAddressSafe(intBuffer2), glGetPerfMonitorCounterDataAMD);
    }
    
    static native void nglGetPerfMonitorCounterDataAMD(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    public static int glGetPerfMonitorCounterDataAMD(final int n, final int n2) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        final long glGetPerfMonitorCounterDataAMD = capabilities.glGetPerfMonitorCounterDataAMD;
        BufferChecks.checkFunctionAddress(glGetPerfMonitorCounterDataAMD);
        final IntBuffer bufferInt = APIUtil.getBufferInt(capabilities);
        nglGetPerfMonitorCounterDataAMD(n, n2, 4, MemoryUtil.getAddress(bufferInt), 0L, glGetPerfMonitorCounterDataAMD);
        return bufferInt.get(0);
    }
}
