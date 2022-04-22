package org.lwjgl.openal;

import org.lwjgl.*;
import java.nio.*;

public final class AL10
{
    public static final int AL_INVALID = -1;
    public static final int AL_NONE = 0;
    public static final int AL_FALSE = 0;
    public static final int AL_TRUE = 1;
    public static final int AL_SOURCE_TYPE = 4135;
    public static final int AL_SOURCE_ABSOLUTE = 513;
    public static final int AL_SOURCE_RELATIVE = 514;
    public static final int AL_CONE_INNER_ANGLE = 4097;
    public static final int AL_CONE_OUTER_ANGLE = 4098;
    public static final int AL_PITCH = 4099;
    public static final int AL_POSITION = 4100;
    public static final int AL_DIRECTION = 4101;
    public static final int AL_VELOCITY = 4102;
    public static final int AL_LOOPING = 4103;
    public static final int AL_BUFFER = 4105;
    public static final int AL_GAIN = 4106;
    public static final int AL_MIN_GAIN = 4109;
    public static final int AL_MAX_GAIN = 4110;
    public static final int AL_ORIENTATION = 4111;
    public static final int AL_REFERENCE_DISTANCE = 4128;
    public static final int AL_ROLLOFF_FACTOR = 4129;
    public static final int AL_CONE_OUTER_GAIN = 4130;
    public static final int AL_MAX_DISTANCE = 4131;
    public static final int AL_CHANNEL_MASK = 12288;
    public static final int AL_SOURCE_STATE = 4112;
    public static final int AL_INITIAL = 4113;
    public static final int AL_PLAYING = 4114;
    public static final int AL_PAUSED = 4115;
    public static final int AL_STOPPED = 4116;
    public static final int AL_BUFFERS_QUEUED = 4117;
    public static final int AL_BUFFERS_PROCESSED = 4118;
    public static final int AL_FORMAT_MONO8 = 4352;
    public static final int AL_FORMAT_MONO16 = 4353;
    public static final int AL_FORMAT_STEREO8 = 4354;
    public static final int AL_FORMAT_STEREO16 = 4355;
    public static final int AL_FORMAT_VORBIS_EXT = 65539;
    public static final int AL_FREQUENCY = 8193;
    public static final int AL_BITS = 8194;
    public static final int AL_CHANNELS = 8195;
    public static final int AL_SIZE = 8196;
    @Deprecated
    public static final int AL_DATA = 8197;
    public static final int AL_UNUSED = 8208;
    public static final int AL_PENDING = 8209;
    public static final int AL_PROCESSED = 8210;
    public static final int AL_NO_ERROR = 0;
    public static final int AL_INVALID_NAME = 40961;
    public static final int AL_INVALID_ENUM = 40962;
    public static final int AL_INVALID_VALUE = 40963;
    public static final int AL_INVALID_OPERATION = 40964;
    public static final int AL_OUT_OF_MEMORY = 40965;
    public static final int AL_VENDOR = 45057;
    public static final int AL_VERSION = 45058;
    public static final int AL_RENDERER = 45059;
    public static final int AL_EXTENSIONS = 45060;
    public static final int AL_DOPPLER_FACTOR = 49152;
    public static final int AL_DOPPLER_VELOCITY = 49153;
    public static final int AL_DISTANCE_MODEL = 53248;
    public static final int AL_INVERSE_DISTANCE = 53249;
    public static final int AL_INVERSE_DISTANCE_CLAMPED = 53250;
    
    private AL10() {
    }
    
    static native void initNativeStubs() throws LWJGLException;
    
    public static void alEnable(final int n) {
        nalEnable(n);
    }
    
    static native void nalEnable(final int p0);
    
    public static void alDisable(final int n) {
        nalDisable(n);
    }
    
    static native void nalDisable(final int p0);
    
    public static boolean alIsEnabled(final int n) {
        return nalIsEnabled(n);
    }
    
    static native boolean nalIsEnabled(final int p0);
    
    public static boolean alGetBoolean(final int n) {
        return nalGetBoolean(n);
    }
    
    static native boolean nalGetBoolean(final int p0);
    
    public static int alGetInteger(final int n) {
        return nalGetInteger(n);
    }
    
    static native int nalGetInteger(final int p0);
    
    public static float alGetFloat(final int n) {
        return nalGetFloat(n);
    }
    
    static native float nalGetFloat(final int p0);
    
