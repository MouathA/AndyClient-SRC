package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import java.util.*;
import com.viaversion.viaversion.dump.*;
import java.net.*;
import java.nio.charset.*;
import com.google.common.io.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.util.*;
import java.io.*;

public class DumpSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "dump";
    }
    
    @Override
    public String description() {
        return "Dump information about your server, this is helpful if you report bugs.";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final VersionInfo versionInfo = new VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), Via.getAPI().getServerVersion().lowestSupportedVersion(), Via.getManager().getProtocolManager().getSupportedVersions(), Via.getPlatform().getPlatformName(), Via.getPlatform().getPlatformVersion(), Via.getPlatform().getPluginVersion(), "git-ViaVersion-4.2.0-22w06a-SNAPSHOT:130ab70", Via.getManager().getSubPlatforms());
        Via.getPlatform().runAsync(new Runnable(viaCommandSender, versionInfo, new DumpTemplate(versionInfo, Via.getPlatform().getConfigurationProvider().getValues(), Via.getPlatform().getDump(), Via.getManager().getInjector().getDump())) {
            final ViaCommandSender val$sender;
            final VersionInfo val$version;
            final DumpTemplate val$template;
            final DumpSubCmd this$0;
            
            @Override
            public void run() {
                final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://dump.viaversion.com/documents").openConnection();
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.addRequestProperty("User-Agent", "ViaVersion/" + this.val$version.getPluginVersion());
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                final OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(new GsonBuilder().setPrettyPrinting().create().toJson(this.val$template).getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                if (httpURLConnection.getResponseCode() == 429) {
                    this.val$sender.sendMessage("§4You can only paste once every minute to protect our systems.");
                    return;
                }
                final String string = CharStreams.toString(new InputStreamReader(httpURLConnection.getInputStream()));
                httpURLConnection.getInputStream().close();
                final JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(string, JsonObject.class);
                if (!jsonObject.has("key")) {
                    throw new InvalidObjectException("Key is not given in Hastebin output");
                }
                this.val$sender.sendMessage("§2We've made a dump with useful information, report your issue and provide this url: " + DumpSubCmd.access$000(this.this$0, jsonObject.get("key").getAsString()));
            }
        });
        return true;
    }
    
    private String getUrl(final String s) {
        return String.format("https://dump.viaversion.com/%s", s);
    }
    
    static String access$000(final DumpSubCmd dumpSubCmd, final String s) {
        return dumpSubCmd.getUrl(s);
    }
}
