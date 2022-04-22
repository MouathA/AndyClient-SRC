package org.apache.commons.codec.language;

import org.apache.commons.codec.*;

public class Soundex implements StringEncoder
{
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    private static final char[] US_ENGLISH_MAPPING;
    public static final Soundex US_ENGLISH;
    @Deprecated
    private int maxLength;
    private final char[] soundexMapping;
    
    public Soundex() {
        this.maxLength = 4;
        this.soundexMapping = Soundex.US_ENGLISH_MAPPING;
    }
    
    public Soundex(final char[] array) {
        this.maxLength = 4;
        System.arraycopy(array, 0, this.soundexMapping = new char[array.length], 0, array.length);
    }
    
    public Soundex(final String s) {
        this.maxLength = 4;
        this.soundexMapping = s.toCharArray();
    }
    
    public int difference(final String s, final String s2) throws EncoderException {
        return SoundexUtils.difference(this, s, s2);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
        }
        return this.soundex((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.soundex(s);
    }
    
    private char getMappingCode(final String s, final int n) {
        final char map = this.map(s.charAt(n));
        if (n > 1 && map != '0') {
            final char char1 = s.charAt(n - 1);
            if ('H' == char1 || 'W' == char1) {
                final char char2 = s.charAt(n - 2);
                if (this.map(char2) == map || 'H' == char2 || 'W' == char2) {
                    return '\0';
                }
            }
        }
        return map;
    }
    
    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }
    
    private char[] getSoundexMapping() {
        return this.soundexMapping;
    }
    
    private char map(final char c) {
        final int n = c - 'A';
        if (n < 0 || n >= this.getSoundexMapping().length) {
            throw new IllegalArgumentException("The character is not mapped: " + c);
        }
        return this.getSoundexMapping()[n];
    }
    
    @Deprecated
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }
    
    public String soundex(String clean) {
        if (clean == null) {
            return null;
        }
        clean = SoundexUtils.clean(clean);
        if (clean.length() == 0) {
            return clean;
        }
        final char[] array = { '0', '0', '0', '0' };
        array[0] = clean.charAt(0);
        char mappingCode = this.getMappingCode(clean, 0);
        while (1 < clean.length() && 1 < array.length) {
            final String s = clean;
            final int n = 1;
            int n2 = 0;
            ++n2;
            final char mappingCode2 = this.getMappingCode(s, n);
            if (mappingCode2 != '\0') {
                if (mappingCode2 != '0' && mappingCode2 != mappingCode) {
                    final char[] array2 = array;
                    final int n3 = 1;
                    int n4 = 0;
                    ++n4;
                    array2[n3] = mappingCode2;
                }
                mappingCode = mappingCode2;
            }
        }
        return new String(array);
    }
    
    static {
        US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
        US_ENGLISH = new Soundex();
    }
}
