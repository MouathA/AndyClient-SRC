package org.apache.commons.compress.archivers.tar;

import java.io.*;

public class TarArchiveSparseEntry implements TarConstants
{
    private final boolean isExtended;
    
    public TarArchiveSparseEntry(final byte[] array) throws IOException {
        final int n;
        n += 504;
        this.isExtended = TarUtils.parseBoolean(array, 0);
    }
    
    public boolean isExtended() {
        return this.isExtended;
    }
}
