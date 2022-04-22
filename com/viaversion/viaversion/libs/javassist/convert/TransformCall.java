package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class TransformCall extends Transformer
{
    protected String classname;
    protected String methodname;
    protected String methodDescriptor;
    protected String newClassname;
    protected String newMethodname;
    protected boolean newMethodIsPrivate;
    protected int newIndex;
    protected ConstPool constPool;
    
    public TransformCall(final Transformer transformer, final CtMethod ctMethod, final CtMethod ctMethod2) {
        this(transformer, ctMethod.getName(), ctMethod2);
        this.classname = ctMethod.getDeclaringClass().getName();
    }
    
    public TransformCall(final Transformer transformer, final String methodname, final CtMethod ctMethod) {
        super(transformer);
        this.methodname = methodname;
        this.methodDescriptor = ctMethod.getMethodInfo2().getDescriptor();
        final String name = ctMethod.getDeclaringClass().getName();
        this.newClassname = name;
        this.classname = name;
        this.newMethodname = ctMethod.getName();
        this.constPool = null;
        this.newMethodIsPrivate = Modifier.isPrivate(ctMethod.getModifiers());
    }
    
    @Override
    public void initialize(final ConstPool constPool, final CodeAttribute codeAttribute) {
        if (this.constPool != constPool) {
            this.newIndex = 0;
        }
    }
    
    @Override
    public int transform(final CtClass p0, final int p1, final CodeIterator p2, final ConstPool p3) throws BadBytecode {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_2        
        //     2: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/CodeIterator.byteAt:(I)I
        //     5: istore          5
        //     7: iload           5
        //     9: sipush          185
        //    12: if_icmpeq       39
        //    15: iload           5
        //    17: sipush          183
        //    20: if_icmpeq       39
        //    23: iload           5
        //    25: sipush          184
        //    28: if_icmpeq       39
        //    31: iload           5
        //    33: sipush          182
        //    36: if_icmpne       107
        //    39: aload_3        
        //    40: iload_2        
        //    41: iconst_1       
        //    42: iadd           
        //    43: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/CodeIterator.u16bitAt:(I)I
        //    46: istore          6
        //    48: aload           4
        //    50: aload_0        
        //    51: getfield        com/viaversion/viaversion/libs/javassist/convert/TransformCall.methodname:Ljava/lang/String;
        //    54: aload_0        
        //    55: getfield        com/viaversion/viaversion/libs/javassist/convert/TransformCall.methodDescriptor:Ljava/lang/String;
        //    58: iload           6
        //    60: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.eqMember:(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
        //    63: astore          7
        //    65: aload           7
        //    67: ifnull          107
        //    70: aload_0        
        //    71: aload           7
        //    73: aload_1        
        //    74: invokevirtual   com/viaversion/viaversion/libs/javassist/CtClass.getClassPool:()Lcom/viaversion/viaversion/libs/javassist/ClassPool;
        //    77: ifeq            107
        //    80: aload           4
        //    82: iload           6
        //    84: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.getMemberNameAndType:(I)I
        //    87: istore          8
        //    89: aload_0        
        //    90: iload           5
        //    92: iload_2        
        //    93: aload_3        
        //    94: aload           4
        //    96: iload           8
        //    98: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/ConstPool.getNameAndTypeDescriptor:(I)I
        //   101: aload           4
        //   103: invokevirtual   com/viaversion/viaversion/libs/javassist/convert/TransformCall.match:(IILcom/viaversion/viaversion/libs/javassist/bytecode/CodeIterator;ILcom/viaversion/viaversion/libs/javassist/bytecode/ConstPool;)I
        //   106: istore_2       
        //   107: iload_2        
        //   108: ireturn        
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0107 (coming from #0077).
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
    
    protected int match(final int n, final int n2, final CodeIterator codeIterator, final int n3, final ConstPool constPool) throws BadBytecode {
        if (this.newIndex == 0) {
            final int addNameAndTypeInfo = constPool.addNameAndTypeInfo(constPool.addUtf8Info(this.newMethodname), n3);
            final int addClassInfo = constPool.addClassInfo(this.newClassname);
            if (n == 185) {
                this.newIndex = constPool.addInterfaceMethodrefInfo(addClassInfo, addNameAndTypeInfo);
            }
            else {
                if (this.newMethodIsPrivate && n == 182) {
                    codeIterator.writeByte(183, n2);
                }
                this.newIndex = constPool.addMethodrefInfo(addClassInfo, addNameAndTypeInfo);
            }
            this.constPool = constPool;
        }
        codeIterator.write16bit(this.newIndex, n2 + 1);
        return n2;
    }
}
