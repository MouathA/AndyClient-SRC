package com.google.common.escape;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;

@Beta
@GwtCompatible
public final class Escapers
{
    private static final Escaper NULL_ESCAPER;
    
    private Escapers() {
    }
    
    public static Escaper nullEscaper() {
        return Escapers.NULL_ESCAPER;
    }
    
    public static Builder builder() {
        return new Builder(null);
    }
    
    static UnicodeEscaper asUnicodeEscaper(final Escaper escaper) {
        Preconditions.checkNotNull(escaper);
        if (escaper instanceof UnicodeEscaper) {
            return (UnicodeEscaper)escaper;
        }
        if (escaper instanceof CharEscaper) {
            return wrap((CharEscaper)escaper);
        }
        throw new IllegalArgumentException("Cannot create a UnicodeEscaper from: " + escaper.getClass().getName());
    }
    
    public static String computeReplacement(final CharEscaper charEscaper, final char c) {
        return stringOrNull(charEscaper.escape(c));
    }
    
    public static String computeReplacement(final UnicodeEscaper unicodeEscaper, final int n) {
        return stringOrNull(unicodeEscaper.escape(n));
    }
    
    private static String stringOrNull(final char[] array) {
        return (array == null) ? null : new String(array);
    }
    
    private static UnicodeEscaper wrap(final CharEscaper charEscaper) {
        return new UnicodeEscaper(charEscaper) {
            final CharEscaper val$escaper;
            
            @Override
            protected char[] escape(final int n) {
                if (n < 65536) {
                    return this.val$escaper.escape((char)n);
                }
                final char[] array = new char[2];
                Character.toChars(n, array, 0);
                final char[] escape = this.val$escaper.escape(array[0]);
                final char[] escape2 = this.val$escaper.escape(array[1]);
                if (escape == null && escape2 == null) {
                    return null;
                }
                final int n2 = (escape != null) ? escape.length : 1;
                final char[] array2 = new char[n2 + ((escape2 != null) ? escape2.length : 1)];
                int n3 = 0;
                if (escape != null) {
                    while (0 < escape.length) {
                        array2[0] = escape[0];
                        ++n3;
                    }
                }
                else {
                    array2[0] = array[0];
                }
                if (escape2 != null) {
                    while (0 < escape2.length) {
                        array2[n2 + 0] = escape2[0];
                        ++n3;
                    }
                }
                else {
                    array2[n2] = array[1];
                }
                return array2;
            }
        };
    }
    
    static {
        NULL_ESCAPER = new CharEscaper() {
            @Override
            public String escape(final String s) {
                return (String)Preconditions.checkNotNull(s);
            }
            
            @Override
            protected char[] escape(final char c) {
                return null;
            }
        };
    }
    
    @Beta
    public static final class Builder
    {
        private final Map replacementMap;
        private char safeMin;
        private char safeMax;
        private String unsafeReplacement;
        
        private Builder() {
            this.replacementMap = new HashMap();
            this.safeMin = '\0';
            this.safeMax = '\uffff';
            this.unsafeReplacement = null;
        }
        
        public Builder setSafeRange(final char safeMin, final char safeMax) {
            this.safeMin = safeMin;
            this.safeMax = safeMax;
            return this;
        }
        
        public Builder setUnsafeReplacement(@Nullable final String unsafeReplacement) {
            this.unsafeReplacement = unsafeReplacement;
            return this;
        }
        
        public Builder addEscape(final char c, final String s) {
            Preconditions.checkNotNull(s);
            this.replacementMap.put(c, s);
            return this;
        }
        
        public Escaper build() {
            return new ArrayBasedCharEscaper(this.replacementMap, this.safeMin, this.safeMax) {
                private final char[] replacementChars = (Builder.access$100(this.this$0) != null) ? Builder.access$100(this.this$0).toCharArray() : null;
                final Builder this$0;
                
                @Override
                protected char[] escapeUnsafe(final char c) {
                    return this.replacementChars;
                }
            };
        }
        
        Builder(final Escapers$1 charEscaper) {
            this();
        }
        
        static String access$100(final Builder builder) {
            return builder.unsafeReplacement;
        }
    }
}
