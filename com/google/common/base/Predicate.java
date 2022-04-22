package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public interface Predicate
{
    boolean apply(@Nullable final Object p0);
    
    boolean equals(@Nullable final Object p0);
}
