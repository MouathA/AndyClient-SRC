package org.lwjgl.opengl;

import java.nio.*;
import org.lwjgl.*;
import java.util.*;

public final class ContextAttribs
{
    public static final int CONTEXT_MAJOR_VERSION_ARB = 8337;
    public static final int CONTEXT_MINOR_VERSION_ARB = 8338;
    public static final int CONTEXT_PROFILE_MASK_ARB = 37158;
    public static final int CONTEXT_CORE_PROFILE_BIT_ARB = 1;
    public static final int CONTEXT_COMPATIBILITY_PROFILE_BIT_ARB = 2;
    public static final int CONTEXT_ES2_PROFILE_BIT_EXT = 4;
    public static final int CONTEXT_FLAGS_ARB = 8340;
    public static final int CONTEXT_DEBUG_BIT_ARB = 1;
    public static final int CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;
    public static final int CONTEXT_ROBUST_ACCESS_BIT_ARB = 4;
    public static final int CONTEXT_RESET_ISOLATION_BIT_ARB = 8;
    public static final int CONTEXT_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int CONTEXT_RELEASE_BEHABIOR_ARB = 8343;
    public static final int CONTEXT_RELEASE_BEHAVIOR_NONE_ARB = 0;
    public static final int CONTEXT_RELEASE_BEHAVIOR_FLUSH_ARB = 8344;
    public static final int CONTEXT_LAYER_PLANE_ARB = 8339;
    private int majorVersion;
    private int minorVersion;
    private int profileMask;
    private int contextFlags;
    private int contextResetNotificationStrategy;
    private int contextReleaseBehavior;
    private int layerPlane;
    
    public ContextAttribs() {
        this(1, 0);
    }
    
    public ContextAttribs(final int n, final int n2) {
        this(n, n2, 0, 0);
    }
    
    public ContextAttribs(final int n, final int n2, final int n3) {
        this(n, n2, 0, n3);
    }
    
    public ContextAttribs(final int majorVersion, final int minorVersion, final int profileMask, final int contextFlags) {
        this.contextResetNotificationStrategy = 33377;
        this.contextReleaseBehavior = 8344;
        if (majorVersion < 0 || 4 < majorVersion || minorVersion < 0 || (majorVersion == 4 && 5 < minorVersion) || (majorVersion == 3 && 3 < minorVersion) || (majorVersion == 2 && 1 < minorVersion) || (majorVersion == 1 && 5 < minorVersion)) {
            throw new IllegalArgumentException("Invalid OpenGL version specified: " + majorVersion + '.' + minorVersion);
        }
        if (LWJGLUtil.CHECKS) {
            if (1 < Integer.bitCount(profileMask) || 4 < profileMask) {
                throw new IllegalArgumentException("Invalid profile mask specified: " + Integer.toBinaryString(profileMask));
            }
            if (15 < contextFlags) {
                throw new IllegalArgumentException("Invalid context flags specified: " + Integer.toBinaryString(profileMask));
            }
        }
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.profileMask = profileMask;
        this.contextFlags = contextFlags;
    }
    
    private ContextAttribs(final ContextAttribs contextAttribs) {
        this.contextResetNotificationStrategy = 33377;
        this.contextReleaseBehavior = 8344;
        this.majorVersion = contextAttribs.majorVersion;
        this.minorVersion = contextAttribs.minorVersion;
        this.profileMask = contextAttribs.profileMask;
        this.contextFlags = contextAttribs.contextFlags;
        this.contextResetNotificationStrategy = contextAttribs.contextResetNotificationStrategy;
        this.contextReleaseBehavior = contextAttribs.contextReleaseBehavior;
        this.layerPlane = contextAttribs.layerPlane;
    }
    
    public int getMajorVersion() {
        return this.majorVersion;
    }
    
    public int getMinorVersion() {
        return this.minorVersion;
    }
    
    public int getProfileMask() {
        return this.profileMask;
    }
    
    public boolean isProfileCore() {
        return this.hasMask(1);
    }
    
    public boolean isProfileCompatibility() {
        return this.hasMask(2);
    }
    
    public boolean isProfileES() {
        return this.hasMask(4);
    }
    
    public int getContextFlags() {
        return this.contextFlags;
    }
    
    public boolean isDebug() {
        return this.hasFlag(1);
    }
    
    public boolean isForwardCompatible() {
        return this.hasFlag(2);
    }
    
    public boolean isRobustAccess() {
        return this.hasFlag(4);
    }
    
    public boolean isContextResetIsolation() {
        return this.hasFlag(8);
    }
    
    public int getContextResetNotificationStrategy() {
        return this.contextResetNotificationStrategy;
    }
    
