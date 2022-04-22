package org.apache.commons.lang3.text.translate;

import java.io.*;

public class NumericEntityEscaper extends CodePointTranslator
{
    private final int below;
    private final int above;
    private final boolean between;
    
    private NumericEntityEscaper(final int below, final int above, final boolean between) {
        this.below = below;
        this.above = above;
        this.between = between;
    }
    
    public NumericEntityEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }
    
    public static NumericEntityEscaper below(final int n) {
        return outsideOf(n, Integer.MAX_VALUE);
    }
    
    public static NumericEntityEscaper above(final int n) {
        return outsideOf(0, n);
    }
    
    public static NumericEntityEscaper between(final int n, final int n2) {
        return new NumericEntityEscaper(n, n2, true);
    }
    
    public static NumericEntityEscaper outsideOf(final int n, final int n2) {
        return new NumericEntityEscaper(n, n2, false);
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
        writer.write("&#");
        writer.write(Integer.toString(n, 10));
        writer.write(59);
        return true;
    }
}
