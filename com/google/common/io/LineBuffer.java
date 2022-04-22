package com.google.common.io;

import java.io.*;

abstract class LineBuffer
{
    private StringBuilder line;
    private boolean sawReturn;
    
    LineBuffer() {
        this.line = new StringBuilder();
    }
    
    protected void add(final char[] p0, final int p1, final int p2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          4
        //     3: aload_0        
        //     4: getfield        com/google/common/io/LineBuffer.sawReturn:Z
        //     7: ifeq            35
        //    10: iload_3        
        //    11: ifle            35
        //    14: aload_0        
        //    15: aload_1        
        //    16: iload           4
        //    18: caload         
        //    19: bipush          10
        //    21: if_icmpne       28
        //    24: iconst_1       
        //    25: goto            29
        //    28: iconst_0       
        //    29: ifeq            35
        //    32: iinc            4, 1
        //    35: iload           4
        //    37: istore          5
        //    39: iload_2        
        //    40: iload_3        
        //    41: iadd           
        //    42: istore          6
        //    44: iload           4
        //    46: iload           6
        //    48: if_icmpge       176
        //    51: aload_1        
        //    52: iload           4
        //    54: caload         
        //    55: lookupswitch {
        //               10: 142
        //               13: 80
        //          default: 170
        //        }
        //    80: aload_0        
        //    81: getfield        com/google/common/io/LineBuffer.line:Ljava/lang/StringBuilder;
        //    84: aload_1        
        //    85: iload           5
        //    87: iload           4
        //    89: iload           5
        //    91: isub           
        //    92: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //    95: pop            
        //    96: aload_0        
        //    97: iconst_1       
        //    98: putfield        com/google/common/io/LineBuffer.sawReturn:Z
        //   101: iload           4
        //   103: iconst_1       
        //   104: iadd           
        //   105: iload           6
        //   107: if_icmpge       133
        //   110: aload_0        
        //   111: aload_1        
        //   112: iload           4
        //   114: iconst_1       
        //   115: iadd           
        //   116: caload         
        //   117: bipush          10
        //   119: if_icmpne       126
        //   122: iconst_1       
        //   123: goto            127
        //   126: iconst_0       
        //   127: ifeq            133
        //   130: iinc            4, 1
        //   133: iload           4
        //   135: iconst_1       
        //   136: iadd           
        //   137: istore          5
        //   139: goto            170
        //   142: aload_0        
        //   143: getfield        com/google/common/io/LineBuffer.line:Ljava/lang/StringBuilder;
        //   146: aload_1        
        //   147: iload           5
        //   149: iload           4
        //   151: iload           5
        //   153: isub           
        //   154: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   157: pop            
        //   158: aload_0        
        //   159: iconst_1       
        //   160: invokespecial   com/google/common/io/LineBuffer.finishLine:(Z)Z
        //   163: pop            
        //   164: iload           4
        //   166: iconst_1       
        //   167: iadd           
        //   168: istore          5
        //   170: iinc            4, 1
        //   173: goto            44
        //   176: aload_0        
        //   177: getfield        com/google/common/io/LineBuffer.line:Ljava/lang/StringBuilder;
        //   180: aload_1        
        //   181: iload           5
        //   183: iload_2        
        //   184: iload_3        
        //   185: iadd           
        //   186: iload           5
        //   188: isub           
        //   189: invokevirtual   java/lang/StringBuilder.append:([CII)Ljava/lang/StringBuilder;
        //   192: pop            
        //   193: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0133 (coming from #0127).
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
    
    protected void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            this.finishLine(false);
        }
    }
    
    protected abstract void handleLine(final String p0, final String p1) throws IOException;
}
