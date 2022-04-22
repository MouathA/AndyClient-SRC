package org.lwjgl.util;

import java.io.*;
import java.nio.*;

public final class Color implements ReadableColor, Serializable, WritableColor
{
    static final long serialVersionUID = 1L;
    private byte red;
    private byte green;
    private byte blue;
    private byte alpha;
    
    public Color() {
        this(0, 0, 0, 255);
    }
    
    public Color(final int n, final int n2, final int n3) {
        this(n, n2, n3, 255);
    }
    
    public Color(final byte b, final byte b2, final byte b3) {
        this(b, b2, b3, (byte)(-1));
    }
    
    public Color(final int n, final int n2, final int n3, final int n4) {
        this.set(n, n2, n3, n4);
    }
    
    public Color(final byte b, final byte b2, final byte b3, final byte b4) {
        this.set(b, b2, b3, b4);
    }
    
    public Color(final ReadableColor color) {
        this.setColor(color);
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.red = (byte)n;
        this.green = (byte)n2;
        this.blue = (byte)n3;
        this.alpha = (byte)n4;
    }
    
    public void set(final byte red, final byte green, final byte blue, final byte alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public void set(final int n, final int n2, final int n3) {
        this.set(n, n2, n3, 255);
    }
    
    public void set(final byte b, final byte b2, final byte b3) {
        this.set(b, b2, b3, (byte)(-1));
    }
    
    public int getRed() {
        return this.red & 0xFF;
    }
    
    public int getGreen() {
        return this.green & 0xFF;
    }
    
    public int getBlue() {
        return this.blue & 0xFF;
    }
    
    public int getAlpha() {
        return this.alpha & 0xFF;
    }
    
    public void setRed(final int n) {
        this.red = (byte)n;
    }
    
    public void setGreen(final int n) {
        this.green = (byte)n;
    }
    
    public void setBlue(final int n) {
        this.blue = (byte)n;
    }
    
    public void setAlpha(final int n) {
        this.alpha = (byte)n;
    }
    
    public void setRed(final byte red) {
        this.red = red;
    }
    
    public void setGreen(final byte green) {
        this.green = green;
    }
    
    public void setBlue(final byte blue) {
        this.blue = blue;
    }
    
    public void setAlpha(final byte alpha) {
        this.alpha = alpha;
    }
    
    @Override
    public String toString() {
        return "Color [" + this.getRed() + ", " + this.getGreen() + ", " + this.getBlue() + ", " + this.getAlpha() + "]";
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof ReadableColor && ((ReadableColor)o).getRed() == this.getRed() && ((ReadableColor)o).getGreen() == this.getGreen() && ((ReadableColor)o).getBlue() == this.getBlue() && ((ReadableColor)o).getAlpha() == this.getAlpha();
    }
    
    @Override
    public int hashCode() {
        return this.red << 24 | this.green << 16 | this.blue << 8 | this.alpha;
    }
    
    public byte getAlphaByte() {
        return this.alpha;
    }
    
    public byte getBlueByte() {
        return this.blue;
    }
    
    public byte getGreenByte() {
        return this.green;
    }
    
    public byte getRedByte() {
        return this.red;
    }
    
    public void writeRGBA(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.red);
        byteBuffer.put(this.green);
        byteBuffer.put(this.blue);
        byteBuffer.put(this.alpha);
    }
    
