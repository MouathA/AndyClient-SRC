package org.apache.commons.lang3.text;

import java.util.*;
import org.apache.commons.lang3.*;

public class StrTokenizer implements ListIterator, Cloneable
{
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char[] chars;
    private String[] tokens;
    private int tokenPos;
    private StrMatcher delimMatcher;
    private StrMatcher quoteMatcher;
    private StrMatcher ignoredMatcher;
    private StrMatcher trimmerMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    
    private static StrTokenizer getCSVClone() {
        return (StrTokenizer)StrTokenizer.CSV_TOKENIZER_PROTOTYPE.clone();
    }
    
    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }
    
    public static StrTokenizer getCSVInstance(final String s) {
        final StrTokenizer csvClone = getCSVClone();
        csvClone.reset(s);
        return csvClone;
    }
    
    public static StrTokenizer getCSVInstance(final char[] array) {
        final StrTokenizer csvClone = getCSVClone();
        csvClone.reset(array);
        return csvClone;
    }
    
    private static StrTokenizer getTSVClone() {
        return (StrTokenizer)StrTokenizer.TSV_TOKENIZER_PROTOTYPE.clone();
    }
    
    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }
    
    public static StrTokenizer getTSVInstance(final String s) {
        final StrTokenizer tsvClone = getTSVClone();
        tsvClone.reset(s);
        return tsvClone;
    }
    
    public static StrTokenizer getTSVInstance(final char[] array) {
        final StrTokenizer tsvClone = getTSVClone();
        tsvClone.reset(array);
        return tsvClone;
    }
    
    public StrTokenizer() {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = null;
    }
    
    public StrTokenizer(final String s) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        if (s != null) {
            this.chars = s.toCharArray();
        }
        else {
            this.chars = null;
        }
    }
    
    public StrTokenizer(final String s, final char delimiterChar) {
        this(s);
        this.setDelimiterChar(delimiterChar);
    }
    
    public StrTokenizer(final String s, final String delimiterString) {
        this(s);
        this.setDelimiterString(delimiterString);
    }
    
    public StrTokenizer(final String s, final StrMatcher delimiterMatcher) {
        this(s);
        this.setDelimiterMatcher(delimiterMatcher);
    }
    
    public StrTokenizer(final String s, final char c, final char quoteChar) {
        this(s, c);
        this.setQuoteChar(quoteChar);
    }
    
    public StrTokenizer(final String s, final StrMatcher strMatcher, final StrMatcher quoteMatcher) {
        this(s, strMatcher);
        this.setQuoteMatcher(quoteMatcher);
    }
    
    public StrTokenizer(final char[] array) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = ArrayUtils.clone(array);
    }
    
    public StrTokenizer(final char[] array, final char delimiterChar) {
        this(array);
        this.setDelimiterChar(delimiterChar);
    }
    
    public StrTokenizer(final char[] array, final String delimiterString) {
        this(array);
        this.setDelimiterString(delimiterString);
    }
    
    public StrTokenizer(final char[] array, final StrMatcher delimiterMatcher) {
        this(array);
        this.setDelimiterMatcher(delimiterMatcher);
    }
    
    public StrTokenizer(final char[] array, final char c, final char quoteChar) {
        this(array, c);
        this.setQuoteChar(quoteChar);
    }
    
    public StrTokenizer(final char[] array, final StrMatcher strMatcher, final StrMatcher quoteMatcher) {
        this(array, strMatcher);
        this.setQuoteMatcher(quoteMatcher);
    }
    
    public int size() {
        this.checkTokenized();
        return this.tokens.length;
    }
    
    public String nextToken() {
        if (this.hasNext()) {
            return this.tokens[this.tokenPos++];
        }
        return null;
    }
    
    public String previousToken() {
        if (this.hasPrevious()) {
            final String[] tokens = this.tokens;
            final int tokenPos = this.tokenPos - 1;
            this.tokenPos = tokenPos;
            return tokens[tokenPos];
        }
        return null;
    }
    
    public String[] getTokenArray() {
        this.checkTokenized();
        return this.tokens.clone();
    }
    
    public List getTokenList() {
        this.checkTokenized();
        final ArrayList<String> list = new ArrayList<String>(this.tokens.length);
        final String[] tokens = this.tokens;
        while (0 < tokens.length) {
            list.add(tokens[0]);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public StrTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }
    
    public StrTokenizer reset(final String s) {
        this.reset();
        if (s != null) {
            this.chars = s.toCharArray();
        }
        else {
            this.chars = null;
        }
        return this;
    }
    
    public StrTokenizer reset(final char[] array) {
        this.reset();
        this.chars = ArrayUtils.clone(array);
        return this;
    }
    
    @Override
    public boolean hasNext() {
        this.checkTokenized();
        return this.tokenPos < this.tokens.length;
    }
    
    @Override
    public String next() {
        if (this.hasNext()) {
            return this.tokens[this.tokenPos++];
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public int nextIndex() {
        return this.tokenPos;
    }
    
    @Override
    public boolean hasPrevious() {
        this.checkTokenized();
        return this.tokenPos > 0;
    }
    
    @Override
    public String previous() {
        if (this.hasPrevious()) {
            final String[] tokens = this.tokens;
            final int tokenPos = this.tokenPos - 1;
            this.tokenPos = tokenPos;
            return tokens[tokenPos];
        }
        throw new NoSuchElementException();
    }
    
    @Override
    public int previousIndex() {
        return this.tokenPos - 1;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }
    
    public void set(final String s) {
        throw new UnsupportedOperationException("set() is unsupported");
    }
    
    public void add(final String s) {
        throw new UnsupportedOperationException("add() is unsupported");
    }
    
    private void checkTokenized() {
        if (this.tokens == null) {
            if (this.chars == null) {
                final List tokenize = this.tokenize(null, 0, 0);
                this.tokens = tokenize.toArray(new String[tokenize.size()]);
            }
            else {
                final List tokenize2 = this.tokenize(this.chars, 0, this.chars.length);
                this.tokens = tokenize2.toArray(new String[tokenize2.size()]);
            }
        }
    }
    
    protected List tokenize(final char[] array, final int n, final int n2) {
        if (array == null || n2 == 0) {
            return Collections.emptyList();
        }
        final StrBuilder strBuilder = new StrBuilder();
        final ArrayList list = new ArrayList();
        int nextToken = n;
        while (nextToken >= 0 && nextToken < n2) {
            nextToken = this.readNextToken(array, nextToken, n2, strBuilder, list);
            if (nextToken >= n2) {
                this.addToken(list, "");
            }
        }
        return list;
    }
    
    private void addToken(final List list, String s) {
        if (StringUtils.isEmpty(s)) {
            if (this.isIgnoreEmptyTokens()) {
                return;
            }
            if (this.isEmptyTokenAsNull()) {
                s = null;
            }
        }
        list.add(s);
    }
    
    private int readNextToken(final char[] array, int i, final int n, final StrBuilder strBuilder, final List list) {
        while (i < n) {
            final int max = Math.max(this.getIgnoredMatcher().isMatch(array, i, i, n), this.getTrimmerMatcher().isMatch(array, i, i, n));
            if (max == 0 || this.getDelimiterMatcher().isMatch(array, i, i, n) > 0) {
                break;
            }
            if (this.getQuoteMatcher().isMatch(array, i, i, n) > 0) {
                break;
            }
            i += max;
        }
        if (i >= n) {
            this.addToken(list, "");
            return -1;
        }
        final int match = this.getDelimiterMatcher().isMatch(array, i, i, n);
        if (match > 0) {
            this.addToken(list, "");
            return i + match;
        }
        final int match2 = this.getQuoteMatcher().isMatch(array, i, i, n);
        if (match2 > 0) {
            return this.readWithQuotes(array, i + match2, n, strBuilder, list, i, match2);
        }
        return this.readWithQuotes(array, i, n, strBuilder, list, 0, 0);
    }
    
    private int readWithQuotes(final char[] array, final int n, final int n2, final StrBuilder strBuilder, final List list, final int n3, final int n4) {
        strBuilder.clear();
        int i = n;
        final boolean b = n4 > 0;
        while (i < n2) {
            if (true) {
                if (this.isQuote(array, i, n2, n3, n4)) {
                    if (this.isQuote(array, i + n4, n2, n3, n4)) {
                        strBuilder.append(array, i, n4);
                        i += n4 * 2;
                        strBuilder.size();
                    }
                    else {
                        i += n4;
                    }
                }
                else {
                    strBuilder.append(array[i++]);
                    strBuilder.size();
                }
            }
            else {
                final int match = this.getDelimiterMatcher().isMatch(array, i, n, n2);
                if (match > 0) {
                    this.addToken(list, strBuilder.substring(0, 0));
                    return i + match;
                }
                if (n4 > 0 && this.isQuote(array, i, n2, n3, n4)) {
                    i += n4;
                }
                else {
                    final int match2 = this.getIgnoredMatcher().isMatch(array, i, n, n2);
                    if (match2 > 0) {
                        i += match2;
                    }
                    else {
                        final int match3 = this.getTrimmerMatcher().isMatch(array, i, n, n2);
                        if (match3 > 0) {
                            strBuilder.append(array, i, match3);
                            i += match3;
                        }
                        else {
                            strBuilder.append(array[i++]);
                            strBuilder.size();
                        }
                    }
                }
            }
        }
        this.addToken(list, strBuilder.substring(0, 0));
        return -1;
    }
    
    private boolean isQuote(final char[] array, final int n, final int n2, final int n3, final int n4) {
        while (0 < n4) {
            if (n + 0 >= n2 || array[n + 0] != array[n3 + 0]) {
                return false;
            }
            int n5 = 0;
            ++n5;
        }
        return true;
    }
    
    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }
    
    public StrTokenizer setDelimiterMatcher(final StrMatcher delimMatcher) {
        if (delimMatcher == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        }
        else {
            this.delimMatcher = delimMatcher;
        }
        return this;
    }
    
    public StrTokenizer setDelimiterChar(final char c) {
        return this.setDelimiterMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrTokenizer setDelimiterString(final String s) {
        return this.setDelimiterMatcher(StrMatcher.stringMatcher(s));
    }
    
    public StrMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }
    
    public StrTokenizer setQuoteMatcher(final StrMatcher quoteMatcher) {
        if (quoteMatcher != null) {
            this.quoteMatcher = quoteMatcher;
        }
        return this;
    }
    
    public StrTokenizer setQuoteChar(final char c) {
        return this.setQuoteMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }
    
    public StrTokenizer setIgnoredMatcher(final StrMatcher ignoredMatcher) {
        if (ignoredMatcher != null) {
            this.ignoredMatcher = ignoredMatcher;
        }
        return this;
    }
    
    public StrTokenizer setIgnoredChar(final char c) {
        return this.setIgnoredMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrMatcher getTrimmerMatcher() {
        return this.trimmerMatcher;
    }
    
    public StrTokenizer setTrimmerMatcher(final StrMatcher trimmerMatcher) {
        if (trimmerMatcher != null) {
            this.trimmerMatcher = trimmerMatcher;
        }
        return this;
    }
    
    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }
    
    public StrTokenizer setEmptyTokenAsNull(final boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
        return this;
    }
    
    public boolean isIgnoreEmptyTokens() {
        return this.ignoreEmptyTokens;
    }
    
    public StrTokenizer setIgnoreEmptyTokens(final boolean ignoreEmptyTokens) {
        this.ignoreEmptyTokens = ignoreEmptyTokens;
        return this;
    }
    
    public String getContent() {
        if (this.chars == null) {
            return null;
        }
        return new String(this.chars);
    }
    
    public Object clone() {
        return this.cloneReset();
    }
    
    Object cloneReset() throws CloneNotSupportedException {
        final StrTokenizer strTokenizer = (StrTokenizer)super.clone();
        if (strTokenizer.chars != null) {
            strTokenizer.chars = strTokenizer.chars.clone();
        }
        strTokenizer.reset();
        return strTokenizer;
    }
    
    @Override
    public String toString() {
        if (this.tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + this.getTokenList();
    }
    
    @Override
    public void add(final Object o) {
        this.add((String)o);
    }
    
    @Override
    public void set(final Object o) {
        this.set((String)o);
    }
    
    @Override
    public Object previous() {
        return this.previous();
    }
    
    @Override
    public Object next() {
        return this.next();
    }
    
    static {
        (CSV_TOKENIZER_PROTOTYPE = new StrTokenizer()).setDelimiterMatcher(StrMatcher.commaMatcher());
        StrTokenizer.CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        StrTokenizer.CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        StrTokenizer.CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        StrTokenizer.CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        StrTokenizer.CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
        (TSV_TOKENIZER_PROTOTYPE = new StrTokenizer()).setDelimiterMatcher(StrMatcher.tabMatcher());
        StrTokenizer.TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        StrTokenizer.TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        StrTokenizer.TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        StrTokenizer.TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        StrTokenizer.TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
    }
}
