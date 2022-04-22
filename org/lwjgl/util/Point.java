package org.lwjgl.util;

import java.io.*;

public final class Point implements ReadablePoint, WritablePoint, Serializable
{
    static final long serialVersionUID = 1L;
    private int x;
    private int y;
    
    public Point() {
    }
    
    public Point(final int n, final int n2) {
        this.setLocation(n, n2);
    }
    
    public Point(final ReadablePoint location) {
        this.setLocation(location);
    }
    
    public void setLocation(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setLocation(final ReadablePoint readablePoint) {
        this.x = readablePoint.getX();
        this.y = readablePoint.getY();
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public void translate(final int n, final int n2) {
        this.x += n;
        this.y += n2;
    }
    
    public void translate(final ReadablePoint readablePoint) {
        this.x += readablePoint.getX();
        this.y += readablePoint.getY();
    }
    
    public void untranslate(final ReadablePoint readablePoint) {
        this.x -= readablePoint.getX();
        this.y -= readablePoint.getY();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Point) {
            final Point point = (Point)o;
            return this.x == point.x && this.y == point.y;
        }
        return super.equals(o);
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[x=" + this.x + ",y=" + this.y + "]";
    }
    
    @Override
    public int hashCode() {
        final int n = this.x + this.y;
        return n * (n + 1) / 2 + this.x;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void getLocation(final WritablePoint writablePoint) {
        writablePoint.setLocation(this.x, this.y);
    }
}
