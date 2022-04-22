package de.gerrygames.viarewind.utils;

import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;

public class ChatUtil
{
    private static final Pattern UNUSED_COLOR_PATTERN;
    
    public static String jsonToLegacy(final String input) {
        if (input == null || input.equals("null") || input.isEmpty()) {
            return "";
        }
        String s;
        for (s = LegacyComponentSerializer.legacySection().serialize(ChatRewriter.HOVER_GSON_SERIALIZER.deserialize(input)); s.startsWith("§f"); s = s.substring(2)) {}
        return s;
    }
    
    public static String jsonToLegacy(final JsonElement jsonElement) {
        if (jsonElement.isJsonNull() || (jsonElement.isJsonArray() && jsonElement.getAsJsonArray().isEmpty()) || (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().size() == 0)) {
            return "";
        }
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        return jsonToLegacy(jsonElement.toString());
    }
    
    public static String legacyToJson(final String input) {
        if (input == null) {
            return "";
        }
        return (String)GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(input));
    }
    
    public static String removeUnusedColor(String replaceAll, char c) {
        if (replaceAll == null) {
            return null;
        }
        replaceAll = ChatUtil.UNUSED_COLOR_PATTERN.matcher(replaceAll).replaceAll("$1$2");
        final StringBuilder sb = new StringBuilder();
        while (0 < replaceAll.length()) {
            final char char1 = replaceAll.charAt(0);
            int n = 0;
            if (char1 != '§' || 0 == replaceAll.length() - 1) {
                sb.append(char1);
            }
            else {
                final String s = replaceAll;
                ++n;
                final char char2 = s.charAt(0);
                if (char2 != c) {
                    sb.append('§').append(char2);
                    c = char2;
                }
            }
            ++n;
        }
        return sb.toString();
    }
    
    static {
        UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
    }
}
