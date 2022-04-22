package org.lwjgl.openal;

public final class EFXUtil
{
    private static final int EFFECT = 1111;
    private static final int FILTER = 2222;
    
    private EFXUtil() {
    }
    
    public static boolean isEfxSupported() {
        if (!AL.isCreated()) {
            throw new OpenALException("OpenAL has not been created.");
        }
        return ALC10.alcIsExtensionPresent(AL.getDevice(), "ALC_EXT_EFX");
    }
    
    public static boolean isEffectSupported(final int n) {
        switch (n) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 32768: {
                return testSupportGeneric(1111, n);
            }
            default: {
                throw new IllegalArgumentException("Unknown or invalid effect type: " + n);
            }
        }
    }
    
    public static boolean isFilterSupported(final int n) {
        switch (n) {
            case 0:
            case 1:
            case 2:
            case 3: {
                return testSupportGeneric(2222, n);
            }
            default: {
                throw new IllegalArgumentException("Unknown or invalid filter type: " + n);
            }
        }
    }
    
    private static boolean testSupportGeneric(final int n, final int n2) {
        switch (n) {
            case 1111:
            case 2222: {
                if (isEfxSupported()) {
                    AL10.alGetError();
                    switch (n) {
                        case 1111: {
                            EFX10.alGenEffects();
                            break;
                        }
                        case 2222: {
                            EFX10.alGenFilters();
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid objectType: " + n);
                        }
                    }
                    AL10.alGetError();
                    if (40964 == 0) {
                        AL10.alGetError();
                        switch (n) {
                            case 1111: {
                                EFX10.alEffecti(0, 32769, n2);
                                break;
                            }
                            case 2222: {
                                EFX10.alFilteri(0, 32769, n2);
                                break;
                            }
                            default: {
                                throw new IllegalArgumentException("Invalid objectType: " + n);
                            }
                        }
                        AL10.alGetError();
                        if (40963 == 0) {}
                        switch (n) {
                            case 1111: {
                                EFX10.alDeleteEffects(0);
                                break;
                            }
                            case 2222: {
                                EFX10.alDeleteFilters(0);
                                break;
                            }
                            default: {
                                throw new IllegalArgumentException("Invalid objectType: " + n);
                            }
                        }
                    }
                    else if (40964 == 40965) {
                        throw new OpenALException(40964);
                    }
                }
                return true;
            }
            default: {
                throw new IllegalArgumentException("Invalid objectType: " + n);
            }
        }
    }
}
