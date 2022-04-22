package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;

final class HoverEventActionSerializer
{
    static final TypeAdapter INSTANCE;
    
    private HoverEventActionSerializer() {
    }
    
    static {
        INSTANCE = IndexedSerializer.of("hover action", HoverEvent.Action.NAMES);
    }
}
