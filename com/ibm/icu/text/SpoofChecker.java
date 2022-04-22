package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.text.*;
import com.ibm.icu.util.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.impl.*;

public class SpoofChecker
{
    public static final UnicodeSet INCLUSION;
    public static final UnicodeSet RECOMMENDED;
    public static final int SINGLE_SCRIPT_CONFUSABLE = 1;
    public static final int MIXED_SCRIPT_CONFUSABLE = 2;
    public static final int WHOLE_SCRIPT_CONFUSABLE = 4;
    public static final int ANY_CASE = 8;
    public static final int RESTRICTION_LEVEL = 16;
    @Deprecated
    public static final int SINGLE_SCRIPT = 16;
    public static final int INVISIBLE = 32;
    public static final int CHAR_LIMIT = 64;
    public static final int MIXED_NUMBERS = 128;
    public static final int ALL_CHECKS = -1;
    static final int MAGIC = 944111087;
    private IdentifierInfo fCachedIdentifierInfo;
    private int fMagic;
    private int fChecks;
    private SpoofData fSpoofData;
    private Set fAllowedLocales;
    private UnicodeSet fAllowedCharsSet;
    private RestrictionLevel fRestrictionLevel;
    private static Normalizer2 nfdNormalizer;
    static final int SL_TABLE_FLAG = 16777216;
    static final int SA_TABLE_FLAG = 33554432;
    static final int ML_TABLE_FLAG = 67108864;
    static final int MA_TABLE_FLAG = 134217728;
    static final int KEY_MULTIPLE_VALUES = 268435456;
    static final int KEY_LENGTH_SHIFT = 29;
    static final boolean $assertionsDisabled;
    
    private SpoofChecker() {
        this.fCachedIdentifierInfo = null;
    }
    
    public RestrictionLevel getRestrictionLevel() {
        return this.fRestrictionLevel;
    }
    
    public int getChecks() {
        return this.fChecks;
    }
    
    public Set getAllowedLocales() {
        return this.fAllowedLocales;
    }
    
    public UnicodeSet getAllowedChars() {
        return this.fAllowedCharsSet;
    }
    
    public boolean failsChecks(final String s, final CheckResult checkResult) {
        final int length = s.length();
        if (checkResult != null) {
            checkResult.position = 0;
            checkResult.numerics = null;
            checkResult.restrictionLevel = null;
        }
        IdentifierInfo identifierInfo = null;
        if (0x0 != (this.fChecks & 0x90)) {
            identifierInfo = this.getIdentifierInfo().setIdentifier(s).setIdentifierProfile(this.fAllowedCharsSet);
        }
        if (0x0 != (this.fChecks & 0x10)) {
            final RestrictionLevel restrictionLevel = identifierInfo.getRestrictionLevel();
            if (restrictionLevel.compareTo(this.fRestrictionLevel) > 0) {}
            if (checkResult != null) {
                checkResult.restrictionLevel = restrictionLevel;
            }
        }
        if (0x0 != (this.fChecks & 0x80)) {
            final UnicodeSet numerics = identifierInfo.getNumerics();
            if (numerics.size() > 1) {}
            if (checkResult != null) {
                checkResult.numerics = numerics;
            }
        }
        if (0x0 != (this.fChecks & 0x40)) {
            while (0 < length) {
                Character.codePointAt(s, 0);
                Character.offsetByCodePoints(s, 0, 1);
                if (!this.fAllowedCharsSet.contains(0)) {
                    break;
                }
            }
        }
        if (0x0 != (this.fChecks & 0x26)) {
            final String normalize = SpoofChecker.nfdNormalizer.normalize(s);
            if (0x0 != (this.fChecks & 0x20)) {
                final UnicodeSet set = new UnicodeSet();
                while (0 < length) {
                    final int codePoint = Character.codePointAt(normalize, 0);
                    Character.offsetByCodePoints(normalize, 0, 1);
                    if (Character.getType(codePoint) != 6) {
                        if (!true) {
                            continue;
                        }
                        set.clear();
                    }
                    else {
                        if (!false) {
                            continue;
                        }
                        if (!true) {
                            set.add(0);
                        }
                        if (set.contains(codePoint)) {
                            break;
                        }
                        set.add(codePoint);
                    }
                }
            }
            if (0x0 != (this.fChecks & 0x6)) {
                if (identifierInfo == null) {
                    identifierInfo = this.getIdentifierInfo();
                    identifierInfo.setIdentifier(s);
                }
                identifierInfo.getScriptCount();
                final ScriptSet set2 = new ScriptSet();
                this.wholeScriptCheck(normalize, set2);
                set2.countMembers();
                if (0x0 == (this.fChecks & 0x4) || 0 < 2 || false == true) {}
                if (0x0 == (this.fChecks & 0x2) || 0 < 1 || 0 > 1) {}
            }
        }
        if (checkResult != null) {
            checkResult.checks = 0;
        }
        this.releaseIdentifierInfo(identifierInfo);
        return false;
    }
    
    public boolean failsChecks(final String s) {
        return this.failsChecks(s, null);
    }
    
    public int areConfusable(final String identifier, final String identifier2) {
        if ((this.fChecks & 0x7) == 0x0) {
            throw new IllegalArgumentException("No confusable checks are enabled.");
        }
        int n = this.fChecks & 0x8;
        final IdentifierInfo identifierInfo = this.getIdentifierInfo();
        identifierInfo.setIdentifier(identifier);
        final int scriptCount = identifierInfo.getScriptCount();
        identifierInfo.setIdentifier(identifier2);
        final int scriptCount2 = identifierInfo.getScriptCount();
        this.releaseIdentifierInfo(identifierInfo);
        if (0x0 != (this.fChecks & 0x1) && scriptCount <= 1 && scriptCount2 <= 1) {
            n |= 0x1;
            if (this.getSkeleton(n, identifier).equals(this.getSkeleton(n, identifier2))) {}
        }
        if (false) {
            return 0;
        }
        final boolean b = scriptCount <= 1 && scriptCount2 <= 1 && 0x0 != (this.fChecks & 0x4);
        if (0x0 != (this.fChecks & 0x2) || b) {
            final int n2 = n & 0xFFFFFFFE;
            if (!this.getSkeleton(n2, identifier).equals(this.getSkeleton(n2, identifier2)) || b) {}
        }
        return 0;
    }
    
