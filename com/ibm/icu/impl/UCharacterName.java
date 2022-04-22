package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.text.*;
import java.io.*;
import com.ibm.icu.lang.*;

public final class UCharacterName
{
    public static final UCharacterName INSTANCE;
    public static final int LINES_PER_GROUP_ = 32;
    public int m_groupcount_;
    int m_groupsize_;
    private char[] m_tokentable_;
    private byte[] m_tokenstring_;
    private char[] m_groupinfo_;
    private byte[] m_groupstring_;
    private AlgorithmName[] m_algorithm_;
    private char[] m_groupoffsets_;
    private char[] m_grouplengths_;
    private static final String NAME_FILE_NAME_ = "data/icudt51b/unames.icu";
    private static final int GROUP_SHIFT_ = 5;
    private static final int GROUP_MASK_ = 31;
    private static final int NAME_BUFFER_SIZE_ = 100000;
    private static final int OFFSET_HIGH_OFFSET_ = 1;
    private static final int OFFSET_LOW_OFFSET_ = 2;
    private static final int SINGLE_NIBBLE_MAX_ = 11;
    private int[] m_nameSet_;
    private int[] m_ISOCommentSet_;
    private StringBuffer m_utilStringBuffer_;
    private int[] m_utilIntBuffer_;
    private int m_maxISOCommentLength_;
    private int m_maxNameLength_;
    private static final String UNKNOWN_TYPE_NAME_ = "unknown";
    private static final int NON_CHARACTER_ = 30;
    private static final int LEAD_SURROGATE_ = 31;
    private static final int TRAIL_SURROGATE_ = 32;
    static final int EXTENDED_CATEGORY_ = 33;
    
    public String getName(final int n, final int n2) {
        if (n < 0 || n > 1114111 || n2 > 4) {
            return null;
        }
        String s = this.getAlgName(n, n2);
        if (s == null || s.length() == 0) {
            if (n2 == 2) {
                s = this.getExtendedName(n);
            }
            else {
                s = this.getGroupName(n, n2);
            }
        }
        return s;
    }
    
    public int getCharFromName(final int n, final String s) {
        if (n >= 4 || s == null || s.length() == 0) {
            return -1;
        }
        final int extendedChar = getExtendedChar(s.toLowerCase(Locale.ENGLISH), n);
        if (extendedChar >= -1) {
            return extendedChar;
        }
        final String upperCase = s.toUpperCase(Locale.ENGLISH);
        if (n == 0 || n == 2) {
            int length = 0;
            if (this.m_algorithm_ != null) {
                length = this.m_algorithm_.length;
            }
            --length;
            int char1;
            while (true) {
                char1 = this.m_algorithm_[0].getChar(upperCase);
                if (char1 >= 0) {
                    break;
                }
                --length;
            }
            return char1;
        }
        int n2;
        if (n == 2) {
            n2 = this.getGroupChar(upperCase, 0);
            if (n2 == -1) {
                n2 = this.getGroupChar(upperCase, 3);
            }
        }
        else {
            n2 = this.getGroupChar(upperCase, n);
        }
        return n2;
    }
    
    public int getGroupLengths(int n, final char[] array, final char[] array2) {
        n *= this.m_groupsize_;
        final int int1 = UCharacterUtility.toInt(this.m_groupinfo_[n + 1], this.m_groupinfo_[n + 2]);
        array[0] = '\0';
        final byte b = this.m_groupstring_[int1];
        while (true) {
            final byte b2 = 0;
            array2[0] = 0;
            array[1] = (char)(array[0] + array2[0]);
            int n2 = 0;
            ++n2;
            final int n3;
            n3 -= 4;
        }
    }
    
    public String getGroupName(int n, int n2, final int n3) {
        if (n3 != 0 && n3 != 2 && (59 >= this.m_tokentable_.length || this.m_tokentable_[59] == '\uffff')) {
            int n4 = (n3 == 4) ? 2 : n3;
            do {
                final int n5 = n;
                n += UCharacterUtility.skipByteSubString(this.m_groupstring_, n, 0, (byte)59);
                n2 = 0 - (n - n5);
            } while (--n4 > 0);
        }
        // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
        this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
        if (this.m_utilStringBuffer_.length() > 0) {
            // monitorexit(utilStringBuffer_)
            return this.m_utilStringBuffer_.toString();
        }
        // monitorexit(utilStringBuffer_)
        return null;
    }
    
