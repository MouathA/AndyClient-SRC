package com.viaversion.viaversion.update;

import com.viaversion.viaversion.api.*;
import java.util.*;
import java.net.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.util.*;

public class UpdateUtil
{
    private static final String PREFIX = "§a§l[ViaVersion] §a";
    private static final String URL = "https://api.spiget.org/v2/resources/";
    private static final int PLUGIN = 19254;
    private static final String LATEST_VERSION = "/versions/latest";
    
    public static void sendUpdateMessage(final UUID uuid) {
        Via.getPlatform().runAsync(UpdateUtil::lambda$sendUpdateMessage$1);
    }
    
    public static void sendUpdateMessage() {
        Via.getPlatform().runAsync(UpdateUtil::lambda$sendUpdateMessage$3);
    }
    
    private static String getUpdateMessage(final boolean b) {
        if (Via.getPlatform().getPluginVersion().equals("${version}")) {
            return "You are using a debug/custom version, consider updating.";
        }
        final String newestVersion = getNewestVersion();
        if (newestVersion == null) {
            if (b) {
                return "Could not check for updates, check your connection.";
            }
            return null;
        }
        else {
            final Version version = new Version(Via.getPlatform().getPluginVersion());
            final Version version2 = new Version(newestVersion);
            if (version.compareTo(version2) < 0) {
                return "There is a newer plugin version available: " + version2 + ", you're on: " + version;
            }
            if (!b || version.compareTo(version2) == 0) {
                return null;
            }
            final String lowerCase = version.getTag().toLowerCase(Locale.ROOT);
            if (lowerCase.startsWith("dev") || lowerCase.startsWith("snapshot")) {
                return "You are running a development version of the plugin, please report any bugs to GitHub.";
            }
            return "You are running a newer version of the plugin than is released!";
        }
    }
    
    private static String getNewestVersion() {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://api.spiget.org/v2/resources/19254/versions/latest?" + System.currentTimeMillis()).openConnection();
        httpURLConnection.setUseCaches(true);
        httpURLConnection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
        httpURLConnection.setDoOutput(true);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        return ((JsonObject)GsonUtil.getGson().fromJson(sb.toString(), JsonObject.class)).get("name").getAsString();
    }
    
    private static void lambda$sendUpdateMessage$3() {
        final String updateMessage = getUpdateMessage(true);
        if (updateMessage != null) {
            Via.getPlatform().runSync(UpdateUtil::lambda$null$2);
        }
    }
    
    private static void lambda$null$2(final String s) {
        Via.getPlatform().getLogger().warning(s);
    }
    
    private static void lambda$sendUpdateMessage$1(final UUID uuid) {
        final String updateMessage = getUpdateMessage(false);
        if (updateMessage != null) {
            Via.getPlatform().runSync(UpdateUtil::lambda$null$0);
        }
    }
    
    private static void lambda$null$0(final UUID uuid, final String s) {
        Via.getPlatform().sendMessage(uuid, "§a§l[ViaVersion] §a" + s);
    }
}
