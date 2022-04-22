package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import org.jetbrains.annotations.*;

@Debug.Renderer(text = "asHexString()")
final class TextColorImpl implements TextColor
{
    private final int value;
    
    TextColorImpl(final int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other instanceof TextColorImpl && this.value == ((TextColorImpl)other).value);
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.asHexString();
    }
}
