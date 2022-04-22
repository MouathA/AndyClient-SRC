package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "String.valueOf(this.value) + \"l\"", hasChildren = "false")
final class LongBinaryTagImpl extends AbstractBinaryTag implements LongBinaryTag
{
    private final long value;
    
    LongBinaryTagImpl(final long value) {
        this.value = value;
    }
    
    @Override
    public long value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return (byte)(this.value & 0xFFL);
    }
    
    @Override
    public double doubleValue() {
        return (double)this.value;
    }
    
    @Override
    public float floatValue() {
        return (float)this.value;
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public short shortValue() {
        return (short)(this.value & 0xFFFFL);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && this.value == ((LongBinaryTagImpl)other).value);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
