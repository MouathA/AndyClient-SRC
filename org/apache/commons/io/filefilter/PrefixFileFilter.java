package org.apache.commons.io.filefilter;

import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public class PrefixFileFilter extends AbstractFileFilter implements Serializable
{
    private final String[] prefixes;
    private final IOCase caseSensitivity;
    
    public PrefixFileFilter(final String s) {
        this(s, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The prefix must not be null");
        }
        this.prefixes = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public PrefixFileFilter(final String[] array) {
        this(array, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of prefixes must not be null");
        }
        System.arraycopy(array, 0, this.prefixes = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public PrefixFileFilter(final List list) {
        this(list, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final List list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of prefixes must not be null");
        }
        this.prefixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] prefixes = this.prefixes;
        while (0 < prefixes.length) {
            if (this.caseSensitivity.checkStartsWith(name, prefixes[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] prefixes = this.prefixes;
        while (0 < prefixes.length) {
            if (this.caseSensitivity.checkStartsWith(s, prefixes[0])) {
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
        if (this.prefixes != null) {
            while (0 < this.prefixes.length) {
                sb.append(this.prefixes[0]);
                int n = 0;
                ++n;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
