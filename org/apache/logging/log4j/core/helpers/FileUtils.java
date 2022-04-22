package org.apache.logging.log4j.core.helpers;

import org.apache.logging.log4j.*;
import java.net.*;
import java.io.*;
import org.apache.logging.log4j.status.*;

public final class FileUtils
{
    private static final String PROTOCOL_FILE = "file";
    private static final String JBOSS_FILE = "vfsfile";
    private static final Logger LOGGER;
    
    private FileUtils() {
    }
    
    public static File fileFromURI(URI uri) {
        if (uri == null || (uri.getScheme() != null && !"file".equals(uri.getScheme()) && !"vfsfile".equals(uri.getScheme()))) {
            return null;
        }
        if (uri.getScheme() == null) {
            uri = new File(uri.getPath()).toURI();
        }
        return new File(URLDecoder.decode(uri.toURL().getFile(), "UTF8"));
    }
    
    public static boolean isFile(final URL url) {
        return url != null && (url.getProtocol().equals("file") || url.getProtocol().equals("vfsfile"));
    }
    
    public static void mkdir(final File file, final boolean b) throws IOException {
        if (!file.exists()) {
            if (!b) {
                throw new IOException("The directory " + file.getAbsolutePath() + " does not exist.");
            }
            if (!file.mkdirs()) {
                throw new IOException("Could not create directory " + file.getAbsolutePath());
            }
        }
        if (!file.isDirectory()) {
            throw new IOException("File " + file + " exists and is not a directory. Unable to create directory.");
        }
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
