package org.apache.commons.codec.language;

import java.util.*;
import org.apache.commons.codec.*;

public class MatchRatingApproachEncoder implements StringEncoder
{
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int ELEVEN = 11;
    private static final int TWELVE = 12;
    private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
    private static final String UNICODE = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171";
    
    String cleanName(final String s) {
        String s2 = s.toUpperCase(Locale.ENGLISH);
        final String[] array = { "\\-", "[&]", "\\'", "\\.", "[\\,]" };
        while (0 < array.length) {
            s2 = s2.replaceAll(array[0], "");
            int n = 0;
            ++n;
        }
        return this.removeAccents(s2).replaceAll("\\s+", "");
    }
    
    @Override
    public final Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
        }
        return this.encode((String)o);
    }
    
    @Override
    public final String encode(String s) {
        if (s == null || "".equalsIgnoreCase(s) || " ".equalsIgnoreCase(s) || s.length() == 1) {
            return "";
        }
        s = this.cleanName(s);
        s = this.removeVowels(s);
        s = this.removeDoubleConsonants(s);
        s = this.getFirst3Last3(s);
        return s;
    }
    
    String getFirst3Last3(final String s) {
        final int length = s.length();
        if (length > 6) {
            return s.substring(0, 3) + s.substring(length - 3, length);
        }
        return s;
    }
    
    int getMinRating(final int n) {
        if (n > 4) {
            if (n < 5 || n > 7) {
                if (n < 8 || n > 11) {
                    if (n == 12) {}
                }
            }
        }
        return 1;
    }
    
    public boolean isEncodeEquals(String s, String s2) {
        if (s == null || "".equalsIgnoreCase(s) || " ".equalsIgnoreCase(s)) {
            return false;
        }
        if (s2 == null || "".equalsIgnoreCase(s2) || " ".equalsIgnoreCase(s2)) {
            return false;
        }
        if (s.length() == 1 || s2.length() == 1) {
            return false;
        }
        if (s.equalsIgnoreCase(s2)) {
            return true;
        }
        s = this.cleanName(s);
        s2 = this.cleanName(s2);
        s = this.removeVowels(s);
        s2 = this.removeVowels(s2);
        s = this.removeDoubleConsonants(s);
        s2 = this.removeDoubleConsonants(s2);
        s = this.getFirst3Last3(s);
        s2 = this.getFirst3Last3(s2);
        if (Math.abs(s.length() - s2.length()) >= 3) {
            return false;
        }
        this.getMinRating(Math.abs(s.length() + s2.length()));
        return this.leftToRightThenRightToLeftProcessing(s, s2) >= 0;
    }
    
    int leftToRightThenRightToLeftProcessing(final String s, final String s2) {
        final char[] charArray = s.toCharArray();
        final char[] charArray2 = s2.toCharArray();
        final int n = s.length() - 1;
        final int n2 = s2.length() - 1;
        while (0 < charArray.length && 0 <= n2) {
            final String substring = s.substring(0, 1);
            final String substring2 = s.substring(n - 0, n - 0 + 1);
            final String substring3 = s2.substring(0, 1);
            final String substring4 = s2.substring(n2 - 0, n2 - 0 + 1);
            if (substring.equals(substring3)) {
                charArray2[0] = (charArray[0] = ' ');
            }
            if (substring2.equals(substring4)) {
                charArray2[n2 - 0] = (charArray[n - 0] = ' ');
            }
            int n3 = 0;
            ++n3;
        }
        final String replaceAll = new String(charArray).replaceAll("\\s+", "");
        final String replaceAll2 = new String(charArray2).replaceAll("\\s+", "");
        if (replaceAll.length() > replaceAll2.length()) {
            return Math.abs(6 - replaceAll.length());
        }
        return Math.abs(6 - replaceAll2.length());
    }
    
    String removeAccents(final String s) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            final int index = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171".indexOf(char1);
            if (index > -1) {
                sb.append("AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu".charAt(index));
            }
            else {
                sb.append(char1);
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    String removeDoubleConsonants(final String s) {
        String s2 = s.toUpperCase();
        final String[] double_CONSONANT = MatchRatingApproachEncoder.DOUBLE_CONSONANT;
        while (0 < double_CONSONANT.length) {
            final String s3 = double_CONSONANT[0];
            if (s2.contains(s3)) {
                s2 = s2.replace(s3, s3.substring(0, 1));
            }
            int n = 0;
            ++n;
        }
        return s2;
    }
    
    String removeVowels(String s) {
        final String substring = s.substring(0, 1);
        s = s.replaceAll("A", "");
        s = s.replaceAll("E", "");
        s = s.replaceAll("I", "");
        s = s.replaceAll("O", "");
        s = s.replaceAll("U", "");
        s = s.replaceAll("\\s{2,}\\b", " ");
        if (substring == 0) {
            return substring + s;
        }
        return s;
    }
    
    static {
        MatchRatingApproachEncoder.DOUBLE_CONSONANT = new String[] { "BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ" };
    }
}
