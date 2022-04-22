package org.apache.logging.log4j.core.pattern;

import java.util.*;

public enum AnsiEscape
{
    PREFIX("PREFIX", 0, "\u001b["), 
    SUFFIX("SUFFIX", 1, "m"), 
    SEPARATOR("SEPARATOR", 2, ";"), 
    NORMAL("NORMAL", 3, "0"), 
    BRIGHT("BRIGHT", 4, "1"), 
    DIM("DIM", 5, "2"), 
    UNDERLINE("UNDERLINE", 6, "3"), 
    BLINK("BLINK", 7, "5"), 
    REVERSE("REVERSE", 8, "7"), 
    HIDDEN("HIDDEN", 9, "8"), 
    BLACK("BLACK", 10, "30"), 
    FG_BLACK("FG_BLACK", 11, "30"), 
    RED("RED", 12, "31"), 
    FG_RED("FG_RED", 13, "31"), 
    GREEN("GREEN", 14, "32"), 
    FG_GREEN("FG_GREEN", 15, "32"), 
    YELLOW("YELLOW", 16, "33"), 
    FG_YELLOW("FG_YELLOW", 17, "33"), 
    BLUE("BLUE", 18, "34"), 
    FG_BLUE("FG_BLUE", 19, "34"), 
    MAGENTA("MAGENTA", 20, "35"), 
    FG_MAGENTA("FG_MAGENTA", 21, "35"), 
    CYAN("CYAN", 22, "36"), 
    FG_CYAN("FG_CYAN", 23, "36"), 
    WHITE("WHITE", 24, "37"), 
    FG_WHITE("FG_WHITE", 25, "37"), 
    DEFAULT("DEFAULT", 26, "39"), 
    FG_DEFAULT("FG_DEFAULT", 27, "39"), 
    BG_BLACK("BG_BLACK", 28, "40"), 
    BG_RED("BG_RED", 29, "41"), 
    BG_GREEN("BG_GREEN", 30, "42"), 
    BG_YELLOW("BG_YELLOW", 31, "43"), 
    BG_BLUE("BG_BLUE", 32, "44"), 
    BG_MAGENTA("BG_MAGENTA", 33, "45"), 
    BG_CYAN("BG_CYAN", 34, "46"), 
    BG_WHITE("BG_WHITE", 35, "47");
    
    private static final String WHITESPACE_REGEX = "\\s*";
    private final String code;
    private static final AnsiEscape[] $VALUES;
    
    private AnsiEscape(final String s, final int n, final String code) {
        this.code = code;
    }
    
    public static String getDefaultStyle() {
        return AnsiEscape.PREFIX.getCode() + AnsiEscape.SUFFIX.getCode();
    }
    
    private static String toRegexSeparator(final String s) {
        return "\\s*" + s + "\\s*";
    }
    
    public String getCode() {
        return this.code;
    }
    
    public static Map createMap(final String s, final String[] array) {
        return createMap(s.split(toRegexSeparator(",")), array);
    }
    
    public static Map createMap(final String[] array, final String[] array2) {
        final String[] array3 = (array2 != null) ? array2.clone() : new String[0];
        Arrays.sort(array3);
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        while (0 < array.length) {
            final String[] split = array[0].split(toRegexSeparator("="));
            if (split.length > 1) {
                final String upperCase = split[0].toUpperCase(Locale.ENGLISH);
                final String s = split[1];
                hashMap.put(upperCase, (Arrays.binarySearch(array3, upperCase) < 0) ? createSequence(s.split("\\s")) : s);
            }
            int n = 0;
            ++n;
        }
        return hashMap;
    }
    
    public static String createSequence(final String... array) {
        if (array == null) {
            return getDefaultStyle();
        }
        final StringBuilder sb = new StringBuilder(AnsiEscape.PREFIX.getCode());
        while (0 < array.length) {
            final AnsiEscape value = valueOf(array[0].trim().toUpperCase(Locale.ENGLISH));
            if (!false) {
                sb.append(AnsiEscape.SEPARATOR.getCode());
            }
            sb.append(value.getCode());
            int n = 0;
            ++n;
        }
        sb.append(AnsiEscape.SUFFIX.getCode());
        return sb.toString();
    }
    
    static {
        $VALUES = new AnsiEscape[] { AnsiEscape.PREFIX, AnsiEscape.SUFFIX, AnsiEscape.SEPARATOR, AnsiEscape.NORMAL, AnsiEscape.BRIGHT, AnsiEscape.DIM, AnsiEscape.UNDERLINE, AnsiEscape.BLINK, AnsiEscape.REVERSE, AnsiEscape.HIDDEN, AnsiEscape.BLACK, AnsiEscape.FG_BLACK, AnsiEscape.RED, AnsiEscape.FG_RED, AnsiEscape.GREEN, AnsiEscape.FG_GREEN, AnsiEscape.YELLOW, AnsiEscape.FG_YELLOW, AnsiEscape.BLUE, AnsiEscape.FG_BLUE, AnsiEscape.MAGENTA, AnsiEscape.FG_MAGENTA, AnsiEscape.CYAN, AnsiEscape.FG_CYAN, AnsiEscape.WHITE, AnsiEscape.FG_WHITE, AnsiEscape.DEFAULT, AnsiEscape.FG_DEFAULT, AnsiEscape.BG_BLACK, AnsiEscape.BG_RED, AnsiEscape.BG_GREEN, AnsiEscape.BG_YELLOW, AnsiEscape.BG_BLUE, AnsiEscape.BG_MAGENTA, AnsiEscape.BG_CYAN, AnsiEscape.BG_WHITE };
    }
}