    public String getExtendedName(final int n) {
        String s = this.getName(n, 0);
        if (s == null && s == null) {
            s = this.getExtendedOr10Name(n);
        }
        return s;
    }
    
    public int getGroup(final int n) {
        int groupcount_ = this.m_groupcount_;
        final int codepointMSB = getCodepointMSB(n);
        while (0 < groupcount_ - 1) {
            final int n2 = 0 + groupcount_ >> 1;
            if (codepointMSB < this.getGroupMSB(n2)) {
                groupcount_ = n2;
            }
        }
        return 0;
    }
    
    public String getExtendedOr10Name(final int n) {
        String string = null;
        if (string == null) {
            final int type = getType(n);
            String s;
            if (type >= UCharacterName.TYPE_NAMES_.length) {
                s = "unknown";
            }
            else {
                s = UCharacterName.TYPE_NAMES_[type];
            }
            // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
            this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
            this.m_utilStringBuffer_.append('<');
            this.m_utilStringBuffer_.append(s);
            this.m_utilStringBuffer_.append('-');
            final String upperCase = Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
            for (int i = 4 - upperCase.length(); i > 0; --i) {
                this.m_utilStringBuffer_.append('0');
            }
            this.m_utilStringBuffer_.append(upperCase);
            this.m_utilStringBuffer_.append('>');
            string = this.m_utilStringBuffer_.toString();
        }
        // monitorexit(utilStringBuffer_)
        return string;
    }
    
    public int getGroupMSB(final int n) {
        if (n >= this.m_groupcount_) {
            return -1;
        }
        return this.m_groupinfo_[n * this.m_groupsize_];
    }
    
    public static int getCodepointMSB(final int n) {
        return n >> 5;
    }
    
    public static int getGroupLimit(final int n) {
        return (n << 5) + 32;
    }
    
    public static int getGroupMin(final int n) {
        return n << 5;
    }
    
    public static int getGroupOffset(final int n) {
        return n & 0x1F;
    }
    
    public static int getGroupMinFromCodepoint(final int n) {
        return n & 0xFFFFFFE0;
    }
    
    public int getAlgorithmLength() {
        return this.m_algorithm_.length;
    }
    
    public int getAlgorithmStart(final int n) {
        return AlgorithmName.access$000(this.m_algorithm_[n]);
    }
    
    public int getAlgorithmEnd(final int n) {
        return AlgorithmName.access$100(this.m_algorithm_[n]);
    }
    
    public String getAlgorithmName(final int n, final int n2) {
        // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
        this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
        this.m_algorithm_[n].appendName(n2, this.m_utilStringBuffer_);
        // monitorexit(utilStringBuffer_)
        return this.m_utilStringBuffer_.toString();
    }
    
    public synchronized String getGroupName(final int n, final int n2) {
        final int codepointMSB = getCodepointMSB(n);
        final int group = this.getGroup(n);
        if (codepointMSB == this.m_groupinfo_[group * this.m_groupsize_]) {
            final int groupLengths = this.getGroupLengths(group, this.m_groupoffsets_, this.m_grouplengths_);
            final int n3 = n & 0x1F;
            return this.getGroupName(groupLengths + this.m_groupoffsets_[n3], this.m_grouplengths_[n3], n2);
        }
        return null;
    }
    
    public int getMaxCharNameLength() {
        if (this > 0) {
            return this.m_maxNameLength_;
        }
        return 0;
    }
    
    public int getMaxISOCommentLength() {
        if (this > 0) {
            return this.m_maxISOCommentLength_;
        }
        return 0;
    }
    
    public void getCharNameCharacters(final UnicodeSet set) {
        this.convert(this.m_nameSet_, set);
    }
    
    public void getISOCommentCharacters(final UnicodeSet set) {
        this.convert(this.m_ISOCommentSet_, set);
    }
    
