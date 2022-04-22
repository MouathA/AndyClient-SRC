package org.apache.commons.compress.archivers.jar;

import org.apache.commons.compress.archivers.zip.*;
import java.io.*;
import org.apache.commons.compress.archivers.*;

public class JarArchiveInputStream extends ZipArchiveInputStream
{
    public JarArchiveInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    public JarArchiveEntry getNextJarEntry() throws IOException {
        final ZipArchiveEntry nextZipEntry = this.getNextZipEntry();
        return (nextZipEntry == null) ? null : new JarArchiveEntry(nextZipEntry);
    }
    
    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextJarEntry();
    }
    
    public static boolean matches(final byte[] array, final int n) {
        return ZipArchiveInputStream.matches(array, n);
    }
}
