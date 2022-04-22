package paulscode.sound.libraries;

import paulscode.sound.*;
import java.util.*;
import javax.sound.sampled.*;

public class LibraryJavaSound extends Library
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final int XXX = 0;
    private final int maxClipSize = 1048576;
    private static Mixer myMixer;
    private static MixerRanking myMixerRanking;
    private static LibraryJavaSound instance;
    private static boolean useGainControl;
    private static boolean usePanControl;
    private static boolean useSampleRateControl;
    
    public LibraryJavaSound() throws SoundSystemException {
        LibraryJavaSound.instance = this;
    }
    
    @Override
    public void init() throws SoundSystemException {
        MixerRanking mixerRanking = null;
        if (LibraryJavaSound.myMixer == null) {
            final Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            while (0 < mixerInfo.length) {
                final Mixer.Info info = mixerInfo[0];
                if (info.getName().equals("Java Sound Audio Engine")) {
                    mixerRanking = new MixerRanking();
                    mixerRanking.rank(info);
                    if (mixerRanking.rank < 14) {
                        break;
                    }
                    LibraryJavaSound.myMixer = AudioSystem.getMixer(info);
                    mixerRanking(true, mixerRanking);
                    break;
                }
                else {
                    int length = 0;
                    ++length;
                }
            }
            if (LibraryJavaSound.myMixer == null) {
                final MixerRanking mixerRanking2 = mixerRanking;
                final int length = AudioSystem.getMixerInfo().length;
                if (mixerRanking2 == null) {
                    throw new Exception("No useable mixers found!", new MixerRanking());
                }
                LibraryJavaSound.myMixer = AudioSystem.getMixer(mixerRanking2.mixerInfo);
                mixerRanking(true, mixerRanking2);
            }
        }
        this.setMasterVolume(1.0f);
        this.message("JavaSound initialized.");
        super.init();
    }
    
    public static boolean libraryCompatible() {
        final Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        while (0 < mixerInfo.length) {
            if (mixerInfo[0].getName().equals("Java Sound Audio Engine")) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    protected Channel createChannel(final int n) {
        return new ChannelJavaSound(n, LibraryJavaSound.myMixer);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        LibraryJavaSound.instance = null;
        LibraryJavaSound.myMixer = null;
        LibraryJavaSound.myMixerRanking = null;
    }
    
    @Override
    public boolean loadSound(final SoundBuffer soundBuffer, final String s) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.errorCheck(s == null, "Identifier not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(s) != null) {
            return true;
        }
        if (soundBuffer != null) {
            this.bufferMap.put(s, soundBuffer);
        }
        else {
            this.errorMessage("Sound buffer null in method 'loadSound'");
        }
        return true;
    }
    
    @Override
    public void setMasterVolume(final float masterVolume) {
        super.setMasterVolume(masterVolume);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    @Override
    public void newSource(final boolean p0, final boolean p1, final boolean p2, final String p3, final FilenameURL p4, final float p5, final float p6, final float p7, final int p8, final float p9) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore          11
        //     3: iload_2        
        //     4: ifne            143
        //     7: aload_0        
        //     8: getfield        paulscode/sound/libraries/LibraryJavaSound.bufferMap:Ljava/util/HashMap;
        //    11: aload           5
        //    13: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    16: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    19: checkcast       Lpaulscode/sound/SoundBuffer;
        //    22: astore          11
        //    24: aload           11
        //    26: ifnonnull       78
        //    29: aload_0        
        //    30: aload           5
        //    32: ifnonnull       78
        //    35: aload_0        
        //    36: new             Ljava/lang/StringBuilder;
        //    39: dup            
        //    40: invokespecial   java/lang/StringBuilder.<init>:()V
        //    43: ldc             "Source '"
        //    45: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    48: aload           4
        //    50: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    53: ldc             "' was not created "
        //    55: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    58: ldc             "because an error occurred while loading "
        //    60: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    63: aload           5
        //    65: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    68: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    71: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    74: invokevirtual   paulscode/sound/libraries/LibraryJavaSound.errorMessage:(Ljava/lang/String;)V
        //    77: return         
        //    78: aload_0        
        //    79: getfield        paulscode/sound/libraries/LibraryJavaSound.bufferMap:Ljava/util/HashMap;
        //    82: aload           5
        //    84: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    87: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    90: checkcast       Lpaulscode/sound/SoundBuffer;
        //    93: astore          11
        //    95: aload           11
        //    97: ifnonnull       143
        //   100: aload_0        
        //   101: new             Ljava/lang/StringBuilder;
        //   104: dup            
        //   105: invokespecial   java/lang/StringBuilder.<init>:()V
        //   108: ldc             "Source '"
        //   110: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   113: aload           4
        //   115: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   118: ldc             "' was not created "
        //   120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   123: ldc             "because audio data was not found for "
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: aload           5
        //   130: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   139: invokevirtual   paulscode/sound/libraries/LibraryJavaSound.errorMessage:(Ljava/lang/String;)V
        //   142: return         
        //   143: iload_2        
        //   144: ifne            159
        //   147: aload           11
        //   149: ifnull          159
        //   152: aload           11
        //   154: ldc             1048576
        //   156: invokevirtual   paulscode/sound/SoundBuffer.trimData:(I)V
        //   159: aload_0        
        //   160: getfield        paulscode/sound/libraries/LibraryJavaSound.sourceMap:Ljava/util/HashMap;
        //   163: aload           4
        //   165: new             Lpaulscode/sound/libraries/SourceJavaSound;
        //   168: dup            
        //   169: aload_0        
        //   170: getfield        paulscode/sound/libraries/LibraryJavaSound.listener:Lpaulscode/sound/ListenerData;
        //   173: iload_1        
        //   174: iload_2        
        //   175: iload_3        
        //   176: aload           4
        //   178: aload           5
        //   180: aload           11
        //   182: fload           6
        //   184: fload           7
        //   186: fload           8
        //   188: iload           9
        //   190: fload           10
        //   192: iconst_0       
        //   193: invokespecial   paulscode/sound/libraries/SourceJavaSound.<init>:(Lpaulscode/sound/ListenerData;ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;Lpaulscode/sound/SoundBuffer;FFFIFZ)V
        //   196: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   199: pop            
        //   200: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0078 (coming from #0032).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void rawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.sourceMap.put(s, new SourceJavaSound(this.listener, audioFormat, b, s, n, n2, n3, n4, n5));
    }
    
    @Override
    public void quickPlay(final boolean p0, final boolean p1, final boolean p2, final String p3, final FilenameURL p4, final float p5, final float p6, final float p7, final int p8, final float p9, final boolean p10) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore          12
        //     3: iload_2        
        //     4: ifne            143
        //     7: aload_0        
        //     8: getfield        paulscode/sound/libraries/LibraryJavaSound.bufferMap:Ljava/util/HashMap;
        //    11: aload           5
        //    13: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    16: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    19: checkcast       Lpaulscode/sound/SoundBuffer;
        //    22: astore          12
        //    24: aload           12
        //    26: ifnonnull       78
        //    29: aload_0        
        //    30: aload           5
        //    32: ifnonnull       78
        //    35: aload_0        
        //    36: new             Ljava/lang/StringBuilder;
        //    39: dup            
        //    40: invokespecial   java/lang/StringBuilder.<init>:()V
        //    43: ldc             "Source '"
        //    45: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    48: aload           4
        //    50: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    53: ldc             "' was not created "
        //    55: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    58: ldc             "because an error occurred while loading "
        //    60: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    63: aload           5
        //    65: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    68: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    71: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    74: invokevirtual   paulscode/sound/libraries/LibraryJavaSound.errorMessage:(Ljava/lang/String;)V
        //    77: return         
        //    78: aload_0        
        //    79: getfield        paulscode/sound/libraries/LibraryJavaSound.bufferMap:Ljava/util/HashMap;
        //    82: aload           5
        //    84: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    87: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    90: checkcast       Lpaulscode/sound/SoundBuffer;
        //    93: astore          12
        //    95: aload           12
        //    97: ifnonnull       143
        //   100: aload_0        
        //   101: new             Ljava/lang/StringBuilder;
        //   104: dup            
        //   105: invokespecial   java/lang/StringBuilder.<init>:()V
        //   108: ldc             "Source '"
        //   110: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   113: aload           4
        //   115: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   118: ldc             "' was not created "
        //   120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   123: ldc             "because audio data was not found for "
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: aload           5
        //   130: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   139: invokevirtual   paulscode/sound/libraries/LibraryJavaSound.errorMessage:(Ljava/lang/String;)V
        //   142: return         
        //   143: iload_2        
        //   144: ifne            159
        //   147: aload           12
        //   149: ifnull          159
        //   152: aload           12
        //   154: ldc             1048576
        //   156: invokevirtual   paulscode/sound/SoundBuffer.trimData:(I)V
        //   159: aload_0        
        //   160: getfield        paulscode/sound/libraries/LibraryJavaSound.sourceMap:Ljava/util/HashMap;
        //   163: aload           4
        //   165: new             Lpaulscode/sound/libraries/SourceJavaSound;
        //   168: dup            
        //   169: aload_0        
        //   170: getfield        paulscode/sound/libraries/LibraryJavaSound.listener:Lpaulscode/sound/ListenerData;
        //   173: iload_1        
        //   174: iload_2        
        //   175: iload_3        
        //   176: aload           4
        //   178: aload           5
        //   180: aload           12
        //   182: fload           6
        //   184: fload           7
        //   186: fload           8
        //   188: iload           9
        //   190: fload           10
        //   192: iload           11
        //   194: invokespecial   paulscode/sound/libraries/SourceJavaSound.<init>:(Lpaulscode/sound/ListenerData;ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;Lpaulscode/sound/SoundBuffer;FFFIFZ)V
        //   197: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   200: pop            
        //   201: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0078 (coming from #0032).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void copySources(final HashMap hashMap) {
        if (hashMap == null) {
            return;
        }
        final Iterator<String> iterator = hashMap.keySet().iterator();
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'copySources'");
        }
        this.sourceMap.clear();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Source source = (Source)hashMap.get(s);
            if (source != null) {
                SoundBuffer soundBuffer = null;
                if (!source.toStream) {
                    this.loadSound(source.filenameURL);
                    soundBuffer = this.bufferMap.get(source.filenameURL.getFilename());
                }
                if (!source.toStream && soundBuffer != null) {
                    soundBuffer.trimData(1048576);
                }
                if (!source.toStream && soundBuffer == null) {
                    continue;
                }
                this.sourceMap.put(s, new SourceJavaSound(this.listener, source, soundBuffer));
            }
        }
    }
    
    @Override
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        super.setListenerVelocity(n, n2, n3);
        this.listenerMoved();
    }
    
    @Override
    public void dopplerChanged() {
        super.dopplerChanged();
        this.listenerMoved();
    }
    
    public static Mixer getMixer() {
        return mixer(false, null);
    }
    
    public static void setMixer(final Mixer mixer) throws SoundSystemException {
        mixer(true, mixer);
        final SoundSystemException lastException = SoundSystem.getLastException();
        SoundSystem.setException(null);
        if (lastException != null) {
            throw lastException;
        }
    }
    
    private static synchronized Mixer mixer(final boolean b, final Mixer myMixer) {
        if (b) {
            if (myMixer == null) {
                return LibraryJavaSound.myMixer;
            }
            final MixerRanking mixerRanking = new MixerRanking();
            mixerRanking.rank(myMixer.getMixerInfo());
            LibraryJavaSound.myMixer = myMixer;
            mixerRanking(true, mixerRanking);
            if (LibraryJavaSound.instance != null) {
                final ListIterator<ChannelJavaSound> listIterator = LibraryJavaSound.instance.normalChannels.listIterator();
                SoundSystem.setException(null);
                while (listIterator.hasNext()) {
                    listIterator.next().newMixer(myMixer);
                }
                final ListIterator<ChannelJavaSound> listIterator2 = LibraryJavaSound.instance.streamingChannels.listIterator();
                while (listIterator2.hasNext()) {
                    listIterator2.next().newMixer(myMixer);
                }
            }
        }
        return LibraryJavaSound.myMixer;
    }
    
    public static MixerRanking getMixerRanking() {
        return mixerRanking(false, null);
    }
    
    private static synchronized MixerRanking mixerRanking(final boolean b, final MixerRanking myMixerRanking) {
        if (b) {
            LibraryJavaSound.myMixerRanking = myMixerRanking;
        }
        return LibraryJavaSound.myMixerRanking;
    }
    
    public static void setMinSampleRate(final int n) {
        minSampleRate(true, n);
    }
    
    private static synchronized int minSampleRate(final boolean b, final int minSampleRate) {
        if (b) {
            LibraryJavaSound.minSampleRate = minSampleRate;
        }
        return 4000;
    }
    
    public static void setMaxSampleRate(final int n) {
        maxSampleRate(true, n);
    }
    
    private static synchronized int maxSampleRate(final boolean b, final int maxSampleRate) {
        if (b) {
            LibraryJavaSound.maxSampleRate = maxSampleRate;
        }
        return 48000;
    }
    
    public static void setLineCount(final int n) {
        lineCount(true, n);
    }
    
    private static synchronized int lineCount(final boolean b, final int lineCount) {
        if (b) {
            LibraryJavaSound.lineCount = lineCount;
        }
        return 32;
    }
    
    public static void useGainControl(final boolean b) {
        useGainControl(true, b);
    }
    
    private static synchronized boolean useGainControl(final boolean b, final boolean useGainControl) {
        if (b) {
            LibraryJavaSound.useGainControl = useGainControl;
        }
        return LibraryJavaSound.useGainControl;
    }
    
    public static void usePanControl(final boolean b) {
        usePanControl(true, b);
    }
    
    private static synchronized boolean usePanControl(final boolean b, final boolean usePanControl) {
        if (b) {
            LibraryJavaSound.usePanControl = usePanControl;
        }
        return LibraryJavaSound.usePanControl;
    }
    
    public static void useSampleRateControl(final boolean b) {
        useSampleRateControl(true, b);
    }
    
    private static synchronized boolean useSampleRateControl(final boolean b, final boolean useSampleRateControl) {
        if (b) {
            LibraryJavaSound.useSampleRateControl = useSampleRateControl;
        }
        return LibraryJavaSound.useSampleRateControl;
    }
    
    public static String getTitle() {
        return "Java Sound";
    }
    
    public static String getDescription() {
        return "The Java Sound API.  For more information, see http://java.sun.com/products/java-media/sound/";
    }
    
    @Override
    public String getClassName() {
        return "LibraryJavaSound";
    }
    
    static int access$000(final boolean b, final int n) {
        return minSampleRate(b, n);
    }
    
    static int access$100(final boolean b, final int n) {
        return maxSampleRate(b, n);
    }
    
    static int access$200(final boolean b, final int n) {
        return lineCount(b, n);
    }
    
    static boolean access$300(final boolean b, final boolean b2) {
        return useGainControl(b, b2);
    }
    
    static boolean access$400(final boolean b, final boolean b2) {
        return usePanControl(b, b2);
    }
    
    static boolean access$500(final boolean b, final boolean b2) {
        return useSampleRateControl(b, b2);
    }
    
    static {
        LibraryJavaSound.myMixer = null;
        LibraryJavaSound.myMixerRanking = null;
        LibraryJavaSound.instance = null;
        LibraryJavaSound.useGainControl = true;
        LibraryJavaSound.usePanControl = true;
        LibraryJavaSound.useSampleRateControl = true;
    }
    
    public static class Exception extends SoundSystemException
    {
        public static final int MIXER_PROBLEM = 101;
        public static MixerRanking mixerRanking;
        
        public Exception(final String s) {
            super(s);
        }
        
        public Exception(final String s, final int n) {
            super(s, n);
        }
        
        public Exception(final String s, final MixerRanking mixerRanking) {
            super(s, 101);
            Exception.mixerRanking = mixerRanking;
        }
        
        static {
            Exception.mixerRanking = null;
        }
    }
    
    public static class MixerRanking
    {
        public static final int HIGH = 1;
        public static final int MEDIUM = 2;
        public static final int LOW = 3;
        public static final int NONE = 4;
        public Mixer.Info mixerInfo;
        public int rank;
        public boolean mixerExists;
        public boolean minSampleRateOK;
        public boolean maxSampleRateOK;
        public boolean lineCountOK;
        public boolean gainControlOK;
        public boolean panControlOK;
        public boolean sampleRateControlOK;
        public int minSampleRatePossible;
        public int maxSampleRatePossible;
        public int maxLinesPossible;
        
        public MixerRanking() {
            this.mixerInfo = null;
            this.rank = 0;
            this.mixerExists = false;
            this.minSampleRateOK = false;
            this.maxSampleRateOK = false;
            this.lineCountOK = false;
            this.gainControlOK = false;
            this.panControlOK = false;
            this.sampleRateControlOK = false;
            this.minSampleRatePossible = -1;
            this.maxSampleRatePossible = -1;
            this.maxLinesPossible = 0;
        }
        
        public MixerRanking(final Mixer.Info mixerInfo, final int rank, final boolean mixerExists, final boolean minSampleRateOK, final boolean maxSampleRateOK, final boolean lineCountOK, final boolean gainControlOK, final boolean panControlOK, final boolean sampleRateControlOK) {
            this.mixerInfo = null;
            this.rank = 0;
            this.mixerExists = false;
            this.minSampleRateOK = false;
            this.maxSampleRateOK = false;
            this.lineCountOK = false;
            this.gainControlOK = false;
            this.panControlOK = false;
            this.sampleRateControlOK = false;
            this.minSampleRatePossible = -1;
            this.maxSampleRatePossible = -1;
            this.maxLinesPossible = 0;
            this.mixerInfo = mixerInfo;
            this.rank = rank;
            this.mixerExists = mixerExists;
            this.minSampleRateOK = minSampleRateOK;
            this.maxSampleRateOK = maxSampleRateOK;
            this.lineCountOK = lineCountOK;
            this.gainControlOK = gainControlOK;
            this.panControlOK = panControlOK;
            this.sampleRateControlOK = sampleRateControlOK;
        }
        
        public void rank(final Mixer.Info mixerInfo) throws Exception {
            if (mixerInfo == null) {
                throw new Exception("No Mixer info specified in method 'MixerRanking.rank'", this);
            }
            this.mixerInfo = mixerInfo;
            final Mixer mixer = AudioSystem.getMixer(this.mixerInfo);
            if (mixer == null) {
                throw new Exception("Unable to acquire the specified Mixer in method 'MixerRanking.rank'", this);
            }
            this.mixerExists = true;
            if (!AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, new AudioFormat((float)LibraryJavaSound.access$000(false, 0), 16, 2, true, false)))) {
                if (true == true) {
                    throw new Exception("Specified minimum sample-rate not possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            }
            else {
                this.minSampleRateOK = true;
            }
            if (!AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, new AudioFormat((float)LibraryJavaSound.access$100(false, 0), 16, 2, true, false)))) {
                if (true == true) {
                    throw new Exception("Specified maximum sample-rate not possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            }
            else {
                this.maxSampleRateOK = true;
            }
            if (this.minSampleRateOK) {
                this.minSampleRatePossible = LibraryJavaSound.access$000(false, 0);
            }
            else {
                int access$000 = LibraryJavaSound.access$000(false, 0);
                int access$2 = LibraryJavaSound.access$100(false, 0);
                while (access$2 - access$000 > 1) {
                    final int minSampleRatePossible = access$000 + (access$2 - access$000) / 2;
                    if (AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, new AudioFormat((float)minSampleRatePossible, 16, 2, true, false)))) {
                        this.minSampleRatePossible = minSampleRatePossible;
                        access$2 = minSampleRatePossible;
                    }
                    else {
                        access$000 = minSampleRatePossible;
                    }
                }
            }
            if (this.maxSampleRateOK) {
                this.maxSampleRatePossible = LibraryJavaSound.access$100(false, 0);
            }
            else if (this.minSampleRatePossible != -1) {
                int access$3 = LibraryJavaSound.access$100(false, 0);
                int minSampleRatePossible2 = this.minSampleRatePossible;
                while (access$3 - minSampleRatePossible2 > 1) {
                    final int maxSampleRatePossible = minSampleRatePossible2 + (access$3 - minSampleRatePossible2) / 2;
                    if (AudioSystem.isLineSupported(new DataLine.Info(SourceDataLine.class, new AudioFormat((float)maxSampleRatePossible, 16, 2, true, false)))) {
                        this.maxSampleRatePossible = maxSampleRatePossible;
                        minSampleRatePossible2 = maxSampleRatePossible;
                    }
                    else {
                        access$3 = maxSampleRatePossible;
                    }
                }
            }
            if (this.minSampleRatePossible == -1 || this.maxSampleRatePossible == -1) {
                throw new Exception("No possible sample-rate found for Mixer '" + this.mixerInfo.getName() + "'", this);
            }
            final AudioFormat audioFormat = new AudioFormat((float)this.minSampleRatePossible, 16, 2, true, false);
            final Clip clip = (Clip)mixer.getLine(new DataLine.Info(Clip.class, audioFormat));
            final byte[] array = new byte[10];
            clip.open(audioFormat, array, 0, array.length);
            this.maxLinesPossible = 1;
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            final SourceDataLine[] array2 = new SourceDataLine[LibraryJavaSound.access$200(false, 0) - 1];
            while (1 < array2.length + 1) {
                array2[0] = (SourceDataLine)mixer.getLine(info);
                this.maxLinesPossible = 2;
                int n = 0;
                ++n;
            }
            clip.close();
            if (this.maxLinesPossible == LibraryJavaSound.access$200(false, 0)) {
                this.lineCountOK = true;
            }
            if (!LibraryJavaSound.access$300(false, false)) {
                MixerRanking.GAIN_CONTROL_PRIORITY = 4;
            }
            else if (!array2[0].isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                if (2 == 1) {
                    throw new Exception("Gain control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            }
            else {
                this.gainControlOK = true;
            }
            if (!LibraryJavaSound.access$400(false, false)) {
                MixerRanking.PAN_CONTROL_PRIORITY = 4;
            }
            else if (!array2[0].isControlSupported(FloatControl.Type.PAN)) {
                if (2 == 1) {
                    throw new Exception("Pan control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            }
            else {
                this.panControlOK = true;
            }
            if (!LibraryJavaSound.access$500(false, false)) {
                MixerRanking.SAMPLE_RATE_CONTROL_PRIORITY = 4;
            }
            else if (!array2[0].isControlSupported(FloatControl.Type.SAMPLE_RATE)) {
                if (3 == 1) {
                    throw new Exception("Sample-rate control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            }
            else {
                this.sampleRateControlOK = true;
            }
            this.rank += this.getRankValue(this.mixerExists, 1);
            this.rank += this.getRankValue(this.minSampleRateOK, 1);
            this.rank += this.getRankValue(this.maxSampleRateOK, 1);
            this.rank += this.getRankValue(this.lineCountOK, 1);
            this.rank += this.getRankValue(this.gainControlOK, 2);
            this.rank += this.getRankValue(this.panControlOK, 2);
            this.rank += this.getRankValue(this.sampleRateControlOK, 3);
        }
        
        private int getRankValue(final boolean b, final int n) {
            if (b) {
                return 2;
            }
            if (n == 4) {
                return 2;
            }
            if (n == 3) {
                return 1;
            }
            return 0;
        }
    }
}
