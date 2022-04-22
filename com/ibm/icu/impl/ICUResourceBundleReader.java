package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;
import com.ibm.icu.util.*;

public final class ICUResourceBundleReader implements ICUBinary.Authenticate
{
    private static final byte[] DATA_FORMAT_ID;
    private static final int URES_INDEX_LENGTH = 0;
    private static final int URES_INDEX_KEYS_TOP = 1;
    private static final int URES_INDEX_BUNDLE_TOP = 3;
    private static final int URES_INDEX_ATTRIBUTES = 5;
    private static final int URES_INDEX_16BIT_TOP = 6;
    private static final int URES_INDEX_POOL_CHECKSUM = 7;
    private static final int URES_ATT_NO_FALLBACK = 1;
    private static final int URES_ATT_IS_POOL_BUNDLE = 2;
    private static final int URES_ATT_USES_POOL_BUNDLE = 4;
    private static final boolean DEBUG = false;
    private byte[] dataVersion;
    private String s16BitUnits;
    private byte[] poolBundleKeys;
    private String poolBundleKeysAsString;
    private int rootRes;
    private int localKeyLimit;
    private boolean noFallback;
    private boolean isPoolBundle;
    private boolean usesPoolBundle;
    private int[] indexes;
    private byte[] keyStrings;
    private String keyStringsAsString;
    private byte[] resourceBytes;
    private int resourceBottom;
    private static ReaderCache CACHE;
    private static final ICUResourceBundleReader NULL_READER;
    private static byte[] emptyBytes;
    private static ByteBuffer emptyByteBuffer;
    private static char[] emptyChars;
    private static int[] emptyInts;
    private static String emptyString;
    private static final String ICU_RESOURCE_SUFFIX = ".res";
    
    private ICUResourceBundleReader() {
    }
    
    private ICUResourceBundleReader(final InputStream inputStream, final String s, final String s2, final ClassLoader classLoader) {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        try {
            this.dataVersion = ICUBinary.readHeader(bufferedInputStream, ICUResourceBundleReader.DATA_FORMAT_ID, this);
            this.readData(bufferedInputStream);
            inputStream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("Data file " + getFullName(s, s2) + " is corrupt - " + ex.getMessage());
        }
        if (this.usesPoolBundle) {
            final ICUResourceBundleReader reader = getReader(s, "pool", classLoader);
            if (!reader.isPoolBundle) {
                throw new IllegalStateException("pool.res is not a pool bundle");
            }
            if (reader.indexes[7] != this.indexes[7]) {
                throw new IllegalStateException("pool.res has a different checksum than this bundle");
            }
            this.poolBundleKeys = reader.keyStrings;
            this.poolBundleKeysAsString = reader.keyStringsAsString;
        }
    }
    
    static ICUResourceBundleReader getReader(final String s, final String s2, final ClassLoader classLoader) {
        final ReaderInfo readerInfo = new ReaderInfo(s, s2, classLoader);
        final ICUResourceBundleReader icuResourceBundleReader = (ICUResourceBundleReader)ICUResourceBundleReader.CACHE.getInstance(readerInfo, readerInfo);
        if (icuResourceBundleReader == ICUResourceBundleReader.NULL_READER) {
            return null;
        }
        return icuResourceBundleReader;
    }
    
