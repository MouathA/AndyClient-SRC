package org.apache.commons.codec.language;

import java.util.*;
import org.apache.commons.codec.*;

public class Metaphone implements StringEncoder
{
    private static final String VOWELS = "AEIOU";
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private int maxCodeLen;
    
    public Metaphone() {
        this.maxCodeLen = 4;
    }
    
    public String metaphone(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (s.length() == 1) {
            return s.toUpperCase(Locale.ENGLISH);
        }
        final char[] charArray = s.toUpperCase(Locale.ENGLISH).toCharArray();
        final StringBuilder sb = new StringBuilder(40);
        final StringBuilder sb2 = new StringBuilder(10);
        switch (charArray[0]) {
            case 'G':
            case 'K':
            case 'P': {
                if (charArray[1] == 'N') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'A': {
                if (charArray[1] == 'E') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'W': {
                if (charArray[1] == 'R') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                if (charArray[1] == 'H') {
                    sb.append(charArray, 1, charArray.length - 1);
                    sb.setCharAt(0, 'W');
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'X': {
                charArray[0] = 'S';
                sb.append(charArray);
                break;
            }
            default: {
                sb.append(charArray);
                break;
            }
        }
        final int length = sb.length();
        while (sb2.length() < this.getMaxCodeLen() && 0 < length) {
            final char char1 = sb.charAt(0);
            if (char1 != 'C' && this.isPreviousChar(sb, 0, char1)) {
                int n = 0;
                ++n;
            }
            else {
                int n = 0;
                switch (char1) {
                    case 'A':
                    case 'E':
                    case 'I':
                    case 'O':
                    case 'U': {
                        if (!false) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'B': {
                        if (this.isPreviousChar(sb, 0, 'M') && this.isLastChar(length, 0)) {
                            break;
                        }
                        sb2.append(char1);
                        break;
                    }
                    case 'C': {
                        if (this.isPreviousChar(sb, 0, 'S') && !this.isLastChar(length, 0) && "EIY".indexOf(sb.charAt(1)) >= 0) {
                            break;
                        }
                        if (this.regionMatch(sb, 0, "CIA")) {
                            sb2.append('X');
                            break;
                        }
                        if (!this.isLastChar(length, 0) && "EIY".indexOf(sb.charAt(1)) >= 0) {
                            sb2.append('S');
                            break;
                        }
                        if (this.isPreviousChar(sb, 0, 'S') && this.isNextChar(sb, 0, 'H')) {
                            sb2.append('K');
                            break;
                        }
                        if (!this.isNextChar(sb, 0, 'H')) {
                            sb2.append('K');
                            break;
                        }
                        if (!false && length >= 3 && this.isVowel(sb, 2)) {
                            sb2.append('K');
                            break;
                        }
                        sb2.append('X');
                        break;
                    }
                    case 'D': {
                        if (!this.isLastChar(length, 1) && this.isNextChar(sb, 0, 'G') && "EIY".indexOf(sb.charAt(2)) >= 0) {
                            sb2.append('J');
                            n += 2;
                            break;
                        }
                        sb2.append('T');
                        break;
                    }
                    case 'G': {
                        if (this.isLastChar(length, 1) && this.isNextChar(sb, 0, 'H')) {
                            break;
                        }
                        if (!this.isLastChar(length, 1) && this.isNextChar(sb, 0, 'H') && !this.isVowel(sb, 2)) {
                            break;
                        }
                        if (0 > 0) {
                            if (this.regionMatch(sb, 0, "GN")) {
                                break;
                            }
                            if (this.regionMatch(sb, 0, "GNED")) {
                                break;
                            }
                        }
                        if (this.isPreviousChar(sb, 0, 'G')) {}
                        if (!this.isLastChar(length, 0) && "EIY".indexOf(sb.charAt(1)) >= 0 && !false) {
                            sb2.append('J');
                            break;
                        }
                        sb2.append('K');
                        break;
                    }
                    case 'H': {
                        if (this.isLastChar(length, 0)) {
                            break;
                        }
                        if (0 > 0 && "CSPTG".indexOf(sb.charAt(-1)) >= 0) {
                            break;
                        }
                        if (this.isVowel(sb, 1)) {
                            sb2.append('H');
                            break;
                        }
                        break;
                    }
                    case 'F':
                    case 'J':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'R': {
                        sb2.append(char1);
                        break;
                    }
                    case 'K': {
                        if (0 <= 0) {
                            sb2.append(char1);
                            break;
                        }
                        if (!this.isPreviousChar(sb, 0, 'C')) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'P': {
                        if (this.isNextChar(sb, 0, 'H')) {
                            sb2.append('F');
                            break;
                        }
                        sb2.append(char1);
                        break;
                    }
                    case 'Q': {
                        sb2.append('K');
                        break;
                    }
                    case 'S': {
                        if (this.regionMatch(sb, 0, "SH") || this.regionMatch(sb, 0, "SIO") || this.regionMatch(sb, 0, "SIA")) {
                            sb2.append('X');
                            break;
                        }
                        sb2.append('S');
                        break;
                    }
                    case 'T': {
                        if (this.regionMatch(sb, 0, "TIA") || this.regionMatch(sb, 0, "TIO")) {
                            sb2.append('X');
                            break;
                        }
                        if (this.regionMatch(sb, 0, "TCH")) {
                            break;
                        }
                        if (this.regionMatch(sb, 0, "TH")) {
                            sb2.append('0');
                            break;
                        }
                        sb2.append('T');
                        break;
                    }
                    case 'V': {
                        sb2.append('F');
                        break;
                    }
                    case 'W':
                    case 'Y': {
                        if (!this.isLastChar(length, 0) && this.isVowel(sb, 1)) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'X': {
                        sb2.append('K');
                        sb2.append('S');
                        break;
                    }
                    case 'Z': {
                        sb2.append('S');
                        break;
                    }
                }
                ++n;
            }
            if (sb2.length() > this.getMaxCodeLen()) {
                sb2.setLength(this.getMaxCodeLen());
            }
        }
        return sb2.toString();
    }
    
    private boolean isVowel(final StringBuilder sb, final int n) {
        return "AEIOU".indexOf(sb.charAt(n)) >= 0;
    }
    
    private boolean isPreviousChar(final StringBuilder sb, final int n, final char c) {
        if (n > 0 && n < sb.length()) {
            final boolean b = sb.charAt(n - 1) == c;
        }
        return false;
    }
    
    private boolean isNextChar(final StringBuilder sb, final int n, final char c) {
        if (n >= 0 && n < sb.length() - 1) {
            final boolean b = sb.charAt(n + 1) == c;
        }
        return false;
    }
    
    private boolean regionMatch(final StringBuilder sb, final int n, final String s) {
        if (n >= 0 && n + s.length() - 1 < sb.length()) {
            sb.substring(n, n + s.length()).equals(s);
        }
        return false;
    }
    
    private boolean isLastChar(final int n, final int n2) {
        return n2 + 1 == n;
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
        }
        return this.metaphone((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.metaphone(s);
    }
    
    public boolean isMetaphoneEqual(final String s, final String s2) {
        return this.metaphone(s).equals(this.metaphone(s2));
    }
    
    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }
    
    public void setMaxCodeLen(final int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }
}
