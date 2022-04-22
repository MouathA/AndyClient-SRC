package org.apache.commons.io.comparator;

import java.io.*;
import java.util.*;

public class DirectoryFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator DIRECTORY_COMPARATOR;
    public static final Comparator DIRECTORY_REVERSE;
    
    public int compare(final File file, final File file2) {
        return this.getType(file) - this.getType(file2);
    }
    
    private int getType(final File file) {
        if (file.isDirectory()) {
            return 1;
        }
        return 2;
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
        DIRECTORY_COMPARATOR = new DirectoryFileComparator();
        DIRECTORY_REVERSE = new ReverseComparator(DirectoryFileComparator.DIRECTORY_COMPARATOR);
    }
}
