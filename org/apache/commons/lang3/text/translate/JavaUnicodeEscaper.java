package org.apache.commons.lang3.text.translate;

public class JavaUnicodeEscaper extends UnicodeEscaper
{
    public static JavaUnicodeEscaper above(final int n) {
        return outsideOf(0, n);
    }
    
    public static JavaUnicodeEscaper below(final int n) {
        return outsideOf(n, Integer.MAX_VALUE);
    }
    
    public static JavaUnicodeEscaper between(final int n, final int n2) {
        return new JavaUnicodeEscaper(n, n2, true);
    }
    
    public static JavaUnicodeEscaper outsideOf(final int n, final int n2) {
        return new JavaUnicodeEscaper(n, n2, false);
    }
    
    public JavaUnicodeEscaper(final int n, final int n2, final boolean b) {
        super(n, n2, b);
    }
    
    @Override
    protected String toUtf16Escape(final int n) {
        final char[] chars = Character.toChars(n);
        return "\\u" + CharSequenceTranslator.hex(chars[0]) + "\\u" + CharSequenceTranslator.hex(chars[1]);
    }
}
