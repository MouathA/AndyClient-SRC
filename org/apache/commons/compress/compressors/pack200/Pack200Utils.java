package org.apache.commons.compress.compressors.pack200;

import java.util.*;
import java.io.*;
import java.util.jar.*;
import java.util.zip.*;

public class Pack200Utils
{
    private Pack200Utils() {
    }
    
    public static void normalize(final File file) throws IOException {
        normalize(file, file, null);
    }
    
    public static void normalize(final File file, final Map map) throws IOException {
        normalize(file, file, map);
    }
    
    public static void normalize(final File file, final File file2) throws IOException {
        normalize(file, file2, null);
    }
    
    public static void normalize(final File file, final File file2, Map hashMap) throws IOException {
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
        }
        hashMap.put("pack.segment.limit", "-1");
        final File tempFile = File.createTempFile("commons-compress", "pack200normalize");
        tempFile.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        final Pack200.Packer packer = Pack200.newPacker();
        packer.properties().putAll((Map<?, ?>)hashMap);
        packer.pack(new JarFile(file), fileOutputStream);
        final ZipFile zipFile = null;
        fileOutputStream.close();
        final Pack200.Unpacker unpacker = Pack200.newUnpacker();
        final JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file2));
        unpacker.unpack(tempFile, jarOutputStream);
        if (zipFile != null) {
            zipFile.close();
        }
        if (jarOutputStream != null) {
            jarOutputStream.close();
        }
        tempFile.delete();
    }
}