    @Deprecated
    public boolean isLoseContextOnReset() {
        return this.contextResetNotificationStrategy == 33362;
    }
    
    public int getContextReleaseBehavior() {
        return this.contextReleaseBehavior;
    }
    
    public int getLayerPlane() {
        return this.layerPlane;
    }
    
    private ContextAttribs toggleMask(final int n, final boolean b) {
        if (this == n) {
            return this;
        }
        final ContextAttribs contextAttribs = new ContextAttribs(this);
        contextAttribs.profileMask = (b ? n : 0);
        return contextAttribs;
    }
    
    public ContextAttribs withProfileCore(final boolean b) {
        if (this.majorVersion < 3 || (this.majorVersion == 3 && this.minorVersion < 2)) {
            throw new IllegalArgumentException("Profiles are only supported on OpenGL version 3.2 or higher.");
        }
        return this.toggleMask(1, b);
    }
    
    public ContextAttribs withProfileCompatibility(final boolean b) {
        if (this.majorVersion < 3 || (this.majorVersion == 3 && this.minorVersion < 2)) {
            throw new IllegalArgumentException("Profiles are only supported on OpenGL version 3.2 or higher.");
        }
        return this.toggleMask(2, b);
    }
    
    public ContextAttribs withProfileES(final boolean b) {
        if (this.majorVersion != 2 || this.minorVersion != 0) {
            throw new IllegalArgumentException("The OpenGL ES profile is only supported on OpenGL version 2.0.");
        }
        return this.toggleMask(4, b);
    }
    
    private ContextAttribs toggleFlag(final int n, final boolean b) {
        if (n != 0) {
            return this;
        }
        final ContextAttribs contextAttribs2;
        final ContextAttribs contextAttribs = contextAttribs2 = new ContextAttribs(this);
        contextAttribs2.contextFlags ^= n;
        return contextAttribs;
    }
    
    public ContextAttribs withDebug(final boolean b) {
        return this.toggleFlag(1, b);
    }
    
    public ContextAttribs withForwardCompatible(final boolean b) {
        return this.toggleFlag(2, b);
    }
    
    public ContextAttribs withRobustAccess(final boolean b) {
        return this.toggleFlag(4, b);
    }
    
    public ContextAttribs withContextResetIsolation(final boolean b) {
        return this.toggleFlag(8, b);
    }
    
    public ContextAttribs withResetNotificationStrategy(final int contextResetNotificationStrategy) {
        if (contextResetNotificationStrategy == this.contextResetNotificationStrategy) {
            return this;
        }
        if (LWJGLUtil.CHECKS && contextResetNotificationStrategy != 33377 && contextResetNotificationStrategy != 33362) {
            throw new IllegalArgumentException("Invalid context reset notification strategy specified: 0x" + LWJGLUtil.toHexString(contextResetNotificationStrategy));
        }
        final ContextAttribs contextAttribs = new ContextAttribs(this);
        contextAttribs.contextResetNotificationStrategy = contextResetNotificationStrategy;
        return contextAttribs;
    }
    
    @Deprecated
    public ContextAttribs withLoseContextOnReset(final boolean b) {
        return this.withResetNotificationStrategy(b ? 33362 : 33377);
    }
    
    public ContextAttribs withContextReleaseBehavior(final int contextReleaseBehavior) {
        if (contextReleaseBehavior == this.contextReleaseBehavior) {
            return this;
        }
        if (LWJGLUtil.CHECKS && contextReleaseBehavior != 8344 && contextReleaseBehavior != 0) {
            throw new IllegalArgumentException("Invalid context release behavior specified: 0x" + LWJGLUtil.toHexString(contextReleaseBehavior));
        }
        final ContextAttribs contextAttribs = new ContextAttribs(this);
        contextAttribs.contextReleaseBehavior = contextReleaseBehavior;
        return contextAttribs;
    }
    
    public ContextAttribs withLayer(final int layerPlane) {
        if (LWJGLUtil.getPlatform() != 3) {
            throw new IllegalArgumentException("The CONTEXT_LAYER_PLANE_ARB attribute is supported only on the Windows platform.");
        }
        if (layerPlane == this.layerPlane) {
            return this;
        }
        if (layerPlane < 0) {
            throw new IllegalArgumentException("Invalid layer plane specified: " + layerPlane);
        }
        final ContextAttribs contextAttribs = new ContextAttribs(this);
        contextAttribs.layerPlane = layerPlane;
        return contextAttribs;
    }
    
