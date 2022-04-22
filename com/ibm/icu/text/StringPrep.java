package com.ibm.icu.text;

import java.lang.ref.*;
import com.ibm.icu.util.*;
import com.ibm.icu.lang.*;
import java.io.*;
import com.ibm.icu.impl.*;

public final class StringPrep
{
    public static final int DEFAULT = 0;
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int RFC3491_NAMEPREP = 0;
    public static final int RFC3530_NFS4_CS_PREP = 1;
    public static final int RFC3530_NFS4_CS_PREP_CI = 2;
    public static final int RFC3530_NFS4_CIS_PREP = 3;
    public static final int RFC3530_NFS4_MIXED_PREP_PREFIX = 4;
    public static final int RFC3530_NFS4_MIXED_PREP_SUFFIX = 5;
    public static final int RFC3722_ISCSI = 6;
    public static final int RFC3920_NODEPREP = 7;
    public static final int RFC3920_RESOURCEPREP = 8;
    public static final int RFC4011_MIB = 9;
    public static final int RFC4013_SASLPREP = 10;
    public static final int RFC4505_TRACE = 11;
    public static final int RFC4518_LDAP = 12;
    public static final int RFC4518_LDAP_CI = 13;
    private static final int MAX_PROFILE = 13;
    private static final WeakReference[] CACHE;
    private static final int UNASSIGNED = 0;
    private static final int MAP = 1;
    private static final int PROHIBITED = 2;
    private static final int DELETE = 3;
    private static final int TYPE_LIMIT = 4;
    private static final int NORMALIZATION_ON = 1;
    private static final int CHECK_BIDI_ON = 2;
    private static final int TYPE_THRESHOLD = 65520;
    private static final int MAX_INDEX_VALUE = 16319;
    private static final int INDEX_TRIE_SIZE = 0;
    private static final int INDEX_MAPPING_DATA_SIZE = 1;
    private static final int NORM_CORRECTNS_LAST_UNI_VERSION = 2;
    private static final int ONE_UCHAR_MAPPING_INDEX_START = 3;
    private static final int TWO_UCHARS_MAPPING_INDEX_START = 4;
    private static final int THREE_UCHARS_MAPPING_INDEX_START = 5;
    private static final int FOUR_UCHARS_MAPPING_INDEX_START = 6;
    private static final int OPTIONS = 7;
    private static final int INDEX_TOP = 16;
    private static final int DATA_BUFFER_SIZE = 25000;
    private CharTrie sprepTrie;
    private int[] indexes;
    private char[] mappingData;
    private VersionInfo sprepUniVer;
    private VersionInfo normCorrVer;
    private boolean doNFKC;
    private boolean checkBiDi;
    private UBiDiProps bdp;
    
    private char getCodePointValue(final int n) {
        return this.sprepTrie.getCodePointValue(n);
    }
    
