package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.libs.gson.*;

public class ChatRewriter
{
    public static void toClient(final JsonObject jsonObject, final UserConnection userConnection) {
        if (jsonObject.get("translate") != null && jsonObject.get("translate").getAsString().equals("gameMode.changed")) {
            final String text = ((EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class)).getGameMode().getText();
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("text", text);
            jsonObject2.addProperty("color", "gray");
            jsonObject2.addProperty("italic", true);
            final JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject2);
            jsonObject.add("with", jsonArray);
        }
    }
}
