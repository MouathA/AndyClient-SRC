package joptsimple;

import java.util.*;

final class ParserRules
{
    static final char HYPHEN_CHAR = '-';
    static final String HYPHEN;
    static final String DOUBLE_HYPHEN = "--";
    static final String OPTION_TERMINATOR = "--";
    static final String RESERVED_FOR_EXTENSIONS = "W";
    
    private ParserRules() {
        throw new UnsupportedOperationException();
    }
    
    static boolean isShortOptionToken(final String s) {
        return s.startsWith(ParserRules.HYPHEN) && !ParserRules.HYPHEN.equals(s) && !isLongOptionToken(s);
    }
    
    static boolean isLongOptionToken(final String s) {
        return s.startsWith("--") && !isOptionTerminator(s);
    }
    
    static boolean isOptionTerminator(final String s) {
        return "--".equals(s);
    }
    
    static void ensureLegalOption(final String s) {
        if (s.startsWith(ParserRules.HYPHEN)) {
            throw new IllegalOptionSpecificationException(String.valueOf(s));
        }
        while (0 < s.length()) {
            ensureLegalOptionCharacter(s.charAt(0));
            int n = 0;
            ++n;
        }
    }
    
    static void ensureLegalOptions(final Collection collection) {
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ensureLegalOption(iterator.next());
        }
    }
    
    private static void ensureLegalOptionCharacter(final char c) {
        if (!Character.isLetterOrDigit(c) && !isAllowedPunctuation(c)) {
            throw new IllegalOptionSpecificationException(String.valueOf(c));
        }
    }
    
    private static boolean isAllowedPunctuation(final char c) {
        return "?.-".indexOf(c) != -1;
    }
    
    static {
        HYPHEN = String.valueOf('-');
    }
}
