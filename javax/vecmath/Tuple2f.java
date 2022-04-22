package javax.vecmath;

import java.io.*;

public abstract class Tuple2f implements Serializable
{
    public float x;
    public float y;
    
    public Tuple2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public Tuple2f(final float[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    
    public Tuple2f(final Tuple2f tuple2f) {
        this.x = tuple2f.x;
        this.y = tuple2f.y;
    }
    
    public Tuple2f(final Tuple2d tuple2d) {
        this.x = (float)tuple2d.x;
        this.y = (float)tuple2d.y;
    }
    
    public Tuple2f() {
        this.x = 0.0f;
        this.y = 0.0f;
    }
    
    public final void set(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public final void set(final float[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    
    public final void set(final Tuple2f tuple2f) {
        this.x = tuple2f.x;
        this.y = tuple2f.y;
    }
    
    public final void set(final Tuple2d tuple2d) {
        this.x = (float)tuple2d.x;
        this.y = (float)tuple2d.y;
    }
    
    public final void get(final float[] array) {
        array[0] = this.x;
        array[1] = this.y;
    }
    
    public final void add(final Tuple2f tuple2f, final Tuple2f tuple2f2) {
        this.x = tuple2f.x + tuple2f2.x;
        this.y = tuple2f.y + tuple2f2.y;
    }
    
    public final void add(final Tuple2f tuple2f) {
        this.x += tuple2f.x;
        this.y += tuple2f.y;
    }
    
    public final void sub(final Tuple2f tuple2f, final Tuple2f tuple2f2) {
        this.x = tuple2f.x - tuple2f2.x;
        this.y = tuple2f.y - tuple2f2.y;
    }
    
    public final void sub(final Tuple2f tuple2f) {
        this.x -= tuple2f.x;
        this.y -= tuple2f.y;
    }
    
    public final void negate(final Tuple2f tuple2f) {
        this.x = -tuple2f.x;
        this.y = -tuple2f.y;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    
    public final void scale(final float n, final Tuple2f tuple2f) {
        this.x = n * tuple2f.x;
        this.y = n * tuple2f.y;
    }
    
    public final void scale(final float n) {
        this.x *= n;
        this.y *= n;
    }
    
    public final void scaleAdd(final float n, final Tuple2f tuple2f, final Tuple2f tuple2f2) {
        this.x = n * tuple2f.x + tuple2f2.x;
        this.y = n * tuple2f.y + tuple2f2.y;
    }
    
    public final void scaleAdd(final float n, final Tuple2f tuple2f) {
        this.x = n * this.x + tuple2f.x;
        this.y = n * this.y + tuple2f.y;
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.x) ^ Float.floatToIntBits(this.y);
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
        //     5: instanceof      Ljavax/vecmath/Tuple2f;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Tuple2f;
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
    
    public boolean epsilonEquals(final Tuple2f tuple2f, final float n) {
        return Math.abs(tuple2f.x - this.x) <= n && Math.abs(tuple2f.y - this.y) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    
    public final void clamp(final float n, final float n2, final Tuple2f tuple2f) {
        this.set(tuple2f);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final float n, final Tuple2f tuple2f) {
        this.set(tuple2f);
        this.clampMin(n);
    }
    
    public final void clampMax(final float n, final Tuple2f tuple2f) {
        this.set(tuple2f);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple2f tuple2f) {
        this.set(tuple2f);
        this.absolute();
    }
    
    public final void clamp(final float n, final float n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final float n) {
        if (this.x < n) {
            this.x = n;
        }
        if (this.y < n) {
            this.y = n;
        }
    }
    
    public final void clampMax(final float n) {
        if (this.x > n) {
            this.x = n;
        }
        if (this.y > n) {
            this.y = n;
        }
    }
    
    public final void absolute() {
        if (this.x < 0.0) {
            this.x = -this.x;
        }
        if (this.y < 0.0) {
            this.y = -this.y;
        }
    }
    
    public final void interpolate(final Tuple2f tuple2f, final Tuple2f tuple2f2, final float n) {
        this.set(tuple2f);
        this.interpolate(tuple2f2, n);
    }
    
    public final void interpolate(final Tuple2f tuple2f, final float n) {
        final float n2 = 1.0f - n;
        this.x = n2 * this.x + n * tuple2f.x;
        this.y = n2 * this.y + n * tuple2f.y;
    }
}
