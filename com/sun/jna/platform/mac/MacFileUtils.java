package com.sun.jna.platform.mac;

import com.sun.jna.platform.*;
import java.io.*;
import java.util.*;
import com.sun.jna.ptr.*;
import com.sun.jna.*;

public class MacFileUtils extends FileUtils
{
    @Override
    public boolean hasTrash() {
        return true;
    }
    
    @Override
    public void moveToTrash(final File[] array) throws IOException {
        final File file = new File(new File(System.getProperty("user.home")), ".Trash");
        if (!file.exists()) {
            throw new IOException("The Trash was not found in its expected location (" + file + ")");
        }
        final ArrayList<File> list = new ArrayList<File>();
        while (0 < array.length) {
            final File file2 = array[0];
            if (FileManager.INSTANCE.FSPathMoveObjectToTrashSync(file2.getAbsolutePath(), null, 0) != 0) {
                list.add(file2);
            }
            int n = 0;
            ++n;
        }
        if (list.size() > 0) {
            throw new IOException("The following files could not be trashed: " + list);
        }
    }
    
    public interface FileManager extends Library
    {
        public static final int kFSFileOperationDefaultOptions = 0;
        public static final int kFSFileOperationsOverwrite = 1;
        public static final int kFSFileOperationsSkipSourcePermissionErrors = 2;
        public static final int kFSFileOperationsDoNotMoveAcrossVolumes = 4;
        public static final int kFSFileOperationsSkipPreflight = 8;
        public static final FileManager INSTANCE = (FileManager)Native.loadLibrary("CoreServices", FileManager.class);
        
        int FSPathMoveObjectToTrashSync(final String p0, final PointerByReference p1, final int p2);
    }
}
