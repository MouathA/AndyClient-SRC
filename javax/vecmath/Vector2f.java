package javax.vecmath;

import java.io.*;

public class Vector2f extends Tuple2f implements Serializable
{
    public Vector2f(final float n, final float n2) {
        super(n, n2);
    }
    
    public Vector2f(final float[] array) {
        super(array);
    }
    
    public Vector2f(final Vector2f vector2f) {
        super(vector2f);
    }
    
    public Vector2f(final Vector2d vector2d) {
        super(vector2d);
    }
    
    public Vector2f(final Tuple2f tuple2f) {
        super(tuple2f);
    }
    
    public Vector2f(final Tuple2d tuple2d) {
        super(tuple2d);
    }
    
    public Vector2f() {
    }
    
    public final float dot(final Vector2f vector2f) {
        return this.x * vector2f.x + this.y * vector2f.y;
    }
    
    public final float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }
    
    public final void normalize() {
        final double n = this.length();
        this.x /= (float)n;
        this.y /= (float)n;
    }
    
    public final void normalize(final Vector2f vector2f) {
        this.set(vector2f);
        this.normalize();
    }
    
    public final float angle(final Vector2f vector2f) {
        return (float)Math.abs(Math.atan2(this.x * vector2f.y - this.y * vector2f.x, this.dot(vector2f)));
    }
}
