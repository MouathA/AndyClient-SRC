package net.minecraft.potion;

import optifine.*;
import com.google.common.collect.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class PotionHelper
{
    public static final String field_77924_a;
    public static final String sugarEffect;
    public static final String ghastTearEffect;
    public static final String spiderEyeEffect;
    public static final String fermentedSpiderEyeEffect;
    public static final String speckledMelonEffect;
    public static final String blazePowderEffect;
    public static final String magmaCreamEffect;
    public static final String redstoneEffect;
    public static final String glowstoneEffect;
    public static final String gunpowderEffect;
    public static final String goldenCarrotEffect;
    public static final String field_151423_m;
    public static final String field_179538_n;
    private static final Map field_179539_o;
    private static final Map field_179540_p;
    private static final Map field_77925_n;
    private static final String[] potionPrefixes;
    private static final String[] lIlIIIIlllllIIIl;
    private static String[] lIlIIIlIIIIIIIlI;
    
    static {
        lllIlllIIllIlIII();
        lllIlllIIllIIlll();
        ghastTearEffect = PotionHelper.lIlIIIIlllllIIIl[0];
        field_77924_a = null;
        field_179539_o = Maps.newHashMap();
        field_179540_p = Maps.newHashMap();
        PotionHelper.field_179539_o.put(Potion.regeneration.getId(), PotionHelper.lIlIIIIlllllIIIl[1]);
        sugarEffect = PotionHelper.lIlIIIIlllllIIIl[2];
        PotionHelper.field_179539_o.put(Potion.moveSpeed.getId(), PotionHelper.lIlIIIIlllllIIIl[3]);
        magmaCreamEffect = PotionHelper.lIlIIIIlllllIIIl[4];
        PotionHelper.field_179539_o.put(Potion.fireResistance.getId(), PotionHelper.lIlIIIIlllllIIIl[5]);
        speckledMelonEffect = PotionHelper.lIlIIIIlllllIIIl[6];
        PotionHelper.field_179539_o.put(Potion.heal.getId(), PotionHelper.lIlIIIIlllllIIIl[7]);
        spiderEyeEffect = PotionHelper.lIlIIIIlllllIIIl[8];
        PotionHelper.field_179539_o.put(Potion.poison.getId(), PotionHelper.lIlIIIIlllllIIIl[9]);
        fermentedSpiderEyeEffect = PotionHelper.lIlIIIIlllllIIIl[10];
        PotionHelper.field_179539_o.put(Potion.weakness.getId(), PotionHelper.lIlIIIIlllllIIIl[11]);
        PotionHelper.field_179539_o.put(Potion.harm.getId(), PotionHelper.lIlIIIIlllllIIIl[12]);
        PotionHelper.field_179539_o.put(Potion.moveSlowdown.getId(), PotionHelper.lIlIIIIlllllIIIl[13]);
        blazePowderEffect = PotionHelper.lIlIIIIlllllIIIl[14];
        PotionHelper.field_179539_o.put(Potion.damageBoost.getId(), PotionHelper.lIlIIIIlllllIIIl[15]);
        goldenCarrotEffect = PotionHelper.lIlIIIIlllllIIIl[16];
        PotionHelper.field_179539_o.put(Potion.nightVision.getId(), PotionHelper.lIlIIIIlllllIIIl[17]);
        PotionHelper.field_179539_o.put(Potion.invisibility.getId(), PotionHelper.lIlIIIIlllllIIIl[18]);
        field_151423_m = PotionHelper.lIlIIIIlllllIIIl[19];
        PotionHelper.field_179539_o.put(Potion.waterBreathing.getId(), PotionHelper.lIlIIIIlllllIIIl[20]);
        field_179538_n = PotionHelper.lIlIIIIlllllIIIl[21];
        PotionHelper.field_179539_o.put(Potion.jump.getId(), PotionHelper.lIlIIIIlllllIIIl[22]);
        glowstoneEffect = PotionHelper.lIlIIIIlllllIIIl[23];
        PotionHelper.field_179540_p.put(Potion.moveSpeed.getId(), PotionHelper.lIlIIIIlllllIIIl[24]);
        PotionHelper.field_179540_p.put(Potion.digSpeed.getId(), PotionHelper.lIlIIIIlllllIIIl[25]);
        PotionHelper.field_179540_p.put(Potion.damageBoost.getId(), PotionHelper.lIlIIIIlllllIIIl[26]);
        PotionHelper.field_179540_p.put(Potion.regeneration.getId(), PotionHelper.lIlIIIIlllllIIIl[27]);
        PotionHelper.field_179540_p.put(Potion.harm.getId(), PotionHelper.lIlIIIIlllllIIIl[28]);
        PotionHelper.field_179540_p.put(Potion.heal.getId(), PotionHelper.lIlIIIIlllllIIIl[29]);
        PotionHelper.field_179540_p.put(Potion.resistance.getId(), PotionHelper.lIlIIIIlllllIIIl[30]);
        PotionHelper.field_179540_p.put(Potion.poison.getId(), PotionHelper.lIlIIIIlllllIIIl[31]);
        PotionHelper.field_179540_p.put(Potion.jump.getId(), PotionHelper.lIlIIIIlllllIIIl[32]);
        redstoneEffect = PotionHelper.lIlIIIIlllllIIIl[33];
        gunpowderEffect = PotionHelper.lIlIIIIlllllIIIl[34];
        field_77925_n = Maps.newHashMap();
        potionPrefixes = new String[] { PotionHelper.lIlIIIIlllllIIIl[35], PotionHelper.lIlIIIIlllllIIIl[36], PotionHelper.lIlIIIIlllllIIIl[37], PotionHelper.lIlIIIIlllllIIIl[38], PotionHelper.lIlIIIIlllllIIIl[39], PotionHelper.lIlIIIIlllllIIIl[40], PotionHelper.lIlIIIIlllllIIIl[41], PotionHelper.lIlIIIIlllllIIIl[42], PotionHelper.lIlIIIIlllllIIIl[43], PotionHelper.lIlIIIIlllllIIIl[44], PotionHelper.lIlIIIIlllllIIIl[45], PotionHelper.lIlIIIIlllllIIIl[46], PotionHelper.lIlIIIIlllllIIIl[47], PotionHelper.lIlIIIIlllllIIIl[48], PotionHelper.lIlIIIIlllllIIIl[49], PotionHelper.lIlIIIIlllllIIIl[50], PotionHelper.lIlIIIIlllllIIIl[51], PotionHelper.lIlIIIIlllllIIIl[52], PotionHelper.lIlIIIIlllllIIIl[53], PotionHelper.lIlIIIIlllllIIIl[54], PotionHelper.lIlIIIIlllllIIIl[55], PotionHelper.lIlIIIIlllllIIIl[56], PotionHelper.lIlIIIIlllllIIIl[57], PotionHelper.lIlIIIIlllllIIIl[58], PotionHelper.lIlIIIIlllllIIIl[59], PotionHelper.lIlIIIIlllllIIIl[60], PotionHelper.lIlIIIIlllllIIIl[61], PotionHelper.lIlIIIIlllllIIIl[62], PotionHelper.lIlIIIIlllllIIIl[63], PotionHelper.lIlIIIIlllllIIIl[64], PotionHelper.lIlIIIIlllllIIIl[65], PotionHelper.lIlIIIIlllllIIIl[66] };
    }
    
    public static boolean checkFlag(final int n, final int n2) {
        return (n & 1 << n2) != 0x0;
    }
    
    private static int isFlagSet(final int n, final int n2) {
        return checkFlag(n, n2) ? 1 : 0;
    }
    
    private static int isFlagUnset(final int n, final int n2) {
        return checkFlag(n, n2) ? 0 : 1;
    }
    
    public static int func_77909_a(final int n) {
        return func_77908_a(n, 5, 4, 3, 2, 1);
    }
    
    public static int calcPotionLiquidColor(final Collection collection) {
        int potionColor = 3694022;
        if (collection == null || collection.isEmpty()) {
            if (Config.isCustomColors()) {
                potionColor = CustomColors.getPotionColor(0, potionColor);
            }
            return potionColor;
        }
        float n = 0.0f;
        float n2 = 0.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        for (final PotionEffect potionEffect : collection) {
            if (potionEffect.func_180154_f()) {
                int n5 = Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();
                if (Config.isCustomColors()) {
                    n5 = CustomColors.getPotionColor(potionEffect.getPotionID(), n5);
                }
                for (int i = 0; i <= potionEffect.getAmplifier(); ++i) {
                    n += (n5 >> 16 & 0xFF) / 255.0f;
                    n2 += (n5 >> 8 & 0xFF) / 255.0f;
                    n3 += (n5 >> 0 & 0xFF) / 255.0f;
                    ++n4;
                }
            }
        }
        if (n4 == 0.0f) {
            return 0;
        }
        return (int)(n / n4 * 255.0f) << 16 | (int)(n2 / n4 * 255.0f) << 8 | (int)(n3 / n4 * 255.0f);
    }
    
    public static boolean func_82817_b(final Collection collection) {
        final Iterator<PotionEffect> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getIsAmbient()) {
                return false;
            }
        }
        return true;
    }
    
    public static int func_77915_a(final int n, final boolean b) {
        if (b) {
            return calcPotionLiquidColor(getPotionEffects(n, true));
        }
        if (PotionHelper.field_77925_n.containsKey(n)) {
            return PotionHelper.field_77925_n.get(n);
        }
        final int calcPotionLiquidColor = calcPotionLiquidColor(getPotionEffects(n, false));
        PotionHelper.field_77925_n.put(n, calcPotionLiquidColor);
        return calcPotionLiquidColor;
    }
    
    public static String func_77905_c(final int n) {
        return PotionHelper.potionPrefixes[func_77909_a(n)];
    }
    
    private static int func_77904_a(final boolean b, final boolean b2, final boolean b3, final int n, final int n2, final int n3, final int n4) {
        int n5 = 0;
        if (b) {
            n5 = isFlagUnset(n4, n2);
        }
        else if (n != -1) {
            if (n == 0 && countSetFlags(n4) == n2) {
                n5 = 1;
            }
            else if (n == 1 && countSetFlags(n4) > n2) {
                n5 = 1;
            }
            else if (n == 2 && countSetFlags(n4) < n2) {
                n5 = 1;
            }
        }
        else {
            n5 = isFlagSet(n4, n2);
        }
        if (b2) {
            n5 *= n3;
        }
        if (b3) {
            n5 *= -1;
        }
        return n5;
    }
    
    private static int countSetFlags(int i) {
        int n;
        for (n = 0; i > 0; i &= i - 1, ++n) {}
        return n;
    }
    
    private static int parsePotionEffects(final String s, final int n, final int n2, final int n3) {
        if (n >= s.length() || n2 < 0 || n >= n2) {
            return 0;
        }
        final int index = s.indexOf(124, n);
        if (index >= 0 && index < n2) {
            final int potionEffects = parsePotionEffects(s, n, index - 1, n3);
            if (potionEffects > 0) {
                return potionEffects;
            }
            final int potionEffects2 = parsePotionEffects(s, index + 1, n2, n3);
            return (potionEffects2 > 0) ? potionEffects2 : false;
        }
        else {
            final int index2 = s.indexOf(38, n);
            if (index2 < 0 || index2 >= n2) {
                int n4 = 0;
                boolean b = false;
                int n5 = 0;
                boolean b2 = false;
                boolean b3 = false;
                int n6 = -1;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                for (int i = n; i < n2; ++i) {
                    final char char1 = s.charAt(i);
                    if (char1 >= '0' && char1 <= '9') {
                        if (n4 != 0) {
                            n8 = char1 - '0';
                            b = true;
                        }
                        else {
                            n7 = n7 * 10 + (char1 - '0');
                            n5 = 1;
                        }
                    }
                    else if (char1 == '*') {
                        n4 = 1;
                    }
                    else if (char1 == '!') {
                        if (n5 != 0) {
                            n9 += func_77904_a(b2, b, b3, n6, n7, n8, n3);
                            b3 = false;
                            n4 = 0;
                            b = false;
                            n5 = 0;
                            n8 = 0;
                            n7 = 0;
                            n6 = -1;
                        }
                        b2 = true;
                    }
                    else if (char1 == '-') {
                        if (n5 != 0) {
                            n9 += func_77904_a(b2, b, b3, n6, n7, n8, n3);
                            b2 = false;
                            n4 = 0;
                            b = false;
                            n5 = 0;
                            n8 = 0;
                            n7 = 0;
                            n6 = -1;
                        }
                        b3 = true;
                    }
                    else if (char1 != '=' && char1 != '<' && char1 != '>') {
                        if (char1 == '+' && n5 != 0) {
                            n9 += func_77904_a(b2, b, b3, n6, n7, n8, n3);
                            b2 = false;
                            b3 = false;
                            n4 = 0;
                            b = false;
                            n5 = 0;
                            n8 = 0;
                            n7 = 0;
                            n6 = -1;
                        }
                    }
                    else {
                        if (n5 != 0) {
                            n9 += func_77904_a(b2, b, b3, n6, n7, n8, n3);
                            b2 = false;
                            b3 = false;
                            n4 = 0;
                            b = false;
                            n5 = 0;
                            n8 = 0;
                            n7 = 0;
                            n6 = -1;
                        }
                        if (char1 == '=') {
                            n6 = 0;
                        }
                        else if (char1 == '<') {
                            n6 = 2;
                        }
                        else if (char1 == '>') {
                            n6 = 1;
                        }
                    }
                }
                if (n5 != 0) {
                    n9 += func_77904_a(b2, b, b3, n6, n7, n8, n3);
                }
                return n9;
            }
            final int potionEffects3 = parsePotionEffects(s, n, index2 - 1, n3);
            if (potionEffects3 <= 0) {
                return 0;
            }
            final int potionEffects4 = parsePotionEffects(s, index2 + 1, n2, n3);
            return (potionEffects4 <= 0) ? 0 : ((potionEffects3 > potionEffects4) ? potionEffects3 : potionEffects4);
        }
    }
    
    public static List getPotionEffects(final int n, final boolean b) {
        ArrayList<PotionEffect> arrayList = null;
        for (final Potion potion : Potion.potionTypes) {
            if (potion != null && (!potion.isUsable() || b)) {
                final String s = PotionHelper.field_179539_o.get(potion.getId());
                if (s != null) {
                    final int potionEffects = parsePotionEffects(s, 0, s.length(), n);
                    if (potionEffects > 0) {
                        int potionEffects2 = 0;
                        final String s2 = PotionHelper.field_179540_p.get(potion.getId());
                        if (s2 != null) {
                            potionEffects2 = parsePotionEffects(s2, 0, s2.length(), n);
                            if (potionEffects2 < 0) {
                                potionEffects2 = 0;
                            }
                        }
                        int n2;
                        if (potion.isInstant()) {
                            n2 = 1;
                        }
                        else {
                            n2 = (int)Math.round((1200 * (potionEffects * 3 + (potionEffects - 1) * 2) >> potionEffects2) * potion.getEffectiveness());
                            if ((n & 0x4000) != 0x0) {
                                n2 = (int)Math.round(n2 * 0.75 + 0.5);
                            }
                        }
                        if (arrayList == null) {
                            arrayList = (ArrayList<PotionEffect>)Lists.newArrayList();
                        }
                        final PotionEffect potionEffect = new PotionEffect(potion.getId(), n2, potionEffects2);
                        if ((n & 0x4000) != 0x0) {
                            potionEffect.setSplashPotion(true);
                        }
                        arrayList.add(potionEffect);
                    }
                }
            }
        }
        return arrayList;
    }
    
    private static int brewBitOperations(int n, final int n2, final boolean b, final boolean b2, final boolean b3) {
        if (b3) {
            if (!checkFlag(n, n2)) {
                return 0;
            }
        }
        else if (b) {
            n &= ~(1 << n2);
        }
        else if (b2) {
            if ((n & 1 << n2) == 0x0) {
                n |= 1 << n2;
            }
            else {
                n &= ~(1 << n2);
            }
        }
        else {
            n |= 1 << n2;
        }
        return n;
    }
    
    public static int applyIngredient(int n, final String s) {
        final int n2 = 0;
        final int length = s.length();
        int n3 = 0;
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        int n4 = 0;
        for (int i = n2; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '0' && char1 <= '9') {
                n4 = n4 * 10 + (char1 - '0');
                n3 = 1;
            }
            else if (char1 == '!') {
                if (n3 != 0) {
                    n = brewBitOperations(n, n4, b2, b, b3);
                    b3 = false;
                    b2 = false;
                    n3 = 0;
                    n4 = 0;
                }
                b = true;
            }
            else if (char1 == '-') {
                if (n3 != 0) {
                    n = brewBitOperations(n, n4, b2, b, b3);
                    b3 = false;
                    b = false;
                    n3 = 0;
                    n4 = 0;
                }
                b2 = true;
            }
            else if (char1 == '+') {
                if (n3 != 0) {
                    n = brewBitOperations(n, n4, b2, b, b3);
                    b3 = false;
                    b = false;
                    b2 = false;
                    n3 = 0;
                    n4 = 0;
                }
            }
            else if (char1 == '&') {
                if (n3 != 0) {
                    n = brewBitOperations(n, n4, b2, b, b3);
                    b = false;
                    b2 = false;
                    n3 = 0;
                    n4 = 0;
                }
                b3 = true;
            }
        }
        if (n3 != 0) {
            n = brewBitOperations(n, n4, b2, b, b3);
        }
        return n & 0x7FFF;
    }
    
    public static int func_77908_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return (checkFlag(n, n2) ? 16 : 0) | (checkFlag(n, n3) ? 8 : 0) | (checkFlag(n, n4) ? 4 : 0) | (checkFlag(n, n5) ? 2 : 0) | (checkFlag(n, n6) ? 1 : 0);
    }
    
    public static void clearPotionColorCache() {
        PotionHelper.field_77925_n.clear();
    }
    
    private static void lllIlllIIllIIlll() {
        (lIlIIIIlllllIIIl = new String[67])[0] = lllIlllIIlIlIIIl(PotionHelper.lIlIIIlIIIIIIIlI[0], PotionHelper.lIlIIIlIIIIIIIlI[1]);
        PotionHelper.lIlIIIIlllllIIIl[1] = lllIlllIIlIlIIlI(PotionHelper.lIlIIIlIIIIIIIlI[2], PotionHelper.lIlIIIlIIIIIIIlI[3]);
        PotionHelper.lIlIIIIlllllIIIl[2] = lllIlllIIlIlIIlI(PotionHelper.lIlIIIlIIIIIIIlI[4], PotionHelper.lIlIIIlIIIIIIIlI[5]);
        PotionHelper.lIlIIIIlllllIIIl[3] = lllIlllIIlIlIIIl(PotionHelper.lIlIIIlIIIIIIIlI[6], PotionHelper.lIlIIIlIIIIIIIlI[7]);
        PotionHelper.lIlIIIIlllllIIIl[4] = lllIlllIIlIlIIll(PotionHelper.lIlIIIlIIIIIIIlI[8], PotionHelper.lIlIIIlIIIIIIIlI[9]);
        PotionHelper.lIlIIIIlllllIIIl[5] = lllIlllIIlIlIlIl(PotionHelper.lIlIIIlIIIIIIIlI[10], PotionHelper.lIlIIIlIIIIIIIlI[11]);
        PotionHelper.lIlIIIIlllllIIIl[6] = lllIlllIIlIlIlIl(PotionHelper.lIlIIIlIIIIIIIlI[12], PotionHelper.lIlIIIlIIIIIIIlI[13]);
        PotionHelper.lIlIIIIlllllIIIl[7] = lllIlllIIlIlIlIl(PotionHelper.lIlIIIlIIIIIIIlI[14], PotionHelper.lIlIIIlIIIIIIIlI[15]);
        PotionHelper.lIlIIIIlllllIIIl[8] = lllIlllIIlIlIIIl(PotionHelper.lIlIIIlIIIIIIIlI[16], PotionHelper.lIlIIIlIIIIIIIlI[17]);
        PotionHelper.lIlIIIIlllllIIIl[9] = lllIlllIIlIlIIlI(PotionHelper.lIlIIIlIIIIIIIlI[18], PotionHelper.lIlIIIlIIIIIIIlI[19]);
        PotionHelper.lIlIIIIlllllIIIl[10] = lllIlllIIlIlIIll(PotionHelper.lIlIIIlIIIIIIIlI[20], PotionHelper.lIlIIIlIIIIIIIlI[21]);
        PotionHelper.lIlIIIIlllllIIIl[11] = lllIlllIIlIlIIIl(PotionHelper.lIlIIIlIIIIIIIlI[22], PotionHelper.lIlIIIlIIIIIIIlI[23]);
        PotionHelper.lIlIIIIlllllIIIl[12] = lllIlllIIlIlIIIl(PotionHelper.lIlIIIlIIIIIIIlI[24], PotionHelper.lIlIIIlIIIIIIIlI[25]);
        PotionHelper.lIlIIIIlllllIIIl[13] = lllIlllIIlIlIlIl(PotionHelper.lIlIIIlIIIIIIIlI[26], PotionHelper.lIlIIIlIIIIIIIlI[27]);
        PotionHelper.lIlIIIIlllllIIIl[14] = lllIlllIIlIlIIll("Hyky/yhMxMBpvbPe6faPIg==", "djHON");
        PotionHelper.lIlIIIIlllllIIIl[15] = lllIlllIIlIlIlIl("n5Uf+SPkqPRy8lWQ+lvCB3HQdlOvshQC", "lWKLO");
        PotionHelper.lIlIIIIlllllIIIl[16] = lllIlllIIlIlIIll("5s0IXMUtc4xBejueGcqAEg==", "pWLcY");
        PotionHelper.lIlIIIIlllllIIIl[17] = lllIlllIIlIlIIll("flU/nqqlkc8OqVCXJLwbdJdbZFKUW7sl", "VDOBz");
        PotionHelper.lIlIIIIlllllIIIl[18] = lllIlllIIlIlIIIl("T0Rsa29fVGptfU5SbH5vSFR+Znk=", "ntLMO");
        PotionHelper.lIlIIIIlllllIIIl[19] = lllIlllIIlIlIIlI("JuBpSHp3cxdSy2CoFPW6VA==", "paVlt");
        PotionHelper.lIlIIIIlllllIIIl[20] = lllIlllIIlIlIIIl("QlN+Y0VDU35jVlJVeHBEVFNqaFI=", "rsXCd");
        PotionHelper.lIlIIIIlllllIIIl[21] = lllIlllIIlIlIlIl("WbF24P3rsgEfiFgC87tVjg==", "dJLEG");
        PotionHelper.lIlIIIIlllllIIIl[22] = lllIlllIIlIlIIll("hxfAnN7SVy+2L1L035SxhA==", "xbwVy");
        PotionHelper.lIlIIIIlllllIIIl[23] = lllIlllIIlIlIIIl("ZkBFQE96", "Muhvb");
        PotionHelper.lIlIIIIlllllIIIl[24] = lllIlllIIlIlIIlI("O73tXrLtUyxfLwG+a3YQfg==", "vBtcG");
        PotionHelper.lIlIIIIlllllIIIl[25] = lllIlllIIlIlIIlI("QIwu4exfr/SGaInTQ9+/Sw==", "ZrDtr");
        PotionHelper.lIlIIIIlllllIIIl[26] = lllIlllIIlIlIlIl("IeQRtBZ5r0U=", "BPMnd");
        PotionHelper.lIlIIIIlllllIIIl[27] = lllIlllIIlIlIIlI("DtjyFa4KTG15mYEbk68Mxg==", "VSwPd");
        PotionHelper.lIlIIIIlllllIIIl[28] = lllIlllIIlIlIIll("ANaFHFoSj+k=", "QZHcE");
        PotionHelper.lIlIIIIlllllIIIl[29] = lllIlllIIlIlIIll("tWvLy8+lWQ4=", "IRhXi");
        PotionHelper.lIlIIIIlllllIIIl[30] = lllIlllIIlIlIIIl("Xg==", "kBIJg");
        PotionHelper.lIlIIIIlllllIIIl[31] = lllIlllIIlIlIIlI("BSXCo+gC++JdTWd8XX85cw==", "yxEqJ");
        PotionHelper.lIlIIIIlllllIIIl[32] = lllIlllIIlIlIIlI("nEzSTwuJrs+E74oTJYx9Jg==", "jTuQS");
        PotionHelper.lIlIIIIlllllIIIl[33] = lllIlllIIlIlIIlI("hzKdaQxG/3aEG/8oDWAWIQ==", "wqXqF");
        PotionHelper.lIlIIIIlllllIIIl[34] = lllIlllIIlIlIlIl("y60NA0SN3av+L3tnKaV7Dg==", "ykbdo");
        PotionHelper.lIlIIIIlllllIIIl[35] = lllIlllIIlIlIIll("6+5ekEL/xMDuO67Jk9k53yY4kr87ZU3E", "ScveX");
        PotionHelper.lIlIIIIlllllIIIl[36] = lllIlllIIlIlIIll("mohuTmIwLiZIo7v/3gtDBF8qIkUdgs40IoCWE8VbffE=", "rMGBZ");
        PotionHelper.lIlIIIIlllllIIIl[37] = lllIlllIIlIlIlIl("Dd2ihvvC34L2PxTsXodx3zPoS7p3j4C8", "ulPMl");
        PotionHelper.lIlIIIIlllllIIIl[38] = lllIlllIIlIlIIlI("6yYjuLOx5Rm6P8dP+ajdiL7ihgnVp27rWVIpgLKzvc0=", "QhXaM");
        PotionHelper.lIlIIIIlllllIIIl[39] = lllIlllIIlIlIIll("kQKWP8Ipzpibkw7fffv+U6Sq8gOzjEE9", "BxUum");
        PotionHelper.lIlIIIIlllllIIIl[40] = lllIlllIIlIlIIll("DoRgNopzhpf+SUcGxAewZ4DieykRjZ77", "GijvX");
        PotionHelper.lIlIIIIlllllIIIl[41] = lllIlllIIlIlIIlI("mpw1+9+aVgP/AwbFF0rUh+QI0fejAimvqz6q/b5TqmM=", "vUUfe");
        PotionHelper.lIlIIIIlllllIIIl[42] = lllIlllIIlIlIIll("Q6qGxG1t0L22Ltw247vgqJZkB3k0KzbW", "iTvVN");
        PotionHelper.lIlIIIIlllllIIIl[43] = lllIlllIIlIlIIIl("KBsGOBY2WgIjHD4dCn8YLx8FMAs8", "XtrQy");
        PotionHelper.lIlIIIIlllllIIIl[44] = lllIlllIIlIlIIll("MKbc3Ryh+cuRpMTktPXt1YCcs/vuxX6s", "KyvJP");
        PotionHelper.lIlIIIIlllllIIIl[45] = lllIlllIIlIlIIIl("EiY9KhwMZzkxFgQgMW0RFyUiOg==", "bIICs");
        PotionHelper.lIlIIIIlllllIIIl[46] = lllIlllIIlIlIIIl("ISI8Gzg/YzgAMjckMFw1JCMvHj4/Kg==", "QMHrW");
        PotionHelper.lIlIIIIlllllIIIl[47] = lllIlllIIlIlIIlI("U7XJHyJWhA56ziqzQ+30C9oqfJ7gbTrqymcCpytbcy8=", "OMFPH");
        PotionHelper.lIlIIIIlllllIIIl[48] = lllIlllIIlIlIlIl("Qv9igHfn8NZh0pCzpRse22Ckl2enqlR1", "OpgGw");
        PotionHelper.lIlIIIIlllllIIIl[49] = lllIlllIIlIlIlIl("RFWm0jb3JJHsd07dkzSGTtPMtvApFxgu", "eFWwh");
        PotionHelper.lIlIIIIlllllIIIl[50] = lllIlllIIlIlIIIl("JB42LwA6XzI0CjIYOmgLMRMtKA49Aw==", "TqBFo");
        PotionHelper.lIlIIIIlllllIIIl[51] = lllIlllIIlIlIIIl("GjoEGA4EewADBAw8CF8VAjwTGg==", "jUpqa");
        PotionHelper.lIlIIIIlllllIIIl[52] = lllIlllIIlIlIIlI("j79w4ls4CUk1UJtexZtsaLXotp4HaMN1Go+ZZNuDyXU=", "CERjc");
        PotionHelper.lIlIIIIlllllIIIl[53] = lllIlllIIlIlIIll("EEnW//2sGS5ZF+yFP/5DnNKgEGXli3+t", "xqOmt");
        PotionHelper.lIlIIIIlllllIIIl[54] = lllIlllIIlIlIlIl("MOim/AOKqrjbBHOjEXWQhO/uSnTqUAI/", "QuWTs");
        PotionHelper.lIlIIIIlllllIIIl[55] = lllIlllIIlIlIIlI("mt9vysxS916pNEj7ymhK3PLLoRRugVB3LFuKLWX3W3E=", "mQLmf");
        PotionHelper.lIlIIIIlllllIIIl[56] = lllIlllIIlIlIIll("54bIqe1bKtLKKPrzaU2sgmgzjQpKmnzQ", "yCpNy");
        PotionHelper.lIlIIIIlllllIIIl[57] = lllIlllIIlIlIlIl("GepaFVdED/g5umyPGCMmP3Mo5QHSRWaL", "FxDAg");
        PotionHelper.lIlIIIIlllllIIIl[58] = lllIlllIIlIlIlIl("BRFejSFKmSf7aDBMb1JIQRr8C7MfCsdq", "Tgrbn");
        PotionHelper.lIlIIIIlllllIIIl[59] = lllIlllIIlIlIIlI("Bx6LzM8V6NjkJj1bdOGr3HLp7lWpGhfZiGJQc+5o5+4=", "mUVZk");
        PotionHelper.lIlIIIIlllllIIIl[60] = lllIlllIIlIlIIll("IkX56maDnYTtXPmLSgQogYFstMMa+wVL", "gpSUg");
        PotionHelper.lIlIIIIlllllIIIl[61] = lllIlllIIlIlIlIl("pPPxhCnhIH0RVuNeihlYTT4Q3yGjJ+Ig", "pOiPc");
        PotionHelper.lIlIIIIlllllIIIl[62] = lllIlllIIlIlIIIl("Pgw7KxUgTT8wHygKN2wILw0k", "NcOBz");
        PotionHelper.lIlIIIIlllllIIIl[63] = lllIlllIIlIlIIlI("YzSOOQyIxZSNcFvm6lgL6BoZACyGv7zprFaHhxaBm6I=", "WnhDy");
        PotionHelper.lIlIIIIlllllIIIl[64] = lllIlllIIlIlIlIl("8ONt/XIsWryDGG+ym1ew9Ill5bet4HJl", "nlcwB");
        PotionHelper.lIlIIIIlllllIIIl[65] = lllIlllIIlIlIIlI("AX0A3ECRP2uSH/BdtBTPqMa2+X0jjoqrd60thEfcoxU=", "svktB");
        PotionHelper.lIlIIIIlllllIIIl[66] = lllIlllIIlIlIlIl("zJhsc278Y9elsLtSNhzPijPAXh0tYbgF", "WUjNA");
        PotionHelper.lIlIIIlIIIIIIIlI = null;
    }
    
    private static void lllIlllIIllIlIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        PotionHelper.lIlIIIlIIIIIIIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllIlllIIlIlIIlI(final String s, final String s2) {
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
    
    private static String lllIlllIIlIlIIIl(String s, final String s2) {
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
    
    private static String lllIlllIIlIlIIll(final String s, final String s2) {
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
    
    private static String lllIlllIIlIlIlIl(final String s, final String s2) {
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
