package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.*;
import java.util.*;

public class StrSubstitutor
{
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX;
    public static final StrMatcher DEFAULT_SUFFIX;
    private static final int BUF_SIZE = 256;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrLookup variableResolver;
    private boolean enableSubstitutionInVariables;
    
    public StrSubstitutor() {
        this(null, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map) {
        this(new MapLookup(map), StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2) {
        this(new MapLookup(map), s, s2, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2, final char c) {
        this(new MapLookup(map), s, s2, c);
    }
    
    public StrSubstitutor(final StrLookup strLookup) {
        this(strLookup, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final String variablePrefix, final String variableSuffix, final char escapeChar) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefix(variablePrefix);
        this.setVariableSuffix(variableSuffix);
        this.setEscapeChar(escapeChar);
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final StrMatcher variablePrefixMatcher, final StrMatcher variableSuffixMatcher, final char escapeChar) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefixMatcher(variablePrefixMatcher);
        this.setVariableSuffixMatcher(variableSuffixMatcher);
        this.setEscapeChar(escapeChar);
    }
    
    public static String replace(final Object o, final Map map) {
        return new StrSubstitutor(map).replace(o);
    }
    
    public static String replace(final Object o, final Map map, final String s, final String s2) {
        return new StrSubstitutor(map, s, s2).replace(o);
    }
    
    public static String replace(final Object o, final Properties properties) {
        if (properties == null) {
            return o.toString();
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String s = (String)propertyNames.nextElement();
            hashMap.put(s, properties.getProperty(s));
        }
        return replace(o, hashMap);
    }
    
    public String replace(final String s) {
        return this.replace(null, s);
    }
    
    public String replace(final LogEvent logEvent, final String s) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(s);
        false;
        if (s.length() > 0) {
            return s;
        }
        return sb.toString();
    }
    
    public String replace(final String s, final int n, final int n2) {
        return this.replace(null, s, n, n2);
    }
    
    public String replace(final LogEvent logEvent, final String s, final int n, final int n2) {
        if (s == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(n2).append(s, n, n2);
        false;
        if (n2 > 0) {
            return s.substring(n, n + n2);
        }
        return append.toString();
    }
    
    public String replace(final char[] array) {
        return this.replace(null, array);
    }
    
    public String replace(final LogEvent logEvent, final char[] array) {
        if (array == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(array.length).append(array);
        this.substitute(logEvent, append, 0, array.length);
        return append.toString();
    }
    
    public String replace(final char[] array, final int n, final int n2) {
        return this.replace(null, array, n, n2);
    }
    
    public String replace(final LogEvent logEvent, final char[] array, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(n2).append(array, n, n2);
        this.substitute(logEvent, append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StringBuffer sb) {
        return this.replace(null, sb);
    }
    
    public String replace(final LogEvent logEvent, final StringBuffer sb) {
        if (sb == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(sb.length()).append(sb);
        this.substitute(logEvent, append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StringBuffer sb, final int n, final int n2) {
        return this.replace(null, sb, n, n2);
    }
    
    public String replace(final LogEvent logEvent, final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(n2).append(sb, n, n2);
        this.substitute(logEvent, append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StringBuilder sb) {
        return this.replace(null, sb);
    }
    
    public String replace(final LogEvent logEvent, final StringBuilder sb) {
        if (sb == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(sb.length()).append((CharSequence)sb);
        this.substitute(logEvent, append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StringBuilder sb, final int n, final int n2) {
        return this.replace(null, sb, n, n2);
    }
    
    public String replace(final LogEvent logEvent, final StringBuilder sb, final int n, final int n2) {
        if (sb == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(n2).append(sb, n, n2);
        this.substitute(logEvent, append, 0, n2);
        return append.toString();
    }
    
    public String replace(final Object o) {
        return this.replace(null, o);
    }
    
    public String replace(final LogEvent logEvent, final Object o) {
        if (o == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder().append(o);
        this.substitute(logEvent, append, 0, append.length());
        return append.toString();
    }
    
    public boolean replaceIn(final StringBuffer sb) {
        return sb != null && this.replaceIn(sb, 0, sb.length());
    }
    
    public boolean replaceIn(final StringBuffer sb, final int n, final int n2) {
        return this.replaceIn(null, sb, n, n2);
    }
    
    public boolean replaceIn(final LogEvent logEvent, final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return false;
        }
        final StringBuilder append = new StringBuilder(n2).append(sb, n, n2);
        false;
        if (n2 > 0) {
            return false;
        }
        sb.replace(n, n + n2, append.toString());
        return true;
    }
    
    public boolean replaceIn(final StringBuilder sb) {
        return this.replaceIn(null, sb);
    }
    
    public boolean replaceIn(final LogEvent logEvent, final StringBuilder sb) {
        return sb != null && this.substitute(logEvent, sb, 0, sb.length());
    }
    
    public boolean replaceIn(final StringBuilder sb, final int n, final int n2) {
        return this.replaceIn(null, sb, n, n2);
    }
    
    public boolean replaceIn(final LogEvent logEvent, final StringBuilder sb, final int n, final int n2) {
        return sb != null && this.substitute(logEvent, sb, n, n2);
    }
    
    private int substitute(final LogEvent logEvent, final StringBuilder sb, final int n, final int n2, final List list) {
        final StrMatcher variablePrefixMatcher = this.getVariablePrefixMatcher();
        final StrMatcher variableSuffixMatcher = this.getVariableSuffixMatcher();
        final char escapeChar = this.getEscapeChar();
        final boolean b = list == null;
        char[] array = this.getChars(sb);
        int n3 = n + n2;
        int i = n;
        while (i < n3) {
            final int match = variablePrefixMatcher.isMatch(array, i, n, n3);
            if (match == 0) {
                ++i;
            }
            else if (i > n && array[i - 1] == escapeChar) {
                sb.deleteCharAt(i - 1);
                array = this.getChars(sb);
                int n4 = 0;
                --n4;
                --n3;
            }
            else {
                i += match;
                while (i < n3) {
                    final int match2;
                    if (this.isEnableSubstitutionInVariables() && (match2 = variablePrefixMatcher.isMatch(array, i, n, n3)) != 0) {
                        int n5 = 0;
                        ++n5;
                        i += 0;
                    }
                    else {
                        variableSuffixMatcher.isMatch(array, i, n, n3);
                        ++i;
                    }
                }
            }
        }
        if (b) {
            return 1;
        }
        return 0;
    }
    
    private void checkCyclicSubstitution(final String s, final List list) {
        if (!list.contains(s)) {
            return;
        }
        final StringBuilder sb = new StringBuilder(256);
        sb.append("Infinite loop in property interpolation of ");
        sb.append(list.remove(0));
        sb.append(": ");
        this.appendWithSeparators(sb, list, "->");
        throw new IllegalStateException(sb.toString());
    }
    
    protected String resolveVariable(final LogEvent logEvent, final String s, final StringBuilder sb, final int n, final int n2) {
        final StrLookup variableResolver = this.getVariableResolver();
        if (variableResolver == null) {
            return null;
        }
        return variableResolver.lookup(logEvent, s);
    }
    
    public char getEscapeChar() {
        return this.escapeChar;
    }
    
    public void setEscapeChar(final char escapeChar) {
        this.escapeChar = escapeChar;
    }
    
    public StrMatcher getVariablePrefixMatcher() {
        return this.prefixMatcher;
    }
    
    public StrSubstitutor setVariablePrefixMatcher(final StrMatcher prefixMatcher) {
        if (prefixMatcher == null) {
            throw new IllegalArgumentException("Variable prefix matcher must not be null!");
        }
        this.prefixMatcher = prefixMatcher;
        return this;
    }
    
    public StrSubstitutor setVariablePrefix(final char c) {
        return this.setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrSubstitutor setVariablePrefix(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Variable prefix must not be null!");
        }
        return this.setVariablePrefixMatcher(StrMatcher.stringMatcher(s));
    }
    
    public StrMatcher getVariableSuffixMatcher() {
        return this.suffixMatcher;
    }
    
    public StrSubstitutor setVariableSuffixMatcher(final StrMatcher suffixMatcher) {
        if (suffixMatcher == null) {
            throw new IllegalArgumentException("Variable suffix matcher must not be null!");
        }
        this.suffixMatcher = suffixMatcher;
        return this;
    }
    
    public StrSubstitutor setVariableSuffix(final char c) {
        return this.setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrSubstitutor setVariableSuffix(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Variable suffix must not be null!");
        }
        return this.setVariableSuffixMatcher(StrMatcher.stringMatcher(s));
    }
    
    public StrLookup getVariableResolver() {
        return this.variableResolver;
    }
    
    public void setVariableResolver(final StrLookup variableResolver) {
        this.variableResolver = variableResolver;
    }
    
    public boolean isEnableSubstitutionInVariables() {
        return this.enableSubstitutionInVariables;
    }
    
    public void setEnableSubstitutionInVariables(final boolean enableSubstitutionInVariables) {
        this.enableSubstitutionInVariables = enableSubstitutionInVariables;
    }
    
    private char[] getChars(final StringBuilder sb) {
        final char[] array = new char[sb.length()];
        sb.getChars(0, sb.length(), array, 0);
        return array;
    }
    
    public void appendWithSeparators(final StringBuilder sb, final Iterable iterable, String s) {
        if (iterable != null) {
            s = ((s == null) ? "" : s);
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                if (iterator.hasNext()) {
                    sb.append(s);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "StrSubstitutor(" + this.variableResolver.toString() + ")";
    }
    
    static {
        DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
        DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    }
}
