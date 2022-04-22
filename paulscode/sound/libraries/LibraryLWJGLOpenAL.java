package paulscode.sound.libraries;

import org.lwjgl.*;
import org.lwjgl.openal.*;
import java.util.*;
import java.nio.*;
import javax.sound.sampled.*;
import paulscode.sound.*;

public class LibraryLWJGLOpenAL extends Library
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private FloatBuffer listenerPositionAL;
    private FloatBuffer listenerOrientation;
    private FloatBuffer listenerVelocity;
    private HashMap ALBufferMap;
    private static boolean alPitchSupported;
    
    public LibraryLWJGLOpenAL() throws SoundSystemException {
        this.listenerPositionAL = null;
        this.listenerOrientation = null;
        this.listenerVelocity = null;
        this.ALBufferMap = null;
        this.ALBufferMap = new HashMap();
        this.reverseByteOrder = true;
    }
    
    @Override
    public void init() throws SoundSystemException {
        this.checkALError();
        this.message("OpenAL initialized.");
        this.listenerPositionAL = BufferUtils.createFloatBuffer(3).put(new float[] { this.listener.position.x, this.listener.position.y, this.listener.position.z });
        this.listenerOrientation = BufferUtils.createFloatBuffer(6).put(new float[] { this.listener.lookAt.x, this.listener.lookAt.y, this.listener.lookAt.z, this.listener.up.x, this.listener.up.y, this.listener.up.z });
        this.listenerVelocity = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        this.listenerPositionAL.flip();
        this.listenerOrientation.flip();
        this.listenerVelocity.flip();
        AL10.alListener(4100, this.listenerPositionAL);
        if (!this.checkALError()) {}
        AL10.alListener(4111, this.listenerOrientation);
        if (!this.checkALError()) {}
        AL10.alListener(4102, this.listenerVelocity);
        if (!this.checkALError()) {}
        AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
        if (!this.checkALError()) {}
        AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
        if (!this.checkALError()) {}
        super.init();
        AL10.alSourcef(this.normalChannels.get(1).ALSource.get(0), 4099, 1.0f);
        if (this.checkALError()) {
            alPitchSupported(true, false);
            throw new Exception("OpenAL: AL_PITCH not supported.", 108);
        }
        alPitchSupported(true, true);
    }
    
    public static boolean libraryCompatible() {
        return !AL.isCreated() || true;
    }
    
    @Override
    protected Channel createChannel(final int n) {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alGenSources(intBuffer);
        if (AL10.alGetError() != 0) {
            return null;
        }
        return new ChannelLWJGLOpenAL(n, intBuffer);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        final Iterator<String> iterator = this.bufferMap.keySet().iterator();
        while (iterator.hasNext()) {
            final IntBuffer intBuffer = this.ALBufferMap.get(iterator.next());
            if (intBuffer != null) {
                AL10.alDeleteBuffers(intBuffer);
                this.checkALError();
                intBuffer.clear();
            }
        }
        this.bufferMap.clear();
        this.bufferMap = null;
        this.listenerPositionAL = null;
        this.listenerOrientation = null;
        this.listenerVelocity = null;
    }
    
    @Override
    public boolean loadSound(final SoundBuffer soundBuffer, final String s) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.ALBufferMap == null) {
            this.ALBufferMap = new HashMap();
            this.importantMessage("Open AL Buffer Map was null in method'loadSound'");
        }
        if (this.errorCheck(s == null, "Identifier not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(s) != null) {
            return true;
        }
        if (this.errorCheck(soundBuffer == null, "Sound buffer null in method 'loadSound'")) {
            return false;
        }
        this.bufferMap.put(s, soundBuffer);
        final AudioFormat audioFormat = soundBuffer.audioFormat;
        if (audioFormat.getChannels() == 1) {
            if (audioFormat.getSampleSizeInBits() != 8) {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
            }
        }
        else {
            if (audioFormat.getChannels() != 2) {
                this.errorMessage("File neither mono nor stereo in method 'loadSound'");
                return false;
            }
            if (audioFormat.getSampleSizeInBits() != 8) {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
            }
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(intBuffer);
        if (this.errorCheck(AL10.alGetError() != 0, "alGenBuffers error when saving " + s)) {
            return false;
        }
        AL10.alBufferData(intBuffer.get(0), 4355, (ByteBuffer)BufferUtils.createByteBuffer(soundBuffer.audioData.length).put(soundBuffer.audioData).flip(), (int)audioFormat.getSampleRate());
        if (this.errorCheck(AL10.alGetError() != 0, "alBufferData error when saving " + s) && this.errorCheck(intBuffer == null, "Sound buffer was not created for " + s)) {
            return false;
        }
        this.ALBufferMap.put(s, intBuffer);
        return true;
    }
    
    @Override
    public void unloadSound(final String s) {
        this.ALBufferMap.remove(s);
        super.unloadSound(s);
    }
    
    @Override
    public void setMasterVolume(final float masterVolume) {
        super.setMasterVolume(masterVolume);
        AL10.alListenerf(4106, masterVolume);
        this.checkALError();
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
        //     4: ifne            149
        //     7: aload_0        
        //     8: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.ALBufferMap:Ljava/util/HashMap;
        //    11: aload           5
        //    13: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    16: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    19: checkcast       Ljava/nio/IntBuffer;
        //    22: astore          11
        //    24: aload           11
        //    26: ifnonnull       81
        //    29: aload_0        
        //    30: aload           5
        //    32: ifnonnull       81
        //    35: aload_0        
        //    36: new             Ljava/lang/StringBuilder;
        //    39: dup            
        //    40: invokespecial   java/lang/StringBuilder.<init>:()V
        //    43: ldc_w           "Source '"
        //    46: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    49: aload           4
        //    51: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    54: ldc_w           "' was not created "
        //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    60: ldc_w           "because an error occurred while loading "
        //    63: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    66: aload           5
        //    68: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    77: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //    80: return         
        //    81: aload_0        
        //    82: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.ALBufferMap:Ljava/util/HashMap;
        //    85: aload           5
        //    87: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    90: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    93: checkcast       Ljava/nio/IntBuffer;
        //    96: astore          11
        //    98: aload           11
        //   100: ifnonnull       149
        //   103: aload_0        
        //   104: new             Ljava/lang/StringBuilder;
        //   107: dup            
        //   108: invokespecial   java/lang/StringBuilder.<init>:()V
        //   111: ldc_w           "Source '"
        //   114: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   117: aload           4
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: ldc_w           "' was not created "
        //   125: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   128: ldc_w           "because a sound buffer was not found for "
        //   131: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   134: aload           5
        //   136: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   139: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   142: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   145: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //   148: return         
        //   149: aconst_null    
        //   150: astore          12
        //   152: iload_2        
        //   153: ifne            298
        //   156: aload_0        
        //   157: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.bufferMap:Ljava/util/HashMap;
        //   160: aload           5
        //   162: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   165: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   168: checkcast       Lpaulscode/sound/SoundBuffer;
        //   171: astore          12
        //   173: aload           12
        //   175: ifnonnull       230
        //   178: aload_0        
        //   179: aload           5
        //   181: ifnonnull       230
        //   184: aload_0        
        //   185: new             Ljava/lang/StringBuilder;
        //   188: dup            
        //   189: invokespecial   java/lang/StringBuilder.<init>:()V
        //   192: ldc_w           "Source '"
        //   195: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   198: aload           4
        //   200: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   203: ldc_w           "' was not created "
        //   206: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   209: ldc_w           "because an error occurred while loading "
        //   212: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   215: aload           5
        //   217: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   220: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   223: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   226: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //   229: return         
        //   230: aload_0        
        //   231: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.bufferMap:Ljava/util/HashMap;
        //   234: aload           5
        //   236: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   239: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   242: checkcast       Lpaulscode/sound/SoundBuffer;
        //   245: astore          12
        //   247: aload           12
        //   249: ifnonnull       298
        //   252: aload_0        
        //   253: new             Ljava/lang/StringBuilder;
        //   256: dup            
        //   257: invokespecial   java/lang/StringBuilder.<init>:()V
        //   260: ldc_w           "Source '"
        //   263: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   266: aload           4
        //   268: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   271: ldc_w           "' was not created "
        //   274: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   277: ldc_w           "because audio data was not found for "
        //   280: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   283: aload           5
        //   285: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   288: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   291: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   294: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //   297: return         
        //   298: aload_0        
        //   299: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.sourceMap:Ljava/util/HashMap;
        //   302: aload           4
        //   304: new             Lpaulscode/sound/libraries/SourceLWJGLOpenAL;
        //   307: dup            
        //   308: aload_0        
        //   309: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.listenerPositionAL:Ljava/nio/FloatBuffer;
        //   312: aload           11
        //   314: iload_1        
        //   315: iload_2        
        //   316: iload_3        
        //   317: aload           4
        //   319: aload           5
        //   321: aload           12
        //   323: fload           6
        //   325: fload           7
        //   327: fload           8
        //   329: iload           9
        //   331: fload           10
        //   333: iconst_0       
        //   334: invokespecial   paulscode/sound/libraries/SourceLWJGLOpenAL.<init>:(Ljava/nio/FloatBuffer;Ljava/nio/IntBuffer;ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;Lpaulscode/sound/SoundBuffer;FFFIFZ)V
        //   337: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   340: pop            
        //   341: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0230 (coming from #0181).
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
        this.sourceMap.put(s, new SourceLWJGLOpenAL(this.listenerPositionAL, audioFormat, b, s, n, n2, n3, n4, n5));
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
        //     4: ifne            87
        //     7: aload_0        
        //     8: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.ALBufferMap:Ljava/util/HashMap;
        //    11: aload           5
        //    13: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    16: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    19: checkcast       Ljava/nio/IntBuffer;
        //    22: astore          12
        //    24: aload           12
        //    26: ifnonnull       36
        //    29: aload_0        
        //    30: aload           5
        //    32: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.loadSound:(Lpaulscode/sound/FilenameURL;)Z
        //    35: pop            
        //    36: aload_0        
        //    37: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.ALBufferMap:Ljava/util/HashMap;
        //    40: aload           5
        //    42: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    45: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    48: checkcast       Ljava/nio/IntBuffer;
        //    51: astore          12
        //    53: aload           12
        //    55: ifnonnull       87
        //    58: aload_0        
        //    59: new             Ljava/lang/StringBuilder;
        //    62: dup            
        //    63: invokespecial   java/lang/StringBuilder.<init>:()V
        //    66: ldc_w           "Sound buffer was not created for "
        //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    72: aload           5
        //    74: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //    77: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    80: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    83: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //    86: return         
        //    87: aconst_null    
        //    88: astore          13
        //    90: iload_2        
        //    91: ifne            236
        //    94: aload_0        
        //    95: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.bufferMap:Ljava/util/HashMap;
        //    98: aload           5
        //   100: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   103: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   106: checkcast       Lpaulscode/sound/SoundBuffer;
        //   109: astore          13
        //   111: aload           13
        //   113: ifnonnull       168
        //   116: aload_0        
        //   117: aload           5
        //   119: ifnonnull       168
        //   122: aload_0        
        //   123: new             Ljava/lang/StringBuilder;
        //   126: dup            
        //   127: invokespecial   java/lang/StringBuilder.<init>:()V
        //   130: ldc_w           "Source '"
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: aload           4
        //   138: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   141: ldc_w           "' was not created "
        //   144: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   147: ldc_w           "because an error occurred while loading "
        //   150: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   153: aload           5
        //   155: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   158: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   161: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   164: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //   167: return         
        //   168: aload_0        
        //   169: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.bufferMap:Ljava/util/HashMap;
        //   172: aload           5
        //   174: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   177: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   180: checkcast       Lpaulscode/sound/SoundBuffer;
        //   183: astore          13
        //   185: aload           13
        //   187: ifnonnull       236
        //   190: aload_0        
        //   191: new             Ljava/lang/StringBuilder;
        //   194: dup            
        //   195: invokespecial   java/lang/StringBuilder.<init>:()V
        //   198: ldc_w           "Source '"
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   204: aload           4
        //   206: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   209: ldc_w           "' was not created "
        //   212: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   215: ldc_w           "because audio data was not found for "
        //   218: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   221: aload           5
        //   223: invokevirtual   paulscode/sound/FilenameURL.getFilename:()Ljava/lang/String;
        //   226: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   229: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   232: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.errorMessage:(Ljava/lang/String;)V
        //   235: return         
        //   236: new             Lpaulscode/sound/libraries/SourceLWJGLOpenAL;
        //   239: dup            
        //   240: aload_0        
        //   241: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.listenerPositionAL:Ljava/nio/FloatBuffer;
        //   244: aload           12
        //   246: iload_1        
        //   247: iload_2        
        //   248: iload_3        
        //   249: aload           4
        //   251: aload           5
        //   253: aload           13
        //   255: fload           6
        //   257: fload           7
        //   259: fload           8
        //   261: iload           9
        //   263: fload           10
        //   265: iconst_0       
        //   266: invokespecial   paulscode/sound/libraries/SourceLWJGLOpenAL.<init>:(Ljava/nio/FloatBuffer;Ljava/nio/IntBuffer;ZZZLjava/lang/String;Lpaulscode/sound/FilenameURL;Lpaulscode/sound/SoundBuffer;FFFIFZ)V
        //   269: astore          14
        //   271: aload_0        
        //   272: getfield        paulscode/sound/libraries/LibraryLWJGLOpenAL.sourceMap:Ljava/util/HashMap;
        //   275: aload           4
        //   277: aload           14
        //   279: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   282: pop            
        //   283: aload_0        
        //   284: aload           14
        //   286: invokevirtual   paulscode/sound/libraries/LibraryLWJGLOpenAL.play:(Lpaulscode/sound/Source;)V
        //   289: iload           11
        //   291: ifeq            300
        //   294: aload           14
        //   296: iconst_1       
        //   297: invokevirtual   paulscode/sound/libraries/SourceLWJGLOpenAL.setTemporary:(Z)V
        //   300: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0168 (coming from #0119).
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
        if (this.ALBufferMap == null) {
            this.ALBufferMap = new HashMap();
            this.importantMessage("Open AL Buffer Map was null in method'copySources'");
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
                if (!source.toStream && soundBuffer == null) {
                    continue;
                }
                this.sourceMap.put(s, new SourceLWJGLOpenAL(this.listenerPositionAL, this.ALBufferMap.get(source.filenameURL.getFilename()), source, soundBuffer));
            }
        }
    }
    
    @Override
    public void setListenerPosition(final float n, final float n2, final float n3) {
        super.setListenerPosition(n, n2, n3);
        this.listenerPositionAL.put(0, n);
        this.listenerPositionAL.put(1, n2);
        this.listenerPositionAL.put(2, n3);
        AL10.alListener(4100, this.listenerPositionAL);
        this.checkALError();
    }
    
    @Override
    public void setListenerAngle(final float listenerAngle) {
        super.setListenerAngle(listenerAngle);
        this.listenerOrientation.put(0, this.listener.lookAt.x);
        this.listenerOrientation.put(2, this.listener.lookAt.z);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
    }
    
    @Override
    public void setListenerOrientation(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.setListenerOrientation(n, n2, n3, n4, n5, n6);
        this.listenerOrientation.put(0, n);
        this.listenerOrientation.put(1, n2);
        this.listenerOrientation.put(2, n3);
        this.listenerOrientation.put(3, n4);
        this.listenerOrientation.put(4, n5);
        this.listenerOrientation.put(5, n6);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
    }
    
    @Override
    public void setListenerData(final ListenerData listenerData) {
        super.setListenerData(listenerData);
        this.listenerPositionAL.put(0, listenerData.position.x);
        this.listenerPositionAL.put(1, listenerData.position.y);
        this.listenerPositionAL.put(2, listenerData.position.z);
        AL10.alListener(4100, this.listenerPositionAL);
        this.checkALError();
        this.listenerOrientation.put(0, listenerData.lookAt.x);
        this.listenerOrientation.put(1, listenerData.lookAt.y);
        this.listenerOrientation.put(2, listenerData.lookAt.z);
        this.listenerOrientation.put(3, listenerData.up.x);
        this.listenerOrientation.put(4, listenerData.up.y);
        this.listenerOrientation.put(5, listenerData.up.z);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
        this.listenerVelocity.put(0, listenerData.velocity.x);
        this.listenerVelocity.put(1, listenerData.velocity.y);
        this.listenerVelocity.put(2, listenerData.velocity.z);
        AL10.alListener(4102, this.listenerVelocity);
        this.checkALError();
    }
    
    @Override
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        super.setListenerVelocity(n, n2, n3);
        this.listenerVelocity.put(0, this.listener.velocity.x);
        this.listenerVelocity.put(1, this.listener.velocity.y);
        this.listenerVelocity.put(2, this.listener.velocity.z);
        AL10.alListener(4102, this.listenerVelocity);
    }
    
    @Override
    public void dopplerChanged() {
        super.dopplerChanged();
        AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
        this.checkALError();
        AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
        this.checkALError();
    }
    
    private boolean checkALError() {
        switch (AL10.alGetError()) {
            case 0: {
                return false;
            }
            case 40961: {
                this.errorMessage("Invalid name parameter.");
                return true;
            }
            case 40962: {
                this.errorMessage("Invalid parameter.");
                return true;
            }
            case 40963: {
                this.errorMessage("Invalid enumerated parameter value.");
                return true;
            }
            case 40964: {
                this.errorMessage("Illegal call.");
                return true;
            }
            case 40965: {
                this.errorMessage("Unable to allocate memory.");
                return true;
            }
            default: {
                this.errorMessage("An unrecognized error occurred.");
                return true;
            }
        }
    }
    
    public static boolean alPitchSupported() {
        return alPitchSupported(false, false);
    }
    
    private static synchronized boolean alPitchSupported(final boolean b, final boolean alPitchSupported) {
        if (b) {
            LibraryLWJGLOpenAL.alPitchSupported = alPitchSupported;
        }
        return LibraryLWJGLOpenAL.alPitchSupported;
    }
    
    public static String getTitle() {
        return "LWJGL OpenAL";
    }
    
    public static String getDescription() {
        return "The LWJGL binding of OpenAL.  For more information, see http://www.lwjgl.org";
    }
    
    @Override
    public String getClassName() {
        return "LibraryLWJGLOpenAL";
    }
    
    static {
        LibraryLWJGLOpenAL.alPitchSupported = true;
    }
    
    public static class Exception extends SoundSystemException
    {
        public static final int CREATE = 101;
        public static final int INVALID_NAME = 102;
        public static final int INVALID_ENUM = 103;
        public static final int INVALID_VALUE = 104;
        public static final int INVALID_OPERATION = 105;
        public static final int OUT_OF_MEMORY = 106;
        public static final int LISTENER = 107;
        public static final int NO_AL_PITCH = 108;
        
        public Exception(final String s) {
            super(s);
        }
        
        public Exception(final String s, final int n) {
            super(s, n);
        }
    }
}
