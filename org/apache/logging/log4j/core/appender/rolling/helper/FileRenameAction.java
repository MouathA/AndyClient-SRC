package org.apache.logging.log4j.core.appender.rolling.helper;

import java.nio.channels.*;
import java.io.*;

public class FileRenameAction extends AbstractAction
{
    private final File source;
    private final File destination;
    private final boolean renameEmptyFiles;
    
    public FileRenameAction(final File source, final File destination, final boolean renameEmptyFiles) {
        this.source = source;
        this.destination = destination;
        this.renameEmptyFiles = renameEmptyFiles;
    }
    
    @Override
    public boolean execute() {
        return execute(this.source, this.destination, this.renameEmptyFiles);
    }
    
    public static boolean execute(final File file, final File file2, final boolean b) {
        if (!b && file.length() <= 0L) {
            file.delete();
            return false;
        }
        final File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            FileRenameAction.LOGGER.error("Unable to create directory {}", parentFile.getAbsolutePath());
            return false;
        }
        if (!file.renameTo(file2)) {
            copyFile(file, file2);
            return file.delete();
        }
        return true;
    }
    
    private static void copyFile(final File file, final File file2) throws IOException {
        if (!file2.exists()) {
            file2.createNewFile();
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final FileOutputStream fileOutputStream = new FileOutputStream(file2);
        final FileChannel channel = fileInputStream.getChannel();
        final FileChannel channel2 = fileOutputStream.getChannel();
        channel2.transferFrom(channel, 0L, channel.size());
        if (channel != null) {
            channel.close();
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (channel2 != null) {
            channel2.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }
}
