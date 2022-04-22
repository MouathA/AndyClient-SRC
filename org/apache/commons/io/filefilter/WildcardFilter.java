package org.apache.commons.io.filefilter;

import java.util.*;
import java.io.*;
import org.apache.commons.io.*;

@Deprecated
public class WildcardFilter extends AbstractFileFilter implements Serializable
{
    private final String[] wildcards;
    
    public WildcardFilter(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[] { s };
    }
    
    public WildcardFilter(final String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        System.arraycopy(array, 0, this.wildcards = new String[array.length], 0, array.length);
    }
    
    public WildcardFilter(final List list) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        if (file != null && new File(file, s).isDirectory()) {
            return false;
        }
        final String[] wildcards = this.wildcards;
        while (0 < wildcards.length) {
            if (FilenameUtils.wildcardMatch(s, wildcards[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file) {
        if (file.isDirectory()) {
            return false;
        }
        final String[] wildcards = this.wildcards;
        while (0 < wildcards.length) {
            if (FilenameUtils.wildcardMatch(file.getName(), wildcards[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
}
