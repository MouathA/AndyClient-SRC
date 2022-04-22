package optifine;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.util.*;
import java.util.zip.*;
import java.util.*;

public class ResUtils
{
    public static String[] collectFiles(final String s, final String s2) {
        return collectFiles(new String[] { s }, new String[] { s2 });
    }
    
    public static String[] collectFiles(final String[] array, final String[] array2) {
        final LinkedHashSet set = new LinkedHashSet();
        final IResourcePack[] resourcePacks = Config.getResourcePacks();
        while (0 < resourcePacks.length) {
            set.addAll(Arrays.asList(collectFiles(resourcePacks[0], array, array2, null)));
            int n = 0;
            ++n;
        }
        return (String[])set.toArray(new String[set.size()]);
    }
    
    public static String[] collectFiles(final IResourcePack resourcePack, final String s, final String s2, final String[] array) {
        return collectFiles(resourcePack, new String[] { s }, new String[] { s2 }, array);
    }
    
    public static String[] collectFiles(final IResourcePack resourcePack, final String[] array, final String[] array2) {
        return collectFiles(resourcePack, array, array2, null);
    }
    
    public static String[] collectFiles(final IResourcePack resourcePack, final String[] array, final String[] array2, final String[] array3) {
        if (resourcePack instanceof DefaultResourcePack) {
            return collectFilesFixed(resourcePack, array3);
        }
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final File resourcePackFile = ((AbstractResourcePack)resourcePack).resourcePackFile;
        return (resourcePackFile == null) ? new String[0] : (resourcePackFile.isDirectory() ? collectFilesFolder(resourcePackFile, "", array, array2) : (resourcePackFile.isFile() ? collectFilesZIP(resourcePackFile, array, array2) : new String[0]));
    }
    
    private static String[] collectFilesFixed(final IResourcePack resourcePack, final String[] array) {
        if (array == null) {
            return new String[0];
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < array.length) {
            final String s = array[0];
            if (resourcePack.resourceExists(new ResourceLocation(s))) {
                list.add(s);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static String[] collectFilesFolder(final File file, final String s, final String[] array, final String[] array2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s2 = "assets/minecraft/";
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String[0];
        }
        while (0 < listFiles.length) {
            final File file2 = listFiles[0];
            if (file2.isFile()) {
                final String string = String.valueOf(s) + file2.getName();
                if (string.startsWith(s2)) {
                    final String substring = string.substring(s2.length());
                    if (StrUtils.startsWith(substring, array) && StrUtils.endsWith(substring, array2)) {
                        list.add(substring);
                    }
                }
            }
            else if (file2.isDirectory()) {
                final String[] collectFilesFolder = collectFilesFolder(file2, String.valueOf(s) + file2.getName() + "/", array, array2);
                while (0 < collectFilesFolder.length) {
                    list.add(collectFilesFolder[0]);
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static String[] collectFilesZIP(final File file, final String[] array, final String[] array2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s = "assets/minecraft/";
        final ZipFile zipFile = new ZipFile(file);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith(s)) {
                final String substring = name.substring(s.length());
                if (!StrUtils.startsWith(substring, array) || !StrUtils.endsWith(substring, array2)) {
                    continue;
                }
                list.add(substring);
            }
        }
        zipFile.close();
        return list.toArray(new String[list.size()]);
    }
}
