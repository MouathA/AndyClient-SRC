package org.apache.commons.io.filefilter;

import java.util.*;
import java.io.*;
import org.apache.commons.io.*;

public class WildcardFileFilter extends AbstractFileFilter implements Serializable
{
    private final String[] wildcards;
    private final IOCase caseSensitivity;
    
    public WildcardFileFilter(final String s) {
        this(s, null);
    }
    
    public WildcardFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public WildcardFileFilter(final String[] array) {
        this(array, null);
    }
    
    public WildcardFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        System.arraycopy(array, 0, this.wildcards = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public WildcardFileFilter(final List list) {
        this(list, null);
    }
    
    public WildcardFileFilter(final List list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] wildcards = this.wildcards;
        while (0 < wildcards.length) {
            if (FilenameUtils.wildcardMatch(s, wildcards[0], this.caseSensitivity)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] wildcards = this.wildcards;
        while (0 < wildcards.length) {
            if (FilenameUtils.wildcardMatch(name, wildcards[0], this.caseSensitivity)) {
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
        if (this.wildcards != null) {
            while (0 < this.wildcards.length) {
                if (0 > 0) {
                    sb.append(",");
                }
                sb.append(this.wildcards[0]);
                int n = 0;
                ++n;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
