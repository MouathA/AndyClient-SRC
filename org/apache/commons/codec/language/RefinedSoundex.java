package org.apache.commons.codec.language;

import org.apache.commons.codec.*;

public class RefinedSoundex implements StringEncoder
{
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
    private static final char[] US_ENGLISH_MAPPING;
    private final char[] soundexMapping;
    public static final RefinedSoundex US_ENGLISH;
    
    public RefinedSoundex() {
        this.soundexMapping = RefinedSoundex.US_ENGLISH_MAPPING;
    }
    
    public RefinedSoundex(final char[] array) {
        System.arraycopy(array, 0, this.soundexMapping = new char[array.length], 0, array.length);
    }
    
    public RefinedSoundex(final String s) {
        this.soundexMapping = s.toCharArray();
    }
    
    public int difference(final String s, final String s2) throws EncoderException {
        return SoundexUtils.difference(this, s, s2);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
        }
        return this.soundex((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.soundex(s);
    }
    
    char getMappingCode(final char c) {
        if (!Character.isLetter(c)) {
            return '\0';
        }
        return this.soundexMapping[Character.toUpperCase(c) - 'A'];
    }
    
    public String soundex(String clean) {
        if (clean == null) {
            return null;
        }
        clean = SoundexUtils.clean(clean);
        if (clean.length() == 0) {
            return clean;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(clean.charAt(0));
        while (0 < clean.length()) {
            final char mappingCode = this.getMappingCode(clean.charAt(0));
            if (mappingCode != '*') {
                if (mappingCode != '\0') {
                    sb.append(mappingCode);
                }
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    static {
        US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
        US_ENGLISH = new RefinedSoundex();
    }
}
