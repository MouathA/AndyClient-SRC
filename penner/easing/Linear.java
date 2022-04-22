package penner.easing;

public class Linear
{
    public static float easeNone(final float n, final float n2, final float n3, final float n4) {
        return n3 * n / n4 + n2;
    }
    
    public static float easeIn(final float n, final float n2, final float n3, final float n4) {
        return n3 * n / n4 + n2;
    }
    
    public static float easeOut(final float n, final float n2, final float n3, final float n4) {
        return n3 * n / n4 + n2;
    }
    
    public static float easeInOut(final float n, final float n2, final float n3, final float n4) {
        return n3 * n / n4 + n2;
    }
}
