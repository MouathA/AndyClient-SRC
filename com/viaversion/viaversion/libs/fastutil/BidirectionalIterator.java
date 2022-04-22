package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface BidirectionalIterator extends Iterator
{
    Object previous();
    
    boolean hasPrevious();
}
