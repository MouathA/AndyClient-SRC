package com.google.common.reflect;

import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@Beta
public interface TypeToInstanceMap extends Map
{
    @Nullable
    Object getInstance(final Class p0);
    
    @Nullable
    Object putInstance(final Class p0, @Nullable final Object p1);
    
    @Nullable
    Object getInstance(final TypeToken p0);
    
    @Nullable
    Object putInstance(final TypeToken p0, @Nullable final Object p1);
}
