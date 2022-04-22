package com.google.common.collect;

import com.google.common.annotations.*;

@GwtCompatible
interface Constraint
{
    Object checkElement(final Object p0);
    
    String toString();
}
