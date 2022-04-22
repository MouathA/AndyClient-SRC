package penner.easing;

public class Elastic
{
    public static float easeIn(float n, final float n2, final float n3, final float n4) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4) == 1.0f) {
            return n2 + n3;
        }
        final float n5 = n4 * 0.3f;
        return -(n3 * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n5 / 4.0f) * 6.2831855f / n5)) + n2;
    }
    
    public static float easeIn(float n, final float n2, final float n3, final float n4, float n5, final float n6) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4) == 1.0f) {
            return n2 + n3;
        }
        float n7;
        if (n5 < Math.abs(n3)) {
            n5 = n3;
            n7 = n6 / 4.0f;
        }
        else {
            n7 = n6 / 6.2831855f * (float)Math.asin(n3 / n5);
        }
        return -(n5 * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n7) * 6.283185307179586 / n6)) + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4) == 1.0f) {
            return n2 + n3;
        }
        final float n5 = n4 * 0.3f;
        return n3 * (float)Math.pow(2.0, -10.0f * n) * (float)Math.sin((n * n4 - n5 / 4.0f) * 6.2831855f / n5) + n3 + n2;
    }
    
    public static float easeOut(float n, final float n2, final float n3, final float n4, float n5, final float n6) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4) == 1.0f) {
            return n2 + n3;
        }
        float n7;
        if (n5 < Math.abs(n3)) {
            n5 = n3;
            n7 = n6 / 4.0f;
        }
        else {
            n7 = n6 / 6.2831855f * (float)Math.asin(n3 / n5);
        }
        return n5 * (float)Math.pow(2.0, -10.0f * n) * (float)Math.sin((n * n4 - n7) * 6.2831855f / n6) + n3 + n2;
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4 / 2.0f) == 2.0f) {
            return n2 + n3;
        }
        final float n5 = n4 * 0.45000002f;
        final float n6 = n5 / 4.0f;
        if (n < 1.0f) {
            return -0.5f * n3 * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n6) * 6.2831855f / n5) + n2;
        }
        return n3 * (float)Math.pow(2.0, -10.0f * --n) * (float)Math.sin((n * n4 - n6) * 6.2831855f / n5) * 0.5f + n3 + n2;
    }
    
    public static float easeInOut(float n, final float n2, final float n3, final float n4, float n5, final float n6) {
        if (n == 0.0f) {
            return n2;
        }
        if ((n /= n4 / 2.0f) == 2.0f) {
            return n2 + n3;
        }
        float n7;
        if (n5 < Math.abs(n3)) {
            n5 = n3;
            n7 = n6 / 4.0f;
        }
        else {
            n7 = n6 / 6.2831855f * (float)Math.asin(n3 / n5);
        }
        if (n < 1.0f) {
            return -0.5f * n5 * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n7) * 6.2831855f / n6) + n2;
        }
        return n5 * (float)Math.pow(2.0, -10.0f * --n) * (float)Math.sin((n * n4 - n7) * 6.2831855f / n6) * 0.5f + n3 + n2;
    }
}
