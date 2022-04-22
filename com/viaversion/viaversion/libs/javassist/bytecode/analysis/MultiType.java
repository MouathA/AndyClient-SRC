package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.*;
import java.util.*;

public class MultiType extends Type
{
    private Map interfaces;
    private Type resolved;
    private Type potentialClass;
    private MultiType mergeSource;
    private boolean changed;
    
    public MultiType(final Map map) {
        this(map, null);
    }
    
    public MultiType(final Map interfaces, final Type potentialClass) {
        super(null);
        this.changed = false;
        this.interfaces = interfaces;
        this.potentialClass = potentialClass;
    }
    
    @Override
    public CtClass getCtClass() {
        if (this.resolved != null) {
            return this.resolved.getCtClass();
        }
        return Type.OBJECT.getCtClass();
    }
    
    @Override
    public Type getComponent() {
        return null;
    }
    
    @Override
    public int getSize() {
        return 1;
    }
    
    @Override
    public boolean isArray() {
        return false;
    }
    
    @Override
    boolean popChanged() {
        final boolean changed = this.changed;
        this.changed = false;
        return changed;
    }
    
    @Override
    public boolean isAssignableFrom(final Type type) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    public boolean isAssignableTo(final Type type) {
        if (this.resolved != null) {
            return type.isAssignableFrom(this.resolved);
        }
        if (Type.OBJECT.equals(type)) {
            return true;
        }
        if (this.potentialClass != null && !type.isAssignableFrom(this.potentialClass)) {
            this.potentialClass = null;
        }
        final Map mergeMultiAndSingle = this.mergeMultiAndSingle(this, type);
        if (mergeMultiAndSingle.size() == 1 && this.potentialClass == null) {
            this.resolved = Type.get(mergeMultiAndSingle.values().iterator().next());
            this.propogateResolved();
            return true;
        }
        if (mergeMultiAndSingle.size() >= 1) {
            this.interfaces = mergeMultiAndSingle;
            this.propogateState();
            return true;
        }
        if (this.potentialClass != null) {
            this.resolved = this.potentialClass;
            this.propogateResolved();
            return true;
        }
        return false;
    }
    
    private void propogateState() {
        for (MultiType multiType = this.mergeSource; multiType != null; multiType = multiType.mergeSource) {
            multiType.interfaces = this.interfaces;
            multiType.potentialClass = this.potentialClass;
        }
    }
    
    private void propogateResolved() {
        for (MultiType multiType = this.mergeSource; multiType != null; multiType = multiType.mergeSource) {
            multiType.resolved = this.resolved;
        }
    }
    
    @Override
    public boolean isReference() {
        return true;
    }
    
    private Map getAllMultiInterfaces(final MultiType multiType) {
        final HashMap<String, CtClass> hashMap = new HashMap<String, CtClass>();
        for (final CtClass ctClass : multiType.interfaces.values()) {
            hashMap.put(ctClass.getName(), ctClass);
            this.getAllInterfaces(ctClass, hashMap);
        }
        return hashMap;
    }
    
    private Map mergeMultiInterfaces(final MultiType multiType, final MultiType multiType2) {
        return this.findCommonInterfaces(this.getAllMultiInterfaces(multiType), this.getAllMultiInterfaces(multiType2));
    }
    
    private Map mergeMultiAndSingle(final MultiType multiType, final Type type) {
        return this.findCommonInterfaces(this.getAllMultiInterfaces(multiType), this.getAllInterfaces(type.getCtClass(), null));
    }
    
