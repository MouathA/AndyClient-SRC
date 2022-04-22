package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import com.ibm.icu.lang.*;
import java.io.*;
import com.ibm.icu.text.*;
import java.util.*;

public final class UCharacterProperty
{
    public static final UCharacterProperty INSTANCE;
    public Trie2_16 m_trie_;
    public VersionInfo m_unicodeVersion_;
    public static final char LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE_ = '\u0130';
    public static final char LATIN_SMALL_LETTER_DOTLESS_I_ = '\u0131';
    public static final char LATIN_SMALL_LETTER_I_ = 'i';
    public static final int TYPE_MASK = 31;
    public static final int SRC_NONE = 0;
    public static final int SRC_CHAR = 1;
    public static final int SRC_PROPSVEC = 2;
    public static final int SRC_NAMES = 3;
    public static final int SRC_CASE = 4;
    public static final int SRC_BIDI = 5;
    public static final int SRC_CHAR_AND_PROPSVEC = 6;
    public static final int SRC_CASE_AND_NORM = 7;
    public static final int SRC_NFC = 8;
    public static final int SRC_NFKC = 9;
    public static final int SRC_NFKC_CF = 10;
    public static final int SRC_NFC_CANON_ITER = 11;
    public static final int SRC_COUNT = 12;
    static final int MY_MASK = 30;
    private static final int GC_CN_MASK;
    private static final int GC_CC_MASK;
    private static final int GC_CS_MASK;
    private static final int GC_ZS_MASK;
    private static final int GC_ZL_MASK;
    private static final int GC_ZP_MASK;
    private static final int GC_Z_MASK;
    BinaryProperty[] binProps;
    private static final int[] gcbToHst;
    IntProperty[] intProps;
    Trie2_16 m_additionalTrie_;
    int[] m_additionalVectors_;
    int m_additionalColumnsCount_;
    int m_maxBlockScriptValue_;
    int m_maxJTGValue_;
    public char[] m_scriptExtensions_;
    private static final String DATA_FILE_NAME_ = "data/icudt51b/uprops.icu";
    private static final int DATA_BUFFER_SIZE_ = 25000;
    private static final int LEAD_SURROGATE_SHIFT_ = 10;
    private static final int SURROGATE_OFFSET_ = -56613888;
    private static final int NUMERIC_TYPE_VALUE_SHIFT_ = 6;
    private static final int NTV_NONE_ = 0;
    private static final int NTV_DECIMAL_START_ = 1;
    private static final int NTV_DIGIT_START_ = 11;
    private static final int NTV_NUMERIC_START_ = 21;
    private static final int NTV_FRACTION_START_ = 176;
    private static final int NTV_LARGE_START_ = 480;
    private static final int NTV_BASE60_START_ = 768;
    private static final int NTV_RESERVED_START_ = 804;
    public static final int SCRIPT_X_MASK = 12583167;
    private static final int EAST_ASIAN_MASK_ = 917504;
    private static final int EAST_ASIAN_SHIFT_ = 17;
    private static final int BLOCK_MASK_ = 130816;
    private static final int BLOCK_SHIFT_ = 8;
    public static final int SCRIPT_MASK_ = 255;
    public static final int SCRIPT_X_WITH_COMMON = 4194304;
    public static final int SCRIPT_X_WITH_INHERITED = 8388608;
    public static final int SCRIPT_X_WITH_OTHER = 12582912;
    private static final int WHITE_SPACE_PROPERTY_ = 0;
    private static final int DASH_PROPERTY_ = 1;
    private static final int HYPHEN_PROPERTY_ = 2;
    private static final int QUOTATION_MARK_PROPERTY_ = 3;
    private static final int TERMINAL_PUNCTUATION_PROPERTY_ = 4;
    private static final int MATH_PROPERTY_ = 5;
    private static final int HEX_DIGIT_PROPERTY_ = 6;
    private static final int ASCII_HEX_DIGIT_PROPERTY_ = 7;
    private static final int ALPHABETIC_PROPERTY_ = 8;
    private static final int IDEOGRAPHIC_PROPERTY_ = 9;
    private static final int DIACRITIC_PROPERTY_ = 10;
    private static final int EXTENDER_PROPERTY_ = 11;
    private static final int NONCHARACTER_CODE_POINT_PROPERTY_ = 12;
    private static final int GRAPHEME_EXTEND_PROPERTY_ = 13;
    private static final int GRAPHEME_LINK_PROPERTY_ = 14;
    private static final int IDS_BINARY_OPERATOR_PROPERTY_ = 15;
    private static final int IDS_TRINARY_OPERATOR_PROPERTY_ = 16;
    private static final int RADICAL_PROPERTY_ = 17;
    private static final int UNIFIED_IDEOGRAPH_PROPERTY_ = 18;
    private static final int DEFAULT_IGNORABLE_CODE_POINT_PROPERTY_ = 19;
    private static final int DEPRECATED_PROPERTY_ = 20;
    private static final int LOGICAL_ORDER_EXCEPTION_PROPERTY_ = 21;
    private static final int XID_START_PROPERTY_ = 22;
    private static final int XID_CONTINUE_PROPERTY_ = 23;
    private static final int ID_START_PROPERTY_ = 24;
    private static final int ID_CONTINUE_PROPERTY_ = 25;
    private static final int GRAPHEME_BASE_PROPERTY_ = 26;
    private static final int S_TERM_PROPERTY_ = 27;
    private static final int VARIATION_SELECTOR_PROPERTY_ = 28;
    private static final int PATTERN_SYNTAX = 29;
    private static final int PATTERN_WHITE_SPACE = 30;
    private static final int LB_MASK = 66060288;
    private static final int LB_SHIFT = 20;
    private static final int SB_MASK = 1015808;
    private static final int SB_SHIFT = 15;
    private static final int WB_MASK = 31744;
    private static final int WB_SHIFT = 10;
    private static final int GCB_MASK = 992;
    private static final int GCB_SHIFT = 5;
    private static final int DECOMPOSITION_TYPE_MASK_ = 31;
    private static final int FIRST_NIBBLE_SHIFT_ = 4;
    private static final int LAST_NIBBLE_MASK_ = 15;
    private static final int AGE_SHIFT_ = 24;
    private static final byte[] DATA_FORMAT;
    private static final int TAB = 9;
    private static final int CR = 13;
    private static final int U_A = 65;
    private static final int U_F = 70;
    private static final int U_Z = 90;
    private static final int U_a = 97;
    private static final int U_f = 102;
    private static final int U_z = 122;
    private static final int DEL = 127;
    private static final int NL = 133;
    private static final int NBSP = 160;
    private static final int CGJ = 847;
    private static final int FIGURESP = 8199;
    private static final int HAIRSP = 8202;
    private static final int RLM = 8207;
    private static final int NNBSP = 8239;
    private static final int WJ = 8288;
    private static final int INHSWAP = 8298;
    private static final int NOMDIG = 8303;
    private static final int U_FW_A = 65313;
    private static final int U_FW_F = 65318;
    private static final int U_FW_Z = 65338;
    private static final int U_FW_a = 65345;
    private static final int U_FW_f = 65350;
    private static final int U_FW_z = 65370;
    private static final int ZWNBSP = 65279;
    static final boolean $assertionsDisabled;
    
