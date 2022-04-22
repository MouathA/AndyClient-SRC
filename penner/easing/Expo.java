package penner.easing;

public class Expo
{
    public static float easeIn(final float n, final float n2, final float n3, final float n4) {
        return (n == 0.0f) ? n2 : (n3 * (float)Math.pow(2.0, 10.0f * (n / n4 - 1.0f)) + n2);
    }
    
    public static float easeOut(final float n, final float n2, final float n3, final float n4) {
        return (n == n4) ? (n2 + n3) : (n3 * (-(float)Math.pow(2.0, -10.0f * n / n4) + 1.0f) + n2);
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4) {
        if (n == 0.0f) {
            return n2;
        }
        if (n == n4) {
            return n2 + n3;
        }
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * (float)Math.pow(2.0, 10.0f * (n - 1.0f)) + n2;
        }
        return n3 / 2.0f * (-(float)Math.pow(2.0, -10.0f * --n) + 2.0f) + n2;
    }
}
