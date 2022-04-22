package org.apache.commons.io.comparator;

import java.io.*;
import java.util.*;

abstract class AbstractFileComparator implements Comparator
{
    public File[] sort(final File... array) {
        if (array != null) {
            Arrays.sort(array, this);
        }
        return array;
    }
    
    public List sort(final List list) {
        if (list != null) {
            Collections.sort((List<Object>)list, this);
        }
        return list;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
