package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;

final class TextDecorationSerializer
{
    static final TypeAdapter INSTANCE;
    
    private TextDecorationSerializer() {
    }
    
    static {
        INSTANCE = IndexedSerializer.of("text decoration", TextDecoration.NAMES);
    }
}
