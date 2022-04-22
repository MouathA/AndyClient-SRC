package org.apache.commons.io.comparator;

import java.io.*;
import java.util.*;

public class DefaultFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator DEFAULT_COMPARATOR;
    public static final Comparator DEFAULT_REVERSE;
    
    public int compare(final File file, final File file2) {
        return file.compareTo(file2);
    }
    
    @Override
    public String toString() {
        return super.toString();
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
        DEFAULT_COMPARATOR = new DefaultFileComparator();
        DEFAULT_REVERSE = new ReverseComparator(DefaultFileComparator.DEFAULT_COMPARATOR);
    }
}
