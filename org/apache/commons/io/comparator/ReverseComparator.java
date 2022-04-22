package org.apache.commons.io.comparator;

import java.util.*;
import java.io.*;

class ReverseComparator extends AbstractFileComparator implements Serializable
{
    private final Comparator delegate;
    
    public ReverseComparator(final Comparator delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate comparator is missing");
        }
        this.delegate = delegate;
    }
    
    public int compare(final File file, final File file2) {
        return this.delegate.compare(file2, file);
    }
    
    @Override
    public String toString() {
        return super.toString() + "[" + this.delegate.toString() + "]";
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((File)o, (File)o2);
    }
}
