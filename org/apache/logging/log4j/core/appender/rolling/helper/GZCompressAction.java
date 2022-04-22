package org.apache.logging.log4j.core.appender.rolling.helper;

import java.util.zip.*;
import java.io.*;

public final class GZCompressAction extends AbstractAction
{
    private static final int BUF_SIZE = 8102;
    private final File source;
    private final File destination;
    private final boolean deleteSource;
    
    public GZCompressAction(final File source, final File destination, final boolean deleteSource) {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (destination == null) {
            throw new NullPointerException("destination");
        }
        this.source = source;
        this.destination = destination;
        this.deleteSource = deleteSource;
    }
    
    @Override
    public boolean execute() throws IOException {
        return execute(this.source, this.destination, this.deleteSource);
    }
    
    public static boolean execute(final File file, final File file2, final boolean b) throws IOException {
        if (file.exists()) {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file2)));
            final byte[] array = new byte[8102];
            int read;
            while ((read = fileInputStream.read(array)) != -1) {
                bufferedOutputStream.write(array, 0, read);
            }
            bufferedOutputStream.close();
            fileInputStream.close();
            if (b && !file.delete()) {
                GZCompressAction.LOGGER.warn("Unable to delete " + file.toString() + '.');
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void reportException(final Exception ex) {
        GZCompressAction.LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", ex);
    }
}
