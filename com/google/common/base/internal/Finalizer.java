package com.google.common.base.internal;

import java.util.logging.*;
import java.lang.ref.*;
import java.lang.reflect.*;

public class Finalizer implements Runnable
{
    private static final Logger logger;
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    private final WeakReference finalizableReferenceClassReference;
    private final PhantomReference frqReference;
    private final ReferenceQueue queue;
    private static final Field inheritableThreadLocals;
    
    public static void startFinalizer(final Class clazz, final ReferenceQueue referenceQueue, final PhantomReference phantomReference) {
        if (!clazz.getName().equals("com.google.common.base.FinalizableReference")) {
            throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
        }
        final Thread thread = new Thread(new Finalizer(clazz, referenceQueue, phantomReference));
        thread.setName(Finalizer.class.getName());
        thread.setDaemon(true);
        if (Finalizer.inheritableThreadLocals != null) {
            Finalizer.inheritableThreadLocals.set(thread, null);
        }
        thread.start();
    }
    
    private Finalizer(final Class clazz, final ReferenceQueue queue, final PhantomReference frqReference) {
        this.queue = queue;
        this.finalizableReferenceClassReference = new WeakReference((T)clazz);
        this.frqReference = frqReference;
    }
    
    @Override
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        com/google/common/base/internal/Finalizer.queue:Ljava/lang/ref/ReferenceQueue;
        //     5: invokevirtual   java/lang/ref/ReferenceQueue.remove:()Ljava/lang/ref/Reference;
        //     8: ifnonnull       14
        //    11: goto            21
        //    14: goto            0
        //    17: astore_1       
        //    18: goto            0
        //    21: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0000 (coming from #0014).
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
    
    private Method getFinalizeReferentMethod() {
        final Class clazz = (Class)this.finalizableReferenceClassReference.get();
        if (clazz == null) {
            return null;
        }
        return clazz.getMethod("finalizeReferent", (Class[])new Class[0]);
    }
    
    public static Field getInheritableThreadLocalsField() {
        final Field declaredField = Thread.class.getDeclaredField("inheritableThreadLocals");
        declaredField.setAccessible(true);
        return declaredField;
    }
    
    static {
        logger = Logger.getLogger(Finalizer.class.getName());
        inheritableThreadLocals = getInheritableThreadLocalsField();
    }
}