    public static double alGetDouble(final int n) {
        return nalGetDouble(n);
    }
    
    static native double nalGetDouble(final int p0);
    
    public static void alGetInteger(final int n, final IntBuffer intBuffer) {
        BufferChecks.checkBuffer(intBuffer, 1);
        nalGetIntegerv(n, MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalGetIntegerv(final int p0, final long p1);
    
    public static void alGetFloat(final int n, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalGetFloatv(n, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalGetFloatv(final int p0, final long p1);
    
    public static void alGetDouble(final int n, final DoubleBuffer doubleBuffer) {
        BufferChecks.checkBuffer(doubleBuffer, 1);
        nalGetDoublev(n, MemoryUtil.getAddress(doubleBuffer));
    }
    
    static native void nalGetDoublev(final int p0, final long p1);
    
    public static String alGetString(final int n) {
        return nalGetString(n);
    }
    
    static native String nalGetString(final int p0);
    
    public static int alGetError() {
        return nalGetError();
    }
    
    static native int nalGetError();
    
    public static boolean alIsExtensionPresent(final String s) {
        BufferChecks.checkNotNull(s);
        return nalIsExtensionPresent(s);
    }
    
    static native boolean nalIsExtensionPresent(final String p0);
    
    public static int alGetEnumValue(final String s) {
        BufferChecks.checkNotNull(s);
        return nalGetEnumValue(s);
    }
    
    static native int nalGetEnumValue(final String p0);
    
    public static void alListeneri(final int n, final int n2) {
        nalListeneri(n, n2);
    }
    
    static native void nalListeneri(final int p0, final int p1);
    
    public static void alListenerf(final int n, final float n2) {
        nalListenerf(n, n2);
    }
    
    static native void nalListenerf(final int p0, final float p1);
    
    public static void alListener(final int n, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalListenerfv(n, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalListenerfv(final int p0, final long p1);
    
    public static void alListener3f(final int n, final float n2, final float n3, final float n4) {
        nalListener3f(n, n2, n3, n4);
    }
    
    static native void nalListener3f(final int p0, final float p1, final float p2, final float p3);
    
    public static int alGetListeneri(final int n) {
        return nalGetListeneri(n);
    }
    
    static native int nalGetListeneri(final int p0);
    
    public static float alGetListenerf(final int n) {
        return nalGetListenerf(n);
    }
    
    static native float nalGetListenerf(final int p0);
    
    public static void alGetListener(final int n, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalGetListenerfv(n, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalGetListenerfv(final int p0, final long p1);
    
    public static void alGenSources(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalGenSources(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalGenSources(final int p0, final long p1);
    
    public static int alGenSources() {
        return nalGenSources2(1);
    }
    
    static native int nalGenSources2(final int p0);
    
    public static void alDeleteSources(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalDeleteSources(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalDeleteSources(final int p0, final long p1);
    
    public static void alDeleteSources(final int n) {
        nalDeleteSources2(1, n);
    }
    
    static native void nalDeleteSources2(final int p0, final int p1);
    
    public static boolean alIsSource(final int n) {
        return nalIsSource(n);
    }
    
    static native boolean nalIsSource(final int p0);
    
    public static void alSourcei(final int n, final int n2, final int n3) {
        nalSourcei(n, n2, n3);
    }
    
    static native void nalSourcei(final int p0, final int p1, final int p2);
    
    public static void alSourcef(final int n, final int n2, final float n3) {
        nalSourcef(n, n2, n3);
    }
    
    static native void nalSourcef(final int p0, final int p1, final float p2);
    
    public static void alSource(final int n, final int n2, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalSourcefv(n, n2, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalSourcefv(final int p0, final int p1, final long p2);
    
    public static void alSource3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        nalSource3f(n, n2, n3, n4, n5);
    }
    
    static native void nalSource3f(final int p0, final int p1, final float p2, final float p3, final float p4);
    
    public static int alGetSourcei(final int n, final int n2) {
        return nalGetSourcei(n, n2);
    }
    
    static native int nalGetSourcei(final int p0, final int p1);
    
    public static float alGetSourcef(final int n, final int n2) {
        return nalGetSourcef(n, n2);
    }
    
    static native float nalGetSourcef(final int p0, final int p1);
    
    public static void alGetSource(final int n, final int n2, final FloatBuffer floatBuffer) {
        BufferChecks.checkBuffer(floatBuffer, 1);
        nalGetSourcefv(n, n2, MemoryUtil.getAddress(floatBuffer));
    }
    
    static native void nalGetSourcefv(final int p0, final int p1, final long p2);
    
    public static void alSourcePlay(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourcePlayv(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourcePlayv(final int p0, final long p1);
    
    public static void alSourcePause(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourcePausev(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourcePausev(final int p0, final long p1);
    
    public static void alSourceStop(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourceStopv(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourceStopv(final int p0, final long p1);
    
    public static void alSourceRewind(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourceRewindv(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourceRewindv(final int p0, final long p1);
    
    public static void alSourcePlay(final int n) {
        nalSourcePlay(n);
    }
    
    static native void nalSourcePlay(final int p0);
    
    public static void alSourcePause(final int n) {
        nalSourcePause(n);
    }
    
    static native void nalSourcePause(final int p0);
    
    public static void alSourceStop(final int n) {
        nalSourceStop(n);
    }
    
    static native void nalSourceStop(final int p0);
    
    public static void alSourceRewind(final int n) {
        nalSourceRewind(n);
    }
    
    static native void nalSourceRewind(final int p0);
    
    public static void alGenBuffers(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalGenBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalGenBuffers(final int p0, final long p1);
    
    public static int alGenBuffers() {
        return nalGenBuffers2(1);
    }
    
    static native int nalGenBuffers2(final int p0);
    
    public static void alDeleteBuffers(final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalDeleteBuffers(intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalDeleteBuffers(final int p0, final long p1);
    
    public static void alDeleteBuffers(final int n) {
        nalDeleteBuffers2(1, n);
    }
    
    static native void nalDeleteBuffers2(final int p0, final int p1);
    
    public static boolean alIsBuffer(final int n) {
        return nalIsBuffer(n);
    }
    
    static native boolean nalIsBuffer(final int p0);
    
    public static void alBufferData(final int n, final int n2, final ByteBuffer byteBuffer, final int n3) {
        BufferChecks.checkDirect(byteBuffer);
        nalBufferData(n, n2, MemoryUtil.getAddress(byteBuffer), byteBuffer.remaining(), n3);
    }
    
    public static void alBufferData(final int n, final int n2, final IntBuffer intBuffer, final int n3) {
        BufferChecks.checkDirect(intBuffer);
        nalBufferData(n, n2, MemoryUtil.getAddress(intBuffer), intBuffer.remaining() << 2, n3);
    }
    
    public static void alBufferData(final int n, final int n2, final ShortBuffer shortBuffer, final int n3) {
        BufferChecks.checkDirect(shortBuffer);
        nalBufferData(n, n2, MemoryUtil.getAddress(shortBuffer), shortBuffer.remaining() << 1, n3);
    }
    
    static native void nalBufferData(final int p0, final int p1, final long p2, final int p3, final int p4);
    
    public static int alGetBufferi(final int n, final int n2) {
        return nalGetBufferi(n, n2);
    }
    
    static native int nalGetBufferi(final int p0, final int p1);
    
    public static float alGetBufferf(final int n, final int n2) {
        return nalGetBufferf(n, n2);
    }
    
    static native float nalGetBufferf(final int p0, final int p1);
    
    public static void alSourceQueueBuffers(final int n, final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourceQueueBuffers(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourceQueueBuffers(final int p0, final int p1, final long p2);
    
    public static void alSourceQueueBuffers(final int n, final int n2) {
        nalSourceQueueBuffers2(n, 1, n2);
    }
    
    static native void nalSourceQueueBuffers2(final int p0, final int p1, final int p2);
    
    public static void alSourceUnqueueBuffers(final int n, final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalSourceUnqueueBuffers(n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
    }
    
    static native void nalSourceUnqueueBuffers(final int p0, final int p1, final long p2);
    
    public static int alSourceUnqueueBuffers(final int n) {
        return nalSourceUnqueueBuffers2(n, 1);
    }
    
    static native int nalSourceUnqueueBuffers2(final int p0, final int p1);
    
    public static void alDistanceModel(final int n) {
        nalDistanceModel(n);
    }
    
    static native void nalDistanceModel(final int p0);
    
    public static void alDopplerFactor(final float n) {
        nalDopplerFactor(n);
    }
    
    static native void nalDopplerFactor(final float p0);
    
    public static void alDopplerVelocity(final float n) {
        nalDopplerVelocity(n);
    }
    
    static native void nalDopplerVelocity(final float p0);
}
