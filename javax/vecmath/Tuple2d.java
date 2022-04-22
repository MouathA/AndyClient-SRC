package javax.vecmath;

import java.io.*;

public abstract class Tuple2d implements Serializable
{
    public double x;
    public double y;
    
    public Tuple2d(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public Tuple2d(final double[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    
    public Tuple2d(final Tuple2d tuple2d) {
        this.x = tuple2d.x;
        this.y = tuple2d.y;
    }
    
    public Tuple2d(final Tuple2f tuple2f) {
        this.x = tuple2f.x;
        this.y = tuple2f.y;
    }
    
    public Tuple2d() {
        this.x = 0.0;
        this.y = 0.0;
    }
    
    public final void set(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public final void set(final double[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    
    public final void set(final Tuple2d tuple2d) {
        this.x = tuple2d.x;
        this.y = tuple2d.y;
    }
    
    public final void set(final Tuple2f tuple2f) {
        this.x = tuple2f.x;
        this.y = tuple2f.y;
    }
    
    public final void get(final double[] array) {
        array[0] = this.x;
        array[1] = this.y;
    }
    
    public final void add(final Tuple2d tuple2d, final Tuple2d tuple2d2) {
        this.x = tuple2d.x + tuple2d2.x;
        this.y = tuple2d.y + tuple2d2.y;
    }
    
    public final void add(final Tuple2d tuple2d) {
        this.x += tuple2d.x;
        this.y += tuple2d.y;
    }
    
    public final void sub(final Tuple2d tuple2d, final Tuple2d tuple2d2) {
        this.x = tuple2d.x - tuple2d2.x;
        this.y = tuple2d.y - tuple2d2.y;
    }
    
    public final void sub(final Tuple2d tuple2d) {
        this.x -= tuple2d.x;
        this.y -= tuple2d.y;
    }
    
    public final void negate(final Tuple2d tuple2d) {
        this.x = -tuple2d.x;
        this.y = -tuple2d.y;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    
    public final void scale(final double n, final Tuple2d tuple2d) {
        this.x = n * tuple2d.x;
        this.y = n * tuple2d.y;
    }
    
    public final void scale(final double n) {
        this.x *= n;
        this.y *= n;
    }
    
    public final void scaleAdd(final double n, final Tuple2d tuple2d, final Tuple2d tuple2d2) {
        this.x = n * tuple2d.x + tuple2d2.x;
        this.y = n * tuple2d.y + tuple2d2.y;
    }
    
    public final void scaleAdd(final double n, final Tuple2d tuple2d) {
        this.x = n * this.x + tuple2d.x;
        this.y = n * this.y + tuple2d.y;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.x);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.y);
        return (int)(doubleToLongBits ^ doubleToLongBits >> 32 ^ doubleToLongBits2 ^ doubleToLongBits2 >> 32);
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
        //     5: instanceof      Ljavax/vecmath/Tuple2d;
        //     8: ifeq            21
        //    11: aload_0        
        //    12: aload_1        
        //    13: checkcast       Ljavax/vecmath/Tuple2d;
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
    
    public boolean epsilonEquals(final Tuple2d tuple2d, final double n) {
        return Math.abs(tuple2d.x - this.x) <= n && Math.abs(tuple2d.y - this.y) <= n;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    
    public final void clamp(final double n, final double n2, final Tuple2d tuple2d) {
        this.set(tuple2d);
        this.clamp(n, n2);
    }
    
    public final void clampMin(final double n, final Tuple2d tuple2d) {
        this.set(tuple2d);
        this.clampMin(n);
    }
    
    public final void clampMax(final double n, final Tuple2d tuple2d) {
        this.set(tuple2d);
        this.clampMax(n);
    }
    
    public final void absolute(final Tuple2d tuple2d) {
        this.set(tuple2d);
        this.absolute();
    }
    
    public final void clamp(final double n, final double n2) {
        this.clampMin(n);
        this.clampMax(n2);
    }
    
    public final void clampMin(final double n) {
        if (this.x < n) {
            this.x = n;
        }
        if (this.y < n) {
            this.y = n;
        }
    }
    
    public final void clampMax(final double n) {
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
    
    public final void interpolate(final Tuple2d tuple2d, final Tuple2d tuple2d2, final double n) {
        this.set(tuple2d);
        this.interpolate(tuple2d2, n);
    }
    
    public final void interpolate(final Tuple2d tuple2d, final double n) {
        final double n2 = 1.0 - n;
        this.x = n2 * this.x + n * tuple2d.x;
        this.y = n2 * this.y + n * tuple2d.y;
    }
}
