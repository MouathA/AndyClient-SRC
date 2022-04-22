package com.google.gson.internal;

import java.util.*;
import com.google.gson.reflect.*;
import com.google.gson.*;
import java.io.*;
import com.google.gson.stream.*;
import java.lang.reflect.*;

public final class Excluder implements TypeAdapterFactory, Cloneable
{
    private static final double IGNORE_VERSIONS = -1.0;
    public static final Excluder DEFAULT;
    private double version;
    private int modifiers;
    private boolean serializeInnerClasses;
    private boolean requireExpose;
    private List serializationStrategies;
    private List deserializationStrategies;
    
    public Excluder() {
        this.version = -1.0;
        this.modifiers = 136;
        this.serializeInnerClasses = true;
        this.serializationStrategies = Collections.emptyList();
        this.deserializationStrategies = Collections.emptyList();
    }
    
    @Override
    protected Excluder clone() {
        return (Excluder)super.clone();
    }
    
    public Excluder withVersion(final double version) {
        final Excluder clone = this.clone();
        clone.version = version;
        return clone;
    }
    
    public Excluder withModifiers(final int... array) {
        final Excluder clone = this.clone();
        clone.modifiers = 0;
        while (0 < array.length) {
            final int n = array[0];
            final Excluder excluder = clone;
            excluder.modifiers |= n;
            int n2 = 0;
            ++n2;
        }
        return clone;
    }
    
    public Excluder disableInnerClassSerialization() {
        final Excluder clone = this.clone();
        clone.serializeInnerClasses = false;
        return clone;
    }
    
    public Excluder excludeFieldsWithoutExposeAnnotation() {
        final Excluder clone = this.clone();
        clone.requireExpose = true;
        return clone;
    }
    
    public Excluder withExclusionStrategy(final ExclusionStrategy exclusionStrategy, final boolean b, final boolean b2) {
        final Excluder clone = this.clone();
        if (b) {
            (clone.serializationStrategies = new ArrayList(this.serializationStrategies)).add(exclusionStrategy);
        }
        if (b2) {
            (clone.deserializationStrategies = new ArrayList(this.deserializationStrategies)).add(exclusionStrategy);
        }
        return clone;
    }
    
