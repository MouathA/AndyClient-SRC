package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface BiMap extends Map
{
    Object put(@Nullable final Object p0, @Nullable final Object p1);
    
    Object forcePut(@Nullable final Object p0, @Nullable final Object p1);
    
    void putAll(final Map p0);
    
    Set values();
    
    BiMap inverse();
}
