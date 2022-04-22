package org.apache.commons.lang3.text.translate;

import java.util.*;
import java.io.*;

public class NumericEntityUnescaper extends CharSequenceTranslator
{
    private final EnumSet options;
    
    public NumericEntityUnescaper(final OPTION... array) {
        if (array.length > 0) {
            this.options = EnumSet.copyOf(Arrays.asList(array));
        }
        else {
            this.options = EnumSet.copyOf(Arrays.asList(OPTION.semiColonRequired));
        }
    }
    
    public boolean isSet(final OPTION option) {
        return this.options != null && this.options.contains(option);
    }
    
    @Override
    public int translate(final CharSequence charSequence, final int n, final Writer writer) throws IOException {
        final int length = charSequence.length();
        if (charSequence.charAt(n) != '&' || n >= length - 2 || charSequence.charAt(n + 1) != '#') {
            return 0;
        }
        int n2 = n + 2;
        final char char1 = charSequence.charAt(n2);
        if ((char1 == 'x' || char1 == 'X') && ++n2 == length) {
            return 0;
        }
        int n3;
        for (n3 = n2; n3 < length && ((charSequence.charAt(n3) >= '0' && charSequence.charAt(n3) <= '9') || (charSequence.charAt(n3) >= 'a' && charSequence.charAt(n3) <= 'f') || (charSequence.charAt(n3) >= 'A' && charSequence.charAt(n3) <= 'F')); ++n3) {}
        final int n4 = (n3 != length && charSequence.charAt(n3) == ';') ? 1 : 0;
        if (n4 == 0) {
            if (this.isSet(OPTION.semiColonRequired)) {
                return 0;
            }
            if (this.isSet(OPTION.errorIfNoSemiColon)) {
                throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
            }
        }
        int n5;
        if (true) {
            n5 = Integer.parseInt(charSequence.subSequence(n2, n3).toString(), 16);
        }
        else {
            n5 = Integer.parseInt(charSequence.subSequence(n2, n3).toString(), 10);
        }
        if (n5 > 65535) {
            final char[] chars = Character.toChars(n5);
            writer.write(chars[0]);
            writer.write(chars[1]);
        }
        else {
            writer.write(n5);
        }
        return 2 + n3 - n2 + 1 + n4;
    }
    
    public enum OPTION
    {
        semiColonRequired("semiColonRequired", 0), 
        semiColonOptional("semiColonOptional", 1), 
        errorIfNoSemiColon("errorIfNoSemiColon", 2);
        
        private static final OPTION[] $VALUES;
        
        private OPTION(final String s, final int n) {
        }
        
        static {
            $VALUES = new OPTION[] { OPTION.semiColonRequired, OPTION.semiColonOptional, OPTION.errorIfNoSemiColon };
        }
    }
}
