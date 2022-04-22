package Mood.Cosmetics.Utils.File;

import com.google.gson.*;
import net.minecraft.client.*;
import java.io.*;

public class FileManager
{
    public FileEntry mainFile;
    public FileEntry currentTheme;
    public FileEntry language;
    public File moduleFile;
    public File settingsFile;
    private static Gson gson;
    private static File ROOT_DIR;
    public static File MODS_DIR;
    
    static {
        FileManager.gson = new Gson();
        FileManager.ROOT_DIR = new File("Mood");
        FileManager.MODS_DIR = new File(FileManager.ROOT_DIR, "Mods");
    }
    
    public FileManager() {
        this.mainFile = new FileEntry(Minecraft.mcDataDir, "Language");
        this.currentTheme = new FileEntry(this.mainFile.getFile(), "theme.mood");
        this.language = new FileEntry(this.mainFile.getFile(), "language.mood");
    }
    
    public void loadFiles() {
        this.language.initData("hungary");
    }
    
    public static void init() {
        if (!FileManager.ROOT_DIR.exists()) {
            FileManager.ROOT_DIR.mkdirs();
        }
        if (!FileManager.MODS_DIR.exists()) {
            FileManager.MODS_DIR.mkdirs();
        }
    }
    
    public static Gson getGson() {
        return FileManager.gson;
    }
    
    public static File getModsDirectory() {
        return FileManager.MODS_DIR;
    }
    
    public static boolean writeJsonToFile(final File file, final Object o) {
        if (!file.exists()) {
            file.createNewFile();
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(FileManager.gson.toJson(o).getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return true;
    }
    
    public static Object readFromJson(final File file, final Class clazz) {
        final FileInputStream fileInputStream = new FileInputStream(file);
        final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();
        return FileManager.gson.fromJson(sb.toString(), clazz);
    }
}
