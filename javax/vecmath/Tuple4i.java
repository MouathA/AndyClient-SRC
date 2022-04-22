package javax.vecmath;

import java.io.*;

public abstract class Tuple4i implements Serializable
{
    public int x;
    public int y;
    public int z;
    public int w;
    
    public Tuple4i(final int x, final int y, final int z, final int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Tuple4i(final int[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public Tuple4i(final Tuple4i tuple4i) {
        this.x = tuple4i.x;
        this.y = tuple4i.y;
        this.z = tuple4i.z;
        this.w = tuple4i.w;
    }
    
    public Tuple4i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    
    public final void set(final int x, final int y, final int z, final int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public final void set(final int[] array) {
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }
    
    public final void set(final Tuple4i tuple4i) {
        this.x = tuple4i.x;
        this.y = tuple4i.y;
        this.z = tuple4i.z;
        this.w = tuple4i.w;
    }
    
    public final void get(final int[] array) {
        array[0] = this.x;
        array[1] = this.y;
        array[2] = this.z;
        array[3] = this.w;
    }
    
    public final void get(final Tuple4i tuple4i) {
        tuple4i.x = this.x;
        tuple4i.y = this.y;
        tuple4i.z = this.z;
        tuple4i.w = this.w;
    }
    
    public final void add(final Tuple4i tuple4i, final Tuple4i tuple4i2) {
        this.x = tuple4i.x + tuple4i2.x;
        this.y = tuple4i.y + tuple4i2.y;
        this.z = tuple4i.z + tuple4i2.z;
        this.w = tuple4i.w + tuple4i2.w;
    }
    
    public final void add(final Tuple4i tuple4i) {
        this.x += tuple4i.x;
        this.y += tuple4i.y;
        this.z += tuple4i.z;
        this.w += tuple4i.w;
    }
    
    public final void sub(final Tuple4i tuple4i, final Tuple4i tuple4i2) {
        this.x = tuple4i.x - tuple4i2.x;
        this.y = tuple4i.y - tuple4i2.y;
        this.z = tuple4i.z - tuple4i2.z;
        this.w = tuple4i.w - tuple4i2.w;
    }
    
    public final void sub(final Tuple4i tuple4i) {
        this.x -= tuple4i.x;
        this.y -= tuple4i.y;
        this.z -= tuple4i.z;
        this.w -= tuple4i.w;
    }
    
    public final void negate(final Tuple4i tuple4i) {
        this.x = -tuple4i.x;
        this.y = -tuple4i.y;
        this.z = -tuple4i.z;
        this.w = -tuple4i.w;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }
    
    public final void scale(final int n, final Tuple4i tuple4i) {
        this.x = n * tuple4i.x;
        this.y = n * tuple4i.y;
        this.z = n * tuple4i.z;
        this.w = n * tuple4i.w;
    }
    
    public final void scale(final int n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
    }
    
    public final void scaleAdd(final int n, final Tuple4i tuple4i, final Tuple4i tuple4i2) {
        this.x = n * tuple4i.x + tuple4i2.x;
        this.y = n * tuple4i.y + tuple4i2.y;
        this.z = n * tuple4i.z + tuple4i2.z;
        this.w = n * tuple4i.w + tuple4i2.w;
    }
    
    public final void scaleAdd(final int n, final Tuple4i tuple4i) {
        this.x = n * this.x + tuple4i.x;
        this.y = n * this.y + tuple4i.y;
        this.z = n * this.z + tuple4i.z;
        this.w = n * this.w + tuple4i.w;
    }
    
    @Override
    public int hashCode() {
        return this.x ^ this.y ^ (this.z ^ this.w);
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
        //     5: instanceof      Ljavax/vecmath/Tuple4i;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Tuple4i;
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
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }
    
    public final void clamp(final int n, final int n2, final Tuple4i tuple4i) {
        this.set(tuple4i);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final int n, final Tuple4i tuple4i) {
        this.set(tuple4i);
        this.clampMin(n);
    }
    
    public final void clampMax(final int n, final Tuple4i tuple4i) {
        this.set(tuple4i);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple4i tuple4i) {
        this.set(tuple4i);
        this.absolute();
    }
    
    public final void clamp(final int n, final int n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final int n) {
        if (this.x < n) {
            this.x = n;
        }
        if (this.y < n) {
            this.y = n;
        }
        if (this.z < n) {
            this.z = n;
        }
        if (this.w < n) {
            this.w = n;
        }
    }
    
    public final void clampMax(final int n) {
        if (this.x > n) {
            this.x = n;
        }
        if (this.y > n) {
            this.y = n;
        }
        if (this.z > n) {
            this.z = n;
        }
        if (this.w > n) {
            this.w = n;
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
        if (this.w < 0.0) {
            this.w = -this.w;
        }
    }
}
