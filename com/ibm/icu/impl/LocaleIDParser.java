package com.ibm.icu.impl;

import com.ibm.icu.impl.locale.*;
import java.util.*;

public final class LocaleIDParser
{
    private char[] id;
    private int index;
    private StringBuilder buffer;
    private boolean canonicalize;
    private boolean hadCountry;
    Map keywords;
    String baseName;
    private static final char KEYWORD_SEPARATOR = '@';
    private static final char HYPHEN = '-';
    private static final char KEYWORD_ASSIGN = '=';
    private static final char COMMA = ',';
    private static final char ITEM_SEPARATOR = ';';
    private static final char DOT = '.';
    private static final char UNDERSCORE = '_';
    private static final char DONE = '\uffff';
    
    public LocaleIDParser(final String s) {
        this(s, false);
    }
    
    public LocaleIDParser(final String s, final boolean canonicalize) {
        this.id = s.toCharArray();
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
        this.canonicalize = canonicalize;
    }
    
    private void reset() {
        this.index = 0;
        this.buffer = new StringBuilder(this.id.length + 5);
    }
    
    private void append(final char c) {
        this.buffer.append(c);
    }
    
    private void addSeparator() {
        this.append('_');
    }
    
    private String getString(final int n) {
        return this.buffer.substring(n);
    }
    
    private void set(final int n, final String s) {
        this.buffer.delete(n, this.buffer.length());
        this.buffer.insert(n, s);
    }
    
    private void append(final String s) {
        this.buffer.append(s);
    }
    
    private char next() {
        if (this.index == this.id.length) {
            ++this.index;
            return '\uffff';
        }
        return this.id[this.index++];
    }
    
    private void skipUntilTerminatorOrIDSeparator() {
        while (!this.isTerminatorOrIDSeparator(this.next())) {}
        --this.index;
    }
    
    private boolean atTerminator() {
        return this.index >= this.id.length || this.isTerminator(this.id[this.index]);
    }
    
    private boolean isTerminator(final char c) {
        return c == '@' || c == '\uffff' || c == '.';
    }
    
    private boolean isTerminatorOrIDSeparator(final char c) {
        return c == '_' || c == '-' || this.isTerminator(c);
    }
    
    private boolean haveExperimentalLanguagePrefix() {
        if (this.id.length > 2) {
            final char c = this.id[1];
            if (c == '-' || c == '_') {
                final char c2 = this.id[0];
                return c2 == 'x' || c2 == 'X' || c2 == 'i' || c2 == 'I';
            }
        }
        return false;
    }
    
    private boolean haveKeywordAssign() {
        for (int i = this.index; i < this.id.length; ++i) {
            if (this.id[i] == '=') {
                return true;
            }
        }
        return false;
    }
    
    private int parseLanguage() {
        final int length = this.buffer.length();
        if (this.haveExperimentalLanguagePrefix()) {
            this.append(AsciiUtil.toLower(this.id[0]));
            this.append('-');
            this.index = 2;
        }
        char next;
        while (!this.isTerminatorOrIDSeparator(next = this.next())) {
            this.append(AsciiUtil.toLower(next));
        }
        --this.index;
        if (this.buffer.length() - length == 3) {
            final String threeToTwoLetterLanguage = LocaleIDs.threeToTwoLetterLanguage(this.getString(0));
            if (threeToTwoLetterLanguage != null) {
                this.set(0, threeToTwoLetterLanguage);
            }
        }
        return 0;
    }
    
    private void skipLanguage() {
        if (this.haveExperimentalLanguagePrefix()) {
            this.index = 2;
        }
        this.skipUntilTerminatorOrIDSeparator();
    }
    
    private int parseScript() {
        if (!this.atTerminator()) {
            final int index = this.index;
            ++this.index;
            int length = this.buffer.length();
            char next;
            while (!this.isTerminatorOrIDSeparator(next = this.next()) && AsciiUtil.isAlpha(next)) {
                if (false) {
                    this.addSeparator();
                    this.append(AsciiUtil.toUpper(next));
                }
                else {
                    this.append(AsciiUtil.toLower(next));
                }
            }
            --this.index;
            if (this.index - index != 5) {
                this.index = index;
                this.buffer.delete(length, this.buffer.length());
            }
            else {
                ++length;
            }
            return length;
        }
        return this.buffer.length();
    }
    
