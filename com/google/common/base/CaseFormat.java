package com.google.common.base;

import com.google.common.annotations.*;
import java.io.*;
import javax.annotation.*;

@GwtCompatible
public enum CaseFormat
{
    LOWER_HYPHEN(CharMatcher.is('-'), "-") {
        @Override
        String normalizeWord(final String s) {
            return Ascii.toLowerCase(s);
        }
        
        @Override
        String convert(final CaseFormat caseFormat, final String s) {
            if (caseFormat == CaseFormat$1.LOWER_UNDERSCORE) {
                return s.replace('-', '_');
            }
            if (caseFormat == CaseFormat$1.UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s.replace('-', '_'));
            }
            return super.convert(caseFormat, s);
        }
    }, 
    LOWER_UNDERSCORE(CharMatcher.is('_'), "_") {
        @Override
        String normalizeWord(final String s) {
            return Ascii.toLowerCase(s);
        }
        
        @Override
        String convert(final CaseFormat caseFormat, final String s) {
            if (caseFormat == CaseFormat$2.LOWER_HYPHEN) {
                return s.replace('_', '-');
            }
            if (caseFormat == CaseFormat$2.UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s);
            }
            return super.convert(caseFormat, s);
        }
    }, 
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        @Override
        String normalizeWord(final String s) {
            return CaseFormat.access$100(s);
        }
    }, 
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
        @Override
        String normalizeWord(final String s) {
            return CaseFormat.access$100(s);
        }
    }, 
    UPPER_UNDERSCORE(CharMatcher.is('_'), "_") {
        @Override
        String normalizeWord(final String s) {
            return Ascii.toUpperCase(s);
        }
        
        @Override
        String convert(final CaseFormat caseFormat, final String s) {
            if (caseFormat == CaseFormat$5.LOWER_HYPHEN) {
                return Ascii.toLowerCase(s.replace('_', '-'));
            }
            if (caseFormat == CaseFormat$5.LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(s);
            }
            return super.convert(caseFormat, s);
        }
    };
    
    private final CharMatcher wordBoundary;
    private final String wordSeparator;
    private static final CaseFormat[] $VALUES;
    
    private CaseFormat(final String s, final int n, final CharMatcher wordBoundary, final String wordSeparator) {
        this.wordBoundary = wordBoundary;
        this.wordSeparator = wordSeparator;
    }
    
    public final String to(final CaseFormat caseFormat, final String s) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(s);
        return (caseFormat == this) ? s : this.convert(caseFormat, s);
    }
    
    String convert(final CaseFormat caseFormat, final String s) {
        while (true) {
            final CharMatcher wordBoundary = this.wordBoundary;
            int indexIn = 0;
            ++indexIn;
            if ((indexIn = wordBoundary.indexIn(s, -1)) == -1) {
                break;
            }
            final StringBuilder sb = new StringBuilder(s.length() + 4 * this.wordSeparator.length());
            sb.append(caseFormat.normalizeFirstWord(s.substring(0, -1)));
            sb.append(caseFormat.wordSeparator);
            final int n = -1 + this.wordSeparator.length();
        }
        return caseFormat.normalizeFirstWord(s);
    }
    
    @Beta
    public Converter converterTo(final CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }
    
    abstract String normalizeWord(final String p0);
    
    private String normalizeFirstWord(final String s) {
        return (this == CaseFormat.LOWER_CAMEL) ? Ascii.toLowerCase(s) : this.normalizeWord(s);
    }
    
    private static String firstCharOnlyToUpper(final String s) {
        return s.isEmpty() ? s : new StringBuilder(s.length()).append(Ascii.toUpperCase(s.charAt(0))).append(Ascii.toLowerCase(s.substring(1))).toString();
    }
    
    CaseFormat(final String s, final int n, final CharMatcher charMatcher, final String s2, final CaseFormat$1 caseFormat) {
        this(s, n, charMatcher, s2);
    }
    
    static String access$100(final String s) {
        return firstCharOnlyToUpper(s);
    }
    
    static {
        $VALUES = new CaseFormat[] { CaseFormat.LOWER_HYPHEN, CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL, CaseFormat.UPPER_CAMEL, CaseFormat.UPPER_UNDERSCORE };
    }
    
    private static final class StringConverter extends Converter implements Serializable
    {
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;
        private static final long serialVersionUID = 0L;
        
        StringConverter(final CaseFormat caseFormat, final CaseFormat caseFormat2) {
            this.sourceFormat = (CaseFormat)Preconditions.checkNotNull(caseFormat);
            this.targetFormat = (CaseFormat)Preconditions.checkNotNull(caseFormat2);
        }
        
        protected String doForward(final String s) {
            return (s == null) ? null : this.sourceFormat.to(this.targetFormat, s);
        }
        
        protected String doBackward(final String s) {
            return (s == null) ? null : this.targetFormat.to(this.sourceFormat, s);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof StringConverter) {
                final StringConverter stringConverter = (StringConverter)o;
                return this.sourceFormat.equals(stringConverter.sourceFormat) && this.targetFormat.equals(stringConverter.targetFormat);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }
        
        @Override
        public String toString() {
            return this.sourceFormat + ".converterTo(" + this.targetFormat + ")";
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((String)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
    }
}