    public TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final Class rawType = typeToken.getRawType();
        final boolean excludeClass = this.excludeClass(rawType, true);
        final boolean excludeClass2 = this.excludeClass(rawType, false);
        if (!excludeClass && !excludeClass2) {
            return null;
        }
        return new TypeAdapter(excludeClass2, excludeClass, gson, typeToken) {
            private TypeAdapter delegate;
            final boolean val$skipDeserialize;
            final boolean val$skipSerialize;
            final Gson val$gson;
            final TypeToken val$type;
            final Excluder this$0;
            
            @Override
            public Object read(final JsonReader jsonReader) throws IOException {
                if (this.val$skipDeserialize) {
                    jsonReader.skipValue();
                    return null;
                }
                return this.delegate().read(jsonReader);
            }
            
            @Override
            public void write(final JsonWriter jsonWriter, final Object o) throws IOException {
                if (this.val$skipSerialize) {
                    jsonWriter.nullValue();
                    return;
                }
                this.delegate().write(jsonWriter, o);
            }
            
            private TypeAdapter delegate() {
                final TypeAdapter delegate = this.delegate;
                return (delegate != null) ? delegate : (this.delegate = this.val$gson.getDelegateAdapter(this.this$0, this.val$type));
            }
        };
    }
    
    public boolean excludeField(final Field p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/google/gson/internal/Excluder.modifiers:I
        //     4: aload_1        
        //     5: invokevirtual   java/lang/reflect/Field.getModifiers:()I
        //     8: iand           
        //     9: ifeq            14
        //    12: iconst_1       
        //    13: ireturn        
        //    14: aload_0        
        //    15: getfield        com/google/gson/internal/Excluder.version:D
        //    18: ldc2_w          -1.0
        //    21: dcmpl          
        //    22: ifeq            49
        //    25: aload_0        
        //    26: aload_1        
        //    27: ldc             Lcom/google/gson/annotations/Since;.class
        //    29: invokevirtual   java/lang/reflect/Field.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    32: checkcast       Lcom/google/gson/annotations/Since;
        //    35: aload_1        
        //    36: ldc             Lcom/google/gson/annotations/Until;.class
        //    38: invokevirtual   java/lang/reflect/Field.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    41: checkcast       Lcom/google/gson/annotations/Until;
        //    44: ifeq            49
        //    47: iconst_1       
        //    48: ireturn        
        //    49: aload_1        
        //    50: invokevirtual   java/lang/reflect/Field.isSynthetic:()Z
        //    53: ifeq            58
        //    56: iconst_1       
        //    57: ireturn        
        //    58: aload_0        
        //    59: getfield        com/google/gson/internal/Excluder.requireExpose:Z
        //    62: ifeq            106
        //    65: aload_1        
        //    66: ldc             Lcom/google/gson/annotations/Expose;.class
        //    68: invokevirtual   java/lang/reflect/Field.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    71: checkcast       Lcom/google/gson/annotations/Expose;
        //    74: astore_3       
        //    75: aload_3        
        //    76: ifnull          104
        //    79: iload_2        
        //    80: ifeq            95
        //    83: aload_3        
        //    84: invokeinterface com/google/gson/annotations/Expose.serialize:()Z
        //    89: ifne            106
        //    92: goto            104
        //    95: aload_3        
        //    96: invokeinterface com/google/gson/annotations/Expose.deserialize:()Z
        //   101: ifne            106
        //   104: iconst_1       
        //   105: ireturn        
        //   106: aload_0        
        //   107: getfield        com/google/gson/internal/Excluder.serializeInnerClasses:Z
        //   110: ifne            123
        //   113: aload_0        
        //   114: aload_1        
        //   115: invokevirtual   java/lang/reflect/Field.getType:()Ljava/lang/Class;
        //   118: ifeq            123
        //   121: iconst_1       
        //   122: ireturn        
        //   123: aload_0        
        //   124: aload_1        
        //   125: invokevirtual   java/lang/reflect/Field.getType:()Ljava/lang/Class;
        //   128: ifne            133
        //   131: iconst_1       
        //   132: ireturn        
        //   133: iload_2        
        //   134: ifeq            144
        //   137: aload_0        
        //   138: getfield        com/google/gson/internal/Excluder.serializationStrategies:Ljava/util/List;
        //   141: goto            148
        //   144: aload_0        
        //   145: getfield        com/google/gson/internal/Excluder.deserializationStrategies:Ljava/util/List;
        //   148: astore_3       
        //   149: aload_3        
        //   150: invokeinterface java/util/List.isEmpty:()Z
        //   155: ifne            215
        //   158: new             Lcom/google/gson/FieldAttributes;
        //   161: dup            
        //   162: aload_1        
        //   163: invokespecial   com/google/gson/FieldAttributes.<init>:(Ljava/lang/reflect/Field;)V
        //   166: astore          4
        //   168: aload_3        
        //   169: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   174: astore          5
        //   176: aload           5
        //   178: invokeinterface java/util/Iterator.hasNext:()Z
        //   183: ifeq            215
        //   186: aload           5
        //   188: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   193: checkcast       Lcom/google/gson/ExclusionStrategy;
        //   196: astore          6
        //   198: aload           6
        //   200: aload           4
        //   202: invokeinterface com/google/gson/ExclusionStrategy.shouldSkipField:(Lcom/google/gson/FieldAttributes;)Z
        //   207: ifeq            212
        //   210: iconst_1       
        //   211: ireturn        
        //   212: goto            176
        //   215: iconst_0       
        //   216: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0123 (coming from #0118).
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
    
    public boolean excludeClass(final Class p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/google/gson/internal/Excluder.version:D
        //     4: ldc2_w          -1.0
        //     7: dcmpl          
        //     8: ifeq            35
        //    11: aload_0        
        //    12: aload_1        
        //    13: ldc             Lcom/google/gson/annotations/Since;.class
        //    15: invokevirtual   java/lang/Class.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    18: checkcast       Lcom/google/gson/annotations/Since;
        //    21: aload_1        
        //    22: ldc             Lcom/google/gson/annotations/Until;.class
        //    24: invokevirtual   java/lang/Class.getAnnotation:(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
        //    27: checkcast       Lcom/google/gson/annotations/Until;
        //    30: ifeq            35
        //    33: iconst_1       
        //    34: ireturn        
        //    35: aload_0        
        //    36: getfield        com/google/gson/internal/Excluder.serializeInnerClasses:Z
        //    39: ifne            49
        //    42: aload_0        
        //    43: aload_1        
        //    44: ifeq            49
        //    47: iconst_1       
        //    48: ireturn        
        //    49: aload_0        
        //    50: aload_1        
        //    51: ifne            56
        //    54: iconst_1       
        //    55: ireturn        
        //    56: iload_2        
        //    57: ifeq            67
        //    60: aload_0        
        //    61: getfield        com/google/gson/internal/Excluder.serializationStrategies:Ljava/util/List;
        //    64: goto            71
        //    67: aload_0        
        //    68: getfield        com/google/gson/internal/Excluder.deserializationStrategies:Ljava/util/List;
        //    71: astore_3       
        //    72: aload_3        
        //    73: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    78: astore          4
        //    80: aload           4
        //    82: invokeinterface java/util/Iterator.hasNext:()Z
        //    87: ifeq            118
        //    90: aload           4
        //    92: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    97: checkcast       Lcom/google/gson/ExclusionStrategy;
        //   100: astore          5
        //   102: aload           5
        //   104: aload_1        
        //   105: invokeinterface com/google/gson/ExclusionStrategy.shouldSkipClass:(Ljava/lang/Class;)Z
        //   110: ifeq            115
        //   113: iconst_1       
        //   114: ireturn        
        //   115: goto            80
        //   118: iconst_0       
        //   119: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0044).
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
    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static {
        DEFAULT = new Excluder();
    }
}
