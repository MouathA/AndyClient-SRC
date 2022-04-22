package org.lwjgl.openal;

import org.lwjgl.*;
import java.nio.*;

public final class AL
{
    static ALCdevice device;
    static ALCcontext context;
    private static boolean created;
    
    private AL() {
    }
    
    private static native void nCreate(final String p0) throws LWJGLException;
    
    private static native void nCreateDefault() throws LWJGLException;
    
    private static native void nDestroy();
    
    public static boolean isCreated() {
        return AL.created;
    }
    
    public static void create(final String s, final int n, final int n2, final boolean b) throws LWJGLException {
        create(s, n, n2, b, true);
    }
    
    public static void create(final String s, final int n, final int n2, final boolean b, final boolean b2) throws LWJGLException {
        if (AL.created) {
            throw new IllegalStateException("Only one OpenAL context may be instantiated at any one time.");
        }
        String s2 = null;
        String[] array = null;
        switch (LWJGLUtil.getPlatform()) {
            case 3: {
                if (Sys.is64Bit()) {
                    s2 = "OpenAL64";
                    array = new String[] { "OpenAL64.dll" };
                    break;
                }
                s2 = "OpenAL32";
                array = new String[] { "OpenAL32.dll" };
                break;
            }
            case 1: {
                s2 = "openal";
                array = new String[] { "libopenal64.so", "libopenal.so", "libopenal.so.0" };
                break;
            }
            case 2: {
                s2 = "openal";
                array = new String[] { "openal.dylib" };
                break;
            }
            default: {
                throw new LWJGLException("Unknown platform: " + LWJGLUtil.getPlatform());
            }
        }
        final String[] libraryPaths = LWJGLUtil.getLibraryPaths(s2, array, AL.class.getClassLoader());
        LWJGLUtil.log("Found " + libraryPaths.length + " OpenAL paths");
        final String[] array2 = libraryPaths;
        if (0 < array2.length) {
            nCreate(array2[0]);
            AL.created = true;
            init(s, n, n2, b, b2);
        }
        if (!AL.created && LWJGLUtil.getPlatform() == 2) {
            AL.created = true;
            init(s, n, n2, b, b2);
        }
        if (!AL.created) {
            throw new LWJGLException("Could not locate OpenAL library.");
        }
    }
    
    private static void init(final String s, final int n, final int n2, final boolean b, final boolean b2) throws LWJGLException {
        if (b2) {
            AL.device = ALC10.alcOpenDevice(s);
            if (AL.device == null) {
                throw new LWJGLException("Could not open ALC device");
            }
            if (n == -1) {
                AL.context = ALC10.alcCreateContext(AL.device, null);
            }
            else {
                AL.context = ALC10.alcCreateContext(AL.device, ALCcontext.createAttributeList(n, n2, (int)(b ? 1 : 0)));
            }
            ALC10.alcMakeContextCurrent(AL.context);
        }
        ALC11.initialize();
        ALC10.alcIsExtensionPresent(AL.device, "ALC_EXT_EFX");
    }
    
    public static void create() throws LWJGLException {
        create(null, 44100, 60, false);
    }
    
    public static void destroy() {
        if (AL.context != null) {
            ALC10.alcMakeContextCurrent(null);
            ALC10.alcDestroyContext(AL.context);
            AL.context = null;
        }
        if (AL.device != null) {
            ALC10.alcCloseDevice(AL.device);
            AL.device = null;
        }
        resetNativeStubs(AL10.class);
        resetNativeStubs(AL11.class);
        resetNativeStubs(ALC10.class);
        resetNativeStubs(ALC11.class);
        resetNativeStubs(EFX10.class);
        AL.created;
        AL.created = false;
    }
    
    private static native void resetNativeStubs(final Class p0);
    
    public static ALCcontext getContext() {
        return AL.context;
    }
    
    public static ALCdevice getDevice() {
        return AL.device;
    }
}
