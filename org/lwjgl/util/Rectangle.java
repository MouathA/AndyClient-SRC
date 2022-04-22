package org.lwjgl.util;

import java.io.*;

public final class Rectangle implements ReadableRectangle, WritableRectangle, Serializable
{
    static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private int width;
    private int height;
    
    public Rectangle() {
    }
    
    public Rectangle(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Rectangle(final ReadablePoint readablePoint, final ReadableDimension readableDimension) {
        this.x = readablePoint.getX();
        this.y = readablePoint.getY();
        this.width = readableDimension.getWidth();
        this.height = readableDimension.getHeight();
    }
    
    public Rectangle(final ReadableRectangle readableRectangle) {
        this.x = readableRectangle.getX();
        this.y = readableRectangle.getY();
        this.width = readableRectangle.getWidth();
        this.height = readableRectangle.getHeight();
    }
    
    public void setLocation(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setLocation(final ReadablePoint readablePoint) {
        this.x = readablePoint.getX();
        this.y = readablePoint.getY();
    }
    
    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public void setSize(final ReadableDimension readableDimension) {
        this.width = readableDimension.getWidth();
        this.height = readableDimension.getHeight();
    }
    
    public void setBounds(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setBounds(final ReadablePoint readablePoint, final ReadableDimension readableDimension) {
        this.x = readablePoint.getX();
        this.y = readablePoint.getY();
        this.width = readableDimension.getWidth();
        this.height = readableDimension.getHeight();
    }
    
    public void setBounds(final ReadableRectangle readableRectangle) {
        this.x = readableRectangle.getX();
        this.y = readableRectangle.getY();
        this.width = readableRectangle.getWidth();
        this.height = readableRectangle.getHeight();
    }
    
    public void getBounds(final WritableRectangle writableRectangle) {
        writableRectangle.setBounds(this.x, this.y, this.width, this.height);
    }
    
    public void getLocation(final WritablePoint writablePoint) {
        writablePoint.setLocation(this.x, this.y);
    }
    
    public void getSize(final WritableDimension writableDimension) {
        writableDimension.setSize(this.width, this.height);
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
    
    public boolean contains(final ReadablePoint readablePoint) {
        return this.contains(readablePoint.getX(), readablePoint.getY());
    }
    
    public boolean contains(final int n, final int n2) {
        final int width = this.width;
        final int height = this.height;
        if ((width | height) < 0) {
            return false;
        }
        final int x = this.x;
        final int y = this.y;
        if (n < x || n2 < y) {
            return false;
        }
        final int n3 = width + x;
        final int n4 = height + y;
        return (n3 < x || n3 > n) && (n4 < y || n4 > n2);
    }
    
    public boolean contains(final ReadableRectangle readableRectangle) {
        return this.contains(readableRectangle.getX(), readableRectangle.getY(), readableRectangle.getWidth(), readableRectangle.getHeight());
    }
    
    public boolean contains(final int n, final int n2, int n3, int n4) {
        final int width = this.width;
        final int height = this.height;
        if ((width | height | n3 | n4) < 0) {
            return false;
        }
        final int x = this.x;
        final int y = this.y;
        if (n < x || n2 < y) {
            return false;
        }
        final int n5 = width + x;
        n3 += n;
        if (n3 <= n) {
            if (n5 >= x || n3 > n5) {
                return false;
            }
        }
        else if (n5 >= x && n3 > n5) {
            return false;
        }
        final int n6 = height + y;
        n4 += n2;
        if (n4 <= n2) {
            if (n6 >= y || n4 > n6) {
                return false;
            }
        }
        else if (n6 >= y && n4 > n6) {
            return false;
        }
        return true;
    }
    
    public boolean intersects(final ReadableRectangle readableRectangle) {
        final int width = this.width;
        final int height = this.height;
        final int width2 = readableRectangle.getWidth();
        final int height2 = readableRectangle.getHeight();
        if (width2 <= 0 || height2 <= 0 || width <= 0 || height <= 0) {
            return false;
        }
        final int x = this.x;
        final int y = this.y;
        final int x2 = readableRectangle.getX();
        final int y2 = readableRectangle.getY();
        final int n = width2 + x2;
        final int n2 = height2 + y2;
        final int n3 = width + x;
        final int n4 = height + y;
        return (n < x2 || n > x) && (n2 < y2 || n2 > y) && (n3 < x || n3 > x2) && (n4 < y || n4 > y2);
    }
    
    public Rectangle intersection(final ReadableRectangle readableRectangle, Rectangle rectangle) {
        int x = this.x;
        int y = this.y;
        final int x2 = readableRectangle.getX();
        final int y2 = readableRectangle.getY();
        long n = x + (long)this.width;
        long n2 = y + (long)this.height;
        final long n3 = x2 + (long)readableRectangle.getWidth();
        final long n4 = y2 + (long)readableRectangle.getHeight();
        if (x < x2) {
            x = x2;
        }
        if (y < y2) {
            y = y2;
        }
        if (n > n3) {
            n = n3;
        }
        if (n2 > n4) {
            n2 = n4;
        }
        long n5 = n - x;
        long n6 = n2 - y;
        if (n5 < -2147483648L) {
            n5 = -2147483648L;
        }
        if (n6 < -2147483648L) {
            n6 = -2147483648L;
        }
        if (rectangle == null) {
            rectangle = new Rectangle(x, y, (int)n5, (int)n6);
        }
        else {
            rectangle.setBounds(x, y, (int)n5, (int)n6);
        }
        return rectangle;
    }
    
    public WritableRectangle union(final ReadableRectangle readableRectangle, final WritableRectangle writableRectangle) {
        final int min = Math.min(this.x, readableRectangle.getX());
        final int max = Math.max(this.x + this.width, readableRectangle.getX() + readableRectangle.getWidth());
        final int min2 = Math.min(this.y, readableRectangle.getY());
        writableRectangle.setBounds(min, min2, max - min, Math.max(this.y + this.height, readableRectangle.getY() + readableRectangle.getHeight()) - min2);
        return writableRectangle;
    }
    
    public void add(final int n, final int n2) {
        final int min = Math.min(this.x, n);
        final int max = Math.max(this.x + this.width, n);
        final int min2 = Math.min(this.y, n2);
        final int max2 = Math.max(this.y + this.height, n2);
        this.x = min;
        this.y = min2;
        this.width = max - min;
        this.height = max2 - min2;
    }
    
    public void add(final ReadablePoint readablePoint) {
        this.add(readablePoint.getX(), readablePoint.getY());
    }
    
    public void add(final ReadableRectangle readableRectangle) {
        final int min = Math.min(this.x, readableRectangle.getX());
        final int max = Math.max(this.x + this.width, readableRectangle.getX() + readableRectangle.getWidth());
        final int min2 = Math.min(this.y, readableRectangle.getY());
        final int max2 = Math.max(this.y + this.height, readableRectangle.getY() + readableRectangle.getHeight());
        this.x = min;
        this.y = min2;
        this.width = max - min;
        this.height = max2 - min2;
    }
    
    public void grow(final int n, final int n2) {
        this.x -= n;
        this.y -= n2;
        this.width += n * 2;
        this.height += n2 * 2;
    }
    
    public boolean isEmpty() {
        return this.width <= 0 || this.height <= 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Rectangle) {
            final Rectangle rectangle = (Rectangle)o;
            return this.x == rectangle.x && this.y == rectangle.y && this.width == rectangle.width && this.height == rectangle.height;
        }
        return super.equals(o);
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + "]";
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
}
