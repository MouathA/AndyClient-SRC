package org.lwjgl.opengl;

public final class DisplayMode
{
    private final int width;
    private final int height;
    private final int bpp;
    private final int freq;
    private final boolean fullscreen;
    
    public DisplayMode(final int n, final int n2) {
        this(n, n2, 0, 0, false);
    }
    
    DisplayMode(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, true);
    }
    
    private DisplayMode(final int width, final int height, final int bpp, final int freq, final boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.freq = freq;
        this.fullscreen = fullscreen;
    }
    
    public boolean isFullscreenCapable() {
        return this.fullscreen;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getBitsPerPixel() {
        return this.bpp;
    }
    
    public int getFrequency() {
        return this.freq;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof DisplayMode)) {
            return false;
        }
        final DisplayMode displayMode = (DisplayMode)o;
        return displayMode.width == this.width && displayMode.height == this.height && displayMode.bpp == this.bpp && displayMode.freq == this.freq;
    }
    
    @Override
    public int hashCode() {
        return this.width ^ this.height ^ this.freq ^ this.bpp;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        sb.append(this.width);
        sb.append(" x ");
        sb.append(this.height);
        sb.append(" x ");
        sb.append(this.bpp);
        sb.append(" @");
        sb.append(this.freq);
        sb.append("Hz");
        return sb.toString();
    }
}
