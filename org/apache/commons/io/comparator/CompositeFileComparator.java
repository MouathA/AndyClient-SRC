package org.apache.commons.io.comparator;

import java.io.*;
import java.util.*;

public class CompositeFileComparator extends AbstractFileComparator implements Serializable
{
    private static final Comparator[] NO_COMPARATORS;
    private final Comparator[] delegates;
    
    public CompositeFileComparator(final Comparator... array) {
        if (array == null) {
            this.delegates = CompositeFileComparator.NO_COMPARATORS;
        }
        else {
            System.arraycopy(array, 0, this.delegates = new Comparator[array.length], 0, array.length);
        }
    }
    
    public CompositeFileComparator(final Iterable iterable) {
        if (iterable == null) {
            this.delegates = CompositeFileComparator.NO_COMPARATORS;
        }
        else {
            final ArrayList<Comparator> list = new ArrayList<Comparator>();
            final Iterator<Comparator> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            this.delegates = list.toArray(new Comparator[list.size()]);
        }
    }
    
    public int compare(final File file, final File file2) {
        final Comparator[] delegates = this.delegates;
        while (0 < delegates.length) {
            delegates[0].compare(file, file2);
            if (false) {
                break;
            }
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('{');
        while (0 < this.delegates.length) {
            if (0 > 0) {
                sb.append(',');
            }
            sb.append(this.delegates[0]);
            int n = 0;
            ++n;
        }
        sb.append('}');
        return sb.toString();
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
        NO_COMPARATORS = new Comparator[0];
    }
}