    private void readData(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.rootRes = dataInputStream.readInt();
        final int int1 = dataInputStream.readInt();
        final int n = int1 & 0xFF;
        (this.indexes = new int[n])[0] = int1;
        for (int i = 1; i < n; ++i) {
            this.indexes[i] = dataInputStream.readInt();
        }
        this.resourceBottom = 1 + n << 2;
        if (n > 5) {
            final int n2 = this.indexes[5];
            this.noFallback = ((n2 & 0x1) != 0x0);
            this.isPoolBundle = ((n2 & 0x2) != 0x0);
            this.usesPoolBundle = ((n2 & 0x4) != 0x0);
        }
        final int n3 = this.indexes[3] * 4;
        if (this.indexes[1] > 1 + n) {
            int n4 = 1 + n << 2;
            int n5 = this.indexes[1] << 2;
            this.resourceBottom = n5;
            if (this.isPoolBundle) {
                n5 -= n4;
                n4 = 0;
            }
            else {
                this.localKeyLimit = n5;
            }
            dataInputStream.readFully(this.keyStrings = new byte[n5], n4, n5 - n4);
            if (this.isPoolBundle) {
                while (n4 < n5 && this.keyStrings[n5 - 1] == -86) {
                    this.keyStrings[--n5] = 0;
                }
                this.keyStringsAsString = new String(this.keyStrings, "US-ASCII");
            }
        }
        if (n > 6 && this.indexes[6] > this.indexes[1]) {
            final int n6 = (this.indexes[6] - this.indexes[1]) * 2;
            final char[] array = new char[n6];
            final byte[] array2 = new byte[n6 * 2];
            dataInputStream.readFully(array2);
            for (int j = 0; j < n6; ++j) {
                array[j] = (char)(array2[j * 2] << 8 | (array2[j * 2 + 1] & 0xFF));
            }
            this.s16BitUnits = new String(array);
            this.resourceBottom = this.indexes[6] << 2;
        }
        else {
            this.s16BitUnits = "\u0000";
        }
        dataInputStream.readFully(this.resourceBytes = new byte[n3 - this.resourceBottom]);
    }
    
    VersionInfo getVersion() {
        return VersionInfo.getInstance(this.dataVersion[0], this.dataVersion[1], this.dataVersion[2], this.dataVersion[3]);
    }
    
    public boolean isDataVersionAcceptable(final byte[] array) {
        return (array[0] == 1 && array[1] >= 1) || array[0] == 2;
    }
    
    int getRootResource() {
        return this.rootRes;
    }
    
    boolean getNoFallback() {
        return this.noFallback;
    }
    
    boolean getUsesPoolBundle() {
        return this.usesPoolBundle;
    }
    
    static int RES_GET_TYPE(final int n) {
        return n >>> 28;
    }
    
    private static int RES_GET_OFFSET(final int n) {
        return n & 0xFFFFFFF;
    }
    
    private int getResourceByteOffset(final int n) {
        return (n << 2) - this.resourceBottom;
    }
    
    static int RES_GET_INT(final int n) {
        return n << 4 >> 4;
    }
    
    static int RES_GET_UINT(final int n) {
        return n & 0xFFFFFFF;
    }
    
    static boolean URES_IS_TABLE(final int n) {
        return n == 2 || n == 5 || n == 4;
    }
    
    private char getChar(final int n) {
        return (char)(this.resourceBytes[n] << 8 | (this.resourceBytes[n + 1] & 0xFF));
    }
    
    private char[] getChars(int n, final int n2) {
        final char[] array = new char[n2];
        for (int i = 0; i < n2; ++i) {
            array[i] = (char)(this.resourceBytes[n] << 8 | (this.resourceBytes[n + 1] & 0xFF));
            n += 2;
        }
        return array;
    }
    
    private int getInt(final int n) {
        return this.resourceBytes[n] << 24 | (this.resourceBytes[n + 1] & 0xFF) << 16 | (this.resourceBytes[n + 2] & 0xFF) << 8 | (this.resourceBytes[n + 3] & 0xFF);
    }
    
    private int[] getInts(int n, final int n2) {
        final int[] array = new int[n2];
        for (int i = 0; i < n2; ++i) {
            array[i] = (this.resourceBytes[n] << 24 | (this.resourceBytes[n + 1] & 0xFF) << 16 | (this.resourceBytes[n + 2] & 0xFF) << 8 | (this.resourceBytes[n + 3] & 0xFF));
            n += 4;
        }
        return array;
    }
    
    private char[] getTable16KeyOffsets(int n) {
        final char char1 = this.s16BitUnits.charAt(n++);
        if (char1 > '\0') {
            return this.s16BitUnits.substring(n, n + char1).toCharArray();
        }
        return ICUResourceBundleReader.emptyChars;
    }
    
    private char[] getTableKeyOffsets(final int n) {
        final char char1 = this.getChar(n);
        if (char1 > '\0') {
            return this.getChars(n + 2, char1);
        }
        return ICUResourceBundleReader.emptyChars;
    }
    
