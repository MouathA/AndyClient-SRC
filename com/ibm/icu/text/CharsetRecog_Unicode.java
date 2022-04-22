package com.ibm.icu.text;

abstract class CharsetRecog_Unicode extends CharsetRecognizer
{
    @Override
    abstract String getName();
    
    @Override
    abstract CharsetMatch match(final CharsetDetector p0);
    
    static class CharsetRecog_UTF_32_LE extends CharsetRecog_UTF_32
    {
        @Override
        int getChar(final byte[] array, final int n) {
            return (array[n + 3] & 0xFF) << 24 | (array[n + 2] & 0xFF) << 16 | (array[n + 1] & 0xFF) << 8 | (array[n + 0] & 0xFF);
        }
        
        @Override
        String getName() {
            return "UTF-32LE";
        }
    }
    
    abstract static class CharsetRecog_UTF_32 extends CharsetRecog_Unicode
    {
        abstract int getChar(final byte[] p0, final int p1);
        
        @Override
        abstract String getName();
        
        @Override
        CharsetMatch match(final CharsetDetector p0) {
            // 
            // This method could not be decompiled.
            // 
            // Could not show original bytecode, likely due to the same error.
            // 
            // The error that occurred was:
            // 
            // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'com/ibm/icu/text/CharsetRecog_Unicode$CharsetRecog_UTF_32.match:(Lcom/ibm/icu/text/CharsetDetector;)Lcom/ibm/icu/text/CharsetMatch;'.
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
            //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
            //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
            // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 84.
            //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
            //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
            //     ... 20 more
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
    
    static class CharsetRecog_UTF_32_BE extends CharsetRecog_UTF_32
    {
        @Override
        int getChar(final byte[] array, final int n) {
            return (array[n + 0] & 0xFF) << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF);
        }
        
        @Override
        String getName() {
            return "UTF-32BE";
        }
    }
    
    static class CharsetRecog_UTF_16_LE extends CharsetRecog_Unicode
    {
        @Override
        String getName() {
            return "UTF-16LE";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector charsetDetector) {
            final byte[] fRawInput = charsetDetector.fRawInput;
            if (fRawInput.length < 2 || (fRawInput[0] & 0xFF) != 0xFF || (fRawInput[1] & 0xFF) != 0xFE) {
                return null;
            }
            if (fRawInput.length >= 4 && fRawInput[2] == 0 && fRawInput[3] == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, 100);
        }
    }
    
    static class CharsetRecog_UTF_16_BE extends CharsetRecog_Unicode
    {
        @Override
        String getName() {
            return "UTF-16BE";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector charsetDetector) {
            final byte[] fRawInput = charsetDetector.fRawInput;
            if (fRawInput.length >= 2 && (fRawInput[0] & 0xFF) == 0xFE && (fRawInput[1] & 0xFF) == 0xFF) {
                return new CharsetMatch(charsetDetector, this, 100);
            }
            return null;
        }
    }
}
