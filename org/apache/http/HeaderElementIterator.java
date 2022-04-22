package org.apache.http;

import java.util.*;

public interface HeaderElementIterator extends Iterator
{
    boolean hasNext();
    
    HeaderElement nextElement();
}
