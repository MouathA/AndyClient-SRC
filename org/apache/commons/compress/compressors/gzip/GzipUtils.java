package org.apache.commons.compress.compressors.gzip;

import org.apache.commons.compress.compressors.*;
import java.util.*;

public class GzipUtils
{
    private static final FileNameUtil fileNameUtil;
    
    private GzipUtils() {
    }
    
    public static boolean isCompressedFilename(final String s) {
        return GzipUtils.fileNameUtil.isCompressedFilename(s);
    }
    
    public static String getUncompressedFilename(final String s) {
        return GzipUtils.fileNameUtil.getUncompressedFilename(s);
    }
    
    public static String getCompressedFilename(final String s) {
        return GzipUtils.fileNameUtil.getCompressedFilename(s);
    }
    
    static {
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put(".tgz", ".tar");
        linkedHashMap.put(".taz", ".tar");
        linkedHashMap.put(".svgz", ".svg");
        linkedHashMap.put(".cpgz", ".cpio");
        linkedHashMap.put(".wmz", ".wmf");
        linkedHashMap.put(".emz", ".emf");
        linkedHashMap.put(".gz", "");
        linkedHashMap.put(".z", "");
        linkedHashMap.put("-gz", "");
        linkedHashMap.put("-z", "");
        linkedHashMap.put("_z", "");
        fileNameUtil = new FileNameUtil(linkedHashMap, ".gz");
    }
}
