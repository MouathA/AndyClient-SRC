package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.*;

public class ChatItemRewriter
{
    public static void toClient(final JsonElement jsonElement, final UserConnection userConnection) {
        if (jsonElement instanceof JsonObject) {
            final JsonObject jsonObject = (JsonObject)jsonElement;
            if (jsonObject.has("hoverEvent")) {
                if (jsonObject.get("hoverEvent") instanceof JsonObject) {
                    final JsonObject jsonObject2 = (JsonObject)jsonObject.get("hoverEvent");
                    if (jsonObject2.has("action") && jsonObject2.has("value")) {
                        final String asString = jsonObject2.get("action").getAsString();
                        if (asString.equals("show_item") || asString.equals("show_entity")) {
                            final JsonElement value = jsonObject2.get("value");
                            if (value.isJsonArray()) {
                                final JsonArray jsonArray = new JsonArray();
                                for (final JsonElement jsonElement2 : value.getAsJsonArray()) {
                                    if (jsonElement2.isJsonPrimitive() && jsonElement2.getAsJsonPrimitive().isString()) {
                                        jsonArray.add(new JsonPrimitive(0 + ":" + jsonElement2.getAsString()));
                                    }
                                }
                                jsonObject2.add("value", jsonArray);
                            }
                        }
                    }
                }
            }
            else if (jsonObject.has("extra")) {
                toClient(jsonObject.get("extra"), userConnection);
            }
        }
        else if (jsonElement instanceof JsonArray) {
            final Iterator iterator2 = ((JsonArray)jsonElement).iterator();
            while (iterator2.hasNext()) {
                toClient(iterator2.next(), userConnection);
            }
        }
    }
}
