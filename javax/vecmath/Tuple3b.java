package javax.vecmath;

import java.io.*;

public abstract class Tuple3b implements Serializable
{
    public byte x;
    public byte y;
    public byte z;
    
    public Tuple3b(final byte x, final byte y, final byte z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Tuple3b(final byte[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public Tuple3b(final Tuple3b tuple3b) {
        this.x = tuple3b.x;
        this.y = tuple3b.y;
        this.z = tuple3b.z;
    }
    
    public Tuple3b() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public final void set(final Tuple3b tuple3b) {
        this.x = tuple3b.x;
        this.y = tuple3b.y;
        this.z = tuple3b.z;
    }
    
    public final void set(final byte[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public final void get(final byte[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
    }
    
    public final void get(final Tuple3b tuple3b) {
        tuple3b.x = this.x;
        tuple3b.y = this.y;
        tuple3b.z = this.z;
    }
    
    @Override
    public int hashCode() {
        return this.x | this.y << 8 | this.z << 16;
    }
    
    @Override
    public boolean equals(final Object p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          21
        //     4: aload_1        
        //     5: instanceof      Ljavax/vecmath/Tuple3b;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Tuple3b;
        //    16: ifnull          21
        //    19: iconst_1       
        //    20: ireturn        
        //    21: iconst_0       
        //    22: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0021 (coming from #0016).
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
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
