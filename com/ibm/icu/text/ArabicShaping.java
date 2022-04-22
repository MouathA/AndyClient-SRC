package com.ibm.icu.text;

import com.ibm.icu.impl.*;

public final class ArabicShaping
{
    private final int options;
    private boolean isLogical;
    private boolean spacesRelativeToTextBeginEnd;
    private char tailChar;
    public static final int SEEN_TWOCELL_NEAR = 2097152;
    public static final int SEEN_MASK = 7340032;
    public static final int YEHHAMZA_TWOCELL_NEAR = 16777216;
    public static final int YEHHAMZA_MASK = 58720256;
    public static final int TASHKEEL_BEGIN = 262144;
    public static final int TASHKEEL_END = 393216;
    public static final int TASHKEEL_RESIZE = 524288;
    public static final int TASHKEEL_REPLACE_BY_TATWEEL = 786432;
    public static final int TASHKEEL_MASK = 917504;
    public static final int SPACES_RELATIVE_TO_TEXT_BEGIN_END = 67108864;
    public static final int SPACES_RELATIVE_TO_TEXT_MASK = 67108864;
    public static final int SHAPE_TAIL_NEW_UNICODE = 134217728;
    public static final int SHAPE_TAIL_TYPE_MASK = 134217728;
    public static final int LENGTH_GROW_SHRINK = 0;
    public static final int LAMALEF_RESIZE = 0;
    public static final int LENGTH_FIXED_SPACES_NEAR = 1;
    public static final int LAMALEF_NEAR = 1;
    public static final int LENGTH_FIXED_SPACES_AT_END = 2;
    public static final int LAMALEF_END = 2;
    public static final int LENGTH_FIXED_SPACES_AT_BEGINNING = 3;
    public static final int LAMALEF_BEGIN = 3;
    public static final int LAMALEF_AUTO = 65536;
    public static final int LENGTH_MASK = 65539;
    public static final int LAMALEF_MASK = 65539;
    public static final int TEXT_DIRECTION_LOGICAL = 0;
    public static final int TEXT_DIRECTION_VISUAL_RTL = 0;
    public static final int TEXT_DIRECTION_VISUAL_LTR = 4;
    public static final int TEXT_DIRECTION_MASK = 4;
    public static final int LETTERS_NOOP = 0;
    public static final int LETTERS_SHAPE = 8;
    public static final int LETTERS_UNSHAPE = 16;
    public static final int LETTERS_SHAPE_TASHKEEL_ISOLATED = 24;
    public static final int LETTERS_MASK = 24;
    public static final int DIGITS_NOOP = 0;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_MASK = 224;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final int DIGIT_TYPE_MASK = 256;
    private static final char HAMZAFE_CHAR = '\ufe80';
    private static final char HAMZA06_CHAR = '\u0621';
    private static final char YEH_HAMZA_CHAR = '\u0626';
    private static final char YEH_HAMZAFE_CHAR = '\ufe89';
    private static final char LAMALEF_SPACE_SUB = '\uffff';
    private static final char TASHKEEL_SPACE_SUB = '\ufffe';
    private static final char LAM_CHAR = '\u0644';
    private static final char SPACE_CHAR = ' ';
    private static final char SHADDA_CHAR = '\ufe7c';
    private static final char SHADDA06_CHAR = '\u0651';
    private static final char TATWEEL_CHAR = '\u0640';
    private static final char SHADDA_TATWEEL_CHAR = '\ufe7d';
    private static final char NEW_TAIL_CHAR = '\ufe73';
    private static final char OLD_TAIL_CHAR = '\u200b';
    private static final int SHAPE_MODE = 0;
    private static final int DESHAPE_MODE = 1;
    private static final int IRRELEVANT = 4;
    private static final int LAMTYPE = 16;
    private static final int ALEFTYPE = 32;
    private static final int LINKR = 1;
    private static final int LINKL = 2;
    private static final int LINK_MASK = 3;
    private static final int[] irrelevantPos;
    private static final int[] tailFamilyIsolatedFinal;
    private static final int[] tashkeelMedial;
    private static final char[] yehHamzaToYeh;
    private static final char[] convertNormalizedLamAlef;
    private static final int[] araLink;
    private static final int[] presLink;
    private static int[] convertFEto06;
    private static final int[][][] shapeTable;
    
