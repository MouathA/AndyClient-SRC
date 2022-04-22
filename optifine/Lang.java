package optifine;

import com.google.common.base.*;
import java.util.regex.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;
import java.util.*;
import java.io.*;

public class Lang
{
    private static final Splitter splitter;
    private static final Pattern pattern;
    
    static {
        splitter = Splitter.on('=').limit(2);
        pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
    
    public static void resourcesReloaded() {
        final Map localeProperties = I18n.getLocaleProperties();
        final ArrayList<String> list = new ArrayList<String>();
        final String s = "optifine/lang/";
        final String s2 = "en_US";
        final String s3 = ".lang";
        list.add(String.valueOf(s) + s2 + s3);
        Config.getGameSettings();
        if (!GameSettings.language.equals(s2)) {
            final ArrayList<String> list2 = list;
            final StringBuilder sb = new StringBuilder(String.valueOf(s));
            Config.getGameSettings();
            list2.add(sb.append(GameSettings.language).append(s3).toString());
        }
        final String[] array = list.toArray(new String[list.size()]);
        loadResources(Config.getDefaultResourcePack(), array, localeProperties);
        final IResourcePack[] resourcePacks = Config.getResourcePacks();
        while (0 < resourcePacks.length) {
            loadResources(resourcePacks[0], array, localeProperties);
            int n = 0;
            ++n;
        }
    }
    
    private static void loadResources(final IResourcePack resourcePack, final String[] array, final Map map) {
        while (0 < array.length) {
            final ResourceLocation resourceLocation = new ResourceLocation(array[0]);
            if (resourcePack.resourceExists(resourceLocation)) {
                final InputStream inputStream = resourcePack.getInputStream(resourceLocation);
                if (inputStream != null) {
                    loadLocaleData(inputStream, map);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void loadLocaleData(final InputStream inputStream, final Map map) throws IOException {
        for (final String s : IOUtils.readLines(inputStream, Charsets.UTF_8)) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
                final String[] array = (String[])Iterables.toArray(Lang.splitter.split(s), String.class);
                if (array == null || array.length != 2) {
                    continue;
                }
                map.put(array[0], Lang.pattern.matcher(array[1]).replaceAll("%$1s"));
            }
        }
    }
    
    public static String get(final String s) {
        return I18n.format(s, new Object[0]);
    }
    
    public static String get(final String s, final String s2) {
        final String format = I18n.format(s, new Object[0]);
        return (format != null && !format.equals(s)) ? format : s2;
    }
    
    public static String getOn() {
        return I18n.format("options.on", new Object[0]);
    }
    
    public static String getOff() {
        return I18n.format("options.off", new Object[0]);
    }
    
    public static String getFast() {
        return I18n.format("options.graphics.fast", new Object[0]);
    }
    
    public static String getFancy() {
        return I18n.format("options.graphics.fancy", new Object[0]);
    }
    
    public static String getDefault() {
        return I18n.format("generator.default", new Object[0]);
    }
}
