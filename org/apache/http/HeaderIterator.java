package org.apache.http;

import java.util.*;

public interface HeaderIterator extends Iterator
{
    boolean hasNext();
    
    Header nextHeader();
}
