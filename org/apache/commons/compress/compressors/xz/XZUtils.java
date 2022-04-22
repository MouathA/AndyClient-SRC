package org.apache.commons.compress.compressors.xz;

import org.apache.commons.compress.compressors.*;
import java.util.*;

public class XZUtils
{
    private static final FileNameUtil fileNameUtil;
    
    private XZUtils() {
    }
    
    public static boolean isXZCompressionAvailable() {
        XZCompressorInputStream.matches(null, 0);
        return true;
    }
    
    public static boolean isCompressedFilename(final String s) {
        return XZUtils.fileNameUtil.isCompressedFilename(s);
    }
    
    public static String getUncompressedFilename(final String s) {
        return XZUtils.fileNameUtil.getUncompressedFilename(s);
    }
    
    public static String getCompressedFilename(final String s) {
        return XZUtils.fileNameUtil.getCompressedFilename(s);
    }
    
    static {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(".txz", ".tar");
        hashMap.put(".xz", "");
        hashMap.put("-xz", "");
        fileNameUtil = new FileNameUtil(hashMap, ".xz");
    }
}
