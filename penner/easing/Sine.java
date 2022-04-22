package penner.easing;

public class Sine
{
    public static float easeIn(final float n, final float n2, final float n3, final float n4) {
        return -n3 * (float)Math.cos(n / n4 * 1.5707963267948966) + n3 + n2;
    }
    
    public static float easeOut(final float n, final float n2, final float n3, final float n4) {
        return n3 * (float)Math.sin(n / n4 * 1.5707963267948966) + n2;
    }
    
    public static float easeInOut(final float n, final float n2, final float n3, final float n4) {
        return -n3 / 2.0f * ((float)Math.cos(3.141592653589793 * n / n4) - 1.0f) + n2;
    }
}
