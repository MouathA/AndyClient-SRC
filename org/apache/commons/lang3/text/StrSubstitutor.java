package org.apache.commons.lang3.text;

import java.util.*;
import org.apache.commons.lang3.*;

public class StrSubstitutor
{
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX;
    public static final StrMatcher DEFAULT_SUFFIX;
    public static final StrMatcher DEFAULT_VALUE_DELIMITER;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrMatcher valueDelimiterMatcher;
    private StrLookup variableResolver;
    private boolean enableSubstitutionInVariables;
    
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
    
    public static String replaceSystemProperties(final Object o) {
        return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(o);
    }
    
    public StrSubstitutor() {
        this(null, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map) {
        this(StrLookup.mapLookup(map), StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2) {
        this(StrLookup.mapLookup(map), s, s2, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2, final char c) {
        this(StrLookup.mapLookup(map), s, s2, c);
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2, final char c, final String s3) {
        this(StrLookup.mapLookup(map), s, s2, c, s3);
    }
    
    public StrSubstitutor(final StrLookup strLookup) {
        this(strLookup, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final String variablePrefix, final String variableSuffix, final char escapeChar) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefix(variablePrefix);
        this.setVariableSuffix(variableSuffix);
        this.setEscapeChar(escapeChar);
        this.setValueDelimiterMatcher(StrSubstitutor.DEFAULT_VALUE_DELIMITER);
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final String variablePrefix, final String variableSuffix, final char escapeChar, final String valueDelimiter) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefix(variablePrefix);
        this.setVariableSuffix(variableSuffix);
        this.setEscapeChar(escapeChar);
        this.setValueDelimiter(valueDelimiter);
    }
    
    public StrSubstitutor(final StrLookup strLookup, final StrMatcher strMatcher, final StrMatcher strMatcher2, final char c) {
        this(strLookup, strMatcher, strMatcher2, c, StrSubstitutor.DEFAULT_VALUE_DELIMITER);
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final StrMatcher variablePrefixMatcher, final StrMatcher variableSuffixMatcher, final char escapeChar, final StrMatcher valueDelimiterMatcher) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefixMatcher(variablePrefixMatcher);
        this.setVariableSuffixMatcher(variableSuffixMatcher);
        this.setEscapeChar(escapeChar);
        this.setValueDelimiterMatcher(valueDelimiterMatcher);
    }
    
    public String replace(final String s) {
        if (s == null) {
            return null;
        }
        final StrBuilder strBuilder = new StrBuilder(s);
        if (!this.substitute(strBuilder, 0, s.length())) {
            return s;
        }
        return strBuilder.toString();
    }
    
    public String replace(final String s, final int n, final int n2) {
        if (s == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(s, n, n2);
        if (!this.substitute(append, 0, n2)) {
            return s.substring(n, n + n2);
        }
        return append.toString();
    }
    
    public String replace(final char[] array) {
        if (array == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(array.length).append(array);
        this.substitute(append, 0, array.length);
        return append.toString();
    }
    
    public String replace(final char[] array, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(array, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StringBuffer sb) {
        if (sb == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(sb.length()).append(sb);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(sb, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return this.replace(charSequence, 0, charSequence.length());
    }
    
    public String replace(final CharSequence charSequence, final int n, final int n2) {
        if (charSequence == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(charSequence, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StrBuilder strBuilder) {
        if (strBuilder == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(strBuilder.length()).append(strBuilder);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StrBuilder strBuilder, final int n, final int n2) {
        if (strBuilder == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(strBuilder, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final Object o) {
        if (o == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder().append(o);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public boolean replaceIn(final StringBuffer sb) {
        return sb != null && this.replaceIn(sb, 0, sb.length());
    }
    
    public boolean replaceIn(final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return false;
        }
        final StrBuilder append = new StrBuilder(n2).append(sb, n, n2);
        if (!this.substitute(append, 0, n2)) {
            return false;
        }
        sb.replace(n, n + n2, append.toString());
        return true;
    }
    
    public boolean replaceIn(final StringBuilder sb) {
        return sb != null && this.replaceIn(sb, 0, sb.length());
    }
    
    public boolean replaceIn(final StringBuilder sb, final int n, final int n2) {
        if (sb == null) {
            return false;
        }
        final StrBuilder append = new StrBuilder(n2).append(sb, n, n2);
        if (!this.substitute(append, 0, n2)) {
            return false;
        }
        sb.replace(n, n + n2, append.toString());
        return true;
    }
    
    public boolean replaceIn(final StrBuilder strBuilder) {
        return strBuilder != null && this.substitute(strBuilder, 0, strBuilder.length());
    }
    
    public boolean replaceIn(final StrBuilder strBuilder, final int n, final int n2) {
        return strBuilder != null && this.substitute(strBuilder, n, n2);
    }
    
    protected boolean substitute(final StrBuilder strBuilder, final int n, final int n2) {
        return this.substitute(strBuilder, n, n2, null) > 0;
    }
    
    private int substitute(final StrBuilder strBuilder, final int n, final int n2, List list) {
        final StrMatcher variablePrefixMatcher = this.getVariablePrefixMatcher();
        final StrMatcher variableSuffixMatcher = this.getVariableSuffixMatcher();
        final char escapeChar = this.getEscapeChar();
        final StrMatcher valueDelimiterMatcher = this.getValueDelimiterMatcher();
        final boolean enableSubstitutionInVariables = this.isEnableSubstitutionInVariables();
        final boolean b = list == null;
        char[] array = strBuilder.buffer;
        int n3 = n + n2;
        int i = n;
        while (i < n3) {
            final int match = variablePrefixMatcher.isMatch(array, i, n, n3);
            if (match == 0) {
                ++i;
            }
            else if (i > n && array[i - 1] == escapeChar) {
                strBuilder.deleteCharAt(i - 1);
                array = strBuilder.buffer;
                int n4 = 0;
                --n4;
                --n3;
            }
            else {
                final int n5 = i;
                i += match;
                while (i < n3) {
                    final int match2;
                    if (enableSubstitutionInVariables && (match2 = variablePrefixMatcher.isMatch(array, i, n, n3)) != 0) {
                        int n6 = 0;
                        ++n6;
                        i += 0;
                    }
                    else {
                        variableSuffixMatcher.isMatch(array, i, n, n3);
                        if (!false) {
                            ++i;
                        }
                        else {
                            if (!false) {
                                String string = new String(array, n5 + match, i - n5 - match);
                                if (enableSubstitutionInVariables) {
                                    final StrBuilder strBuilder2 = new StrBuilder(string);
                                    this.substitute(strBuilder2, 0, strBuilder2.length());
                                    string = strBuilder2.toString();
                                }
                                final int n7;
                                i = (n7 = i + 0);
                                String substring = string;
                                String substring2 = null;
                                if (valueDelimiterMatcher != null) {
                                    final char[] charArray = string.toCharArray();
                                    while (0 < charArray.length) {
                                        if (!enableSubstitutionInVariables && variablePrefixMatcher.isMatch(charArray, 0, 0, charArray.length) != 0) {
                                            break;
                                        }
                                        final int match3;
                                        if ((match3 = valueDelimiterMatcher.isMatch(charArray, 0)) != 0) {
                                            substring = string.substring(0, 0);
                                            substring2 = string.substring(0);
                                            break;
                                        }
                                        int n8 = 0;
                                        ++n8;
                                    }
                                }
                                if (list == null) {
                                    list = new ArrayList<String>();
                                    list.add(new String(array, n, n2));
                                }
                                this.checkCyclicSubstitution(substring, list);
                                list.add(substring);
                                String resolveVariable = this.resolveVariable(substring, strBuilder, n5, n7);
                                if (resolveVariable == null) {
                                    resolveVariable = substring2;
                                }
                                if (resolveVariable != null) {
                                    resolveVariable.length();
                                    strBuilder.replace(n5, n7, resolveVariable);
                                    this.substitute(strBuilder, n5, 0, list);
                                    final int n8 = 0 - (n7 - n5);
                                    i += 0;
                                    n3 += 0;
                                    array = strBuilder.buffer;
                                }
                                list.remove(list.size() - 1);
                                break;
                            }
                            int n6 = 0;
                            --n6;
                            i += 0;
                        }
                    }
                }
            }
        }
        return (b && true) ? 1 : 0;
    }
    
    private void checkCyclicSubstitution(final String s, final List list) {
        if (!list.contains(s)) {
            return;
        }
        final StrBuilder strBuilder = new StrBuilder(256);
        strBuilder.append("Infinite loop in property interpolation of ");
        strBuilder.append(list.remove(0));
        strBuilder.append(": ");
        strBuilder.appendWithSeparators(list, "->");
        throw new IllegalStateException(strBuilder.toString());
    }
    
    protected String resolveVariable(final String s, final StrBuilder strBuilder, final int n, final int n2) {
        final StrLookup variableResolver = this.getVariableResolver();
        if (variableResolver == null) {
            return null;
        }
        return variableResolver.lookup(s);
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
    
    public StrMatcher getValueDelimiterMatcher() {
        return this.valueDelimiterMatcher;
    }
    
    public StrSubstitutor setValueDelimiterMatcher(final StrMatcher valueDelimiterMatcher) {
        this.valueDelimiterMatcher = valueDelimiterMatcher;
        return this;
    }
    
    public StrSubstitutor setValueDelimiter(final char c) {
        return this.setValueDelimiterMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrSubstitutor setValueDelimiter(final String s) {
        if (StringUtils.isEmpty(s)) {
            this.setValueDelimiterMatcher(null);
            return this;
        }
        return this.setValueDelimiterMatcher(StrMatcher.stringMatcher(s));
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
    
    static {
        DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
        DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
        DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
    }
}
