package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;

public interface Codec
{
    @NotNull
    default Codec of(@NotNull final Decoder decoder, @NotNull final Encoder encoder) {
        return new Codec() {
            final Decoder val$decoder;
            final Encoder val$encoder;
            
            @NotNull
            @Override
            public Object decode(@NotNull final Object encoded) throws Throwable {
                return this.val$decoder.decode(encoded);
            }
            
            @NotNull
            @Override
            public Object encode(@NotNull final Object decoded) throws Throwable {
                return this.val$encoder.encode(decoded);
            }
        };
    }
    
    @NotNull
    Object decode(@NotNull final Object encoded) throws Throwable;
    
    @NotNull
    Object encode(@NotNull final Object decoded) throws Throwable;
    
    public interface Encoder
    {
        @NotNull
        Object encode(@NotNull final Object decoded) throws Throwable;
    }
    
    public interface Decoder
    {
        @NotNull
        Object decode(@NotNull final Object encoded) throws Throwable;
    }
}
