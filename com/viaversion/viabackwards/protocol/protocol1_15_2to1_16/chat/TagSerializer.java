package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import java.util.regex.*;
import com.google.common.base.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.gson.*;

@Deprecated
public class TagSerializer
{
    private static final Pattern PLAIN_TEXT;
    
    public static String toString(final JsonObject jsonObject) {
        final StringBuilder sb = new StringBuilder("{");
        for (final Map.Entry<K, JsonElement> entry : jsonObject.entrySet()) {
            Preconditions.checkArgument(entry.getValue().isJsonPrimitive());
            if (sb.length() != 1) {
                sb.append(',');
            }
            sb.append((String)entry.getKey()).append(':').append(escape(entry.getValue().getAsString()));
        }
        return sb.append('}').toString();
    }
    
    public static JsonObject toJson(final CompoundTag compoundTag) {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<String, V> entry : compoundTag.entrySet()) {
            jsonObject.add(entry.getKey(), toJson((Tag)entry.getValue()));
        }
        return jsonObject;
    }
    
    private static JsonElement toJson(final Tag tag) {
        if (tag instanceof CompoundTag) {
            return toJson((CompoundTag)tag);
        }
        if (tag instanceof ListTag) {
            final ListTag listTag = (ListTag)tag;
            final JsonArray jsonArray = new JsonArray();
            final Iterator iterator = listTag.iterator();
            while (iterator.hasNext()) {
                jsonArray.add(toJson(iterator.next()));
            }
            return jsonArray;
        }
        return new JsonPrimitive(tag.getValue().toString());
    }
    
    public static String escape(final String s) {
        if (TagSerializer.PLAIN_TEXT.matcher(s).matches()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(" ");
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '\\') {
                sb.append('\\');
            }
            else if (char1 == '\"' || char1 == '\'') {
                if (34 == 0) {
                    final int n = (char1 == '\"') ? 39 : 34;
                }
                if ('\"' == char1) {
                    sb.append('\\');
                }
            }
            sb.append(char1);
            int n2 = 0;
            ++n2;
        }
        if (34 == 0) {}
        sb.setCharAt(0, '\"');
        sb.append('\"');
        return sb.toString();
    }
    
    static {
        PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");
    }
}