    boolean setToken(final char[] tokentable_, final byte[] tokenstring_) {
        if (tokentable_ != null && tokenstring_ != null && tokentable_.length > 0 && tokenstring_.length > 0) {
            this.m_tokentable_ = tokentable_;
            this.m_tokenstring_ = tokenstring_;
            return true;
        }
        return false;
    }
    
    boolean setAlgorithm(final AlgorithmName[] algorithm_) {
        if (algorithm_ != null && algorithm_.length != 0) {
            this.m_algorithm_ = algorithm_;
            return true;
        }
        return false;
    }
    
    boolean setGroupCountSize(final int groupcount_, final int groupsize_) {
        if (groupcount_ <= 0 || groupsize_ <= 0) {
            return false;
        }
        this.m_groupcount_ = groupcount_;
        this.m_groupsize_ = groupsize_;
        return true;
    }
    
    boolean setGroup(final char[] groupinfo_, final byte[] groupstring_) {
        if (groupinfo_ != null && groupstring_ != null && groupinfo_.length > 0 && groupstring_.length > 0) {
            this.m_groupinfo_ = groupinfo_;
            this.m_groupstring_ = groupstring_;
            return true;
        }
        return false;
    }
    
    private UCharacterName() throws IOException {
        this.m_groupcount_ = 0;
        this.m_groupsize_ = 0;
        this.m_groupoffsets_ = new char[33];
        this.m_grouplengths_ = new char[33];
        this.m_nameSet_ = new int[8];
        this.m_ISOCommentSet_ = new int[8];
        this.m_utilStringBuffer_ = new StringBuffer();
        this.m_utilIntBuffer_ = new int[2];
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(ICUData.getRequiredStream("data/icudt51b/unames.icu"), 100000);
        new UCharacterNameReader(bufferedInputStream).read(this);
        bufferedInputStream.close();
    }
    
    private String getAlgName(final int n, final int n2) {
        if (n2 == 0 || n2 == 2) {
            // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
            this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
            for (int i = this.m_algorithm_.length - 1; i >= 0; --i) {
                if (this.m_algorithm_[i].contains(n)) {
                    this.m_algorithm_[i].appendName(n, this.m_utilStringBuffer_);
                    // monitorexit(utilStringBuffer_)
                    return this.m_utilStringBuffer_.toString();
                }
            }
        }
        // monitorexit(utilStringBuffer_)
        return null;
    }
    
