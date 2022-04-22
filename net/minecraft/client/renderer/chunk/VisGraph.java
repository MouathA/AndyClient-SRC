package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import optifine.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class VisGraph
{
    private static final int field_178616_a;
    private static final int field_178614_b;
    private static final int field_178615_c;
    private final BitSet field_178612_d;
    private static final int[] field_178613_e;
    private int field_178611_f;
    private static final String __OBFID;
    private static final String[] lIIlIIlIllllIIll;
    private static String[] lIIlIIlIllllIlII;
    
    static {
        llIllIIIlIlIIIIl();
        llIllIIIlIlIIIII();
        __OBFID = VisGraph.lIIlIIlIllllIIll[0];
        field_178616_a = (int)Math.pow(16.0, 0.0);
        field_178614_b = (int)Math.pow(16.0, 1.0);
        field_178615_c = (int)Math.pow(16.0, 2.0);
        field_178613_e = new int[1352];
        int n = 0;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
                        VisGraph.field_178613_e[n++] = func_178605_a(i, j, k);
                    }
                }
            }
        }
    }
    
    public VisGraph() {
        this.field_178612_d = new BitSet(4096);
        this.field_178611_f = 4096;
    }
    
    public void func_178606_a(final BlockPos blockPos) {
        this.field_178612_d.set(func_178608_c(blockPos), true);
        --this.field_178611_f;
    }
    
    private static int func_178608_c(final BlockPos blockPos) {
        return func_178605_a(blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF);
    }
    
    private static int func_178605_a(final int n, final int n2, final int n3) {
        return n << 0 | n2 << 8 | n3 << 4;
    }
    
    public SetVisibility func_178607_a() {
        final SetVisibility setVisibility = new SetVisibility();
        if (4096 - this.field_178611_f < 256) {
            setVisibility.func_178618_a(true);
        }
        else if (this.field_178611_f == 0) {
            setVisibility.func_178618_a(false);
        }
        else {
            for (final int n : VisGraph.field_178613_e) {
                if (!this.field_178612_d.get(n)) {
                    setVisibility.func_178620_a(this.func_178604_a(n));
                }
            }
        }
        return setVisibility;
    }
    
    public Set func_178609_b(final BlockPos blockPos) {
        return this.func_178604_a(func_178608_c(blockPos));
    }
    
    private Set func_178604_a(final int n) {
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        final ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>(384);
        arrayDeque.add(IntegerCache.valueOf(n));
        this.field_178612_d.set(n, true);
        while (!arrayDeque.isEmpty()) {
            final int intValue = arrayDeque.poll();
            this.func_178610_a(intValue, none);
            final EnumFacing[] values = EnumFacing.VALUES;
            for (int length = values.length, i = 0; i < length; ++i) {
                final int func_178603_a = this.func_178603_a(intValue, values[i]);
                if (func_178603_a >= 0 && !this.field_178612_d.get(func_178603_a)) {
                    this.field_178612_d.set(func_178603_a, true);
                    arrayDeque.add(IntegerCache.valueOf(func_178603_a));
                }
            }
        }
        return none;
    }
    
    private void func_178610_a(final int n, final Set set) {
        final int n2 = n >> 0 & 0xF;
        if (n2 == 0) {
            set.add(EnumFacing.WEST);
        }
        else if (n2 == 15) {
            set.add(EnumFacing.EAST);
        }
        final int n3 = n >> 8 & 0xF;
        if (n3 == 0) {
            set.add(EnumFacing.DOWN);
        }
        else if (n3 == 15) {
            set.add(EnumFacing.UP);
        }
        final int n4 = n >> 4 & 0xF;
        if (n4 == 0) {
            set.add(EnumFacing.NORTH);
        }
        else if (n4 == 15) {
            set.add(EnumFacing.SOUTH);
        }
    }
    
    private int func_178603_a(final int n, final EnumFacing enumFacing) {
        switch (SwitchEnumFacing.field_178617_a[enumFacing.ordinal()]) {
            case 1: {
                if ((n >> 8 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178615_c;
            }
            case 2: {
                if ((n >> 8 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178615_c;
            }
            case 3: {
                if ((n >> 4 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178614_b;
            }
            case 4: {
                if ((n >> 4 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178614_b;
            }
            case 5: {
                if ((n >> 0 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178616_a;
            }
            case 6: {
                if ((n >> 0 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178616_a;
            }
            default: {
                return -1;
            }
        }
    }
    
    private static void llIllIIIlIlIIIII() {
        (lIIlIIlIllllIIll = new String[1])[0] = llIllIIIlIIlllll(VisGraph.lIIlIIlIllllIlII[0], VisGraph.lIIlIIlIllllIlII[1]);
        VisGraph.lIIlIIlIllllIlII = null;
    }
    
    private static void llIllIIIlIlIIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        VisGraph.lIIlIIlIllllIlII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llIllIIIlIIlllll(final String s, final String s2) {
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
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178617_a;
        private static final String __OBFID;
        private static final String[] lIlIIlIlIIllIIIl;
        private static String[] lIlIIlIlIIllIIll;
        
        static {
            llllIIllIlIlIllI();
            llllIIllIlIlIlIl();
            __OBFID = SwitchEnumFacing.lIlIIlIlIIllIIIl[0];
            field_178617_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178617_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void llllIIllIlIlIlIl() {
            (lIlIIlIlIIllIIIl = new String[1])[0] = llllIIllIlIlIlII(SwitchEnumFacing.lIlIIlIlIIllIIll[0], SwitchEnumFacing.lIlIIlIlIIllIIll[1]);
            SwitchEnumFacing.lIlIIlIlIIllIIll = null;
        }
        
        private static void llllIIllIlIlIllI() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIlIIlIlIIllIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llllIIllIlIlIlII(final String s, final String s2) {
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
    }
}
