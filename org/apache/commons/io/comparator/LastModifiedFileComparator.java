package org.apache.commons.io.comparator;

import java.io.*;
import java.util.*;

public class LastModifiedFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator LASTMODIFIED_COMPARATOR;
    public static final Comparator LASTMODIFIED_REVERSE;
    
    public int compare(final File file, final File file2) {
        final long n = file.lastModified() - file2.lastModified();
        if (n < 0L) {
            return -1;
        }
        if (n > 0L) {
            return 1;
        }
        return 0;
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
        LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
        LASTMODIFIED_REVERSE = new ReverseComparator(LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
    }
}
