package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public interface PeekingIterator extends Iterator
{
    Object peek();
    
    Object next();
    
    void remove();
}
