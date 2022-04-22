package org.apache.commons.io.comparator;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class NameFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator NAME_COMPARATOR;
    public static final Comparator NAME_REVERSE;
    public static final Comparator NAME_INSENSITIVE_COMPARATOR;
    public static final Comparator NAME_INSENSITIVE_REVERSE;
    public static final Comparator NAME_SYSTEM_COMPARATOR;
    public static final Comparator NAME_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;
    
    public NameFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }
    
    public NameFileComparator(final IOCase ioCase) {
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public int compare(final File file, final File file2) {
        return this.caseSensitivity.checkCompareTo(file.getName(), file2.getName());
    }
    
    @Override
    public String toString() {
        return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
    }
    
    @Override
    public List sort(final List list) {
        return super.sort(list);
    }
    
    @Override
    public File[] sort(final File[] array) {
        return super.sort(array);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((File)o, (File)o2);
    }
    
    static {
        NAME_COMPARATOR = new NameFileComparator();
        NAME_REVERSE = new ReverseComparator(NameFileComparator.NAME_COMPARATOR);
        NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
        NAME_INSENSITIVE_REVERSE = new ReverseComparator(NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
        NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
        NAME_SYSTEM_REVERSE = new ReverseComparator(NameFileComparator.NAME_SYSTEM_COMPARATOR);
    }
}
