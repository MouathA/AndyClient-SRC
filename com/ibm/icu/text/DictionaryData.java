package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.io.*;

final class DictionaryData
{
    public static final int TRIE_TYPE_BYTES = 0;
    public static final int TRIE_TYPE_UCHARS = 1;
    public static final int TRIE_TYPE_MASK = 7;
    public static final int TRIE_HAS_VALUES = 8;
    public static final int TRANSFORM_NONE = 0;
    public static final int TRANSFORM_TYPE_OFFSET = 16777216;
    public static final int TRANSFORM_TYPE_MASK = 2130706432;
    public static final int TRANSFORM_OFFSET_MASK = 2097151;
    public static final int IX_STRING_TRIE_OFFSET = 0;
    public static final int IX_RESERVED1_OFFSET = 1;
    public static final int IX_RESERVED2_OFFSET = 2;
    public static final int IX_TOTAL_SIZE = 3;
    public static final int IX_TRIE_TYPE = 4;
    public static final int IX_TRANSFORM = 5;
    public static final int IX_RESERVED6 = 6;
    public static final int IX_RESERVED7 = 7;
    public static final int IX_COUNT = 8;
    private static final byte[] DATA_FORMAT_ID;
    
    private DictionaryData() {
    }
    
    public static DictionaryMatcher loadDictionaryFor(final String s) throws IOException {
        final InputStream stream = ICUData.getStream("data/icudt51b/brkitr/" + ((ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b/brkitr")).getStringWithFallback("dictionaries/" + s));
        ICUBinary.readHeader(stream, DictionaryData.DATA_FORMAT_ID, null);
        final DataInputStream dataInputStream = new DataInputStream(stream);
        final int[] array = new int[8];
        while (0 < 8) {
            array[0] = dataInputStream.readInt();
            int n = 0;
            ++n;
        }
        int n = array[0];
        Assert.assrt(0 >= 32);
        if (0 > 32) {
            dataInputStream.skipBytes(-32);
        }
        final int n2 = array[4] & 0x7;
        final int n3 = array[3] - 0;
        DictionaryMatcher dictionaryMatcher;
        if (-32 == 0) {
            final int n4 = array[5];
            final byte[] array2 = new byte[n3];
            while (0 < array2.length) {
                array2[0] = dataInputStream.readByte();
                int n5 = 0;
                ++n5;
            }
            Assert.assrt(n3 == 0);
            dictionaryMatcher = new BytesDictionaryMatcher(array2, n4);
        }
        else if (-32 == 1) {
            Assert.assrt(n3 % 2 == 0);
            final int n6 = n3 / 2;
            final char[] array3 = new char[n3 / 2];
            while (0 < n6) {
                array3[0] = dataInputStream.readChar();
                int n5 = 0;
                ++n5;
            }
            dictionaryMatcher = new CharsDictionaryMatcher(new String(array3));
        }
        else {
            dictionaryMatcher = null;
        }
        dataInputStream.close();
        stream.close();
        return dictionaryMatcher;
    }
    
    static {
        DATA_FORMAT_ID = new byte[] { 68, 105, 99, 116 };
    }
}
