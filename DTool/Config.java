package DTool;

import Mood.*;
import java.nio.file.*;
import java.util.function.*;
import com.google.gson.*;
import java.io.*;

public class Config
{
    private static final Config INSTANCE;
    private static final GsonBuilder GSON_BUILDER;
    public static File ROOT_DIR;
    private static File configFile;
    private static File settingsFile;
    private static File miscFile;
    private static File musicFile;
    
    static {
        INSTANCE = new Config();
        GSON_BUILDER = new GsonBuilder();
        Config.ROOT_DIR = new File(Client.name);
        Client.getInstance();
        Config.configFile = new File(Client.name, "config.json");
        Client.getInstance();
        Config.settingsFile = new File(Client.name, "settings.json");
        Client.getInstance();
        Config.miscFile = new File(Client.name, "misc.json");
        Client.getInstance();
        Config.musicFile = new File(Client.name, "music.json");
        Client.getInstance();
    }
    
    public static final Config getInstance() {
        return Config.INSTANCE;
    }
    
    public static void init() {
        if (!Config.ROOT_DIR.exists()) {
            Config.ROOT_DIR.mkdirs();
        }
        if (!Files.exists(Paths.get(Config.configFile.getPath(), new String[0]), new LinkOption[0]) && !Files.exists(Paths.get(Config.settingsFile.getPath(), new String[0]), new LinkOption[0]) && !Files.exists(Paths.get(Config.miscFile.getPath(), new String[0]), new LinkOption[0]) && !Files.exists(Paths.get(Config.musicFile.getPath(), new String[0]), new LinkOption[0])) {
            Files.copy(Config.class.getResourceAsStream("/assets/minecraft/client/config.json"), Paths.get(Config.configFile.getPath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Config.class.getResourceAsStream("/assets/minecraft/client/settings.json"), Paths.get(Config.settingsFile.getPath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Config.class.getResourceAsStream("/assets/minecraft/client/misc.json"), Paths.get(Config.miscFile.getPath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Config.class.getResourceAsStream("/assets/minecraft/client/music.json"), Paths.get(Config.musicFile.getPath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public void editConfig(final String s, final Consumer consumer) {
        final JsonObject asJsonObject = read(Config.configFile).getAsJsonObject();
        final JsonObject jsonObject = asJsonObject.has(s) ? asJsonObject.get(s).getAsJsonObject() : new JsonObject();
        consumer.accept(jsonObject);
        asJsonObject.add(s, jsonObject);
        write(Config.configFile, asJsonObject);
    }
    
    public final JsonObject getConfig(final String s) {
        return read(Config.configFile).getAsJsonObject().get(s).getAsJsonObject();
    }
    
    public void editMisc(final String s, final Consumer consumer) {
        final JsonObject asJsonObject = read(Config.miscFile).getAsJsonObject();
        final JsonObject jsonObject = asJsonObject.has(s) ? asJsonObject.get(s).getAsJsonObject() : new JsonObject();
        consumer.accept(jsonObject);
        asJsonObject.add(s, jsonObject);
        write(Config.miscFile, asJsonObject);
    }
    
    public final JsonObject getMisc(final String s) {
        return read(Config.miscFile).getAsJsonObject().get(s).getAsJsonObject();
    }
    
    public void editSettings(final String s, final Consumer consumer) {
        final JsonObject asJsonObject = read(Config.settingsFile).getAsJsonObject();
        final JsonObject jsonObject = asJsonObject.has(s) ? asJsonObject.get(s).getAsJsonObject() : new JsonObject();
        consumer.accept(jsonObject);
        asJsonObject.add(s, jsonObject);
        write(Config.settingsFile, asJsonObject);
    }
    
    public final JsonObject getSettings(final String s) {
        return read(Config.settingsFile).getAsJsonObject().get(s).getAsJsonObject();
    }
    
    public void editMusic(final String s, final Consumer consumer) {
        final JsonObject asJsonObject = read(Config.musicFile).getAsJsonObject();
        final JsonObject jsonObject = asJsonObject.has(s) ? asJsonObject.get(s).getAsJsonObject() : new JsonObject();
        consumer.accept(jsonObject);
        asJsonObject.add(s, jsonObject);
        write(Config.musicFile, asJsonObject);
    }
    
    public final JsonObject getMusic(final String s) {
        return read(Config.musicFile).getAsJsonObject().get(s).getAsJsonObject();
    }
    
    public static final JsonElement read(final File file) {
        final FileReader fileReader = new FileReader(file);
        final JsonElement parse = new JsonParser().parse(fileReader);
        fileReader.close();
        return parse;
    }
    
    public static final void write(final File file, final JsonElement jsonElement) {
        final FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(Config.GSON_BUILDER.setPrettyPrinting().create().toJson(jsonElement).toString());
        fileWriter.close();
    }
}
