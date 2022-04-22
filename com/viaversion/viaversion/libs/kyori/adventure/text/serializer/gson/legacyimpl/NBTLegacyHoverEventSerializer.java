package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import org.jetbrains.annotations.*;

public interface NBTLegacyHoverEventSerializer extends LegacyHoverEventSerializer
{
    @NotNull
    default LegacyHoverEventSerializer get() {
        return NBTLegacyHoverEventSerializerImpl.INSTANCE;
    }
}
