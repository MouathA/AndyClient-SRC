package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.impl.*;

public final class MessagePattern implements Cloneable, Freezable
{
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8;
    private static final int MAX_PREFIX_LENGTH = 24;
    private ApostropheMode aposMode;
    private String msg;
    private ArrayList parts;
    private ArrayList numericValues;
    private boolean hasArgNames;
    private boolean hasArgNumbers;
    private boolean needsAutoQuoting;
    private boolean frozen;
    private static final ApostropheMode defaultAposMode;
    private static final ArgType[] argTypes;
    static final boolean $assertionsDisabled;
    
    public MessagePattern() {
        this.parts = new ArrayList();
        this.aposMode = MessagePattern.defaultAposMode;
    }
    
    public MessagePattern(final ApostropheMode aposMode) {
        this.parts = new ArrayList();
        this.aposMode = aposMode;
    }
    
    public MessagePattern(final String s) {
        this.parts = new ArrayList();
        this.aposMode = MessagePattern.defaultAposMode;
        this.parse(s);
    }
    
    public MessagePattern parse(final String s) {
        this.preParse(s);
        this.parseMessage(0, 0, 0, ArgType.NONE);
        this.postParse();
        return this;
    }
    
    public MessagePattern parseChoiceStyle(final String s) {
        this.preParse(s);
        this.parseChoiceStyle(0, 0);
        this.postParse();
        return this;
    }
    
    public MessagePattern parsePluralStyle(final String s) {
        this.preParse(s);
        this.parsePluralOrSelectStyle(ArgType.PLURAL, 0, 0);
        this.postParse();
        return this;
    }
    
    public MessagePattern parseSelectStyle(final String s) {
        this.preParse(s);
        this.parsePluralOrSelectStyle(ArgType.SELECT, 0, 0);
        this.postParse();
        return this;
    }
    
