package paulscode.sound;

import java.lang.reflect.*;
import java.util.*;

public class SoundSystemConfig
{
    public static final Object THREAD_SYNC;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_STREAMING = 1;
    public static final int ATTENUATION_NONE = 0;
    public static final int ATTENUATION_ROLLOFF = 1;
    public static final int ATTENUATION_LINEAR = 2;
    public static String EXTENSION_MIDI;
    public static String PREFIX_URL;
    private static SoundSystemLogger logger;
    private static LinkedList libraries;
    private static LinkedList codecs;
    private static LinkedList streamListeners;
    private static final Object streamListenersLock;
    private static String soundFilesPackage;
    private static boolean streamQueueFormatsMatch;
    private static boolean midiCodec;
    private static String overrideMIDISynthesizer;
    
    public static void addLibrary(final Class clazz) throws SoundSystemException {
        if (clazz == null) {
            throw new SoundSystemException("Parameter null in method 'addLibrary'", 2);
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            throw new SoundSystemException("The specified class does not extend class 'Library' in method 'addLibrary'");
        }
        if (SoundSystemConfig.libraries == null) {
            SoundSystemConfig.libraries = new LinkedList();
        }
        if (!SoundSystemConfig.libraries.contains(clazz)) {
            SoundSystemConfig.libraries.add(clazz);
        }
    }
    
    public static void removeLibrary(final Class clazz) throws SoundSystemException {
        if (SoundSystemConfig.libraries == null || clazz == null) {
            return;
        }
        SoundSystemConfig.libraries.remove(clazz);
    }
    
    public static LinkedList getLibraries() {
        return SoundSystemConfig.libraries;
    }
    
