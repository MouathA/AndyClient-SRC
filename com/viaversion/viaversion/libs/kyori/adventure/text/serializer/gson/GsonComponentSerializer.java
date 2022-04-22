package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.function.*;
import org.jetbrains.annotations.*;

public interface GsonComponentSerializer extends ComponentSerializer, Buildable
{
    @NotNull
    default GsonComponentSerializer gson() {
        return GsonComponentSerializerImpl.Instances.INSTANCE;
    }
    
    @NotNull
    default GsonComponentSerializer colorDownsamplingGson() {
        return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
    }
    
    default Builder builder() {
        return new GsonComponentSerializerImpl.BuilderImpl();
    }
    
    @NotNull
    Gson serializer();
    
    @NotNull
    UnaryOperator populator();
    
    @NotNull
    Component deserializeFromTree(@NotNull final JsonElement input);
    
    @NotNull
    JsonElement serializeToTree(@NotNull final Component component);
    
    @ApiStatus.Internal
    public interface Provider
    {
        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gson();
        
        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gsonLegacy();
        
        @ApiStatus.Internal
        @NotNull
        Consumer builder();
    }
    
    public interface Builder extends Buildable.Builder
    {
        @NotNull
        Builder downsampleColors();
        
        @NotNull
        Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer);
        
        @NotNull
        Builder emitLegacyHoverEvent();
        
        @NotNull
        GsonComponentSerializer build();
        
        @NotNull
        default Object build() {
            return this.build();
        }
    }
}