    private int[] getTable32KeyOffsets(final int n) {
        final int int1 = this.getInt(n);
        if (int1 > 0) {
            return this.getInts(n + 4, int1);
        }
        return ICUResourceBundleReader.emptyInts;
    }
    
    private String makeKeyStringFromBytes(int n) {
        final StringBuilder sb = new StringBuilder();
        byte b;
        while ((b = this.keyStrings[n++]) != 0) {
            sb.append((char)b);
        }
        return sb.toString();
    }
    
    private String makeKeyStringFromString(final int n) {
        int n2;
        for (n2 = n; this.poolBundleKeysAsString.charAt(n2) != '\0'; ++n2) {}
        return this.poolBundleKeysAsString.substring(n, n2);
    }
    
    private ByteSequence RES_GET_KEY16(final char c) {
        if (c < this.localKeyLimit) {
            return new ByteSequence(this.keyStrings, c);
        }
        return new ByteSequence(this.poolBundleKeys, c - this.localKeyLimit);
    }
    
    private String getKey16String(final int n) {
        if (n < this.localKeyLimit) {
            return this.makeKeyStringFromBytes(n);
        }
        return this.makeKeyStringFromString(n - this.localKeyLimit);
    }
    
    private ByteSequence RES_GET_KEY32(final int n) {
        if (n >= 0) {
            return new ByteSequence(this.keyStrings, n);
        }
        return new ByteSequence(this.poolBundleKeys, n & Integer.MAX_VALUE);
    }
    
    private String getKey32String(final int n) {
        if (n >= 0) {
            return this.makeKeyStringFromBytes(n);
        }
        return this.makeKeyStringFromString(n & Integer.MAX_VALUE);
    }
    
    private static int compareKeys(final CharSequence charSequence, final ByteSequence byteSequence) {
        int i;
        for (i = 0; i < charSequence.length(); ++i) {
            final byte char1 = byteSequence.charAt(i);
            if (char1 == 0) {
                return 1;
            }
            final int n = charSequence.charAt(i) - char1;
            if (n != 0) {
                return n;
            }
        }
        return -byteSequence.charAt(i);
    }
    
    private int compareKeys(final CharSequence charSequence, final char c) {
        return compareKeys(charSequence, this.RES_GET_KEY16(c));
    }
    
    private int compareKeys32(final CharSequence charSequence, final int n) {
        return compareKeys(charSequence, this.RES_GET_KEY32(n));
    }
    
    String getString(final int n) {
        int res_GET_OFFSET = RES_GET_OFFSET(n);
        if (RES_GET_TYPE(n) == 6) {
            final char char1 = this.s16BitUnits.charAt(res_GET_OFFSET);
            if ((char1 & 0xFFFFFC00) == 0xDC00) {
                int n2;
                if (char1 < '\udfef') {
                    n2 = (char1 & '\u03ff');
                    ++res_GET_OFFSET;
                }
                else if (char1 < '\udfff') {
                    n2 = (char1 - '\udfef' << 16 | this.s16BitUnits.charAt(res_GET_OFFSET + 1));
                    res_GET_OFFSET += 2;
                }
                else {
                    n2 = (this.s16BitUnits.charAt(res_GET_OFFSET + 1) << 16 | this.s16BitUnits.charAt(res_GET_OFFSET + 2));
                    res_GET_OFFSET += 3;
                }
                return this.s16BitUnits.substring(res_GET_OFFSET, res_GET_OFFSET + n2);
            }
            if (char1 == '\0') {
                return ICUResourceBundleReader.emptyString;
            }
            int n3;
            for (n3 = res_GET_OFFSET + 1; this.s16BitUnits.charAt(n3) != '\0'; ++n3) {}
            return this.s16BitUnits.substring(res_GET_OFFSET, n3);
        }
        else {
            if (n != res_GET_OFFSET) {
                return null;
            }
            if (n == 0) {
                return ICUResourceBundleReader.emptyString;
            }
            final int resourceByteOffset = this.getResourceByteOffset(res_GET_OFFSET);
            return new String(this.getChars(resourceByteOffset + 4, this.getInt(resourceByteOffset)));
        }
    }
    
