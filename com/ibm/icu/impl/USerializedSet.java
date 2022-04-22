package com.ibm.icu.impl;

public final class USerializedSet
{
    private char[] array;
    private int arrayOffset;
    private int bmpLength;
    private int length;
    
    public USerializedSet() {
        this.array = new char[8];
    }
    
    public final boolean getSet(final char[] array, int n) {
        this.array = null;
        final int arrayOffset = 0;
        this.length = arrayOffset;
        this.bmpLength = arrayOffset;
        this.arrayOffset = arrayOffset;
        this.length = array[n++];
        if ((this.length & 0x8000) > 0) {
            this.length &= 0x7FFF;
            if (array.length < n + 1 + this.length) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = array[n++];
        }
        else {
            if (array.length < n + this.length) {
                this.length = 0;
                throw new IndexOutOfBoundsException();
            }
            this.bmpLength = this.length;
        }
        System.arraycopy(array, n, this.array = new char[this.length], 0, this.length);
        return true;
    }
    
    public final void setToOne(int n) {
        if (1114111 < n) {
            return;
        }
        if (n < 65535) {
            final int n2 = 2;
            this.length = n2;
            this.bmpLength = n2;
            this.array[0] = (char)n;
            this.array[1] = (char)(n + 1);
        }
        else if (n == 65535) {
            this.bmpLength = 1;
            this.length = 3;
            this.array[0] = '\uffff';
            this.array[1] = '\u0001';
            this.array[2] = '\0';
        }
        else if (n < 1114111) {
            this.bmpLength = 0;
            this.length = 4;
            this.array[0] = (char)(n >> 16);
            this.array[1] = (char)n;
            ++n;
            this.array[2] = (char)(n >> 16);
            this.array[3] = (char)n;
        }
        else {
            this.bmpLength = 0;
            this.length = 2;
            this.array[0] = '\u0010';
            this.array[1] = '\uffff';
        }
    }
    
    public final boolean getRange(int n, final int[] array) {
        if (n < 0) {
            return false;
        }
        if (this.array == null) {
            this.array = new char[8];
        }
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException();
        }
        n *= 2;
        if (n < this.bmpLength) {
            array[0] = this.array[n++];
            if (n < this.bmpLength) {
                array[1] = this.array[n] - '\u0001';
            }
            else if (n < this.length) {
                array[1] = (this.array[n] << 16 | this.array[n + 1]) - 1;
            }
            else {
                array[1] = 1114111;
            }
            return true;
        }
        n -= this.bmpLength;
        n *= 2;
        final int n2 = this.length - this.bmpLength;
        if (n < n2) {
            final int n3 = this.arrayOffset + this.bmpLength;
            array[0] = (this.array[n3 + n] << 16 | this.array[n3 + n + 1]);
            n += 2;
            if (n < n2) {
                array[1] = (this.array[n3 + n] << 16 | this.array[n3 + n + 1]) - 1;
            }
            else {
                array[1] = 1114111;
            }
            return true;
        }
        return false;
    }
    
    public final boolean contains(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             1114111
        //     3: if_icmple       8
        //     6: iconst_0       
        //     7: ireturn        
        //     8: iload_1        
        //     9: ldc             65535
        //    11: if_icmpgt       49
        //    14: iconst_0       
        //    15: aload_0        
        //    16: getfield        com/ibm/icu/impl/USerializedSet.bmpLength:I
        //    19: if_icmpge       39
        //    22: iload_1        
        //    23: i2c            
        //    24: aload_0        
        //    25: getfield        com/ibm/icu/impl/USerializedSet.array:[C
        //    28: iconst_0       
        //    29: caload         
        //    30: if_icmplt       39
        //    33: iinc            2, 1
        //    36: goto            14
        //    39: iconst_0       
        //    40: ifeq            47
        //    43: iconst_1       
        //    44: goto            48
        //    47: iconst_0       
        //    48: ireturn        
        //    49: iload_1        
        //    50: bipush          16
        //    52: ishr           
        //    53: i2c            
        //    54: istore_3       
        //    55: iload_1        
        //    56: i2c            
        //    57: istore          4
        //    59: aload_0        
        //    60: getfield        com/ibm/icu/impl/USerializedSet.bmpLength:I
        //    63: istore_2       
        //    64: iconst_0       
        //    65: aload_0        
        //    66: getfield        com/ibm/icu/impl/USerializedSet.length:I
        //    69: if_icmpge       109
        //    72: iload_3        
        //    73: aload_0        
        //    74: getfield        com/ibm/icu/impl/USerializedSet.array:[C
        //    77: iconst_0       
        //    78: caload         
        //    79: if_icmpgt       103
        //    82: iload_3        
        //    83: aload_0        
        //    84: getfield        com/ibm/icu/impl/USerializedSet.array:[C
        //    87: iconst_0       
        //    88: caload         
        //    89: if_icmpne       109
        //    92: iload           4
        //    94: aload_0        
        //    95: getfield        com/ibm/icu/impl/USerializedSet.array:[C
        //    98: iconst_1       
        //    99: caload         
        //   100: if_icmplt       109
        //   103: iinc            2, 2
        //   106: goto            64
        //   109: iconst_0       
        //   110: aload_0        
        //   111: getfield        com/ibm/icu/impl/USerializedSet.bmpLength:I
        //   114: iadd           
        //   115: iconst_2       
        //   116: iand           
        //   117: ifeq            124
        //   120: iconst_1       
        //   121: goto            125
        //   124: iconst_0       
        //   125: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
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
    
    public final int countRanges() {
        return (this.bmpLength + (this.length - this.bmpLength) / 2 + 1) / 2;
    }
}
