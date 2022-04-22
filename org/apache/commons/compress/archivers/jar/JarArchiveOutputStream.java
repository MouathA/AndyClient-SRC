package org.apache.commons.compress.archivers.jar;

import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.zip.*;
import java.io.*;

public class JarArchiveOutputStream extends ZipArchiveOutputStream
{
    private boolean jarMarkerAdded;
    
    public JarArchiveOutputStream(final OutputStream outputStream) {
        super(outputStream);
        this.jarMarkerAdded = false;
    }
    
    @Override
    public void putArchiveEntry(final ArchiveEntry archiveEntry) throws IOException {
        if (!this.jarMarkerAdded) {
            ((ZipArchiveEntry)archiveEntry).addAsFirstExtraField(JarMarker.getInstance());
            this.jarMarkerAdded = true;
        }
        super.putArchiveEntry(archiveEntry);
    }
}
