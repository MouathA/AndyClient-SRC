package org.lwjgl.openal;

import java.util.*;
import org.lwjgl.*;
import java.nio.*;

public final class ALC11
{
    public static final int ALC_DEFAULT_ALL_DEVICES_SPECIFIER = 4114;
    public static final int ALC_ALL_DEVICES_SPECIFIER = 4115;
    public static final int ALC_CAPTURE_DEVICE_SPECIFIER = 784;
    public static final int ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER = 785;
    public static final int ALC_CAPTURE_SAMPLES = 786;
    public static final int ALC_MONO_SOURCES = 4112;
    public static final int ALC_STEREO_SOURCES = 4113;
    
    public static ALCdevice alcCaptureOpenDevice(final String s, final int n, final int n2, final int n3) {
        final long nalcCaptureOpenDevice = nalcCaptureOpenDevice(MemoryUtil.getAddressSafe(MemoryUtil.encodeASCII(s)), n, n2, n3);
        if (nalcCaptureOpenDevice != 0L) {
            final ALCdevice alCdevice = new ALCdevice(nalcCaptureOpenDevice);
            // monitorenter(devices = ALC10.devices)
            ALC10.devices.put(nalcCaptureOpenDevice, alCdevice);
            // monitorexit(devices)
            return alCdevice;
        }
        return null;
    }
    
    private static native long nalcCaptureOpenDevice(final long p0, final int p1, final int p2, final int p3);
    
    public static boolean alcCaptureCloseDevice(final ALCdevice alCdevice) {
        final boolean nalcCaptureCloseDevice = nalcCaptureCloseDevice(ALC10.getDevice(alCdevice));
        // monitorenter(devices = ALC10.devices)
        alCdevice.setInvalid();
        ALC10.devices.remove(new Long(alCdevice.device));
        // monitorexit(devices)
        return nalcCaptureCloseDevice;
    }
    
    static native boolean nalcCaptureCloseDevice(final long p0);
    
    public static void alcCaptureStart(final ALCdevice alCdevice) {
        nalcCaptureStart(ALC10.getDevice(alCdevice));
    }
    
    static native void nalcCaptureStart(final long p0);
    
    public static void alcCaptureStop(final ALCdevice alCdevice) {
        nalcCaptureStop(ALC10.getDevice(alCdevice));
    }
    
    static native void nalcCaptureStop(final long p0);
    
    public static void alcCaptureSamples(final ALCdevice alCdevice, final ByteBuffer byteBuffer, final int n) {
        nalcCaptureSamples(ALC10.getDevice(alCdevice), MemoryUtil.getAddress(byteBuffer), n);
    }
    
    static native void nalcCaptureSamples(final long p0, final long p1, final int p2);
    
    static native void initNativeStubs() throws LWJGLException;
    
    static boolean initialize() {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(2);
        ALC10.alcGetInteger(AL.getDevice(), 4096, intBuffer);
        intBuffer.position(1);
        ALC10.alcGetInteger(AL.getDevice(), 4097, intBuffer);
        final int value = intBuffer.get(0);
        final int value2 = intBuffer.get(1);
        if (value < 1 || value > 1 || value2 >= 1) {}
        return true;
    }
}
