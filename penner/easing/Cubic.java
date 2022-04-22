package penner.easing;

public class Cubic
{
    public static float easeIn(float n, final float n2, final float n3, final float n4) {
        return n3 * (n /= n4) * n * n + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4) {
        return n3 * ((n = n / n4 - 1.0f) * n * n + 1.0f) + n2;
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n * n + n2;
        }
        return n3 / 2.0f * ((n -= 2.0f) * n * n + 2.0f) + n2;
    }
}
