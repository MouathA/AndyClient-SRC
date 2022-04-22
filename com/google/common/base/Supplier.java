package com.google.common.base;

import com.google.common.annotations.*;

@GwtCompatible
public interface Supplier
{
    Object get();
}
