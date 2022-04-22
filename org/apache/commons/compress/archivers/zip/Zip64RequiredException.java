package org.apache.commons.compress.archivers.zip;

import java.util.zip.*;

public class Zip64RequiredException extends ZipException
{
    private static final long serialVersionUID = 20110809L;
    static final String ARCHIVE_TOO_BIG_MESSAGE = "archive's size exceeds the limit of 4GByte.";
    static final String TOO_MANY_ENTRIES_MESSAGE = "archive contains more than 65535 entries.";
    
    static String getEntryTooBigMessage(final ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getName() + "'s size exceeds the limit of 4GByte.";
    }
    
    public Zip64RequiredException(final String s) {
        super(s);
    }
}
