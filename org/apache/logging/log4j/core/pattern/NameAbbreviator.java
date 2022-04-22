package org.apache.logging.log4j.core.pattern;

import java.util.*;

public abstract class NameAbbreviator
{
    private static final NameAbbreviator DEFAULT;
    
    public static NameAbbreviator getAbbreviator(final String s) {
        if (s.length() <= 0) {
            return NameAbbreviator.DEFAULT;
        }
        final String trim = s.trim();
        if (trim.isEmpty()) {
            return NameAbbreviator.DEFAULT;
        }
        while (0 < trim.length() && trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
            int n = 0;
            ++n;
        }
        if (0 == trim.length()) {
            return new MaxElementAbbreviator(Integer.parseInt(trim));
        }
        final ArrayList<PatternAbbreviatorFragment> list = new ArrayList<PatternAbbreviatorFragment>(5);
        while (0 < trim.length() && 0 >= 0) {
            if (trim.charAt(0) == '*') {
                int n2 = 0;
                ++n2;
            }
            else if (trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
                final int n3 = trim.charAt(0) - '0';
                int n2 = 0;
                ++n2;
            }
            if (0 < trim.length()) {
                trim.charAt(0);
                if (0 == 46) {}
            }
            list.add(new PatternAbbreviatorFragment(0, '\0'));
            int index = trim.indexOf(46, 0);
            if (0 == -1) {
                break;
            }
            ++index;
        }
        return new PatternAbbreviator(list);
    }
    
    public static NameAbbreviator getDefaultAbbreviator() {
        return NameAbbreviator.DEFAULT;
    }
    
    public abstract String abbreviate(final String p0);
    
    static {
        DEFAULT = new NOPAbbreviator();
    }
    
    private static class PatternAbbreviator extends NameAbbreviator
    {
        private final PatternAbbreviatorFragment[] fragments;
        
        public PatternAbbreviator(final List list) {
            if (list.size() == 0) {
                throw new IllegalArgumentException("fragments must have at least one element");
            }
            list.toArray(this.fragments = new PatternAbbreviatorFragment[list.size()]);
        }
        
        @Override
        public String abbreviate(final String s) {
            final StringBuilder sb = new StringBuilder(s);
            while (0 < this.fragments.length - 1 && 0 < s.length()) {
                this.fragments[0].abbreviate(sb, 0);
                int n = 0;
                ++n;
            }
            final PatternAbbreviatorFragment patternAbbreviatorFragment = this.fragments[this.fragments.length - 1];
            while (0 < s.length() && 0 >= 0) {
                patternAbbreviatorFragment.abbreviate(sb, 0);
            }
            return sb.toString();
        }
    }
    
    private static class PatternAbbreviatorFragment
    {
        private final int charCount;
        private final char ellipsis;
        
        public PatternAbbreviatorFragment(final int charCount, final char ellipsis) {
            this.charCount = charCount;
            this.ellipsis = ellipsis;
        }
        
        public int abbreviate(final StringBuilder sb, final int n) {
            int index = sb.toString().indexOf(46, n);
            if (index != -1) {
                if (index - n > this.charCount) {
                    sb.delete(n + this.charCount, index);
                    index = n + this.charCount;
                    if (this.ellipsis != '\0') {
                        sb.insert(index, this.ellipsis);
                        ++index;
                    }
                }
                ++index;
            }
            return index;
        }
    }
    
    private static class MaxElementAbbreviator extends NameAbbreviator
    {
        private final int count;
        
        public MaxElementAbbreviator(final int n) {
            this.count = ((n < 1) ? 1 : n);
        }
        
        @Override
        public String abbreviate(final String s) {
            int lastIndex = s.length() - 1;
            for (int i = this.count; i > 0; --i) {
                lastIndex = s.lastIndexOf(46, lastIndex - 1);
                if (lastIndex == -1) {
                    return s;
                }
            }
            return s.substring(lastIndex + 1);
        }
    }
    
    private static class NOPAbbreviator extends NameAbbreviator
    {
        public NOPAbbreviator() {
        }
        
        @Override
        public String abbreviate(final String s) {
            return s;
        }
    }
}
