package Mood.Cosmetics;

import java.io.*;
import Mood.Cosmetics.Utils.File.*;

public class ModToggle
{
    public static boolean CosmeticWings;
    
    public static File getFolder(final String s) {
        final File file = new File(FileManager.MODS_DIR, s);
        file.mkdirs();
        return file;
    }
    
    public static void saveIsEnabledToFile(final String s, final Boolean b) {
        FileManager.writeJsonToFile(new File(getFolder(s), "Enabled.json"), b);
    }
    
    public static Boolean loadEnabledFromFile(final String s) {
        Boolean value = (Boolean)FileManager.readFromJson(new File(getFolder(s), "Enabled.json"), Boolean.class);
        if (value == null) {
            value = true;
            saveIsEnabledToFile(s, value);
        }
        return value;
    }
    
    public static void saveDoubleToFile(final String s, final Double n) {
        FileManager.writeJsonToFile(new File(getFolder(s), "Double.json"), n);
    }
    
    public static Double loadDoubleFromFile(final String s) {
        Double value = (Double)FileManager.readFromJson(new File(getFolder(s), "Double.json"), Double.class);
        if (value == null) {
            value = 1.0;
            saveDoubleToFile(s, value);
        }
        return value;
    }
}
