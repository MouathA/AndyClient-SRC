package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public class PatternTokenizer
{
    private UnicodeSet ignorableCharacters;
    private UnicodeSet syntaxCharacters;
    private UnicodeSet extraQuotingCharacters;
    private UnicodeSet escapeCharacters;
    private boolean usingSlash;
    private boolean usingQuote;
    private transient UnicodeSet needingQuoteCharacters;
    private int start;
    private int limit;
    private String pattern;
    public static final char SINGLE_QUOTE = '\'';
    public static final char BACK_SLASH = '\\';
    public static final int DONE = 0;
    public static final int SYNTAX = 1;
    public static final int LITERAL = 2;
    public static final int BROKEN_QUOTE = 3;
    public static final int BROKEN_ESCAPE = 4;
    public static final int UNKNOWN = 5;
    private static final int AFTER_QUOTE = -1;
    private static final int NONE = 0;
    private static final int START_QUOTE = 1;
    private static final int NORMAL_QUOTE = 2;
    private static final int SLASH_START = 3;
    private static final int HEX = 4;
    
    public PatternTokenizer() {
        this.ignorableCharacters = new UnicodeSet();
        this.syntaxCharacters = new UnicodeSet();
        this.extraQuotingCharacters = new UnicodeSet();
        this.escapeCharacters = new UnicodeSet();
        this.usingSlash = false;
        this.usingQuote = false;
        this.needingQuoteCharacters = null;
    }
    
    public UnicodeSet getIgnorableCharacters() {
        return (UnicodeSet)this.ignorableCharacters.clone();
    }
    
    public PatternTokenizer setIgnorableCharacters(final UnicodeSet set) {
        this.ignorableCharacters = (UnicodeSet)set.clone();
        this.needingQuoteCharacters = null;
        return this;
    }
    
    public UnicodeSet getSyntaxCharacters() {
        return (UnicodeSet)this.syntaxCharacters.clone();
    }
    
    public UnicodeSet getExtraQuotingCharacters() {
        return (UnicodeSet)this.extraQuotingCharacters.clone();
    }
    
    public PatternTokenizer setSyntaxCharacters(final UnicodeSet set) {
        this.syntaxCharacters = (UnicodeSet)set.clone();
        this.needingQuoteCharacters = null;
        return this;
    }
    
    public PatternTokenizer setExtraQuotingCharacters(final UnicodeSet set) {
        this.extraQuotingCharacters = (UnicodeSet)set.clone();
        this.needingQuoteCharacters = null;
        return this;
    }
    
    public UnicodeSet getEscapeCharacters() {
        return (UnicodeSet)this.escapeCharacters.clone();
    }
    
    public PatternTokenizer setEscapeCharacters(final UnicodeSet set) {
        this.escapeCharacters = (UnicodeSet)set.clone();
        return this;
    }
    
    public boolean isUsingQuote() {
        return this.usingQuote;
    }
    
    public PatternTokenizer setUsingQuote(final boolean usingQuote) {
        this.usingQuote = usingQuote;
        this.needingQuoteCharacters = null;
        return this;
    }
    
    public boolean isUsingSlash() {
        return this.usingSlash;
    }
    
    public PatternTokenizer setUsingSlash(final boolean usingSlash) {
        this.usingSlash = usingSlash;
        this.needingQuoteCharacters = null;
        return this;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public PatternTokenizer setLimit(final int limit) {
        this.limit = limit;
        return this;
    }
    
    public int getStart() {
        return this.start;
    }
    
    public PatternTokenizer setStart(final int start) {
        this.start = start;
        return this;
    }
    
    public PatternTokenizer setPattern(final CharSequence charSequence) {
        return this.setPattern(charSequence.toString());
    }
    
    public PatternTokenizer setPattern(final String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Inconsistent arguments");
        }
        this.start = 0;
        this.limit = pattern.length();
        this.pattern = pattern;
        return this;
    }
    
    public String quoteLiteral(final CharSequence charSequence) {
        return this.quoteLiteral(charSequence.toString());
    }
    
    public String quoteLiteral(final String s) {
        if (this.needingQuoteCharacters == null) {
            this.needingQuoteCharacters = new UnicodeSet().addAll(this.syntaxCharacters).addAll(this.ignorableCharacters).addAll(this.extraQuotingCharacters);
            if (this.usingSlash) {
                this.needingQuoteCharacters.add(92);
            }
            if (this.usingQuote) {
                this.needingQuoteCharacters.add(39);
            }
        }
        final StringBuffer sb = new StringBuffer();
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            if (this.escapeCharacters.contains(char1)) {
                if (-1 == -2) {
                    sb.append('\'');
                }
                this.appendEscaped(sb, char1);
            }
            else if (this.needingQuoteCharacters.contains(char1)) {
                if (-1 == -2) {
                    UTF16.append(sb, char1);
                    if (this.usingQuote && char1 == 39) {
                        sb.append('\'');
                    }
                }
                else if (this.usingSlash) {
                    sb.append('\\');
                    UTF16.append(sb, char1);
                }
                else if (this.usingQuote) {
                    if (char1 == 39) {
                        sb.append('\'');
                        sb.append('\'');
                    }
                    else {
                        sb.append('\'');
                        UTF16.append(sb, char1);
                    }
                }
                else {
                    this.appendEscaped(sb, char1);
                }
            }
            else {
                if (-1 == -2) {
                    sb.append('\'');
                }
                UTF16.append(sb, char1);
            }
            final int n = 0 + UTF16.getCharCount(char1);
        }
        if (-1 == -2) {
            sb.append('\'');
        }
        return sb.toString();
    }
    
    private void appendEscaped(final StringBuffer sb, final int n) {
        if (n <= 65535) {
            sb.append("\\u").append(Utility.hex(n, 4));
        }
        else {
            sb.append("\\U").append(Utility.hex(n, 8));
        }
    }
    
    public String normalize() {
        final int start = this.start;
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        while (true) {
            sb2.setLength(0);
            final int next = this.next(sb2);
            if (next == 0) {
                break;
            }
            if (next != 1) {
                sb.append(this.quoteLiteral(sb2));
            }
            else {
                sb.append(sb2);
            }
        }
        this.start = start;
        return sb.toString();
    }
    
    public int next(final StringBuffer sb) {
        if (this.start >= this.limit) {
            return 0;
        }
        int char1;
        for (int i = this.start; i < this.limit; i += UTF16.getCharCount(char1)) {
            char1 = UTF16.charAt(this.pattern, i);
            Label_0491: {
                switch (true) {
                    case 3: {
                        switch (char1) {
                            case 117: {}
                            case 85: {
                                continue;
                            }
                            default: {
                                if (this.usingSlash) {
                                    UTF16.append(sb, char1);
                                    continue;
                                }
                                sb.append('\\');
                                break Label_0491;
                            }
                        }
                        break;
                    }
                    case 4: {
                        int n = 0 + char1;
                        switch (char1) {
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57: {
                                n -= 48;
                                break;
                            }
                            case 97:
                            case 98:
                            case 99:
                            case 100:
                            case 101:
                            case 102: {
                                n -= 87;
                                break;
                            }
                            case 65:
                            case 66:
                            case 67:
                            case 68:
                            case 69:
                            case 70: {
                                n -= 55;
                                break;
                            }
                            default: {
                                this.start = i;
                                return 4;
                            }
                        }
                        int n2 = 0;
                        --n2;
                        if (8 == 0) {
                            UTF16.append(sb, 0);
                        }
                        continue;
                    }
                    case -1: {
                        if (char1 == 5) {
                            UTF16.append(sb, char1);
                            continue;
                        }
                        break;
                    }
                    case 1: {
                        if (char1 == 5) {
                            UTF16.append(sb, char1);
                            continue;
                        }
                        UTF16.append(sb, char1);
                        continue;
                    }
                    case 2: {
                        if (char1 == 5) {
                            continue;
                        }
                        UTF16.append(sb, char1);
                        continue;
                    }
                }
            }
            if (!this.ignorableCharacters.contains(char1)) {
                if (this.syntaxCharacters.contains(char1)) {
                    if (3 == 5) {
                        UTF16.append(sb, char1);
                        this.start = i + UTF16.getCharCount(char1);
                        return 1;
                    }
                    this.start = i;
                    return 3;
                }
                else if (char1 != 92) {
                    if (!this.usingQuote || char1 != 39) {
                        UTF16.append(sb, char1);
                    }
                }
            }
        }
        this.start = this.limit;
        switch (true) {
            case 3: {
                if (this.usingSlash) {
                    break;
                }
                sb.append('\\');
                break;
            }
        }
        return 3;
    }
}
