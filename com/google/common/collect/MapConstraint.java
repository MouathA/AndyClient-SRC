package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
@Beta
public interface MapConstraint
{
    void checkKeyValue(@Nullable final Object p0, @Nullable final Object p1);
    
    String toString();
}
