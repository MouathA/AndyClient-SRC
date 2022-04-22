package net.minecraft.world;

import net.minecraft.util.*;

public class DifficultyInstance
{
    private final EnumDifficulty field_180172_a;
    private final float field_180171_b;
    private static final String __OBFID;
    
    public DifficultyInstance(final EnumDifficulty field_180172_a, final long n, final long n2, final float n3) {
        this.field_180172_a = field_180172_a;
        this.field_180171_b = this.func_180169_a(field_180172_a, n, n2, n3);
    }
    
    public float func_180168_b() {
        return this.field_180171_b;
    }
    
    public float func_180170_c() {
        return (this.field_180171_b < 2.0f) ? 0.0f : ((this.field_180171_b > 4.0f) ? 1.0f : ((this.field_180171_b - 2.0f) / 2.0f));
    }
    
    private float func_180169_a(final EnumDifficulty enumDifficulty, final long n, final long n2, final float n3) {
        if (enumDifficulty == EnumDifficulty.PEACEFUL) {
            return 0.0f;
        }
        final boolean b = enumDifficulty == EnumDifficulty.HARD;
        final float n4 = 0.75f;
        final float n5 = MathHelper.clamp_float((n - 72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        final float n6 = n4 + n5;
        float n7 = 0.0f + MathHelper.clamp_float(n2 / 3600000.0f, 0.0f, 1.0f) * (b ? 1.0f : 0.75f) + MathHelper.clamp_float(n3 * 0.25f, 0.0f, n5);
        if (enumDifficulty == EnumDifficulty.EASY) {
            n7 *= 0.5f;
        }
        return enumDifficulty.getDifficultyId() * (n6 + n7);
    }
    
    static {
        __OBFID = "CL_00002261";
    }
}
