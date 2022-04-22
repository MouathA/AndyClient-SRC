package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class TransformReadField extends Transformer
{
    protected String fieldname;
    protected CtClass fieldClass;
    protected boolean isPrivate;
    protected String methodClassname;
    protected String methodName;
    
    public TransformReadField(final Transformer transformer, final CtField ctField, final String methodClassname, final String methodName) {
        super(transformer);
        this.fieldClass = ctField.getDeclaringClass();
        this.fieldname = ctField.getName();
        this.methodClassname = methodClassname;
        this.methodName = methodName;
        this.isPrivate = Modifier.isPrivate(ctField.getModifiers());
    }
    
    static String isField(final ClassPool p0, final ConstPool p1, final CtClass p2, final String p3, final boolean p4, final int p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload           5
        //     3: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.getFieldrefName:(I)Ljava/lang/String;
        //     6: aload_3        
        //     7: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    10: ifne            15
        //    13: aconst_null    
        //    14: areturn        
        //    15: aload_0        
        //    16: aload_1        
        //    17: iload           5
        //    19: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.getFieldrefClassName:(I)Ljava/lang/String;
        //    22: invokevirtual   com/viaversion/viaversion/libs/javassist/ClassPool.get:(Ljava/lang/String;)Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //    25: astore          6
        //    27: aload           6
        //    29: aload_2        
        //    30: if_acmpeq       45
        //    33: iload           4
        //    35: ifne            52
        //    38: aload           6
        //    40: aload_2        
        //    41: aload_3        
        //    42: ifne            52
        //    45: aload_1        
        //    46: iload           5
        //    48: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.getFieldrefType:(I)Ljava/lang/String;
        //    51: areturn        
        //    52: goto            57
        //    55: astore          6
        //    57: aconst_null    
        //    58: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0045 (coming from #0042).
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
    public int transform(final CtClass ctClass, int n, final CodeIterator codeIterator, final ConstPool constPool) throws BadBytecode {
        final int byte1 = codeIterator.byteAt(n);
        if (byte1 == 180 || byte1 == 178) {
            final String field = isField(ctClass.getClassPool(), constPool, this.fieldClass, this.fieldname, this.isPrivate, codeIterator.u16bitAt(n + 1));
            if (field != null) {
                if (byte1 == 178) {
                    codeIterator.move(n);
                    n = codeIterator.insertGap(1);
                    codeIterator.writeByte(1, n);
                    n = codeIterator.next();
                }
                final int addMethodrefInfo = constPool.addMethodrefInfo(constPool.addClassInfo(this.methodClassname), this.methodName, "(Ljava/lang/Object;)" + field);
                codeIterator.writeByte(184, n);
                codeIterator.write16bit(addMethodrefInfo, n + 1);
                return n;
            }
        }
        return n;
    }
}