    public int shape(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4) throws ArabicShapingException {
        if (array == null) {
            throw new IllegalArgumentException("source can not be null");
        }
        if (n < 0 || n2 < 0 || n + n2 > array.length) {
            throw new IllegalArgumentException("bad source start (" + n + ") or length (" + n2 + ") for buffer of length " + array.length);
        }
        if (array2 == null && n4 != 0) {
            throw new IllegalArgumentException("null dest requires destSize == 0");
        }
        if (n4 != 0 && (n3 < 0 || n4 < 0 || n3 + n4 > array2.length)) {
            throw new IllegalArgumentException("bad dest start (" + n3 + ") or size (" + n4 + ") for buffer of length " + array2.length);
        }
        if ((this.options & 0xE0000) > 0 && (this.options & 0xE0000) != 0x40000 && (this.options & 0xE0000) != 0x60000 && (this.options & 0xE0000) != 0x80000 && (this.options & 0xE0000) != 0xC0000) {
            throw new IllegalArgumentException("Wrong Tashkeel argument");
        }
        if ((this.options & 0x10003) > 0 && (this.options & 0x10003) != 0x3 && (this.options & 0x10003) != 0x2 && (this.options & 0x10003) != 0x0 && (this.options & 0x10003) != 0x10000 && (this.options & 0x10003) != 0x1) {
            throw new IllegalArgumentException("Wrong Lam Alef argument");
        }
        if ((this.options & 0xE0000) > 0 && (this.options & 0x18) == 0x10) {
            throw new IllegalArgumentException("Tashkeel replacement should not be enabled in deshaping mode ");
        }
        return this.internalShape(array, n, n2, array2, n3, n4);
    }
    
    public void shape(final char[] array, final int n, final int n2) throws ArabicShapingException {
        if ((this.options & 0x10003) == 0x0) {
            throw new ArabicShapingException("Cannot shape in place with length option resize.");
        }
        this.shape(array, n, n2, array, n, n2);
    }
    
    public String shape(final String s) throws ArabicShapingException {
        char[] charArray;
        final char[] array = charArray = s.toCharArray();
        if ((this.options & 0x10003) == 0x0 && (this.options & 0x18) == 0x10) {
            charArray = new char[array.length * 2];
        }
        return new String(charArray, 0, this.shape(array, 0, array.length, charArray, 0, charArray.length));
    }
    
