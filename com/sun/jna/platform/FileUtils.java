package com.sun.jna.platform;

import java.io.*;
import java.util.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.mac.*;

public abstract class FileUtils
{
    public boolean hasTrash() {
        return false;
    }
    
    public abstract void moveToTrash(final File[] p0) throws IOException;
    
    public static FileUtils getInstance() {
        return Holder.INSTANCE;
    }
    
    private static class DefaultFileUtils extends FileUtils
    {
        private DefaultFileUtils() {
        }
        
        private File getTrashDirectory() {
            final File file = new File(System.getProperty("user.home"));
            File file2 = new File(file, ".Trash");
            if (!file2.exists()) {
                file2 = new File(file, "Trash");
                if (!file2.exists()) {
                    final File file3 = new File(file, "Desktop");
                    if (file3.exists()) {
                        file2 = new File(file3, ".Trash");
                        if (!file2.exists()) {
                            file2 = new File(file3, "Trash");
                            if (!file2.exists()) {
                                file2 = new File(System.getProperty("fileutils.trash", "Trash"));
                            }
                        }
                    }
                }
            }
            return file2;
        }
        
        @Override
        public boolean hasTrash() {
            return this.getTrashDirectory().exists();
        }
        
        @Override
        public void moveToTrash(final File[] array) throws IOException {
            final File trashDirectory = this.getTrashDirectory();
            if (!trashDirectory.exists()) {
                throw new IOException("No trash location found (define fileutils.trash to be the path to the trash)");
            }
            final ArrayList<File> list = new ArrayList<File>();
            while (0 < array.length) {
                final File file = array[0];
                if (!file.renameTo(new File(trashDirectory, file.getName()))) {
                    list.add(file);
                }
                int n = 0;
                ++n;
            }
            if (list.size() > 0) {
                throw new IOException("The following files could not be trashed: " + list);
            }
        }
        
        DefaultFileUtils(final FileUtils$1 object) {
            this();
        }
    }
    
    private static class Holder
    {
        public static final FileUtils INSTANCE;
        
        static {
            final String property = System.getProperty("os.name");
            if (property.startsWith("Windows")) {
                INSTANCE = new W32FileUtils();
            }
            else if (property.startsWith("Mac")) {
                INSTANCE = new MacFileUtils();
            }
            else {
                INSTANCE = new DefaultFileUtils(null);
            }
        }
    }
}
