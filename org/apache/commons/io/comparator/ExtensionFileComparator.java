package org.apache.commons.io.comparator;

import java.io.*;
import org.apache.commons.io.*;
import java.util.*;

public class ExtensionFileComparator extends AbstractFileComparator implements Serializable
{
    public static final Comparator EXTENSION_COMPARATOR;
    public static final Comparator EXTENSION_REVERSE;
    public static final Comparator EXTENSION_INSENSITIVE_COMPARATOR;
    public static final Comparator EXTENSION_INSENSITIVE_REVERSE;
    public static final Comparator EXTENSION_SYSTEM_COMPARATOR;
    public static final Comparator EXTENSION_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;
    
    public ExtensionFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }
    
    public ExtensionFileComparator(final IOCase ioCase) {
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public int compare(final File file, final File file2) {
        return this.caseSensitivity.checkCompareTo(FilenameUtils.getExtension(file.getName()), FilenameUtils.getExtension(file2.getName()));
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
        EXTENSION_COMPARATOR = new ExtensionFileComparator();
        EXTENSION_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_COMPARATOR);
        EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
        EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_INSENSITIVE_COMPARATOR);
        EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
        EXTENSION_SYSTEM_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_SYSTEM_COMPARATOR);
    }
}