    private void skipScript() {
        if (!this.atTerminator()) {
            final int index = this.index;
            ++this.index;
            char next;
            while (!this.isTerminatorOrIDSeparator(next = this.next()) && AsciiUtil.isAlpha(next)) {}
            --this.index;
            if (this.index - index != 5) {
                this.index = index;
            }
        }
    }
    
    private int parseCountry() {
        if (!this.atTerminator()) {
            final int index = this.index;
            ++this.index;
            int length = this.buffer.length();
            char next;
            while (!this.isTerminatorOrIDSeparator(next = this.next())) {
                if (false) {
                    this.hadCountry = true;
                    this.addSeparator();
                    ++length;
                }
                this.append(AsciiUtil.toUpper(next));
            }
            --this.index;
            final int n = this.buffer.length() - length;
            if (n != 0) {
                if (n < 2 || n > 3) {
                    this.index = index;
                    --length;
                    this.buffer.delete(length, this.buffer.length());
                    this.hadCountry = false;
                }
                else if (n == 3) {
                    final String threeToTwoLetterRegion = LocaleIDs.threeToTwoLetterRegion(this.getString(length));
                    if (threeToTwoLetterRegion != null) {
                        this.set(length, threeToTwoLetterRegion);
                    }
                }
            }
            return length;
        }
        return this.buffer.length();
    }
    
    private void skipCountry() {
        if (!this.atTerminator()) {
            if (this.id[this.index] == '_' || this.id[this.index] == '-') {
                ++this.index;
            }
            final int index = this.index;
            this.skipUntilTerminatorOrIDSeparator();
            final int n = this.index - index;
            if (n < 2 || n > 3) {
                this.index = index;
            }
        }
    }
    
    private int parseVariant() {
        int length = this.buffer.length();
        char next;
        while ((next = this.next()) != '\uffff') {
            if (95 == 46) {
                continue;
            }
            if (95 == 64) {
                if (this.haveKeywordAssign()) {
                    break;
                }
                continue;
            }
            else if (false) {
                if (95 == 95 || 95 == 45) {
                    continue;
                }
                --this.index;
            }
            else {
                if (false) {
                    continue;
                }
                if (false) {
                    if (false && !this.hadCountry) {
                        this.addSeparator();
                        ++length;
                    }
                    this.addSeparator();
                    if (false) {
                        ++length;
                    }
                }
                AsciiUtil.toUpper('_');
                if (95 == 45 || 95 == 44) {}
                this.append('_');
            }
        }
        --this.index;
        return length;
    }
    
    public String getLanguage() {
        this.reset();
        return this.getString(this.parseLanguage());
    }
    
    public String getScript() {
        this.reset();
        this.skipLanguage();
        return this.getString(this.parseScript());
    }
    
