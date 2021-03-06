package Mood.hologram.impl;

import java.util.regex.*;
import java.util.*;
import com.google.common.collect.*;

public enum PictureHologramColor
{
    BLACK("BLACK", 0, '0', 0), 
    DARK_BLUE("DARK_BLUE", 1, '1', 1), 
    DARK_GREEN("DARK_GREEN", 2, '2', 2), 
    DARK_AQUA("DARK_AQUA", 3, '3', 3), 
    DARK_RED("DARK_RED", 4, '4', 4), 
    DARK_PURPLE("DARK_PURPLE", 5, '5', 5), 
    GOLD("GOLD", 6, '6', 6), 
    GRAY("GRAY", 7, '7', 7), 
    DARK_GRAY("DARK_GRAY", 8, '8', 8), 
    BLUE("BLUE", 9, '9', 9), 
    GREEN("GREEN", 10, 'a', 10), 
    AQUA("AQUA", 11, 'b', 11), 
    RED("RED", 12, 'c', 12), 
    LIGHT_PURPLE("LIGHT_PURPLE", 13, 'd', 13), 
    YELLOW("YELLOW", 14, 'e', 14), 
    WHITE("WHITE", 15, 'f', 15), 
    MAGIC("MAGIC", 16, 'k', 16, true), 
    BOLD("BOLD", 17, 'l', 17, true), 
    STRIKETHROUGH("STRIKETHROUGH", 18, 'm', 18, true), 
    UNDERLINE("UNDERLINE", 19, 'n', 19, true), 
    ITALIC("ITALIC", 20, 'o', 20, true), 
    RESET("RESET", 21, 'r', 21);
    
    public static final char COLOR_CHAR;
    private static final Pattern STRIP_COLOR_PATTERN;
    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final Map BY_ID;
    private static final Map BY_CHAR;
    private static final PictureHologramColor[] ENUM$VALUES;
    
    static {
        COLOR_CHAR = '?';
        ENUM$VALUES = new PictureHologramColor[] { PictureHologramColor.BLACK, PictureHologramColor.DARK_BLUE, PictureHologramColor.DARK_GREEN, PictureHologramColor.DARK_AQUA, PictureHologramColor.DARK_RED, PictureHologramColor.DARK_PURPLE, PictureHologramColor.GOLD, PictureHologramColor.GRAY, PictureHologramColor.DARK_GRAY, PictureHologramColor.BLUE, PictureHologramColor.GREEN, PictureHologramColor.AQUA, PictureHologramColor.RED, PictureHologramColor.LIGHT_PURPLE, PictureHologramColor.YELLOW, PictureHologramColor.WHITE, PictureHologramColor.MAGIC, PictureHologramColor.BOLD, PictureHologramColor.STRIKETHROUGH, PictureHologramColor.UNDERLINE, PictureHologramColor.ITALIC, PictureHologramColor.RESET };
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('?') + "[0-9A-FK-OR]");
        BY_ID = Maps.newHashMap();
        BY_CHAR = Maps.newHashMap();
        PictureHologramColor[] values;
        while (0 < (values = values()).length) {
            final PictureHologramColor pictureHologramColor = values[0];
            PictureHologramColor.BY_ID.put(pictureHologramColor.intCode, pictureHologramColor);
            PictureHologramColor.BY_CHAR.put(pictureHologramColor.code, pictureHologramColor);
            int n = 0;
            ++n;
        }
    }
    
    private PictureHologramColor(final String s, final int n, final char c, final int n2) {
        this(s, n, c, n2, false);
    }
    
    private PictureHologramColor(final String s, final int n, final char code, final int intCode, final boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[] { '?', code });
    }
    
    public char getChar() {
        return this.code;
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    public boolean isFormat() {
        return this.isFormat;
    }
    
    public static PictureHologramColor getByChar(final char c) {
        return PictureHologramColor.BY_CHAR.get(c);
    }
    
    public static PictureHologramColor getByChar(final String s) {
        return PictureHologramColor.BY_CHAR.get(s.charAt(0));
    }
    
    public static String stripColor(final String s) {
        return (s == null) ? null : PictureHologramColor.STRIP_COLOR_PATTERN.matcher(s).replaceAll("");
    }
    
    public static String translateAlternateColorCodes(final char c, final String s) {
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length - 1) {
            if (charArray[0] == c && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(charArray[1]) > -1) {
                charArray[0] = '?';
                charArray[1] = Character.toLowerCase(charArray[1]);
            }
            int n = 0;
            ++n;
        }
        return new String(charArray);
    }
    
    public static String getLastColors(final String s) {
        String string = "";
        final int length = s.length();
        for (int i = length - 1; i > -1; --i) {
            if (s.charAt(i) == '?' && i < length - 1) {
                final PictureHologramColor byChar = getByChar(s.charAt(i + 1));
                if (byChar != null) {
                    string = String.valueOf(byChar.toString()) + string;
                    if (byChar != 0) {
                        break;
                    }
                    if (byChar.equals(PictureHologramColor.RESET)) {
                        break;
                    }
                }
            }
        }
        return string;
    }
}
