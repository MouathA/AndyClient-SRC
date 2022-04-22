package org.lwjgl.openal;

public final class Util
{
    private Util() {
    }
    
    public static void checkALCError(final ALCdevice alCdevice) {
        final int alcGetError = ALC10.alcGetError(alCdevice);
        if (alcGetError != 0) {
            throw new OpenALException(ALC10.alcGetString(AL.getDevice(), alcGetError));
        }
    }
    
    public static void checkALError() {
        final int alGetError = AL10.alGetError();
        if (alGetError != 0) {
            throw new OpenALException(alGetError);
        }
    }
    
    public static void checkALCValidDevice(final ALCdevice alCdevice) {
        if (!alCdevice.isValid()) {
            throw new OpenALException("Invalid device: " + alCdevice);
        }
    }
    
    public static void checkALCValidContext(final ALCcontext alCcontext) {
        if (!alCcontext.isValid()) {
            throw new OpenALException("Invalid context: " + alCcontext);
        }
    }
}
