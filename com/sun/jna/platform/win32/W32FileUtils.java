package com.sun.jna.platform.win32;

import com.sun.jna.platform.*;
import com.sun.jna.*;
import java.io.*;

public class W32FileUtils extends FileUtils
{
    @Override
    public boolean hasTrash() {
        return true;
    }
    
    @Override
    public void moveToTrash(final File[] array) throws IOException {
        final Shell32 instance = Shell32.INSTANCE;
        final ShellAPI.SHFILEOPSTRUCT shfileopstruct = new ShellAPI.SHFILEOPSTRUCT();
        shfileopstruct.wFunc = 3;
        final String[] array2 = new String[array.length];
        while (0 < array2.length) {
            array2[0] = array[0].getAbsolutePath();
            int shFileOperation = 0;
            ++shFileOperation;
        }
        shfileopstruct.pFrom = new WString(shfileopstruct.encodePaths(array2));
        shfileopstruct.fFlags = 1620;
        int shFileOperation = instance.SHFileOperation(shfileopstruct);
        if (shfileopstruct.fAnyOperationsAborted) {
            throw new IOException("Move to trash aborted");
        }
    }
}