    public String getCountry() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        return this.getString(this.parseCountry());
    }
    
    public String getVariant() {
        this.reset();
        this.skipLanguage();
        this.skipScript();
        this.skipCountry();
        return this.getString(this.parseVariant());
    }
    
    public String[] getLanguageScriptCountryVariant() {
        this.reset();
        return new String[] { this.getString(this.parseLanguage()), this.getString(this.parseScript()), this.getString(this.parseCountry()), this.getString(this.parseVariant()) };
    }
    
    public void setBaseName(final String baseName) {
        this.baseName = baseName;
    }
    
    public void parseBaseName() {
        if (this.baseName != null) {
            this.set(0, this.baseName);
        }
        else {
            this.reset();
            this.parseLanguage();
            this.parseScript();
            this.parseCountry();
            this.parseVariant();
            final int length = this.buffer.length();
            if (length > 0 && this.buffer.charAt(length - 1) == '_') {
                this.buffer.deleteCharAt(length - 1);
            }
        }
    }
    
    public String getBaseName() {
        if (this.baseName != null) {
            return this.baseName;
        }
        this.parseBaseName();
        return this.getString(0);
    }
    
    public String getName() {
        this.parseBaseName();
        this.parseKeywords();
        return this.getString(0);
    }
    
    private boolean setToKeywordStart() {
        int i = this.index;
        while (i < this.id.length) {
            if (this.id[i] == '@') {
                if (this.canonicalize) {
                    for (int j = ++i; j < this.id.length; ++j) {
                        if (this.id[j] == '=') {
                            this.index = i;
                            return true;
                        }
                    }
                    break;
                }
                if (++i < this.id.length) {
                    this.index = i;
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    private static boolean isDoneOrKeywordAssign(final char c) {
        return c == '\uffff' || c == '=';
    }
    
    private static boolean isDoneOrItemSeparator(final char c) {
        return c == '\uffff' || c == ';';
    }
    
    private String getKeyword() {
        final int index = this.index;
        while (!isDoneOrKeywordAssign(this.next())) {}
        --this.index;
        return AsciiUtil.toLowerString(new String(this.id, index, this.index - index).trim());
    }
    
    private String getValue() {
        final int index = this.index;
        while (!isDoneOrItemSeparator(this.next())) {}
        --this.index;
        return new String(this.id, index, this.index - index).trim();
    }
    
    private Comparator getKeyComparator() {
        return new Comparator() {
            final LocaleIDParser this$0;
            
            public int compare(final String s, final String s2) {
                return s.compareTo(s2);
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((String)o, (String)o2);
            }
        };
    }
    
    public Map getKeywordMap() {
        if (this.keywords == null) {
            TreeMap<Object, Object> treeMap = null;
            if (this.setToKeywordStart()) {
                do {
                    final String keyword = this.getKeyword();
                    if (keyword.length() == 0) {
                        break;
                    }
                    final char next = this.next();
                    if (next != '=') {
                        if (next == '\uffff') {
                            break;
                        }
                        continue;
                    }
                    else {
                        final String value = this.getValue();
                        if (value.length() == 0) {
                            continue;
                        }
                        if (treeMap == null) {
                            treeMap = new TreeMap<Object, Object>(this.getKeyComparator());
                        }
                        else if (treeMap.containsKey(keyword)) {
                            continue;
                        }
                        treeMap.put(keyword, value);
                    }
                } while (this.next() == ';');
            }
            this.keywords = ((treeMap != null) ? treeMap : Collections.emptyMap());
        }
        return this.keywords;
    }
    
    private int parseKeywords() {
        int length = this.buffer.length();
        final Map keywordMap = this.getKeywordMap();
        if (!keywordMap.isEmpty()) {
            for (final Map.Entry<String, V> entry : keywordMap.entrySet()) {
                this.append(false ? '@' : ';');
                this.append(entry.getKey());
                this.append('=');
                this.append((String)entry.getValue());
            }
            if (!false) {
                ++length;
            }
        }
        return length;
    }
    
    public Iterator getKeywords() {
        final Map keywordMap = this.getKeywordMap();
        return keywordMap.isEmpty() ? null : keywordMap.keySet().iterator();
    }
    
    public String getKeywordValue(final String s) {
        final Map keywordMap = this.getKeywordMap();
        return keywordMap.isEmpty() ? null : keywordMap.get(AsciiUtil.toLowerString(s.trim()));
    }
    
    public void defaultKeywordValue(final String s, final String s2) {
        this.setKeywordValue(s, s2, false);
    }
    
    public void setKeywordValue(final String s, final String s2) {
        this.setKeywordValue(s, s2, true);
    }
    
    private void setKeywordValue(String lowerString, String trim, final boolean b) {
        if (lowerString == null) {
            if (b) {
                this.keywords = Collections.emptyMap();
            }
        }
        else {
            lowerString = AsciiUtil.toLowerString(lowerString.trim());
            if (lowerString.length() == 0) {
                throw new IllegalArgumentException("keyword must not be empty");
            }
            if (trim != null) {
                trim = trim.trim();
                if (trim.length() == 0) {
                    throw new IllegalArgumentException("value must not be empty");
                }
            }
            final Map keywordMap = this.getKeywordMap();
            if (keywordMap.isEmpty()) {
                if (trim != null) {
                    (this.keywords = new TreeMap(this.getKeyComparator())).put(lowerString, trim.trim());
                }
            }
            else if (b || !keywordMap.containsKey(lowerString)) {
                if (trim != null) {
                    keywordMap.put(lowerString, trim);
                }
                else {
                    keywordMap.remove(lowerString);
                    if (keywordMap.isEmpty()) {
                        this.keywords = Collections.emptyMap();
                    }
                }
            }
        }
    }
}