    String getAlias(final int n) {
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        if (RES_GET_TYPE(n) != 3) {
            return null;
        }
        if (res_GET_OFFSET == 0) {
            return ICUResourceBundleReader.emptyString;
        }
        final int resourceByteOffset = this.getResourceByteOffset(res_GET_OFFSET);
        return new String(this.getChars(resourceByteOffset + 4, this.getInt(resourceByteOffset)));
    }
    
    byte[] getBinary(final int n, byte[] array) {
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        if (RES_GET_TYPE(n) != 1) {
            return null;
        }
        if (res_GET_OFFSET == 0) {
            return ICUResourceBundleReader.emptyBytes;
        }
        final int resourceByteOffset = this.getResourceByteOffset(res_GET_OFFSET);
        final int int1 = this.getInt(resourceByteOffset);
        if (array == null || array.length != int1) {
            array = new byte[int1];
        }
        System.arraycopy(this.resourceBytes, resourceByteOffset + 4, array, 0, int1);
        return array;
    }
    
    ByteBuffer getBinary(final int n) {
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        if (RES_GET_TYPE(n) != 1) {
            return null;
        }
        if (res_GET_OFFSET == 0) {
            return ICUResourceBundleReader.emptyByteBuffer.duplicate();
        }
        final int resourceByteOffset = this.getResourceByteOffset(res_GET_OFFSET);
        return ByteBuffer.wrap(this.resourceBytes, resourceByteOffset + 4, this.getInt(resourceByteOffset)).slice().asReadOnlyBuffer();
    }
    
    int[] getIntVector(final int n) {
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        if (RES_GET_TYPE(n) != 14) {
            return null;
        }
        if (res_GET_OFFSET == 0) {
            return ICUResourceBundleReader.emptyInts;
        }
        final int resourceByteOffset = this.getResourceByteOffset(res_GET_OFFSET);
        return this.getInts(resourceByteOffset + 4, this.getInt(resourceByteOffset));
    }
    
    Container getArray(final int n) {
        final int res_GET_TYPE = RES_GET_TYPE(n);
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        switch (res_GET_TYPE) {
            case 8:
            case 9: {
                if (res_GET_OFFSET == 0) {
                    return new Container(this);
                }
                switch (res_GET_TYPE) {
                    case 8: {
                        return new Array(this, res_GET_OFFSET);
                    }
                    case 9: {
                        return new Array16(this, res_GET_OFFSET);
                    }
                    default: {
                        return null;
                    }
                }
                break;
            }
            default: {
                return null;
            }
        }
    }
    
    Table getTable(final int n) {
        final int res_GET_TYPE = RES_GET_TYPE(n);
        final int res_GET_OFFSET = RES_GET_OFFSET(n);
        switch (res_GET_TYPE) {
            case 2:
            case 4:
            case 5: {
                if (res_GET_OFFSET == 0) {
                    return new Table(this);
                }
                switch (res_GET_TYPE) {
                    case 2: {
                        return new Table1632(this, res_GET_OFFSET);
                    }
                    case 5: {
                        return new Table16(this, res_GET_OFFSET);
                    }
                    case 4: {
                        return new Table32(this, res_GET_OFFSET);
                    }
                    default: {
                        return null;
                    }
                }
                break;
            }
            default: {
                return null;
            }
        }
    }
    
    public static String getFullName(String replace, String string) {
        if (replace == null || replace.length() == 0) {
            if (string.length() == 0) {
                return string = ULocale.getDefault().toString();
            }
            return string + ".res";
        }
        else if (replace.indexOf(46) == -1) {
            if (replace.charAt(replace.length() - 1) != '/') {
                return replace + "/" + string + ".res";
            }
            return replace + string + ".res";
        }
        else {
            replace = replace.replace('.', '/');
            if (string.length() == 0) {
                return replace + ".res";
            }
            return replace + "_" + string + ".res";
        }
    }
    
    static ICUResourceBundleReader access$100() {
        return ICUResourceBundleReader.NULL_READER;
    }
    
    ICUResourceBundleReader(final InputStream inputStream, final String s, final String s2, final ClassLoader classLoader, final ICUResourceBundleReader$1 object) {
        this(inputStream, s, s2, classLoader);
    }
    
