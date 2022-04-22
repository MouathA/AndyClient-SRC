package optifine;

public class IntArray
{
    private int[] array;
    private int position;
    private int limit;
    
    public IntArray(final int n) {
        this.array = null;
        this.position = 0;
        this.limit = 0;
        this.array = new int[n];
    }
    
    public void put(final int n) {
        this.array[this.position] = n;
        ++this.position;
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }
    
    public void put(final int limit, final int n) {
        this.array[limit] = n;
        if (this.limit < limit) {
            this.limit = limit;
        }
    }
    
    public void position(final int position) {
        this.position = position;
    }
    
    public void put(final int[] array) {
        while (0 < array.length) {
            this.array[this.position] = array[0];
            ++this.position;
            int n = 0;
            ++n;
        }
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }
    
    public int get(final int n) {
        return this.array[n];
    }
    
    public int[] getArray() {
        return this.array;
    }
    
    public void clear() {
        this.position = 0;
        this.limit = 0;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public int getPosition() {
        return this.position;
    }
}
