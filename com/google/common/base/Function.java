package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public interface Function
{
    @Nullable
    Object apply(@Nullable final Object p0);
    
    boolean equals(@Nullable final Object p0);
}
