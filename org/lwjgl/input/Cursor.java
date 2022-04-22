package org.lwjgl.input;

import org.lwjgl.*;
import java.nio.*;

public class Cursor
{
    public static final int CURSOR_ONE_BIT_TRANSPARENCY = 1;
    public static final int CURSOR_8_BIT_ALPHA = 2;
    public static final int CURSOR_ANIMATION = 4;
    private final CursorElement[] cursors;
    private int index;
    private boolean destroyed;
    
    public Cursor(final int n, final int n2, final int n3, int n4, final int n5, final IntBuffer intBuffer, final IntBuffer intBuffer2) throws LWJGLException {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if ((getCapabilities() & 0x1) == 0x0) {
            throw new LWJGLException("Native cursors not supported");
        }
        BufferChecks.checkBufferSize(intBuffer, n * n2 * n5);
        if (intBuffer2 != null) {
            BufferChecks.checkBufferSize(intBuffer2, n5);
        }
        if (!Mouse.isCreated()) {
            throw new IllegalStateException("Mouse must be created before creating cursor objects");
        }
        if (n * n2 * n5 > intBuffer.remaining()) {
            throw new IllegalArgumentException("width*height*numImages > images.remaining()");
        }
        if (n3 >= n || n3 < 0) {
            throw new IllegalArgumentException("xHotspot > width || xHotspot < 0");
        }
        if (n4 >= n2 || n4 < 0) {
            throw new IllegalArgumentException("yHotspot > height || yHotspot < 0");
        }
        n4 = n2 - 1 - n4;
        this.cursors = createCursors(n, n2, n3, n4, n5, intBuffer, intBuffer2);
    }
    // monitorexit(global_lock)
    
