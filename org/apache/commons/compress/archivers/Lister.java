package org.apache.commons.compress.archivers;

import java.io.*;

public final class Lister
{
    private static final ArchiveStreamFactory factory;
    
    public static void main(final String[] array) throws Exception {
        if (array.length == 0) {
            return;
        }
        System.out.println("Analysing " + array[0]);
        final File file = new File(array[0]);
        if (!file.isFile()) {
            System.err.println(file + " doesn't exist or is a directory");
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        ArchiveInputStream archiveInputStream;
        if (array.length > 1) {
            archiveInputStream = Lister.factory.createArchiveInputStream(array[1], bufferedInputStream);
        }
        else {
            archiveInputStream = Lister.factory.createArchiveInputStream(bufferedInputStream);
        }
        System.out.println("Created " + archiveInputStream.toString());
        ArchiveEntry nextEntry;
        while ((nextEntry = archiveInputStream.getNextEntry()) != null) {
            System.out.println(nextEntry.getName());
        }
        archiveInputStream.close();
        bufferedInputStream.close();
    }
    
    private static void usage() {
        System.out.println("Parameters: archive-name [archive-type]");
    }
    
    static {
        factory = new ArchiveStreamFactory();
    }
}
