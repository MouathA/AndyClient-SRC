package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data.*;
import com.viaversion.viaversion.libs.gson.*;

public class TranslateRewriter
{
    private static final ComponentRewriter achievementTextRewriter;
    
    public static void toClient(final JsonElement jsonElement, final UserConnection userConnection) {
        if (jsonElement instanceof JsonObject) {
            final JsonObject jsonObject = (JsonObject)jsonElement;
            final JsonElement value = jsonObject.get("translate");
            if (value != null && value.getAsString().startsWith("chat.type.achievement")) {
                TranslateRewriter.achievementTextRewriter.processText(jsonObject);
            }
        }
    }
    
    static {
        achievementTextRewriter = new ComponentRewriter() {
            @Override
            protected void handleTranslate(final JsonObject jsonObject, final String s) {
                final String value = AchievementTranslationMapping.get(s);
                if (value != null) {
                    jsonObject.addProperty("translate", value);
                }
            }
            
            @Override
            protected void handleHoverEvent(final JsonObject jsonObject) {
                if (!jsonObject.getAsJsonPrimitive("action").getAsString().equals("show_achievement")) {
                    super.handleHoverEvent(jsonObject);
                    return;
                }
                final JsonElement value = jsonObject.get("value");
                String s;
                if (value.isJsonObject()) {
                    s = value.getAsJsonObject().get("text").getAsString();
                }
                else {
                    s = value.getAsJsonPrimitive().getAsString();
                }
                if (AchievementTranslationMapping.get(s) == null) {
                    final JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("text", "Invalid statistic/achievement!");
                    jsonObject2.addProperty("color", "red");
                    jsonObject.addProperty("action", "show_text");
                    jsonObject.add("value", jsonObject2);
                    super.handleHoverEvent(jsonObject);
                    return;
                }
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("text", "\n");
                final JsonArray jsonArray = new JsonArray();
                jsonArray.add("");
                final JsonObject jsonObject4 = new JsonObject();
                final JsonObject jsonObject5 = new JsonObject();
                jsonArray.add(jsonObject4);
                jsonArray.add(jsonObject3);
                jsonArray.add(jsonObject5);
                if (s.startsWith("achievement")) {
                    jsonObject4.addProperty("translate", s);
                    jsonObject4.addProperty("color", AchievementTranslationMapping.isSpecial(s) ? "dark_purple" : "green");
                    jsonObject5.addProperty("translate", "stats.tooltip.type.achievement");
                    final JsonObject jsonObject6 = new JsonObject();
                    jsonObject5.addProperty("italic", true);
                    jsonObject6.addProperty("translate", value + ".desc");
                    jsonArray.add(jsonObject3);
                    jsonArray.add(jsonObject6);
                }
                else if (s.startsWith("stat")) {
                    jsonObject4.addProperty("translate", s);
                    jsonObject4.addProperty("color", "gray");
                    jsonObject5.addProperty("translate", "stats.tooltip.type.statistic");
                    jsonObject5.addProperty("italic", true);
                }
                jsonObject.addProperty("action", "show_text");
                jsonObject.add("value", jsonArray);
                super.handleHoverEvent(jsonObject);
            }
        };
    }
}
