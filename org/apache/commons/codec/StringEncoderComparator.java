package org.apache.commons.codec;

import java.util.*;

public class StringEncoderComparator implements Comparator
{
    private final StringEncoder stringEncoder;
    
    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }
    
    public StringEncoderComparator(final StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        ((Comparable)this.stringEncoder.encode(o)).compareTo(this.stringEncoder.encode(o2));
        return 0;
    }
}
