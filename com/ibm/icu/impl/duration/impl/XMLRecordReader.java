package com.ibm.icu.impl.duration.impl;

import java.io.*;
import java.util.*;
import com.ibm.icu.lang.*;

public class XMLRecordReader implements RecordReader
{
    private Reader r;
    private List nameStack;
    private boolean atTag;
    private String tag;
    
    public XMLRecordReader(final Reader r) {
        this.r = r;
        this.nameStack = new ArrayList();
        if (this.getTag().startsWith("?xml")) {
            this.advance();
        }
        if (this.getTag().startsWith("!--")) {
            this.advance();
        }
    }
    
    public boolean open(final String s) {
        if (this.getTag().equals(s)) {
            this.nameStack.add(s);
            this.advance();
            return true;
        }
        return false;
    }
    
    public boolean close() {
        final int n = this.nameStack.size() - 1;
        if (this.getTag().equals("/" + (String)this.nameStack.get(n))) {
            this.nameStack.remove(n);
            this.advance();
            return true;
        }
        return false;
    }
    
    public boolean bool(final String s) {
        final String string = this.string(s);
        return string != null && "true".equals(string);
    }
    
    public boolean[] boolArray(final String s) {
        final String[] stringArray = this.stringArray(s);
        if (stringArray != null) {
            final boolean[] array = new boolean[stringArray.length];
            while (0 < stringArray.length) {
                array[0] = "true".equals(stringArray[0]);
                int n = 0;
                ++n;
            }
            return array;
        }
        return null;
    }
    
    public char character(final String s) {
        final String string = this.string(s);
        if (string != null) {
            return string.charAt(0);
        }
        return '\uffff';
    }
    
    public char[] characterArray(final String s) {
        final String[] stringArray = this.stringArray(s);
        if (stringArray != null) {
            final char[] array = new char[stringArray.length];
            while (0 < stringArray.length) {
                array[0] = stringArray[0].charAt(0);
                int n = 0;
                ++n;
            }
            return array;
        }
        return null;
    }
    
    public byte namedIndex(final String s, final String[] array) {
        final String string = this.string(s);
        if (string != null) {
            while (0 < array.length) {
                if (string.equals(array[0])) {
                    return 0;
                }
                int n = 0;
                ++n;
            }
        }
        return -1;
    }
    
    public byte[] namedIndexArray(final String s, final String[] array) {
        final String[] stringArray = this.stringArray(s);
        if (stringArray != null) {
            final byte[] array2 = new byte[stringArray.length];
        Label_0016:
            while (0 < stringArray.length) {
                final String s2 = stringArray[0];
                while (true) {
                    while (0 < array.length) {
                        if (array[0].equals(s2)) {
                            array2[0] = 0;
                            int n = 0;
                            ++n;
                            continue Label_0016;
                        }
                        int n2 = 0;
                        ++n2;
                    }
                    array2[0] = -1;
                    continue;
                }
            }
            return array2;
        }
        return null;
    }
    
