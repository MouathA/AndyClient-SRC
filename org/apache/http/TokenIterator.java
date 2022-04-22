package org.apache.http;

import java.util.*;

public interface TokenIterator extends Iterator
{
    boolean hasNext();
    
    String nextToken();
}
