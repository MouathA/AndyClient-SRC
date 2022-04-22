package org.apache.commons.compress.compressors.bzip2;

import org.apache.commons.compress.compressors.*;
import java.util.*;

public abstract class BZip2Utils
{
    private static final FileNameUtil fileNameUtil;
    
    private BZip2Utils() {
    }
    
    public static boolean isCompressedFilename(final String s) {
        return BZip2Utils.fileNameUtil.isCompressedFilename(s);
    }
    
    public static String getUncompressedFilename(final String s) {
        return BZip2Utils.fileNameUtil.getUncompressedFilename(s);
    }
    
    public static String getCompressedFilename(final String s) {
        return BZip2Utils.fileNameUtil.getCompressedFilename(s);
    }
    
    static {
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put(".tar.bz2", ".tar");
        linkedHashMap.put(".tbz2", ".tar");
        linkedHashMap.put(".tbz", ".tar");
        linkedHashMap.put(".bz2", "");
        linkedHashMap.put(".bz", "");
        fileNameUtil = new FileNameUtil(linkedHashMap, ".bz2");
    }
}
