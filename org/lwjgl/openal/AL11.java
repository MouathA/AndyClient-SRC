package org.lwjgl.openal;

import org.lwjgl.*;
import java.nio.*;

public final class AL11
{
    public static final int AL_SEC_OFFSET = 4132;
    public static final int AL_SAMPLE_OFFSET = 4133;
    public static final int AL_BYTE_OFFSET = 4134;
    public static final int AL_STATIC = 4136;
    public static final int AL_STREAMING = 4137;
    public static final int AL_UNDETERMINED = 4144;
    public static final int AL_ILLEGAL_COMMAND = 40964;
    public static final int AL_SPEED_OF_SOUND = 49155;
    public static final int AL_LINEAR_DISTANCE = 53251;
    public static final int AL_LINEAR_DISTANCE_CLAMPED = 53252;
    public static final int AL_EXPONENT_DISTANCE = 53253;
    public static final int AL_EXPONENT_DISTANCE_CLAMPED = 53254;
    
    private AL11() {
    }
    
    static native void initNativeStubs() throws LWJGLException;
    
    public static void alListener3i(final int n, final int n2, final int n3, final int n4) {
        nalListener3i(n, n2, n3, n4);
    }
    
    static native void nalListener3i(final int p0, final int p1, final int p2, final int p3);
    
    public static void alGetListeneri(final int n, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalGetListeneriv(n, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalGetListeneriv(final int p0, final long p1);
    
    public static void alSource3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        nalSource3i(n, n2, n3, n4, n5);
    }
    
    static native void nalSource3i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    public static void alSource(final int n, final int n2, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        nalSourceiv(n, n2, MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourceiv(final int p0, final int p1, final long p2);
    
    public static void alBufferf(final int n, final int n2, final float n3) {
        nalBufferf(n, n2, n3);
    }
    
    static native void nalBufferf(final int p0, final int p1, final float p2);
    
    public static void alBuffer3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        nalBuffer3f(n, n2, n3, n4, n5);
    }
    
    static native void nalBuffer3f(final int p0, final int p1, final float p2, final float p3, final float p4);
    
    public static void alBuffer(final int n, final int n2, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalBufferfv(n, n2, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalBufferfv(final int p0, final int p1, final long p2);
    
    public static void alBufferi(final int n, final int n2, final int n3) {
        nalBufferi(n, n2, n3);
    }
    
    static native void nalBufferi(final int p0, final int p1, final int p2);
    
    public static void alBuffer3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        nalBuffer3i(n, n2, n3, n4, n5);
    }
    
    static native void nalBuffer3i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    public static void alBuffer(final int n, final int n2, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        nalBufferiv(n, n2, MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalBufferiv(final int p0, final int p1, final long p2);
    
    public static int alGetBufferi(final int n, final int n2) {
        return nalGetBufferi(n, n2);
    }
    
    static native int nalGetBufferi(final int p0, final int p1);
    
    public static void alGetBuffer(final int n, final int n2, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        nalGetBufferiv(n, n2, MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalGetBufferiv(final int p0, final int p1, final long p2);
    
    public static float alGetBufferf(final int n, final int n2) {
        return nalGetBufferf(n, n2);
    }
    
    static native float nalGetBufferf(final int p0, final int p1);
    
    public static void alGetBuffer(final int n, final int n2, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalGetBufferfv(n, n2, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalGetBufferfv(final int p0, final int p1, final long p2);
    
    public static void alSpeedOfSound(final float n) {
        nalSpeedOfSound(n);
    }
    
    static native void nalSpeedOfSound(final float p0);
}
