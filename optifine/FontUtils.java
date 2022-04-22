package optifine;

import net.minecraft.util.*;
import java.io.*;
import java.util.*;

public class FontUtils
{
    public static Properties readFontProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        final Properties properties = new Properties();
        final String s = ".png";
        if (!resourcePath.endsWith(s)) {
            return properties;
        }
        final String string = String.valueOf(resourcePath.substring(0, resourcePath.length() - s.length())) + ".properties";
        final InputStream resourceStream = Config.getResourceStream(Config.getResourceManager(), new ResourceLocation(resourceLocation.getResourceDomain(), string));
        if (resourceStream == null) {
            return properties;
        }
        Config.log("Loading " + string);
        properties.load(resourceStream);
        return properties;
    }
    
    public static void readCustomCharWidths(final Properties properties, final float[] array) {
        for (final String s : ((Hashtable<String, V>)properties).keySet()) {
            final String s2 = "width.";
            if (s.startsWith(s2)) {
                final int int1 = Config.parseInt(s.substring(s2.length()), -1);
                if (int1 < 0 || int1 >= array.length) {
                    continue;
                }
                final float float1 = Config.parseFloat(properties.getProperty(s), -1.0f);
                if (float1 < 0.0f) {
                    continue;
                }
                array[int1] = float1;
            }
        }
    }
    
    public static float readFloat(final Properties properties, final String s, final float n) {
        final String property = properties.getProperty(s);
        if (property == null) {
            return n;
        }
        final float float1 = Config.parseFloat(property, Float.MIN_VALUE);
        if (float1 == Float.MIN_VALUE) {
            Config.warn("Invalid value for " + s + ": " + property);
            return n;
        }
        return float1;
    }
    
    public static ResourceLocation getHdFontLocation(final ResourceLocation resourceLocation) {
        if (!Config.isCustomFonts()) {
            return resourceLocation;
        }
        if (resourceLocation == null) {
            return resourceLocation;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final String s = "textures/";
        final String s2 = "mcpatcher/";
        if (!resourcePath.startsWith(s)) {
            return resourceLocation;
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(s2) + resourcePath.substring(s.length()));
        return Config.hasResource(Config.getResourceManager(), resourceLocation2) ? resourceLocation2 : resourceLocation;
    }
}