    private synchronized int getGroupChar(final String s, final int n) {
        while (0 < this.m_groupcount_) {
            final int groupChar = this.getGroupChar(this.getGroupLengths(0, this.m_groupoffsets_, this.m_grouplengths_), this.m_grouplengths_, s, n);
            if (groupChar != -1) {
                return this.m_groupinfo_[0 * this.m_groupsize_] << 5 | groupChar;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    private int getGroupChar(int n, final char[] array, final String s, final int n2) {
        final int length = s.length();
        while (true) {
            int n3 = array[0];
            if (n2 != 0 && n2 != 2) {
                int n4 = (n2 == 4) ? 2 : n2;
                do {
                    final int n5 = n;
                    n += UCharacterUtility.skipByteSubString(this.m_groupstring_, n, n3, (byte)59);
                    n3 -= n - n5;
                } while (--n4 > 0);
            }
            if (0 < n3) {}
            if (length == -1 && (n3 == 0 || this.m_groupstring_[n + 0] == 59)) {
                break;
            }
            n += n3;
            int n6 = 0;
            ++n6;
        }
        return 0;
    }
    
    private static int getType(final int n) {
        if (UCharacterUtility.isNonCharacter(n)) {
            return 30;
        }
        UCharacter.getType(n);
        return 32;
    }
    
    private static int getExtendedChar(final String s, final int n) {
        if (s.charAt(0) == '<') {
            if (n == 2) {
                final int n2 = s.length() - 1;
                if (s.charAt(n2) == '>') {
                    int lastIndex = s.lastIndexOf(45);
                    if (lastIndex >= 0) {
                        ++lastIndex;
                        Integer.parseInt(s.substring(lastIndex, n2), 16);
                        final String substring = s.substring(1, lastIndex - 1);
                        while (0 < UCharacterName.TYPE_NAMES_.length) {
                            if (substring.compareTo(UCharacterName.TYPE_NAMES_[0]) == 0) {
                                if (getType(-1) == 0) {
                                    return -1;
                                }
                                break;
                            }
                            else {
                                int n3 = 0;
                                ++n3;
                            }
                        }
                    }
                }
            }
            return -1;
        }
        return -2;
    }
    
    private static void add(final int[] array, final char c) {
        final int n = c >>> 5;
        array[n] |= 1 << (c & '\u001f');
    }
    
    private static int add(final int[] array, final String s) {
        final int length = s.length();
        for (int i = length - 1; i >= 0; --i) {
            add(array, s.charAt(i));
        }
        return length;
    }
    
    private static int add(final int[] array, final StringBuffer sb) {
        final int length = sb.length();
        for (int i = length - 1; i >= 0; --i) {
            add(array, sb.charAt(i));
        }
        return length;
    }
    
    private int addAlgorithmName(final int n) {
        for (int i = this.m_algorithm_.length - 1; i >= 0; --i) {
            this.m_algorithm_[i].add(this.m_nameSet_, 0);
        }
        return 0;
    }
    
    private int addExtendedName(int n) {
        for (int i = UCharacterName.TYPE_NAMES_.length - 1; i >= 0; --i) {
            final int n2 = 9 + add(this.m_nameSet_, UCharacterName.TYPE_NAMES_[i]);
            if (n2 > n) {
                n = n2;
            }
        }
        return n;
    }
    
    private int[] addGroupName(final int n, final int n2, final byte[] array, final int[] array2) {
        while (0 < n2) {
            char c = (char)(this.m_groupstring_[n + 0] & 0xFF);
            int n3 = 0;
            ++n3;
            if (c == ';') {
                break;
            }
            if (c >= this.m_tokentable_.length) {
                add(array2, c);
                int n4 = 0;
                ++n4;
            }
            else {
                char c2 = this.m_tokentable_[c & '\u00ff'];
                if (c2 == '\ufffe') {
                    c = (char)(c << 8 | (this.m_groupstring_[n + 0] & 0xFF));
                    c2 = this.m_tokentable_[c];
                    ++n3;
                }
                if (c2 == '\uffff') {
                    add(array2, c);
                    int n4 = 0;
                    ++n4;
                }
                else {
                    byte b = array[c];
                    if (b == 0) {
                        // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
                        this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
                        UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_tokenstring_, c2);
                        b = (byte)add(array2, this.m_utilStringBuffer_);
                        // monitorexit(utilStringBuffer_)
                        array[c] = b;
                    }
                    final int n4 = 0 + b;
                }
            }
        }
        this.m_utilIntBuffer_[0] = 0;
        this.m_utilIntBuffer_[1] = 0;
        return this.m_utilIntBuffer_;
    }
    
    private void addGroupName(int maxNameLength_) {
        final char[] array = new char[34];
        final char[] array2 = new char[34];
        final byte[] array3 = new byte[this.m_tokentable_.length];
        if (0 >= this.m_groupcount_) {
            this.m_maxISOCommentLength_ = 0;
            this.m_maxNameLength_ = maxNameLength_;
            return;
        }
        final int groupLengths = this.getGroupLengths(0, array, array2);
        while (true) {
            final int n = groupLengths + array[0];
            final char c = array2[0];
            if (c != '\0') {
                final int[] addGroupName = this.addGroupName(n, c, array3, this.m_nameSet_);
                if (addGroupName[0] > maxNameLength_) {
                    maxNameLength_ = addGroupName[0];
                }
                final int n2 = n + addGroupName[1];
                if (addGroupName[1] < c) {
                    final int n3 = c - addGroupName[1];
                    final int[] addGroupName2 = this.addGroupName(n2, n3, array3, this.m_nameSet_);
                    if (addGroupName2[0] > maxNameLength_) {
                        maxNameLength_ = addGroupName2[0];
                    }
                    final int n4 = n2 + addGroupName2[1];
                    if (addGroupName2[1] < n3) {
                        if (this.addGroupName(n4, n3 - addGroupName2[1], array3, this.m_ISOCommentSet_)[1] > 0) {}
                    }
                }
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    private void convert(final int[] p0, final UnicodeSet p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/ibm/icu/text/UnicodeSet.clear:()Lcom/ibm/icu/text/UnicodeSet;
        //     4: pop            
        //     5: aload_0        
        //     6: ifle            10
        //     9: return         
        //    10: aload_1        
        //    11: goto            22
        //    14: aload_2        
        //    15: sipush          255
        //    18: invokevirtual   com/ibm/icu/text/UnicodeSet.add:(I)Lcom/ibm/icu/text/UnicodeSet;
        //    21: pop            
        //    22: sipush          254
        //    25: i2c            
        //    26: istore_3       
        //    27: goto            10
        //    30: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0010 (coming from #0027).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static int access$200(final int[] array, final String s) {
        return add(array, s);
    }
    
    static int access$300(final int[] array, final StringBuffer sb) {
        return add(array, sb);
    }
    
    static {
        INSTANCE = new UCharacterName();
        UCharacterName.TYPE_NAMES_ = new String[] { "unassigned", "uppercase letter", "lowercase letter", "titlecase letter", "modifier letter", "other letter", "non spacing mark", "enclosing mark", "combining spacing mark", "decimal digit number", "letter number", "other number", "space separator", "line separator", "paragraph separator", "control", "format", "private use area", "surrogate", "dash punctuation", "start punctuation", "end punctuation", "connector punctuation", "other punctuation", "math symbol", "currency symbol", "modifier symbol", "other symbol", "initial punctuation", "final punctuation", "noncharacter", "lead surrogate", "trail surrogate" };
    }
    
    static final class AlgorithmName
    {
        static final int TYPE_0_ = 0;
        static final int TYPE_1_ = 1;
        private int m_rangestart_;
        private int m_rangeend_;
        private byte m_type_;
        private byte m_variant_;
        private char[] m_factor_;
        private String m_prefix_;
        private byte[] m_factorstring_;
        private StringBuffer m_utilStringBuffer_;
        private int[] m_utilIntBuffer_;
        
        AlgorithmName() {
            this.m_utilStringBuffer_ = new StringBuffer();
            this.m_utilIntBuffer_ = new int[256];
        }
        
        boolean setInfo(final int rangestart_, final int rangeend_, final byte type_, final byte variant_) {
            if (rangestart_ >= 0 && rangestart_ <= rangeend_ && rangeend_ <= 1114111 && (type_ == 0 || type_ == 1)) {
                this.m_rangestart_ = rangestart_;
                this.m_rangeend_ = rangeend_;
                this.m_type_ = type_;
                this.m_variant_ = variant_;
                return true;
            }
            return false;
        }
        
        boolean setFactor(final char[] factor_) {
            if (factor_.length == this.m_variant_) {
                this.m_factor_ = factor_;
                return true;
            }
            return false;
        }
        
        boolean setPrefix(final String prefix_) {
            if (prefix_ != null && prefix_.length() > 0) {
                this.m_prefix_ = prefix_;
                return true;
            }
            return false;
        }
        
        boolean setFactorString(final byte[] factorstring_) {
            this.m_factorstring_ = factorstring_;
            return true;
        }
        
        boolean contains(final int n) {
            return this.m_rangestart_ <= n && n <= this.m_rangeend_;
        }
        
        void appendName(final int n, final StringBuffer sb) {
            sb.append(this.m_prefix_);
            switch (this.m_type_) {
                case 0: {
                    sb.append(Utility.hex(n, this.m_variant_));
                    break;
                }
                case 1: {
                    int n2 = n - this.m_rangestart_;
                    final int[] utilIntBuffer_ = this.m_utilIntBuffer_;
                    // monitorenter(utilIntBuffer_2 = this.m_utilIntBuffer_)
                    for (int i = this.m_variant_ - 1; i > 0; --i) {
                        final int n3 = this.m_factor_[i] & '\u00ff';
                        utilIntBuffer_[i] = n2 % n3;
                        n2 /= n3;
                    }
                    utilIntBuffer_[0] = n2;
                    sb.append(this.getFactorString(utilIntBuffer_, this.m_variant_));
                    // monitorexit(utilIntBuffer_2)
                    break;
                }
            }
        }
        
        int getChar(final String s) {
            final int length = this.m_prefix_.length();
            if (s.length() < length || !this.m_prefix_.equals(s.substring(0, length))) {
                return -1;
            }
            switch (this.m_type_) {
                case 0: {
                    final int int1 = Integer.parseInt(s.substring(length), 16);
                    if (this.m_rangestart_ <= int1 && int1 <= this.m_rangeend_) {
                        return int1;
                    }
                    break;
                }
                case 1: {
                    for (int i = this.m_rangestart_; i <= this.m_rangeend_; ++i) {
                        int n = i - this.m_rangestart_;
                        final int[] utilIntBuffer_ = this.m_utilIntBuffer_;
                        // monitorenter(utilIntBuffer_2 = this.m_utilIntBuffer_)
                        for (int j = this.m_variant_ - 1; j > 0; --j) {
                            final int n2 = this.m_factor_[j] & '\u00ff';
                            utilIntBuffer_[j] = n % n2;
                            n /= n2;
                        }
                        utilIntBuffer_[0] = n;
                        if (this.compareFactorString(utilIntBuffer_, this.m_variant_, s, length)) {
                            // monitorexit(utilIntBuffer_2)
                            return i;
                        }
                    }
                    // monitorexit(utilIntBuffer_2)
                    break;
                }
            }
            return -1;
        }
        
        int add(final int[] array, final int n) {
            int access$200 = UCharacterName.access$200(array, this.m_prefix_);
            switch (this.m_type_) {
                case 0: {
                    access$200 += this.m_variant_;
                    break;
                }
                case 1: {
                    for (int i = this.m_variant_ - 1; i > 0; --i) {
                        for (int j = this.m_factor_[i]; j > 0; --j) {
                            // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
                            this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
                            UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_factorstring_, 0);
                            UCharacterName.access$300(array, this.m_utilStringBuffer_);
                            if (this.m_utilStringBuffer_.length() > 0) {
                                this.m_utilStringBuffer_.length();
                            }
                        }
                        // monitorexit(utilStringBuffer_)
                        access$200 += 0;
                    }
                    break;
                }
            }
            if (access$200 > n) {
                return access$200;
            }
            return n;
        }
        
        private String getFactorString(final int[] array, final int n) {
            int length = this.m_factor_.length;
            if (array == null || n != length) {
                return null;
            }
            // monitorenter(utilStringBuffer_ = this.m_utilStringBuffer_)
            this.m_utilStringBuffer_.delete(0, this.m_utilStringBuffer_.length());
            --length;
            while (0 <= length) {
                final char c = this.m_factor_[0];
                UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, 0, array[0]);
                UCharacterUtility.getNullTermByteSubString(this.m_utilStringBuffer_, this.m_factorstring_, 0);
                if (length != 0) {
                    UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, 0, c - array[0] - 1);
                }
                int n2 = 0;
                ++n2;
            }
            // monitorexit(utilStringBuffer_)
            return this.m_utilStringBuffer_.toString();
        }
        
        private boolean compareFactorString(final int[] array, final int n, final String s, final int n2) {
            int length = this.m_factor_.length;
            if (array == null || n != length) {
                return false;
            }
            int compareNullTermByteSubString = n2;
            --length;
            while (0 <= length) {
                final char c = this.m_factor_[0];
                UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, 0, array[0]);
                compareNullTermByteSubString = UCharacterUtility.compareNullTermByteSubString(s, this.m_factorstring_, compareNullTermByteSubString, 0);
                if (compareNullTermByteSubString < 0) {
                    return false;
                }
                if (length != 0) {
                    UCharacterUtility.skipNullTermByteSubString(this.m_factorstring_, 0, c - array[0]);
                }
                int n3 = 0;
                ++n3;
            }
            return compareNullTermByteSubString == s.length();
        }
        
        static int access$000(final AlgorithmName algorithmName) {
            return algorithmName.m_rangestart_;
        }
        
        static int access$100(final AlgorithmName algorithmName) {
            return algorithmName.m_rangeend_;
        }
    }
}
