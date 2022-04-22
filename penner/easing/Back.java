package penner.easing;

public class Back
{
    public static float easeIn(float n, final float n2, final float n3, final float n4) {
        final float n5 = 1.70158f;
        return n3 * (n /= n4) * n * ((n5 + 1.0f) * n - n5) + n2;
    }
    
    public static float easeIn(float n, final float n2, final float n3, final float n4, final float n5) {
        return n3 * (n /= n4) * n * ((n5 + 1.0f) * n - n5) + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4) {
        final float n5 = 1.70158f;
        return n3 * ((n = n / n4 - 1.0f) * n * ((n5 + 1.0f) * n + n5) + 1.0f) + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4, final float n5) {
        return n3 * ((n = n / n4 - 1.0f) * n * ((n5 + 1.0f) * n + n5) + 1.0f) + n2;
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4) {
        final float n5 = 1.70158f;
        if ((n /= n4 / 2.0f) < 1.0f) {
            final float n6 = n3 / 2.0f * n * n;
            final float n7 = n5 * 1.525f;
            return n6 * ((n7 + 1.0f) * n - n7) + n2;
        }
        final float n8 = n3 / 2.0f;
        final float n9 = (n -= 2.0f) * n;
        final float n10 = n5 * 1.525f;
        return n8 * (n9 * ((n10 + 1.0f) * n + n10) + 2.0f) + n2;
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4, float n5) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n * (((n5 *= 1.525f) + 1.0f) * n - n5) + n2;
        }
        return n3 / 2.0f * ((n -= 2.0f) * n * (((n5 *= 1.525f) + 1.0f) * n + n5) + 2.0f) + n2;
    }
}
