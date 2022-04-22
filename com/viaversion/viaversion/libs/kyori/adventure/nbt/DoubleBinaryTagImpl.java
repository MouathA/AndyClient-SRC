package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;

@Debug.Renderer(text = "String.valueOf(this.value) + \"d\"", hasChildren = "false")
final class DoubleBinaryTagImpl extends AbstractBinaryTag implements DoubleBinaryTag
{
    private final double value;
    
    DoubleBinaryTagImpl(final double value) {
        this.value = value;
    }
    
    @Override
    public double value() {
        return this.value;
    }
    
    @Override
    public byte byteValue() {
        return (byte)(ShadyPines.floor(this.value) & 0xFF);
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
        return ShadyPines.floor(this.value);
    }
    
    @Override
    public long longValue() {
        return (long)Math.floor(this.value);
    }
    
    @Override
    public short shortValue() {
        return (short)(ShadyPines.floor(this.value) & 0xFFFF);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((DoubleBinaryTagImpl)other).value));
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
