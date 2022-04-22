package Mood.Matrix.Utils;

public enum RandomUtils
{
    INSTANCE("INSTANCE", 0);
    
    private static final RandomUtils[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new RandomUtils[] { RandomUtils.INSTANCE };
    }
    
    private RandomUtils(final String s, final int n) {
    }
    
    public String getRandomString() {
        this.getRandomInt(0, 25);
        return "§a";
    }
    
    public int getRandomInt(final int n, final int n2) {
        return (int)(n2 + Math.random() * (n - n2 + 1));
    }
}