    public static boolean libraryCompatible(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'librayCompatible'");
            return false;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'libraryCompatible'");
            return false;
        }
        final Object runMethod = runMethod(clazz, "libraryCompatible", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.libraryCompatible' returned 'null' in method 'libraryCompatible'");
            return false;
        }
        return (boolean)runMethod;
    }
    
    public static String getLibraryTitle(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'getLibrayTitle'");
            return null;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'getLibraryTitle'");
            return null;
        }
        final Object runMethod = runMethod(clazz, "getTitle", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.getTitle' returned 'null' in method 'getLibraryTitle'");
            return null;
        }
        return (String)runMethod;
    }
    
    public static String getLibraryDescription(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'getLibrayDescription'");
            return null;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'getLibraryDescription'");
            return null;
        }
        final Object runMethod = runMethod(clazz, "getDescription", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.getDescription' returned 'null' in method 'getLibraryDescription'");
            return null;
        }
        return (String)runMethod;
    }
    
    public static boolean reverseByteOrder(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'reverseByteOrder'");
            return false;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'reverseByteOrder'");
            return false;
        }
        final Object runMethod = runMethod(clazz, "reversByteOrder", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.reverseByteOrder' returned 'null' in method 'getLibraryDescription'");
            return false;
        }
        return (boolean)runMethod;
    }
    
    public static void setLogger(final SoundSystemLogger logger) {
        SoundSystemConfig.logger = logger;
    }
    
    public static SoundSystemLogger getLogger() {
        return SoundSystemConfig.logger;
    }
    
    public static synchronized void setNumberNormalChannels(final int numberNormalChannels) {
        SoundSystemConfig.numberNormalChannels = numberNormalChannels;
    }
    
    public static synchronized int getNumberNormalChannels() {
        return 28;
    }
    
    public static synchronized void setNumberStreamingChannels(final int numberStreamingChannels) {
        SoundSystemConfig.numberStreamingChannels = numberStreamingChannels;
    }
    
    public static synchronized int getNumberStreamingChannels() {
        return 4;
    }
    
    public static synchronized void setMasterGain(final float masterGain) {
        SoundSystemConfig.masterGain = masterGain;
    }
    
    public static synchronized float getMasterGain() {
        return 1.0f;
    }
    
    public static synchronized void setDefaultAttenuation(final int defaultAttenuationModel) {
        SoundSystemConfig.defaultAttenuationModel = defaultAttenuationModel;
    }
    
    public static synchronized int getDefaultAttenuation() {
        return 1;
    }
    
    public static synchronized void setDefaultRolloff(final float defaultRolloffFactor) {
        SoundSystemConfig.defaultRolloffFactor = defaultRolloffFactor;
    }
    
    public static synchronized float getDopplerFactor() {
        return 0.0f;
    }
    
    public static synchronized void setDopplerFactor(final float dopplerFactor) {
        SoundSystemConfig.dopplerFactor = dopplerFactor;
    }
    
    public static synchronized float getDopplerVelocity() {
        return 1.0f;
    }
    
    public static synchronized void setDopplerVelocity(final float dopplerVelocity) {
        SoundSystemConfig.dopplerVelocity = dopplerVelocity;
    }
    
    public static synchronized float getDefaultRolloff() {
        return 0.0f;
    }
    
    public static synchronized void setDefaultFadeDistance(final float defaultFadeDistance) {
        SoundSystemConfig.defaultFadeDistance = defaultFadeDistance;
    }
    
    public static synchronized float getDefaultFadeDistance() {
        return 1000.0f;
    }
    
    public static synchronized void setSoundFilesPackage(final String soundFilesPackage) {
        SoundSystemConfig.soundFilesPackage = soundFilesPackage;
    }
    
    public static synchronized String getSoundFilesPackage() {
        return SoundSystemConfig.soundFilesPackage;
    }
    
    public static synchronized void setStreamingBufferSize(final int streamingBufferSize) {
        SoundSystemConfig.streamingBufferSize = streamingBufferSize;
    }
    
    public static synchronized int getStreamingBufferSize() {
        return 131072;
    }
    
    public static synchronized void setNumberStreamingBuffers(final int numberStreamingBuffers) {
        SoundSystemConfig.numberStreamingBuffers = numberStreamingBuffers;
    }
    
    public static synchronized int getNumberStreamingBuffers() {
        return 3;
    }
    
    public static synchronized void setStreamQueueFormatsMatch(final boolean streamQueueFormatsMatch) {
        SoundSystemConfig.streamQueueFormatsMatch = streamQueueFormatsMatch;
    }
    
    public static synchronized boolean getStreamQueueFormatsMatch() {
        return SoundSystemConfig.streamQueueFormatsMatch;
    }
    
    public static synchronized void setMaxFileSize(final int maxFileSize) {
        SoundSystemConfig.maxFileSize = maxFileSize;
    }
    
    public static synchronized int getMaxFileSize() {
        return 268435456;
    }
    
    public static synchronized void setFileChunkSize(final int fileChunkSize) {
        SoundSystemConfig.fileChunkSize = fileChunkSize;
    }
    
    public static synchronized int getFileChunkSize() {
        return 1048576;
    }
    
    public static synchronized String getOverrideMIDISynthesizer() {
        return SoundSystemConfig.overrideMIDISynthesizer;
    }
    
    public static synchronized void setOverrideMIDISynthesizer(final String overrideMIDISynthesizer) {
        SoundSystemConfig.overrideMIDISynthesizer = overrideMIDISynthesizer;
    }
    
    public static synchronized void setCodec(final String s, final Class clazz) throws SoundSystemException {
        if (s == null) {
            throw new SoundSystemException("Parameter 'extension' null in method 'setCodec'.", 2);
        }
        if (clazz == null) {
            throw new SoundSystemException("Parameter 'iCodecClass' null in method 'setCodec'.", 2);
        }
        if (!ICodec.class.isAssignableFrom(clazz)) {
            throw new SoundSystemException("The specified class does not implement interface 'ICodec' in method 'setCodec'", 3);
        }
        if (SoundSystemConfig.codecs == null) {
            SoundSystemConfig.codecs = new LinkedList();
        }
        final ListIterator listIterator = SoundSystemConfig.codecs.listIterator();
        while (listIterator.hasNext()) {
            if (s.matches(listIterator.next().extensionRegX)) {
                listIterator.remove();
            }
        }
        SoundSystemConfig.codecs.add(new Codec(s, clazz));
        if (s.matches(SoundSystemConfig.EXTENSION_MIDI)) {
            SoundSystemConfig.midiCodec = true;
        }
    }
    
    public static synchronized ICodec getCodec(final String s) {
        if (SoundSystemConfig.codecs == null) {
            return null;
        }
        final ListIterator listIterator = SoundSystemConfig.codecs.listIterator();
        while (listIterator.hasNext()) {
            final Codec codec = listIterator.next();
            if (s.matches(codec.extensionRegX)) {
                return codec.getInstance();
            }
        }
        return null;
    }
    
    public static boolean midiCodec() {
        return SoundSystemConfig.midiCodec;
    }
    
    public static void addStreamListener(final IStreamListener streamListener) {
        // monitorenter(streamListenersLock = SoundSystemConfig.streamListenersLock)
        if (SoundSystemConfig.streamListeners == null) {
            SoundSystemConfig.streamListeners = new LinkedList();
        }
        if (!SoundSystemConfig.streamListeners.contains(streamListener)) {
            SoundSystemConfig.streamListeners.add(streamListener);
        }
    }
    // monitorexit(streamListenersLock)
    
    public static void removeStreamListener(final IStreamListener streamListener) {
        // monitorenter(streamListenersLock = SoundSystemConfig.streamListenersLock)
        if (SoundSystemConfig.streamListeners == null) {
            SoundSystemConfig.streamListeners = new LinkedList();
        }
        if (SoundSystemConfig.streamListeners.contains(streamListener)) {
            SoundSystemConfig.streamListeners.remove(streamListener);
        }
    }
    // monitorexit(streamListenersLock)
    
    public static void notifyEOS(final String s, final int n) {
        // monitorenter(streamListenersLock = SoundSystemConfig.streamListenersLock)
        if (SoundSystemConfig.streamListeners == null) {
            // monitorexit(streamListenersLock)
            return;
        }
        // monitorexit(streamListenersLock)
        new Thread(s, n) {
            final String val$srcName;
            final int val$qSize;
            
            @Override
            public void run() {
                // monitorenter(access$000 = SoundSystemConfig.access$000())
                if (SoundSystemConfig.access$100() == null) {
                    // monitorexit(access$000)
                    return;
                }
                final ListIterator listIterator = SoundSystemConfig.access$100().listIterator();
                while (listIterator.hasNext()) {
                    final IStreamListener streamListener = listIterator.next();
                    if (streamListener == null) {
                        listIterator.remove();
                    }
                    else {
                        streamListener.endOfStream(this.val$srcName, this.val$qSize);
                    }
                }
            }
            // monitorexit(access$000)
        }.start();
    }
    
    private static void errorMessage(final String s) {
        if (SoundSystemConfig.logger != null) {
            SoundSystemConfig.logger.errorMessage("SoundSystemConfig", s, 0);
        }
    }
    
    private static Object runMethod(final Class clazz, final String s, final Class[] array, final Object[] array2) {
        final Method method = clazz.getMethod(s, (Class[])array);
        if (method == null) {
            errorMessage("Method '" + s + "' not found for the class " + "specified in method 'runMethod'");
            return null;
        }
        return method.invoke(null, array2);
    }
    
    static Object access$000() {
        return SoundSystemConfig.streamListenersLock;
    }
    
    static LinkedList access$100() {
        return SoundSystemConfig.streamListeners;
    }
    
    static void access$200(final String s) {
        errorMessage(s);
    }
    
    static {
        THREAD_SYNC = new Object();
        SoundSystemConfig.EXTENSION_MIDI = ".*[mM][iI][dD][iI]?$";
        SoundSystemConfig.PREFIX_URL = "^[hH][tT][tT][pP]://.*";
        SoundSystemConfig.logger = null;
        SoundSystemConfig.codecs = null;
        SoundSystemConfig.streamListeners = null;
        streamListenersLock = new Object();
        SoundSystemConfig.soundFilesPackage = "Sounds/";
        SoundSystemConfig.streamQueueFormatsMatch = false;
        SoundSystemConfig.midiCodec = false;
        SoundSystemConfig.overrideMIDISynthesizer = "";
    }
    
    private static class Codec
    {
        public String extensionRegX;
        public Class iCodecClass;
        
        public Codec(final String s, final Class iCodecClass) {
            this.extensionRegX = "";
            if (s != null && s.length() > 0) {
                this.extensionRegX = ".*";
                while (0 < s.length()) {
                    final String substring = s.substring(0, 1);
                    this.extensionRegX = this.extensionRegX + "[" + substring.toLowerCase(Locale.ENGLISH) + substring.toUpperCase(Locale.ENGLISH) + "]";
                    int n = 0;
                    ++n;
                }
                this.extensionRegX += "$";
            }
            this.iCodecClass = iCodecClass;
        }
        
        public ICodec getInstance() {
            if (this.iCodecClass == null) {
                return null;
            }
            final ICodec instance = this.iCodecClass.newInstance();
            if (instance == null) {
                this.instantiationErrorMessage();
                return null;
            }
            return instance;
        }
        
        private void instantiationErrorMessage() {
            SoundSystemConfig.access$200("Unrecognized ICodec implementation in method 'getInstance'.  Ensure that the implementing class has one public, parameterless constructor.");
        }
    }
}
