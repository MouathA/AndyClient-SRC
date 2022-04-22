package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.*;

final class ClickEventActionSerializer
{
    static final TypeAdapter INSTANCE;
    
    private ClickEventActionSerializer() {
    }
    
    static {
        INSTANCE = IndexedSerializer.of("click action", ClickEvent.Action.NAMES);
    }
}
