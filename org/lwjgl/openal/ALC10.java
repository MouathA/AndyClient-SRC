package org.lwjgl.openal;

import java.util.*;
import java.nio.*;
import org.lwjgl.*;

public final class ALC10
{
    static final HashMap contexts;
    static final HashMap devices;
    public static final int ALC_INVALID = 0;
    public static final int ALC_FALSE = 0;
    public static final int ALC_TRUE = 1;
    public static final int ALC_NO_ERROR = 0;
    public static final int ALC_MAJOR_VERSION = 4096;
    public static final int ALC_MINOR_VERSION = 4097;
    public static final int ALC_ATTRIBUTES_SIZE = 4098;
    public static final int ALC_ALL_ATTRIBUTES = 4099;
    public static final int ALC_DEFAULT_DEVICE_SPECIFIER = 4100;
    public static final int ALC_DEVICE_SPECIFIER = 4101;
    public static final int ALC_EXTENSIONS = 4102;
    public static final int ALC_FREQUENCY = 4103;
    public static final int ALC_REFRESH = 4104;
    public static final int ALC_SYNC = 4105;
    public static final int ALC_INVALID_DEVICE = 40961;
    public static final int ALC_INVALID_CONTEXT = 40962;
    public static final int ALC_INVALID_ENUM = 40963;
    public static final int ALC_INVALID_VALUE = 40964;
    public static final int ALC_OUT_OF_MEMORY = 40965;
    
    static native void initNativeStubs() throws LWJGLException;
    
    public static String alcGetString(final ALCdevice alCdevice, final int n) {
        final ByteBuffer nalcGetString = nalcGetString(getDevice(alCdevice), n);
        Util.checkALCError(alCdevice);
        return MemoryUtil.decodeUTF8(nalcGetString);
    }
    
    static native ByteBuffer nalcGetString(final long p0, final int p1);
    
    public static void alcGetInteger(final ALCdevice alCdevice, final int n, final IntBuffer intBuffer) {
        BufferChecks.checkDirect(intBuffer);
        nalcGetIntegerv(getDevice(alCdevice), n, intBuffer.remaining(), MemoryUtil.getAddress(intBuffer));
        Util.checkALCError(alCdevice);
    }
    
    static native void nalcGetIntegerv(final long p0, final int p1, final int p2, final long p3);
    
    public static ALCdevice alcOpenDevice(final String s) {
        final long nalcOpenDevice = nalcOpenDevice(MemoryUtil.getAddressSafe(MemoryUtil.encodeUTF8(s)));
        if (nalcOpenDevice != 0L) {
            final ALCdevice alCdevice = new ALCdevice(nalcOpenDevice);
            // monitorenter(devices = ALC10.devices)
            ALC10.devices.put(nalcOpenDevice, alCdevice);
            // monitorexit(devices)
            return alCdevice;
        }
        return null;
    }
    
    static native long nalcOpenDevice(final long p0);
    
    public static boolean alcCloseDevice(final ALCdevice alCdevice) {
        final boolean nalcCloseDevice = nalcCloseDevice(getDevice(alCdevice));
        // monitorenter(devices = ALC10.devices)
        alCdevice.setInvalid();
        ALC10.devices.remove(new Long(alCdevice.device));
        // monitorexit(devices)
        return nalcCloseDevice;
    }
    
    static native boolean nalcCloseDevice(final long p0);
    
    public static ALCcontext alcCreateContext(final ALCdevice alCdevice, final IntBuffer intBuffer) {
        final long nalcCreateContext = nalcCreateContext(getDevice(alCdevice), MemoryUtil.getAddressSafe(intBuffer));
        Util.checkALCError(alCdevice);
        if (nalcCreateContext != 0L) {
            final ALCcontext alCcontext = new ALCcontext(nalcCreateContext);
            // monitorenter(contexts = ALC10.contexts)
            ALC10.contexts.put(nalcCreateContext, alCcontext);
            alCdevice.addContext(alCcontext);
            // monitorexit(contexts)
            return alCcontext;
        }
        return null;
    }
    
