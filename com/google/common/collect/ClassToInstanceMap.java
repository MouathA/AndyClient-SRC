package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public interface ClassToInstanceMap extends Map
{
    Object getInstance(final Class p0);
    
    Object putInstance(final Class p0, @Nullable final Object p1);
}