    static String access$300(final ICUResourceBundleReader icuResourceBundleReader) {
        return icuResourceBundleReader.s16BitUnits;
    }
    
    static int access$400(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getInt(n);
    }
    
    static int access$500(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getResourceByteOffset(n);
    }
    
    static String access$600(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getKey16String(n);
    }
    
    static String access$700(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getKey32String(n);
    }
    
    static int access$800(final ICUResourceBundleReader icuResourceBundleReader, final CharSequence charSequence, final char c) {
        return icuResourceBundleReader.compareKeys(charSequence, c);
    }
    
    static int access$900(final ICUResourceBundleReader icuResourceBundleReader, final CharSequence charSequence, final int n) {
        return icuResourceBundleReader.compareKeys32(charSequence, n);
    }
    
    static char[] access$1000(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getTableKeyOffsets(n);
    }
    
    static char[] access$1100(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getTable16KeyOffsets(n);
    }
    
    static int[] access$1200(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
        return icuResourceBundleReader.getTable32KeyOffsets(n);
    }
    
    static {
        DATA_FORMAT_ID = new byte[] { 82, 101, 115, 66 };
        ICUResourceBundleReader.CACHE = new ReaderCache(null);
        NULL_READER = new ICUResourceBundleReader();
        ICUResourceBundleReader.emptyBytes = new byte[0];
        ICUResourceBundleReader.emptyByteBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
        ICUResourceBundleReader.emptyChars = new char[0];
        ICUResourceBundleReader.emptyInts = new int[0];
        ICUResourceBundleReader.emptyString = "";
    }
    
    private static final class Table32 extends Table
    {
        @Override
        int getContainerResource(final int n) {
            return this.getContainer32Resource(n);
        }
        
        Table32(final ICUResourceBundleReader icuResourceBundleReader, int access$500) {
            super(icuResourceBundleReader);
            access$500 = ICUResourceBundleReader.access$500(icuResourceBundleReader, access$500);
            this.key32Offsets = ICUResourceBundleReader.access$1200(icuResourceBundleReader, access$500);
            this.size = this.key32Offsets.length;
            this.itemsOffset = access$500 + 4 * (1 + this.size);
        }
    }
    
    static class Table extends Container
    {
        protected char[] keyOffsets;
        protected int[] key32Offsets;
        private static final int URESDATA_ITEM_NOT_FOUND = -1;
        
        String getKey(final int n) {
            if (n < 0 || this.size <= n) {
                return null;
            }
            return (this.keyOffsets != null) ? ICUResourceBundleReader.access$600(this.reader, this.keyOffsets[n]) : ICUResourceBundleReader.access$700(this.reader, this.key32Offsets[n]);
        }
        
        int findTableItem(final CharSequence charSequence) {
            int size = this.size;
            while (0 < size) {
                final int n = 0 + size >>> 1;
                int n2;
                if (this.keyOffsets != null) {
                    n2 = ICUResourceBundleReader.access$800(this.reader, charSequence, this.keyOffsets[n]);
                }
                else {
                    n2 = ICUResourceBundleReader.access$900(this.reader, charSequence, this.key32Offsets[n]);
                }
                if (n2 < 0) {
                    size = n;
                }
                else {
                    if (n2 > 0) {
                        continue;
                    }
                    return n;
                }
            }
            return -1;
        }
        
        int getTableResource(final String s) {
            return this.getContainerResource(this.findTableItem(s));
        }
        
        Table(final ICUResourceBundleReader icuResourceBundleReader) {
            super(icuResourceBundleReader);
        }
    }
    
    static class Container
    {
        protected ICUResourceBundleReader reader;
        protected int size;
        protected int itemsOffset;
        
        int getSize() {
            return this.size;
        }
        
        int getContainerResource(final int n) {
            return -1;
        }
        
        protected int getContainer16Resource(final int n) {
            if (n < 0 || this.size <= n) {
                return -1;
            }
            return 0x60000000 | ICUResourceBundleReader.access$300(this.reader).charAt(this.itemsOffset + n);
        }
        
        protected int getContainer32Resource(final int n) {
            if (n < 0 || this.size <= n) {
                return -1;
            }
            return ICUResourceBundleReader.access$400(this.reader, this.itemsOffset + 4 * n);
        }
        