    public ArabicShaping(final int options) {
        this.options = options;
        if ((options & 0xE0) > 128) {
            throw new IllegalArgumentException("bad DIGITS options");
        }
        this.isLogical = ((options & 0x4) == 0x0);
        this.spacesRelativeToTextBeginEnd = ((options & 0x4000000) == 0x4000000);
        if ((options & 0x8000000) == 0x8000000) {
            this.tailChar = '\ufe73';
        }
        else {
            this.tailChar = '\u200b';
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o.getClass() == ArabicShaping.class && this.options == ((ArabicShaping)o).options;
    }
    
    @Override
    public int hashCode() {
        return this.options;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append('[');
        switch (this.options & 0x10003) {
            case 0: {
                sb.append("LamAlef resize");
                break;
            }
            case 1: {
                sb.append("LamAlef spaces at near");
                break;
            }
            case 3: {
                sb.append("LamAlef spaces at begin");
                break;
            }
            case 2: {
                sb.append("LamAlef spaces at end");
                break;
            }
            case 65536: {
                sb.append("lamAlef auto");
                break;
            }
        }
        switch (this.options & 0x4) {
            case 0: {
                sb.append(", logical");
                break;
            }
            case 4: {
                sb.append(", visual");
                break;
            }
        }
        switch (this.options & 0x18) {
            case 0: {
                sb.append(", no letter shaping");
                break;
            }
            case 8: {
                sb.append(", shape letters");
                break;
            }
            case 24: {
                sb.append(", shape letters tashkeel isolated");
                break;
            }
            case 16: {
                sb.append(", unshape letters");
                break;
            }
        }
        switch (this.options & 0x700000) {
            case 2097152: {
                sb.append(", Seen at near");
                break;
            }
        }
        switch (this.options & 0x3800000) {
            case 16777216: {
                sb.append(", Yeh Hamza at near");
                break;
            }
        }
        switch (this.options & 0xE0000) {
            case 262144: {
                sb.append(", Tashkeel at begin");
                break;
            }
            case 393216: {
                sb.append(", Tashkeel at end");
                break;
            }
            case 786432: {
                sb.append(", Tashkeel replace with tatweel");
                break;
            }
            case 524288: {
                sb.append(", Tashkeel resize");
                break;
            }
        }
        switch (this.options & 0xE0) {
            case 0: {
                sb.append(", no digit shaping");
                break;
            }
            case 32: {
                sb.append(", shape digits to AN");
                break;
            }
            case 64: {
                sb.append(", shape digits to EN");
                break;
            }
            case 96: {
                sb.append(", shape digits to AN contextually: default EN");
                break;
            }
            case 128: {
                sb.append(", shape digits to AN contextually: default AL");
                break;
            }
        }
        switch (this.options & 0x100) {
            case 0: {
                sb.append(", standard Arabic-Indic digits");
                break;
            }
            case 256: {
                sb.append(", extended Arabic-Indic digits");
                break;
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    private void shapeToArabicDigitsWithContext(final char[] array, final int n, final int n2, final char c, boolean b) {
        final UBiDiProps instance = UBiDiProps.INSTANCE;
        final char c2 = (char)(c - '0');
        int n3 = n + n2;
        while (--n3 >= n) {
            final char c3 = array[n3];
            switch (instance.getClass(c3)) {
                case 0:
                case 1: {
                    b = false;
                    continue;
                }
                case 13: {
                    b = true;
                    continue;
                }
                case 2: {
                    if (b && c3 <= '9') {
                        array[n3] = (char)(c3 + c2);
                        continue;
                    }
                    continue;
                }
            }
        }
    }
    
    private static void invertBuffer(final char[] array, final int n, final int n2) {
        for (int i = n, n3 = n + n2 - 1; i < n3; ++i, --n3) {
            final char c = array[i];
            array[i] = array[n3];
            array[n3] = c;
        }
    }
    
    private static char changeLamAlef(final char c) {
        switch (c) {
            case '\u0622': {
                return '\u065c';
            }
            case '\u0623': {
                return '\u065d';
            }
            case '\u0625': {
                return '\u065e';
            }
            case '\u0627': {
                return '\u065f';
            }
            default: {
                return '\0';
            }
        }
    }
    
    private static int specialChar(final char c) {
        if ((c > '\u0621' && c < '\u0626') || c == '\u0627' || (c > '\u062e' && c < '\u0633') || (c > '\u0647' && c < '\u064a') || c == '\u0629') {
            return 1;
        }
        if (c >= '\u064b' && c <= '\u0652') {
            return 2;
        }
        if ((c >= '\u0653' && c <= '\u0655') || c == '\u0670' || (c >= '\ufe70' && c <= '\ufe7f')) {
            return 3;
        }
        return 0;
    }
    
    private static int getLink(final char c) {
        if (c >= '\u0622' && c <= '\u06d3') {
            return ArabicShaping.araLink[c - '\u0622'];
        }
        if (c == '\u200d') {
            return 3;
        }
        if (c >= '\u206d' && c <= '\u206f') {
            return 4;
        }
        if (c >= '\ufe70' && c <= '\ufefc') {
            return ArabicShaping.presLink[c - '\ufe70'];
        }
        return 0;
    }
    
    private static int countSpacesLeft(final char[] array, final int n, final int n2) {
        for (int i = n; i < n + n2; ++i) {
            if (array[i] != ' ') {
                return i - n;
            }
        }
        return n2;
    }
    
    private static int countSpacesRight(final char[] array, final int n, final int n2) {
        int n3 = n + n2;
        while (--n3 >= n) {
            if (array[n3] != ' ') {
                return n + n2 - 1 - n3;
            }
        }
        return n2;
    }
    
    private static boolean isTashkeelChar(final char c) {
        return c >= '\u064b' && c <= '\u0652';
    }
    
    private static int isSeenTailFamilyChar(final char c) {
        if (c >= '\ufeb1' && c < '\ufebf') {
            return ArabicShaping.tailFamilyIsolatedFinal[c - '\ufeb1'];
        }
        return 0;
    }
    
    private static int isSeenFamilyChar(final char c) {
        if (c >= '\u0633' && c <= '\u0636') {
            return 1;
        }
        return 0;
    }
    
    private static boolean isTailChar(final char c) {
        return c == '\u200b' || c == '\ufe73';
    }
    
    private static boolean isAlefMaksouraChar(final char c) {
        return c == '\ufeef' || c == '\ufef0' || c == '\u0649';
    }
    
    private static boolean isYehHamzaChar(final char c) {
        return c == '\ufe89' || c == '\ufe8a';
    }
    
    private static boolean isTashkeelCharFE(final char c) {
        return c != '\ufe75' && c >= '\ufe70' && c <= '\ufe7f';
    }
    
    private static int isTashkeelOnTatweelChar(final char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75' && c != '\ufe7d') {
            return ArabicShaping.tashkeelMedial[c - '\ufe70'];
        }
        if ((c >= '\ufcf2' && c <= '\ufcf4') || c == '\ufe7d') {
            return 2;
        }
        return 0;
    }
    
    private static int isIsolatedTashkeelChar(final char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75') {
            return 1 - ArabicShaping.tashkeelMedial[c - '\ufe70'];
        }
        if (c >= '\ufc5e' && c <= '\ufc63') {
            return 1;
        }
        return 0;
    }
    
    private static boolean isAlefChar(final char c) {
        return c == '\u0622' || c == '\u0623' || c == '\u0625' || c == '\u0627';
    }
    
    private static boolean isLamAlefChar(final char c) {
        return c >= '\ufef5' && c <= '\ufefc';
    }
    
    private static boolean isNormalizedLamAlefChar(final char c) {
        return c >= '\u065c' && c <= '\u065f';
    }
    
    private int calculateSize(final char[] array, final int n, final int n2) {
        int n3 = n2;
        switch (this.options & 0x18) {
            case 8:
            case 24: {
                if (this.isLogical) {
                    for (int i = n; i < n + n2 - 1; ++i) {
                        if ((array[i] == '\u0644' && isAlefChar(array[i + 1])) || isTashkeelCharFE(array[i])) {
                            --n3;
                        }
                    }
                    break;
                }
                for (int j = n + 1; j < n + n2; ++j) {
                    if ((array[j] == '\u0644' && isAlefChar(array[j - 1])) || isTashkeelCharFE(array[j])) {
                        --n3;
                    }
                }
                break;
            }
            case 16: {
                for (int k = n; k < n + n2; ++k) {
                    if (isLamAlefChar(array[k])) {
                        ++n3;
                    }
                }
                break;
            }
        }
        return n3;
    }
    
    private static int countSpaceSub(final char[] array, final int n, final char c) {
        int i = 0;
        int n2 = 0;
        while (i < n) {
            if (array[i] == c) {
                ++n2;
            }
            ++i;
        }
        return n2;
    }
    
    private static void shiftArray(final char[] array, final int n, final int n2, final char c) {
        int n3 = n2;
        int n4 = n2;
        while (--n4 >= n) {
            final char c2 = array[n4];
            if (c2 != c && --n3 != n4) {
                array[n3] = c2;
            }
        }
    }
    
    private static int flipArray(final char[] array, final int n, final int n2, int n3) {
        if (n3 > n) {
            int i;
            for (i = n3, n3 = n; i < n2; array[n3++] = array[i++]) {}
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    private static int handleTashkeelWithTatweel(final char[] array, final int n) {
        for (int i = 0; i < n; ++i) {
            if (isTashkeelOnTatweelChar(array[i]) == 1) {
                array[i] = '\u0640';
            }
            else if (isTashkeelOnTatweelChar(array[i]) == 2) {
                array[i] = '\ufe7d';
            }
            else if (isIsolatedTashkeelChar(array[i]) == 1 && array[i] != '\ufe7c') {
                array[i] = ' ';
            }
        }
        return n;
    }
    
    private int handleGeneratedSpaces(final char[] array, final int n, int n2) {
        int n3 = this.options & 0x10003;
        int n4 = this.options & 0xE0000;
        boolean b = false;
        boolean b2 = false;
        if (!this.isLogical & !this.spacesRelativeToTextBeginEnd) {
            switch (n3) {
                case 3: {
                    n3 = 2;
                    break;
                }
                case 2: {
                    n3 = 3;
                    break;
                }
            }
            switch (n4) {
                case 262144: {
                    n4 = 393216;
                    break;
                }
                case 393216: {
                    n4 = 262144;
                    break;
                }
            }
        }
        if (n3 == 1) {
            for (int i = n; i < i + n2; ++i) {
                if (array[i] == '\uffff') {
                    array[i] = ' ';
                }
            }
        }
        else {
            final int n5 = n + n2;
            int j = countSpaceSub(array, n2, '\uffff');
            int k = countSpaceSub(array, n2, '\ufffe');
            if (n3 == 2) {
                b = true;
            }
            if (n4 == 393216) {
                b2 = true;
            }
            if (b && n3 == 2) {
                shiftArray(array, n, n5, '\uffff');
                while (j > n) {
                    array[--j] = ' ';
                }
            }
            if (b2 && n4 == 393216) {
                shiftArray(array, n, n5, '\ufffe');
                while (k > n) {
                    array[--k] = ' ';
                }
            }
            boolean b3 = false;
            boolean b4 = false;
            if (n3 == 0) {
                b3 = true;
            }
            if (n4 == 524288) {
                b4 = true;
            }
            if (b3 && n3 == 0) {
                shiftArray(array, n, n5, '\uffff');
                j = flipArray(array, n, n5, j);
                n2 = j - n;
            }
            if (b4 && n4 == 524288) {
                shiftArray(array, n, n5, '\ufffe');
                k = flipArray(array, n, n5, k);
                n2 = k - n;
            }
            boolean b5 = false;
            boolean b6 = false;
            if (n3 == 3 || n3 == 65536) {
                b5 = true;
            }
            if (n4 == 262144) {
                b6 = true;
            }
            if (b5 && (n3 == 3 || n3 == 65536)) {
                shiftArray(array, n, n5, '\uffff');
                for (int l = flipArray(array, n, n5, j); l < n5; array[l++] = ' ') {}
            }
            if (b6 && n4 == 262144) {
                shiftArray(array, n, n5, '\ufffe');
                for (int flipArray = flipArray(array, n, n5, k); flipArray < n5; array[flipArray++] = ' ') {}
            }
        }
        return n2;
    }
    
    private boolean expandCompositCharAtBegin(final char[] array, final int n, final int n2, final int n3) {
        final boolean b = false;
        if (n3 > countSpacesRight(array, n, n2)) {
            return true;
        }
        int n4 = n + n2 - n3;
        int n5 = n + n2;
        while (--n4 >= n) {
            final char c = array[n4];
            if (isNormalizedLamAlefChar(c)) {
                array[--n5] = '\u0644';
                array[--n5] = ArabicShaping.convertNormalizedLamAlef[c - '\u065c'];
            }
            else {
                array[--n5] = c;
            }
        }
        return b;
    }
    
    private boolean expandCompositCharAtEnd(final char[] array, final int n, final int n2, final int n3) {
        final boolean b = false;
        if (n3 > countSpacesLeft(array, n, n2)) {
            return true;
        }
        int i = n + n3;
        int n4 = n;
        while (i < n + n2) {
            final char c = array[i];
            if (isNormalizedLamAlefChar(c)) {
                array[n4++] = ArabicShaping.convertNormalizedLamAlef[c - '\u065c'];
                array[n4++] = '\u0644';
            }
            else {
                array[n4++] = c;
            }
            ++i;
        }
        return b;
    }
    
    private boolean expandCompositCharAtNear(final char[] array, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (isNormalizedLamAlefChar(array[n])) {
            return true;
        }
        int n6 = n + n2;
        while (--n6 >= n) {
            final char c = array[n6];
            if (n5 == 1 && isNormalizedLamAlefChar(c)) {
                if (n6 <= n || array[n6 - 1] != ' ') {
                    return true;
                }
                array[n6] = '\u0644';
                array[--n6] = ArabicShaping.convertNormalizedLamAlef[c - '\u065c'];
            }
            else if (n4 == 1 && isSeenTailFamilyChar(c) == 1) {
                if (n6 <= n || array[n6 - 1] != ' ') {
                    return true;
                }
                array[n6 - 1] = this.tailChar;
            }
            else {
                if (n3 != 1 || !isYehHamzaChar(c)) {
                    continue;
                }
                if (n6 <= n || array[n6 - 1] != ' ') {
                    return true;
                }
                array[n6] = ArabicShaping.yehHamzaToYeh[c - '\ufe89'];
                array[n6 - 1] = '\ufe80';
            }
        }
        return false;
    }
    
    private int expandCompositChar(final char[] array, final int n, int n2, final int n3, final int n4) throws ArabicShapingException {
        int n5 = this.options & 0x10003;
        final int n6 = this.options & 0x700000;
        final int n7 = this.options & 0x3800000;
        if (!this.isLogical && !this.spacesRelativeToTextBeginEnd) {
            switch (n5) {
                case 3: {
                    n5 = 2;
                    break;
                }
                case 2: {
                    n5 = 3;
                    break;
                }
            }
        }
        if (n4 == 1) {
            if (n5 == 65536) {
                if (this.isLogical) {
                    boolean b = this.expandCompositCharAtEnd(array, n, n2, n3);
                    if (b) {
                        b = this.expandCompositCharAtBegin(array, n, n2, n3);
                    }
                    if (b) {
                        b = this.expandCompositCharAtNear(array, n, n2, 0, 0, 1);
                    }
                    if (b) {
                        throw new ArabicShapingException("No spacefor lamalef");
                    }
                }
                else {
                    boolean b2 = this.expandCompositCharAtBegin(array, n, n2, n3);
                    if (b2) {
                        b2 = this.expandCompositCharAtEnd(array, n, n2, n3);
                    }
                    if (b2) {
                        b2 = this.expandCompositCharAtNear(array, n, n2, 0, 0, 1);
                    }
                    if (b2) {
                        throw new ArabicShapingException("No spacefor lamalef");
                    }
                }
            }
            else if (n5 == 2) {
                if (this.expandCompositCharAtEnd(array, n, n2, n3)) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            }
            else if (n5 == 3) {
                if (this.expandCompositCharAtBegin(array, n, n2, n3)) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            }
            else if (n5 == 1) {
                if (this.expandCompositCharAtNear(array, n, n2, 0, 0, 1)) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            }
            else if (n5 == 0) {
                int n8 = n + n2;
                int n9 = n8 + n3;
                while (--n8 >= n) {
                    final char c = array[n8];
                    if (isNormalizedLamAlefChar(c)) {
                        array[--n9] = '\u0644';
                        array[--n9] = ArabicShaping.convertNormalizedLamAlef[c - '\u065c'];
                    }
                    else {
                        array[--n9] = c;
                    }
                }
                n2 += n3;
            }
        }
        else {
            if (n6 == 2097152 && this.expandCompositCharAtNear(array, n, n2, 0, 1, 0)) {
                throw new ArabicShapingException("No space for Seen tail expansion");
            }
            if (n7 == 16777216 && this.expandCompositCharAtNear(array, n, n2, 1, 0, 0)) {
                throw new ArabicShapingException("No space for YehHamza expansion");
            }
        }
        return n2;
    }
    
    private int normalize(final char[] array, final int n, final int n2) {
        int n3 = 0;
        for (int i = n; i < i + n2; ++i) {
            final char c = array[i];
            if (c >= '\ufe70' && c <= '\ufefc') {
                if (isLamAlefChar(c)) {
                    ++n3;
                }
                array[i] = (char)ArabicShaping.convertFEto06[c - '\ufe70'];
            }
        }
        return n3;
    }
    
    private int deshapeNormalize(final char[] array, final int n, final int n2) {
        int n3 = 0;
        final boolean b = (this.options & 0x3800000) == 0x1000000;
        final boolean b2 = (this.options & 0x700000) == 0x200000;
        for (int i = n; i < i + n2; ++i) {
            final char c = array[i];
            if (b && (c == '\u0621' || c == '\ufe80') && i < n2 - 1 && isAlefMaksouraChar(array[i + 1])) {
                array[i] = ' ';
                array[i + 1] = '\u0626';
            }
            else if (b2 && isTailChar(c) && i < n2 - 1 && isSeenTailFamilyChar(array[i + 1]) == 1) {
                array[i] = ' ';
            }
            else if (c >= '\ufe70' && c <= '\ufefc') {
                if (isLamAlefChar(c)) {
                    ++n3;
                }
                array[i] = (char)ArabicShaping.convertFEto06[c - '\ufe70'];
            }
        }
        return n3;
    }
    
    private int shapeUnicode(final char[] array, final int n, final int n2, int n3, final int n4) throws ArabicShapingException {
        final int normalize = this.normalize(array, n, n2);
        boolean b = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        int i = n + n2 - 1;
        int n5 = getLink(array[i]);
        int link = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = i;
        int j = -2;
        while (i >= 0) {
            if ((n5 & 0xFF00) > 0 || isTashkeelChar(array[i])) {
                int n9 = i - 1;
                j = -2;
                while (j < 0) {
                    if (n9 == -1) {
                        link = 0;
                        j = Integer.MAX_VALUE;
                    }
                    else {
                        link = getLink(array[n9]);
                        if ((link & 0x4) == 0x0) {
                            j = n9;
                        }
                        else {
                            --n9;
                        }
                    }
                }
                if ((n5 & 0x20) > 0 && (n7 & 0x10) > 0) {
                    b = true;
                    final char changeLamAlef = changeLamAlef(array[i]);
                    if (changeLamAlef != '\0') {
                        array[i] = '\uffff';
                        array[n8] = changeLamAlef;
                        i = n8;
                    }
                    n7 = n6;
                    n5 = getLink(changeLamAlef);
                }
                if (i > 0 && array[i - 1] == ' ') {
                    if (isSeenFamilyChar(array[i]) == 1) {
                        b2 = true;
                    }
                    else if (array[i] == '\u0626') {
                        b3 = true;
                    }
                }
                else if (i == 0) {
                    if (isSeenFamilyChar(array[i]) == 1) {
                        b2 = true;
                    }
                    else if (array[i] == '\u0626') {
                        b3 = true;
                    }
                }
                final int specialChar = specialChar(array[i]);
                int n10 = ArabicShaping.shapeTable[link & 0x3][n7 & 0x3][n5 & 0x3];
                if (specialChar == 1) {
                    n10 &= 0x1;
                }
                else if (specialChar == 2) {
                    if (n4 == 0 && (n7 & 0x2) != 0x0 && (link & 0x1) != 0x0 && array[i] != '\u064c' && array[i] != '\u064d' && ((link & 0x20) != 0x20 || (n7 & 0x10) != 0x10)) {
                        n10 = 1;
                    }
                    else if (n4 == 2 && array[i] == '\u0651') {
                        n10 = 1;
                    }
                    else {
                        n10 = 0;
                    }
                }
                if (specialChar == 2) {
                    if (n4 == 2 && array[i] != '\u0651') {
                        array[i] = '\ufffe';
                        b4 = true;
                    }
                    else {
                        array[i] = (char)(65136 + ArabicShaping.irrelevantPos[array[i] - '\u064b'] + n10);
                    }
                }
                else {
                    array[i] = (char)(65136 + (n5 >> 8) + n10);
                }
            }
            if ((n5 & 0x4) == 0x0) {
                n6 = n7;
                n7 = n5;
                n8 = i;
            }
            if (--i == j) {
                n5 = link;
                j = -2;
            }
            else {
                if (i == -1) {
                    continue;
                }
                n5 = getLink(array[i]);
            }
        }
        n3 = n2;
        if (b || b4) {
            n3 = this.handleGeneratedSpaces(array, n, n2);
        }
        if (b2 || b3) {
            n3 = this.expandCompositChar(array, n, n3, normalize, 0);
        }
        return n3;
    }
    
    private int deShapeUnicode(final char[] array, final int n, final int n2, int expandCompositChar) throws ArabicShapingException {
        final int deshapeNormalize = this.deshapeNormalize(array, n, n2);
        if (deshapeNormalize != 0) {
            expandCompositChar = this.expandCompositChar(array, n, n2, deshapeNormalize, 1);
        }
        else {
            expandCompositChar = n2;
        }
        return expandCompositChar;
    }
    
    private int internalShape(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4) throws ArabicShapingException {
        if (n2 == 0) {
            return 0;
        }
        if (n4 == 0) {
            if ((this.options & 0x18) != 0x0 && (this.options & 0x10003) == 0x0) {
                return this.calculateSize(array, n, n2);
            }
            return n2;
        }
        else {
            final char[] array3 = new char[n2 * 2];
            System.arraycopy(array, n, array3, 0, n2);
            if (this.isLogical) {
                invertBuffer(array3, 0, n2);
            }
            int n5 = n2;
            switch (this.options & 0x18) {
                case 24: {
                    n5 = this.shapeUnicode(array3, 0, n2, n4, 1);
                    break;
                }
                case 8: {
                    if ((this.options & 0xE0000) > 0 && (this.options & 0xE0000) != 0xC0000) {
                        n5 = this.shapeUnicode(array3, 0, n2, n4, 2);
                        break;
                    }
                    n5 = this.shapeUnicode(array3, 0, n2, n4, 0);
                    if ((this.options & 0xE0000) == 0xC0000) {
                        n5 = handleTashkeelWithTatweel(array3, n2);
                        break;
                    }
                    break;
                }
                case 16: {
                    n5 = this.deShapeUnicode(array3, 0, n2, n4);
                    break;
                }
            }
            if (n5 > n4) {
                throw new ArabicShapingException("not enough room for result data");
            }
            if ((this.options & 0xE0) != 0x0) {
                char c = '0';
                switch (this.options & 0x100) {
                    case 0: {
                        c = '\u0660';
                        break;
                    }
                    case 256: {
                        c = '\u06f0';
                        break;
                    }
                }
                switch (this.options & 0xE0) {
                    case 32: {
                        final int n6 = c - '0';
                        for (int i = 0; i < n5; ++i) {
                            final char c2 = array3[i];
                            if (c2 <= '9' && c2 >= '0') {
                                final char[] array4 = array3;
                                final int n7 = i;
                                array4[n7] += (char)n6;
                            }
                        }
                        break;
                    }
                    case 64: {
                        final char c3 = (char)(c + '\t');
                        final int n8 = '0' - c;
                        for (int j = 0; j < n5; ++j) {
                            final char c4 = array3[j];
                            if (c4 <= c3 && c4 >= c) {
                                final char[] array5 = array3;
                                final int n9 = j;
                                array5[n9] += (char)n8;
                            }
                        }
                        break;
                    }
                    case 96: {
                        this.shapeToArabicDigitsWithContext(array3, 0, n5, c, false);
                        break;
                    }
                    case 128: {
                        this.shapeToArabicDigitsWithContext(array3, 0, n5, c, true);
                        break;
                    }
                }
            }
            if (this.isLogical) {
                invertBuffer(array3, 0, n5);
            }
            System.arraycopy(array3, 0, array2, n3, n5);
            return n5;
        }
    }
    
    static {
        irrelevantPos = new int[] { 0, 2, 4, 6, 8, 10, 12, 14 };
        tailFamilyIsolatedFinal = new int[] { 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1 };
        tashkeelMedial = new int[] { 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        yehHamzaToYeh = new char[] { '\ufeef', '\ufef0' };
        convertNormalizedLamAlef = new char[] { '\u0622', '\u0623', '\u0625', '\u0627' };
        araLink = new int[] { 4385, 4897, 5377, 5921, 6403, 7457, 7939, 8961, 9475, 10499, 11523, 12547, 13571, 14593, 15105, 15617, 16129, 16643, 17667, 18691, 19715, 20739, 21763, 22787, 23811, 0, 0, 0, 0, 0, 3, 24835, 25859, 26883, 27923, 28931, 29955, 30979, 32001, 32513, 33027, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 34049, 34561, 35073, 35585, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 33, 33, 0, 33, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3, 1, 1 };
        presLink = new int[] { 3, 3, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 32, 33, 32, 33, 0, 1, 32, 33, 0, 2, 3, 1, 32, 33, 0, 2, 3, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 16, 18, 19, 17, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        ArabicShaping.convertFEto06 = new int[] { 1611, 1611, 1612, 1612, 1613, 1613, 1614, 1614, 1615, 1615, 1616, 1616, 1617, 1617, 1618, 1618, 1569, 1570, 1570, 1571, 1571, 1572, 1572, 1573, 1573, 1574, 1574, 1574, 1574, 1575, 1575, 1576, 1576, 1576, 1576, 1577, 1577, 1578, 1578, 1578, 1578, 1579, 1579, 1579, 1579, 1580, 1580, 1580, 1580, 1581, 1581, 1581, 1581, 1582, 1582, 1582, 1582, 1583, 1583, 1584, 1584, 1585, 1585, 1586, 1586, 1587, 1587, 1587, 1587, 1588, 1588, 1588, 1588, 1589, 1589, 1589, 1589, 1590, 1590, 1590, 1590, 1591, 1591, 1591, 1591, 1592, 1592, 1592, 1592, 1593, 1593, 1593, 1593, 1594, 1594, 1594, 1594, 1601, 1601, 1601, 1601, 1602, 1602, 1602, 1602, 1603, 1603, 1603, 1603, 1604, 1604, 1604, 1604, 1605, 1605, 1605, 1605, 1606, 1606, 1606, 1606, 1607, 1607, 1607, 1607, 1608, 1608, 1609, 1609, 1610, 1610, 1610, 1610, 1628, 1628, 1629, 1629, 1630, 1630, 1631, 1631 };
        shapeTable = new int[][][] { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 1, 0, 3 }, { 0, 1, 0, 1 } }, { { 0, 0, 2, 2 }, { 0, 0, 1, 2 }, { 0, 1, 1, 2 }, { 0, 1, 1, 3 } }, { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 1, 0, 3 }, { 0, 1, 0, 3 } }, { { 0, 0, 1, 2 }, { 0, 0, 1, 2 }, { 0, 1, 1, 2 }, { 0, 1, 1, 3 } } };
    }
}
