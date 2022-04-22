package javax.vecmath;

import java.io.*;

public abstract class Tuple3i implements Serializable
{
    public int x;
    public int y;
    public int z;
    
    public Tuple3i(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Tuple3i(final int[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public Tuple3i(final Tuple3i tuple3i) {
        this.x = tuple3i.x;
        this.y = tuple3i.y;
        this.z = tuple3i.z;
    }
    
    public Tuple3i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    public final void set(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public final void set(final int[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
    }
    
    public final void set(final Tuple3i tuple3i) {
        this.x = tuple3i.x;
        this.y = tuple3i.y;
        this.z = tuple3i.z;
    }
    
    public final void get(final int[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
    }
    
    public final void get(final Tuple3i tuple3i) {
        tuple3i.x = this.x;
        tuple3i.y = this.y;
        tuple3i.z = this.z;
    }
    
    public final void add(final Tuple3i tuple3i, final Tuple3i tuple3i2) {
        this.x = tuple3i.x + tuple3i2.x;
        this.y = tuple3i.y + tuple3i2.y;
        this.z = tuple3i.z + tuple3i2.z;
    }
    
    public final void add(final Tuple3i tuple3i) {
        this.x += tuple3i.x;
        this.y += tuple3i.y;
        this.z += tuple3i.z;
    }
    
    public final void sub(final Tuple3i tuple3i, final Tuple3i tuple3i2) {
        this.x = tuple3i.x - tuple3i2.x;
        this.y = tuple3i.y - tuple3i2.y;
        this.z = tuple3i.z - tuple3i2.z;
    }
    
    public final void sub(final Tuple3i tuple3i) {
        this.x -= tuple3i.x;
        this.y -= tuple3i.y;
        this.z -= tuple3i.z;
    }
    
    public final void negate(final Tuple3i tuple3i) {
        this.x = -tuple3i.x;
        this.y = -tuple3i.y;
        this.z = -tuple3i.z;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void scale(final int n, final Tuple3i tuple3i) {
        this.x = n * tuple3i.x;
        this.y = n * tuple3i.y;
        this.z = n * tuple3i.z;
    }
    
    public final void scale(final int n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
    }
    
    public final void scaleAdd(final int n, final Tuple3i tuple3i, final Tuple3i tuple3i2) {
        this.x = n * tuple3i.x + tuple3i2.x;
        this.y = n * tuple3i.y + tuple3i2.y;
        this.z = n * tuple3i.z + tuple3i2.z;
    }
    
    public final void scaleAdd(final int n, final Tuple3i tuple3i) {
        this.x = n * this.x + tuple3i.x;
        this.y = n * this.y + tuple3i.y;
        this.z = n * this.z + tuple3i.z;
    }
    
    @Override
    public int hashCode() {
        return this.x ^ this.y ^ this.z;
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
        //     5: instanceof      Ljavax/vecmath/Tuple3i;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Tuple3i;
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
    
    public final void clamp(final int n, final int n2, final Tuple3i tuple3i) {
        this.set(tuple3i);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final int n, final Tuple3i tuple3i) {
        this.set(tuple3i);
        this.clampMin(n);
    }
    
    public final void clampMax(final int n, final Tuple3i tuple3i) {
        this.set(tuple3i);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple3i tuple3i) {
        this.set(tuple3i);
        this.absolute();
    }
    
    public final void clamp(final int n, final int n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final int z) {
        if (this.x < z) {
            this.x = z;
        }
        if (this.y < z) {
            this.y = z;
        }
        if (this.z < z) {
            this.z = z;
        }
    }
    
    public final void clampMax(final int z) {
        if (this.x > z) {
            this.x = z;
        }
        if (this.y > z) {
            this.y = z;
        }
        if (this.z > z) {
            this.z = z;
        }
    }
    
    public final void absolute() {
        if (this.x < 0.0) {
            this.x = -this.x;
        }
        if (this.y < 0.0) {
            this.y = -this.y;
        }
        if (this.z < 0.0) {
            this.z = -this.z;
        }
    }
}
