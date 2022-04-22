package net.minecraft.util;

import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class MathHelper
{
    public static final float field_180189_a;
    private static final int SIN_BITS;
    private static final int SIN_MASK;
    private static final int SIN_COUNT;
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    public static final float PId2 = 1.5707964f;
    private static final float radFull = 6.2831855f;
    private static final float degFull = 360.0f;
    private static final float radToIndex = 651.8986f;
    private static final float degToIndex = 11.377778f;
    public static final float deg2Rad = 0.017453292f;
    private static final float[] SIN_TABLE_FAST;
    public static boolean fastMath;
    private static final float[] SIN_TABLE;
    private static final int[] multiplyDeBruijnBitPosition;
    private static final String __OBFID;
    private static final String[] lIlIlIlIIIIIIIII;
    private static String[] lIlIlIlIIIIIIlll;
    
    static {
        lllllIlIIllllIIl();
        lllllIlIIllllIII();
        SIN_MASK = 4095;
        SIN_COUNT = 4096;
        SIN_BITS = 12;
        __OBFID = MathHelper.lIlIlIlIIIIIIIII[0];
        field_180189_a = sqrt_float(2.0f);
        SIN_TABLE_FAST = new float[4096];
        MathHelper.fastMath = false;
        SIN_TABLE = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.SIN_TABLE[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
        for (int j = 0; j < 4096; ++j) {
            MathHelper.SIN_TABLE_FAST[j] = (float)Math.sin((j + 0.5f) / 4096.0f * 6.2831855f);
        }
        for (int k = 0; k < 360; k += 90) {
            MathHelper.SIN_TABLE_FAST[(int)(k * 11.377778f) & 0xFFF] = (float)Math.sin(k * 0.017453292f);
        }
    }
    
    public static float sin(final float n) {
        return MathHelper.fastMath ? MathHelper.SIN_TABLE_FAST[(int)(n * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(n * 10430.378f) & 0xFFFF];
    }
    
    public static float cos(final float n) {
        return MathHelper.fastMath ? MathHelper.SIN_TABLE_FAST[(int)((n + 1.5707964f) * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(n * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static float sqrt_float(final float n) {
        return (float)Math.sqrt(n);
    }
    
    public static float sqrt_double(final double n) {
        return (float)Math.sqrt(n);
    }
    
    public static int floor_float(final float n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static int truncateDoubleToInt(final double n) {
        return (int)(n + 1024.0) - 1024;
    }
    
    public static int floor_double(final double n) {
        final int n2 = (int)n;
        return (n < n2) ? (n2 - 1) : n2;
    }
    
    public static long floor_double_long(final double n) {
        final long n2 = (long)n;
        return (n < n2) ? (n2 - 1L) : n2;
    }
    
    public static int func_154353_e(final double n) {
        return (int)((n >= 0.0) ? n : (-n + 1.0));
    }
    
    public static float abs(final float n) {
        return (n >= 0.0f) ? n : (-n);
    }
    
    public static int abs_int(final int n) {
        return (n >= 0) ? n : (-n);
    }
    
    public static int ceiling_float_int(final float n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int ceiling_double_int(final double n) {
        final int n2 = (int)n;
        return (n > n2) ? (n2 + 1) : n2;
    }
    
    public static int clamp_int(final int n, final int n2, final int n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static float clamp_float(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static double clamp_double(final double n, final double n2, final double n3) {
        return (n < n2) ? n2 : ((n > n3) ? n3 : n);
    }
    
    public static double denormalizeClamp(final double n, final double n2, final double n3) {
        return (n3 < 0.0) ? n : ((n3 > 1.0) ? n2 : (n + (n2 - n) * n3));
    }
    
    public static double abs_max(double n, double n2) {
        if (n < 0.0) {
            n = -n;
        }
        if (n2 < 0.0) {
            n2 = -n2;
        }
        return (n > n2) ? n : n2;
    }
    
    public static int bucketInt(final int n, final int n2) {
        return (n < 0) ? (-((-n - 1) / n2) - 1) : (n / n2);
    }
    
    public static int getRandomIntegerInRange(final Random random, final int n, final int n2) {
        return (n >= n2) ? n : (random.nextInt(n2 - n + 1) + n);
    }
    
    public static float randomFloatClamp(final Random random, final float n, final float n2) {
        return (n >= n2) ? n : (random.nextFloat() * (n2 - n) + n);
    }
    
    public static double getRandomDoubleInRange(final Random random, final double n, final double n2) {
        return (n >= n2) ? n : (random.nextDouble() * (n2 - n) + n);
    }
    
    public static double average(final long[] array) {
        long n = 0L;
        for (int length = array.length, i = 0; i < length; ++i) {
            n += array[i];
        }
        return n / (double)array.length;
    }
    
    public static boolean func_180185_a(final float n, final float n2) {
        return abs(n2 - n) < 1.0E-5f;
    }
    
    public static int func_180184_b(final int n, final int n2) {
        return (n % n2 + n2) % n2;
    }
    
    public static float wrapAngleTo180_float(float n) {
        n %= 360.0f;
        if (n >= 180.0f) {
            n -= 360.0f;
        }
        if (n < -180.0f) {
            n += 360.0f;
        }
        return n;
    }
    
    public static double wrapAngleTo180_double(double n) {
        n %= 360.0;
        if (n >= 180.0) {
            n -= 360.0;
        }
        if (n < -180.0) {
            n += 360.0;
        }
        return n;
    }
    
    public static int parseIntWithDefault(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Throwable t) {
            return n;
        }
    }
    
    public static int parseIntWithDefaultAndMax(final String s, final int n, final int n2) {
        return Math.max(n2, parseIntWithDefault(s, n));
    }
    
    public static double parseDoubleWithDefault(final String s, final double n) {
        try {
            return Double.parseDouble(s);
        }
        catch (Throwable t) {
            return n;
        }
    }
    
    public static double parseDoubleWithDefaultAndMax(final String s, final double n, final double n2) {
        return Math.max(n2, parseDoubleWithDefault(s, n));
    }
    
    public static int roundUpToPowerOfTwo(final int n) {
        final int n2 = n - 1;
        final int n3 = n2 | n2 >> 1;
        final int n4 = n3 | n3 >> 2;
        final int n5 = n4 | n4 >> 4;
        final int n6 = n5 | n5 >> 8;
        return (n6 | n6 >> 16) + 1;
    }
    
    private static boolean isPowerOfTwo(final int n) {
        return n != 0 && (n & n - 1) == 0x0;
    }
    
    private static int calculateLogBaseTwoDeBruijn(int n) {
        n = (isPowerOfTwo(n) ? n : roundUpToPowerOfTwo(n));
        return MathHelper.multiplyDeBruijnBitPosition[(int)(n * 125613361L >> 27) & 0x1F];
    }
    
    public static int calculateLogBaseTwo(final int n) {
        return calculateLogBaseTwoDeBruijn(n) - (isPowerOfTwo(n) ? 0 : 1);
    }
    
    public static int func_154354_b(final int n, int n2) {
        if (n2 == 0) {
            return 0;
        }
        if (n == 0) {
            return n2;
        }
        if (n < 0) {
            n2 *= -1;
        }
        final int n3 = n % n2;
        return (n3 == 0) ? n : (n + n2 - n3);
    }
    
    public static int func_180183_b(final float n, final float n2, final float n3) {
        return func_180181_b(floor_float(n * 255.0f), floor_float(n2 * 255.0f), floor_float(n3 * 255.0f));
    }
    
    public static int func_180181_b(final int n, final int n2, final int n3) {
        return ((n << 8) + n2 << 8) + n3;
    }
    
    public static int func_180188_d(final int n, final int n2) {
        return (n & 0xFF000000) | (int)(((n & 0xFF0000) >> 16) * (float)((n2 & 0xFF0000) >> 16) / 255.0f) << 16 | (int)(((n & 0xFF00) >> 8) * (float)((n2 & 0xFF00) >> 8) / 255.0f) << 8 | (int)(((n & 0xFF) >> 0) * (float)((n2 & 0xFF) >> 0) / 255.0f);
    }
    
    public static long func_180186_a(final Vec3i vec3i) {
        return func_180187_c(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public static long func_180187_c(final int n, final int n2, final int n3) {
        final long n4 = (long)(n * 3129871) ^ n3 * 116129781L ^ (long)n2;
        return n4 * n4 * 42317861L + n4 * 11L;
    }
    
    public static UUID func_180182_a(final Random random) {
        return new UUID((random.nextLong() & 0xFFFFFFFFFFFF0FFFL) | 0x4000L, (random.nextLong() & 0x3FFFFFFFFFFFFFFFL) | Long.MIN_VALUE);
    }
    
    private static void lllllIlIIllllIII() {
        (lIlIlIlIIIIIIIII = new String[1])[0] = lllllIlIIlllIlII(MathHelper.lIlIlIlIIIIIIlll[0], MathHelper.lIlIlIlIIIIIIlll[1]);
        MathHelper.lIlIlIlIIIIIIlll = null;
    }
    
    private static void lllllIlIIllllIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        MathHelper.lIlIlIlIIIIIIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllllIlIIlllIlII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
