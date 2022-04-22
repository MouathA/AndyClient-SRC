package org.apache.commons.compress.archivers.sevenz;

import java.util.*;

public enum SevenZMethod
{
    COPY("COPY", 0, new byte[] { 0 }), 
    LZMA("LZMA", 1, new byte[] { 3, 1, 1 }), 
    LZMA2("LZMA2", 2, new byte[] { 33 }), 
    DEFLATE("DEFLATE", 3, new byte[] { 4, 1, 8 }), 
    BZIP2("BZIP2", 4, new byte[] { 4, 2, 2 }), 
    AES256SHA256("AES256SHA256", 5, new byte[] { 6, -15, 7, 1 }), 
    BCJ_X86_FILTER("BCJ_X86_FILTER", 6, new byte[] { 3, 3, 1, 3 }), 
    BCJ_PPC_FILTER("BCJ_PPC_FILTER", 7, new byte[] { 3, 3, 2, 5 }), 
    BCJ_IA64_FILTER("BCJ_IA64_FILTER", 8, new byte[] { 3, 3, 4, 1 }), 
    BCJ_ARM_FILTER("BCJ_ARM_FILTER", 9, new byte[] { 3, 3, 5, 1 }), 
    BCJ_ARM_THUMB_FILTER("BCJ_ARM_THUMB_FILTER", 10, new byte[] { 3, 3, 7, 1 }), 
    BCJ_SPARC_FILTER("BCJ_SPARC_FILTER", 11, new byte[] { 3, 3, 8, 5 }), 
    DELTA_FILTER("DELTA_FILTER", 12, new byte[] { 3 });
    
    private final byte[] id;
    private static final SevenZMethod[] $VALUES;
    
    private SevenZMethod(final String s, final int n, final byte[] id) {
        this.id = id;
    }
    
    byte[] getId() {
        final byte[] array = new byte[this.id.length];
        System.arraycopy(this.id, 0, array, 0, this.id.length);
        return array;
    }
    
    static SevenZMethod byId(final byte[] array) {
        final SevenZMethod[] array2 = SevenZMethod.class.getEnumConstants();
        while (0 < array2.length) {
            final SevenZMethod sevenZMethod = array2[0];
            if (Arrays.equals(sevenZMethod.id, array)) {
                return sevenZMethod;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    static {
        $VALUES = new SevenZMethod[] { SevenZMethod.COPY, SevenZMethod.LZMA, SevenZMethod.LZMA2, SevenZMethod.DEFLATE, SevenZMethod.BZIP2, SevenZMethod.AES256SHA256, SevenZMethod.BCJ_X86_FILTER, SevenZMethod.BCJ_PPC_FILTER, SevenZMethod.BCJ_IA64_FILTER, SevenZMethod.BCJ_ARM_FILTER, SevenZMethod.BCJ_ARM_THUMB_FILTER, SevenZMethod.BCJ_SPARC_FILTER, SevenZMethod.DELTA_FILTER };
    }
}