    public String getSkeleton(final int n, final String s) {
        switch (n) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 8: {
                break;
            }
            case 9: {
                break;
            }
            default: {
                throw new IllegalArgumentException("SpoofChecker.getSkeleton(), bad type value.");
            }
        }
        final String normalize = SpoofChecker.nfdNormalizer.normalize(s);
        final int length = normalize.length();
        final StringBuilder sb = new StringBuilder();
        while (0 < length) {
            final int codePoint = Character.codePointAt(normalize, 0);
            final int n2 = 0 + Character.charCount(codePoint);
            this.confusableLookup(codePoint, 33554432, sb);
        }
        return SpoofChecker.nfdNormalizer.normalize(sb.toString());
    }
    
    private void confusableLookup(final int n, final int n2, final StringBuilder sb) {
        final int fcfuKeysSize = this.fSpoofData.fRawData.fCFUKeysSize;
        do {
            final int n3 = this.fSpoofData.fCFUKeys[0] & 0x1FFFFF;
            if (n == n3) {
                break;
            }
            if (n < n3) {}
        } while (0 < 0);
        if (!true) {
            sb.appendCodePoint(n);
            return;
        }
        int n4 = this.fSpoofData.fCFUKeys[0] & 0xFF000000;
        if ((n4 & n2) == 0x0) {
            if (0x0 != (n4 & 0x10000000)) {
                int fStrLength = 0;
                while ((this.fSpoofData.fCFUKeys[1] & 0xFFFFFF) == n) {
                    n4 = (this.fSpoofData.fCFUKeys[1] & 0xFF000000);
                    if (0x0 != (n4 & n2)) {
                        break;
                    }
                    --fStrLength;
                }
                if (!true) {
                    while ((this.fSpoofData.fCFUKeys[1] & 0xFFFFFF) == n) {
                        n4 = (this.fSpoofData.fCFUKeys[1] & 0xFF000000);
                        if (0x0 != (n4 & n2)) {
                            break;
                        }
                        ++fStrLength;
                    }
                }
            }
            if (!true) {
                sb.appendCodePoint(n);
                return;
            }
        }
        int fStrLength = getKeyLength(n4) + 1;
        final short n5 = this.fSpoofData.fCFUValues[0];
        if (true == true) {
            sb.append((char)n5);
            return;
        }
        if (1 == 4) {
            final int fcfuStringLengthsSize = this.fSpoofData.fRawData.fCFUStringLengthsSize;
            while (0 < fcfuStringLengthsSize) {
                if (this.fSpoofData.fCFUStringLengths[0].fLastString >= n5) {
                    fStrLength = this.fSpoofData.fCFUStringLengths[0].fStrLength;
                    break;
                }
                int n6 = 0;
                ++n6;
            }
            assert 0 < fcfuStringLengthsSize;
        }
        assert n5 + 1 <= this.fSpoofData.fRawData.fCFUStringTableLen;
        sb.append(this.fSpoofData.fCFUStrings, n5, 1);
    }
    
    void wholeScriptCheck(final CharSequence charSequence, final ScriptSet set) {
        final Trie2 trie2 = (0x0 != (this.fChecks & 0x8)) ? this.fSpoofData.fAnyCaseTrie : this.fSpoofData.fLowerCaseTrie;
        set.setAll();
        while (0 < charSequence.length()) {
            final int codePoint = Character.codePointAt(charSequence, 0);
            Character.offsetByCodePoints(charSequence, 0, 1);
            final int value = trie2.get(codePoint);
            if (value == 0) {
                final int script = UScript.getScript(codePoint);
                assert script > 1;
                set.intersect(script);
            }
            else {
                if (value == 1) {
                    continue;
                }
                set.intersect(this.fSpoofData.fScriptSets[value]);
            }
        }
    }
    
    private IdentifierInfo getIdentifierInfo() {
        // monitorenter(this)
        IdentifierInfo fCachedIdentifierInfo = this.fCachedIdentifierInfo;
        this.fCachedIdentifierInfo = null;
        // monitorexit(this)
        if (fCachedIdentifierInfo == null) {
            fCachedIdentifierInfo = new IdentifierInfo();
        }
        return fCachedIdentifierInfo;
    }
    
    private void releaseIdentifierInfo(final IdentifierInfo fCachedIdentifierInfo) {
        if (fCachedIdentifierInfo != null) {
            // monitorenter(this)
            if (this.fCachedIdentifierInfo == null) {
                this.fCachedIdentifierInfo = fCachedIdentifierInfo;
            }
        }
        // monitorexit(this)
    }
    
    static final int getKeyLength(final int n) {
        return n >> 29 & 0x3;
    }
    
    static int access$000(final SpoofChecker spoofChecker) {
        return spoofChecker.fMagic;
    }
    
    static int access$100(final SpoofChecker spoofChecker) {
        return spoofChecker.fChecks;
    }
    
    static UnicodeSet access$200(final SpoofChecker spoofChecker) {
        return spoofChecker.fAllowedCharsSet;
    }
    
    static Set access$300(final SpoofChecker spoofChecker) {
        return spoofChecker.fAllowedLocales;
    }
    
    static RestrictionLevel access$400(final SpoofChecker spoofChecker) {
        return spoofChecker.fRestrictionLevel;
    }
    
    SpoofChecker(final SpoofChecker$1 object) {
        this();
    }
    
    static int access$002(final SpoofChecker spoofChecker, final int fMagic) {
        return spoofChecker.fMagic = fMagic;
    }
    
    static int access$102(final SpoofChecker spoofChecker, final int fChecks) {
        return spoofChecker.fChecks = fChecks;
    }
    
    static SpoofData access$602(final SpoofChecker spoofChecker, final SpoofData fSpoofData) {
        return spoofChecker.fSpoofData = fSpoofData;
    }
    
    static UnicodeSet access$202(final SpoofChecker spoofChecker, final UnicodeSet fAllowedCharsSet) {
        return spoofChecker.fAllowedCharsSet = fAllowedCharsSet;
    }
    
    static Set access$302(final SpoofChecker spoofChecker, final Set fAllowedLocales) {
        return spoofChecker.fAllowedLocales = fAllowedLocales;
    }
    
    static RestrictionLevel access$402(final SpoofChecker spoofChecker, final RestrictionLevel fRestrictionLevel) {
        return spoofChecker.fRestrictionLevel = fRestrictionLevel;
    }
    
    static {
        $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
        INCLUSION = new UnicodeSet("[\\-.\\u00B7\\u05F3\\u05F4\\u0F0B\\u200C\\u200D\\u2019]");
        RECOMMENDED = new UnicodeSet("[[0-z\\u00C0-\\u017E\\u01A0\\u01A1\\u01AF\\u01B0\\u01CD-\\u01DC\\u01DE-\\u01E3\\u01E6-\\u01F5\\u01F8-\\u021B\\u021E\\u021F\\u0226-\\u0233\\u02BB\\u02BC\\u02EC\\u0300-\\u0304\\u0306-\\u030C\\u030F-\\u0311\\u0313\\u0314\\u031B\\u0323-\\u0328\\u032D\\u032E\\u0330\\u0331\\u0335\\u0338\\u0339\\u0342-\\u0345\\u037B-\\u03CE\\u03FC-\\u045F\\u048A-\\u0525\\u0531-\\u0586\\u05D0-\\u05F2\\u0621-\\u063F\\u0641-\\u0655\\u0660-\\u0669\\u0670-\\u068D\\u068F-\\u06D5\\u06E5\\u06E6\\u06EE-\\u06FF\\u0750-\\u07B1\\u0901-\\u0939\\u093C-\\u094D\\u0950\\u0960-\\u0972\\u0979-\\u0A4D\\u0A5C-\\u0A74\\u0A81-\\u0B43\\u0B47-\\u0B61\\u0B66-\\u0C56\\u0C60\\u0C61\\u0C66-\\u0CD6\\u0CE0-\\u0CEF\\u0D02-\\u0D28\\u0D2A-\\u0D39\\u0D3D-\\u0D43\\u0D46-\\u0D4D\\u0D57-\\u0D61\\u0D66-\\u0D8E\\u0D91-\\u0DA5\\u0DA7-\\u0DDE\\u0DF2\\u0E01-\\u0ED9\\u0F00\\u0F20-\\u0F8B\\u0F90-\\u109D\\u10D0-\\u10F0\\u10F7-\\u10FA\\u1200-\\u135A\\u135F\\u1380-\\u138F\\u1401-\\u167F\\u1780-\\u17A2\\u17A5-\\u17A7\\u17A9-\\u17B3\\u17B6-\\u17CA\\u17D2\\u17D7-\\u17DC\\u17E0-\\u17E9\\u1810-\\u18A8\\u18AA-\\u18F5\\u1E00-\\u1E99\\u1F00-\\u1FFC\\u2D30-\\u2D65\\u2D80-\\u2DDE\\u3005-\\u3007\\u3041-\\u31B7\\u3400-\\u9FCB\\uA000-\\uA48C\\uA67F\\uA717-\\uA71F\\uA788\\uAA60-\\uAA7B\\uAC00-\\uD7A3\\uFA0E-\\uFA29\\U00020000-\\U0002B734]-[[:Cn:][:nfkcqc=n:][:XIDC=n:]]]");
        SpoofChecker.nfdNormalizer = Normalizer2.getNFDInstance();
    }
    
    private static class ScriptSet
    {
        private int[] bits;
        static final boolean $assertionsDisabled;
        
        public ScriptSet() {
            this.bits = new int[6];
        }
        
        public ScriptSet(final DataInputStream dataInputStream) throws IOException {
            this.bits = new int[6];
            while (0 < this.bits.length) {
                this.bits[0] = dataInputStream.readInt();
                int n = 0;
                ++n;
            }
        }
        
        public void output(final DataOutputStream dataOutputStream) throws IOException {
            while (0 < this.bits.length) {
                dataOutputStream.writeInt(this.bits[0]);
                int n = 0;
                ++n;
            }
        }
        
        public boolean equals(final ScriptSet set) {
            while (0 < this.bits.length) {
                if (this.bits[0] != set.bits[0]) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        public void Union(final int n) {
            final int n2 = n / 32;
            final int n3 = 1 << (n & 0x1F);
            assert n2 < this.bits.length * 4 * 4;
            final int[] bits = this.bits;
            final int n4 = n2;
            bits[n4] |= n3;
        }
        
        public void Union(final ScriptSet set) {
            while (0 < this.bits.length) {
                final int[] bits = this.bits;
                final int n = 0;
                bits[n] |= set.bits[0];
                int n2 = 0;
                ++n2;
            }
        }
        
        public void intersect(final ScriptSet set) {
            while (0 < this.bits.length) {
                final int[] bits = this.bits;
                final int n = 0;
                bits[n] &= set.bits[0];
                int n2 = 0;
                ++n2;
            }
        }
        
        public void intersect(final int p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: bipush          32
            //     3: idiv           
            //     4: istore_2       
            //     5: iconst_1       
            //     6: iload_1        
            //     7: bipush          31
            //     9: iand           
            //    10: ishl           
            //    11: istore_3       
            //    12: getstatic       com/ibm/icu/text/SpoofChecker$ScriptSet.$assertionsDisabled:Z
            //    15: ifne            39
            //    18: iload_2        
            //    19: aload_0        
            //    20: getfield        com/ibm/icu/text/SpoofChecker$ScriptSet.bits:[I
            //    23: arraylength    
            //    24: iconst_4       
            //    25: imul           
            //    26: iconst_4       
            //    27: imul           
            //    28: if_icmplt       39
            //    31: new             Ljava/lang/AssertionError;
            //    34: dup            
            //    35: invokespecial   java/lang/AssertionError.<init>:()V
            //    38: athrow         
            //    39: iconst_0       
            //    40: iload_2        
            //    41: if_icmpge       57
            //    44: aload_0        
            //    45: getfield        com/ibm/icu/text/SpoofChecker$ScriptSet.bits:[I
            //    48: iconst_0       
            //    49: iconst_0       
            //    50: iastore        
            //    51: iinc            4, 1
            //    54: goto            39
            //    57: aload_0        
            //    58: getfield        com/ibm/icu/text/SpoofChecker$ScriptSet.bits:[I
            //    61: iload_2        
            //    62: dup2           
            //    63: iaload         
            //    64: iload_3        
            //    65: iand           
            //    66: iastore        
            //    67: iload_2        
            //    68: iconst_1       
            //    69: iadd           
            //    70: istore          4
            //    72: iconst_0       
            //    73: aload_0        
            //    74: getfield        com/ibm/icu/text/SpoofChecker$ScriptSet.bits:[I
            //    77: arraylength    
            //    78: if_icmpge       94
            //    81: aload_0        
            //    82: getfield        com/ibm/icu/text/SpoofChecker$ScriptSet.bits:[I
            //    85: iconst_0       
            //    86: iconst_0       
            //    87: iastore        
            //    88: iinc            4, 1
            //    91: goto            72
            //    94: return         
            // 
            // The error that occurred was:
            // 
            // java.util.ConcurrentModificationException
            //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
            //     at java.util.ArrayList$Itr.next(Unknown Source)
            //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        
        public void setAll() {
            while (0 < this.bits.length) {
                this.bits[0] = -1;
                int n = 0;
                ++n;
            }
        }
        
        public void resetAll() {
            while (0 < this.bits.length) {
                this.bits[0] = 0;
                int n = 0;
                ++n;
            }
        }
        
        public int countMembers() {
            while (0 < this.bits.length) {
                for (int i = this.bits[0]; i != 0; i &= i - 1) {
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            return 0;
        }
        
        static {
            $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
        }
    }
    
    private static class SpoofData
    {
        SpoofDataHeader fRawData;
        int[] fCFUKeys;
        short[] fCFUValues;
        SpoofStringLengthsElement[] fCFUStringLengths;
        char[] fCFUStrings;
        Trie2 fAnyCaseTrie;
        Trie2 fLowerCaseTrie;
        ScriptSet[] fScriptSets;
        static final boolean $assertionsDisabled;
        
        public static SpoofData getDefault() throws IOException {
            return new SpoofData(ICUData.getRequiredStream("data/icudt51b/confusables.cfu"));
        }
        
        public SpoofData() {
            this.fRawData = new SpoofDataHeader();
            this.fRawData.fMagic = 944111087;
            this.fRawData.fFormatVersion[0] = 1;
            this.fRawData.fFormatVersion[1] = 0;
            this.fRawData.fFormatVersion[2] = 0;
            this.fRawData.fFormatVersion[3] = 0;
        }
        
        public SpoofData(final InputStream inputStream) throws IOException {
            final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            dataInputStream.skip(128L);
            assert dataInputStream.markSupported();
            dataInputStream.mark(Integer.MAX_VALUE);
            this.fRawData = new SpoofDataHeader(dataInputStream);
            this.initPtrs(dataInputStream);
        }
        
        static boolean validateDataVersion(final SpoofDataHeader spoofDataHeader) {
            return spoofDataHeader != null && spoofDataHeader.fMagic == 944111087 && spoofDataHeader.fFormatVersion[0] <= 1 && spoofDataHeader.fFormatVersion[1] <= 0;
        }
        
        void initPtrs(final DataInputStream dataInputStream) throws IOException {
            this.fCFUKeys = null;
            this.fCFUValues = null;
            this.fCFUStringLengths = null;
            this.fCFUStrings = null;
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fCFUKeys);
            int n = 0;
            if (this.fRawData.fCFUKeys != 0) {
                this.fCFUKeys = new int[this.fRawData.fCFUKeysSize];
                while (0 < this.fRawData.fCFUKeysSize) {
                    this.fCFUKeys[0] = dataInputStream.readInt();
                    ++n;
                }
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fCFUStringIndex);
            if (this.fRawData.fCFUStringIndex != 0) {
                this.fCFUValues = new short[this.fRawData.fCFUStringIndexSize];
                while (0 < this.fRawData.fCFUStringIndexSize) {
                    this.fCFUValues[0] = dataInputStream.readShort();
                    ++n;
                }
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fCFUStringTable);
            if (this.fRawData.fCFUStringTable != 0) {
                this.fCFUStrings = new char[this.fRawData.fCFUStringTableLen];
                while (0 < this.fRawData.fCFUStringTableLen) {
                    this.fCFUStrings[0] = dataInputStream.readChar();
                    ++n;
                }
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fCFUStringLengths);
            if (this.fRawData.fCFUStringLengths != 0) {
                this.fCFUStringLengths = new SpoofStringLengthsElement[this.fRawData.fCFUStringLengthsSize];
                while (0 < this.fRawData.fCFUStringLengthsSize) {
                    this.fCFUStringLengths[0] = new SpoofStringLengthsElement(null);
                    this.fCFUStringLengths[0].fLastString = dataInputStream.readShort();
                    this.fCFUStringLengths[0].fStrLength = dataInputStream.readShort();
                    ++n;
                }
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fAnyCaseTrie);
            if (this.fAnyCaseTrie == null && this.fRawData.fAnyCaseTrie != 0) {
                this.fAnyCaseTrie = Trie2.createFromSerialized(dataInputStream);
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fLowerCaseTrie);
            if (this.fLowerCaseTrie == null && this.fRawData.fLowerCaseTrie != 0) {
                this.fLowerCaseTrie = Trie2.createFromSerialized(dataInputStream);
            }
            dataInputStream.reset();
            dataInputStream.skip(this.fRawData.fScriptSets);
            if (this.fRawData.fScriptSets != 0) {
                this.fScriptSets = new ScriptSet[this.fRawData.fScriptSetsLength];
                while (0 < this.fRawData.fScriptSetsLength) {
                    this.fScriptSets[0] = new ScriptSet(dataInputStream);
                    ++n;
                }
            }
        }
        
        static {
            $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
        }
        
        private static class SpoofStringLengthsElement
        {
            short fLastString;
            short fStrLength;
            
            private SpoofStringLengthsElement() {
            }
            
            SpoofStringLengthsElement(final SpoofChecker$1 object) {
                this();
            }
        }
    }
    
    private static class SpoofDataHeader
    {
        int fMagic;
        byte[] fFormatVersion;
        int fLength;
        int fCFUKeys;
        int fCFUKeysSize;
        int fCFUStringIndex;
        int fCFUStringIndexSize;
        int fCFUStringTable;
        int fCFUStringTableLen;
        int fCFUStringLengths;
        int fCFUStringLengthsSize;
        int fAnyCaseTrie;
        int fAnyCaseTrieLength;
        int fLowerCaseTrie;
        int fLowerCaseTrieLength;
        int fScriptSets;
        int fScriptSetsLength;
        int[] unused;
        
        public SpoofDataHeader() {
            this.fFormatVersion = new byte[4];
            this.unused = new int[15];
        }
        
        public SpoofDataHeader(final DataInputStream dataInputStream) throws IOException {
            this.fFormatVersion = new byte[4];
            this.unused = new int[15];
            this.fMagic = dataInputStream.readInt();
            int n = 0;
            while (0 < this.fFormatVersion.length) {
                this.fFormatVersion[0] = dataInputStream.readByte();
                ++n;
            }
            this.fLength = dataInputStream.readInt();
            this.fCFUKeys = dataInputStream.readInt();
            this.fCFUKeysSize = dataInputStream.readInt();
            this.fCFUStringIndex = dataInputStream.readInt();
            this.fCFUStringIndexSize = dataInputStream.readInt();
            this.fCFUStringTable = dataInputStream.readInt();
            this.fCFUStringTableLen = dataInputStream.readInt();
            this.fCFUStringLengths = dataInputStream.readInt();
            this.fCFUStringLengthsSize = dataInputStream.readInt();
            this.fAnyCaseTrie = dataInputStream.readInt();
            this.fAnyCaseTrieLength = dataInputStream.readInt();
            this.fLowerCaseTrie = dataInputStream.readInt();
            this.fLowerCaseTrieLength = dataInputStream.readInt();
            this.fScriptSets = dataInputStream.readInt();
            this.fScriptSetsLength = dataInputStream.readInt();
            while (0 < this.unused.length) {
                this.unused[0] = dataInputStream.readInt();
                ++n;
            }
        }
        
        public void output(final DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(this.fMagic);
            int n = 0;
            while (0 < this.fFormatVersion.length) {
                dataOutputStream.writeByte(this.fFormatVersion[0]);
                ++n;
            }
            dataOutputStream.writeInt(this.fLength);
            dataOutputStream.writeInt(this.fCFUKeys);
            dataOutputStream.writeInt(this.fCFUKeysSize);
            dataOutputStream.writeInt(this.fCFUStringIndex);
            dataOutputStream.writeInt(this.fCFUStringIndexSize);
            dataOutputStream.writeInt(this.fCFUStringTable);
            dataOutputStream.writeInt(this.fCFUStringTableLen);
            dataOutputStream.writeInt(this.fCFUStringLengths);
            dataOutputStream.writeInt(this.fCFUStringLengthsSize);
            dataOutputStream.writeInt(this.fAnyCaseTrie);
            dataOutputStream.writeInt(this.fAnyCaseTrieLength);
            dataOutputStream.writeInt(this.fLowerCaseTrie);
            dataOutputStream.writeInt(this.fLowerCaseTrieLength);
            dataOutputStream.writeInt(this.fScriptSets);
            dataOutputStream.writeInt(this.fScriptSetsLength);
            while (0 < this.unused.length) {
                dataOutputStream.writeInt(this.unused[0]);
                ++n;
            }
        }
    }
    
    public static class CheckResult
    {
        public int checks;
        @Deprecated
        public int position;
        public UnicodeSet numerics;
        public RestrictionLevel restrictionLevel;
        
        public CheckResult() {
            this.checks = 0;
            this.position = 0;
        }
    }
    
    public enum RestrictionLevel
    {
        ASCII("ASCII", 0), 
        HIGHLY_RESTRICTIVE("HIGHLY_RESTRICTIVE", 1), 
        MODERATELY_RESTRICTIVE("MODERATELY_RESTRICTIVE", 2), 
        MINIMALLY_RESTRICTIVE("MINIMALLY_RESTRICTIVE", 3), 
        UNRESTRICTIVE("UNRESTRICTIVE", 4);
        
        private static final RestrictionLevel[] $VALUES;
        
        private RestrictionLevel(final String s, final int n) {
        }
        
        static {
            $VALUES = new RestrictionLevel[] { RestrictionLevel.ASCII, RestrictionLevel.HIGHLY_RESTRICTIVE, RestrictionLevel.MODERATELY_RESTRICTIVE, RestrictionLevel.MINIMALLY_RESTRICTIVE, RestrictionLevel.UNRESTRICTIVE };
        }
    }
    
    public static class Builder
    {
        int fMagic;
        int fChecks;
        SpoofData fSpoofData;
        final UnicodeSet fAllowedCharsSet;
        final Set fAllowedLocales;
        private RestrictionLevel fRestrictionLevel;
        
        public Builder() {
            this.fAllowedCharsSet = new UnicodeSet(0, 1114111);
            this.fAllowedLocales = new LinkedHashSet();
            this.fMagic = 944111087;
            this.fChecks = -1;
            this.fSpoofData = null;
            this.fRestrictionLevel = RestrictionLevel.HIGHLY_RESTRICTIVE;
        }
        
        public Builder(final SpoofChecker spoofChecker) {
            this.fAllowedCharsSet = new UnicodeSet(0, 1114111);
            this.fAllowedLocales = new LinkedHashSet();
            this.fMagic = SpoofChecker.access$000(spoofChecker);
            this.fChecks = SpoofChecker.access$100(spoofChecker);
            this.fSpoofData = null;
            this.fAllowedCharsSet.set(SpoofChecker.access$200(spoofChecker));
            this.fAllowedLocales.addAll(SpoofChecker.access$300(spoofChecker));
            this.fRestrictionLevel = SpoofChecker.access$400(spoofChecker);
        }
        
        public SpoofChecker build() {
            if (this.fSpoofData == null) {
                this.fSpoofData = SpoofData.getDefault();
            }
            if (!SpoofData.validateDataVersion(this.fSpoofData.fRawData)) {
                return null;
            }
            final SpoofChecker spoofChecker = new SpoofChecker(null);
            SpoofChecker.access$002(spoofChecker, this.fMagic);
            SpoofChecker.access$102(spoofChecker, this.fChecks);
            SpoofChecker.access$602(spoofChecker, this.fSpoofData);
            SpoofChecker.access$202(spoofChecker, (UnicodeSet)this.fAllowedCharsSet.clone());
            SpoofChecker.access$200(spoofChecker).freeze();
            SpoofChecker.access$302(spoofChecker, this.fAllowedLocales);
            SpoofChecker.access$402(spoofChecker, this.fRestrictionLevel);
            return spoofChecker;
        }
        
        public Builder setData(final Reader reader, final Reader reader2) throws ParseException, IOException {
            this.fSpoofData = new SpoofData();
            final DataOutputStream dataOutputStream = new DataOutputStream(new ByteArrayOutputStream());
            ConfusabledataBuilder.buildConfusableData(this.fSpoofData, reader);
            WSConfusableDataBuilder.buildWSConfusableData(this.fSpoofData, dataOutputStream, reader2);
            return this;
        }
        
        public Builder setChecks(final int n) {
            if (0x0 != (n & 0x0)) {
                throw new IllegalArgumentException("Bad Spoof Checks value.");
            }
            this.fChecks = (n & -1);
            return this;
        }
        
        public Builder setAllowedLocales(final Set set) {
            this.fAllowedCharsSet.clear();
            final Iterator<ULocale> iterator = set.iterator();
            while (iterator.hasNext()) {
                this.addScriptChars(iterator.next(), this.fAllowedCharsSet);
            }
            this.fAllowedLocales.clear();
            if (set.size() == 0) {
                this.fAllowedCharsSet.add(0, 1114111);
                this.fChecks &= 0xFFFFFFBF;
                return this;
            }
            final UnicodeSet set2 = new UnicodeSet();
            set2.applyIntPropertyValue(4106, 0);
            this.fAllowedCharsSet.addAll(set2);
            set2.applyIntPropertyValue(4106, 1);
            this.fAllowedCharsSet.addAll(set2);
            this.fAllowedLocales.addAll(set);
            this.fChecks |= 0x40;
            return this;
        }
        
        private void addScriptChars(final ULocale uLocale, final UnicodeSet set) {
            final int[] code = UScript.getCode(uLocale);
            final UnicodeSet set2 = new UnicodeSet();
            while (0 < code.length) {
                set2.applyIntPropertyValue(4106, code[0]);
                set.addAll(set2);
                int n = 0;
                ++n;
            }
        }
        
        public Builder setAllowedChars(final UnicodeSet set) {
            this.fAllowedCharsSet.set(set);
            this.fAllowedLocales.clear();
            this.fChecks |= 0x40;
            return this;
        }
        
        public Builder setRestrictionLevel(final RestrictionLevel fRestrictionLevel) {
            this.fRestrictionLevel = fRestrictionLevel;
            this.fChecks |= 0x10;
            return this;
        }
        
        private static class ConfusabledataBuilder
        {
            private SpoofData fSpoofData;
            private ByteArrayOutputStream bos;
            private DataOutputStream os;
            private Hashtable fSLTable;
            private Hashtable fSATable;
            private Hashtable fMLTable;
            private Hashtable fMATable;
            private UnicodeSet fKeySet;
            private StringBuffer fStringTable;
            private Vector fKeyVec;
            private Vector fValueVec;
            private Vector fStringLengthsTable;
            private SPUStringPool stringPool;
            private Pattern fParseLine;
            private Pattern fParseHexNum;
            private int fLineNum;
            static final boolean $assertionsDisabled;
            
            ConfusabledataBuilder(final SpoofData fSpoofData, final ByteArrayOutputStream bos) {
                this.bos = bos;
                this.os = new DataOutputStream(bos);
                this.fSpoofData = fSpoofData;
                this.fSLTable = new Hashtable();
                this.fSATable = new Hashtable();
                this.fMLTable = new Hashtable();
                this.fMATable = new Hashtable();
                this.fKeySet = new UnicodeSet();
                this.fKeyVec = new Vector();
                this.fValueVec = new Vector();
                this.stringPool = new SPUStringPool();
            }
            
            void build(final Reader p0) throws ParseException, IOException {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     4: invokespecial   java/lang/StringBuffer.<init>:()V
                //     7: astore_2       
                //     8: aload_1        
                //     9: aload_2        
                //    10: invokestatic    com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder.readWholeFileToString:(Ljava/io/Reader;Ljava/lang/StringBuffer;)V
                //    13: aload_0        
                //    14: ldc             "(?m)^[ \\t]*([0-9A-Fa-f]+)[ \\t]+;[ \\t]*([0-9A-Fa-f]+(?:[ \\t]+[0-9A-Fa-f]+)*)[ \\t]*;\\s*(?:(SL)|(SA)|(ML)|(MA))[ \\t]*(?:#.*?)?$|^([ \\t]*(?:#.*?)?)$|^(.*?)$"
                //    16: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
                //    19: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fParseLine:Ljava/util/regex/Pattern;
                //    22: aload_0        
                //    23: ldc             "\\s*([0-9A-F]+)"
                //    25: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
                //    28: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fParseHexNum:Ljava/util/regex/Pattern;
                //    31: aload_2        
                //    32: iconst_0       
                //    33: invokevirtual   java/lang/StringBuffer.charAt:(I)C
                //    36: ldc             65279
                //    38: if_icmpne       48
                //    41: aload_2        
                //    42: iconst_0       
                //    43: bipush          32
                //    45: invokevirtual   java/lang/StringBuffer.setCharAt:(IC)V
                //    48: aload_0        
                //    49: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fParseLine:Ljava/util/regex/Pattern;
                //    52: aload_2        
                //    53: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
                //    56: astore_3       
                //    57: aload_3        
                //    58: invokevirtual   java/util/regex/Matcher.find:()Z
                //    61: ifeq            458
                //    64: aload_0        
                //    65: dup            
                //    66: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fLineNum:I
                //    69: iconst_1       
                //    70: iadd           
                //    71: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fLineNum:I
                //    74: aload_3        
                //    75: bipush          7
                //    77: invokevirtual   java/util/regex/Matcher.start:(I)I
                //    80: iflt            86
                //    83: goto            57
                //    86: aload_3        
                //    87: bipush          8
                //    89: invokevirtual   java/util/regex/Matcher.start:(I)I
                //    92: iflt            145
                //    95: new             Ljava/text/ParseException;
                //    98: dup            
                //    99: new             Ljava/lang/StringBuilder;
                //   102: dup            
                //   103: invokespecial   java/lang/StringBuilder.<init>:()V
                //   106: ldc             "Confusables, line "
                //   108: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   111: aload_0        
                //   112: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fLineNum:I
                //   115: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   118: ldc             ": Unrecognized Line: "
                //   120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   123: aload_3        
                //   124: bipush          8
                //   126: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   129: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   132: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   135: aload_3        
                //   136: bipush          8
                //   138: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   141: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   144: athrow         
                //   145: aload_3        
                //   146: iconst_1       
                //   147: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   150: bipush          16
                //   152: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;I)I
                //   155: istore          4
                //   157: goto            208
                //   160: new             Ljava/text/ParseException;
                //   163: dup            
                //   164: new             Ljava/lang/StringBuilder;
                //   167: dup            
                //   168: invokespecial   java/lang/StringBuilder.<init>:()V
                //   171: ldc             "Confusables, line "
                //   173: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   176: aload_0        
                //   177: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fLineNum:I
                //   180: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   183: ldc             ": Bad code point: "
                //   185: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   188: aload_3        
                //   189: iconst_1       
                //   190: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   193: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   196: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   199: aload_3        
                //   200: iconst_1       
                //   201: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   204: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   207: athrow         
                //   208: aload_0        
                //   209: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fParseHexNum:Ljava/util/regex/Pattern;
                //   212: aload_3        
                //   213: iconst_2       
                //   214: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   217: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
                //   220: astore          5
                //   222: new             Ljava/lang/StringBuilder;
                //   225: dup            
                //   226: invokespecial   java/lang/StringBuilder.<init>:()V
                //   229: astore          6
                //   231: aload           5
                //   233: invokevirtual   java/util/regex/Matcher.find:()Z
                //   236: ifeq            314
                //   239: aload           5
                //   241: iconst_1       
                //   242: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   245: bipush          16
                //   247: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;I)I
                //   250: istore          7
                //   252: goto            304
                //   255: new             Ljava/text/ParseException;
                //   258: dup            
                //   259: new             Ljava/lang/StringBuilder;
                //   262: dup            
                //   263: invokespecial   java/lang/StringBuilder.<init>:()V
                //   266: ldc             "Confusables, line "
                //   268: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   271: aload_0        
                //   272: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fLineNum:I
                //   275: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   278: ldc             ": Bad code point: "
                //   280: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   283: iconst_0       
                //   284: bipush          16
                //   286: invokestatic    java/lang/Integer.toString:(II)Ljava/lang/String;
                //   289: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   292: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   295: aload_3        
                //   296: iconst_2       
                //   297: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   300: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   303: athrow         
                //   304: aload           6
                //   306: iconst_0       
                //   307: invokevirtual   java/lang/StringBuilder.appendCodePoint:(I)Ljava/lang/StringBuilder;
                //   310: pop            
                //   311: goto            231
                //   314: getstatic       com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.$assertionsDisabled:Z
                //   317: ifne            337
                //   320: aload           6
                //   322: invokevirtual   java/lang/StringBuilder.length:()I
                //   325: iconst_1       
                //   326: if_icmpge       337
                //   329: new             Ljava/lang/AssertionError;
                //   332: dup            
                //   333: invokespecial   java/lang/AssertionError.<init>:()V
                //   336: athrow         
                //   337: aload_0        
                //   338: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.stringPool:Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool;
                //   341: aload           6
                //   343: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   346: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool.addString:(Ljava/lang/String;)Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString;
                //   349: astore          7
                //   351: aload_3        
                //   352: iconst_3       
                //   353: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   356: iflt            366
                //   359: aload_0        
                //   360: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fSLTable:Ljava/util/Hashtable;
                //   363: goto            413
                //   366: aload_3        
                //   367: iconst_4       
                //   368: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   371: iflt            381
                //   374: aload_0        
                //   375: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fSATable:Ljava/util/Hashtable;
                //   378: goto            413
                //   381: aload_3        
                //   382: iconst_5       
                //   383: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   386: iflt            396
                //   389: aload_0        
                //   390: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fMLTable:Ljava/util/Hashtable;
                //   393: goto            413
                //   396: aload_3        
                //   397: bipush          6
                //   399: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   402: iflt            412
                //   405: aload_0        
                //   406: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fMATable:Ljava/util/Hashtable;
                //   409: goto            413
                //   412: aconst_null    
                //   413: astore          8
                //   415: getstatic       com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.$assertionsDisabled:Z
                //   418: ifne            434
                //   421: aload           8
                //   423: ifnonnull       434
                //   426: new             Ljava/lang/AssertionError;
                //   429: dup            
                //   430: invokespecial   java/lang/AssertionError.<init>:()V
                //   433: athrow         
                //   434: aload           8
                //   436: iconst_0       
                //   437: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   440: aload           7
                //   442: invokevirtual   java/util/Hashtable.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                //   445: pop            
                //   446: aload_0        
                //   447: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fKeySet:Lcom/ibm/icu/text/UnicodeSet;
                //   450: iconst_0       
                //   451: invokevirtual   com/ibm/icu/text/UnicodeSet.add:(I)Lcom/ibm/icu/text/UnicodeSet;
                //   454: pop            
                //   455: goto            57
                //   458: aload_0        
                //   459: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.stringPool:Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool;
                //   462: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool.sort:()V
                //   465: aload_0        
                //   466: new             Ljava/lang/StringBuffer;
                //   469: dup            
                //   470: invokespecial   java/lang/StringBuffer.<init>:()V
                //   473: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringTable:Ljava/lang/StringBuffer;
                //   476: aload_0        
                //   477: new             Ljava/util/Vector;
                //   480: dup            
                //   481: invokespecial   java/util/Vector.<init>:()V
                //   484: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringLengthsTable:Ljava/util/Vector;
                //   487: aload_0        
                //   488: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.stringPool:Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool;
                //   491: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool.size:()I
                //   494: istore          6
                //   496: iconst_0       
                //   497: iload           6
                //   499: if_icmpge       639
                //   502: aload_0        
                //   503: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.stringPool:Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool;
                //   506: iconst_0       
                //   507: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUStringPool.getByIndex:(I)Lcom/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString;
                //   510: astore          8
                //   512: aload           8
                //   514: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString.fStr:Ljava/lang/String;
                //   517: invokevirtual   java/lang/String.length:()I
                //   520: istore          9
                //   522: aload_0        
                //   523: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringTable:Ljava/lang/StringBuffer;
                //   526: invokevirtual   java/lang/StringBuffer.length:()I
                //   529: istore          10
                //   531: getstatic       com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.$assertionsDisabled:Z
                //   534: ifne            551
                //   537: iload           9
                //   539: iconst_0       
                //   540: if_icmpge       551
                //   543: new             Ljava/lang/AssertionError;
                //   546: dup            
                //   547: invokespecial   java/lang/AssertionError.<init>:()V
                //   550: athrow         
                //   551: iload           9
                //   553: iconst_1       
                //   554: if_icmpne       574
                //   557: aload           8
                //   559: aload           8
                //   561: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString.fStr:Ljava/lang/String;
                //   564: iconst_0       
                //   565: invokevirtual   java/lang/String.charAt:(I)C
                //   568: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString.fStrTableIndex:I
                //   571: goto            625
                //   574: iload           9
                //   576: iconst_0       
                //   577: if_icmple       605
                //   580: goto            605
                //   583: aload_0        
                //   584: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringLengthsTable:Ljava/util/Vector;
                //   587: iconst_0       
                //   588: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   591: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //   594: aload_0        
                //   595: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringLengthsTable:Ljava/util/Vector;
                //   598: iconst_0       
                //   599: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   602: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //   605: aload           8
                //   607: iload           10
                //   609: putfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString.fStrTableIndex:I
                //   612: aload_0        
                //   613: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringTable:Ljava/lang/StringBuffer;
                //   616: aload           8
                //   618: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder$SPUString.fStr:Ljava/lang/String;
                //   621: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
                //   624: pop            
                //   625: iload           9
                //   627: istore          4
                //   629: iload           10
                //   631: istore          5
                //   633: iinc            7, 1
                //   636: goto            496
                //   639: goto            664
                //   642: aload_0        
                //   643: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringLengthsTable:Ljava/util/Vector;
                //   646: iconst_0       
                //   647: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   650: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //   653: aload_0        
                //   654: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fStringLengthsTable:Ljava/util/Vector;
                //   657: iconst_0       
                //   658: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
                //   661: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //   664: iconst_0       
                //   665: aload_0        
                //   666: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fKeySet:Lcom/ibm/icu/text/UnicodeSet;
                //   669: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeCount:()I
                //   672: if_icmpge       762
                //   675: aload_0        
                //   676: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fKeySet:Lcom/ibm/icu/text/UnicodeSet;
                //   679: iconst_0       
                //   680: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeStart:(I)I
                //   683: istore          9
                //   685: iload           9
                //   687: aload_0        
                //   688: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fKeySet:Lcom/ibm/icu/text/UnicodeSet;
                //   691: iconst_0       
                //   692: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeEnd:(I)I
                //   695: if_icmpgt       756
                //   698: aload_0        
                //   699: iload           9
                //   701: aload_0        
                //   702: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fSLTable:Ljava/util/Hashtable;
                //   705: ldc_w           16777216
                //   708: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.addKeyEntry:(ILjava/util/Hashtable;I)V
                //   711: aload_0        
                //   712: iload           9
                //   714: aload_0        
                //   715: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fSATable:Ljava/util/Hashtable;
                //   718: ldc_w           33554432
                //   721: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.addKeyEntry:(ILjava/util/Hashtable;I)V
                //   724: aload_0        
                //   725: iload           9
                //   727: aload_0        
                //   728: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fMLTable:Ljava/util/Hashtable;
                //   731: ldc_w           67108864
                //   734: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.addKeyEntry:(ILjava/util/Hashtable;I)V
                //   737: aload_0        
                //   738: iload           9
                //   740: aload_0        
                //   741: getfield        com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.fMATable:Ljava/util/Hashtable;
                //   744: ldc_w           134217728
                //   747: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.addKeyEntry:(ILjava/util/Hashtable;I)V
                //   750: iinc            9, 1
                //   753: goto            685
                //   756: iinc            8, 1
                //   759: goto            664
                //   762: aload_0        
                //   763: invokevirtual   com/ibm/icu/text/SpoofChecker$Builder$ConfusabledataBuilder.outputData:()V
                //   766: return         
                //    Exceptions:
                //  throws java.text.ParseException
                //  throws java.io.IOException
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            void addKeyEntry(final int n, final Hashtable hashtable, final int n2) {
                final SPUString spuString = hashtable.get(n);
                if (spuString == null) {
                    return;
                }
                for (int i = this.fKeyVec.size() - 1; i >= 0; --i) {
                    final int intValue = this.fKeyVec.elementAt(i);
                    if ((intValue & 0xFFFFFF) != n) {
                        break;
                    }
                    if (this.getMapping(i).equals(spuString.fStr)) {
                        this.fKeyVec.setElementAt(intValue | n2, i);
                        return;
                    }
                }
                final int n3 = n | n2 | 0x10000000;
                final int n4 = spuString.fStr.length() - 1;
                final int n5 = n3 | 0x60000000;
                final int fStrTableIndex = spuString.fStrTableIndex;
                this.fKeyVec.addElement(n5);
                this.fValueVec.addElement(fStrTableIndex);
                final int n6 = this.fKeyVec.size() - 2;
                this.fKeyVec.setElementAt((int)this.fKeyVec.elementAt(n6) | 0x10000000, n6);
            }
            
            String getMapping(final int n) {
                final int intValue = this.fKeyVec.elementAt(n);
                final int intValue2 = this.fValueVec.elementAt(n);
                SpoofChecker.getKeyLength(intValue);
                switch (false) {
                    case 0: {
                        return new String(new char[] { (char)intValue2 });
                    }
                    case 1:
                    case 2: {
                        return this.fStringTable.substring(intValue2, intValue2 + 0 + 1);
                    }
                    case 3: {
                        while (0 < this.fStringLengthsTable.size()) {
                            if (intValue2 <= this.fStringLengthsTable.elementAt(0)) {
                                this.fStringLengthsTable.elementAt(1);
                                break;
                            }
                            final int n2;
                            n2 += 2;
                        }
                        assert false;
                        return this.fStringTable.substring(intValue2, intValue2 + 0);
                    }
                    default: {
                        assert false;
                        return "";
                    }
                }
            }
            
            void outputData() throws IOException {
                final SpoofDataHeader fRawData = this.fSpoofData.fRawData;
                final int size = this.fKeyVec.size();
                fRawData.output(this.os);
                fRawData.fCFUKeys = this.os.size();
                assert fRawData.fCFUKeys == 128;
                fRawData.fCFUKeysSize = size;
                int n = 0;
                while (0 < size) {
                    final int intValue = this.fKeyVec.elementAt(0);
                    assert (intValue & 0xFFFFFF) >= 0;
                    assert (intValue & 0xFF000000) != 0x0;
                    this.os.writeInt(intValue);
                    ++n;
                }
                final int size2 = this.fValueVec.size();
                assert size == size2;
                fRawData.fCFUStringIndex = this.os.size();
                fRawData.fCFUStringIndexSize = size2;
                while (0 < size2) {
                    final int intValue2 = this.fValueVec.elementAt(0);
                    assert intValue2 < 65535;
                    this.os.writeShort((short)intValue2);
                    ++n;
                }
                final int length = this.fStringTable.length();
                final String string = this.fStringTable.toString();
                fRawData.fCFUStringTable = this.os.size();
                fRawData.fCFUStringTableLen = length;
                while (0 < length) {
                    this.os.writeChar(string.charAt(0));
                    ++n;
                }
                final int size3 = this.fStringLengthsTable.size();
                fRawData.fCFUStringLengthsSize = size3 / 2;
                fRawData.fCFUStringLengths = this.os.size();
                while (0 < size3) {
                    final int intValue3 = this.fStringLengthsTable.elementAt(0);
                    final int intValue4 = this.fStringLengthsTable.elementAt(1);
                    assert intValue3 < length;
                    assert intValue4 < 40;
                    assert intValue4 > 0;
                    this.os.writeShort((short)intValue3);
                    this.os.writeShort((short)intValue4);
                    n += 2;
                }
                this.os.flush();
                final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(this.bos.toByteArray()));
                dataInputStream.mark(Integer.MAX_VALUE);
                this.fSpoofData.initPtrs(dataInputStream);
            }
            
            public static void buildConfusableData(final SpoofData spoofData, final Reader reader) throws IOException, ParseException {
                new ConfusabledataBuilder(spoofData, new ByteArrayOutputStream()).build(reader);
            }
            
            static {
                $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
            }
            
            private static class SPUStringPool
            {
                private Vector fVec;
                private Hashtable fHash;
                
                public SPUStringPool() {
                    this.fVec = new Vector();
                    this.fHash = new Hashtable();
                }
                
                public int size() {
                    return this.fVec.size();
                }
                
                public SPUString getByIndex(final int n) {
                    return this.fVec.elementAt(n);
                }
                
                public SPUString addString(final String s) {
                    SPUString spuString = this.fHash.get(s);
                    if (spuString == null) {
                        spuString = new SPUString(s);
                        this.fHash.put(s, spuString);
                        this.fVec.addElement(spuString);
                    }
                    return spuString;
                }
                
                public void sort() {
                    Collections.sort((List<Object>)this.fVec, new SPUStringComparator(null));
                }
            }
            
            private static class SPUString
            {
                String fStr;
                int fStrTableIndex;
                
                SPUString(final String fStr) {
                    this.fStr = fStr;
                    this.fStrTableIndex = 0;
                }
            }
            
            private static class SPUStringComparator implements Comparator
            {
                private SPUStringComparator() {
                }
                
                public int compare(final SPUString spuString, final SPUString spuString2) {
                    final int length = spuString.fStr.length();
                    final int length2 = spuString2.fStr.length();
                    if (length < length2) {
                        return -1;
                    }
                    if (length > length2) {
                        return 1;
                    }
                    return spuString.fStr.compareTo(spuString2.fStr);
                }
                
                public int compare(final Object o, final Object o2) {
                    return this.compare((SPUString)o, (SPUString)o2);
                }
                
                SPUStringComparator(final SpoofChecker$1 object) {
                    this();
                }
            }
        }
        
        private static class WSConfusableDataBuilder
        {
            static String parseExp;
            static final boolean $assertionsDisabled;
            
            static void readWholeFileToString(final Reader reader, final StringBuffer sb) throws IOException {
                final LineNumberReader lineNumberReader = new LineNumberReader(reader);
                while (true) {
                    final String line = lineNumberReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    sb.append('\n');
                }
            }
            
            static void buildWSConfusableData(final SpoofData p0, final DataOutputStream p1, final Reader p2) throws ParseException, IOException {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: astore_3       
                //     2: new             Ljava/lang/StringBuffer;
                //     5: dup            
                //     6: invokespecial   java/lang/StringBuffer.<init>:()V
                //     9: astore          4
                //    11: aconst_null    
                //    12: astore          6
                //    14: new             Lcom/ibm/icu/impl/Trie2Writable;
                //    17: dup            
                //    18: iconst_0       
                //    19: iconst_0       
                //    20: invokespecial   com/ibm/icu/impl/Trie2Writable.<init>:(II)V
                //    23: astore          8
                //    25: new             Lcom/ibm/icu/impl/Trie2Writable;
                //    28: dup            
                //    29: iconst_0       
                //    30: iconst_0       
                //    31: invokespecial   com/ibm/icu/impl/Trie2Writable.<init>:(II)V
                //    34: astore          9
                //    36: new             Ljava/util/Vector;
                //    39: dup            
                //    40: invokespecial   java/util/Vector.<init>:()V
                //    43: astore          6
                //    45: aload           6
                //    47: aconst_null    
                //    48: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //    51: aload           6
                //    53: aconst_null    
                //    54: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //    57: aload_2        
                //    58: aload           4
                //    60: invokestatic    com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder.readWholeFileToString:(Ljava/io/Reader;Ljava/lang/StringBuffer;)V
                //    63: getstatic       com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder.parseExp:Ljava/lang/String;
                //    66: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
                //    69: astore_3       
                //    70: aload           4
                //    72: iconst_0       
                //    73: invokevirtual   java/lang/StringBuffer.charAt:(I)C
                //    76: ldc             65279
                //    78: if_icmpne       89
                //    81: aload           4
                //    83: iconst_0       
                //    84: bipush          32
                //    86: invokevirtual   java/lang/StringBuffer.setCharAt:(IC)V
                //    89: aload_3        
                //    90: aload           4
                //    92: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
                //    95: astore          10
                //    97: aload           10
                //    99: invokevirtual   java/util/regex/Matcher.find:()Z
                //   102: ifeq            695
                //   105: iinc            5, 1
                //   108: aload           10
                //   110: iconst_1       
                //   111: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   114: iflt            120
                //   117: goto            97
                //   120: aload           10
                //   122: bipush          8
                //   124: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   127: iflt            175
                //   130: new             Ljava/text/ParseException;
                //   133: dup            
                //   134: new             Ljava/lang/StringBuilder;
                //   137: dup            
                //   138: invokespecial   java/lang/StringBuilder.<init>:()V
                //   141: ldc             "ConfusablesWholeScript, line "
                //   143: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   146: iconst_0       
                //   147: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   150: ldc             ": Unrecognized input: "
                //   152: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   155: aload           10
                //   157: invokevirtual   java/util/regex/Matcher.group:()Ljava/lang/String;
                //   160: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   163: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   166: aload           10
                //   168: invokevirtual   java/util/regex/Matcher.start:()I
                //   171: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   174: athrow         
                //   175: aload           10
                //   177: iconst_2       
                //   178: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   181: bipush          16
                //   183: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;I)I
                //   186: istore          11
                //   188: iconst_2       
                //   189: ldc             1114111
                //   191: if_icmple       241
                //   194: new             Ljava/text/ParseException;
                //   197: dup            
                //   198: new             Ljava/lang/StringBuilder;
                //   201: dup            
                //   202: invokespecial   java/lang/StringBuilder.<init>:()V
                //   205: ldc             "ConfusablesWholeScript, line "
                //   207: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   210: iconst_0       
                //   211: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   214: ldc             ": out of range code point: "
                //   216: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   219: aload           10
                //   221: iconst_2       
                //   222: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   228: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   231: aload           10
                //   233: iconst_2       
                //   234: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   237: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   240: athrow         
                //   241: aload           10
                //   243: iconst_3       
                //   244: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   247: iflt            263
                //   250: aload           10
                //   252: iconst_3       
                //   253: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   256: bipush          16
                //   258: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;I)I
                //   261: istore          12
                //   263: iconst_2       
                //   264: ldc             1114111
                //   266: if_icmple       316
                //   269: new             Ljava/text/ParseException;
                //   272: dup            
                //   273: new             Ljava/lang/StringBuilder;
                //   276: dup            
                //   277: invokespecial   java/lang/StringBuilder.<init>:()V
                //   280: ldc             "ConfusablesWholeScript, line "
                //   282: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   285: iconst_0       
                //   286: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   289: ldc             ": out of range code point: "
                //   291: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   294: aload           10
                //   296: iconst_3       
                //   297: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   300: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   303: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   306: aload           10
                //   308: iconst_3       
                //   309: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   312: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   315: athrow         
                //   316: aload           10
                //   318: iconst_4       
                //   319: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   322: astore          13
                //   324: aload           10
                //   326: iconst_5       
                //   327: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   330: astore          14
                //   332: sipush          4106
                //   335: aload           13
                //   337: invokestatic    com/ibm/icu/lang/UCharacter.getPropertyValueEnum:(ILjava/lang/CharSequence;)I
                //   340: istore          15
                //   342: sipush          4106
                //   345: aload           14
                //   347: invokestatic    com/ibm/icu/lang/UCharacter.getPropertyValueEnum:(ILjava/lang/CharSequence;)I
                //   350: istore          16
                //   352: iload           15
                //   354: iconst_m1      
                //   355: if_icmpne       405
                //   358: new             Ljava/text/ParseException;
                //   361: dup            
                //   362: new             Ljava/lang/StringBuilder;
                //   365: dup            
                //   366: invokespecial   java/lang/StringBuilder.<init>:()V
                //   369: ldc             "ConfusablesWholeScript, line "
                //   371: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   374: iconst_0       
                //   375: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   378: ldc             ": Invalid script code t: "
                //   380: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   383: aload           10
                //   385: iconst_4       
                //   386: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   389: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   392: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   395: aload           10
                //   397: iconst_4       
                //   398: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   401: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   404: athrow         
                //   405: iload           16
                //   407: iconst_m1      
                //   408: if_icmpne       458
                //   411: new             Ljava/text/ParseException;
                //   414: dup            
                //   415: new             Ljava/lang/StringBuilder;
                //   418: dup            
                //   419: invokespecial   java/lang/StringBuilder.<init>:()V
                //   422: ldc             "ConfusablesWholeScript, line "
                //   424: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   427: iconst_0       
                //   428: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   431: ldc             ": Invalid script code t: "
                //   433: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   436: aload           10
                //   438: iconst_5       
                //   439: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
                //   442: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   445: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   448: aload           10
                //   450: iconst_5       
                //   451: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   454: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   457: athrow         
                //   458: aload           8
                //   460: astore          17
                //   462: aload           10
                //   464: bipush          7
                //   466: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   469: iflt            476
                //   472: aload           9
                //   474: astore          17
                //   476: iconst_2       
                //   477: iconst_2       
                //   478: if_icmpgt       692
                //   481: aload           17
                //   483: iconst_2       
                //   484: invokevirtual   com/ibm/icu/impl/Trie2Writable.get:(I)I
                //   487: istore          19
                //   489: aconst_null    
                //   490: astore          20
                //   492: iload           19
                //   494: ifle            536
                //   497: getstatic       com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder.$assertionsDisabled:Z
                //   500: ifne            521
                //   503: iload           19
                //   505: aload           6
                //   507: invokevirtual   java/util/Vector.size:()I
                //   510: if_icmplt       521
                //   513: new             Ljava/lang/AssertionError;
                //   516: dup            
                //   517: invokespecial   java/lang/AssertionError.<init>:()V
                //   520: athrow         
                //   521: aload           6
                //   523: iload           19
                //   525: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
                //   528: checkcast       Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //   531: astore          20
                //   533: goto            606
                //   536: new             Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //   539: dup            
                //   540: invokespecial   com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.<init>:()V
                //   543: astore          20
                //   545: aload           20
                //   547: iconst_2       
                //   548: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.codePoint:I
                //   551: aload           20
                //   553: aload           17
                //   555: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.trie:Lcom/ibm/icu/impl/Trie2Writable;
                //   558: aload           20
                //   560: new             Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   563: dup            
                //   564: invokespecial   com/ibm/icu/text/SpoofChecker$ScriptSet.<init>:()V
                //   567: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   570: aload           6
                //   572: invokevirtual   java/util/Vector.size:()I
                //   575: istore          19
                //   577: aload           20
                //   579: iload           19
                //   581: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.index:I
                //   584: aload           20
                //   586: iconst_0       
                //   587: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   590: aload           6
                //   592: aload           20
                //   594: invokevirtual   java/util/Vector.addElement:(Ljava/lang/Object;)V
                //   597: aload           17
                //   599: iconst_2       
                //   600: iload           19
                //   602: invokevirtual   com/ibm/icu/impl/Trie2Writable.set:(II)Lcom/ibm/icu/impl/Trie2Writable;
                //   605: pop            
                //   606: aload           20
                //   608: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   611: iload           16
                //   613: invokevirtual   com/ibm/icu/text/SpoofChecker$ScriptSet.Union:(I)V
                //   616: aload           20
                //   618: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   621: iload           15
                //   623: invokevirtual   com/ibm/icu/text/SpoofChecker$ScriptSet.Union:(I)V
                //   626: iconst_2       
                //   627: invokestatic    com/ibm/icu/lang/UScript.getScript:(I)I
                //   630: istore          21
                //   632: iload           21
                //   634: iload           15
                //   636: if_icmpeq       686
                //   639: new             Ljava/text/ParseException;
                //   642: dup            
                //   643: new             Ljava/lang/StringBuilder;
                //   646: dup            
                //   647: invokespecial   java/lang/StringBuilder.<init>:()V
                //   650: ldc             "ConfusablesWholeScript, line "
                //   652: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   655: iconst_0       
                //   656: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   659: ldc             ": Mismatch between source script and code point "
                //   661: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   664: iconst_2       
                //   665: bipush          16
                //   667: invokestatic    java/lang/Integer.toString:(II)Ljava/lang/String;
                //   670: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   673: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   676: aload           10
                //   678: iconst_5       
                //   679: invokevirtual   java/util/regex/Matcher.start:(I)I
                //   682: invokespecial   java/text/ParseException.<init>:(Ljava/lang/String;I)V
                //   685: athrow         
                //   686: iinc            18, 1
                //   689: goto            476
                //   692: goto            97
                //   695: iconst_2       
                //   696: aload           6
                //   698: invokevirtual   java/util/Vector.size:()I
                //   701: if_icmpge       823
                //   704: aload           6
                //   706: iconst_2       
                //   707: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
                //   710: checkcast       Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //   713: astore          12
                //   715: aload           12
                //   717: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.index:I
                //   720: iconst_2       
                //   721: if_icmpeq       727
                //   724: goto            817
                //   727: aload           12
                //   729: iconst_2       
                //   730: iinc            7, 1
                //   733: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   736: iconst_0       
                //   737: aload           6
                //   739: invokevirtual   java/util/Vector.size:()I
                //   742: if_icmpge       817
                //   745: aload           6
                //   747: iconst_0       
                //   748: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
                //   751: checkcast       Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //   754: astore          14
                //   756: aload           12
                //   758: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   761: aload           14
                //   763: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   766: invokevirtual   com/ibm/icu/text/SpoofChecker$ScriptSet.equals:(Lcom/ibm/icu/text/SpoofChecker$ScriptSet;)Z
                //   769: ifeq            811
                //   772: aload           12
                //   774: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   777: aload           14
                //   779: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   782: if_acmpeq       811
                //   785: aload           14
                //   787: aload           12
                //   789: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   792: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //   795: aload           14
                //   797: iconst_2       
                //   798: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.index:I
                //   801: aload           14
                //   803: aload           12
                //   805: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   808: putfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   811: iinc            13, 1
                //   814: goto            736
                //   817: iinc            11, 1
                //   820: goto            695
                //   823: iconst_2       
                //   824: aload           6
                //   826: invokevirtual   java/util/Vector.size:()I
                //   829: if_icmpge       877
                //   832: aload           6
                //   834: iconst_2       
                //   835: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
                //   838: checkcast       Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //   841: astore          12
                //   843: aload           12
                //   845: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   848: iconst_2       
                //   849: if_icmpeq       871
                //   852: aload           12
                //   854: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.trie:Lcom/ibm/icu/impl/Trie2Writable;
                //   857: aload           12
                //   859: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.codePoint:I
                //   862: aload           12
                //   864: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //   867: invokevirtual   com/ibm/icu/impl/Trie2Writable.set:(II)Lcom/ibm/icu/impl/Trie2Writable;
                //   870: pop            
                //   871: iinc            11, 1
                //   874: goto            823
                //   877: new             Lcom/ibm/icu/text/UnicodeSet;
                //   880: dup            
                //   881: invokespecial   com/ibm/icu/text/UnicodeSet.<init>:()V
                //   884: astore          11
                //   886: aload           11
                //   888: sipush          4106
                //   891: iconst_0       
                //   892: invokevirtual   com/ibm/icu/text/UnicodeSet.applyIntPropertyValue:(II)Lcom/ibm/icu/text/UnicodeSet;
                //   895: pop            
                //   896: new             Lcom/ibm/icu/text/UnicodeSet;
                //   899: dup            
                //   900: invokespecial   com/ibm/icu/text/UnicodeSet.<init>:()V
                //   903: astore          12
                //   905: aload           12
                //   907: sipush          4106
                //   910: iconst_1       
                //   911: invokevirtual   com/ibm/icu/text/UnicodeSet.applyIntPropertyValue:(II)Lcom/ibm/icu/text/UnicodeSet;
                //   914: pop            
                //   915: aload           11
                //   917: aload           12
                //   919: invokevirtual   com/ibm/icu/text/UnicodeSet.addAll:(Lcom/ibm/icu/text/UnicodeSet;)Lcom/ibm/icu/text/UnicodeSet;
                //   922: pop            
                //   923: iconst_0       
                //   924: aload           11
                //   926: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeCount:()I
                //   929: if_icmpge       978
                //   932: aload           11
                //   934: iconst_0       
                //   935: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeStart:(I)I
                //   938: istore          14
                //   940: aload           11
                //   942: iconst_0       
                //   943: invokevirtual   com/ibm/icu/text/UnicodeSet.getRangeEnd:(I)I
                //   946: istore          15
                //   948: aload           8
                //   950: iload           14
                //   952: iload           15
                //   954: iconst_1       
                //   955: iconst_1       
                //   956: invokevirtual   com/ibm/icu/impl/Trie2Writable.setRange:(IIIZ)Lcom/ibm/icu/impl/Trie2Writable;
                //   959: pop            
                //   960: aload           9
                //   962: iload           14
                //   964: iload           15
                //   966: iconst_1       
                //   967: iconst_1       
                //   968: invokevirtual   com/ibm/icu/impl/Trie2Writable.setRange:(IIIZ)Lcom/ibm/icu/impl/Trie2Writable;
                //   971: pop            
                //   972: iinc            13, 1
                //   975: goto            923
                //   978: aload           8
                //   980: invokevirtual   com/ibm/icu/impl/Trie2Writable.toTrie2_16:()Lcom/ibm/icu/impl/Trie2_16;
                //   983: aload_1        
                //   984: invokevirtual   com/ibm/icu/impl/Trie2_16.serialize:(Ljava/io/OutputStream;)I
                //   987: pop            
                //   988: aload           9
                //   990: invokevirtual   com/ibm/icu/impl/Trie2Writable.toTrie2_16:()Lcom/ibm/icu/impl/Trie2_16;
                //   993: aload_1        
                //   994: invokevirtual   com/ibm/icu/impl/Trie2_16.serialize:(Ljava/io/OutputStream;)I
                //   997: pop            
                //   998: aload_0        
                //   999: getfield        com/ibm/icu/text/SpoofChecker$SpoofData.fRawData:Lcom/ibm/icu/text/SpoofChecker$SpoofDataHeader;
                //  1002: iconst_2       
                //  1003: putfield        com/ibm/icu/text/SpoofChecker$SpoofDataHeader.fScriptSetsLength:I
                //  1006: iconst_2       
                //  1007: aload           6
                //  1009: invokevirtual   java/util/Vector.size:()I
                //  1012: if_icmpge       1079
                //  1015: aload           6
                //  1017: iconst_2       
                //  1018: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
                //  1021: checkcast       Lcom/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet;
                //  1024: astore          13
                //  1026: aload           13
                //  1028: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //  1031: iconst_2       
                //  1032: if_icmpge       1038
                //  1035: goto            1073
                //  1038: getstatic       com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder.$assertionsDisabled:Z
                //  1041: ifne            1061
                //  1044: iconst_2       
                //  1045: aload           13
                //  1047: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.rindex:I
                //  1050: if_icmpeq       1061
                //  1053: new             Ljava/lang/AssertionError;
                //  1056: dup            
                //  1057: invokespecial   java/lang/AssertionError.<init>:()V
                //  1060: athrow         
                //  1061: aload           13
                //  1063: getfield        com/ibm/icu/text/SpoofChecker$Builder$WSConfusableDataBuilder$BuilderScriptSet.sset:Lcom/ibm/icu/text/SpoofChecker$ScriptSet;
                //  1066: aload_1        
                //  1067: invokevirtual   com/ibm/icu/text/SpoofChecker$ScriptSet.output:(Ljava/io/DataOutputStream;)V
                //  1070: iinc            11, 1
                //  1073: iinc            12, 1
                //  1076: goto            1006
                //  1079: return         
                //    Exceptions:
                //  throws java.text.ParseException
                //  throws java.io.IOException
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            static {
                $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
                WSConfusableDataBuilder.parseExp = "(?m)^([ \\t]*(?:#.*?)?)$|^(?:\\s*([0-9A-F]{4,})(?:..([0-9A-F]{4,}))?\\s*;\\s*([A-Za-z]+)\\s*;\\s*([A-Za-z]+)\\s*;\\s*(?:(A)|(L))[ \\t]*(?:#.*?)?)$|^(.*?)$";
            }
            
            private static class BuilderScriptSet
            {
                int codePoint;
                Trie2Writable trie;
                ScriptSet sset;
                int index;
                int rindex;
                
                BuilderScriptSet() {
                    this.codePoint = -1;
                    this.trie = null;
                    this.sset = null;
                    this.index = 0;
                    this.rindex = 0;
                }
            }
        }
    }
}
