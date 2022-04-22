package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.compressors.z._internal_.*;
import java.io.*;

class UnshrinkingInputStream extends InternalLZWInputStream
{
    private static final int MAX_CODE_SIZE = 13;
    private static final int MAX_TABLE_SIZE = 8192;
    private final boolean[] isUsed;
    
    public UnshrinkingInputStream(final InputStream inputStream) throws IOException {
        super(inputStream);
        this.setClearCode(this.codeSize);
        this.initializeTables(13);
        this.isUsed = new boolean[this.prefixes.length];
        while (0 < 256) {
            this.isUsed[0] = true;
            int n = 0;
            ++n;
        }
        this.tableSize = this.clearCode + 1;
    }
    
    @Override
    protected int addEntry(final int n, final byte b) throws IOException {
        while (this.tableSize < 8192 && this.isUsed[this.tableSize]) {
            ++this.tableSize;
        }
        final int addEntry = this.addEntry(n, b, 8192);
        if (addEntry >= 0) {
            this.isUsed[addEntry] = true;
        }
        return addEntry;
    }
    
    private void partialClear() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: newarray        Z
        //     5: astore_1       
        //     6: iconst_0       
        //     7: aload_0        
        //     8: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.isUsed:[Z
        //    11: arraylength    
        //    12: if_icmpge       49
        //    15: aload_0        
        //    16: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.isUsed:[Z
        //    19: iconst_0       
        //    20: baload         
        //    21: ifeq            43
        //    24: aload_0        
        //    25: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.prefixes:[I
        //    28: iconst_0       
        //    29: iaload         
        //    30: iconst_m1      
        //    31: if_icmpeq       43
        //    34: aload_1        
        //    35: aload_0        
        //    36: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.prefixes:[I
        //    39: iconst_0       
        //    40: iaload         
        //    41: iconst_1       
        //    42: bastore        
        //    43: iinc            2, 1
        //    46: goto            6
        //    49: aload_0        
        //    50: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.clearCode:I
        //    53: iconst_1       
        //    54: iadd           
        //    55: istore_2       
        //    56: iconst_0       
        //    57: aload_1        
        //    58: arraylength    
        //    59: if_icmpge       88
        //    62: aload_1        
        //    63: iconst_0       
        //    64: baload         
        //    65: ifne            82
        //    68: aload_0        
        //    69: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.isUsed:[Z
        //    72: iconst_0       
        //    73: iconst_0       
        //    74: bastore        
        //    75: aload_0        
        //    76: getfield        org/apache/commons/compress/archivers/zip/UnshrinkingInputStream.prefixes:[I
        //    79: iconst_0       
        //    80: iconst_m1      
        //    81: iastore        
        //    82: iinc            2, 1
        //    85: goto            56
        //    88: return         
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
    
    @Override
    protected int decompressNextSymbol() throws IOException {
        final int nextCode = this.readNextCode();
        if (nextCode < 0) {
            return -1;
        }
        if (nextCode != this.clearCode) {
            int addRepeatOfPreviousCode = nextCode;
            if (!this.isUsed[nextCode]) {
                addRepeatOfPreviousCode = this.addRepeatOfPreviousCode();
            }
            return this.expandCodeToOutputStack(addRepeatOfPreviousCode, true);
        }
        this.readNextCode();
        if (1 < 0) {
            throw new IOException("Unexpected EOF;");
        }
        if (true == true) {
            if (this.codeSize >= 13) {
                throw new IOException("Attempt to increase code size beyond maximum");
            }
            ++this.codeSize;
        }
        else {
            if (1 != 2) {
                throw new IOException("Invalid clear code subcode " + 1);
            }
            this.partialClear();
            this.tableSize = this.clearCode + 1;
        }
        return 0;
    }
}
