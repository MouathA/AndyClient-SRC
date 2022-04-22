package com.viaversion.viaversion.libs.gson;

public interface ExclusionStrategy
{
    boolean shouldSkipField(final FieldAttributes p0);
    
    boolean shouldSkipClass(final Class p0);
}