    public String string(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: ifeq            35
        //     5: aload_0        
        //     6: invokespecial   com/ibm/icu/impl/duration/impl/XMLRecordReader.readData:()Ljava/lang/String;
        //     9: astore_2       
        //    10: aload_0        
        //    11: new             Ljava/lang/StringBuilder;
        //    14: dup            
        //    15: invokespecial   java/lang/StringBuilder.<init>:()V
        //    18: ldc             "/"
        //    20: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    23: aload_1        
        //    24: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    27: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    30: ifeq            35
        //    33: aload_2        
        //    34: areturn        
        //    35: aconst_null    
        //    36: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0035 (coming from #0030).
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
    
    public String[] stringArray(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: new             Ljava/lang/StringBuilder;
        //     4: dup            
        //     5: invokespecial   java/lang/StringBuilder.<init>:()V
        //     8: aload_1        
        //     9: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    12: ldc             "List"
        //    14: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    17: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    20: ifeq            111
        //    23: new             Ljava/util/ArrayList;
        //    26: dup            
        //    27: invokespecial   java/util/ArrayList.<init>:()V
        //    30: astore_2       
        //    31: aconst_null    
        //    32: aload_0        
        //    33: aload_1        
        //    34: invokevirtual   com/ibm/icu/impl/duration/impl/XMLRecordReader.string:(Ljava/lang/String;)Ljava/lang/String;
        //    37: dup            
        //    38: astore_3       
        //    39: if_acmpeq       64
        //    42: ldc             "Null"
        //    44: aload_3        
        //    45: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    48: ifeq            53
        //    51: aconst_null    
        //    52: astore_3       
        //    53: aload_2        
        //    54: aload_3        
        //    55: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    60: pop            
        //    61: goto            31
        //    64: aload_0        
        //    65: new             Ljava/lang/StringBuilder;
        //    68: dup            
        //    69: invokespecial   java/lang/StringBuilder.<init>:()V
        //    72: ldc             "/"
        //    74: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    77: aload_1        
        //    78: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    81: ldc             "List"
        //    83: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    86: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    89: ifeq            111
        //    92: aload_2        
        //    93: aload_2        
        //    94: invokeinterface java/util/List.size:()I
        //    99: anewarray       Ljava/lang/String;
        //   102: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   107: checkcast       [Ljava/lang/String;
        //   110: areturn        
        //   111: aconst_null    
        //   112: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0111 (coming from #0089).
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
    
    public String[][] stringTable(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: new             Ljava/lang/StringBuilder;
        //     4: dup            
        //     5: invokespecial   java/lang/StringBuilder.<init>:()V
        //     8: aload_1        
        //     9: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    12: ldc             "Table"
        //    14: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    17: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    20: ifeq            100
        //    23: new             Ljava/util/ArrayList;
        //    26: dup            
        //    27: invokespecial   java/util/ArrayList.<init>:()V
        //    30: astore_2       
        //    31: aconst_null    
        //    32: aload_0        
        //    33: aload_1        
        //    34: invokevirtual   com/ibm/icu/impl/duration/impl/XMLRecordReader.stringArray:(Ljava/lang/String;)[Ljava/lang/String;
        //    37: dup            
        //    38: astore_3       
        //    39: if_acmpeq       53
        //    42: aload_2        
        //    43: aload_3        
        //    44: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    49: pop            
        //    50: goto            31
        //    53: aload_0        
        //    54: new             Ljava/lang/StringBuilder;
        //    57: dup            
        //    58: invokespecial   java/lang/StringBuilder.<init>:()V
        //    61: ldc             "/"
        //    63: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    66: aload_1        
        //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    70: ldc             "Table"
        //    72: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    75: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    78: ifeq            100
        //    81: aload_2        
        //    82: aload_2        
        //    83: invokeinterface java/util/List.size:()I
        //    88: anewarray       [Ljava/lang/String;
        //    91: invokeinterface java/util/List.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //    96: checkcast       [[Ljava/lang/String;
        //    99: areturn        
        //   100: aconst_null    
        //   101: checkcast       [[Ljava/lang/String;
        //   104: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0100 (coming from #0078).
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
    
    private String getTag() {
        if (this.tag == null) {
            this.tag = this.readNextTag();
        }
        return this.tag;
    }
    
    private void advance() {
        this.tag = null;
    }
    
    private String readData() {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            this.readChar();
            if (UCharacter.isWhitespace(32)) {}
            sb.append((char)32);
        }
    }
    
    private String readNextTag() {
        while (!this.atTag) {
            this.readChar();
            if (!UCharacter.isWhitespace(0)) {
                System.err.println("Unexpected non-whitespace character " + Integer.toHexString(0));
                break;
            }
        }
        if (!this.atTag) {
            return null;
        }
        this.atTag = false;
        final StringBuilder sb = new StringBuilder();
        while (true) {
            this.readChar();
            sb.append((char)0);
        }
    }
    
    int readChar() {
        return this.r.read();
    }
}