    public void writeRGB(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.red);
        byteBuffer.put(this.green);
        byteBuffer.put(this.blue);
    }
    
    public void writeABGR(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.alpha);
        byteBuffer.put(this.blue);
        byteBuffer.put(this.green);
        byteBuffer.put(this.red);
    }
    
    public void writeARGB(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.alpha);
        byteBuffer.put(this.red);
        byteBuffer.put(this.green);
        byteBuffer.put(this.blue);
    }
    
    public void writeBGR(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.blue);
        byteBuffer.put(this.green);
        byteBuffer.put(this.red);
    }
    
    public void writeBGRA(final ByteBuffer byteBuffer) {
        byteBuffer.put(this.blue);
        byteBuffer.put(this.green);
        byteBuffer.put(this.red);
        byteBuffer.put(this.alpha);
    }
    
    public void readRGBA(final ByteBuffer byteBuffer) {
        this.red = byteBuffer.get();
        this.green = byteBuffer.get();
        this.blue = byteBuffer.get();
        this.alpha = byteBuffer.get();
    }
    
    public void readRGB(final ByteBuffer byteBuffer) {
        this.red = byteBuffer.get();
        this.green = byteBuffer.get();
        this.blue = byteBuffer.get();
    }
    
    public void readARGB(final ByteBuffer byteBuffer) {
        this.alpha = byteBuffer.get();
        this.red = byteBuffer.get();
        this.green = byteBuffer.get();
        this.blue = byteBuffer.get();
    }
    
    public void readBGRA(final ByteBuffer byteBuffer) {
        this.blue = byteBuffer.get();
        this.green = byteBuffer.get();
        this.red = byteBuffer.get();
        this.alpha = byteBuffer.get();
    }
    
    public void readBGR(final ByteBuffer byteBuffer) {
        this.blue = byteBuffer.get();
        this.green = byteBuffer.get();
        this.red = byteBuffer.get();
    }
    
    public void readABGR(final ByteBuffer byteBuffer) {
        this.alpha = byteBuffer.get();
        this.blue = byteBuffer.get();
        this.green = byteBuffer.get();
        this.red = byteBuffer.get();
    }
    
    public void setColor(final ReadableColor readableColor) {
        this.red = readableColor.getRedByte();
        this.green = readableColor.getGreenByte();
        this.blue = readableColor.getBlueByte();
        this.alpha = readableColor.getAlphaByte();
    }
    
    public void fromHSB(final float n, final float n2, final float n3) {
        if (n2 == 0.0f) {
            final byte red = (byte)(n3 * 255.0f + 0.5f);
            this.blue = red;
            this.green = red;
            this.red = red;
        }
        else {
            final float n4 = (n - (float)Math.floor(n)) * 6.0f;
            final float n5 = n4 - (float)Math.floor(n4);
            final float n6 = n3 * (1.0f - n2);
            final float n7 = n3 * (1.0f - n2 * n5);
            final float n8 = n3 * (1.0f - n2 * (1.0f - n5));
            switch ((int)n4) {
                case 0: {
                    this.red = (byte)(n3 * 255.0f + 0.5f);
                    this.green = (byte)(n8 * 255.0f + 0.5f);
                    this.blue = (byte)(n6 * 255.0f + 0.5f);
                    break;
                }
                case 1: {
                    this.red = (byte)(n7 * 255.0f + 0.5f);
                    this.green = (byte)(n3 * 255.0f + 0.5f);
                    this.blue = (byte)(n6 * 255.0f + 0.5f);
                    break;
                }
                case 2: {
                    this.red = (byte)(n6 * 255.0f + 0.5f);
                    this.green = (byte)(n3 * 255.0f + 0.5f);
                    this.blue = (byte)(n8 * 255.0f + 0.5f);
                    break;
                }
                case 3: {
                    this.red = (byte)(n6 * 255.0f + 0.5f);
                    this.green = (byte)(n7 * 255.0f + 0.5f);
                    this.blue = (byte)(n3 * 255.0f + 0.5f);
                    break;
                }
                case 4: {
                    this.red = (byte)(n8 * 255.0f + 0.5f);
                    this.green = (byte)(n6 * 255.0f + 0.5f);
                    this.blue = (byte)(n3 * 255.0f + 0.5f);
                    break;
                }
                case 5: {
                    this.red = (byte)(n3 * 255.0f + 0.5f);
                    this.green = (byte)(n6 * 255.0f + 0.5f);
                    this.blue = (byte)(n7 * 255.0f + 0.5f);
                    break;
                }
            }
        }
    }
    
    public float[] toHSB(float[] array) {
        final int red = this.getRed();
        final int green = this.getGreen();
        final int blue = this.getBlue();
        if (array == null) {
            array = new float[3];
        }
        int n = (red <= green) ? green : red;
        if (blue > n) {
            n = blue;
        }
        int n2 = (red >= green) ? green : red;
        if (blue < n2) {
            n2 = blue;
        }
        final float n3 = n / 255.0f;
        float n4;
        if (n != 0) {
            n4 = (n - n2) / (float)n;
        }
        else {
            n4 = 0.0f;
        }
        float n5;
        if (n4 == 0.0f) {
            n5 = 0.0f;
        }
        else {
            final float n6 = (n - red) / (float)(n - n2);
            final float n7 = (n - green) / (float)(n - n2);
            final float n8 = (n - blue) / (float)(n - n2);
            float n9;
            if (red == n) {
                n9 = n8 - n7;
            }
            else if (green == n) {
                n9 = 2.0f + n6 - n8;
            }
            else {
                n9 = 4.0f + n7 - n6;
            }
            n5 = n9 / 6.0f;
            if (n5 < 0.0f) {
                ++n5;
            }
        }
        array[0] = n5;
        array[1] = n4;
        array[2] = n3;
        return array;
    }
}
