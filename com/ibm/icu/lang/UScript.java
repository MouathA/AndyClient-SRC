package com.ibm.icu.lang;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public final class UScript
{
    public static final int INVALID_CODE = -1;
    public static final int COMMON = 0;
    public static final int INHERITED = 1;
    public static final int ARABIC = 2;
    public static final int ARMENIAN = 3;
    public static final int BENGALI = 4;
    public static final int BOPOMOFO = 5;
    public static final int CHEROKEE = 6;
    public static final int COPTIC = 7;
    public static final int CYRILLIC = 8;
    public static final int DESERET = 9;
    public static final int DEVANAGARI = 10;
    public static final int ETHIOPIC = 11;
    public static final int GEORGIAN = 12;
    public static final int GOTHIC = 13;
    public static final int GREEK = 14;
    public static final int GUJARATI = 15;
    public static final int GURMUKHI = 16;
    public static final int HAN = 17;
    public static final int HANGUL = 18;
    public static final int HEBREW = 19;
    public static final int HIRAGANA = 20;
    public static final int KANNADA = 21;
    public static final int KATAKANA = 22;
    public static final int KHMER = 23;
    public static final int LAO = 24;
    public static final int LATIN = 25;
    public static final int MALAYALAM = 26;
    public static final int MONGOLIAN = 27;
    public static final int MYANMAR = 28;
    public static final int OGHAM = 29;
    public static final int OLD_ITALIC = 30;
    public static final int ORIYA = 31;
    public static final int RUNIC = 32;
    public static final int SINHALA = 33;
    public static final int SYRIAC = 34;
    public static final int TAMIL = 35;
    public static final int TELUGU = 36;
    public static final int THAANA = 37;
    public static final int THAI = 38;
    public static final int TIBETAN = 39;
    public static final int CANADIAN_ABORIGINAL = 40;
    public static final int UCAS = 40;
    public static final int YI = 41;
    public static final int TAGALOG = 42;
    public static final int HANUNOO = 43;
    public static final int BUHID = 44;
    public static final int TAGBANWA = 45;
    public static final int BRAILLE = 46;
    public static final int CYPRIOT = 47;
    public static final int LIMBU = 48;
    public static final int LINEAR_B = 49;
    public static final int OSMANYA = 50;
    public static final int SHAVIAN = 51;
    public static final int TAI_LE = 52;
    public static final int UGARITIC = 53;
    public static final int KATAKANA_OR_HIRAGANA = 54;
    public static final int BUGINESE = 55;
    public static final int GLAGOLITIC = 56;
    public static final int KHAROSHTHI = 57;
    public static final int SYLOTI_NAGRI = 58;
    public static final int NEW_TAI_LUE = 59;
    public static final int TIFINAGH = 60;
    public static final int OLD_PERSIAN = 61;
    public static final int BALINESE = 62;
    public static final int BATAK = 63;
    public static final int BLISSYMBOLS = 64;
    public static final int BRAHMI = 65;
    public static final int CHAM = 66;
    public static final int CIRTH = 67;
    public static final int OLD_CHURCH_SLAVONIC_CYRILLIC = 68;
    public static final int DEMOTIC_EGYPTIAN = 69;
    public static final int HIERATIC_EGYPTIAN = 70;
    public static final int EGYPTIAN_HIEROGLYPHS = 71;
    public static final int KHUTSURI = 72;
    public static final int SIMPLIFIED_HAN = 73;
    public static final int TRADITIONAL_HAN = 74;
    public static final int PAHAWH_HMONG = 75;
    public static final int OLD_HUNGARIAN = 76;
    public static final int HARAPPAN_INDUS = 77;
    public static final int JAVANESE = 78;
    public static final int KAYAH_LI = 79;
    public static final int LATIN_FRAKTUR = 80;
    public static final int LATIN_GAELIC = 81;
    public static final int LEPCHA = 82;
    public static final int LINEAR_A = 83;
    public static final int MANDAIC = 84;
    public static final int MANDAEAN = 84;
    public static final int MAYAN_HIEROGLYPHS = 85;
    public static final int MEROITIC_HIEROGLYPHS = 86;
    public static final int MEROITIC = 86;
    public static final int NKO = 87;
    public static final int ORKHON = 88;
    public static final int OLD_PERMIC = 89;
    public static final int PHAGS_PA = 90;
    public static final int PHOENICIAN = 91;
    public static final int PHONETIC_POLLARD = 92;
    public static final int RONGORONGO = 93;
    public static final int SARATI = 94;
    public static final int ESTRANGELO_SYRIAC = 95;
    public static final int WESTERN_SYRIAC = 96;
    public static final int EASTERN_SYRIAC = 97;
    public static final int TENGWAR = 98;
    public static final int VAI = 99;
    public static final int VISIBLE_SPEECH = 100;
    public static final int CUNEIFORM = 101;
    public static final int UNWRITTEN_LANGUAGES = 102;
    public static final int UNKNOWN = 103;
    public static final int CARIAN = 104;
    public static final int JAPANESE = 105;
    public static final int LANNA = 106;
    public static final int LYCIAN = 107;
    public static final int LYDIAN = 108;
    public static final int OL_CHIKI = 109;
    public static final int REJANG = 110;
    public static final int SAURASHTRA = 111;
    public static final int SIGN_WRITING = 112;
    public static final int SUNDANESE = 113;
    public static final int MOON = 114;
    public static final int MEITEI_MAYEK = 115;
    public static final int IMPERIAL_ARAMAIC = 116;
    public static final int AVESTAN = 117;
    public static final int CHAKMA = 118;
    public static final int KOREAN = 119;
    public static final int KAITHI = 120;
    public static final int MANICHAEAN = 121;
    public static final int INSCRIPTIONAL_PAHLAVI = 122;
    public static final int PSALTER_PAHLAVI = 123;
    public static final int BOOK_PAHLAVI = 124;
    public static final int INSCRIPTIONAL_PARTHIAN = 125;
    public static final int SAMARITAN = 126;
    public static final int TAI_VIET = 127;
    public static final int MATHEMATICAL_NOTATION = 128;
    public static final int SYMBOLS = 129;
    public static final int BAMUM = 130;
    public static final int LISU = 131;
    public static final int NAKHI_GEBA = 132;
    public static final int OLD_SOUTH_ARABIAN = 133;
    public static final int BASSA_VAH = 134;
    public static final int DUPLOYAN_SHORTAND = 135;
    public static final int ELBASAN = 136;
    public static final int GRANTHA = 137;
    public static final int KPELLE = 138;
    public static final int LOMA = 139;
    public static final int MENDE = 140;
    public static final int MEROITIC_CURSIVE = 141;
    public static final int OLD_NORTH_ARABIAN = 142;
    public static final int NABATAEAN = 143;
    public static final int PALMYRENE = 144;
    public static final int SINDHI = 145;
    public static final int WARANG_CITI = 146;
    public static final int AFAKA = 147;
    public static final int JURCHEN = 148;
    public static final int MRO = 149;
    public static final int NUSHU = 150;
    public static final int SHARADA = 151;
    public static final int SORA_SOMPENG = 152;
    public static final int TAKRI = 153;
    public static final int TANGUT = 154;
    public static final int WOLEAI = 155;
    public static final int ANATOLIAN_HIEROGLYPHS = 156;
    public static final int KHOJKI = 157;
    public static final int TIRHUTA = 158;
    public static final int CODE_LIMIT = 159;
    private static final String kLocaleScript = "LocaleScript";
    private static final ScriptUsage[] usageValues;
    
    private static int[] findCodeFromLocale(final ULocale uLocale) {
        final ICUResourceBundle icuResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale);
        final ICUResourceBundle icuResourceBundle2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", uLocale);
        if (icuResourceBundle2.getLoadingStatus() == 3 && !uLocale.equals(ULocale.getDefault())) {
            return null;
        }
        final UResourceBundle value = icuResourceBundle2.get("LocaleScript");
        final int[] array = new int[value.getSize()];
        while (0 < array.length) {
            final int propertyValueEnum = UCharacter.getPropertyValueEnum(4106, value.getString(0));
            final int[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = propertyValueEnum;
            int n3 = 0;
            ++n3;
        }
        if (0 < array.length) {
            throw new IllegalStateException("bad locale data, listed " + array.length + " scripts but found only " + 0);
        }
        return array;
    }
    
    public static final int[] getCode(final Locale locale) {
        return findCodeFromLocale(ULocale.forLocale(locale));
    }
    
    public static final int[] getCode(final ULocale uLocale) {
        return findCodeFromLocale(uLocale);
    }
    
    public static final int[] getCode(final String s) {
        return new int[] { UCharacter.getPropertyValueEnum(4106, s) };
    }
    
    @Deprecated
    public static final int getCodeFromName(final String s) {
        return UCharacter.getPropertyValueEnum(4106, s);
    }
    
    public static final int getScript(final int n) {
        if (!(n >= 0 & n <= 1114111)) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        final int n2 = UCharacterProperty.INSTANCE.getAdditional(n, 0) & 0xC000FF;
        if (n2 < 4194304) {
            return n2;
        }
        if (n2 < 8388608) {
            return 0;
        }
        if (n2 < 12582912) {
            return 1;
        }
        return UCharacterProperty.INSTANCE.m_scriptExtensions_[n2 & 0xFF];
    }
    
    public static final boolean hasScript(final int n, final int i) {
        final int n2 = UCharacterProperty.INSTANCE.getAdditional(n, 0) & 0xC000FF;
        if (n2 < 4194304) {
            return i == n2;
        }
        final char[] scriptExtensions_ = UCharacterProperty.INSTANCE.m_scriptExtensions_;
        int n3 = n2 & 0xFF;
        if (n2 >= 12582912) {
            n3 = scriptExtensions_[n3 + 1];
        }
        if (i > 32767) {
            return false;
        }
        while (i > scriptExtensions_[n3]) {
            ++n3;
        }
        return i == (scriptExtensions_[n3] & '\u7fff');
    }
    
    public static final int getScriptExtensions(final int n, final BitSet set) {
        set.clear();
        final int n2 = UCharacterProperty.INSTANCE.getAdditional(n, 0) & 0xC000FF;
        if (n2 < 4194304) {
            set.set(n2);
            return n2;
        }
        final char[] scriptExtensions_ = UCharacterProperty.INSTANCE.m_scriptExtensions_;
        int n3 = n2 & 0xFF;
        if (n2 >= 12582912) {
            n3 = scriptExtensions_[n3 + 1];
        }
        char c;
        do {
            c = scriptExtensions_[n3++];
            set.set(c & '\u7fff');
            int n4 = 0;
            ++n4;
        } while (c < '\u8000');
        return 0;
    }
    
    public static final String getName(final int n) {
        return UCharacter.getPropertyValueName(4106, n, 1);
    }
    
    public static final String getShortName(final int n) {
        return UCharacter.getPropertyValueName(4106, n, 0);
    }
    
    public static final String getSampleString(final int n) {
        final int n2 = ScriptMetadata.access$000(n) & 0x1FFFFF;
        if (n2 != 0) {
            return new StringBuilder().appendCodePoint(n2).toString();
        }
        return "";
    }
    
    public static final ScriptUsage getUsage(final int n) {
        return UScript.usageValues[ScriptMetadata.access$000(n) >> 21 & 0x7];
    }
    
    public static final boolean isRightToLeft(final int n) {
        return (ScriptMetadata.access$000(n) & 0x1000000) != 0x0;
    }
    
    public static final boolean breaksBetweenLetters(final int n) {
        return (ScriptMetadata.access$000(n) & 0x2000000) != 0x0;
    }
    
    public static final boolean isCased(final int n) {
        return (ScriptMetadata.access$000(n) & 0x4000000) != 0x0;
    }
    
    private UScript() {
    }
    
    static {
        usageValues = ScriptUsage.values();
    }
    
    public enum ScriptUsage
    {
        NOT_ENCODED("NOT_ENCODED", 0), 
        UNKNOWN("UNKNOWN", 1), 
        EXCLUDED("EXCLUDED", 2), 
        LIMITED_USE("LIMITED_USE", 3), 
        ASPIRATIONAL("ASPIRATIONAL", 4), 
        RECOMMENDED("RECOMMENDED", 5);
        
        private static final ScriptUsage[] $VALUES;
        
        private ScriptUsage(final String s, final int n) {
        }
        
        static {
            $VALUES = new ScriptUsage[] { ScriptUsage.NOT_ENCODED, ScriptUsage.UNKNOWN, ScriptUsage.EXCLUDED, ScriptUsage.LIMITED_USE, ScriptUsage.ASPIRATIONAL, ScriptUsage.RECOMMENDED };
        }
    }
    
    private static final class ScriptMetadata
    {
        private static final int UNKNOWN = 2097152;
        private static final int EXCLUSION = 4194304;
        private static final int LIMITED_USE = 6291456;
        private static final int ASPIRATIONAL = 8388608;
        private static final int RECOMMENDED = 10485760;
        private static final int RTL = 16777216;
        private static final int LB_LETTERS = 33554432;
        private static final int CASED = 67108864;
        private static final int[] SCRIPT_PROPS;
        
        private static final int getScriptProps(final int n) {
            if (0 <= n && n < ScriptMetadata.SCRIPT_PROPS.length) {
                return ScriptMetadata.SCRIPT_PROPS[n];
            }
            return 0;
        }
        
        static int access$000(final int n) {
            return getScriptProps(n);
        }
        
        static {
            SCRIPT_PROPS = new int[] { 2097216, 2097928, 27264552, 77595953, 10488213, 44052741, 6296516, 71304162, 77595695, 71369748, 10488069, 10490528, 10490067, 4260656, 77595561, 10488469, 10488341, 44063575, 10529792, 27264464, 44052555, 10488981, 44052651, 44046208, 44043941, 77594700, 10489109, 8394790, 44044288, 4200079, 4260608, 10488597, 4200096, 10489221, 23070480, 10488725, 10488853, 27264908, 44043799, 10489664, 8393920, 41984648, 4200195, 4200227, 4200259, 4200291, 2107392, 21039104, 6297856, 4259840, 4260992, 4260944, 39852368, 4260736, 0, 4200960, 71314432, 21039616, 6334464, 39852416, 8400176, 4260768, 39852805, 6298560, 0, 4263941, 6334976, 0, 0, 0, 0, 4272467, 0, 44063575, 44063575, 0, 0, 0, 39889284, 6334730, 0, 0, 6298624, 0, 23070784, 0, 21039488, 23070666, 21040128, 0, 4237376, 21039360, 8482560, 0, 0, 0, 0, 0, 0, 6333769, 0, 4268032, 0, 2162128, 4260512, 44052555, 39852576, 4260480, 21039392, 6298714, 4237616, 6334594, 0, 6298499, 0, 6335424, 21039168, 21039872, 6361347, 10529792, 4264067, 0, 21039968, 0, 0, 21039936, 20973568, 39889536, 0, 0, 6334112, 6333648, 0, 21039712, 0, 0, 0, 0, 0, 0, 0, 21039520, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4264323, 4264144, 4265600, 0, 0, 0, 0, 0 };
        }
    }
}
