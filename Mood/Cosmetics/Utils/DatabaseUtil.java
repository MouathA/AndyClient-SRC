package Mood.Cosmetics.Utils;

import java.net.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;

public class DatabaseUtil
{
    private static String stringurl;
    private static JsonArray data;
    
    static {
        DatabaseUtil.stringurl = "https://raw.githubusercontent.com/cornly/coscoremod/main/cos.json";
    }
    
    public static void parseData() {
        final Scanner useDelimiter = new Scanner(new URL(DatabaseUtil.stringurl).openStream(), "UTF-8").useDelimiter("\\A");
        DatabaseUtil.data = new JsonParser().parse(useDelimiter.next()).getAsJsonArray();
        useDelimiter.close();
    }
    
    public static boolean getBoolean(final String s, final String s2) throws IOException {
        while (0 < DatabaseUtil.data.size()) {
            final JsonObject asJsonObject = DatabaseUtil.data.get(0).getAsJsonObject();
            if (asJsonObject.get("uuid").getAsString().equals(s) && asJsonObject.get(s2).getAsBoolean()) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static double getDouble(final String s, final String s2) throws IOException {
        while (0 < DatabaseUtil.data.size()) {
            final JsonObject asJsonObject = DatabaseUtil.data.get(0).getAsJsonObject();
            if (asJsonObject.get("uuid").getAsString().equals(s)) {
                return asJsonObject.get(s2).getAsDouble();
            }
            int n = 0;
            ++n;
        }
        return 0.0;
    }
    
    public static String getString(final String s, final String s2) throws IOException {
        while (0 < DatabaseUtil.data.size()) {
            final JsonObject asJsonObject = DatabaseUtil.data.get(0).getAsJsonObject();
            if (asJsonObject.get("uuid").getAsString().equals(s)) {
                return asJsonObject.get(s2).getAsString();
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static Integer getInt(final String s, final String s2) throws IOException {
        while (0 < DatabaseUtil.data.size()) {
            final JsonObject asJsonObject = DatabaseUtil.data.get(0).getAsJsonObject();
            if (asJsonObject.get("uuid").getAsString().equals(s)) {
                return asJsonObject.get(s2).getAsInt();
            }
            int n = 0;
            ++n;
        }
        return null;
    }
}
