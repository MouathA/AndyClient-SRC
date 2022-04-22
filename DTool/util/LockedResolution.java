package DTool.util;

public final class LockedResolution
{
    private final int width;
    private final int height;
    
    public LockedResolution(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}