    public final int getProperty(final int n) {
        return this.m_trie_.get(n);
    }
    
    public int getAdditional(final int n, final int n2) {
        assert n2 >= 0;
        if (n2 >= this.m_additionalColumnsCount_) {
            return 0;
        }
        return this.m_additionalVectors_[this.m_additionalTrie_.get(n) + n2];
    }
    
    public VersionInfo getAge(final int n) {
        final int n2 = this.getAdditional(n, 0) >> 24;
        return VersionInfo.getInstance(n2 >> 4 & 0xF, n2 & 0xF, 0, 0);
    }
    
    private static final boolean isgraphPOSIX(final int n) {
        return (getMask(UCharacter.getType(n)) & (UCharacterProperty.GC_CC_MASK | UCharacterProperty.GC_CS_MASK | UCharacterProperty.GC_CN_MASK | UCharacterProperty.GC_Z_MASK)) == 0x0;
    }
    
    public boolean hasBinaryProperty(final int n, final int n2) {
        return n2 >= 0 && 57 > n2 && this.binProps[n2].contains(n);
    }
    
    public int getType(final int n) {
        return this.getProperty(n) & 0x1F;
    }
    
    public int getIntPropertyValue(final int n, final int n2) {
        if (n2 < 4096) {
            if (0 <= n2 && n2 < 57) {
                return this.binProps[n2].contains(n) ? 1 : 0;
            }
        }
        else {
            if (n2 < 4117) {
                return this.intProps[n2 - 4096].getValue(n);
            }
            if (n2 == 8192) {
                return getMask(this.getType(n));
            }
        }
        return 0;
    }
    
