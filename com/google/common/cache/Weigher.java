package com.google.common.cache;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public interface Weigher
{
    int weigh(final Object p0, final Object p1);
}
