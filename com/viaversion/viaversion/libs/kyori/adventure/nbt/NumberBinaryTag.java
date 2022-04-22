package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;

public interface NumberBinaryTag extends BinaryTag
{
    @NotNull
    BinaryTagType type();
    
    byte byteValue();
    
    double doubleValue();
    
    float floatValue();
    
    int intValue();
    
    long longValue();
    
    short shortValue();
}
