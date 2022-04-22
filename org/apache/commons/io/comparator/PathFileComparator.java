package org.apache.commons.io.comparator;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class PathFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator PATH_COMPARATOR;
    public static final Comparator PATH_REVERSE;
    public static final Comparator PATH_INSENSITIVE_COMPARATOR;
    public static final Comparator PATH_INSENSITIVE_REVERSE;
    public static final Comparator PATH_SYSTEM_COMPARATOR;
    public static final Comparator PATH_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;
    
    public PathFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }
    
    public PathFileComparator(final IOCase ioCase) {
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public int compare(final File file, final File file2) {
        return this.caseSensitivity.checkCompareTo(file.getPath(), file2.getPath());
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
        PATH_COMPARATOR = new PathFileComparator();
        PATH_REVERSE = new ReverseComparator(PathFileComparator.PATH_COMPARATOR);
        PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
        PATH_INSENSITIVE_REVERSE = new ReverseComparator(PathFileComparator.PATH_INSENSITIVE_COMPARATOR);
        PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
        PATH_SYSTEM_REVERSE = new ReverseComparator(PathFileComparator.PATH_SYSTEM_COMPARATOR);
    }
}
