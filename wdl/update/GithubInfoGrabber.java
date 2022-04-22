package wdl.update;

import net.minecraft.client.*;
import com.google.gson.*;
import java.util.*;
import javax.net.ssl.*;
import java.net.*;
import wdl.*;
import wdl.api.*;
import java.io.*;

public class GithubInfoGrabber
{
    private static final String USER_AGENT;
    private static final JsonParser PARSER;
    private static final String RELEASE_LIST_LOCATION;
    private static final File CACHED_RELEASES_FILE;
    
    static {
        RELEASE_LIST_LOCATION = "https://api.github.com/repos/Pokechu22/WorldDownloader/releases?per_page=100";
        PARSER = new JsonParser();
        Minecraft.getMinecraft();
        CACHED_RELEASES_FILE = new File(Minecraft.mcDataDir, "WorldDownloader_Update_Cache.json");
        USER_AGENT = String.format("WorldDownloader mod by Pokechu22 (Minecraft %s; WDL %s) ", WDL.getMinecraftVersionInfo(), "1.8.9a-beta2");
    }
    
    public static List getReleases() throws Exception {
        final JsonArray asJsonArray = query("https://api.github.com/repos/Pokechu22/WorldDownloader/releases?per_page=100").getAsJsonArray();
        final ArrayList<Release> list = new ArrayList<Release>();
        final Iterator iterator = asJsonArray.iterator();
        while (iterator.hasNext()) {
            list.add(new Release(iterator.next().getAsJsonObject()));
        }
        return list;
    }
    
    public static JsonElement query(final String s) throws Exception {
        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL(s).openConnection();
        httpsURLConnection.setRequestProperty("User-Agent", GithubInfoGrabber.USER_AGENT);
        httpsURLConnection.setRequestProperty("Accept", "application/vnd.github.v3.full+json");
        if (WDL.globalProps.getProperty("UpdateETag") != null) {
            final String property = WDL.globalProps.getProperty("UpdateETag");
            if (!property.isEmpty()) {
                httpsURLConnection.setRequestProperty("If-None-Match", property);
            }
        }
        httpsURLConnection.connect();
        InputStream inputStream;
        if (httpsURLConnection.getResponseCode() == 304) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.UPDATE_DEBUG, "wdl.messages.updates.usingCachedUpdates", new Object[0]);
            inputStream = new FileInputStream(GithubInfoGrabber.CACHED_RELEASES_FILE);
        }
        else {
            if (httpsURLConnection.getResponseCode() != 200) {
                throw new Exception("Unexpected response while getting " + s + ": " + httpsURLConnection.getResponseCode() + " " + httpsURLConnection.getResponseMessage());
            }
            WDLMessages.chatMessageTranslated(WDLMessageTypes.UPDATE_DEBUG, "wdl.messages.updates.grabingUpdatesFromGithub", new Object[0]);
            inputStream = httpsURLConnection.getInputStream();
        }
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final JsonElement parse = GithubInfoGrabber.PARSER.parse(inputStreamReader);
        final PrintStream printStream = new PrintStream(GithubInfoGrabber.CACHED_RELEASES_FILE);
        printStream.println(parse.toString());
        final String headerField = httpsURLConnection.getHeaderField("ETag");
        if (printStream != null) {
            printStream.close();
        }
        if (headerField != null) {
            WDL.globalProps.setProperty("UpdateETag", headerField);
        }
        else {
            WDL.globalProps.remove("UpdateETag");
        }
        final JsonElement jsonElement = parse;
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return jsonElement;
    }
}
