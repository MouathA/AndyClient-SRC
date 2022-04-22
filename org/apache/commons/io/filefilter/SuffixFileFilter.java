package org.apache.commons.io.filefilter;

import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public class SuffixFileFilter extends AbstractFileFilter implements Serializable
{
    private final String[] suffixes;
    private final IOCase caseSensitivity;
    
    public SuffixFileFilter(final String s) {
        this(s, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The suffix must not be null");
        }
        this.suffixes = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public SuffixFileFilter(final String[] array) {
        this(array, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of suffixes must not be null");
        }
        System.arraycopy(array, 0, this.suffixes = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public SuffixFileFilter(final List list) {
        this(list, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final List list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of suffixes must not be null");
        }
        this.suffixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] suffixes = this.suffixes;
        while (0 < suffixes.length) {
            if (this.caseSensitivity.checkEndsWith(name, suffixes[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] suffixes = this.suffixes;
        while (0 < suffixes.length) {
            if (this.caseSensitivity.checkEndsWith(s, suffixes[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        if (this.suffixes != null) {
            while (0 < this.suffixes.length) {
                if (0 > 0) {
                    sb.append(",");
                }
                sb.append(this.suffixes[0]);
                int n = 0;
                ++n;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