    public static int getMinCursorSize() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.isCreated()) {
            throw new IllegalStateException("Mouse must be created.");
        }
        // monitorexit(global_lock)
        return Mouse.getImplementation().getMinCursorSize();
    }
    
    public static int getMaxCursorSize() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (!Mouse.isCreated()) {
            throw new IllegalStateException("Mouse must be created.");
        }
        // monitorexit(global_lock)
        return Mouse.getImplementation().getMaxCursorSize();
    }
    
    public static int getCapabilities() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (Mouse.getImplementation() != null) {
            // monitorexit(global_lock)
            return Mouse.getImplementation().getNativeCursorCapabilities();
        }
        // monitorexit(global_lock)
        return OpenGLPackageAccess.createImplementation().getNativeCursorCapabilities();
    }
    
    private static CursorElement[] createCursors(final int p0, final int p1, final int p2, final int p3, final int p4, final IntBuffer p5, final IntBuffer p6) throws LWJGLException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokevirtual   java/nio/IntBuffer.remaining:()I
        //     5: invokestatic    org/lwjgl/BufferUtils.createIntBuffer:(I)Ljava/nio/IntBuffer;
        //     8: astore          7
        //    10: iload_0        
        //    11: iload_1        
        //    12: iload           4
        //    14: aload           5
        //    16: aload           7
        //    18: invokestatic    org/lwjgl/input/Cursor.flipImages:(IIILjava/nio/IntBuffer;Ljava/nio/IntBuffer;)V
        //    21: invokestatic    org/lwjgl/LWJGLUtil.getPlatform:()I
        //    24: tableswitch {
        //                2: 297
        //                3: 52
        //                4: 148
        //          default: 348
        //        }
        //    52: aload           7
        //    54: invokestatic    org/lwjgl/input/Cursor.convertARGBtoABGR:(Ljava/nio/IntBuffer;)V
        //    57: iload           4
        //    59: anewarray       Lorg/lwjgl/input/Cursor$CursorElement;
        //    62: astore          8
        //    64: iconst_0       
        //    65: iload           4
        //    67: if_icmpge       145
        //    70: invokestatic    org/lwjgl/input/Mouse.getImplementation:()Lorg/lwjgl/opengl/InputImplementation;
        //    73: iload_0        
        //    74: iload_1        
        //    75: iload_2        
        //    76: iload_3        
        //    77: iconst_1       
        //    78: aload           7
        //    80: aconst_null    
        //    81: invokeinterface org/lwjgl/opengl/InputImplementation.createCursor:(IIIIILjava/nio/IntBuffer;Ljava/nio/IntBuffer;)Ljava/lang/Object;
        //    86: astore          10
        //    88: aload           6
        //    90: ifnull          103
        //    93: aload           6
        //    95: iconst_0       
        //    96: invokevirtual   java/nio/IntBuffer.get:(I)I
        //    99: i2l            
        //   100: goto            104
        //   103: lconst_0       
        //   104: lstore          11
        //   106: invokestatic    java/lang/System.currentTimeMillis:()J
        //   109: lstore          13
        //   111: aload           8
        //   113: iconst_0       
        //   114: new             Lorg/lwjgl/input/Cursor$CursorElement;
        //   117: dup            
        //   118: aload           10
        //   120: lload           11
        //   122: lload           13
        //   124: invokespecial   org/lwjgl/input/Cursor$CursorElement.<init>:(Ljava/lang/Object;JJ)V
        //   127: aastore        
        //   128: aload           7
        //   130: iload_0        
        //   131: iload_1        
        //   132: imul           
        //   133: iconst_1       
        //   134: imul           
        //   135: invokevirtual   java/nio/IntBuffer.position:(I)Ljava/nio/Buffer;
        //   138: pop            
        //   139: iinc            9, 1
        //   142: goto            64
        //   145: goto            358
        //   148: iload           4
        //   150: anewarray       Lorg/lwjgl/input/Cursor$CursorElement;
        //   153: astore          8
        //   155: iconst_0       
        //   156: iload           4
        //   158: if_icmpge       294
        //   161: iload_0        
        //   162: iload_1        
        //   163: imul           
        //   164: istore          10
        //   166: iconst_0       
        //   167: iload           10
        //   169: if_icmpge       219
        //   172: iconst_0       
        //   173: iconst_0       
        //   174: iload           10
        //   176: imul           
        //   177: iadd           
        //   178: istore          12
        //   180: aload           7
        //   182: iload           12
        //   184: invokevirtual   java/nio/IntBuffer.get:(I)I
        //   187: bipush          24
        //   189: ishr           
        //   190: sipush          255
        //   193: iand           
        //   194: istore          13
        //   196: iload           13
        //   198: sipush          255
        //   201: if_icmpeq       213
        //   204: aload           7
        //   206: iload           12
        //   208: iconst_0       
        //   209: invokevirtual   java/nio/IntBuffer.put:(II)Ljava/nio/IntBuffer;
        //   212: pop            
        //   213: iinc            11, 1
        //   216: goto            166
        //   219: invokestatic    org/lwjgl/input/Mouse.getImplementation:()Lorg/lwjgl/opengl/InputImplementation;
        //   222: iload_0        
        //   223: iload_1        
        //   224: iload_2        
        //   225: iload_3        
        //   226: iconst_1       
        //   227: aload           7
        //   229: aconst_null    
        //   230: invokeinterface org/lwjgl/opengl/InputImplementation.createCursor:(IIIIILjava/nio/IntBuffer;Ljava/nio/IntBuffer;)Ljava/lang/Object;
        //   235: astore          11
        //   237: aload           6
        //   239: ifnull          252
        //   242: aload           6
        //   244: iconst_0       
        //   245: invokevirtual   java/nio/IntBuffer.get:(I)I
        //   248: i2l            
        //   249: goto            253
        //   252: lconst_0       
        //   253: lstore          12
        //   255: invokestatic    java/lang/System.currentTimeMillis:()J
        //   258: lstore          14
        //   260: aload           8
        //   262: iconst_0       
        //   263: new             Lorg/lwjgl/input/Cursor$CursorElement;
        //   266: dup            
        //   267: aload           11
        //   269: lload           12
        //   271: lload           14
        //   273: invokespecial   org/lwjgl/input/Cursor$CursorElement.<init>:(Ljava/lang/Object;JJ)V
        //   276: aastore        
        //   277: aload           7
        //   279: iload_0        
        //   280: iload_1        
        //   281: imul           
        //   282: iconst_1       
        //   283: imul           
        //   284: invokevirtual   java/nio/IntBuffer.position:(I)Ljava/nio/Buffer;
        //   287: pop            
        //   288: iinc            9, 1
        //   291: goto            155
        //   294: goto            358
        //   297: invokestatic    org/lwjgl/input/Mouse.getImplementation:()Lorg/lwjgl/opengl/InputImplementation;
        //   300: iload_0        
        //   301: iload_1        
        //   302: iload_2        
        //   303: iload_3        
        //   304: iload           4
        //   306: aload           7
        //   308: aload           6
        //   310: invokeinterface org/lwjgl/opengl/InputImplementation.createCursor:(IIIIILjava/nio/IntBuffer;Ljava/nio/IntBuffer;)Ljava/lang/Object;
        //   315: astore          9
        //   317: new             Lorg/lwjgl/input/Cursor$CursorElement;
        //   320: dup            
        //   321: aload           9
        //   323: ldc2_w          -1
        //   326: ldc2_w          -1
        //   329: invokespecial   org/lwjgl/input/Cursor$CursorElement.<init>:(Ljava/lang/Object;JJ)V
        //   332: astore          10
        //   334: iconst_1       
        //   335: anewarray       Lorg/lwjgl/input/Cursor$CursorElement;
        //   338: dup            
        //   339: iconst_0       
        //   340: aload           10
        //   342: aastore        
        //   343: astore          8
        //   345: goto            358
        //   348: new             Ljava/lang/RuntimeException;
        //   351: dup            
        //   352: ldc             "Unknown OS"
        //   354: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   357: athrow         
        //   358: aload           8
        //   360: areturn        
        //    Exceptions:
        //  throws org.lwjgl.LWJGLException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    private static void convertARGBtoABGR(final IntBuffer intBuffer) {
        while (0 < intBuffer.limit()) {
            final int value = intBuffer.get(0);
            intBuffer.put(0, (((byte)(value >>> 24) & 0xFF) << 24) + (((byte)value & 0xFF) << 16) + (((byte)(value >>> 8) & 0xFF) << 8) + ((byte)(value >>> 16) & 0xFF));
            int n = 0;
            ++n;
        }
    }
    
    private static void flipImages(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        while (0 < n3) {
            flipImage(n, n2, 0 * n * n2, intBuffer, intBuffer2);
            int n4 = 0;
            ++n4;
        }
    }
    
    private static void flipImage(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        while (0 < n2 >> 1) {
            final int n4 = 0 * n + n3;
            final int n5 = (n2 - 0 - 1) * n + n3;
            while (0 < n) {
                final int n6 = n4 + 0;
                final int n7 = n5 + 0;
                final int value = intBuffer.get(n6 + intBuffer.position());
                intBuffer2.put(n6, intBuffer.get(n7 + intBuffer.position()));
                intBuffer2.put(n7, value);
                int n8 = 0;
                ++n8;
            }
            int n9 = 0;
            ++n9;
        }
    }
    
    Object getHandle() {
        this.checkValid();
        return this.cursors[this.index].cursorHandle;
    }
    
    private void checkValid() {
        if (this.destroyed) {
            throw new IllegalStateException("The cursor is destroyed");
        }
    }
    
    public void destroy() {
        // monitorenter(global_lock = OpenGLPackageAccess.global_lock)
        if (this.destroyed) {
            // monitorexit(global_lock)
            return;
        }
        if (Mouse.getNativeCursor() == this) {
            Mouse.setNativeCursor(null);
        }
        final CursorElement[] cursors = this.cursors;
        while (0 < cursors.length) {
            Mouse.getImplementation().destroyCursor(cursors[0].cursorHandle);
            int n = 0;
            ++n;
        }
        this.destroyed = true;
    }
    // monitorexit(global_lock)
    
    protected void setTimeout() {
        this.checkValid();
        this.cursors[this.index].timeout = System.currentTimeMillis() + this.cursors[this.index].delay;
    }
    
    protected boolean hasTimedOut() {
        this.checkValid();
        return this.cursors.length > 1 && this.cursors[this.index].timeout < System.currentTimeMillis();
    }
    
    protected void nextCursor() {
        this.checkValid();
        this.index = ++this.index % this.cursors.length;
    }
    
    private static class CursorElement
    {
        final Object cursorHandle;
        final long delay;
        long timeout;
        
        CursorElement(final Object cursorHandle, final long delay, final long timeout) {
            this.cursorHandle = cursorHandle;
            this.delay = delay;
            this.timeout = timeout;
        }
    }
}
