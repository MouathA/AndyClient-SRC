package net.minecraft.util;

import java.util.regex.*;
import com.google.common.collect.*;
import java.util.*;

public enum EnumChatFormatting
{
    BLACK("BLACK", 0, "BLACK", 0, "BLACK", '0', 0), 
    DARK_BLUE("DARK_BLUE", 1, "DARK_BLUE", 1, "DARK_BLUE", '1', 1), 
    DARK_GREEN("DARK_GREEN", 2, "DARK_GREEN", 2, "DARK_GREEN", '2', 2), 
    DARK_AQUA("DARK_AQUA", 3, "DARK_AQUA", 3, "DARK_AQUA", '3', 3), 
    DARK_RED("DARK_RED", 4, "DARK_RED", 4, "DARK_RED", '4', 4), 
    DARK_PURPLE("DARK_PURPLE", 5, "DARK_PURPLE", 5, "DARK_PURPLE", '5', 5), 
    GOLD("GOLD", 6, "GOLD", 6, "GOLD", '6', 6), 
    GRAY("GRAY", 7, "GRAY", 7, "GRAY", '7', 7), 
    DARK_GRAY("DARK_GRAY", 8, "DARK_GRAY", 8, "DARK_GRAY", '8', 8), 
    BLUE("BLUE", 9, "BLUE", 9, "BLUE", '9', 9), 
    GREEN("GREEN", 10, "GREEN", 10, "GREEN", 'a', 10), 
    AQUA("AQUA", 11, "AQUA", 11, "AQUA", 'b', 11), 
    RED("RED", 12, "RED", 12, "RED", 'c', 12), 
    LIGHT_PURPLE("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13), 
    YELLOW("YELLOW", 14, "YELLOW", 14, "YELLOW", 'e', 14), 
    WHITE("WHITE", 15, "WHITE", 15, "WHITE", 'f', 15), 
    OBFUSCATED("OBFUSCATED", 16, "OBFUSCATED", 16, "OBFUSCATED", 'k', true), 
    BOLD("BOLD", 17, "BOLD", 17, "BOLD", 'l', true), 
    STRIKETHROUGH("STRIKETHROUGH", 18, "STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true), 
    UNDERLINE("UNDERLINE", 19, "UNDERLINE", 19, "UNDERLINE", 'n', true), 
    ITALIC("ITALIC", 20, "ITALIC", 20, "ITALIC", 'o', true), 
    RESET("RESET", 21, "RESET", 21, "RESET", 'r', -1);
    
    private static final Map nameMapping;
    private static final Pattern formattingCodePattern;
    private final String field_175748_y;
    private final char formattingCode;
    private final boolean fancyStyling;
    private final String controlString;
    private final int field_175747_C;
    private static final EnumChatFormatting[] $VALUES;
    private static final String __OBFID;
    private static final EnumChatFormatting[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00000342";
        ENUM$VALUES = new EnumChatFormatting[] { EnumChatFormatting.BLACK, EnumChatFormatting.DARK_BLUE, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_RED, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.GOLD, EnumChatFormatting.GRAY, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.BLUE, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.RED, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.WHITE, EnumChatFormatting.OBFUSCATED, EnumChatFormatting.BOLD, EnumChatFormatting.STRIKETHROUGH, EnumChatFormatting.UNDERLINE, EnumChatFormatting.ITALIC, EnumChatFormatting.RESET };
        nameMapping = Maps.newHashMap();
        formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        $VALUES = new EnumChatFormatting[] { EnumChatFormatting.BLACK, EnumChatFormatting.DARK_BLUE, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_RED, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.GOLD, EnumChatFormatting.GRAY, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.BLUE, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.RED, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.WHITE, EnumChatFormatting.OBFUSCATED, EnumChatFormatting.BOLD, EnumChatFormatting.STRIKETHROUGH, EnumChatFormatting.UNDERLINE, EnumChatFormatting.ITALIC, EnumChatFormatting.RESET };
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            EnumChatFormatting.nameMapping.put(func_175745_c(enumChatFormatting.field_175748_y), enumChatFormatting);
            int n = 0;
            ++n;
        }
    }
    
    private static String func_175745_c(final String s) {
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final int n2, final String s3, final char c, final int n3) {
        this(s, n, s2, n2, s3, c, false, n3);
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final int n2, final String s3, final char c, final boolean b) {
        this(s, n, s2, n2, s3, c, b, -1);
    }
    
    private EnumChatFormatting(final String s, final int n, final String s2, final int n2, final String field_175748_y, final char formattingCode, final boolean fancyStyling, final int field_175747_C) {
        this.field_175748_y = field_175748_y;
        this.formattingCode = formattingCode;
        this.fancyStyling = fancyStyling;
        this.field_175747_C = field_175747_C;
        this.controlString = "§" + formattingCode;
    }
    
    public int func_175746_b() {
        return this.field_175747_C;
    }
    
    public boolean isFancyStyling() {
        return this.fancyStyling;
    }
    
    public boolean isColor() {
        return !this.fancyStyling && this != EnumChatFormatting.RESET;
    }
    
    public String getFriendlyName() {
        return this.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return this.controlString;
    }
    
    public static String getTextWithoutFormattingCodes(final String s) {
        return (s == null) ? null : EnumChatFormatting.formattingCodePattern.matcher(s).replaceAll("");
    }
    
    public static EnumChatFormatting getValueByName(final String s) {
        return (s == null) ? null : EnumChatFormatting.nameMapping.get(func_175745_c(s));
    }
    
    public static EnumChatFormatting func_175744_a(final int n) {
        if (n < 0) {
            return EnumChatFormatting.RESET;
        }
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            if (enumChatFormatting.func_175746_b() == n) {
                return enumChatFormatting;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public static Collection getValidValues(final boolean b, final boolean b2) {
        final ArrayList arrayList = Lists.newArrayList();
        final EnumChatFormatting[] values = values();
        while (0 < values.length) {
            final EnumChatFormatting enumChatFormatting = values[0];
            if ((!enumChatFormatting.isColor() || b) && (!enumChatFormatting.isFancyStyling() || b2)) {
                arrayList.add(enumChatFormatting.getFriendlyName());
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
}
