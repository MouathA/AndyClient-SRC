package net.minecraft.command;

import net.minecraft.scoreboard.*;
import net.minecraft.nbt.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class CommandResultStats
{
    private static final int field_179676_a;
    private static final String[] field_179674_b;
    private String[] field_179675_c;
    private String[] field_179673_d;
    private static final String __OBFID;
    private static final String[] lllIllIIIIllIlI;
    private static String[] lllIllIIIIlllII;
    
    static {
        lIlIlllIIIIllIlI();
        lIlIlllIIIIllIIl();
        __OBFID = CommandResultStats.lllIllIIIIllIlI[0];
        field_179676_a = Type.values().length;
        field_179674_b = new String[CommandResultStats.field_179676_a];
    }
    
    public CommandResultStats() {
        this.field_179675_c = CommandResultStats.field_179674_b;
        this.field_179673_d = CommandResultStats.field_179674_b;
    }
    
    public void func_179672_a(final ICommandSender commandSender, final Type type, final int scorePoints) {
        final String s = this.field_179675_c[type.func_179636_a()];
        if (s != null) {
            String func_175758_e;
            try {
                func_175758_e = CommandBase.func_175758_e(commandSender, s);
            }
            catch (EntityNotFoundException ex) {
                return;
            }
            final String s2 = this.field_179673_d[type.func_179636_a()];
            if (s2 != null) {
                final Scoreboard scoreboard = commandSender.getEntityWorld().getScoreboard();
                final ScoreObjective objective = scoreboard.getObjective(s2);
                if (objective != null && scoreboard.func_178819_b(func_175758_e, objective)) {
                    scoreboard.getValueFromObjective(func_175758_e, objective).setScorePoints(scorePoints);
                }
            }
        }
    }
    
    public void func_179668_a(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(CommandResultStats.lllIllIIIIllIlI[1], 10)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(CommandResultStats.lllIllIIIIllIlI[2]);
            for (final Type type : Type.values()) {
                final String string = String.valueOf(type.func_179637_b()) + CommandResultStats.lllIllIIIIllIlI[3];
                final String string2 = String.valueOf(type.func_179637_b()) + CommandResultStats.lllIllIIIIllIlI[4];
                if (compoundTag.hasKey(string, 8) && compoundTag.hasKey(string2, 8)) {
                    func_179667_a(this, type, compoundTag.getString(string), compoundTag.getString(string2));
                }
            }
        }
    }
    
    public void func_179670_b(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        for (final Type type : Type.values()) {
            final String s = this.field_179675_c[type.func_179636_a()];
            final String s2 = this.field_179673_d[type.func_179636_a()];
            if (s != null && s2 != null) {
                nbtTagCompound2.setString(String.valueOf(type.func_179637_b()) + CommandResultStats.lllIllIIIIllIlI[5], s);
                nbtTagCompound2.setString(String.valueOf(type.func_179637_b()) + CommandResultStats.lllIllIIIIllIlI[6], s2);
            }
        }
        if (!nbtTagCompound2.hasNoTags()) {
            nbtTagCompound.setTag(CommandResultStats.lllIllIIIIllIlI[7], nbtTagCompound2);
        }
    }
    
    public static void func_179667_a(final CommandResultStats commandResultStats, final Type type, final String s, final String s2) {
        if (s != null && s.length() != 0 && s2 != null && s2.length() != 0) {
            if (commandResultStats.field_179675_c == CommandResultStats.field_179674_b || commandResultStats.field_179673_d == CommandResultStats.field_179674_b) {
                commandResultStats.field_179675_c = new String[CommandResultStats.field_179676_a];
                commandResultStats.field_179673_d = new String[CommandResultStats.field_179676_a];
            }
            commandResultStats.field_179675_c[type.func_179636_a()] = s;
            commandResultStats.field_179673_d[type.func_179636_a()] = s2;
        }
        else {
            func_179669_a(commandResultStats, type);
        }
    }
    
    private static void func_179669_a(final CommandResultStats commandResultStats, final Type type) {
        if (commandResultStats.field_179675_c != CommandResultStats.field_179674_b && commandResultStats.field_179673_d != CommandResultStats.field_179674_b) {
            commandResultStats.field_179675_c[type.func_179636_a()] = null;
            commandResultStats.field_179673_d[type.func_179636_a()] = null;
            boolean b = true;
            for (final Type type2 : Type.values()) {
                if (commandResultStats.field_179675_c[type2.func_179636_a()] != null && commandResultStats.field_179673_d[type2.func_179636_a()] != null) {
                    b = false;
                    break;
                }
            }
            if (b) {
                commandResultStats.field_179675_c = CommandResultStats.field_179674_b;
                commandResultStats.field_179673_d = CommandResultStats.field_179674_b;
            }
        }
    }
    
    public void func_179671_a(final CommandResultStats commandResultStats) {
        for (final Type type : Type.values()) {
            func_179667_a(this, type, commandResultStats.field_179675_c[type.func_179636_a()], commandResultStats.field_179673_d[type.func_179636_a()]);
        }
    }
    
    private static void lIlIlllIIIIllIIl() {
        (lllIllIIIIllIlI = new String[8])[0] = lIlIlllIIIIlIIlI(CommandResultStats.lllIllIIIIlllII[0], CommandResultStats.lllIllIIIIlllII[1]);
        CommandResultStats.lllIllIIIIllIlI[1] = lIlIlllIIIIlIIll(CommandResultStats.lllIllIIIIlllII[2], CommandResultStats.lllIllIIIIlllII[3]);
        CommandResultStats.lllIllIIIIllIlI[2] = lIlIlllIIIIlIlII(CommandResultStats.lllIllIIIIlllII[4], CommandResultStats.lllIllIIIIlllII[5]);
        CommandResultStats.lllIllIIIIllIlI[3] = lIlIlllIIIIlIlII(CommandResultStats.lllIllIIIIlllII[6], CommandResultStats.lllIllIIIIlllII[7]);
        CommandResultStats.lllIllIIIIllIlI[4] = lIlIlllIIIIlIIlI(CommandResultStats.lllIllIIIIlllII[8], CommandResultStats.lllIllIIIIlllII[9]);
        CommandResultStats.lllIllIIIIllIlI[5] = lIlIlllIIIIlIIll(CommandResultStats.lllIllIIIIlllII[10], CommandResultStats.lllIllIIIIlllII[11]);
        CommandResultStats.lllIllIIIIllIlI[6] = lIlIlllIIIIlIIll(CommandResultStats.lllIllIIIIlllII[12], CommandResultStats.lllIllIIIIlllII[13]);
        CommandResultStats.lllIllIIIIllIlI[7] = lIlIlllIIIIlIIlI(CommandResultStats.lllIllIIIIlllII[14], CommandResultStats.lllIllIIIIlllII[15]);
        CommandResultStats.lllIllIIIIlllII = null;
    }
    
    private static void lIlIlllIIIIllIlI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        CommandResultStats.lllIllIIIIlllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIlIlllIIIIlIIlI(final String s, final String s2) {
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
    
    private static String lIlIlllIIIIlIlII(final String s, final String s2) {
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
    
    private static String lIlIlllIIIIlIIll(final String s, final String s2) {
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
    
    public enum Type
    {
        SUCCESS_COUNT("SUCCESS_COUNT", 0, "SUCCESS_COUNT", 0, 0, "SuccessCount"), 
        AFFECTED_BLOCKS("AFFECTED_BLOCKS", 1, "AFFECTED_BLOCKS", 1, 1, "AffectedBlocks"), 
        AFFECTED_ENTITIES("AFFECTED_ENTITIES", 2, "AFFECTED_ENTITIES", 2, 2, "AffectedEntities"), 
        AFFECTED_ITEMS("AFFECTED_ITEMS", 3, "AFFECTED_ITEMS", 3, 3, "AffectedItems"), 
        QUERY_RESULT("QUERY_RESULT", 4, "QUERY_RESULT", 4, 4, "QueryResult");
        
        final int field_179639_f;
        final String field_179640_g;
        private static final Type[] $VALUES;
        private static final String __OBFID;
        private static final Type[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002363";
            ENUM$VALUES = new Type[] { Type.SUCCESS_COUNT, Type.AFFECTED_BLOCKS, Type.AFFECTED_ENTITIES, Type.AFFECTED_ITEMS, Type.QUERY_RESULT };
            $VALUES = new Type[] { Type.SUCCESS_COUNT, Type.AFFECTED_BLOCKS, Type.AFFECTED_ENTITIES, Type.AFFECTED_ITEMS, Type.QUERY_RESULT };
        }
        
        private Type(final String s, final int n, final String s2, final int n2, final int field_179639_f, final String field_179640_g) {
            this.field_179639_f = field_179639_f;
            this.field_179640_g = field_179640_g;
        }
        
        public int func_179636_a() {
            return this.field_179639_f;
        }
        
        public String func_179637_b() {
            return this.field_179640_g;
        }
        
        public static String[] func_179634_c() {
            final String[] array = new String[values().length];
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = type.func_179637_b();
                int n3 = 0;
                ++n3;
            }
            return array;
        }
        
        public static Type func_179635_a(final String s) {
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                if (type.func_179637_b().equals(s)) {
                    return type;
                }
                int n = 0;
                ++n;
            }
            return null;
        }
    }
}
