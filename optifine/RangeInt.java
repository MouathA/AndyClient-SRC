package optifine;

public class RangeInt
{
    private int min;
    private int max;
    
    public RangeInt(final int n, final int n2) {
        this.min = Math.min(n, n2);
        this.max = Math.max(n, n2);
    }
    
    public boolean isInRange(final int n) {
        return n >= this.min && n <= this.max;
    }
    
    public int getMin() {
        return this.min;
    }
    
    public int getMax() {
        return this.max;
    }
    
    @Override
    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }
}
