package org.apache.commons.lang3.text.translate;

import java.io.*;

public class UnicodeEscaper extends CodePointTranslator
{
    private final int below;
    private final int above;
    private final boolean between;
    
    public UnicodeEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }
    
    protected UnicodeEscaper(final int below, final int above, final boolean between) {
        this.below = below;
        this.above = above;
        this.between = between;
    }
    
    public static UnicodeEscaper below(final int n) {
        return outsideOf(n, Integer.MAX_VALUE);
    }
    
    public static UnicodeEscaper above(final int n) {
        return outsideOf(0, n);
    }
    
    public static UnicodeEscaper outsideOf(final int n, final int n2) {
        return new UnicodeEscaper(n, n2, false);
    }
    
    public static UnicodeEscaper between(final int n, final int n2) {
        return new UnicodeEscaper(n, n2, true);
    }
    
    @Override
    public boolean translate(final int n, final Writer writer) throws IOException {
        if (this.between) {
            if (n < this.below || n > this.above) {
                return false;
            }
        }
        else if (n >= this.below && n <= this.above) {
            return false;
        }
        if (n > 65535) {
            writer.write(this.toUtf16Escape(n));
        }
        else if (n > 4095) {
            writer.write("\\u" + CharSequenceTranslator.hex(n));
        }
        else if (n > 255) {
            writer.write("\\u0" + CharSequenceTranslator.hex(n));
        }
        else if (n > 15) {
            writer.write("\\u00" + CharSequenceTranslator.hex(n));
        }
        else {
            writer.write("\\u000" + CharSequenceTranslator.hex(n));
        }
        return true;
    }
    
    protected String toUtf16Escape(final int n) {
        return "\\u" + CharSequenceTranslator.hex(n);
    }
}