        Container(final ICUResourceBundleReader reader) {
            this.reader = reader;
        }
    }
    
    private static final class Table16 extends Table
    {
        @Override
        int getContainerResource(final int n) {
            return this.getContainer16Resource(n);
        }
        
        Table16(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
            super(icuResourceBundleReader);
            this.keyOffsets = ICUResourceBundleReader.access$1100(icuResourceBundleReader, n);
            this.size = this.keyOffsets.length;
            this.itemsOffset = n + 1 + this.size;
        }
    }
    
    private static final class Table1632 extends Table
    {
        @Override
        int getContainerResource(final int n) {
            return this.getContainer32Resource(n);
        }
        
        Table1632(final ICUResourceBundleReader icuResourceBundleReader, int access$500) {
            super(icuResourceBundleReader);
            access$500 = ICUResourceBundleReader.access$500(icuResourceBundleReader, access$500);
            this.keyOffsets = ICUResourceBundleReader.access$1000(icuResourceBundleReader, access$500);
            this.size = this.keyOffsets.length;
            this.itemsOffset = access$500 + 2 * (this.size + 2 & 0xFFFFFFFE);
        }
    }
    
    private static final class Array16 extends Container
    {
        @Override
        int getContainerResource(final int n) {
            return this.getContainer16Resource(n);
        }
        
        Array16(final ICUResourceBundleReader icuResourceBundleReader, final int n) {
            super(icuResourceBundleReader);
            this.size = ICUResourceBundleReader.access$300(icuResourceBundleReader).charAt(n);
            this.itemsOffset = n + 1;
        }
    }
    
    private static final class Array extends Container
    {
        @Override
        int getContainerResource(final int n) {
            return this.getContainer32Resource(n);
        }
        
        Array(final ICUResourceBundleReader icuResourceBundleReader, int access$500) {
            super(icuResourceBundleReader);
            access$500 = ICUResourceBundleReader.access$500(icuResourceBundleReader, access$500);
            this.size = ICUResourceBundleReader.access$400(icuResourceBundleReader, access$500);
            this.itemsOffset = access$500 + 4;
        }
    }
    
    private static final class ByteSequence
    {
        private byte[] bytes;
        private int offset;
        
        public ByteSequence(final byte[] bytes, final int offset) {
            this.bytes = bytes;
            this.offset = offset;
        }
        
        public byte charAt(final int n) {
            return this.bytes[this.offset + n];
        }
    }
    
    private static class ReaderCache extends SoftCache
    {
        private ReaderCache() {
        }
        
        protected ICUResourceBundleReader createInstance(final ReaderInfo readerInfo, final ReaderInfo readerInfo2) {
            final InputStream stream = ICUData.getStream(readerInfo2.loader, ICUResourceBundleReader.getFullName(readerInfo2.baseName, readerInfo2.localeID));
            if (stream == null) {
                return ICUResourceBundleReader.access$100();
            }
            return new ICUResourceBundleReader(stream, readerInfo2.baseName, readerInfo2.localeID, readerInfo2.loader, null);
        }
        
        @Override
        protected Object createInstance(final Object o, final Object o2) {
            return this.createInstance((ReaderInfo)o, (ReaderInfo)o2);
        }
        
        ReaderCache(final ICUResourceBundleReader$1 object) {
            this();
        }
    }
    
    private static class ReaderInfo
    {
        final String baseName;
        final String localeID;
        final ClassLoader loader;
        
        ReaderInfo(final String s, final String s2, final ClassLoader loader) {
            this.baseName = ((s == null) ? "" : s);
            this.localeID = ((s2 == null) ? "" : s2);
            this.loader = loader;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ReaderInfo)) {
                return false;
            }
            final ReaderInfo readerInfo = (ReaderInfo)o;
            return this.baseName.equals(readerInfo.baseName) && this.localeID.equals(readerInfo.localeID) && this.loader.equals(readerInfo.loader);
        }
        
        @Override
        public int hashCode() {
            return this.baseName.hashCode() ^ this.localeID.hashCode() ^ this.loader.hashCode();
        }
    }
}
