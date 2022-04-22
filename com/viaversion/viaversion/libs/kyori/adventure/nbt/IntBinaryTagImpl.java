package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "String.valueOf(this.value) + \"i\"", hasChildren = "false")
final class IntBinaryTagImpl extends AbstractBinaryTag implements IntBinaryTag
{
    private final int value;
    
    IntBinaryTagImpl(final int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return (byte)(this.value & 0xFF);
    }
    
    @Override
    public double doubleValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        return (float)this.value;
    }
    
    @Override
    public int intValue() {
        return this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public short shortValue() {
        return (short)(this.value & 0xFFFF);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && this.value == ((IntBinaryTagImpl)other).value);
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
