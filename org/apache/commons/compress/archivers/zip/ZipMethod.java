package org.apache.commons.compress.archivers.zip;

import java.util.*;

public enum ZipMethod
{
    STORED("STORED", 0, 0), 
    UNSHRINKING("UNSHRINKING", 1, 1), 
    EXPANDING_LEVEL_1("EXPANDING_LEVEL_1", 2, 2), 
    EXPANDING_LEVEL_2("EXPANDING_LEVEL_2", 3, 3), 
    EXPANDING_LEVEL_3("EXPANDING_LEVEL_3", 4, 4), 
    EXPANDING_LEVEL_4("EXPANDING_LEVEL_4", 5, 5), 
    IMPLODING("IMPLODING", 6, 6), 
    TOKENIZATION("TOKENIZATION", 7, 7), 
    DEFLATED("DEFLATED", 8, 8), 
    ENHANCED_DEFLATED("ENHANCED_DEFLATED", 9, 9), 
    PKWARE_IMPLODING("PKWARE_IMPLODING", 10, 10), 
    BZIP2("BZIP2", 11, 12), 
    LZMA("LZMA", 12, 14), 
    JPEG("JPEG", 13, 96), 
    WAVPACK("WAVPACK", 14, 97), 
    PPMD("PPMD", 15, 98), 
    AES_ENCRYPTED("AES_ENCRYPTED", 16, 99), 
    UNKNOWN("UNKNOWN", 17, -1);
    
    private final int code;
    private static final Map codeToEnum;
    private static final ZipMethod[] $VALUES;
    
    private ZipMethod(final String s, final int n, final int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public static ZipMethod getMethodByCode(final int n) {
        return ZipMethod.codeToEnum.get(n);
    }
    
    static {
        $VALUES = new ZipMethod[] { ZipMethod.STORED, ZipMethod.UNSHRINKING, ZipMethod.EXPANDING_LEVEL_1, ZipMethod.EXPANDING_LEVEL_2, ZipMethod.EXPANDING_LEVEL_3, ZipMethod.EXPANDING_LEVEL_4, ZipMethod.IMPLODING, ZipMethod.TOKENIZATION, ZipMethod.DEFLATED, ZipMethod.ENHANCED_DEFLATED, ZipMethod.PKWARE_IMPLODING, ZipMethod.BZIP2, ZipMethod.LZMA, ZipMethod.JPEG, ZipMethod.WAVPACK, ZipMethod.PPMD, ZipMethod.AES_ENCRYPTED, ZipMethod.UNKNOWN };
        final HashMap<Integer, ZipMethod> hashMap = new HashMap<Integer, ZipMethod>();
        final ZipMethod[] values = values();
        while (0 < values.length) {
            final ZipMethod zipMethod = values[0];
            hashMap.put(zipMethod.getCode(), zipMethod);
            int n = 0;
            ++n;
        }
        codeToEnum = Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
}
