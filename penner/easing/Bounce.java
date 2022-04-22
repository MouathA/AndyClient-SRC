package penner.easing;

public class Bounce
{
    public static float easeIn(final float n, final float n2, final float n3, final float n4) {
        return n3 - easeOut(n4 - n, 0.0f, n3, n4) + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4) < 0.36363637f) {
            return n3 * 7.5625f * n * n + n2;
        }
        if (n < 0.72727275f) {
            return n3 * (7.5625f * (n -= 0.54545456f) * n + 0.75f) + n2;
        }
        if (n < 0.9090909090909091) {
            return n3 * (7.5625f * (n -= 0.8181818f) * n + 0.9375f) + n2;
        }
        return n3 * (7.5625f * (n -= 0.95454544f) * n + 0.984375f) + n2;
    }
    
    public static float easeInOut(final float n, final float n2, final float n3, final float n4) {
        if (n < n4 / 2.0f) {
            return easeIn(n * 2.0f, 0.0f, n3, n4) * 0.5f + n2;
        }
        return easeOut(n * 2.0f - n4, 0.0f, n3, n4) * 0.5f + n3 * 0.5f + n2;
    }
}
