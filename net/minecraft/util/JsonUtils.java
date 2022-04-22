package net.minecraft.util;

import org.apache.commons.lang3.*;
import com.google.gson.*;

public class JsonUtils
{
    private static final String __OBFID;
    
    public static boolean jsonObjectFieldTypeIsString(final JsonObject jsonObject, final String s) {
        return jsonObjectFieldTypeIsPrimitive(jsonObject, s) && jsonObject.getAsJsonPrimitive(s).isString();
    }
    
    public static boolean jsonElementTypeIsString(final JsonElement jsonElement) {
        return jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString();
    }
    
    public static boolean func_180199_c(final JsonObject jsonObject, final String s) {
        return jsonObjectFieldTypeIsPrimitive(jsonObject, s) && jsonObject.getAsJsonPrimitive(s).isBoolean();
    }
    
    public static boolean jsonObjectFieldTypeIsArray(final JsonObject jsonObject, final String s) {
        return jsonObjectHasNamedField(jsonObject, s) && jsonObject.get(s).isJsonArray();
    }
    
    public static boolean jsonObjectFieldTypeIsPrimitive(final JsonObject jsonObject, final String s) {
        return jsonObjectHasNamedField(jsonObject, s) && jsonObject.get(s).isJsonPrimitive();
    }
    
    public static boolean jsonObjectHasNamedField(final JsonObject jsonObject, final String s) {
        return jsonObject != null && jsonObject.get(s) != null;
    }
    
    public static String getJsonElementStringValue(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a string, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static String getJsonObjectStringFieldValue(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonElementStringValue(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a string");
    }
    
    public static String getJsonObjectStringFieldValueOrDefault(final JsonObject jsonObject, final String s, final String s2) {
        return jsonObject.has(s) ? getJsonElementStringValue(jsonObject.get(s), s) : s2;
    }
    
    public static boolean getJsonElementBooleanValue(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Boolean, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static boolean getJsonObjectBooleanFieldValue(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonElementBooleanValue(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Boolean");
    }
    
    public static boolean getJsonObjectBooleanFieldValueOrDefault(final JsonObject jsonObject, final String s, final boolean b) {
        return jsonObject.has(s) ? getJsonElementBooleanValue(jsonObject.get(s), s) : b;
    }
    
    public static float getJsonElementFloatValue(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Float, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static float getJsonObjectFloatFieldValue(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonElementFloatValue(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Float");
    }
    
    public static float getJsonObjectFloatFieldValueOrDefault(final JsonObject jsonObject, final String s, final float n) {
        return jsonObject.has(s) ? getJsonElementFloatValue(jsonObject.get(s), s) : n;
    }
    
    public static int getJsonElementIntegerValue(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a Int, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static int getJsonObjectIntegerFieldValue(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonElementIntegerValue(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a Int");
    }
    
    public static int getJsonObjectIntegerFieldValueOrDefault(final JsonObject jsonObject, final String s, final int n) {
        return jsonObject.has(s) ? getJsonElementIntegerValue(jsonObject.get(s), s) : n;
    }
    
    public static JsonObject getElementAsJsonObject(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a JsonObject, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static JsonObject getJsonObject(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getElementAsJsonObject(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonObject");
    }
    
    public static JsonObject getJsonObjectFieldOrDefault(final JsonObject jsonObject, final String s, final JsonObject jsonObject2) {
        return jsonObject.has(s) ? getElementAsJsonObject(jsonObject.get(s), s) : jsonObject2;
    }
    
    public static JsonArray getJsonElementAsJsonArray(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + s + " to be a JsonArray, was " + getJsonElementTypeDescription(jsonElement));
    }
    
    public static JsonArray getJsonObjectJsonArrayField(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonElementAsJsonArray(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonArray");
    }
    
    public static JsonArray getJsonObjectJsonArrayFieldOrDefault(final JsonObject jsonObject, final String s, final JsonArray jsonArray) {
        return jsonObject.has(s) ? getJsonElementAsJsonArray(jsonObject.get(s), s) : jsonArray;
    }
    
    public static String getJsonElementTypeDescription(final JsonElement jsonElement) {
        final String abbreviateMiddle = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
        if (jsonElement == null) {
            return "null (missing)";
        }
        if (jsonElement.isJsonNull()) {
            return "null (json)";
        }
        if (jsonElement.isJsonArray()) {
            return "an array (" + abbreviateMiddle + ")";
        }
        if (jsonElement.isJsonObject()) {
            return "an object (" + abbreviateMiddle + ")";
        }
        if (jsonElement.isJsonPrimitive()) {
            final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (asJsonPrimitive.isNumber()) {
                return "a number (" + abbreviateMiddle + ")";
            }
            if (asJsonPrimitive.isBoolean()) {
                return "a boolean (" + abbreviateMiddle + ")";
            }
        }
        return abbreviateMiddle;
    }
    
    static {
        __OBFID = "CL_00001484";
    }
}
