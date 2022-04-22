package org.apache.commons.compress.compressors;

import java.util.*;

public class FileNameUtil
{
    private final Map compressSuffix;
    private final Map uncompressSuffix;
    private final int longestCompressedSuffix;
    private final int shortestCompressedSuffix;
    private final int longestUncompressedSuffix;
    private final int shortestUncompressedSuffix;
    private final String defaultExtension;
    
    public FileNameUtil(final Map map, final String defaultExtension) {
        this.compressSuffix = new HashMap();
        this.uncompressSuffix = Collections.unmodifiableMap((Map<?, ?>)map);
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            final int length = ((String)entry.getKey()).length();
            if (length > Integer.MIN_VALUE) {}
            if (length < Integer.MAX_VALUE) {}
            final String s = (String)entry.getValue();
            final int length2 = s.length();
            if (length2 > 0) {
                if (!this.compressSuffix.containsKey(s)) {
                    this.compressSuffix.put(s, entry.getKey());
                }
                if (length2 > Integer.MIN_VALUE) {}
                if (length2 < Integer.MAX_VALUE) {
                    continue;
                }
                continue;
            }
        }
        this.longestCompressedSuffix = Integer.MIN_VALUE;
        this.longestUncompressedSuffix = Integer.MIN_VALUE;
        this.shortestCompressedSuffix = Integer.MAX_VALUE;
        this.shortestUncompressedSuffix = Integer.MAX_VALUE;
        this.defaultExtension = defaultExtension;
    }
    
    public boolean isCompressedFilename(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ENGLISH);
        for (int length = lowerCase.length(), shortestCompressedSuffix = this.shortestCompressedSuffix; shortestCompressedSuffix <= this.longestCompressedSuffix && shortestCompressedSuffix < length; ++shortestCompressedSuffix) {
            if (this.uncompressSuffix.containsKey(lowerCase.substring(length - shortestCompressedSuffix))) {
                return true;
            }
        }
        return false;
    }
    
    public String getUncompressedFilename(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ENGLISH);
        for (int length = lowerCase.length(), shortestCompressedSuffix = this.shortestCompressedSuffix; shortestCompressedSuffix <= this.longestCompressedSuffix && shortestCompressedSuffix < length; ++shortestCompressedSuffix) {
            final String s2 = this.uncompressSuffix.get(lowerCase.substring(length - shortestCompressedSuffix));
            if (s2 != null) {
                return s.substring(0, length - shortestCompressedSuffix) + s2;
            }
        }
        return s;
    }
    
    public String getCompressedFilename(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ENGLISH);
        for (int length = lowerCase.length(), shortestUncompressedSuffix = this.shortestUncompressedSuffix; shortestUncompressedSuffix <= this.longestUncompressedSuffix && shortestUncompressedSuffix < length; ++shortestUncompressedSuffix) {
            final String s2 = this.compressSuffix.get(lowerCase.substring(length - shortestUncompressedSuffix));
            if (s2 != null) {
                return s.substring(0, length - shortestUncompressedSuffix) + s2;
            }
        }
        return s + this.defaultExtension;
    }
}
