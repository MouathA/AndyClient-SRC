package org.yaml.snakeyaml.error;

import java.io.*;
import org.yaml.snakeyaml.scanner.*;

public final class Mark implements Serializable
{
    private String name;
    private int index;
    private int line;
    private int column;
    private int[] buffer;
    private int pointer;
    
    private static int[] toCodePoints(final char[] array) {
        final int[] array2 = new int[Character.codePointCount(array, 0, array.length)];
        while (0 < array.length) {
            final int codePoint = Character.codePointAt(array, 0);
            array2[0] = codePoint;
            final int n = 0 + Character.charCount(codePoint);
            int n2 = 0;
            ++n2;
        }
        return array2;
    }
    
    public Mark(final String s, final int n, final int n2, final int n3, final char[] array, final int n4) {
        this(s, n, n2, n3, toCodePoints(array), n4);
    }
    
    @Deprecated
    public Mark(final String s, final int n, final int n2, final int n3, final String s2, final int n4) {
        this(s, n, n2, n3, s2.toCharArray(), n4);
    }
    
    public Mark(final String name, final int index, final int line, final int column, final int[] buffer, final int pointer) {
        this.name = name;
        this.index = index;
        this.line = line;
        this.column = column;
        this.buffer = buffer;
        this.pointer = pointer;
    }
    
    private boolean isLineBreak(final int n) {
        return Constant.NULL_OR_LINEBR.has(n);
    }
    
    public String get_snippet(final int p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: i2f            
        //     2: fconst_2       
        //     3: fdiv           
        //     4: fconst_1       
        //     5: fsub           
        //     6: fstore_3       
        //     7: aload_0        
        //     8: getfield        org/yaml/snakeyaml/error/Mark.pointer:I
        //    11: istore          4
        //    13: ldc             ""
        //    15: astore          5
        //    17: iload           4
        //    19: ifle            61
        //    22: aload_0        
        //    23: aload_0        
        //    24: getfield        org/yaml/snakeyaml/error/Mark.buffer:[I
        //    27: iload           4
        //    29: iconst_1       
        //    30: isub           
        //    31: iaload         
        //    32: invokespecial   org/yaml/snakeyaml/error/Mark.isLineBreak:(I)Z
        //    35: ifne            61
        //    38: iinc            4, -1
        //    41: aload_0        
        //    42: getfield        org/yaml/snakeyaml/error/Mark.pointer:I
        //    45: iload           4
        //    47: isub           
        //    48: i2f            
        //    49: fload_3        
        //    50: fcmpl          
        //    51: ifle            17
        //    54: ldc             " ... "
        //    56: astore          5
        //    58: iinc            4, 5
        //    61: ldc             ""
        //    63: astore          6
        //    65: aload_0        
        //    66: getfield        org/yaml/snakeyaml/error/Mark.pointer:I
        //    69: istore          7
        //    71: iload           7
        //    73: aload_0        
        //    74: getfield        org/yaml/snakeyaml/error/Mark.buffer:[I
        //    77: arraylength    
        //    78: if_icmpge       118
        //    81: aload_0        
        //    82: aload_0        
        //    83: getfield        org/yaml/snakeyaml/error/Mark.buffer:[I
        //    86: iload           7
        //    88: iaload         
        //    89: invokespecial   org/yaml/snakeyaml/error/Mark.isLineBreak:(I)Z
        //    92: ifne            118
        //    95: iinc            7, 1
        //    98: iload           7
        //   100: aload_0        
        //   101: getfield        org/yaml/snakeyaml/error/Mark.pointer:I
        //   104: isub           
        //   105: i2f            
        //   106: fload_3        
        //   107: fcmpl          
        //   108: ifle            71
        //   111: ldc             " ... "
        //   113: astore          6
        //   115: iinc            7, -5
        //   118: new             Ljava/lang/StringBuilder;
        //   121: dup            
        //   122: invokespecial   java/lang/StringBuilder.<init>:()V
        //   125: astore          8
        //   127: iconst_0       
        //   128: iload_1        
        //   129: if_icmpge       146
        //   132: aload           8
        //   134: ldc             " "
        //   136: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   139: pop            
        //   140: iinc            9, 1
        //   143: goto            127
        //   146: aload           8
        //   148: aload           5
        //   150: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   153: pop            
        //   154: iload           4
        //   156: istore          9
        //   158: iconst_0       
        //   159: iload           7
        //   161: if_icmpge       182
        //   164: aload           8
        //   166: aload_0        
        //   167: getfield        org/yaml/snakeyaml/error/Mark.buffer:[I
        //   170: iconst_0       
        //   171: iaload         
        //   172: invokevirtual   java/lang/StringBuilder.appendCodePoint:(I)Ljava/lang/StringBuilder;
        //   175: pop            
        //   176: iinc            9, 1
        //   179: goto            158
        //   182: aload           8
        //   184: aload           6
        //   186: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: pop            
        //   190: aload           8
        //   192: ldc             "\n"
        //   194: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   197: pop            
        //   198: iconst_0       
        //   199: iload_1        
        //   200: aload_0        
        //   201: getfield        org/yaml/snakeyaml/error/Mark.pointer:I
        //   204: iadd           
        //   205: iload           4
        //   207: isub           
        //   208: aload           5
        //   210: invokevirtual   java/lang/String.length:()I
        //   213: iadd           
        //   214: if_icmpge       231
        //   217: aload           8
        //   219: ldc             " "
        //   221: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   224: pop            
        //   225: iinc            9, 1
        //   228: goto            198
        //   231: aload           8
        //   233: ldc             "^"
        //   235: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   238: pop            
        //   239: aload           8
        //   241: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   244: areturn        
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
    
    public String get_snippet() {
        return this.get_snippet(4, 75);
    }
    
    @Override
    public String toString() {
        final String get_snippet = this.get_snippet();
        final StringBuilder sb = new StringBuilder(" in ");
        sb.append(this.name);
        sb.append(", line ");
        sb.append(this.line + 1);
        sb.append(", column ");
        sb.append(this.column + 1);
        sb.append(":\n");
        sb.append(get_snippet);
        return sb.toString();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int[] getBuffer() {
        return this.buffer;
    }
    
    public int getPointer() {
        return this.pointer;
    }
}
