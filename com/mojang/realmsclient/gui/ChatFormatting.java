package com.mojang.realmsclient.gui;

import java.util.regex.*;
import java.util.*;

public enum ChatFormatting
{
    BLACK("BLACK", 0, '0'), 
    DARK_BLUE("DARK_BLUE", 1, '1'), 
    DARK_GREEN("DARK_GREEN", 2, '2'), 
    DARK_AQUA("DARK_AQUA", 3, '3'), 
    DARK_RED("DARK_RED", 4, '4'), 
    DARK_PURPLE("DARK_PURPLE", 5, '5'), 
    GOLD("GOLD", 6, '6'), 
    GRAY("GRAY", 7, '7'), 
    DARK_GRAY("DARK_GRAY", 8, '8'), 
    BLUE("BLUE", 9, '9'), 
    GREEN("GREEN", 10, 'a'), 
    AQUA("AQUA", 11, 'b'), 
    RED("RED", 12, 'c'), 
    LIGHT_PURPLE("LIGHT_PURPLE", 13, 'd'), 
    YELLOW("YELLOW", 14, 'e'), 
    WHITE("WHITE", 15, 'f'), 
    OBFUSCATED("OBFUSCATED", 16, 'k', true), 
    BOLD("BOLD", 17, 'l', true), 
    STRIKETHROUGH("STRIKETHROUGH", 18, 'm', true), 
    UNDERLINE("UNDERLINE", 19, 'n', true), 
    ITALIC("ITALIC", 20, 'o', true), 
    RESET("RESET", 21, 'r');
    
    public static final char PREFIX_CODE = '§';
    private static final Map FORMATTING_BY_CHAR;
    private static final Map FORMATTING_BY_NAME;
    private static final Pattern STRIP_FORMATTING_PATTERN;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final ChatFormatting[] $VALUES;
    
    private ChatFormatting(final String s, final int n, final char c) {
        this(s, n, c, false);
    }
    
    private ChatFormatting(final String s, final int n, final char code, final boolean isFormat) {
        this.code = code;
        this.isFormat = isFormat;
        this.toString = "§" + code;
    }
    
    public char getChar() {
        return this.code;
    }
    
    public boolean isFormat() {
        return this.isFormat;
    }
    
    public boolean isColor() {
        return !this.isFormat && this != ChatFormatting.RESET;
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    public static String stripFormatting(final String s) {
        return (s == null) ? null : ChatFormatting.STRIP_FORMATTING_PATTERN.matcher(s).replaceAll("");
    }
    
    public static ChatFormatting getByChar(final char c) {
        return ChatFormatting.FORMATTING_BY_CHAR.get(c);
    }
    
    public static ChatFormatting getByName(final String s) {
        if (s == null) {
            return null;
        }
        return ChatFormatting.FORMATTING_BY_NAME.get(s.toLowerCase());
    }
    
    public static Collection getNames(final boolean b, final boolean b2) {
        final ArrayList<String> list = new ArrayList<String>();
        final ChatFormatting[] values = values();
        while (0 < values.length) {
            final ChatFormatting chatFormatting = values[0];
            if (!chatFormatting.isColor() || b) {
                if (!chatFormatting.isFormat() || b2) {
                    list.add(chatFormatting.getName());
                }
            }
            int n = 0;
            ++n;
        }
        return list;
    }
    
    static {
        $VALUES = new ChatFormatting[] { ChatFormatting.BLACK, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_GREEN, ChatFormatting.DARK_AQUA, ChatFormatting.DARK_RED, ChatFormatting.DARK_PURPLE, ChatFormatting.GOLD, ChatFormatting.GRAY, ChatFormatting.DARK_GRAY, ChatFormatting.BLUE, ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.RED, ChatFormatting.LIGHT_PURPLE, ChatFormatting.YELLOW, ChatFormatting.WHITE, ChatFormatting.OBFUSCATED, ChatFormatting.BOLD, ChatFormatting.STRIKETHROUGH, ChatFormatting.UNDERLINE, ChatFormatting.ITALIC, ChatFormatting.RESET };
        FORMATTING_BY_CHAR = new HashMap();
        FORMATTING_BY_NAME = new HashMap();
        STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        final ChatFormatting[] values = values();
        while (0 < values.length) {
            final ChatFormatting chatFormatting = values[0];
            ChatFormatting.FORMATTING_BY_CHAR.put(chatFormatting.getChar(), chatFormatting);
            ChatFormatting.FORMATTING_BY_NAME.put(chatFormatting.getName(), chatFormatting);
            int n = 0;
            ++n;
        }
    }
}