    private static VersionInfo getVersionInfo(final int n) {
        return VersionInfo.getInstance(n >> 24 & 0xFF, n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }
    
    private static VersionInfo getVersionInfo(final byte[] array) {
        if (array.length != 4) {
            return null;
        }
        return VersionInfo.getInstance(array[0], array[1], array[2], array[3]);
    }
    
    public StringPrep(final InputStream inputStream) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 25000);
        final StringPrepDataReader stringPrepDataReader = new StringPrepDataReader(bufferedInputStream);
        this.indexes = stringPrepDataReader.readIndexes(16);
        final byte[] array = new byte[this.indexes[0]];
        stringPrepDataReader.read(array, this.mappingData = new char[this.indexes[1] / 2]);
        this.sprepTrie = new CharTrie(new ByteArrayInputStream(array), null);
        stringPrepDataReader.getDataFormatVersion();
        this.doNFKC = ((this.indexes[7] & 0x1) > 0);
        this.checkBiDi = ((this.indexes[7] & 0x2) > 0);
        this.sprepUniVer = getVersionInfo(stringPrepDataReader.getUnicodeVersion());
        this.normCorrVer = getVersionInfo(this.indexes[2]);
        final VersionInfo unicodeVersion = UCharacter.getUnicodeVersion();
        if (unicodeVersion.compareTo(this.sprepUniVer) < 0 && unicodeVersion.compareTo(this.normCorrVer) < 0 && (this.indexes[7] & 0x1) > 0) {
            throw new IOException("Normalization Correction version not supported");
        }
        bufferedInputStream.close();
        if (this.checkBiDi) {
            this.bdp = UBiDiProps.INSTANCE;
        }
    }
    
    public static StringPrep getInstance(final int n) {
        if (n < 0 || n > 13) {
            throw new IllegalArgumentException("Bad profile type");
        }
        StringPrep stringPrep = null;
        // monitorenter(cache = StringPrep.CACHE)
        final WeakReference weakReference = StringPrep.CACHE[n];
        if (weakReference != null) {
            stringPrep = weakReference.get();
        }
        if (stringPrep == null) {
            final InputStream requiredStream = ICUData.getRequiredStream("data/icudt51b/" + StringPrep.PROFILE_NAMES[n] + ".spp");
            if (requiredStream != null) {
                stringPrep = new StringPrep(requiredStream);
                requiredStream.close();
            }
            if (stringPrep != null) {
                StringPrep.CACHE[n] = new WeakReference(stringPrep);
            }
        }
        // monitorexit(cache)
        return stringPrep;
    }
    
    private static final void getValues(final char c, final Values values) {
        values.reset();
        if (c == '\0') {
            values.type = 4;
        }
        else if (c >= '\ufff0') {
            values.type = c - '\ufff0';
        }
        else {
            values.type = 1;
            if ((c & '\u0002') > 0) {
                values.isIndex = true;
                values.value = c >> 2;
            }
            else {
                values.isIndex = false;
                values.value = c << 16 >> 16;
                values.value >>= 2;
            }
            if (c >> 2 == 16319) {
                values.type = 3;
                values.isIndex = false;
                values.value = 0;
            }
        }
    }
    
    private StringBuffer map(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        final Values values = new Values(null);
        final StringBuffer sb = new StringBuffer();
        final boolean b = (n & 0x1) > 0;
        while (uCharacterIterator.nextCodePoint() != -1) {
            this.getCodePointValue(-1);
            getValues('\0', values);
            if (values.type == 0 && !b) {
                throw new StringPrepParseException("An unassigned code point was found in the input", 3, uCharacterIterator.getText(), uCharacterIterator.getIndex());
            }
            if (values.type == 1) {
                if (values.isIndex) {
                    int value = values.value;
                    if (value < this.indexes[3] || value >= this.indexes[4]) {
                        if (value < this.indexes[4] || value >= this.indexes[5]) {
                            if (value < this.indexes[5] || value >= this.indexes[6]) {
                                final char c = this.mappingData[value++];
                            }
                        }
                    }
                    sb.append(this.mappingData, value, 3);
                    continue;
                }
                final int n2 = -1 - values.value;
            }
            else if (values.type == 3) {
                continue;
            }
            UTF16.append(sb, -1);
        }
        return sb;
    }
    
    private StringBuffer normalize(final StringBuffer sb) {
        return new StringBuffer(Normalizer.normalize(sb.toString(), Normalizer.NFKC, 32));
    }
    
    public StringBuffer prepare(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        StringBuffer sb2;
        final StringBuffer sb = sb2 = this.map(uCharacterIterator, n);
        if (this.doNFKC) {
            sb2 = this.normalize(sb);
        }
        final UCharacterIterator instance = UCharacterIterator.getInstance(sb2);
        final Values values = new Values(null);
        int nextCodePoint;
        while ((nextCodePoint = instance.nextCodePoint()) != -1) {
            getValues(this.getCodePointValue(nextCodePoint), values);
            if (values.type == 2) {
                throw new StringPrepParseException("A prohibited code point was found in the input", 2, instance.getText(), values.value);
            }
            if (!this.checkBiDi) {
                continue;
            }
            this.bdp.getClass(nextCodePoint);
            if (19 == 19) {}
            if (19 == 0) {
                final int n2 = instance.getIndex() - 1;
            }
            if (19 != 1 && 19 != 13) {
                continue;
            }
            final int n3 = instance.getIndex() - 1;
        }
        if (this.checkBiDi) {
            if (true == true && true == true) {
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, instance.getText(), (-1 > -1) ? -1 : -1);
            }
            if (true == true && ((19 != 1 && 19 != 13) || (19 != 1 && 19 != 13))) {
                throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, instance.getText(), (-1 > -1) ? -1 : -1);
            }
        }
        return sb2;
    }
    
    public String prepare(final String s, final int n) throws StringPrepParseException {
        return this.prepare(UCharacterIterator.getInstance(s), n).toString();
    }
    
    static {
        StringPrep.PROFILE_NAMES = new String[] { "rfc3491", "rfc3530cs", "rfc3530csci", "rfc3491", "rfc3530mixp", "rfc3491", "rfc3722", "rfc3920node", "rfc3920res", "rfc4011", "rfc4013", "rfc4505", "rfc4518", "rfc4518ci" };
        CACHE = new WeakReference[14];
    }
    
    private static final class Values
    {
        boolean isIndex;
        int value;
        int type;
        
        private Values() {
        }
        
        public void reset() {
            this.isIndex = false;
            this.value = 0;
            this.type = -1;
        }
        
        Values(final StringPrep$1 object) {
            this();
        }
    }
}