    @Override
    public Type merge(final Type p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: if_acmpne       7
        //     5: aload_0        
        //     6: areturn        
        //     7: aload_1        
        //     8: getstatic       com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.UNINIT:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    11: if_acmpne       16
        //    14: aload_0        
        //    15: areturn        
        //    16: aload_1        
        //    17: getstatic       com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.BOGUS:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    20: if_acmpne       27
        //    23: getstatic       com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.BOGUS:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    26: areturn        
        //    27: aload_1        
        //    28: ifnonnull       33
        //    31: aload_0        
        //    32: areturn        
        //    33: aload_0        
        //    34: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    37: ifnull          49
        //    40: aload_0        
        //    41: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    44: aload_1        
        //    45: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.merge:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    48: areturn        
        //    49: aload_0        
        //    50: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    53: ifnull          107
        //    56: aload_0        
        //    57: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    60: aload_1        
        //    61: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.merge:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    64: astore_2       
        //    65: aload_2        
        //    66: aload_0        
        //    67: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    70: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.equals:(Ljava/lang/Object;)Z
        //    73: ifeq            83
        //    76: aload_2        
        //    77: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.popChanged:()Z
        //    80: ifeq            107
        //    83: aload_0        
        //    84: getstatic       com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.OBJECT:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //    87: aload_2        
        //    88: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.equals:(Ljava/lang/Object;)Z
        //    91: ifeq            98
        //    94: aconst_null    
        //    95: goto            99
        //    98: aload_2        
        //    99: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   102: aload_0        
        //   103: iconst_1       
        //   104: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.changed:Z
        //   107: aload_1        
        //   108: instanceof      Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;
        //   111: ifeq            159
        //   114: aload_1        
        //   115: checkcast       Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;
        //   118: astore_3       
        //   119: aload_3        
        //   120: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   123: ifnull          139
        //   126: aload_0        
        //   127: aload_0        
        //   128: aload_3        
        //   129: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   132: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.mergeMultiAndSingle:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;)Ljava/util/Map;
        //   135: astore_2       
        //   136: goto            156
        //   139: aload_0        
        //   140: aload_3        
        //   141: aload_0        
        //   142: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.mergeMultiInterfaces:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;)Ljava/util/Map;
        //   145: astore_2       
        //   146: aload_0        
        //   147: aload_3        
        //   148: ifnull          156
        //   151: aload_0        
        //   152: aload_3        
        //   153: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.mergeSource:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;
        //   156: goto            166
        //   159: aload_0        
        //   160: aload_0        
        //   161: aload_1        
        //   162: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.mergeMultiAndSingle:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType;Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;)Ljava/util/Map;
        //   165: astore_2       
        //   166: aload_2        
        //   167: invokeinterface java/util/Map.size:()I
        //   172: iconst_1       
        //   173: if_icmpgt       193
        //   176: aload_2        
        //   177: invokeinterface java/util/Map.size:()I
        //   182: iconst_1       
        //   183: if_icmpne       291
        //   186: aload_0        
        //   187: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   190: ifnull          291
        //   193: aload_2        
        //   194: invokeinterface java/util/Map.size:()I
        //   199: aload_0        
        //   200: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.interfaces:Ljava/util/Map;
        //   203: invokeinterface java/util/Map.size:()I
        //   208: if_icmpeq       219
        //   211: aload_0        
        //   212: iconst_1       
        //   213: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.changed:Z
        //   216: goto            280
        //   219: aload_0        
        //   220: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.changed:Z
        //   223: ifne            280
        //   226: aload_2        
        //   227: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //   232: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   237: astore_3       
        //   238: aload_3        
        //   239: invokeinterface java/util/Iterator.hasNext:()Z
        //   244: ifeq            280
        //   247: aload_3        
        //   248: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   253: checkcast       Ljava/lang/String;
        //   256: astore          4
        //   258: aload_0        
        //   259: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.interfaces:Ljava/util/Map;
        //   262: aload           4
        //   264: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   269: ifne            277
        //   272: aload_0        
        //   273: iconst_1       
        //   274: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.changed:Z
        //   277: goto            238
        //   280: aload_0        
        //   281: aload_2        
        //   282: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.interfaces:Ljava/util/Map;
        //   285: aload_0        
        //   286: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.propogateState:()V
        //   289: aload_0        
        //   290: areturn        
        //   291: aload_2        
        //   292: invokeinterface java/util/Map.size:()I
        //   297: iconst_1       
        //   298: if_icmpne       330
        //   301: aload_0        
        //   302: aload_2        
        //   303: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   308: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   313: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   318: checkcast       Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //   321: invokestatic    com/viaversion/viaversion/libs/javassist/bytecode/analysis/Type.get:(Lcom/viaversion/viaversion/libs/javassist/CtClass;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   324: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   327: goto            355
        //   330: aload_0        
        //   331: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   334: ifnull          348
        //   337: aload_0        
        //   338: aload_0        
        //   339: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.potentialClass:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   342: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   345: goto            355
        //   348: aload_0        
        //   349: getstatic       com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.OBJECT:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   352: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   355: aload_0        
        //   356: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.propogateResolved:()V
        //   359: aload_0        
        //   360: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/MultiType.resolved:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Type;
        //   363: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0166 (coming from #0156).
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
    public int hashCode() {
        if (this.resolved != null) {
            return this.resolved.hashCode();
        }
        return this.interfaces.keySet().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MultiType)) {
            return false;
        }
        final MultiType multiType = (MultiType)o;
        if (this.resolved != null) {
            return this.resolved.equals(multiType.resolved);
        }
        return multiType.resolved == null && this.interfaces.keySet().equals(multiType.interfaces.keySet());
    }
    
    @Override
    public String toString() {
        if (this.resolved != null) {
            return this.resolved.toString();
        }
        final StringBuffer sb = new StringBuffer("{");
        final Iterator<String> iterator = this.interfaces.keySet().iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(", ");
        }
        if (this.potentialClass != null) {
            sb.append("*").append(this.potentialClass.toString());
        }
        else {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
