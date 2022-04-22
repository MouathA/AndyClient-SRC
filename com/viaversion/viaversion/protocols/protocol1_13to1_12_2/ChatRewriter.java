package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.*;

public final class ChatRewriter
{
    public static final GsonComponentSerializer HOVER_GSON_SERIALIZER;
    
    public static String legacyTextToJsonString(final String s, final boolean b) {
        return (String)GsonComponentSerializer.gson().serialize(Component.text(ChatRewriter::lambda$legacyTextToJsonString$0));
    }
    
    public static String legacyTextToJsonString(final String s) {
        return legacyTextToJsonString(s, false);
    }
    
    public static JsonElement legacyTextToJson(final String s) {
        return JsonParser.parseString(legacyTextToJsonString(s, false));
    }
    
    public static String jsonToLegacyText(final String input) {
        return LegacyComponentSerializer.legacySection().serialize(ChatRewriter.HOVER_GSON_SERIALIZER.deserialize(input));
    }
    
    @Deprecated
    public static void processTranslate(final JsonElement jsonElement) {
        ((Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getComponentRewriter().processText(jsonElement);
    }
    
    private static void lambda$legacyTextToJsonString$0(final boolean b, final String input, final TextComponent.Builder builder) {
        if (b) {
            builder.decoration(TextDecoration.ITALIC, false);
        }
        builder.append(LegacyComponentSerializer.legacySection().deserialize(input));
    }
    
    static {
        HOVER_GSON_SERIALIZER = GsonComponentSerializer.builder().emitLegacyHoverEvent().legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get()).build();
    }
}
