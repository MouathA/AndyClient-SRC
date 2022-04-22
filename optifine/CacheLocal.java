package optifine;

public class CacheLocal
{
    private int maxX;
    private int maxY;
    private int maxZ;
    private int offsetX;
    private int offsetY;
    private int offsetZ;
    private int[][][] cache;
    private int[] lastZs;
    private int lastDz;
    
    public CacheLocal(final int maxX, final int maxY, final int maxZ) {
        this.maxX = 18;
        this.maxY = 128;
        this.maxZ = 18;
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
        this.cache = null;
        this.lastZs = null;
        this.lastDz = 0;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.cache = new int[maxX][maxY][maxZ];
        this.resetCache();
    }
    
    public void resetCache() {
        while (0 < this.maxX) {
            final int[][] array = this.cache[0];
            while (0 < this.maxY) {
                final int[] array2 = array[0];
                while (0 < this.maxZ) {
                    array2[0] = -1;
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public void setOffset(final int offsetX, final int offsetY, final int offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.resetCache();
    }
    
    public int get(final int n, final int n2, final int n3) {
        this.lastZs = this.cache[n - this.offsetX][n2 - this.offsetY];
        this.lastDz = n3 - this.offsetZ;
        return this.lastZs[this.lastDz];
    }
    
    public void setLast(final int n) {
        this.lastZs[this.lastDz] = n;
    }
}
