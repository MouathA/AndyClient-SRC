package org.apache.logging.log4j.core.appender.rolling.helper;

import java.io.*;
import java.util.zip.*;

public final class ZipCompressAction extends AbstractAction
{
    private static final int BUF_SIZE = 8102;
    private final File source;
    private final File destination;
    private final boolean deleteSource;
    private final int level;
    
    public ZipCompressAction(final File source, final File destination, final boolean deleteSource, final int level) {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (destination == null) {
            throw new NullPointerException("destination");
        }
        this.source = source;
        this.destination = destination;
        this.deleteSource = deleteSource;
        this.level = level;
    }
    
    @Override
    public boolean execute() throws IOException {
        return execute(this.source, this.destination, this.deleteSource, this.level);
    }
    
    public static boolean execute(final File file, final File file2, final boolean b, final int level) throws IOException {
        if (file.exists()) {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
            zipOutputStream.setLevel(level);
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            final byte[] array = new byte[8102];
            int read;
            while ((read = fileInputStream.read(array)) != -1) {
                zipOutputStream.write(array, 0, read);
            }
            zipOutputStream.close();
            fileInputStream.close();
            if (b && !file.delete()) {
                ZipCompressAction.LOGGER.warn("Unable to delete " + file.toString() + '.');
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void reportException(final Exception ex) {
        ZipCompressAction.LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", ex);
    }
}
