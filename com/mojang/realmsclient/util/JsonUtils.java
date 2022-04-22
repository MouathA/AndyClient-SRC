package com.mojang.realmsclient.util;

import com.google.gson.*;
import java.util.*;

public class JsonUtils
{
    public static String getStringOr(final String s, final JsonObject jsonObject, final String s2) {
        final JsonElement value = jsonObject.get(s);
        if (value != null) {
            return value.isJsonNull() ? s2 : value.getAsString();
        }
        return s2;
    }
    
    public static int getIntOr(final String s, final JsonObject jsonObject, final int n) {
        final JsonElement value = jsonObject.get(s);
        if (value != null) {
            return value.isJsonNull() ? n : value.getAsInt();
        }
        return n;
    }
    
    public static long getLongOr(final String s, final JsonObject jsonObject, final long n) {
        final JsonElement value = jsonObject.get(s);
        if (value != null) {
            return value.isJsonNull() ? n : value.getAsLong();
        }
        return n;
    }
    
    public static boolean getBooleanOr(final String s, final JsonObject jsonObject, final boolean b) {
        final JsonElement value = jsonObject.get(s);
        if (value != null) {
            return value.isJsonNull() ? b : value.getAsBoolean();
        }
        return b;
    }
    
    public static Date getDateOr(final String s, final JsonObject jsonObject) {
        final JsonElement value = jsonObject.get(s);
        if (value != null) {
            return new Date(Long.parseLong(value.getAsString()));
        }
        return new Date();
    }
}
