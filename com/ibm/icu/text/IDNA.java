package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;

public abstract class IDNA
{
    public static final int DEFAULT = 0;
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int USE_STD3_RULES = 2;
    public static final int CHECK_BIDI = 4;
    public static final int CHECK_CONTEXTJ = 8;
    public static final int NONTRANSITIONAL_TO_ASCII = 16;
    public static final int NONTRANSITIONAL_TO_UNICODE = 32;
    public static final int CHECK_CONTEXTO = 64;
    
    public static IDNA getUTS46Instance(final int n) {
        return new UTS46(n);
    }
    
    public abstract StringBuilder labelToASCII(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder labelToUnicode(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder nameToASCII(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    public abstract StringBuilder nameToUnicode(final CharSequence p0, final StringBuilder p1, final Info p2);
    
    @Deprecated
    protected static void resetInfo(final Info info) {
        Info.access$000(info);
    }
    
    @Deprecated
    protected static boolean hasCertainErrors(final Info info, final EnumSet set) {
        return !Info.access$100(info).isEmpty() && !Collections.disjoint(Info.access$100(info), set);
    }
    
    @Deprecated
    protected static boolean hasCertainLabelErrors(final Info info, final EnumSet set) {
        return !Info.access$200(info).isEmpty() && !Collections.disjoint(Info.access$200(info), set);
    }
    
    @Deprecated
    protected static void addLabelError(final Info info, final Error error) {
        Info.access$200(info).add(error);
    }
    
    @Deprecated
    protected static void promoteAndResetLabelErrors(final Info info) {
        if (!Info.access$200(info).isEmpty()) {
            Info.access$100(info).addAll(Info.access$200(info));
            Info.access$200(info).clear();
        }
    }
    
    @Deprecated
    protected static void addError(final Info info, final Error error) {
        Info.access$100(info).add(error);
    }
    
    @Deprecated
    protected static void setTransitionalDifferent(final Info info) {
        Info.access$302(info, true);
    }
    
    @Deprecated
    protected static void setBiDi(final Info info) {
        Info.access$402(info, true);
    }
    
    @Deprecated
    protected static boolean isBiDi(final Info info) {
        return Info.access$400(info);
    }
    
    @Deprecated
    protected static void setNotOkBiDi(final Info info) {
        Info.access$502(info, false);
    }
    
    @Deprecated
    protected static boolean isOkBiDi(final Info info) {
        return Info.access$500(info);
    }
    
    @Deprecated
    protected IDNA() {
    }
    
    public static StringBuffer convertToASCII(final String s, final int n) throws StringPrepParseException {
        return convertToASCII(UCharacterIterator.getInstance(s), n);
    }
    
    public static StringBuffer convertToASCII(final StringBuffer sb, final int n) throws StringPrepParseException {
        return convertToASCII(UCharacterIterator.getInstance(sb), n);
    }
    
    public static StringBuffer convertToASCII(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        return IDNA2003.convertToASCII(uCharacterIterator, n);
    }
    
    public static StringBuffer convertIDNToASCII(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        return convertIDNToASCII(uCharacterIterator.getText(), n);
    }
    
    public static StringBuffer convertIDNToASCII(final StringBuffer sb, final int n) throws StringPrepParseException {
        return convertIDNToASCII(sb.toString(), n);
    }
    
    public static StringBuffer convertIDNToASCII(final String s, final int n) throws StringPrepParseException {
        return IDNA2003.convertIDNToASCII(s, n);
    }
    
    public static StringBuffer convertToUnicode(final String s, final int n) throws StringPrepParseException {
        return convertToUnicode(UCharacterIterator.getInstance(s), n);
    }
    
    public static StringBuffer convertToUnicode(final StringBuffer sb, final int n) throws StringPrepParseException {
        return convertToUnicode(UCharacterIterator.getInstance(sb), n);
    }
    
    public static StringBuffer convertToUnicode(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        return IDNA2003.convertToUnicode(uCharacterIterator, n);
    }
    
    public static StringBuffer convertIDNToUnicode(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        return convertIDNToUnicode(uCharacterIterator.getText(), n);
    }
    
    public static StringBuffer convertIDNToUnicode(final StringBuffer sb, final int n) throws StringPrepParseException {
        return convertIDNToUnicode(sb.toString(), n);
    }
    
    public static StringBuffer convertIDNToUnicode(final String s, final int n) throws StringPrepParseException {
        return IDNA2003.convertIDNToUnicode(s, n);
    }
    
    public static int compare(final StringBuffer sb, final StringBuffer sb2, final int n) throws StringPrepParseException {
        if (sb == null || sb2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(sb.toString(), sb2.toString(), n);
    }
    
    public static int compare(final String s, final String s2, final int n) throws StringPrepParseException {
        if (s == null || s2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(s, s2, n);
    }
    
    public static int compare(final UCharacterIterator uCharacterIterator, final UCharacterIterator uCharacterIterator2, final int n) throws StringPrepParseException {
        if (uCharacterIterator == null || uCharacterIterator2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(uCharacterIterator.getText(), uCharacterIterator2.getText(), n);
    }
    
    public enum Error
    {
        EMPTY_LABEL("EMPTY_LABEL", 0), 
        LABEL_TOO_LONG("LABEL_TOO_LONG", 1), 
        DOMAIN_NAME_TOO_LONG("DOMAIN_NAME_TOO_LONG", 2), 
        LEADING_HYPHEN("LEADING_HYPHEN", 3), 
        TRAILING_HYPHEN("TRAILING_HYPHEN", 4), 
        HYPHEN_3_4("HYPHEN_3_4", 5), 
        LEADING_COMBINING_MARK("LEADING_COMBINING_MARK", 6), 
        DISALLOWED("DISALLOWED", 7), 
        PUNYCODE("PUNYCODE", 8), 
        LABEL_HAS_DOT("LABEL_HAS_DOT", 9), 
        INVALID_ACE_LABEL("INVALID_ACE_LABEL", 10), 
        BIDI("BIDI", 11), 
        CONTEXTJ("CONTEXTJ", 12), 
        CONTEXTO_PUNCTUATION("CONTEXTO_PUNCTUATION", 13), 
        CONTEXTO_DIGITS("CONTEXTO_DIGITS", 14);
        
        private static final Error[] $VALUES;
        
        private Error(final String s, final int n) {
        }
        
        static {
            $VALUES = new Error[] { Error.EMPTY_LABEL, Error.LABEL_TOO_LONG, Error.DOMAIN_NAME_TOO_LONG, Error.LEADING_HYPHEN, Error.TRAILING_HYPHEN, Error.HYPHEN_3_4, Error.LEADING_COMBINING_MARK, Error.DISALLOWED, Error.PUNYCODE, Error.LABEL_HAS_DOT, Error.INVALID_ACE_LABEL, Error.BIDI, Error.CONTEXTJ, Error.CONTEXTO_PUNCTUATION, Error.CONTEXTO_DIGITS };
        }
    }
    
    public static final class Info
    {
        private EnumSet errors;
        private EnumSet labelErrors;
        private boolean isTransDiff;
        private boolean isBiDi;
        private boolean isOkBiDi;
        
        public Info() {
            this.errors = EnumSet.noneOf(Error.class);
            this.labelErrors = EnumSet.noneOf(Error.class);
            this.isTransDiff = false;
            this.isBiDi = false;
            this.isOkBiDi = true;
        }
        
        public boolean hasErrors() {
            return !this.errors.isEmpty();
        }
        
        public Set getErrors() {
            return this.errors;
        }
        
        public boolean isTransitionalDifferent() {
            return this.isTransDiff;
        }
        
        private void reset() {
            this.errors.clear();
            this.labelErrors.clear();
            this.isTransDiff = false;
            this.isBiDi = false;
            this.isOkBiDi = true;
        }
        
        static void access$000(final Info info) {
            info.reset();
        }
        
        static EnumSet access$100(final Info info) {
            return info.errors;
        }
        
        static EnumSet access$200(final Info info) {
            return info.labelErrors;
        }
        
        static boolean access$302(final Info info, final boolean isTransDiff) {
            return info.isTransDiff = isTransDiff;
        }
        
        static boolean access$402(final Info info, final boolean isBiDi) {
            return info.isBiDi = isBiDi;
        }
        
        static boolean access$400(final Info info) {
            return info.isBiDi;
        }
        
        static boolean access$502(final Info info, final boolean isOkBiDi) {
            return info.isOkBiDi = isOkBiDi;
        }
        
        static boolean access$500(final Info info) {
            return info.isOkBiDi;
        }
    }
}
