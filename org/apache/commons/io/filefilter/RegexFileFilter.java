package org.apache.commons.io.filefilter;

import java.util.regex.*;
import org.apache.commons.io.*;
import java.io.*;

public class RegexFileFilter extends AbstractFileFilter implements Serializable
{
    private final Pattern pattern;
    
    public RegexFileFilter(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(s);
    }
    
    public RegexFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        if (ioCase == null || !ioCase.isCaseSensitive()) {}
        this.pattern = Pattern.compile(s, 2);
    }
    
    public RegexFileFilter(final String s, final int n) {
        if (s == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(s, n);
    }
    
    public RegexFileFilter(final Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = pattern;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return this.pattern.matcher(s).matches();
    }
}
