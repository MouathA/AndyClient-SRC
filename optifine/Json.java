package optifine;

import com.google.gson.*;

public class Json
{
    public static float getFloat(final JsonObject jsonObject, final String s, final float n) {
        final JsonElement value = jsonObject.get(s);
        return (value == null) ? n : value.getAsFloat();
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s, final boolean b) {
        final JsonElement value = jsonObject.get(s);
        return (value == null) ? b : value.getAsBoolean();
    }
    
    public static String getString(final JsonObject jsonObject, final String s) {
        return getString(jsonObject, s, null);
    }
    
    public static String getString(final JsonObject jsonObject, final String s, final String s2) {
        final JsonElement value = jsonObject.get(s);
        return (value == null) ? s2 : value.getAsString();
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int n) {
        return parseFloatArray(jsonElement, n, null);
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int n, final float[] array) {
        if (jsonElement == null) {
            return array;
        }
        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
        if (asJsonArray.size() != n) {
            throw new JsonParseException("Wrong array length: " + asJsonArray.size() + ", should be: " + n + ", array: " + asJsonArray);
        }
        final float[] array2 = new float[asJsonArray.size()];
        while (0 < array2.length) {
            array2[0] = asJsonArray.get(0).getAsFloat();
            int n2 = 0;
            ++n2;
        }
        return array2;
    }
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int n) {
        return parseIntArray(jsonElement, n, null);
    }
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int n, final int[] array) {
        if (jsonElement == null) {
            return array;
        }
        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
        if (asJsonArray.size() != n) {
            throw new JsonParseException("Wrong array length: " + asJsonArray.size() + ", should be: " + n + ", array: " + asJsonArray);
        }
        final int[] array2 = new int[asJsonArray.size()];
        while (0 < array2.length) {
            array2[0] = asJsonArray.get(0).getAsInt();
            int n2 = 0;
            ++n2;
        }
        return array2;
    }
}
