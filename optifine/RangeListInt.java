package optifine;

public class RangeListInt
{
    private RangeInt[] ranges;
    
    public RangeListInt() {
        this.ranges = new RangeInt[0];
    }
    
    public void addRange(final RangeInt rangeInt) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, rangeInt);
    }
    
    public boolean isInRange(final int n) {
        while (0 < this.ranges.length) {
            if (this.ranges[0].isInRange(n)) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public int getCountRanges() {
        return this.ranges.length;
    }
    
    public RangeInt getRange(final int n) {
        return this.ranges[n];
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        while (0 < this.ranges.length) {
            sb.append(this.ranges[0].toString());
            int n = 0;
            ++n;
        }
        sb.append("]");
        return sb.toString();
    }
}