    IntBuffer getAttribList() {
        if (LWJGLUtil.getPlatform() == 2) {
            return null;
        }
        final LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>(8);
        if (this.majorVersion != 1 || this.minorVersion != 0) {
            linkedHashMap.put(8337, this.majorVersion);
            linkedHashMap.put(8338, this.minorVersion);
        }
        if (this.contextFlags != 0) {
            linkedHashMap.put(8340, this.contextFlags);
        }
        if (this.profileMask != 0) {
            linkedHashMap.put(37158, this.profileMask);
        }
        if (this.contextResetNotificationStrategy != 33377) {
            linkedHashMap.put(33366, this.contextResetNotificationStrategy);
        }
        if (this.contextReleaseBehavior != 8344) {
            linkedHashMap.put(8343, this.contextReleaseBehavior);
        }
        if (this.layerPlane != 0) {
            linkedHashMap.put(8339, this.layerPlane);
        }
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(linkedHashMap.size() * 2 + 1);
        for (final Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            intBuffer.put(entry.getKey()).put(entry.getValue());
        }
        intBuffer.put(0);
        intBuffer.rewind();
        return intBuffer;
    }
    
    @Override
    public String toString() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: bipush          32
        //     6: invokespecial   java/lang/StringBuilder.<init>:(I)V
        //     9: astore_1       
        //    10: aload_1        
        //    11: ldc             "ContextAttribs:"
        //    13: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    16: pop            
        //    17: aload_1        
        //    18: ldc             " Version="
        //    20: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    23: aload_0        
        //    24: getfield        org/lwjgl/opengl/ContextAttribs.majorVersion:I
        //    27: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    30: bipush          46
        //    32: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    35: aload_0        
        //    36: getfield        org/lwjgl/opengl/ContextAttribs.minorVersion:I
        //    39: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    42: pop            
        //    43: aload_0        
        //    44: getfield        org/lwjgl/opengl/ContextAttribs.profileMask:I
        //    47: ifeq            114
        //    50: aload_1        
        //    51: ldc_w           ", Profile="
        //    54: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    57: pop            
        //    58: aload_0        
        //    59: iconst_1       
        //    60: if_icmpne       74
        //    63: aload_1        
        //    64: ldc_w           "CORE"
        //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    70: pop            
        //    71: goto            114
        //    74: aload_0        
        //    75: iconst_2       
        //    76: if_icmpne       90
        //    79: aload_1        
        //    80: ldc_w           "COMPATIBLITY"
        //    83: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    86: pop            
        //    87: goto            114
        //    90: aload_0        
        //    91: iconst_4       
        //    92: if_icmpne       106
        //    95: aload_1        
        //    96: ldc_w           "ES2"
        //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   102: pop            
        //   103: goto            114
        //   106: aload_1        
        //   107: ldc_w           "*unknown*"
        //   110: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   113: pop            
        //   114: aload_0        
        //   115: getfield        org/lwjgl/opengl/ContextAttribs.contextFlags:I
        //   118: ifeq            166
        //   121: aload_0        
        //   122: aload_1        
        //   123: ldc_w           ", DEBUG"
        //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: pop            
        //   130: aload_0        
        //   131: goto            142
        //   134: aload_1        
        //   135: ldc_w           ", FORWARD_COMPATIBLE"
        //   138: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   141: pop            
        //   142: aload_0        
        //   143: goto            154
        //   146: aload_1        
        //   147: ldc_w           ", ROBUST_ACCESS"
        //   150: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   153: pop            
        //   154: aload_0        
        //   155: goto            166
        //   158: aload_1        
        //   159: ldc_w           ", RESET_ISOLATION"
        //   162: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   165: pop            
        //   166: aload_0        
        //   167: getfield        org/lwjgl/opengl/ContextAttribs.contextResetNotificationStrategy:I
        //   170: ldc             33377
        //   172: if_icmpeq       183
        //   175: aload_1        
        //   176: ldc_w           ", LOSE_CONTEXT_ON_RESET"
        //   179: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   182: pop            
        //   183: aload_0        
        //   184: getfield        org/lwjgl/opengl/ContextAttribs.contextReleaseBehavior:I
        //   187: sipush          8344
        //   190: if_icmpeq       201
        //   193: aload_1        
        //   194: ldc_w           ", RELEASE_BEHAVIOR_NONE"
        //   197: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   200: pop            
        //   201: aload_0        
        //   202: getfield        org/lwjgl/opengl/ContextAttribs.layerPlane:I
        //   205: ifeq            223
        //   208: aload_1        
        //   209: ldc_w           ", Layer="
        //   212: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   215: aload_0        
        //   216: getfield        org/lwjgl/opengl/ContextAttribs.layerPlane:I
        //   219: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   222: pop            
        //   223: aload_1        
        //   224: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   227: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0166 (coming from #0155).
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
}