    public int getIntPropertyMaxValue(final int n) {
        if (n < 4096) {
            if (0 <= n && n < 57) {
                return 1;
            }
        }
        else if (n < 4117) {
            return this.intProps[n - 4096].getMaxValue(n);
        }
        return -1;
    }
    
    public final int getSource(final int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 57) {
            return this.binProps[n].getSource();
        }
        if (n < 4096) {
            return 0;
        }
        if (n < 4117) {
            return this.intProps[n - 4096].getSource();
        }
        if (n < 16384) {
            switch (n) {
                case 8192:
                case 12288: {
                    return 1;
                }
                default: {
                    return 0;
                }
            }
        }
        else if (n < 16397) {
            switch (n) {
                case 16384: {
                    return 2;
                }
                case 16385: {
                    return 5;
                }
                case 16386:
                case 16388:
                case 16390:
                case 16391:
                case 16392:
                case 16393:
                case 16394:
                case 16396: {
                    return 4;
                }
                case 16387:
                case 16389:
                case 16395: {
                    return 3;
                }
                default: {
                    return 0;
                }
            }
        }
        else {
            switch (n) {
                case 28672: {
                    return 2;
                }
                default: {
                    return 0;
                }
            }
        }
    }
    
    public static int getRawSupplementary(final char c, final char c2) {
        return (c << 10) + c2 - 56613888;
    }
    
    public int getMaxValues(final int n) {
        switch (n) {
            case 0: {
                return this.m_maxBlockScriptValue_;
            }
            case 2: {
                return this.m_maxJTGValue_;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static final int getMask(final int n) {
        return 1 << n;
    }
    
    public static int getEuropeanDigit(final int n) {
        if ((n > 122 && n < 65313) || n < 65 || (n > 90 && n < 97) || n > 65370 || (n > 65338 && n < 65345)) {
            return -1;
        }
        if (n <= 122) {
            return n + 10 - ((n <= 90) ? 65 : 97);
        }
        if (n <= 65338) {
            return n + 10 - 65313;
        }
        return n + 10 - 65345;
    }
    
    public int digit(final int n) {
        final int n2 = getNumericTypeValue(this.getProperty(n)) - 1;
        if (n2 <= 9) {
            return n2;
        }
        return -1;
    }
    
    public int getNumericValue(final int n) {
        final int numericTypeValue = getNumericTypeValue(this.getProperty(n));
        if (numericTypeValue == 0) {
            return getEuropeanDigit(n);
        }
        if (numericTypeValue < 11) {
            return numericTypeValue - 1;
        }
        if (numericTypeValue < 21) {
            return numericTypeValue - 11;
        }
        if (numericTypeValue < 176) {
            return numericTypeValue - 21;
        }
        if (numericTypeValue < 480) {
            return -2;
        }
        if (numericTypeValue < 768) {
            final int n2 = (numericTypeValue >> 5) - 14;
            int n3 = (numericTypeValue & 0x1F) + 2;
            if (n3 < 9 || (n3 == 9 && n2 <= 2)) {
                int n4 = n2;
                do {
                    n4 *= 10;
                } while (--n3 > 0);
                return n4;
            }
            return -2;
        }
        else {
            if (numericTypeValue < 804) {
                int n5 = (numericTypeValue >> 2) - 191;
                switch ((numericTypeValue & 0x3) + 1) {
                    case 4: {
                        n5 *= 12960000;
                        break;
                    }
                    case 3: {
                        n5 *= 216000;
                        break;
                    }
                    case 2: {
                        n5 *= 3600;
                        break;
                    }
                    case 1: {
                        n5 *= 60;
                        break;
                    }
                }
                return n5;
            }
            return -2;
        }
    }
    
    public double getUnicodeNumericValue(final int n) {
        final int numericTypeValue = getNumericTypeValue(this.getProperty(n));
        if (numericTypeValue == 0) {
            return -1.23456789E8;
        }
        if (numericTypeValue < 11) {
            return numericTypeValue - 1;
        }
        if (numericTypeValue < 21) {
            return numericTypeValue - 11;
        }
        if (numericTypeValue < 176) {
            return numericTypeValue - 21;
        }
        if (numericTypeValue < 480) {
            return ((numericTypeValue >> 4) - 12) / (double)((numericTypeValue & 0xF) + 1);
        }
        if (numericTypeValue < 768) {
            final int n2 = (numericTypeValue >> 5) - 14;
            int i = (numericTypeValue & 0x1F) + 2;
            double n3 = n2;
            while (i >= 4) {
                n3 *= 10000.0;
                i -= 4;
            }
            switch (i) {
                case 3: {
                    n3 *= 1000.0;
                    break;
                }
                case 2: {
                    n3 *= 100.0;
                    break;
                }
                case 1: {
                    n3 *= 10.0;
                    break;
                }
            }
            return n3;
        }
        if (numericTypeValue < 804) {
            int n4 = (numericTypeValue >> 2) - 191;
            switch ((numericTypeValue & 0x3) + 1) {
                case 4: {
                    n4 *= 12960000;
                    break;
                }
                case 3: {
                    n4 *= 216000;
                    break;
                }
                case 2: {
                    n4 *= 3600;
                    break;
                }
                case 1: {
                    n4 *= 60;
                    break;
                }
            }
            return n4;
        }
        return -1.23456789E8;
    }
    
    private static final int getNumericTypeValue(final int n) {
        return n >> 6;
    }
    
    private static final int ntvGetType(final int n) {
        return (n == 0) ? 0 : ((n < 11) ? 1 : ((n < 21) ? 2 : 3));
    }
    
    private UCharacterProperty() throws IOException {
        this.binProps = new BinaryProperty[] { new BinaryProperty(1, 256), new BinaryProperty(1, 128), new BinaryProperty(5) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UBiDiProps.INSTANCE.isBidiControl(n);
                }
            }, new BinaryProperty(5) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UBiDiProps.INSTANCE.isMirrored(n);
                }
            }, new BinaryProperty(1, 2), new BinaryProperty(1, 524288), new BinaryProperty(1, 1048576), new BinaryProperty(1, 1024), new BinaryProperty(1, 2048), new BinaryProperty(8) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    final Normalizer2Impl impl = Norm2AllModes.getNFCInstance().impl;
                    return impl.isCompNo(impl.getNorm16(n));
                }
            }, new BinaryProperty(1, 67108864), new BinaryProperty(1, 8192), new BinaryProperty(1, 16384), new BinaryProperty(1, 64), new BinaryProperty(1, 4), new BinaryProperty(1, 33554432), new BinaryProperty(1, 16777216), new BinaryProperty(1, 512), new BinaryProperty(1, 32768), new BinaryProperty(1, 65536), new BinaryProperty(5) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UBiDiProps.INSTANCE.isJoinControl(n);
                }
            }, new BinaryProperty(1, 2097152), new CaseBinaryProperty(22), new BinaryProperty(1, 32), new BinaryProperty(1, 4096), new BinaryProperty(1, 8), new BinaryProperty(1, 131072), new CaseBinaryProperty(27), new BinaryProperty(1, 16), new BinaryProperty(1, 262144), new CaseBinaryProperty(30), new BinaryProperty(1, 1), new BinaryProperty(1, 8388608), new BinaryProperty(1, 4194304), new CaseBinaryProperty(34), new BinaryProperty(1, 134217728), new BinaryProperty(1, 268435456), new NormInertBinaryProperty(8, 37), new NormInertBinaryProperty(9, 38), new NormInertBinaryProperty(8, 39), new NormInertBinaryProperty(9, 40), new BinaryProperty(11) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return Norm2AllModes.getNFCInstance().impl.ensureCanonIterData().isCanonSegmentStarter(n);
                }
            }, new BinaryProperty(1, 536870912), new BinaryProperty(1, 1073741824), new BinaryProperty(6) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UCharacter.isUAlphabetic(n) || UCharacter.isDigit(n);
                }
            }, new BinaryProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    if (n <= 159) {
                        return n == 9 || n == 32;
                    }
                    return UCharacter.getType(n) == 12;
                }
            }, new BinaryProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UCharacterProperty.access$000(n);
                }
            }, new BinaryProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return UCharacter.getType(n) == 12 || UCharacterProperty.access$000(n);
                }
            }, new BinaryProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    return (n <= 102 && n >= 65 && (n <= 70 || n >= 97)) || (n >= 65313 && n <= 65350 && (n <= 65318 || n >= 65345)) || UCharacter.getType(n) == 9;
                }
            }, new CaseBinaryProperty(49), new CaseBinaryProperty(50), new CaseBinaryProperty(51), new CaseBinaryProperty(52), new CaseBinaryProperty(53), new BinaryProperty(7) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(int codePoint) {
                    final String decomposition = Norm2AllModes.getNFCInstance().impl.getDecomposition(-1);
                    if (decomposition != null) {
                        codePoint = decomposition.codePointAt(0);
                        if (Character.charCount(-1) != decomposition.length()) {}
                    }
                    else if (-1 < 0) {
                        return false;
                    }
                    if (-1 >= 0) {
                        final UCaseProps instance = UCaseProps.INSTANCE;
                        UCaseProps.dummyStringBuilder.setLength(0);
                        return instance.toFullFolding(-1, UCaseProps.dummyStringBuilder, 0) >= 0;
                    }
                    return !UCharacter.foldCase(decomposition, true).equals(decomposition);
                }
            }, new CaseBinaryProperty(55), new BinaryProperty(10) {
                final UCharacterProperty this$0;
                
                @Override
                boolean contains(final int n) {
                    final Normalizer2Impl impl = Norm2AllModes.getNFKC_CFInstance().impl;
                    final String value = UTF16.valueOf(n);
                    final StringBuilder sb = new StringBuilder();
                    impl.compose(value, 0, value.length(), false, true, new Normalizer2Impl.ReorderingBuffer(impl, sb, 5));
                    return !Normalizer2Impl.UTF16Plus.equal(sb, value);
                }
            } };
        this.intProps = new IntProperty[] { new BiDiIntProperty() {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return UBiDiProps.INSTANCE.getClass(n);
                }
            }, new IntProperty(0, 130816, 8), new CombiningClassIntProperty(8) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return Norm2AllModes.getNFCInstance().decomp.getCombiningClass(n);
                }
            }, new IntProperty(2, 31, 0), new IntProperty(0, 917504, 17), new IntProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return this.this$0.getType(n);
                }
                
                @Override
                int getMaxValue(final int n) {
                    return 29;
                }
            }, new BiDiIntProperty() {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return UBiDiProps.INSTANCE.getJoiningGroup(n);
                }
            }, new BiDiIntProperty() {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return UBiDiProps.INSTANCE.getJoiningType(n);
                }
            }, new IntProperty(2, 66060288, 20), new IntProperty(1) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return UCharacterProperty.access$200(UCharacterProperty.access$100(this.this$0.getProperty(n)));
                }
                
                @Override
                int getMaxValue(final int n) {
                    return 3;
                }
            }, new IntProperty(0, 255, 0) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return UScript.getScript(n);
                }
            }, new IntProperty(2) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    final int n2 = (this.this$0.getAdditional(n, 2) & 0x3E0) >>> 5;
                    if (n2 < UCharacterProperty.access$300().length) {
                        return UCharacterProperty.access$300()[n2];
                    }
                    return 0;
                }
                
                @Override
                int getMaxValue(final int n) {
                    return 5;
                }
            }, new NormQuickCheckIntProperty(8, 4108, 1), new NormQuickCheckIntProperty(9, 4109, 1), new NormQuickCheckIntProperty(8, 4110, 2), new NormQuickCheckIntProperty(9, 4111, 2), new CombiningClassIntProperty(8) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return Norm2AllModes.getNFCInstance().impl.getFCD16(n) >> 8;
                }
            }, new CombiningClassIntProperty(8) {
                final UCharacterProperty this$0;
                
                @Override
                int getValue(final int n) {
                    return Norm2AllModes.getNFCInstance().impl.getFCD16(n) & 0xFF;
                }
            }, new IntProperty(2, 992, 5), new IntProperty(2, 1015808, 15), new IntProperty(2, 31744, 10) };
        if (this.binProps.length != 57) {
            throw new RuntimeException("binProps.length!=UProperty.BINARY_LIMIT");
        }
        if (this.intProps.length != 21) {
            throw new RuntimeException("intProps.length!=(UProperty.INT_LIMIT-UProperty.INT_START)");
        }
        final InputStream requiredStream = ICUData.getRequiredStream("data/icudt51b/uprops.icu");
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(requiredStream, 25000);
        this.m_unicodeVersion_ = ICUBinary.readHeaderAndDataVersion(bufferedInputStream, UCharacterProperty.DATA_FORMAT, new IsAcceptable(null));
        final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        final int int1 = dataInputStream.readInt();
        dataInputStream.readInt();
        dataInputStream.readInt();
        final int int2 = dataInputStream.readInt();
        final int int3 = dataInputStream.readInt();
        this.m_additionalColumnsCount_ = dataInputStream.readInt();
        final int int4 = dataInputStream.readInt();
        final int int5 = dataInputStream.readInt();
        dataInputStream.readInt();
        dataInputStream.readInt();
        this.m_maxBlockScriptValue_ = dataInputStream.readInt();
        this.m_maxJTGValue_ = dataInputStream.readInt();
        dataInputStream.skipBytes(16);
        this.m_trie_ = Trie2_16.createFromSerialized(dataInputStream);
        final int n = (int1 - 16) * 4;
        final int serializedLength = this.m_trie_.getSerializedLength();
        if (serializedLength > n) {
            throw new IOException("uprops.icu: not enough bytes for main trie");
        }
        dataInputStream.skipBytes(n - serializedLength);
        dataInputStream.skipBytes((int2 - int1) * 4);
        if (this.m_additionalColumnsCount_ > 0) {
            this.m_additionalTrie_ = Trie2_16.createFromSerialized(dataInputStream);
            final int n2 = (int3 - int2) * 4;
            final int serializedLength2 = this.m_additionalTrie_.getSerializedLength();
            if (serializedLength2 > n2) {
                throw new IOException("uprops.icu: not enough bytes for additional-properties trie");
            }
            dataInputStream.skipBytes(n2 - serializedLength2);
            final int n3 = int4 - int3;
            this.m_additionalVectors_ = new int[n3];
            for (int i = 0; i < n3; ++i) {
                this.m_additionalVectors_[i] = dataInputStream.readInt();
            }
        }
        final int n4 = (int5 - int4) * 2;
        if (n4 > 0) {
            this.m_scriptExtensions_ = new char[n4];
            for (int j = 0; j < n4; ++j) {
                this.m_scriptExtensions_[j] = dataInputStream.readChar();
            }
        }
        requiredStream.close();
    }
    
    public UnicodeSet addPropertyStarts(final UnicodeSet set) {
        final Iterator iterator = this.m_trie_.iterator();
        Trie2.Range range;
        while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
        set.add(9);
        set.add(10);
        set.add(14);
        set.add(28);
        set.add(32);
        set.add(133);
        set.add(134);
        set.add(127);
        set.add(8202);
        set.add(8208);
        set.add(8298);
        set.add(8304);
        set.add(65279);
        set.add(65280);
        set.add(160);
        set.add(161);
        set.add(8199);
        set.add(8200);
        set.add(8239);
        set.add(8240);
        set.add(12295);
        set.add(12296);
        set.add(19968);
        set.add(19969);
        set.add(20108);
        set.add(20109);
        set.add(19977);
        set.add(19978);
        set.add(22235);
        set.add(22236);
        set.add(20116);
        set.add(20117);
        set.add(20845);
        set.add(20846);
        set.add(19971);
        set.add(19972);
        set.add(20843);
        set.add(20844);
        set.add(20061);
        set.add(20062);
        set.add(97);
        set.add(123);
        set.add(65);
        set.add(91);
        set.add(65345);
        set.add(65371);
        set.add(65313);
        set.add(65339);
        set.add(103);
        set.add(71);
        set.add(65351);
        set.add(65319);
        set.add(8288);
        set.add(65520);
        set.add(65532);
        set.add(917504);
        set.add(921600);
        set.add(847);
        set.add(848);
        return set;
    }
    
    public void upropsvec_addPropertyStarts(final UnicodeSet set) {
        if (this.m_additionalColumnsCount_ > 0) {
            final Iterator iterator = this.m_additionalTrie_.iterator();
            Trie2.Range range;
            while (iterator.hasNext() && !(range = iterator.next()).leadSurrogate) {
                set.add(range.startCodePoint);
            }
        }
    }
    
    static boolean access$000(final int n) {
        return isgraphPOSIX(n);
    }
    
    static int access$100(final int n) {
        return getNumericTypeValue(n);
    }
    
    static int access$200(final int n) {
        return ntvGetType(n);
    }
    
    static int[] access$300() {
        return UCharacterProperty.gcbToHst;
    }
    
    static {
        $assertionsDisabled = !UCharacterProperty.class.desiredAssertionStatus();
        GC_CN_MASK = getMask(0);
        GC_CC_MASK = getMask(15);
        GC_CS_MASK = getMask(18);
        GC_ZS_MASK = getMask(12);
        GC_ZL_MASK = getMask(13);
        GC_ZP_MASK = getMask(14);
        GC_Z_MASK = (UCharacterProperty.GC_ZS_MASK | UCharacterProperty.GC_ZL_MASK | UCharacterProperty.GC_ZP_MASK);
        gcbToHst = new int[] { 0, 0, 0, 0, 1, 0, 4, 5, 3, 2 };
        DATA_FORMAT = new byte[] { 85, 80, 114, 111 };
        try {
            INSTANCE = new UCharacterProperty();
        }
        catch (IOException ex) {
            throw new MissingResourceException(ex.getMessage(), "", "");
        }
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        private IsAcceptable() {
        }
        
        public boolean isDataVersionAcceptable(final byte[] array) {
            return array[0] == 7;
        }
        
        IsAcceptable(final UCharacterProperty$1 binaryProperty) {
            this();
        }
    }
    
    private class NormQuickCheckIntProperty extends IntProperty
    {
        int which;
        int max;
        final UCharacterProperty this$0;
        
        NormQuickCheckIntProperty(final UCharacterProperty this$0, final int n, final int which, final int max) {
            this.this$0 = this$0.super(n);
            this.which = which;
            this.max = max;
        }
        
        @Override
        int getValue(final int n) {
            return Norm2AllModes.getN2WithImpl(this.which - 4108).getQuickCheck(n);
        }
        
        @Override
        int getMaxValue(final int n) {
            return this.max;
        }
    }
    
    private class IntProperty
    {
        int column;
        int mask;
        int shift;
        final UCharacterProperty this$0;
        
        IntProperty(final UCharacterProperty this$0, final int column, final int mask, final int shift) {
            this.this$0 = this$0;
            this.column = column;
            this.mask = mask;
            this.shift = shift;
        }
        
        IntProperty(final UCharacterProperty this$0, final int column) {
            this.this$0 = this$0;
            this.column = column;
            this.mask = 0;
        }
        
        final int getSource() {
            return (this.mask == 0) ? this.column : 2;
        }
        
        int getValue(final int n) {
            return (this.this$0.getAdditional(n, this.column) & this.mask) >>> this.shift;
        }
        
        int getMaxValue(final int n) {
            return (this.this$0.getMaxValues(this.column) & this.mask) >>> this.shift;
        }
    }
    
    private class CombiningClassIntProperty extends IntProperty
    {
        final UCharacterProperty this$0;
        
        CombiningClassIntProperty(final UCharacterProperty this$0, final int n) {
            this.this$0 = this$0.super(n);
        }
        
        @Override
        int getMaxValue(final int n) {
            return 255;
        }
    }
    
    private class BiDiIntProperty extends IntProperty
    {
        final UCharacterProperty this$0;
        
        BiDiIntProperty(final UCharacterProperty this$0) {
            this.this$0 = this$0.super(5);
        }
        
        @Override
        int getMaxValue(final int n) {
            return UBiDiProps.INSTANCE.getMaxValue(n);
        }
    }
    
    private class NormInertBinaryProperty extends BinaryProperty
    {
        int which;
        final UCharacterProperty this$0;
        
        NormInertBinaryProperty(final UCharacterProperty this$0, final int n, final int which) {
            this.this$0 = this$0.super(n);
            this.which = which;
        }
        
        @Override
        boolean contains(final int n) {
            return Norm2AllModes.getN2WithImpl(this.which - 37).isInert(n);
        }
    }
    
    private class BinaryProperty
    {
        int column;
        int mask;
        final UCharacterProperty this$0;
        
        BinaryProperty(final UCharacterProperty this$0, final int column, final int mask) {
            this.this$0 = this$0;
            this.column = column;
            this.mask = mask;
        }
        
        BinaryProperty(final UCharacterProperty this$0, final int column) {
            this.this$0 = this$0;
            this.column = column;
            this.mask = 0;
        }
        
        final int getSource() {
            return (this.mask == 0) ? this.column : 2;
        }
        
        boolean contains(final int n) {
            return (this.this$0.getAdditional(n, this.column) & this.mask) != 0x0;
        }
    }
    
    private class CaseBinaryProperty extends BinaryProperty
    {
        int which;
        final UCharacterProperty this$0;
        
        CaseBinaryProperty(final UCharacterProperty this$0, final int which) {
            this.this$0 = this$0.super(4);
            this.which = which;
        }
        
        @Override
        boolean contains(final int n) {
            return UCaseProps.INSTANCE.hasBinaryProperty(n, this.which);
        }
    }
}