    public void clear() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to clear() a frozen MessagePattern instance.");
        }
        this.msg = null;
        final boolean b = false;
        this.hasArgNumbers = b;
        this.hasArgNames = b;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }
    
    public void clearPatternAndSetApostropheMode(final ApostropheMode aposMode) {
        this.clear();
        this.aposMode = aposMode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MessagePattern messagePattern = (MessagePattern)o;
        if (this.aposMode.equals(messagePattern.aposMode)) {
            if (this.msg == null) {
                if (messagePattern.msg != null) {
                    return false;
                }
            }
            else if (!this.msg.equals(messagePattern.msg)) {
                return false;
            }
            if (this.parts.equals(messagePattern.parts)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (this.aposMode.hashCode() * 37 + ((this.msg != null) ? this.msg.hashCode() : 0)) * 37 + this.parts.hashCode();
    }
    
    public ApostropheMode getApostropheMode() {
        return this.aposMode;
    }
    
    boolean jdkAposMode() {
        return this.aposMode == ApostropheMode.DOUBLE_REQUIRED;
    }
    
    public String getPatternString() {
        return this.msg;
    }
    
    public boolean hasNamedArguments() {
        return this.hasArgNames;
    }
    
    public boolean hasNumberedArguments() {
        return this.hasArgNumbers;
    }
    
    @Override
    public String toString() {
        return this.msg;
    }
    
    public static int validateArgumentName(final String s) {
        if (!PatternProps.isIdentifier(s)) {
            return -2;
        }
        return parseArgNumber(s, 0, s.length());
    }
    
    public String autoQuoteApostropheDeep() {
        if (!this.needsAutoQuoting) {
            return this.msg;
        }
        StringBuilder append = null;
        int i = this.countParts();
        while (i > 0) {
            final Part part;
            if ((part = this.getPart(--i)).getType() == Part.Type.INSERT_CHAR) {
                if (append == null) {
                    append = new StringBuilder(this.msg.length() + 10).append(this.msg);
                }
                append.insert(Part.access$000(part), (char)Part.access$100(part));
            }
        }
        if (append == null) {
            return this.msg;
        }
        return append.toString();
    }
    
    public int countParts() {
        return this.parts.size();
    }
    
    public Part getPart(final int n) {
        return this.parts.get(n);
    }
    
    public Part.Type getPartType(final int n) {
        return Part.access$200(this.parts.get(n));
    }
    
    public int getPatternIndex(final int n) {
        return Part.access$000(this.parts.get(n));
    }
    
    public String getSubstring(final Part part) {
        final int access$000 = Part.access$000(part);
        return this.msg.substring(access$000, access$000 + Part.access$300(part));
    }
    
    public boolean partSubstringMatches(final Part part, final String s) {
        return this.msg.regionMatches(Part.access$000(part), s, 0, Part.access$300(part));
    }
    
    public double getNumericValue(final Part part) {
        final Part.Type access$200 = Part.access$200(part);
        if (access$200 == Part.Type.ARG_INT) {
            return Part.access$100(part);
        }
        if (access$200 == Part.Type.ARG_DOUBLE) {
            return this.numericValues.get(Part.access$100(part));
        }
        return -1.23456789E8;
    }
    
    public double getPluralOffset(final int n) {
        final Part part = this.parts.get(n);
        if (Part.access$200(part).hasNumericValue()) {
            return this.getNumericValue(part);
        }
        return 0.0;
    }
    
    public int getLimitPartIndex(final int n) {
        final int access$400 = Part.access$400(this.parts.get(n));
        if (access$400 < n) {
            return n;
        }
        return access$400;
    }
    
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    public MessagePattern cloneAsThawed() {
        final MessagePattern messagePattern = (MessagePattern)super.clone();
        messagePattern.parts = (ArrayList)this.parts.clone();
        if (this.numericValues != null) {
            messagePattern.numericValues = (ArrayList)this.numericValues.clone();
        }
        messagePattern.frozen = false;
        return messagePattern;
    }
    
    public MessagePattern freeze() {
        this.frozen = true;
        return this;
    }
    
    public boolean isFrozen() {
        return this.frozen;
    }
    
    private void preParse(final String msg) {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to parse(" + prefix(msg) + ") on frozen MessagePattern instance.");
        }
        this.msg = msg;
        final boolean b = false;
        this.hasArgNumbers = b;
        this.hasArgNames = b;
        this.needsAutoQuoting = false;
        this.parts.clear();
        if (this.numericValues != null) {
            this.numericValues.clear();
        }
    }
    
    private void postParse() {
    }
    
    private int parseMessage(int i, final int n, final int n2, final ArgType argType) {
        if (n2 > 32767) {
            throw new IndexOutOfBoundsException();
        }
        final int size = this.parts.size();
        this.addPart(Part.Type.MSG_START, i, n, n2);
        i += n;
        while (i < this.msg.length()) {
            final char char1 = this.msg.charAt(i++);
            if (char1 == '\'') {
                if (i == this.msg.length()) {
                    this.addPart(Part.Type.INSERT_CHAR, i, 0, 39);
                    this.needsAutoQuoting = true;
                }
                else {
                    final char char2 = this.msg.charAt(i);
                    if (char2 == '\'') {
                        this.addPart(Part.Type.SKIP_SYNTAX, i++, 1, 0);
                    }
                    else if (this.aposMode == ApostropheMode.DOUBLE_REQUIRED || char2 == '{' || char2 == '}' || (argType == ArgType.CHOICE && char2 == '|') || (argType.hasPluralStyle() && char2 == '#')) {
                        this.addPart(Part.Type.SKIP_SYNTAX, i - 1, 1, 0);
                        while (true) {
                            i = this.msg.indexOf(39, i + 1);
                            if (i < 0) {
                                i = this.msg.length();
                                this.addPart(Part.Type.INSERT_CHAR, i, 0, 39);
                                this.needsAutoQuoting = true;
                                break;
                            }
                            if (i + 1 >= this.msg.length() || this.msg.charAt(i + 1) != '\'') {
                                this.addPart(Part.Type.SKIP_SYNTAX, i++, 1, 0);
                                break;
                            }
                            this.addPart(Part.Type.SKIP_SYNTAX, ++i, 1, 0);
                        }
                    }
                    else {
                        this.addPart(Part.Type.INSERT_CHAR, i, 0, 39);
                        this.needsAutoQuoting = true;
                    }
                }
            }
            else if (argType.hasPluralStyle() && char1 == '#') {
                this.addPart(Part.Type.REPLACE_NUMBER, i - 1, 1, 0);
            }
            else if (char1 == '{') {
                i = this.parseArg(i - 1, 1, n2);
            }
            else {
                if ((n2 <= 0 || char1 != '}') && (argType != ArgType.CHOICE || char1 != '|')) {
                    continue;
                }
                this.addLimitPart(size, Part.Type.MSG_LIMIT, i - 1, (argType != ArgType.CHOICE || char1 != '}') ? 1 : 0, n2);
                if (argType == ArgType.CHOICE) {
                    return i - 1;
                }
                return i;
            }
        }
        if (n2 > 0 && !this.inTopLevelChoiceMessage(n2, argType)) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
        }
        this.addLimitPart(size, Part.Type.MSG_LIMIT, i, 0, n2);
        return i;
    }
    
    private int parseArg(int n, final int n2, final int n3) {
        final int size = this.parts.size();
        ArgType argType = ArgType.NONE;
        this.addPart(Part.Type.ARG_START, n, n2, argType.ordinal());
        final int skipWhiteSpace;
        n = (skipWhiteSpace = this.skipWhiteSpace(n + n2));
        if (n == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
        }
        n = this.skipIdentifier(n);
        final int argNumber = this.parseArgNumber(skipWhiteSpace, n);
        if (argNumber >= 0) {
            final int n4 = n - skipWhiteSpace;
            if (n4 > 65535 || argNumber > 32767) {
                throw new IndexOutOfBoundsException("Argument number too large: " + this.prefix(skipWhiteSpace));
            }
            this.hasArgNumbers = true;
            this.addPart(Part.Type.ARG_NUMBER, skipWhiteSpace, n4, argNumber);
        }
        else {
            if (argNumber != -1) {
                throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(skipWhiteSpace));
            }
            final int n5 = n - skipWhiteSpace;
            if (n5 > 65535) {
                throw new IndexOutOfBoundsException("Argument name too long: " + this.prefix(skipWhiteSpace));
            }
            this.hasArgNames = true;
            this.addPart(Part.Type.ARG_NAME, skipWhiteSpace, n5, 0);
        }
        n = this.skipWhiteSpace(n);
        if (n == this.msg.length()) {
            throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
        }
        final char char1 = this.msg.charAt(n);
        if (char1 != '}') {
            if (char1 != ',') {
                throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(skipWhiteSpace));
            }
            int skipWhiteSpace2;
            for (n = (skipWhiteSpace2 = this.skipWhiteSpace(n + 1)); n < this.msg.length() && isArgTypeChar(this.msg.charAt(n)); ++n) {}
            final int n6 = n - skipWhiteSpace2;
            n = this.skipWhiteSpace(n);
            if (n == this.msg.length()) {
                throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
            }
            final char char2;
            if (n6 == 0 || ((char2 = this.msg.charAt(n)) != ',' && char2 != '}')) {
                throw new IllegalArgumentException("Bad argument syntax: " + this.prefix(skipWhiteSpace));
            }
            if (n6 > 65535) {
                throw new IndexOutOfBoundsException("Argument type name too long: " + this.prefix(skipWhiteSpace));
            }
            argType = ArgType.SIMPLE;
            if (n6 == 6) {
                if (this.isChoice(skipWhiteSpace2)) {
                    argType = ArgType.CHOICE;
                }
                else if (this.isPlural(skipWhiteSpace2)) {
                    argType = ArgType.PLURAL;
                }
                else if (this.isSelect(skipWhiteSpace2)) {
                    argType = ArgType.SELECT;
                }
            }
            else if (n6 == 13 && this.isSelect(skipWhiteSpace2) && this.isOrdinal(skipWhiteSpace2 + 6)) {
                argType = ArgType.SELECTORDINAL;
            }
            Part.access$102(this.parts.get(size), (short)argType.ordinal());
            if (argType == ArgType.SIMPLE) {
                this.addPart(Part.Type.ARG_TYPE, skipWhiteSpace2, n6, 0);
            }
            if (char2 == '}') {
                if (argType != ArgType.SIMPLE) {
                    throw new IllegalArgumentException("No style field for complex argument: " + this.prefix(skipWhiteSpace));
                }
            }
            else {
                ++n;
                if (argType == ArgType.SIMPLE) {
                    n = this.parseSimpleStyle(n);
                }
                else if (argType == ArgType.CHOICE) {
                    n = this.parseChoiceStyle(n, n3);
                }
                else {
                    n = this.parsePluralOrSelectStyle(argType, n, n3);
                }
            }
        }
        this.addLimitPart(size, Part.Type.ARG_LIMIT, n, 1, argType.ordinal());
        return n + 1;
    }
    
    private int parseSimpleStyle(int i) {
        final int n = i;
        while (i < this.msg.length()) {
            final char char1 = this.msg.charAt(i++);
            if (char1 == '\'') {
                i = this.msg.indexOf(39, i);
                if (i < 0) {
                    throw new IllegalArgumentException("Quoted literal argument style text reaches to the end of the message: " + this.prefix(n));
                }
                ++i;
            }
            else if (char1 == '{') {
                int n2 = 0;
                ++n2;
            }
            else {
                if (char1 != '}') {
                    continue;
                }
                if (0 > 0) {
                    int n2 = 0;
                    --n2;
                }
                else {
                    final int n3 = --i - n;
                    if (n3 > 65535) {
                        throw new IndexOutOfBoundsException("Argument style text too long: " + this.prefix(n));
                    }
                    this.addPart(Part.Type.ARG_STYLE, n, n3, 0);
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Unmatched '{' braces in message " + this.prefix());
    }
    
    private int parseChoiceStyle(int n, final int n2) {
        final int n3 = n;
        n = this.skipWhiteSpace(n);
        if (n == this.msg.length() || this.msg.charAt(n) == '}') {
            throw new IllegalArgumentException("Missing choice argument pattern in " + this.prefix());
        }
        while (true) {
            final int n4 = n;
            n = this.skipDouble(n);
            final int n5 = n - n4;
            if (n5 == 0) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
            }
            if (n5 > 65535) {
                throw new IndexOutOfBoundsException("Choice number too long: " + this.prefix(n4));
            }
            this.parseDouble(n4, n, true);
            n = this.skipWhiteSpace(n);
            if (n == this.msg.length()) {
                throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
            }
            final char char1 = this.msg.charAt(n);
            if (char1 != '#' && char1 != '<' && char1 != '\u2264') {
                throw new IllegalArgumentException("Expected choice separator (#<\u2264) instead of '" + char1 + "' in choice pattern " + this.prefix(n3));
            }
            this.addPart(Part.Type.ARG_SELECTOR, n, 1, 0);
            n = this.parseMessage(++n, 0, n2 + 1, ArgType.CHOICE);
            if (n == this.msg.length()) {
                return n;
            }
            if (this.msg.charAt(n) == '}') {
                if (!this.inMessageFormatPattern(n2)) {
                    throw new IllegalArgumentException("Bad choice pattern syntax: " + this.prefix(n3));
                }
                return n;
            }
            else {
                n = this.skipWhiteSpace(n + 1);
            }
        }
    }
    
    private int parsePluralOrSelectStyle(final ArgType argType, int n, final int n2) {
        final int n3 = n;
        while (true) {
            n = this.skipWhiteSpace(n);
            final boolean b = n == this.msg.length();
            if (b || this.msg.charAt(n) == '}') {
                if (b == this.inMessageFormatPattern(n2)) {
                    throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                }
                if (!true) {
                    throw new IllegalArgumentException("Missing 'other' keyword in " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern in " + this.prefix());
                }
                return n;
            }
            else {
                final int n4 = n;
                if (argType.hasPluralStyle() && this.msg.charAt(n4) == '=') {
                    n = this.skipDouble(n + 1);
                    final int n5 = n - n4;
                    if (n5 == 1) {
                        throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                    }
                    if (n5 > 65535) {
                        throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(n4));
                    }
                    this.addPart(Part.Type.ARG_SELECTOR, n4, n5, 0);
                    this.parseDouble(n4 + 1, n, false);
                }
                else {
                    n = this.skipIdentifier(n);
                    final int n6 = n - n4;
                    if (n6 == 0) {
                        throw new IllegalArgumentException("Bad " + argType.toString().toLowerCase(Locale.ENGLISH) + " pattern syntax: " + this.prefix(n3));
                    }
                    if (argType.hasPluralStyle() && n6 == 6 && n < this.msg.length() && this.msg.regionMatches(n4, "offset:", 0, 7)) {
                        if (!false) {
                            throw new IllegalArgumentException("Plural argument 'offset:' (if present) must precede key-message pairs: " + this.prefix(n3));
                        }
                        final int skipWhiteSpace = this.skipWhiteSpace(n + 1);
                        n = this.skipDouble(skipWhiteSpace);
                        if (n == skipWhiteSpace) {
                            throw new IllegalArgumentException("Missing value for plural 'offset:' " + this.prefix(n3));
                        }
                        if (n - skipWhiteSpace > 65535) {
                            throw new IndexOutOfBoundsException("Plural offset value too long: " + this.prefix(skipWhiteSpace));
                        }
                        this.parseDouble(skipWhiteSpace, n, false);
                        continue;
                    }
                    else {
                        if (n6 > 65535) {
                            throw new IndexOutOfBoundsException("Argument selector too long: " + this.prefix(n4));
                        }
                        this.addPart(Part.Type.ARG_SELECTOR, n4, n6, 0);
                        if (this.msg.regionMatches(n4, "other", 0, n6)) {}
                    }
                }
                n = this.skipWhiteSpace(n);
                if (n == this.msg.length() || this.msg.charAt(n) != '{') {
                    throw new IllegalArgumentException("No message fragment after " + argType.toString().toLowerCase(Locale.ENGLISH) + " selector: " + this.prefix(n4));
                }
                n = this.parseMessage(n, 1, n2 + 1, argType);
            }
        }
    }
    
    private static int parseArgNumber(final CharSequence charSequence, int i, final int n) {
        if (i >= n) {
            return -2;
        }
        final char char1 = charSequence.charAt(i++);
        if (char1 == '0') {
            if (i == n) {
                return 0;
            }
        }
        else if ('1' > char1 || char1 > '9') {
            return -1;
        }
        while (i < n) {
            final char char2 = charSequence.charAt(i++);
            if ('0' > char2 || char2 > '9') {
                return -1;
            }
            if (0 >= 214748364) {}
        }
        if (true) {
            return -2;
        }
        return 0;
    }
    
    private int parseArgNumber(final int n, final int n2) {
        return parseArgNumber(this.msg, n, n2);
    }
    
    private void parseDouble(final int n, final int n2, final boolean b) {
        assert n < n2;
        int n3 = n;
        char c = this.msg.charAt(n3++);
        if (c == '-') {
            if (n3 == n2) {
                throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(n, n2));
            }
            c = this.msg.charAt(n3++);
        }
        else if (c == '+') {
            if (n3 == n2) {
                throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(n, n2));
            }
            c = this.msg.charAt(n3++);
        }
        if (c != '\u221e') {
            while ('0' <= c && c <= '9' && 0 <= 32768) {
                if (n3 == n2) {
                    this.addPart(Part.Type.ARG_INT, n, n2 - n, true ? 0 : 0);
                    return;
                }
                c = this.msg.charAt(n3++);
            }
            this.addArgDoublePart(Double.parseDouble(this.msg.substring(n, n2)), n, n2 - n);
            return;
        }
        if (b && n3 == n2) {
            this.addArgDoublePart(true ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, n, n2 - n);
            return;
        }
        throw new NumberFormatException("Bad syntax for numeric value: " + this.msg.substring(n, n2));
    }
    
    static void appendReducedApostrophes(final String s, int n, final int n2, final StringBuilder sb) {
        while (true) {
            final int index = s.indexOf(39, n);
            if (index < 0 || index >= n2) {
                break;
            }
            if (index == -1) {
                sb.append('\'');
                ++n;
            }
            else {
                sb.append(s, n, index);
                n = index + 1;
            }
        }
        sb.append(s, n, n2);
    }
    
    private int skipWhiteSpace(final int n) {
        return PatternProps.skipWhiteSpace(this.msg, n);
    }
    
    private int skipIdentifier(final int n) {
        return PatternProps.skipIdentifier(this.msg, n);
    }
    
    private int skipDouble(int i) {
        while (i < this.msg.length()) {
            final char char1 = this.msg.charAt(i);
            if (char1 < '0' && "+-.".indexOf(char1) < 0) {
                break;
            }
            if (char1 > '9' && char1 != 'e' && char1 != 'E' && char1 != '\u221e') {
                break;
            }
            ++i;
        }
        return i;
    }
    
    private static boolean isArgTypeChar(final int n) {
        return (97 <= n && n <= 122) || (65 <= n && n <= 90);
    }
    
    private boolean isChoice(int n) {
        final char char1;
        final char char2;
        final char char3;
        final char char4;
        final char char5;
        final char char6;
        return ((char1 = this.msg.charAt(n++)) == 'c' || char1 == 'C') && ((char2 = this.msg.charAt(n++)) == 'h' || char2 == 'H') && ((char3 = this.msg.charAt(n++)) == 'o' || char3 == 'O') && ((char4 = this.msg.charAt(n++)) == 'i' || char4 == 'I') && ((char5 = this.msg.charAt(n++)) == 'c' || char5 == 'C') && ((char6 = this.msg.charAt(n)) == 'e' || char6 == 'E');
    }
    
    private boolean isPlural(int n) {
        final char char1;
        final char char2;
        final char char3;
        final char char4;
        final char char5;
        final char char6;
        return ((char1 = this.msg.charAt(n++)) == 'p' || char1 == 'P') && ((char2 = this.msg.charAt(n++)) == 'l' || char2 == 'L') && ((char3 = this.msg.charAt(n++)) == 'u' || char3 == 'U') && ((char4 = this.msg.charAt(n++)) == 'r' || char4 == 'R') && ((char5 = this.msg.charAt(n++)) == 'a' || char5 == 'A') && ((char6 = this.msg.charAt(n)) == 'l' || char6 == 'L');
    }
    
    private boolean isSelect(int n) {
        final char char1;
        final char char2;
        final char char3;
        final char char4;
        final char char5;
        final char char6;
        return ((char1 = this.msg.charAt(n++)) == 's' || char1 == 'S') && ((char2 = this.msg.charAt(n++)) == 'e' || char2 == 'E') && ((char3 = this.msg.charAt(n++)) == 'l' || char3 == 'L') && ((char4 = this.msg.charAt(n++)) == 'e' || char4 == 'E') && ((char5 = this.msg.charAt(n++)) == 'c' || char5 == 'C') && ((char6 = this.msg.charAt(n)) == 't' || char6 == 'T');
    }
    
    private boolean isOrdinal(int n) {
        final char char1;
        final char char2;
        final char char3;
        final char char4;
        final char char5;
        final char char6;
        final char char7;
        return ((char1 = this.msg.charAt(n++)) == 'o' || char1 == 'O') && ((char2 = this.msg.charAt(n++)) == 'r' || char2 == 'R') && ((char3 = this.msg.charAt(n++)) == 'd' || char3 == 'D') && ((char4 = this.msg.charAt(n++)) == 'i' || char4 == 'I') && ((char5 = this.msg.charAt(n++)) == 'n' || char5 == 'N') && ((char6 = this.msg.charAt(n++)) == 'a' || char6 == 'A') && ((char7 = this.msg.charAt(n)) == 'l' || char7 == 'L');
    }
    
    private boolean inMessageFormatPattern(final int n) {
        return n > 0 || Part.access$200(this.parts.get(0)) == Part.Type.MSG_START;
    }
    
    private boolean inTopLevelChoiceMessage(final int n, final ArgType argType) {
        return n == 1 && argType == ArgType.CHOICE && Part.access$200(this.parts.get(0)) != Part.Type.MSG_START;
    }
    
    private void addPart(final Part.Type type, final int n, final int n2, final int n3) {
        this.parts.add(new Part(type, n, n2, n3, null));
    }
    
    private void addLimitPart(final int n, final Part.Type type, final int n2, final int n3, final int n4) {
        Part.access$402(this.parts.get(n), this.parts.size());
        this.addPart(type, n2, n3, n4);
    }
    
    private void addArgDoublePart(final double n, final int n2, final int n3) {
        if (this.numericValues == null) {
            this.numericValues = new ArrayList();
        }
        else {
            this.numericValues.size();
            if (0 > 32767) {
                throw new IndexOutOfBoundsException("Too many numeric values");
            }
        }
        this.numericValues.add(n);
        this.addPart(Part.Type.ARG_DOUBLE, n2, n3, 0);
    }
    
    private static String prefix(final String s, final int n) {
        final StringBuilder sb = new StringBuilder(44);
        if (n == 0) {
            sb.append("\"");
        }
        else {
            sb.append("[at pattern index ").append(n).append("] \"");
        }
        if (s.length() - n <= 24) {
            sb.append((n == 0) ? s : s.substring(n));
        }
        else {
            int n2 = n + 24 - 4;
            if (Character.isHighSurrogate(s.charAt(n2 - 1))) {
                --n2;
            }
            sb.append(s, n, n2).append(" ...");
        }
        return sb.append("\"").toString();
    }
    
    private static String prefix(final String s) {
        return prefix(s, 0);
    }
    
    private String prefix(final int n) {
        return prefix(this.msg, n);
    }
    
    private String prefix() {
        return prefix(this.msg, 0);
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    static ArgType[] access$500() {
        return MessagePattern.argTypes;
    }
    
    static {
        $assertionsDisabled = !MessagePattern.class.desiredAssertionStatus();
        defaultAposMode = ApostropheMode.valueOf(ICUConfig.get("com.ibm.icu.text.MessagePattern.ApostropheMode", "DOUBLE_OPTIONAL"));
        argTypes = ArgType.values();
    }
    
    public enum ArgType
    {
        NONE("NONE", 0), 
        SIMPLE("SIMPLE", 1), 
        CHOICE("CHOICE", 2), 
        PLURAL("PLURAL", 3), 
        SELECT("SELECT", 4), 
        SELECTORDINAL("SELECTORDINAL", 5);
        
        private static final ArgType[] $VALUES;
        
        private ArgType(final String s, final int n) {
        }
        
        public boolean hasPluralStyle() {
            return this == ArgType.PLURAL || this == ArgType.SELECTORDINAL;
        }
        
        static {
            $VALUES = new ArgType[] { ArgType.NONE, ArgType.SIMPLE, ArgType.CHOICE, ArgType.PLURAL, ArgType.SELECT, ArgType.SELECTORDINAL };
        }
    }
    
    public static final class Part
    {
        private static final int MAX_LENGTH = 65535;
        private static final int MAX_VALUE = 32767;
        private final Type type;
        private final int index;
        private final char length;
        private short value;
        private int limitPartIndex;
        
        private Part(final Type type, final int index, final int n, final int n2) {
            this.type = type;
            this.index = index;
            this.length = (char)n;
            this.value = (short)n2;
        }
        
        public Type getType() {
            return this.type;
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public int getLength() {
            return this.length;
        }
        
        public int getLimit() {
            return this.index + this.length;
        }
        
        public int getValue() {
            return this.value;
        }
        
        public ArgType getArgType() {
            final Type type = this.getType();
            if (type == Type.ARG_START || type == Type.ARG_LIMIT) {
                return MessagePattern.access$500()[this.value];
            }
            return ArgType.NONE;
        }
        
        @Override
        public String toString() {
            return this.type.name() + "(" + ((this.type == Type.ARG_START || this.type == Type.ARG_LIMIT) ? this.getArgType().name() : Integer.toString(this.value)) + ")@" + this.index;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Part part = (Part)o;
            return this.type.equals(part.type) && this.index == part.index && this.length == part.length && this.value == part.value && this.limitPartIndex == part.limitPartIndex;
        }
        
        @Override
        public int hashCode() {
            return ((this.type.hashCode() * 37 + this.index) * 37 + this.length) * 37 + this.value;
        }
        
        static int access$000(final Part part) {
            return part.index;
        }
        
        static short access$100(final Part part) {
            return part.value;
        }
        
        static Type access$200(final Part part) {
            return part.type;
        }
        
        static char access$300(final Part part) {
            return part.length;
        }
        
        static int access$400(final Part part) {
            return part.limitPartIndex;
        }
        
        static short access$102(final Part part, final short value) {
            return part.value = value;
        }
        
        Part(final Type type, final int n, final int n2, final int n3, final MessagePattern$1 object) {
            this(type, n, n2, n3);
        }
        
        static int access$402(final Part part, final int limitPartIndex) {
            return part.limitPartIndex = limitPartIndex;
        }
        
        public enum Type
        {
            MSG_START("MSG_START", 0), 
            MSG_LIMIT("MSG_LIMIT", 1), 
            SKIP_SYNTAX("SKIP_SYNTAX", 2), 
            INSERT_CHAR("INSERT_CHAR", 3), 
            REPLACE_NUMBER("REPLACE_NUMBER", 4), 
            ARG_START("ARG_START", 5), 
            ARG_LIMIT("ARG_LIMIT", 6), 
            ARG_NUMBER("ARG_NUMBER", 7), 
            ARG_NAME("ARG_NAME", 8), 
            ARG_TYPE("ARG_TYPE", 9), 
            ARG_STYLE("ARG_STYLE", 10), 
            ARG_SELECTOR("ARG_SELECTOR", 11), 
            ARG_INT("ARG_INT", 12), 
            ARG_DOUBLE("ARG_DOUBLE", 13);
            
            private static final Type[] $VALUES;
            
            private Type(final String s, final int n) {
            }
            
            public boolean hasNumericValue() {
                return this == Type.ARG_INT || this == Type.ARG_DOUBLE;
            }
            
            static {
                $VALUES = new Type[] { Type.MSG_START, Type.MSG_LIMIT, Type.SKIP_SYNTAX, Type.INSERT_CHAR, Type.REPLACE_NUMBER, Type.ARG_START, Type.ARG_LIMIT, Type.ARG_NUMBER, Type.ARG_NAME, Type.ARG_TYPE, Type.ARG_STYLE, Type.ARG_SELECTOR, Type.ARG_INT, Type.ARG_DOUBLE };
            }
        }
    }
    
    public enum ApostropheMode
    {
        DOUBLE_OPTIONAL("DOUBLE_OPTIONAL", 0), 
        DOUBLE_REQUIRED("DOUBLE_REQUIRED", 1);
        
        private static final ApostropheMode[] $VALUES;
        
        private ApostropheMode(final String s, final int n) {
        }
        
        static {
            $VALUES = new ApostropheMode[] { ApostropheMode.DOUBLE_OPTIONAL, ApostropheMode.DOUBLE_REQUIRED };
        }
    }
}
