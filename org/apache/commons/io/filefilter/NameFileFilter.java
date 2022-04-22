package org.apache.commons.io.filefilter;

import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public class NameFileFilter extends AbstractFileFilter implements Serializable
{
    private final String[] names;
    private final IOCase caseSensitivity;
    
    public NameFileFilter(final String s) {
        this(s, null);
    }
    
    public NameFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.names = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public NameFileFilter(final String[] array) {
        this(array, null);
    }
    
    public NameFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of names must not be null");
        }
        System.arraycopy(array, 0, this.names = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public NameFileFilter(final List list) {
        this(list, null);
    }
    
    public NameFileFilter(final List list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of names must not be null");
        }
        this.names = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] names = this.names;
        while (0 < names.length) {
            if (this.caseSensitivity.checkEquals(name, names[0])) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] names = this.names;
        while (0 < names.length) {
            if (this.caseSensitivity.checkEquals(s, names[0])) {
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
        if (this.names != null) {
            while (0 < this.names.length) {
                if (0 > 0) {
                    sb.append(",");
                }
                sb.append(this.names[0]);
                int n = 0;
                ++n;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
