package net.minecraft.util;

import com.google.common.collect.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public enum EnumParticleTypes
{
    EXPLOSION_NORMAL(EnumParticleTypes.lIllIllIIlIlIlII[1], 0, EnumParticleTypes.lIllIllIIlIlIlII[2], 0, EnumParticleTypes.lIllIllIIlIlIlII[3], 0, true), 
    EXPLOSION_LARGE(EnumParticleTypes.lIllIllIIlIlIlII[4], 1, EnumParticleTypes.lIllIllIIlIlIlII[5], 1, EnumParticleTypes.lIllIllIIlIlIlII[6], 1, true), 
    EXPLOSION_HUGE(EnumParticleTypes.lIllIllIIlIlIlII[7], 2, EnumParticleTypes.lIllIllIIlIlIlII[8], 2, EnumParticleTypes.lIllIllIIlIlIlII[9], 2, true), 
    FIREWORKS_SPARK(EnumParticleTypes.lIllIllIIlIlIlII[10], 3, EnumParticleTypes.lIllIllIIlIlIlII[11], 3, EnumParticleTypes.lIllIllIIlIlIlII[12], 3, false), 
    WATER_BUBBLE(EnumParticleTypes.lIllIllIIlIlIlII[13], 4, EnumParticleTypes.lIllIllIIlIlIlII[14], 4, EnumParticleTypes.lIllIllIIlIlIlII[15], 4, false), 
    WATER_SPLASH(EnumParticleTypes.lIllIllIIlIlIlII[16], 5, EnumParticleTypes.lIllIllIIlIlIlII[17], 5, EnumParticleTypes.lIllIllIIlIlIlII[18], 5, false), 
    WATER_WAKE(EnumParticleTypes.lIllIllIIlIlIlII[19], 6, EnumParticleTypes.lIllIllIIlIlIlII[20], 6, EnumParticleTypes.lIllIllIIlIlIlII[21], 6, false), 
    SUSPENDED(EnumParticleTypes.lIllIllIIlIlIlII[22], 7, EnumParticleTypes.lIllIllIIlIlIlII[23], 7, EnumParticleTypes.lIllIllIIlIlIlII[24], 7, false), 
    SUSPENDED_DEPTH(EnumParticleTypes.lIllIllIIlIlIlII[25], 8, EnumParticleTypes.lIllIllIIlIlIlII[26], 8, EnumParticleTypes.lIllIllIIlIlIlII[27], 8, false), 
    CRIT(EnumParticleTypes.lIllIllIIlIlIlII[28], 9, EnumParticleTypes.lIllIllIIlIlIlII[29], 9, EnumParticleTypes.lIllIllIIlIlIlII[30], 9, false), 
    CRIT_MAGIC(EnumParticleTypes.lIllIllIIlIlIlII[31], 10, EnumParticleTypes.lIllIllIIlIlIlII[32], 10, EnumParticleTypes.lIllIllIIlIlIlII[33], 10, false), 
    SMOKE_NORMAL(EnumParticleTypes.lIllIllIIlIlIlII[34], 11, EnumParticleTypes.lIllIllIIlIlIlII[35], 11, EnumParticleTypes.lIllIllIIlIlIlII[36], 11, false), 
    SMOKE_LARGE(EnumParticleTypes.lIllIllIIlIlIlII[37], 12, EnumParticleTypes.lIllIllIIlIlIlII[38], 12, EnumParticleTypes.lIllIllIIlIlIlII[39], 12, false), 
    SPELL(EnumParticleTypes.lIllIllIIlIlIlII[40], 13, EnumParticleTypes.lIllIllIIlIlIlII[41], 13, EnumParticleTypes.lIllIllIIlIlIlII[42], 13, false), 
    SPELL_INSTANT(EnumParticleTypes.lIllIllIIlIlIlII[43], 14, EnumParticleTypes.lIllIllIIlIlIlII[44], 14, EnumParticleTypes.lIllIllIIlIlIlII[45], 14, false), 
    SPELL_MOB(EnumParticleTypes.lIllIllIIlIlIlII[46], 15, EnumParticleTypes.lIllIllIIlIlIlII[47], 15, EnumParticleTypes.lIllIllIIlIlIlII[48], 15, false), 
    SPELL_MOB_AMBIENT(EnumParticleTypes.lIllIllIIlIlIlII[49], 16, EnumParticleTypes.lIllIllIIlIlIlII[50], 16, EnumParticleTypes.lIllIllIIlIlIlII[51], 16, false), 
    SPELL_WITCH(EnumParticleTypes.lIllIllIIlIlIlII[52], 17, EnumParticleTypes.lIllIllIIlIlIlII[53], 17, EnumParticleTypes.lIllIllIIlIlIlII[54], 17, false), 
    DRIP_WATER(EnumParticleTypes.lIllIllIIlIlIlII[55], 18, EnumParticleTypes.lIllIllIIlIlIlII[56], 18, EnumParticleTypes.lIllIllIIlIlIlII[57], 18, false), 
    DRIP_LAVA(EnumParticleTypes.lIllIllIIlIlIlII[58], 19, EnumParticleTypes.lIllIllIIlIlIlII[59], 19, EnumParticleTypes.lIllIllIIlIlIlII[60], 19, false), 
    VILLAGER_ANGRY(EnumParticleTypes.lIllIllIIlIlIlII[61], 20, EnumParticleTypes.lIllIllIIlIlIlII[62], 20, EnumParticleTypes.lIllIllIIlIlIlII[63], 20, false), 
    VILLAGER_HAPPY(EnumParticleTypes.lIllIllIIlIlIlII[64], 21, EnumParticleTypes.lIllIllIIlIlIlII[65], 21, EnumParticleTypes.lIllIllIIlIlIlII[66], 21, false), 
    TOWN_AURA(EnumParticleTypes.lIllIllIIlIlIlII[67], 22, EnumParticleTypes.lIllIllIIlIlIlII[68], 22, EnumParticleTypes.lIllIllIIlIlIlII[69], 22, false), 
    NOTE(EnumParticleTypes.lIllIllIIlIlIlII[70], 23, EnumParticleTypes.lIllIllIIlIlIlII[71], 23, EnumParticleTypes.lIllIllIIlIlIlII[72], 23, false), 
    PORTAL(EnumParticleTypes.lIllIllIIlIlIlII[73], 24, EnumParticleTypes.lIllIllIIlIlIlII[74], 24, EnumParticleTypes.lIllIllIIlIlIlII[75], 24, false), 
    ENCHANTMENT_TABLE(EnumParticleTypes.lIllIllIIlIlIlII[76], 25, EnumParticleTypes.lIllIllIIlIlIlII[77], 25, EnumParticleTypes.lIllIllIIlIlIlII[78], 25, false), 
    FLAME(EnumParticleTypes.lIllIllIIlIlIlII[79], 26, EnumParticleTypes.lIllIllIIlIlIlII[80], 26, EnumParticleTypes.lIllIllIIlIlIlII[81], 26, false), 
    LAVA(EnumParticleTypes.lIllIllIIlIlIlII[82], 27, EnumParticleTypes.lIllIllIIlIlIlII[83], 27, EnumParticleTypes.lIllIllIIlIlIlII[84], 27, false), 
    FOOTSTEP(EnumParticleTypes.lIllIllIIlIlIlII[85], 28, EnumParticleTypes.lIllIllIIlIlIlII[86], 28, EnumParticleTypes.lIllIllIIlIlIlII[87], 28, false), 
    CLOUD(EnumParticleTypes.lIllIllIIlIlIlII[88], 29, EnumParticleTypes.lIllIllIIlIlIlII[89], 29, EnumParticleTypes.lIllIllIIlIlIlII[90], 29, false), 
    REDSTONE(EnumParticleTypes.lIllIllIIlIlIlII[91], 30, EnumParticleTypes.lIllIllIIlIlIlII[92], 30, EnumParticleTypes.lIllIllIIlIlIlII[93], 30, false), 
    SNOWBALL(EnumParticleTypes.lIllIllIIlIlIlII[94], 31, EnumParticleTypes.lIllIllIIlIlIlII[95], 31, EnumParticleTypes.lIllIllIIlIlIlII[96], 31, false), 
    SNOW_SHOVEL(EnumParticleTypes.lIllIllIIlIlIlII[97], 32, EnumParticleTypes.lIllIllIIlIlIlII[98], 32, EnumParticleTypes.lIllIllIIlIlIlII[99], 32, false), 
    SLIME(EnumParticleTypes.lIllIllIIlIlIlII[100], 33, EnumParticleTypes.lIllIllIIlIlIlII[101], 33, EnumParticleTypes.lIllIllIIlIlIlII[102], 33, false), 
    HEART(EnumParticleTypes.lIllIllIIlIlIlII[103], 34, EnumParticleTypes.lIllIllIIlIlIlII[104], 34, EnumParticleTypes.lIllIllIIlIlIlII[105], 34, false), 
    BARRIER(EnumParticleTypes.lIllIllIIlIlIlII[106], 35, EnumParticleTypes.lIllIllIIlIlIlII[107], 35, EnumParticleTypes.lIllIllIIlIlIlII[108], 35, false), 
    ITEM_CRACK(EnumParticleTypes.lIllIllIIlIlIlII[109], 36, EnumParticleTypes.lIllIllIIlIlIlII[110], 36, EnumParticleTypes.lIllIllIIlIlIlII[111], 36, false, 2), 
    BLOCK_CRACK(EnumParticleTypes.lIllIllIIlIlIlII[112], 37, EnumParticleTypes.lIllIllIIlIlIlII[113], 37, EnumParticleTypes.lIllIllIIlIlIlII[114], 37, false, 1), 
    BLOCK_DUST(EnumParticleTypes.lIllIllIIlIlIlII[115], 38, EnumParticleTypes.lIllIllIIlIlIlII[116], 38, EnumParticleTypes.lIllIllIIlIlIlII[117], 38, false, 1), 
    WATER_DROP(EnumParticleTypes.lIllIllIIlIlIlII[118], 39, EnumParticleTypes.lIllIllIIlIlIlII[119], 39, EnumParticleTypes.lIllIllIIlIlIlII[120], 39, false), 
    ITEM_TAKE(EnumParticleTypes.lIllIllIIlIlIlII[121], 40, EnumParticleTypes.lIllIllIIlIlIlII[122], 40, EnumParticleTypes.lIllIllIIlIlIlII[123], 40, false), 
    MOB_APPEARANCE(EnumParticleTypes.lIllIllIIlIlIlII[124], 41, EnumParticleTypes.lIllIllIIlIlIlII[125], 41, EnumParticleTypes.lIllIllIIlIlIlII[126], 41, true);
    
    private final String field_179369_Q;
    private final int field_179372_R;
    private final boolean field_179371_S;
    private final int field_179366_T;
    private static final Map field_179365_U;
    private static final String[] field_179368_V;
    private static final EnumParticleTypes[] $VALUES;
    private static final String __OBFID;
    private static final EnumParticleTypes[] ENUM$VALUES;
    private static final String[] lIllIllIIlIlIlII;
    private static String[] lIllIllIIllllllI;
    
    static {
        lIIIIllIIlIIIlIIl();
        lIIIIllIIlIIIlIII();
        __OBFID = EnumParticleTypes.lIllIllIIlIlIlII[0];
        ENUM$VALUES = new EnumParticleTypes[] { EnumParticleTypes.EXPLOSION_NORMAL, EnumParticleTypes.EXPLOSION_LARGE, EnumParticleTypes.EXPLOSION_HUGE, EnumParticleTypes.FIREWORKS_SPARK, EnumParticleTypes.WATER_BUBBLE, EnumParticleTypes.WATER_SPLASH, EnumParticleTypes.WATER_WAKE, EnumParticleTypes.SUSPENDED, EnumParticleTypes.SUSPENDED_DEPTH, EnumParticleTypes.CRIT, EnumParticleTypes.CRIT_MAGIC, EnumParticleTypes.SMOKE_NORMAL, EnumParticleTypes.SMOKE_LARGE, EnumParticleTypes.SPELL, EnumParticleTypes.SPELL_INSTANT, EnumParticleTypes.SPELL_MOB, EnumParticleTypes.SPELL_MOB_AMBIENT, EnumParticleTypes.SPELL_WITCH, EnumParticleTypes.DRIP_WATER, EnumParticleTypes.DRIP_LAVA, EnumParticleTypes.VILLAGER_ANGRY, EnumParticleTypes.VILLAGER_HAPPY, EnumParticleTypes.TOWN_AURA, EnumParticleTypes.NOTE, EnumParticleTypes.PORTAL, EnumParticleTypes.ENCHANTMENT_TABLE, EnumParticleTypes.FLAME, EnumParticleTypes.LAVA, EnumParticleTypes.FOOTSTEP, EnumParticleTypes.CLOUD, EnumParticleTypes.REDSTONE, EnumParticleTypes.SNOWBALL, EnumParticleTypes.SNOW_SHOVEL, EnumParticleTypes.SLIME, EnumParticleTypes.HEART, EnumParticleTypes.BARRIER, EnumParticleTypes.ITEM_CRACK, EnumParticleTypes.BLOCK_CRACK, EnumParticleTypes.BLOCK_DUST, EnumParticleTypes.WATER_DROP, EnumParticleTypes.ITEM_TAKE, EnumParticleTypes.MOB_APPEARANCE };
        field_179365_U = Maps.newHashMap();
        $VALUES = new EnumParticleTypes[] { EnumParticleTypes.EXPLOSION_NORMAL, EnumParticleTypes.EXPLOSION_LARGE, EnumParticleTypes.EXPLOSION_HUGE, EnumParticleTypes.FIREWORKS_SPARK, EnumParticleTypes.WATER_BUBBLE, EnumParticleTypes.WATER_SPLASH, EnumParticleTypes.WATER_WAKE, EnumParticleTypes.SUSPENDED, EnumParticleTypes.SUSPENDED_DEPTH, EnumParticleTypes.CRIT, EnumParticleTypes.CRIT_MAGIC, EnumParticleTypes.SMOKE_NORMAL, EnumParticleTypes.SMOKE_LARGE, EnumParticleTypes.SPELL, EnumParticleTypes.SPELL_INSTANT, EnumParticleTypes.SPELL_MOB, EnumParticleTypes.SPELL_MOB_AMBIENT, EnumParticleTypes.SPELL_WITCH, EnumParticleTypes.DRIP_WATER, EnumParticleTypes.DRIP_LAVA, EnumParticleTypes.VILLAGER_ANGRY, EnumParticleTypes.VILLAGER_HAPPY, EnumParticleTypes.TOWN_AURA, EnumParticleTypes.NOTE, EnumParticleTypes.PORTAL, EnumParticleTypes.ENCHANTMENT_TABLE, EnumParticleTypes.FLAME, EnumParticleTypes.LAVA, EnumParticleTypes.FOOTSTEP, EnumParticleTypes.CLOUD, EnumParticleTypes.REDSTONE, EnumParticleTypes.SNOWBALL, EnumParticleTypes.SNOW_SHOVEL, EnumParticleTypes.SLIME, EnumParticleTypes.HEART, EnumParticleTypes.BARRIER, EnumParticleTypes.ITEM_CRACK, EnumParticleTypes.BLOCK_CRACK, EnumParticleTypes.BLOCK_DUST, EnumParticleTypes.WATER_DROP, EnumParticleTypes.ITEM_TAKE, EnumParticleTypes.MOB_APPEARANCE };
        final ArrayList arrayList = Lists.newArrayList();
        for (final EnumParticleTypes enumParticleTypes : values()) {
            EnumParticleTypes.field_179365_U.put(enumParticleTypes.func_179348_c(), enumParticleTypes);
            if (!enumParticleTypes.func_179346_b().endsWith(EnumParticleTypes.lIllIllIIlIlIlII[127])) {
                arrayList.add(enumParticleTypes.func_179346_b());
            }
        }
        field_179368_V = arrayList.toArray(new String[arrayList.size()]);
    }
    
    private EnumParticleTypes(final String s, final int n, final String s2, final int n2, final String field_179369_Q, final int field_179372_R, final boolean field_179371_S, final int field_179366_T) {
        this.field_179369_Q = field_179369_Q;
        this.field_179372_R = field_179372_R;
        this.field_179371_S = field_179371_S;
        this.field_179366_T = field_179366_T;
    }
    
    private EnumParticleTypes(final String s, final int n, final String s2, final int n2, final String s3, final int n3, final boolean b) {
        this(s, n, s2, n2, s3, n3, b, 0);
    }
    
    public static String[] func_179349_a() {
        return EnumParticleTypes.field_179368_V;
    }
    
    public String func_179346_b() {
        return this.field_179369_Q;
    }
    
    public int func_179348_c() {
        return this.field_179372_R;
    }
    
    public int func_179345_d() {
        return this.field_179366_T;
    }
    
    public boolean func_179344_e() {
        return this.field_179371_S;
    }
    
    public boolean func_179343_f() {
        return this.field_179366_T > 0;
    }
    
    public static EnumParticleTypes func_179342_a(final int n) {
        return EnumParticleTypes.field_179365_U.get(n);
    }
    
    private static void lIIIIllIIlIIIlIII() {
        (lIllIllIIlIlIlII = new String[128])[0] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[0], EnumParticleTypes.lIllIllIIllllllI[1]);
        EnumParticleTypes.lIllIllIIlIlIlII[1] = lIIIIllIIIlIllIll(EnumParticleTypes.lIllIllIIllllllI[2], EnumParticleTypes.lIllIllIIllllllI[3]);
        EnumParticleTypes.lIllIllIIlIlIlII[2] = lIIIIllIIIlIlllII(EnumParticleTypes.lIllIllIIllllllI[4], EnumParticleTypes.lIllIllIIllllllI[5]);
        EnumParticleTypes.lIllIllIIlIlIlII[3] = lIIIIllIIIlIlllII(EnumParticleTypes.lIllIllIIllllllI[6], EnumParticleTypes.lIllIllIIllllllI[7]);
        EnumParticleTypes.lIllIllIIlIlIlII[4] = lIIIIllIIIlIlllIl(EnumParticleTypes.lIllIllIIllllllI[8], EnumParticleTypes.lIllIllIIllllllI[9]);
        EnumParticleTypes.lIllIllIIlIlIlII[5] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[10], EnumParticleTypes.lIllIllIIllllllI[11]);
        EnumParticleTypes.lIllIllIIlIlIlII[6] = lIIIIllIIIlIllIll(EnumParticleTypes.lIllIllIIllllllI[12], EnumParticleTypes.lIllIllIIllllllI[13]);
        EnumParticleTypes.lIllIllIIlIlIlII[7] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[14], EnumParticleTypes.lIllIllIIllllllI[15]);
        EnumParticleTypes.lIllIllIIlIlIlII[8] = lIIIIllIIIlIllIll(EnumParticleTypes.lIllIllIIllllllI[16], EnumParticleTypes.lIllIllIIllllllI[17]);
        EnumParticleTypes.lIllIllIIlIlIlII[9] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[18], EnumParticleTypes.lIllIllIIllllllI[19]);
        EnumParticleTypes.lIllIllIIlIlIlII[10] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[20], EnumParticleTypes.lIllIllIIllllllI[21]);
        EnumParticleTypes.lIllIllIIlIlIlII[11] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[22], EnumParticleTypes.lIllIllIIllllllI[23]);
        EnumParticleTypes.lIllIllIIlIlIlII[12] = lIIIIllIIIlIlllII(EnumParticleTypes.lIllIllIIllllllI[24], EnumParticleTypes.lIllIllIIllllllI[25]);
        EnumParticleTypes.lIllIllIIlIlIlII[13] = lIIIIllIIIlIllIII(EnumParticleTypes.lIllIllIIllllllI[26], EnumParticleTypes.lIllIllIIllllllI[27]);
        EnumParticleTypes.lIllIllIIlIlIlII[14] = lIIIIllIIIlIlllII(EnumParticleTypes.lIllIllIIllllllI[28], EnumParticleTypes.lIllIllIIllllllI[29]);
        EnumParticleTypes.lIllIllIIlIlIlII[15] = lIIIIllIIIlIlllII(EnumParticleTypes.lIllIllIIllllllI[30], EnumParticleTypes.lIllIllIIllllllI[31]);
        EnumParticleTypes.lIllIllIIlIlIlII[16] = lIIIIllIIIlIllIll(EnumParticleTypes.lIllIllIIllllllI[32], EnumParticleTypes.lIllIllIIllllllI[33]);
        EnumParticleTypes.lIllIllIIlIlIlII[17] = lIIIIllIIIlIlllIl("HpmdDwnhM7z9honA9+NDjA==", "XMvty");
        EnumParticleTypes.lIllIllIIlIlIlII[18] = lIIIIllIIIlIlllIl("TAAjpYF7Hz6hRVO+Lq+QBg==", "qnyMy");
        EnumParticleTypes.lIllIllIIlIlIlII[19] = lIIIIllIIIlIllIll("qbSwn6aAIyJhZxJrLvs6jQ==", "ZEeTC");
        EnumParticleTypes.lIllIllIIlIlIlII[20] = lIIIIllIIIlIlllIl("hmBN+TI/b8y/dXb0ADjHUA==", "sBqiu");
        EnumParticleTypes.lIllIllIIlIlIlII[21] = lIIIIllIIIlIlllIl("NTbhHgi231NFQNVwALJucg==", "yzJHZ");
        EnumParticleTypes.lIllIllIIlIlIlII[22] = lIIIIllIIIlIlllIl("W6WepNhOdYf9HMgN4q1F+g==", "JJlGQ");
        EnumParticleTypes.lIllIllIIlIlIlII[23] = lIIIIllIIIlIllIll("bnmvAVjz4WFhn1NYf2m8pQ==", "OKXGt");
        EnumParticleTypes.lIllIllIIlIlIlII[24] = lIIIIllIIIlIllIll("p9IMnJefE1mRaxLYuwj+zA==", "kUPKa");
        EnumParticleTypes.lIllIllIIlIlIlII[25] = lIIIIllIIIlIllIll("Y1UNXoeWEXGkrbNdWI4JFw==", "AokMh");
        EnumParticleTypes.lIllIllIIlIlIlII[26] = lIIIIllIIIlIlllII("OD8APxIlLhYrCC8vAzsf", "kjSoW");
        EnumParticleTypes.lIllIllIIlIlIlII[27] = lIIIIllIIIlIlllII("Fg8jEA4BHyAUAxwO", "rjSdf");
        EnumParticleTypes.lIllIllIIlIlIlII[28] = lIIIIllIIIlIllIll("NX9xfQFPDnA=", "pzpJI");
        EnumParticleTypes.lIllIllIIlIlIlII[29] = lIIIIllIIIlIllIll("YWwJIjUXZdY=", "KatXO");
        EnumParticleTypes.lIllIllIIlIlIlII[30] = lIIIIllIIIlIllIII("lcNkeT54Xig=", "UVQwu");
        EnumParticleTypes.lIllIllIIlIlIlII[31] = lIIIIllIIIlIllIll("fzsXUovFQYAjAxxMmseBIA==", "AIIou");
        EnumParticleTypes.lIllIllIIlIlIlII[32] = lIIIIllIIIlIllIll("OzNTTSwqc43NggypMv44IQ==", "MbYJH");
        EnumParticleTypes.lIllIllIIlIlIlII[33] = lIIIIllIIIlIlllIl("XimWo5k/w3Mw7HX6I3/iCw==", "TUOcr");
        EnumParticleTypes.lIllIllIIlIlIlII[34] = lIIIIllIIIlIlllII("PgUGJTAyBgY8OCwE", "mHInu");
        EnumParticleTypes.lIllIllIIlIlIlII[35] = lIIIIllIIIlIlllII("OiouPyw2KS4mJCgr", "igati");
        EnumParticleTypes.lIllIllIIlIlIlII[36] = lIIIIllIIIlIlllII("Hhs3Gxw=", "mvXpy");
        EnumParticleTypes.lIllIllIIlIlIlII[37] = lIIIIllIIIlIllIII("iZaBksRykJxa1m5R5xIyPw==", "NarFR");
        EnumParticleTypes.lIllIllIIlIlIlII[38] = lIIIIllIIIlIlllII("IjwEDDwuPQoVPjQ=", "qqKGy");
        EnumParticleTypes.lIllIllIIlIlIlII[39] = lIIIIllIIIlIllIll("CGDOQ3lm2R3CfkNRnX63zw==", "vogFR");
        EnumParticleTypes.lIllIllIIlIlIlII[40] = lIIIIllIIIlIlllIl("ZFCfM/DgQkdZX4N0QxyFtw==", "UCuFb");
        EnumParticleTypes.lIllIllIIlIlIlII[41] = lIIIIllIIIlIlllII("GTkwHx8=", "JiuSS");
        EnumParticleTypes.lIllIllIIlIlIlII[42] = lIIIIllIIIlIlllIl("w4LmfzTuEN4zjbZTMyYWqw==", "TnajL");
        EnumParticleTypes.lIllIllIIlIlIlII[43] = lIIIIllIIIlIllIII("UXnhcRRPvx67yul0G5dtcg==", "zrEjZ");
        EnumParticleTypes.lIllIllIIlIlIlII[44] = lIIIIllIIIlIlllII("AgYGIwkOHw08ERAYFw==", "QVCoE");
        EnumParticleTypes.lIllIllIIlIlIlII[45] = lIIIIllIIIlIlllII("Aj0jEwUFJwMXAQc/", "kSPgd");
        EnumParticleTypes.lIllIllIIlIlIlII[46] = lIIIIllIIIlIlllIl("8uS3rdOsWmneVnRbExqLUg==", "FTplt");
        EnumParticleTypes.lIllIllIIlIlIlII[47] = lIIIIllIIIlIlllII("KyYuOS4nOyQ3", "xvkub");
        EnumParticleTypes.lIllIllIIlIlIlII[48] = lIIIIllIIIlIlllIl("C+LSXL99aLoQPEQDCUTQsw==", "JDSyM");
        EnumParticleTypes.lIllIllIIlIlIlII[49] = lIIIIllIIIlIlllII("IScGAg0tOgwMHjM6AQcEPCM=", "rwCNA");
        EnumParticleTypes.lIllIllIIlIlIlII[50] = lIIIIllIIIlIlllIl("UAd3/op17YKaCvj/5dm1ta4Y4Aeu+F5JO5Kjbk7Vi24=", "CbSxc");
        EnumParticleTypes.lIllIllIIlIlIlII[51] = lIIIIllIIIlIllIII("WfyCpYIkLhpFABK+aXiVTg==", "VEpsC");
        EnumParticleTypes.lIllIllIIlIlIlII[52] = lIIIIllIIIlIllIll("RMN6pn3xxBCU9lstlUt+hg==", "EnEAy");
        EnumParticleTypes.lIllIllIIlIlIlII[53] = lIIIIllIIIlIllIll("kGlZtwOWGFwqOHHHOOw18A==", "jwGAi");
        EnumParticleTypes.lIllIllIIlIlIlII[54] = lIIIIllIIIlIllIll("xl8piNOJFscFrf2th8KrcQ==", "qwUYJ");
        EnumParticleTypes.lIllIllIIlIlIlII[55] = lIIIIllIIIlIlllII("ECgHFggDOxoDBQ==", "TzNFW");
        EnumParticleTypes.lIllIllIIlIlIlII[56] = lIIIIllIIIlIllIll("ZrlBUof0HTHUFS9fhUVSXw==", "OthEK");
        EnumParticleTypes.lIllIllIIlIlIlII[57] = lIIIIllIIIlIllIll("wzacmGtnE6JxR3sNWirQeQ==", "pTcad");
        EnumParticleTypes.lIllIllIIlIlIlII[58] = lIIIIllIIIlIlllIl("7atTwl7M9VOYNqrWxhkYkg==", "zrAwz");
        EnumParticleTypes.lIllIllIIlIlIlII[59] = lIIIIllIIIlIlllII("Kj4uGQciLTEI", "nlgIX");
        EnumParticleTypes.lIllIllIIlIlIlII[60] = lIIIIllIIIlIllIll("NOIUQ+UBBqxgGOwwcE1yuw==", "vBGfh");
        EnumParticleTypes.lIllIllIIlIlIlII[61] = lIIIIllIIIlIllIll("g5lEcYJoZ8SUClw4T0b5DQ==", "Kieal");
        EnumParticleTypes.lIllIllIIlIlIlII[62] = lIIIIllIIIlIlllIl("5azYZef1qhEz2YKfGNJh1Q==", "QtOel");
        EnumParticleTypes.lIllIllIIlIlIlII[63] = lIIIIllIIIlIllIII("jbsM0ZlvCB2YTMAp5nkRDg==", "bfEpK");
        EnumParticleTypes.lIllIllIIlIlIlII[64] = lIIIIllIIIlIllIll("3Wsd9aCvJSVNuFK9TgBQIQ==", "tJXkB");
        EnumParticleTypes.lIllIllIIlIlIlII[65] = lIIIIllIIIlIlllIl("2RNEZJWY1HzKWpP/mvnIow==", "FSUTM");
        EnumParticleTypes.lIllIllIIlIlIlII[66] = lIIIIllIIIlIlllII("LwUDMjIRDR8uKiABAQ==", "GdsBK");
        EnumParticleTypes.lIllIllIIlIlIlII[67] = lIIIIllIIIlIllIII("BXKd7Bl1HUzrWCXmnK70ig==", "kUudS");
        EnumParticleTypes.lIllIllIIlIlIlII[68] = lIIIIllIIIlIlllIl("hiNWBA54E7KbYO/nFWpmxQ==", "FzqpC");
        EnumParticleTypes.lIllIllIIlIlIlII[69] = lIIIIllIIIlIlllII("IykZBS8iNA8=", "WFnkN");
        EnumParticleTypes.lIllIllIIlIlIlII[70] = lIIIIllIIIlIlllII("Bh0RFQ==", "HREPV");
        EnumParticleTypes.lIllIllIIlIlIlII[71] = lIIIIllIIIlIlllIl("Yyv9xDqk3/cofWdUzeQSEg==", "enKCo");
        EnumParticleTypes.lIllIllIIlIlIlII[72] = lIIIIllIIIlIllIll("y9Z0npAstlc=", "SnDYV");
        EnumParticleTypes.lIllIllIIlIlIlII[73] = lIIIIllIIIlIlllII("EhUUJzcO", "BZFsv");
        EnumParticleTypes.lIllIllIIlIlIlII[74] = lIIIIllIIIlIlllII("JQcZBSU5", "uHKQd");
        EnumParticleTypes.lIllIllIIlIlIlII[75] = lIIIIllIIIlIlllIl("mdrqIvu4HMEBzqeahXoc1g==", "aGAAC");
        EnumParticleTypes.lIllIllIIlIlIlII[76] = lIIIIllIIIlIlllIl("iq5IFcoXKmFPK+tlphSpyjAtRjCdOjDu6mROitt0tw8=", "Lpvfr");
        EnumParticleTypes.lIllIllIIlIlIlII[77] = lIIIIllIIIlIllIII("2G3Gtjg3uRW9kgkGuOOvYf+nIZVxbCAE", "pIqQj");
        EnumParticleTypes.lIllIllIIlIlIlII[78] = lIIIIllIIIlIllIll("yvvb8ckmDCrwgangr+FWkOC3o6dfLcpD", "FfAia");
        EnumParticleTypes.lIllIllIIlIlIlII[79] = lIIIIllIIIlIlllII("JAADPgM=", "bLBsF");
        EnumParticleTypes.lIllIllIIlIlIlII[80] = lIIIIllIIIlIllIII("QrhmE9b9IWE=", "buvAQ");
        EnumParticleTypes.lIllIllIIlIlIlII[81] = lIIIIllIIIlIlllII("ISoOGCM=", "GFouF");
        EnumParticleTypes.lIllIllIIlIlIlII[82] = lIIIIllIIIlIlllII("OzgbOw==", "wyMzG");
        EnumParticleTypes.lIllIllIIlIlIlII[83] = lIIIIllIIIlIllIII("MHkXQherRVM=", "vDqfJ");
        EnumParticleTypes.lIllIllIIlIlIlII[84] = lIIIIllIIIlIllIII("MqzbP2c/WOQ=", "wFklL");
        EnumParticleTypes.lIllIllIIlIlIlII[85] = lIIIIllIIIlIlllII("CS0FMDkbJxo=", "ObJdj");
        EnumParticleTypes.lIllIllIIlIlIlII[86] = lIIIIllIIIlIlllII("AxwGGyURFhk=", "ESIOv");
        EnumParticleTypes.lIllIllIIlIlIlII[87] = lIIIIllIIIlIllIll("GT+xpPhqo5UgnA3chx0m7A==", "CjLvf");
        EnumParticleTypes.lIllIllIIlIlIlII[88] = lIIIIllIIIlIllIII("2O57FcZGC0M=", "RdvUr");
        EnumParticleTypes.lIllIllIIlIlIlII[89] = lIIIIllIIIlIlllII("CTscGSY=", "JwSLb");
        EnumParticleTypes.lIllIllIIlIlIlII[90] = lIIIIllIIIlIllIll("2B9m8S0srUY=", "Ejrmi");
        EnumParticleTypes.lIllIllIIlIlIlII[91] = lIIIIllIIIlIllIII("vonGAvB5otNN+w22yq7ZuA==", "bgcrH");
        EnumParticleTypes.lIllIllIIlIlIlII[92] = lIIIIllIIIlIlllII("IQcLHi48DAo=", "sBOMz");
        EnumParticleTypes.lIllIllIIlIlIlII[93] = lIIIIllIIIlIllIll("IrLI08EhDxI=", "kntMN");
        EnumParticleTypes.lIllIllIIlIlIlII[94] = lIIIIllIIIlIllIll("gw+yY4UHYppn4VdAWggIKA==", "DvYSD");
        EnumParticleTypes.lIllIllIIlIlIlII[95] = lIIIIllIIIlIllIII("dR3H1ImnRJV03jExjy3k1g==", "rKlut");
        EnumParticleTypes.lIllIllIIlIlIlII[96] = lIIIIllIIIlIllIll("gTCm51PHFbp1mo03Gf3uew==", "dnUPV");
        EnumParticleTypes.lIllIllIIlIlIlII[97] = lIIIIllIIIlIllIll("PlplYLQl/0rOK28QLWRwWw==", "kuHuL");
        EnumParticleTypes.lIllIllIIlIlIlII[98] = lIIIIllIIIlIllIII("kQRcO8nknmIZ5IY+l5e9Tg==", "PaldS");
        EnumParticleTypes.lIllIllIIlIlIlII[99] = lIIIIllIIIlIlllII("NDkGJxgvOB81Bw==", "GWiPk");
        EnumParticleTypes.lIllIllIIlIlIlII[100] = lIIIIllIIIlIllIII("WrlrVggLXyY=", "ZcKIH");
        EnumParticleTypes.lIllIllIIlIlIlII[101] = lIIIIllIIIlIllIII("+h50GsYiVVo=", "LlPBd");
        EnumParticleTypes.lIllIllIIlIlIlII[102] = lIIIIllIIIlIlllII("ICYmBRU=", "SJOhp");
        EnumParticleTypes.lIllIllIIlIlIlII[103] = lIIIIllIIIlIllIll("bPpRdaH6FT8=", "LVyzw");
        EnumParticleTypes.lIllIllIIlIlIlII[104] = lIIIIllIIIlIlllIl("BU57rYUWIURh2gn9EDq14w==", "xsHKa");
        EnumParticleTypes.lIllIllIIlIlIlII[105] = lIIIIllIIIlIlllII("DR0MJgc=", "exmTs");
        EnumParticleTypes.lIllIllIIlIlIlII[106] = lIIIIllIIIlIlllII("CzQkMCEMJw==", "Iuvbh");
        EnumParticleTypes.lIllIllIIlIlIlII[107] = lIIIIllIIIlIllIll("2XHDa+oTWco=", "mPhzD");
        EnumParticleTypes.lIllIllIIlIlIlII[108] = lIIIIllIIIlIllIll("2Ehqn3pihJY=", "wZLcE");
        EnumParticleTypes.lIllIllIIlIlIlII[109] = lIIIIllIIIlIlllII("IiMvFxEoJSsZBQ==", "kwjZN");
        EnumParticleTypes.lIllIllIIlIlIlII[110] = lIIIIllIIIlIllIll("IcCIk7TRax54wQUPVV1lKw==", "wfAbL");
        EnumParticleTypes.lIllIllIIlIlIlII[111] = lIIIIllIIIlIlllIl("7o02UOLn5jsev5mGBg94hA==", "IMkQy");
        EnumParticleTypes.lIllIllIIlIlIlII[112] = lIIIIllIIIlIllIII("5UNa8W9aTp32U6kKEK2BGA==", "Htwcj");
        EnumParticleTypes.lIllIllIIlIlIlII[113] = lIIIIllIIIlIllIII("+vWNTSbKPnszSJ6WH5USHw==", "tDcfV");
        EnumParticleTypes.lIllIllIIlIlIlII[114] = lIIIIllIIIlIllIII("WoLsSwqxJOqGNyZ+WUiwrw==", "IVXcv");
        EnumParticleTypes.lIllIllIIlIlIlII[115] = lIIIIllIIIlIllIll("MFntid23SF5POKrHZEHwJA==", "qmEqZ");
        EnumParticleTypes.lIllIllIIlIlIlII[116] = lIIIIllIIIlIllIll("w3pknFG5o3vftnH6XrfVQw==", "BGMri");
        EnumParticleTypes.lIllIllIIlIlIlII[117] = lIIIIllIIIlIllIll("qrMPH9s1ooeAKPPdxiu0YQ==", "WHqpe");
        EnumParticleTypes.lIllIllIIlIlIlII[118] = lIIIIllIIIlIllIII("uZmjoMMcHDro6Z4P5+Oogw==", "IHFTV");
        EnumParticleTypes.lIllIllIIlIlIlII[119] = lIIIIllIIIlIllIll("E1OUV75QxcLVxFfb0U9W0A==", "KSruA");
        EnumParticleTypes.lIllIllIIlIlIlII[120] = lIIIIllIIIlIlllII("EicMMzgTIQ==", "vUcCT");
        EnumParticleTypes.lIllIllIIlIlIlII[121] = lIIIIllIIIlIlllIl("YOgbEAoIQI4xUT4UQwHGZQ==", "NiOev");
        EnumParticleTypes.lIllIllIIlIlIlII[122] = lIIIIllIIIlIlllII("BDk3ABAZLDkI", "MmrMO");
        EnumParticleTypes.lIllIllIIlIlIlII[123] = lIIIIllIIIlIlllII("AhQKBw==", "vuabb");
        EnumParticleTypes.lIllIllIIlIlIlII[124] = lIIIIllIIIlIllIll("oe4nDQT8L6QGIG87bidRGQ==", "nIlzJ");
        EnumParticleTypes.lIllIllIIlIlIlII[125] = lIIIIllIIIlIllIII("m5E7NUFCeou/4UNYDSf0KQ==", "zuFyw");
        EnumParticleTypes.lIllIllIIlIlIlII[126] = lIIIIllIIIlIllIII("VYFNPuvpERf3YG9ykdmJBw==", "mWQbc");
        EnumParticleTypes.lIllIllIIlIlIlII[127] = lIIIIllIIIlIllIII("bZku7wGHnQI=", "HtHvu");
        EnumParticleTypes.lIllIllIIllllllI = null;
    }
    
    private static void lIIIIllIIlIIIlIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        EnumParticleTypes.lIllIllIIllllllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIIIllIIIlIllIll(final String s, final String s2) {
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
    
    private static String lIIIIllIIIlIlllII(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIIIIllIIIlIlllIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIIllIIIlIllIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