    static native long nalcCreateContext(final long p0, final long p1);
    
    public static int alcMakeContextCurrent(final ALCcontext alCcontext) {
        return nalcMakeContextCurrent(getContext(alCcontext));
    }
    
    static native int nalcMakeContextCurrent(final long p0);
    
    public static void alcProcessContext(final ALCcontext alCcontext) {
        nalcProcessContext(getContext(alCcontext));
    }
    
    static native void nalcProcessContext(final long p0);
    
    public static ALCcontext alcGetCurrentContext() {
        ALCcontext alCcontext = null;
        final long nalcGetCurrentContext = nalcGetCurrentContext();
        if (nalcGetCurrentContext != 0L) {
            // monitorenter(contexts = ALC10.contexts)
            alCcontext = (ALCcontext)ALC10.contexts.get(nalcGetCurrentContext);
        }
        // monitorexit(contexts)
        return alCcontext;
    }
    
    static native long nalcGetCurrentContext();
    
    public static ALCdevice alcGetContextsDevice(final ALCcontext alCcontext) {
        ALCdevice alCdevice = null;
        final long nalcGetContextsDevice = nalcGetContextsDevice(getContext(alCcontext));
        if (nalcGetContextsDevice != 0L) {
            // monitorenter(devices = ALC10.devices)
            alCdevice = (ALCdevice)ALC10.devices.get(nalcGetContextsDevice);
        }
        // monitorexit(devices)
        return alCdevice;
    }
    
    static native long nalcGetContextsDevice(final long p0);
    
    public static void alcSuspendContext(final ALCcontext alCcontext) {
        nalcSuspendContext(getContext(alCcontext));
    }
    
    static native void nalcSuspendContext(final long p0);
    
    public static void alcDestroyContext(final ALCcontext alCcontext) {
        // monitorenter(contexts = ALC10.contexts)
        final ALCdevice alcGetContextsDevice = alcGetContextsDevice(alCcontext);
        nalcDestroyContext(getContext(alCcontext));
        alcGetContextsDevice.removeContext(alCcontext);
        alCcontext.setInvalid();
    }
    // monitorexit(contexts)
    
    static native void nalcDestroyContext(final long p0);
    
    public static int alcGetError(final ALCdevice alCdevice) {
        return nalcGetError(getDevice(alCdevice));
    }
    
    static native int nalcGetError(final long p0);
    
    public static boolean alcIsExtensionPresent(final ALCdevice alCdevice, final String s) {
        final boolean nalcIsExtensionPresent = nalcIsExtensionPresent(getDevice(alCdevice), MemoryUtil.getAddress(MemoryUtil.encodeASCII(s)));
        Util.checkALCError(alCdevice);
        return nalcIsExtensionPresent;
    }
    
    private static native boolean nalcIsExtensionPresent(final long p0, final long p1);
    
    public static int alcGetEnumValue(final ALCdevice alCdevice, final String s) {
        final int nalcGetEnumValue = nalcGetEnumValue(getDevice(alCdevice), MemoryUtil.getAddress(MemoryUtil.encodeASCII(s)));
        Util.checkALCError(alCdevice);
        return nalcGetEnumValue;
    }
    
    private static native int nalcGetEnumValue(final long p0, final long p1);
    
    static long getDevice(final ALCdevice alCdevice) {
        if (alCdevice != null) {
            Util.checkALCValidDevice(alCdevice);
            return alCdevice.device;
        }
        return 0L;
    }
    
    static long getContext(final ALCcontext alCcontext) {
        if (alCcontext != null) {
            Util.checkALCValidContext(alCcontext);
            return alCcontext.context;
        }
        return 0L;
    }
    
    static {
        contexts = new HashMap();
        devices = new HashMap();
    }
}
