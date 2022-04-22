package javax.vecmath;

import java.io.*;

public class Vector2d extends Tuple2d implements Serializable
{
    public Vector2d(final double n, final double n2) {
        super(n, n2);
    }
    
    public Vector2d(final double[] array) {
        super(array);
    }
    
    public Vector2d(final Vector2d vector2d) {
        super(vector2d);
    }
    
    public Vector2d(final Vector2f vector2f) {
        super(vector2f);
    }
    
    public Vector2d(final Tuple2d tuple2d) {
        super(tuple2d);
    }
    
    public Vector2d(final Tuple2f tuple2f) {
        super(tuple2f);
    }
    
    public Vector2d() {
    }
    
    public final double dot(final Vector2d vector2d) {
        return this.x * vector2d.x + this.y * vector2d.y;
    }
    
    public final double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }
    
    public final void normalize() {
        final double length = this.length();
        this.x /= length;
        this.y /= length;
    }
    
    public final void normalize(final Vector2d vector2d) {
        this.set(vector2d);
        this.normalize();
    }
    
    public final double angle(final Vector2d vector2d) {
        return Math.abs(Math.atan2(this.x * vector2d.y - this.y * vector2d.x, this.dot(vector2d)));
    }
}
